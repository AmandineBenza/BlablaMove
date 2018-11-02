package com.xaamruda.bbm.users.identification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xaamruda.bbm.users.dbaccess.service.IUserService;
import com.xaamruda.bbm.users.model.User;

@Component
public class UserIdentificator implements IUserIdentificator {

	@Autowired
	private IUserService service;

	public UserIdentificator(IUserService userService) {
		this.service = userService;
	}
	
	public UserIdentificator() {
		// 
	}

	@Override
	public boolean identify(String userMail, String userPassword) {
		User user = service.getUserByMail(userMail);
		userPassword = (userPassword == null) ? null : userPassword.trim();
		return user != null && user.getPassword().trim().equals(userPassword);
	}

}
