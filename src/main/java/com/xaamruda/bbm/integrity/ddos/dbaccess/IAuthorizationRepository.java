package com.xaamruda.bbm.integrity.ddos.dbaccess;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xaamruda.bbm.integrity.ddos.DDOSMetadata;

public interface IAuthorizationRepository extends JpaRepository<DDOSMetadata, Integer> {

	public List<DDOSMetadata> findByRequestRemoteAddress(String remoteAddress);
	public List<DDOSMetadata> findByBanned(boolean banned);
	public List<DDOSMetadata> findByBannedTimeMs(long bannedTimeMs);
	
}
