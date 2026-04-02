package net.finmath.smartcontract.valuation.service.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SDCUserTest {

	@Test
	void testGetSetUsername() {
		SDCUser user = new SDCUser();
		user.setUsername("testuser");

		Assertions.assertEquals("testuser", user.getUsername());
	}

	@Test
	void testGetSetPassword() {
		SDCUser user = new SDCUser();
		user.setPassword("secret");

		Assertions.assertEquals("secret", user.getPassword());
	}

	@Test
	void testGetSetRole() {
		SDCUser user = new SDCUser();
		user.setRole("ADMIN");

		Assertions.assertEquals("ADMIN", user.getRole());
	}

	@Test
	void testDefaultValuesAreNull() {
		SDCUser user = new SDCUser();

		Assertions.assertNull(user.getUsername());
		Assertions.assertNull(user.getPassword());
		Assertions.assertNull(user.getRole());
	}
}
