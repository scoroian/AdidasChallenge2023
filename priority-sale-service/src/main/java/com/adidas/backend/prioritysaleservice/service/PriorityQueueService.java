package com.adidas.backend.prioritysaleservice.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.adidas.backend.prioritysaleservice.exception.DuplicateElementException;
import com.adidas.backend.prioritysaleservice.model.PriorityQueueImpl;
import com.adidas.backend.prioritysaleservice.model.User;

@Service
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
			throw new DuplicateElementException(
					"User with email " + user.getEmail() + " already exists in the priority queue.");
		}
		return true;
	}

	// Method to get the user with the highest priority
	public User getMostPriorityUser() {
		if (priorityQueue.isEmpty()) {
			return null;
		}
		User mostPriorityUser = priorityQueue.poll();
		return mostPriorityUser;
	}
}
