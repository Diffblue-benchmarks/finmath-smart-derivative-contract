package net.finmath.smartcontract.valuation.service.websocket.client;

import io.reactivex.rxjava3.core.Observable;
import jakarta.websocket.CloseReason;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.MessageHandler;
import jakarta.websocket.Session;
import net.finmath.smartcontract.model.SDCException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for WebSocketClientEndpoint
 */
class WebSocketClientEndpointTest {

	@Test
	void testConstructor() throws Exception {
		// Given
		final URI endpointURI = new URI("ws://localhost:8080/test");
		final String user = "testuser";
		final String password = "testpass";

		// When
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(endpointURI, user, password);

		// Then
		assertNotNull(endpoint);
	}

	@Test
	void testGetBasicAuthHeader() throws Exception {
		// Given
		final String username = "testuser";
		final String password = "testpass";
		final String expectedAuth = username + ":" + password;
		final byte[] encodedAuth = Base64.getEncoder().encode(expectedAuth.getBytes(StandardCharsets.UTF_8));
		final String expectedHeader = "Basic " + new String(encodedAuth);

		// When
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(new URI("ws://localhost:8080/test"), username, password);
		final Method method = WebSocketClientEndpoint.class.getDeclaredMethod("getBasicAuthHeader", String.class, String.class);
		method.setAccessible(true);
		final String result = (String) method.invoke(endpoint, username, password);

		// Then
		assertEquals(expectedHeader, result);
	}

	@Test
	void testOnClose() throws Exception {
		// Given
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(new URI("ws://localhost:8080/test"), "user", "pass");
		final CloseReason closeReason = new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Test close");

		// When
		endpoint.onClose(null, closeReason);

		// Then - verify no exceptions thrown
		assertNotNull(endpoint);
	}

	@Test
	void testOnOpen() throws Exception {
		// Given
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(new URI("ws://localhost:8080/test"), "user", "pass");
		final StubSession session = new StubSession();
		final StubEndpointConfig config = new StubEndpointConfig();

		// When
		endpoint.onOpen(session, config);

		// Then
		assertEquals(1, session.getStoredMessageHandlers().size());
		assertTrue(session.getStoredMessageHandlers().get(0) instanceof MessageHandler.Whole);
	}

	@Test
	void testSendTextMessage() throws Exception {
		// Given
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(new URI("ws://localhost:8080/test"), "user", "pass");
		final StubSessionWithRemote session = new StubSessionWithRemote();
		final java.lang.reflect.Field sessionField = WebSocketClientEndpoint.class.getDeclaredField("userSession");
		sessionField.setAccessible(true);
		sessionField.set(endpoint, session);
		final String testMessage = "test message";

		// When
		endpoint.sendTextMessage(testMessage);

		// Then
		assertEquals(testMessage, ((StubBasicRemote) session.getBasicRemote()).getSentText());
	}

	/**
	 * Stub implementation of Session for testing
	 */
	private static class StubSession implements Session {
		private final List<MessageHandler> storedMessageHandlers = new ArrayList<>();

		@Override
		public void addMessageHandler(MessageHandler handler) {
			storedMessageHandlers.add(handler);
		}

		public List<MessageHandler> getStoredMessageHandlers() {
			return storedMessageHandlers;
		}

