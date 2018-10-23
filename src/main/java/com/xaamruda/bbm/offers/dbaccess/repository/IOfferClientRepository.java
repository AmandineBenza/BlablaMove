package com.xaamruda.bbm.offers.dbaccess.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

import  com.xaamruda.bbm.offers.model.ClientOffer;


public interface IOfferClientRepository extends MongoRepository<ClientOffer, Integer>//CrudRepository<ClientOffer, Integer> 
{
 
}