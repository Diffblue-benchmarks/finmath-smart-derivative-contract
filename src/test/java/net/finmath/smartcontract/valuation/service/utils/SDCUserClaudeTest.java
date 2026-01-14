/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */

package net.finmath.smartcontract.valuation.service.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for SDCUser.
 * Tests the constructor and all getter/setter methods with focus on branch and condition coverage.
 *
 * @author Claude Code
 */
class SDCUserClaudeTest {

	/**
	 * Test constructor creates a valid instance.
	 */
	@Test
	void testConstructor_CreatesValidInstance() {
		// Arrange & Act
		SDCUser user = new SDCUser();

		// Assert
		assertNotNull(user, "Constructor should create a non-null instance");
	}

	/**
	 * Test constructor initializes fields to null.
	 * Verifies default state of a newly constructed SDCUser.
	 */
	@Test
	void testConstructor_InitializesFieldsToNull() {
		// Arrange & Act
		SDCUser user = new SDCUser();

		// Assert
		assertNull(user.getUsername(), "Username should be null after construction");
		assertNull(user.getPassword(), "Password should be null after construction");
		assertNull(user.getRole(), "Role should be null after construction");
	}

	/**
	 * Test constructor can be called multiple times to create independent instances.
	 * Verifies that constructor has no side effects and each instance is separate.
	 */
	@Test
	void testConstructor_MultipleInstances_AreIndependent() {
		// Arrange & Act
		SDCUser user1 = new SDCUser();
		SDCUser user2 = new SDCUser();
		SDCUser user3 = new SDCUser();

		// Assert
		assertNotNull(user1, "First user should not be null");
		assertNotNull(user2, "Second user should not be null");
		assertNotNull(user3, "Third user should not be null");

		// Verify they are different instances
		assertNotSame(user1, user2, "Users should be different instances");
		assertNotSame(user2, user3, "Users should be different instances");
		assertNotSame(user1, user3, "Users should be different instances");
	}

	/**
	 * Test that constructor doesn't throw any exceptions.
	 * Verifies that the default constructor is safe to call.
	 */
	@Test
	void testConstructor_DoesNotThrowException() {
		// Arrange & Act & Assert
		assertDoesNotThrow(() -> {
			new SDCUser();
		}, "Constructor should not throw any exception");
	}

	/**
	 * Test that constructed instance has proper class type.
	 */
	@Test
	void testConstructor_CreatesCorrectType() {
		// Arrange & Act
		SDCUser user = new SDCUser();

		// Assert
		assertNotNull(user, "User should not be null");
		assertEquals(SDCUser.class, user.getClass(),
				"Instance should be of type SDCUser");
	}

	/**
	 * Test getUsername returns null for a newly constructed instance.
	 */
	@Test
	void testGetUsername_InitiallyNull() {
		// Arrange
		SDCUser user = new SDCUser();

		// Act
		String username = user.getUsername();

		// Assert
		assertNull(username, "Username should be null for a newly constructed instance");
	}

	/**
	 * Test setUsername and getUsername with a non-null value.
	 * Verifies that a username can be stored and retrieved.
	 */
	@Test
	void testSetUsername_WithNonNullValue() {
		// Arrange
		SDCUser user = new SDCUser();
		String expectedUsername = "testuser";

		// Act
		user.setUsername(expectedUsername);
		String actualUsername = user.getUsername();

		// Assert
		assertEquals(expectedUsername, actualUsername, "Username should match the set value");
	}

	/**
	 * Test setUsername and getUsername with null.
	 * Verifies that null can be explicitly set as username.
	 */
	@Test
	void testSetUsername_WithNull() {
		// Arrange
		SDCUser user = new SDCUser();
		user.setUsername("initialUser");

		// Act
		user.setUsername(null);
		String username = user.getUsername();

		// Assert
		assertNull(username, "Username should be null after setting to null");
	}

	/**
	 * Test setUsername with an empty string.
	 * Verifies that an empty string is a valid username.
	 */
	@Test
	void testSetUsername_WithEmptyString() {
		// Arrange
		SDCUser user = new SDCUser();

		// Act
		user.setUsername("");
		String username = user.getUsername();

		// Assert
		assertEquals("", username, "Username should be empty string");
		assertNotNull(username, "Username should not be null");
	}

