import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
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
        System.out.println(rList);
        // updateRegistrationValidTill(2,rList.get(0).getValidTill());
        // updateAddress(2, "zXXXXXXX@unsw.edu.au");
    }

    /**
     * Creates a List of Car Registrations from the supplied XML file
     *
     * @return An Array list of Car Registrations
     */
    public static List<Registration> makeRegistrationList() {
        // Refrence:
        // https://www.tutorialspoint.com/java_xml/java_xpath_parse_document.htm
        List rList = new ArrayList<Registration>();
        try {
            NodeList nodeList = constructNodeList();
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
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return rList;
    }

    private static void updateDriverField(Integer rID, String updateString, String elementName) {
        assert rID > 0;
        try {
            String filePath1 = new File("").getAbsolutePath();
            String filePath = filePath1.concat("/src/CarRegistrations.xml");

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(filePath);
            Node Entry = doc.getElementsByTagName("Entry").item(rID - 1);
            NodeList nodeList = Entry.getChildNodes();
            for (int i = 0; nodeList != null && i < nodeList.getLength(); i++) {
                Node nod = nodeList.item(i);
                if (nod.getNodeType() == Node.ELEMENT_NODE && nod.getNodeName().equals("Driver")) {
                    NodeList nodeList2 = nod.getChildNodes();
                    for (int j = 0; nodeList2 != null && j < nodeList2.getLength(); j++) {
                        Node nod1 = nodeList2.item(j);
                        if (nod1.getNodeName().equals(elementName)) {
                            System.out.println(nod1.getTextContent());
                            nod1.setTextContent(updateString);
                            TransformerFactory transformerFactory = TransformerFactory.newInstance();
                            try {
                                Transformer transformer = transformerFactory.newTransformer();
                                DOMSource source = new DOMSource(doc);
                                System.out.println(filePath);
                                StreamResult result = new StreamResult(new File(filePath));
                                transformer.transform(source, result);
                                System.out.println(elementName + " Updated");

                            } catch (TransformerConfigurationException e) {
                                e.printStackTrace();
                            } catch (TransformerException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Updates the Email field in the XML file for a given rID
     * @param rID Integer ID for a Car registration entry
     * @param updatedEmail New Email String
     */
    public static void updateEmail(Integer rID, String updatedEmail) {
        updateDriverField(rID,updatedEmail, "Email");
    }

    public static void updateAddress(Integer rID, String updatedAddress) {
        updateDriverField(rID,updatedAddress, "Address");
    }

    public static void updateRegistrationValidTill(Integer rID, Date updatedDate) {
        //TODO: Test function
        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
        String date = sdf.format(updatedDate);
        // System.out.println("New Date: " + date);

        assert rID > 0;
        try {
            String filePath1 = new File("").getAbsolutePath();
            String filePath = filePath1.concat("/src/CarRegistrations.xml");

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(filePath);
            Node Entry = doc.getElementsByTagName("Entry").item(rID - 1);
            NodeList nodeList = Entry.getChildNodes();
            for (int i = 0; nodeList != null && i < nodeList.getLength(); i++) {
                Node nod = nodeList.item(i);
                if (nod.getNodeType() == Node.ELEMENT_NODE && nod.getNodeName().equals("RegistrationValidTill")) {
                    nod.setTextContent(date);

                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    try {
                        Transformer transformer = transformerFactory.newTransformer();
                        DOMSource source = new DOMSource(doc);
                        // System.out.println(filePath);
                        StreamResult result = new StreamResult(new File(filePath));
                        transformer.transform(source, result);
                        System.out.println("Valid till updated");

                    } catch (TransformerConfigurationException e) {
                        e.printStackTrace();
                    } catch (TransformerException e) {
                        e.printStackTrace();
                    }

                }
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static NodeList constructNodeList() {

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
            return nodeList;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        System.out.println("Error occured with constructNodeList, returning NULL");
        return null;
    }

    public static Document buildDoc() {
        try {
            File inputFile = new File("src/CarRegistrations.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder;
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            return doc;
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

