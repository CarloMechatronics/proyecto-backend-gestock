package com.proyecto.gestock.useraccount.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserAccountTest {
    private UserAccount userAccount;

    @BeforeEach
    public void setUp() {
        userAccount = new UserAccount();
        userAccount.setUsername("User 1");
        userAccount.setPassword("password1");
        userAccount.setEmail("user1@gmail.com");
        userAccount.setRole("ADMIN");
        userAccount.setUrlProfilePhoto("profile.jpg");
    }

    @Test
    public void testUserCreation() {
        assertNotNull(userAccount);
        assertEquals("User 1", userAccount.getUsername());
        assertEquals("password1", userAccount.getPassword());
        assertEquals("user1@gmail.com", userAccount.getEmail());
        assertEquals("profile.jpg", userAccount.getUrlProfilePhoto());
        assertEquals("ADMIN", userAccount.getRole());
    }

    @Test
    public void testUserUpdate() {
        userAccount.setUsername("User 2");
        userAccount.setPassword("password2");
        userAccount.setEmail("user2@gmail.com");
        userAccount.setRole("ADMIN");
        userAccount.setUrlProfilePhoto("profile.jpg");
        assertEquals("User 2", userAccount.getUsername());
        assertEquals("password2", userAccount.getPassword());
    }
}
