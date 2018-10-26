package com.xaamruda.bbm.offers.dbaccess.services;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;

import com.xaamruda.bbm.offers.model.ClientOffer;
import com.xaamruda.bbm.offers.model.Offer;
import com.xaamruda.bbm.offers.model.OfferStatus;

//Import du pif
import ch.qos.logback.core.filter.Filter;

public interface IOfferService {
	List<Offer> getAllArchivedOffers();
	List<Offer> getOffersByOwnerID(int OwnerID);
	
	//???
	//List<ClientOffer> askOfferByPrice(Filter f);
	List<Offer> getAvailableOffers();
	List<Offer> getAvailableOffers(Query query); 
	List<ClientOffer> askOfferByPrice(Filter f);
	boolean changeOfferStatus(int id, OfferStatus status);
	boolean createNewOffer(String jsonOffer);
	
	
}
