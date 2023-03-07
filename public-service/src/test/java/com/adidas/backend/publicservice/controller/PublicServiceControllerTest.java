package com.adidas.backend.publicservice.controller;

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

import com.adidas.backend.publicservice.service.PublicService;

@SpringBootTest
@AutoConfigureMockMvc
public class PublicServiceControllerTest {

	@MockBean
	public PublicService publicService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void whenAddUser_returnOk() throws Exception {
		String email = "test@adiclub.com";
		when(publicService.addUser(email)).thenReturn("User added to the queue.");
		this.mockMvc.perform(post("/public").param("email", email)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("User added to the queue."));
	}

	@Test
	void whenAddUser_returnInternalServerError() throws Exception {
		String email = "test@adiclub.com";
		when(publicService.addUser(email)).thenReturn("Error adding user to the queue.");
		this.mockMvc.perform(post("/public").param("email", email)).andExpect(status().isInternalServerError())
				.andExpect(MockMvcResultMatchers.content().string("Error adding user to the queue."));
	}

}
