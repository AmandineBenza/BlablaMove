package com.xaamruda.bbm.offers.dbaccess.repository;

import org.springframework.data.repository.CrudRepository;

import  com.xaamruda.bbm.offers.model.ClientOffer;


public interface IOfferClientRepository extends CrudRepository<ClientOffer, Integer> {
 
}