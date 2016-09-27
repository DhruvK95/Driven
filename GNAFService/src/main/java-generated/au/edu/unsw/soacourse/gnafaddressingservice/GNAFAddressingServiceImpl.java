package au.edu.unsw.soacourse.gnafaddressingservice;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.cxf.common.util.SortedArraySet;
import org.apache.cxf.transport.http.DestinationRegistry;
import org.sqlite.core.DB;
import sun.java2d.pipe.SpanShapeRenderer;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.*;
import java.lang.*;

/**
 * Created by Dhruv on 2/09/2016. Asi1
 */
public class GNAFAddressingServiceImpl implements GNAFAddressingService{
    public AddressExistsInFileResponse addressExistsInFile(AddressExistsInFileRequest parameters) throws AddressExistsFaultMsg{
        String outputMessage = "";
        if (parameters.getFullAddress() != null && !parameters.getFullAddress().isEmpty()) {
            System.out.println("The input was " + parameters.getFullAddress());

//            Extract parameters from input
//            street number, street name, suburb name, state, postcode
            Integer counter = 0;
            String pattern = "(.*)?,(.*)?,(.*)?,(.*)?,(.*)?";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(parameters.getFullAddress());
            if (m.find()) {
                System.out.println("Found street number: " + m.group(1) );
                System.out.println("Found street name: " + m.group(2) );
                System.out.println("Found suburb name: " + m.group(3) );
                System.out.println("Found state: " + m.group(4) );
                System.out.println("Found postcode: " + m.group(5) );
            } else {

                String code = "ERROR 2";
                String msg = "The input string was incorrectly formatted, please use the format 'street number, " +
                        "street name, suburb name, state, postcode' and provide correct input for each part";
                ServiceFaultType simAddFaultMessage = new ServiceFaultType();
                simAddFaultMessage.setErrtext(msg);
                simAddFaultMessage.setErrcode(code);
                throw new AddressExistsFaultMsg(msg, simAddFaultMessage);
            }

            // Check the numbers were numbers, Characters were correct.
            if (!postcodeFormatCorrect(m.group(5))) {
                String code = "ERROR 2";
                String msg = "The Postcode was not the right format, it must consist of 4 numbers eg. '2016'";
                ServiceFaultType simAddFaultMessage = new ServiceFaultType();
                simAddFaultMessage.setErrtext(msg);
                simAddFaultMessage.setErrcode(code);
                throw new AddressExistsFaultMsg(msg, simAddFaultMessage);
            }
            if (!streetNumberFormatCorrect(m.group(1))) {
                String code = "ERROR 2";
                String msg = "The Street Number was not the right format, it must consist of numbers";
                ServiceFaultType simAddFaultMessage = new ServiceFaultType();
                simAddFaultMessage.setErrtext(msg);
                simAddFaultMessage.setErrcode(code);
                throw new AddressExistsFaultMsg(msg, simAddFaultMessage);
            }
            if (!stateFormatCorrect(m.group(4))) {
                String code = "ERROR 2";
                String msg = "The State was not the right format, it must consist of Characters eg. 'ACT'";
                ServiceFaultType simAddFaultMessage = new ServiceFaultType();
                simAddFaultMessage.setErrtext(msg);
                simAddFaultMessage.setErrcode(code);
                throw new AddressExistsFaultMsg(msg, simAddFaultMessage);
            }
            if (!(streetAndSuburbFormatCorrect(m.group(2)) || streetAndSuburbFormatCorrect(m.group(3)))) {
                String code = "ERROR 2";
                String msg = "The Street Name or Suburb were not the right format, it must consist of Characters ";
                ServiceFaultType simAddFaultMessage = new ServiceFaultType();
                simAddFaultMessage.setErrtext(msg);
                simAddFaultMessage.setErrcode(code);
                throw new AddressExistsFaultMsg(msg, simAddFaultMessage);
            }


            // Connect to DB to check for exact match
            Connection c = null;
            Statement stmt = null;
            ResultSet rs;

            try {
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite::resource:gnaf.db");
                c.setAutoCommit(false);
                if (c != null) {
                    System.out.println("Opened database successfully");

                    stmt = c.createStatement();
                    String SQLQuery = "SELECT addresses.number_first AS street_number, " +
                            "streets.name AS street_name1,  " +
                            "streets.type_code AS street_name2, " +
                            "localities.name AS suburb_name, " +
                            "states.abbrev AS state,  " +
                            "localities.postcode FROM addresses " +
                            "JOIN streets ON addresses.street = streets.id JOIN " +
                            "localities ON addresses.locality = localities.id  " +
                            "JOIN states ON localities.in_state = states.id;";
                    rs = stmt.executeQuery(SQLQuery);
                    while (rs.next()) {
                        if (rs.getString("street_number") == null || rs.getString("street_number").isEmpty()) {
                            System.out.println("Null figure found");
                            continue;
                        }

                        String street_name = new String();
                        street_name = street_name.concat(rs.getString("street_name1"));
                        street_name = street_name.concat(" ");
                        street_name = street_name.concat(rs.getString("street_name2"));
                        System.out.println(counter.toString() + " " + street_name);
                        counter++;
                        //street number, street name, suburb name, state, postcode
                        if (rs.getString("street_number").equalsIgnoreCase(m.group(1).trim()) &&
                                street_name.equalsIgnoreCase(m.group(2).trim()) &&
                                rs.getString("suburb_name").equalsIgnoreCase(m.group(3).trim()) &&
                                rs.getString("state").equalsIgnoreCase(m.group(4).trim()) &&
                                rs.getString("postcode").equalsIgnoreCase(m.group(5).trim())
                                ) {
                            System.out.println("Found Exact match!!!!!!!!!!!!!!");
                            outputMessage = ("Found Address: " + parameters.getFullAddress());
                            AddressExistsInFileResponse afr = new AddressExistsInFileResponse();
                            afr.setMatchingAddress(outputMessage);
                            return afr;
                            //Set the final string to the address here
                        }
                    }
                    // No matches were found...
                    // Set Final string appropriately
                    rs.close();
                    stmt.close();
                    c.close();
                }
            } catch ( Exception e ) {
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            }
        } else {

            String code = "ERROR 1";
            String msg = "The input string was empty";
            ServiceFaultType simAddFaultMessage = new ServiceFaultType();
            simAddFaultMessage.setErrtext(msg);
            simAddFaultMessage.setErrcode(code);
            throw new AddressExistsFaultMsg(msg, simAddFaultMessage);
        }

        String code = "ERROR 0";
        String msg = "No Matching address found";
        ServiceFaultType simAddFaultMessage = new ServiceFaultType();
        simAddFaultMessage.setErrtext(msg);
        simAddFaultMessage.setErrcode(code);
        throw new AddressExistsFaultMsg(msg, simAddFaultMessage);

    }

