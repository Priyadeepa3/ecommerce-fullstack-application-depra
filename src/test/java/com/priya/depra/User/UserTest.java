package com.priya.depra.User;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testUserCreation() {
        User user = new User();

        user.setId(1L);
        user.setName("Priya");
        user.setEmail("priya@gmail.com");
        user.setPassword("123456");

        assertEquals(1L, user.getId());
        assertEquals("Priya", user.getName());
        assertEquals("priya@gmail.com", user.getEmail());
        assertEquals("123456", user.getPassword());
    }

    @Test
    void testUserNotNull() {
        User user = new User();

        user.setName("Test");

        assertNotNull(user);
        assertNotNull(user.getName());
    }

    @Test
    void testUserEmailValidationExample() {
        User user = new User();
        user.setEmail("test@gmail.com");

        assertTrue(user.getEmail().contains("@"));
    }
}