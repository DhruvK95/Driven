package au.edu.unsw.soacourse.driven;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Dhruv on 1/10/2016. Driven
 */
public class DB_Handler {

    /* Sample DB connection code
Connection c = null;
Statement stmt = null;
try {
    Class.forName("org.sqlite.JDBC");
    c = DriverManager.getConnection("jdbc:sqlite:Driven.db");
    System.out.println("DBh: Opened database successfully");
    stmt = c.createStatement();
    String sql = "";
    stmt.executeUpdate(sql);
    c.close();
} catch ( Exception e ) {
    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
    System.exit(0);
}
     */
    // Find DB via:
    // find ~/ -type f -name "Driven.db"

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
                    ");\n ";
            stmt.executeUpdate(sql);
            stmt.close();

            stmt = c.createStatement();
            sql = "CREATE TABLE Payments (" +
                    "pid INT PRIMARY KEY, " +
                    "nid INT NOT NULL, " +
                    "amount INT NOT NULL, " +
                    "credit_card_number INT NOT NULL," +
                    "credit_card_name CHAR(500), " +
                    "credit_card_ccv INT NOT NULL, " +
                    "paid_date CHAR(100)" +
                    ", CONSTRAINT\n" +
                    "  Payments_Renewal_Notices_nid_fk FOREIGN KEY (nid) REFERENCES Renewal_Notices (nid)" +
                    ");\n";
            stmt.executeUpdate(sql);
            c.close();
        } catch (Exception e) {
            System.out.println("Create Tables ERROR");
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
            System.out.println("Drop tables ERROR");
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("DBh: Tables dropped successfully");

    }

    public List<RenewalNotice> getRenewalNoticesList () {
        List<RenewalNotice> rnl = new ArrayList<RenewalNotice>();
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Driven.db");
            System.out.println("DBh/getRenewalNoticesList: Opened database successfully");
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Renewal_Notices;");
            while (rs.next()) {
                Integer nid = rs.getInt("nid");
                Integer rid = rs.getInt("rid");
                String strStatus = rs.getString("status");
                // RenewalNotice.Status s = new RenewalNotice.Status(A)
                RenewalNotice rn = new RenewalNotice(nid, rid, strStatus);
                rnl.add(rn);
            }
                c.close();
        } catch ( Exception e ) {
            System.out.println("getRenewalNoticesList ERROR");
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Successfully returning RenewalNoticesList");
        return rnl;
    }

    public void addPayment(Integer nid, Integer amount, Integer credit_card_number, String credit_card_name, Integer
            credit_card_ccv, Date paid_date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
        String strDate = sdf.format(paid_date);
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Driven.db");
            // System.out.println("DBh/addRenewalNotice: Opened database successfully");
            stmt = c.createStatement();
            Integer paymentID = getPaymentsRows();
            //INSERT INTO Renewal_Notices (nid, rid, status) VALUES (0, 0, 'String');
            String sql = "INSERT INTO Payments (pid, nid, amount, credit_card_number, credit_card_name, credit_card_ccv, paid_date) VALUES ("
                    + paymentID.toString() + ","
                    + nid.toString() + ","
                    + amount.toString() + ","
                    + credit_card_number.toString() + ","
                    + "'" + credit_card_name + "'" + ","
                    + credit_card_ccv.toString() + ","
                    + "'" + strDate + "'"
                    + ");";
            //Insert Close code
            stmt.executeUpdate(sql);
            if (getPaymentsRows() > paymentID)
                System.out.println("DBh/addPayment: Payment successfully added");
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.out.println("addPayment ERROR");
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Payment added successfully");
    }

    public void addRenewalNotice(Integer rid, String status) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Driven.db");
            // System.out.println("DBh/addRenewalNotice: Opened database successfully");
            stmt = c.createStatement();
            Integer notice_ID = getRenewalNoticesRows();
            //INSERT INTO Renewal_Notices (nid, rid, status) VALUES (0, 0, 'String');
            String sql = "INSERT INTO Renewal_Notices (nid, rid, status) VALUES ("
                    + notice_ID.toString() + ","
                    + rid.toString() + ","
                    + "'" + status + "'"
                    + ");";
            //Insert Close code
            stmt.executeUpdate(sql);
            if (getRenewalNoticesRows() > notice_ID)
                System.out.println("DBh: Renewal notice " + rid.toString() + " " + status + " successfully added");
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.out.println("addRenewalNotice ERROR");
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    /**
     * Overloaded addPayment method, takes in a Payment object. The pid in the object is disregarded, the
     * function calculates the pid itself.
     * @param payment Payment object
     */
    public void addPayment(Payment payment) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
        String strDate = sdf.format(payment.getPaid_date());
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Driven.db");
            // System.out.println("DBh/addPayment_o: Opened database successfully");
            stmt = c.createStatement();
            Integer paymentID = getPaymentsRows();
            String sql = "INSERT INTO Payments (pid, nid, amount, credit_card_number, credit_card_name, credit_card_ccv, paid_date) VALUES ("
                    + paymentID.toString() + ","
                    + payment.getNid().toString() + ","
                    + payment.getAmount().toString() + ","
                    + payment.getCredit_card_number().toString() + ","
                    + "'" + payment.getCredit_card_name() + "'" + ","
                    + payment.getCredit_card_ccv().toString() + ","
                    + "'" + strDate + "'"
                    + ");";
            //Insert Close code
            stmt.executeUpdate(sql);
            if (getPaymentsRows() > paymentID)
                System.out.println("DBh/addPayment_o: Payment successfully added");
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.out.println("addPayment_o ERROR adding " + payment.toString());
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }


    /**
     * Overloaded addRenewalNotice method, takes in a RenewalNotice object. The nid in the object is disregarded, the
     * function calculates the nid itself.
     * @param renewalNotice RenewalNotice object
     */
    public void addRenewalNotice(RenewalNotice renewalNotice) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Driven.db");
            // System.out.println("DBh/addRenewalNotice_o: Opened database successfully");
            stmt = c.createStatement();
            Integer notice_ID = getRenewalNoticesRows();
            //INSERT INTO Renewal_Notices (nid, rid, status) VALUES (0, 0, 'String');
            String sql = "INSERT INTO Renewal_Notices (nid, rid, status) VALUES ("
                    + notice_ID.toString() + ","
                    + renewalNotice.getRid().toString() + ","
                    + "'" + renewalNotice.getStatus() + "'"
                    + ");";
            //Insert Close code
            stmt.executeUpdate(sql);
            if (getRenewalNoticesRows() > notice_ID)
                System.out.println("DBh: Renewal notice " + renewalNotice.getNid().toString() + " " + renewalNotice.getStatus()
                        + " " + "successfully added");
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.out.println("addRenewalNotice_o ERROR adding " + renewalNotice.toString());
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public List<Payment> getPaymentsList() {
        Connection c = null;
        Statement stmt = null;
        List<Payment> lp = new ArrayList<Payment>();
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Driven.db");
            System.out.println("DBh/getPaymentsList: Opened database successfully");
            stmt = c.createStatement();
            // String sql = "";
            ResultSet rs = stmt.executeQuery("SELECT * FROM Payments;");
            while (rs.next()) {
                Integer pid = rs.getInt("pid");
                Integer nid = rs.getInt("nid");
                Integer amount = rs.getInt("amount");
                Integer credit_card_number = rs.getInt("credit_card_number");
                String credit_card_name = rs.getString("credit_card_name");
                Integer credit_card_ccv = rs.getInt("credit_card_ccv");
                String strDate = rs.getString("paid_date");
                DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                Date date = format.parse(strDate);
                Payment p = new Payment(pid, nid, amount, credit_card_number, credit_card_name, credit_card_ccv, date);
                lp.add(p);
            }
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.out.println("getPaymentsList ERROR");
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Returning Payments List");
        return lp;
    }

    public void deletePayment(Integer pid) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Driven.db");
            // System.out.println("DBh/deletePayment: Opened database successfully");
            stmt = c.createStatement();
            String sql = "DELETE FROM Payments WHERE pid=" + pid.toString() + ";";
            stmt.executeUpdate(sql);
            System.out.println("DBh/deletePayment: " + pid.toString() + " deleteRenewalNotice completed");
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.out.println("deletePayment ERROR");
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public void deleteRenewalNotice(Integer nid) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Driven.db");
            System.out.println("DBh/deleteRenewalNotice: Opened database successfully");
            stmt = c.createStatement();
            String sql = "DELETE FROM Renewal_Notices WHERE nid=" + nid.toString() + ";";
            stmt.executeUpdate(sql);
            System.out.println("DBh/deleteRenewalNotice: " + nid.toString() + " deleteRenewalNotice completed");
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.out.println("deleteRenewalNotice ERROR");
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public Integer getRenewalNoticesRows () {
        Integer i = 0;
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Driven.db");
            // System.out.println("DBh/getRenewalNoticesRows: Opened database successfully");
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT MAX(nid) AS MAX FROM Renewal_Notices;");
            i = rs.getInt("MAX") + 1;
            stmt.close();
            c.close();
            return i;

        } catch ( Exception e ) {
            System.out.println("renewalNoticesRows ERROR");
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        return i;
    }

    public Integer getPaymentsRows () {
        Integer i = 0;
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Driven.db");
            // System.out.println("DBh/getPaymentsRows: Opened database successfully");
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT MAX(pid) AS MAX FROM Payments;");
            i = rs.getInt("MAX") + 1;
            stmt.close();
            c.close();
            return i;

        } catch ( Exception e ) {
            System.out.println("getPaymentsRows ERROR");
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        return i;
    }

}
