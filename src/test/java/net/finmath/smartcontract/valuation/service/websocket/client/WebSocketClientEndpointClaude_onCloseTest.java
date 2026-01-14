/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */

package net.finmath.smartcontract.valuation.service.websocket.client;

import io.reactivex.rxjava3.observers.TestObserver;
import jakarta.websocket.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for WebSocketClientEndpoint.onClose(Session, CloseReason) method.
 * Tests the onClose() method with focus on branch and condition coverage.
 *
 * Testing approach:
 * - The onClose() method is a callback invoked by the WebSocket container when a connection is closed
 * - It logs a message, completes the messageSubject Observable, and sets userSession to null
 * - Since this is a callback method that requires Session and CloseReason objects,
 *   we create minimal test implementations of these interfaces to test the method behavior
 * - We use reflection to verify that userSession is set to null, as this is an internal state
 *   change that cannot be verified through the public API
 * - We verify Observable completion by subscribing a TestObserver before calling onClose
 * - This approach uses minimal test doubles rather than full mocking frameworks
 *
 * @author Claude Code
 */
class WebSocketClientEndpointClaude_onCloseTest {

	/**
	 * Simple test implementation of Session interface for testing purposes.
	 */
	private static class TestSession implements Session {
		private final String id;
		private boolean open = true;

		public TestSession(String id) {
			this.id = id;
		}

		@Override
		public WebSocketContainer getContainer() {
			return null;
		}

		@Override
		public void addMessageHandler(MessageHandler handler) throws IllegalStateException {
		}

		@Override
		public <T> void addMessageHandler(Class<T> clazz, MessageHandler.Whole<T> handler) throws IllegalStateException {
		}

		@Override
		public <T> void addMessageHandler(Class<T> clazz, MessageHandler.Partial<T> handler) throws IllegalStateException {
		}

		@Override
		public Set<MessageHandler> getMessageHandlers() {
			return Collections.emptySet();
		}

		@Override
		public void removeMessageHandler(MessageHandler handler) {
		}

		@Override
		public String getProtocolVersion() {
			return "13";
		}

		@Override
		public String getNegotiatedSubprotocol() {
			return "";
		}

		@Override
		public List<Extension> getNegotiatedExtensions() {
			return Collections.emptyList();
		}

		@Override
		public boolean isSecure() {
			return false;
		}

		@Override
		public boolean isOpen() {
			return open;
		}

		@Override
		public long getMaxIdleTimeout() {
			return 0;
		}

		@Override
		public void setMaxIdleTimeout(long milliseconds) {
		}

		@Override
		public void setMaxBinaryMessageBufferSize(int length) {
		}

		@Override
		public int getMaxBinaryMessageBufferSize() {
			return 0;
		}

		@Override
		public void setMaxTextMessageBufferSize(int length) {
		}

		@Override
		public int getMaxTextMessageBufferSize() {
			return 0;
		}

		@Override
		public RemoteEndpoint.Async getAsyncRemote() {
			return null;
		}

		@Override
		public RemoteEndpoint.Basic getBasicRemote() {
			return null;
		}

		@Override
		public String getId() {
			return id;
		}

		@Override
		public void close() throws IOException {
			open = false;
		}

		@Override
		public void close(CloseReason closeReason) throws IOException {
			open = false;
		}

		@Override
		public URI getRequestURI() {
			try {
				return new URI("ws://localhost:8080/test");
			} catch (URISyntaxException e) {
				return null;
			}
		}

		@Override
		public Map<String, List<String>> getRequestParameterMap() {
			return Collections.emptyMap();
		}

		@Override
		public String getQueryString() {
			return "";
		}

		@Override
		public Map<String, String> getPathParameters() {
			return Collections.emptyMap();
		}

		@Override
		public Map<String, Object> getUserProperties() {
			return new HashMap<>();
		}

		@Override
		public Principal getUserPrincipal() {
			return null;
		}

		@Override
		public Set<Session> getOpenSessions() {
			return Collections.emptySet();
		}
	}

	/**
	 * Simple test implementation of CloseReason.
	 */
	private static CloseReason createCloseReason(CloseReason.CloseCodes code, String reasonPhrase) {
		return new CloseReason(code, reasonPhrase);
	}

	/**
	 * Helper method to get the userSession field value using reflection.
	 * Reflection is used here because there is no other way to verify that the internal
	 * userSession field is set to null by onClose(). This is necessary to test the
	 * method's contract of cleaning up internal state.
	 */
	private Session getUserSessionViaReflection(WebSocketClientEndpoint endpoint) throws Exception {
		final Field field = WebSocketClientEndpoint.class.getDeclaredField("userSession");
		field.setAccessible(true);
		return (Session) field.get(endpoint);
	}

