package net.finmath.smartcontract.valuation.service.websocket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import jakarta.websocket.server.ServerContainer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.ServletWebSocketHandlerRegistration;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

class WebSocketConfigDiffblueTest {
  /**
   * Test {@link WebSocketConfig#registerWebSocketHandlers(WebSocketHandlerRegistry)}.
   *
   * <ul>
   *   <li>Then calls {@link WebSocketHandlerRegistry#addHandler(WebSocketHandler, String[])}.
   * </ul>
   *
   * <p>Method under test: {@link
   * WebSocketConfig#registerWebSocketHandlers(WebSocketHandlerRegistry)}
   */
  @Test
  @DisplayName(
      "Test registerWebSocketHandlers(WebSocketHandlerRegistry); then calls addHandler(WebSocketHandler, String[])")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void WebSocketConfig.registerWebSocketHandlers(WebSocketHandlerRegistry)"})
  void testRegisterWebSocketHandlers_thenCallsAddHandler() {
    // Arrange
    WebSocketConfig webSocketConfig = new WebSocketConfig();

    WebSocketHandlerRegistry registry = mock(WebSocketHandlerRegistry.class);
    when(registry.addHandler(Mockito.<WebSocketHandler>any(), isA(String[].class)))
        .thenReturn(new ServletWebSocketHandlerRegistration());

    // Act
    webSocketConfig.registerWebSocketHandlers(registry);

    // Assert
    verify(registry).addHandler(isA(WebSocketHandler.class), isA(String[].class));
  }

  /**
   * Test {@link WebSocketConfig#createWebSocketContainer()}.
   *
   * <ul>
   *   <li>Given {@link WebSocketConfig} (default constructor).
   *   <li>Then return Object is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link WebSocketConfig#createWebSocketContainer()}
   */
  @Test
  @DisplayName(
      "Test createWebSocketContainer(); given WebSocketConfig (default constructor); then return Object is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ServletServerContainerFactoryBean WebSocketConfig.createWebSocketContainer()"
  })
  void testCreateWebSocketContainer_givenWebSocketConfig_thenReturnObjectIsNull() {
    // Arrange and Act
    ServletServerContainerFactoryBean actualCreateWebSocketContainerResult =
        new WebSocketConfig().createWebSocketContainer();

    // Assert
    assertNull(actualCreateWebSocketContainerResult.getObject());
    assertNull(actualCreateWebSocketContainerResult.getAsyncSendTimeout());
    assertNull(actualCreateWebSocketContainerResult.getMaxSessionIdleTimeout());
    assertEquals(
        1048576, actualCreateWebSocketContainerResult.getMaxBinaryMessageBufferSize().intValue());
    assertEquals(
        1048576, actualCreateWebSocketContainerResult.getMaxTextMessageBufferSize().intValue());
    assertTrue(actualCreateWebSocketContainerResult.isSingleton());
    Class<ServerContainer> expectedObjectType = ServerContainer.class;
    assertEquals(expectedObjectType, actualCreateWebSocketContainerResult.getObjectType());
  }
}
