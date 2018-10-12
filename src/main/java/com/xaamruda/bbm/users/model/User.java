package com.xaamruda.bbm.users.model;

public class User {

	private long id;
	private long telNumber;
	private long pointsAmount;
	private String address;
	private String mail;
	private String creditCard;
	
	public User(){
		
	}

	public User(long id, long telNumber, long pointsAmount, String address, String mail, String creditCard) {
		this.id = id;
		this.telNumber = telNumber;
		this.pointsAmount = pointsAmount;
		this.address = address;
		this.mail = mail;
		this.creditCard = creditCard;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getTelNumber() {
		return telNumber;
	}

	public void setTelNumber(long telNumber) {
		this.telNumber = telNumber;
	}

	public long getPointsAmount() {
		return pointsAmount;
	}

	public void setPointsAmount(long pointsAmount) {
		this.pointsAmount = pointsAmount;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}
	
}
