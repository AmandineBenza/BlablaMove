package com.xaamruda.bbm.offers.dbaccess.services;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;

import com.xaamruda.bbm.offers.model.PostedOffer;
import com.xaamruda.bbm.offers.model.OfferStatus;

public interface IOfferService {
	public List<PostedOffer> getAllArchivedOffers();

	public List<PostedOffer> getOffersByOwnerID(int OwnerID);

	public List<PostedOffer> getAvailableOffers();

	public List<PostedOffer> getAvailableOffers(Query query);

	public boolean changeOfferStatus(int id, OfferStatus status);

	public boolean createNewOffer(String jsonOffer);

}
