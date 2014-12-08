package dk.visualdesign.mail;
/*
Some SMTP servers require a username and password authentication before you
can use their Server for Sending mail. This is most common with couple
of ISP's who provide SMTP Address to Send Mail.

This Program gives any example on how to do SMTP Authentication
(User and Password verification)

This is a free source code and is provided as it is without any warranties and
it can be used in any your code for free.

Author : Sudhir Ancha
 */


import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


/*


 Next change values for fields

 emailMsgTxt  -- Message Text for the Email
 emailSubjectTxt  -- Subject for email
 emailFromAddress -- Email Address whose name will appears as "from" address

 Next change value for "emailList".
 This String array has List of all Email Addresses to Email Email needs to be sent to.


 Next to run the program, execute it as follows,

 SendMailUsingAuthentication authProg = new SendMailUsingAuthentication();

 */

public class SendMailUsingAuthentication {
	
	private final String _SMTP_HOST_NAME;
	private final String _SMTP_USER;
	private final String _SMTP_PASSWORD;

	public SendMailUsingAuthentication(String hostname, String user, String password){
		this._SMTP_HOST_NAME = hostname;
		this._SMTP_USER = user;
		this._SMTP_PASSWORD = password;
	}


//	private static final String emailMsgTxt = "Online Order Confirmation Message. Also include the Tracking Number.";
//	private static final String emailSubjectTxt = "Order Confirmation Subject";
//	private static final String emailFromAddress = "sudhir@javacommerce.com";

	// Add List of Email address to who email needs to be sent to
//	private static final String[] emailList = { "mark@yahoo.com",
//			"robin@javacommerce.com" };

//	public static void main(String args[]) throws Exception {
//		SendMailUsingAuthentication smtpMailSender = new SendMailUsingAuthentication();
//		smtpMailSender.postMail(emailList, emailSubjectTxt, emailMsgTxt, emailFromAddress);
//		System.out.println("Sucessfully Sent mail to All Users");
//	}

	public void postMail(String recipients[], String subject, String message, String from) throws MessagingException {
		boolean debug = false;

		// Set the host smtp address
		Properties props = new Properties();
		props.put("mail.smtp.host", _SMTP_HOST_NAME);
		props.put("mail.smtp.auth", "true");

		Authenticator auth = new SMTPAuthenticator();
		Session session = Session.getDefaultInstance(props, auth);

		session.setDebug(debug);

		// create a message
		Message msg = new MimeMessage(session);

		// set the from and to address
		InternetAddress addressFrom = new InternetAddress(from);
		msg.setFrom(addressFrom);
		
		InternetAddress[] addressTo = new InternetAddress[recipients.length];
		for (int i = 0; i < recipients.length; i++) {
			addressTo[i] = new InternetAddress(recipients[i]);
		}
		msg.setRecipients(Message.RecipientType.TO, addressTo);

		// Setting the Subject and Content Type
		msg.setSubject(subject);
		msg.setContent(message, "text/plain");
		Transport.send(msg);
	}

	/**
	 * SimpleAuthenticator is used to do simple authentication when the SMTP
	 * server requires it.
	 */
	private class SMTPAuthenticator extends javax.mail.Authenticator {

		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(_SMTP_USER, _SMTP_PASSWORD);
		}
	}

}
