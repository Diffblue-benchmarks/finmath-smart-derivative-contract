package net.finmath.smartcontract.valuation.service.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class RefinitivConfigDiffblueTest {
  /**
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

    // Assert that nothing has changed
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
