package com.xaamruda.bbm.users.identification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xaamruda.bbm.users.dbaccess.service.IUserService;
import com.xaamruda.bbm.users.model.User;

@Component
public class UserIdentificator implements IUserIdentificator {
	
	private IUserService service;

	@Autowired
	public UserIdentificator(IUserService userService) {
		this.service = userService;
	}
	
	@Override
	public boolean identify(String userMail, String userPassword) {
		User user = service.getUserByMail(userMail).get(0);
		userPassword = (userPassword == null) ? null : userPassword.trim();
		return user != null && user.getPassword().trim().equals(userPassword);
	}

}
