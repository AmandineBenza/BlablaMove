package main.java.com.xaamruda.bbm.billing;

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
	 * calcul point repartition with an ....
	 * @param workData string of the object user
	 * @return the calculation of point for user
	 */
	public Offer calcul_without_offer(String workData) {
		Offer user = workData_Parser(workData);
		facturation.calcul_price_base(user.getWeight(),user.getDistance(),user.getVolume(),user.getDay());
		user.setFinalPrice(facturation.getUserPoint());
		return reponse_flow_parser(user);
	}

	/**
	 * calcul point repartition with an offer
	 * @param workData string of the object user
	 * @param offer object for offer
	 * @return the calculation of point for user
	 */
	public Offer calcul_with_offer(String workData, Offer offer) {
		Offer user = workData_Parser(workData);
		facturation.advance_date_with_offer(offer.getPrice(),user.getFinalPrice(),user.getDay());
		user.setFinalPrice(facturation.getUserPoint());
		return reponse_flow_parser(offer);
	}
	/**
	 * parse the response that will be send
	 * @param type object we want to parse in the response
	 * @return String of the object
	 */
	public String reponse_flow_parser(Class<?> type) {
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
	private Offer workData_Parser(String workData) {
		Gson gson = new Gson();
		UserOffer res = gson.fromJson(workData,UserOffer.class);
		return res;
	}
}