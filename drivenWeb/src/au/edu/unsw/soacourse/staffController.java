package au.edu.unsw.soacourse;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bpelSpawner.soapClient;
import restClient.json.*;
import restClient.*;

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
        String nextPage = "";
        String action = request.getParameter("action");
        System.out.println("Action was: " + action);
		restJavaOperation restClient = new restJavaOperation(); // Init.

        if (action == null) {
            nextPage = "officerHome.jsp";
        }else if(action.equals("generate")){
    		
    		ArrayList<RenewalNoticeResponse> rN = restClient.postNotices();		
    		ArrayList<registrationAddressType> rAT = new ArrayList<registrationAddressType>();
    		
			for(RenewalNoticeResponse r : rN){
				registrationAddressType rA = new registrationAddressType();
				
				rA.setRegistration(restClient.getRegistrationDriver(r.getRenewalNotice().getRid()));
				rA.setRenewalNoticeResponse(r);

				rAT.add(rA);
	
			}
        	request.setAttribute("notices", rAT);

            nextPage = "officerNotices.jsp";

        }else if(action.equals("check")){
        	nextPage = "Unauthorized.jsp";
        	Integer rID = Integer.parseInt(request.getParameter("field"));
        	System.out.print(rID);
        	String address = restClient.getRegistrationDriver(rID).getDriver().getAddress();
        	System.out.print("SSS" + restClient.getRegistrationDriver(rID).getDriver().getAddress());
        	
        	soapClient sP = new soapClient();
        	
        	sP.doSoap(address);
        	
        	System.out.print("SSSSSSSSSSSS"  + sP.Exact);
        	if(sP.Exact){
            	request.setAttribute("exact", true);

        	}else{        		
            	request.setAttribute("exact", false);

        	}
        	
    		nextPage = "officerConfirmAddress.jsp";

            
        }

        RequestDispatcher rd = request.getRequestDispatcher("/"+nextPage);
        rd.forward(request, response);

    }
}
