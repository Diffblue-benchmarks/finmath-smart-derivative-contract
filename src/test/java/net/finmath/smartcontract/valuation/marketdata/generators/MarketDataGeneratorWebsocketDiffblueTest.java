package net.finmath.smartcontract.valuation.marketdata.generators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.neovisionaries.ws.client.WebSocket;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.internal.operators.observable.ObservableMap;
import io.reactivex.rxjava3.internal.operators.observable.ObservableToListSingle;
import io.reactivex.rxjava3.internal.operators.single.SingleTimeInterval;
import io.reactivex.rxjava3.schedulers.Timed;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.finmath.smartcontract.model.MarketDataList;
import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationDataItem;
import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationDataItem.Spec;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class MarketDataGeneratorWebsocketDiffblueTest {
  /**
   * Test {@link MarketDataGeneratorWebsocket#allQuotesRetrieved()}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MarketDataGeneratorWebsocket#allQuotesRetrieved()}
   */
  @Test
  @DisplayName("Test allQuotesRetrieved(); then return 'false'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean MarketDataGeneratorWebsocket.allQuotesRetrieved()"})
  void testAllQuotesRetrieved_thenReturnFalse() {
    // Arrange
    ArrayList<Spec> itemList = new ArrayList<>();
    itemList.add(new Spec("Key", "Curve Name", "Product Name", "Maturity"));

    // Act and Assert
    assertFalse((new MarketDataGeneratorWebsocket(new JSONObject(), "Position", itemList)).allQuotesRetrieved());
  }

  /**
   * Test {@link MarketDataGeneratorWebsocket#allQuotesRetrieved()}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MarketDataGeneratorWebsocket#allQuotesRetrieved()}
   */
  @Test
  @DisplayName("Test allQuotesRetrieved(); then return 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean MarketDataGeneratorWebsocket.allQuotesRetrieved()"})
  void testAllQuotesRetrieved_thenReturnTrue() {
    // Arrange
    JSONObject authJson = new JSONObject();

    // Act and Assert
    assertTrue((new MarketDataGeneratorWebsocket(authJson, "Position", new ArrayList<>())).allQuotesRetrieved());
  }

  /**
   * Test {@link MarketDataGeneratorWebsocket#onConnected(WebSocket, Map)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link WebSocket} {@link WebSocket#sendText(String)} return {@code null}.</li>
   *   <li>Then calls {@link WebSocket#sendText(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MarketDataGeneratorWebsocket#onConnected(WebSocket, Map)}
   */
  @Test
  @DisplayName("Test onConnected(WebSocket, Map); given 'null'; when WebSocket sendText(String) return 'null'; then calls sendText(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void MarketDataGeneratorWebsocket.onConnected(WebSocket, Map)"})
  void testOnConnected_givenNull_whenWebSocketSendTextReturnNull_thenCallsSendText() throws Exception {
    // Arrange
    JSONObject authJson = mock(JSONObject.class);
    when(authJson.getString(Mockito.<String>any())).thenReturn("String");
    MarketDataGeneratorWebsocket marketDataGeneratorWebsocket = new MarketDataGeneratorWebsocket(authJson, "Position",
        new ArrayList<>());
    WebSocket websocket = mock(WebSocket.class);
    when(websocket.sendText(Mockito.<String>any())).thenReturn(null);

    // Act
    marketDataGeneratorWebsocket.onConnected(websocket, new HashMap<>());

    // Assert
    verify(websocket).sendText(eq(
        "{\"ID\":1,\"Domain\":\"Login\",\"Key\":{\"Elements\":{\"ApplicationId\":\"256\",\"Position\":\"Position\",\"AuthenticationToken\":\"String\"},\"NameType\":\"AuthnToken\"}}"));
    verify(authJson).getString(eq("access_token"));
  }

  /**
   * Test {@link MarketDataGeneratorWebsocket#asObservable()}.
   * <ul>
   *   <li>Then timestamp timestamp return {@link ObservableMap}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MarketDataGeneratorWebsocket#asObservable()}
   */
  @Test
  @DisplayName("Test asObservable(); then timestamp timestamp return ObservableMap")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Observable MarketDataGeneratorWebsocket.asObservable()"})
  void testAsObservable_thenTimestampTimestampReturnObservableMap() {
    // Arrange
    JSONObject authJson = new JSONObject();

    // Act
    Observable<MarketDataList> actualAsObservableResult = (new MarketDataGeneratorWebsocket(authJson, "Position",
        new ArrayList<>())).asObservable();

    // Assert
    Observable<Timed<MarketDataList>> timestampResult = actualAsObservableResult.timestamp();
    assertTrue(timestampResult.timestamp() instanceof ObservableMap);
    Subject<MarketDataList> toSerializedResult = ((PublishSubject<MarketDataList>) actualAsObservableResult)
        .toSerialized();
    assertTrue(toSerializedResult.timestamp() instanceof ObservableMap);
    assertTrue(timestampResult instanceof ObservableMap);
    assertTrue(timestampResult.toList() instanceof ObservableToListSingle);
    assertTrue(toSerializedResult.toList() instanceof ObservableToListSingle);
    Single<List<MarketDataList>> toListResult = actualAsObservableResult.toList();
    assertTrue(toListResult instanceof ObservableToListSingle);
    assertTrue(toListResult.timestamp() instanceof SingleTimeInterval);
    assertTrue(actualAsObservableResult instanceof PublishSubject);
    assertNull(((PublishSubject<MarketDataList>) actualAsObservableResult).getThrowable());
    assertFalse(((PublishSubject<MarketDataList>) actualAsObservableResult).hasComplete());
    assertFalse(((PublishSubject<MarketDataList>) actualAsObservableResult).hasObservers());
    assertFalse(((PublishSubject<MarketDataList>) actualAsObservableResult).hasThrowable());
    assertSame(toSerializedResult.toSerialized(), toSerializedResult.toSerialized());
  }

  /**
   * Test {@link MarketDataGeneratorWebsocket#closeStreamsAndLogoff(WebSocket)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>Then calls {@link WebSocket#sendText(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MarketDataGeneratorWebsocket#closeStreamsAndLogoff(WebSocket)}
   */
  @Test
  @DisplayName("Test closeStreamsAndLogoff(WebSocket); given 'null'; then calls sendText(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void MarketDataGeneratorWebsocket.closeStreamsAndLogoff(WebSocket)"})
  void testCloseStreamsAndLogoff_givenNull_thenCallsSendText() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    JSONObject authJson = new JSONObject();
    MarketDataGeneratorWebsocket marketDataGeneratorWebsocket = new MarketDataGeneratorWebsocket(authJson, "Position",
        new ArrayList<>());
    WebSocket webSocket = mock(WebSocket.class);
    when(webSocket.sendText(Mockito.<String>any())).thenReturn(null);

    // Act
    marketDataGeneratorWebsocket.closeStreamsAndLogoff(webSocket);

    // Assert
    verify(webSocket).sendText(eq("{\"ID\":1, \"Type\": \"Close\", \"Domain\":\"Login\"}"));
  }

  /**
   * Test {@link MarketDataGeneratorWebsocket#writeDataset(String, MarketDataList, boolean)}.
   * <ul>
   *   <li>Then throw {@link RuntimeException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MarketDataGeneratorWebsocket#writeDataset(String, MarketDataList, boolean)}
   */
  @Test
  @DisplayName("Test writeDataset(String, MarketDataList, boolean); then throw RuntimeException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void MarketDataGeneratorWebsocket.writeDataset(String, MarketDataList, boolean)"})
  void testWriteDataset_thenThrowRuntimeException() throws IOException {
    // Arrange
    JSONObject authJson = new JSONObject();
    MarketDataGeneratorWebsocket marketDataGeneratorWebsocket = new MarketDataGeneratorWebsocket(authJson, "Position",
        new ArrayList<>());

    // Act and Assert
    assertThrows(RuntimeException.class,
        () -> marketDataGeneratorWebsocket.writeDataset("Import Dir", new MarketDataList(), true));
  }

  /**
   * Test {@link MarketDataGeneratorWebsocket#sendRICRequest(WebSocket)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link WebSocket} {@link WebSocket#sendText(String)} return {@code null}.</li>
   *   <li>Then calls {@link WebSocket#sendText(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MarketDataGeneratorWebsocket#sendRICRequest(WebSocket)}
   */
  @Test
  @DisplayName("Test sendRICRequest(WebSocket); given 'null'; when WebSocket sendText(String) return 'null'; then calls sendText(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void MarketDataGeneratorWebsocket.sendRICRequest(WebSocket)"})
  void testSendRICRequest_givenNull_whenWebSocketSendTextReturnNull_thenCallsSendText() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    JSONObject authJson = new JSONObject();
    MarketDataGeneratorWebsocket marketDataGeneratorWebsocket = new MarketDataGeneratorWebsocket(authJson, "Position",
        new ArrayList<>());
    WebSocket websocket = mock(WebSocket.class);
    when(websocket.sendText(Mockito.<String>any())).thenReturn(null);

    // Act
    marketDataGeneratorWebsocket.sendRICRequest(websocket);

    // Assert
    verify(websocket)
        .sendText(eq("{\"ID\":2,\"Key\":{\"Name\":]},\"View\":[\"MID\",\"BID\",\"ASK\",\"VALUE_DT1\",\"VALUE_TS1\"]}"));
  }

  /**
   * Test {@link MarketDataGeneratorWebsocket#sendLoginRequest(WebSocket, String, boolean)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link WebSocket} {@link WebSocket#sendText(String)} return {@code null}.</li>
   *   <li>Then calls {@link WebSocket#sendText(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MarketDataGeneratorWebsocket#sendLoginRequest(WebSocket, String, boolean)}
   */
  @Test
  @DisplayName("Test sendLoginRequest(WebSocket, String, boolean); given 'null'; when WebSocket sendText(String) return 'null'; then calls sendText(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void MarketDataGeneratorWebsocket.sendLoginRequest(WebSocket, String, boolean)"})
  void testSendLoginRequest_givenNull_whenWebSocketSendTextReturnNull_thenCallsSendText() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    JSONObject authJson = new JSONObject();
    MarketDataGeneratorWebsocket marketDataGeneratorWebsocket = new MarketDataGeneratorWebsocket(authJson, "Position",
        new ArrayList<>());
    WebSocket websocket = mock(WebSocket.class);
    when(websocket.sendText(Mockito.<String>any())).thenReturn(null);

    // Act
    marketDataGeneratorWebsocket.sendLoginRequest(websocket, "ABC123", true);

    // Assert
    verify(websocket).sendText(eq(
        "{\"ID\":1,\"Domain\":\"Login\",\"Key\":{\"Elements\":{\"ApplicationId\":\"256\",\"Position\":\"Position\",\"AuthenticationToken\":\"ABC123\"},\"NameType\":\"AuthnToken\"}}"));
  }

  /**
   * Test {@link MarketDataGeneratorWebsocket#sendLoginRequest(WebSocket, String, boolean)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link WebSocket} {@link WebSocket#sendText(String)} return {@code null}.</li>
   *   <li>Then calls {@link WebSocket#sendText(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MarketDataGeneratorWebsocket#sendLoginRequest(WebSocket, String, boolean)}
   */
  @Test
  @DisplayName("Test sendLoginRequest(WebSocket, String, boolean); given 'null'; when WebSocket sendText(String) return 'null'; then calls sendText(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void MarketDataGeneratorWebsocket.sendLoginRequest(WebSocket, String, boolean)"})
  void testSendLoginRequest_givenNull_whenWebSocketSendTextReturnNull_thenCallsSendText2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    JSONObject authJson = new JSONObject();
    MarketDataGeneratorWebsocket marketDataGeneratorWebsocket = new MarketDataGeneratorWebsocket(authJson, "Position",
        new ArrayList<>());
    WebSocket websocket = mock(WebSocket.class);
    when(websocket.sendText(Mockito.<String>any())).thenReturn(null);

    // Act
    marketDataGeneratorWebsocket.sendLoginRequest(websocket, "ABC123", false);

    // Assert
    verify(websocket).sendText(eq(
        "{\"ID\":1,\"Domain\":\"Login\",\"Key\":{\"Elements\":{\"ApplicationId\":\"256\",\"Position\":\"Position\",\"AuthenticationToken\":\"ABC123\"},\"NameType\":\"AuthnToken\"},\"Refresh\":false}"));
  }

  /**
   * Test {@link MarketDataGeneratorWebsocket#sendLoginRequest(WebSocket, String, boolean)}.
   * <ul>
   *   <li>Given {@link RuntimeException#RuntimeException(String)} with a string.</li>
   *   <li>Then throw {@link RuntimeException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MarketDataGeneratorWebsocket#sendLoginRequest(WebSocket, String, boolean)}
   */
  @Test
  @DisplayName("Test sendLoginRequest(WebSocket, String, boolean); given RuntimeException(String) with a string; then throw RuntimeException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void MarketDataGeneratorWebsocket.sendLoginRequest(WebSocket, String, boolean)"})
  void testSendLoginRequest_givenRuntimeExceptionWithAString_thenThrowRuntimeException() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    JSONObject authJson = new JSONObject();
    MarketDataGeneratorWebsocket marketDataGeneratorWebsocket = new MarketDataGeneratorWebsocket(authJson, "Position",
        new ArrayList<>());
    WebSocket websocket = mock(WebSocket.class);
    when(websocket.sendText(Mockito.<String>any())).thenThrow(new RuntimeException(
        "{\"ID\":1,\"Domain\":\"Login\",\"Key\":{\"Elements\":{\"ApplicationId\":\"\",\"Position\":\"\",\"AuthenticationToken\":\""
            + "\"},\"NameType\":\"AuthnToken\"}}"));

    // Act and Assert
    assertThrows(RuntimeException.class,
        () -> marketDataGeneratorWebsocket.sendLoginRequest(websocket, "ABC123", false));
    verify(websocket).sendText(eq(
        "{\"ID\":1,\"Domain\":\"Login\",\"Key\":{\"Elements\":{\"ApplicationId\":\"256\",\"Position\":\"Position\",\"AuthenticationToken\":\"ABC123\"},\"NameType\":\"AuthnToken\"},\"Refresh\":false}"));
  }
}
