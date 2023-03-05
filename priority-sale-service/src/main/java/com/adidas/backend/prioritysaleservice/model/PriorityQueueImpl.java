package com.adidas.backend.prioritysaleservice.model;

import java.util.PriorityQueue;

import org.springframework.stereotype.Component;

import com.adidas.backend.prioritysaleservice.exception.DuplicateElementException;

@Component
public class PriorityQueueImpl implements PriorityQueueInterface<User> {

	private final PriorityQueue<User> priorityQueue;

	public PriorityQueueImpl() {
		priorityQueue = new PriorityQueue<>(new PriorityComparatorImpl());
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
