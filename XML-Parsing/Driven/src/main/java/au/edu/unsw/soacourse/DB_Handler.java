package au.edu.unsw.soacourse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Created by Dhruv on 1/10/2016. Driven
 */
public class DB_Handler {

    public void createTables () {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Driven.db");
            System.out.println("DBh: Opened database successfully");
            stmt = c.createStatement();
            String sql = "CREATE TABLE Renewal_Notices (" +
                    "nid INT PRIMARY KEY," +
                    "rid INT NOT NULL," +
                    "status CHAR(50)" +
                    ");\n " +
                    "CREATE TABLE Payments (" +
                    "pid INT PRIMARY KEY, " +
                    "nid INT NOT NULL, " +
                    "amount INT NOT NULL, " +
                    "credit_card_number INT NOT NULL," +
                    "credit_card_name CHAR(500), " +
                    "credit_card_ccv INT NOT NULL, " +
                    "paid_date CHAR(100)" +
                    ");\n";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("DBh: Tables created successfully");
    }

    public void dropTables () {
        //DROP TABLE Renewal_Notices;
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Driven.db");
            System.out.println("DBh: Opened database successfully");
            stmt = c.createStatement();
            String sql = "DROP TABLE Renewal_Notices; \n" +
                    "DROP TABLE Payments;";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("DBh: Tables dropped successfully");

    }
}
