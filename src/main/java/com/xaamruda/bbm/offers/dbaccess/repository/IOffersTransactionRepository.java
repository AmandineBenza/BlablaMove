package com.xaamruda.bbm.offers.dbaccess.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.xaamruda.bbm.offers.model.OffersTransaction;
import com.xaamruda.bbm.offers.model.PostedOffer;

public interface IOffersTransactionRepository extends MongoRepository<OffersTransaction, Integer>//CrudRepository<ClientOffer, Integer> 
{
 
	List<OffersTransaction> getByOwnerID(String ownerID);

	List<OffersTransaction> getByTransactionID(String transactionID);
	
}
