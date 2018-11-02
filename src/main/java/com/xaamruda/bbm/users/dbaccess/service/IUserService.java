package com.xaamruda.bbm.users.dbaccess.service;

import java.util.List;

import com.xaamruda.bbm.users.model.User;

public interface IUserService {

	public User getUserByMail(String mail);
	public void store(User user);
	public List<User> getAllUsers();
	
}
