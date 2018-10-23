package com.xaamruda.bbm.communication.internal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Component;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.xaamruda.bbm.offers.model.Offer;

@Component
public class FlowOrchestrator implements IFlowOrchestrator {

	@Autowired
	private com.xaamruda.bbm.users.IOHandler userIO;

	@Autowired
	private com.xaamruda.bbm.calculator.IOHandler calculatorIO;

	@Autowired
	private com.xaamruda.bbm.offers.IOHandler offerIO;

	@Autowired
	private com.xaamruda.bbm.roads.IOHandler roadsIO;
	
	public FlowOrchestrator() {}

	
	/*
	 * -------- USERS --------
	 */
	
	@SuppressWarnings("rawtypes")
	@Override
	public FlowOrchestrationResult orchestrateUsersEntryPoint(String jsonEvents) {
		JsonObject jsonObject = GsonBuilderUtils.gsonBuilderWithBase64EncodedByteArrays().create().fromJson(jsonEvents,
				JsonObject.class);
		
		JsonElement event = jsonObject.get("event");
		JsonElement data = jsonObject.get("data");

		HttpStatus status;

		Class clazz = null;
		Object content = null;

		switch (event.getAsString()) {
		case "create-offer": {
			content = callMakeOffer(data.getAsString());
			clazz = Boolean.class;
			status = HttpStatus.OK;
			break;
		}

		case "consult-offers": {
			content = callGetFilteredOffers(jsonObject.get("filters").getAsString(), data.getAsString());
			clazz = List.class;
			status = HttpStatus.OK;
			break;
		}

		// user identification case
		case "identify-user" : {
			// identify or create if does not exist
			JsonArray dataArray = data.getAsJsonArray();
			boolean userExists = userIO.identifyUserByMailPlusPassword(data.getAsString(),
					dataArray.get(0).getAsString(), dataArray.get(1).getAsString()); 
			clazz = Boolean.class;
			status = userExists ? HttpStatus.OK : HttpStatus.CREATED;
			content = userExists;
		}

		default:
			status = HttpStatus.NOT_ACCEPTABLE;
			content = null;
			clazz = null;
		}
		
		return new FlowOrchestrationResult(status, content, clazz);
	}

	// TODO verify prices
	private boolean callMakeOffer(String offerInformation) {
		return offerIO.postNewOffer(offerInformation);
	}

	// data are data used for calculation
	private List<Offer> callGetFilteredOffers(String filters, String calculationData) {
		return offerIO.retrieveOffers(filters, calculationData);
	}
	
	/*
	 * -------- OFFERS --------
	 */

	@Override
	public FlowOrchestrationResult orchestrateOffersEntryPoint(String jsonEvents) {
		return null;
	}

}
