package com.xaamruda.bbm.integrity.auditor;

import com.xaamruda.bbm.commons.logging.BBMLogger;

public class IntegrityAuditor{

	private static IntegrityAuditorThread thread; 
	
	private IntegrityAuditor() {}
	
	public static void start(String... config) {
		BBMLogger.infoln("Starting integrity auditor...");
		// use configuration to parameterize thread launch ?
		thread = new IntegrityAuditorThread();
		thread.start();
	}
	
	public static void stop(String... config) {
		thread.stop();
	}
	
	private static class IntegrityAuditorThread implements Runnable {

		private Thread thread;
		private boolean running;
		
		public IntegrityAuditorThread() {
			running = false;
		}
		
		@Override
		public void run() {
			while(running) {
				// do work
				// BBMLogger.debugln("IA thread running..");
			}
		}
		
		public void start() {
			if(!running) {
				running = true;
				thread = new Thread(this);
				thread.start();
			}
		}
		
		public void stop() {
			if(running) {
				running = false;
			}
		}
		
	}
	
}
