package com.xaamruda.bbm.controller;

import org.springframework.http.ResponseEntity;

@SuppressWarnings({ "rawtypes"})
public interface IWebServiceController {
	
	// GET
	public ResponseEntity getAllOffers();
	public ResponseEntity getAveragePriceByPath(); //TODO : A path in argument
	public ResponseEntity getPointsByUser(); // TODO : A user in argument 
	public ResponseEntity getPathByOffer(); // // TODO : A offer in argument 
		
	//POST
	public ResponseEntity identifyUser(); // TODO : User mail/id(?) and password 	
	//public ResponseEntity makeOffer(); 
	
	// New offer
	// Delete offer
	// Modify User Info ?
	// Make research ? 
	// Modify user points 

}

