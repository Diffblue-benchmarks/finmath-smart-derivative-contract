package net.finmath.smartcontract.valuation.service.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class RefinitivConfigDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link RefinitivConfig#setAuthUrl(String)}
   *   <li>{@link RefinitivConfig#setClientId(String)}
   *   <li>{@link RefinitivConfig#setHostName(String)}
   *   <li>{@link RefinitivConfig#setPassword(String)}
   *   <li>{@link RefinitivConfig#setPort(int)}
   *   <li>{@link RefinitivConfig#setProxyHost(String)}
   *   <li>{@link RefinitivConfig#setProxyPassword(String)}
   *   <li>{@link RefinitivConfig#setProxyPort(int)}
   *   <li>{@link RefinitivConfig#setProxyUser(String)}
   *   <li>{@link RefinitivConfig#setUseProxy(String)}
   *   <li>{@link RefinitivConfig#setUser(String)}
   *   <li>{@link RefinitivConfig#getAuthUrl()}
   *   <li>{@link RefinitivConfig#getClientId()}
   *   <li>{@link RefinitivConfig#getHostName()}
   *   <li>{@link RefinitivConfig#getPassword()}
   *   <li>{@link RefinitivConfig#getPort()}
   *   <li>{@link RefinitivConfig#getProxyHost()}
   *   <li>{@link RefinitivConfig#getProxyPassword()}
   *   <li>{@link RefinitivConfig#getProxyPort()}
   *   <li>{@link RefinitivConfig#getProxyUser()}
   *   <li>{@link RefinitivConfig#getUseProxy()}
   *   <li>{@link RefinitivConfig#getUser()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String RefinitivConfig.getAuthUrl()", "String RefinitivConfig.getClientId()",
      "String RefinitivConfig.getHostName()", "String RefinitivConfig.getPassword()", "int RefinitivConfig.getPort()",
      "String RefinitivConfig.getProxyHost()", "String RefinitivConfig.getProxyPassword()",
      "int RefinitivConfig.getProxyPort()", "String RefinitivConfig.getProxyUser()",
      "String RefinitivConfig.getUseProxy()", "String RefinitivConfig.getUser()",
      "void RefinitivConfig.setAuthUrl(String)", "void RefinitivConfig.setClientId(String)",
      "void RefinitivConfig.setHostName(String)", "void RefinitivConfig.setPassword(String)",
      "void RefinitivConfig.setPort(int)", "void RefinitivConfig.setProxyHost(String)",
      "void RefinitivConfig.setProxyPassword(String)", "void RefinitivConfig.setProxyPort(int)",
      "void RefinitivConfig.setProxyUser(String)", "void RefinitivConfig.setUseProxy(String)",
      "void RefinitivConfig.setUser(String)"})
  void testGettersAndSetters() {
    // Arrange
    RefinitivConfig refinitivConfig = new RefinitivConfig();

    // Act
    refinitivConfig.setAuthUrl("https://example.org/example");
    refinitivConfig.setClientId("42");
    refinitivConfig.setHostName("Host Name");
    refinitivConfig.setPassword("iloveyou");
    refinitivConfig.setPort(8080);
    refinitivConfig.setProxyHost("localhost");
    refinitivConfig.setProxyPassword("iloveyou");
    refinitivConfig.setProxyPort(8080);
    refinitivConfig.setProxyUser("Proxy User");
    refinitivConfig.setUseProxy("Use Proxy");
    refinitivConfig.setUser("User");
    String actualAuthUrl = refinitivConfig.getAuthUrl();
    String actualClientId = refinitivConfig.getClientId();
    String actualHostName = refinitivConfig.getHostName();
    String actualPassword = refinitivConfig.getPassword();
    int actualPort = refinitivConfig.getPort();
    String actualProxyHost = refinitivConfig.getProxyHost();
    String actualProxyPassword = refinitivConfig.getProxyPassword();
    int actualProxyPort = refinitivConfig.getProxyPort();
    String actualProxyUser = refinitivConfig.getProxyUser();
    String actualUseProxy = refinitivConfig.getUseProxy();

    // Assert
    assertEquals("42", actualClientId);
    assertEquals("Host Name", actualHostName);
    assertEquals("Proxy User", actualProxyUser);
    assertEquals("Use Proxy", actualUseProxy);
    assertEquals("User", refinitivConfig.getUser());
    assertEquals("https://example.org/example", actualAuthUrl);
    assertEquals("iloveyou", actualPassword);
    assertEquals("iloveyou", actualProxyPassword);
    assertEquals("localhost", actualProxyHost);
    assertEquals(8080, actualPort);
    assertEquals(8080, actualProxyPort);
  }
}
