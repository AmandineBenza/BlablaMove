package com.xaamruda.bbm.users.info;

import com.xaamruda.bbm.users.model.User;
import com.xaamruda.bbm.users.model.UserCreationContainer;

/**
 * Allows processing on user data. 
 */
public interface IUserDataManager {

	public User storeNewUser(UserCreationContainer container);
	
}
