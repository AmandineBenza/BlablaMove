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
		JsonObject jsonObject = JsonUtils.getFromJson(jsonEvents);
		JsonElement event = jsonObject.get("event");
		JsonElement data = jsonObject.get("data");
		BBMLogger.infoln("Event is: \"" + event.getAsString() + "\".");
		BBMLogger.infoln("Handling user request...");

		HttpStatus status;

		Class clazz = null;
		Object content = null;

		switch (event.getAsString()) {
		case "create-user" : {
			BBMLogger.infoln("Creating user...");
			callCreateUser(data.toString());
			clazz = String.class;
			content = "User created.\n";
			status = HttpStatus.OK;
			break;
		}

		case "identify-user" : {
			// identify or create if does not exist
			BBMLogger.infoln("Identifying a user...");
			JsonArray dataArray = data.getAsJsonArray();
			boolean userExists = userIO.identifyUserByMailPlusPassword(data.getAsString(),
					dataArray.get(0).getAsString(), dataArray.get(1).getAsString()); 
			clazz = Boolean.class;
			status = userExists ? HttpStatus.OK : HttpStatus.CREATED;
			content = userExists;
			break;
		}

		case "consult-users" : {
			BBMLogger.infoln("Consulting users...");
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

		case "consult-user" : {
			BBMLogger.infoln("Consulting user...");
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
		JsonObject jsonObject = JsonUtils.getFromJson(jsonEvents);
		JsonElement event = jsonObject.get("event");
		JsonElement data = jsonObject.get("data");
		BBMLogger.infoln("Event is: \"" + event.getAsString() + "\".");
		BBMLogger.infoln("Handling offer request...");

		HttpStatus status;
		Class clazz = null;
		Object content = null;

		switch (event.getAsString()) { // TODO
		case "validate-price":{
			BBMLogger.infoln("Validating pricing...");
			content = callValidatePrice(data.toString(), jsonObject.get("filters").toString());
			clazz = Boolean.class;
			status = HttpStatus.OK;
			break;
		}	
		
		case "create-offer": {
			BBMLogger.infoln("Creating an offer...");
			content = callMakeOffer(data.toString());
			clazz = Boolean.class;
			status = HttpStatus.OK;
			break;
		}
		
		case "consult-offers": {
			BBMLogger.infoln("Consulting offers...");
			content = callGetFilteredOffers(jsonObject.get("filters").toString(), data.toString());
			clazz = List.class;
			status = HttpStatus.OK;
			break;
		}

		// Bob requests to Alice
		case "ask-offer": {
			BBMLogger.infoln("Requesting for an offer...");
			content = callAskOffer(data.toString());
			clazz = List.class;
			status = HttpStatus.OK;
			break;
		}
		
		// Alice consults its requests
		case "consult-awaiting-offers": {
			BBMLogger.infoln("Consulting pending offers...");
			content = callConsultAwaitingOffers(data.toString());
			clazz = List.class;
			status = HttpStatus.OK;
			break;
		}
		
		// Alice confirms one of its requests
		case "confirm-awaiting-offers": {
			BBMLogger.infoln("Confirming pending offers...");
			content = callConfirmAwaitingOffers(data.toString());
			clazz = List.class;
			status = HttpStatus.OK;
			break;
		}
		
		// Bob claims that his items has been sent 
		case "claim-receipt":{
			BBMLogger.infoln("Claiming items reception...");
			content = callClaimReceipt(data.toString());
			clazz = List.class;
			status = HttpStatus.OK;
			break;
		}
		
		// Alice confirms she receives Bob items
		case "confirm-receipt":{
			BBMLogger.infoln("Confirming items reception...");
			content = callConfirmReceipt(data.toString());
			clazz = List.class;
			status = HttpStatus.OK;
			break;
		}
		
		// Alice claims Bob items has been deposit
		case "claim-deposit":{
			BBMLogger.infoln("Claiming items deposit...");
			content = callClaimDeposit(data.toString());
			clazz = List.class;
			status = HttpStatus.OK;
			break;
		}
		
		// Bob confirms his items has been deposit
		case "confirm-deposit":{
			BBMLogger.infoln("Confirming items deposit...");
			content = callConfirmDeposits(data.toString());
			clazz = List.class;
			status = HttpStatus.OK;
			break;
		}

		case "confirm-command":{
			BBMLogger.infoln("System return the command....");
			content = callConfirmCommand(data.toString());
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

	private Object callValidatePrice(String string,String filters) {
		return offerIO.validatePrice(filters, string);
	}

	private String callConfirmCommand(String string) {
		JsonObject json = JsonUtils.getFromJson(string);
		String res = "Resume of your command :\n ";
		res = res + "Number of day until moving : " + json.get("date").getAsString() + "\n";
		res = res + "Address of claim : " + json.get("startAddress").getAsString() + "\n";
		res = res + "Address of deposit : " + json.get("endAddress").getAsString() + "\n";
		res = res + "Offer : " + json.get("offerID").getAsString() + "\n";
		res = res + "Price : " + json.get("price").getAsString() + "\n";
		return res + "\n";
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
		return offerIO.askValidate(string);
	}


	private String callMakeOffer(String offerJson) {
		return offerIO.postNewOffer(offerJson);
	}

	// data are data used for calculation
	private List<PostedOffer> callGetFilteredOffers(String filters, String calculationData) {
		return offerIO.retrieveOffers(filters, calculationData);
	}

	private void callCreateUser(String userJson) {
		userIO.postNewUser(userJson);
	}

	private List<User> callGetUsers() {
		return userIO.retrieveUsers();
	}

	private User callGetUser(String mail) {
		return userIO.retrieveUser(mail);
	}
}
