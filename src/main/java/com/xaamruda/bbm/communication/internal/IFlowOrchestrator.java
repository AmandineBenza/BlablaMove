package com.xaamruda.bbm.communication.internal;


/**
 * Handles communication between modules.
 */
public interface IFlowOrchestrator {

	public FlowOrchestrationResult orchestrateUsersEntryPoint(String jsonEvents);
	public FlowOrchestrationResult orchestrateOffersEntryPoint(String jsonEvents);
	
//	// >>>>> GET <<<<<<
//	public ResponseEntity getAllOffers();
//	public ResponseEntity getOfferByPath(); // Todo : Add parameters
//	public ResponseEntity getOfferByNumberOfPoint(); // Todo : Add parameters
//	public ResponseEntity getOfferByDate(); // Todo : Add parameters
//	
//	// >>>>> POST <<<<<<
//	
//	//Shared
//	public ResponseEntity identifyUser(String userInformation);
//	public ResponseEntity confirmTransaction(); // Todo : Add parameters
//	public ResponseEntity cancelTransaction(); // Todo : Add parameters
//	public ResponseEntity confirmOffer(String offerInformation);
//	
//	//Alice
//	public ResponseEntity modifyOffer(String offerInformation); 
//	public ResponseEntity deleteOffer(String offerInformation); 
//
//	//Bob
//	public ResponseEntity selectOffer(String offerInformation);
	
}
