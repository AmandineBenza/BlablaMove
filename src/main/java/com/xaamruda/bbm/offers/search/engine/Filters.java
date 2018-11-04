package com.xaamruda.bbm.offers.search.engine;

import com.xaamruda.bbm.offers.model.OfferStatus;

//TODO Debate
//TODO complete
public class Filters {

	// Mandatory field
	public String startAddress;

	// Mandatory field
	public String endAddress;

	// Mandatory field
	public int weight;

	// TODO mocked for now
	// Mandatory field
	// public int

	@Override
	public String toString() {
		return "Filters [startAddress=" + startAddress + ", endAddress=" + endAddress + ", weight=" + weight
				+ ", maxPrice=" + maxPrice + ", status=" + status + ", distance=" + distance + "]";
	}

	public int maxPrice;

	public OfferStatus status;
	
	public int distance; 
	
	public Filters(){
		
	}

	public String getStartAddress() {
		return startAddress;
	}

	public void setStartAddress(String startAddress) {
		this.startAddress = startAddress;
	}

	public String getEndAddress() {
		return endAddress;
	}

	public void setEndAddress(String endAddress) {
		this.endAddress = endAddress;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(int maxPrice) {
		this.maxPrice = maxPrice;
	}

	public OfferStatus getStatus() {
		return status;
	}

	public void setStatus(OfferStatus status) {
		this.status = status;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public Filters(String startAddress, String endAddress, int weith, int maxPrice, OfferStatus status) {
		this.startAddress = startAddress;
		this.endAddress = endAddress;
		this.weight = weith;
		this.maxPrice = maxPrice;
		this.status = status;
	}

}
