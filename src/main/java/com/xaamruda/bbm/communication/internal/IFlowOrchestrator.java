package com.xaamruda.bbm.communication.internal;

import com.xaamruda.bbm.controller.loadUtils.UserCreationContainer;
import com.xaamruda.bbm.dbaccess.IDatabaseAccessor;
import com.xaamruda.bbm.users.model.User;

/**
 * Handles communication between modules.
 */
public interface IFlowOrchestrator {

	public IDatabaseAccessor getDatabaseAccessor();
	public User createUser(UserCreationContainer container);
	
}
