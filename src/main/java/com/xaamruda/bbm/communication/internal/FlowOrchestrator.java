package com.xaamruda.bbm.communication.internal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xaamruda.bbm.commons.users.UserCreationContainer;
import com.xaamruda.bbm.users.UserIOHandler;

@Component
public class FlowOrchestrator implements IFlowOrchestrator {

	@Autowired
	private UserIOHandler userIOHandler;
	
	@Autowired
	public FlowOrchestrator(){}
	
	@Override
	public boolean createUser(UserCreationContainer container) {
		return userIOHandler.callUserCreation(container);
	}
	
}