		// Minimal implementation of other required methods
		@Override public <T> void addMessageHandler(Class<T> clazz, MessageHandler.Partial<T> handler) {}
		@Override public <T> void addMessageHandler(Class<T> clazz, MessageHandler.Whole<T> handler) {}
		@Override public void close() {}
		@Override public void close(CloseReason closeReason) {}
		@Override public jakarta.websocket.RemoteEndpoint.Async getAsyncRemote() { return null; }
		@Override public jakarta.websocket.RemoteEndpoint.Basic getBasicRemote() { return null; }
		@Override public String getId() { return null; }
		@Override public int getMaxBinaryMessageBufferSize() { return 0; }
		@Override public long getMaxIdleTimeout() { return 0; }
		@Override public int getMaxTextMessageBufferSize() { return 0; }
		@Override public java.util.Set<MessageHandler> getMessageHandlers() { return new java.util.HashSet<>(storedMessageHandlers); }
		@Override public java.util.List<jakarta.websocket.Extension> getNegotiatedExtensions() { return null; }
		@Override public String getNegotiatedSubprotocol() { return null; }
		@Override public java.util.Set<Session> getOpenSessions() { return null; }
		@Override public java.util.Map<String, String> getPathParameters() { return null; }
		@Override public String getProtocolVersion() { return null; }
		@Override public String getQueryString() { return null; }
		@Override public java.util.Map<String, java.util.List<String>> getRequestParameterMap() { return null; }
		@Override public java.net.URI getRequestURI() { return null; }
		@Override public java.security.Principal getUserPrincipal() { return null; }
		@Override public java.util.Map<String, Object> getUserProperties() { return null; }
		@Override public boolean isOpen() { return false; }
		@Override public boolean isSecure() { return false; }
		@Override public void removeMessageHandler(MessageHandler handler) {}
		@Override public void setMaxBinaryMessageBufferSize(int length) {}
		@Override public void setMaxIdleTimeout(long milliseconds) {}
		@Override public void setMaxTextMessageBufferSize(int length) {}
		@Override public jakarta.websocket.WebSocketContainer getContainer() { return null; }
	}

	/**
	 * Stub implementation of Session with BasicRemote for testing sendTextMessage
	 */
	private static class StubSessionWithRemote extends StubSession {
		private final StubBasicRemote basicRemote = new StubBasicRemote();

		@Override
		public jakarta.websocket.RemoteEndpoint.Basic getBasicRemote() {
			return basicRemote;
		}
	}

	/**
	 * Stub implementation of RemoteEndpoint.Basic for testing
	 */
	private static class StubBasicRemote implements jakarta.websocket.RemoteEndpoint.Basic {
		private String sentText;

		@Override
		public void sendText(String text) {
			this.sentText = text;
		}

		public String getSentText() {
			return sentText;
		}

		// Minimal implementation of other required methods
		@Override public void setBatchingAllowed(boolean allowed) {}
		@Override public boolean getBatchingAllowed() { return false; }
		@Override public void flushBatch() {}
		@Override public void sendPing(java.nio.ByteBuffer applicationData) {}
		@Override public void sendPong(java.nio.ByteBuffer applicationData) {}
		@Override public void sendText(String partialMessage, boolean isLast) {}
		@Override public void sendBinary(java.nio.ByteBuffer data) {}
		@Override public void sendBinary(java.nio.ByteBuffer partialByte, boolean isLast) {}
		@Override public java.io.OutputStream getSendStream() { return null; }
		@Override public java.io.Writer getSendWriter() { return null; }
		@Override public void sendObject(Object data) {}
	}

	/**
	 * Stub implementation of EndpointConfig for testing
	 */
	private static class StubEndpointConfig implements EndpointConfig {
		@Override public java.util.List<Class<? extends jakarta.websocket.Encoder>> getEncoders() { return null; }
		@Override public java.util.List<Class<? extends jakarta.websocket.Decoder>> getDecoders() { return null; }
		@Override public java.util.Map<String, Object> getUserProperties() { return null; }
	}

	@Test
	void testGetUserSessionTriggersInitSession() throws Exception {
		// Given
		final URI endpointURI = new URI("ws://localhost:9999/nonexistent");
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(endpointURI, "user", "pass");

		// When/Then
		assertThrows(SDCException.class, () -> endpoint.getUserSession());
	}

	@Test
	void testAsObservableTriggersInitSession() throws Exception {
		// Given
		final URI endpointURI = new URI("ws://localhost:9999/nonexistent");
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(endpointURI, "user", "pass");

		// When/Then
		assertThrows(SDCException.class, () -> endpoint.asObservable());
	}

	@Test
	void testSendTextMessageTriggersInitSession() throws Exception {
		// Given
		final URI endpointURI = new URI("ws://localhost:9999/nonexistent");
		final WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(endpointURI, "user", "pass");

		// When/Then
		assertThrows(SDCException.class, () -> endpoint.sendTextMessage("test"));
	}
}
