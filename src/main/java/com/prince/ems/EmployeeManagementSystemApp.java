package com.prince.ems;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class EmployeeManagementSystemApp {
	
	public static void main(String[] args) {
	SpringApplication.run(EmployeeManagementSystemApp.class);
	}
}
