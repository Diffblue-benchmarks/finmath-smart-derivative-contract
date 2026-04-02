package net.finmath.smartcontract.valuation.service.utils;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ApplicationPropertiesTest {

	@Test
	void testGetUsersReturnsNullByDefault() {
		ApplicationProperties properties = new ApplicationProperties();

		assertNull(properties.getUsers());
	}

	@Test
	void testSetAndGetUsers() {
		ApplicationProperties properties = new ApplicationProperties();

		SDCUser user1 = new SDCUser();
		user1.setUsername("alice");
		user1.setPassword("pass1");
		user1.setRole("USER");

		SDCUser user2 = new SDCUser();
		user2.setUsername("bob");
		user2.setPassword("pass2");
		user2.setRole("ADMIN");

		List<SDCUser> users = Arrays.asList(user1, user2);
		properties.setUsers(users);

		List<SDCUser> result = properties.getUsers();

		assertEquals(2, result.size());
		assertEquals("alice", result.get(0).getUsername());
		assertEquals("bob", result.get(1).getUsername());
	}

}
