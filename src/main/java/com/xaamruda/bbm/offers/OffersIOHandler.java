package com.xaamruda.bbm.offers;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.google.gson.JsonObject;
import com.xaamruda.bbm.billing.BillingIOHandler;
import com.xaamruda.bbm.commons.json.JsonUtils;
import com.xaamruda.bbm.offers.billing.calculator.Utils;
import com.xaamruda.bbm.commons.logging.BBMLogger;
import com.xaamruda.bbm.offers.dbaccess.services.IOfferService;
import com.xaamruda.bbm.offers.dbaccess.services.IOffersTransactionService;
import com.xaamruda.bbm.offers.model.OfferStatus;
import com.xaamruda.bbm.offers.model.OffersTransaction;
import com.xaamruda.bbm.offers.model.PostedOffer;
import com.xaamruda.bbm.offers.search.engine.QueryEngine;
import com.xaamruda.bbm.roads.RoadsIOHandler;
import com.xaamruda.bbm.users.UsersIOHandler;
import com.xaamruda.bbm.offers.search.engine.Filters;

@Component
public class OffersIOHandler {

	@Autowired
	private IOfferService offerService;

	@Autowired
	private IOffersTransactionService offerTransactionService;

	@Autowired
	private BillingIOHandler calculatorHandler;

	@Autowired 
	private UsersIOHandler usersHandler;

	@Autowired
	private RoadsIOHandler pathHandler;

	public OffersIOHandler() {
	}

	public List<PostedOffer> getOffers() {
		BBMLogger.infoln("Processing...");
		return offerService.getAvailableOffers();
	}

	public String postNewOffer(String jsonObject) {
		BBMLogger.infoln("Processing...");
		PostedOffer offer = JsonUtils.getFromJson(jsonObject, PostedOffer.class);

		offer.setOfferID(offer.getOwnerID() + new Date().getTime() + "_" + offer.getPrice());


		int distance = pathHandler.getPathDistances(offer.getStartCity(), offer.getEndCity());
		List<PostedOffer> offers = offerService.getAvailableOffers(QueryEngine.buildMongoQuery(distance));

		Utils range = offerService.checkPrice(offers, distance);

		BBMLogger.infoln("Authorized price range is [" + range.getInfValue() + " : " + range.getSupValue() + " ]");

		if((offer.getPrice() < range.getSupValue() && offer.getPrice() > range.getInfValue())) {
			offer.setDistance(distance);
			offer.setStatus(OfferStatus.POSTED);
			offerService.createNewOffer(offer);
			return "<span>Offer successfully posted</span>\n<BR>" + JsonUtils.toJson(offer);
		}
		return "Incorect price ! For the the distance the authorised amount is [" + range.getInfValue() + " : " + range.getSupValue() + " ]";
	}

	// TODO add filterChecker to add the "status.Available" filter ?
	// TODO add check on offer if length == 0
	public List<PostedOffer> retrieveOffers(String filters, String workData) {
		BBMLogger.infoln("Processing...");

		Filters filtersObject = JsonUtils.getFromJson(filters, Filters.class);
		List<PostedOffer> offers = offerService.getAvailableOffers(
				QueryEngine.buildMongoQuery(
						filtersObject
						));

		for (PostedOffer offer : offers) {
			BBMLogger.infoln("" + calculatorHandler.calcul_without_offer(workData , offer.getDistance()));
			offer.setPrice(offer.getPrice() + calculatorHandler.calcul_without_offer(workData , offer.getDistance()));
			//			calculatorHandler.
		}
		BBMLogger.infoln(offers.size() + "");
		return offers.stream().filter(offer -> offer.getPrice() < filtersObject.getMaxPrice()).collect(Collectors.toList());
	}

	public String validatePrice(String filters, String workData) {
		BBMLogger.infoln("Processing...");
		Filters fil = JsonUtils.getFromJson(filters, Filters.class);
		int distance = pathHandler.getPathDistances(fil.startAddress, fil.endAddress);
		List<PostedOffer> offers = offerService.getAvailableOffers(QueryEngine.buildMongoQuery(distance));
		Utils range = offerService.checkPrice(offers, distance);

		return (fil.maxPrice  < range.getSupValue() && fil.maxPrice > range.getInfValue()) ? "OK" : "NOK";
	}
	//TODO rename 
	//this is the method to ask the offer to get accepted by ALICE
	public String askValidate(String workData) {
		BBMLogger.infoln("Processing...");
		JsonObject json =  JsonUtils.getFromJson(workData);
		String offerID = json.get("offerID").getAsString();
		String buyerID = json.get("buyerID").getAsString();

		List<PostedOffer> offers = offerService.getOfferByID(offerID);
		if(!offers.isEmpty()  && offers.get(0).getStatus() == OfferStatus.POSTED) {
			PostedOffer offer = offers.get(0);
			int newPrice =  offer.getPrice() 
					+ calculatorHandler.calcul_without_offer(workData , offer.getDistance());	

			usersHandler.sendMail(offer.getOwnerID(),newPrice,buyerID);
			OffersTransaction offerTransaction =  new OffersTransaction();
			offerService.remove(offer);
			//TODO complexify ID
			offerTransaction.setTransactionID("" + new Date().getTime());
			offerTransaction.setBuyerID(buyerID);
			offerTransaction.setFinalPrice(newPrice);
			offerTransaction.setStatus(OfferStatus.AWAITING_CONFIRMATION);
			offerTransaction.setOfferID(offerID);
			offerTransaction.setOwnerID(offer.getOwnerID());
			offerTransaction.setWeigth(json.get("weight").getAsInt());
			offerTransaction.setVolume(json.get("volume").getAsInt());
			offerTransaction.setDateBeforeOrder(json.get("date").getAsInt());
			offerTransaction.setAskforConfirmationDate(new Date().toString());
			offer.setStatus(OfferStatus.AWAITING_CONFIRMATION);
			offerService.createNewOffer(offer);
			offerTransactionService.createNewOffer(offerTransaction);
			//TODO
			return "Ordering accepted please wait for confirmation now";
		}else {

			return "INVALID OPERATION";
		} 
	}

