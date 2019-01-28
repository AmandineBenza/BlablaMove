package com.xaamruda.bbm.users.data;

import com.xaamruda.bbm.users.model.User;

/**
 * Allows processing on user data. 
 */
public interface IUserDataManager {

	public User storeNewUser(User user);
	
}
