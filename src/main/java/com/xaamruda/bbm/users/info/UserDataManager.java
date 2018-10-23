package com.xaamruda.bbm.users.info;

import com.xaamruda.bbm.users.dbaccess.service.IUserService;
import com.xaamruda.bbm.users.model.User;
import com.xaamruda.bbm.users.model.UserCreationContainer;

public class UserDataManager implements IUserDataManager {

	private static IUserDataManager userDataManager;
	private IUserService service;
	
	private UserDataManager(IUserService service){
		this.service = service;
	}
	
	public static IUserDataManager init(IUserService service){
		userDataManager = new UserDataManager(service);
		return userDataManager;
	}
	
	public static IUserDataManager getInstance(){
		return userDataManager;
	}

	@Override
	public User storeNewUser(UserCreationContainer container) {
		User user = new User(container);
		service.store(user);
		return user;
	}
	
}
