package net.finmath.smartcontract.valuation.service.websocket.client;

import static org.junit.jupiter.api.Assertions.assertThrows;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.IOException;
import java.net.URI;
import net.finmath.smartcontract.model.SDCException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WebSocketClientEndpointDiffblueTest {
  @InjectMocks
  private String string;

  @InjectMocks
  private URI uRI;

  @InjectMocks
  private WebSocketClientEndpoint webSocketClientEndpoint;

  /**
   * Test {@link WebSocketClientEndpoint#getUserSession()}.
   * <p>
   * Method under test: {@link WebSocketClientEndpoint#getUserSession()}
   */
  @Test
  @DisplayName("Test getUserSession()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"jakarta.websocket.Session WebSocketClientEndpoint.getUserSession()"})
  void testGetUserSession() {
    // Arrange, Act and Assert
    assertThrows(SDCException.class, () -> webSocketClientEndpoint.getUserSession());
  }

  /**
   * Test {@link WebSocketClientEndpoint#asObservable()}.
   * <p>
   * Method under test: {@link WebSocketClientEndpoint#asObservable()}
   */
  @Test
  @DisplayName("Test asObservable()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"io.reactivex.rxjava3.core.Observable WebSocketClientEndpoint.asObservable()"})
  void testAsObservable() {
    // Arrange, Act and Assert
    assertThrows(SDCException.class, () -> webSocketClientEndpoint.asObservable());
  }

  /**
   * Test {@link WebSocketClientEndpoint#sendTextMessage(String)}.
   * <p>
   * Method under test: {@link WebSocketClientEndpoint#sendTextMessage(String)}
   */
  @Test
  @DisplayName("Test sendTextMessage(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void WebSocketClientEndpoint.sendTextMessage(String)"})
  void testSendTextMessage() throws IOException {
    // Arrange, Act and Assert
    assertThrows(SDCException.class, () -> webSocketClientEndpoint.sendTextMessage("Not all who wander are lost"));
  }
}
