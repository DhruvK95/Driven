package au.edu.unsw.soacourse;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import restClient.json.*;
import restClient.*;

/**
 * Servlet implementation class workflowController
 */
@WebServlet("/home")
public class workflowController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public workflowController() {
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

		String code = request.getParameter("code"); // Check the params for driver email code
		restJavaOperation restClient = new restJavaOperation(); // Init.

		if (action == null) {

			if (code != null) { // The driver just arrived from the email
				System.out.println("Code found in GET request " + code);
				// Check the code
				Integer codeNid = restClient.getCheckEmailCode(code);
				if (codeNid != null) {
					// Correct code was found
					System.out.println("Correct code found");

					// Use 'http://localhost:8080/DrivenRest/driven/notices/?nid=' to get RID
					RenewalNotice renewalNotice = restClient.getRenewalNoticeDriver(codeNid);
					// Use RID with 'http://localhost:8080/DrivenRest/driven/registrations/?rid=' to get Driver
					Registration registration = restClient.getRegistrationDriver(renewalNotice.getRid());
					// Set params for JSP to access
					request.setAttribute("fname", registration.getDriver().getFirstName());
					request.setAttribute("lname", registration.getDriver().getLastName());
					request.setAttribute("RegistrationNumber", registration.getRegistrationNumber());
					System.out.println(registration.getDriver().getFirstName());

					nextPage = "driverHome.jsp";
				} else {
					// Correct code not found
					System.out.println("Correct code NOT found");
					nextPage = "Unauthorized.jsp";
				}
			} else {
				System.out.println("No Code found in GET request");
				// Return error...
			}
		} else {

			if (action.equals("cancel")) {
				System.out.println("cancel requested by the driver...");
				nextPage = "Unauthorized.jsp"; //TODO: Change to new JSP
			} else if (action.equals("process")) {
				System.out.println("process requested by the driver...");
				nextPage = "Unauthorized.jsp"; //TODO: Change to new JSP
			}
		}

		RequestDispatcher rd = request.getRequestDispatcher("/"+nextPage);
		rd.forward(request, response);

	}
}
