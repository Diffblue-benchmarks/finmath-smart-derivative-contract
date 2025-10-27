package net.finmath.smartcontract.valuation.service.websocket;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.ServletWebSocketHandlerRegistration;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

class WebSocketConfigDiffblueTest {
  /**
   * Method under test:
   * {@link WebSocketConfig#registerWebSocketHandlers(WebSocketHandlerRegistry)}
   */
  @Test
  void testRegisterWebSocketHandlers() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
}
