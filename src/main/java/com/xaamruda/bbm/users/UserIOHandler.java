package com.xaamruda.bbm.users;

import com.xaamruda.bbm.users.identification.IUserIdentificator;
import com.xaamruda.bbm.users.identification.UserIdentificator;
import com.xaamruda.bbm.users.info.IUserDataManager;
import com.xaamruda.bbm.users.mailing.IMailSender;
import com.xaamruda.bbm.users.model.User;

/**
 * Entry point to users module. 
 */
public class UserIOHandler {
	
	public UserIOHandler() {
		
	}
	
	public User callUserCreation() {
		IUserIdentificator id = UserIdentificator.getInstance();
		return null;
	}
}
