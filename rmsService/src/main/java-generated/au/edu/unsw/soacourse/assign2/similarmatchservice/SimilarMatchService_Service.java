package au.edu.unsw.soacourse.assign2.similarmatchservice;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.0.4
 * 2016-10-08T17:53:41.037+11:00
 * Generated source version: 3.0.4
 * 
 */
@WebServiceClient(name = "SimilarMatchService", 
                  wsdlLocation = "file:/Users/Leon/eclipse/workspace9322/rmsService/src/main/resources/wsdl/SimilarMatchService.wsdl",
                  targetNamespace = "http://similarmatchservice.assign2.soacourse.unsw.edu.au") 
public class SimilarMatchService_Service extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://similarmatchservice.assign2.soacourse.unsw.edu.au", "SimilarMatchService");
    public final static QName SimilarMatchService = new QName("http://similarmatchservice.assign2.soacourse.unsw.edu.au", "SimilarMatchService");
    static {
        URL url = null;
        try {
            url = new URL("file:/Users/Leon/eclipse/workspace9322/rmsService/src/main/resources/wsdl/SimilarMatchService.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(SimilarMatchService_Service.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "file:/Users/Leon/eclipse/workspace9322/rmsService/src/main/resources/wsdl/SimilarMatchService.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public SimilarMatchService_Service(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public SimilarMatchService_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public SimilarMatchService_Service() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public SimilarMatchService_Service(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public SimilarMatchService_Service(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public SimilarMatchService_Service(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }    

    /**
     *
     * @return
     *     returns SimilarMatchService
     */
    @WebEndpoint(name = "SimilarMatchService")
    public SimilarMatchService getSimilarMatchService() {
        return super.getPort(SimilarMatchService, SimilarMatchService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns SimilarMatchService
     */
    @WebEndpoint(name = "SimilarMatchService")
    public SimilarMatchService getSimilarMatchService(WebServiceFeature... features) {
        return super.getPort(SimilarMatchService, SimilarMatchService.class, features);
    }

}