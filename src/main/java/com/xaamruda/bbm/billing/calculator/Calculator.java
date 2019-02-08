package com.xaamruda.bbm.billing.calculator;

/**
 * Class for billing calculation
 */
public class Calculator implements ICalculator {

	private double companyPoints;
	private double userPoints;

	public Calculator() {
		this.companyPoints = 0.0;
		this.userPoints = 0.0;
	}

	@Override
	public void calcul_price_base(double weight, double distance, double volume, double day) {
		double calcul = fullAddition(weight, distance, volume, day);
		this.userPoints = (calcul);
	}

	@Override
	public void advance_date_with_offer(double date, int offer){
		double res = offer + conversionDay(date);
		this.userPoints = (res);
	}

	@Override
	public void finalConfirmation(double point){
		this.userPoints = (point/100.0) * 90.0;
		this.companyPoints = (point/100.0) * 10.0;
	}

	@Override
	public void reset(){
		this.userPoints = 0.0;
		this.companyPoints = 0.0;
	}

	//////////// SETTER ////////////
	public void setCompanyPoints(int total) {
		this.companyPoints = total;
	}

	public void setUserPoints(int total) {
		this.companyPoints = total;
	}

	//////////// GETTER ////////////
	public double getCompanyPoints() {
		return this.companyPoints;
	}

	public double getUserPoints() {
		return this.userPoints;
	}



	private double fullAddition(double weight, double distance, double volume, double day) {
		double weightPoint = conversionWeight(weight);
		double distancePoint = conversionDistance(distance);
		double volumePoint = conversionVolume(volume);
		double reservationPoint = conversionDay(day);
		return Math.floor(weightPoint + distancePoint + volumePoint + reservationPoint);
	}

	//////////// CONVERTER ////////////

	private double conversionWeight(double weight) {
		return (weight/1.0);
	}

	private double conversionDistance(double distance) {
		return (distance/1.0);
	}

	private double conversionVolume(double volume) {
		return (volume/1.0);
	}

	private double conversionDay(double day) {
		return day > 3 ? 3.0 : 1.0;
	}
}
