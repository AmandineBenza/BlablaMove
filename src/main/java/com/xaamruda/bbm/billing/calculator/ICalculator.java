package com.xaamruda.bbm.billing.calculator;

/**
 * External provider connector.
 */
public interface ICalculator{

	public void calcul_price_base(int weight,int size, int distance,int day);
	public void advance_date_with_offer(int date,int offer);
}
