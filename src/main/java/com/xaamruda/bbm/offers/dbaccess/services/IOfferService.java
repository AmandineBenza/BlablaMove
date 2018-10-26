package com.xaamruda.bbm.offers.dbaccess.services;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;

import com.xaamruda.bbm.offers.model.PostedOffer;
import com.xaamruda.bbm.offers.model.OfferStatus;

//Import du pif
import ch.qos.logback.core.filter.Filter;

public interface IOfferService {
	List<PostedOffer> getAllArchivedOffers();
	List<PostedOffer> getOffersByOwnerID(int OwnerID);
	
	//???
	//List<ClientOffer> askOfferByPrice(Filter f);
	List<PostedOffer> getAvailableOffers();
	List<PostedOffer> getAvailableOffers(Query query); 
	boolean changeOfferStatus(int id, OfferStatus status);
	boolean createNewOffer(String jsonOffer);
	
	
}
