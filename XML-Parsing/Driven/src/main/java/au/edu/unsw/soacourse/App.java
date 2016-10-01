package au.edu.unsw.soacourse;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;


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
        db.dropTables();
        // db.createTables();
    }
}
