package com.xaamruda.bbm.controller;

import org.springframework.http.ResponseEntity;

import com.xaamruda.bbm.commons.users.UserCreationContainer;

@SuppressWarnings({ "rawtypes"})
public interface IWebServiceController {
	
	// >>>>> GET <<<<<<
	public ResponseEntity getAllOffers();
	public ResponseEntity getOfferByPath(); // Todo : Add parameters
	public ResponseEntity getOfferByNumberOfPoint(); // Todo : Add parameters
	public ResponseEntity getOfferByDate(); // Todo : Add parameters
	
	// >>>>> POST <<<<<<
	
	//Shared
	public ResponseEntity createUser(UserCreationContainer userInformation);	
	public ResponseEntity identifyUser(String userInformation);
	public ResponseEntity confirmTransaction(); // Todo : Add parameters
	public ResponseEntity cancelTransaction(); // Todo : Add parameters
	public ResponseEntity confirmOffer(String offerInformation);
	
	//Alice
	public ResponseEntity makeOffer(String offerInformation); 
	public ResponseEntity modifyOffer(String offerInformation); 
	public ResponseEntity deleteOffer(String offerInformation); 

	//Bob
	public ResponseEntity selectOffer(String offerInformation);
	
}

