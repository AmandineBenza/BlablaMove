package com.xaamruda.bbm.users.identification;

import com.xaamruda.bbm.controller.loadUtils.UserCreationContainer;

public class UserIdentificator implements IUserIdentificator {

	private final static IUserIdentificator instance = new UserIdentificator();
	
	private UserIdentificator() {
		
	}

	public static IUserIdentificator getInstance() {
		return instance;
	}
	
	@Override
	public boolean identify(UserCreationContainer user) {
		
		return false;
	}

}
