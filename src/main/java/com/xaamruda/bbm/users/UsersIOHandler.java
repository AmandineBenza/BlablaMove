package com.xaamruda.bbm.users;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xaamruda.bbm.commons.json.JsonUtils;
import com.xaamruda.bbm.commons.logging.BBMLogger;
import com.xaamruda.bbm.users.data.IUserDataManager;
import com.xaamruda.bbm.users.dbaccess.service.IUserService;
import com.xaamruda.bbm.users.identification.IUserIdentificator;
import com.xaamruda.bbm.users.mailing.MailSender;
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

	public UsersIOHandler() {
	}

	public boolean identifyUserByMailPlusPassword(String userMail, String userPassword) {
		boolean exists = identificator.identify(userMail, userPassword);
		if (exists) {
			User user = service.getUserByMail(userMail).get(0);
			user.setIdentified(true);
			service.store(user);
		}
		return exists;
	}

	public boolean isIdentified(String userMail) {
		List<User> userList = service.getUserByMail(userMail);
		if (userList != null && !userList.isEmpty()) {
			User user = userList.get(0);
			return user.isIdentified();
		}
		return false;
	}

	public void postNewUser(String userJson) {
		dataManager.storeNewUser(JsonUtils.getFromJson(userJson, User.class));
		BBMLogger.infoln("User created.");
	}

	public List<User> retrieveUsers() {
		BBMLogger.infoln("Processing...");
		List<User> users = service.getAllUsers();
		return users;
	}

	public User retrieveUser(String mail) {
		BBMLogger.infoln("Processing...");
		User user = service.getUserByMail(mail).get(0);
		return user;
	}

	public boolean sendMail(String mail, int price, String from) {
		return MailSender.sendEmail(mail);
	}

	public void makeTransaction(String ownerID, String buyerID, Integer finalPrice) {
		User buyer = null;
		User owner = null;
		try {
			buyer = service.getUserByMail(buyerID).get(0);
			owner = service.getUserByMail(ownerID).get(0);
		} catch (Exception ex) {
			// TODO journalisation
			System.out.println(ex.toString());
		}
		
		// TODO Check negative amount and associated test
		owner.setPointsAmount(owner.getPointsAmount() + finalPrice);
		buyer.setPointsAmount(buyer.getPointsAmount() - finalPrice);
		ArrayList<User> users = new ArrayList<>();
		users.add(owner);
		users.add(buyer);
		service.store(users);
	}

	public void debit(String buyerID, Integer finalPrice) {
		User buyer = service.getUserByMail(buyerID).get(0);
		// service.delete(buyer);
		if (buyer.getPointsAmount() == null)
			buyer.setPointsAmount(0);
		buyer.setPointsAmount(buyer.getPointsAmount() - finalPrice);
		service.store(buyer);
	}

	public void credit(String ownerID, Integer finalPrice) {
		User owner = service.getUserByMail(ownerID).get(0);
		// service.delete(owner);
		if (owner.getPointsAmount() == null)
			owner.setPointsAmount(0);
		owner.setPointsAmount(owner.getPointsAmount() + finalPrice);
		service.store(owner);
	}
}
