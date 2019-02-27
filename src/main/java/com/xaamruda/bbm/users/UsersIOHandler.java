package com.xaamruda.bbm.users;

import java.util.List;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xaamruda.bbm.commons.exceptions.DatabaseException;
import com.xaamruda.bbm.commons.json.JsonUtils;
import com.xaamruda.bbm.commons.logging.BBMLogger;
import com.xaamruda.bbm.integrity.IntegrityIOHandler;
import com.xaamruda.bbm.users.data.IUserDataManager;
import com.xaamruda.bbm.users.dbaccess.service.IUserService;
import com.xaamruda.bbm.users.identification.IUserIdentificator;
import com.xaamruda.bbm.commons.mailing.MailSender;
import com.xaamruda.bbm.users.model.User;

/**
 * Entry point to users module.
 */
@Component
public class UsersIOHandler {

	@Autowired
	private IUserService service;

	@Autowired
	private IUserIdentificator identificator;

	@Autowired
	private IUserDataManager dataManager;
	
	@Autowired
	private MailSender mailsender;
	
	@Autowired
	private IntegrityIOHandler integrityIOHandler;

	public UsersIOHandler() {
	}

	public boolean identifyUserByMailPlusPassword(String userMail, String userPassword) throws DatabaseException {
		long journalId = integrityIOHandler.addUserJournalEntry("identifyUserByMailPlusPassword",
				this.getClass().getSimpleName(), userMail, userPassword);
		
		boolean exists = identificator.identify(userMail, userPassword);
		
		if (exists) {
			try {
				List<User> users = service.getUserByMail(userMail);

				if(users != null && !users.isEmpty()) {
					User user = users.get(0);
					user.setIdentified(true);
					service.store(user);
				}
			} catch(Exception e) {
				throw new DatabaseException("User identification failed because database is down.\n");
			}
		}
		
		integrityIOHandler.endUserJournalEntry(journalId);
		return exists;
	}

	public boolean isIdentified(String userMail) throws DatabaseException {
		List<User> userList = null;
		
		try {
			userList = service.getUserByMail(userMail);
		} catch(Exception e) {
			throw new DatabaseException("User identification failed as database is down.\n");
		}
		
		if (userList != null && !userList.isEmpty()) {
			User user = userList.get(0);
			return user.isIdentified();
		}
		
		return false;
	}

	public void postNewUser(String userJson) throws DatabaseException {
		long journalId = integrityIOHandler.addUserJournalEntry("postNewUser",
				this.getClass().getSimpleName(), userJson);
		User user = JsonUtils.getFromJson(userJson, User.class);
		
		try {
			dataManager.storeNewUser(user);
		} catch(Exception e) {
			throw new DatabaseException("User creation failed because database is down.\n");
		}
		
		integrityIOHandler.endUserJournalEntry(journalId);
		BBMLogger.infoln("User created.");
	}

	public List<User> retrieveUsers() throws DatabaseException {
		BBMLogger.infoln("Processing...");
		
		List<User> users = null;
		try {
			users = service.getAllUsers();
		} catch(Exception e) {
			throw new DatabaseException("User recovery failed because database is down.\n");
		}
		
		return users;
	}

	public User retrieveUser(String mail) throws DatabaseException {
		BBMLogger.infoln("Processing...");
		User user = null;
		
		try {
			List<User> users = service.getUserByMail(mail); 
			if(users != null && !users.isEmpty()) {
				users = service.getUserByMail(mail);
				user = users.get(0);
			}
		} catch(Exception e) {
			throw new DatabaseException("User recovery failed because database is down.\n");
		}
		
		return user;
	}

	public boolean sendMail(String ownerMail, String buyerMail, String subject) {
		return mailsender.sendEmail(ownerMail, buyerMail, subject);
	}
	
	@Transactional(value=TxType.MANDATORY)
	public void makeTransaction(String ownerID, String buyerID, Integer finalPrice) throws DatabaseException {
		// add journaling entry to user journal file
		long journalId = integrityIOHandler.addUserJournalEntry("makeTransaction", this.getClass().getSimpleName(),
				ownerID, buyerID, finalPrice);
		
		User buyer = null;
		User owner = null;
		
		try {
			buyer = service.getUserByMail(buyerID).get(0);
			owner = service.getUserByMail(ownerID).get(0);
		} catch (Exception ex) {
			throw new DatabaseException("Transaction failed.", "Owner: " + ownerID,
					"Buyer: " + buyerID, "Price: " + finalPrice + "\n");
		}
		
		int newBuyerPoints = buyer.getPointsAmount() - finalPrice;
		
		
		// inferior bound
		if(newBuyerPoints < 0) {
			newBuyerPoints = 0;
		}
		
		owner.setPointsAmount(owner.getPointsAmount() + finalPrice);
		buyer.setPointsAmount(newBuyerPoints);

		BBMLogger.advancedDemoln("The system credits " + owner.getMail() + " with " + finalPrice + " points.");
		service.store(owner);
		BBMLogger.advancedDemoln("The system is freezing in order to shutdown the database...");
		BBMLogger.waitForEvent();
		
		service.store(buyer);
		
		// end journaling entry
		integrityIOHandler.endUserJournalEntry(journalId);
	}

	public void debit(String buyerID, Integer finalPrice) throws DatabaseException {
		// add journaling entry to user journal file
		long journalId = integrityIOHandler.addUserJournalEntry("debit", this.getClass().getSimpleName(), buyerID, finalPrice);
		User buyer = null;
		
		try {
			buyer = service.getUserByMail(buyerID).get(0);
		} catch(Exception e) {
			throw new DatabaseException("Debit failed.", "Buyer: " + buyerID, "Price: " + finalPrice);
		}
		
		if (buyer.getPointsAmount() == null)
			buyer.setPointsAmount(0);
		
		buyer.setPointsAmount(buyer.getPointsAmount() - finalPrice);
		
		try {
			service.store(buyer);
		} catch(Exception e) {
			throw new DatabaseException("Debit: buyer storing failed.", "Buyer: " + buyerID, "Price: " + finalPrice + "\n");
		}
		
		// end journaling entry
		integrityIOHandler.endUserJournalEntry(journalId);
	}

	public void credit(String ownerID, Integer finalPrice) throws DatabaseException {
		// add journaling entry to user journal file
		long journalId = integrityIOHandler.addUserJournalEntry("credit", this.getClass().getSimpleName(), ownerID, finalPrice);
		User owner = null;
		
		try {
			owner = service.getUserByMail(ownerID).get(0);
		} catch(Exception e) {
			throw new DatabaseException("Credit: owner retrieving failed.",
					"Owner: " + ownerID, "Price: " + finalPrice + "\n");
		}
		
		if (owner.getPointsAmount() == null)
			owner.setPointsAmount(0);
		
		owner.setPointsAmount(owner.getPointsAmount() + finalPrice);
		
		try {
			service.store(owner);
		} catch(Exception e) {
			throw new DatabaseException("Credit: owner storing failed.",
					"Owner: " + ownerID, "Price: " + finalPrice + "\n");
		}
		
		// end journaling entry
		integrityIOHandler.endUserJournalEntry(journalId);
	}
}
