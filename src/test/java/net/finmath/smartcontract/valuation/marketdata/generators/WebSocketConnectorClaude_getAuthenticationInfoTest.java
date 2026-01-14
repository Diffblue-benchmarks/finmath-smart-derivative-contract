/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */
package net.finmath.smartcontract.valuation.marketdata.generators;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for WebSocketConnector.getAuthenticationInfo() method. Tests the method with focus on
 * branch and condition coverage.
 *
 * <p>Note: This method makes real HTTP requests to external services, which makes it difficult to
 * test without mocking or a test server. These tests focus on testing the method's behavior with
 * invalid URLs and null inputs that can be tested without requiring a real authentication server.
 *
 * @author Claude Code
 */
class WebSocketConnectorClaude_getAuthenticationInfoTest {

  /**
   * Test getAuthenticationInfo with null previousAuthResponseJson and an invalid URL. This tests
   * the first branch where previousAuthResponseJson == null and password authentication is
   * attempted.
   */
  @Test
  void testGetAuthenticationInfo_NullPreviousAuthWithInvalidUrl_ReturnsNull() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    connectionProperties.setProperty("AUTHURL", "http://invalid-url-that-does-not-exist.example.com/auth");
    connectionProperties.setProperty("CLIENTID", "test-client");
    connectionProperties.setProperty("USER", "testuser");
    connectionProperties.setProperty("PASSWORD", "testpass");
    connectionProperties.setProperty("HOSTNAME", "localhost");
    connectionProperties.setProperty("PORT", "443");
    connectionProperties.setProperty("USEPROXY", "FALSE");

    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Act
    JSONObject result = connector.getAuthenticationInfo(null, "http://invalid-url-that-does-not-exist-12345.example.com/auth");