	/**
	 * Test setUsername replaces the previous value.
	 * Verifies that calling setUsername multiple times updates the stored value.
	 */
	@Test
	void testSetUsername_ReplacesExistingValue() {
		// Arrange
		SDCUser user = new SDCUser();

		// Act
		user.setUsername("firstUser");
		String afterFirst = user.getUsername();

		user.setUsername("secondUser");
		String afterSecond = user.getUsername();

		// Assert
		assertEquals("firstUser", afterFirst, "First set should return first username");
		assertEquals("secondUser", afterSecond, "Second set should return second username");
	}

	/**
	 * Test setUsername with special characters and whitespace.
	 * Verifies that various string values are handled correctly.
	 */
	@Test
	void testSetUsername_WithSpecialCharacters() {
		// Arrange
		SDCUser user = new SDCUser();
		String specialUsername = "user@example.com!#$%&*()";

		// Act
		user.setUsername(specialUsername);
		String username = user.getUsername();

		// Assert
		assertEquals(specialUsername, username, "Username should handle special characters");
	}

	/**
	 * Test setUsername with whitespace.
	 */
	@Test
	void testSetUsername_WithWhitespace() {
		// Arrange
		SDCUser user = new SDCUser();
		String whitespaceUsername = "  user with spaces  ";

		// Act
		user.setUsername(whitespaceUsername);
		String username = user.getUsername();

		// Assert
		assertEquals(whitespaceUsername, username, "Username should preserve whitespace");
	}

	/**
	 * Test getPassword returns null for a newly constructed instance.
	 */
	@Test
	void testGetPassword_InitiallyNull() {
		// Arrange
		SDCUser user = new SDCUser();

		// Act
		String password = user.getPassword();

		// Assert
		assertNull(password, "Password should be null for a newly constructed instance");
	}

	/**
	 * Test setPassword and getPassword with a non-null value.
	 * Verifies that a password can be stored and retrieved.
	 */
	@Test
	void testSetPassword_WithNonNullValue() {
		// Arrange
		SDCUser user = new SDCUser();
		String expectedPassword = "securePassword123";

		// Act
		user.setPassword(expectedPassword);
		String actualPassword = user.getPassword();

		// Assert
		assertEquals(expectedPassword, actualPassword, "Password should match the set value");
	}

	/**
	 * Test setPassword and getPassword with null.
	 * Verifies that null can be explicitly set as password.
	 */
	@Test
	void testSetPassword_WithNull() {
		// Arrange
		SDCUser user = new SDCUser();
		user.setPassword("initialPassword");

		// Act
		user.setPassword(null);
		String password = user.getPassword();

		// Assert
		assertNull(password, "Password should be null after setting to null");
	}

	/**
	 * Test setPassword with an empty string.
	 * Verifies that an empty string is a valid password.
	 */
	@Test
	void testSetPassword_WithEmptyString() {
		// Arrange
		SDCUser user = new SDCUser();

		// Act
		user.setPassword("");
		String password = user.getPassword();

		// Assert
		assertEquals("", password, "Password should be empty string");
		assertNotNull(password, "Password should not be null");
	}

	/**
	 * Test setPassword replaces the previous value.
	 * Verifies that calling setPassword multiple times updates the stored value.
	 */
	@Test
	void testSetPassword_ReplacesExistingValue() {
		// Arrange
		SDCUser user = new SDCUser();

		// Act
		user.setPassword("firstPassword");
		String afterFirst = user.getPassword();

		user.setPassword("secondPassword");
		String afterSecond = user.getPassword();

		// Assert
		assertEquals("firstPassword", afterFirst, "First set should return first password");
		assertEquals("secondPassword", afterSecond, "Second set should return second password");
	}

	/**
	 * Test setPassword with special characters.
	 * Verifies that complex passwords are handled correctly.
	 */
	@Test
	void testSetPassword_WithSpecialCharacters() {
		// Arrange
		SDCUser user = new SDCUser();
		String complexPassword = "P@ssw0rd!#$%^&*(){}[]|\\:;\"'<>,.?/~`";

		// Act
		user.setPassword(complexPassword);
		String password = user.getPassword();

		// Assert
		assertEquals(complexPassword, password, "Password should handle special characters");
	}

	/**
	 * Test setPassword with long string.
	 * Verifies that long passwords are stored correctly.
	 */
	@Test
	void testSetPassword_WithLongString() {
		// Arrange
		SDCUser user = new SDCUser();
		String longPassword = "a".repeat(1000);

		// Act
		user.setPassword(longPassword);
		String password = user.getPassword();

		// Assert
		assertEquals(longPassword, password, "Password should handle long strings");
		assertEquals(1000, password.length(), "Password length should be preserved");
	}

