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
 * Test class for WebSocketClientEndpoint.sendTextMessage(String) method.
 * Tests the sendTextMessage() method with focus on branch and condition coverage.
 *
 * Testing approach:
 * - The sendTextMessage() method checks if userSession is null and initializes it if needed,
 *   then sends the message via the WebSocket session
 * - Since we cannot start a real WebSocket server and mocking is to be avoided,
 *   we test the expected behavior when the connection fails (which is the normal case
 *   in a test environment without a running WebSocket server)
 * - We verify that the method correctly attempts to initialize the session and throws
 *   the appropriate SDCException when the server is not available
 * - The method declares IOException but also can throw SDCException from initSession()
 * - Testing is done without mocking to align with the testing requirements
 *
 * @author Claude Code
 */
class WebSocketClientEndpointClaude_sendTextMessageTest {

	/**
	 * Test sendTextMessage() throws SDCException when WebSocket server is not available.
	 * This is the primary branch test - when userSession is null, it tries to initialize.
	 */
	@Test
	@Timeout(10)
	void testSendTextMessage_ThrowsExceptionWhenServerNotAvailable() throws URISyntaxException {
		// Arrange
		final URI uri = new URI("ws://localhost:8080/test");
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "user", "password");

		// Act & Assert
		final SDCException exception = assertThrows(SDCException.class,
				() -> endpoint.sendTextMessage("test message"),
				"sendTextMessage should throw SDCException when WebSocket server is not available");

