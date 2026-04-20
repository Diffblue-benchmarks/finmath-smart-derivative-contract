package net.finmath.smartcontract.valuation.marketdata.generators;

import com.neovisionaries.ws.client.WebSocket;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.Inet4Address;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class WebSocketConnectorTest {

	private Properties connectionProperties;

	@BeforeEach
	void setUp() {
		connectionProperties = new Properties();
		connectionProperties.setProperty("AUTHURL", "https://localhost:0/auth");
		connectionProperties.setProperty("HOSTNAME", "localhost");
		connectionProperties.setProperty("PORT", "443");
		connectionProperties.setProperty("USEPROXY", "FALSE");
		connectionProperties.setProperty("CLIENTID", "testClient");
		connectionProperties.setProperty("USER", "testUser");
		connectionProperties.setProperty("PASSWORD", "testPass");
	}

	@Test
	void testConstructor() throws Exception {
		WebSocketConnector connector = new WebSocketConnector(connectionProperties);
		String expectedPosition = Inet4Address.getLocalHost().getHostAddress();
		assertEquals(expectedPosition, connector.getPosition());
	}

	@Test
	void testGetAuthJsonReturnsNullBeforeInit() throws Exception {
		WebSocketConnector connector = new WebSocketConnector(connectionProperties);
		assertNull(connector.getAuthJson());
	}

	@Test
	void testGetPosition() throws Exception {
		WebSocketConnector connector = new WebSocketConnector(connectionProperties);
		assertNotNull(connector.getPosition());
		assertFalse(connector.getPosition().isEmpty());
	}

	@Test
	void testInitWebSocketConnection() throws Exception {
		WebSocketConnector connector = new WebSocketConnector(connectionProperties);
		WebSocket webSocket = connector.initWebSocketConnection();
		assertNotNull(webSocket);
		assertEquals("wss://localhost:443/WebSocket", connector.server);
	}

	@Test
	void testInitWebSocketConnectionWithProxy() throws Exception {
		connectionProperties.setProperty("USEPROXY", "TRUE");
		connectionProperties.setProperty("PROXYHOST", "proxy.example.com");
		connectionProperties.setProperty("PROXYPORT", "8080");
		connectionProperties.setProperty("PROXYUSER", "proxyUser");
		connectionProperties.setProperty("PROXYPASS", "proxyPass");
		WebSocketConnector connector = new WebSocketConnector(connectionProperties);
		WebSocket webSocket = connector.initWebSocketConnection();
		assertNotNull(webSocket);
	}

	@Test
	void testInitAuthJsonHandlesConnectionFailure() throws Exception {
		WebSocketConnector connector = new WebSocketConnector(connectionProperties);
		WebSocketConnector result = connector.initAuthJson();
		assertSame(connector, result);
		// authJson will be null since the auth URL is not reachable
		assertNull(connector.getAuthJson());
	}

	@Test
	void testGetAuthenticationInfoWithNullPreviousAuth() throws Exception {
		WebSocketConnector connector = new WebSocketConnector(connectionProperties);
		// Calling with unreachable URL returns null due to exception handling
		JSONObject result = connector.getAuthenticationInfo(null, "https://localhost:0/auth");
		assertNull(result);
	}

	@Test
	void testGetAuthenticationInfoWithPreviousAuth() throws Exception {
		WebSocketConnector connector = new WebSocketConnector(connectionProperties);
		JSONObject previousAuth = new JSONObject();
		previousAuth.put("refresh_token", "test_refresh_token");
		// Calling with unreachable URL returns null due to exception handling
		JSONObject result = connector.getAuthenticationInfo(previousAuth, "https://localhost:0/auth");
		assertNull(result);
	}
}