	/**
	 * Test getRole returns null for a newly constructed instance.
	 */
	@Test
	void testGetRole_InitiallyNull() {
		// Arrange
		SDCUser user = new SDCUser();

		// Act
		String role = user.getRole();

		// Assert
		assertNull(role, "Role should be null for a newly constructed instance");
	}

	/**
	 * Test setRole and getRole with a non-null value.
	 * Verifies that a role can be stored and retrieved.
	 */
	@Test
	void testSetRole_WithNonNullValue() {
		// Arrange
		SDCUser user = new SDCUser();
		String expectedRole = "admin";

		// Act
		user.setRole(expectedRole);
		String actualRole = user.getRole();

		// Assert
		assertEquals(expectedRole, actualRole, "Role should match the set value");
	}

	/**
	 * Test setRole and getRole with null.
	 * Verifies that null can be explicitly set as role.
	 */
	@Test
	void testSetRole_WithNull() {
		// Arrange
		SDCUser user = new SDCUser();
		user.setRole("initialRole");

		// Act
		user.setRole(null);
		String role = user.getRole();

		// Assert
		assertNull(role, "Role should be null after setting to null");
	}

	/**
	 * Test setRole with an empty string.
	 * Verifies that an empty string is a valid role.
	 */
	@Test
	void testSetRole_WithEmptyString() {
		// Arrange
		SDCUser user = new SDCUser();

		// Act
		user.setRole("");
		String role = user.getRole();

		// Assert
		assertEquals("", role, "Role should be empty string");
		assertNotNull(role, "Role should not be null");
	}

	/**
	 * Test setRole replaces the previous value.
	 * Verifies that calling setRole multiple times updates the stored value.
	 */
	@Test
	void testSetRole_ReplacesExistingValue() {
		// Arrange
		SDCUser user = new SDCUser();

		// Act
		user.setRole("user");
		String afterFirst = user.getRole();

		user.setRole("admin");
		String afterSecond = user.getRole();

		// Assert
		assertEquals("user", afterFirst, "First set should return first role");
		assertEquals("admin", afterSecond, "Second set should return second role");
	}

	/**
	 * Test setRole with various role values.
	 * Verifies that different role names are handled correctly.
	 */
	@Test
	void testSetRole_WithVariousRoleValues() {
		// Arrange
		SDCUser user = new SDCUser();
		String[] roles = {"admin", "user", "viewer", "editor", "moderator", "ADMIN", "Admin"};

		// Act & Assert
		for (String role : roles) {
			user.setRole(role);
			assertEquals(role, user.getRole(), "Role should match the set value: " + role);
		}
	}

	/**
	 * Test that multiple instances maintain independent state.
	 * Verifies that setting properties on one instance doesn't affect another.
	 */
	@Test
	void testSetters_IndependentInstances() {
		// Arrange
		SDCUser user1 = new SDCUser();
		SDCUser user2 = new SDCUser();

		// Act
		user1.setUsername("user1");
		user1.setPassword("pass1");
		user1.setRole("admin");

		user2.setUsername("user2");
		user2.setPassword("pass2");
		user2.setRole("user");

		// Assert
		assertEquals("user1", user1.getUsername(), "First user should have user1");
		assertEquals("pass1", user1.getPassword(), "First user should have pass1");
		assertEquals("admin", user1.getRole(), "First user should have admin role");

		assertEquals("user2", user2.getUsername(), "Second user should have user2");
		assertEquals("pass2", user2.getPassword(), "Second user should have pass2");
		assertEquals("user", user2.getRole(), "Second user should have user role");
	}

	/**
	 * Test setting all properties on a single instance.
	 * Verifies that all properties can be set and retrieved independently.
	 */
	@Test
	void testSetters_AllPropertiesTogether() {
		// Arrange
		SDCUser user = new SDCUser();
		String username = "testUser";
		String password = "testPassword";
		String role = "testRole";

		// Act
		user.setUsername(username);
		user.setPassword(password);
		user.setRole(role);

		// Assert
		assertEquals(username, user.getUsername(), "Username should be set correctly");
		assertEquals(password, user.getPassword(), "Password should be set correctly");
		assertEquals(role, user.getRole(), "Role should be set correctly");
	}

