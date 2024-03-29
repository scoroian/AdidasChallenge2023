package com.adidas.backend.prioritysaleservice.service;

import com.adidas.backend.prioritysaleservice.model.User;

public class PriorityComparatorImpl implements PriorityComparator{

    public int comparePriority(User u1, User u2) {
        if (u1.getPoints() > u2.getPoints()) {
            return -1;
        } else if (u1.getPoints() < u2.getPoints()) {
            return 1;
        } else {
            return u1.getSubscriptionDate().compareTo(u2.getSubscriptionDate());
        }
    }

    public int compare(User u1, User u2) {
        return comparePriority(u1, u2);
    }
}
