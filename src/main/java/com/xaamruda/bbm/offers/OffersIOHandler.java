package com.xaamruda.bbm.offers;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.google.gson.JsonObject;
import com.xaamruda.bbm.billing.BillingIOHandler;
import com.xaamruda.bbm.commons.exceptions.DatabaseException;
import com.xaamruda.bbm.commons.json.JsonUtils;
import com.xaamruda.bbm.commons.logging.BBMLogger;
import com.xaamruda.bbm.integrity.IntegrityIOHandler;
import com.xaamruda.bbm.offers.dbaccess.service.IOfferService;
import com.xaamruda.bbm.offers.dbaccess.service.IOffersTransactionService;
import com.xaamruda.bbm.offers.model.OfferStatus;
import com.xaamruda.bbm.offers.model.OffersTransaction;
import com.xaamruda.bbm.offers.model.PostedOffer;
import com.xaamruda.bbm.offers.utils.Range;
import com.xaamruda.bbm.roads.RoadsIOHandler;
import com.xaamruda.bbm.users.UsersIOHandler;
import com.xaamruda.bbm.users.mailing.BlablaMailConstants;
import com.xaamruda.bbm.offers.search.engine.Filters;
import com.xaamruda.bbm.offers.search.engine.QueryEngine;

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

	@Autowired
	private IntegrityIOHandler integrityIOHandler;

	public OffersIOHandler() {
	}

	public List<PostedOffer> getOffers() {
		return offerService.getAvailableOffers();
	}

	public String postNewOffer(String jsonObject) throws DatabaseException {
		// add entry to offer journal
		long journalId = integrityIOHandler.addOfferJournalEntry("postNewOffer",
				this.getClass().getSimpleName(), jsonObject);
		
		PostedOffer offer = JsonUtils.getFromJson(jsonObject, PostedOffer.class);
		int distance = pathHandler.getPathDistances(offer.getStartCity(), offer.getEndCity());
		List<PostedOffer> offers = null;

		try {
			offers = offerService.getAvailableOffers(QueryEngine.buildMySqlQuery(distance));
		} catch (Exception ex) {
			throw new DatabaseException("Posting new offer failed while trying to retrieve available offers.");
		}

		Range range = calculatorHandler.checkPrice(offers, distance);
		BBMLogger.infoln("Authorized price range is [" + range.getInfValue() + " : " + range.getSupValue() + "]");

		if ((offer.getPrice() < range.getSupValue() && offer.getPrice() > range.getInfValue())) {
			BBMLogger.infoln("Setting offer...");
			offer.setDistance(distance);
			
			BBMLogger.infoln("Offer distance set.");
			offer.setStatus(OfferStatus.POSTED);
			
			BBMLogger.infoln("Offer status set to " + OfferStatus.POSTED);

			try {
				offerService.saveOffer(offer);
			} catch (Exception e) {
				throw new DatabaseException("Posting new offer failed while trying to create new posted offer.");
			}

			BBMLogger.infoln("Offer created. Content:");
			
			String json = JsonUtils.toJson(offer);
			logJson(offer);
			
			return json;
		}

		String message = "Incorrect price ! For this distance (" + distance + ") the authorized points amount is within ["
				+ range.getInfValue() + " : " + range.getSupValue() + "].\n";
		
		// end journal entry
		integrityIOHandler.endOfferJournalEntry(journalId);
		return message;
	}

	public List<PostedOffer> retrieveOffers(String filters, String workData) throws DatabaseException {
		// add entry to offer journal
		long journalId = integrityIOHandler.addOfferJournalEntry("retrieveOffers", this.getClass().getSimpleName(),
				filters, workData);
		
		Filters filtersObject = JsonUtils.getFromJson(filters, Filters.class);
		List<PostedOffer> offers = null;

		try {
			offers = offerService.getAvailableOffers(QueryEngine.buildMySqlQuery(filtersObject));
		} catch (Exception ex) {
			throw new DatabaseException("Retrieving offers failed while trying to get available offers.");
		}

		BBMLogger.infoln("Calculating offers prices...");

		for (PostedOffer offer : offers) {
			offer.setPrice(offer.getPrice() + calculatorHandler.calcul_without_offer(workData, offer.getDistance()));
		}

		BBMLogger.infoln(offers.size() + " offers processed.");
		BBMLogger.infoln("Filtering offers by prices...");
		
		List<PostedOffer> retrievedOffers = offers.stream().filter(offer -> offer.getPrice() < filtersObject.getMaxPrice())
				.collect(Collectors.toList());
		
		// end journal entry
		integrityIOHandler.endOfferJournalEntry(journalId);
		return retrievedOffers;
	}

	public String validatePrice(String filters, String workData) throws DatabaseException {
		// add entry to offer journal
		long journalId = integrityIOHandler.addOfferJournalEntry("validatePrice", this.getClass().getSimpleName(), filters, workData);

		Filters fil = JsonUtils.getFromJson(filters, Filters.class);
		BBMLogger.infoln("Computing path distance...");

		int distance = pathHandler.getPathDistances(fil.startAddress, fil.endAddress);
		List<PostedOffer> offers = null;

		try {
			offers = offerService.getAvailableOffers(QueryEngine.buildMySqlQuery(distance));
		} catch (Exception ex) {
			throw new DatabaseException("Offers: validating price failed while trying to retrieve available offers");
		}

		BBMLogger.infoln("Checking offers price...");
		Range range = calculatorHandler.checkPrice(offers, distance);
		
		String message = (fil.maxPrice < range.getSupValue() && fil.maxPrice > range.getInfValue())
				? "Correct price ! For the distance the authorized amount is [" + range.getInfValue() + " : "
						+ range.getSupValue() + "]\n"
				: "Incorrect price ! For this distance (" + distance + ") the authorized points amount is within ["
						+ range.getInfValue() + " : " + range.getSupValue() + "]\n";
		
		// end journal entry
		integrityIOHandler.endOfferJournalEntry(journalId);
		return message;
	}

	// This is the method to ask the offer to get accepted by ALICE
	public String askForValidation(String workData) throws DatabaseException {
		// add entry to offer journal
		long journalId = integrityIOHandler.addOfferJournalEntry("askForValidation", this.getClass().getSimpleName(), workData);
		
		String mailSubject = BlablaMailConstants.BLABLA_SUBJECT_ASK_VALIDATION;
		JsonObject json = JsonUtils.getFromJson(workData);
		String offerID = json.get("offerID").getAsString();
		String buyerID = json.get("buyerID").getAsString();

		List<PostedOffer> offers = null;

		try {
			offers = offerService.getOfferByID(offerID);
		} catch (Exception ex) {
			throw new DatabaseException("Offers: ask for validation failed while trying to retrieve offer.");
		}

		if (!offers.isEmpty() && offers.get(0).getStatus() == OfferStatus.POSTED) {
			BBMLogger.infoln("Computing pricing...");

			PostedOffer offer = offers.get(0);
			int newPrice = offer.getPrice() + calculatorHandler.calcul_without_offer(workData, offer.getDistance());
			usersHandler.sendMail(offer.getOwnerID(), newPrice, buyerID, mailSubject);

			OffersTransaction offerTransaction = new OffersTransaction();
			BBMLogger.infoln("Creating offer transaction...");

			// Init offer transaction
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

			// Store offer in database
			try {
				offerService.saveOffer(offer);
				offerTransactionService.saveOffer(offerTransaction);
			} catch (Exception ex) {
				throw new DatabaseException("Offers: ask for validation failed while trying to create new offer.");
			}

			String offersTransactionJson = JsonUtils.toJson(offerTransaction);
			
			// end journal entry
			integrityIOHandler.endOfferJournalEntry(journalId);
			return offersTransactionJson;
		} else {
			// end journal entry
			integrityIOHandler.endOfferJournalEntry(journalId);
			return "INVALID OPERATION\n";
		}
	}

	// this is the method where Alice accepts an offer
	public String consultAwaitingOffers(String workData) throws DatabaseException {
		// add entry to offer journal
		long journalId = integrityIOHandler.addOfferJournalEntry("consultAwaitingOffers",
				this.getClass().getSimpleName(), workData);
		
		JsonObject json = JsonUtils.getFromJson(workData);
		String ownerID = json.get("ownerID").getAsString();
		List<OffersTransaction> offers = null;

		try {
			offers = offerTransactionService.getOffersByOwnerId(ownerID).stream()
					.filter(item -> item.getStatus() == OfferStatus.AWAITING_CONFIRMATION).collect(Collectors.toList());
		} catch (Exception ex) {
			throw new DatabaseException("Offers: consult awaiting offers failed.");
		}

		if (!offers.isEmpty()) {
			BBMLogger.infoln("Retrieved offers according to owner identifier.");
			String offersJson = JsonUtils.toJson(offers);
			// end journal entry
			integrityIOHandler.endOfferJournalEntry(journalId);
			return offersJson;
		}

		integrityIOHandler.endOfferJournalEntry(journalId);
		return "No offers waiting for confirmation.\n";
	}

	// Alice accepts an offer
	public String confirmAwaitingOffer(String workData) throws DatabaseException {
		// add entry to offer journal
		long journalId = integrityIOHandler.addOfferJournalEntry("confirmAwaitingOffer", this.getClass().getSimpleName(), workData);
		
		String mailSubject = BlablaMailConstants.BLABLA_SUBJECT_CONFIRM_OFFER;
		
		JsonObject json = JsonUtils.getFromJson(workData);
		String transactionID = json.get("transactionID").getAsString();
		List<OffersTransaction> offers = null;

		try {
			offers = offerTransactionService.getOffersByTransactionID(transactionID);
		} catch (Exception ex) {
			throw new DatabaseException("Offers: confirm awaiting offer failed while trying to retrieve offers.");
		}

		if (!offers.isEmpty()) {
			BBMLogger.infoln("Retrieved offers according to transaction identifier.");
			BBMLogger.infoln("Updated offer status to \"" + OfferStatus.CONFIRMED + "\".");

			// Update offer's fields
			OffersTransaction offer = offers.get(0);
			offer.setStatus(OfferStatus.CONFIRMED);
			offer.setConfirmationDate(new Date().toString());

			// TODO mailing plus fin ? (genre autre method)
			usersHandler.sendMail(offer.getOwnerID(), offer.getFinalPrice(),offer.getBuyerID(), mailSubject);

			try {
				offerTransactionService.saveOffer(offer);
			} catch (Exception e) {
				throw new DatabaseException("Offers: confirm awaiting offer failed while setting offer status.");
			}

			String offersJson = JsonUtils.toJson(offers); 
			
			// end journal entry
			integrityIOHandler.endOfferJournalEntry(journalId);
			
			return offersJson;
		}

		// end journal entry
		integrityIOHandler.endOfferJournalEntry(journalId);
		return "INVALID OPERATION\n";
	}

	public String claimReceipt(String workData) throws DatabaseException {
		// add entry to offer journal
		long journalId = integrityIOHandler.addOfferJournalEntry("claimReceipt", this.getClass().getSimpleName(), workData);
		
		JsonObject json = JsonUtils.getFromJson(workData);
		String transactionID = json.get("transactionID").getAsString();

		List<OffersTransaction> offers = null;

		try {
			offers = offerTransactionService.getOffersByTransactionID(transactionID);
		} catch (Exception ex) {
			throw new DatabaseException("Offers: claim reception failed while trying to retrieve transactions.");
		}

		if (!offers.isEmpty()) {
			BBMLogger.infoln("Retrieved offers according to transaction identifier.");
			BBMLogger.infoln("Updated offer status to \"" + OfferStatus.AWAITING_RECEIPT_CONFIRMATION + "\".");

			// Update offer's fields
			OffersTransaction offer = offers.get(0);
			offer.setStatus(OfferStatus.AWAITING_RECEIPT_CONFIRMATION);
			offer.setClientDepositDate(new Date().toString());

			// Update offer
			try {
				offerTransactionService.saveOffer(offer);
			} catch (Exception e) {
				throw new DatabaseException("Offers: claim reception failed while trying to update transaction.");
			}

			String offerJson = JsonUtils.toJson(offer); 
			
			// end journal entry
			integrityIOHandler.endOfferJournalEntry(journalId);
			return offerJson;
		}
		// end journal entry
		integrityIOHandler.endOfferJournalEntry(journalId);
		return "INVALID OPERATION\n";
	}

	public String confirmReceipt(String workData) throws DatabaseException {
		// add entry to offer journal
		long journalId = integrityIOHandler.addOfferJournalEntry("confirmReceipt", this.getClass().getSimpleName(), workData);
		
		JsonObject json = JsonUtils.getFromJson(workData);
		String transactionID = json.get("transactionID").getAsString();

		List<OffersTransaction> offers = null;

		try {
			offers = offerTransactionService.getOffersByTransactionID(transactionID);
		} catch (Exception ex) {
			throw new DatabaseException("Offers: confirm reception failed while trying to retrieve transactions.");
		}

		if (!offers.isEmpty()) {
			BBMLogger.infoln("Retrieved offers according to transaction identifier.");
			BBMLogger.infoln("Updated offer status to \"" + OfferStatus.RECEIPT_DONE + "\".");

			// Update offer's fields
			OffersTransaction offer = offers.get(0);
			offer.setStatus(OfferStatus.RECEIPT_DONE);
			offer.setClientDepositConfimationDate(new Date().toString());

			// Update offer in DB
			try {
				offerTransactionService.saveOffer(offer);
			} catch (Exception e) {
				throw new DatabaseException("Offers: confirm reception failed while trying to update transaction.");
			}

			String jsonOffers = JsonUtils.toJson(offers); 
			
			// end journal entry
			integrityIOHandler.endOfferJournalEntry(journalId);
			return jsonOffers;
		}
		
		// end journal entry
		integrityIOHandler.endOfferJournalEntry(journalId);
		return "INVALID OPERATION\n";
	}

	public String claimDeposit(String workData) throws DatabaseException {
		// add entry to offer journal
		long journalId = integrityIOHandler.addOfferJournalEntry("claimDeposit", this.getClass().getSimpleName(), workData);
		
		JsonObject json = JsonUtils.getFromJson(workData);
		String transactionID = json.get("transactionID").getAsString();
		List<OffersTransaction> offers = null;

		try {
			offers = offerTransactionService.getOffersByTransactionID(transactionID);
		} catch (Exception ex) {
			throw new DatabaseException("Offers: claim deposit failed while trying to retrieve transactions.");
		}

		if (!offers.isEmpty()) {
			BBMLogger.infoln("Retrieved offers according to transaction identifier.");
			OffersTransaction offer = offers.get(0);

			BBMLogger.infoln("Updated offer status to \"" + OfferStatus.AWAITING_DEPOSIT_CONFIRMATION + "\".");
			offer.setStatus(OfferStatus.AWAITING_DEPOSIT_CONFIRMATION);
			offer.setClientDepositConfimationDate(new Date().toString());

			try {
				offerTransactionService.saveOffer(offer);
			} catch (Exception ex) {
				integrityIOHandler.addOfferJournalEntry("claimDeposit", this.getClass().getSimpleName(), workData);
				throw new DatabaseException("Offers: claim deposit failed while trying to update transaction.");
			}

			String offersJson = JsonUtils.toJson(offers);
			
			// end journal entry
			integrityIOHandler.endOfferJournalEntry(journalId);
			return offersJson;
		}

		// end journal entry
		integrityIOHandler.endOfferJournalEntry(journalId);
		return "INVALID OPERATION\n";
	}

	@Transactional()
	public String confirmDeposit(String workData) throws DatabaseException {
		// add entry to offer journal
		long journalId = integrityIOHandler.addOfferJournalEntry("confirmDeposit", this.getClass().getSimpleName(), workData);
		
		JsonObject json = JsonUtils.getFromJson(workData);
		String transactionID = json.get("transactionID").getAsString();
		List<OffersTransaction> offers = null;

		try {
			offers = offerTransactionService.getOffersByTransactionID(transactionID);
		} catch (Exception ex) {
			throw new DatabaseException("Offers: confirm deposit failed while trying to retrieve transactions.");
		}

		if (!offers.isEmpty()) {
			// We get the first element
			BBMLogger.infoln("Retrieved offer according to transaction identifier.");
			OffersTransaction offer = offers.get(0);

			// Set the offer's new parameters
			offer.setStatus(OfferStatus.CLOSED);
			offer.setClientDepositConfimationDate(new Date().toString());

			try {
				offerTransactionService.saveOffer(offer);
			} catch (Exception e) {
				integrityIOHandler.addOfferJournalEntry("confirmDeposit", this.getClass().getSimpleName(), workData);
				BBMLogger.errorln("Offers: confirm deposit failed while trying update offer."
						+ "New entry added to offer journal.");
				return null;
			}

			BBMLogger.infoln("Performing transaction, withdrawal account: " + offer.getBuyerID() + ", deposit account: "
					+ offer.getOwnerID() + ".");

			usersHandler.makeTransaction(offer.getOwnerID(), offer.getBuyerID(), offer.getFinalPrice());
			BBMLogger.infoln("Done.");

			List<PostedOffer> offersI = null;

			try {
				offersI = offerService.getOfferByID(offer.getOfferID());
			} catch (Exception ex) {
				integrityIOHandler.addOfferJournalEntry("confirmDeposit", this.getClass().getSimpleName(), workData);
				BBMLogger.errorln("Offers: confirm deposit failed while trying to retrieve offer."
						+ "New entry added to offer journal.");
				return null;
			}

			// Set the offer status to closed
			PostedOffer postoff = offersI.get(0);
			postoff.setStatus(OfferStatus.CLOSED);

			try {
				offerService.saveOffer(postoff);
			} catch (Exception e) {
				integrityIOHandler.addOfferJournalEntry("confirmDeposit", this.getClass().getSimpleName(), workData);
				BBMLogger.errorln("Offers: confirm deposit failed while trying to update offer."
						+ "New entry added to offer journal.");
				return null;
			}

			String offersJson = JsonUtils.toJson(offers); 
			BBMLogger.infoln("Updated offer status to \"" + OfferStatus.CLOSED + "\".");
			
			// end journal entry
			integrityIOHandler.endOfferJournalEntry(journalId);
			return offersJson;
		}
		
		// end journal entry
		integrityIOHandler.endOfferJournalEntry(journalId);
		return "INVALID OPERATION\n";
	}

	private void logJson(PostedOffer offer) {
		BBMLogger.infoln("{\"offerID\": \"" + offer.getOfferID() + "\",");
		BBMLogger.infoln("\"ownerID\": \"" + offer.getOwnerID() + "\",");
		BBMLogger.infoln("\"price\": \"" + offer.getPrice() + "\",");
		BBMLogger.infoln("\"startCity\": \"" + offer.getStartCity() + "\",");
		BBMLogger.infoln("\"endCity\": \"" + offer.getEndCity() + "\",");
		BBMLogger.infoln("\"capacity\": \"" + offer.getCapacity() + "\",");
		BBMLogger.infoln("\"status\": \"" + offer.getStatus() + "\",");
		BBMLogger.infoln("\"distance\": \"" + offer.getDistance() + "\"}");
	}

	public void shutDownDB() {
		offerService.shutDown();
	}
}
