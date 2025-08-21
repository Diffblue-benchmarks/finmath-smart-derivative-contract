package net.finmath.smartcontract.valuation.service.websocket.client;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import jakarta.websocket.ClientEndpointConfig;
import jakarta.websocket.ClientEndpointConfig.Builder;
import jakarta.websocket.ClientEndpointConfig.Configurator;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.MessageHandler;
import jakarta.websocket.Session;
import java.io.IOException;
import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import javax.net.ssl.SSLContext;
import net.finmath.smartcontract.model.SDCException;
import org.apache.tomcat.websocket.WsSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WebSocketClientEndpointDiffblueTest {
  @InjectMocks private URI uRI;

  @InjectMocks private WebSocketClientEndpoint webSocketClientEndpoint;

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
    // Arrange, Act and Assert
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
    // Arrange, Act and Assert
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
    // Arrange, Act and Assert
    assertThrows(
        SDCException.class,
        () -> webSocketClientEndpoint.sendTextMessage("Not all who wander are lost"));
  }

  /**
   * Test {@link WebSocketClientEndpoint#onOpen(Session, EndpointConfig)}.
   *
   * <ul>
   *   <li>When {@link WsSession} {@link WsSession#addMessageHandler(MessageHandler)} does nothing.
   *   <li>Then calls {@link WsSession#addMessageHandler(MessageHandler)}.
   * </ul>
   *
   * <p>Method under test: {@link WebSocketClientEndpoint#onOpen(Session, EndpointConfig)}
   */
  @Test
  @DisplayName(
      "Test onOpen(Session, EndpointConfig); when WsSession addMessageHandler(MessageHandler) does nothing; then calls addMessageHandler(MessageHandler)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void WebSocketClientEndpoint.onOpen(Session, EndpointConfig)"})
  void testOnOpen_whenWsSessionAddMessageHandlerDoesNothing_thenCallsAddMessageHandler()
      throws NoSuchAlgorithmException {
    // Arrange
    WsSession session = mock(WsSession.class);
    doNothing().when(session).addMessageHandler(Mockito.<MessageHandler>any());
    Builder createResult = Builder.create();
    Builder configuratorResult = createResult.configurator(new Configurator());
    Builder decodersResult = configuratorResult.decoders(new ArrayList<>());
    Builder encodersResult = decodersResult.encoders(new ArrayList<>());
    Builder extensionsResult = encodersResult.extensions(new ArrayList<>());
    Builder preferredSubprotocolsResult = extensionsResult.preferredSubprotocols(new ArrayList<>());
    ClientEndpointConfig config =
        preferredSubprotocolsResult.sslContext(SSLContext.getDefault()).build();

    // Act
    webSocketClientEndpoint.onOpen(session, config);

    // Assert
    verify(session).addMessageHandler(isA(MessageHandler.class));
  }
}
