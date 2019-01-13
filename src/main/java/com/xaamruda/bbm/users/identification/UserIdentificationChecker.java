package com.xaamruda.bbm.users.identification;

import java.sql.Timestamp;

import com.xaamruda.bbm.commons.logging.BBMLogger;
import com.xaamruda.bbm.users.dbaccess.service.IUserService;
import com.xaamruda.bbm.users.model.User;

public class UserIdentificationChecker {

	private static UserIdentificationCheckerThread thread = new UserIdentificationCheckerThread();
	
	public static void start(IUserService service) {
		thread.start(service);
	}

	public static void stop() {
		thread.stop();
	}
	
	private static class UserIdentificationCheckerThread implements Runnable {
		private boolean running = false;
		private Thread thread;
		private IUserService service;
		
		public void start(IUserService userService) {
			if(this.running) return;
			this.thread = new Thread(this);
			this.running = true;
			this.service = userService;
			this.thread.start();
		}

		public void stop() {
			if(!this.running) return;
			this.running = false;
		}

		@Override
		public void run() {
			while(running) {
				if(service.getAllUsers() != null) {		
					
					for(User user : service.getAllUsers()) {
						Timestamp identificationTime = user.getIdentificationTime();
						if(identificationTime == null) continue;
						Timestamp currentTime = new Timestamp(System.currentTimeMillis());
					/*	BBMLogger.infoln("ID TIME : " + identificationTime.getTime());
						BBMLogger.infoln("CURRENT TIME : " + System.currentTimeMillis());
						BBMLogger.infoln("SOUSTRACTION : " + (System.currentTimeMillis() - identificationTime.getTime())); */

						if(currentTime.getTime() - identificationTime.getTime() > 300000 ) { 	// 2 minutes
							user.setIdentified(false);
							service.store(user);	
						}		
					}
				}
				
				try {
					Thread.sleep(30000);
				} catch (InterruptedException e) {
					BBMLogger.errorln("User Identification Checker Thread failed to sleep.");
				}
			}
		}
	}
}
