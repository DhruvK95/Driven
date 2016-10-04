package au.edu.unsw.soacourse.driven;

import java.util.*;
import java.util.Calendar;


/**
 * Created by Dhruv on 4/10/2016. DrivenRest
 */
public class RMS_Impl {

    public void generateNotices() {
        // TODO: Change function to return list of generated notices
        System.out.println("generateNotices Invoked");

        // Get list of drivers
        List<Registration> rList = new ArrayList<Registration>();
        XML_Handler xh = new XML_Handler();
        rList = xh.makeRegistrationList();
        System.out.println("Registrations:");
        System.out.println(rList);

        DB_Handler db = new DB_Handler();

        // Get today's date
        Date currDate = new Date();
        System.out.println(currDate.toString());

        // For each driver.date ..., if <condition>
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
                // TODO: Check for duplicates before inserting into the DB
                // Insert into db
                db.addRenewalNotice(rn);
                // TODO: Add to the generatedNotices list
            }
        }
    }
}
