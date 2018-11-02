package com.xaamruda.bbm.billing;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.xaamruda.bbm.billing.calculator.Calculator;
import com.xaamruda.bbm.offers.model.Offer;
import com.xaamruda.bbm.offers.model.OffersTransaction;
import com.xaamruda.bbm.offers.model.PostedOffer;

/**
 * Class for Calculator entry point.
 */
@Component
public class BillingIOHandler {
	Calculator facturation = new Calculator();

	/**
	 * calcul point repartition with an ....
	 * @param workData string of the object user
	 * @return the calculation of point for user
	 */
	//TODO distance
	public String calcul_without_offer(String workData) {
		OffersTransaction offer = workData_Parser(workData);
		facturation.calcul_price_base(offer.getWeigth(),0,offer.getVolume(),offer.getDateBeforeOrder());
		offer.setFinalPrice((int)facturation.getUserPoint());
		return reponse_flow_parser(offer);
	}

	/**
	 * calcul point repartition with an offer
	 * @param workData string of the object user
	 * @param offer object for offer
	 * @return the calculation of point for user
	 */
	public String calcul_with_offer(String workData, PostedOffer offer) {
		OffersTransaction user = workData_Parser(workData);
		facturation.advance_date_with_offer(offer.getPrice(),user.getFinalPrice());
		user.setFinalPrice((int) facturation.getUserPoint());
		return reponse_flow_parser(offer);
	}
	/**
	 * parse the response that will be send
	 * @param type object we want to parse in the response
	 * @return String of the object
	 */
	public String reponse_flow_parser(Object type) {
		String res = "";
		Gson gson = new Gson();
		res = gson.toJson(type);
		return res;
	}

	/**
	 * parser to converst string to the object user
	 * @param workData string of the object user
	 * @return the object user
	 */
	private OffersTransaction workData_Parser(String workData) {
		Gson gson = new Gson();
		OffersTransaction res = gson.fromJson(workData, OffersTransaction.class);
		return res;
	}
}