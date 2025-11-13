package net.finmath.smartcontract.valuation.service.websocket.client;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import jakarta.websocket.Endpoint;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.MessageHandler;
import jakarta.websocket.Session;
import java.io.IOException;
import java.nio.file.Paths;
import net.finmath.smartcontract.model.SDCException;
import org.apache.tomcat.websocket.WsSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.socket.server.standard.ServerEndpointRegistration;

class WebSocketClientEndpointDiffblueTest {
  /**
   * Test {@link WebSocketClientEndpoint#getUserSession()}.
   *
   * <p>Method under test: {@link WebSocketClientEndpoint#getUserSession()}
   */
  @Test
  @DisplayName("Test getUserSession()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Session WebSocketClientEndpoint.getUserSession()"})
  void testGetUserSession() {
    // Arrange
    WebSocketClientEndpoint webSocketClientEndpoint =
        new WebSocketClientEndpoint(
            Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri(),
            "User",
            "iloveyou");

    // Act and Assert
    assertThrows(SDCException.class, () -> webSocketClientEndpoint.getUserSession());
  }

  /**
   * Test {@link WebSocketClientEndpoint#asObservable()}.
   *
   * <p>Method under test: {@link WebSocketClientEndpoint#asObservable()}
   */
  @Test
  @DisplayName("Test asObservable()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"io.reactivex.rxjava3.core.Observable WebSocketClientEndpoint.asObservable()"})
  void testAsObservable() {
    // Arrange
    WebSocketClientEndpoint webSocketClientEndpoint =
        new WebSocketClientEndpoint(
            Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri(),
            "User",
            "iloveyou");

    // Act and Assert
    assertThrows(SDCException.class, () -> webSocketClientEndpoint.asObservable());
  }

  /**
   * Test {@link WebSocketClientEndpoint#sendTextMessage(String)}.
   *
   * <p>Method under test: {@link WebSocketClientEndpoint#sendTextMessage(String)}
   */
  @Test
  @DisplayName("Test sendTextMessage(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void WebSocketClientEndpoint.sendTextMessage(String)"})
  void testSendTextMessage() throws IOException {
    // Arrange
    WebSocketClientEndpoint webSocketClientEndpoint =
        new WebSocketClientEndpoint(
            Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri(),
            "User",
            "iloveyou");

    // Act and Assert
    assertThrows(
        SDCException.class,
        () -> webSocketClientEndpoint.sendTextMessage("Not all who wander are lost"));
  }

  /**
   * Test {@link WebSocketClientEndpoint#onOpen(Session, EndpointConfig)}.
   *
   * <ul>
   *   <li>Then calls {@link WsSession#addMessageHandler(MessageHandler)}.
   * </ul>
   *
   * <p>Method under test: {@link WebSocketClientEndpoint#onOpen(Session, EndpointConfig)}
   */
  @Test
  @DisplayName("Test onOpen(Session, EndpointConfig); then calls addMessageHandler(MessageHandler)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void WebSocketClientEndpoint.onOpen(Session, EndpointConfig)"})
  void testOnOpen_thenCallsAddMessageHandler() {
    // Arrange
    WebSocketClientEndpoint webSocketClientEndpoint =
        new WebSocketClientEndpoint(
            Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri(),
            "User",
            "iloveyou");

    WsSession session = mock(WsSession.class);
    doNothing().when(session).addMessageHandler(Mockito.<MessageHandler>any());
    Class<Endpoint> endpointClass = Endpoint.class;

    // Act
    webSocketClientEndpoint.onOpen(session, new ServerEndpointRegistration("Path", endpointClass));

    // Assert
    verify(session).addMessageHandler(isA(MessageHandler.class));
  }
}
