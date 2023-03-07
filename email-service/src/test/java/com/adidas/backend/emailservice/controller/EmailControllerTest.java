package com.adidas.backend.emailservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.adidas.backend.emailservice.service.EmailService;

public class EmailControllerTest {

    private EmailController emailController;
    private EmailService emailService;

    @BeforeEach
    public void setUp() {
        emailService = mock(EmailService.class);
        emailController = new EmailController(emailService);
    }

    @Test
    public void testSendEmail() throws Exception {
        // Arrange
        String email = "test@adiclub.com";
        emailService.sendEmail(email);

        // Act
        ResponseEntity<String> response = emailController.sendEmail(email);

        // Assert
        verify(emailService).sendEmail(email);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testSendEmailError() throws Exception {
        // Arrange
        String email = null;
        Exception exception = new Exception("Failed to send email");
        emailService.sendEmail(email);

        // Act
        ResponseEntity<String> response = emailController.sendEmail(email);

        // Assert
        verify(emailService).sendEmail(email);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertEquals("Failed to send email", response.getBody());
	}
}
