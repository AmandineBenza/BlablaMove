package com.xaamruda.bbm.communication.internal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.xaamruda.bbm.commons.exceptions.DatabaseException;
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
			try {
				callCreateUser(data.toString());
			} catch (DatabaseException e) {
				content = e.getWholeMessage();
				clazz = String.class;
				status = e.getRelatedHttpStatus();
				break;
			}
			
			clazz = String.class;
			content = "User created.\n";
			status = HttpStatus.OK;
			break;
		}

		case "identify-user" : {
			BBMLogger.infoln("Identifying a user...");
			boolean userExists;
			try {
				userExists = userIO.identifyUserByMailPlusPassword
						(data.getAsJsonObject().get("mail").getAsString(), 
								data.getAsJsonObject().get("password").getAsString());
			} catch (DatabaseException e) {
				content = e.getWholeMessage();
				clazz = String.class;
				status = e.getRelatedHttpStatus();
				break;
			} 
			
			clazz = Boolean.class;
			status = userExists ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE;
			content = userExists ? "Successfully identified.\n" : "Wrong identifiers ! Please retry.\n";
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
			boolean identifed = false;
			try {
				identifed = userIO.isIdentified(identification.getAsJsonObject().get("userID").getAsString());
			} catch (DatabaseException e1) {
				content = e1.getWholeMessage();
				clazz = String.class;
				status = e1.getRelatedHttpStatus();
				break;
			}
			
			if(!identifed) {
				BBMLogger.infoln("Unindentified user tried to create an offer.");
				content = "Please login to your account.\n";
			} else {
				BBMLogger.infoln("Validating pricing...");
				try {
					content = callValidatePrice(data.toString(), jsonObject.get("filters").toString());
				} catch (DatabaseException e) {
					content = e.getWholeMessage();
					clazz = String.class;
					status = e.getRelatedHttpStatus();
					break;
				}
			}
			
			clazz = Boolean.class;
			status = identifed ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE;
			break;
		}	

		case "create-offer": {
			boolean identifed = false;
			
			try {
				identifed = userIO.isIdentified(identification.getAsJsonObject().get("userID").getAsString());
			} catch (DatabaseException e1) {
				content = e1.getWholeMessage();
				clazz = String.class;
				status = e1.getRelatedHttpStatus();
				break;
			}
			
			if(!identifed) {
				BBMLogger.infoln("Unindentified user tried to create an offer.");
				content = "Please login to your account.\n";
			} else {
				BBMLogger.infoln("Creating an offer...");
				try {
					content = callMakeOffer(data.toString());
				} catch (DatabaseException e) {
					content = e.getWholeMessage();
					clazz = String.class;
					status = e.getRelatedHttpStatus();
					break;
				}
			}

			clazz = Boolean.class;
			status = identifed ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE;
			break;	
		}

		case "consult-offers": {
			boolean identifed = false;
			try {
				identifed = userIO.isIdentified(identification.getAsJsonObject().get("userID").getAsString());
			} catch (DatabaseException e1) {
				content = e1.getWholeMessage();
				clazz = String.class;
				status = e1.getRelatedHttpStatus();
				break;
			}
			
			if(!identifed) {
				BBMLogger.infoln("Unindentified user tried to consult an offer.");
				content = "Please login to your account.\n";
			} else {
				BBMLogger.infoln("Consulting offers...");
				try {
					content = callGetFilteredOffers(jsonObject.get("filters").toString(), data.toString());
				} catch (DatabaseException e) {
					content = e.getWholeMessage();
					clazz = String.class;
					status = e.getRelatedHttpStatus();
					break;
				}		
			}
			
			clazz = List.class;
			status = identifed ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE;
			break;
		}

		// Bob requests to Alice
		case "ask-offer": {
			boolean identifed = false;
			try {
				identifed = userIO.isIdentified(identification.getAsJsonObject().get("userID").getAsString());
			} catch (DatabaseException e1) {
				content = e1.getWholeMessage();
				clazz = String.class;
				status = e1.getRelatedHttpStatus();
				break;
			}
			
			if(!identifed) {
				BBMLogger.infoln("Unindentified user tried to request an offer.");
				content = "Please login to your account.\n";
			} else {
				BBMLogger.infoln("Requesting for an offer...");
				try {
					content = callAskOffer(data.toString());
				} catch (DatabaseException e) {
					content = e.getWholeMessage();
					clazz = String.class;
					status = e.getRelatedHttpStatus();
					break;
				}
			}
			
			clazz = List.class;
			status = identifed ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE;
			break;
		}

		// Alice consults her requests
		case "consult-awaiting-offers": {
			BBMLogger.infoln("Consulting pending offers...");
			boolean identifed = false;
			try {
				identifed = userIO.isIdentified(identification.getAsJsonObject().get("userID").getAsString());
			} catch (DatabaseException e1) {
				content = e1.getWholeMessage();
				clazz = String.class;
				status = e1.getRelatedHttpStatus();
				break;
			}
			
			if(!identifed) {
				BBMLogger.infoln("Unindentified user tried to consult his awaiting offers.");
				content = "Please login to your account.\n";
			} else {
				try {
					content = callConsultAwaitingOffers(data.toString());
				} catch (DatabaseException e) {
					content = e.getWholeMessage();
					clazz = String.class;
					status = e.getRelatedHttpStatus();
					break;
				}
			}
			
			clazz = List.class;
			status = identifed ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE;
			break;
		}

		// Alice confirms one of her requests
		case "confirm-awaiting-offers": {
			BBMLogger.infoln("Confirming pending offers...");
			boolean identifed = false;
			try {
				identifed = userIO.isIdentified(identification.getAsJsonObject().get("userID").getAsString());
			} catch (DatabaseException e1) {
				content = e1.getWholeMessage();
				clazz = String.class;
				status = e1.getRelatedHttpStatus();
				break;
			}
			
			if(!identifed) {
				BBMLogger.infoln("Unindentified user tried to confirm his awaiting offers.");
				content = "Please login to your account.\n";
			} else {
				try {
					content = callConfirmAwaitingOffers(data.toString());
				} catch (DatabaseException e) {
					content = e.getWholeMessage();
					clazz = String.class;
					status = e.getRelatedHttpStatus();
					break;
				}
			}
			
			clazz = List.class;
			status = identifed ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE;
			break;
		}

		// Bob claims that his items has been sent 
		case "claim-receipt":{
			BBMLogger.infoln("Claiming items reception...");
			boolean identifed = false;
			try {
				identifed = userIO.isIdentified(identification.getAsJsonObject().get("userID").getAsString());
			} catch (DatabaseException e1) {
				content = e1.getWholeMessage();
				clazz = String.class;
				status = e1.getRelatedHttpStatus();
				break;
			}
			
			if(!identifed) {
				BBMLogger.infoln("Unindentified user tried to claim that his items has been sent.");
				content = "Please login to your account.\n";
			} else {
				try {
					content = callClaimReceipt(data.toString());
				} catch (DatabaseException e) {
					content = e.getWholeMessage();
					clazz = String.class;
					status = e.getRelatedHttpStatus();
					break;
				}
			}
			
			clazz = List.class;
			status = identifed ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE;
			break;
		}

		// Alice confirms she receives Bob items
		case "confirm-receipt":{
			BBMLogger.infoln("Confirming items reception...");
			boolean identifed = false;
			try {
				identifed = userIO.isIdentified(identification.getAsJsonObject().get("userID").getAsString());
			} catch (DatabaseException e1) {
				content = e1.getWholeMessage();
				clazz = String.class;
				status = e1.getRelatedHttpStatus();
				break;
			}
			
			if(!identifed) {
				BBMLogger.infoln("Unindentified user tried to confirms he received an item");
				content = "Please login to your account.\n";
			} else {
				try {
					content = callConfirmReceipt(data.toString());
				} catch (DatabaseException e) {
					content = e.getWholeMessage();
					clazz = String.class;
					status = e.getRelatedHttpStatus();
					break;
				}
			}
			
			clazz = List.class;
			status = identifed ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE;
			break;
		}

		// Alice claims Bob items has been delivered
		case "claim-deposit":{
			BBMLogger.infoln("Claiming items deposit...");
			boolean identifed = false;
			try {
				identifed = userIO.isIdentified(identification.getAsJsonObject().get("userID").getAsString());
			} catch (DatabaseException e1) {
				content = e1.getWholeMessage();
				clazz = String.class;
				status = e1.getRelatedHttpStatus();
				break;
			}
			
			if(!identifed) {
				BBMLogger.infoln("Unindentified user tried to claim that an item has been delivered.");
				content = "Please login to your account.\n";
			} else {
				try {
					content = callClaimDeposit(data.toString());
				} catch (DatabaseException e) {
					content = e.getWholeMessage();
					clazz = String.class;
					status = e.getRelatedHttpStatus();
					break;
				}
			}
			
			clazz = List.class;
			status = identifed ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE;
			break;
		}

		// Bob confirms his items has been delivered
		case "confirm-deposit":{
			BBMLogger.infoln("Confirming items deposit...");
			boolean identifed = false;
			try {
				identifed = userIO.isIdentified(identification.getAsJsonObject().get("userID").getAsString());
			} catch (DatabaseException e1) {
				content = e1.getWholeMessage();
				clazz = String.class;
				status = e1.getRelatedHttpStatus();
				break;
			}
			
			if(!identifed) {
				BBMLogger.infoln("Unindentified user tried to confirm that an item has been delivered.");
				content = "Please login to your account.\n";
			} else {
				try {
					content = callConfirmDeposits(data.toString());
				} catch (DatabaseException e) {
					content = e.getWholeMessage();
					clazz = String.class;
					status = e.getRelatedHttpStatus();
					break;
				}
			}

			clazz = List.class;
			status = identifed ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE;
			
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

	private String callValidatePrice(String string,String filters) throws DatabaseException {
		return offerIO.validatePrice(filters, string);
	}

	private String callConfirmCommand(String string) {
		return JsonUtils.toJson(JsonUtils.getFromJson(string, CommandResume.class));
	}

	private Object callConfirmDeposits(String string) throws DatabaseException {
		return offerIO.confirmDeposit(string);
	}


	private Object callClaimDeposit(String string) throws DatabaseException {
		return offerIO.claimDeposit(string);
	}


	private Object callConfirmReceipt(String string) throws DatabaseException {
		return offerIO.confirmReceipt(string);
	}


	private Object callClaimReceipt(String string) throws DatabaseException {
		return offerIO.claimReceipt(string);
	}


	private Object callConfirmAwaitingOffers(String workData) throws DatabaseException {
		return offerIO.confirmAwaitingOffer(workData);
	}


	private Object callConsultAwaitingOffers(String workData) throws DatabaseException {
		return offerIO.consultAwaitingOffers(workData);
	}


	private Object callAskOffer(String string) throws DatabaseException {
		return offerIO.askForValidation(string);
	}


	private String callMakeOffer(String offerJson) throws DatabaseException {
		return offerIO.postNewOffer(offerJson);
	}

	// data are data used for calculation
	private List<PostedOffer> callGetFilteredOffers(String filters, String calculationData) throws DatabaseException {
		return offerIO.retrieveOffers(filters, calculationData);
	}

	private void callCreateUser(String userJson) throws DatabaseException {
		userIO.postNewUser(userJson);
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
