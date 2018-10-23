package com.xaamruda.bbm.users.model;

import org.springframework.data.annotation.Id;

//@Entity
//@Table(name = "user_tbl")
public class User {

	@Id
	private String mail;
	private String address;
	private long telNumber;
	private long pointsAmount;
	
	public User(){
		
	}
	
	public User(UserCreationContainer container){
		this();
	}
	
	public User(String mail, String address, long telNumber, long pointsAmount) {
		this.mail = mail;
		this.address = address;
		this.telNumber = telNumber;
		this.pointsAmount = pointsAmount;
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

}
