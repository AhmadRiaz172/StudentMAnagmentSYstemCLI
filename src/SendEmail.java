
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class SendEmail {

	public void Send( String recieverEmail , String MessageToSend ){
		
        final String username = "youremailhere@gmail.com";
        final String password = "yourpasswordhere";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS
        
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("youremailhere@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(recieverEmail)
            );
            message.setSubject("Message by java application");
            message.setText(MessageToSend);

            Transport.send(message);
            System.out.println("Email notification sent to " + recieverEmail + ".....");

        } catch (MessagingException e) {
            //e.printStackTrace();
	   
			System.out.println("Unable to send email notification....");
		}


	}


}