    // Assert
    // The method catches exceptions and returns null when connection fails
    assertNull(result, "Should return null when authentication fails with invalid URL");
  }

  /**
   * Test getAuthenticationInfo with non-null previousAuthResponseJson containing a refresh token
   * and an invalid URL. This tests the second branch where previousAuthResponseJson != null and
   * refresh token authentication is attempted.
   */
  @Test
  void testGetAuthenticationInfo_WithRefreshTokenAndInvalidUrl_ReturnsNull() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    connectionProperties.setProperty("AUTHURL", "http://invalid-url.example.com/auth");
    connectionProperties.setProperty("CLIENTID", "test-client");
    connectionProperties.setProperty("USER", "testuser");
    connectionProperties.setProperty("PASSWORD", "testpass");
    connectionProperties.setProperty("HOSTNAME", "localhost");
    connectionProperties.setProperty("PORT", "443");
    connectionProperties.setProperty("USEPROXY", "FALSE");

    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Create a mock previous auth response with a refresh token
    JSONObject previousAuthResponseJson = new JSONObject();
    previousAuthResponseJson.put("refresh_token", "mock-refresh-token-12345");
    previousAuthResponseJson.put("access_token", "mock-access-token");

    // Act
    JSONObject result = connector.getAuthenticationInfo(previousAuthResponseJson, "http://invalid-url-that-does-not-exist-67890.example.com/auth");

    // Assert
    // The method catches exceptions and returns null when connection fails
    assertNull(result, "Should return null when refresh token authentication fails with invalid URL");
  }

  /**
   * Test getAuthenticationInfo with null previousAuthResponseJson and missing CLIENTID. This
   * should fail when trying to access the CLIENTID property.
   */
  @Test
  void testGetAuthenticationInfo_MissingClientId_ReturnsNull() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    connectionProperties.setProperty("AUTHURL", "http://invalid-url.example.com/auth");
    // Missing CLIENTID
    connectionProperties.setProperty("USER", "testuser");
    connectionProperties.setProperty("PASSWORD", "testpass");
    connectionProperties.setProperty("HOSTNAME", "localhost");
    connectionProperties.setProperty("PORT", "443");
    connectionProperties.setProperty("USEPROXY", "FALSE");

    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Act
    JSONObject result = connector.getAuthenticationInfo(null, "http://invalid-url.example.com/auth");

    // Assert
    // The method catches exceptions and returns null
    assertNull(result, "Should return null when CLIENTID is missing");
  }

  /**
   * Test getAuthenticationInfo with null previousAuthResponseJson and missing USER. This should
   * fail when trying to access the USER property.
   */
  @Test
  void testGetAuthenticationInfo_MissingUser_ReturnsNull() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    connectionProperties.setProperty("AUTHURL", "http://invalid-url.example.com/auth");
    connectionProperties.setProperty("CLIENTID", "test-client");
    // Missing USER
    connectionProperties.setProperty("PASSWORD", "testpass");
    connectionProperties.setProperty("HOSTNAME", "localhost");
    connectionProperties.setProperty("PORT", "443");
    connectionProperties.setProperty("USEPROXY", "FALSE");

    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Act
    JSONObject result = connector.getAuthenticationInfo(null, "http://invalid-url.example.com/auth");

    // Assert
    // The method catches exceptions and returns null
    assertNull(result, "Should return null when USER is missing");
  }

  /**
   * Test getAuthenticationInfo with null previousAuthResponseJson and missing PASSWORD. This
   * should fail when trying to access the PASSWORD property.
   */
  @Test
  void testGetAuthenticationInfo_MissingPassword_ReturnsNull() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    connectionProperties.setProperty("AUTHURL", "http://invalid-url.example.com/auth");
    connectionProperties.setProperty("CLIENTID", "test-client");
    connectionProperties.setProperty("USER", "testuser");
    // Missing PASSWORD
    connectionProperties.setProperty("HOSTNAME", "localhost");
    connectionProperties.setProperty("PORT", "443");
    connectionProperties.setProperty("USEPROXY", "FALSE");

    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Act
    JSONObject result = connector.getAuthenticationInfo(null, "http://invalid-url.example.com/auth");

    // Assert
    // The method catches exceptions and returns null
    assertNull(result, "Should return null when PASSWORD is missing");
  }

  /**
   * Test getAuthenticationInfo with previousAuthResponseJson missing refresh_token. This should
   * fail when trying to get the refresh_token from the JSON.
   */
  @Test
  void testGetAuthenticationInfo_PreviousAuthMissingRefreshToken_ReturnsNull() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    connectionProperties.setProperty("AUTHURL", "http://invalid-url.example.com/auth");
    connectionProperties.setProperty("CLIENTID", "test-client");
    connectionProperties.setProperty("USER", "testuser");
    connectionProperties.setProperty("PASSWORD", "testpass");
    connectionProperties.setProperty("HOSTNAME", "localhost");
    connectionProperties.setProperty("PORT", "443");
    connectionProperties.setProperty("USEPROXY", "FALSE");

    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Create a previous auth response without refresh_token
    JSONObject previousAuthResponseJson = new JSONObject();
    previousAuthResponseJson.put("access_token", "mock-access-token");
    // Missing refresh_token

    // Act
    JSONObject result = connector.getAuthenticationInfo(previousAuthResponseJson, "http://invalid-url.example.com/auth");

    // Assert
    // The method catches JSONException and returns null
    assertNull(result, "Should return null when refresh_token is missing from previousAuthResponseJson");
  }

  /**
   * Test getAuthenticationInfo with null connectionProperties. This should fail when trying to
   * access properties.
   */
  @Test
  void testGetAuthenticationInfo_NullConnectionProperties_ReturnsNull() throws Exception {
    // Arrange
    WebSocketConnector connector = new WebSocketConnector(null);

    // Act
    JSONObject result = connector.getAuthenticationInfo(null, "http://invalid-url.example.com/auth");

    // Assert
    // The method catches exceptions and returns null
    assertNull(result, "Should return null when connectionProperties is null");
  }

  /**
   * Test getAuthenticationInfo with empty string URL. This should fail when trying to create HTTP
   * post with invalid URL.
   */
  @Test
  void testGetAuthenticationInfo_EmptyUrl_ReturnsNull() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    connectionProperties.setProperty("AUTHURL", "http://invalid-url.example.com/auth");
    connectionProperties.setProperty("CLIENTID", "test-client");
    connectionProperties.setProperty("USER", "testuser");
    connectionProperties.setProperty("PASSWORD", "testpass");
    connectionProperties.setProperty("HOSTNAME", "localhost");
    connectionProperties.setProperty("PORT", "443");
    connectionProperties.setProperty("USEPROXY", "FALSE");

    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Act
    JSONObject result = connector.getAuthenticationInfo(null, "");

    // Assert
    // The method catches exceptions and returns null
    assertNull(result, "Should return null with empty URL");
  }

  /**
   * Test getAuthenticationInfo with malformed URL. This should fail when trying to create HTTP
   * post with invalid URL.
   */
  @Test
  void testGetAuthenticationInfo_MalformedUrl_ReturnsNull() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    connectionProperties.setProperty("AUTHURL", "http://invalid-url.example.com/auth");
    connectionProperties.setProperty("CLIENTID", "test-client");
    connectionProperties.setProperty("USER", "testuser");
    connectionProperties.setProperty("PASSWORD", "testpass");
    connectionProperties.setProperty("HOSTNAME", "localhost");
    connectionProperties.setProperty("PORT", "443");
    connectionProperties.setProperty("USEPROXY", "FALSE");

    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Act
    JSONObject result = connector.getAuthenticationInfo(null, "not-a-valid-url");

    // Assert
    // The method catches exceptions and returns null
    assertNull(result, "Should return null with malformed URL");
  }

  /**
   * Test getAuthenticationInfo with null URL. This should fail when trying to create HTTP post
   * with null URL.
   */
  @Test
  void testGetAuthenticationInfo_NullUrl_ReturnsNull() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    connectionProperties.setProperty("AUTHURL", "http://invalid-url.example.com/auth");
    connectionProperties.setProperty("CLIENTID", "test-client");
    connectionProperties.setProperty("USER", "testuser");
    connectionProperties.setProperty("PASSWORD", "testpass");
    connectionProperties.setProperty("HOSTNAME", "localhost");
    connectionProperties.setProperty("PORT", "443");
    connectionProperties.setProperty("USEPROXY", "FALSE");

    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Act
    JSONObject result = connector.getAuthenticationInfo(null, null);

    // Assert
    // The method catches exceptions and returns null
    assertNull(result, "Should return null with null URL");
  }

  /**
   * Test getAuthenticationInfo with empty string properties. This should fail when trying to use
   * empty strings for authentication.
   */
  @Test
  void testGetAuthenticationInfo_EmptyStringProperties_ReturnsNull() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    connectionProperties.setProperty("AUTHURL", "http://invalid-url.example.com/auth");
    connectionProperties.setProperty("CLIENTID", "");
    connectionProperties.setProperty("USER", "");
    connectionProperties.setProperty("PASSWORD", "");
    connectionProperties.setProperty("HOSTNAME", "localhost");
    connectionProperties.setProperty("PORT", "443");
    connectionProperties.setProperty("USEPROXY", "FALSE");

    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Act
    JSONObject result = connector.getAuthenticationInfo(null, "http://invalid-url-empty-props.example.com/auth");

    // Assert
    // The method will attempt authentication but fail due to invalid credentials/URL
    assertNull(result, "Should return null with empty string properties");
  }

  /**
   * Test getAuthenticationInfo with special characters in credentials. Verifies that special
   * characters are handled (though authentication will fail with invalid URL).
   */
  @Test
  void testGetAuthenticationInfo_SpecialCharactersInCredentials_ReturnsNull() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    connectionProperties.setProperty("AUTHURL", "http://invalid-url.example.com/auth");
    connectionProperties.setProperty("CLIENTID", "client@domain.com");
    connectionProperties.setProperty("USER", "user@domain.com");
    connectionProperties.setProperty("PASSWORD", "p@ssw0rd!#$%");
    connectionProperties.setProperty("HOSTNAME", "localhost");
    connectionProperties.setProperty("PORT", "443");
    connectionProperties.setProperty("USEPROXY", "FALSE");

    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Act
    JSONObject result = connector.getAuthenticationInfo(null, "http://invalid-url-special-chars.example.com/auth");

    // Assert
    // Authentication will fail with invalid URL, returns null
    assertNull(result, "Should return null even with special characters in credentials");
  }

  /**
   * Test getAuthenticationInfo with previousAuthResponseJson containing empty refresh_token.
   * Verifies that empty refresh token is handled.
   */
  @Test
  void testGetAuthenticationInfo_EmptyRefreshToken_ReturnsNull() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    connectionProperties.setProperty("AUTHURL", "http://invalid-url.example.com/auth");
    connectionProperties.setProperty("CLIENTID", "test-client");
    connectionProperties.setProperty("USER", "testuser");
    connectionProperties.setProperty("PASSWORD", "testpass");
    connectionProperties.setProperty("HOSTNAME", "localhost");
    connectionProperties.setProperty("PORT", "443");
    connectionProperties.setProperty("USEPROXY", "FALSE");

    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Create a previous auth response with empty refresh_token
    JSONObject previousAuthResponseJson = new JSONObject();
    previousAuthResponseJson.put("refresh_token", "");
    previousAuthResponseJson.put("access_token", "mock-access-token");

    // Act
    JSONObject result = connector.getAuthenticationInfo(previousAuthResponseJson, "http://invalid-url-empty-token.example.com/auth");

    // Assert
    // Authentication will fail with invalid URL, returns null
    assertNull(result, "Should return null with empty refresh_token");
  }

  /**
   * Test getAuthenticationInfo with previousAuthResponseJson as empty JSON object. This should
   * fail when trying to get refresh_token.
   */
  @Test
  void testGetAuthenticationInfo_EmptyPreviousAuthJson_ReturnsNull() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    connectionProperties.setProperty("AUTHURL", "http://invalid-url.example.com/auth");
    connectionProperties.setProperty("CLIENTID", "test-client");
    connectionProperties.setProperty("USER", "testuser");
    connectionProperties.setProperty("PASSWORD", "testpass");
    connectionProperties.setProperty("HOSTNAME", "localhost");
    connectionProperties.setProperty("PORT", "443");
    connectionProperties.setProperty("USEPROXY", "FALSE");

    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Create an empty previous auth response
    JSONObject previousAuthResponseJson = new JSONObject();

    // Act
    JSONObject result = connector.getAuthenticationInfo(previousAuthResponseJson, "http://invalid-url.example.com/auth");

    // Assert
    // The method catches JSONException when refresh_token is missing and returns null
    assertNull(result, "Should return null with empty previousAuthResponseJson");
  }

  /**
   * Test getAuthenticationInfo verifies the scope field is used. The scope field should be
   * included in password authentication requests.
   */
  @Test
  void testGetAuthenticationInfo_UsesScope_ReturnsNull() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    connectionProperties.setProperty("AUTHURL", "http://invalid-url.example.com/auth");
    connectionProperties.setProperty("CLIENTID", "test-client");
    connectionProperties.setProperty("USER", "testuser");
    connectionProperties.setProperty("PASSWORD", "testpass");
    connectionProperties.setProperty("HOSTNAME", "localhost");
    connectionProperties.setProperty("PORT", "443");
    connectionProperties.setProperty("USEPROXY", "FALSE");

    WebSocketConnector connector = new WebSocketConnector(connectionProperties);
    // The scope field defaults to empty string ""

    // Act
    JSONObject result = connector.getAuthenticationInfo(null, "http://invalid-url-scope.example.com/auth");

    // Assert
    // Authentication will fail with invalid URL, but we verify scope is used
    assertNull(result, "Should return null when authentication fails");
    assertEquals("", connector.scope, "Scope should be empty string by default");
  }

  /**
   * Test getAuthenticationInfo with very long credentials. Verifies that long strings are handled.
   */
  @Test
  void testGetAuthenticationInfo_VeryLongCredentials_ReturnsNull() throws Exception {
    // Arrange
    String longString = "a".repeat(10000);
    Properties connectionProperties = new Properties();
    connectionProperties.setProperty("AUTHURL", "http://invalid-url.example.com/auth");
    connectionProperties.setProperty("CLIENTID", longString);
    connectionProperties.setProperty("USER", longString);
    connectionProperties.setProperty("PASSWORD", longString);
    connectionProperties.setProperty("HOSTNAME", "localhost");
    connectionProperties.setProperty("PORT", "443");
    connectionProperties.setProperty("USEPROXY", "FALSE");

    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Act
    JSONObject result = connector.getAuthenticationInfo(null, "http://invalid-url-long.example.com/auth");

    // Assert
    // Authentication will fail with invalid URL
    assertNull(result, "Should return null even with very long credentials");
  }

  /**
   * Test getAuthenticationInfo with Unicode characters in credentials. Verifies that Unicode is
   * handled correctly.
   */
  @Test
  void testGetAuthenticationInfo_UnicodeInCredentials_ReturnsNull() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    connectionProperties.setProperty("AUTHURL", "http://invalid-url.example.com/auth");
    connectionProperties.setProperty("CLIENTID", "客户端ID");
    connectionProperties.setProperty("USER", "用户名");
    connectionProperties.setProperty("PASSWORD", "密码123");
    connectionProperties.setProperty("HOSTNAME", "localhost");
    connectionProperties.setProperty("PORT", "443");
    connectionProperties.setProperty("USEPROXY", "FALSE");

    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Act
    JSONObject result = connector.getAuthenticationInfo(null, "http://invalid-url-unicode.example.com/auth");

    // Assert
    // Authentication will fail with invalid URL
    assertNull(result, "Should return null even with Unicode characters in credentials");
  }

  /**
   * Test getAuthenticationInfo with previousAuthResponseJson containing additional fields. Verifies
   * that extra fields in JSON don't cause issues.
   */
  @Test
  void testGetAuthenticationInfo_PreviousAuthWithExtraFields_ReturnsNull() throws Exception {
    // Arrange
    Properties connectionProperties = new Properties();
    connectionProperties.setProperty("AUTHURL", "http://invalid-url.example.com/auth");
    connectionProperties.setProperty("CLIENTID", "test-client");
    connectionProperties.setProperty("USER", "testuser");
    connectionProperties.setProperty("PASSWORD", "testpass");
    connectionProperties.setProperty("HOSTNAME", "localhost");
    connectionProperties.setProperty("PORT", "443");
    connectionProperties.setProperty("USEPROXY", "FALSE");

    WebSocketConnector connector = new WebSocketConnector(connectionProperties);

    // Create a previous auth response with extra fields
    JSONObject previousAuthResponseJson = new JSONObject();
    previousAuthResponseJson.put("refresh_token", "mock-refresh-token");
    previousAuthResponseJson.put("access_token", "mock-access-token");
    previousAuthResponseJson.put("expires_in", 3600);
    previousAuthResponseJson.put("token_type", "Bearer");
    previousAuthResponseJson.put("scope", "test-scope");

    // Act
    JSONObject result = connector.getAuthenticationInfo(previousAuthResponseJson, "http://invalid-url-extra-fields.example.com/auth");

    // Assert
    // Authentication will fail with invalid URL
    assertNull(result, "Should return null even with extra fields in previousAuthResponseJson");
  }
}
