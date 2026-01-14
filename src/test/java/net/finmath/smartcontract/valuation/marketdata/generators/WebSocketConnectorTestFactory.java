package net.finmath.smartcontract.valuation.marketdata.generators;

import com.diffblue.cover.annotations.InterestingTestFactory;

import java.util.Properties;

/**
 * Factory class to provide test instances for WebSocketConnector.
 */
public class WebSocketConnectorTestFactory {

    /**
     * Creates a WebSocketConnector instance with minimal valid configuration.
     * The configuration includes placeholder values that allow the class to be instantiated
     * without triggering network calls in the constructor.
     */
    @InterestingTestFactory
    public static WebSocketConnector createWebSocketConnector() {
        Properties properties = new Properties();

        // Authentication properties
        properties.setProperty("AUTHURL", "https://api.refinitiv.com/auth/oauth2/v1/token");
        properties.setProperty("CLIENTID", "test-client-id");
        properties.setProperty("USER", "test-user");
        properties.setProperty("PASSWORD", "test-password");

        // WebSocket connection properties
        properties.setProperty("HOSTNAME", "test-host.refinitiv.com");
        properties.setProperty("PORT", "443");

        // Proxy properties
        properties.setProperty("USEPROXY", "FALSE");
        properties.setProperty("PROXYHOST", "");
        properties.setProperty("PROXYPORT", "8080");
        properties.setProperty("PROXYUSER", "");
        properties.setProperty("PROXYPASS", "");

        return new WebSocketConnector(properties);
    }

    /**
     * Creates a WebSocketConnector instance with proxy configuration enabled.
     */
    @InterestingTestFactory
    public static WebSocketConnector createWebSocketConnectorWithProxy() {
        Properties properties = new Properties();

        // Authentication properties
        properties.setProperty("AUTHURL", "https://api.refinitiv.com/auth/oauth2/v1/token");
        properties.setProperty("CLIENTID", "test-client-id");
        properties.setProperty("USER", "test-user");
        properties.setProperty("PASSWORD", "test-password");

        // WebSocket connection properties
        properties.setProperty("HOSTNAME", "test-host.refinitiv.com");
        properties.setProperty("PORT", "443");

        // Proxy properties
        properties.setProperty("USEPROXY", "TRUE");
        properties.setProperty("PROXYHOST", "proxy.example.com");
        properties.setProperty("PROXYPORT", "8080");
        properties.setProperty("PROXYUSER", "proxy-user");
        properties.setProperty("PROXYPASS", "proxy-pass");

        return new WebSocketConnector(properties);
    }
}
