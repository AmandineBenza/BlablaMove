package com.xaamruda.bbm.communication.internal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

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
	
	@Autowired
	private com.xaamruda.bbm.integrity.IntegrityIOHandler integrityIOHandler;

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
		BBMLogger.infoln("Handling request...");

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
			BBMLogger.infoln("Identifying a user...");
			boolean userExists = userIO.identifyUserByMailPlusPassword
					(data.getAsJsonObject().get("mail").getAsString(), 
							data.getAsJsonObject().get("password").getAsString()); 
			clazz = Boolean.class;
			status = userExists ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE;
			content = userExists ? "Successfully identified.\n" : "Wrong identifiers ! Please retry.\n";
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
		JsonElement identification = jsonObject.get("identification");
		BBMLogger.infoln("Event is: \"" + event.getAsString() + "\".");
		BBMLogger.infoln("Handling request...");

		HttpStatus status;
		Class clazz = null;
		Object content = null;

		switch (event.getAsString()) { // TODO
		
		case "validate-price":{
			boolean identifed = userIO.isIdentified(identification.getAsJsonObject().get("userID").getAsString());
			if(!identifed) {
				BBMLogger.infoln("Unindentified user tried to create an offer.");
				content = "Please login to your account.\n";
			} else {
				BBMLogger.infoln("Validating pricing...");
				content = callValidatePrice(data.toString(), jsonObject.get("filters").toString());
			}
			clazz = Boolean.class;
			status = identifed ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE;
			break;
		}	

		case "create-offer": {
			boolean identifed = userIO.isIdentified(identification.getAsJsonObject().get("userID").getAsString());
			if(!identifed) {
				BBMLogger.infoln("Unindentified user tried to create an offer.");
				content = "Please login to your account.\n";
			} else {
				BBMLogger.infoln("Creating an offer...");
				content = callMakeOffer(data.toString());
			}

			clazz = Boolean.class;
			status = identifed ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE;
			break;	
		}

		case "consult-offers": {
			boolean identifed = userIO.isIdentified(identification.getAsJsonObject().get("userID").getAsString());
			if(!identifed) {
				BBMLogger.infoln("Unindentified user tried to consult an offer.");
				content = "Please login to your account.\n";
			} else {
				BBMLogger.infoln("Consulting offers...");
				content = callGetFilteredOffers(jsonObject.get("filters").toString(), data.toString());		
			}
			clazz = List.class;
			status = identifed ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE;
			break;
		}

		// Bob requests to Alice
		case "ask-offer": {
			boolean identifed = userIO.isIdentified(identification.getAsJsonObject().get("userID").getAsString());
			if(!identifed) {
				BBMLogger.infoln("Unindentified user tried to request an offer.");
				content = "Please login to your account.\n";
			} else {
				BBMLogger.infoln("Requesting for an offer...");
				content = callAskOffer(data.toString());
			}
			clazz = List.class;
			status = identifed ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE;
			break;
		}

		// Alice consults her requests
		case "consult-awaiting-offers": {
			BBMLogger.infoln("Consulting pending offers...");
			boolean identifed = userIO.isIdentified(identification.getAsJsonObject().get("userID").getAsString());
			if(!identifed) {
				BBMLogger.infoln("Unindentified user tried to consult his awaiting offers.");
				content = "Please login to your account.\n";
			} else {
				content = callConsultAwaitingOffers(data.toString());
			}
			clazz = List.class;
			status = identifed ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE;
			break;
		}

		// Alice confirms one of her requests
		case "confirm-awaiting-offers": {
			BBMLogger.infoln("Confirming pending offers...");
			boolean identifed = userIO.isIdentified(identification.getAsJsonObject().get("userID").getAsString());
			if(!identifed) {
				BBMLogger.infoln("Unindentified user tried to confirm his awaiting offers.");
				content = "Please login to your account.\n";
			} else {
				content = callConfirmAwaitingOffers(data.toString());
			}
			clazz = List.class;
			status = identifed ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE;
			break;
		}

		// Bob claims that his items has been sent 
		case "claim-receipt":{
			BBMLogger.infoln("Claiming items reception...");
			boolean identifed = userIO.isIdentified(identification.getAsJsonObject().get("userID").getAsString());
			if(!identifed) {
				BBMLogger.infoln("Unindentified user tried to claim that his items has been sent.");
				content = "Please login to your account.\n";
			} else {
			content = callClaimReceipt(data.toString());
			}
			clazz = List.class;
			status = identifed ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE;
			break;
		}

		// Alice confirms she receives Bob items
		case "confirm-receipt":{
			BBMLogger.infoln("Confirming items reception...");
			boolean identifed = userIO.isIdentified(identification.getAsJsonObject().get("userID").getAsString());
			if(!identifed) {
				BBMLogger.infoln("Unindentified user tried to confirms he received an item");
				content = "Please login to your account.\n";
			} else {
				content = callConfirmReceipt(data.toString());
			}
			clazz = List.class;
			status = identifed ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE;
			break;
		}

		// Alice claims Bob items has been delivered
		case "claim-deposit":{
			BBMLogger.infoln("Claiming items deposit...");
			boolean identifed = userIO.isIdentified(identification.getAsJsonObject().get("userID").getAsString());
			if(!identifed) {
				BBMLogger.infoln("Unindentified user tried to claim that an item has been delivered.");
				content = "Please login to your account.\n";
			} else {
			content = callClaimDeposit(data.toString());
			}
			clazz = List.class;
			status = identifed ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE;
			break;
		}

		// Bob confirms his items has been delivered
		case "confirm-deposit":{
			BBMLogger.infoln("Confirming items deposit...");
			boolean identifed = userIO.isIdentified(identification.getAsJsonObject().get("userID").getAsString());
			if(!identifed) {
				BBMLogger.infoln("Unindentified user tried to confirm that an item has been delivered.");
				content = "Please login to your account.\n";
			} else {
				content = callConfirmDeposits(data.toString());
			}
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
		// TODO
		return offerIO.validatePrice(filters, string);
	}

	private String callConfirmCommand(String string) {
		return JsonUtils.toJson(JsonUtils.getFromJson(string, CommandResume.class));
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

	@SuppressWarnings("unused")
	private static final class CommandResume {
		public String date;
		public String startAddress;
		public String endAddress;
		public String offerID;
		public String price;

		public CommandResume(String date, String startAddress, String endAddress, String offerID, String price) {
			this.date = date;
			this.startAddress = startAddress;
			this.endAddress = endAddress;
			this.offerID = offerID;
			this.price = price;
		}

		public CommandResume() {

		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public String getStartAddress() {
			return startAddress;
		}

		public void setStartAddress(String startAddress) {
			this.startAddress = startAddress;
		}

		public String getEndAddress() {
			return endAddress;
		}

		public void setEndAddress(String endAddress) {
			this.endAddress = endAddress;
		}

		public String getOfferID() {
			return offerID;
		}

		public void setOfferID(String offerID) {
			this.offerID = offerID;
		}

		public String getPrice() {
			return price;
		}

		public void setPrice(String price) {
			this.price = price;
		}
	}
}
