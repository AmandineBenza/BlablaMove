package com.xaamruda.bbm.billing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.xaamruda.bbm.commons.json.JsonUtils;
import com.xaamruda.bbm.offers.model.OffersTransaction;
import org.springframework.stereotype.Component;

import com.xaamruda.bbm.billing.calculator.Calculator;
import com.xaamruda.bbm.offers.model.Offer;

/**
 * Class for Calculator entry point.
 */
@Component
public class BillingIOHandler {
	Calculator facturation = new Calculator();

	/**
	 * do work depending of event
	 * @param workData Json containing data for the calculator
	 * @return the calculation of point for user
	 */
	public String mainHandler(String workData){
		JsonObject data = JsonUtils.getFromJson(workData);
		if(data.get("event").equals("calcul_without_offer")){
			return calcul_without_offer(data);
		}else if (JsonUtils.getFromJson(workData).get("event").equals("calcul_with_offer")){
			return calcul_with_offer(data);
		}else{
			return "error : Wrong event";
		}
	}
	/**
	 * calcul point repartition with an ....
	 * @param workData Json containing data for the calculator
	 * @return the calculation of point for user
	 */
	private String calcul_without_offer(JsonObject workData) {
		JsonObject data = workData.get("data").getAsJsonObject();
		facturation.calcul_price_base(data.get("weight").getAsInt(),
				data.get("distance").getAsInt(),
				data.get("volume").getAsInt(),
				data.get("date").getAsInt());
		return JsonUtils.toJson("{ userPoint : " + facturation.getUserPoint() + ", socityPoint : " + facturation.getSocityPoint());
	}

	/**
	 * calcul point repartition with an offer
	 * @param workData Json containing data for the calculator
	 * @return the calculation of point for user
	 */
	private String calcul_with_offer(JsonObject workData) {
		JsonObject data = workData.get("data").getAsJsonObject();
		facturation.advance_date_with_offer(data.get("date").getAsInt(),
				data.get("offerPrice").getAsInt());
		return JsonUtils.toJson("{ userPoint : " + facturation.getUserPoint() + ", socityPoint : " + facturation.getSocityPoint());
	}
}