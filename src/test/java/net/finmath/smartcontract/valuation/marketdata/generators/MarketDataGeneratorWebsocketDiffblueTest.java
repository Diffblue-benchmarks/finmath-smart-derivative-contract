package net.finmath.smartcontract.valuation.marketdata.generators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
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
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class MarketDataGeneratorWebsocketDiffblueTest {
  /**
   * Test {@link MarketDataGeneratorWebsocket#allQuotesRetrieved()}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link MarketDataGeneratorWebsocket#allQuotesRetrieved()}
   */
  @Test
  @DisplayName("Test allQuotesRetrieved(); then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean MarketDataGeneratorWebsocket.allQuotesRetrieved()"})
  void testAllQuotesRetrieved_thenReturnFalse() {
    // Arrange
    ArrayList<Spec> itemList = new ArrayList<>();
    Spec spec = new Spec("Key", "Curve Name", "Product Name", "Maturity");
    itemList.add(spec);
    MarketDataGeneratorWebsocket marketDataGeneratorWebsocket =
        new MarketDataGeneratorWebsocket(new JSONObject(), "Position", itemList);

    // Act and Assert
    assertFalse(marketDataGeneratorWebsocket.allQuotesRetrieved());
  }

  /**
   * Test {@link MarketDataGeneratorWebsocket#allQuotesRetrieved()}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link MarketDataGeneratorWebsocket#allQuotesRetrieved()}
   */
  @Test
  @DisplayName("Test allQuotesRetrieved(); then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean MarketDataGeneratorWebsocket.allQuotesRetrieved()"})
  void testAllQuotesRetrieved_thenReturnTrue() {
    // Arrange
    JSONObject authJson = new JSONObject();
    MarketDataGeneratorWebsocket marketDataGeneratorWebsocket =
        new MarketDataGeneratorWebsocket(authJson, "Position", new ArrayList<>());

    // Act and Assert
    assertTrue(marketDataGeneratorWebsocket.allQuotesRetrieved());
  }

  /**
   * Test {@link MarketDataGeneratorWebsocket#onConnected(WebSocket, Map)}.
   *
   * <p>Method under test: {@link MarketDataGeneratorWebsocket#onConnected(WebSocket, Map)}
   */
  @Test
  @DisplayName("Test onConnected(WebSocket, Map)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void MarketDataGeneratorWebsocket.onConnected(WebSocket, Map)"})
  void testOnConnected() throws Exception {
    // Arrange
    JSONObject authJson = mock(JSONObject.class);
    when(authJson.getString(Mockito.<String>any())).thenReturn("String");
    MarketDataGeneratorWebsocket marketDataGeneratorWebsocket =
        new MarketDataGeneratorWebsocket(
            authJson,
            "{\"ID\":1,\"Domain\":\"Login\",\"Key\":{\"Elements\":{\"ApplicationId\":\"\",\"Position\":\"\",\"AuthenticationToken\":\""
                + "\"},\"NameType\":\"AuthnToken\"}}",
            new ArrayList<>());

    WebSocket websocket = mock(WebSocket.class);
    when(websocket.sendText(Mockito.<String>any())).thenReturn(null);

    // Act
    marketDataGeneratorWebsocket.onConnected(websocket, new HashMap<>());

    // Assert
    verify(websocket)
        .sendText(
            "{\"ID\":1,\"Domain\":\"Login\",\"Key\":{\"Elements\":{\"ApplicationId\":\"256\",\"Position\":\"{\\\"ID\\\":1,\\\"Domain\\\":\\\"Login\\\",\\\"Key\\\":{\\\"Elements\\\":{\\\"ApplicationId\\\":\\\"\\\",\\\"Position\\\":\\\"\\\",\\\"AuthenticationToken\\\":\\\"\\\"},\\\"NameType\\\":\\\"AuthnToken\\\"}}\",\"AuthenticationToken\":\"String\"},\"NameType\":\"AuthnToken\"}}");
    verify(authJson).getString("access_token");
  }

  /**
   * Test {@link MarketDataGeneratorWebsocket#onConnected(WebSocket, Map)}.
   *
   * <ul>
   *   <li>Then calls {@link WebSocket#sendText(String)}.
   * </ul>
   *
   * <p>Method under test: {@link MarketDataGeneratorWebsocket#onConnected(WebSocket, Map)}
   */
  @Test
  @DisplayName("Test onConnected(WebSocket, Map); then calls sendText(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void MarketDataGeneratorWebsocket.onConnected(WebSocket, Map)"})
  void testOnConnected_thenCallsSendText() throws Exception {
    // Arrange
    JSONObject authJson = mock(JSONObject.class);
    when(authJson.getString(Mockito.<String>any())).thenReturn("String");
    MarketDataGeneratorWebsocket marketDataGeneratorWebsocket =
        new MarketDataGeneratorWebsocket(authJson, "Position", new ArrayList<>());

    WebSocket websocket = mock(WebSocket.class);
    when(websocket.sendText(Mockito.<String>any())).thenReturn(null);

    // Act
    marketDataGeneratorWebsocket.onConnected(websocket, new HashMap<>());

    // Assert
    verify(websocket)
        .sendText(
            "{\"ID\":1,\"Domain\":\"Login\",\"Key\":{\"Elements\":{\"ApplicationId\":\"256\",\"Position\":\"Position\",\"AuthenticationToken\":\"String\"},\"NameType\":\"AuthnToken\"}}");
    verify(authJson).getString("access_token");
  }

  /**
   * Test {@link MarketDataGeneratorWebsocket#asObservable()}.
   *
   * <p>Method under test: {@link MarketDataGeneratorWebsocket#asObservable()}
   */
  @Test
  @DisplayName("Test asObservable()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Observable MarketDataGeneratorWebsocket.asObservable()"})
  void testAsObservable() {
    // Arrange
    JSONObject authJson = new JSONObject();
    MarketDataGeneratorWebsocket marketDataGeneratorWebsocket =
        new MarketDataGeneratorWebsocket(authJson, "Position", new ArrayList<>());

    // Act
    Observable<MarketDataList> actualAsObservableResult =
        marketDataGeneratorWebsocket.asObservable();

    // Assert
    Observable<Timed<MarketDataList>> timestampResult = actualAsObservableResult.timestamp();
    assertTrue(timestampResult.timestamp() instanceof ObservableMap);
    Subject<MarketDataList> toSerializedResult =
        ((PublishSubject<MarketDataList>) actualAsObservableResult).toSerialized();
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
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link CalibrationDataItem.Spec}.
   *   <li>Then calls {@link WebSocket#sendText(String)}.
   * </ul>
   *
   * <p>Method under test: {@link MarketDataGeneratorWebsocket#closeStreamsAndLogoff(WebSocket)}
   */
  @Test
  @DisplayName(
      "Test closeStreamsAndLogoff(WebSocket); given ArrayList() add Spec; then calls sendText(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void MarketDataGeneratorWebsocket.closeStreamsAndLogoff(WebSocket)"})
  void testCloseStreamsAndLogoff_givenArrayListAddSpec_thenCallsSendText() {
    // Arrange
    ArrayList<Spec> itemList = new ArrayList<>();
    itemList.add(mock(Spec.class));
    MarketDataGeneratorWebsocket marketDataGeneratorWebsocket =
        new MarketDataGeneratorWebsocket(new JSONObject(), "Position", itemList);

    WebSocket webSocket = mock(WebSocket.class);
    when(webSocket.sendText(Mockito.<String>any())).thenReturn(null);

    // Act
    marketDataGeneratorWebsocket.closeStreamsAndLogoff(webSocket);

    // Assert
    verify(webSocket).sendText("{\"ID\":1, \"Type\": \"Close\", \"Domain\":\"Login\"}");
  }

  /**
   * Test {@link MarketDataGeneratorWebsocket#closeStreamsAndLogoff(WebSocket)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>Then calls {@link WebSocket#sendText(String)}.
   * </ul>
   *
   * <p>Method under test: {@link MarketDataGeneratorWebsocket#closeStreamsAndLogoff(WebSocket)}
   */
  @Test
  @DisplayName("Test closeStreamsAndLogoff(WebSocket); given 'null'; then calls sendText(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void MarketDataGeneratorWebsocket.closeStreamsAndLogoff(WebSocket)"})
  void testCloseStreamsAndLogoff_givenNull_thenCallsSendText() {
    // Arrange
    JSONObject authJson = new JSONObject();
    MarketDataGeneratorWebsocket marketDataGeneratorWebsocket =
        new MarketDataGeneratorWebsocket(authJson, "Position", new ArrayList<>());

    WebSocket webSocket = mock(WebSocket.class);
    when(webSocket.sendText(Mockito.<String>any())).thenReturn(null);

    // Act
    marketDataGeneratorWebsocket.closeStreamsAndLogoff(webSocket);

    // Assert
    verify(webSocket).sendText("{\"ID\":1, \"Type\": \"Close\", \"Domain\":\"Login\"}");
  }

  /**
   * Test {@link MarketDataGeneratorWebsocket#writeDataset(String, MarketDataList, boolean)}.
   *
   * <p>Method under test: {@link MarketDataGeneratorWebsocket#writeDataset(String, MarketDataList,
   * boolean)}
   */
  @Test
  @DisplayName("Test writeDataset(String, MarketDataList, boolean)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MarketDataGeneratorWebsocket.writeDataset(String, MarketDataList, boolean)"
  })
  void testWriteDataset() throws IOException {
    // Arrange
    JSONObject authJson = new JSONObject();
    MarketDataGeneratorWebsocket marketDataGeneratorWebsocket =
        new MarketDataGeneratorWebsocket(authJson, "Position", new ArrayList<>());

    // Act and Assert
    assertThrows(
        RuntimeException.class,
        () -> marketDataGeneratorWebsocket.writeDataset("Import Dir", new MarketDataList(), true));
  }

  /**
   * Test {@link MarketDataGeneratorWebsocket#onTextMessage(WebSocket, String)} with {@code
   * websocket}, {@code message}.
   *
   * <p>Method under test: {@link MarketDataGeneratorWebsocket#onTextMessage(WebSocket, String)}
   */
  @Test
  @DisplayName("Test onTextMessage(WebSocket, String) with 'websocket', 'message'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void MarketDataGeneratorWebsocket.onTextMessage(WebSocket, String)"})
  void testOnTextMessageWithWebsocketMessage() throws Exception {
    // Arrange
    JSONObject authJson = new JSONObject();
    MarketDataGeneratorWebsocket marketDataGeneratorWebsocket =
        new MarketDataGeneratorWebsocket(authJson, "Position", new ArrayList<>());

    WebSocket websocket = mock(WebSocket.class);
    when(websocket.sendText(Mockito.<String>any())).thenReturn(null);

    // Act
    marketDataGeneratorWebsocket.onTextMessage(websocket, "42");

    // Assert that nothing has changed
    verify(websocket)
        .sendText(
            "{\"ID\":2,\"Key\":{\"Name\":]},\"View\":[\"MID\",\"BID\",\"ASK\",\"VALUE_DT1\",\"VALUE_TS1\"]}");
    assertFalse(marketDataGeneratorWebsocket.requestSent);
  }

  /**
   * Test {@link MarketDataGeneratorWebsocket#onTextMessage(WebSocket, String)} with {@code
   * websocket}, {@code message}.
   *
   * <p>Method under test: {@link MarketDataGeneratorWebsocket#onTextMessage(WebSocket, String)}
   */
  @Test
  @DisplayName("Test onTextMessage(WebSocket, String) with 'websocket', 'message'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void MarketDataGeneratorWebsocket.onTextMessage(WebSocket, String)"})
  void testOnTextMessageWithWebsocketMessage2() throws Exception {
    // Arrange
    ArrayList<Spec> itemList = new ArrayList<>();
    Spec spec = new Spec("message: {}", "message: {}", "message: {}", "message: {}");
    itemList.add(spec);
    MarketDataGeneratorWebsocket marketDataGeneratorWebsocket =
        new MarketDataGeneratorWebsocket(new JSONObject(), "Position", itemList);

    WebSocket websocket = mock(WebSocket.class);
    when(websocket.sendText(Mockito.<String>any())).thenReturn(null);

    // Act
    marketDataGeneratorWebsocket.onTextMessage(websocket, "42");

    // Assert
    verify(websocket)
        .sendText(
            "{\"ID\":2,\"Key\":{\"Name\":[\"message: {}\"]},\"View\":[\"MID\",\"BID\",\"ASK\",\"VALUE_DT1\",\"VALUE_TS1\"]}");
    assertTrue(marketDataGeneratorWebsocket.requestSent);
  }

  /**
   * Test {@link MarketDataGeneratorWebsocket#onTextMessage(WebSocket, String)} with {@code
   * websocket}, {@code message}.
   *
   * <ul>
   *   <li>When a string.
   * </ul>
   *
   * <p>Method under test: {@link MarketDataGeneratorWebsocket#onTextMessage(WebSocket, String)}
   */
  @Test
  @DisplayName("Test onTextMessage(WebSocket, String) with 'websocket', 'message'; when a string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void MarketDataGeneratorWebsocket.onTextMessage(WebSocket, String)"})
  void testOnTextMessageWithWebsocketMessage_whenAString() throws Exception {
    // Arrange
    JSONObject authJson = new JSONObject();
    MarketDataGeneratorWebsocket marketDataGeneratorWebsocket =
        new MarketDataGeneratorWebsocket(authJson, "Position", new ArrayList<>());

    WebSocket websocket = mock(WebSocket.class);
    when(websocket.sendText(Mockito.<String>any())).thenReturn(null);

    // Act
    marketDataGeneratorWebsocket.onTextMessage(
        websocket,
        "{\"ID\":1,\"Domain\":\"Login\",\"Key\":{\"Elements\":{\"ApplicationId\":\"\",\"Position\":\"\",\"AuthenticationToken\":\""
            + "\"},\"NameType\":\"AuthnToken\"}}");

    // Assert that nothing has changed
    verify(websocket)
        .sendText(
            "{\"ID\":2,\"Key\":{\"Name\":]},\"View\":[\"MID\",\"BID\",\"ASK\",\"VALUE_DT1\",\"VALUE_TS1\"]}");
    assertFalse(marketDataGeneratorWebsocket.requestSent);
  }

  /**
   * Test {@link MarketDataGeneratorWebsocket#onTextMessage(WebSocket, String)} with {@code
   * websocket}, {@code message}.
   *
   * <ul>
   *   <li>When empty string.
   * </ul>
   *
   * <p>Method under test: {@link MarketDataGeneratorWebsocket#onTextMessage(WebSocket, String)}
   */
  @Test
  @DisplayName(
      "Test onTextMessage(WebSocket, String) with 'websocket', 'message'; when empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void MarketDataGeneratorWebsocket.onTextMessage(WebSocket, String)"})
  void testOnTextMessageWithWebsocketMessage_whenEmptyString() throws Exception {
    // Arrange
    JSONObject authJson = new JSONObject();
    MarketDataGeneratorWebsocket marketDataGeneratorWebsocket =
        new MarketDataGeneratorWebsocket(authJson, "Position", new ArrayList<>());

    // Act
    marketDataGeneratorWebsocket.onTextMessage(null, "");

    // Assert that nothing has changed
    assertFalse(marketDataGeneratorWebsocket.requestSent);
  }

  /**
   * Test {@link MarketDataGeneratorWebsocket#onTextMessage(WebSocket, String)} with {@code websocket}, {@code message}.
   * <ul>
   *   <li>When {@code "Key":{"Name":[}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MarketDataGeneratorWebsocket#onTextMessage(WebSocket, String)}
   */
  @Test
  @DisplayName(
      "Test onTextMessage(WebSocket, String) with 'websocket', 'message'; when '\"Key\":{\"Name\":['")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void MarketDataGeneratorWebsocket.onTextMessage(WebSocket, String)"})
  void testOnTextMessageWithWebsocketMessage_whenKeyName() throws Exception {
    // Arrange
    JSONObject authJson = new JSONObject();
    MarketDataGeneratorWebsocket marketDataGeneratorWebsocket =
        new MarketDataGeneratorWebsocket(authJson, "Position", new ArrayList<>());

    WebSocket websocket = mock(WebSocket.class);
    when(websocket.sendText(Mockito.<String>any())).thenReturn(null);

    // Act
    marketDataGeneratorWebsocket.onTextMessage(websocket, "\"Key\":{\"Name\":[");

    // Assert that nothing has changed
    verify(websocket)
        .sendText(
            "{\"ID\":2,\"Key\":{\"Name\":]},\"View\":[\"MID\",\"BID\",\"ASK\",\"VALUE_DT1\",\"VALUE_TS1\"]}");
    assertFalse(marketDataGeneratorWebsocket.requestSent);
  }

  /**
   * Test {@link MarketDataGeneratorWebsocket#sendRICRequest(WebSocket)}.
   *
   * <p>Method under test: {@link MarketDataGeneratorWebsocket#sendRICRequest(WebSocket)}
   */
  @Test
  @DisplayName("Test sendRICRequest(WebSocket)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void MarketDataGeneratorWebsocket.sendRICRequest(WebSocket)"})
  void testSendRICRequest() {
    // Arrange
    ArrayList<Spec> itemList = new ArrayList<>();
    Spec spec =
        new Spec(
            "\"Key\":{\"Name\":[",
            "\"Key\":{\"Name\":[",
            "\"Key\":{\"Name\":[",
            "\"Key\":{\"Name\":[");
    itemList.add(spec);
    MarketDataGeneratorWebsocket marketDataGeneratorWebsocket =
        new MarketDataGeneratorWebsocket(new JSONObject(), "Position", itemList);

    WebSocket websocket = mock(WebSocket.class);
    when(websocket.sendText(Mockito.<String>any())).thenReturn(null);

    // Act
    marketDataGeneratorWebsocket.sendRICRequest(websocket);

    // Assert
    verify(websocket)
        .sendText(
            "{\"ID\":2,\"Key\":{\"Name\":[\"\"Key\":{\"Name\":[\"]},\"View\":[\"MID\",\"BID\",\"ASK\",\"VALUE_DT1\",\"VALUE_TS1\"]}");
  }

  /**
   * Test {@link MarketDataGeneratorWebsocket#sendRICRequest(WebSocket)}.
   *
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject(Map)} with {@link HashMap#HashMap()} {@code ",} is
   *       {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link MarketDataGeneratorWebsocket#sendRICRequest(WebSocket)}
   */
  @Test
  @DisplayName("Test sendRICRequest(WebSocket); given JSONObject(Map) with HashMap() '\",' is '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void MarketDataGeneratorWebsocket.sendRICRequest(WebSocket)"})
  void testSendRICRequest_givenJSONObjectWithHashMapQuotationMarkCommaIs42() throws JSONException {
    // Arrange
    JSONObject authJson = new JSONObject(new HashMap<>());
    authJson.put("\",", "42");

    ArrayList<Spec> itemList = new ArrayList<>();
    Spec spec =
        new Spec(
            "\"Key\":{\"Name\":[",
            "\"Key\":{\"Name\":[",
            "\"Key\":{\"Name\":[",
            "\"Key\":{\"Name\":[");
    itemList.add(spec);

    MarketDataGeneratorWebsocket marketDataGeneratorWebsocket =
        new MarketDataGeneratorWebsocket(authJson, "Position", itemList);

    WebSocket websocket = mock(WebSocket.class);
    when(websocket.sendText(Mockito.<String>any())).thenReturn(null);

    // Act
    marketDataGeneratorWebsocket.sendRICRequest(websocket);

    // Assert
    verify(websocket)
        .sendText(
            "{\"ID\":2,\"Key\":{\"Name\":[\"\"Key\":{\"Name\":[\"]},\"View\":[\"MID\",\"BID\",\"ASK\",\"VALUE_DT1\",\"VALUE_TS1\"]}");
  }

  /**
   * Test {@link MarketDataGeneratorWebsocket#sendRICRequest(WebSocket)}.
   *
   * <ul>
   *   <li>Then calls {@link WebSocket#sendText(String)}.
   * </ul>
   *
   * <p>Method under test: {@link MarketDataGeneratorWebsocket#sendRICRequest(WebSocket)}
   */
  @Test
  @DisplayName("Test sendRICRequest(WebSocket); then calls sendText(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void MarketDataGeneratorWebsocket.sendRICRequest(WebSocket)"})
  void testSendRICRequest_thenCallsSendText() {
    // Arrange
    JSONObject authJson = new JSONObject();
    MarketDataGeneratorWebsocket marketDataGeneratorWebsocket =
        new MarketDataGeneratorWebsocket(authJson, "Position", new ArrayList<>());

    WebSocket websocket = mock(WebSocket.class);
    when(websocket.sendText(Mockito.<String>any())).thenReturn(null);

    // Act
    marketDataGeneratorWebsocket.sendRICRequest(websocket);

    // Assert
    verify(websocket)
        .sendText(
            "{\"ID\":2,\"Key\":{\"Name\":]},\"View\":[\"MID\",\"BID\",\"ASK\",\"VALUE_DT1\",\"VALUE_TS1\"]}");
  }

  /**
   * Test {@link MarketDataGeneratorWebsocket#sendLoginRequest(WebSocket, String, boolean)}.
   *
   * <p>Method under test: {@link MarketDataGeneratorWebsocket#sendLoginRequest(WebSocket, String,
   * boolean)}
   */
  @Test
  @DisplayName("Test sendLoginRequest(WebSocket, String, boolean)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MarketDataGeneratorWebsocket.sendLoginRequest(WebSocket, String, boolean)"
  })
  void testSendLoginRequest() throws Exception {
    // Arrange
    JSONObject authJson = new JSONObject();
    MarketDataGeneratorWebsocket marketDataGeneratorWebsocket =
        new MarketDataGeneratorWebsocket(
            authJson,
            "{\"ID\":1,\"Domain\":\"Login\",\"Key\":{\"Elements\":{\"ApplicationId\":\"\",\"Position\":\"\",\"AuthenticationToken\":\""
                + "\"},\"NameType\":\"AuthnToken\"}}",
            new ArrayList<>());

    WebSocket websocket = mock(WebSocket.class);
    when(websocket.sendText(Mockito.<String>any())).thenReturn(null);

    // Act
    marketDataGeneratorWebsocket.sendLoginRequest(websocket, "ABC123", true);

    // Assert
    verify(websocket)
        .sendText(
            "{\"ID\":1,\"Domain\":\"Login\",\"Key\":{\"Elements\":{\"ApplicationId\":\"256\",\"Position\":\"{\\\"ID\\\":1,\\\"Domain\\\":\\\"Login\\\",\\\"Key\\\":{\\\"Elements\\\":{\\\"ApplicationId\\\":\\\"\\\",\\\"Position\\\":\\\"\\\",\\\"AuthenticationToken\\\":\\\"\\\"},\\\"NameType\\\":\\\"AuthnToken\\\"}}\",\"AuthenticationToken\":\"ABC123\"},\"NameType\":\"AuthnToken\"}}");
  }

  /**
   * Test {@link MarketDataGeneratorWebsocket#sendLoginRequest(WebSocket, String, boolean)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When empty string.
   *   <li>Then calls {@link WebSocket#sendText(String)}.
   * </ul>
   *
   * <p>Method under test: {@link MarketDataGeneratorWebsocket#sendLoginRequest(WebSocket, String,
   * boolean)}
   */
  @Test
  @DisplayName(
      "Test sendLoginRequest(WebSocket, String, boolean); given 'null'; when empty string; then calls sendText(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MarketDataGeneratorWebsocket.sendLoginRequest(WebSocket, String, boolean)"
  })
  void testSendLoginRequest_givenNull_whenEmptyString_thenCallsSendText() throws Exception {
    // Arrange
    JSONObject authJson = new JSONObject();
    MarketDataGeneratorWebsocket marketDataGeneratorWebsocket =
        new MarketDataGeneratorWebsocket(authJson, "Position", new ArrayList<>());

    WebSocket websocket = mock(WebSocket.class);
    when(websocket.sendText(Mockito.<String>any())).thenReturn(null);

    // Act
    marketDataGeneratorWebsocket.sendLoginRequest(websocket, "", false);

    // Assert
    verify(websocket)
        .sendText(
            "{\"ID\":1,\"Domain\":\"Login\",\"Key\":{\"Elements\":{\"ApplicationId\":\"256\",\"Position\":\"Position\",\"AuthenticationToken\":\"\"},\"NameType\":\"AuthnToken\"},\"Refresh\":false}");
  }

  /**
   * Test {@link MarketDataGeneratorWebsocket#sendLoginRequest(WebSocket, String, boolean)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@code false}.
   *   <li>Then calls {@link WebSocket#sendText(String)}.
   * </ul>
   *
   * <p>Method under test: {@link MarketDataGeneratorWebsocket#sendLoginRequest(WebSocket, String,
   * boolean)}
   */
  @Test
  @DisplayName(
      "Test sendLoginRequest(WebSocket, String, boolean); given 'null'; when 'false'; then calls sendText(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MarketDataGeneratorWebsocket.sendLoginRequest(WebSocket, String, boolean)"
  })
  void testSendLoginRequest_givenNull_whenFalse_thenCallsSendText() throws Exception {
    // Arrange
    JSONObject authJson = new JSONObject();
    MarketDataGeneratorWebsocket marketDataGeneratorWebsocket =
        new MarketDataGeneratorWebsocket(authJson, "Position", new ArrayList<>());

    WebSocket websocket = mock(WebSocket.class);
    when(websocket.sendText(Mockito.<String>any())).thenReturn(null);

    // Act
    marketDataGeneratorWebsocket.sendLoginRequest(websocket, "ABC123", false);

    // Assert
    verify(websocket)
        .sendText(
            "{\"ID\":1,\"Domain\":\"Login\",\"Key\":{\"Elements\":{\"ApplicationId\":\"256\",\"Position\":\"Position\",\"AuthenticationToken\":\"ABC123\"},\"NameType\":\"AuthnToken\"},\"Refresh\":false}");
  }

  /**
   * Test {@link MarketDataGeneratorWebsocket#sendLoginRequest(WebSocket, String, boolean)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@code true}.
   *   <li>Then calls {@link WebSocket#sendText(String)}.
   * </ul>
   *
   * <p>Method under test: {@link MarketDataGeneratorWebsocket#sendLoginRequest(WebSocket, String,
   * boolean)}
   */
  @Test
  @DisplayName(
      "Test sendLoginRequest(WebSocket, String, boolean); given 'null'; when 'true'; then calls sendText(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MarketDataGeneratorWebsocket.sendLoginRequest(WebSocket, String, boolean)"
  })
  void testSendLoginRequest_givenNull_whenTrue_thenCallsSendText() throws Exception {
    // Arrange
    JSONObject authJson = new JSONObject();
    MarketDataGeneratorWebsocket marketDataGeneratorWebsocket =
        new MarketDataGeneratorWebsocket(authJson, "Position", new ArrayList<>());

    WebSocket websocket = mock(WebSocket.class);
    when(websocket.sendText(Mockito.<String>any())).thenReturn(null);

    // Act
    marketDataGeneratorWebsocket.sendLoginRequest(websocket, "ABC123", true);

    // Assert
    verify(websocket)
        .sendText(
            "{\"ID\":1,\"Domain\":\"Login\",\"Key\":{\"Elements\":{\"ApplicationId\":\"256\",\"Position\":\"Position\",\"AuthenticationToken\":\"ABC123\"},\"NameType\":\"AuthnToken\"}}");
  }

  /**
   * Test {@link MarketDataGeneratorWebsocket#sendLoginRequest(WebSocket, String, boolean)}.
   *
   * <ul>
   *   <li>Given {@link RuntimeException#RuntimeException()}.
   *   <li>Then throw {@link RuntimeException}.
   * </ul>
   *
   * <p>Method under test: {@link MarketDataGeneratorWebsocket#sendLoginRequest(WebSocket, String,
   * boolean)}
   */
  @Test
  @DisplayName(
      "Test sendLoginRequest(WebSocket, String, boolean); given RuntimeException(); then throw RuntimeException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MarketDataGeneratorWebsocket.sendLoginRequest(WebSocket, String, boolean)"
  })
  void testSendLoginRequest_givenRuntimeException_thenThrowRuntimeException() throws Exception {
    // Arrange
    JSONObject authJson = new JSONObject();
    MarketDataGeneratorWebsocket marketDataGeneratorWebsocket =
        new MarketDataGeneratorWebsocket(authJson, "Position", new ArrayList<>());

    WebSocket websocket = mock(WebSocket.class);
    when(websocket.sendText(Mockito.<String>any())).thenThrow(new RuntimeException());

    // Act and Assert
    assertThrows(
        RuntimeException.class,
        () -> marketDataGeneratorWebsocket.sendLoginRequest(websocket, "ABC123", false));
    verify(websocket)
        .sendText(
            "{\"ID\":1,\"Domain\":\"Login\",\"Key\":{\"Elements\":{\"ApplicationId\":\"256\",\"Position\":\"Position\",\"AuthenticationToken\":\"ABC123\"},\"NameType\":\"AuthnToken\"},\"Refresh\":false}");
  }
}
