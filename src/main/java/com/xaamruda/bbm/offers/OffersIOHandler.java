package com.xaamruda.bbm.offers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.Filter;
import org.springframework.stereotype.Component;

import com.xaamruda.bbm.commons.json.JsonUtils;
import com.xaamruda.bbm.commons.logging.BBMLogger;
import com.xaamruda.bbm.offers.dbaccess.services.IOfferService;
import com.xaamruda.bbm.offers.model.PostedOffer;
import com.xaamruda.bbm.offers.search.engine.QueryEngine;
import com.xaamruda.bbm.offers.search.engine.Filters;

@Component
public class OffersIOHandler {
	
	@Autowired
	private IOfferService offerService;

	@Autowired
	private com.xaamruda.bbm.billing.BillingIOHandler calculatorHandler;
	
	public OffersIOHandler() {
	}
	
	public List<PostedOffer> getOffers() {
		BBMLogger.infoln("Processing...");
		return offerService.getAvailableOffers();
	}

	public boolean postNewOffer(String jsonObject) {
		BBMLogger.infoln("Processing...");
		return offerService.createNewOffer(jsonObject);
	}

	// TODO add filterChecker to add the "status.Available" filter ?
	// TODO add check on offer if length == 0
	public List<PostedOffer> retrieveOffers(String filters, String workData) {
		BBMLogger.infoln("Processing...");
		List<PostedOffer> offers = offerService.getAvailableOffers(QueryEngine.buildMongoQuery(JsonUtils.getFromJson(filters, Filters.class)));
		//TODO lol |
		for (PostedOffer offer : offers) {
//			calculatorHandler.calculatorHandler(workData, offer);
//			calculatorHandler.
		}
		
		return offers;
	}
	
	public List<PostedOffer> retrieve(String filters, String workData) {
		//TODO XD
		BBMLogger.infoln("Processing...");
		int distance = 0 ;
		List<PostedOffer> offers = offerService.getAvailableOffers(QueryEngine.buildMongoQuery(distance));
		return offers;
	}
	
}
