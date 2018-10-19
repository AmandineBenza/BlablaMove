package com.xaamruda.bbm.calculator.core;

public class Calculator implements ICalculator {

	double socityPoint = 0;
	double userPoint = 0;

	public Calculator() {
		this.socityPoint = 0;
		this.userPoint = 0;
	}

	@Override
	public void simpleCalcul(int weight, int distance, int volume, int day) {
		double calcul = fullAddition(weight, distance, volume, day);
		this.socityPoint = (calcul / 100) * 10;
		this.userPoint = (calcul / 100) * 90;
	}

	//////////// SETTER////////////
	public void setSocityPoint(int total) {
		this.socityPoint = total;
	}

	public void setUserPoint(int total) {
		this.socityPoint = total;
	}

	//////////// GETTER////////////
	public double getSocityPoint() {
		return this.socityPoint;
	}

	public double getUserPoint() {
		return this.userPoint;
	}

	private double fullAddition(double weight, double distance, double volume, double day) {
		double weightPoint = conversionWeight(weight);
		double distancePoint = conversionDistance(distance);
		double volumePoint = conversionVolume(volume);
		double reservationPoint = conversionDay(day);
		return (weightPoint + distancePoint + volumePoint + reservationPoint);
	}

	//////////// CONVERTISOR////////////
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
