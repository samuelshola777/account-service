package com.accountService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot Application class for the Account Service.
 * This class serves as the entry point for the Spring Boot application and enables auto-configuration.
 */
@SpringBootApplication
public class AccountServiceApplication {

	/**
	 * The main method that bootstraps and launches the Spring Boot application.
	 * 
	 * @param args Command line arguments passed to the application
	 */
	public static void main(String[] args) {
		SpringApplication.run(AccountServiceApplication.class, args);
	}

}
