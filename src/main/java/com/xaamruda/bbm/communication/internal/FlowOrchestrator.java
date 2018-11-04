package com.xaamruda.bbm.communication.internal;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.xaamruda.bbm.commons.json.JsonUtils;
import com.xaamruda.bbm.commons.logging.BBMLogger;
import com.xaamruda.bbm.offers.model.OfferStatus;
import com.xaamruda.bbm.offers.model.OffersTransaction;
import com.xaamruda.bbm.offers.model.PostedOffer;
import com.xaamruda.bbm.users.model.User;

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
				callCreateUser(data.toString());
				clazz = String.class;
				content = "User created.";
				status = HttpStatus.OK;
				break;
			}

		// user identification case TODO
		case "identify-user" : {
			// identify or create if does not exist
			BBMLogger.infoln("Event: " + event.getAsString());
			JsonArray dataArray = data.getAsJsonArray();
			boolean userExists = userIO.identifyUserByMailPlusPassword(data.getAsString(),
					dataArray.get(0).getAsString(), dataArray.get(1).getAsString()); 
			clazz = Boolean.class;
			status = userExists ? HttpStatus.OK : HttpStatus.CREATED;
			content = userExists;
			break;
		}
		
		// consult users TODO
		case "consult-users" : {
			BBMLogger.infoln("Event: " + event.getAsString());
			content = callGetUsers();
			clazz = List.class;
			List lcontent = (List) content;
			if(content == null || lcontent == null || lcontent.isEmpty()) {
				status = HttpStatus.NOT_FOUND;		
			}
			else {
				status = HttpStatus.OK;
			}
			
			break;	
		}
		
		//consult user by mail TODO
		case "consult-user" : {
			BBMLogger.infoln("Event: " + event.getAsString());
			content = callGetUser(data.getAsJsonObject().get("mail").getAsString());
			clazz = User.class;
			if(content == null) {
				status = HttpStatus.NOT_FOUND;		
			}
			else {
				status = HttpStatus.OK;
			}
			
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
		BBMLogger.infoln("Handling offer request...");
		JsonObject jsonObject = JsonUtils.getFromJson(jsonEvents);
		JsonElement event = jsonObject.get("event");
		JsonElement data = jsonObject.get("data");
		BBMLogger.infoln("Request's event and data recovered.");
		HttpStatus status;
		BBMLogger.infoln("" + jsonObject.get("filters"));
		BBMLogger.infoln("" + data);
		BBMLogger.infoln("Event: " + event.getAsString());
		
		Class clazz = null;
		Object content = null;

		switch (event.getAsString()) { // TODO
			case "create-offer": {
				content = callMakeOffer(data.toString());
				clazz = Boolean.class;
				status = HttpStatus.OK;
				break;
			}
	
			case "consult-offers": { // TODO
				content = callGetFilteredOffers(jsonObject.get("filters").toString(), data.toString());
				clazz = List.class;
				status = HttpStatus.OK;
				break;
			}
			case "ask-offer": { // TODO
				content = callAskOffer( data.toString());
				clazz = List.class;
				status = HttpStatus.OK;
				break;
			}
			case "consult-awaiting-offers": { 
				content = callConsultAwaitingOffers(data.toString());
				clazz = List.class;
				status = HttpStatus.OK;
				break;
			}
			case "confirm-awaiting-offers": { 
				content = callConfirmAwaitingOffers(data.toString());
				clazz = List.class;
				status = HttpStatus.OK;
				break;
			}
			case "claim-receipt":{
				content = callClaimReceipt(data.toString());
				clazz = List.class;
				status = HttpStatus.OK;
				break;
			}
			case "confirm-receipt":{
				content = callConfirmReceipt(data.toString());
				clazz = List.class;
				status = HttpStatus.OK;
				break;
			}
			case "claim-deposit":{
				content = callClaimDeposit(data.toString());
				clazz = List.class;
				status = HttpStatus.OK;
				break;
			}
			case "confirm-deposit":{
				content = callConfirmDeposits(data.toString());
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
	
	private Object callConfirmDeposits(String string) {
		return offerIO.confirmDeposit(string);
	}


	private Object callClaimDeposit(String string) {
		return offerIO.claimDeposit(string);
	}


	private Object callConfirmReceipt(String string) {
		return offerIO.confirmReceipt(string);
	}


	private Object callClaimReceipt(String string) {
		return offerIO.claimReceipt(string);
	}


	private Object callConfirmAwaitingOffers(String workData) {
		return offerIO.confirmAwaitingOffer(workData);
	}


	private Object callConsultAwaitingOffers(String workData) {

		return offerIO.consultAwaitingOffers(workData);
	}


	private Object callAskOffer(String string) {
		BBMLogger.infoln("Request new offer...");
		return offerIO.askValidate(string);
	}


	private String callMakeOffer(String offerJson) {
		BBMLogger.infoln("Creating new offer...");
		return offerIO.postNewOffer(offerJson);
	}

	// data are data used for calculation
	private List<PostedOffer> callGetFilteredOffers(String filters, String calculationData) {
		BBMLogger.infoln("Retrieving offers...");
		return offerIO.retrieveOffers(filters, calculationData);
	}
	
	private void callCreateUser(String userJson) {
		BBMLogger.infoln("Creating new user...");
		userIO.postNewUser(userJson);
	}
	
	private List<User> callGetUsers() {
		BBMLogger.infoln("Retrieving users...");
		return userIO.retrieveUsers();
	}
	
	private User callGetUser(String mail) {
		BBMLogger.infoln("Retrieving user...");
		return userIO.retrieveUser(mail);
	}

}
