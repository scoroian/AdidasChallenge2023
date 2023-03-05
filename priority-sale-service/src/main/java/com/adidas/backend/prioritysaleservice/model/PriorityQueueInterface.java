package com.adidas.backend.prioritysaleservice.model;

import com.adidas.backend.prioritysaleservice.exception.DuplicateElementException;

public interface PriorityQueueInterface<T> {

	/**
	 * Inserts the specified element into this priority queue.
	 *
	 * @param item the element to insert
	 * @return {@code true} if the element was added to this queue, else
	 *         {@code false}
	 * @throws ClassCastException   if the specified element cannot be compared with
	 *                              elements currently in this priority queue
	 *                              according to the queue's ordering
	 * @throws NullPointerException if the specified element is null
	 */
	boolean add(T item) throws DuplicateElementException;

	/**
	 * Retrieves, but does not remove, the head of this queue, or returns
	 * {@code null} if this queue is empty.
	 *
	 * @return the head of this queue, or {@code null} if this queue is empty
	 */
	T peek();

	/**
	 * Retrieves and removes the head of this queue, or returns {@code null} if this
	 * queue is empty.
	 *
	 * @return the head of this queue, or {@code null} if this queue is empty
	 */
	T poll();

	/**
	 * Returns the number of elements in this collection.
	 *
	 * @return the number of elements in this collection
	 */
	int size();

	/**
	 * Returns true if this collection contains no elements.
	 *
	 * @return true if this collection contains no elements, otherwise false
	 */
	boolean isEmpty();

	/**
	 * Returns true if this priority queue contains the specified element, false
	 * otherwise.
	 * 
	 * @param element the element to check for
	 * @return true if this priority queue contains the specified element, false
	 *         otherwise
	 */
	boolean contains(T item);

}
