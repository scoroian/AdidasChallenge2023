package com.adidas.backend.prioritysaleservice.service;

import org.springframework.stereotype.Component;

import com.adidas.backend.prioritysaleservice.exception.DuplicateElementException;
import com.adidas.backend.prioritysaleservice.model.User;

@Component
public class PriorityQueueImpl implements PriorityQueue<User> {

	private final java.util.PriorityQueue<User> priorityQueue;

	public PriorityQueueImpl() {
		priorityQueue = new java.util.PriorityQueue<>(new PriorityComparatorImpl());
	}

	@Override
	public boolean add(User user) throws DuplicateElementException {
		if (user == null) {
			throw new IllegalArgumentException("User cannot be null.");
		}
		if (contains(user)) {
			throw new DuplicateElementException(
					"User with email " + user.getEmail() + " already exists in the priority queue.");
		}
		return priorityQueue.add(user);
	}

	@Override
	public User peek() {
		return priorityQueue.peek();
	}

	@Override
	public User poll() {
		return priorityQueue.poll();
	}

	@Override
	public int size() {
		return priorityQueue.size();
	}

	@Override
	public boolean isEmpty() {
		return priorityQueue.isEmpty();
	}

	@Override
	public boolean contains(User user) {
		for (User u : priorityQueue) {
			if (u.getEmail().equals(user.getEmail())) {
				return true;
			}
		}
		return false;
	}

}
