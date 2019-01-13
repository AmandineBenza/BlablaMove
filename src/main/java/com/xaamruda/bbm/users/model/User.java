package com.xaamruda.bbm.users.model;

import javax.persistence.Id;
import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Integer id;
	private String mail;
	private String name;
	private String address;
	private String phone;
	private String password;
	private Integer pointsAmount;
	private boolean identified;
	private Timestamp identificationTime;
	

	public User(){}

	public User(String mail, String name, String address, String phone, String password, Integer pointsAmount) {
		this.mail = mail;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.password = password;
		this.pointsAmount = pointsAmount;
		this.identified = false;
		this.identificationTime = null;
	}
	
	public boolean isIdentified() {
		return identified;
	}

	public void setIdentified(boolean identified) {
		if(identified && !(this.identified)) {
			 this.identificationTime = new Timestamp(System.currentTimeMillis());
		}
		this.identified = identified;
	}
	
	public Timestamp getIdentificationTime() {
		return identificationTime;
	}

	public void setIdentificationTime(Timestamp identificationTime) {
		this.identificationTime = identificationTime;
	}

	public Integer getId(){return id;}

	public void setId(int newId){
		id = newId;
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
