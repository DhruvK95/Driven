import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class Main {

    public static void main(String[] args) {
        List<Registration> rList = new ArrayList<Registration>();
        rList = makeRegistrationList();
        // System.out.println(rList);
    }

    /**
     * Creates a List of Car Registrations from the supplied XML file
     * @return An Array list of Car Registrations
     */
    public static List<Registration> makeRegistrationList() {
        // Refrence:
        // https://www.tutorialspoint.com/java_xml/java_xpath_parse_document.htm
        List rList = new ArrayList<Registration>();
        try {
            // Use the print statement to get the path Java is looking for the input file in.
            // System.out.println(new File(".").getAbsoluteFile());
            File inputFile = new File("src/CarRegistrations.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder;
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            XPath xPath = XPathFactory.newInstance().newXPath();
            String expression = "/Registrations/Entry";
            NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node nNode = nodeList.item(i);
                // System.out.println("C " + nNode.getNodeName());
                // If an Entry has inner elements
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element currEntry = (Element) nNode;
                    Integer rID = Integer.parseInt(currEntry.getElementsByTagName("_rid").item(0).getTextContent());
                    // System.out.println("rID " + rID.toString());
                    String RegistrationNumber = currEntry.getElementsByTagName("RegistrationNumber").item(0).getTextContent();
                    // System.out.println("Reg Number " + RegistrationNumber);
                    // DD/MM/YYYY
                    String dateString = currEntry.getElementsByTagName("RegistrationValidTill").item(0).getTextContent();
                    DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                    Date date = format.parse(dateString);
                    // System.out.println(date);
                    // Extract the Driver
                    if (currEntry.getNodeType() == Node.ELEMENT_NODE) {
                        Element currDriver = (Element) currEntry;
                        String LastName = currDriver.getElementsByTagName("LastName").item(0).getTextContent();
                        String FirstName = currDriver.getElementsByTagName("FirstName").item(0).getTextContent();
                        String Email = currDriver.getElementsByTagName("Email").item(0).getTextContent();
                        String Address = currDriver.getElementsByTagName("Address").item(0).getTextContent();
                        String DriversLicenseNo = currDriver.getElementsByTagName("DriversLicenseNo").item(0).getTextContent();

                        Driver d = new Driver(LastName, FirstName, DriversLicenseNo, Address, Email);
                        // Create the Registration object
                        Registration r = new Registration(rID, RegistrationNumber, date, d);
                        // System.out.println(r);
                        rList.add(r);
                    }
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return rList;
    }

}

