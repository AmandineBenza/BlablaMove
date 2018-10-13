package com.xaamruda.bbm.dbaccess.users.service;

import java.util.List;

import com.xaamruda.bbm.users.model.User;

public interface IUserService {

	public List<User> getUsersByMail(String mail);
	
}
