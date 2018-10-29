package com.xaamruda.bbm.roads.model;

public class Path {

	private String departureLocation;
	private String arrivalLocation;
	
	public Path(String departureLocation, String arrivalLocation) {
		this.departureLocation = departureLocation;
		this.arrivalLocation = arrivalLocation;
	}

	public String getDepartureLocation() {
		return departureLocation;
	}

	public void setDepartureLocation(String departureLocation) {
		this.departureLocation = departureLocation;
	}

	public String getArrivalLocation() {
		return arrivalLocation;
	}

	public void setArrivalLocation(String arrivalLocation) {
		this.arrivalLocation = arrivalLocation;
	}
	
}

