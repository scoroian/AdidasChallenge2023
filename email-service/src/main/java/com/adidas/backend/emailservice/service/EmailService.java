package com.adidas.backend.emailservice.service;

import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class EmailService {

	public EmailService() {
	}

	private final String EMAIL_SUBJECT = "You have been selected for the new Adidas sneaker release";
	private final String EMAIL_BODY = "Congratulations! You have been selected to purchase the latest Adidas sneakers before their official release. Follow the link below to complete your purchase.";

	public void sendEmail(String email) throws Exception {
		if (email.isBlank()) {
			throw new NullPointerException("Email cannot be null");
		}
        log.info("Preparing to send email to: {}", email);
        log.info("Email sent successfully to: {}, with subject: {} and body: {}", email, EMAIL_SUBJECT, EMAIL_BODY);
    }
}
