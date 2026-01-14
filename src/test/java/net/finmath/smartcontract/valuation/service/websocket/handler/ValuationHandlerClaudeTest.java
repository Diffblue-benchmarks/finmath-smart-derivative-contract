/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */

package net.finmath.smartcontract.valuation.service.websocket.handler;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for ValuationHandler.
 * Tests all methods in net.finmath.smartcontract.valuation.service.websocket.handler.ValuationHandler
 * with focus on branch and condition coverage.
 *
 * Testing approach:
 * - The ValuationHandler implements Spring's WebSocketHandler interface
 * - Most methods are empty implementations (afterConnectionEstablished, afterConnectionClosed, handleTransportError)
 * - The supportsPartialMessages() method returns a simple boolean
 * - The handleMessage() method is complex and requires external resources (properties file, market data connection)
 * - Due to hardcoded file paths and external dependencies, we test what can be tested without mocking
 *
 * @author Claude Code
 */
class ValuationHandlerClaudeTest {

	/**
	 * Simple test implementation of WebSocketSession for testing purposes.
	 */
	private static class TestWebSocketSession implements WebSocketSession {
		private final String id;
		private final Map<String, Object> attributes = new HashMap<>();
		private boolean open = true;

		public TestWebSocketSession(String id) {
			this.id = id;
		}

		@Override
		public String getId() {
			return id;
		}

		@Override
		public URI getUri() {
			return null;
		}

		@Override
		public HttpHeaders getHandshakeHeaders() {
			return new HttpHeaders();
		}

		@Override
		public Map<String, Object> getAttributes() {
			return attributes;
		}

		@Override
		public Principal getPrincipal() {
			return null;
		}

		@Override
		public InetSocketAddress getLocalAddress() {
			return null;
		}

		@Override
		public InetSocketAddress getRemoteAddress() {
			return null;
		}

		@Override
		public String getAcceptedProtocol() {
			return null;
		}

		@Override
		public void setTextMessageSizeLimit(int messageSizeLimit) {
		}

		@Override
		public int getTextMessageSizeLimit() {
			return 0;
		}

		@Override
		public void setBinaryMessageSizeLimit(int messageSizeLimit) {
		}

		@Override
		public int getBinaryMessageSizeLimit() {
			return 0;
		}

		@Override
		public List<WebSocketExtension> getExtensions() {
			return Collections.emptyList();
		}

		@Override
		public void sendMessage(WebSocketMessage<?> message) throws IOException {
			// Do nothing in test
		}

		@Override
		public boolean isOpen() {
			return open;
		}

		@Override
		public void close() throws IOException {
			open = false;
		}

		@Override
		public void close(CloseStatus status) throws IOException {
			open = false;
		}
	}

	/**
	 * Test constructor creates instance successfully.
	 */
	@Test
	void testConstructor_CreatesInstanceSuccessfully() {
		// Act
		final ValuationHandler handler = new ValuationHandler();

		// Assert
		assertNotNull(handler, "ValuationHandler should be created successfully");
	}

	/**
	 * Test supportsPartialMessages returns false.
	 * The handler does not support partial/fragmented WebSocket messages.
	 */
	@Test
	void testSupportsPartialMessages_ReturnsFalse() {
		// Arrange
		final ValuationHandler handler = new ValuationHandler();

		// Act
		final boolean result = handler.supportsPartialMessages();

		// Assert
		assertFalse(result, "supportsPartialMessages should return false");
	}

	/**
	 * Test afterConnectionEstablished does not throw exception.
	 * The method has an empty implementation.
	 */
	@Test
	void testAfterConnectionEstablished_WithValidSession_DoesNotThrow() throws Exception {
		// Arrange
		final ValuationHandler handler = new ValuationHandler();
		final TestWebSocketSession session = new TestWebSocketSession("test-session-1");

		// Act & Assert
		assertDoesNotThrow(() -> handler.afterConnectionEstablished(session),
				"afterConnectionEstablished should not throw exception");
	}

