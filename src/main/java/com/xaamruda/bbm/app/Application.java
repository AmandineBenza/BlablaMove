package com.xaamruda.bbm.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * BlablaMove entry point. 
 */
@SpringBootApplication(scanBasePackages = {
		"com.xaamruda.bbm.app",
		"com.xaamruda.bbm.communication",
		"com.xaamruda.bbm.controller",
		"com.xaamruda.bbm.dbaccess",
		"com.xaamruda.bbm.roads",
		"com.xaamruda.bbm.users",
})
//@EnableJpaRepositories(basePackages = {
//		"com.xaamruda.bbm.dbaccess"
//})
public class Application {
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}
}