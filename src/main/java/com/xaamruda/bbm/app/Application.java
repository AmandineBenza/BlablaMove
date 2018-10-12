package com.xaamruda.bbm.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.xaamruda.bbm.app", 
		"com.xaamruda.bbm.controller" })
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}
}