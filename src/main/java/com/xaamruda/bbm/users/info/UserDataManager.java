package com.xaamruda.bbm.users.info;

import com.xaamruda.bbm.users.dbaccess.service.IUserService;
import com.xaamruda.bbm.users.model.User;

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
	public User storeNewUser(User user) {
		service.store(user);
		return user;
	}
	
}
