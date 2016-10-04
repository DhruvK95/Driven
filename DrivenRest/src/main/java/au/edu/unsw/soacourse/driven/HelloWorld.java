package au.edu.unsw.soacourse.driven;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.xml.parsers.ParserConfigurationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;


@Path("/driven")
public class HelloWorld {

    @POST
    @Path("/staff/generate")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response generateNotices() {
        // TODO: This endpoint may require auth

        List<Registration> rList = new ArrayList<Registration>();
        XML_Handler xh = new XML_Handler();
        rList = xh.makeRegistrationList();
        System.out.println("Registrations:");
        // System.out.println(rList);

        System.out.println("TestDB");
        DB_Handler db = new DB_Handler();
        // db.createTables();
        db.addRenewalNotice(5, "Test...");
        Date currDate = new Date();
        db.addPayment(0, 200, 198231, "Dhruv", 222, currDate);

        // getPaymentsList Testing
        List<Payment> p = new ArrayList<Payment>();
        p = db.getPaymentsList();
        System.out.println("Payment List: " + p);

        RMS_Impl rms = new RMS_Impl();
        rms.generateNotices();
        // TODO: Take response list from function and encapsulate into RenewalNoticeResponse Objects (with links)

        ResponseBuilder builder = Response.ok().entity(rList);

        return builder.build();
    }

    @GET
    @Path("/notices/")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getRenewalNotice (@QueryParam("nid") Integer nid) throws ParserConfigurationException{
        if (nid == null) {
            ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
            return builder.build();
        }
        // System.out.println("getRenewalNotice QueryParam: " + nid.toString());

        // Get all RenewalNotices from the DB
        DB_Handler db = new DB_Handler();
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

