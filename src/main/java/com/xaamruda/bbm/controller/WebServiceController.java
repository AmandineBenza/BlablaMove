package com.xaamruda.bbm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xaamruda.bbm.commons.users.UserCreationContainer;
import com.xaamruda.bbm.communication.internal.FlowOrchestrator;
import com.xaamruda.bbm.communication.internal.IFlowOrchestrator;

@RestController
@RequestMapping("/BBM/")
@SuppressWarnings({"rawtypes", "unchecked"})
public class WebServiceController implements IWebServiceController {
	
	@Autowired
	private IFlowOrchestrator flowOrchestrator;
		
	public WebServiceController() {
		flowOrchestrator = new FlowOrchestrator();
	}
	
	@Override
	@RequestMapping(value = "USER/CREATE", method = RequestMethod.POST)
	public ResponseEntity createUser(@RequestBody UserCreationContainer userInformation) {
		boolean created = flowOrchestrator.createUser(userInformation);
		
		if(!created)
			return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
		
		return new ResponseEntity("User successfully created.", HttpStatus.OK);
	}


	@Override
	@RequestMapping(value = "USER/IDENTIFY", method = RequestMethod.POST)
	public ResponseEntity identifyUser(@RequestBody String userInformation) {
		return null;
	}


	@Override
	public ResponseEntity getAllOffers() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ResponseEntity getOfferByPath() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ResponseEntity getOfferByNumberOfPoint() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ResponseEntity getOfferByDate() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ResponseEntity confirmTransaction() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ResponseEntity cancelTransaction() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ResponseEntity confirmOffer(String offerInformation) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ResponseEntity makeOffer(String offerInformation) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ResponseEntity modifyOffer(String offerInformation) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ResponseEntity deleteOffer(String offerInformation) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ResponseEntity selectOffer(String offerInformation) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
