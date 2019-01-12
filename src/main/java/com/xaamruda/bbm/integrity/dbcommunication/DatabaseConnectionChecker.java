package com.xaamruda.bbm.integrity.dbcommunication;

import com.xaamruda.bbm.commons.logging.BBMLogger;

public class DatabaseConnectionChecker {

	private static DatabaseConnectionThread thread; 
	
	private DatabaseConnectionChecker() {}
	
	public static void start(String... config) {
		BBMLogger.infoln("Starting database connection checker...");
		thread = new DatabaseConnectionThread();
		thread.start();
	}
	
	public static void stop(String... config) {
		thread.stop();
	}
	
	private static class DatabaseConnectionThread implements Runnable {

		private Thread thread;
		private boolean running;
		
		public DatabaseConnectionThread() {
			running = false;
		}
		
		@Override
		public void run() {
			while(running) {
				// do work
				
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
