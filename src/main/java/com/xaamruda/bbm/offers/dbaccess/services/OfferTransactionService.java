package com.xaamruda.bbm.offers.dbaccess.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xaamruda.bbm.offers.dbaccess.repository.IOfferRepository;
import com.xaamruda.bbm.offers.dbaccess.repository.IOffersTransactionRepository;
import com.xaamruda.bbm.offers.model.OffersTransaction;
import com.xaamruda.bbm.offers.model.PostedOffer;


@Service("OfferTransactionService")
public class OfferTransactionService implements IOffersTransactionService {

	
	@Autowired
	IOffersTransactionRepository offerRepository;
	
	@Override
	public boolean createNewOffer(OffersTransaction offer) {
		offerRepository.insert(offer);
		return (offer != null);
	}
	
	@Override
	public List<OffersTransaction> getOffersBy(String userID) {
		
		return offerRepository.getByOwnerID(userID);
	}
	
	@Override
	public void remove(OffersTransaction offer) {
		
		 offerRepository.delete(offer);
	}

	@Override
	public List<OffersTransaction> getOffersByTransactionID(String transactionID) {
		// TODO Auto-generated method stub
		return  offerRepository.getByTransactionID(transactionID);
	}
}
