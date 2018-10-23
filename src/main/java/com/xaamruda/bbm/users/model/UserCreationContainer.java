package com.xaamruda.bbm.users.model;

public class UserCreationContainer {

	private String mail;
	private String address;
	private long telNumber;
	private long pointsAmount;

	public UserCreationContainer() {
	};

	public UserCreationContainer(String mail, String address, long telNumber, long pointsAmount) {
		this.mail = mail;
		this.address = address;
		this.telNumber = telNumber;
		this.pointsAmount = pointsAmount;
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
	
}
