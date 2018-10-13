package com.xaamruda.bbm.dbaccess;

import java.util.List;

import com.xaamruda.bbm.users.model.User;

public interface IDatabaseAccessor {

	public List<User> getUsersByMail(String mail);
	
	public List<User> getHardcodedUsersByMail(String mail);
	
}
