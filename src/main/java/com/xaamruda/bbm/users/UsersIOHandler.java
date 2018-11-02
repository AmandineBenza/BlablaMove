package com.xaamruda.bbm.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xaamruda.bbm.commons.json.JsonUtils;
import com.xaamruda.bbm.commons.logging.BBMLogger;
import com.xaamruda.bbm.users.dbaccess.service.IUserService;
import com.xaamruda.bbm.users.identification.IUserIdentificator;
import com.xaamruda.bbm.users.info.IUserDataManager;
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
		List<User> users = service.getAllUsers();
		return users;
	}
	
	public User retrieveUser(String mail) {
		User user = service.getUserByMail(mail);
		return user;
	}
	
}