	//TODO rename 
	//this is the method where Alicia accept an offer
	public String consultAwaitingOffers(String workData) {
		BBMLogger.infoln("Processing...");
		JsonObject json =  JsonUtils.getFromJson(workData);
		String ownerID = json.get("ownerID").getAsString();

		List<OffersTransaction> offers = offerTransactionService.getOffersBy(ownerID).stream().filter(item -> item.getStatus() == OfferStatus.AWAITING_CONFIRMATION).collect(Collectors.toList());
		if(!offers.isEmpty()) {
			return JsonUtils.toJson(offers);
		}

		return "No offers awaiting confirmation";
	}

	//TODO rename 
	//this is the method where Alicia accept an offer
	public String confirmAwaitingOffer(String workData) {
		BBMLogger.infoln("Processing...");
		JsonObject json =  JsonUtils.getFromJson(workData);
		String transactionID = json.get("transactionID").getAsString();

		List<OffersTransaction> offers = offerTransactionService.getOffersByTransactionID(transactionID);

		if(!offers.isEmpty()) {
			OffersTransaction offer = offers.get(0);
			offerTransactionService.remove(offer);
			offer.setStatus(OfferStatus.CONFIRMED);
			offer.setConfirmationDate(new Date().toString());
			//TODO mailing plus fin ? (genre autre method)
			usersHandler.sendMail(offer.getBuyerID(),offer.getFinalPrice(),offer.getOwnerID());
			offerTransactionService.createNewOffer(offer);
			return 	JsonUtils.toJson(offers);
		}

		return "INVALID OPERATION";
	}

	//Todo rename
	public String claimReceipt(String workData) {
		BBMLogger.infoln("Processing...");
		JsonObject json =  JsonUtils.getFromJson(workData);
		String transactionID = json.get("transactionID").getAsString();

		List<OffersTransaction> offers = offerTransactionService.getOffersByTransactionID(transactionID);

		if(!offers.isEmpty()) {
			OffersTransaction offer = offers.get(0);
			offerTransactionService.remove(offer);
			offer.setStatus(OfferStatus.AWAITING_RECEIPT_CONFIRMATION);
			offer.setClientDepositDate(new Date().toString());
			offerTransactionService.createNewOffer(offer);
			return 	JsonUtils.toJson(offer);
		}
		return "INVALID OPERATION";
	}

	//Todo rename
	public String confirmReceipt(String workData) {
		BBMLogger.infoln("Processing...");
		JsonObject json =  JsonUtils.getFromJson(workData);
		String transactionID = json.get("transactionID").getAsString();

		List<OffersTransaction> offers = offerTransactionService.getOffersByTransactionID(transactionID);

		if(!offers.isEmpty()) {
			OffersTransaction offer = offers.get(0);
			offerTransactionService.remove(offer);
			offer.setStatus(OfferStatus.RECEIPT_DONE);
			offer.setClientDepositConfimationDate(new Date().toString());
			offerTransactionService.createNewOffer(offer);
			return 	JsonUtils.toJson(offers);
		}
		return "INVALID OPERATION";
	}

	public String claimDeposit(String workData) {
		BBMLogger.infoln("Processing...");
		JsonObject json =  JsonUtils.getFromJson(workData);
		String transactionID = json.get("transactionID").getAsString();

		List<OffersTransaction> offers = offerTransactionService.getOffersByTransactionID(transactionID);

		if(!offers.isEmpty()) {
			OffersTransaction offer = offers.get(0);
			offerTransactionService.remove(offer);
			offer.setStatus(OfferStatus.AWAITING_DEPOSIT_CONFIRMATION);
			offer.setClientDepositConfimationDate(new Date().toString());
			offerTransactionService.createNewOffer(offer);
			return 	JsonUtils.toJson(offers);
		}
		return "INVALID OPERATION";
	}

	public String confirmDeposit(String workData) {
		BBMLogger.infoln("Processing...");
		JsonObject json =  JsonUtils.getFromJson(workData);
		String transactionID = json.get("transactionID").getAsString();

		List<OffersTransaction> offers = offerTransactionService.getOffersByTransactionID(transactionID);

		if(!offers.isEmpty()) {
			OffersTransaction offer = offers.get(0);
			offerTransactionService.remove(offer);
			offer.setStatus(OfferStatus.CLOSED);
			offer.setClientDepositConfimationDate(new Date().toString());
			offerTransactionService.createNewOffer(offer);
			usersHandler.debit(offer.getBuyerID(),offer.getFinalPrice());
			usersHandler.credit(offer.getOwnerID(),offer.getFinalPrice());
			List<PostedOffer> offersI = offerService.getOfferByID(offer.getOfferID());
			PostedOffer postoff = offersI.get(0);
			offerService.remove(postoff);
			postoff.setStatus(OfferStatus.CLOSED);
			offerService.createNewOffer(postoff);				
			return JsonUtils.toJson(offers);
		}
		return "INVALID OPERATION";
	}
}
