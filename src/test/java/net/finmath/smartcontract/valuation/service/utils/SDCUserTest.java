package net.finmath.smartcontract.valuation.service.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Tests for SDCUser
 */
class SDCUserTest {

	@Test
	void testUsernameGetterAndSetter() {
		final SDCUser user = new SDCUser();

		assertNull(user.getUsername());

		final String username = "testUser";
		user.setUsername(username);

		assertEquals(username, user.getUsername());
	}

	@Test
	void testPasswordGetterAndSetter() {
		final SDCUser user = new SDCUser();

		assertNull(user.getPassword());

		final String password = "testPassword";
		user.setPassword(password);

		assertEquals(password, user.getPassword());
	}

	@Test
	void testRoleGetterAndSetter() {
		final SDCUser user = new SDCUser();

		assertNull(user.getRole());

		final String role = "admin";
		user.setRole(role);

		assertEquals(role, user.getRole());
	}
}