	/**
	 * Test afterConnectionEstablished with null session.
	 * Even though it's an empty implementation, we verify it handles null gracefully.
	 */
	@Test
	void testAfterConnectionEstablished_WithNullSession_DoesNotThrow() throws Exception {
		// Arrange
		final ValuationHandler handler = new ValuationHandler();

		// Act & Assert
		assertDoesNotThrow(() -> handler.afterConnectionEstablished(null),
				"afterConnectionEstablished should not throw exception with null session");
	}

	/**
	 * Test afterConnectionClosed does not throw exception with normal close status.
	 */
	@Test
	void testAfterConnectionClosed_WithNormalCloseStatus_DoesNotThrow() throws Exception {
		// Arrange
		final ValuationHandler handler = new ValuationHandler();
		final TestWebSocketSession session = new TestWebSocketSession("test-session-1");
		final CloseStatus status = CloseStatus.NORMAL;

		// Act & Assert
		assertDoesNotThrow(() -> handler.afterConnectionClosed(session, status),
				"afterConnectionClosed should not throw exception with normal close status");
	}

	/**
	 * Test afterConnectionClosed with GOING_AWAY close status.
	 */
	@Test
	void testAfterConnectionClosed_WithGoingAwayStatus_DoesNotThrow() throws Exception {
		// Arrange
		final ValuationHandler handler = new ValuationHandler();
		final TestWebSocketSession session = new TestWebSocketSession("test-session-1");
		final CloseStatus status = CloseStatus.GOING_AWAY;

		// Act & Assert
		assertDoesNotThrow(() -> handler.afterConnectionClosed(session, status),
				"afterConnectionClosed should not throw exception with GOING_AWAY status");
	}

	/**
	 * Test afterConnectionClosed with PROTOCOL_ERROR close status.
	 */
	@Test
	void testAfterConnectionClosed_WithProtocolErrorStatus_DoesNotThrow() throws Exception {
		// Arrange
		final ValuationHandler handler = new ValuationHandler();
		final TestWebSocketSession session = new TestWebSocketSession("test-session-1");
		final CloseStatus status = CloseStatus.PROTOCOL_ERROR;

		// Act & Assert
		assertDoesNotThrow(() -> handler.afterConnectionClosed(session, status),
				"afterConnectionClosed should not throw exception with PROTOCOL_ERROR status");
	}

	/**
	 * Test afterConnectionClosed with SERVER_ERROR close status.
	 */
	@Test
	void testAfterConnectionClosed_WithServerErrorStatus_DoesNotThrow() throws Exception {
		// Arrange
		final ValuationHandler handler = new ValuationHandler();
		final TestWebSocketSession session = new TestWebSocketSession("test-session-1");
		final CloseStatus status = CloseStatus.SERVER_ERROR;

		// Act & Assert
		assertDoesNotThrow(() -> handler.afterConnectionClosed(session, status),
				"afterConnectionClosed should not throw exception with SERVER_ERROR status");
	}

	/**
	 * Test afterConnectionClosed with null session.
	 */
	@Test
	void testAfterConnectionClosed_WithNullSession_DoesNotThrow() throws Exception {
		// Arrange
		final ValuationHandler handler = new ValuationHandler();
		final CloseStatus status = CloseStatus.NORMAL;

		// Act & Assert
		assertDoesNotThrow(() -> handler.afterConnectionClosed(null, status),
				"afterConnectionClosed should not throw exception with null session");
	}

	/**
	 * Test afterConnectionClosed with null close status.
	 */
	@Test
	void testAfterConnectionClosed_WithNullCloseStatus_DoesNotThrow() throws Exception {
		// Arrange
		final ValuationHandler handler = new ValuationHandler();
		final TestWebSocketSession session = new TestWebSocketSession("test-session-1");

		// Act & Assert
		assertDoesNotThrow(() -> handler.afterConnectionClosed(session, null),
				"afterConnectionClosed should not throw exception with null close status");
	}

