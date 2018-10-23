package com.xaamruda.bbm.offers.dbaccess.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

import  com.xaamruda.bbm.offers.model.Offer;
import com.xaamruda.bbm.offers.model.OfferStatus;


public interface IOfferRepository extends MongoRepository<Offer, Integer>//CrudRepository<Offer, Integer> 
{

	List<Offer> getByStatus(OfferStatus status);

	List<Offer> getByOwnerID(int ownerID);
 
}