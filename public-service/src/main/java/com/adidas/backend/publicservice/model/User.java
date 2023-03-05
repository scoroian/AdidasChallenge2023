package com.adidas.backend.publicservice.model;

import java.time.Instant;

import lombok.Data;

@Data
public class User {
	private String email;
	private Integer points;
	private Instant subscriptionDate;
}