	/**
	 * Test that setting one property doesn't affect others.
	 * Verifies field independence.
	 */
	@Test
	void testSetters_FieldIndependence() {
		// Arrange
		SDCUser user = new SDCUser();
		user.setUsername("user");
		user.setPassword("pass");
		user.setRole("role");

		// Act - change only username
		user.setUsername("newUser");

		// Assert
		assertEquals("newUser", user.getUsername(), "Username should be updated");
		assertEquals("pass", user.getPassword(), "Password should remain unchanged");
		assertEquals("role", user.getRole(), "Role should remain unchanged");

		// Act - change only password
		user.setPassword("newPass");

		// Assert
		assertEquals("newUser", user.getUsername(), "Username should remain unchanged");
		assertEquals("newPass", user.getPassword(), "Password should be updated");
		assertEquals("role", user.getRole(), "Role should remain unchanged");

		// Act - change only role
		user.setRole("newRole");

		// Assert
		assertEquals("newUser", user.getUsername(), "Username should remain unchanged");
		assertEquals("newPass", user.getPassword(), "Password should remain unchanged");
		assertEquals("newRole", user.getRole(), "Role should be updated");
	}

	/**
	 * Test setting properties to null independently.
	 * Verifies that each property can be nullified without affecting others.
	 */
	@Test
	void testSetters_NullifyIndependently() {
		// Arrange
		SDCUser user = new SDCUser();
		user.setUsername("user");
		user.setPassword("pass");
		user.setRole("role");

		// Act - nullify username
		user.setUsername(null);

		// Assert
		assertNull(user.getUsername(), "Username should be null");
		assertEquals("pass", user.getPassword(), "Password should remain unchanged");
		assertEquals("role", user.getRole(), "Role should remain unchanged");

		// Act - nullify password
		user.setPassword(null);

		// Assert
		assertNull(user.getUsername(), "Username should remain null");
		assertNull(user.getPassword(), "Password should be null");
		assertEquals("role", user.getRole(), "Role should remain unchanged");

		// Act - nullify role
		user.setRole(null);

		// Assert
		assertNull(user.getUsername(), "Username should remain null");
		assertNull(user.getPassword(), "Password should remain null");
		assertNull(user.getRole(), "Role should be null");
	}

	/**
	 * Test getters return the same reference that was set.
	 * Verifies that setters store the reference, not a copy.
	 */
	@Test
	void testGetters_ReturnSameReference() {
		// Arrange
		SDCUser user = new SDCUser();
		String username = "testUser";
		String password = "testPassword";
		String role = "testRole";

		// Act
		user.setUsername(username);
		user.setPassword(password);
		user.setRole(role);

		// Assert
		assertSame(username, user.getUsername(), "Username should be the same reference");
		assertSame(password, user.getPassword(), "Password should be the same reference");
		assertSame(role, user.getRole(), "Role should be the same reference");
	}

	/**
	 * Test multiple calls to getters return the same value.
	 * Verifies that getters are idempotent and don't modify state.
	 */
	@Test
	void testGetters_MultipleCallsReturnSameValue() {
		// Arrange
		SDCUser user = new SDCUser();
		user.setUsername("testUser");
		user.setPassword("testPassword");
		user.setRole("testRole");

		// Act
		String username1 = user.getUsername();
		String username2 = user.getUsername();
		String password1 = user.getPassword();
		String password2 = user.getPassword();
		String role1 = user.getRole();
		String role2 = user.getRole();

		// Assert
		assertSame(username1, username2, "Multiple calls to getUsername should return same reference");
		assertSame(password1, password2, "Multiple calls to getPassword should return same reference");
		assertSame(role1, role2, "Multiple calls to getRole should return same reference");
	}

	/**
	 * Test that getters don't throw exceptions even when fields are null.
	 * Verifies safe getter behavior.
	 */
	@Test
	void testGetters_DoNotThrowExceptionWhenNull() {
		// Arrange
		SDCUser user = new SDCUser();

		// Act & Assert
		assertDoesNotThrow(() -> user.getUsername(), "getUsername should not throw when null");
		assertDoesNotThrow(() -> user.getPassword(), "getPassword should not throw when null");
		assertDoesNotThrow(() -> user.getRole(), "getRole should not throw when null");
	}

	/**
	 * Test setters with the same value multiple times.
	 * Verifies idempotent setter behavior.
	 */
	@Test
	void testSetters_SameValueMultipleTimes() {
		// Arrange
		SDCUser user = new SDCUser();
		String username = "testUser";
		String password = "testPassword";
		String role = "testRole";

		// Act
		user.setUsername(username);
		user.setUsername(username);
		user.setPassword(password);
		user.setPassword(password);
		user.setRole(role);
		user.setRole(role);

		// Assert
		assertEquals(username, user.getUsername(), "Username should be set correctly");
		assertEquals(password, user.getPassword(), "Password should be set correctly");
		assertEquals(role, user.getRole(), "Role should be set correctly");
	}
}
