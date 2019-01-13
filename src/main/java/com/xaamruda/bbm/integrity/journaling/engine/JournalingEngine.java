package com.xaamruda.bbm.integrity.journaling.engine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import com.google.gson.Gson;
import com.xaamruda.bbm.commons.logging.BBMLogger;

public class JournalingEngine {

	private final static JournalingEngine usersJournalEngine =
			new JournalingEngine("./src/main/java/com/xaamruda/bbm/integrity/journaling/data/usersJournal.ddb").start();
	
	private final static JournalingEngine offersJournalEngine = 
			new JournalingEngine("./src/main/java/com/xaamruda/bbm/integrity/journaling/data/offersJournal.ddb").start();
	
	public final static String OFFERS_SERVICE = "offers";
	public final static String USERS_SERVICE = "users";
	public final static long ERROR_CODE = -1L;
	public final static String TODO_STATE = "TODO";
	public final static String DONE_STATE = "DONE";
	
	private boolean safeStart;
	private long maxId;
	public String journalFilePath;
	
	public static synchronized JournalingEngine get(String service) {
		if(OFFERS_SERVICE.equals(service)) {
			return offersJournalEngine;
		} else if(USERS_SERVICE.equals(service)) {
			return usersJournalEngine;
		}
		
		return null;
	}
	
	private JournalingEngine(String journalFilePath) {
		this.journalFilePath = journalFilePath;
		this.safeStart = false;
		this.maxId = extractId();
		BBMLogger.infoln("Initialized \"" + journalFilePath + "\" database journaling.");
	}
	
	// TODO buffered reader better perfs
	private long extractId() {
		long id = 0;
		
		try {
			Scanner scanner = new Scanner(new File(journalFilePath));
			String journalLine = null;
			
			while(scanner.hasNextLine()) {
				journalLine = scanner.nextLine();
			}
			
			scanner.close();
			
			if(journalLine != null && !journalLine.isEmpty()) {
				return Long.parseLong(journalLine.split(";")[0]) + 1;
			}
			
		} catch (FileNotFoundException e) {
			BBMLogger.errorln("Journal \"" + journalFilePath + "\" is in an unsatisfied state.");
			safeStart = false;
		}
		
		return id;
	}
	
	// TODO perform journaling analyze
	public JournalingEngine start() {
		File journalFile = new File(journalFilePath);
		
		if(!journalFile.exists()) {
			try {
				journalFile.createNewFile();
			} catch (IOException e) {
				safeStart = false;
				BBMLogger.infoln("Could not create journal file.");
				return this;
			}
		}
		
		safeStart = true;
		return this;
	}
	
	/**
	 * Journal an operation.
	 *  
	 * @param service caller, use one of static variables above
	 * @param action action to perform "createUser"..
	 * @param parameters action parameters
	 * @return journaling id
	 */
	public long journal(String service, String action, Object... parameters){
		if(!safeStart) {
			BBMLogger.infoln("Could not log on closed journal");
			return ERROR_CODE;
		}
		
		if(!OFFERS_SERVICE.equals(service) && (!USERS_SERVICE.equals(service))) {
			BBMLogger.infoln("Could not journal for service \"" + service + "\".");
			return ERROR_CODE;
		}
		
		String parametersRepresentation = formatParameters(parameters);
		long id = getId();
		toJournal(id, service, action, parametersRepresentation);
		return id;
	}
	
	/**
	 * Set to status "DONE" a journal entry.
	 * 
	 * @param id journaling identifier
	 */
	public void endJournal(long id) {
		BufferedReader bReader;
		try {
			bReader = new BufferedReader(new FileReader(new File(journalFilePath)));
			StringBuilder sb = new StringBuilder();
			String line = null;
			long i = 0;
			
			do {
				line = bReader.readLine();
				if(line == null) break;
				
				if(i == id) {
					sb.append(line.substring(0, line.length() - 4));
					sb.append(DONE_STATE);
				} else {
					sb.append(line);
				}
				
				sb.append("\n");
				++i;
			} while(line != null);
			
			bReader.close();
			PrintWriter pw = new PrintWriter(new FileWriter(new File(journalFilePath)));
			pw.println(sb.toString().trim());
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String formatParameters(Object... parameters) {
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		
		for(int i = 0; i < parameters.length; ++i) {
			Object parameter = parameters[i];
			sb.append("[");
			
			boolean primitiveType = isPrimitive(parameter);
			String className = parameter.getClass().getTypeName();
			
			sb.append(className);
			sb.append(";");
			
			if(primitiveType) {
				sb.append(parameter);
			} else {
				sb.append(new Gson().toJson(parameter));
			}
			
			sb.append("]");
			
			if(i + 1 < parameters.length) {
				sb.append(";");
			}
		}
		
		sb.append(")");
		return sb.toString();
	}
	
	private static boolean isPrimitive(Object o) {
		return o.getClass().isPrimitive();
	}

	// 0;users;store;([User;userJson]);
	private void toJournal(long id, String service, String action, String parameters) {
		StringBuilder sb = new StringBuilder();
		sb.append(id);
		sb.append(";");
		sb.append(service);
		sb.append(";");
		sb.append(action);
		sb.append(";");
		sb.append(parameters);
		sb.append(";");
		sb.append(TODO_STATE);
		
		PrintWriter pw;
		try {
			pw = new PrintWriter(new FileWriter(new File(journalFilePath), true));
			pw.println(sb.toString());
			pw.close();
		} catch (IOException e) {
			BBMLogger.infoln("Journaling failed.");
		}
	}
	
	private long getId() {
		return maxId++;
	}
}