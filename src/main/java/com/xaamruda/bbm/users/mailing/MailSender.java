package com.xaamruda.bbm.users.mailing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.xaamruda.bbm.commons.logging.BBMLogger;

@Component
public class MailSender {

	private MailSender() {
	}

	@Autowired
	public JavaMailSender emailSender;

	private void sendSimpleEmail(String mail) {
		
		// Create a Simple MailMessage.
		SimpleMailMessage message = new SimpleMailMessage();

		message.setTo(mail);
		message.setSubject("Test Simple Email");
		message.setText("Hello, Im testing Simple Email");

		// Send Message!
		this.emailSender.send(message);

	}
	
	
	private void sendEmailConfirmAwaitingOffer(String ownerMail, String buyerMail) {
		
		SimpleMailMessage message = new SimpleMailMessage();

		message.setTo(ownerMail);
		message.setSubject("Confirm your new awaiting offer !");
		message.setText("Dear " + ownerMail + ",\n\n" + buyerMail + " asked for one of your offers to be confirmed !\n\n You can now log in on BlablaMove to have a full recap about this offer ! :D \n\n Have a nice day ! \n\n - BlablaMove Staff");

		this.emailSender.send(message);

	}
	
	private void sendEmailAskValidation(String ownerMail, String buyerMail) {
		
		SimpleMailMessage message = new SimpleMailMessage();

		message.setTo(buyerMail);
		message.setSubject("Confirmation mail");
		message.setText("Dear " + buyerMail + ",\n\n" + "In order to validate your request to " + ownerMail + " you can now log in on BlablaMove and have a full recap about this offer ! :D \n\n Have a nice day ! \n\n - BlablaMove Staff");

		this.emailSender.send(message);

	}

	public boolean sendEmail(String ownerMail, String buyerMail, String subject) {
		
		switch (subject) {
		
		case BlablaMailConstants.BLABLA_SUBJECT_CONFIRM_OFFER:
			
			sendEmailConfirmAwaitingOffer(ownerMail,buyerMail);
			BBMLogger.infoln("Email successfully sent to : " + ownerMail);
			
			break;
		
		case BlablaMailConstants.BLABLA_SUBJECT_ASK_VALIDATION :
			
			sendEmailAskValidation(ownerMail,buyerMail);
			BBMLogger.infoln("Email successfully sent to : " + buyerMail);
			
			break;

		default:
			
			break;
		}
		
		return true;
	}

}