	/**
	 * Test afterConnectionClosed with custom close status code.
	 */
	@Test
	void testAfterConnectionClosed_WithCustomCloseStatus_DoesNotThrow() throws Exception {
		// Arrange
		final ValuationHandler handler = new ValuationHandler();
		final TestWebSocketSession session = new TestWebSocketSession("test-session-1");
		final CloseStatus status = new CloseStatus(4000, "Custom close reason");

		// Act & Assert
		assertDoesNotThrow(() -> handler.afterConnectionClosed(session, status),
				"afterConnectionClosed should not throw exception with custom close status");
	}

	/**
	 * Test handleTransportError does not throw exception with RuntimeException.
	 */
	@Test
	void testHandleTransportError_WithRuntimeException_DoesNotThrow() throws Exception {
		// Arrange
		final ValuationHandler handler = new ValuationHandler();
		final TestWebSocketSession session = new TestWebSocketSession("test-session-1");
		final Throwable exception = new RuntimeException("Test error");

		// Act & Assert
		assertDoesNotThrow(() -> handler.handleTransportError(session, exception),
				"handleTransportError should not throw exception with RuntimeException");
	}

	/**
	 * Test handleTransportError with IOException.
	 */
	@Test
	void testHandleTransportError_WithIOException_DoesNotThrow() throws Exception {
		// Arrange
		final ValuationHandler handler = new ValuationHandler();
		final TestWebSocketSession session = new TestWebSocketSession("test-session-1");
		final Throwable exception = new IOException("Connection error");

		// Act & Assert
		assertDoesNotThrow(() -> handler.handleTransportError(session, exception),
				"handleTransportError should not throw exception with IOException");
	}

	/**
	 * Test handleTransportError with null session.
	 */
	@Test
	void testHandleTransportError_WithNullSession_DoesNotThrow() throws Exception {
		// Arrange
		final ValuationHandler handler = new ValuationHandler();
		final Throwable exception = new RuntimeException("Test error");

		// Act & Assert
		assertDoesNotThrow(() -> handler.handleTransportError(null, exception),
				"handleTransportError should not throw exception with null session");
	}

	/**
	 * Test handleTransportError with null exception.
	 */
	@Test
	void testHandleTransportError_WithNullException_DoesNotThrow() throws Exception {
		// Arrange
		final ValuationHandler handler = new ValuationHandler();
		final TestWebSocketSession session = new TestWebSocketSession("test-session-1");

		// Act & Assert
		assertDoesNotThrow(() -> handler.handleTransportError(session, null),
				"handleTransportError should not throw exception with null exception");
	}

	/**
	 * Test handleTransportError with nested exception.
	 */
	@Test
	void testHandleTransportError_WithNestedException_DoesNotThrow() throws Exception {
		// Arrange
		final ValuationHandler handler = new ValuationHandler();
		final TestWebSocketSession session = new TestWebSocketSession("test-session-1");
		final Throwable cause = new IOException("Root cause");
		final Throwable exception = new RuntimeException("Wrapper exception", cause);

		// Act & Assert
		assertDoesNotThrow(() -> handler.handleTransportError(session, exception),
				"handleTransportError should not throw exception with nested exception");
	}

	/**
	 * Test handleMessage with non-TextMessage throws IllegalStateException.
	 * The handler only accepts TextMessage.
	 */
	@Test
	void testHandleMessage_WithBinaryMessage_ThrowsIllegalStateException() {
		// Arrange
		final ValuationHandler handler = new ValuationHandler();
		final TestWebSocketSession session = new TestWebSocketSession("test-session-1");
		final BinaryMessage message = new BinaryMessage(new byte[]{1, 2, 3});

		// Act & Assert
		final Exception exception = assertThrows(Exception.class,
				() -> handler.handleMessage(session, message),
				"handleMessage should throw exception with BinaryMessage");

		assertTrue(exception instanceof IllegalStateException || exception.getCause() instanceof IllegalStateException,
				"Exception should be or contain IllegalStateException");
	}

