package com.techelevator.email;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Component;

@Component
public class Mailer {

	public void sendMail(String recepient) throws Exception {
		Properties properties = new Properties();

		//Enable authentication
		properties.put("mail.smtp.auth", "true");
		//Set TLS encryption enabled
		properties.put("mail.smtp.starttls.enable", "true");
		//Set SMTP host
		properties.put("mail.smtp.host", "smtp.gmail.com");
		//Set smtp port
		properties.put("mail.smtp.port", "587");

		//Your gmail address
		String myAccountEmail = "toollendinglibraryupdates@gmail.com";
		//Your gmail password
		String password = "TechElevatorFinalCapstoneTeamCharlie";

		//Create a session with account credentials
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(myAccountEmail, password);
			}
		});

		//Prepare email message
		Message message = prepareMessage(session, myAccountEmail, recepient);

		//Send mail
		Transport.send(message);
	}


	private static Message prepareMessage(Session session, String myAccountEmail, String recepient) {
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(myAccountEmail));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
			message.setSubject("Your Tool Reservation");
			String htmlCode = "<h1>Your Tool Reservation</h1><p>Your wait is over! The tool you reserved is now available and ready to be checked out. We went ahead and added it to your cart, and we will hold it for you for 2 days. After that time, your reservation will expire and the tool will be released for other customers to reserve.</p>";
			message.setContent(htmlCode, "text/html");
			return message;
		} catch (Exception ex) {
			Logger.getLogger(Mailer.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

}
