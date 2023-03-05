package com.adidas.backend.emailservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class PriorityUserEmailSender implements Runnable {

	private final EmailService emailService;
	private final RestTemplate restTemplate;

	@Autowired
	Environment env;

	@Autowired
	public PriorityUserEmailSender(EmailService emailService, RestTemplate restTemplate) {
		this.emailService = emailService;
		this.restTemplate = restTemplate;
	}

	@Override
	@Scheduled(fixedRate = 60000)
	public void run() {
		log.info("Starting PriorityUserEmailSender process");
		ResponseEntity<String> response = restTemplate.getForEntity(env.getProperty("url.priorityQueue"), String.class);
		log.debug("Response from priority queue service: {}", response.getBody());

		if (response.getStatusCode() == HttpStatus.OK) {
			emailService.sendEmail(response.getBody());
		}
	}
}