	/**
	 * Test handleMessage with PingMessage throws IllegalStateException.
	 */
	@Test
	void testHandleMessage_WithPingMessage_ThrowsIllegalStateException() {
		// Arrange
		final ValuationHandler handler = new ValuationHandler();
		final TestWebSocketSession session = new TestWebSocketSession("test-session-1");
		final PingMessage message = new PingMessage();

		// Act & Assert
		final Exception exception = assertThrows(Exception.class,
				() -> handler.handleMessage(session, message),
				"handleMessage should throw exception with PingMessage");

		assertTrue(exception instanceof IllegalStateException || exception.getCause() instanceof IllegalStateException,
				"Exception should be or contain IllegalStateException");
	}

	/**
	 * Test handleMessage with PongMessage throws IllegalStateException.
	 */
	@Test
	void testHandleMessage_WithPongMessage_ThrowsIllegalStateException() {
		// Arrange
		final ValuationHandler handler = new ValuationHandler();
		final TestWebSocketSession session = new TestWebSocketSession("test-session-1");
		final PongMessage message = new PongMessage();

		// Act & Assert
		final Exception exception = assertThrows(Exception.class,
				() -> handler.handleMessage(session, message),
				"handleMessage should throw exception with PongMessage");

		assertTrue(exception instanceof IllegalStateException || exception.getCause() instanceof IllegalStateException,
				"Exception should be or contain IllegalStateException");
	}

	/**
	 * Test handleMessage with TextMessage containing invalid XML.
	 * The method will fail when trying to parse the XML, but we're testing that it
	 * attempts to process TextMessage correctly (not throwing IllegalStateException).
	 */
	@Test
	void testHandleMessage_WithInvalidXML_ThrowsException() {
		// Arrange
		final ValuationHandler handler = new ValuationHandler();
		final TestWebSocketSession session = new TestWebSocketSession("test-session-1");
		final TextMessage message = new TextMessage("not valid xml");

		// Act & Assert
		assertThrows(Exception.class,
				() -> handler.handleMessage(session, message),
				"handleMessage should throw exception when XML parsing fails");
	}

	/**
	 * Test handleMessage with null session throws exception.
	 */
	@Test
	void testHandleMessage_WithNullSession_ThrowsException() {
		// Arrange
		final ValuationHandler handler = new ValuationHandler();
		final TextMessage message = new TextMessage("test");

		// Act & Assert
		assertThrows(Exception.class,
				() -> handler.handleMessage(null, message),
				"handleMessage should throw exception with null session");
	}

	/**
	 * Test handleMessage with null message throws exception.
	 */
	@Test
	void testHandleMessage_WithNullMessage_ThrowsException() {
		// Arrange
		final ValuationHandler handler = new ValuationHandler();
		final TestWebSocketSession session = new TestWebSocketSession("test-session-1");

		// Act & Assert
		assertThrows(Exception.class,
				() -> handler.handleMessage(session, null),
				"handleMessage should throw exception with null message");
	}

	/**
	 * Test handleMessage with empty TextMessage.
	 */
	@Test
	void testHandleMessage_WithEmptyTextMessage_ThrowsException() {
		// Arrange
		final ValuationHandler handler = new ValuationHandler();
		final TestWebSocketSession session = new TestWebSocketSession("test-session-1");
		final TextMessage message = new TextMessage("");

		// Act & Assert
		assertThrows(Exception.class,
				() -> handler.handleMessage(session, message),
				"handleMessage should throw exception with empty text message");
	}

