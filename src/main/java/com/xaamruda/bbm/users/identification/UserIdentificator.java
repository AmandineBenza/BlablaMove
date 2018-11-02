package com.xaamruda.bbm.users.identification;

import com.xaamruda.bbm.users.dbaccess.service.IUserService;

public class UserIdentificator implements IUserIdentificator {

	private static IUserIdentificator instance;
	private IUserService service;
	
	private UserIdentificator(IUserService userService) {
		this.service = userService;
	}
	
	public static IUserIdentificator init(IUserService service){
		instance = new UserIdentificator(service);
		return instance;
	}

	public static IUserIdentificator getInstance() {
		return instance;
	}
	
	@Override
	// TODO see password ?
	public boolean identify(String userMail, String userPassword) {
		//return service.getUsersByMail(userMail).size() > 0;
		return (service.getUserByMail(userMail) != null);
	}

}
