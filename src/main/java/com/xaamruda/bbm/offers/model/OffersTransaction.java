package com.xaamruda.bbm.offers.model;

import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

public class OffersTransaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer transactionID;
	private String offerID;
	private String ownerID;
	private String buyerID;
	
	private Integer volume;
	private Integer weigth;
	private Integer finalPrice;
	private Integer dateBeforeOrder;

	private String askforConfirmationDate;
	private String confirmationDate;

	private String clientDepositDate;
	private String clientDepositConfimationDate;

	private String packageArrivalDate;
	private String packageArrivalConfirmation;

	private OfferStatus status;

	public String getOfferID() {
		return offerID;
	}

	public void setOfferID(String offerID) {
		this.offerID = offerID;
	}

	public String getBuyerID() {
		return buyerID;
	}

	public void setBuyerID(String buyerID) {
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

	public OfferStatus getStatus() {
		return status;
	}

	public void setStatus(OfferStatus status) {
		this.status = status;
	}

	public Integer getDateBeforeOrder() {
		return dateBeforeOrder;
	}

	public void setDateBeforeOrder(Integer dateBeforeOrder) {
		this.dateBeforeOrder = dateBeforeOrder;
	}

	public String getOwnerID() {
		return ownerID;
	}

	public void setOwnerID(String ownerID) {
		this.ownerID = ownerID;
	}

	public Integer getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(Integer transactionID) {
		this.transactionID = transactionID;
	}

	public String getClientDepositConfimationDate() {
		return clientDepositConfimationDate;
	}

	public void setClientDepositConfimationDate(String clientDepositConfimationDate) {
		this.clientDepositConfimationDate = clientDepositConfimationDate;
	}

	public String getPackageArrivalConfirmation() {
		return packageArrivalConfirmation;
	}

	public void setPackageArrivalConfirmation(String packageArrivalConfirmation) {
		this.packageArrivalConfirmation = packageArrivalConfirmation;
	}

}