	/**
	 * Test onClose() with normal close reason.
	 * This is the basic happy path test.
	 */
	@Test
	void testOnClose_WithNormalCloseReason() throws URISyntaxException {
		// Arrange
		final URI uri = new URI("ws://localhost:8080/test");
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "user", "password");
		final TestSession session = new TestSession("test-session-1");
		final CloseReason reason = createCloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Normal closure");

		// Act - should not throw any exception
		assertDoesNotThrow(() -> endpoint.onClose(session, reason),
				"onClose should not throw exception with normal close reason");
	}

	/**
	 * Test onClose() completes the Observable.
	 * We subscribe a TestObserver before closing to verify completion.
	 */
	@Test
	void testOnClose_CompletesObservable() throws URISyntaxException {
		// Arrange
		final URI uri = new URI("ws://localhost:8080/test");
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "user", "password");

		// Subscribe to the observable (note: this won't call initSession because we access it directly)
		final TestObserver<String> testObserver = new TestObserver<>();

		// We need to get the observable without triggering initSession
		// Create a new endpoint and use reflection to access messageSubject
		try {
			final Field field = WebSocketClientEndpoint.class.getDeclaredField("messageSubject");
			field.setAccessible(true);
			final io.reactivex.rxjava3.subjects.PublishSubject<String> subject =
				(io.reactivex.rxjava3.subjects.PublishSubject<String>) field.get(endpoint);
			subject.subscribe(testObserver);
		} catch (Exception e) {
			fail("Failed to access messageSubject: " + e.getMessage());
		}

		final TestSession session = new TestSession("test-session-1");
		final CloseReason reason = createCloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Normal closure");

		// Act
		endpoint.onClose(session, reason);

		// Assert - verify the observable completed
		testObserver.assertComplete();
		testObserver.assertNoErrors();
	}

	/**
	 * Test onClose() sets userSession to null.
	 * We use reflection to verify this internal state change.
	 *
	 * Reflection is necessary here because there is no public API to verify that
	 * userSession has been set to null. This is an important part of the method's
	 * contract - cleaning up internal state when the connection is closed.
	 */
	@Test
	void testOnClose_SetsUserSessionToNull() throws Exception {
		// Arrange
		final URI uri = new URI("ws://localhost:8080/test");
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "user", "password");
		final TestSession session = new TestSession("test-session-1");
		final CloseReason reason = createCloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Normal closure");

		// First, set userSession using reflection to simulate an open connection
		final Field field = WebSocketClientEndpoint.class.getDeclaredField("userSession");
		field.setAccessible(true);
		field.set(endpoint, session);

		// Verify it's set
		assertNotNull(getUserSessionViaReflection(endpoint), "userSession should be set before onClose");

		// Act
		endpoint.onClose(session, reason);

		// Assert - verify userSession is now null
		assertNull(getUserSessionViaReflection(endpoint), "userSession should be null after onClose");
	}

	/**
	 * Test onClose() with GOING_AWAY close code.
	 */
	@Test
	void testOnClose_WithGoingAwayReason() throws URISyntaxException {
		// Arrange
		final URI uri = new URI("ws://localhost:8080/test");
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "user", "password");
		final TestSession session = new TestSession("test-session-1");
		final CloseReason reason = createCloseReason(CloseReason.CloseCodes.GOING_AWAY, "Server going away");

		// Act & Assert
		assertDoesNotThrow(() -> endpoint.onClose(session, reason),
				"onClose should handle GOING_AWAY reason");
	}

	/**
	 * Test onClose() with PROTOCOL_ERROR close code.
	 */
	@Test
	void testOnClose_WithProtocolErrorReason() throws URISyntaxException {
		// Arrange
		final URI uri = new URI("ws://localhost:8080/test");
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "user", "password");
		final TestSession session = new TestSession("test-session-1");
		final CloseReason reason = createCloseReason(CloseReason.CloseCodes.PROTOCOL_ERROR, "Protocol error");

		// Act & Assert
		assertDoesNotThrow(() -> endpoint.onClose(session, reason),
				"onClose should handle PROTOCOL_ERROR reason");
	}

	/**
	 * Test onClose() with CANNOT_ACCEPT close code.
	 */
	@Test
	void testOnClose_WithCannotAcceptReason() throws URISyntaxException {
		// Arrange
		final URI uri = new URI("ws://localhost:8080/test");
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "user", "password");
		final TestSession session = new TestSession("test-session-1");
		final CloseReason reason = createCloseReason(CloseReason.CloseCodes.CANNOT_ACCEPT, "Cannot accept");

		// Act & Assert
		assertDoesNotThrow(() -> endpoint.onClose(session, reason),
				"onClose should handle CANNOT_ACCEPT reason");
	}

	/**
	 * Test onClose() with TOO_BIG close code.
	 */
	@Test
	void testOnClose_WithTooBigReason() throws URISyntaxException {
		// Arrange
		final URI uri = new URI("ws://localhost:8080/test");
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "user", "password");
		final TestSession session = new TestSession("test-session-1");
		final CloseReason reason = createCloseReason(CloseReason.CloseCodes.TOO_BIG, "Message too big");

		// Act & Assert
		assertDoesNotThrow(() -> endpoint.onClose(session, reason),
				"onClose should handle TOO_BIG reason");
	}

	/**
	 * Test onClose() with empty reason phrase.
	 */
	@Test
	void testOnClose_WithEmptyReasonPhrase() throws URISyntaxException {
		// Arrange
		final URI uri = new URI("ws://localhost:8080/test");
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "user", "password");
		final TestSession session = new TestSession("test-session-1");
		final CloseReason reason = createCloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "");

		// Act & Assert
		assertDoesNotThrow(() -> endpoint.onClose(session, reason),
				"onClose should handle empty reason phrase");
	}

	/**
	 * Test onClose() called on endpoint with null URI.
	 * The method should still work as it doesn't use the URI.
	 */
	@Test
	void testOnClose_WithNullURI() {
		// Arrange
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(null, "user", "password");
		final TestSession session = new TestSession("test-session-1");
		final CloseReason reason = createCloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Normal closure");

		// Act & Assert
		assertDoesNotThrow(() -> endpoint.onClose(session, reason),
				"onClose should work even with null URI");
	}

	/**
	 * Test onClose() called on endpoint with null username.
	 */
	@Test
	void testOnClose_WithNullUsername() throws URISyntaxException {
		// Arrange
		final URI uri = new URI("ws://localhost:8080/test");
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, null, "password");
		final TestSession session = new TestSession("test-session-1");
		final CloseReason reason = createCloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Normal closure");

		// Act & Assert
		assertDoesNotThrow(() -> endpoint.onClose(session, reason),
				"onClose should work even with null username");
	}

	/**
	 * Test onClose() called on endpoint with null password.
	 */
	@Test
	void testOnClose_WithNullPassword() throws URISyntaxException {
		// Arrange
		final URI uri = new URI("ws://localhost:8080/test");
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "user", null);
		final TestSession session = new TestSession("test-session-1");
		final CloseReason reason = createCloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Normal closure");

		// Act & Assert
		assertDoesNotThrow(() -> endpoint.onClose(session, reason),
				"onClose should work even with null password");
	}

	/**
	 * Test onClose() called multiple times on the same endpoint.
	 * Each call should be handled gracefully.
	 */
	@Test
	void testOnClose_CalledMultipleTimes() throws URISyntaxException {
		// Arrange
		final URI uri = new URI("ws://localhost:8080/test");
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "user", "password");
		final TestSession session1 = new TestSession("test-session-1");
		final TestSession session2 = new TestSession("test-session-2");
		final TestSession session3 = new TestSession("test-session-3");
		final CloseReason reason = createCloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Normal closure");

		// Act - call onClose multiple times
		assertDoesNotThrow(() -> endpoint.onClose(session1, reason),
				"First onClose should not throw");
		assertDoesNotThrow(() -> endpoint.onClose(session2, reason),
				"Second onClose should not throw");
		assertDoesNotThrow(() -> endpoint.onClose(session3, reason),
				"Third onClose should not throw");
	}

	/**
	 * Test onClose() on multiple different endpoints.
	 * Each endpoint should maintain its own state.
	 */
	@Test
	void testOnClose_MultipleEndpoints() throws URISyntaxException {
		// Arrange
		final WebSocketClientEndpoint endpoint1 = new WebSocketClientEndpoint(
				new URI("ws://localhost:8080/test1"), "user1", "pass1");
		final WebSocketClientEndpoint endpoint2 = new WebSocketClientEndpoint(
				new URI("ws://localhost:8081/test2"), "user2", "pass2");
		final TestSession session1 = new TestSession("test-session-1");
		final TestSession session2 = new TestSession("test-session-2");
		final CloseReason reason = createCloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Normal closure");

		// Act & Assert
		assertDoesNotThrow(() -> endpoint1.onClose(session1, reason),
				"First endpoint onClose should not throw");
		assertDoesNotThrow(() -> endpoint2.onClose(session2, reason),
				"Second endpoint onClose should not throw");
	}

	/**
	 * Test that onClose() can be called after onOpen().
	 * This simulates a normal connection lifecycle.
	 */
	@Test
	void testOnClose_AfterOnOpen() throws URISyntaxException {
		// Arrange
		final URI uri = new URI("ws://localhost:8080/test");
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "user", "password");
		final TestSession session = new TestSession("test-session-1");
		final TestEndpointConfig config = new TestEndpointConfig();
		final CloseReason reason = createCloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Normal closure");

		// Act - simulate connection lifecycle
		assertDoesNotThrow(() -> endpoint.onOpen(session, config),
				"onOpen should not throw");
		assertDoesNotThrow(() -> endpoint.onClose(session, reason),
				"onClose should not throw after onOpen");
	}

	/**
	 * Test onClose() with very long reason phrase.
	 */
	@Test
	void testOnClose_WithLongReasonPhrase() throws URISyntaxException {
		// Arrange
		final URI uri = new URI("ws://localhost:8080/test");
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "user", "password");
		final TestSession session = new TestSession("test-session-1");
		final String longReason = "x".repeat(1000);
		final CloseReason reason = createCloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, longReason);

		// Act & Assert
		assertDoesNotThrow(() -> endpoint.onClose(session, reason),
				"onClose should handle long reason phrase");
	}

	/**
	 * Test onClose() with special characters in reason phrase.
	 */
	@Test
	void testOnClose_WithSpecialCharactersInReason() throws URISyntaxException {
		// Arrange
		final URI uri = new URI("ws://localhost:8080/test");
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "user", "password");
		final TestSession session = new TestSession("test-session-1");
		final CloseReason reason = createCloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE,
				"Closing with special chars: !@#$%^&*()");

		// Act & Assert
		assertDoesNotThrow(() -> endpoint.onClose(session, reason),
				"onClose should handle special characters in reason");
	}

	/**
	 * Test onClose() with Unicode characters in reason phrase.
	 */
	@Test
	void testOnClose_WithUnicodeInReason() throws URISyntaxException {
		// Arrange
		final URI uri = new URI("ws://localhost:8080/test");
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "user", "password");
		final TestSession session = new TestSession("test-session-1");
		final CloseReason reason = createCloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE,
				"Closing: 世界 мир");

		// Act & Assert
		assertDoesNotThrow(() -> endpoint.onClose(session, reason),
				"onClose should handle Unicode characters in reason");
	}

	/**
	 * Test that Observable only completes once even with multiple onClose calls.
	 */
	@Test
	void testOnClose_ObservableCompletesOnlyOnce() throws URISyntaxException {
		// Arrange
		final URI uri = new URI("ws://localhost:8080/test");
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "user", "password");

		final TestObserver<String> testObserver = new TestObserver<>();

		// Access messageSubject via reflection
		try {
			final Field field = WebSocketClientEndpoint.class.getDeclaredField("messageSubject");
			field.setAccessible(true);
			final io.reactivex.rxjava3.subjects.PublishSubject<String> subject =
				(io.reactivex.rxjava3.subjects.PublishSubject<String>) field.get(endpoint);
			subject.subscribe(testObserver);
		} catch (Exception e) {
			fail("Failed to access messageSubject: " + e.getMessage());
		}

		final TestSession session1 = new TestSession("test-session-1");
		final TestSession session2 = new TestSession("test-session-2");
		final CloseReason reason = createCloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Normal closure");

		// Act - call onClose multiple times
		endpoint.onClose(session1, reason);
		endpoint.onClose(session2, reason);

		// Assert - observable should be completed
		testObserver.assertComplete();
		testObserver.assertNoErrors();
	}

	/**
	 * Simple test implementation of EndpointConfig interface.
	 * Reused from onOpen tests for lifecycle testing.
	 */
	private static class TestEndpointConfig implements EndpointConfig {
		private final Map<String, Object> userProperties = new HashMap<>();

		@Override
		public List<Class<? extends Encoder>> getEncoders() {
			return Collections.emptyList();
		}

		@Override
		public List<Class<? extends Decoder>> getDecoders() {
			return Collections.emptyList();
		}

		@Override
		public Map<String, Object> getUserProperties() {
			return userProperties;
		}
	}
}
