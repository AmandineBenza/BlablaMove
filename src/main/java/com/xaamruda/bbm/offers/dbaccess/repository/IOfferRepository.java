package com.xaamruda.bbm.offers.dbaccess.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import  com.xaamruda.bbm.offers.model.PostedOffer;
import com.xaamruda.bbm.offers.model.OfferStatus;

public interface IOfferRepository extends MongoRepository<PostedOffer, Integer>{

	List<PostedOffer> getByStatus(OfferStatus status);

	List<PostedOffer> getByOwnerID(String ownerID);
	
	List<PostedOffer> getByOfferID(String offerID);
 
}