package com.adidas.backend.prioritysaleservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.Instant;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import com.adidas.backend.prioritysaleservice.exception.DuplicateElementException;
import com.adidas.backend.prioritysaleservice.model.User;

@SpringBootTest
public class PriorityQueueServiceTest {

	@Mock
	private PriorityQueueImpl priorityQueueImpl;

	@Mock
	private RestTemplate restTemplate;

	@Mock
	private Environment env;

	@InjectMocks
	private PriorityQueueService priorityQueueService;

	@Test
	void whenAddUser_userIsAdded() throws DuplicateElementException {
		when(env.getProperty("url.adiClub")).thenReturn("http://adidas-be-challenge-adiclubservice:8082/adiclub");
		String email = "email@adiclub.com";
		User user = new User();
		user.setEmail(email);
		user.setPoints(500);
		user.setSubscriptionDate(Instant.now());
		
		when(restTemplate.getForObject("http://adidas-be-challenge-adiclubservice:8082/adiclub?emailAddress=email@adiclub.com", User.class))
		.thenReturn(user);
		
		when(priorityQueueImpl.add(user)).thenReturn(true);
		
		boolean result = priorityQueueService.addUser(email);
		assertEquals(true, result);
	}

	@Test
	void whenAddDuplicatedUser_duplicatedException() throws DuplicateElementException {
		when(env.getProperty("url.adiClub")).thenReturn("http://adidas-be-challenge-adiclubservice:8082/adiclub");
		String email = "email@adiclub.com";
		User user = new User();
		user.setEmail(email);
		user.setPoints(500);
		user.setSubscriptionDate(Instant.now());
		
		when(restTemplate.getForObject("http://adidas-be-challenge-adiclubservice:8082/adiclub?emailAddress=email@adiclub.com", User.class))
		.thenReturn(user);
		
		when(priorityQueueImpl.add(user)).thenReturn(false);
		
		DuplicateElementException thrown = assertThrows(
				DuplicateElementException.class,
		           () -> priorityQueueService.addUser(email)
		    );

	    assertTrue(thrown.getMessage().contentEquals("User with email " + email
		+ " already exists in the priority queue."));
	}

	@Test
	void whenGetMostPriorityUser_returnUser() {
		when(priorityQueueImpl.isEmpty()).thenReturn(false);
		String email = "email@adiclub.com";
		User user = new User();
		user.setEmail(email);
		user.setPoints(500);
		user.setSubscriptionDate(Instant.now());
		when(priorityQueueImpl.poll()).thenReturn(user);
		User result = priorityQueueService.getMostPriorityUser();
		
		assertEquals(user, result);
	}
	
	@Test
	void whenGetMostPriorityUser_returnNull() {
		when(priorityQueueImpl.isEmpty()).thenReturn(true);
		User result = priorityQueueService.getMostPriorityUser();
		assertNull(result);
	}

}
