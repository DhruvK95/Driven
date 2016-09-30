package au.edu.unsw.soacourse;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by Dhruv on 1/10/2016. Driven
 */
public class DB_Handler {

    public void createTables () {
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Driven.db");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("DBh: Opened database successfully");

    }
}
