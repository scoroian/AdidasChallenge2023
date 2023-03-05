package com.adidas.backend.prioritysaleservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.adidas.backend.prioritysaleservice.exception.DuplicateElementException;
import com.adidas.backend.prioritysaleservice.model.User;
import com.adidas.backend.prioritysaleservice.service.PriorityQueueService;

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
	public ResponseEntity<String> addUserToQueue(@RequestParam(value = "email") final String email) {
		// If the user's email doesn't end with "@adiclub.com"
		// Return a bad request response with an error message.
		if (!email.endsWith("@adiclub.com")) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid email address.");
		}

		try {
			// Send a GET request to the "adiClub" service to get the user with the given
			// email
			String urlAdiClub = env.getProperty("url.adiClub") + "?emailAddress=" + email;
			User adiClubUser = restTemplate.getForObject(urlAdiClub, User.class);

			// Add the user to the priority queue
			prioritySalesService.addUser(adiClubUser);

			// Return a response indicating that the user was added successfully
			return ResponseEntity.status(HttpStatus.CREATED).body("User added to the queue.");

		} catch (DuplicateElementException e) {
			// If the user is already in the priority queue, return a conflict response
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());

		} catch (Exception e) {
			// If an error occurred while adding the user to the priority queue, return an
			// internal server error response
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
		// Call the getMostPriorityUser method from the prioritySalesService to get the
		// user with the highest priority
		User mostPriorityUser = prioritySalesService.getMostPriorityUser();
		// If there is no user in the queue, return a response indicating that the user
		// was not found
		if (mostPriorityUser == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		// If there is a user in the queue, return a response containing the user with
		// the highest priority
		return ResponseEntity.status(HttpStatus.OK).body(mostPriorityUser.getEmail());
	}

}
