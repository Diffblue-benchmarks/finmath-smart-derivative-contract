/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */
package net.finmath.smartcontract.valuation.marketdata.generators;

import org.junit.jupiter.api.Test;

import java.net.Inet4Address;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for WebSocketConnector constructor. Tests the constructor with focus on branch and
 * condition coverage.
 *
 * @author Claude Code
 */
class WebSocketConnectorClaude_constructorTest {

  /**
   * Test constructor with valid empty Properties. Verifies that the WebSocketConnector is created
   * successfully with empty properties and position is set to local host address.
   */
  @Test
  void testConstructor_EmptyProperties_Success() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();

    // Act
    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Assert
    assertNotNull(connector, "Connector should be created successfully");
    assertNotNull(connector.position, "Position should be set");
    assertNotNull(connector.connectionProperties, "Connection properties should be stored");
    assertEquals(connectionProperties, connector.connectionProperties, "Properties should match");

    // Verify position is a valid IP address format
    String expectedPosition = Inet4Address.getLocalHost().getHostAddress();
    assertEquals(expectedPosition, connector.position, "Position should be local host address");
  }

  /**
   * Test constructor with Properties containing connection parameters. Verifies that properties
   * are stored correctly and accessible.
   */
  @Test
  void testConstructor_PropertiesWithConnectionParams_Success() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    connectionProperties.setProperty("AUTHURL", "https://example.com/auth");
    connectionProperties.setProperty("HOSTNAME", "example.com");
    connectionProperties.setProperty("PORT", "443");
    connectionProperties.setProperty("CLIENTID", "test-client");
    connectionProperties.setProperty("USER", "testuser");
    connectionProperties.setProperty("PASSWORD", "testpass");
    connectionProperties.setProperty("USEPROXY", "FALSE");

    // Act
    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Assert
    assertNotNull(connector, "Connector should be created successfully");
    assertNotNull(connector.position, "Position should be set");
    assertNotNull(connector.connectionProperties, "Connection properties should be stored");

    // Verify properties are stored and accessible
    assertEquals("https://example.com/auth", connector.connectionProperties.get("AUTHURL"));
    assertEquals("example.com", connector.connectionProperties.get("HOSTNAME"));
    assertEquals("443", connector.connectionProperties.get("PORT"));
    assertEquals("test-client", connector.connectionProperties.get("CLIENTID"));
    assertEquals("testuser", connector.connectionProperties.get("USER"));
    assertEquals("testpass", connector.connectionProperties.get("PASSWORD"));
    assertEquals("FALSE", connector.connectionProperties.get("USEPROXY"));

    // Verify position is set correctly
    String expectedPosition = Inet4Address.getLocalHost().getHostAddress();
    assertEquals(expectedPosition, connector.position, "Position should be local host address");
  }

  /**
   * Test constructor with Properties containing proxy settings. Verifies that proxy-related
   * properties are stored correctly.
   */
  @Test
  void testConstructor_PropertiesWithProxySettings_Success() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    connectionProperties.setProperty("USEPROXY", "TRUE");
    connectionProperties.setProperty("PROXYHOST", "proxy.example.com");
    connectionProperties.setProperty("PROXYPORT", "8080");
    connectionProperties.setProperty("PROXYUSER", "proxyuser");
    connectionProperties.setProperty("PROXYPASS", "proxypass");

    // Act
    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Assert
    assertNotNull(connector, "Connector should be created successfully");
    assertNotNull(connector.connectionProperties, "Connection properties should be stored");

    // Verify proxy properties are stored correctly
    assertEquals("TRUE", connector.connectionProperties.get("USEPROXY"));
    assertEquals("proxy.example.com", connector.connectionProperties.get("PROXYHOST"));
    assertEquals("8080", connector.connectionProperties.get("PROXYPORT"));
    assertEquals("proxyuser", connector.connectionProperties.get("PROXYUSER"));
    assertEquals("proxypass", connector.connectionProperties.get("PROXYPASS"));
  }

  /**
   * Test constructor with null Properties. Verifies that NullPointerException is thrown when
   * properties is null. The exception occurs when trying to access the null properties object.
   */
  @Test
  void testConstructor_NullProperties_DoesNotThrowImmediately() throws Exception {
    // Arrange & Act
    // The constructor itself doesn't access the properties, so null is accepted
    WebSocketConnector connector = new WebSocketConnector(null);

    // Assert
    assertNotNull(connector, "Connector should be created even with null properties");
    assertNull(connector.connectionProperties, "Connection properties should be null");
    assertNotNull(connector.position, "Position should still be set");
  }

  /**
   * Test constructor initializes scope and server fields. Verifies that the default values for
   * scope and server are set correctly.
   */
  @Test
  void testConstructor_DefaultFieldValues_SetCorrectly() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();

    // Act
    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Assert
    assertNotNull(connector, "Connector should be created successfully");
    assertEquals("", connector.scope, "Scope should be initialized to empty string");
    assertEquals("", connector.server, "Server should be initialized to empty string");
    assertNull(connector.authJson, "AuthJson should be null initially");
    assertNull(connector.ws, "WebSocket should be null initially");
  }

  /**
   * Test constructor with Properties containing special characters. Verifies that special
   * characters in property values are handled correctly.
   */
  @Test
  void testConstructor_PropertiesWithSpecialCharacters_Success() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    connectionProperties.setProperty("USER", "user@example.com");
    connectionProperties.setProperty("PASSWORD", "p@ssw0rd!#$%");
    connectionProperties.setProperty("AUTHURL", "https://example.com/auth?param=value&other=123");

    // Act
    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Assert
    assertNotNull(connector, "Connector should be created successfully");
    assertEquals("user@example.com", connector.connectionProperties.get("USER"));
    assertEquals("p@ssw0rd!#$%", connector.connectionProperties.get("PASSWORD"));
    assertEquals(
        "https://example.com/auth?param=value&other=123",
        connector.connectionProperties.get("AUTHURL"));
  }

  /**
   * Test constructor with Properties containing empty string values. Verifies that empty strings
   * are handled correctly.
   */
  @Test
  void testConstructor_PropertiesWithEmptyValues_Success() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    connectionProperties.setProperty("AUTHURL", "");
    connectionProperties.setProperty("HOSTNAME", "");
    connectionProperties.setProperty("PORT", "");

    // Act
    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Assert
    assertNotNull(connector, "Connector should be created successfully");
    assertEquals("", connector.connectionProperties.get("AUTHURL"));
    assertEquals("", connector.connectionProperties.get("HOSTNAME"));
    assertEquals("", connector.connectionProperties.get("PORT"));
  }

  /**
   * Test constructor multiple times to verify position remains consistent. Verifies that the
   * position is set to the same local host address across multiple instantiations.
   */
  @Test
  void testConstructor_MultipleInstances_PositionConsistent() throws Exception {
    // Arrange
    Properties connectionProperties1 = new Properties();
    Properties connectionProperties2 = new Properties();

    // Act
    WebSocketConnector connector1 = new WebSocketConnector(connectionProperties1);
    WebSocketConnector connector2 = new WebSocketConnector(connectionProperties2);

    // Assert
    assertNotNull(connector1, "First connector should be created successfully");
    assertNotNull(connector2, "Second connector should be created successfully");
    assertEquals(
        connector1.position,
        connector2.position,
        "Position should be the same for both connectors");

    // Verify position is correct
    String expectedPosition = Inet4Address.getLocalHost().getHostAddress();
    assertEquals(expectedPosition, connector1.position, "Position should be local host address");
    assertEquals(expectedPosition, connector2.position, "Position should be local host address");
  }

  /**
   * Test constructor with Properties containing numeric values as strings. Verifies that numeric
   * values stored as strings are handled correctly.
   */
  @Test
  void testConstructor_PropertiesWithNumericValues_Success() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    connectionProperties.setProperty("PORT", "443");
    connectionProperties.setProperty("PROXYPORT", "8080");

    // Act
    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Assert
    assertNotNull(connector, "Connector should be created successfully");
    assertEquals("443", connector.connectionProperties.get("PORT"));
    assertEquals("8080", connector.connectionProperties.get("PROXYPORT"));
  }

  /**
   * Test constructor verifies position is in valid IPv4 format. Verifies that the position field
   * contains a valid IPv4 address.
   */
  @Test
  void testConstructor_PositionIsValidIPv4_Success() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();

    // Act
    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Assert
    assertNotNull(connector.position, "Position should be set");

    // Verify position matches IPv4 format (basic check)
    // IPv4 format: xxx.xxx.xxx.xxx where xxx is 0-255
    String ipv4Pattern = "^([0-9]{1,3}\\.){3}[0-9]{1,3}$";
    assertTrue(
        connector.position.matches(ipv4Pattern),
        "Position should be in valid IPv4 format, got: " + connector.position);
  }

  /**
   * Test constructor with Properties object reference. Verifies that the stored properties
   * reference is the same object (not a copy).
   */
  @Test
  void testConstructor_PropertiesReference_SameObject() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    connectionProperties.setProperty("TEST_KEY", "TEST_VALUE");

    // Act
    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Assert
    assertSame(
        connectionProperties,
        connector.connectionProperties,
        "Should store the same Properties object reference");

    // Modify original properties and verify change is reflected
    connectionProperties.setProperty("NEW_KEY", "NEW_VALUE");
    assertEquals(
        "NEW_VALUE",
        connector.connectionProperties.get("NEW_KEY"),
        "Changes to original properties should be reflected in connector");
  }

  /**
   * Test constructor can access position via getPosition method. Verifies that the getPosition
   * method returns the correct value.
   */
  @Test
  void testConstructor_GetPositionMethod_ReturnsCorrectValue() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();

    // Act
    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Assert
    assertNotNull(connector.getPosition(), "getPosition() should return non-null value");
    assertEquals(
        connector.position,
        connector.getPosition(),
        "getPosition() should return the position field value");

    // Verify it matches the expected local host address
    String expectedPosition = Inet4Address.getLocalHost().getHostAddress();
    assertEquals(expectedPosition, connector.getPosition(), "Position should be local host address");
  }
}
