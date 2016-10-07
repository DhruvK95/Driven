package au.edu.unsw.soacourse.assign2.driverslicense;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.0.4
 * 2016-10-06T00:42:51.939+11:00
 * Generated source version: 3.0.4
 * 
 */
@WebServiceClient(name = "DriversLicenseService", 
                  wsdlLocation = "file:/Users/Leon/eclipse/workspace9322/rmsService/src/main/resources/wsdl/DriversLicenseService.wsdl",
                  targetNamespace = "http://driverslicense.assign2.soacourse.unsw.edu.au") 
public class DriversLicenseService_Service extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://driverslicense.assign2.soacourse.unsw.edu.au", "DriversLicenseService");
    public final static QName DriversLicenseService = new QName("http://driverslicense.assign2.soacourse.unsw.edu.au", "DriversLicenseService");
    static {
        URL url = null;
        try {
            url = new URL("file:/Users/Leon/eclipse/workspace9322/rmsService/src/main/resources/wsdl/DriversLicenseService.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(DriversLicenseService_Service.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "file:/Users/Leon/eclipse/workspace9322/rmsService/src/main/resources/wsdl/DriversLicenseService.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public DriversLicenseService_Service(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public DriversLicenseService_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public DriversLicenseService_Service() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public DriversLicenseService_Service(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public DriversLicenseService_Service(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public DriversLicenseService_Service(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }    

    /**
     *
     * @return
     *     returns DriversLicenseService
     */
    @WebEndpoint(name = "DriversLicenseService")
    public DriversLicenseService getDriversLicenseService() {
        return super.getPort(DriversLicenseService, DriversLicenseService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns DriversLicenseService
     */
    @WebEndpoint(name = "DriversLicenseService")
    public DriversLicenseService getDriversLicenseService(WebServiceFeature... features) {
        return super.getPort(DriversLicenseService, DriversLicenseService.class, features);
    }

}
