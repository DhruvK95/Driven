package restClient;
import java.util.Date;
import restClient.json.*;
import javax.ws.rs.core.Form;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import bpelSpawner.soapClient;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;


//import bpelSpawner.soapClient;



public class restJavaOperation {

    final String REST_URI = "http://localhost:8080/DrivenRest/driven/";

    public restJavaOperation() {
    }

    //checkEmailCode
    public Integer getCheckEmailCode(String query_code){
//    	code=5a3cc6a0-846e-4d5e-860e-e1caf2ae29d0
    	
        WebClient drivenClient = WebClient.create(REST_URI);
        drivenClient.path("/checkEmailCode/").accept(MediaType.APPLICATION_JSON);
        drivenClient.header("Content-Type", "application/json");

        drivenClient.query("code", query_code);//("rid=1");
        

        Response s = drivenClient.get();
    	
    	if(s.getStatus()==404){
    		return null;
    	}
    	
        String result = s.readEntity(String.class);

    	JSONObject jObj = new JSONObject(result);

    	Integer ret = jObj.getInt("id");

    	
    	return ret;
    	
    }

    public Registration getRegistrationDriver(Integer rid) {
        WebClient drivenClient = WebClient.create(REST_URI);
        drivenClient.path("/registrations").accept(MediaType.APPLICATION_JSON);
        drivenClient.header("authorization", "driver");
        drivenClient.query("rid", rid.toString());//("rid=1");

        drivenClient.header("Content-Type", "application/json");
        Response s = drivenClient.get();

        if (s.getStatus() == 200) {
            String result = s.readEntity(String.class);
            JSONObject jObj = new JSONObject(result);

            JSONObject jDriverObj = (JSONObject) jObj.get("driver");
            String address = jDriverObj.getString("address");
            String lastName = jDriverObj.getString("lastName");
            String firstName = jDriverObj.getString("firstName");
            String licenseNumber = jDriverObj.getString("licenseNumber");
            String email = jDriverObj.getString("email");
            Driver currDriver = new Driver(lastName, firstName, licenseNumber, address, email);

            System.out.println("vallidTill==>" +  jObj.getLong("validTill"));
            System.out.println("registrationNumber==>" +jObj.getString("registrationNumber"));
            System.out.println("rID==>" +jObj.getInt("rID"));
            Date date = new Date();
            date.setTime(jObj.getLong("validTill"));

            Registration renewalNotice = new Registration(jObj.getInt("rID"), jObj.getString("registrationNumber"),
                    date, currDriver);
            return renewalNotice;

        } else {
            return null;
        }

    }

