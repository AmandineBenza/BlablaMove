package com.xaamruda.bbm.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xaamruda.bbm.commons.json.JsonUtils;
import com.xaamruda.bbm.commons.logging.BBMLogger;
import com.xaamruda.bbm.users.dbaccess.service.IUserService;
import com.xaamruda.bbm.users.identification.IUserIdentificator;
import com.xaamruda.bbm.users.info.IUserDataManager;
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
	
	// TODO
	public boolean identifyUserByMailPlusPassword(String wholeUserData, String userMail, String userPassword){
		boolean exists = identificator.identify(userMail, userPassword);
		
		if(!exists){
			dataManager.storeNewUser(JsonUtils.getFromJson(wholeUserData, User.class));
		}
		
		return exists;
	}
	
	public void postNewUser(String userJson){
		BBMLogger.infoln("Creating user...");
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
		User user = service.getUserByMail(mail);
		return user;
	}
	public boolean sendMail(String mail, int price, String from ) {
		MailSender.sendEmail(mail);
		return true;
	}

	public void debit(String buyerID, Integer finalPrice) {
		User buyer = service.getUserByMail(buyerID);
//		service.delete(buyer);
		buyer.setPointsAmount(buyer.getPointsAmount() + finalPrice);
//		service.store(buyer);
		
	}

	public void credit(String ownerID, Integer finalPrice) {
		User owner = service.getUserByMail(ownerID);
//		service.delete(owner);
//		owner.setPointsAmount(owner.getPointsAmount() + finalPrice);
//		service.store(owner);
	}
	
	
}
