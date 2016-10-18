package au.edu.unsw.soacourse;

import java.io.IOException;
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
		// String action = request.getParameter("action");
		// System.out.println("Action was: " + action);

		String code = request.getParameter("code"); // Check the params for driver email code
		restJavaOperation restClient = new restJavaOperation(); // Init.


		if (code != null) { // The driver just arrived from the email
			System.out.println("Code found in GET request " + code);
			// Check the code
			System.out.println(restClient.getCheckEmailCode(code));
			Integer codeNid = restClient.getCheckEmailCode(code);
			if (codeNid != null) {
				// Correct code was found
				System.out.println("Correct code found");
				nextPage = "driverHome.jsp";
			} else {
				// Correct code not found
				System.out.println("Correct code NOT found");
				nextPage = "Unauthorized.jsp";
			}
		} else {
			System.out.println("No Code found in GET request");
			// Normal workflow ...
		}

		RequestDispatcher rd = request.getRequestDispatcher("/"+nextPage);
		rd.forward(request, response);

	}
}
