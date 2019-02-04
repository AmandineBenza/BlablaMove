package com.xaamruda.bbm.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.xaamruda.bbm.commons.logging.BBMLogger;
import com.xaamruda.bbm.commons.spring.context.ContextProvider;
import com.xaamruda.bbm.integrity.dbcommunication.DatabaseConnectionChecker;
import com.xaamruda.bbm.integrity.ddos.DDOSChecker;
import com.xaamruda.bbm.integrity.ddos.dbaccess.AuthorizationService;
import com.xaamruda.bbm.integrity.journaling.engine.JournalingEngine;
import com.xaamruda.bbm.users.dbaccess.service.UserService;
import com.xaamruda.bbm.users.identification.UserIdentificationChecker;

import net.bytebuddy.asm.Advice.Return;

/**
 * BlablaMove entry point. 
 */

@EnableJpaRepositories(basePackages = {
		"com.xaamruda.bbm.offers",
		"com.xaamruda.bbm.users",
		"com.xaamruda.bbm.integrity"
})
@SpringBootApplication(scanBasePackages = { 
		"com.xaamruda.bbm.app",
		"com.xaamruda.bbm.billing",
		"com.xaamruda.bbm.commons",
		"com.xaamruda.bbm.communication",
		"com.xaamruda.bbm.controller",
		"com.xaamruda.bbm.integrity",
		"com.xaamruda.bbm.offers",
		"com.xaamruda.bbm.roads",
		"com.xaamruda.bbm.users",
		"com.xaamruda.bbm.chaos"
})
@ComponentScan(basePackages = {
		"com.xaamruda.bbm.app",
		"com.xaamruda.bbm.billing",
		"com.xaamruda.bbm.commons",
		"com.xaamruda.bbm.communication",
		"com.xaamruda.bbm.controller",
		"com.xaamruda.bbm.integrity",
		"com.xaamruda.bbm.offers",
		"com.xaamruda.bbm.roads",
		"com.xaamruda.bbm.users",
		"com.xaamruda.bbm.chaos"
})
@EntityScan(basePackages = {
		"com.xaamruda.bbm.app",
		"com.xaamruda.bbm.billing",
		"com.xaamruda.bbm.commons",
		"com.xaamruda.bbm.communication",
		"com.xaamruda.bbm.controller",
		"com.xaamruda.bbm.integrity",
		"com.xaamruda.bbm.offers",
		"com.xaamruda.bbm.roads",
		"com.xaamruda.bbm.users",
		"com.xaamruda.bbm.chaos"
})
@EnableCaching
public class Application {
	
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Application.class);
		BBMLogger.infoln(">> Welcome to BlablaMove <<");
		launch(context);
	}
	
	private static void launch(ConfigurableApplicationContext context) {
		ContextProvider.init(context);
		// DDOSChecker.start(context.getBean(AuthorizationService.class), DDOSChecker.STANDARD_THREAD_SLEEP_MS);
		DatabaseConnectionChecker.start();
		UserIdentificationChecker.start(context.getBean(UserService.class), 30000);
		JournalingEngine.init();
	}
	
//	@Bean
//	public DDOSChecker generateDDOSChecker() {
//		return new DDOSChecker(DDOSChecker.STANDARD_BAN_BOUND_REQUESTS_COUNT).loadCacheFromDatabase();
//	}
	
}