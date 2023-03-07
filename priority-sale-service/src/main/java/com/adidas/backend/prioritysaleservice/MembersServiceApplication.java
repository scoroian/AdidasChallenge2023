package com.adidas.backend.prioritysaleservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = {"com.adidas.backend.prioritysaleservice", "com.adidas.backend.common", "com.adidas.backend.logging", "com.adidas.backend.prioritysaleservice.config"})
@EnableScheduling
public class MembersServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MembersServiceApplication.class, args);
	}

}
