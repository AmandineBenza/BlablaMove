package com.xaamruda.bbm.commons.exceptions;

import org.springframework.http.HttpStatus;

public class DatabaseException extends BlablaMoveException {

	private final static HttpStatus HTTP_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;
	
	private static final long serialVersionUID = -7890465337034112372L;

	public DatabaseException() {
		super(HTTP_STATUS);
	}

	public DatabaseException(String msg, String... additionalMessages) {
		super(HTTP_STATUS, msg, additionalMessages);
	}
	
}
