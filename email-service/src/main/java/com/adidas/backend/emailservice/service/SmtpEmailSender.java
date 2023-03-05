package com.adidas.backend.emailservice.service;

import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class SmtpEmailSender implements EmailSender {
	public void send(String email) {
		log.info("Email sent to user : {}", email);
	}
}
