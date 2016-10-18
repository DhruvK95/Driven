package au.edu.unsw.soacourse.driven;

// Refrence: http://crunchify.com/java-mailapi-example-send-an-email-via-gmail-smtp/

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailHandler {

    static Properties mailServerProperties;
    static Session getMailSession;
    static MimeMessage generateMailMessage;

    // public static void main(String args[]) throws AddressException, MessagingException {
    //     String body = "E-mail by Driven RMS <br>" +
    //             "<p><a href=\"http://localhost:8080/DrivenRest/driven/notices/?nid=1\">Click here to view Renewal " +
    //             "Notice</a></p> " +
    //             "<br> Regards, <br>Driven Admin";
    //     generateAndSendEmail("dhruvk995@gmail.com", "Driven renewal Notice", body);
    //     System.out.println("\n\n ===> Your Java Program has just sent an Email successfully. Check your email..");
    // }

    public void generateAndSendEmail(String recipientEmail,String ccEmail, String emailSubject, String emailBody) throws
            AddressException,
            MessagingException {

        // Step1
        System.out.println("\n 1st ===> setup Mail Server Properties..");
        mailServerProperties = System.getProperties();
        mailServerProperties.put("mail.smtp.port", "587");
        mailServerProperties.put("mail.smtp.auth", "true");
        mailServerProperties.put("mail.smtp.starttls.enable", "true");
        System.out.println("Mail Server Properties have been setup successfully..");

        // Step2
        System.out.println("2nd ===> get Mail Session..");
        getMailSession = Session.getDefaultInstance(mailServerProperties, null);
        generateMailMessage = new MimeMessage(getMailSession);
        generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
        generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(ccEmail));
        generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress("laugustine1@gmail.com"));
        generateMailMessage.setSubject(emailSubject);
        generateMailMessage.setContent(emailBody, "text/html");
        System.out.println("Mail Session has been created successfully..");

        // Step3
        System.out.println("3rd ===> Get Session and Send mail");
        Transport transport = getMailSession.getTransport("smtp");

        // Enter your correct gmail UserID and Password
        // if you have 2FA enabled then provide App Specific Password
        transport.connect("smtp.gmail.com", "drivenrms@gmail.com", "COMP9322");
        transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
        transport.close();
    }
}
