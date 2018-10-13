package com.xaamruda.bbm.communication.internal;

import com.xaamruda.bbm.dbaccess.IDatabaseAccessor;

/**
 * Handles communication between modules.
 */
public interface IFlowOrchestrator {

	public IDatabaseAccessor getDatabaseAccessor();
	
}
