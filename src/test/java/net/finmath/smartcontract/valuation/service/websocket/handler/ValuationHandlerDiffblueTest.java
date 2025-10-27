package net.finmath.smartcontract.valuation.service.websocket.handler;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.HashMap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.adapter.standard.StandardWebSocketSession;
import org.springframework.web.socket.handler.ConcurrentWebSocketSessionDecorator;
import org.springframework.web.socket.handler.WebSocketSessionDecorator;

@ContextConfiguration(classes = {ValuationHandler.class})
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ValuationHandlerDiffblueTest {
  @Autowired
  private ValuationHandler valuationHandler;

  /**
   * Method under test:
   * {@link ValuationHandler#handleMessage(WebSocketSession, WebSocketMessage)}
   */
  @Test
  void testHandleMessage() throws Exception {
    // Arrange
    HttpHeaders headers = new HttpHeaders();
    HashMap<String, Object> attributes = new HashMap<>();
    InetSocketAddress localAddress = InetSocketAddress.createUnresolved("foo", 1);
    WebSocketSessionDecorator session = new WebSocketSessionDecorator(new ConcurrentWebSocketSessionDecorator(
        new StandardWebSocketSession(headers, attributes, localAddress, InetSocketAddress.createUnresolved("foo", 1)),
        3, 3));

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> valuationHandler.handleMessage(session,
        new BinaryMessage(ByteBuffer.wrap("AXAXAXAX".getBytes("UTF-8")))));
  }

  /**
   * Method under test: {@link ValuationHandler#supportsPartialMessages()}
   */
  @Test
  void testSupportsPartialMessages() {
    // Arrange, Act and Assert
    assertFalse(valuationHandler.supportsPartialMessages());
  }
}
