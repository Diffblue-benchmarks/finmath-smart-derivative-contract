/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */

package net.finmath.smartcontract.valuation.service.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for RefinitivConfig.
 * Tests all methods with focus on branch and condition coverage.
 *
 * @author Claude Code
 */
class RefinitivConfigClaudeTest {

	private RefinitivConfig config;

	@BeforeEach
	void setUp() {
		config = new RefinitivConfig();
	}

	/**
	 * Test the default constructor of RefinitivConfig.
	 * The constructor should create a valid instance with all fields initialized to default values.
	 */
	@Test
	void testConstructor() {
		RefinitivConfig newConfig = new RefinitivConfig();
		assertNotNull(newConfig, "Configuration instance should not be null");

		// Verify all String fields are null by default
		assertNull(newConfig.getUser(), "User should be null by default");
		assertNull(newConfig.getPassword(), "Password should be null by default");
		assertNull(newConfig.getClientId(), "ClientId should be null by default");
		assertNull(newConfig.getHostName(), "HostName should be null by default");
		assertNull(newConfig.getAuthUrl(), "AuthUrl should be null by default");
		assertNull(newConfig.getUseProxy(), "UseProxy should be null by default");
		assertNull(newConfig.getProxyHost(), "ProxyHost should be null by default");
		assertNull(newConfig.getProxyUser(), "ProxyUser should be null by default");
		assertNull(newConfig.getProxyPassword(), "ProxyPassword should be null by default");

		// Verify int fields are 0 by default
		assertEquals(0, newConfig.getPort(), "Port should be 0 by default");
		assertEquals(0, newConfig.getProxyPort(), "ProxyPort should be 0 by default");
	}

	/**
	 * Test getUser and setUser methods.
	 * Verifies that:
	 * - Setting a user value stores it correctly
	 * - Getting returns the set value
	 * - Setting null is allowed
	 * - Setting empty string is allowed
	 */
	@Test
	void testUserGetterSetter() {
		// Test setting a normal value
		String testUser = "testUser";
		config.setUser(testUser);
		assertEquals(testUser, config.getUser(), "User should match the set value");

		// Test setting null
		config.setUser(null);
		assertNull(config.getUser(), "User should be null after setting to null");

		// Test setting empty string
		config.setUser("");
		assertEquals("", config.getUser(), "User should be empty string");

		// Test setting a different value
		String anotherUser = "anotherUser";
		config.setUser(anotherUser);
		assertEquals(anotherUser, config.getUser(), "User should match the new value");
	}

	/**
	 * Test getPassword and setPassword methods.
	 * Verifies that:
	 * - Setting a password value stores it correctly
	 * - Getting returns the set value
	 * - Setting null is allowed
	 * - Setting empty string is allowed
	 */
	@Test
	void testPasswordGetterSetter() {
		// Test setting a normal value
		String testPassword = "testPassword123";
		config.setPassword(testPassword);
		assertEquals(testPassword, config.getPassword(), "Password should match the set value");

		// Test setting null
		config.setPassword(null);
		assertNull(config.getPassword(), "Password should be null after setting to null");

		// Test setting empty string
		config.setPassword("");
		assertEquals("", config.getPassword(), "Password should be empty string");

		// Test setting a different value
		String anotherPassword = "anotherPassword456";
		config.setPassword(anotherPassword);
		assertEquals(anotherPassword, config.getPassword(), "Password should match the new value");
	}

	/**
	 * Test getClientId and setClientId methods.
	 * Verifies that:
	 * - Setting a clientId value stores it correctly
	 * - Getting returns the set value
	 * - Setting null is allowed
	 * - Setting empty string is allowed
	 */
	@Test
	void testClientIdGetterSetter() {
		// Test setting a normal value
		String testClientId = "client-12345";
		config.setClientId(testClientId);
		assertEquals(testClientId, config.getClientId(), "ClientId should match the set value");

		// Test setting null
		config.setClientId(null);
		assertNull(config.getClientId(), "ClientId should be null after setting to null");

		// Test setting empty string
		config.setClientId("");
		assertEquals("", config.getClientId(), "ClientId should be empty string");

		// Test setting a different value
		String anotherClientId = "client-67890";
		config.setClientId(anotherClientId);
		assertEquals(anotherClientId, config.getClientId(), "ClientId should match the new value");
	}

