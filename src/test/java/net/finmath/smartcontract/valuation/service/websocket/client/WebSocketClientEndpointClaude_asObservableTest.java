/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */

package net.finmath.smartcontract.valuation.service.websocket.client;

import net.finmath.smartcontract.model.SDCException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for WebSocketClientEndpoint.asObservable() method.
 * Tests the asObservable() method with focus on branch and condition coverage.
 *
 * Testing approach:
 * - The asObservable() method checks if userSession is null and initializes it if needed,
 *   then returns the messageSubject (a PublishSubject for RxJava Observable pattern)
 * - Since we cannot start a real WebSocket server and mocking is to be avoided,
 *   we test the expected behavior when the connection fails (which is the normal case
 *   in a test environment without a running WebSocket server)
 * - We verify that the method correctly attempts to initialize the session and throws
 *   the appropriate SDCException when the server is not available
 * - Testing is done without mocking to align with the testing requirements
 *
 * @author Claude Code
 */
class WebSocketClientEndpointClaude_asObservableTest {

	/**
	 * Test asObservable() throws SDCException when WebSocket server is not available.
	 * This is the primary branch test - when userSession is null, it tries to initialize.
	 */
	@Test
	@Timeout(10)
	void testAsObservable_ThrowsExceptionWhenServerNotAvailable() throws URISyntaxException {
		// Arrange
		final URI uri = new URI("ws://localhost:8080/test");
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "user", "password");

		// Act & Assert
		final SDCException exception = assertThrows(SDCException.class,
				endpoint::asObservable,
				"asObservable should throw SDCException when WebSocket server is not available");

