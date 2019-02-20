package com.xaamruda.bbm.offers.dbaccess.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xaamruda.bbm.offers.dbaccess.repository.IOffersTransactionRepository;
import com.xaamruda.bbm.offers.model.OffersTransaction;

@Service("OfferTransactionService")
public class OfferTransactionService implements IOffersTransactionService {

	@Autowired
	IOffersTransactionRepository offerRepository;
	
	@Override
	public synchronized boolean saveOffer(OffersTransaction offer) {
		offerRepository.save(offer);
		return (offer != null);
	}
	
	@Override
	public List<OffersTransaction> getOffersByOwnerId(String ownerId) {
		return offerRepository.getByOwnerID(ownerId);
	}
	
	@Override
	public synchronized void remove(OffersTransaction offer) {
		 offerRepository.delete(offer);
	}

	@Override
	public List<OffersTransaction> getOffersByTransactionID(String transactionID) {
		return offerRepository.getByTransactionID(Integer.parseInt(transactionID));
	}
	
	@Override
	public List<OffersTransaction> getAll() {
		return offerRepository.findAll();
	}
}
