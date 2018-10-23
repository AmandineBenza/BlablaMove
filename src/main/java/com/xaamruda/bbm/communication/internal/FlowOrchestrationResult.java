package com.xaamruda.bbm.communication.internal;

import org.springframework.http.HttpStatus;

@SuppressWarnings("rawtypes")
public class FlowOrchestrationResult {

	private HttpStatus httpStatus;
	private Object content;
	private Class contentType;
	
	public FlowOrchestrationResult(HttpStatus status, Object content, Class contentType){
		this.httpStatus = status;
		this.content = content;
		this.contentType = contentType;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	public Class getContentType() {
		return contentType;
	}

	public void setContentType(Class contentType) {
		this.contentType = contentType;
	}
	
}
