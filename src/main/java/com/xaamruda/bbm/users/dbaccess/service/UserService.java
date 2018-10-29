package com.xaamruda.bbm.users.dbaccess.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xaamruda.bbm.users.model.User;

@Transactional
@Service("UserService")
public class UserService implements IUserService {

	// @Autowired
	// private IUserRepository repository;
	
	public UserService() {}
	
	@Override
	public List<User> getUsersByMail(String mail) {
		// return repository.findByMail(mail);
		return null;
	}

	@Override
	public void store(User user) {
		// srepository.save(user);
	}

}
