package net.finmath.smartcontract.valuation.service.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class SDCUserTest {

	@Test
	void testGetAndSetUsername() {
		SDCUser user = new SDCUser();
		assertNull(user.getUsername());
		user.setUsername("testUser");
		assertEquals("testUser", user.getUsername());
	}

	@Test
	void testGetAndSetPassword() {
		SDCUser user = new SDCUser();
		assertNull(user.getPassword());
		user.setPassword("secret123");
		assertEquals("secret123", user.getPassword());
	}

	@Test
	void testGetAndSetRole() {
		SDCUser user = new SDCUser();
		assertNull(user.getRole());
		user.setRole("ADMIN");
		assertEquals("ADMIN", user.getRole());
	}
}
