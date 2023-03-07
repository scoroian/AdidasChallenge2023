package com.adidas.backend.prioritysaleservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adidas.backend.prioritysaleservice.exception.DuplicateElementException;
import com.adidas.backend.prioritysaleservice.service.PriorityQueueService;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping(value = "/priority-queue")
public class PriorityQueueController {

	@Autowired
	private PriorityQueueService prioritySalesService;

	public PriorityQueueController(PriorityQueueService prioritySalesService) {
		this.prioritySalesService = prioritySalesService;
	}

	/**
	 * Endpoint for adding a user to the priority queue.
	 *
	 * @param email The email of the user to add to the priority queue.
	 * @return ResponseEntity containing the result of the operation.
	 */
	@PostMapping
	public ResponseEntity<String> addUserToQueue(@RequestParam(value = "email") String email) {
		// If the user's email doesn't end with "@adiclub.com"
		// Return a bad request response with an error message.
		if (!email.endsWith("@adiclub.com")) {
			log.warn("Invalid email address {}", email);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid email address.");
		}
		try {
			// Add the user to the priority queue
			prioritySalesService.addUser(email);
			// Return a response indicating that the user was added successfully
			return ResponseEntity.status(HttpStatus.CREATED).body("User added to the queue.");
		} catch (DuplicateElementException e) {
			// If the user is already in the priority queue, return a conflict response
			log.warn("User with email {} already in the priority queue.", email);
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		} catch (Exception e) {
			// If an error occurred while adding the user to the priority queue, return an
			// internal server error response
			log.error("Error adding user with email {} to the queue. Exception: {}", email, e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding user to the queue.");
		}
	}

}
