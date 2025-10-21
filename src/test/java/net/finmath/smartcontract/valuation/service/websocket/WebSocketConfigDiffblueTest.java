package net.finmath.smartcontract.valuation.service.websocket;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.ServletWebSocketHandlerRegistration;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@ExtendWith(MockitoExtension.class)
class WebSocketConfigDiffblueTest {
  @InjectMocks
  private WebSocketConfig webSocketConfig;

  /**
   * Test {@link WebSocketConfig#registerWebSocketHandlers(WebSocketHandlerRegistry)}.
   * <ul>
   *   <li>Then calls {@link WebSocketHandlerRegistry#addHandler(WebSocketHandler, String[])}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WebSocketConfig#registerWebSocketHandlers(WebSocketHandlerRegistry)}
   */
  @Test
  @DisplayName("Test registerWebSocketHandlers(WebSocketHandlerRegistry); then calls addHandler(WebSocketHandler, String[])")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void WebSocketConfig.registerWebSocketHandlers(WebSocketHandlerRegistry)"})
  void testRegisterWebSocketHandlers_thenCallsAddHandler() {
    // Arrange
    WebSocketHandlerRegistry registry = mock(WebSocketHandlerRegistry.class);
    when(registry.addHandler(Mockito.<WebSocketHandler>any(), isA(String[].class)))
        .thenReturn(new ServletWebSocketHandlerRegistration());

    // Act
    webSocketConfig.registerWebSocketHandlers(registry);

    // Assert
    verify(registry).addHandler(isA(WebSocketHandler.class), isA(String[].class));
  }
}
