package com.adidas.backend.prioritysaleservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.Duration;
import java.time.Instant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.adidas.backend.prioritysaleservice.exception.DuplicateElementException;
import com.adidas.backend.prioritysaleservice.model.PriorityQueueImpl;
import com.adidas.backend.prioritysaleservice.model.User;
import com.adidas.backend.prioritysaleservice.service.PriorityQueueService;

@SpringBootTest
public class PriorityQueueServiceTest {

    private PriorityQueueService priorityQueueService;

    @BeforeEach
    public void setUp() {
        PriorityQueueImpl priorityQueue = new PriorityQueueImpl();
        priorityQueueService = new PriorityQueueService(priorityQueue);
    }

    @Test
    public void testAddUser() throws DuplicateElementException {
        User user1 = new User();
        user1.setEmail("user1@adiclub.com");
        user1.setPoints(100);
        user1.setSubscriptionDate(Instant.now());

        User user2 = new User();
        user2.setEmail("user2@adiclub.com");
        user2.setPoints(200);
        user2.setSubscriptionDate(Instant.now());

        User user3 = new User();
        user3.setEmail("user3@adiclub.com");
        user3.setPoints(300);
        user3.setSubscriptionDate(Instant.now());

        assertTrue(priorityQueueService.addUser(user1));
        assertTrue(priorityQueueService.addUser(user2));
        assertTrue(priorityQueueService.addUser(user3));

        assertEquals(user3, priorityQueueService.getMostPriorityUser());
    }
    
    @Test
    public void testAddDuplicateUser() {
        User user1 = new User();
        user1.setEmail("test1@adiclub.com");
        user1.setPoints(100);
        user1.setSubscriptionDate(Instant.now());

        User user2 = new User();
        user2.setEmail("test1@adiclub.com"); // duplicate email
        user2.setPoints(200);
        user2.setSubscriptionDate(Instant.now().plusSeconds(60));

        try {
            assertTrue(priorityQueueService.addUser(user1));
            assertFalse(priorityQueueService.addUser(user2)); // should not add duplicate user
        } catch (DuplicateElementException e) {
            fail("Unexpected DuplicateElementException thrown");
        }
        
        // verify that only one user was added
        assertEquals(1, priorityQueueService.getMostPriorityUser());
    }
    
    @Test
    public void testAddUserWithPriority() throws DuplicateElementException {
        User user1 = new User();
        user1.setEmail("user1@adiclub.com");
        user1.setPoints(10);
        user1.setSubscriptionDate(Instant.now().minus(Duration.ofDays(1)));

        User user2 = new User();
        user2.setEmail("user2@adiclub.com");
        user2.setPoints(20);
        user2.setSubscriptionDate(Instant.now());

        User user3 = new User();
        user3.setEmail("user3@adiclub.com");
        user3.setPoints(30);
        user3.setSubscriptionDate(Instant.now().plus(Duration.ofDays(1)));

        assertTrue(priorityQueueService.addUser(user1));
        assertTrue(priorityQueueService.addUser(user2));
        assertTrue(priorityQueueService.addUser(user3));

        User retrievedUser1 = priorityQueueService.getMostPriorityUser();
        assertEquals(user3.getEmail(), retrievedUser1.getEmail());

        User retrievedUser2 = priorityQueueService.getMostPriorityUser();
        assertEquals(user2.getEmail(), retrievedUser2.getEmail());

        User retrievedUser3 = priorityQueueService.getMostPriorityUser();
        assertEquals(user1.getEmail(), retrievedUser3.getEmail());

        assertNull(priorityQueueService.getMostPriorityUser());
    }

    @Test
    public void testAddUserWithSamePriorityUsingSetters() throws DuplicateElementException {
        // Create users with the same priority
        User user1 = new User();
        user1.setEmail("user1@adiclub.com");
        user1.setPoints(10);
        user1.setSubscriptionDate(Instant.now());

        User user2 = new User();
        user2.setEmail("user2@adiclub.com");
        user2.setPoints(10);
        user2.setSubscriptionDate(Instant.now().minus(Duration.ofDays(1)));

        User user3 = new User();
        user3.setEmail("user3@adiclub.com");
        user3.setPoints(10);
        user3.setSubscriptionDate(Instant.now().plus(Duration.ofDays(1)));

        // Add users to priority queue
        assertTrue(priorityQueueService.addUser(user1));
        assertTrue(priorityQueueService.addUser(user2));
        assertTrue(priorityQueueService.addUser(user3));

        // Verify order of users in priority queue
        User firstUser = priorityQueueService.getMostPriorityUser();
        assertEquals(user2, firstUser);

        User secondUser = priorityQueueService.getMostPriorityUser();
        assertEquals(user1, secondUser);

        User thirdUser = priorityQueueService.getMostPriorityUser();
        assertEquals(user3, thirdUser);

    }


}