package com.xaamruda.bbm.billing.calculator;

public interface ICalculator{

	public void calcul_price_base(double weight,double size, double distance,double day);
	public void advance_date_with_offer(double date,int offer);
	public void finalConfirmation(double point);
	public void reset();
}

