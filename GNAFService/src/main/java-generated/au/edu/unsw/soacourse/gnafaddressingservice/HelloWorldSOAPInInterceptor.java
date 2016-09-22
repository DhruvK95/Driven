package au.edu.unsw.soacourse.gnafaddressingservice;

import org.apache.cxf.binding.soap.SoapHeader;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Element;

import javax.xml.namespace.QName;

public class HelloWorldSOAPInInterceptor extends AbstractSoapInterceptor {

	 public HelloWorldSOAPInInterceptor() {
	      super(Phase.USER_PROTOCOL);
	}
	 
	@Override
	public void handleMessage(SoapMessage message) throws Fault {
		// TODO Auto-generated method stub
		//au.edu.unsw.soacourse.gnafaddressingservice
		 QName qname = new QName("http://soacourse.unsw.edu.au/gnafaddressingservice/intercept","apikey");

	    // Get the SOAP header from the message using the qualified name.
		SoapHeader header = (SoapHeader) message.getHeader(qname);

		// Get the element from the soap header and set the api key to its text
		// content.
		String apikey = ((Element) header.getObject()).getTextContent();
		System.out.println("Found API Key " + apikey.toString());
		System.err.println("API Key = " + apikey);
	}

}
