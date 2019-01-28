package com.xaamruda.bbm.users.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xaamruda.bbm.users.dbaccess.service.IUserService;
import com.xaamruda.bbm.users.model.User;

@Component
public class UserDataManager implements IUserDataManager {

	@Autowired
	private IUserService service;
	
	public UserDataManager(IUserService service){
		this.service = service;
	}
	
	public UserDataManager() {
		//
	}
	
	@Override
	public User storeNewUser(User user) {
		user.setPointsAmount(500);
		service.store(user);
		return user;
	}
	
}
