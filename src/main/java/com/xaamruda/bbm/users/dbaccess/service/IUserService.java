package com.xaamruda.bbm.users.dbaccess.service;

import java.util.List;

import com.xaamruda.bbm.users.model.User;

public interface IUserService {
	public List<User> getUserByMail(String mail);
	public void store(User user);
	public List<User> getAllUsers();
	public void delete(User user);
	void store(List<User> user);
}
