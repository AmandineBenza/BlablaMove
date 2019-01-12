package com.xaamruda.bbm.offers.dbaccess.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xaamruda.bbm.offers.model.OffersTransaction;

public interface IOffersTransactionRepository extends JpaRepository<OffersTransaction, Integer>{

	List<OffersTransaction> getByOwnerID(String ownerID);

	List<OffersTransaction> getByTransactionID(Integer transactionID);

}
