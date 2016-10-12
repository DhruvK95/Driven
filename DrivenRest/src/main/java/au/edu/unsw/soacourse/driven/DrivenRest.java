package au.edu.unsw.soacourse.driven;

import org.sqlite.core.DB;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.xml.parsers.ParserConfigurationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;


@Path("/driven")
public class DrivenRest {
    private final static String OFFICER_KEY = "RMSofficer";
    private final static String DRIVER_KEY = "driver";
    RMS_Impl rms = new RMS_Impl();

    @GET
    @Path("/payments/")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getPayment(@Context HttpHeaders headers,
                               @QueryParam("pid") Integer query_pid) {
        System.out.println(headers.toString());
        String auth = headers.getRequestHeaders().getFirst("authorization");
        if (auth == null) return Response.status(Response.Status.BAD_REQUEST).build(); // Required fields

        if (query_pid == null) {
            if (auth.equals(OFFICER_KEY)) {
                // An OFFICER can pass no query param to retrieve all payments
                DB_Handler db_handler = new DB_Handler();
                return Response.ok().entity(db_handler.getPaymentsList()).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        }

        if (auth.equals(OFFICER_KEY) || auth.equals(DRIVER_KEY)) {
            // Check if pid exitsts
            if (rms.paymentExists(query_pid)) {
                // Get the payment and return
                Payment payment = rms.getPayment(query_pid);
                return Response.ok().entity(payment).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @DELETE
    @Path("/notices")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteNotice(@Context HttpHeaders headers,
                                 @FormParam("nid") Integer form_nid) {
        String auth = headers.getRequestHeaders().getFirst("authorization");
        if (auth == null || form_nid == null) return Response.status(Response.Status.BAD_REQUEST).build(); // Required fields

        if (rms.noticeExists(form_nid)) {
            RenewalNotice renewalNotice = rms.getNotice(form_nid);
            if (auth.equals(DRIVER_KEY)) {
                // Driver can set to archived if it is already rejected, or cancelled
                if (renewalNotice.getStatus().equals("rejected") || renewalNotice.getStatus().equals("cancelled")) {
                    rms.updateNotice(renewalNotice.getNid(), "archived");
                    renewalNotice.setStatus("archived");
                } else {
                    // Driver can set to cancelled at any time?
                    rms.updateNotice(renewalNotice.getNid(), "cancelled");
                    renewalNotice.setStatus("cancelled");
                }
            } else {
                // Archiving (i.e., deleting) renewal requests is only done by the drivers when the outcome of the
                // processing is 'Rejected', or the request itself is cancelled by the driver
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
            return Response.ok().entity(renewalNotice).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No notice with given NID was found").build();
        }
    }



    @POST
    @Path("/notices/newNotices")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response generateNotices(@Context HttpHeaders headers) {
        System.out.println(headers.toString());
        String auth = headers.getRequestHeaders().getFirst("authorization");
        if (auth == null) return Response.status(Response.Status.BAD_REQUEST).build(); // Required fields
        if (!auth.equals(OFFICER_KEY)) return Response.status(Response.Status.UNAUTHORIZED).build(); // Auth

        List<RenewalNotice> generatedNotices = rms.generateNotices();
        ResponseBuilder builder = Response.serverError();

        List<RenewalNoticeResponse> renewalNoticeResponses = new ArrayList<>();
        if (generatedNotices.size() > 0) {
            // Take response list from function and encapsulate into RenewalNoticeResponse Objects (with links)
            for (RenewalNotice aRN : generatedNotices) {
                RenewalNoticeResponse renewalNoticeResponse = new RenewalNoticeResponse(aRN,
                        "http://localhost:8080/DrivenRest/driven/notices?nid=" + aRN.getNid().toString());
                renewalNoticeResponses.add(renewalNoticeResponse);
                System.out.println(renewalNoticeResponses);
            }
            // Add all to the response
            builder = Response.ok().entity(renewalNoticeResponses);
        } else {
            // ERROR
            builder = Response.status(Response.Status.NOT_MODIFIED);
        }

        return builder.build();
    }

    @GET
    @Path("/notices")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getRenewalNotice (@QueryParam("nid") Integer nid,
                                      @QueryParam("status") String status,
                                      @Context HttpHeaders headers)
            throws ParserConfigurationException {
        System.out.println(headers.toString());
        String auth = headers.getRequestHeaders().getFirst("authorization");

        // Check required fields
        if (nid == null) {
            if (auth.equals(OFFICER_KEY)) { // Return all notices to an officer

                // Get all RenewalNotices from the DB
                DB_Handler db = new DB_Handler();
                List<RenewalNotice> rnl = new ArrayList<>();
                RenewalNotice respRenewalNotice = null;
                rnl = db.getRenewalNoticesList();
                ResponseBuilder builder = null;

                if (status != null) { // If a particular status is queried
                    for (RenewalNotice aRnl : rnl) {
                        if (aRnl.getStatus().equals(status)) {
                            builder = Response.ok().entity(aRnl);
                        }
                    }
                    if (builder != null) {
                        return builder.build();
                    } else {
                        ResponseBuilder builder2 = Response.status(Response.Status.NOT_FOUND).entity("No notices with" +
                                " specified status found");
                        return builder2.build();
                    }
                }

                // If status is not queried, return all notices.
                builder = Response.ok().entity(rnl);
                return builder.build();

            } else if (auth.equals(null) || auth.equals(DRIVER_KEY)) {
                ResponseBuilder builder = Response.status(Response.Status.UNAUTHORIZED).entity("Driver must supply a " +
                        "Notice ID(nid)");
                System.out.println("getRenewalNotice: BAD request");
                return builder.build();
            }
        }

        // Get all RenewalNotices from the DB
        DB_Handler db = new DB_Handler();
        // db.createTables();
        List<RenewalNotice> rnl = new ArrayList<>();
        RenewalNotice respRenewalNotice = null;
        rnl = db.getRenewalNoticesList();

        // Assume content is not found, change response IF a notice with 'nid' is found
        ResponseBuilder builder = Response.status(Response.Status.NOT_FOUND);

        // Loop through all notices, looking for one matching the Query'nid'
        for (RenewalNotice aRnl : rnl) {
            if (aRnl.getNid().equals(nid)) {
                respRenewalNotice = aRnl;
                builder = Response.ok().entity(respRenewalNotice);
            }
        }
        return builder.build();
    }

    @PUT
    @Path("/notices")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateRenewalNotice(@FormParam("nid") Integer form_nid,
                                        @FormParam("status") String form_status,
                                        @Context HttpHeaders headers) {
        ResponseBuilder builder = null;

        System.out.println(headers.toString());
        String auth = headers.getRequestHeaders().getFirst("authorization");

        System.out.println("form_nid: " + form_nid.toString());
        System.out.println("form_status" + form_status);

        if (!auth.equals(OFFICER_KEY) && !auth.equals(DRIVER_KEY)) {
            builder = Response.status(Response.Status.UNAUTHORIZED);
        } else {

            DB_Handler db = new DB_Handler();
            List<RenewalNotice> renewalNoticesList = db.getRenewalNoticesList();
            Boolean found = Boolean.FALSE;
            for (int i = 0; i < renewalNoticesList.size(); i++) {
                if (renewalNoticesList.get(i).getNid().equals(form_nid)) {
                    RenewalNotice currNotice = renewalNoticesList.get(i);
                    found = Boolean.TRUE;
                    if (auth.equals(DRIVER_KEY) && currNotice.getStatus().equals("under_review")) {
                        // Not allowed from DRIVER once the status of a renewal notice has moved to 'Under-Review'
                        builder = Response.status(Response.Status.UNAUTHORIZED).entity("Drivers cannot update " +
                                "under-review notices");
                    } else {
                        // If found send to RMS to update.
                        RenewalNotice updatedRN = rms.updateRenewalNotice(currNotice, auth, form_status);
                        builder = Response.ok().entity(updatedRN);
                    }
                }
            }
            if (!found) {
                builder = Response.status(Response.Status.NOT_FOUND).entity("Could not find a notice with the " +
                        "supplied Notice ID(nid)");
            }
        }


        if (builder == null) {
            builder = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
            return builder.build();
        } else {
            return builder.build();
        }
    }



    @GET
    @Path("/registrations")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getRegistrations (@QueryParam("rid") Integer rid,
                                      @Context HttpHeaders headers) {
        System.out.println(headers.toString());
        String auth = headers.getRequestHeaders().getFirst("authorization");

        // Check required fields
        if (rid == null) {
            if (auth.equals(OFFICER_KEY)) { 
                XML_Handler xml = new XML_Handler();
                List<Registration> rrl = new ArrayList<>();
                //grab registrations from xml
                rrl = xml.makeRegistrationList();
                ResponseBuilder builder = null;
                //for all registrations
                for (Registration aRrl : rrl) {
                	builder = Response.ok().entity(aRrl);
                }
                //make builder object as list object
                builder = Response.ok().entity(rrl);
                
                if (builder != null) {
                	return builder.build();
                } else {
                	ResponseBuilder builder2 = Response.status(Response.Status.NOT_FOUND).entity("No registrations");
                	return builder2.build();
                }
            } else if (auth.equals(null) || auth.equals(DRIVER_KEY)) {
                ResponseBuilder builder = Response.status(Response.Status.UNAUTHORIZED).entity(" Supply an Rid ");
                System.out.println("getRegistrations: BAD request");
                return builder.build();
            }
        }

        XML_Handler xml = new XML_Handler();
        List<Registration> rrl = new ArrayList<>();
        Registration respRegistration = null;
        rrl = xml.makeRegistrationList();

        // Assume content is not found, change response IF a notice with 'nid' is found
        ResponseBuilder builder = Response.status(Response.Status.NOT_FOUND);

        for (Registration aRrl : rrl) {
            if (aRrl.getrID().equals(rid)) {
            	respRegistration = aRrl;
                builder = Response.ok().entity(respRegistration);
            }
        }
        return builder.build();
    }
    
    
    @PUT
    @Path("/registrations")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateRegistrations(@FormParam("rid") Integer form_rid,
    									@FormParam("email") String form_email,
    									@FormParam("address") String form_address,
//    									@FormParam("RegistrationValidTill") String form_RegistrationValidTill,
                                        @Context HttpHeaders headers) {
        ResponseBuilder builder = null;

        System.out.println(headers.toString());
        String auth = headers.getRequestHeaders().getFirst("authorization");

        System.out.println("form_rid: " + form_rid.toString());

        if (!auth.equals(OFFICER_KEY) && !auth.equals(DRIVER_KEY)) {
            builder = Response.status(Response.Status.UNAUTHORIZED);
        } else {

            XML_Handler xml = new XML_Handler();
            List<Registration> registrationList = xml.makeRegistrationList();
            Boolean found = Boolean.FALSE;
            for (int i = 0; i < registrationList.size(); i++) {
                if (registrationList.get(i).getrID().equals(form_rid)) {
                    Registration currNotice = registrationList.get(i);
                    found = Boolean.TRUE;
                    if (currNotice.getrID().equals(form_rid)){
                    	xml.updateEmail(form_rid, form_email);
                    	xml.updateAddress(form_rid, form_address);
                        registrationList = xml.makeRegistrationList();
	            		for (int i1 = 0; i1 < registrationList.size(); i1++) {
	            			if (registrationList.get(i1).getrID().equals(form_rid)) {
	            				 currNotice = registrationList.get(i1);

	            			}
	            		}

                        builder = Response.ok().entity(currNotice);
                    }
                }
            }
            if (!found) {
                builder = Response.status(Response.Status.NOT_FOUND).entity("Could not find a notice with the " +
                        "supplied Notice ID(nid)");
            }
        }


        if (builder == null) {
            builder = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
            return builder.build();
        } else {
            return builder.build();
        }
    }
    
    
    @GET
    @Path("/echo/{input}")
    @Produces("text/plain")
    public String ping(@PathParam("input") String input) {
        return input;
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/jsonBean")
    public Response modifyJson(JsonBean input) {
        input.setVal2(input.getVal1());
        return Response.ok().entity(input).build();
    }
}

