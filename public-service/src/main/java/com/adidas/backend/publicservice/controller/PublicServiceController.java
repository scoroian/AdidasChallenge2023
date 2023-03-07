package com.adidas.backend.publicservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping(value = "/public")
public class PublicServiceController {

	@Autowired
	private Environment env;

	@Autowired
	private RestTemplate restTemplate;

	public PublicServiceController(Environment env, RestTemplate restTemplate) {
		this.env = env;
		this.restTemplate = restTemplate;
	}

	@PostMapping
	public ResponseEntity<String> getUser(@RequestParam(value = "email") final String email) {
		try {
			log.info("Starting request to get user with email: {}", email);
			String urlAdiClub = env.getProperty("url.priorityQueue");

			MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
			requestBody.add("email", email);

//			String response = restTemplate.postForObject(urlAdiClub, requestBody, String.class);

			HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody);
			log.info("Request body: {}", requestEntity.getBody());
			String response = restTemplate.postForObject(urlAdiClub, requestEntity, String.class);

			log.info("Members service response: {}", response);
			return ResponseEntity.ok(response);
		} catch (RestClientException e) {
			log.error("Error processing request to get user with email {}: {}", email, e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
