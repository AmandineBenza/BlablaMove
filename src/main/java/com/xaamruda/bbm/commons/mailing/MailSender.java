package com.xaamruda.bbm.commons.mailing;

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
		message.setText("Dear " + ownerMail + ",\n\n" 
		+ buyerMail + " asked for one of your offers to be confirmed !\n\n "
		+ "You can now log in on BlablaMove to have a full recap about this offer ! :D \n\n "
		+ "Have a nice day ! \n\n - BlablaMove Staff");

		this.emailSender.send(message);

	}
	
	private void sendEmailAskValidation(String ownerMail, String buyerMail) {
		
		SimpleMailMessage message = new SimpleMailMessage();

		message.setTo(buyerMail);
		message.setSubject("Confirmation mail");
		message.setText("Dear " + buyerMail + ",\n\n" 
		+ "In order to validate your request to " + ownerMail 
		+ " you can now log in on BlablaMove and have a full recap about this offer ! :D \n\n "
		+ "Have a nice day ! \n\n - BlablaMove Staff");

		this.emailSender.send(message);

	}
	
	private void sendEmailProblemOccuredConfirmDeposit(String ownerMail, String buyerMail) {
		
		SimpleMailMessage message = new SimpleMailMessage();

		message.setTo(buyerMail);
		message.setSubject("A problem occured with your confirmation");
		message.setText("Dear " + buyerMail + ",\n\n" 
		+ "Unfortunately a problem occured with the deposit confirmation you made about the item that was moved by " 
		+ ownerMail + 
		".\n You can now log in on BlablaMove in order to redo the confirmation !  \n\n "
		+ "Thank you and have a nice day ! \n\n - BlablaMove Staff");

		this.emailSender.send(message);

	}
	
	private void sendEmailProblemOccuredClaimDeposit(String ownerMail, String buyerMail) {
		
		SimpleMailMessage message = new SimpleMailMessage();

		message.setTo(ownerMail);
		message.setSubject("A problem occured with your claim");
		message.setText("Dear " + ownerMail + ",\n\n" 
		+ "Unfortunately a problem occured with the claim deposit you made about the item that you moved for " 
		+ buyerMail + 
		".\n You can now log in on BlablaMove in order to redo the claim !  \n\n "
		+ "Thank you and have a nice day ! \n\n - BlablaMove Staff");

		this.emailSender.send(message);

	}
	
	private void sendEmailProblemOccuredConfirmReception(String ownerMail, String buyerMail) {
		
		SimpleMailMessage message = new SimpleMailMessage();

		message.setTo(ownerMail);
		message.setSubject("A problem occured with your claim");
		message.setText("Dear " + ownerMail + ",\n\n" 
		+ "Unfortunately a problem occured with the reception confirmation you made about the item you will move for " 
		+ buyerMail + 
		".\n You can now log in on BlablaMove in order to redo the confirmation !  \n\n "
		+ "Thank you and have a nice day ! \n\n - BlablaMove Staff");

		this.emailSender.send(message);

	}
	
	private void sendEmailProblemOccuredClaimReception(String ownerMail, String buyerMail) {
		
		SimpleMailMessage message = new SimpleMailMessage();

		message.setTo(buyerMail);
		message.setSubject("A problem occured with your claim");
		message.setText("Dear " + buyerMail + ",\n\n" 
		+ "Unfortunately a problem occured with the claim reception you made about the item that that will be move by " 
		+ ownerMail + 
		".\n You can now log in on BlablaMove in order to redo the claim !  \n\n "
		+ "Thank you and have a nice day ! \n\n - BlablaMove Staff");

		this.emailSender.send(message);

	}
	
	private void sendEmailProblemOccuredConfirmAwaitingOffer(String ownerMail, String buyerMail) {
		
		SimpleMailMessage message = new SimpleMailMessage();

		message.setTo(ownerMail);
		message.setSubject("A problem occured with your claim");
		message.setText("Dear " + ownerMail + ",\n\n" 
		+ "Unfortunately a problem occured with the confirmation of the offer for " 
		+ ownerMail + 
		".\n You can now log in on BlablaMove in order to redo the confirmation !  \n\n "
		+ "Thank you and have a nice day ! \n\n - BlablaMove Staff");

		this.emailSender.send(message);

	}
	
	private void sendEmailProblemOccuredAskValidation(String ownerMail, String buyerMail) {
		
		SimpleMailMessage message = new SimpleMailMessage();

		message.setTo(ownerMail);
		message.setSubject("A problem occured with your request");
		message.setText("Dear " + ownerMail + ",\n\n" 
		+ "Unfortunately a problem occured with validation request you juste made. " 
		+ ".\n You can now log in on BlablaMove in order to redo the request !  \n\n "
		+ "Thank you and have a nice day ! \n\n - BlablaMove Staff");

		this.emailSender.send(message);

	}
	
	private void sendEmailProblemOccuredPostingOffer(String ownerMail, String buyerMail) {
		
		SimpleMailMessage message = new SimpleMailMessage();

		message.setTo(ownerMail);
		message.setSubject("A problem occured when posting your offer");
		message.setText("Dear " + ownerMail + ",\n\n" 
		+ "Unfortunately a problem occured when posting your offer. " 
		+ ".\n You can now log in on BlablaMove in order to post your offer !  \n\n "
		+ "Thank you and have a nice day ! \n\n - BlablaMove Staff");

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
			
		case BlablaMailConstants.BLABLA_SUBJECT_PROBLEM_OCCURED_WITH_CONFIRM_DEPOSIT :
			sendEmailProblemOccuredConfirmDeposit(ownerMail,buyerMail);
			BBMLogger.infoln("Email successfully sent to : " + buyerMail);
		default:
			
			break;
		}
		
		return true;
	}

}
