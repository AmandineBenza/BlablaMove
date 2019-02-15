package com.xaamruda.bbm.offers.dbaccess.service;

import java.util.List;
import java.util.Optional;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.xaamruda.bbm.offers.model.PostedOffer;
import com.xaamruda.bbm.offers.model.OfferStatus;
import com.xaamruda.bbm.commons.logging.BBMLogger;
import com.xaamruda.bbm.integrity.IntegrityIOHandler;
import com.xaamruda.bbm.offers.dbaccess.repository.IOfferRepository;

@Transactional
@Service("OfferService")
public class OfferService implements IOfferService {

	@Autowired
	IOfferRepository offerRepository;

	@Autowired
	JdbcTemplate mongoTemplate;

	SessionFactory ss;
	
	@Autowired
	private IntegrityIOHandler integrityIOHandler;

	@Override
	public List<PostedOffer> getOfferByID(String offerID) {
		BBMLogger.infoln("Retrieving offer of id " + offerID + ".");
		return offerRepository.getByOfferID(Integer.parseInt(offerID));
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

	@Override
	public List<PostedOffer> getAvailableOffers(Specification<PostedOffer> query) {
		List<PostedOffer> offers = offerRepository.findAll(query);
		return offers;
	}

	@Override
	public synchronized boolean saveOffer(PostedOffer offer){
		long journalId = integrityIOHandler.addOfferJournalEntry("createNewOffer", this.getClass().getSimpleName(), offer);
		BBMLogger.infoln("Storing new offer...");
		
		if(offer != null){
			offer = offerRepository.save(offer);
		}
		
		integrityIOHandler.endOfferJournalEntry(journalId);
		return (offer != null);
	}

	@Override
	public synchronized boolean changeOfferStatus(int id, OfferStatus status) {
		long journalId = integrityIOHandler.addOfferJournalEntry("changeOfferStatus", this.getClass().getSimpleName(), id, status);
		
		BBMLogger.infoln("Changing offer identified by " + id + ".");
		Optional<PostedOffer> offer = offerRepository.findById(id);// .findOne(id);
		
		if (offer.isPresent()) {
			offer.get().setStatus(status);
			offerRepository.save(offer.get());
			return true;
		}
		
		integrityIOHandler.endOfferJournalEntry(journalId);
		return false;
	}

	@Override
	public synchronized void removeOffer(PostedOffer offer) {
		long journalId = integrityIOHandler.addOfferJournalEntry("remove", this.getClass().getSimpleName(), offer);
		BBMLogger.infoln("Removing offer...");
		offerRepository.delete(offer);
		integrityIOHandler.endOfferJournalEntry(journalId);
	}
	
	@Override
	public void shutDown() {
		BBMLogger.infoln("Shutting down database...");
		offerRepository.shutDown();
	}

	@Override
	public List<PostedOffer> getAllOffers() {
		return offerRepository.findAll();
	}
	
	@Override
	public void delete(PostedOffer offer) {
		offerRepository.delete(offer);
	}

}