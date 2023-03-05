package com.adidas.emailservice.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.adidas.backend.emailservice.model.Email;

public class EmailTest {

	@Test
	public void testEmailFields() {
		// Create an email
		Email email = new Email();
		email.setToEmail("test@example.com");
		email.setSubject("Test Email");
		email.setContent("This is a test email");

		// Check the email fields
		assertEquals("test@example.com", email.getToEmail());
		assertEquals("Test Email", email.getSubject());
		assertEquals("This is a test email", email.getContent());
	}
}