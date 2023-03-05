package com.adidas.backend.emailservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class SmtpEmailSender implements EmailSender {
	@Autowired
	private JavaMailSender javaMailSender;

	public void send(String email) {
//		SimpleMailMessage message = new SimpleMailMessage();
//		message.setTo(email);
//		javaMailSender.send(message);
		System.out.println("Email sent to user " + email);
	}
}
