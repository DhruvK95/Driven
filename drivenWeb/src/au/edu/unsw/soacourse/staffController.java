package au.edu.unsw.soacourse;

import java.io.IOException;
import java.util.ArrayList;
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bpelSpawner.soapClient;
import restClient.json.*;
import restClient.*;
import restClient.Registration;

/**
 * Servlet implementation class workflowController
 */
@WebServlet("/officerhome")
public class staffController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public staffController() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("GET");
        processRequest(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("POST");
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	String nid = null;
        String nextPage = "";
        String action = request.getParameter("action");
        System.out.println("Action was: " + action);
		restJavaOperation restClient = new restJavaOperation(); // Init.
		
		Cookie[] cookies = request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			String name = cookies[i].getName();
			String value = cookies[i].getValue();
			if (cookies[i].getName().equals("nidC")) {
				nid = cookies[i].getValue();
			}
		}
		
        if (action == null) {
            nextPage = "officerHome.jsp";
        }else if(action.equals("generateNotices")){
        	restClient.postNotices();	
        }else if(action.equals("getPending")){
        	
    		System.out.print("generate");
    		ArrayList<RenewalNotice> rN1 = new ArrayList<RenewalNotice>();
    		
    		ArrayList<RenewalNotice> rN = restClient.getRenewalNoticeOfficer();		
			for(RenewalNotice r : rN){

				if(r.getStatus().equals("requested")){
					rN1.add(r);
				}
	
			}
			
        	request.setAttribute("notices", rN1);

            nextPage = "officerNotices.jsp";

        }else if(action.equals("check")){
        	nextPage = "Unauthorized.jsp";
        	Integer rID = Integer.parseInt(request.getParameter("field"));
        	System.out.print(rID);
        	String address = restClient.getRegistrationDriver(rID).getDriver().getAddress();
        	
        	soapClient sP = new soapClient();
        	
        	sP.doSoap(address);
        	
        	System.out.print("SSSSSSSSSSSS"  + sP.Exact);
        	if(sP.Exact){
            	request.setAttribute("exact", true);

        	}else{        		
            	request.setAttribute("exact", false);

        	}
        	//System.out.println("Dffwrfregegetgerwgegewgewth"+request.getParameter("nid"));
        	request.setAttribute("nid", request.getParameter("nid"));
        	
			Cookie nidC = new Cookie("nidC", request.getParameter("nid"));
			nidC.setMaxAge(60*60*24);
			response.addCookie(nidC);
    		
            nextPage = "officerConfirmAddress.jsp";

            
        }else if(action.equals("createPayment")){
        	
//        	System.out.print(">>>>>>>>>>>CREATE WITH:" + request.getParameter("amount") + " NID:" + request.getParameter("nid") +" <<<<<<<<<<<<<<<<<<");

        	restClient.postPayments(nid, request.getParameter("amount").toString());
            
        	nextPage = "officerHome.jsp";

        	
        }else if(action.equals("rejectPayment")){
        	
        	System.out.println(request.getParameter("rejection"));
        	restClient.putNoticesOfficer(nid, "rejected-"+request.getParameter("rejection"));
        }else if(action.equals("getRegistrationsDetails")){
        	System.out.print("hhjkgg");
        	System.out.print(request.getParameter("rid") + "  " +Integer.parseInt(request.getParameter("rid")) );
        	Registration r = restClient.getRegistrationDriver(Integer.parseInt(request.getParameter("rid")));
        	request.setAttribute("registration", r);
        	
        	
        	nextPage = "registrationDetails.jsp";

        	

        }

        RequestDispatcher rd = request.getRequestDispatcher("/"+nextPage);
        rd.forward(request, response);

    }
}
