package com.xaamruda.bbm.offers.dbaccess.services;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;

import com.xaamruda.bbm.offers.model.PostedOffer;
import com.xaamruda.bbm.offers.billing.calculator.Utils;
import com.xaamruda.bbm.offers.model.OfferStatus;


public interface IOfferService {
	
	
	Utils checkPrice(List<PostedOffer> offers, int distance);
	
	boolean createNewOffer(PostedOffer offer);
	
	public List<PostedOffer> getAllArchivedOffers();

	public List<PostedOffer> getAvailableOffers();

	public List<PostedOffer> getAvailableOffers(Query query);

	public boolean changeOfferStatus(int id, OfferStatus status);

	List<PostedOffer> getOfferByID(String offerID);

	void remove(PostedOffer offer);

	List<PostedOffer> getOffersByOwnerID(String OwnerID);
	
}
