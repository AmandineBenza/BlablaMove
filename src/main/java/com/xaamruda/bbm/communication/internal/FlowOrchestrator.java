package com.xaamruda.bbm.communication.internal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xaamruda.bbm.dbaccess.IDatabaseAccessor;

@Component
public class FlowOrchestrator implements IFlowOrchestrator {

	@Autowired
	private IDatabaseAccessor databaseAccessor;
	
	public FlowOrchestrator(){}
	
	@Override
	public IDatabaseAccessor getDatabaseAccessor(){
		return databaseAccessor;
	}
	
}
