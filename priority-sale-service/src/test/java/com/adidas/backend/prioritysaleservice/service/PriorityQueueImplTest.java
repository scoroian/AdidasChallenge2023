package com.adidas.backend.prioritysaleservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.time.Instant;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import com.adidas.backend.prioritysaleservice.exception.DuplicateElementException;
import com.adidas.backend.prioritysaleservice.model.User;

@SpringBootTest
public class PriorityQueueImplTest {

	@InjectMocks
	private PriorityQueueImpl priorityQueueImpl;
	
	@Test
	void whenAddUser_returnTrue() throws DuplicateElementException {
		String email = "email@adiclub.com";
		User user = new User();
		user.setEmail(email);
		user.setPoints(500);
		user.setSubscriptionDate(Instant.now());
		
		boolean result = priorityQueueImpl.add(user);
		assertTrue(result);
		
		User resultUser = priorityQueueImpl.peek();
		
		assertEquals(user, resultUser);
	}
	
	@Test
	void whenAddNullUser_illegalArgumentException() throws DuplicateElementException {
		User user = null;
		
		IllegalArgumentException thrown = assertThrows(
				IllegalArgumentException.class,
		           () -> priorityQueueImpl.add(user)
		    );

	    assertTrue(thrown.getMessage().contentEquals("User cannot be null." ));
	}
	
	@Test
	void whenAddDuplicatedUser_duplicateElementException() throws DuplicateElementException {
		String email = "email@adiclub.com";
		User user1 = new User();
		user1.setEmail(email);
		user1.setPoints(500);
		user1.setSubscriptionDate(Instant.now());
		priorityQueueImpl.add(user1);
		User user2 = new User();
		user2.setEmail(email);
		user2.setPoints(600);
		user2.setSubscriptionDate(Instant.now());
		
		DuplicateElementException thrown = assertThrows(
				DuplicateElementException.class,
		           () -> priorityQueueImpl.add(user2)
		    );

	    assertTrue(thrown.getMessage().contentEquals("User with email " + user2.getEmail() + " already exists in the priority queue."));
	}
	
	@Test
	void whenAddDifferentUsers_returnMostPrioritaryUserPoints() throws DuplicateElementException {
		String emailPepe = "pepe@adiclub.com";
		User userPepe = new User();
		userPepe.setEmail(emailPepe);
		userPepe.setPoints(600);
		userPepe.setSubscriptionDate(Instant.now());
		priorityQueueImpl.add(userPepe);
		String emailJuan = "juan@adiclub.com";
		User userJuan = new User();
		userJuan.setEmail(emailJuan);
		userJuan.setPoints(500);
		userJuan.setSubscriptionDate(Instant.now());
		priorityQueueImpl.add(userJuan);
		
		User responseUser = priorityQueueImpl.peek();
		assertEquals(userPepe, responseUser);
		assertEquals(userPepe, priorityQueueImpl.poll());
		assertEquals(1, priorityQueueImpl.size());
		assertEquals(userJuan, priorityQueueImpl.poll());
		assertTrue(priorityQueueImpl.isEmpty());
	}
	
	@Test
	void whenAddDifferentUsers_returnMostPrioritaryUserDate() throws DuplicateElementException {
		String emailPepe = "pepe@adiclub.com";
		User userPepe = new User();
		userPepe.setEmail(emailPepe);
		userPepe.setPoints(600);
		userPepe.setSubscriptionDate(Instant.now());
		priorityQueueImpl.add(userPepe);
		String emailJuan = "juan@adiclub.com";
		User userJuan = new User();
		userJuan.setEmail(emailJuan);
		userJuan.setPoints(600);
		userJuan.setSubscriptionDate((Instant.now().minus(Duration.ofHours(2))));
		priorityQueueImpl.add(userJuan);
		
		User responseUser = priorityQueueImpl.peek();
		assertEquals(userJuan, responseUser);
		assertEquals(userJuan, priorityQueueImpl.poll());
		assertEquals(userPepe, priorityQueueImpl.poll());
		assertTrue(priorityQueueImpl.isEmpty());
	}
	
}
