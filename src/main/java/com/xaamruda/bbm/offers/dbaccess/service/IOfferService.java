package com.xaamruda.bbm.offers.dbaccess.service;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.xaamruda.bbm.offers.model.PostedOffer;
import com.xaamruda.bbm.offers.model.OfferStatus;

public interface IOfferService {

	boolean saveOffer(PostedOffer offer);

	public List<PostedOffer> getAllArchivedOffers();

	public List<PostedOffer> getAvailableOffers();

	public boolean changeOfferStatus(int id, OfferStatus status);

	List<PostedOffer> getOfferByID(String offerID);

	void removeOffer(PostedOffer offer);

	List<PostedOffer> getOffersByOwnerID(String OwnerID);

	List<PostedOffer> getAvailableOffers(Specification<PostedOffer> query);

	public	void shutDown();

	}
