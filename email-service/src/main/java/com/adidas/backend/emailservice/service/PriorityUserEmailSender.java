package com.adidas.backend.emailservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PriorityUserEmailSender implements Runnable {

    private final EmailService emailService;
    private final RestTemplate restTemplate;

    @Autowired
    public PriorityUserEmailSender(EmailService emailService, RestTemplate restTemplate) {
        this.emailService = emailService;
        this.restTemplate = restTemplate;
    }

    @Override
    @Scheduled(fixedRate = 60000) 
    public void run() {
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/priority-queue/user/priority", String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            emailService.sendEmail(response.getBody());
        }
    }
}
