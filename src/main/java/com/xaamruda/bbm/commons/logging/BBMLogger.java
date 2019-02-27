package com.xaamruda.bbm.commons.logging;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.xaamruda.bbm.chaos.ChaosManager;

public final class BBMLogger {

	private static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
	private static int DEFAULT_PAUSE_TIME = 0;
	private static int ADVANCED_PAUSE_TIME = 10000;
	
	private static boolean LOGGING_ACTIVATED  = true;
	private static boolean ADVANCED_LOGGING_ACTIVATED = false;
	private static String dateInfo() {
		return formatter.format(new Date()) + " | ";
	}

	public static void info(String msg) {
		System.out.print("[INFO|" + dateInfo() + msg + "]");
	}

	public static void debug(String msg) {
		System.out.print("[DEBUG|" + dateInfo() + msg + "]");
	}

	public static void error(String msg) {
		System.out.print("[ERROR|" + dateInfo() + msg + "]");
	}

	public static void infoln(String msg) {
		// System.out.println("[INFO|" + dateInfo() + msg + "]");
		infoln(msg, DEFAULT_PAUSE_TIME);
	}

	public static void infoln(String msg, int sleepms) {
		if(LOGGING_ACTIVATED)
			try {
				Thread.sleep(sleepms);
				System.out.println("[INFO|" + dateInfo() + msg + "]");
			} catch (InterruptedException e) {
			}
	}
	
	public static void advancedDemoln(String msg) {
		if(ADVANCED_LOGGING_ACTIVATED)
			try {
				Thread.sleep(DEFAULT_PAUSE_TIME);
				System.out.println("[INFO|" + dateInfo() + msg + "]");
			} catch (InterruptedException e) {
			}
	}
	public static void waitForEvent() {
		if(ADVANCED_LOGGING_ACTIVATED)
			try {
				Thread.sleep(ADVANCED_PAUSE_TIME);
			} catch (InterruptedException e) {
			}
	}


	public static void debugln(String msg) {
		System.out.println("[DEBUG|" + dateInfo() + msg + "]");
	}

	public static void errorln(String msg) {
		System.out.println("[ERROR|" + dateInfo() + msg + "]");
	}

	public static synchronized void changePauseTime(int newTime) {
		DEFAULT_PAUSE_TIME = newTime;
	}
	
	public static synchronized void toogleLogging(boolean state) {
		LOGGING_ACTIVATED = state;
	}
	
	public static synchronized void toogleAdvancedLogging(boolean state) {
		LOGGING_ACTIVATED = state;
	}
}
