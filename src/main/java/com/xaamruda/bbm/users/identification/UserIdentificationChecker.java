package com.xaamruda.bbm.users.identification;

import java.sql.Timestamp;

import com.xaamruda.bbm.commons.logging.BBMLogger;
import com.xaamruda.bbm.users.dbaccess.service.IUserService;
import com.xaamruda.bbm.users.model.User;

public class UserIdentificationChecker {

	private static UserIdentificationCheckerThread thread = new UserIdentificationCheckerThread();

	public static void start(IUserService service, long sleepTimeMs) {
		thread.start(service, sleepTimeMs);
	}

	public static void stop() {
		thread.stop();
	}

	public static class UserIdentificationCheckerThread implements Runnable {
		private boolean running = false;
		private Thread thread;
		private IUserService service;
		private long sleepTimeMs;

		public void start(IUserService userService, long sleepTimeMs) {
			if (this.running)
				return;
			this.thread = new Thread(this);
			this.running = true;
			this.service = userService;
			this.thread.start();
			this.sleepTimeMs = sleepTimeMs;
		}

		public void stop() {
			if (!this.running)
				return;
			this.running = false;
		}

		@Override
		public void run() {
			while (running) {
				if (service.getAllUsers() != null) {
					for (User user : service.getAllUsers()) {
						Timestamp identificationTime = user.getIdentificationTime();
						
						if (identificationTime == null)
							continue;
						Timestamp currentTime = new Timestamp(System.currentTimeMillis());

						if (currentTime.getTime() - identificationTime.getTime() > sleepTimeMs) {
							user.setIdentified(false);
							service.store(user);
						}
					}
				}

				try {
					Thread.sleep(sleepTimeMs);
				} catch (InterruptedException e) {
					BBMLogger.errorln("User Identification Checker Thread failed to sleep.");
				}
			}
		}
	}
}
