package com.xaamruda.bbm.offers.dbaccess.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import  com.xaamruda.bbm.offers.model.PostedOffer;
import com.xaamruda.bbm.offers.model.OfferStatus;

public interface IOfferRepository extends CrudRepository<PostedOffer, Integer>{

	List<PostedOffer> getByStatus(OfferStatus status);

	List<PostedOffer> getByOwnerID(String ownerID);
	
	List<PostedOffer> getByOfferID(String offerID);
 
}