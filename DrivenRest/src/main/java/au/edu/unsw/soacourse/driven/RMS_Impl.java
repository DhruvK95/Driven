package au.edu.unsw.soacourse.driven;

import java.util.*;
import java.util.Calendar;


/**
 * Created by Dhruv on 4/10/2016. DrivenRest
 */
public class RMS_Impl {

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
            // (Date in <RegistrationValidTill>) - 30 days <= TODAY's DATE <= (Date in <RegistrationValidTill>)
            Calendar cal = Calendar.getInstance();
            cal.setTime(reg.getValidTill());
            cal.add(Calendar.DATE, -30);
            // rDate is (Date in <RegistrationValidTill>) - 30 days
            Date rDate = cal.getTime();
            if (rDate.equals(currDate) || rDate.before(currDate) || currDate.before(reg.getValidTill())) {
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
}