	/**
	 * Test that multiple handler instances can be created independently.
	 */
	@Test
	void testMultipleHandlerInstances_AreIndependent() {
		// Arrange & Act
		final ValuationHandler handler1 = new ValuationHandler();
		final ValuationHandler handler2 = new ValuationHandler();
		final ValuationHandler handler3 = new ValuationHandler();

		// Assert
		assertNotNull(handler1, "First handler should be created");
		assertNotNull(handler2, "Second handler should be created");
		assertNotNull(handler3, "Third handler should be created");
		assertNotSame(handler1, handler2, "Handlers should be different instances");
		assertNotSame(handler2, handler3, "Handlers should be different instances");
		assertNotSame(handler1, handler3, "Handlers should be different instances");
	}

	/**
	 * Test supportsPartialMessages on multiple handler instances returns consistent result.
	 */
	@Test
	void testSupportsPartialMessages_MultipleInstances_ReturnsConsistentResult() {
		// Arrange
		final ValuationHandler handler1 = new ValuationHandler();
		final ValuationHandler handler2 = new ValuationHandler();

		// Act
		final boolean result1 = handler1.supportsPartialMessages();
		final boolean result2 = handler2.supportsPartialMessages();

		// Assert
		assertEquals(result1, result2, "supportsPartialMessages should return same value for all instances");
		assertFalse(result1, "supportsPartialMessages should return false");
		assertFalse(result2, "supportsPartialMessages should return false");
	}

	/**
	 * Test afterConnectionEstablished can be called multiple times on same handler.
	 */
	@Test
	void testAfterConnectionEstablished_CalledMultipleTimes_DoesNotThrow() throws Exception {
		// Arrange
		final ValuationHandler handler = new ValuationHandler();
		final TestWebSocketSession session1 = new TestWebSocketSession("session-1");
		final TestWebSocketSession session2 = new TestWebSocketSession("session-2");
		final TestWebSocketSession session3 = new TestWebSocketSession("session-3");

		// Act & Assert
		assertDoesNotThrow(() -> handler.afterConnectionEstablished(session1),
				"First call should not throw");
		assertDoesNotThrow(() -> handler.afterConnectionEstablished(session2),
				"Second call should not throw");
		assertDoesNotThrow(() -> handler.afterConnectionEstablished(session3),
				"Third call should not throw");
	}

	/**
	 * Test afterConnectionClosed can be called multiple times on same handler.
	 */
	@Test
	void testAfterConnectionClosed_CalledMultipleTimes_DoesNotThrow() throws Exception {
		// Arrange
		final ValuationHandler handler = new ValuationHandler();
		final TestWebSocketSession session1 = new TestWebSocketSession("session-1");
		final TestWebSocketSession session2 = new TestWebSocketSession("session-2");
		final CloseStatus status = CloseStatus.NORMAL;

		// Act & Assert
		assertDoesNotThrow(() -> handler.afterConnectionClosed(session1, status),
				"First call should not throw");
		assertDoesNotThrow(() -> handler.afterConnectionClosed(session2, status),
				"Second call should not throw");
	}

	/**
	 * Test handleTransportError can be called multiple times on same handler.
	 */
	@Test
	void testHandleTransportError_CalledMultipleTimes_DoesNotThrow() throws Exception {
		// Arrange
		final ValuationHandler handler = new ValuationHandler();
		final TestWebSocketSession session = new TestWebSocketSession("session-1");
		final Throwable exception1 = new RuntimeException("Error 1");
		final Throwable exception2 = new IOException("Error 2");
		final Throwable exception3 = new IllegalStateException("Error 3");

		// Act & Assert
		assertDoesNotThrow(() -> handler.handleTransportError(session, exception1),
				"First call should not throw");
		assertDoesNotThrow(() -> handler.handleTransportError(session, exception2),
				"Second call should not throw");
		assertDoesNotThrow(() -> handler.handleTransportError(session, exception3),
				"Third call should not throw");
	}

