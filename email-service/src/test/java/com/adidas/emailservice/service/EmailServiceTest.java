package com.adidas.emailservice.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.adidas.backend.emailservice.model.Email;
import com.adidas.backend.emailservice.service.EmailSender;
import com.adidas.backend.emailservice.service.EmailService;

public class EmailServiceTest {

	@Test
	public void testSendEmail() throws Exception {
		// Create a mock EmailSender
		EmailSender emailSenderMock = Mockito.mock(EmailSender.class);

		// Create an instance of EmailService with the mock EmailSender
		EmailService emailService = new EmailService(emailSenderMock);

		// Create an email to send
		Email email = new Email();
		email.setToEmail("test@example.com");
		email.setSubject("Test Email");
		email.setContent("This is a test email");

		// Mock the send method of the EmailSender
		Mockito.doNothing().when(emailSenderMock).send(email);

		// Send the email and verify that the EmailSender's send method was called
		emailService.sendEmail(email);

		Mockito.verify(emailSenderMock, Mockito.times(1)).send(email);
	}
}
