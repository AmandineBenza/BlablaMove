package com.xaamruda.bbm.users.identification;

import com.xaamruda.bbm.users.dbaccess.service.IUserService;
import com.xaamruda.bbm.users.model.User;

public class UserIdentificator implements IUserIdentificator {

	private static IUserIdentificator instance;
	private IUserService service;

	private UserIdentificator(IUserService userService) {
		this.service = userService;
	}

	public static IUserIdentificator init(IUserService service) {
		instance = new UserIdentificator(service);
		return instance;
	}

	public static IUserIdentificator getInstance() {
		return instance;
	}

	@Override
	public boolean identify(String userMail, String userPassword) {
		User user = service.getUserByMail(userMail);
		userPassword = (userPassword == null) ? null : userPassword.trim();
		return user != null && user.getPassword().trim().equals(userPassword);
	}

}
