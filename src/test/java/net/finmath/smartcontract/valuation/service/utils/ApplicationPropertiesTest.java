package net.finmath.smartcontract.valuation.service.utils;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationPropertiesTest {

	@Test
	void setUsers_shouldStoreAndRetrieveUsers() {
		ApplicationProperties props = new ApplicationProperties();
		SDCUser user = new SDCUser();
		user.setUsername("testuser");
		props.setUsers(List.of(user));

		assertEquals(1, props.getUsers().size());
		assertEquals("testuser", props.getUsers().get(0).getUsername());
	}

	@Test
	void getUsers_shouldReturnNull_beforeSet() {
		ApplicationProperties props = new ApplicationProperties();
		assertNull(props.getUsers());
	}
}
