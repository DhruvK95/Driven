package au.edu.unsw.soacourse.driven;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.sqlite.core.DB;

import java.util.*;
import java.util.Calendar;


/**
 * Created by Dhruv on 4/10/2016. DrivenRest
 */
public class RMS_Impl {
    /*
    created: set when the notice is generated by the system
    cancelled: set when the request to process is cancelled by the driver
    requested: set when the driver places a request to process the renewal
    under_review: set when the request is being processed by the officer
    accepted : set when the outcome of the process is determined
    rejected: set when the outcome of the process is determined
    archived: set when the driver deletes the request (after it is rejected, or cancelled)

    created, cancelled, requested, under_review, accepted, archived, rejected
     */

    public RenewalNotice updateRenewalNotice(RenewalNotice renewalNotice, String auth, String status) {
        renewalNotice.setStatus(status);
        DB_Handler db = new DB_Handler();
        db.updateNotice(renewalNotice.nid, renewalNotice.status);
        return renewalNotice;
    }

    public void updateNotice(Integer nid, String status) {
        DB_Handler db = new DB_Handler();
        db.updateNotice(nid,status);
        System.out.println("Renewal notice with NID: " + nid + " was " + status);
    }

    /**
     * Generates a list renewal Notices and adds them to the DB (avoids duplicate entries in the DB)
     * @return List of Renewal Notices
     */
    public List<RenewalNotice> generateNotices() {
        List<RenewalNotice> generatedNotices = new ArrayList<>();
        System.out.println("generateNotices Invoked");

        // Get list of drivers
        List<Registration> rList = new ArrayList<Registration>();
        XML_Handler xh = new XML_Handler();
        rList = xh.makeRegistrationList();
        // System.out.println("Registrations:");
        // System.out.println(rList);

        DB_Handler db = new DB_Handler();

        // Get today's date
        Date currDate = new Date();
        System.out.println(currDate.toString());


        // For each driver.validTill ..., if <condition>
        for (Registration reg : rList) {
            // (Date in <RegistrationValidTill>) - 30 days < TODAY's DATE < (Date in <RegistrationValidTill>)
            Calendar cal = Calendar.getInstance();
            cal.setTime(reg.getValidTill());
            cal.add(Calendar.DATE, -30);
            // rDate is (Date in <RegistrationValidTill>) - 30 days
            Date validTill_30 = cal.getTime();
            if ((validTill_30.equals(currDate) || validTill_30.before(currDate)) &&
                    (currDate.before(reg.getValidTill()) || currDate.equals(reg.getValidTill()))) {

                // Create new renewal notice
                System.out.println("Notice generation condition met");
                RenewalNotice rn = new RenewalNotice(db.getRenewalNoticesRows(), reg.getrID(), "created");
                // Check for duplicates before inserting into the DB
                if (!checkDuplicates(rn)) {
                    // Insert into db
                    db.addRenewalNotice(rn);
                    // Add to the generatedNotices list
                    generatedNotices.add(rn);
                } else {
                    System.out.println("Notice already exists in DB");
                }
            }
        }
        return generatedNotices;
    }

    /**
     * Checks weather a Renewal Notice already exists in the DB
     * @param renewalNotice Renewal Notice to check
     * @return True if duplicate found
     */
    private Boolean checkDuplicates(RenewalNotice renewalNotice) {
        Boolean b = false;
        DB_Handler db = new DB_Handler();
        // Get list of current notices (used for checking duplicates)
        List<RenewalNotice> dbRenewalsList = db.getRenewalNoticesList();
        for (RenewalNotice aRN : dbRenewalsList) {
            if (aRN.equals(renewalNotice)) {
                return Boolean.TRUE;
            }
        }
        return b;
    }

    public RenewalNotice getNotice(Integer nid) {
        RenewalNotice renewalNotice = null;
        DB_Handler db_handler = new DB_Handler();
        List<RenewalNotice> notices = db_handler.getRenewalNoticesList();
        for (RenewalNotice aRN : notices) {
            if (aRN.getNid().equals(nid)) {
                renewalNotice = aRN;
            }
        }
        return renewalNotice;
    }

    public Boolean noticeExists(Integer nid) {
        Boolean exists = Boolean.FALSE;

        DB_Handler db_handler = new DB_Handler();
        List<RenewalNotice> notices = db_handler.getRenewalNoticesList();
        for (RenewalNotice aRN : notices) {
            if (aRN.getNid().equals(nid)) {
                exists = Boolean.TRUE;
                break;
            }
        }

        return exists;
    }
}
