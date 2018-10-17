package com.xaamruda.bbm.offers.dbaccess.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xaamruda.bbm.offers.model.ClientOffer;
import com.xaamruda.bbm.offers.model.Offer;
import com.xaamruda.bbm.offers.model.OfferStatus;
import com.xaamruda.bbm.offers.dbaccess.repository.IOfferRepository;

import ch.qos.logback.core.filter.Filter;

@Transactional
@Service("UserService")
public class OfferService implements IOfferService {
	@Autowired 
	IOfferRepository offerRepository;
	@Override
	public List<Offer> getAllArchivedOffers() {
		return offerRepository.getByStatus(OfferStatus.CLOSED);

	}

	@Override
	public List<Offer> getOffersByOwnerID(int OwnerID) {
		return offerRepository.getByOwnerID(OwnerID) ;
	}

	//TODO speak about
	@Override
	public List<ClientOffer> askOfferByPrice(Filter f) {
		return null;
	}

	@Override                                                     
	public List<Offer> getAvailableOffers() {                     
		return  offerRepository.getByStatus(OfferStatus.POSTED);  
	}                                                             

	@Override                                                     
	public List<Offer> createNewOffer() {                     
		return null; //offerRepository.store(new Offer());  
	} 

	@Override                                                     
	public boolean changeOfferStatus(int id , OfferStatus status) {     
		Offer offer = offerRepository.findOne(id);
		if(offer != null){
			offer.setStatus(status);
			offerRepository.save(offer);
			return true;  
		}
		return false;
	}  
}                                                          