/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */
package net.finmath.smartcontract.valuation.marketdata.generators;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for WebSocketConnector.getAuthJson() method. Tests the method with focus on branch
 * and condition coverage.
 *
 * @author Claude Code
 */
class WebSocketConnectorClaude_getAuthJsonTest {

  /**
   * Test getAuthJson when authJson is null (initial state). Verifies that getAuthJson returns null
   * when authJson has not been initialized.
   */
  @Test
  void testGetAuthJson_InitialState_ReturnsNull() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Act
    JSONObject result = connector.getAuthJson();

    // Assert
    assertNull(result, "getAuthJson should return null when authJson is not initialized");
  }

  /**
   * Test getAuthJson after authJson is directly set. Verifies that getAuthJson returns the value
   * that was directly assigned to the public authJson field.
   */
  @Test
  void testGetAuthJson_AfterDirectAssignment_ReturnsAssignedValue() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    JSONObject testAuthJson = new JSONObject();
    testAuthJson.put("access_token", "test-token-12345");
    testAuthJson.put("expires_in", 300);
    testAuthJson.put("token_type", "Bearer");

    connector.authJson = testAuthJson;

    // Act
    JSONObject result = connector.getAuthJson();

    // Assert
    assertNotNull(result, "getAuthJson should return non-null value after assignment");
    assertSame(testAuthJson, result, "getAuthJson should return the same object that was assigned");
    assertEquals("test-token-12345", result.getString("access_token"));
    assertEquals(300, result.getInt("expires_in"));
    assertEquals("Bearer", result.getString("token_type"));
  }

  /**
   * Test getAuthJson returns the same reference on multiple calls. Verifies that getAuthJson
   * consistently returns the same object reference.
   */
  @Test
  void testGetAuthJson_MultipleCalls_ReturnsSameReference() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    JSONObject testAuthJson = new JSONObject();
    testAuthJson.put("refresh_token", "refresh-token-abc");
    connector.authJson = testAuthJson;

    // Act
    JSONObject result1 = connector.getAuthJson();
    JSONObject result2 = connector.getAuthJson();
    JSONObject result3 = connector.getAuthJson();

    // Assert
    assertNotNull(result1, "First call should return non-null");
    assertNotNull(result2, "Second call should return non-null");
    assertNotNull(result3, "Third call should return non-null");
    assertSame(result1, result2, "Multiple calls should return the same reference");
    assertSame(result2, result3, "Multiple calls should return the same reference");
    assertSame(testAuthJson, result1, "Should return the exact object that was set");
  }

  /**
   * Test getAuthJson with empty JSONObject. Verifies that getAuthJson correctly returns an empty
   * JSONObject when that's what was set.
   */
  @Test
  void testGetAuthJson_EmptyJSONObject_ReturnsEmpty() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    JSONObject emptyAuthJson = new JSONObject();
    connector.authJson = emptyAuthJson;

    // Act
    JSONObject result = connector.getAuthJson();

    // Assert
    assertNotNull(result, "getAuthJson should return non-null for empty JSONObject");
    assertSame(emptyAuthJson, result, "Should return the same empty JSONObject");
    assertEquals(0, result.length(), "JSONObject should be empty");
  }

  /**
   * Test getAuthJson with complex JSONObject containing nested objects. Verifies that getAuthJson
   * correctly returns a complex JSONObject with nested structure.
   */
  @Test
  void testGetAuthJson_ComplexJSONObject_ReturnsComplex() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    JSONObject nestedObject = new JSONObject();
    nestedObject.put("sub_key", "sub_value");

    JSONObject complexAuthJson = new JSONObject();
    complexAuthJson.put("access_token", "complex-token");
    complexAuthJson.put("expires_in", 3600);
    complexAuthJson.put("metadata", nestedObject);
    complexAuthJson.put("scope", "read write");

    connector.authJson = complexAuthJson;

    // Act
    JSONObject result = connector.getAuthJson();

    // Assert
    assertNotNull(result, "getAuthJson should return non-null");
    assertSame(complexAuthJson, result, "Should return the same complex object");
    assertEquals("complex-token", result.getString("access_token"));
    assertEquals(3600, result.getInt("expires_in"));
    assertTrue(result.has("metadata"), "Should contain nested metadata");
    assertEquals("sub_value", result.getJSONObject("metadata").getString("sub_key"));
  }

  /**
   * Test getAuthJson after setting authJson to null explicitly. Verifies that getAuthJson returns
   * null when authJson is explicitly set to null.
   */
  @Test
  void testGetAuthJson_ExplicitlySetToNull_ReturnsNull() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // First set to non-null
    connector.authJson = new JSONObject().put("temp", "value");
    assertNotNull(connector.getAuthJson(), "Should be non-null after assignment");

    // Then set to null
    connector.authJson = null;

    // Act
    JSONObject result = connector.getAuthJson();

    // Assert
    assertNull(result, "getAuthJson should return null after being explicitly set to null");
  }

  /**
   * Test getAuthJson with JSONObject containing special characters. Verifies that getAuthJson
   * correctly returns a JSONObject with special characters in values.
   */
  @Test
  void testGetAuthJson_SpecialCharacters_ReturnsCorrectly() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    JSONObject authJsonWithSpecialChars = new JSONObject();
    authJsonWithSpecialChars.put("token", "abc!@#$%^&*()_+-={}[]|:;<>?,./");
    authJsonWithSpecialChars.put("user", "user@example.com");
    authJsonWithSpecialChars.put("message", "Hello \"World\"");

    connector.authJson = authJsonWithSpecialChars;

    // Act
    JSONObject result = connector.getAuthJson();

    // Assert
    assertNotNull(result, "getAuthJson should return non-null");
    assertEquals("abc!@#$%^&*()_+-={}[]|:;<>?,./", result.getString("token"));
    assertEquals("user@example.com", result.getString("user"));
    assertEquals("Hello \"World\"", result.getString("message"));
  }

  /**
   * Test getAuthJson with JSONObject containing various data types. Verifies that getAuthJson
   * correctly returns a JSONObject with different data types.
   */
  @Test
  void testGetAuthJson_VariousDataTypes_ReturnsCorrectly() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    JSONObject authJsonWithTypes = new JSONObject();
    authJsonWithTypes.put("string_value", "test");
    authJsonWithTypes.put("int_value", 42);
    authJsonWithTypes.put("long_value", 9876543210L);
    authJsonWithTypes.put("double_value", 3.14159);
    authJsonWithTypes.put("boolean_value", true);

    connector.authJson = authJsonWithTypes;

    // Act
    JSONObject result = connector.getAuthJson();

    // Assert
    assertNotNull(result, "getAuthJson should return non-null");
    assertEquals("test", result.getString("string_value"));
    assertEquals(42, result.getInt("int_value"));
    assertEquals(9876543210L, result.getLong("long_value"));
    assertEquals(3.14159, result.getDouble("double_value"), 0.00001);
    assertTrue(result.getBoolean("boolean_value"));
  }

  /**
   * Test that getAuthJson returns a reference that can be modified. Verifies that modifications to
   * the returned JSONObject are reflected in subsequent calls (since it returns the same
   * reference).
   */
  @Test
  void testGetAuthJson_ModifyReturnedObject_ChangesReflected() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    JSONObject originalAuthJson = new JSONObject();
    originalAuthJson.put("original_key", "original_value");
    connector.authJson = originalAuthJson;

    // Act
    JSONObject result1 = connector.getAuthJson();
    result1.put("new_key", "new_value"); // Modify the returned object

    JSONObject result2 = connector.getAuthJson();

    // Assert
    assertTrue(result2.has("new_key"), "Modifications should be reflected");
    assertEquals("new_value", result2.getString("new_key"));
    assertTrue(result2.has("original_key"), "Original key should still exist");
    assertEquals("original_value", result2.getString("original_key"));
  }

  /**
   * Test getAuthJson with different connector instances. Verifies that each connector instance has
   * its own authJson field.
   */
  @Test
  void testGetAuthJson_DifferentInstances_IndependentValues() throws Exception {
    // Arrange
    Properties connectionProperties1 = new Properties();
    Properties connectionProperties2 = new Properties();

    WebSocketConnector connector1 = new WebSocketConnector(connectionProperties1);
    WebSocketConnector connector2 = new WebSocketConnector(connectionProperties2);

    JSONObject authJson1 = new JSONObject().put("instance", "1");
    JSONObject authJson2 = new JSONObject().put("instance", "2");

    connector1.authJson = authJson1;
    connector2.authJson = authJson2;

    // Act
    JSONObject result1 = connector1.getAuthJson();
    JSONObject result2 = connector2.getAuthJson();

    // Assert
    assertNotNull(result1, "Connector 1 should have non-null authJson");
    assertNotNull(result2, "Connector 2 should have non-null authJson");
    assertNotSame(result1, result2, "Different connectors should have different authJson objects");
    assertEquals("1", result1.getString("instance"));
    assertEquals("2", result2.getString("instance"));
  }

  /**
   * Test getAuthJson with large JSONObject. Verifies that getAuthJson correctly handles a
   * JSONObject with many fields.
   */
  @Test
  void testGetAuthJson_LargeJSONObject_ReturnsCorrectly() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    JSONObject largeAuthJson = new JSONObject();
    for (int i = 0; i < 100; i++) {
      largeAuthJson.put("key_" + i, "value_" + i);
    }

    connector.authJson = largeAuthJson;

    // Act
    JSONObject result = connector.getAuthJson();

    // Assert
    assertNotNull(result, "getAuthJson should return non-null");
    assertSame(largeAuthJson, result, "Should return the same large object");
    assertEquals(100, result.length(), "Should contain all 100 keys");
    assertEquals("value_0", result.getString("key_0"));
    assertEquals("value_50", result.getString("key_50"));
    assertEquals("value_99", result.getString("key_99"));
  }

  /**
   * Test getAuthJson consistency after reassignment. Verifies that getAuthJson returns the new
   * value after authJson is reassigned.
   */
  @Test
  void testGetAuthJson_AfterReassignment_ReturnsNewValue() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    JSONObject firstAuthJson = new JSONObject().put("version", "1");
    connector.authJson = firstAuthJson;

    JSONObject firstResult = connector.getAuthJson();
    assertEquals("1", firstResult.getString("version"));

    // Reassign to a new JSONObject
    JSONObject secondAuthJson = new JSONObject().put("version", "2");
    connector.authJson = secondAuthJson;

    // Act
    JSONObject result = connector.getAuthJson();

    // Assert
    assertNotNull(result, "getAuthJson should return non-null after reassignment");
    assertSame(secondAuthJson, result, "Should return the new object");
    assertNotSame(firstAuthJson, result, "Should not return the old object");
    assertEquals("2", result.getString("version"));
  }
}
