package com.xaamruda.bbm.commons.exceptions;

import org.springframework.http.HttpStatus;

public abstract class BlablaMoveException extends Exception {

	private static final long serialVersionUID = -2465375959132272733L;
	
	protected String mainMessage;
	protected String[] additionalMessages;
	protected HttpStatus relatedHttpStatus;
	
	public BlablaMoveException(HttpStatus status) {
		super();
		this.relatedHttpStatus = status;
	}
	
	public BlablaMoveException(HttpStatus status, String msg, String... additionalMessages) {
		super(ExceptionUtils.concatExceptionMessages(msg, additionalMessages));
		this.mainMessage = msg;
		this.additionalMessages = additionalMessages;
		this.relatedHttpStatus = status;
	}
	
	public String getMainMessage() {
		return mainMessage;
	}
	
	public String[] getAdditionalMessages() {
		return additionalMessages;
	}
	
	public String getWholeMessage() {
		return ExceptionUtils.concatExceptionMessages(mainMessage, additionalMessages);
	}
	
	public HttpStatus getRelatedHttpStatus() {
		return relatedHttpStatus;
	}
	
}
