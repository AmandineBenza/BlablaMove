package com.xaamruda.bbm.users.dbaccess.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xaamruda.bbm.commons.logging.BBMLogger;
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
	public List<User> getUserByMail(String mail) {
		// infoln("Retrieving user given mail " + mail + ".");
		return repository.findByMail(mail);
	}
	
	@Override
	public synchronized void store(User user) {
		repository.save(user);
	}

	@Override
	public synchronized void store(List<User> users) {
		repository.saveAll(users);
	}

	@Override
	public List<User> getAllUsers() {
		// BBMLogger.infoln("Retrieving all users..");
		return repository.findAll();
	}

	@Override
	public synchronized void delete(User user) {
		BBMLogger.infoln("Deleting user " + user.getMail() + ".");
		repository.delete(user);
	}

}
