package com.adidas.backend.prioritysaleservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.adidas.backend.prioritysaleservice.exception.DuplicateElementException;
import com.adidas.backend.prioritysaleservice.model.User;
import com.adidas.backend.prioritysaleservice.service.PriorityQueueService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/priority-queue")
public class PriorityQueueController {

	@Autowired
	private PriorityQueueService prioritySalesService;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private Environment env;

	/**
	 * Endpoint for adding a user to the priority queue.
	 *
	 * @param email The email of the user to add to the priority queue.
	 * @return ResponseEntity containing the result of the operation.
	 */
	@PostMapping("/user")
	public ResponseEntity<String> addUserToQueue(@RequestBody String email) {
		// If the user's email doesn't end with "@adiclub.com"
		// Return a bad request response with an error message.
		if (!email.endsWith("@adiclub.com")) {
			log.warn("Invalid email address {}", email);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid email address.");
		}

		try {
			// Send a POST request to the "adiClub" service to get the user with the given
			// email
			String urlAdiClub = env.getProperty("url.adiClub");
			User adiClubUser = restTemplate.postForObject(urlAdiClub, email, User.class);

			// Add the user to the priority queue
			prioritySalesService.addUser(adiClubUser);

			// Log the successful operation
			log.info("User {} added to the queue.", adiClubUser.getEmail());

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

	/**
	 * Endpoint to get the user with the highest priority from the priority queue.
	 *
	 * @return ResponseEntity containing the user with the highest priority or an
	 *         error message if there is no user in the queue.
	 */
	@GetMapping("/user/priority")
	public ResponseEntity<String> getMostPriorityUser() {
		log.info("Received request to get most priority user.");
		try {
			User mostPriorityUser = prioritySalesService.getMostPriorityUser();
			if (mostPriorityUser == null) {
				log.warn("No user found in the priority queue.");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			log.info("Retrieved user with email {} from priority queue.", mostPriorityUser.getEmail());
			return ResponseEntity.status(HttpStatus.OK).body(mostPriorityUser.getEmail());
		} catch (Exception e) {
			log.error("An error occurred while retrieving the most priority user.", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error retrieving user from the queue.");
		}
	}

}
