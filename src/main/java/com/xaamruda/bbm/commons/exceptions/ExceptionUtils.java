package com.xaamruda.bbm.commons.exceptions;

public final class ExceptionUtils {

	private ExceptionUtils() {}
	
	public static String concatExceptionMessages(String s1, String... ss) {
		if(ss.length == 0) return s1;
		
		StringBuilder sb = new StringBuilder();
		sb.append(s1);
		sb.append("\nAdditional exception content:\n");
		
		for(int i = 0; i < ss.length; ++i) {
			sb.append("\t");
			sb.append(i);
			sb.append(". ");
			sb.append(ss[i]);
		}
		
		return sb.toString();
	}
}
