package com.adidas.backend.publicservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/public")
public class PublicServiceController {

	@Autowired
	private Environment env;

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/user")
	public ResponseEntity<String> getUser(@RequestParam(value = "email") final String email) {
		try {
			String urlAdiClub = env.getProperty("url.membersService") + "?email=" + email;
			String response = restTemplate.getForObject(urlAdiClub, String.class);

			return ResponseEntity.ok(response);
		} catch (RestClientException e) {
			// Log or handle the exception
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
