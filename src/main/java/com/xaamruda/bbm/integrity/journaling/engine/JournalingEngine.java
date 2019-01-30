package com.xaamruda.bbm.integrity.journaling.engine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.xaamruda.bbm.commons.logging.BBMLogger;
import com.xaamruda.bbm.commons.spring.context.ContextProvider;

public class JournalingEngine {

	private static JournalingEngine usersJournalEngine;
	private static JournalingEngine offersJournalEngine;
	private static JournalingEngine billingJournalEngine;
	
	public final static String OFFERS_MODULE = "offers";
	public final static String USERS_MODULE = "users";
	public final static String BILLING_MODULE = "billing";
	private final static String OFFERS_SERVICE_MODULE_PATH = "com.xaamruda.bbm.offers.dbaccess.service.";
	private final static String USERS_SERVICE_MODULE_PATH = "com.xaamruda.bbm.users.dbaccess.service.";
	private final static String BILLING_MODULE_PATH = "com.xaamruda.bbm.billing.";
	
	public final static long ERROR_CODE = -1L;
	public final static String TODO_STATE = "TODO";
	public final static String DONE_STATE = "DONE";
	
	private boolean safeStart;
	private long maxId;
	private String journalFilePath;
	
	public static void init() {
		usersJournalEngine = new JournalingEngine("./src/main/resources/journaling/usersJournal.ddb").start();
		offersJournalEngine = new JournalingEngine("./src/main/resources/journaling/offersJournal.ddb").start();
		billingJournalEngine = new JournalingEngine("./src/main/resources/journaling/billingJournal.ddb").start();
	}
	
	public synchronized static JournalingEngine get(String module) {
		if(OFFERS_MODULE.equals(module)) {
			return offersJournalEngine;
		} else if(USERS_MODULE.equals(module)) {
			return usersJournalEngine;
		} else if(BILLING_MODULE.equals(module)) {
			return billingJournalEngine;
		}
		
		return null;
	}
	
	public JournalingEngine(String journalFilePath) {
		this.journalFilePath = journalFilePath;
		this.safeStart = false;
		this.maxId = ERROR_CODE;
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
			BBMLogger.errorln("Journal engine for \"" + journalFilePath + "\" is in a bad state.");
			safeStart = false;
		}
		
