package com.xaamruda.bbm.integrity.ddos.dbaccess;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xaamruda.bbm.integrity.ddos.DDOSMetadata;

@Component
public class AuthorizationService implements IAuthorizationService {

	@Autowired
	private IAuthorizationRepository repository;
	
	public AuthorizationService() {
		
	}

	@Override
	public List<DDOSMetadata> getByRequestRemoteAddress(String remoteAddress) {
		return repository.findByRequestRemoteAddress(remoteAddress);
	}

	@Override
	public List<DDOSMetadata> getByBanned(boolean banned) {
		return repository.findByBanned(banned);
	}

	@Override
	public List<DDOSMetadata> getByBannedTimeMs(long bannedTimeMs) {
		return repository.findByBannedTimeMs(bannedTimeMs);
	}

	@Override
	public List<DDOSMetadata> getAll() {
		return repository.findAll();
	}

	@Override
	public synchronized void save(Collection<DDOSMetadata> metadatas) {
		repository.saveAll(metadatas);
	}

	@Override
	public void delete(DDOSMetadata metada) {
		repository.delete(metada);
	}

}
