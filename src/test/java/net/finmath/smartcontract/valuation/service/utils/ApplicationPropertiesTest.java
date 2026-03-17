package net.finmath.smartcontract.valuation.service.utils;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class ApplicationPropertiesTest {

	@Test
	void testGetUsers() {
		final ApplicationProperties properties = new ApplicationProperties();
		final List<SDCUser> users = new ArrayList<>();

		final SDCUser user1 = new SDCUser();
		user1.setUsername("testuser");
		user1.setPassword("testpass");
		user1.setRole("admin");
		users.add(user1);

		properties.setUsers(users);

		final List<SDCUser> retrievedUsers = properties.getUsers();

		assertNotNull(retrievedUsers);
		assertEquals(1, retrievedUsers.size());
		assertSame(users, retrievedUsers);
	}

	@Test
	void testSetUsers() {
		final ApplicationProperties properties = new ApplicationProperties();
		final List<SDCUser> users = new ArrayList<>();

		final SDCUser user1 = new SDCUser();
		user1.setUsername("user1");
		user1.setPassword("pass1");
		user1.setRole("viewer");
		users.add(user1);

		final SDCUser user2 = new SDCUser();
		user2.setUsername("user2");
		user2.setPassword("pass2");
		user2.setRole("admin");
		users.add(user2);

		properties.setUsers(users);

		assertNotNull(properties.getUsers());
		assertEquals(2, properties.getUsers().size());
		assertEquals("user1", properties.getUsers().get(0).getUsername());
		assertEquals("user2", properties.getUsers().get(1).getUsername());
	}

	@Test
	void testGetUsersReturnsNull() {
		final ApplicationProperties properties = new ApplicationProperties();

		final List<SDCUser> retrievedUsers = properties.getUsers();

		assertNull(retrievedUsers);
	}
}
