package com.xaamruda.bbm.offers.dbaccess.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.xaamruda.bbm.offers.model.OffersTransaction;
import com.xaamruda.bbm.offers.model.PostedOffer;

public interface IOffersTransactionRepository extends JpaRepository<OffersTransaction, Integer>//CrudRepository<ClientOffer, Integer> 
{
 
	List<OffersTransaction> getByOwnerID(String ownerID);

	List<OffersTransaction> getByTransactionID(String transactionID);
	
}
