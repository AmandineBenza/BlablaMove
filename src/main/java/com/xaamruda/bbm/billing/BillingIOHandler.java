package com.xaamruda.bbm.billing;

import com.google.gson.JsonObject;
import com.xaamruda.bbm.commons.json.JsonUtils;

import org.springframework.stereotype.Component;

import com.xaamruda.bbm.billing.calculator.Calculator;

/**
 * Class for Calculator entry point.
 */
@Component
public class BillingIOHandler {
	
	private Calculator facturation = new Calculator();
	
	public BillingIOHandler() {
		//
	}

	/**
	 * do work depending of event
	 * 
	 * @param workData Json containing data for the calculator
	 * @return the calculation of point for user
	 */
//	public String mainHandler(String workData) {
////		JsonObject data = JsonUtils.getFromJson(workData);
////		if (data.get("event").getAsString().equals("calcul_without_offer")) {
////			return calcul_without_offer(data);
////		} else if (JsonUtils.getFromJson(workData).get("event").getAsString().equals("calcul_with_offer")) {
////			return calcul_with_offer(data);
////		} else {
////			return "error : Wrong event";
////		}r
//	}

	//TODO 
	/**
	 * calcul point repartition with an ....
	 * 
	 * @param workData Json containing data for the calculator
	 * @return the calculation of point for user
	 */
	public int calcul_without_offer(String workData, int distance) {
		JsonObject data =  JsonUtils.getFromJson(workData);
		facturation.calcul_price_base(data.get("weight").getAsInt(), distance,
				data.get("volume").getAsInt(), data.get("date").getAsInt());
		return  (int) facturation.getUserPoints();
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
		JsonObject data =  JsonUtils.getFromJson(workData);
		facturation.advance_date_with_offer(data.get("date").getAsInt(), data.get("offerPrice").getAsInt());
		return JsonUtils.toJson(
				"{ userPoint : " + facturation.getUserPoints() + ", socityPoint : " + facturation.getCompanyPoints());
	}
}