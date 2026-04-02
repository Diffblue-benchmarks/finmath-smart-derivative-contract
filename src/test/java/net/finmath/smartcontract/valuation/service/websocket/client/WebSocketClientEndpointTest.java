package net.finmath.smartcontract.valuation.service.websocket.client;

import jakarta.websocket.RemoteEndpoint;
import jakarta.websocket.Session;
import net.finmath.smartcontract.model.SDCException;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WebSocketClientEndpointTest {

	@Test
	void testConstructorSetsBasicAuthHeader() throws Exception {
		URI uri = new URI("ws://localhost:8080/ws");
		WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "user", "password");

		Field configField = WebSocketClientEndpoint.class.getDeclaredField("config");
		configField.setAccessible(true);
		jakarta.websocket.ClientEndpointConfig config = (jakarta.websocket.ClientEndpointConfig) configField.get(endpoint);

		String authHeader = (String) config.getUserProperties().get("Authorization");
		String expectedEncoded = Base64.getEncoder().encodeToString("user:password".getBytes(StandardCharsets.UTF_8));
		assertEquals("Basic " + expectedEncoded, authHeader);
	}

	@Test
	void testGetUserSessionThrowsSDCExceptionOnConnectionFailure() throws Exception {
		URI uri = new URI("ws://localhost:0/nonexistent");
		WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "user", "pass");

		assertThrows(SDCException.class, () -> endpoint.getUserSession());
	}

	@Test
	void testGetUserSessionReturnsExistingSession() throws Exception {
		URI uri = new URI("ws://localhost:8080/ws");
		WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "user", "pass");

		Session mockSession = mock(Session.class);
		Field sessionField = WebSocketClientEndpoint.class.getDeclaredField("userSession");
		sessionField.setAccessible(true);
		sessionField.set(endpoint, mockSession);

		assertSame(mockSession, endpoint.getUserSession());
	}

	@Test
	void testAsObservableThrowsSDCExceptionOnConnectionFailure() throws Exception {
		URI uri = new URI("ws://localhost:0/nonexistent");
		WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "user", "pass");

		assertThrows(SDCException.class, () -> endpoint.asObservable());
	}

	@Test
	void testAsObservableReturnsObservable() throws Exception {
		URI uri = new URI("ws://localhost:8080/ws");
		WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "user", "pass");

		Session mockSession = mock(Session.class);
		Field sessionField = WebSocketClientEndpoint.class.getDeclaredField("userSession");
		sessionField.setAccessible(true);
		sessionField.set(endpoint, mockSession);

		assertNotNull(endpoint.asObservable());
	}

	@Test
	void testSendTextMessageThrowsSDCExceptionOnConnectionFailure() throws Exception {
		URI uri = new URI("ws://localhost:0/nonexistent");
		WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "user", "pass");

		assertThrows(SDCException.class, () -> endpoint.sendTextMessage("hello"));
	}

	@Test
	void testSendTextMessageWithExistingSession() throws Exception {
		URI uri = new URI("ws://localhost:8080/ws");
		WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "user", "pass");

		Session mockSession = mock(Session.class);
		RemoteEndpoint.Basic mockBasicRemote = mock(RemoteEndpoint.Basic.class);
		when(mockSession.getBasicRemote()).thenReturn(mockBasicRemote);

		Field sessionField = WebSocketClientEndpoint.class.getDeclaredField("userSession");
		sessionField.setAccessible(true);
		sessionField.set(endpoint, mockSession);

		endpoint.sendTextMessage("hello");
		verify(mockBasicRemote).sendText("hello");
	}

	@Test
	void testOnOpen() throws Exception {
		URI uri = new URI("ws://localhost:8080/ws");
		WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "user", "pass");

		Session mockSession = mock(Session.class);
		assertDoesNotThrow(() -> endpoint.onOpen(mockSession, null));
		verify(mockSession).addMessageHandler(any(jakarta.websocket.MessageHandler.Whole.class));
	}

	@Test
	void testOnClose() throws Exception {
		URI uri = new URI("ws://localhost:8080/ws");
		WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(uri, "user", "pass");

		assertDoesNotThrow(() -> endpoint.onClose(null, null));
	}
}
