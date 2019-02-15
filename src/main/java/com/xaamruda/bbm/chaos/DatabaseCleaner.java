package com.xaamruda.bbm.chaos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xaamruda.bbm.commons.logging.BBMLogger;
import com.xaamruda.bbm.integrity.ddos.DDOSMetadata;
import com.xaamruda.bbm.integrity.ddos.dbaccess.AuthorizationService;
import com.xaamruda.bbm.offers.dbaccess.service.OfferService;
import com.xaamruda.bbm.offers.dbaccess.service.OfferTransactionService;
import com.xaamruda.bbm.offers.model.OffersTransaction;
import com.xaamruda.bbm.offers.model.PostedOffer;
import com.xaamruda.bbm.users.dbaccess.service.UserService;
import com.xaamruda.bbm.users.model.User;

@Component
public class DatabaseCleaner {

	@Autowired
	private UserService userService;
	
	@Autowired
	private OfferService offerService;
	
	@Autowired
	private OfferTransactionService offerTransactionService;
	
	@Autowired
	private AuthorizationService authorizationService;
	
	public void cleanDatabase() {
		BBMLogger.infoln("Cleaning database...");
		
		try {
			List<User> users = userService.getAllUsers();
			users.forEach(user -> userService.delete(user));
			BBMLogger.infoln("User table cleaned.");
			
			List<PostedOffer> offers = offerService.getAllOffers();
			offers.forEach(offer -> offerService.delete(offer));
			BBMLogger.infoln("Offer table cleaned.");
			
			List<OffersTransaction> transactions = offerTransactionService.getAll();
			transactions.forEach(transaction -> offerTransactionService.remove(transaction));
			BBMLogger.infoln("Transaction table cleaned.");
			
			List<DDOSMetadata> metadatas = authorizationService.getAll();
			metadatas.forEach(metada -> authorizationService.delete(metada));
			BBMLogger.infoln("Metadatas table cleaned.");
		} catch(Exception e) {
			BBMLogger.debugln("Error occured while cleaning database.");
		}
	}

}