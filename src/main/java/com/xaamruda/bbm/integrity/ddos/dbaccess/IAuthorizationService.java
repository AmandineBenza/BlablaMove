package com.xaamruda.bbm.integrity.ddos.dbaccess;

import java.util.Collection;
import java.util.List;

import com.xaamruda.bbm.integrity.ddos.DDOSMetadata;

public interface IAuthorizationService {
	
	public List<DDOSMetadata> getByRequestRemoteAddress(String remoteAddress);
	
	public List<DDOSMetadata> getByBanned(boolean banned);
	
	public List<DDOSMetadata> getByBannedTimeMs(long bannedTimeMs);
	
	public List<DDOSMetadata> getAll();
	
	public void save(Collection<DDOSMetadata> metadatas);
	
	public void delete(DDOSMetadata metada); 
	
}