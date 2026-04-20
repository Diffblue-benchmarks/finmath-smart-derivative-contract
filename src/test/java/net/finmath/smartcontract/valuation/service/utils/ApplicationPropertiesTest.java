package net.finmath.smartcontract.valuation.service.utils;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationPropertiesTest {

	@Test
	void testGetUsersReturnsNullByDefault() {
		ApplicationProperties properties = new ApplicationProperties();

		assertNull(properties.getUsers());
	}

	@Test
	void testSetAndGetUsers() {
		ApplicationProperties properties = new ApplicationProperties();

		SDCUser user = new SDCUser();
		user.setUsername("testUser");
		user.setPassword("testPass");
		user.setRole("admin");

		List<SDCUser> users = new ArrayList<>();
		users.add(user);

		properties.setUsers(users);

		List<SDCUser> result = properties.getUsers();
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals("testUser", result.get(0).getUsername());
	}
}
