package net.finmath.smartcontract.valuation.service.websocket.handler;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.adapter.standard.StandardWebSocketSession;
import org.springframework.web.socket.handler.ConcurrentWebSocketSessionDecorator;
import org.springframework.web.socket.handler.WebSocketSessionDecorator;

@ContextConfiguration(classes = {ValuationHandler.class})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
class ValuationHandlerDiffblueTest {
  @Autowired private ValuationHandler valuationHandler;

  /**
   * Test {@link ValuationHandler#handleMessage(WebSocketSession, WebSocketMessage)}.
   *
   * <ul>
   *   <li>Given {@link IllegalStateException#IllegalStateException()}.
   *   <li>Then calls {@link TextMessage#getPayload()}.
   * </ul>
   *
   * <p>Method under test: {@link ValuationHandler#handleMessage(WebSocketSession,
   * WebSocketMessage)}
   */
  @Test
  @DisplayName(
      "Test handleMessage(WebSocketSession, WebSocketMessage); given IllegalStateException(); then calls getPayload()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ValuationHandler.handleMessage(WebSocketSession, WebSocketMessage)"})
  void testHandleMessage_givenIllegalStateException_thenCallsGetPayload() throws Exception {
    // Arrange
    HttpHeaders headers = new HttpHeaders();
    HashMap<String, Object> attributes = new HashMap<>();
    InetSocketAddress localAddress = InetSocketAddress.createUnresolved("localhost", 8080);

    StandardWebSocketSession delegate =
        new StandardWebSocketSession(
            headers,
            attributes,
            localAddress,
            InetSocketAddress.createUnresolved("localhost", 8080));
    WebSocketSessionDecorator session =
        new WebSocketSessionDecorator(new ConcurrentWebSocketSessionDecorator(delegate, 3, 3));

    TextMessage message = mock(TextMessage.class);
    when(message.getPayload()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(
        IllegalStateException.class, () -> valuationHandler.handleMessage(session, message));
    verify(message).getPayload();
  }

  /**
   * Test {@link ValuationHandler#handleMessage(WebSocketSession, WebSocketMessage)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link ValuationHandler#handleMessage(WebSocketSession,
   * WebSocketMessage)}
   */
  @Test
  @DisplayName(
      "Test handleMessage(WebSocketSession, WebSocketMessage); then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ValuationHandler.handleMessage(WebSocketSession, WebSocketMessage)"})
  void testHandleMessage_thenThrowIllegalStateException() throws Exception {
    // Arrange
    HttpHeaders headers = new HttpHeaders();
    HashMap<String, Object> attributes = new HashMap<>();
    InetSocketAddress localAddress = InetSocketAddress.createUnresolved("localhost", 8080);

    StandardWebSocketSession delegate =
        new StandardWebSocketSession(
            headers,
            attributes,
            localAddress,
            InetSocketAddress.createUnresolved("localhost", 8080));
    WebSocketSessionDecorator session =
        new WebSocketSessionDecorator(new ConcurrentWebSocketSessionDecorator(delegate, 3, 3));

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () ->
            valuationHandler.handleMessage(
                session, new BinaryMessage(ByteBuffer.wrap("AXAXAXAX".getBytes("UTF-8")))));
  }

  /**
   * Test {@link ValuationHandler#supportsPartialMessages()}.
   *
   * <p>Method under test: {@link ValuationHandler#supportsPartialMessages()}
   */
  @Test
  @DisplayName("Test supportsPartialMessages()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ValuationHandler.supportsPartialMessages()"})
  void testSupportsPartialMessages() {
    // Arrange, Act and Assert
    assertFalse(valuationHandler.supportsPartialMessages());
  }
}
