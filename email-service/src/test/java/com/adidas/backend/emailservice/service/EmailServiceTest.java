package com.adidas.backend.emailservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

@SpringBootTest
class EmailServiceTest {

	@InjectMocks
	private EmailService emailService;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testSendEmailSuccess() throws Exception {
		Logger fooLogger = (Logger) LoggerFactory.getLogger(EmailService.class);

		ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
		listAppender.start();

		fooLogger.addAppender(listAppender);
		String email = "test@adiclub.com";
		emailService.sendEmail(email);
		List<ILoggingEvent> logsList = listAppender.list;
		assertEquals(Level.INFO, logsList.get(0).getLevel());
		assertEquals("Preparing to send email to: " + email, logsList.get(0).getMessage());
		assertEquals(Level.INFO, logsList.get(1).getLevel());
		assertEquals("Email sent successfully to: test@adiclub.com, with subject: "
				+ "You have been selected for the new Adidas sneaker release and body: "
				+ "Congratulations! You have been selected to purchase the latest Adidas "
				+ "sneakers before their official release. Follow the link below to "
				+ "complete your purchase.", logsList.get(1).getMessage());
	}

	@Test
	void testSendEmailFailure() throws Exception {
		String email = "";
		assertThrows(NullPointerException.class, () -> emailService.sendEmail(email));
	}

}
