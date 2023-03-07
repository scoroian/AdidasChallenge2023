package com.adidas.backend.prioritysaleservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.adidas.backend.prioritysaleservice.exception.DuplicateElementException;
import com.adidas.backend.prioritysaleservice.model.PriorityQueueImpl;
import com.adidas.backend.prioritysaleservice.model.User;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@Scope("singleton")
public class PriorityQueueService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private Environment env;
	// Priority queue to hold the users
	private final PriorityQueueImpl priorityQueue;

	// Constructor to initialize the priority queue
	public PriorityQueueService(PriorityQueueImpl priorityQueue) {
		this.priorityQueue = priorityQueue;
	}

	// Method to add a user to the priority queue
	public boolean addUser(String email) throws DuplicateElementException {
		// Send a POST request to the "adiClub" service to get the user with the given
		// email
		String urlAdiClub = env.getProperty("url.adiClub");
		User adiClubUser = restTemplate.getForObject(urlAdiClub, User.class);

		boolean added = priorityQueue.add(adiClubUser);
		if (!added) {
			String errorMessage = "User with email " + adiClubUser.getEmail()
					+ " already exists in the priority queue.";
			log.error(errorMessage);
			throw new DuplicateElementException(errorMessage);
		}
		// Log the successful operation
		log.info("User {} added to the queue.", adiClubUser.getEmail());
		return true;
	}

	// Method to get the user with the highest priority
	public User getMostPriorityUser() {
		if (priorityQueue.isEmpty()) {
			log.warn("Priority queue is empty.");
			return null;
		}
		User mostPriorityUser = priorityQueue.poll();
		String successMessage = "User with email " + mostPriorityUser.getEmail()
				+ " retrieved from the priority queue.";
		log.info(successMessage);
		return mostPriorityUser;
	}

	/**
	 * Method to periodically call the "send-email" endpoint of the email-service
	 * API for the user with the highest priority.
	 */
	@Scheduled(fixedDelay = 60000) // Execute every minute
	public void sendEmailToMostPriorityUser() {
		// Get the user with the highest priority
		User mostPriorityUser = getMostPriorityUser();
		if (mostPriorityUser == null) {
			log.info("No user found in the priority queue.");
			return;
		}

		String email = mostPriorityUser.getEmail();
		String urlEmailService = env.getProperty("url.emailService") + "?email=" + email;

		try {
			// Send a POST request to the "email-service" API to send an email to the user
			restTemplate.postForObject(urlEmailService, null, Void.class);
			log.info("Email sent to user with email: {}", email);
		} catch (Exception e) {
			log.error("Failed to send email to user with email: {}", email, e);
		}
	}

}
