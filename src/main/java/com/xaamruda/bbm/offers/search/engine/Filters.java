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
	public int weith;

	// TODO mocked for now
	// Mandatory field
	// public int

	public int maxPrice;

	public OfferStatus status;
	
	public int distance; 
	
	public Filters(){
		
	}

	public Filters(String startAddress, String endAddress, int weith, int maxPrice, OfferStatus status) {
		this.startAddress = startAddress;
		this.endAddress = endAddress;
		this.weith = weith;
		this.maxPrice = maxPrice;
		this.status = status;
	}

}
