/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */
package net.finmath.smartcontract.valuation.marketdata.generators;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for WebSocketConnector.initWebSocketConnection() method. Tests the method with focus
 * on branch and condition coverage.
 *
 * @author Claude Code
 */
class WebSocketConnectorClaude_initWebSocketConnectionTest {

  /**
   * Test initWebSocketConnection with valid HOSTNAME and PORT, no proxy. Verifies that a WebSocket
   * object is created successfully with the correct URL.
   */
  @Test
  void testInitWebSocketConnection_ValidHostnameAndPort_NoProxy_Success()
      throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    connectionProperties.setProperty("HOSTNAME", "test.example.com");
    connectionProperties.setProperty("PORT", "443");
    connectionProperties.setProperty("USEPROXY", "FALSE");

    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Act
    WebSocket result = connector.initWebSocketConnection();

    // Assert
    assertNotNull(result, "WebSocket should be created successfully");
    assertEquals(
        "wss://test.example.com:443/WebSocket",
        connector.server,
        "Server field should be set to the correct WebSocket URL");
  }

  /**
   * Test initWebSocketConnection with different port number. Verifies that the WebSocket URL is
   * constructed correctly with the specified port.
   */
  @Test
  void testInitWebSocketConnection_DifferentPort_Success() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    connectionProperties.setProperty("HOSTNAME", "example.com");
    connectionProperties.setProperty("PORT", "8080");
    connectionProperties.setProperty("USEPROXY", "FALSE");

    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Act
    WebSocket result = connector.initWebSocketConnection();

    // Assert
    assertNotNull(result, "WebSocket should be created successfully");
    assertEquals(
        "wss://example.com:8080/WebSocket",
        connector.server,
        "Server field should include the correct port");
  }

  /**
   * Test initWebSocketConnection with USEPROXY set to TRUE and valid proxy settings. Verifies that
   * proxy configuration is applied correctly.
   */
  @Test
  void testInitWebSocketConnection_UseProxyTrue_ValidProxySettings_Success()
      throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    connectionProperties.setProperty("HOSTNAME", "example.com");
    connectionProperties.setProperty("PORT", "443");
    connectionProperties.setProperty("USEPROXY", "TRUE");
    connectionProperties.setProperty("PROXYHOST", "proxy.example.com");
    connectionProperties.setProperty("PROXYPORT", "8080");
    connectionProperties.setProperty("PROXYUSER", "proxyuser");
    connectionProperties.setProperty("PROXYPASS", "proxypass");

    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Act
    WebSocket result = connector.initWebSocketConnection();

    // Assert
    assertNotNull(result, "WebSocket should be created successfully with proxy settings");
    assertEquals(
        "wss://example.com:443/WebSocket",
        connector.server,
        "Server field should be set correctly");
  }

  /**
   * Test initWebSocketConnection with USEPROXY set to FALSE. Verifies that proxy is not used when
   * USEPROXY is FALSE.
   */
  @Test
  void testInitWebSocketConnection_UseProxyFalse_Success() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    connectionProperties.setProperty("HOSTNAME", "localhost");
    connectionProperties.setProperty("PORT", "9000");
    connectionProperties.setProperty("USEPROXY", "FALSE");

    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Act
    WebSocket result = connector.initWebSocketConnection();

    // Assert
    assertNotNull(result, "WebSocket should be created successfully without proxy");
    assertEquals(
        "wss://localhost:9000/WebSocket", connector.server, "Server field should be set correctly");
  }

  /**
   * Test initWebSocketConnection with missing HOSTNAME property. When HOSTNAME is null,
   * String.format converts it to the string "null" in the URL, which creates "wss://null:443/WebSocket".
   * WebSocketFactory accepts this as a valid hostname (treating "null" as a literal hostname string).
   */
  @Test
  void testInitWebSocketConnection_MissingHostname_CreatesWebSocketWithNullHostname() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    connectionProperties.setProperty("PORT", "443");
    connectionProperties.setProperty("USEPROXY", "FALSE");

    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Act
    WebSocket result = connector.initWebSocketConnection();

    // Assert
    assertNotNull(result, "WebSocket should be created with literal 'null' hostname");
    assertEquals("wss://null:443/WebSocket", connector.server, "Server field should contain 'null' as hostname");
  }

  /**
   * Test initWebSocketConnection with missing PORT property. When PORT is null, it creates an
   * invalid WebSocket URL and WebSocketFactory throws IllegalArgumentException.
   */
  @Test
  void testInitWebSocketConnection_MissingPort_ThrowsIllegalArgumentException() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    connectionProperties.setProperty("HOSTNAME", "example.com");
    connectionProperties.setProperty("USEPROXY", "FALSE");

    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Act & Assert
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          connector.initWebSocketConnection();
        },
        "Should throw IllegalArgumentException when PORT is missing");
  }

  /**
   * Test initWebSocketConnection with USEPROXY TRUE but missing PROXYHOST. This should fail when
   * trying to set proxy host.
   */
  @Test
  void testInitWebSocketConnection_UseProxyTrueWithMissingProxyHost_ThrowsNullPointerException()
      throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    connectionProperties.setProperty("HOSTNAME", "example.com");
    connectionProperties.setProperty("PORT", "443");
    connectionProperties.setProperty("USEPROXY", "TRUE");
    connectionProperties.setProperty("PROXYPORT", "8080");
    connectionProperties.setProperty("PROXYUSER", "user");
    connectionProperties.setProperty("PROXYPASS", "pass");

    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Act & Assert
    assertThrows(
        NullPointerException.class,
        () -> {
          connector.initWebSocketConnection();
        },
        "Should throw NullPointerException when PROXYHOST is missing");
  }

  /**
   * Test initWebSocketConnection with USEPROXY TRUE but missing PROXYPORT. This should fail when
   * trying to parse proxy port.
   */
  @Test
  void testInitWebSocketConnection_UseProxyTrueWithMissingProxyPort_ThrowsNullPointerException()
      throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    connectionProperties.setProperty("HOSTNAME", "example.com");
    connectionProperties.setProperty("PORT", "443");
    connectionProperties.setProperty("USEPROXY", "TRUE");
    connectionProperties.setProperty("PROXYHOST", "proxy.example.com");
    connectionProperties.setProperty("PROXYUSER", "user");
    connectionProperties.setProperty("PROXYPASS", "pass");

    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Act & Assert
    assertThrows(
        NullPointerException.class,
        () -> {
          connector.initWebSocketConnection();
        },
        "Should throw NullPointerException when PROXYPORT is missing");
  }

  /**
   * Test initWebSocketConnection with USEPROXY TRUE but missing PROXYUSER. This should fail when
   * trying to set proxy credentials.
   */
  @Test
  void testInitWebSocketConnection_UseProxyTrueWithMissingProxyUser_ThrowsNullPointerException()
      throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    connectionProperties.setProperty("HOSTNAME", "example.com");
    connectionProperties.setProperty("PORT", "443");
    connectionProperties.setProperty("USEPROXY", "TRUE");
    connectionProperties.setProperty("PROXYHOST", "proxy.example.com");
    connectionProperties.setProperty("PROXYPORT", "8080");
    connectionProperties.setProperty("PROXYPASS", "pass");

    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Act & Assert
    assertThrows(
        NullPointerException.class,
        () -> {
          connector.initWebSocketConnection();
        },
        "Should throw NullPointerException when PROXYUSER is missing");
  }

  /**
   * Test initWebSocketConnection with USEPROXY TRUE but missing PROXYPASS. This should fail when
   * trying to set proxy credentials.
   */
  @Test
  void testInitWebSocketConnection_UseProxyTrueWithMissingProxyPass_ThrowsNullPointerException()
      throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    connectionProperties.setProperty("HOSTNAME", "example.com");
    connectionProperties.setProperty("PORT", "443");
    connectionProperties.setProperty("USEPROXY", "TRUE");
    connectionProperties.setProperty("PROXYHOST", "proxy.example.com");
    connectionProperties.setProperty("PROXYPORT", "8080");
    connectionProperties.setProperty("PROXYUSER", "user");

    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Act & Assert
    assertThrows(
        NullPointerException.class,
        () -> {
          connector.initWebSocketConnection();
        },
        "Should throw NullPointerException when PROXYPASS is missing");
  }

  /**
   * Test initWebSocketConnection with invalid PROXYPORT format. This should fail when trying to
   * parse the port number.
   */
  @Test
  void testInitWebSocketConnection_InvalidProxyPortFormat_ThrowsNumberFormatException()
      throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    connectionProperties.setProperty("HOSTNAME", "example.com");
    connectionProperties.setProperty("PORT", "443");
    connectionProperties.setProperty("USEPROXY", "TRUE");
    connectionProperties.setProperty("PROXYHOST", "proxy.example.com");
    connectionProperties.setProperty("PROXYPORT", "not-a-number");
    connectionProperties.setProperty("PROXYUSER", "user");
    connectionProperties.setProperty("PROXYPASS", "pass");

    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Act & Assert
    assertThrows(
        NumberFormatException.class,
        () -> {
          connector.initWebSocketConnection();
        },
        "Should throw NumberFormatException when PROXYPORT is not a valid number");
  }

  /**
   * Test initWebSocketConnection with USEPROXY set to any value other than "TRUE". Verifies that
   * proxy is not used when USEPROXY is not exactly "TRUE".
   */
  @Test
  void testInitWebSocketConnection_UseProxyNotTrue_Success() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    connectionProperties.setProperty("HOSTNAME", "example.com");
    connectionProperties.setProperty("PORT", "443");
    connectionProperties.setProperty("USEPROXY", "false");

    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Act
    WebSocket result = connector.initWebSocketConnection();

    // Assert
    assertNotNull(result, "WebSocket should be created successfully");
    assertEquals(
        "wss://example.com:443/WebSocket", connector.server, "Server field should be set correctly");
  }

  /**
   * Test initWebSocketConnection with empty HOSTNAME. WebSocketFactory throws
   * IllegalArgumentException when the host part is empty.
   */
  @Test
  void testInitWebSocketConnection_EmptyHostname_ThrowsIllegalArgumentException() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    connectionProperties.setProperty("HOSTNAME", "");
    connectionProperties.setProperty("PORT", "443");
    connectionProperties.setProperty("USEPROXY", "FALSE");

    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Act & Assert
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          connector.initWebSocketConnection();
        },
        "Should throw IllegalArgumentException when HOSTNAME is empty");
  }

  /**
   * Test initWebSocketConnection with empty PORT. This will create a WebSocket with an empty port
   * in the URL.
   */
  @Test
  void testInitWebSocketConnection_EmptyPort_CreatesWebSocketWithEmptyPort() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    connectionProperties.setProperty("HOSTNAME", "example.com");
    connectionProperties.setProperty("PORT", "");
    connectionProperties.setProperty("USEPROXY", "FALSE");

    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Act
    WebSocket result = connector.initWebSocketConnection();

    // Assert
    assertNotNull(result, "WebSocket should be created");
    assertEquals("wss://example.com:/WebSocket", connector.server, "Server field should have empty port");
  }

  /**
   * Test initWebSocketConnection with hostname containing special characters. Verifies that
   * hostnames with special characters are handled correctly.
   */
  @Test
  void testInitWebSocketConnection_HostnameWithSpecialCharacters_Success() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    connectionProperties.setProperty("HOSTNAME", "test-server.example.com");
    connectionProperties.setProperty("PORT", "443");
    connectionProperties.setProperty("USEPROXY", "FALSE");

    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Act
    WebSocket result = connector.initWebSocketConnection();

    // Assert
    assertNotNull(result, "WebSocket should be created successfully");
    assertEquals(
        "wss://test-server.example.com:443/WebSocket",
        connector.server,
        "Server field should handle hostname with hyphens");
  }

  /**
   * Test initWebSocketConnection with IPv4 address as hostname. Verifies that IP addresses work as
   * hostnames.
   */
  @Test
  void testInitWebSocketConnection_IPv4AddressAsHostname_Success() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    connectionProperties.setProperty("HOSTNAME", "192.168.1.100");
    connectionProperties.setProperty("PORT", "443");
    connectionProperties.setProperty("USEPROXY", "FALSE");

    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Act
    WebSocket result = connector.initWebSocketConnection();

    // Assert
    assertNotNull(result, "WebSocket should be created successfully with IP address");
    assertEquals(
        "wss://192.168.1.100:443/WebSocket",
        connector.server,
        "Server field should handle IP address as hostname");
  }

  /**
   * Test initWebSocketConnection with null connectionProperties. This should fail when trying to
   * access properties.
   */
  @Test
  void testInitWebSocketConnection_NullConnectionProperties_ThrowsNullPointerException()
      throws Exception {
    // Arrange
    WebSocketConnector connector = new WebSocketConnector(null);

    // Act & Assert
    assertThrows(
        NullPointerException.class,
        () -> {
          connector.initWebSocketConnection();
        },
        "Should throw NullPointerException when connectionProperties is null");
  }

  /**
   * Test initWebSocketConnection with missing USEPROXY property. Verifies behavior when USEPROXY
   * property is not set.
   */
  @Test
  void testInitWebSocketConnection_MissingUseProxyProperty_ThrowsNullPointerException()
      throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    connectionProperties.setProperty("HOSTNAME", "example.com");
    connectionProperties.setProperty("PORT", "443");
    // USEPROXY property not set

    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Act & Assert
    assertThrows(
        NullPointerException.class,
        () -> {
          connector.initWebSocketConnection();
        },
        "Should throw NullPointerException when USEPROXY property is missing");
  }

  /**
   * Test initWebSocketConnection with very large port number. Verifies that large port numbers are
   * accepted.
   */
  @Test
  void testInitWebSocketConnection_LargePortNumber_Success() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    connectionProperties.setProperty("HOSTNAME", "example.com");
    connectionProperties.setProperty("PORT", "65535");
    connectionProperties.setProperty("USEPROXY", "FALSE");

    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Act
    WebSocket result = connector.initWebSocketConnection();

    // Assert
    assertNotNull(result, "WebSocket should be created with large port number");
    assertEquals(
        "wss://example.com:65535/WebSocket",
        connector.server,
        "Server field should handle large port number");
  }

  /**
   * Test initWebSocketConnection with proxy port as zero. This should fail when trying to set
   * proxy port to zero.
   */
  @Test
  void testInitWebSocketConnection_ProxyPortZero_Success() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    connectionProperties.setProperty("HOSTNAME", "example.com");
    connectionProperties.setProperty("PORT", "443");
    connectionProperties.setProperty("USEPROXY", "TRUE");
    connectionProperties.setProperty("PROXYHOST", "proxy.example.com");
    connectionProperties.setProperty("PROXYPORT", "0");
    connectionProperties.setProperty("PROXYUSER", "user");
    connectionProperties.setProperty("PROXYPASS", "pass");

    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Act
    WebSocket result = connector.initWebSocketConnection();

    // Assert
    assertNotNull(result, "WebSocket should be created even with proxy port 0");
  }

  /**
   * Test initWebSocketConnection with negative proxy port. Verifies that negative port number is
   * accepted (though it may not be valid in practice).
   */
  @Test
  void testInitWebSocketConnection_NegativeProxyPort_Success() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    connectionProperties.setProperty("HOSTNAME", "example.com");
    connectionProperties.setProperty("PORT", "443");
    connectionProperties.setProperty("USEPROXY", "TRUE");
    connectionProperties.setProperty("PROXYHOST", "proxy.example.com");
    connectionProperties.setProperty("PROXYPORT", "-1");
    connectionProperties.setProperty("PROXYUSER", "user");
    connectionProperties.setProperty("PROXYPASS", "pass");

    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Act
    WebSocket result = connector.initWebSocketConnection();

    // Assert
    assertNotNull(result, "WebSocket should be created even with negative proxy port");
  }

  /**
   * Test initWebSocketConnection with empty proxy credentials. Verifies that empty strings are
   * accepted for proxy username and password.
   */
  @Test
  void testInitWebSocketConnection_EmptyProxyCredentials_Success() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    connectionProperties.setProperty("HOSTNAME", "example.com");
    connectionProperties.setProperty("PORT", "443");
    connectionProperties.setProperty("USEPROXY", "TRUE");
    connectionProperties.setProperty("PROXYHOST", "proxy.example.com");
    connectionProperties.setProperty("PROXYPORT", "8080");
    connectionProperties.setProperty("PROXYUSER", "");
    connectionProperties.setProperty("PROXYPASS", "");

    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Act
    WebSocket result = connector.initWebSocketConnection();

    // Assert
    assertNotNull(result, "WebSocket should be created with empty proxy credentials");
  }

  /**
   * Test initWebSocketConnection with proxy credentials containing special characters. Verifies
   * that special characters in credentials are handled correctly.
   */
  @Test
  void testInitWebSocketConnection_ProxyCredentialsWithSpecialCharacters_Success()
      throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    connectionProperties.setProperty("HOSTNAME", "example.com");
    connectionProperties.setProperty("PORT", "443");
    connectionProperties.setProperty("USEPROXY", "TRUE");
    connectionProperties.setProperty("PROXYHOST", "proxy.example.com");
    connectionProperties.setProperty("PROXYPORT", "8080");
    connectionProperties.setProperty("PROXYUSER", "user@domain.com");
    connectionProperties.setProperty("PROXYPASS", "p@ssw0rd!#$%");

    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Act
    WebSocket result = connector.initWebSocketConnection();

    // Assert
    assertNotNull(result, "WebSocket should be created with special characters in proxy credentials");
  }

  /**
   * Test initWebSocketConnection multiple times. Verifies that multiple calls create new WebSocket
   * objects.
   */
  @Test
  void testInitWebSocketConnection_MultipleCalls_CreatesNewInstances() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    connectionProperties.setProperty("HOSTNAME", "example.com");
    connectionProperties.setProperty("PORT", "443");
    connectionProperties.setProperty("USEPROXY", "FALSE");

    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Act
    WebSocket result1 = connector.initWebSocketConnection();
    WebSocket result2 = connector.initWebSocketConnection();
    WebSocket result3 = connector.initWebSocketConnection();

    // Assert
    assertNotNull(result1, "First WebSocket should be created");
    assertNotNull(result2, "Second WebSocket should be created");
    assertNotNull(result3, "Third WebSocket should be created");
    assertNotSame(result1, result2, "Each call should create a new WebSocket instance");
    assertNotSame(result2, result3, "Each call should create a new WebSocket instance");
  }

  /**
   * Test initWebSocketConnection with USEPROXY case variations. The equals() method is
   * case-sensitive, so "true", "True", "tRuE" should not match "TRUE".
   */
  @Test
  void testInitWebSocketConnection_UseProxyCaseSensitive_Success() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    connectionProperties.setProperty("HOSTNAME", "example.com");
    connectionProperties.setProperty("PORT", "443");
    connectionProperties.setProperty("USEPROXY", "true"); // lowercase, not "TRUE"

    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Act
    WebSocket result = connector.initWebSocketConnection();

    // Assert
    assertNotNull(result, "WebSocket should be created");
    // Since the check is .equals("TRUE"), "true" should not trigger proxy configuration
    assertEquals("wss://example.com:443/WebSocket", connector.server, "Server should be set");
  }

  /**
   * Test initWebSocketConnection sets server field correctly. Verifies that the server field is
   * updated with the correct WebSocket URL.
   */
  @Test
  void testInitWebSocketConnection_SetsServerField_Success() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    connectionProperties.setProperty("HOSTNAME", "ws.example.com");
    connectionProperties.setProperty("PORT", "9443");
    connectionProperties.setProperty("USEPROXY", "FALSE");

    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Verify server is initially empty
    assertEquals("", connector.server, "server should be empty initially");

    // Act
    connector.initWebSocketConnection();

    // Assert
    String expectedServer = "wss://ws.example.com:9443/WebSocket";
    assertEquals(expectedServer, connector.server, "server field should be set to the WebSocket URL");
  }
}