	/**
	 * Test connection lifecycle: established -> closed.
	 */
	@Test
	void testConnectionLifecycle_EstablishedThenClosed_DoesNotThrow() throws Exception {
		// Arrange
		final ValuationHandler handler = new ValuationHandler();
		final TestWebSocketSession session = new TestWebSocketSession("session-1");
		final CloseStatus status = CloseStatus.NORMAL;

		// Act & Assert
		assertDoesNotThrow(() -> handler.afterConnectionEstablished(session),
				"Connection established should not throw");
		assertDoesNotThrow(() -> handler.afterConnectionClosed(session, status),
				"Connection closed should not throw");
	}

	/**
	 * Test connection lifecycle with error before close.
	 */
	@Test
	void testConnectionLifecycle_EstablishedErrorClosed_DoesNotThrow() throws Exception {
		// Arrange
		final ValuationHandler handler = new ValuationHandler();
		final TestWebSocketSession session = new TestWebSocketSession("session-1");
		final Throwable exception = new IOException("Connection error");
		final CloseStatus status = CloseStatus.SERVER_ERROR;

		// Act & Assert
		assertDoesNotThrow(() -> handler.afterConnectionEstablished(session),
				"Connection established should not throw");
		assertDoesNotThrow(() -> handler.handleTransportError(session, exception),
				"Handle error should not throw");
		assertDoesNotThrow(() -> handler.afterConnectionClosed(session, status),
				"Connection closed should not throw");
	}

	/**
	 * Test afterConnectionClosed with different status codes in sequence.
	 */
	@Test
	void testAfterConnectionClosed_WithVariousStatuses_DoesNotThrow() throws Exception {
		// Arrange
		final ValuationHandler handler = new ValuationHandler();
		final TestWebSocketSession session = new TestWebSocketSession("session-1");

		// Act & Assert - test various close status codes
		assertDoesNotThrow(() -> handler.afterConnectionClosed(session, CloseStatus.NORMAL),
				"Should handle NORMAL status");
		assertDoesNotThrow(() -> handler.afterConnectionClosed(session, CloseStatus.GOING_AWAY),
				"Should handle GOING_AWAY status");
		assertDoesNotThrow(() -> handler.afterConnectionClosed(session, CloseStatus.PROTOCOL_ERROR),
				"Should handle PROTOCOL_ERROR status");
		assertDoesNotThrow(() -> handler.afterConnectionClosed(session, CloseStatus.NOT_ACCEPTABLE),
				"Should handle NOT_ACCEPTABLE status");
		assertDoesNotThrow(() -> handler.afterConnectionClosed(session, CloseStatus.NO_STATUS_CODE),
				"Should handle NO_STATUS_CODE status");
		assertDoesNotThrow(() -> handler.afterConnectionClosed(session, CloseStatus.NO_CLOSE_FRAME),
				"Should handle NO_CLOSE_FRAME status");
		assertDoesNotThrow(() -> handler.afterConnectionClosed(session, CloseStatus.BAD_DATA),
				"Should handle BAD_DATA status");
		assertDoesNotThrow(() -> handler.afterConnectionClosed(session, CloseStatus.POLICY_VIOLATION),
				"Should handle POLICY_VIOLATION status");
		assertDoesNotThrow(() -> handler.afterConnectionClosed(session, CloseStatus.TOO_BIG_TO_PROCESS),
				"Should handle TOO_BIG_TO_PROCESS status");
		assertDoesNotThrow(() -> handler.afterConnectionClosed(session, CloseStatus.REQUIRED_EXTENSION),
				"Should handle REQUIRED_EXTENSION status");
		assertDoesNotThrow(() -> handler.afterConnectionClosed(session, CloseStatus.SERVER_ERROR),
				"Should handle SERVER_ERROR status");
		assertDoesNotThrow(() -> handler.afterConnectionClosed(session, CloseStatus.SERVICE_RESTARTED),
				"Should handle SERVICE_RESTARTED status");
	}
}
