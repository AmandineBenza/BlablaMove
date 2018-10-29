package com.xaamruda.bbm.users.identification;

/**
 * Allows user identification.
 */
public interface IUserIdentificator {

	public boolean identify(String userMail, String userPassword);
	
}
