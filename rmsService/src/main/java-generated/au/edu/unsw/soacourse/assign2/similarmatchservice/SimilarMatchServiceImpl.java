package au.edu.unsw.soacourse.assign2.similarmatchservice;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jws.WebService;

import org.apache.cxf.common.util.SortedArraySet;

@WebService(endpointInterface = "au.edu.unsw.soacourse.assign2.similarmatchservice.SimilarMatchService")
public class SimilarMatchServiceImpl implements SimilarMatchService {
	ObjectFactory factory = new ObjectFactory();

	@Override
	public ValidateSimilarMatchResponse validateSimilarMatch(
			ValidateSimilarMatchRequest parameters) {
		
		ValidateSimilarMatchResponse res  = factory.createValidateSimilarMatchResponse();
		
		
		System.out.println("Similair MAtch--> address: " + parameters.address);
		String m1= "Exact Address Not Found \n"
				+ "Similar Addresses Found, List included below"
				+ "\n";
		res.messageStatus="Output Closed";
		
        Integer counter = 0;
        String pattern = "(.*)?,(.*)?,(.*)?,(.*)?,(.*)?";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(parameters.address);
        System.out.print(parameters.address);
        String inputStreetNumber = null;
        String inputStreetName = null;
        String inputSuburbName = null;
        String inputState = null;
        String inputPostcode = null;
        List<LDist> resultList = new ArrayList<LDist>();


        if (m.find()) {
            System.out.println("Found street number: " + m.group(1) );
            System.out.println("Found street name: " + m.group(2) );
            System.out.println("Found suburb name: " + m.group(3) );
            System.out.println("Found state: " + m.group(4) );
            System.out.println("Found postcode: " + m.group(5) );
            inputStreetNumber =  m.group(1);
            inputStreetName =  m.group(2);
            inputSuburbName = 	 m.group(3);
            inputState =  m.group(4);
            inputPostcode = m.group(5);
        }
		
        
        String fullInput = inputStreetNumber + ',' + inputStreetName + ',' +
                inputSuburbName + ',' + inputState + ',' + inputPostcode;
		
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
                    //System.out.println("In while");
                    if (rs.getString("street_number") == null || rs.getString("street_number").isEmpty()) {
                        //System.out.println("Null figure found");
                        continue;
                    } else {
                        //System.out.println("Failed case");
                    }
                    String street_name = new String();
                    street_name = street_name.concat(rs.getString("street_name1"));
                    street_name = street_name.concat(" ");
                    street_name = street_name.concat(rs.getString("street_name2"));
                    //System.out.println(counter.toString() + " " + street_name);
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
                Iterator<String> iterator = resultSet.iterator();
                while (iterator.hasNext()) {
                    m1 += iterator.next() + "\n";
                }
                
                res.message = m1;

                return res;
            }
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.out.println("In Catch block....");
        }

        
        
        
        
        
        
        
		return res;
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
}


