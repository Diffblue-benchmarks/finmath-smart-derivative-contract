package net.finmath.smartcontract.valuation.service.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link RefinitivConfig}.
 * Tests all getter/setter pairs as plain POJO without Spring context.
 */
class RefinitivConfigTest {

	private RefinitivConfig config;

	@BeforeEach
	void setUp() {
		config = new RefinitivConfig();
	}

	@Test
	void testUser() {
		assertNull(config.getUser());
		config.setUser("testUser");
		assertEquals("testUser", config.getUser());
	}

	@Test
	void testPassword() {
		assertNull(config.getPassword());
		config.setPassword("secret123");
		assertEquals("secret123", config.getPassword());
	}

	@Test
	void testClientId() {
		assertNull(config.getClientId());
		config.setClientId("client-abc-123");
		assertEquals("client-abc-123", config.getClientId());
	}

	@Test
	void testHostName() {
		assertNull(config.getHostName());
		config.setHostName("api.refinitiv.com");
		assertEquals("api.refinitiv.com", config.getHostName());
	}

	@Test
	void testPort() {
		assertEquals(0, config.getPort());
		config.setPort(14002);
		assertEquals(14002, config.getPort());
	}

	@Test
	void testAuthUrl() {
		assertNull(config.getAuthUrl());
		config.setAuthUrl("https://auth.refinitiv.com/token");
		assertEquals("https://auth.refinitiv.com/token", config.getAuthUrl());
	}

	@Test
	void testUseProxy() {
		assertNull(config.getUseProxy());
		config.setUseProxy("true");
		assertEquals("true", config.getUseProxy());
	}

	@Test
	void testProxyHost() {
		assertNull(config.getProxyHost());
		config.setProxyHost("proxy.internal.net");
		assertEquals("proxy.internal.net", config.getProxyHost());
	}

	@Test
	void testProxyPort() {
		assertEquals(0, config.getProxyPort());
		config.setProxyPort(8080);
		assertEquals(8080, config.getProxyPort());
	}

	@Test
	void testProxyUser() {
		assertNull(config.getProxyUser());
		config.setProxyUser("proxyAdmin");
		assertEquals("proxyAdmin", config.getProxyUser());
	}

	@Test
	void testProxyPassword() {
		assertNull(config.getProxyPassword());
		config.setProxyPassword("proxyPass!");
		assertEquals("proxyPass!", config.getProxyPassword());
	}

	@Test
	void testSetAllFieldsAndVerify() {
		config.setUser("user1");
		config.setPassword("pass1");
		config.setClientId("cid1");
		config.setHostName("host1");
		config.setPort(9999);
		config.setAuthUrl("https://auth.example.com");
		config.setUseProxy("false");
		config.setProxyHost("proxy1");
		config.setProxyPort(3128);
		config.setProxyUser("pu1");
		config.setProxyPassword("pp1");

		assertAll(
				() -> assertEquals("user1", config.getUser()),
				() -> assertEquals("pass1", config.getPassword()),
				() -> assertEquals("cid1", config.getClientId()),
				() -> assertEquals("host1", config.getHostName()),
				() -> assertEquals(9999, config.getPort()),
				() -> assertEquals("https://auth.example.com", config.getAuthUrl()),
				() -> assertEquals("false", config.getUseProxy()),
				() -> assertEquals("proxy1", config.getProxyHost()),
				() -> assertEquals(3128, config.getProxyPort()),
				() -> assertEquals("pu1", config.getProxyUser()),
				() -> assertEquals("pp1", config.getProxyPassword())
		);
	}
}
