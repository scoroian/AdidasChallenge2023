package com.adidas.emailservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.adidas.backend.emailservice.controller.EmailController;
import com.adidas.backend.emailservice.exception.EmailSendingException;
import com.adidas.backend.emailservice.model.Email;
import com.adidas.backend.emailservice.service.EmailService;

public class EmailControllerTest {

	@Test
	public void testSendEmailSuccess() throws Exception {
		// Create a mock EmailService
		EmailService emailServiceMock = Mockito.mock(EmailService.class);

		// Create an instance of EmailController with the mock EmailService
		EmailController emailController = new EmailController(emailServiceMock);

		// Create an email to send
		Email email = new Email();
		email.setToEmail("test@example.com");
		email.setSubject("Test Email");
		email.setContent("This is a test email");

		// Mock the sendEmail method of the EmailService
		Mockito.doNothing().when(emailServiceMock).sendEmail(email);

		// Send the email and check the response
		ResponseEntity<String> response = emailController.sendEmail(email);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Email sent successfully.", response.getBody());
	}

	@Test
	public void testSendEmailException() throws Exception {
		// Create a mock EmailService
		EmailService emailServiceMock = Mockito.mock(EmailService.class);

		// Create an instance of EmailController with the mock EmailService
		EmailController emailController = new EmailController(emailServiceMock);

		// Create an email to send
		Email email = new Email();
		email.setToEmail("test@example.com");
		email.setSubject("Test Email");
		email.setContent("This is a test email");

		// Mock the sendEmail method of the EmailService to throw an exception
		Mockito.doThrow(new EmailSendingException("Error sending email")).when(emailServiceMock).sendEmail(email);

		// Send the email and check the response
		ResponseEntity<String> response = emailController.sendEmail(email);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertEquals("Error sending email.", response.getBody());
	}
}
