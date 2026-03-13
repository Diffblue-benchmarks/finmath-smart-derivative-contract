package net.finmath.smartcontract.valuation.service.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for SDCUser class.
 */
class SDCUserTest {

    private SDCUser user;

    @BeforeEach
    void setUp() {
        user = new SDCUser();
    }

    @Test
    void testDefaultConstructor() {
        assertNotNull(user);
        assertNull(user.getUsername());
        assertNull(user.getPassword());
        assertNull(user.getRole());
    }

    @Test
    void testSetAndGetUsername() {
        String username = "john.doe";

        user.setUsername(username);

        assertEquals(username, user.getUsername());
    }

    @Test
    void testSetAndGetPassword() {
        String password = "securePassword123";

        user.setPassword(password);

        assertEquals(password, user.getPassword());
    }

    @Test
    void testSetAndGetRole() {
        String role = "ADMIN";

        user.setRole(role);

        assertEquals(role, user.getRole());
    }

    @Test
    void testSetAllProperties() {
        String username = "jane.smith";
        String password = "pass123";
        String role = "USER";

        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);

        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(role, user.getRole());
    }

    @Test
    void testSetNullUsername() {
        user.setUsername(null);

        assertNull(user.getUsername());
    }

    @Test
    void testSetNullPassword() {
        user.setPassword(null);

        assertNull(user.getPassword());
    }

    @Test
    void testSetNullRole() {
        user.setRole(null);

        assertNull(user.getRole());
    }

    @Test
    void testSetEmptyUsername() {
        user.setUsername("");

        assertEquals("", user.getUsername());
    }

    @Test
    void testSetEmptyPassword() {
        user.setPassword("");

        assertEquals("", user.getPassword());
    }

    @Test
    void testSetEmptyRole() {
        user.setRole("");

        assertEquals("", user.getRole());
    }

    @Test
    void testOverwriteProperties() {
        user.setUsername("user1");
        user.setPassword("pass1");
        user.setRole("ROLE1");

        user.setUsername("user2");
        user.setPassword("pass2");
        user.setRole("ROLE2");

        assertEquals("user2", user.getUsername());
        assertEquals("pass2", user.getPassword());
        assertEquals("ROLE2", user.getRole());
    }

    @Test
    void testUsernameWithSpecialCharacters() {
        String username = "user@domain.com";

        user.setUsername(username);

        assertEquals(username, user.getUsername());
    }

    @Test
    void testRoleWithMultipleValues() {
        String role = "ADMIN,USER,DEVELOPER";

        user.setRole(role);

        assertEquals(role, user.getRole());
    }
}
