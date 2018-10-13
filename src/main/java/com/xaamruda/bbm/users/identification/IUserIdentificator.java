package com.xaamruda.bbm.users.identification;

import com.xaamruda.bbm.controller.loadUtils.UserCreationContainer;

/**
 * Allows user identification.
 */
public interface IUserIdentificator {

	public boolean createUser(UserCreationContainer user);
	
}
