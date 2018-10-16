package com.xaamruda.bbm.communication.internal;

import com.xaamruda.bbm.commons.users.UserCreationContainer;

/**
 * Handles communication between modules.
 */
public interface IFlowOrchestrator {

	public boolean createUser(UserCreationContainer container);
	
}
