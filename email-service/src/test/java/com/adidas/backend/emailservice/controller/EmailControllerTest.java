package com.adidas.backend.emailservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.adidas.backend.emailservice.service.EmailService;

@SpringBootTest
class EmailControllerTest {

	@InjectMocks
	private EmailController emailController;

	@Mock
	private EmailService emailService;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testSendEmailSuccess() throws Exception {
		String email = "test@adiclub.com";
		ResponseEntity<String> response = emailController.sendEmail(email);
		verify(emailService).sendEmail(email);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	void testSendEmailFailure() throws Exception {
		String email = "";
		doThrow(new NullPointerException("Email cannot be null")).when(emailService).sendEmail(email);
		ResponseEntity<String> response = emailController.sendEmail(email);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

}
