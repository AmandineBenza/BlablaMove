package com.xaamruda.bbm.offers.dbaccess.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import  com.xaamruda.bbm.offers.model.PostedOffer;
import com.xaamruda.bbm.offers.model.OfferStatus;

public interface IOfferRepository extends JpaRepository<PostedOffer, Integer>, JpaSpecificationExecutor<PostedOffer> {

	List<PostedOffer> getByStatus(OfferStatus status);

	List<PostedOffer> getByOwnerID(String ownerID);
	
	List<PostedOffer> getByOfferID(Integer offerID);
	@Query(nativeQuery=true,value="SHUTDOWN")
	void shutDown();
}