package net.finmath.smartcontract.valuation.service.websocket.client;

import io.reactivex.rxjava3.core.Observable;
import jakarta.websocket.CloseReason;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.Session;
import net.finmath.smartcontract.model.SDCException;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WebSocketClientEndpointTest {

	private static final URI TEST_URI = URI.create("ws://localhost:8080/ws");

	@Test
	void testConstructor() {
		WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(TEST_URI, "user", "pass");
		assertNotNull(endpoint);
	}

	@Test
	void testGetBasicAuthHeaderViaConstructor() throws Exception {
		WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(TEST_URI, "testUser", "testPass");

		// Verify the auth header was set correctly by accessing the config via reflection
		var configField = WebSocketClientEndpoint.class.getDeclaredField("config");
		configField.setAccessible(true);
		var config = (jakarta.websocket.ClientEndpointConfig) configField.get(endpoint);

		String expectedAuth = "Basic " + new String(
				Base64.getEncoder().encode("testUser:testPass".getBytes(StandardCharsets.UTF_8)));
		assertEquals(expectedAuth, config.getUserProperties().get("Authorization"));
	}

	@Test
	void testOnOpen() {
		WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(TEST_URI, "user", "pass");
		Session mockSession = mock(Session.class);
		EndpointConfig mockConfig = mock(EndpointConfig.class);

		// Should not throw
		assertDoesNotThrow(() -> endpoint.onOpen(mockSession, mockConfig));
		verify(mockSession).addMessageHandler(any(jakarta.websocket.MessageHandler.Whole.class));
	}

	@Test
	void testOnClose() {
		WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(TEST_URI, "user", "pass");
		Session mockSession = mock(Session.class);
		CloseReason closeReason = new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "done");

		// Should not throw
		assertDoesNotThrow(() -> endpoint.onClose(mockSession, closeReason));
	}

	@Test
	void testAsObservableReturnsObservable() {
		WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(TEST_URI, "user", "pass");

		// initSession will fail since no WebSocket container is available in test,
		// which should throw SDCException
		assertThrows(SDCException.class, () -> endpoint.asObservable());
	}

	@Test
	void testGetUserSessionThrowsWhenNoContainer() {
		WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(TEST_URI, "user", "pass");

		// initSession will fail since no WebSocket container is available in test
		assertThrows(SDCException.class, () -> endpoint.getUserSession());
	}

	@Test
	void testSendTextMessageThrowsWhenNoContainer() {
		WebSocketClientEndpoint endpoint = new WebSocketClientEndpoint(TEST_URI, "user", "pass");

		// initSession will fail since no WebSocket container is available in test
		assertThrows(SDCException.class, () -> endpoint.sendTextMessage("hello"));
	}
}
