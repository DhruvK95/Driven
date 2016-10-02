package au.edu.unsw.soacourse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.Date;

public class App
{
    public static void main( String[] args )
    {
        // XML Testing
        List<Registration> rList = new ArrayList<Registration>();
        XML_Handler xh = new XML_Handler();
        rList = xh.makeRegistrationList();
        System.out.println("Registrations:");
        System.out.println(rList);
        xh.updateRegistrationValidTill(2,rList.get(0).getValidTill());
        xh.updateAddress(2, "zXXXXXXX@unsw.edu.au");

        // DB Testing
        DB_Handler db = new DB_Handler();

            // Create/Drop Tables testing
        // db.dropTables();
        // db.createTables();

            // addRenewalNotice/addPayment testing
        db.addRenewalNotice(5, "xD");
        Date currDate = new Date();
        db.addPayment(0, 200, 198231, "Dhruv", 222, currDate);

            // getPaymentsList Testing
        List<Payment> p = new ArrayList<Payment>();
        p = db.getPaymentsList();
        System.out.println("Payment List: " + p);

            // getRenewalNoticesList testing
        List<RenewalNotice> rnl = new ArrayList<RenewalNotice>();
        rnl = db.getRenewalNoticesList();
        System.out.println("Renewal Notice List: " + rnl);

            // deleteRenewalNotice testing

            // deletePayment testing

    }
}
