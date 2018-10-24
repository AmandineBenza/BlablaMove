package com.xaamruda.bbm.billing.calculator;

public class Calculator implements ICalculator {

	private double companyPoints;
	private double userPoints;

	public Calculator() {
		this.companyPoints = 0.0;
		this.userPoints = 0.0;
	}

	@Override
	public void calcul_price_base(int weight, int distance, int volume, int day) {
		double calcul = fullAddition(weight, distance, volume, day);
		this.companyPoints = (calcul / 100) * 10;
		this.userPoints = (calcul / 100) * 90;
	}

	@Override
	public void advance_date_with_offer(int date, int offer){
		int res = 0;
		
		if(date <= 3){
			res = 5 + offer;
		} else {
			res = 2 + offer;
		}
		
		this.companyPoints = (res / 100) * 10;
		this.userPoints = (res / 100) * 90;
	}

	//////////// SETTER ////////////
	public void setSocityPoint(int total) {
		this.companyPoints = total;
	}

	public void setUserPoint(int total) {
		this.companyPoints = total;
	}

	//////////// GETTER ////////////
	public double getSocityPoint() {
		return this.companyPoints;
	}

	public double getUserPoint() {
		return this.userPoints;
	}

	private double fullAddition(double weight, double distance, double volume, double day) {
		double weightPoint = conversionWeight(weight);
		double distancePoint = conversionDistance(distance);
		double volumePoint = conversionVolume(volume);
		double reservationPoint = conversionDay(day);
		return (weightPoint + distancePoint + volumePoint + reservationPoint);
	}

	//////////// CONVERTER ////////////
	private double conversionWeight(double weight) {
		return weight;
	}

	private double conversionDistance(double distance) {
		return distance;
	}

	private double conversionVolume(double volume) {
		return volume;
	}

	private double conversionDay(double day) {
		return day;
	}
}
