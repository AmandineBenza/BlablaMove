package com.xaamruda.bbm.offers.dbaccess.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xaamruda.bbm.offers.model.ClientOffer;
import com.xaamruda.bbm.offers.model.Offer;
import com.xaamruda.bbm.offers.model.OfferStatus;
import com.xaamruda.bbm.commons.json.JsonUtils;
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
	public List<Offer> getAllArchivedOffers() {
		return offerRepository.getByStatus(OfferStatus.CLOSED);

	}

	@Override
	public List<Offer> getOffersByOwnerID(int OwnerID) {
		return offerRepository.getByOwnerID(OwnerID);
	}

	// TODO speak about
	@Override
	public List<ClientOffer> askOfferByPrice(Filter f) {
		return null;
	}

	@Override
	public List<Offer> getAvailableOffers() {
		return offerRepository.getByStatus(OfferStatus.POSTED);
	}

	@Override
	public List<Offer> getAvailableOffers(Query query) {
		List<Offer> offers = mongoTemplate.find(query, Offer.class);
		return offers;
	}

	// TODO
	@Override
	public boolean createNewOffer(String jsonOffer) {
		Offer offer = JsonUtils.getFromJson(jsonOffer, Offer.class);
		if (offer != null) {
			offerRepository.insert(offer);
		}
		return (offer != null);
	}

	@Override
	public boolean changeOfferStatus(int id, OfferStatus status) {
		Optional<Offer> offer = offerRepository.findById(id);// .findOne(id);
		if (offer.isPresent()) {
			offer.get().setStatus(status);
			offerRepository.save(offer.get());
			return true;
		}
		return false;
	}
}