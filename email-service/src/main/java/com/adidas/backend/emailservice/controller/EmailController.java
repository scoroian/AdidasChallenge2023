package com.adidas.backend.emailservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adidas.backend.emailservice.service.EmailService;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping(value = "/")
public class EmailController {

	@Autowired
	private EmailService emailService;

	public EmailController(EmailService emailService) {
		this.emailService = emailService;
	}

	@PostMapping
	public ResponseEntity<String> sendEmail(@RequestParam String email) {
		log.info("Received request to send email to: {}", email);
		try {
			emailService.sendEmail(email);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			log.error("Failed to send email to: {}", email, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email");
		}
	}

}