		return id;
	}

	/**
	 * Starting the journaling engine.<br>
	 * Analyzes the database journal file.<br>
	 */
	public JournalingEngine start() {
		if(safeStart) {
			BBMLogger.infoln("Journaling engine \"" + journalFilePath + "\" could not start as engine is already started.");
			return this;
		}
		
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
		maxId = extractId();
		analyze();
		
		return this;
	}
	
	/**
	 * Performs journal analyze (journalFilePath).
	 */
	public synchronized void analyze() {
		if(!safeStart) {
			BBMLogger.infoln("Journaling engine \"" + journalFilePath + "\" could not perform analyze as engine is not started.");
			return;
		}
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(journalFilePath)));
			String line = null;
			do {
				line = br.readLine();
				analyzeTask(line);
			} while(line != null);
			
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		BBMLogger.infoln("Journaling engine \"" + journalFilePath + "\" analyze finished.");
	}
	
	/**
	 * Journal line example:
	 * 
	 * 0;users;UserService;store;([User|userJson]);
	 */
	private boolean analyzeTask(String line) {
		if(line == null || line.isEmpty())
			return false;

		String[] data = line.split(";");
		String service = data[1].trim();
		String className = data[2].trim();
		String action = data[3].trim();
		
		Object[] parameters = analyzeParameters(line);
		Class<?> serviceCallerClazz = analyzeService(service, className);
		
		if(serviceCallerClazz == null) {
			BBMLogger.errorln("Could not perform journal \"" + journalFilePath + "\" analyze.[Service-caller_error]");
			return false;
		}
		
		Method method = analyzeMethod(serviceCallerClazz, action);
		
		if(method == null) {
			BBMLogger.errorln("Could not perform journal \"" + journalFilePath + "\" analyze.[Method_error]");
			return false;
		}
		
		Object callerInstance;
		try {
			callerInstance = ContextProvider.getBean(serviceCallerClazz);
			
			if(parameters != null) {
				method.invoke(callerInstance, parameters);
			} else {
				method.invoke(callerInstance);
			}
			
			return true;
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			BBMLogger.errorln("Could not perform journal \"" + journalFilePath + "\" analyze.[Method-invocation_error]");
			return false;
		}
	}
	
	private Class<?> analyzeService(String service, String className){
		if(OFFERS_MODULE.equals(service)) {
			try {
				if(!className.contains(".")) {
					return Class.forName(OFFERS_SERVICE_MODULE_PATH + className);
				}
			} catch (ClassNotFoundException e) {
			}
		} else if(USERS_MODULE.equals(service)) {
			try {
				if(!className.contains(".")) {
					return Class.forName(USERS_SERVICE_MODULE_PATH + className);
				}
			} catch (ClassNotFoundException e) {
			}
		} else if(BILLING_MODULE.equals(service)) {
			try {
				return Class.forName(BILLING_MODULE_PATH + className);
			} catch (ClassNotFoundException e) {
			}
		}
		
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			BBMLogger.debugln("Journaling analyze, caller class \"" + className + "\" unknown.");
			return null;
		}
	}
	
	private Method analyzeMethod(Class<?> clazz, String action) {
		for(Method method : clazz.getMethods()) {
			if(method.getName().toLowerCase().equals(action.toLowerCase())){
				return method;
			}
		}
		
		return null;
	}
	
	/**
	 * From journal line, produce objects instances.
	 */
	private Object[] analyzeParameters(String line){
		String[] lineSplit = line.split(";");
		String fullParameters = lineSplit[lineSplit.length - 2];
		
		if(fullParameters.isEmpty() || fullParameters.equals("()")
				|| fullParameters.equals("([])")) {
			BBMLogger.infoln("No parameters found.");
			return null;
		}
		
		Pattern pattern = Pattern.compile("\\(.*\\)");
		Matcher matcher = pattern.matcher(line);
		matcher.find();
		String parameters = matcher.group();
		String[] parametersData = parameters.replaceAll("\\(", "").replaceAll("\\)", "").split(";");
		Object[] parametersArray = new Object[parametersData.length];
		int parametersArrayIndex = 0;
		
		for(String parameterData : parametersData) {
			String[] parameterDataComponents = parameterData.substring(1, parameterData.length() - 1).split("\\|");
			String type = parameterDataComponents[0].trim();
			String value = parameterDataComponents[1].trim();
			
			if(type == null || value == null) continue;
			
			String upType = type.toUpperCase();
			
			if(upType.contains("INTEGER")) {
				parametersArray[parametersArrayIndex++] = Integer.parseInt(value); 
			} else if(upType.contains("FLOAT")) {
				parametersArray[parametersArrayIndex++] = Float.parseFloat(value);
			} else if(upType.contains("LONG")) {
				parametersArray[parametersArrayIndex++] = Long.parseLong(value);
			} else if(upType.contains("DOUBLE")) {
				parametersArray[parametersArrayIndex++] = Double.parseDouble(value);
			} else if(upType.contains("STRING")) {
				parametersArray[parametersArrayIndex++] = value.trim();
			} else {
				// not a primitive type
				Class<?> clazz;
				try {
					clazz = Class.forName(type);
					parametersArray[parametersArrayIndex++] = new Gson().fromJson(value, clazz); 
				} catch (ClassNotFoundException e) {
				}
			}
		}
		
		return parametersArray[0] == null ? null : parametersArray;
	}
	
	/**
	 * Journal an operation.
	 *  
	 * @param service caller, use one of static variables above
	 * @param action action to perform "createUser"..
	 * @param parameters action parameters
	 * @return journaling id
	 */
	public synchronized long journal(String service, String className, String action, Object... parameters){
		if(!safeStart) {
			BBMLogger.infoln("Could not log on closed journal");
			return ERROR_CODE;
		}

		// TODO to be removed as we would like to journal for anywhere
//		if(!OFFERS_SERVICE.equals(service) && (!USERS_SERVICE.equals(service))) {
//			BBMLogger.infoln("Could not journal for service \"" + service + "\".");
//			return ERROR_CODE;
//		}
		
		checkCreate();
		
		String parametersRepresentation = formatParameters(parameters);
		long id = getIdAndIncrement();
		toJournal(id, service, className, action, parametersRepresentation);
		return id;
	}
	
	private void checkCreate() {
		File file = new File(journalFilePath); 
		if(!file.exists()) {
			try {
				file.createNewFile();
				safeStart = true;
			} catch (IOException e) {
				safeStart = false;
			}
		}
	}
	
	/**
	 * Set to status "DONE" a journal entry.
	 * 
	 * @param id journaling identifier
	 */
	public synchronized void endJournal(long id) {
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
		if(parameters == null || parameters.length == 0)
			return null;
		
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		
		for(int i = 0; i < parameters.length; ++i) {
			Object parameter = parameters[i];
			sb.append("[");
			
			boolean primitiveType = isPrimitive(parameter);
			String className = parameter.getClass().getTypeName();
			
			sb.append(className);
			sb.append("|");
			
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
	private void toJournal(long id, String service, String className, String action, String parameters) {
		StringBuilder sb = new StringBuilder();
		sb.append(id);
		sb.append(";");
		
		if(service != null && !service.isEmpty())
			sb.append(service);
		
		sb.append(";");
		sb.append(className);
		sb.append(";");
		sb.append(action);
		sb.append(";");
		
		if(parameters != null)
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
	
	private long getIdAndIncrement() {
		return maxId++;
	}
	
	public synchronized long getMaxId() {
		return maxId;
	}
	
	public synchronized boolean isInSafeStartMode() {
		return safeStart;
	}

	public synchronized String getJournalFilePath() {
		return journalFilePath;
	}
	
}