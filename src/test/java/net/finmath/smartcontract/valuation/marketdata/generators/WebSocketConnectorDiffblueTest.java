package net.finmath.smartcontract.valuation.marketdata.generators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.Properties;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

class WebSocketConnectorDiffblueTest {
  /**
   * Method under test: {@link WebSocketConnector#initAuthJson()}
   */
  @Test
  void testInitAuthJson() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    WebSocketConnector webSocketConnector = new WebSocketConnector(new Properties());

    // Act and Assert
    assertSame(webSocketConnector, webSocketConnector.initAuthJson());
  }

  /**
   * Method under test: {@link WebSocketConnector#initAuthJson()}
   */
  @Test
  void testInitAuthJson2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    WebSocketConnector webSocketConnector = new WebSocketConnector(new Properties());
    webSocketConnector.authJson = mock(JSONObject.class);

    // Act and Assert
    assertSame(webSocketConnector, webSocketConnector.initAuthJson());
  }

  /**
   * Method under test:
   * {@link WebSocketConnector#getAuthenticationInfo(JSONObject, String)}
   */
  @Test
  void testGetAuthenticationInfo() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    WebSocketConnector webSocketConnector = new WebSocketConnector(new Properties());

    // Act and Assert
    assertNull(webSocketConnector.getAuthenticationInfo(new JSONObject(), "https://example.org/example"));
  }

  /**
   * Method under test:
   * {@link WebSocketConnector#getAuthenticationInfo(JSONObject, String)}
   */
  @Test
  void testGetAuthenticationInfo2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange, Act and Assert
    assertNull((new WebSocketConnector(new Properties())).getAuthenticationInfo(null, "https://example.org/example"));
  }

  /**
   * Method under test:
   * {@link WebSocketConnector#getAuthenticationInfo(JSONObject, String)}
   */
  @Test
  void testGetAuthenticationInfo3() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange, Act and Assert
    assertNull((new WebSocketConnector(new Properties())).getAuthenticationInfo(mock(JSONObject.class),
        "https://example.org/example"));
  }

  /**
   * Method under test:
   * {@link WebSocketConnector#getAuthenticationInfo(JSONObject, String)}
   */
  @Test
  void testGetAuthenticationInfo4() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    WebSocketConnector webSocketConnector = new WebSocketConnector(new Properties());

    // Act and Assert
    assertNull(
        webSocketConnector.getAuthenticationInfo(new JSONObject(), "Refinitiv Data Platform authentication failure:"));
  }

  /**
   * Method under test:
   * {@link WebSocketConnector#getAuthenticationInfo(JSONObject, String)}
   */
  @Test
  void testGetAuthenticationInfo5() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    WebSocketConnector webSocketConnector = new WebSocketConnector(new Properties());

    // Act and Assert
    assertNull(webSocketConnector.getAuthenticationInfo(new JSONObject(), ""));
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link WebSocketConnector#getAuthJson()}
   *   <li>{@link WebSocketConnector#getPosition()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() throws Exception {
    // Arrange
    WebSocketConnector webSocketConnector = new WebSocketConnector(new Properties());

    // Act
    JSONObject actualAuthJson = webSocketConnector.getAuthJson();
    webSocketConnector.getPosition();

    // Assert
    assertNull(actualAuthJson);
  }

  /**
   * Method under test: {@link WebSocketConnector#WebSocketConnector(Properties)}
   */
  @Test
  void testNewWebSocketConnector() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange and Act
    WebSocketConnector actualWebSocketConnector = new WebSocketConnector(new Properties());

    // Assert
    assertEquals("", actualWebSocketConnector.scope);
    assertNull(actualWebSocketConnector.getAuthJson());
    assertTrue(actualWebSocketConnector.connectionProperties.isEmpty());
  }
}
