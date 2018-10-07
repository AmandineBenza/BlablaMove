package com.xaamruda.bbm.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.lama.dsa.repository",
		"com.lama.dsa.controller", "com.lama.dsa.model",
		"com.lama.dsa.service","com.lama.dsa.swagger",
		"com.lama.dsa.utils"})
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}
}