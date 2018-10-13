package com.xaamruda.bbm.controller;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xaamruda.bbm.communication.internal.FlowOrchestrator;
import com.xaamruda.bbm.communication.internal.IFlowOrchestrator;
import com.xaamruda.bbm.controller.loadUtils.UserCreationContainer;

@RestController
@RequestMapping("/BBM/")
@SuppressWarnings({ "rawtypes", "unchecked"})
public class WebServiceController implements IWebServiceController {
	
	@Autowired
	private IFlowOrchestrator flowOrchestrator;
		
	public WebServiceController() {
		flowOrchestrator = new FlowOrchestrator();
	}
	
	@Override
	@RequestMapping(value = "USER/CREATE", method = RequestMethod.POST)
	public ResponseEntity createUser(@RequestBody UserCreationContainer userInformation) {
		flowOrchestrator.createUser(userInformation);
		return null;
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
