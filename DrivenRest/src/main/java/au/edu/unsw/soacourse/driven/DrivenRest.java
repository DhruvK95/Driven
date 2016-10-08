package au.edu.unsw.soacourse.driven;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.xml.parsers.ParserConfigurationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;


@Path("/driven")
public class DrivenRest {
    private final static String OFFICER_KEY = "RMSofficer";
    private final static String DRIVER_KEY = "driver";

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
    public Response getRenewalNotice (@QueryParam("nid") Integer nid, @Context HttpHeaders
            headers) {
        System.out.println(headers.toString());
        String auth = headers.getRequestHeaders().getFirst("authorization");

        System.out.println("In function");
        // Check required fields
        if (nid == null) {
            if (auth.equals(OFFICER_KEY)) { // Return all notices to an officer
                // Get all RenewalNotices from the DB
                DB_Handler db = new DB_Handler();
                List<RenewalNotice> rnl = new ArrayList<>();
                RenewalNotice respRenewalNotice = null;
                rnl = db.getRenewalNoticesList();

                ResponseBuilder builder = Response.ok().entity(rnl);
                return builder.build();
            } else {
                ResponseBuilder builder = Response.status(Response.Status.UNAUTHORIZED);
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

