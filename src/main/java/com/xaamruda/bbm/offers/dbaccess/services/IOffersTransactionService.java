package com.xaamruda.bbm.offers.dbaccess.services;

import java.util.List;

import com.xaamruda.bbm.offers.model.OffersTransaction;

public interface IOffersTransactionService {

	boolean createNewOffer(OffersTransaction offer);

	List<OffersTransaction> getOffersBy(String userID);
    List<OffersTransaction> getOffersByTransactionID(String transactionID);

	void remove(OffersTransaction offer);

}
