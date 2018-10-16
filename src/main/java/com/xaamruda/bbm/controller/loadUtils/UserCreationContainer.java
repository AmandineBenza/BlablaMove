package com.xaamruda.bbm.controller.loadUtils;

public class UserCreationContainer {
	
	private String mail;
	private String address;
	private String creditCard;
	private long telNumber;
	
	public UserCreationContainer() {};
	
	public UserCreationContainer(String mail, String address, String creditCard, long telNumber) {
		this.mail = mail;
		this.address = address;
		this.creditCard = creditCard;
		this.telNumber = telNumber;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}

	public long getTelNumber() {
		return telNumber;
	}

	public void setTelNumber(long telNumber) {
		this.telNumber = telNumber;
	}
	
}
