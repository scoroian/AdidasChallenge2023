package com.adidas.backend.prioritysaleservice.service;

import java.util.Comparator;

import com.adidas.backend.prioritysaleservice.model.User;

public interface PriorityComparator extends Comparator<User> {
    int comparePriority(User u1, User u2);
}
