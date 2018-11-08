package com.xaamruda.bbm.offers.utils;

public class Range {
	private int supValue;
	private int infValue;
	private int averageValue;

	public Range(int supValue, int infValue, int averageValue) {
		this.supValue = supValue;
		this.infValue = infValue;
		this.averageValue = averageValue;
	}

	// ----------------------GETTERS---------------------
	public int getSupValue() {
		return supValue;
	}

	public void setSupValue(int supValue) {
		this.supValue = supValue;
	}

	public int getInfValue() {
		return infValue;
	}

	public void setInfValue(int infValue) {
		this.infValue = infValue;
	}

	public int getAverageValue() {
		return averageValue;
	}

	public void setAverageValue(int averageValue) {
		this.averageValue = averageValue;
	}

}
