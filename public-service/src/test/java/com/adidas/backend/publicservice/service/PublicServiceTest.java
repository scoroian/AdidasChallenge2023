package com.adidas.backend.publicservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
public class PublicServiceTest {

	@Mock
	private RestTemplate restTemplate;

	@Mock
	private Environment env;
	
	@InjectMocks
	private PublicService publicService;
	
	@Test
	void whenAddUser_returnString() {
		when(env.getProperty("url.priorityQueue")).thenReturn("http://adidas-be-challenge-prioritysaleservice:8081/priority-queue");
		String email = "email@adiclub.com";
		
		String returnMessage = "User added to the queue.";
		
		when(restTemplate.getForObject("http://adidas-be-challenge-prioritysaleservice:8081/priority-queue?email=email@adiclub.com", String.class))
		.thenReturn(returnMessage);
		
		when(publicService.addUser(email)).thenReturn(returnMessage);
		
		String result = publicService.addUser(email);
		assertEquals(result, returnMessage);
	}
	
}
