package com.xaamruda.bbm.communication.internal;

import com.xaamruda.bbm.controller.loadUtils.UserCreationContainer;
import com.xaamruda.bbm.dbaccess.IDatabaseAccessor;

/**
 * Handles communication between modules.
 */
public interface IFlowOrchestrator {

	public IDatabaseAccessor getDatabaseAccessor();
	public void createUser(UserCreationContainer container);
	
}