	/**
	 * Test getHostName and setHostName methods.
	 * Verifies that:
	 * - Setting a hostName value stores it correctly
	 * - Getting returns the set value
	 * - Setting null is allowed
	 * - Setting empty string is allowed
	 */
	@Test
	void testHostNameGetterSetter() {
		// Test setting a normal value
		String testHostName = "api.refinitiv.com";
		config.setHostName(testHostName);
		assertEquals(testHostName, config.getHostName(), "HostName should match the set value");

		// Test setting null
		config.setHostName(null);
		assertNull(config.getHostName(), "HostName should be null after setting to null");

		// Test setting empty string
		config.setHostName("");
		assertEquals("", config.getHostName(), "HostName should be empty string");

		// Test setting a different value
		String anotherHostName = "localhost";
		config.setHostName(anotherHostName);
		assertEquals(anotherHostName, config.getHostName(), "HostName should match the new value");
	}

	/**
	 * Test getPort and setPort methods.
	 * Verifies that:
	 * - Setting a port value stores it correctly
	 * - Getting returns the set value
	 * - Setting positive, negative, and zero values all work
	 */
	@Test
	void testPortGetterSetter() {
		// Test setting a typical port value
		int testPort = 8080;
		config.setPort(testPort);
		assertEquals(testPort, config.getPort(), "Port should match the set value");

		// Test setting zero
		config.setPort(0);
		assertEquals(0, config.getPort(), "Port should be 0");

		// Test setting a different value
		int anotherPort = 443;
		config.setPort(anotherPort);
		assertEquals(anotherPort, config.getPort(), "Port should match the new value");

		// Test setting a negative value (edge case)
		int negativePort = -1;
		config.setPort(negativePort);
		assertEquals(negativePort, config.getPort(), "Port should match the negative value");

		// Test setting a large value
		int largePort = 65535;
		config.setPort(largePort);
		assertEquals(largePort, config.getPort(), "Port should match the large value");
	}

	/**
	 * Test getAuthUrl and setAuthUrl methods.
	 * Verifies that:
	 * - Setting an authUrl value stores it correctly
	 * - Getting returns the set value
	 * - Setting null is allowed
	 * - Setting empty string is allowed
	 */
	@Test
	void testAuthUrlGetterSetter() {
		// Test setting a normal value
		String testAuthUrl = "https://auth.refinitiv.com/oauth2/token";
		config.setAuthUrl(testAuthUrl);
		assertEquals(testAuthUrl, config.getAuthUrl(), "AuthUrl should match the set value");

		// Test setting null
		config.setAuthUrl(null);
		assertNull(config.getAuthUrl(), "AuthUrl should be null after setting to null");

		// Test setting empty string
		config.setAuthUrl("");
		assertEquals("", config.getAuthUrl(), "AuthUrl should be empty string");

		// Test setting a different value
		String anotherAuthUrl = "https://example.com/auth";
		config.setAuthUrl(anotherAuthUrl);
		assertEquals(anotherAuthUrl, config.getAuthUrl(), "AuthUrl should match the new value");
	}

	/**
	 * Test getUseProxy and setUseProxy methods.
	 * Verifies that:
	 * - Setting a useProxy value stores it correctly
	 * - Getting returns the set value
	 * - Setting null is allowed
	 * - Setting empty string is allowed
	 * - Various string values work (true, false, yes, no, etc.)
	 */
	@Test
	void testUseProxyGetterSetter() {
		// Test setting "true"
		config.setUseProxy("true");
		assertEquals("true", config.getUseProxy(), "UseProxy should be 'true'");

		// Test setting "false"
		config.setUseProxy("false");
		assertEquals("false", config.getUseProxy(), "UseProxy should be 'false'");

		// Test setting null
		config.setUseProxy(null);
		assertNull(config.getUseProxy(), "UseProxy should be null after setting to null");

		// Test setting empty string
		config.setUseProxy("");
		assertEquals("", config.getUseProxy(), "UseProxy should be empty string");

		// Test setting other values
		config.setUseProxy("yes");
		assertEquals("yes", config.getUseProxy(), "UseProxy should be 'yes'");
	}

