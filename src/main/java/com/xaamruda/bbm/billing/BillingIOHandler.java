package com.xaamruda.bbm.billing;

import com.google.gson.JsonObject;
import com.xaamruda.bbm.commons.json.JsonUtils;
import com.xaamruda.bbm.integrity.IntegrityIOHandler;
import com.xaamruda.bbm.offers.model.PostedOffer;
import com.xaamruda.bbm.offers.utils.Range;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xaamruda.bbm.billing.calculator.Calculator;
import com.xaamruda.bbm.billing.calculator.MediumCalculator;

/**
 * Class for Calculator entry point.
 */
@Component
public class BillingIOHandler {
	
	private Calculator facturation = new Calculator();
	
	@Autowired
	private IntegrityIOHandler integrityIOHandler;
	
	public BillingIOHandler() {
		//
	}

	//TODO 
	/**
	 * calcul point repartition with an ....
	 * 
	 * @param workData Json containing data for the calculator
	 * @return the calculation of point for user
	 */
	public int calcul_without_offer(String workData, int distance) {
		long journalId = integrityIOHandler.addBillingJournalEntry("calcul_without_offer", this.getClass().getSimpleName(), workData, distance);
		
		JsonObject data =  JsonUtils.getFromJson(workData);
		facturation.calcul_price_base(data.get("weight").getAsDouble(), distance,
				data.get("volume").getAsDouble(), data.get("date").getAsInt());
		integrityIOHandler.endBillingJournalEntry(journalId);
		int userPoints = (int) facturation.getUserPoints();
		integrityIOHandler.endBillingJournalEntry(journalId);
		return userPoints;
//				JsonUtils.toJson(
//				"{ userPoint : " + facturation.getUserPoints() + ", socityPoint : " + facturation.getCompanyPoints());
	}

	/**
	 * calcul point repartition with an offer
	 * 
	 * @param workData Json containing data for the calculator
	 * @return the calculation of point for user
	 */
	public String calcul_with_offer(String workData) {
		long journalId = integrityIOHandler.addBillingJournalEntry("calcul_with_offer", this.getClass().getSimpleName(), workData);
		
		JsonObject data =  JsonUtils.getFromJson(workData);
		facturation.advance_date_with_offer(data.get("date").getAsInt(), data.get("offerPrice").getAsInt());
		facturation.finalConfirmation(facturation.getUserPoints());
		String json = JsonUtils.toJson("{\"userPoints\":\"" + facturation.getUserPoints() + "\",\"companyPoints\":\"" + facturation.getCompanyPoints() + "\"}");
		
		integrityIOHandler.endBillingJournalEntry(journalId);
		return json;
	}
	
	public Range checkPrice(List<PostedOffer> offers, int distance){
		return MediumCalculator.getInstance().compute(offers, distance);
	}
	
}