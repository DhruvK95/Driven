package au.edu.unsw.soacourse.assign2.exactmatch;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jws.WebService;


@WebService(endpointInterface = "au.edu.unsw.soacourse.assign2.exactmatch.ExactMatchService")
public class ExactMatchServiceImpl implements ExactMatchService{
	ObjectFactory factory = new ObjectFactory();

	@Override
	public ValidateExactMatchResponse validateExactMatch(
			ValidateExactMatchRequest parameters) throws NullEntryFaultMsg{
		
		ValidateExactMatchResponse res  = factory.createValidateExactMatchResponse();		
		
		System.out.println("DriversLicenseServiceImpl ==" +parameters.address);

		res.message=parameters.address;
		res.messageStatus="no"; 			
		int count = 0;
		for(int i =0; i < parameters.address.length(); i++)
		    if(parameters.address.charAt(i) == ',')
		        count++;
		
        String pattern = "(.*)?,(.*)?,(.*)?,(.*)?,(.*)?";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(parameters.address);
        int groupCount = m.groupCount();
        System.out.println("Number of group = " + groupCount);

        if (m.find()) {
            System.out.println("Found street number: " + m.group(1) );
            System.out.println("Found street name: " + m.group(2) );
            System.out.println("Found suburb name: " + m.group(3) );
            System.out.println("Found state: " + m.group(4) );
            System.out.println("Found postcode: " + m.group(5) );

        }
        if (count>4){
			res.message="Exessive Entry Fault";
			res.messageStatus="No values entered for address"
					+ "\n  Feild Entry Structure: StrNumber,StrName,SubName,StateName,"; 
			return res;
		}else if(count <4){
			res.message="Insufficient Entry Fault";
			res.messageStatus="No values entered for address"
					+ "\n  Feild Entry Structure: StrNumber,StrName,SubName,StateName,"; 
			return res;
		}else if(parameters.address.equals("")){
			res.message="Null Entry Fault";
			res.messageStatus="No values entered for address"
					+ "\n  Feild Entry Structure: StrNumber,StrName,SubName,StateName,"; 
			return res;
		
		}else if(!(m.group(1).matches("[-+]?\\d*\\.?\\d+") && m.group(5).matches("[-+]?\\d*\\.?\\d+"))) {
				res.message="Number Entry Fault";
				res.messageStatus="Street Number OR Postcode is not a number";
				return res;
		}else if(!((m.group(2).matches("[a-zA-Z ]+")) && (m.group(3).matches("[a-zA-Z ]+")) & (m.group(4).matches("[a-zA-Z ]+")))){
				res.message="Text Entry Fault";
				res.messageStatus="Street Name OR Suburb Name OR State Name is not text ";
				return res;
		}

		// Connect to DB to check for exact match
        Connection c = null;
        Statement stmt = null;
        ResultSet rs;
        Integer counter = 0;

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
                       //System.out.println("Null figure found");
                       continue;
                   }

                   String street_name = new String();
                   street_name = street_name.concat(rs.getString("street_name1"));
                   street_name = street_name.concat(" ");
                   street_name = street_name.concat(rs.getString("street_name2"));
                   //System.out.println(counter.toString() + " " + street_name);
                   counter++;
                   //street number, street name, suburb name, state, postcode
                   if (rs.getString("street_number").equalsIgnoreCase(m.group(1).trim()) &&
                           street_name.equalsIgnoreCase(m.group(2).trim()) &&
                           rs.getString("suburb_name").equalsIgnoreCase(m.group(3).trim()) &&
                           rs.getString("state").equalsIgnoreCase(m.group(4).trim()) &&
                           rs.getString("postcode").equalsIgnoreCase(m.group(5).trim())
                           ) {
                       System.out.println("Found Exact match!!!!!!!!!!!!!!");
                       	res.message="Exact Address Found";
           				res.messageStatus="Output Closed";
                       return res;
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
		
		
		
		
       res.message=parameters.address;
       res.messageStatus="no"; 
		
		
       return res;		
	}

}
