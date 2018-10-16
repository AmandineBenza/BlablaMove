package com.xaamruda.bbm.users.identification;

import com.xaamruda.bbm.commons.users.UserCreationContainer;
import com.xaamruda.bbm.users.model.User;

/**
 * Allows user identification.
 */
public interface IUserIdentificator {

	public User identify(UserCreationContainer user);
	
}
