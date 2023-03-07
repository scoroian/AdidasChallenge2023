package com.adidas.backend.prioritysaleservice.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.adidas.backend.prioritysaleservice.exception.DuplicateElementException;
import com.adidas.backend.prioritysaleservice.service.PriorityQueueService;

@SpringBootTest
@AutoConfigureMockMvc
public class PriorityQueueControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PriorityQueueService priorityQueueService;

	@Test
	void whenAddUserToQueue_returnCreated() throws Exception {
		String email = "test@adiclub.com";
		when(priorityQueueService.addUser(email)).thenReturn(true);
		this.mockMvc.perform(post("/priority-queue").queryParam("email", email)).andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.content().string("User added to the queue."));
	}

	@Test
	void whenAddUserToQueue_returnBadRequest() throws Exception {
		String email = "test@gmail.com";
		this.mockMvc.perform(post("/priority-queue").queryParam("email", email)).andExpect(status().isBadRequest())
				.andExpect(MockMvcResultMatchers.content().string("Invalid email address."));
	}

	@Test
	void whenAddUserToQueue_returnConflict() throws Exception {
		String email = "test@adiclub.com";
		when(priorityQueueService.addUser(email)).thenThrow(
				new DuplicateElementException("User with email test@adiclub.com already in the priority queue."));
		this.mockMvc.perform(post("/priority-queue").queryParam("email", email)).andExpect(status().isConflict())
				.andExpect(MockMvcResultMatchers.content()
						.string("User with email test@adiclub.com already in the priority queue."));
	}

	@Test
	void whenAddUserToQueue_returnInternalServerError() throws Exception {
		String email = "test@adiclub.com";
		when(priorityQueueService.addUser(email)).thenThrow(new RuntimeException("Error adding user to the queue."));
		this.mockMvc.perform(post("/priority-queue").queryParam("email", email))
				.andExpect(status().isInternalServerError())
				.andExpect(MockMvcResultMatchers.content().string("Error adding user to the queue."));
	}

}
