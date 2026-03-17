package net.finmath.smartcontract.valuation.service.websocket.client;

import io.reactivex.rxjava3.core.Observable;
import jakarta.websocket.CloseReason;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.Session;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

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
}
