package com.adidas.backend.prioritysaleservice.model;

import java.time.Instant;

import lombok.Builder;
import lombok.Data;

@Data
public class User implements Comparable<User> {
	private String email;
	private Integer points;
	private Instant subscriptionDate;

	@Override
	public int compareTo(User otherUser) {
		// Compare by points first
		int result = Integer.compare(otherUser.getPoints(), this.points);
		if (result == 0) {
			// If points are equal, compare by subscription date
			result = this.subscriptionDate.compareTo(otherUser.getSubscriptionDate());
		}
		return result;
	}
}
