package com.adidas.backend.publicservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adidas.backend.publicservice.service.PublicService;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping(value = "/public")
public class PublicServiceController {

	@Autowired
	private PublicService publicService;

	public PublicServiceController(PublicService publicService) {
		this.publicService = publicService;
	}

	@PostMapping
	public ResponseEntity<String> getUser(@RequestParam(value = "email") final String email) {
		log.info("Starting request to get user with email: {}", email);
		String response = publicService.addUser(email);
		log.info("Members service response: {}", response);
		if ("User added to the queue.".equalsIgnoreCase(response)) {
			return ResponseEntity.ok(response);
		} else {
			log.error("Error processing request to get user with email {}: {}", email, response);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

}
