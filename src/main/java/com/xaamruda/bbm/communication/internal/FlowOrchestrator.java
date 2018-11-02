package com.xaamruda.bbm.communication.internal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.xaamruda.bbm.commons.json.JsonUtils;
import com.xaamruda.bbm.commons.logging.BBMLogger;
import com.xaamruda.bbm.offers.model.PostedOffer;

@Component
public class FlowOrchestrator implements IFlowOrchestrator {

	@Autowired
	private com.xaamruda.bbm.users.UsersIOHandler userIO;

	@Autowired
	private com.xaamruda.bbm.billing.BillingIOHandler billingIO;

	@Autowired
	private com.xaamruda.bbm.offers.OffersIOHandler offerIO;

	@Autowired
	private com.xaamruda.bbm.roads.RoadsIOHandler roadsIO;
	
	public FlowOrchestrator() {}

	
	/*
	 * -------- USERS --------
	 */
	
	@SuppressWarnings("rawtypes")
	@Override
	public FlowOrchestrationResult orchestrateUsersEntryPoint(String jsonEvents) {
		BBMLogger.infoln("Handling user request...");
		JsonObject jsonObject = JsonUtils.getFromJson(jsonEvents);
		JsonElement event = jsonObject.get("event");
		JsonElement data = jsonObject.get("data");
		BBMLogger.infoln("Request's event and data recovered.");
		
		HttpStatus status;

		Class clazz = null;
		Object content = null;

		switch (event.getAsString()) {
			case "create-user" : {
				BBMLogger.infoln("Event: " + event.getAsString());
				callCreateUser(data.getAsString());
				clazz = String.class;
				content = "User created.";
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
			break;
		}

		default:
			status = HttpStatus.NOT_ACCEPTABLE;
			content = null;
			clazz = null;
		}
		
		return new FlowOrchestrationResult(status, content, clazz);
	}

	/*
	 * -------- OFFERS --------
	 */

	@Override
	@SuppressWarnings("rawtypes")
	public FlowOrchestrationResult orchestrateOffersEntryPoint(String jsonEvents) {
		JsonObject jsonObject = JsonUtils.getFromJson(jsonEvents);
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
			
			default: {
				status = HttpStatus.NOT_ACCEPTABLE;
				content = null;
				clazz = null;
			}
		}
	
		return new FlowOrchestrationResult(status, content, clazz);
	}
	
	private boolean callMakeOffer(String offerJson) {
		return offerIO.postNewOffer(offerJson);
	}

	// data are data used for calculation
	private List<PostedOffer> callGetFilteredOffers(String filters, String calculationData) {
		return offerIO.retrieveOffers(filters, calculationData);
	}
	
	private void callCreateUser(String userJson) {
		userIO.postNewUser(userJson);
	}

}
