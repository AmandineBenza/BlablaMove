package com.xaamruda.bbm.users.dbaccess.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xaamruda.bbm.users.dbaccess.repository.IUserRepository;
import com.xaamruda.bbm.users.model.User;

@Transactional
@Service("UserService")
public class UserService implements IUserService {

	@Autowired
	private IUserRepository repository;

	public UserService() {
	}

	@Override
	public User getUserByMail(String mail) {
		return repository.findByMail(mail);
	}

	@Override
	public void store(User user) {
		repository.save(user);
	}

	@Override
	public List<User> getAllUsers() {
		return repository.findAll();
	}

}
