package com.xaamruda.bbm.offers.model;

import org.springframework.data.annotation.Id;

public class OffersTransaction {
		
    @Id
  //  @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer offerID;
   
    private Integer buyerID;
    private Integer volume; 
    private Integer weigth; 
    private Integer finalPrice;

    private String askforConfirmationDate;
    private String confirmationDate;
    private String clientDepositDate;
    private String packageArrivalDate;
    private String depositConfirmationDate; 
        
    
    private OfferStatus status;


	public Integer getOfferID() {
		return offerID;
	}


	public void setOfferID(Integer offerID) {
		this.offerID = offerID;
	}


	public Integer getBuyerID() {
		return buyerID;
	}


	public void setBuyerID(Integer buyerID) {
		this.buyerID = buyerID;
	}


	public Integer getVolume() {
		return volume;
	}


	public void setVolume(Integer volume) {
		this.volume = volume;
	}


	public Integer getWeigth() {
		return weigth;
	}


	public void setWeigth(Integer weigth) {
		this.weigth = weigth;
	}


	public Integer getFinalPrice() {
		return finalPrice;
	}


	public void setFinalPrice(Integer finalPrice) {
		this.finalPrice = finalPrice;
	}


	public String getAskforConfirmationDate() {
		return askforConfirmationDate;
	}


	public void setAskforConfirmationDate(String askforConfirmationDate) {
		this.askforConfirmationDate = askforConfirmationDate;
	}


	public String getConfirmationDate() {
		return confirmationDate;
	}


	public void setConfirmationDate(String confirmationDate) {
		this.confirmationDate = confirmationDate;
	}


	public String getClientDepositDate() {
		return clientDepositDate;
	}


	public void setClientDepositDate(String clientDepositDate) {
		this.clientDepositDate = clientDepositDate;
	}


	public String getPackageArrivalDate() {
		return packageArrivalDate;
	}


	public void setPackageArrivalDate(String packageArrivalDate) {
		this.packageArrivalDate = packageArrivalDate;
	}


	public String getDepositConfirmationDate() {
		return depositConfirmationDate;
	}


	public void setDepositConfirmationDate(String depositConfirmationDate) {
		this.depositConfirmationDate = depositConfirmationDate;
	}


	public OfferStatus getStatus() {
		return status;
	}


	public void setStatus(OfferStatus status) {
		this.status = status;
	}
    

}
