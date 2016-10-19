package au.edu.unsw.soacourse;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        }else{
    		
    		ArrayList<RenewalNoticeResponse> rN = restClient.postNotices();		
    		ArrayList<registrationAddressType> rAT = new ArrayList<registrationAddressType>();
    		
			for(RenewalNoticeResponse r : rN){
				registrationAddressType rA = new registrationAddressType();
				System.out.println("DDDDD" + restClient.getRegistrationDriver(r.getRenewalNotice().getRid()).getDriver().getAddress());
				System.out.println("DDDDDDD" + r.getLink());
	
				rA.setRegistration(restClient.getRegistrationDriver(r.getRenewalNotice().getRid()));
				rA.setRenewalNoticeResponse(r);
	        	request.setAttribute("notices", rAT);

				rAT.add(rA);
	
			}
            nextPage = "officer.jsp";

        }

        RequestDispatcher rd = request.getRequestDispatcher("/"+nextPage);
        rd.forward(request, response);

    }
}
