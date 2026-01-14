/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */

package net.finmath.smartcontract.valuation.service.utils;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for ApplicationProperties.
 * Tests the constructor, getUsers, and setUsers methods with focus on branch and condition coverage.
 *
 * @author Claude Code
 */
class ApplicationPropertiesClaudeTest {

	/**
	 * Test constructor creates a valid instance.
	 */
	@Test
	void testConstructor_CreatesValidInstance() {
		// Arrange & Act
		ApplicationProperties properties = new ApplicationProperties();

		// Assert
		assertNotNull(properties, "Constructor should create a non-null instance");
	}

	/**
	 * Test constructor can be called multiple times to create independent instances.
	 * Verifies that constructor has no side effects and each instance is separate.
	 */
	@Test
	void testConstructor_MultipleInstances_AreIndependent() {
		// Arrange & Act
		ApplicationProperties properties1 = new ApplicationProperties();
		ApplicationProperties properties2 = new ApplicationProperties();
		ApplicationProperties properties3 = new ApplicationProperties();

		// Assert
		assertNotNull(properties1, "First properties should not be null");
		assertNotNull(properties2, "Second properties should not be null");
		assertNotNull(properties3, "Third properties should not be null");

		// Verify they are different instances
		assertNotSame(properties1, properties2, "Properties should be different instances");
		assertNotSame(properties2, properties3, "Properties should be different instances");
		assertNotSame(properties1, properties3, "Properties should be different instances");
	}

	/**
	 * Test that constructor doesn't throw any exceptions.
	 * Verifies that the default constructor is safe to call.
	 */
	@Test
	void testConstructor_DoesNotThrowException() {
		// Arrange & Act & Assert
		assertDoesNotThrow(() -> {
			new ApplicationProperties();
		}, "Constructor should not throw any exception");
	}

	/**
	 * Test that constructed instance has proper class type.
	 */
	@Test
	void testConstructor_CreatesCorrectType() {
		// Arrange & Act
		ApplicationProperties properties = new ApplicationProperties();

		// Assert
		assertNotNull(properties, "Properties should not be null");
		assertEquals(ApplicationProperties.class, properties.getClass(),
				"Instance should be of type ApplicationProperties");
	}

	/**
	 * Test that constructed properties has expected Spring annotations.
	 * Verifies that the class is properly annotated as a Component and ConfigurationProperties.
	 */
	@Test
	void testConstructor_ClassHasComponentAnnotation() {
		// Arrange & Act
		ApplicationProperties properties = new ApplicationProperties();

		// Assert
		assertNotNull(properties, "Properties should not be null");
		assertTrue(properties.getClass().isAnnotationPresent(org.springframework.stereotype.Component.class),
				"ApplicationProperties class should have @Component annotation");
		assertTrue(properties.getClass().isAnnotationPresent(org.springframework.boot.context.properties.ConfigurationProperties.class),
				"ApplicationProperties class should have @ConfigurationProperties annotation");
	}

	/**
	 * Test getUsers returns null for a newly constructed instance.
	 * The users field is not initialized by default.
	 */
	@Test
	void testGetUsers_InitiallyNull() {
		// Arrange
		ApplicationProperties properties = new ApplicationProperties();

		// Act
		List<SDCUser> users = properties.getUsers();

		// Assert
		assertNull(users, "Users should be null for a newly constructed instance");
	}

	/**
	 * Test setUsers and getUsers with a null value.
	 * Verifies that null can be explicitly set.
	 */
	@Test
	void testSetUsers_WithNull() {
		// Arrange
		ApplicationProperties properties = new ApplicationProperties();

		// Act
		properties.setUsers(null);
		List<SDCUser> users = properties.getUsers();

		// Assert
		assertNull(users, "Users should be null after setting to null");
	}

	/**
	 * Test setUsers and getUsers with an empty list.
	 * Verifies that an empty list can be stored and retrieved.
	 */
	@Test
	void testSetUsers_WithEmptyList() {
		// Arrange
		ApplicationProperties properties = new ApplicationProperties();
		List<SDCUser> emptyList = new ArrayList<>();

		// Act
		properties.setUsers(emptyList);
		List<SDCUser> users = properties.getUsers();

		// Assert
		assertNotNull(users, "Users should not be null after setting an empty list");
		assertTrue(users.isEmpty(), "Users list should be empty");
		assertSame(emptyList, users, "Should return the same list instance that was set");
	}

