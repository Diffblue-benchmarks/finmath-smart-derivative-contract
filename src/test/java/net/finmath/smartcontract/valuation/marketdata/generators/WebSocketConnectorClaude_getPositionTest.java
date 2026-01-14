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
 * Test class for WebSocketConnector.getPosition() method. Tests the method with focus on branch
 * and condition coverage.
 *
 * @author Claude Code
 */
class WebSocketConnectorClaude_getPositionTest {

  /**
   * Test getPosition returns the local host address. Verifies that getPosition returns the IPv4
   * address set during construction.
   */
  @Test
  void testGetPosition_AfterConstruction_ReturnsLocalHostAddress() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Act
    String result = connector.getPosition();

    // Assert
    assertNotNull(result, "getPosition should return non-null value");

    // Verify it matches the expected local host address
    String expectedPosition = Inet4Address.getLocalHost().getHostAddress();
    assertEquals(expectedPosition, result, "getPosition should return the local host IP address");
  }

  /**
   * Test getPosition returns a valid IPv4 format string. Verifies that the returned string matches
   * IPv4 address format.
   */
  @Test
  void testGetPosition_Format_ValidIPv4() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Act
    String result = connector.getPosition();

    // Assert
    assertNotNull(result, "getPosition should return non-null");

    // Verify it's in IPv4 format (basic pattern check)
    String ipv4Pattern = "^([0-9]{1,3}\\.){3}[0-9]{1,3}$";
    assertTrue(
        result.matches(ipv4Pattern),
        "getPosition should return a valid IPv4 address format, got: " + result);
  }

  /**
   * Test getPosition returns the same value on multiple calls. Verifies that getPosition
   * consistently returns the same value.
   */
  @Test
  void testGetPosition_MultipleCalls_ReturnsSameValue() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Act
    String result1 = connector.getPosition();
    String result2 = connector.getPosition();
    String result3 = connector.getPosition();

    // Assert
    assertNotNull(result1, "First call should return non-null");
    assertNotNull(result2, "Second call should return non-null");
    assertNotNull(result3, "Third call should return non-null");
    assertEquals(result1, result2, "Multiple calls should return the same value");
    assertEquals(result2, result3, "Multiple calls should return the same value");
  }

  /**
   * Test getPosition with different Properties objects. Verifies that getPosition returns the same
   * local host address regardless of the Properties content.
   */
  @Test
  void testGetPosition_DifferentProperties_SamePosition() throws Exception {
    // Arrange
    Properties connectionProperties1 = new Properties();
    connectionProperties1.setProperty("AUTHURL", "https://example1.com/auth");

    Properties connectionProperties2 = new Properties();
    connectionProperties2.setProperty("AUTHURL", "https://example2.com/auth");

    WebSocketConnector connector1 = new WebSocketConnector(connectionProperties1);
    WebSocketConnector connector2 = new WebSocketConnector(connectionProperties2);

    // Act
    String result1 = connector1.getPosition();
    String result2 = connector2.getPosition();

    // Assert
    assertNotNull(result1, "Connector 1 should have non-null position");
    assertNotNull(result2, "Connector 2 should have non-null position");
    assertEquals(result1, result2, "Both connectors should have the same position (local host)");
  }

  /**
   * Test getPosition returns the same value as the public position field. Verifies that getPosition
   * returns the exact same value as directly accessing the position field.
   */
  @Test
  void testGetPosition_MatchesPublicField_SameValue() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Act
    String methodResult = connector.getPosition();
    String fieldValue = connector.position;

    // Assert
    assertNotNull(methodResult, "getPosition should return non-null");
    assertNotNull(fieldValue, "position field should be non-null");
    assertEquals(fieldValue, methodResult, "getPosition should return the same value as the position field");
  }

  /**
   * Test getPosition after directly modifying the position field. Verifies that getPosition returns
   * the modified value when position field is changed.
   */
  @Test
  void testGetPosition_AfterDirectModification_ReturnsModifiedValue() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    String originalPosition = connector.getPosition();
    assertNotNull(originalPosition, "Original position should be non-null");

    // Modify the position field directly
    connector.position = "192.168.1.100";

    // Act
    String result = connector.getPosition();

    // Assert
    assertEquals("192.168.1.100", result, "getPosition should return the modified value");
    assertNotEquals(originalPosition, result, "Modified position should differ from original");
  }

  /**
   * Test getPosition after setting position to null. Verifies that getPosition returns null when
   * position field is explicitly set to null.
   */
  @Test
  void testGetPosition_AfterSetToNull_ReturnsNull() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Verify initial state is non-null
    assertNotNull(connector.getPosition(), "Initial position should be non-null");

    // Set position to null
    connector.position = null;

    // Act
    String result = connector.getPosition();

    // Assert
    assertNull(result, "getPosition should return null after position is set to null");
  }

  /**
   * Test getPosition after setting position to empty string. Verifies that getPosition returns
   * empty string when position field is set to empty string.
   */
  @Test
  void testGetPosition_AfterSetToEmptyString_ReturnsEmptyString() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Set position to empty string
    connector.position = "";

    // Act
    String result = connector.getPosition();

    // Assert
    assertNotNull(result, "getPosition should return non-null");
    assertEquals("", result, "getPosition should return empty string");
    assertTrue(result.isEmpty(), "Result should be empty");
  }

  /**
   * Test getPosition with custom IPv4 address. Verifies that getPosition correctly returns custom
   * IPv4 addresses set directly.
   */
  @Test
  void testGetPosition_CustomIPv4Address_ReturnsCustomValue() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    String customIP = "10.0.0.1";
    connector.position = customIP;

    // Act
    String result = connector.getPosition();

    // Assert
    assertEquals(customIP, result, "getPosition should return the custom IP address");
  }

  /**
   * Test getPosition with different connector instances. Verifies that each connector instance
   * maintains its own position value.
   */
  @Test
  void testGetPosition_DifferentInstances_IndependentValues() throws Exception {
    // Arrange
    Properties connectionProperties1 = new Properties();
    Properties connectionProperties2 = new Properties();

    WebSocketConnector connector1 = new WebSocketConnector(connectionProperties1);
    WebSocketConnector connector2 = new WebSocketConnector(connectionProperties2);

    // Modify position for one connector
    connector1.position = "192.168.1.100";
    connector2.position = "10.10.10.10";

    // Act
    String result1 = connector1.getPosition();
    String result2 = connector2.getPosition();

    // Assert
    assertEquals("192.168.1.100", result1, "Connector 1 should have its own position");
    assertEquals("10.10.10.10", result2, "Connector 2 should have its own position");
    assertNotEquals(result1, result2, "Different connectors should have different position values");
  }

  /**
   * Test getPosition with various IP address formats. Verifies that getPosition correctly handles
   * different IP address string formats.
   */
  @Test
  void testGetPosition_VariousIPFormats_ReturnsCorrectly() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    String[] testIPs = {
      "127.0.0.1",
      "192.168.0.1",
      "10.0.0.0",
      "172.16.0.1",
      "255.255.255.255",
      "0.0.0.0"
    };

    for (String testIP : testIPs) {
      // Set position to test IP
      connector.position = testIP;

      // Act
      String result = connector.getPosition();

      // Assert
      assertEquals(testIP, result, "getPosition should return " + testIP);
    }
  }

  /**
   * Test getPosition with special string values. Verifies that getPosition correctly returns
   * non-standard string values when position is set to such values.
   */
  @Test
  void testGetPosition_SpecialStringValues_ReturnsCorrectly() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    String[] testValues = {
      "localhost",
      "example.com",
      "test-position",
      "192.168.1.1:8080",
      "position with spaces"
    };

    for (String testValue : testValues) {
      // Set position to test value
      connector.position = testValue;

      // Act
      String result = connector.getPosition();

      // Assert
      assertEquals(testValue, result, "getPosition should return: " + testValue);
    }
  }

  /**
   * Test getPosition after multiple reassignments. Verifies that getPosition always returns the
   * most recent value after multiple changes to position.
   */
  @Test
  void testGetPosition_AfterMultipleReassignments_ReturnsLatestValue() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Act & Assert - multiple reassignments
    connector.position = "192.168.1.1";
    assertEquals("192.168.1.1", connector.getPosition(), "Should return first reassigned value");

    connector.position = "10.0.0.1";
    assertEquals("10.0.0.1", connector.getPosition(), "Should return second reassigned value");

    connector.position = "172.16.0.1";
    assertEquals("172.16.0.1", connector.getPosition(), "Should return third reassigned value");

    connector.position = "127.0.0.1";
    assertEquals("127.0.0.1", connector.getPosition(), "Should return final reassigned value");
  }

  /**
   * Test getPosition with null Properties in constructor. Verifies that getPosition still works
   * even when constructor receives null Properties (though position should still be set).
   */
  @Test
  void testGetPosition_NullProperties_StillReturnsPosition() throws Exception {
    // Arrange
    WebSocketConnector connector = new WebSocketConnector(null);

    // Act
    String result = connector.getPosition();

    // Assert
    assertNotNull(result, "getPosition should return non-null even with null Properties");

    // Verify it's a valid IP address
    String expectedPosition = Inet4Address.getLocalHost().getHostAddress();
    assertEquals(expectedPosition, result, "Position should still be set to local host address");
  }
}
