package com.xaamruda.bbm.offers.dbaccess.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xaamruda.bbm.offers.model.PostedOffer;
import com.xaamruda.bbm.offers.model.OfferStatus;
import com.xaamruda.bbm.commons.logging.BBMLogger;
import com.xaamruda.bbm.offers.dbaccess.repository.IOfferRepository;


@Transactional
@Service("OfferService")
public class OfferService implements IOfferService {
	@Autowired
	IOfferRepository offerRepository;

/*	@Autowired
	MongoTemplate mongoTemplate; */

	@Override
	public List<PostedOffer> getOfferByID(String offerID) {
		BBMLogger.infoln("Retrieving offer of id " + offerID + ".");
		return offerRepository.getByOfferID(offerID);
	}
	
	@Override
	public List<PostedOffer> getAllArchivedOffers() {
		BBMLogger.infoln("Retrieving archived offers...");
		return offerRepository.getByStatus(OfferStatus.CLOSED);
	}

	@Override
	public List<PostedOffer> getOffersByOwnerID(String OwnerID) {
		BBMLogger.infoln("Retrieving offers which owner identifier is " + OwnerID + ".");
		return offerRepository.getByOwnerID(OwnerID);
	}
	
	@Override
	public List<PostedOffer> getAvailableOffers() {
		BBMLogger.infoln("Retrieving posted offers...");
		return offerRepository.getByStatus(OfferStatus.POSTED);
	}

/*	@Override
	public List<PostedOffer> getAvailableOffers(Query query) {
		List<PostedOffer> offers = mongoTemplate.find(query, PostedOffer.class);
		return offers;
	} */
	
	@Override
	public boolean createNewOffer(PostedOffer offer){
		BBMLogger.infoln("Storing new offer...");
		if(offer != null){
			offerRepository.save(offer);
		}
		return (offer != null);
	}
	
	@Override
	public boolean changeOfferStatus(int id, OfferStatus status) {
		BBMLogger.infoln("Changing offer identified by " + id + ".");
		Optional<PostedOffer> offer = offerRepository.findById(id);// .findOne(id);
		if (offer.isPresent()) {
			offer.get().setStatus(status);
			offerRepository.save(offer.get());
			return true;
		}
		return false;
	}

	@Override
	public void remove(PostedOffer offer) {
		BBMLogger.infoln("Removing offer...");
		offerRepository.delete(offer);
	}

}