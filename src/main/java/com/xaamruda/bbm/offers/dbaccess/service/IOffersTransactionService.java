package com.xaamruda.bbm.offers.dbaccess.service;

import java.util.List;

import com.xaamruda.bbm.offers.model.OffersTransaction;

public interface IOffersTransactionService {

	boolean saveOffer(OffersTransaction offer);

	List<OffersTransaction> getOffersByOwnerId(String userID);
    List<OffersTransaction> getOffersByTransactionID(String transactionID);

    void remove(OffersTransaction offer);
    List<OffersTransaction> getAll();

}
