package com.xaamruda.bbm.offers;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.google.gson.JsonObject;
import com.xaamruda.bbm.billing.BillingIOHandler;
import com.xaamruda.bbm.commons.json.JsonUtils;
import com.xaamruda.bbm.commons.logging.BBMLogger;
import com.xaamruda.bbm.offers.dbaccess.services.IOfferService;
import com.xaamruda.bbm.offers.dbaccess.services.IOffersTransactionService;
import com.xaamruda.bbm.offers.model.OfferStatus;
import com.xaamruda.bbm.offers.model.OffersTransaction;
import com.xaamruda.bbm.offers.model.PostedOffer;
import com.xaamruda.bbm.offers.search.engine.QueryEngine;
import com.xaamruda.bbm.offers.utils.Range;
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
		PostedOffer offer = JsonUtils.getFromJson(jsonObject, PostedOffer.class);
		offer.setOfferID(offer.getOwnerID() + new Date().getTime() + "_" + offer.getPrice());
		
		int distance = pathHandler.getPathDistances(offer.getStartCity(), offer.getEndCity());

		List<PostedOffer> offers = offerService.getAvailableOffers(QueryEngine.buildMongoQuery(distance));

		Range range = calculatorHandler.checkPrice(offers, distance);
		BBMLogger.infoln("Authorized price range is [" + range.getInfValue() + " : " + range.getSupValue() + "]");

		if ((offer.getPrice() < range.getSupValue() && offer.getPrice() > range.getInfValue())) {
			BBMLogger.infoln("Setting offer...");
			offer.setDistance(distance);
			BBMLogger.infoln("Offer distance set.");
			offer.setStatus(OfferStatus.POSTED);
			BBMLogger.infoln("Offer status set to " + OfferStatus.POSTED);
			offerService.createNewOffer(offer);
			BBMLogger.infoln("Offer created. Content:");
			String json = JsonUtils.toJson(offer);
			BBMLogger.infoln(json);
			return json;
		}
		return "Incorrect price ! For the distance the authorized amount is [" + range.getInfValue() + " : "
		+ range.getSupValue() + "]\n";
	}

	public List<PostedOffer> retrieveOffers(String filters, String workData) {
		Filters filtersObject = JsonUtils.getFromJson(filters, Filters.class);
		List<PostedOffer> offers = offerService.getAvailableOffers(QueryEngine.buildMongoQuery(filtersObject));

		BBMLogger.infoln("Calculating offers prices...");
		for (PostedOffer offer : offers) {
			offer.setPrice(offer.getPrice() + calculatorHandler.calcul_without_offer(workData, offer.getDistance()));
		}

		BBMLogger.infoln(offers.size() + " offers processed.");
		BBMLogger.infoln("Filtering offers by prices...");
		return offers.stream().filter(offer -> offer.getPrice() < filtersObject.getMaxPrice())
				.collect(Collectors.toList());
	}

	public String validatePrice(String filters, String workData) {
		Filters fil = JsonUtils.getFromJson(filters, Filters.class);
		int distance = pathHandler.getPathDistances(fil.startAddress, fil.endAddress);
		List<PostedOffer> offers = offerService.getAvailableOffers(QueryEngine.buildMongoQuery(distance));
		Range range = calculatorHandler.checkPrice(offers, distance);

		return (fil.maxPrice < range.getSupValue() && fil.maxPrice > range.getInfValue()) ? "Correct price ! For the distance the authorized amount is [" + range.getInfValue() + " : "
				+ range.getSupValue() + "]\n" : "Incorrect price ! For the distance the authorized amount is [" + range.getInfValue() + " : "
				+ range.getSupValue() + "]\n";
	}

	// TODO rename
	// this is the method to ask the offer to get accepted by ALICE
	public String askValidate(String workData) {
		JsonObject json = JsonUtils.getFromJson(workData);
		String offerID = json.get("offerID").getAsString();
		String buyerID = json.get("buyerID").getAsString();

		List<PostedOffer> offers = offerService.getOfferByID(offerID);
		if (!offers.isEmpty() && offers.get(0).getStatus() == OfferStatus.POSTED) {
			PostedOffer offer = offers.get(0);

			BBMLogger.infoln("Computing pricing...");
			int newPrice = offer.getPrice() + calculatorHandler.calcul_without_offer(workData, offer.getDistance());

			usersHandler.sendMail(offer.getOwnerID(), newPrice, buyerID);
			OffersTransaction offerTransaction = new OffersTransaction();
			offerService.remove(offer);

			BBMLogger.infoln("Creating offer transaction...");

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
			return JsonUtils.toJson(offerTransaction);
		} else {
			return "INVALID OPERATION\n";
		}
	}

	// TODO rename
	// this is the method where Alicia accept an offer
	public String consultAwaitingOffers(String workData) {
		JsonObject json = JsonUtils.getFromJson(workData);
		String ownerID = json.get("ownerID").getAsString();

		List<OffersTransaction> offers = offerTransactionService.getOffersBy(ownerID).stream()
				.filter(item -> item.getStatus() == OfferStatus.AWAITING_CONFIRMATION).collect(Collectors.toList());
		if (!offers.isEmpty()) {
			BBMLogger.infoln("Retrieved offers according to owner identifier.");
			return JsonUtils.toJson(offers);
		}

		return "No offers waiting for confirmation.\n";
	}

	// Alicia accepts an offer
	public String confirmAwaitingOffer(String workData) {
		BBMLogger.infoln("Processing...");
		JsonObject json = JsonUtils.getFromJson(workData);
		String transactionID = json.get("transactionID").getAsString();

		List<OffersTransaction> offers = offerTransactionService.getOffersByTransactionID(transactionID);

		if (!offers.isEmpty()) {
			BBMLogger.infoln("Retrieved offers according to transaction identifier.");
			OffersTransaction offer = offers.get(0);
			offerTransactionService.remove(offer);
			offer.setStatus(OfferStatus.CONFIRMED);
			BBMLogger.infoln("Updated offer status to \"" + OfferStatus.CONFIRMED + "\".");
			offer.setConfirmationDate(new Date().toString());
			// TODO mailing plus fin ? (genre autre method)
			usersHandler.sendMail(offer.getBuyerID(), offer.getFinalPrice(), offer.getOwnerID());
			offerTransactionService.createNewOffer(offer);
			return JsonUtils.toJson(offers);
		}

		return "INVALID OPERATION\n";
	}

	public String claimReceipt(String workData) {
		BBMLogger.infoln("Processing...");
		JsonObject json = JsonUtils.getFromJson(workData);
		String transactionID = json.get("transactionID").getAsString();

		List<OffersTransaction> offers = offerTransactionService.getOffersByTransactionID(transactionID);

		if (!offers.isEmpty()) {
			BBMLogger.infoln("Retrieved offers according to transaction identifier.");
			OffersTransaction offer = offers.get(0);
			offerTransactionService.remove(offer);
			offer.setStatus(OfferStatus.AWAITING_RECEIPT_CONFIRMATION);
			BBMLogger.infoln("Updated offer status to \"" + OfferStatus.AWAITING_RECEIPT_CONFIRMATION + "\".");
			offer.setClientDepositDate(new Date().toString());
			offerTransactionService.createNewOffer(offer);
			return JsonUtils.toJson(offer);
		}
		return "INVALID OPERATION\n";
	}

	public String confirmReceipt(String workData) {
		BBMLogger.infoln("Processing...");
		JsonObject json = JsonUtils.getFromJson(workData);
		String transactionID = json.get("transactionID").getAsString();

		List<OffersTransaction> offers = offerTransactionService.getOffersByTransactionID(transactionID);

		if (!offers.isEmpty()) {
			BBMLogger.infoln("Retrieved offers according to transaction identifier.");
			OffersTransaction offer = offers.get(0);
			offerTransactionService.remove(offer);
			offer.setStatus(OfferStatus.RECEIPT_DONE);
			BBMLogger.infoln("Updated offer status to \"" + OfferStatus.RECEIPT_DONE + "\".");
			offer.setClientDepositConfimationDate(new Date().toString());
			offerTransactionService.createNewOffer(offer);
			return JsonUtils.toJson(offers);
		}
		return "INVALID OPERATION\n";
	}

	public String claimDeposit(String workData) {
		BBMLogger.infoln("Processing...");
		JsonObject json = JsonUtils.getFromJson(workData);
		String transactionID = json.get("transactionID").getAsString();

		List<OffersTransaction> offers = offerTransactionService.getOffersByTransactionID(transactionID);

		if (!offers.isEmpty()) {
			BBMLogger.infoln("Retrieved offers according to transaction identifier.");
			OffersTransaction offer = offers.get(0);
			offerTransactionService.remove(offer);
			offer.setStatus(OfferStatus.AWAITING_DEPOSIT_CONFIRMATION);
			BBMLogger.infoln("Updated offer status to \"" + OfferStatus.AWAITING_DEPOSIT_CONFIRMATION + "\".");
			offer.setClientDepositConfimationDate(new Date().toString());
			offerTransactionService.createNewOffer(offer);
			return JsonUtils.toJson(offers);
		}

		return "INVALID OPERATION\n";
	}

	public String confirmDeposit(String workData) {
		BBMLogger.infoln("Processing...");
		JsonObject json = JsonUtils.getFromJson(workData);
		String transactionID = json.get("transactionID").getAsString();

		List<OffersTransaction> offers = offerTransactionService.getOffersByTransactionID(transactionID);

		if (!offers.isEmpty()) {
			BBMLogger.infoln("Retrieved offers according to transaction identifier.");
			OffersTransaction offer = offers.get(0);
			offerTransactionService.remove(offer);
			offer.setStatus(OfferStatus.CLOSED);
			offer.setClientDepositConfimationDate(new Date().toString());
			offerTransactionService.createNewOffer(offer);

			BBMLogger.infoln("Performing transaction, withdrawal account: " + offer.getBuyerID()
			+ ", deposit account: " + offer.getOwnerID() + ".");
			usersHandler.debit(offer.getBuyerID(), offer.getFinalPrice());
			usersHandler.credit(offer.getOwnerID(), offer.getFinalPrice());
			BBMLogger.infoln("Done.");

			List<PostedOffer> offersI = offerService.getOfferByID(offer.getOfferID());
			PostedOffer postoff = offersI.get(0);
			offerService.remove(postoff);
			postoff.setStatus(OfferStatus.CLOSED);
			offerService.createNewOffer(postoff);

			BBMLogger.infoln("Updated offer status to \"" + OfferStatus.CLOSED + "\".");
			return JsonUtils.toJson(offers);
		}
		return "INVALID OPERATION\n";
	}
}
