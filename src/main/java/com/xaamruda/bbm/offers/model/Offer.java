package com.xaamruda.bbm.offers.model;

//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Table;

import org.springframework.data.annotation.Id;
//@Entity
//@Table(name = "offer")
public class Offer {
	
    @Id
  //  @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    //TODO test
    //private Client
    private Integer ownerID;
    private Integer clientID;
    private OfferStatus status;
    private Integer price;
    private String StartAddress;
    private String EndAddress;
    private int distance;
    private Integer capacity; 
    private Integer offerPrice;
    

	//--------------------------------GETTERS--------------------------
    public String getStartAddress() {
		return StartAddress;
	}

	public void setStartAddress(String startAddress) {
		StartAddress = startAddress;
	}

	public String getEndAddress() {
		return EndAddress;
	}

	public void setEndAddress(String endAddress) {
		EndAddress = endAddress;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	
    public Integer getClientID() {
		return clientID;
	}

	public void setClientID(Integer clientID) {
		this.clientID = clientID;
	}

	public OfferStatus getStatus() {
		return status;
	}

	public void setStatus(OfferStatus status) {
		this.status = status;
	}

	public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

	public Integer getOwnerID() {
		return ownerID;
	}

	public void setOwnerID(Integer ownerID) {
		this.ownerID = ownerID;
	}
    
	public Integer getOfferPrice() {
		return offerPrice;
	}
	
	public void setOfferPrice(Integer offerPrice) {
		this.offerPrice = offerPrice;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}
    
	//TODO toString offers

}
