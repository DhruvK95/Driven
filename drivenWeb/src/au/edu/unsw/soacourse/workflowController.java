package au.edu.unsw.soacourse;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import restClient.json.*;
import restClient.*;
import restClient.Registration;

import java.io.*;
import java.util.List;
import javax.servlet.*;
import javax.servlet.http.*;


/**
 * Servlet implementation class workflowController
 */
@WebServlet(urlPatterns="/home", displayName="home")
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

		Enumeration params = request.getParameterNames();
		while(params.hasMoreElements()){
			String paramName = (String)params.nextElement();
			System.out.println("Parameter Name - "+paramName+", Value - "+request.getParameter(paramName));
		}


		String nextPage = "";
		String action = request.getParameter("action");
		System.out.println("Action was: " + action);

		String code = request.getParameter("code"); // Check the params for driver email code
		restJavaOperation restClient = new restJavaOperation(); // Init.
		//restClient.postPayments("1", "200");

		Integer nid = null;
		Integer rid = null;
		Integer pid = null;
		Integer lodged = null;
		Cookie[] cookies = request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			String name = cookies[i].getName();
			String value = cookies[i].getValue();
			if (cookies[i].getName().equals("nid")) {
				nid = Integer.parseInt(cookies[i].getValue());
			}
			if (cookies[i].getName().equals("rid")) {
				rid = Integer.parseInt(cookies[i].getValue());
			}
			if (cookies[i].getName().equals("lodged")) {
				lodged = Integer.parseInt(cookies[i].getValue());
			}
			if (cookies[i].getName().equals("pid")) {
				pid = Integer.parseInt(cookies[i].getValue());
			}
		}

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
					request.setAttribute("registration", registration);
					request.setAttribute("notice", renewalNotice);

					if (renewalNotice.getStatus().equals("accepted")) {
						List<Payment> paymentList = restClient.getPaymentsOfficer();
						for (Payment p : paymentList) {
							if (p.getNid().equals(nid)) {
								request.setAttribute("payment", p);
								Integer pid1 = p.getPid();
								Cookie pay1 = new Cookie("pid", pid1.toString());
								pay1.setMaxAge(60 * 60 * 24);
								response.addCookie(pay1);
							}
						}
					}


					Cookie rid1 = new Cookie("rid", registration.getrID().toString());
					Cookie nid1 = new Cookie("nid", renewalNotice.getNid().toString());
					// Set expiry date after 24 Hrs for both the cookies.
					rid1.setMaxAge(60*60*24);
					nid1.setMaxAge(60*60*24);
					// Add to response
					response.addCookie(rid1);
					response.addCookie(nid1);

					nextPage = "driverHome.jsp";
				} else {
					// Correct code not found
					System.out.println("Correct code NOT found");
					nextPage = "Unauthorized.jsp";
				}
			} else if (nid != null) {
				RenewalNotice renewalNotice = restClient.getRenewalNoticeDriver(nid);
				Registration registration = restClient.getRegistrationDriver(renewalNotice.getRid());
				request.setAttribute("registration", registration);
				request.setAttribute("notice", renewalNotice);

				if (renewalNotice.getStatus().equals("accepted")) {
					List<Payment> paymentList = restClient.getPaymentsOfficer();
					for (Payment p : paymentList) {
						if (p.getNid().equals(nid)) {
							request.setAttribute("payment", p);
							Integer pid1 = p.getPid();
							Cookie pay1 = new Cookie("pid", pid1.toString());
							pay1.setMaxAge(60 * 60 * 24);
							response.addCookie(pay1);
						}
					}
				}


				// request.setAttribute("");
				nextPage = "driverHome.jsp";

			} else {
				System.out.println("No Code found in GET request");
				// Return error...
				nextPage = "ERROR.jsp";
			}
		} else {

			if (action.equals("cancel")) {
				System.out.println("cancel requested by the driver...");

				System.out.println("Nid found " + nid);
				if (nid != null) {
					Integer respCode = restClient.deleteNotice(nid.toString());
					if (respCode.equals(200)) {
						nextPage = "Cancelled.jsp";
					} else {
						nextPage = "ERROR.jsp";
					}
				} else {
					nextPage = "ERROR.jsp";
				}
			} else if (action.equals("update")) {
				System.out.println("Update requested by the driver...");
				// Get the address string that was passed in
				String newAddress = request.getParameter("address");

				Registration r = restClient.getRegistrationDriver(rid);
				if (rid != null) {
					Integer respCode = restClient.putRegistration(rid.toString(), r.getDriver().getEmail(), newAddress);
					if (respCode.equals(200)) {
						System.out.println("Updated!");
						Driver d = new Driver(r.getDriver().getLastName(), r.getDriver().getFirstName(), r.getDriver
								().getLicenseNumber(), newAddress, r.getDriver().getEmail());
						r.setDriver(d);

						RenewalNotice renewalNotice = restClient.getRenewalNoticeDriver(nid);
						request.setAttribute("registration", r);
						request.setAttribute("notice", renewalNotice);

						if (renewalNotice.getStatus().equals("accepted")) {
							List<Payment> paymentList = restClient.getPaymentsOfficer();
							for (Payment p : paymentList) {
								if (p.getNid().equals(nid)) {
									request.setAttribute("payment", p);
									Integer pid1 = p.getPid();
									Cookie pay1 = new Cookie("pid", pid1.toString());
									pay1.setMaxAge(60 * 60 * 24);
									response.addCookie(pay1);
								}
							}
						}

						nextPage = "driverHome.jsp";
					} else {
						nextPage = "ERROR.jsp";
					}
				}
			} else if (action.equals("process")) {
				Registration r = restClient.getRegistrationDriver(rid);
				if (nid != null) {
					Integer respCode = restClient.putNoticesDriver(nid.toString(), "requested");
					if (respCode.equals(200)) {
						System.out.println("Requested set");
						RenewalNotice notice = new RenewalNotice(nid, rid, "requested");

						Integer lodg = 1;
						Cookie lodged1 = new Cookie("lodged", lodg.toString());
						lodged1.setMaxAge(60*60*24);
						response.addCookie(lodged1);

						request.setAttribute("registration", r);
						request.setAttribute("notice", notice);

						if (notice.getStatus().equals("accepted")) {
							List<Payment> paymentList = restClient.getPaymentsOfficer();
							for (Payment p : paymentList) {
								if (p.getNid().equals(nid)) {
									request.setAttribute("payment", p);
									Integer pid1 = p.getPid();
									Cookie pay1 = new Cookie("pid", pid1.toString());
									pay1.setMaxAge(60 * 60 * 24);
									response.addCookie(pay1);
								}
							}
						}

						nextPage = "driverHome.jsp";
					} else {
						nextPage = "ERROR.jsp";
					}
				}
			} else if (action.equals("pay")) {
				if (pid != null) {
					String ccn = request.getParameter("ccn");
					String ccName = request.getParameter("cca");
					String ccv = request.getParameter("ccv");
					System.out.println("Putting payment....");
					restClient.putPayments(pid.toString(), ccn, ccName, ccv);
					Registration r = restClient.getRegistrationDriver(rid);
					Calendar cal = Calendar.getInstance();
					cal.setTime(r.getValidTill());
					cal.add(Calendar.YEAR, 1);
					Date vdate = cal.getTime();
					request.setAttribute("vdate", vdate);
					nextPage = "paid.jsp";
				} else {
					System.out.println("pid was null");
					nextPage = "ERROR.jsp";
				}
				// GET PARAMS
			}
		}

		RequestDispatcher rd = request.getRequestDispatcher("/"+nextPage);
		rd.forward(request, response);

	}
}
