package com.xaamruda.bbm.offers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.Filter;
import org.springframework.stereotype.Component;

import com.xaamruda.bbm.commons.json.JsonUtils;
import com.xaamruda.bbm.offers.billing.calculator.Utils;
import com.xaamruda.bbm.commons.logging.BBMLogger;
import com.xaamruda.bbm.offers.dbaccess.services.IOfferService;
import com.xaamruda.bbm.offers.model.PostedOffer;
import com.xaamruda.bbm.offers.search.engine.QueryEngine;
import com.xaamruda.bbm.roads.RoadsIOHandler;
import com.xaamruda.bbm.roads.model.Path;
import com.xaamruda.bbm.offers.search.engine.Filters;

@Component
public class OffersIOHandler {
	
	@Autowired
	private IOfferService offerService;

	@Autowired
	private com.xaamruda.bbm.billing.BillingIOHandler calculatorHandler;

	@Autowired
	private RoadsIOHandler pathHandler;

	public OffersIOHandler() {
	}
	
	public List<PostedOffer> getOffers() {
		BBMLogger.infoln("Processing...");
		return offerService.getAvailableOffers();
	}

	public boolean postNewOffer(String jsonObject) {
		BBMLogger.infoln("Processing...");
		PostedOffer offer = JsonUtils.getFromJson(jsonObject, PostedOffer.class);

		int distance = pathHandler.getPathDistances(offer.getStartCity(), offer.getEndCity());
		List<PostedOffer> offers = offerService.getAvailableOffers(QueryEngine.buildMongoQuery(distance));
		Utils range = offerService.checkPrice(offers, distance);

		if((offer.getPrice() < range.getSupValue() && offer.getPrice() > range.getInfValue()))
			offer.setDistance(distance);

		return offerService.createNewOffer(offer);
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

	public String validate(String filters, String workData) {
		BBMLogger.infoln("Processing...");
		Filters fil = JsonUtils.getFromJson(filters, Filters.class);
		int distance = pathHandler.getPathDistances(fil.startAddress, fil.endAddress);
		List<PostedOffer> offers = offerService.getAvailableOffers(QueryEngine.buildMongoQuery(distance));
		Utils range = offerService.checkPrice(offers, distance);

		return (fil.maxPrice  < range.getSupValue() && fil.maxPrice > range.getInfValue()) ? "OK" : "NOK";
	}

}
