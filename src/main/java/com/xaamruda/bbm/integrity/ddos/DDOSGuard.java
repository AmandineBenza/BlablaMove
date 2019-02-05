package com.xaamruda.bbm.integrity.ddos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.xaamruda.bbm.commons.logging.BBMLogger;
import com.xaamruda.bbm.commons.spring.context.ContextProvider;
import com.xaamruda.bbm.integrity.ddos.DDOSGuard.DDOSThread.DDOSThreadType;
import com.xaamruda.bbm.integrity.ddos.dbaccess.IAuthorizationService;

/**
 * Bad design but functionalities to be tested.
 * 
 * TODO add unbanner thread.
 * TODO CHECKs and tests
 * TODO ADD journaling ?
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
		resetTread.start();
		uploaderTread.start();
		
		DDOSGuard checker = ContextProvider.getBean(DDOSGuard.class);
		if(checker != null) {
			checker.loadCacheFromDatabase();
		}
	}

	public final void stop() {
		resetTread.stop();
		uploaderTread.stop();
	}

	public static class DDOSThread implements Runnable {
		
		public enum DDOSThreadType {
			REQUEST_COUNT_RESET, DATABASE_UPLOADER
		}
		
		public final static long STANDARD_THREAD_SLEEP_MS = 3600;
		
		private boolean running = false;
		private Thread thread;
		private IAuthorizationService service;
		private long sleepTimeMs;
		private DDOSThreadType type;
		private Map<String, DDOSMetadata> cache;
		
		public DDOSThread(DDOSThreadType type, IAuthorizationService service, long sleepTimeMs, Map<String, DDOSMetadata> cache) {
			this.type = type;
			this.service = service;
			this.sleepTimeMs = sleepTimeMs;
			this.cache = cache;
		}

		public void start() {
			if (this.running)
				return;
			
			this.thread = new Thread(this);
			this.running = true;
			this.thread.start();
		}

		public void stop() {
			if (!this.running)
				return;
			this.running = false;
		}
		
		@Override
		public void run() {
			switch (type) {
			case REQUEST_COUNT_RESET:
				runRequestCountReset();
				break;
			case DATABASE_UPLOADER:
				runDatabaseUploader();
			default:
				break;
			}
		}
		
		private void runRequestCountReset() {
			while (running) {
				List<DDOSMetadata> metadatas = service.getAll();

				for (DDOSMetadata metadata : metadatas) {
					if (!metadata.isBanned()) {
						metadata.setRequestsCount(0);
					}
				}

				service.save(metadatas);
				try {
					Thread.sleep(sleepTimeMs);
				} catch (InterruptedException e) {
					BBMLogger.errorln("DDOS Checker Reset Thread failed to sleep.");
				}
			}
		}
		
		private void runDatabaseUploader() {
			while (running) {
				try {
					try {
						service.save(cache.values());
					} catch(Exception e) {
						// hum, maybe not journaling here
					}
					Thread.sleep(sleepTimeMs);
				} catch (InterruptedException e) {
					BBMLogger.errorln("DDOS Checker Uploader Thread failed to sleep.");
				}
			}
		}
	}
	
}
