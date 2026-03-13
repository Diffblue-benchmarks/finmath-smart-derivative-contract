package net.finmath.smartcontract.valuation.service.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SDCUserTest {

	@Test
	void settersAndGetters_shouldWorkCorrectly() {
		SDCUser user = new SDCUser();
		user.setUsername("admin");
		user.setPassword("secret");
		user.setRole("ADMIN");

		assertEquals("admin", user.getUsername());
		assertEquals("secret", user.getPassword());
		assertEquals("ADMIN", user.getRole());
	}

	@Test
	void defaultConstructor_shouldCreateEmptyUser() {
		SDCUser user = new SDCUser();
		assertNull(user.getUsername());
		assertNull(user.getPassword());
		assertNull(user.getRole());
	}
}
