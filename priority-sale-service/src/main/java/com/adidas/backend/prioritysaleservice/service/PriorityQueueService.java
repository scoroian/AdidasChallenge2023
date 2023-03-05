package com.adidas.backend.prioritysaleservice.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.adidas.backend.prioritysaleservice.exception.DuplicateElementException;
import com.adidas.backend.prioritysaleservice.model.PriorityQueueImpl;
import com.adidas.backend.prioritysaleservice.model.User;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@Scope("singleton")
public class PriorityQueueService {

	// Priority queue to hold the users
	private final PriorityQueueImpl priorityQueue;

	// Constructor to initialize the priority queue
	public PriorityQueueService(PriorityQueueImpl priorityQueue) {
		this.priorityQueue = priorityQueue;
	}

	// Method to add a user to the priority queue
	public boolean addUser(User user) throws DuplicateElementException {
		boolean added = priorityQueue.add(user);
		if (!added) {
			String errorMessage = "User with email " + user.getEmail() + " already exists in the priority queue.";
			log.error(errorMessage);
			throw new DuplicateElementException(errorMessage);
		}
		String successMessage = "User with email " + user.getEmail() + " added to the priority queue.";
		log.info(successMessage);
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
}
