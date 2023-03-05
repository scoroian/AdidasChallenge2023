package com.adidas.emailservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.adidas.backend.emailservice.exception.EmailSendingException;
import com.adidas.backend.emailservice.model.Email;
import com.adidas.backend.emailservice.service.SmtpEmailSender;

@ExtendWith(MockitoExtension.class)
public class SmtpEmailSenderTest {

	@Mock
	private JavaMailSenderImpl javaMailSenderImpl;

	@InjectMocks
	private SmtpEmailSender smtpEmailSender;

	@Captor
	private ArgumentCaptor<MimeMessage> mimeMessageCaptor;

	private Email email;

	@BeforeEach
	public void setup() {
		email = new Email();
		email.setToEmail("test@example.com");
		email.setSubject("Test subject");
		email.setContent("Test content");
	}

	@Test
	public void testSendEmail() throws MessagingException, IOException {
		smtpEmailSender.send(email);

		verify(javaMailSenderImpl).send(mimeMessageCaptor.capture());
		MimeMessage mimeMessage = mimeMessageCaptor.getValue();

		assertEquals(email.getToEmail(), mimeMessage.getAllRecipients()[0].toString());
		assertEquals(email.getSubject(), mimeMessage.getSubject());
		assertEquals(email.getContent(), mimeMessage.getContent().toString());
	}

	@Test
    public void testSendEmailWithException() throws MessagingException {
        when(javaMailSenderImpl.createMimeMessage()).thenThrow(new MessagingException());

        try {
            smtpEmailSender.send(email);
        } catch (EmailSendingException e) {
            assertEquals("Failed to send email.", e.getMessage());
        }
    }
}
