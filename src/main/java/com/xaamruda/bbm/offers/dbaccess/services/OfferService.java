package com.xaamruda.bbm.offers.dbaccess.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xaamruda.bbm.offers.model.PostedOffer;
import com.xaamruda.bbm.offers.search.engine.QueryEngine;
import com.xaamruda.bbm.offers.model.OfferStatus;
import com.xaamruda.bbm.commons.json.JsonUtils;
import com.xaamruda.bbm.offers.billing.calculator.MediumCalculator;
import com.xaamruda.bbm.offers.billing.calculator.Utils;
import com.xaamruda.bbm.offers.dbaccess.repository.IOfferRepository;

import ch.qos.logback.core.filter.Filter;

@Transactional
@Service("OfferService")
public class OfferService implements IOfferService {
	@Autowired
	IOfferRepository offerRepository;

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public List<PostedOffer> getAllArchivedOffers() {
		return offerRepository.getByStatus(OfferStatus.CLOSED);

	}

	@Override
	public List<PostedOffer> getOffersByOwnerID(int OwnerID) {
		return offerRepository.getByOwnerID(OwnerID);
	}

	
	@Override
	public List<PostedOffer> getAvailableOffers() {
		return offerRepository.getByStatus(OfferStatus.POSTED);
	}

	@Override
	public List<PostedOffer> getAvailableOffers(Query query) {
		List<PostedOffer> offers = mongoTemplate.find(query, PostedOffer.class);
		return offers;
	}

	
	@Override
	public boolean createNewOffer(PostedOffer offer){
		
		if(offer != null){
			offerRepository.insert(offer);
		}
		return (offer != null);
	}

	@Override
	public Utils checkPrice(List<PostedOffer> offers,int distance){
		return MediumCalculator.getInstance().compute(offers, distance);
	}
	
	@Override
	public boolean changeOfferStatus(int id, OfferStatus status) {
		Optional<PostedOffer> offer = offerRepository.findById(id);// .findOne(id);
		if (offer.isPresent()) {
			offer.get().setStatus(status);
			offerRepository.save(offer.get());
			return true;
		}
		return false;
	}
}