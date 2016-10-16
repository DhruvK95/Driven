package restClient;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;

import restClient.json.*;

import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;



public class restJavaOperation {

    static final String REST_URI = "http://localhost:8080/DrivenRest/driven/";
    
    
    //get registrations, id is inptutted as null if officer
    public static void getRegistrations(String id){
        WebClient drivenClient = WebClient.create(REST_URI);
        drivenClient.path("/registrations").accept(MediaType.APPLICATION_JSON);
        int flag = 0 ;
        if(id==null){
            drivenClient.header("authorization", "RMSofficer");
        }else{
            drivenClient.header("authorization", "driver");
            drivenClient.query("rid", id);//("rid=1");
            flag=1;
        }
        drivenClient.header("Content-Type", "application/json");
        Response s = drivenClient.get();
        String result = s.readEntity(String.class);
        
        
        if(flag==1){
        	JSONObject jObj = new JSONObject(result);
        	
            JSONObject jDriverObj = (JSONObject) jObj.get("driver");
            for(String s1: jDriverObj.getNames(jDriverObj)){          
                System.out.println("    " + s1 + "====>" + jDriverObj.get(s1));
            }

            System.out.println("vallidTill==>" +  jObj.getLong("validTill"));
            System.out.println("registrationNumber==>" +jObj.getString("registrationNumber"));
            System.out.println("rID==>" +jObj.getInt("rID"));

        }else{
	        JSONArray jArrObj = new JSONArray(result);        
	        for(int i=0;i<jArrObj.length(); i++){
	            JSONObject jsonTree = jArrObj.getJSONObject(i);
	            
	            JSONObject jDriverObj = (JSONObject) jsonTree.get("driver");
	            for(String s1: jDriverObj.getNames(jDriverObj)){          
	                System.out.println("    " + s1 + "====>" + jDriverObj.get(s1));
	            }
	
	            System.out.println("vallidTill==>" +  jsonTree.getLong("validTill"));
	            System.out.println("registrationNumber==>" +jsonTree.getString("registrationNumber"));
	            System.out.println("rID==>" +jsonTree.getInt("rID"));
	            
	        }
        }
 
    }
    
    public static void getPayments(String id){
    	WebClient drivenClient = WebClient.create(REST_URI);
    	drivenClient.path("/payments").accept(MediaType.APPLICATION_JSON);
    	int flag = 0 ;
        if(id==null){
            drivenClient.header("authorization", "RMSofficer");
        }else{
            drivenClient.header("authorization", "driver");
            drivenClient.query("pid", id);
            flag=1;
        }
        drivenClient.header("Content-Type", "application/json");
        Response s = drivenClient.get();
        String result = s.readEntity(String.class);
        System.out.println(result);
    	
        if(flag==1){
        	JSONObject jObj = new JSONObject(result);

            System.out.println("pid==>" +  jObj.getInt("pid"));
            System.out.println("credit_card_number==>" +jObj.getInt("credit_card_number"));
            System.out.println("credit_card_name==>" +jObj.getString("credit_card_name"));
            System.out.println("credit_card_ccv==>" +jObj.getInt("credit_card_ccv"));
            System.out.println("paid_date==>" +jObj.getLong("paid_date"));
            System.out.println("nid==>" +jObj.getInt("nid"));
            System.out.println("amount==>" +jObj.getInt("amount"));

        }else{
	        JSONArray jArrObj = new JSONArray(result);        
	        for(int i=0;i<jArrObj.length(); i++){
	            JSONObject jObj = jArrObj.getJSONObject(i);

	            System.out.println("pid==>" +  jObj.getInt("pid"));
	            System.out.println("credit_card_number==>" +jObj.getInt("credit_card_number"));
	            System.out.println("credit_card_name==>" +jObj.getString("credit_card_name"));
	            System.out.println("credit_card_ccv==>" +jObj.getInt("credit_card_ccv"));
	            System.out.println("paid_date==>" +jObj.getLong("paid_date"));
	            System.out.println("nid==>" +jObj.getInt("nid"));
	            System.out.println("amount==>" +jObj.getInt("amount"));
	            
	        }
        }
 
    	
    	
    }
    
//    public static void putRegistrations(String form_rid, String form_email, String form_address){
//        WebClient drivenClient = WebClient.create(REST_URI);
//        
//        drivenClient.path("/registrations").accept(MediaType.APPLICATION_JSON);
//        
//        drivenClient.header("authorization", "RMSofficer");
//        drivenClient.header("Content-Type", "application/json");
//        
////      Form form = new Form();
////      form.param("email", form_email);
////      form.param("address", form_address);
////      form.param("rid", form_rid);
////      
//        //drivenClient.form(form);
//        
//        Response s = drivenClient.put(drivenClient);
//        
//        String result = s.readEntity(String.class);
////      result = result.substring(1, result.length()-1);
////
//        System.out.println(result.substring(1, result.length()-1));
//        
//       // JSONArray jA = new JSONArray(result.substring(1, result.length()-1));
//
//
//       
//    }
    
    
    
    public static void main(String[] args) {
        String empty = null;
        
        //get registrations input must be rid in String ie. "1"
        //getRegistrations(null);
        //get payments input must be rid in String ie. "1"
        //getPayments(null);
    }
    
}
