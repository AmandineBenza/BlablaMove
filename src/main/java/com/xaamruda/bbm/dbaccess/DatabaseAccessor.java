package com.xaamruda.bbm.dbaccess;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xaamruda.bbm.dbaccess.users.service.IUserService;
import com.xaamruda.bbm.users.model.User;

@Component
public class DatabaseAccessor implements IDatabaseAccessor {

	@Autowired
	private IUserService userDatabaseAccessor;
	
	public DatabaseAccessor() {}
	
	@Override
	public List<User> getUsersByMail(String mail) {
		return userDatabaseAccessor.getUsersByMail(mail);
	}

	// TODO TMP
	@Override
	public List<User> getHardcodedUsersByMail(String mail) {
		List<User> hardcodedUsers = new ArrayList<>();
		hardcodedUsers.add(new User(0666666666, 100, "Av3Hell5", mail,"45FD7643565654TTY"));
		return hardcodedUsers;
	}

}
