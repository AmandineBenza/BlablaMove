package com.xaamruda.bbm.users.info;

import com.xaamruda.bbm.commons.users.UserCreationContainer;
import com.xaamruda.bbm.users.model.User;

/**
 * Allows processing on user data. 
 */
public interface IUserDataManager {

	public User storeNewUser(UserCreationContainer container);
	
}