	/**
	 * Test getProxyHost and setProxyHost methods.
	 * Verifies that:
	 * - Setting a proxyHost value stores it correctly
	 * - Getting returns the set value
	 * - Setting null is allowed
	 * - Setting empty string is allowed
	 */
	@Test
	void testProxyHostGetterSetter() {
		// Test setting a normal value
		String testProxyHost = "proxy.example.com";
		config.setProxyHost(testProxyHost);
		assertEquals(testProxyHost, config.getProxyHost(), "ProxyHost should match the set value");

		// Test setting null
		config.setProxyHost(null);
		assertNull(config.getProxyHost(), "ProxyHost should be null after setting to null");

		// Test setting empty string
		config.setProxyHost("");
		assertEquals("", config.getProxyHost(), "ProxyHost should be empty string");

		// Test setting a different value
		String anotherProxyHost = "192.168.1.1";
		config.setProxyHost(anotherProxyHost);
		assertEquals(anotherProxyHost, config.getProxyHost(), "ProxyHost should match the new value");
	}

	/**
	 * Test getProxyPort and setProxyPort methods.
	 * Verifies that:
	 * - Setting a proxyPort value stores it correctly
	 * - Getting returns the set value
	 * - Setting positive, negative, and zero values all work
	 */
	@Test
	void testProxyPortGetterSetter() {
		// Test setting a typical proxy port value
		int testProxyPort = 8888;
		config.setProxyPort(testProxyPort);
		assertEquals(testProxyPort, config.getProxyPort(), "ProxyPort should match the set value");

		// Test setting zero
		config.setProxyPort(0);
		assertEquals(0, config.getProxyPort(), "ProxyPort should be 0");

		// Test setting a different value
		int anotherProxyPort = 3128;
		config.setProxyPort(anotherProxyPort);
		assertEquals(anotherProxyPort, config.getProxyPort(), "ProxyPort should match the new value");

		// Test setting a negative value (edge case)
		int negativeProxyPort = -1;
		config.setProxyPort(negativeProxyPort);
		assertEquals(negativeProxyPort, config.getProxyPort(), "ProxyPort should match the negative value");

		// Test setting a large value
		int largeProxyPort = 65535;
		config.setProxyPort(largeProxyPort);
		assertEquals(largeProxyPort, config.getProxyPort(), "ProxyPort should match the large value");
	}

	/**
	 * Test getProxyUser and setProxyUser methods.
	 * Verifies that:
	 * - Setting a proxyUser value stores it correctly
	 * - Getting returns the set value
	 * - Setting null is allowed
	 * - Setting empty string is allowed
	 */
	@Test
	void testProxyUserGetterSetter() {
		// Test setting a normal value
		String testProxyUser = "proxyUser";
		config.setProxyUser(testProxyUser);
		assertEquals(testProxyUser, config.getProxyUser(), "ProxyUser should match the set value");

		// Test setting null
		config.setProxyUser(null);
		assertNull(config.getProxyUser(), "ProxyUser should be null after setting to null");

		// Test setting empty string
		config.setProxyUser("");
		assertEquals("", config.getProxyUser(), "ProxyUser should be empty string");

		// Test setting a different value
		String anotherProxyUser = "anotherProxyUser";
		config.setProxyUser(anotherProxyUser);
		assertEquals(anotherProxyUser, config.getProxyUser(), "ProxyUser should match the new value");
	}

