package com.xaamruda.bbm.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.xaamruda.bbm.commons.logging.BBMLogger;

/**
 * BlablaMove entry point. 
 */
@SpringBootApplication(scanBasePackages = { 
		"com.xaamruda.bbm.app",
		"com.xaamruda.bbm.billing",
		"com.xaamruda.bbm.commons",
		"com.xaamruda.bbm.communication",
		"com.xaamruda.bbm.controller",
		"com.xaamruda.bbm.offers",
		"com.xaamruda.bbm.roads",
		"com.xaamruda.bbm.users",
})
@EnableMongoRepositories(basePackages = {
	"com.xaamruda.bbm.offers.dbaccess.repository",
	"com.xaamruda.bbm.users.dbaccess.repository"
})
public class Application {
	
	public static void main(String[] args) {
		init();
		SpringApplication.run(Application.class);
	}
	
	private static void init() {
		BBMLogger.init();
		BBMLogger.infoln("Launching BlablaMove...");
	}
	
}