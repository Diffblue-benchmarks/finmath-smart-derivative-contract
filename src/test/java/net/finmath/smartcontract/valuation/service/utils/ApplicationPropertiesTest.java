package net.finmath.smartcontract.valuation.service.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ApplicationProperties class.
 */
class ApplicationPropertiesTest {

    private ApplicationProperties properties;

    @BeforeEach
    void setUp() {
        properties = new ApplicationProperties();
    }

    @Test
    void testDefaultConstructor() {
        assertNotNull(properties);
        assertNull(properties.getUsers());
    }

    @Test
    void testSetAndGetUsers() {
        List<SDCUser> users = new ArrayList<>();
        SDCUser user1 = new SDCUser();
        user1.setUsername("user1");
        users.add(user1);

        properties.setUsers(users);

        assertEquals(users, properties.getUsers());
        assertEquals(1, properties.getUsers().size());
    }

    @Test
    void testSetEmptyUsersList() {
        List<SDCUser> emptyList = new ArrayList<>();

        properties.setUsers(emptyList);

        assertNotNull(properties.getUsers());
        assertEquals(0, properties.getUsers().size());
    }

    @Test
    void testSetMultipleUsers() {
        SDCUser user1 = new SDCUser();
        user1.setUsername("admin");
        user1.setPassword("pass1");
        user1.setRole("ADMIN");

        SDCUser user2 = new SDCUser();
        user2.setUsername("user");
        user2.setPassword("pass2");
        user2.setRole("USER");

        List<SDCUser> users = Arrays.asList(user1, user2);

        properties.setUsers(users);

        assertEquals(2, properties.getUsers().size());
        assertEquals("admin", properties.getUsers().get(0).getUsername());
        assertEquals("user", properties.getUsers().get(1).getUsername());
    }

    @Test
    void testSetNullUsers() {
        properties.setUsers(null);

        assertNull(properties.getUsers());
    }

    @Test
    void testOverwriteUsers() {
        List<SDCUser> firstList = new ArrayList<>();
        SDCUser user1 = new SDCUser();
        user1.setUsername("first");
        firstList.add(user1);

        properties.setUsers(firstList);
        assertEquals(1, properties.getUsers().size());

        List<SDCUser> secondList = new ArrayList<>();
        SDCUser user2 = new SDCUser();
        user2.setUsername("second");
        secondList.add(user2);

        properties.setUsers(secondList);
        assertEquals(1, properties.getUsers().size());
        assertEquals("second", properties.getUsers().get(0).getUsername());
    }

    @Test
    void testUsersListIsModifiable() {
        List<SDCUser> users = new ArrayList<>();
        SDCUser user = new SDCUser();
        user.setUsername("test");
        users.add(user);

        properties.setUsers(users);

        SDCUser newUser = new SDCUser();
        newUser.setUsername("new");
        properties.getUsers().add(newUser);

        assertEquals(2, properties.getUsers().size());
    }

    @Test
    void testWithCompleteUserData() {
        SDCUser user = new SDCUser();
        user.setUsername("testuser");
        user.setPassword("securePassword");
        user.setRole("DEVELOPER");

        List<SDCUser> users = Arrays.asList(user);
        properties.setUsers(users);

        assertEquals("testuser", properties.getUsers().get(0).getUsername());
        assertEquals("securePassword", properties.getUsers().get(0).getPassword());
        assertEquals("DEVELOPER", properties.getUsers().get(0).getRole());
    }
}
