package com.xaamruda.bbm.integrity.ddos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.xaamruda.bbm.commons.logging.BBMLogger;
import com.xaamruda.bbm.commons.spring.context.ContextProvider;
import com.xaamruda.bbm.integrity.ddos.DDOSThread.DDOSThreadType;
import com.xaamruda.bbm.integrity.ddos.dbaccess.IAuthorizationService;

/**
 * Bad design but functionalities to be tested.
 * 
 * TODO Validation
 */
@Component
public class DDOSGuard {

	public static final int STANDARD_BAN_BOUND_REQUESTS_COUNT = 30;
	public final static long STANDARD_THREAD_SLEEP_MS = 3600;
	public final static int STANDARD_BAN_TIME_MS = 3600;
	
	// to reset metadata requests count
	private static DDOSThread resetTread;
	// to upload cache to database
	private static DDOSThread uploaderTread;
	// to unban IPs
	private static DDOSThread unbannerThread;
	
	@Autowired
	private IAuthorizationService ddosService;
	
	private static Map<String, DDOSMetadata> cache = new HashMap<>();
	
	private int boundRequestBeforeBanInOneMinute;
	
	@Autowired
	public DDOSGuard() {
		this.boundRequestBeforeBanInOneMinute = STANDARD_BAN_BOUND_REQUESTS_COUNT;
		BBMLogger.infoln("DDOS Guard instance created.");
	}
	
	public DDOSGuard loadCacheFromDatabase() {
		List<DDOSMetadata> databaseContent = null;
		
		try {
			databaseContent = ddosService.getAll();
		} catch(Exception e) {
			BBMLogger.errorln("DDOS Guard: Could not load database content.");
			return this;
		}
		
		if(databaseContent != null && !databaseContent.isEmpty()) {
			databaseContent.forEach(d -> cache.put(d.getRequestRemoteAddress(), d));
			BBMLogger.infoln("DDOS cache loaded from database.");
		} else {
			BBMLogger.infoln("DDOS cache not loaded from database because database is empty.");
		}
		
		return this;
	}
	
	/**
	 * Check if a request should be processed.
	 * 
	 * @param remoteAddressUrl user IP
	 */
	@Cacheable("authorization")
	public boolean checkNewRequestAuthorization(String remoteAddressUrl) {
		if(cache.containsKey(remoteAddressUrl)) {
			DDOSMetadata metadata = cache.get(remoteAddressUrl);
			
			if(metadata.isBanned())
				return false;
			
			if(metadata.getRequestsCount() + 1 >= boundRequestBeforeBanInOneMinute)
				return false; // THIS OR BAN IT
			
			metadata.addRequest();
			return true;
		} else {
			DDOSMetadata metadata = new DDOSMetadata();
			metadata.setBanned(false);
			metadata.setBannedTimeMs(STANDARD_BAN_TIME_MS);
			metadata.setRequestRemoteAddress(remoteAddressUrl);
			metadata.setRequestsCount(1);
			cache.put(remoteAddressUrl, metadata);
			return true;
		}
	}
	
	// shared sleep time
	public static void start(IAuthorizationService service, long sleepTimeMs) {
		BBMLogger.infoln("Starting ddos guarding...");
		resetTread = new DDOSThread(DDOSThreadType.REQUEST_COUNT_RESET, service, sleepTimeMs, cache);
		uploaderTread = new DDOSThread(DDOSThreadType.DATABASE_UPLOADER, service, sleepTimeMs, cache);
		unbannerThread = new DDOSThread(DDOSThreadType.IP_UNBANNER, service, sleepTimeMs, cache);
		
		resetTread.start();
		uploaderTread.start();
		unbannerThread.start();
		
		DDOSGuard guard = ContextProvider.getBean(DDOSGuard.class);
		
		if(guard != null) {
			guard.loadCacheFromDatabase();
		}
	}

	public final void stop() {
		resetTread.stop();
		uploaderTread.stop();
		unbannerThread.stop();
	}
	
}
