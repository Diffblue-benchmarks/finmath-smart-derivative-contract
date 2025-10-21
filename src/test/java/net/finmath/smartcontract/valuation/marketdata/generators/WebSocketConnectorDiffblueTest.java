package net.finmath.smartcontract.valuation.marketdata.generators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.Properties;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WebSocketConnectorDiffblueTest {
  @InjectMocks
  private Properties properties;

  @InjectMocks
  private WebSocketConnector webSocketConnector;

  /**
   * Test {@link WebSocketConnector#WebSocketConnector(Properties)}.
   * <p>
   * Method under test: {@link WebSocketConnector#WebSocketConnector(Properties)}
   */
  @Test
  @DisplayName("Test new WebSocketConnector(Properties)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void WebSocketConnector.<init>(Properties)"})
  void testNewWebSocketConnector() throws Exception {
    // Arrange and Act
    WebSocketConnector actualWebSocketConnector = new WebSocketConnector(new Properties());

    // Assert
    assertEquals("", actualWebSocketConnector.scope);
    assertNull(actualWebSocketConnector.getAuthJson());
    assertTrue(actualWebSocketConnector.connectionProperties.isEmpty());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link WebSocketConnector#getAuthJson()}
   *   <li>{@link WebSocketConnector#getPosition()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"JSONObject WebSocketConnector.getAuthJson()", "String WebSocketConnector.getPosition()"})
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
   * Test {@link WebSocketConnector#initAuthJson()}.
   * <p>
   * Method under test: {@link WebSocketConnector#initAuthJson()}
   */
  @Test
  @DisplayName("Test initAuthJson()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"WebSocketConnector WebSocketConnector.initAuthJson()"})
  void testInitAuthJson() {
    // Arrange, Act and Assert
    assertSame(webSocketConnector, webSocketConnector.initAuthJson());
  }

  /**
   * Test {@link WebSocketConnector#getAuthenticationInfo(JSONObject, String)}.
   * <ul>
   *   <li>When {@link JSONObject#JSONObject()}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WebSocketConnector#getAuthenticationInfo(JSONObject, String)}
   */
  @Test
  @DisplayName("Test getAuthenticationInfo(JSONObject, String); when JSONObject(); then return 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"JSONObject WebSocketConnector.getAuthenticationInfo(JSONObject, String)"})
  void testGetAuthenticationInfo_whenJSONObject_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(webSocketConnector.getAuthenticationInfo(new JSONObject(), "https://example.org/example"));
  }

  /**
   * Test {@link WebSocketConnector#getAuthenticationInfo(JSONObject, String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WebSocketConnector#getAuthenticationInfo(JSONObject, String)}
   */
  @Test
  @DisplayName("Test getAuthenticationInfo(JSONObject, String); when 'null'; then return 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"JSONObject WebSocketConnector.getAuthenticationInfo(JSONObject, String)"})
  void testGetAuthenticationInfo_whenNull_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(webSocketConnector.getAuthenticationInfo(null, "https://example.org/example"));
  }

  /**
   * Test {@link WebSocketConnector#getAuthenticationInfo(JSONObject, String)}.
   * <ul>
   *   <li>When {@code Refinitiv Data Platform authentication failure:}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WebSocketConnector#getAuthenticationInfo(JSONObject, String)}
   */
  @Test
  @DisplayName("Test getAuthenticationInfo(JSONObject, String); when 'Refinitiv Data Platform authentication failure:'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"JSONObject WebSocketConnector.getAuthenticationInfo(JSONObject, String)"})
  void testGetAuthenticationInfo_whenRefinitivDataPlatformAuthenticationFailure() {
    // Arrange, Act and Assert
    assertNull(
        webSocketConnector.getAuthenticationInfo(new JSONObject(), "Refinitiv Data Platform authentication failure:"));
  }
}
