package com.adidas.backend.prioritysaleservice.model;

import java.util.Comparator;

public interface PriorityComparatorInterface extends Comparator<User> {
    int comparePriority(User u1, User u2);
}
