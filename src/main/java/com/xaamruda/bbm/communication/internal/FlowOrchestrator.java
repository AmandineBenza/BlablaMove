package com.xaamruda.bbm.communication.internal;

import javax.print.attribute.standard.Media;

import org.mockito.cglib.core.ClassEmitter;
import org.mockito.internal.matchers.LessOrEqual;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xaamruda.bbm.controller.loadUtils.UserCreationContainer;
import com.xaamruda.bbm.dbaccess.IDatabaseAccessor;
import com.xaamruda.bbm.users.UserIOHandler;
import com.xaamruda.bbm.users.model.User;

@Component
public class FlowOrchestrator implements IFlowOrchestrator {

	@Autowired
	private IDatabaseAccessor databaseAccessor;
	
	@Autowired
	private UserIOHandler userIOHandler;
	
	@Autowired
	public FlowOrchestrator(){}
	
	@Override
	public IDatabaseAccessor getDatabaseAccessor(){
		return databaseAccessor;
	}

	@Override
	public User createUser(UserCreationContainer container) {
		// TODO TODO
		// User user = userIOHandler.callUserCreation(container);
		// userIOHandler.store(user);
		
		// databaseAccessor.
		// userIOHandler().store(container)...
		
		return null;
	}
	
}
