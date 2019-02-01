package com.xaamruda.bbm.users.dbaccess.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xaamruda.bbm.commons.logging.BBMLogger;
import com.xaamruda.bbm.integrity.IntegrityIOHandler;
import com.xaamruda.bbm.users.dbaccess.repository.IUserRepository;
import com.xaamruda.bbm.users.model.User;

@Transactional
@Service("UserService")
public class UserService implements IUserService {

	@Autowired
	private IUserRepository repository;

	@Autowired
	private IntegrityIOHandler integrityIOHandler;

	public UserService() {
	}

	@Override
	public List<User> getUserByMail(String mail) {
		BBMLogger.infoln("Retrieving user given mail " + mail + ".");
		return repository.findByMail(mail);
	}
	
	@Override
	public void store(User user) {
		long journalId = integrityIOHandler.addUserJournalEntry("store", this.getClass().getSimpleName(), user);
		repository.save(user);
		integrityIOHandler.endUserJournalEntry(journalId);
	}

	@Override
	public void store(List<User> users) {
		long journalId = integrityIOHandler.addUserJournalEntry("store", this.getClass().getSimpleName(), users);
		repository.saveAll(users);
		integrityIOHandler.endUserJournalEntry(journalId);
	}

	@Override
	public List<User> getAllUsers() {
		BBMLogger.infoln("Retrieving all users..");
		return repository.findAll();
	}

	@Override
	public void delete(User user) {
		BBMLogger.infoln("Deleting user " + user.getMail() + ".");
		long journalId = integrityIOHandler.addUserJournalEntry("store", this.getClass().getSimpleName(), user);
		repository.delete(user);
		integrityIOHandler.endUserJournalEntry(journalId);
	}

}
