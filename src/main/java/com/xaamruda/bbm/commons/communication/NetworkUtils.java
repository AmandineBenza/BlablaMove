package com.xaamruda.bbm.commons.communication;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public final class NetworkUtils {

	public static String getRemoteIpAddress(HttpServletRequest request) {
	    String ip = "";
	   
	    Enumeration<String> v = request.getHeaderNames();
	    while(v.hasMoreElements()) {
	    	System.out.println(v.nextElement());
	    }
	    
	    System.out.println(ip);
	    if (null == ip || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
	    	ip = request.getHeader("x-forwarded-for");
	    }
	    if (null == ip || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if (null == ip || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if (null == ip || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("HTTP_CLIENT_IP");
	    }
	    if (null == ip || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("HTTP_X_FORWARDED_FOR");
	    }
	    if (null == ip || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
	       System.out.println("ok");
	    	ip = request.getRemoteAddr();
	    }
	    return ip;
	}
	
	public static String getClientIP(HttpServletRequest request) {
	    String xfHeader = request.getHeader("X-Forwarded-For");
	    if (xfHeader == null){
	        return request.getRemoteAddr();
	    }
	    return xfHeader.split(",")[0];
	}
}
