package com.adidas.backend.emailservice.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

	private final EmailSender emailSender;

    public EmailService(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendEmail(String email) {
        emailSender.send(email);
    }
}