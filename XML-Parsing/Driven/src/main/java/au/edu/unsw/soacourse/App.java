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
        // db.dropTables();
        // db.createTables();
        db.addRenewalNotice(5, "xD");
        // SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
        Date currDate = new Date();
        System.out.println(currDate.toString());
        db.addPayment(0, 200, 198231, "Dhruv", 222, currDate);
    }
}
