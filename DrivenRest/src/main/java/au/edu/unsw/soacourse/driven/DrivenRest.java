package au.edu.unsw.soacourse.driven;

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

    @DELETE
    @Path("/notices")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteNotice(@Context HttpHeaders headers,
                                 @FormParam("nid") Integer form_nid) {
        String auth = headers.getRequestHeaders().getFirst("authorization");
        if (auth == null || form_nid == null) return Response.status(Response.Status.BAD_REQUEST).build(); // Required fields

        RMS_Impl rms = new RMS_Impl();
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

        RMS_Impl rms = new RMS_Impl();
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
                        RMS_Impl rms = new RMS_Impl();
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

