package com.xaamruda.bbm.users.model;

import org.springframework.data.annotation.Id;

public class User {

	@Id
	private String mail;
	private String name;
	private String address;
	private String phone;
	private String password;
	private Integer pointsAmount;
	
	public User(){}

	public User(String mail, String name, String address, String phone, String password, Integer pointsAmount) {
		this.mail = mail;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.password = password;
		this.pointsAmount = pointsAmount;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getPointsAmount() {
		return pointsAmount;
	}

	public void setPointsAmount(Integer pointsAmount) {
		this.pointsAmount = pointsAmount;
	}

}