    public SimilarAddressesInFileResponse similarAddressesInFile(SimilarAddressesInFileRequest parameters) throws
            SimilarAddressesFaultMsg {
        List<LDist> resultList = new ArrayList<LDist>();


//            Extract parameters from input
//            street number, street name, suburb name, state, postcode
        Integer counter = 0;
        String inputStreetNumber = parameters.getStreetNumber();
        String inputStreetName = parameters.getStreetName();
        String inputSuburbName = parameters.getSuburbName();
        String inputState = parameters.getState();
        String inputPostcode = parameters.getPostcode();
        String fullInput = inputStreetNumber + ',' + inputStreetName + ',' +
                inputSuburbName + ',' + inputState + ',' + inputPostcode;

        // Check the numbers were numbers, Characters were correct.
//        if (!streetNumberFormatCorrect(inputPostcode)) {
//            String code = "ERROR 2";
//            String msg = "The Postcode was not the right format, it must consist of 4 numbers eg. '2016'";
//            ServiceFaultType simAddFaultMessage = new ServiceFaultType();
//            simAddFaultMessage.setErrtext(msg);
//            simAddFaultMessage.setErrcode(code);
//            throw new SimilarAddressesFaultMsg(msg, simAddFaultMessage);
//        }
        if (!streetNumberFormatCorrect(inputStreetNumber)) {
            String code = "ERROR 2";
            String msg = "The Street Number was not the right format, it must consist of numbers";
            ServiceFaultType simAddFaultMessage = new ServiceFaultType();
            simAddFaultMessage.setErrtext(msg);
            simAddFaultMessage.setErrcode(code);
            throw new SimilarAddressesFaultMsg(msg, simAddFaultMessage);
        }
//        if (!stateFormatCorrect(inputState)) {
//            String code = "ERROR 2";
//            String msg = "The State was not the right format, it must consist of Characters eg. 'ACT'";
//            ServiceFaultType simAddFaultMessage = new ServiceFaultType();
//            simAddFaultMessage.setErrtext(msg);
//            simAddFaultMessage.setErrcode(code);
//            throw new SimilarAddressesFaultMsg(msg, simAddFaultMessage);
//        }
        if (!(streetAndSuburbFormatCorrect(inputStreetName))) {
            String code = "ERROR 2";
            String msg = "The Street Name or Suburb were not the right format, it must consist of Characters ";
            ServiceFaultType simAddFaultMessage = new ServiceFaultType();
            simAddFaultMessage.setErrtext(msg);
            simAddFaultMessage.setErrcode(code);
            throw new SimilarAddressesFaultMsg(msg, simAddFaultMessage);
        }

        // Connect to DB to check for exact match
        Connection c = null;
        Statement stmt = null;
        ResultSet rs;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite::resource:gnaf.db");
            c.setAutoCommit(false);
            if (c != null) {
                System.out.println("Opened database successfully");

                stmt = c.createStatement();
                String SQLQuery = "SELECT addresses.number_first AS street_number, " +
                        "streets.name AS street_name1,  " +
                        "streets.type_code AS street_name2, " +
                        "localities.name AS suburb_name, " +
                        "states.abbrev AS state,  " +
                        "localities.postcode FROM addresses " +
                        "JOIN streets ON addresses.street = streets.id JOIN " +
                        "localities ON addresses.locality = localities.id  " +
                        "JOIN states ON localities.in_state = states.id;";
                rs = stmt.executeQuery(SQLQuery);
                while (rs.next()) {
                    System.out.println("In while");
                    if (rs.getString("street_number") == null || rs.getString("street_number").isEmpty()) {
                        System.out.println("Null figure found");
                        continue;
                    } else {
                        System.out.println("Failed case");
                    }
                    String street_name = new String();
                    street_name = street_name.concat(rs.getString("street_name1"));
                    street_name = street_name.concat(" ");
                    street_name = street_name.concat(rs.getString("street_name2"));
                    System.out.println(counter.toString() + " " + street_name);
                    counter++;

                    // 1. Turn each DB element into a string
                    String DB_String;
                    DB_String = rs.getString("street_number") + ","
                            + street_name + ","
                            + rs.getString("suburb_name") + ","
                            + rs.getString("state") + ","
                            + rs.getString("postcode");
                    // System.out.println("Testing " + DB_String);

                    // 2. Calc LD score,
                    Integer LDScore = Levenshteindistance(DB_String, fullInput);
                    LDist LD = new LDist();
                    LD.setScore(LDScore);
                    LD.setSearchString(DB_String);
                    // append score and DB elem_str
                    resultList.add(LD);
                }
                // 3. Sort on score
                Collections.sort(resultList);


                rs.close();
                stmt.close();
                c.close();

                Set<String> resultSet = new SortedArraySet<>();
                int i = 0;
                while (resultSet.size() < 3) {
                    resultSet.add(resultList.get(i).getSearchString());
                    i++;
                }

                // 4. Return top 3
                SimilarAddressesInFileResponse sar = new SimilarAddressesInFileResponse();
                ArrayList<String> fr = new ArrayList<>();
                Iterator<String> iterator = resultSet.iterator();
                while (iterator.hasNext()) {
                    fr.add(iterator.next());
                }

                sar.setSimilarAddress1(fr.get(0));
                sar.setSimilarAddress2(fr.get(1));
                sar.setSimilarAddress3(fr.get(2));
                return sar;
            }
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.out.println("In Catch block....");
        }

