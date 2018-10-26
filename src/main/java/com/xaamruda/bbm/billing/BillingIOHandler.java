package com.xaamruda.bbm.billing;

import org.springframework.stereotype.Component;

import com.xaamruda.bbm.billing.calculator.Calculator;
import com.xaamruda.bbm.offers.model.Offer;

/**
 * Calculator entry point.
 */
@Component
public class BillingIOHandler {
	// TODO DO. Calculations.
	Calculator facturation = new Calculator();

	public void doWork(String workData, Offer offer) {
		// DO things
		offer.setOfferPrice(offer.getPrice() + /* do des truccccc */ 10);
	}

	public int calcul_without_offer(String workData) {
		// TODO
		// facturation.calcul_price_base(weight,distance,volume,day=0);
		return 0;
	}

	public int calcul_with_offer(String workData, Offer offer) {
		// TODO
		// facturation.advance_date_with_offer(int data, int offer);
		return 0;
	}

	public String reponse_flow() {
		// TODO
		return null;
	}

	private int offer_Parser(Offer offer) {
		// TODO
		return 0;
	}

	private int workData_Parser(String workData) {
		// TODO
		return 0;
	}
}