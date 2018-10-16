package com.xaamruda.bbm.users.dbaccess.service;

import java.util.List;

import com.xaamruda.bbm.users.model.User;

public interface IUserService {

	public List<User> getUsersByMail(String mail);

	public void store(User user);
	
}
