package com.xaamruda.bbm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xaamruda.bbm.commons.logging.BBMLogger;
import com.xaamruda.bbm.communication.internal.FlowOrchestrationResult;
import com.xaamruda.bbm.communication.internal.IFlowOrchestrator;

@RestController
@RequestMapping("/BBM/")
@SuppressWarnings({"rawtypes", "unchecked"})
public class WebServiceController implements IWebServiceController {
	
	@Autowired
	private IFlowOrchestrator flowOrchestrator;
	
	public WebServiceController() {}
	
	
	/* Json format:
	 * 
	 * 	{
	 * 		"event" : "thing",
	 * 		"data" : { ... }
	 * 	}
	 */
	@Override
	@RequestMapping(value = "USERS/", method = RequestMethod.POST)
	public ResponseEntity usersEntryPoint(@RequestBody String jsonEvents) {
		BBMLogger.infoln("Listened new event on \"BBM/USERS\".");
		FlowOrchestrationResult result = flowOrchestrator.orchestrateUsersEntryPoint(jsonEvents);
		BBMLogger.infoln("Got response from the system !");
		BBMLogger.infoln("[Content:" + result.getContent() + "]");
		return new ResponseEntity(result.getContent(), result.getHttpStatus());
	}
	
	@Override
	@RequestMapping(value = "OFFERS/", method = RequestMethod.POST)
	public ResponseEntity offersEntryPoint(@RequestBody String jsonEvents) {
		BBMLogger.infoln("Listened new event on \"BBM/OFFERS\".");
		FlowOrchestrationResult result = flowOrchestrator.orchestrateOffersEntryPoint(jsonEvents);
		BBMLogger.infoln("Got response from the system !");
		BBMLogger.infoln("[Content:" + result.getContent() + "]");
		return new ResponseEntity(result.getContent(), result.getHttpStatus());
	}
}
