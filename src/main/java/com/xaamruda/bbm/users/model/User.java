package com.xaamruda.bbm.users.model;

import org.springframework.data.annotation.Id;

import com.xaamruda.bbm.commons.users.UserCreationContainer;

//@Entity
//@Table(name = "user_tbl")
public class User {

	@Id
	private String mail;
	private String address;
	private String creditCard;
	private long telNumber;
	private long pointsAmount;
	
	public User(){
		
	}
	
	public User(UserCreationContainer container){
		this(container.getTelNumber(), 100, container.getAddress(), container.getMail(), container.getCreditCard());
	}

	public User(long telNumber, long pointsAmount, String address, String mail, String creditCard) {
		this.telNumber = telNumber;
		this.pointsAmount = pointsAmount;
		this.address = address;
		this.mail = mail;
		this.creditCard = creditCard;
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