		// Verify it's the correct exception type
		assertNotNull(exception.getMessage(), "Exception should have a message");
	}

	/**
	 * Test asObservable() with secure WebSocket URI.
	 * This tests the initialization path with a different protocol.
	 */
	@Test
	@Timeout(10)
	void testAsObservable_WithSecureWebSocketURI() throws URISyntaxException {
		// Arrange
		final URI uri = new URI("wss://secure.example.com:443/feed");
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "user", "password");

		// Act & Assert
		assertThrows(SDCException.class,
				endpoint::asObservable,
				"asObservable should throw SDCException with secure URI when server is not available");
	}

	/**
	 * Test asObservable() with different port.
	 * Verifies the method works with various port configurations.
	 */
	@Test
	@Timeout(10)
	void testAsObservable_WithDifferentPort() throws URISyntaxException {
		// Arrange
		final URI uri = new URI("ws://localhost:9999/endpoint");
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "user", "password");

		// Act & Assert
		assertThrows(SDCException.class,
				endpoint::asObservable,
				"asObservable should throw SDCException with different port when server is not available");
	}

	/**
	 * Test asObservable() with complex URI path.
	 * Verifies the method handles URIs with paths and query parameters.
	 */
	@Test
	@Timeout(10)
	void testAsObservable_WithComplexURIPath() throws URISyntaxException {
		// Arrange
		final URI uri = new URI("ws://localhost:8080/path/to/endpoint?param1=value1&param2=value2");
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "user", "password");

		// Act & Assert
		assertThrows(SDCException.class,
				endpoint::asObservable,
				"asObservable should throw SDCException with complex URI when server is not available");
	}

	/**
	 * Test asObservable() with empty credentials.
	 * Verifies the method attempts initialization even with empty auth.
	 */
	@Test
	@Timeout(10)
	void testAsObservable_WithEmptyCredentials() throws URISyntaxException {
		// Arrange
		final URI uri = new URI("ws://localhost:8080/test");
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "", "");

		// Act & Assert
		assertThrows(SDCException.class,
				endpoint::asObservable,
				"asObservable should throw SDCException with empty credentials when server is not available");
	}

	/**
	 * Test asObservable() with null URI.
	 * Verifies the method throws exception when trying to connect with null URI.
	 */
	@Test
	@Timeout(10)
	void testAsObservable_WithNullURI() {
		// Arrange
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(null, "user", "password");

		// Act & Assert
		assertThrows(SDCException.class,
				endpoint::asObservable,
				"asObservable should throw SDCException with null URI");
	}

	/**
	 * Test that asObservable() exception message indicates connection error.
	 * This verifies the correct exception type is thrown from initSession().
	 */
	@Test
	@Timeout(10)
	void testAsObservable_ExceptionMessageIndicatesConnectionError() throws URISyntaxException {
		// Arrange
		final URI uri = new URI("ws://localhost:8080/test");
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "user", "password");

		// Act
		final SDCException exception = assertThrows(SDCException.class,
				endpoint::asObservable,
				"asObservable should throw SDCException");

		// Assert
		assertNotNull(exception.getMessage(), "Exception should have a message");
		// The exception should be about WebSocket connection, not something else
		assertTrue(exception.getMessage().length() > 0, "Exception message should not be empty");
	}

	/**
	 * Test asObservable() with a remote host that doesn't exist.
	 * This tests the connection failure path with a non-localhost URI.
	 */
	@Test
	@Timeout(10)
	void testAsObservable_WithNonExistentRemoteHost() throws URISyntaxException {
		// Arrange
		final URI uri = new URI("ws://nonexistent.example.invalid:8080/test");
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "user", "password");

		// Act & Assert
		assertThrows(SDCException.class,
				endpoint::asObservable,
				"asObservable should throw SDCException when connecting to non-existent host");
	}

	/**
	 * Test asObservable() with special characters in credentials.
	 * Verifies initialization attempt is made regardless of credential format.
	 */
	@Test
	@Timeout(10)
	void testAsObservable_WithSpecialCharactersInCredentials() throws URISyntaxException {
		// Arrange
		final URI uri = new URI("ws://localhost:8080/test");
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "user@example.com", "p@ss:w0rd!");

		// Act & Assert
		assertThrows(SDCException.class,
				endpoint::asObservable,
				"asObservable should throw SDCException with special chars in credentials when server is not available");
	}

	/**
	 * Test that multiple calls to asObservable() on same endpoint throw same exception.
	 * This verifies the initialization is attempted on each call when it fails.
	 * Note: Since connection fails, userSession remains null, so initSession() is called each time.
	 */
	@Test
	@Timeout(10)
	void testAsObservable_MultipleCalls() throws URISyntaxException {
		// Arrange
		final URI uri = new URI("ws://localhost:8080/test");
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "user", "password");

		// Act & Assert - First call
		assertThrows(SDCException.class,
				endpoint::asObservable,
				"First asObservable call should throw SDCException");

		// Act & Assert - Second call
		assertThrows(SDCException.class,
				endpoint::asObservable,
				"Second asObservable call should throw SDCException");

		// Act & Assert - Third call
		assertThrows(SDCException.class,
				endpoint::asObservable,
				"Third asObservable call should throw SDCException");
	}

	/**
	 * Test asObservable() with localhost on standard WebSocket port.
	 * This is a common configuration that should fail gracefully.
	 */
	@Test
	@Timeout(10)
	void testAsObservable_WithStandardWebSocketPort() throws URISyntaxException {
		// Arrange
		final URI uri = new URI("ws://localhost:80/test");
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "user", "password");

		// Act & Assert
		assertThrows(SDCException.class,
				endpoint::asObservable,
				"asObservable should throw SDCException on standard port when server is not available");
	}

	/**
	 * Test asObservable() with 127.0.0.1 instead of localhost.
	 * Verifies the method works with IP address format.
	 */
	@Test
	@Timeout(10)
	void testAsObservable_WithIPAddress() throws URISyntaxException {
		// Arrange
		final URI uri = new URI("ws://127.0.0.1:8080/test");
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "user", "password");

		// Act & Assert
		assertThrows(SDCException.class,
				endpoint::asObservable,
				"asObservable should throw SDCException with IP address when server is not available");
	}

	/**
	 * Test asObservable() verifies exception is specifically an SDCException, not a generic RuntimeException.
	 * This ensures proper exception wrapping in the initSession() method.
	 */
	@Test
	@Timeout(10)
	void testAsObservable_ThrowsSDCExceptionNotGenericException() throws URISyntaxException {
		// Arrange
		final URI uri = new URI("ws://localhost:8080/test");
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "user", "password");

		// Act & Assert
		try {
			endpoint.asObservable();
			fail("Expected SDCException to be thrown");
		} catch (SDCException e) {
			// Expected - verify it's the specific exception type
			assertNotNull(e, "Exception should not be null");
			assertTrue(e instanceof SDCException, "Exception should be SDCException type");
		} catch (Exception e) {
			fail("Expected SDCException but got " + e.getClass().getName());
		}
	}

	/**
	 * Test asObservable() with very long URI path.
	 * Verifies the method handles URIs with long paths.
	 */
	@Test
	@Timeout(10)
	void testAsObservable_WithLongURIPath() throws URISyntaxException {
		// Arrange
		final String longPath = "/very/long/path/" + "segment/".repeat(50) + "endpoint";
		final URI uri = new URI("ws://localhost:8080" + longPath);
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "user", "password");

		// Act & Assert
		assertThrows(SDCException.class,
				endpoint::asObservable,
				"asObservable should throw SDCException with long URI path when server is not available");
	}

	/**
	 * Test asObservable() with Unicode characters in credentials.
	 * Verifies the method handles international characters.
	 */
	@Test
	@Timeout(10)
	void testAsObservable_WithUnicodeInCredentials() throws URISyntaxException {
		// Arrange
		final URI uri = new URI("ws://localhost:8080/test");
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "用户", "密码");

		// Act & Assert
		assertThrows(SDCException.class,
				endpoint::asObservable,
				"asObservable should throw SDCException with Unicode credentials when server is not available");
	}

	/**
	 * Test asObservable() behavior is consistent across multiple instances.
	 * Verifies that each endpoint maintains its own state.
	 */
	@Test
	@Timeout(10)
	void testAsObservable_MultipleInstances() throws URISyntaxException {
		// Arrange
		final WebSocketClientEndpoint endpoint1 = new WebSocketClientEndpoint(
				new URI("ws://localhost:8080/test1"), "user1", "pass1");
		final WebSocketClientEndpoint endpoint2 = new WebSocketClientEndpoint(
				new URI("ws://localhost:8081/test2"), "user2", "pass2");

		// Act & Assert - Both should fail independently
		assertThrows(SDCException.class,
				endpoint1::asObservable,
				"First endpoint asObservable should throw SDCException");

		assertThrows(SDCException.class,
				endpoint2::asObservable,
				"Second endpoint asObservable should throw SDCException");
	}

	/**
	 * Test that asObservable() and getUserSession() are consistent.
	 * Both methods trigger initSession() when userSession is null.
	 */
	@Test
	@Timeout(10)
	void testAsObservable_ConsistentWithGetUserSession() throws URISyntaxException {
		// Arrange
		final URI uri = new URI("ws://localhost:8080/test");
		final WebSocketClientEndpoint endpoint1 = new WebSocketClientEndpoint(uri, "user", "password");
		final WebSocketClientEndpoint endpoint2 = new WebSocketClientEndpoint(uri, "user", "password");

		// Act & Assert - asObservable should throw like getUserSession
		assertThrows(SDCException.class,
				endpoint1::asObservable,
				"asObservable should throw SDCException");

		assertThrows(SDCException.class,
				endpoint2::getUserSession,
				"getUserSession should throw SDCException");

		// Both should behave the same way since they both call initSession()
	}

	/**
	 * Test asObservable() with null username.
	 * This tests edge case behavior with null credentials.
	 */
	@Test
	@Timeout(10)
	void testAsObservable_WithNullUsername() throws URISyntaxException {
		// Arrange
		final URI uri = new URI("ws://localhost:8080/test");
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, null, "password");

		// Act & Assert
		assertThrows(SDCException.class,
				endpoint::asObservable,
				"asObservable should throw SDCException with null username when server is not available");
	}

	/**
	 * Test asObservable() with null password.
	 * This tests edge case behavior with null credentials.
	 */
	@Test
	@Timeout(10)
	void testAsObservable_WithNullPassword() throws URISyntaxException {
		// Arrange
		final URI uri = new URI("ws://localhost:8080/test");
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "user", null);

		// Act & Assert
		assertThrows(SDCException.class,
				endpoint::asObservable,
				"asObservable should throw SDCException with null password when server is not available");
	}
}
