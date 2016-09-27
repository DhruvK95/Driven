
package au.edu.unsw.soacourse.gnafaddressingservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the au.edu.unsw.soacourse.gnafaddressingservice package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AddressExistsFault_QNAME = new QName("http://GNAFAddressingService.soacourse.unsw.edu.au", "addressExistsFault");
    private final static QName _SimilarAddressesFault_QNAME = new QName("http://GNAFAddressingService.soacourse.unsw.edu.au", "similarAddressesFault");
    private final static QName _ReturnPostcodeFault_QNAME = new QName("http://GNAFAddressingService.soacourse.unsw.edu.au", "returnPostcodeFault");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: au.edu.unsw.soacourse.gnafaddressingservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AddressExistsInFileRequest }
     * 
     */
    public AddressExistsInFileRequest createAddressExistsInFileRequest() {
        return new AddressExistsInFileRequest();
    }

    /**
     * Create an instance of {@link AddressExistsInFileResponse }
     * 
     */
    public AddressExistsInFileResponse createAddressExistsInFileResponse() {
        return new AddressExistsInFileResponse();
    }

    /**
     * Create an instance of {@link SimilarAddressesInFileRequest }
     * 
     */
    public SimilarAddressesInFileRequest createSimilarAddressesInFileRequest() {
        return new SimilarAddressesInFileRequest();
    }

    /**
     * Create an instance of {@link SimilarAddressesInFileResponse }
     * 
     */
    public SimilarAddressesInFileResponse createSimilarAddressesInFileResponse() {
        return new SimilarAddressesInFileResponse();
    }

    /**
     * Create an instance of {@link ReturnPostcodeInfoRequest }
     * 
     */
    public ReturnPostcodeInfoRequest createReturnPostcodeInfoRequest() {
        return new ReturnPostcodeInfoRequest();
    }

    /**
     * Create an instance of {@link ReturnPostcodeInfoResponse }
     * 
     */
    public ReturnPostcodeInfoResponse createReturnPostcodeInfoResponse() {
        return new ReturnPostcodeInfoResponse();
    }

    /**
     * Create an instance of {@link ServiceFaultType }
     * 
     */
    public ServiceFaultType createServiceFaultType() {
        return new ServiceFaultType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServiceFaultType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://GNAFAddressingService.soacourse.unsw.edu.au", name = "addressExistsFault")
    public JAXBElement<ServiceFaultType> createAddressExistsFault(ServiceFaultType value) {
        return new JAXBElement<ServiceFaultType>(_AddressExistsFault_QNAME, ServiceFaultType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServiceFaultType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://GNAFAddressingService.soacourse.unsw.edu.au", name = "similarAddressesFault")
    public JAXBElement<ServiceFaultType> createSimilarAddressesFault(ServiceFaultType value) {
        return new JAXBElement<ServiceFaultType>(_SimilarAddressesFault_QNAME, ServiceFaultType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServiceFaultType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://GNAFAddressingService.soacourse.unsw.edu.au", name = "returnPostcodeFault")
    public JAXBElement<ServiceFaultType> createReturnPostcodeFault(ServiceFaultType value) {
        return new JAXBElement<ServiceFaultType>(_ReturnPostcodeFault_QNAME, ServiceFaultType.class, null, value);
    }

}
