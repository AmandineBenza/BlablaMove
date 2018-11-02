package com.xaamruda.bbm.commons.logging;

import java.text.SimpleDateFormat;

// TODO ONGOING
public final class BBMLogger {

	private final static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	public static void init() {
		info("BBMLogger initialized.");
	}

	public static void info(String msg) {
		
	}

	public static void debug(String msg) {
	
	}

	public static void error(String msg) {
	
	}
	
	public static enum LoggingConfig {
		FILE, CONSOLE, FILE_CONSOLE;
	}
	
}