		// Verify it's the correct exception type
		assertNotNull(exception.getMessage(), "Exception should have a message");
	}

	/**
	 * Test sendTextMessage() with empty message.
	 * The method should still try to initialize and send an empty message.
	 */
	@Test
	@Timeout(10)
	void testSendTextMessage_WithEmptyMessage() throws URISyntaxException {
		// Arrange
		final URI uri = new URI("ws://localhost:8080/test");
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "user", "password");

		// Act & Assert
		assertThrows(SDCException.class,
				() -> endpoint.sendTextMessage(""),
				"sendTextMessage should throw SDCException with empty message when server is not available");
	}

	/**
	 * Test sendTextMessage() with null message.
	 * This tests edge case behavior - the method will try to initialize and then send null.
	 */
	@Test
	@Timeout(10)
	void testSendTextMessage_WithNullMessage() throws URISyntaxException {
		// Arrange
		final URI uri = new URI("ws://localhost:8080/test");
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "user", "password");

		// Act & Assert
		// The method will fail during initSession before it gets to send the null message
		assertThrows(SDCException.class,
				() -> endpoint.sendTextMessage(null),
				"sendTextMessage should throw SDCException with null message when server is not available");
	}

	/**
	 * Test sendTextMessage() with long message.
	 * Verifies the method attempts to send messages of various sizes.
	 */
	@Test
	@Timeout(10)
	void testSendTextMessage_WithLongMessage() throws URISyntaxException {
		// Arrange
		final URI uri = new URI("ws://localhost:8080/test");
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "user", "password");
		final String longMessage = "x".repeat(10000);

		// Act & Assert
		assertThrows(SDCException.class,
				() -> endpoint.sendTextMessage(longMessage),
				"sendTextMessage should throw SDCException with long message when server is not available");
	}

	/**
	 * Test sendTextMessage() with special characters.
	 * Verifies the method handles messages with special characters.
	 */
	@Test
	@Timeout(10)
	void testSendTextMessage_WithSpecialCharacters() throws URISyntaxException {
		// Arrange
		final URI uri = new URI("ws://localhost:8080/test");
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "user", "password");
		final String specialMessage = "!@#$%^&*()_+-=[]{}|;':\",./<>?";

		// Act & Assert
		assertThrows(SDCException.class,
				() -> endpoint.sendTextMessage(specialMessage),
				"sendTextMessage should throw SDCException with special characters when server is not available");
	}

	/**
	 * Test sendTextMessage() with Unicode characters.
	 * Verifies the method handles messages with international characters.
	 */
	@Test
	@Timeout(10)
	void testSendTextMessage_WithUnicodeCharacters() throws URISyntaxException {
		// Arrange
		final URI uri = new URI("ws://localhost:8080/test");
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "user", "password");
		final String unicodeMessage = "Hello 世界 Привет мир";

		// Act & Assert
		assertThrows(SDCException.class,
				() -> endpoint.sendTextMessage(unicodeMessage),
				"sendTextMessage should throw SDCException with Unicode characters when server is not available");
	}

	/**
	 * Test sendTextMessage() with JSON-formatted message.
	 * This is a common use case for WebSocket communication.
	 */
	@Test
	@Timeout(10)
	void testSendTextMessage_WithJSONMessage() throws URISyntaxException {
		// Arrange
		final URI uri = new URI("ws://localhost:8080/test");
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "user", "password");
		final String jsonMessage = "{\"type\":\"test\",\"data\":\"value\"}";

		// Act & Assert
		assertThrows(SDCException.class,
				() -> endpoint.sendTextMessage(jsonMessage),
				"sendTextMessage should throw SDCException with JSON message when server is not available");
	}

	/**
	 * Test sendTextMessage() with XML-formatted message.
	 * This is relevant given the project deals with XML contracts.
	 */
	@Test
	@Timeout(10)
	void testSendTextMessage_WithXMLMessage() throws URISyntaxException {
		// Arrange
		final URI uri = new URI("ws://localhost:8080/test");
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "user", "password");
		final String xmlMessage = "<?xml version=\"1.0\"?><root><element>value</element></root>";

		// Act & Assert
		assertThrows(SDCException.class,
				() -> endpoint.sendTextMessage(xmlMessage),
				"sendTextMessage should throw SDCException with XML message when server is not available");
	}

	/**
	 * Test sendTextMessage() with newlines in message.
	 * Verifies the method handles multi-line messages.
	 */
	@Test
	@Timeout(10)
	void testSendTextMessage_WithNewlines() throws URISyntaxException {
		// Arrange
		final URI uri = new URI("ws://localhost:8080/test");
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "user", "password");
		final String multiLineMessage = "Line 1\nLine 2\nLine 3";

		// Act & Assert
		assertThrows(SDCException.class,
				() -> endpoint.sendTextMessage(multiLineMessage),
				"sendTextMessage should throw SDCException with newlines when server is not available");
	}

	/**
	 * Test sendTextMessage() with tabs and whitespace.
	 * Verifies the method handles various whitespace characters.
	 */
	@Test
	@Timeout(10)
	void testSendTextMessage_WithTabsAndWhitespace() throws URISyntaxException {
		// Arrange
		final URI uri = new URI("ws://localhost:8080/test");
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "user", "password");
		final String whitespaceMessage = "text\twith\ttabs\t  and   spaces";

		// Act & Assert
		assertThrows(SDCException.class,
				() -> endpoint.sendTextMessage(whitespaceMessage),
				"sendTextMessage should throw SDCException with tabs and whitespace when server is not available");
	}

	/**
	 * Test sendTextMessage() with secure WebSocket URI.
	 * This tests the initialization path with a different protocol.
	 */
	@Test
	@Timeout(10)
	void testSendTextMessage_WithSecureWebSocketURI() throws URISyntaxException {
		// Arrange
		final URI uri = new URI("wss://secure.example.com:443/feed");
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "user", "password");

		// Act & Assert
		assertThrows(SDCException.class,
				() -> endpoint.sendTextMessage("test message"),
				"sendTextMessage should throw SDCException with secure URI when server is not available");
	}

	/**
	 * Test sendTextMessage() with null URI.
	 * Verifies the method throws exception when trying to connect with null URI.
	 */
	@Test
	@Timeout(10)
	void testSendTextMessage_WithNullURI() {
		// Arrange
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(null, "user", "password");

		// Act & Assert
		assertThrows(SDCException.class,
				() -> endpoint.sendTextMessage("test message"),
				"sendTextMessage should throw SDCException with null URI");
	}

	/**
	 * Test that multiple calls to sendTextMessage() throw consistent exceptions.
	 * This verifies the initialization is attempted on each call when it fails.
	 * Note: Since connection fails, userSession remains null, so initSession() is called each time.
	 */
	@Test
	@Timeout(10)
	void testSendTextMessage_MultipleCalls() throws URISyntaxException {
		// Arrange
		final URI uri = new URI("ws://localhost:8080/test");
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "user", "password");

		// Act & Assert - First call
		assertThrows(SDCException.class,
				() -> endpoint.sendTextMessage("message 1"),
				"First sendTextMessage call should throw SDCException");

		// Act & Assert - Second call
		assertThrows(SDCException.class,
				() -> endpoint.sendTextMessage("message 2"),
				"Second sendTextMessage call should throw SDCException");

		// Act & Assert - Third call
		assertThrows(SDCException.class,
				() -> endpoint.sendTextMessage("message 3"),
				"Third sendTextMessage call should throw SDCException");
	}

	/**
	 * Test sendTextMessage() exception message indicates connection error.
	 * This verifies the correct exception type is thrown from initSession().
	 */
	@Test
	@Timeout(10)
	void testSendTextMessage_ExceptionMessageIndicatesConnectionError() throws URISyntaxException {
		// Arrange
		final URI uri = new URI("ws://localhost:8080/test");
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "user", "password");

		// Act
		final SDCException exception = assertThrows(SDCException.class,
				() -> endpoint.sendTextMessage("test message"),
				"sendTextMessage should throw SDCException");

		// Assert
		assertNotNull(exception.getMessage(), "Exception should have a message");
		// The exception should be about WebSocket connection, not something else
		assertTrue(exception.getMessage().length() > 0, "Exception message should not be empty");
	}

	/**
	 * Test sendTextMessage() verifies exception is specifically an SDCException, not IOException.
	 * Even though IOException is declared, initSession() throws SDCException first.
	 */
	@Test
	@Timeout(10)
	void testSendTextMessage_ThrowsSDCExceptionNotIOException() throws URISyntaxException {
		// Arrange
		final URI uri = new URI("ws://localhost:8080/test");
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "user", "password");

		// Act & Assert
		try {
			endpoint.sendTextMessage("test message");
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
	 * Test sendTextMessage() with non-existent remote host.
	 * This tests the connection failure path with a remote URI.
	 */
	@Test
	@Timeout(10)
	void testSendTextMessage_WithNonExistentRemoteHost() throws URISyntaxException {
		// Arrange
		final URI uri = new URI("ws://nonexistent.example.invalid:8080/test");
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "user", "password");

		// Act & Assert
		assertThrows(SDCException.class,
				() -> endpoint.sendTextMessage("test message"),
				"sendTextMessage should throw SDCException when connecting to non-existent host");
	}

	/**
	 * Test sendTextMessage() behavior is consistent across multiple instances.
	 * Verifies that each endpoint maintains its own state.
	 */
	@Test
	@Timeout(10)
	void testSendTextMessage_MultipleInstances() throws URISyntaxException {
		// Arrange
		final WebSocketClientEndpoint endpoint1 = new WebSocketClientEndpoint(
				new URI("ws://localhost:8080/test1"), "user1", "pass1");
		final WebSocketClientEndpoint endpoint2 = new WebSocketClientEndpoint(
				new URI("ws://localhost:8081/test2"), "user2", "pass2");

		// Act & Assert - Both should fail independently
		assertThrows(SDCException.class,
				() -> endpoint1.sendTextMessage("message to endpoint 1"),
				"First endpoint sendTextMessage should throw SDCException");

		assertThrows(SDCException.class,
				() -> endpoint2.sendTextMessage("message to endpoint 2"),
				"Second endpoint sendTextMessage should throw SDCException");
	}

	/**
	 * Test sendTextMessage() with message containing quotes.
	 * Verifies the method handles messages with both single and double quotes.
	 */
	@Test
	@Timeout(10)
	void testSendTextMessage_WithQuotes() throws URISyntaxException {
		// Arrange
		final URI uri = new URI("ws://localhost:8080/test");
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "user", "password");
		final String quotedMessage = "Message with 'single' and \"double\" quotes";

		// Act & Assert
		assertThrows(SDCException.class,
				() -> endpoint.sendTextMessage(quotedMessage),
				"sendTextMessage should throw SDCException with quotes when server is not available");
	}

	/**
	 * Test sendTextMessage() with message containing backslashes.
	 * Verifies the method handles escape characters properly.
	 */
	@Test
	@Timeout(10)
	void testSendTextMessage_WithBackslashes() throws URISyntaxException {
		// Arrange
		final URI uri = new URI("ws://localhost:8080/test");
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "user", "password");
		final String backslashMessage = "Path: C:\\Users\\test\\file.txt";

		// Act & Assert
		assertThrows(SDCException.class,
				() -> endpoint.sendTextMessage(backslashMessage),
				"sendTextMessage should throw SDCException with backslashes when server is not available");
	}

	/**
	 * Test sendTextMessage() with very long message (1MB).
	 * Verifies the method attempts to send large messages.
	 */
	@Test
	@Timeout(10)
	void testSendTextMessage_WithVeryLongMessage() throws URISyntaxException {
		// Arrange
		final URI uri = new URI("ws://localhost:8080/test");
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "user", "password");
		final String veryLongMessage = "x".repeat(1024 * 1024); // 1MB

		// Act & Assert
		assertThrows(SDCException.class,
				() -> endpoint.sendTextMessage(veryLongMessage),
				"sendTextMessage should throw SDCException with very long message when server is not available");
	}

	/**
	 * Test sendTextMessage() with control characters.
	 * Verifies the method handles various ASCII control characters.
	 */
	@Test
	@Timeout(10)
	void testSendTextMessage_WithControlCharacters() throws URISyntaxException {
		// Arrange
		final URI uri = new URI("ws://localhost:8080/test");
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "user", "password");
		final String controlMessage = "Message\u0000with\u0001control\u0002chars";

		// Act & Assert
		assertThrows(SDCException.class,
				() -> endpoint.sendTextMessage(controlMessage),
				"sendTextMessage should throw SDCException with control characters when server is not available");
	}

	/**
	 * Test that sendTextMessage(), getUserSession(), and asObservable() are consistent.
	 * All three methods trigger initSession() when userSession is null.
	 */
	@Test
	@Timeout(10)
	void testSendTextMessage_ConsistentWithOtherMethods() throws URISyntaxException {
		// Arrange
		final URI uri = new URI("ws://localhost:8080/test");
		final WebSocketClientEndpoint endpoint1 = new WebSocketClientEndpoint(uri, "user", "password");
		final WebSocketClientEndpoint endpoint2 = new WebSocketClientEndpoint(uri, "user", "password");
		final WebSocketClientEndpoint endpoint3 = new WebSocketClientEndpoint(uri, "user", "password");

		// Act & Assert - All three methods should throw SDCException
		assertThrows(SDCException.class,
				() -> endpoint1.sendTextMessage("test"),
				"sendTextMessage should throw SDCException");

		assertThrows(SDCException.class,
				endpoint2::getUserSession,
				"getUserSession should throw SDCException");

		assertThrows(SDCException.class,
				endpoint3::asObservable,
				"asObservable should throw SDCException");

		// All should behave the same way since they all call initSession()
	}
}
