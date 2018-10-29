package com.xaamruda.bbm.commons.logging;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

public final class BBMLogger {

	private final static Logger logger = Logger.getLogger(BBMLogger.class);
	private final static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	public static void dateInfo() {
		info(formatter.format(new Date()) + " | ");
	}
	
	public static void init() {
		dateInfo();
		info("BBMLogger initialized.");
	}
	
	public static void info(String msg) {
		logger.info(msg);
	}
	
	public static void debug(String msg) {
		logger.debug(msg);
	}
	
	public static void error(String msg) {
		logger.error(msg);
	}
	
	public static void infoln(String msg) {
		logger.info(msg + "\n");
	}
	
	public static void debugln(String msg) {
		logger.debug(msg + "\n");
	}
	
	public static void errorln(String msg) {
	//	logger.error(msg + "\n");
	}
}
