package net.finmath.smartcontract.valuation.service.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for RefinitivConfig configuration class.
 */
class RefinitivConfigTest {

    private RefinitivConfig config;

    @BeforeEach
    void setUp() {
        config = new RefinitivConfig();
    }

    @Test
    void testDefaultConstructor() {
        assertNotNull(config);
    }

    @Test
    void testSetAndGetUser() {
        String user = "testuser";
        config.setUser(user);
        assertEquals(user, config.getUser());
    }

    @Test
    void testSetAndGetPassword() {
        String password = "testpassword";
        config.setPassword(password);
        assertEquals(password, config.getPassword());
    }

    @Test
    void testSetAndGetClientId() {
        String clientId = "client-123";
        config.setClientId(clientId);
        assertEquals(clientId, config.getClientId());
    }

    @Test
    void testSetAndGetHostName() {
        String hostName = "api.refinitiv.com";
        config.setHostName(hostName);
        assertEquals(hostName, config.getHostName());
    }

    @Test
    void testSetAndGetPort() {
        int port = 443;
        config.setPort(port);
        assertEquals(port, config.getPort());
    }

    @Test
    void testSetAndGetAuthUrl() {
        String authUrl = "https://auth.refinitiv.com";
        config.setAuthUrl(authUrl);
        assertEquals(authUrl, config.getAuthUrl());
    }

    @Test
    void testSetAndGetUseProxy() {
        String useProxy = "true";
        config.setUseProxy(useProxy);
        assertEquals(useProxy, config.getUseProxy());
    }

    @Test
    void testSetAndGetProxyHost() {
        String proxyHost = "proxy.example.com";
        config.setProxyHost(proxyHost);
        assertEquals(proxyHost, config.getProxyHost());
    }

    @Test
    void testSetAndGetProxyPort() {
        int proxyPort = 8080;
        config.setProxyPort(proxyPort);
        assertEquals(proxyPort, config.getProxyPort());
    }

    @Test
    void testSetAndGetProxyUser() {
        String proxyUser = "proxyuser";
        config.setProxyUser(proxyUser);
        assertEquals(proxyUser, config.getProxyUser());
    }

    @Test
    void testSetAndGetProxyPassword() {
        String proxyPassword = "proxypass";
        config.setProxyPassword(proxyPassword);
        assertEquals(proxyPassword, config.getProxyPassword());
    }

    @Test
    void testAllPropertiesSet() {
        config.setUser("user");
        config.setPassword("pass");
        config.setClientId("client");
        config.setHostName("host");
        config.setPort(443);
        config.setAuthUrl("auth");
        config.setUseProxy("true");
        config.setProxyHost("proxyhost");
        config.setProxyPort(8080);
        config.setProxyUser("proxyuser");
        config.setProxyPassword("proxypass");

        assertEquals("user", config.getUser());
        assertEquals("pass", config.getPassword());
        assertEquals("client", config.getClientId());
        assertEquals("host", config.getHostName());
        assertEquals(443, config.getPort());
        assertEquals("auth", config.getAuthUrl());
        assertEquals("true", config.getUseProxy());
        assertEquals("proxyhost", config.getProxyHost());
        assertEquals(8080, config.getProxyPort());
        assertEquals("proxyuser", config.getProxyUser());
        assertEquals("proxypass", config.getProxyPassword());
    }

    @Test
    void testNullValues() {
        config.setUser(null);
        config.setPassword(null);
        config.setClientId(null);

        assertNull(config.getUser());
        assertNull(config.getPassword());
        assertNull(config.getClientId());
    }

    @Test
    void testEmptyStringValues() {
        config.setUser("");
        config.setPassword("");
        config.setHostName("");

        assertEquals("", config.getUser());
        assertEquals("", config.getPassword());
        assertEquals("", config.getHostName());
    }

    @Test
    void testPortBoundaries() {
        config.setPort(0);
        assertEquals(0, config.getPort());

        config.setPort(65535);
        assertEquals(65535, config.getPort());
    }

    @Test
    void testProxyPortBoundaries() {
        config.setProxyPort(1);
        assertEquals(1, config.getProxyPort());

        config.setProxyPort(9999);
        assertEquals(9999, config.getProxyPort());
    }
}
