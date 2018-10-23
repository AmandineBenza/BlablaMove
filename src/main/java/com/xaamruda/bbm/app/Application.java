package com.xaamruda.bbm.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * BlablaMove entry point. 
 */
@SpringBootApplication(scanBasePackages = {
		"com.xaamruda.bbm.app",
		"com.xaamruda.bbm.commons",
		"com.xaamruda.bbm.communication",
		"com.xaamruda.bbm.controller",
		"com.xaamruda.bbm.roads",
		"com.xaamruda.bbm.users",
})
public class Application {
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}
}