    //get registrations, id is inptutted as null if officer
    public void getRegistrations(String id){
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
    
    public void getPayments(String id){
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

    public RenewalNotice getRenewalNoticeDriver(Integer nid) {
        WebClient drivenClient = WebClient.create(REST_URI);
        drivenClient.path("/notices").accept(MediaType.APPLICATION_JSON);
        drivenClient.header("authorization", "driver");
        drivenClient.query("nid", nid.toString());

        drivenClient.header("Content-Type", "application/json");
        Response s = drivenClient.get();
        if (s.getStatus() == 200) {
            String result = s.readEntity(String.class);
            JSONObject jObj = new JSONObject(result);

            System.out.println("nid==>" +  jObj.getInt("nid"));
            System.out.println("status==>" +jObj.getString("status"));
            System.out.println("rid==>" +jObj.getInt("rid"));
            RenewalNotice renewalNotice = new RenewalNotice(jObj.getInt("nid"), jObj.getInt("rid"), jObj.getString("status"));
            return renewalNotice;
        } else {
            return null;
        }

    }
    
    public void getNotices(String id){
    	
    	WebClient drivenClient = WebClient.create(REST_URI);
    	drivenClient.path("/notices").accept(MediaType.APPLICATION_JSON);
	    	int flag = 0 ;
	        if(id==null){
	            drivenClient.header("authorization", "RMSofficer");
	        }else{
	            drivenClient.header("authorization", "driver");
	            drivenClient.query("nid", id);
	            flag=1;
	        }
	        drivenClient.header("Content-Type", "application/json");
	        Response s = drivenClient.get();
	        String result = s.readEntity(String.class);
	        //System.out.print(s.getStatus());
	        if(flag==1){
	        	JSONObject jObj = new JSONObject(result);
	
	            System.out.println("nid==>" +  jObj.getInt("nid"));
	            System.out.println("status==>" +jObj.getString("status"));            
	            System.out.println("rid==>" +jObj.getInt("rid"));
	            
	        }else{
		        JSONArray jArrObj = new JSONArray(result);        
		        for(int i=0;i<jArrObj.length(); i++){
		            JSONObject jObj = jArrObj.getJSONObject(i);
	
		            System.out.println("nid==>" +  jObj.getInt("nid"));
		            System.out.println("status==>" +jObj.getString("status"));            
		            System.out.println("rid==>" +jObj.getInt("rid"));
		           
		            
		        }
	        }    	
    	
      	
    }
    
    public void postPayments(String nid, String fee){
    	WebClient drivenClient = WebClient.create(REST_URI);
    	
    	drivenClient.path("/payments").accept(MediaType.APPLICATION_JSON);
        drivenClient.header("authorization", "RMSofficer");
        drivenClient.header("Content-Type", "application/x-www-form-urlencoded"); 
    	
        Form form = new Form();
		form.param("fee", fee);
		form.param("nid", nid);
		
        Response s = drivenClient.post(form);
    	JSONObject jObj = new JSONObject(s.readEntity(String.class));
        
        System.out.println(jObj.getString("link"));
    
    }
    
    public void postNotices(){
    	WebClient drivenClient = WebClient.create(REST_URI);
    	
    	drivenClient.path("/notices/newNotices").accept(MediaType.APPLICATION_JSON);
        drivenClient.header("Authorization", "RMSofficer");
        drivenClient.header("Content-Type", "application/x-www-form-urlencoded"); 
        Form form = new Form();

        Response s = drivenClient.post(form);
    	//JSONObject jObj = new JSONObject();
        JSONArray jArrObj = new JSONArray(s.readEntity(String.class));        
        for(int i=0;i<jArrObj.length(); i++){
        JSONObject jObj = jArrObj.getJSONObject(i);

            //JSONObject jObj = new JSONObject(s.readEntity(String.class));
            System.out.println("nid==>" +  jObj.getString("link"));
           
            
        }
       // System.out.println(jObj.getString("link"));

    
    }
    
    public void deletePayments(String pid){
    	WebClient drivenClient = WebClient.create(REST_URI);
    	
    	drivenClient.path("/payments").accept(MediaType.APPLICATION_JSON);
        drivenClient.header("authorization", "RMSofficer");
        drivenClient.header("Content-Type", "application/x-www-form-urlencoded"); 
		drivenClient.query("pid", pid);

        Response s = drivenClient.delete();
        
        System.out.println(s.getStatus());
    
    }
    
    public void deleteNotice(String pid){
    	WebClient drivenClient = WebClient.create(REST_URI);
    	
    	drivenClient.path("/notices").accept(MediaType.APPLICATION_JSON);
        drivenClient.header("authorization", "driver");
        drivenClient.header("Content-Type", "application/x-www-form-urlencoded"); 
		drivenClient.query("nid", pid);

        Response s = drivenClient.delete();
        
        JSONObject jArrObj = new JSONObject(s.readEntity(String.class));        
        
        
        System.out.println(jArrObj.getString("status"));
        System.out.println(s.getStatus());
    
    }
    
    public void putPayments(String pid, String cc_number, String cc_name, String cc_ccv){
    	WebClient drivenClient = WebClient.create(REST_URI);
    	
    	drivenClient.path("/payments").accept(MediaType.APPLICATION_JSON);
        drivenClient.header("authorization", "driver");
        drivenClient.header("Content-Type", "application/x-www-form-urlencoded"); 


        Form form = new Form();
		form.param("pid", pid);
		form.param("cc_number", cc_number);
		form.param("cc_name", cc_name);
		form.param("cc_ccv", cc_ccv);
		
		
        Response s = drivenClient.put(form);
        
               
        JSONObject jArrObj = new JSONObject(s.readEntity(String.class));        
        
        
        System.out.println(s.getStatus());
    
    }
    
    public void putRegistration(String rid, String email, String address){
    	WebClient drivenClient = WebClient.create(REST_URI);
    	
    	drivenClient.path("/registrations").accept(MediaType.APPLICATION_JSON);
        drivenClient.header("authorization", "RMSofficer");
        drivenClient.header("Content-Type", "application/x-www-form-urlencoded"); 


        Form form = new Form();
		form.param("rid", rid);
		form.param("email", email);
		form.param("address", address);
		
		
        Response s = drivenClient.put(form);
        
               
        JSONObject jArrObj = new JSONObject(s.readEntity(String.class));        
        
        
        System.out.println(s.getStatus());
    
    }
    
    public void putNoticesDriver(String nid, String status){
    	WebClient drivenClient = WebClient.create(REST_URI);
    	
    	drivenClient.path("/notices").accept(MediaType.APPLICATION_JSON);
        drivenClient.header("authorization", "driver");
        drivenClient.header("Content-Type", "application/x-www-form-urlencoded"); 


        Form form = new Form();
		form.param("nid", nid);
		form.param("status", status);
		
        Response s = drivenClient.put(form);
        
               
        JSONObject jArrObj = new JSONObject(s.readEntity(String.class));        
        
        
        System.out.println(s.getStatus());
    }

    public void putNoticesOfficer(String nid, String status){
    	WebClient drivenClient = WebClient.create(REST_URI);
    	
    	drivenClient.path("/notices").accept(MediaType.APPLICATION_JSON);
        drivenClient.header("authorization", "RMSofficer");
        drivenClient.header("Content-Type", "application/x-www-form-urlencoded"); 


        Form form = new Form();
		form.param("nid", nid);
		form.param("status", status);
		
        Response s = drivenClient.put(form);
        
               
        JSONObject jArrObj = new JSONObject(s.readEntity(String.class));        
        
        
        System.out.println(s.getStatus());
    }
    
        
    
//    public void main(String[] args) {
//        String empty = null;
//        
//        //get registrations input must be rid in String ie. "1"
//        //getRegistrations(null);
//        //get payments input must be rid in String ie. "1"
//        //getPayments(null);
//        //get notices input must be rid in String ie. "1"
//        //getNotices("2");
//
//        //postPayments("2","2000");
//        //deletePayments("4");
//        //postNotices();
//        //deleteNotice("2");
//        //String pid, String cc_number, String cc_name, String cc_ccv
//        //putPayments("2","333","LEON","111");
//        //id, email, address
//        //putRegistration("1","123","test");
//        //putNoticesDriver("1","under_review");
//        
//        soapClient sP = new soapClient();
//        
//        sP.doSoap("1,DOWLING SqTREET,BEGA,NSW,2550");
//        if(sP.Exact){
//        	System.out.print("RUN ACCEPT ON ADDRESS");
//        }
//    }
    
}
