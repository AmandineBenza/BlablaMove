package com.xaamruda.bbm.integrity.ddos;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class DDOSMetadata {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer metadataID;
	
	private String requestRemoteAddress;
	private boolean banned;
	private long bannedTimeMs;
	private int requestsCount;
	
	public DDOSMetadata() {
		
	}
	
	public Integer getMetadataID() {
		return metadataID;
	}

	public void setMetadataID(Integer metadataID) {
		this.metadataID = metadataID;
	}

	public boolean isBanned() {
		return banned;
	}

	public void setBanned(boolean banned) {
		this.banned = banned;
	}

	public long getBannedTimeMs() {
		return bannedTimeMs;
	}

	public void setBannedTimeMs(long bannedTimeMs) {
		this.bannedTimeMs = bannedTimeMs;
	}

	public String getRequestRemoteAddress() {
		return requestRemoteAddress;
	}

	public void setRequestRemoteAddress(String requestRemoteAddress) {
		this.requestRemoteAddress = requestRemoteAddress;
	}

	public int getRequestsCount() {
		return requestsCount;
	}

	public void setRequestsCount(int requestsCount) {
		this.requestsCount = requestsCount;
	}
	
	public void addRequest() {
		++this.requestsCount;
	}
	
}
