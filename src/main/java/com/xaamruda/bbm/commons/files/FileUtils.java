package com.xaamruda.bbm.commons.files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileUtils {

	private FileUtils() {}
	
	public static boolean writeTo(String filePath, String content, boolean append) {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(new File(filePath), append));
			pw.print(content);
			pw.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static String fileContentToString(String filePath) {
		File file = new File(filePath);
		
		if(!file.exists()) {
			return null;
		}
		
		StringBuffer buffer = new StringBuffer();
		Scanner scanner;
		try {
			scanner = new Scanner(file);
			while(scanner.hasNextLine()) {
				buffer.append(scanner.nextLine());
				buffer.append("\n");
			}
			
			scanner.close();
			return buffer.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