	/**
	 * Test getProxyPassword and setProxyPassword methods.
	 * Verifies that:
	 * - Setting a proxyPassword value stores it correctly
	 * - Getting returns the set value
	 * - Setting null is allowed
	 * - Setting empty string is allowed
	 */
	@Test
	void testProxyPasswordGetterSetter() {
		// Test setting a normal value
		String testProxyPassword = "proxyPass123";
		config.setProxyPassword(testProxyPassword);
		assertEquals(testProxyPassword, config.getProxyPassword(), "ProxyPassword should match the set value");

		// Test setting null
		config.setProxyPassword(null);
		assertNull(config.getProxyPassword(), "ProxyPassword should be null after setting to null");

		// Test setting empty string
		config.setProxyPassword("");
		assertEquals("", config.getProxyPassword(), "ProxyPassword should be empty string");

		// Test setting a different value
		String anotherProxyPassword = "anotherProxyPass456";
		config.setProxyPassword(anotherProxyPassword);
		assertEquals(anotherProxyPassword, config.getProxyPassword(), "ProxyPassword should match the new value");
	}

	/**
	 * Test that the class has the correct Spring annotations.
	 * This verifies that the class is properly configured as a Spring Configuration.
	 */
	@Test
	void testClassAnnotations() {
		assertTrue(
			RefinitivConfig.class.isAnnotationPresent(
				org.springframework.context.annotation.Configuration.class
			),
			"Class should be annotated with @Configuration"
		);
		assertTrue(
			RefinitivConfig.class.isAnnotationPresent(
				org.springframework.boot.context.properties.ConfigurationProperties.class
			),
			"Class should be annotated with @ConfigurationProperties"
		);
	}

	/**
	 * Test the ConfigurationProperties annotation has the correct prefix.
	 */
	@Test
	void testConfigurationPropertiesPrefix() {
		org.springframework.boot.context.properties.ConfigurationProperties annotation =
			RefinitivConfig.class.getAnnotation(
				org.springframework.boot.context.properties.ConfigurationProperties.class
			);

		assertNotNull(annotation, "ConfigurationProperties annotation should be present");
		assertEquals("refinitiv", annotation.prefix(), "ConfigurationProperties prefix should be 'refinitiv'");
	}

	/**
	 * Test that multiple properties can be set and retrieved correctly in combination.
	 * This simulates a realistic configuration scenario.
	 */
	@Test
	void testMultiplePropertiesSetAndGet() {
		// Set all properties
		config.setUser("testUser");
		config.setPassword("testPassword");
		config.setClientId("client-123");
		config.setHostName("api.refinitiv.com");
		config.setPort(443);
		config.setAuthUrl("https://auth.refinitiv.com");
		config.setUseProxy("true");
		config.setProxyHost("proxy.example.com");
		config.setProxyPort(8888);
		config.setProxyUser("proxyUser");
		config.setProxyPassword("proxyPass");

		// Verify all properties
		assertEquals("testUser", config.getUser());
		assertEquals("testPassword", config.getPassword());
		assertEquals("client-123", config.getClientId());
		assertEquals("api.refinitiv.com", config.getHostName());
		assertEquals(443, config.getPort());
		assertEquals("https://auth.refinitiv.com", config.getAuthUrl());
		assertEquals("true", config.getUseProxy());
		assertEquals("proxy.example.com", config.getProxyHost());
		assertEquals(8888, config.getProxyPort());
		assertEquals("proxyUser", config.getProxyUser());
		assertEquals("proxyPass", config.getProxyPassword());
	}

	/**
	 * Test that properties are independent of each other.
	 * Setting one property should not affect others.
	 */
	@Test
	void testPropertiesIndependence() {
		// Set some properties
		config.setUser("user1");
		config.setPassword("pass1");
		config.setPort(8080);

		// Verify initial values
		assertEquals("user1", config.getUser());
		assertEquals("pass1", config.getPassword());
		assertEquals(8080, config.getPort());

		// Change one property
		config.setUser("user2");

		// Verify only the changed property is affected
		assertEquals("user2", config.getUser());
		assertEquals("pass1", config.getPassword(), "Password should remain unchanged");
		assertEquals(8080, config.getPort(), "Port should remain unchanged");
	}
}
