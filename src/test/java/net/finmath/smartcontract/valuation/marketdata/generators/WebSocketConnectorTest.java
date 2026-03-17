package net.finmath.smartcontract.valuation.marketdata.generators;

import com.neovisionaries.ws.client.WebSocket;
import org.json.JSONObject;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for WebSocketConnector
 */
class WebSocketConnectorTest {

	@Test
	void testConstructorInitializesProperties() throws Exception {
		// Given
		Properties properties = new Properties();
		properties.setProperty("AUTHURL", "https://api.refinitiv.com/auth/oauth2/v1/token");
		properties.setProperty("HOSTNAME", "localhost");
		properties.setProperty("PORT", "443");
		properties.setProperty("CLIENTID", "testClient");
		properties.setProperty("USER", "testUser");
		properties.setProperty("PASSWORD", "testPassword");
		properties.setProperty("USEPROXY", "FALSE");

		// When
		WebSocketConnector connector = new WebSocketConnector(properties);

		// Then
		assertNotNull(connector);
		assertNotNull(connector.getPosition());
		assertFalse(connector.getPosition().isEmpty());
	}

	@Test
	void testGetPositionReturnsHostAddress() throws Exception {
		// Given
		Properties properties = new Properties();
		properties.setProperty("AUTHURL", "https://api.refinitiv.com/auth/oauth2/v1/token");
		properties.setProperty("HOSTNAME", "localhost");
		properties.setProperty("PORT", "443");
		properties.setProperty("CLIENTID", "testClient");
		properties.setProperty("USER", "testUser");
		properties.setProperty("PASSWORD", "testPassword");
		properties.setProperty("USEPROXY", "FALSE");

		// When
		WebSocketConnector connector = new WebSocketConnector(properties);

		// Then
		String position = connector.getPosition();
		assertNotNull(position);
		assertFalse(position.isEmpty());
	}

	@Test
	void testGetAuthJsonReturnsNull() throws Exception {
		// Given
		Properties properties = new Properties();
		properties.setProperty("AUTHURL", "https://api.refinitiv.com/auth/oauth2/v1/token");
		properties.setProperty("HOSTNAME", "localhost");
		properties.setProperty("PORT", "443");
		properties.setProperty("CLIENTID", "testClient");
		properties.setProperty("USER", "testUser");
		properties.setProperty("PASSWORD", "testPassword");
		properties.setProperty("USEPROXY", "FALSE");

		// When
		WebSocketConnector connector = new WebSocketConnector(properties);

		// Then
		JSONObject authJson = connector.getAuthJson();
		assertNull(authJson);
	}

	@Test
	void testInitAuthJsonHandlesException() throws Exception {
		// Given
		Properties properties = new Properties();
		properties.setProperty("AUTHURL", "https://invalid.url.that.does.not.exist.example.com/auth");
		properties.setProperty("HOSTNAME", "localhost");
		properties.setProperty("PORT", "443");
		properties.setProperty("CLIENTID", "testClient");
		properties.setProperty("USER", "testUser");
		properties.setProperty("PASSWORD", "testPassword");
		properties.setProperty("USEPROXY", "FALSE");

		WebSocketConnector connector = new WebSocketConnector(properties);

		// When
		WebSocketConnector result = connector.initAuthJson();

		// Then
		assertNotNull(result);
		assertSame(connector, result);
		assertNull(connector.getAuthJson());
	}

	@Test
	void testGetAuthenticationInfoHandlesInvalidUrl() throws Exception {
		// Given
		Properties properties = new Properties();
		properties.setProperty("AUTHURL", "https://invalid.url.that.does.not.exist.example.com/auth");
		properties.setProperty("HOSTNAME", "localhost");
		properties.setProperty("PORT", "443");
		properties.setProperty("CLIENTID", "testClient");
		properties.setProperty("USER", "testUser");
		properties.setProperty("PASSWORD", "testPassword");
		properties.setProperty("USEPROXY", "FALSE");

		WebSocketConnector connector = new WebSocketConnector(properties);

		// When
		JSONObject authInfo = connector.getAuthenticationInfo(null, "https://invalid.url.that.does.not.exist.example.com/auth");

		// Then
		assertNull(authInfo);
	}
}
