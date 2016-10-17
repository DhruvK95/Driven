package bpelSpawner;
import java.util.Iterator;

import javax.xml.soap.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
/*
 * 
 * BASE CODE FROM http://www.cdyne.com/
 * 
 */
public class soapClient {

	public boolean Exact = false;
	public String message = null;
	private static SOAPMessage createSOAPRequest(String address) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        String serverURI = "http://assign2.soacourse.unsw.edu.au/autocheck";

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("aut", serverURI);

        /*
		<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
		  xmlns:aut="http://assign2.soacourse.unsw.edu.au/autocheck">
		   <soapenv:Header/>
		   <soapenv:Body>
		      <aut:AutoCheckRequest>
		         <aut:address>1,sfdf,ewf,efw,1</aut:address>
		      </aut:AutoCheckRequest>
		   </soapenv:Body>
		</soapenv:Envelope>
         */

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("AutoCheckRequest", "aut");
        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("address", "aut");
        soapBodyElem1.addTextNode(address);
        
        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", serverURI  + "AutoCheckRequest");

        soapMessage.saveChanges();
        return soapMessage;
    }


    
    /**
     * Starting point for the SAAJ - SOAP Client Testing
     */
    public void doSoap(String address) {
        try {
            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            // Send SOAP Message to SOAP Server
            String url = "http://localhost:6060/ode/processes/AutoCheck";
            SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(address), url);
            String message = null;
            String messageStatus = null;
            //MESSAGE
            NodeList returnList = soapResponse.getSOAPBody().getElementsByTagName("tns:message");
            for (int i = 0; i < returnList.getLength(); i++) {
                NodeList innerList = returnList.item(i).getChildNodes();
                for (int j = 0; j < innerList.getLength(); j++) {
                    message = (innerList.item(j).getTextContent());
                }
            }
          //MESSAGESTATUS
            returnList = soapResponse.getSOAPBody().getElementsByTagName("tns:messageStatus");
            for (int i = 0; i < returnList.getLength(); i++) {
                NodeList innerList = returnList.item(i).getChildNodes();
                for (int j = 0; j < innerList.getLength(); j++) {
                	messageStatus = (innerList.item(j).getTextContent());
                }
            }
            if(message.contains("Exact Address Found")){
            	Exact = true;
        	}else{
        		this.message = message;
        	}
            
            System.out.print(message +messageStatus);
            soapConnection.close();
        } catch (Exception e) {
            System.err.println("Error occurred while sending SOAP Request to Server");
            e.printStackTrace();
        }
    }


}