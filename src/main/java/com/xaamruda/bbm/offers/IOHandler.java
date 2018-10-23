package com.xaamruda.bbm.offers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.GsonBuilderUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xaamruda.bbm.offers.dbaccess.services.IOfferService;
import com.xaamruda.bbm.offers.dbaccess.services.OfferService;
import com.xaamruda.bbm.offers.model.Offer;
import com.xaamruda.bbm.offers.search.engine.QueryEngine;
import com.xaamruda.bbm.offers.search.engine.Filters;


public class IOHandler {
	@Autowired
	IOfferService offerService;
	
	@Autowired
	com.xaamruda.bbm.calculator.IOHandler calculatorHandler;
	
	public List<Offer> getOffers(){
		return  offerService.getAvailableOffers();
	}
	
	public boolean postNewOffer(String jsonObject){
		return  offerService.createNewOffer(jsonObject);
	}

	//TODO  add filterChecker to add the "status.Available" filter ?
	//TODO add check on offer if lenght == 0
	//TODO
	public List<Offer> retrieveOffers(String filters,String workData) {
	Gson gb = GsonBuilderUtils.gsonBuilderWithBase64EncodedByteArrays().create();
	
	List<Offer> offers = offerService.getAvailableOffers(QueryEngine.BuildMongoQuery(gb.fromJson(filters, Filters.class)));
	for(Offer offer : offers ){
		calculatorHandler.doWork(workData, offer);
	}
	return offers;
	}
}
