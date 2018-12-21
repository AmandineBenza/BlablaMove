package com.xaamruda.bbm.offers.model;

//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Table;

import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

//@Entity
//@Table(name = "offer")
public class PostedOffer {


	//ID IN MONGO ARE BETTER AS STRING
	//  @GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer offerID;

	private String ownerID;
	private Integer price;
	private String startCity;
	private String endCity;
	private Integer capacity; 
	private OfferStatus status;
	private String days;
	private int distance;
	
	
	public Integer getOfferID() {
		return offerID;
	}

	public void setOfferID(Integer offerID) {
		this.offerID = offerID;
	}
	public String getOwnerID() {
		return ownerID;
	}
	public void setOwnerID(String ownerID) {
		this.ownerID = ownerID;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public String getStartCity() {
		return startCity;
	}
	public void setStartCity(String startCity) {
		this.startCity = startCity;
	}
	public String getEndCity() {
		return endCity;
	}

	public void setEndCity(String endCity) {
		this.endCity = endCity;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
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

	@Override
	public String toString() {
		return "PostedOffer [offerID=" + offerID + ", ownerID=" + ownerID + ", price=" + price + ", startCity="
				+ startCity + ", endCity=" + endCity + ", capacity=" + capacity + ", status=" + status + ", distance="
				+ distance + "]";
	}
}