        String code = "ERROR 0";
        String msg = "No Similar addresses found";
        ServiceFaultType simAddFaultMessage = new ServiceFaultType();
        simAddFaultMessage.setErrtext(msg);
        simAddFaultMessage.setErrcode(code);
        throw new SimilarAddressesFaultMsg(msg, simAddFaultMessage);

    }

    public ReturnPostcodeInfoResponse returnPostcodeInfo(ReturnPostcodeInfoRequest parameters) throws ReturnPostcodeFaultMsg {

        //operation receives a name of a suburb and state, returns the relevant postcode
        String inputSuburb = parameters.getSuburbName();
        String inputState = parameters.getStateName();

        if (!stateFormatCorrect(inputState)) {
            String code = "ERROR 2";
            String msg = "The State was not the right format, it must consist of Characters eg. 'ACT'";
            ServiceFaultType simAddFaultMessage = new ServiceFaultType();
            simAddFaultMessage.setErrtext(msg);
            simAddFaultMessage.setErrcode(code);
            throw new ReturnPostcodeFaultMsg(msg, simAddFaultMessage);
        }
        if (!(streetAndSuburbFormatCorrect(inputSuburb))) {
            String code = "ERROR 2";
            String msg = "The Street Name or Suburb were not the right format, it must consist of Characters ";
            ServiceFaultType simAddFaultMessage = new ServiceFaultType();
            simAddFaultMessage.setErrtext(msg);
            simAddFaultMessage.setErrcode(code);
            throw new ReturnPostcodeFaultMsg(msg, simAddFaultMessage);
        }


        // Connect to DB to check for exact match
        Connection c = null;
        Statement stmt = null;
        ResultSet rs;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite::resource:gnaf.db");
            c.setAutoCommit(false);
            if (c != null) {
                System.out.println("Opened database successfully");

                stmt = c.createStatement();
                String SQLQuery = "SELECT localities.name AS suburb_name, states.abbrev AS state, localities" +
                        ".postcode FROM localities JOIN states ON localities.in_state = states.id;";
                rs = stmt.executeQuery(SQLQuery);
                while (rs.next()) {
                    if (rs.getString("suburb_name").trim().equalsIgnoreCase(inputSuburb) &&
                            rs.getString("state").trim().equalsIgnoreCase(inputState)) {
                        ReturnPostcodeInfoResponse rp = new ReturnPostcodeInfoResponse();
                        rp.setPostcode(rs.getString("postcode"));
                        return rp;
                    }
                }
                rs.close();
                stmt.close();
                c.close();

                // No matches were found...
                // Set Error string appropriately
                String code = "ERROR 0";
                String msg = "No Corresponding postcode was found, please try again with correct Suburb and " +
                        "corresponding State inputs.";
                ServiceFaultType simAddFaultMessage = new ServiceFaultType();
                simAddFaultMessage.setErrtext(msg);
                simAddFaultMessage.setErrcode(code);
                throw new ReturnPostcodeFaultMsg(msg, simAddFaultMessage);
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
//                System.exit(0);
        }

        String code = "ERROR 0";
        String msg = "No Corresponding postcode was found, please try again with correct Suburb and " +
                "corresponding State inputs.";
        ServiceFaultType simAddFaultMessage = new ServiceFaultType();
        simAddFaultMessage.setErrtext(msg);
        simAddFaultMessage.setErrcode(code);
        throw new ReturnPostcodeFaultMsg(msg, simAddFaultMessage);

    }

    // Source: https://rosettacode.org/wiki/Levenshtein_distance#Java
    private static int Levenshteindistance(String a, String b) {
        a = a.toLowerCase();
        b = b.toLowerCase();
        // i == 0
        int [] costs = new int [b.length() + 1];
        for (int j = 0; j < costs.length; j++)
            costs[j] = j;
        for (int i = 1; i <= a.length(); i++) {
            // j == 0; nw = lev(i - 1, j)
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= b.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[b.length()];
    }

    private boolean postcodeFormatCorrect (String s) {
        boolean b = true;
        if (!(s.trim().matches("\\d\\d\\d\\d"))) {
            return false;
        }
        return b;
    }

    private boolean streetNumberFormatCorrect (String s) {
        boolean b = true;
        if (!(s.trim().matches("\\d+"))) {
            return false;
        }
        return b;
    }

    private boolean stateFormatCorrect (String s) {
        boolean b = true;
        if (!(s.trim().matches("\\w+"))) {
            return false;
        }
        return b;
    }

    private boolean streetAndSuburbFormatCorrect (String s) {
        boolean b = true;
        //TODO: Correct the regex
        if (!(s.trim().matches("[A-Za-z]*\\s*?[A-Za-z]+"))) {
            return false;
        }
        return b;
    }


}
