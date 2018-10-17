package com.xaamruda.bbm.offers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.xaamruda.bbm.offers.dbaccess.services.OfferService;
import com.xaamruda.bbm.offers.model.Offer;
public class IOHandler {
	@Autowired
	OfferService offerService;
	
	List<Offer> getOffers(){
		return  offerService.getAvailableOffers();
	}
	
	List<Offer> postNewOffer(){
		return  offerService.createNewOffer();
	}
}
