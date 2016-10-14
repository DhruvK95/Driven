package restClient;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import restClient.json.*;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;



public class restJavaOperation {

	static final String REST_URI = "http://localhost:8080/DrivenRest/driven/";
	
	public static void getRegistrations(String id){
		WebClient drivenClient = WebClient.create(REST_URI);
		drivenClient.path("/registrations").accept(MediaType.APPLICATION_JSON);
		
		if(id==null){
			drivenClient.header("authorization", "RMSofficer");
		}else{
			drivenClient.query("rid", "1");
			drivenClient.header("authorization", "driver");

		}
		drivenClient.header("Content-Type", "application/json");
		Response s = drivenClient.get();
	    String result = s.readEntity(String.class);
	    result = result.substring(1, result.length()-1);

		System.out.println("Get all books --");
	    System.out.println(s.toString());
	    System.out.println(s.readEntity(String.class));
	    System.out.println(result.substring(1, result.length()-1));
	    
	    JSONArray jA = new JSONArray(s.readEntity(String.class));
	    
	    System.out.print(jA.length());
	    for(int i=0;i<jA.length(); i++){
            JSONObject jsonTree = jA.getJSONObject(i);
            System.out.println("vallidTill==>" +  jsonTree.getInt("validTill"));
            System.out.println("registrationNumber==>" +jsonTree.getString("registrationNumber"));
            System.out.println("rID==>" +jsonTree.getInt("rID"));
            JSONObject jsonTree1 = (JSONObject) jsonTree.get("driver");
            for(String s1: jsonTree1.getNames(jsonTree1)){          
            	System.out.println("    " + s1 + "====>" + jsonTree1.get(s1));
            }

	    }
 
	}
	
	public static void main(String[] args) {
		String empty = null;
		getRegistrations(empty);
	}
	
}