	/**
	 * Test setUsers and getUsers with a single user.
	 * Verifies that a list with one user can be stored and retrieved.
	 */
	@Test
	void testSetUsers_WithSingleUser() {
		// Arrange
		ApplicationProperties properties = new ApplicationProperties();
		SDCUser user = createUser("testuser", "testpass", "admin");
		List<SDCUser> userList = new ArrayList<>();
		userList.add(user);

		// Act
		properties.setUsers(userList);
		List<SDCUser> retrievedUsers = properties.getUsers();

		// Assert
		assertNotNull(retrievedUsers, "Users should not be null");
		assertEquals(1, retrievedUsers.size(), "Users list should have one element");
		assertSame(userList, retrievedUsers, "Should return the same list instance that was set");
		assertEquals("testuser", retrievedUsers.get(0).getUsername(), "Username should match");
		assertEquals("testpass", retrievedUsers.get(0).getPassword(), "Password should match");
		assertEquals("admin", retrievedUsers.get(0).getRole(), "Role should match");
	}

	/**
	 * Test setUsers and getUsers with multiple users.
	 * Verifies that a list with multiple users can be stored and retrieved.
	 */
	@Test
	void testSetUsers_WithMultipleUsers() {
		// Arrange
		ApplicationProperties properties = new ApplicationProperties();
		List<SDCUser> userList = Arrays.asList(
				createUser("user1", "pass1", "admin"),
				createUser("user2", "pass2", "user"),
				createUser("user3", "pass3", "viewer")
		);

		// Act
		properties.setUsers(userList);
		List<SDCUser> retrievedUsers = properties.getUsers();

		// Assert
		assertNotNull(retrievedUsers, "Users should not be null");
		assertEquals(3, retrievedUsers.size(), "Users list should have three elements");
		assertEquals("user1", retrievedUsers.get(0).getUsername(), "First user username should match");
		assertEquals("user2", retrievedUsers.get(1).getUsername(), "Second user username should match");
		assertEquals("user3", retrievedUsers.get(2).getUsername(), "Third user username should match");
	}

	/**
	 * Test that setUsers replaces the previous value.
	 * Verifies that calling setUsers multiple times updates the stored list.
	 */
	@Test
	void testSetUsers_ReplacesExistingValue() {
		// Arrange
		ApplicationProperties properties = new ApplicationProperties();
		List<SDCUser> firstList = Arrays.asList(createUser("user1", "pass1", "admin"));
		List<SDCUser> secondList = Arrays.asList(
				createUser("user2", "pass2", "user"),
				createUser("user3", "pass3", "viewer")
		);

		// Act
		properties.setUsers(firstList);
		List<SDCUser> afterFirst = properties.getUsers();

		properties.setUsers(secondList);
		List<SDCUser> afterSecond = properties.getUsers();

		// Assert
		assertSame(firstList, afterFirst, "First set should return first list");
		assertEquals(1, afterFirst.size(), "First list should have one element");

		assertSame(secondList, afterSecond, "Second set should return second list");
		assertEquals(2, afterSecond.size(), "Second list should have two elements");
		assertEquals("user2", afterSecond.get(0).getUsername(), "Username should be from second list");
	}

	/**
	 * Test that modifications to the list after setting it are reflected.
	 * This verifies that the property stores the reference, not a copy.
	 */
	@Test
	void testSetUsers_StoresReference() {
		// Arrange
		ApplicationProperties properties = new ApplicationProperties();
		List<SDCUser> userList = new ArrayList<>();
		userList.add(createUser("user1", "pass1", "admin"));

		// Act
		properties.setUsers(userList);
		userList.add(createUser("user2", "pass2", "user"));
		List<SDCUser> retrievedUsers = properties.getUsers();

		// Assert
		assertNotNull(retrievedUsers, "Users should not be null");
		assertEquals(2, retrievedUsers.size(), "Users list should reflect modifications to original list");
		assertEquals("user1", retrievedUsers.get(0).getUsername(), "First user should be user1");
		assertEquals("user2", retrievedUsers.get(1).getUsername(), "Second user should be user2");
	}

