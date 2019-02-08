package com.xaamruda.bbm.integrity.ddos;

import java.util.List;
import java.util.Map;

import com.xaamruda.bbm.commons.logging.BBMLogger;
import com.xaamruda.bbm.integrity.ddos.dbaccess.IAuthorizationService;

public class DDOSThread implements Runnable {

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
