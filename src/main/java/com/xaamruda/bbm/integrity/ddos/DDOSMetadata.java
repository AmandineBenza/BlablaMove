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
	
	public DDOSMetadata(Integer metadataID, String requestRemoteAddress, boolean banned, long bannedTimeMs,
			int requestsCount) {
		this.metadataID = metadataID;
		this.requestRemoteAddress = requestRemoteAddress;
		this.banned = banned;
		this.bannedTimeMs = bannedTimeMs;
		this.requestsCount = requestsCount;
	}
	
	public DDOSMetadata(String requestRemoteAddress, boolean banned, long bannedTimeMs,
			int requestsCount) {
		this.requestRemoteAddress = requestRemoteAddress;
		this.banned = banned;
		this.bannedTimeMs = bannedTimeMs;
		this.requestsCount = requestsCount;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (banned ? 1231 : 1237);
		result = prime * result + (int) (bannedTimeMs ^ (bannedTimeMs >>> 32));
		result = prime * result + ((metadataID == null) ? 0 : metadataID.hashCode());
		result = prime * result + ((requestRemoteAddress == null) ? 0 : requestRemoteAddress.hashCode());
		result = prime * result + requestsCount;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DDOSMetadata other = (DDOSMetadata) obj;
		if (banned != other.banned)
			return false;
		if (bannedTimeMs != other.bannedTimeMs)
			return false;
		if (metadataID == null) {
			if (other.metadataID != null)
				return false;
		} else if (!metadataID.equals(other.metadataID))
			return false;
		if (requestRemoteAddress == null) {
			if (other.requestRemoteAddress != null)
				return false;
		} else if (!requestRemoteAddress.equals(other.requestRemoteAddress))
			return false;
		if (requestsCount != other.requestsCount)
			return false;
		return true;
	}
	
}