	/**
	 * Test setting users back to null after setting a list.
	 * Verifies that users can be cleared by setting to null.
	 */
	@Test
	void testSetUsers_CanSetToNullAfterSettingList() {
		// Arrange
		ApplicationProperties properties = new ApplicationProperties();
		List<SDCUser> userList = Arrays.asList(createUser("user1", "pass1", "admin"));

		// Act
		properties.setUsers(userList);
		assertNotNull(properties.getUsers(), "Users should be set");

		properties.setUsers(null);
		List<SDCUser> users = properties.getUsers();

		// Assert
		assertNull(users, "Users should be null after setting to null");
	}

	/**
	 * Test multiple instances maintain independent state.
	 * Verifies that setting users on one instance doesn't affect another.
	 */
	@Test
	void testSetUsers_IndependentInstances() {
		// Arrange
		ApplicationProperties properties1 = new ApplicationProperties();
		ApplicationProperties properties2 = new ApplicationProperties();
		List<SDCUser> users1 = Arrays.asList(createUser("user1", "pass1", "admin"));
		List<SDCUser> users2 = Arrays.asList(createUser("user2", "pass2", "user"));

		// Act
		properties1.setUsers(users1);
		properties2.setUsers(users2);

		// Assert
		assertNotSame(properties1.getUsers(), properties2.getUsers(),
				"Different instances should have independent user lists");
		assertEquals(1, properties1.getUsers().size(), "First instance should have its own users");
		assertEquals(1, properties2.getUsers().size(), "Second instance should have its own users");
		assertEquals("user1", properties1.getUsers().get(0).getUsername(),
				"First instance should have user1");
		assertEquals("user2", properties2.getUsers().get(0).getUsername(),
				"Second instance should have user2");
	}

	/**
	 * Test setUsers with users containing various field values.
	 * Verifies that different user configurations are handled correctly.
	 */
	@Test
	void testSetUsers_WithVariousUserConfigurations() {
		// Arrange
		ApplicationProperties properties = new ApplicationProperties();
		SDCUser userWithAllFields = createUser("user1", "pass1", "admin");
		SDCUser userWithNullFields = new SDCUser(); // All fields null
		SDCUser userWithEmptyStrings = createUser("", "", "");

		List<SDCUser> userList = Arrays.asList(
				userWithAllFields,
				userWithNullFields,
				userWithEmptyStrings
		);

		// Act
		properties.setUsers(userList);
		List<SDCUser> retrievedUsers = properties.getUsers();

		// Assert
		assertNotNull(retrievedUsers, "Users should not be null");
		assertEquals(3, retrievedUsers.size(), "Users list should have three elements");

		assertEquals("user1", retrievedUsers.get(0).getUsername(), "First user should have username");
		assertNull(retrievedUsers.get(1).getUsername(), "Second user username should be null");
		assertEquals("", retrievedUsers.get(2).getUsername(), "Third user username should be empty string");
	}

	/**
	 * Test that getUsers doesn't create a defensive copy.
	 * Verifies the behavior of the getter.
	 */
	@Test
	void testGetUsers_ReturnsSameInstance() {
		// Arrange
		ApplicationProperties properties = new ApplicationProperties();
		List<SDCUser> userList = new ArrayList<>();
		userList.add(createUser("user1", "pass1", "admin"));
		properties.setUsers(userList);

		// Act
		List<SDCUser> retrieved1 = properties.getUsers();
		List<SDCUser> retrieved2 = properties.getUsers();

		// Assert
		assertSame(retrieved1, retrieved2, "Multiple calls to getUsers should return the same instance");
		assertSame(userList, retrieved1, "getUsers should return the same instance that was set");
	}

	/**
	 * Helper method to create a SDCUser with specified values.
	 */
	private SDCUser createUser(String username, String password, String role) {
		SDCUser user = new SDCUser();
		user.setUsername(username);
		user.setPassword(password);
		user.setRole(role);
		return user;
	}
}
