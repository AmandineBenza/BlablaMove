package com.xaamruda.bbm.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xaamruda.bbm.commons.users.UserCreationContainer;
import com.xaamruda.bbm.users.dbaccess.service.IUserService;
import com.xaamruda.bbm.users.identification.IUserIdentificator;
import com.xaamruda.bbm.users.identification.UserIdentificator;
import com.xaamruda.bbm.users.info.IUserDataManager;
import com.xaamruda.bbm.users.info.UserDataManager;
import com.xaamruda.bbm.users.model.User;

/**
 * Entry point to users module. 
 */
@Component
public class UserIOHandler {
	
	@Autowired
	private IUserService service;
	
	@Autowired
	public UserIOHandler() {
		UserIdentificator.init(service);
		UserDataManager.init(service);
	}
	
	public boolean callUserCreation(UserCreationContainer container) {
		IUserIdentificator identificator = UserIdentificator.getInstance();
		IUserDataManager dataManager = UserDataManager.getInstance();
		
		User user = identificator.identify(container);
		
		if(user == null){
			user = dataManager.storeNewUser(container);
		}
		
		return user != null;
	}
}
