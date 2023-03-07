package com.adidas.backend.publicservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PublicService {

	@Autowired
	private Environment env;

	@Autowired
	private RestTemplate restTemplate;

	public PublicService(Environment env, RestTemplate restTemplate) {
		this.env = env;
		this.restTemplate = restTemplate;
	}

	public String addUser(String email) {
		String urlAdiClub = env.getProperty("url.priorityQueue") + "?email=" + email;
		String response = restTemplate.postForObject(urlAdiClub, String.class, String.class);

		return response;
	}

}
