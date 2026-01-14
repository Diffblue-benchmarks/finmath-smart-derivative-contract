package net.finmath.smartcontract.valuation.marketdata.generators;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.Properties;

/**
 * Test configuration to provide beans required for testing WebSocketConnector.
 * This configuration addresses Diffblue Cover issue R027 by providing the Properties bean
 * that WebSocketConnector requires as a constructor parameter.
 */
@TestConfiguration
public class WebSocketConnectorTestConfiguration {

	/**
	 * Provides a Properties bean with mock connection properties for testing.
	 * This allows Spring to inject Properties into WebSocketConnector during test execution.
	 *
	 * @return A Properties object with minimal configuration for testing
	 */
	@Bean
	public Properties connectionProperties() {
		Properties properties = new Properties();
		// Set minimal properties required by WebSocketConnector
		properties.setProperty("AUTHURL", "https://test.example.com/auth");
		properties.setProperty("HOSTNAME", "test.example.com");
		properties.setProperty("PORT", "443");
		properties.setProperty("USEPROXY", "FALSE");
		properties.setProperty("CLIENTID", "test-client-id");
		properties.setProperty("USER", "test-user");
		properties.setProperty("PASSWORD", "test-password");
		properties.setProperty("PROXYHOST", "");
		properties.setProperty("PROXYPORT", "");
		properties.setProperty("PROXYUSER", "");
		properties.setProperty("PROXYPASS", "");
		return properties;
	}
}
