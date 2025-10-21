package net.finmath.smartcontract.valuation.marketdata.generators.legacy;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeast;
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
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.finmath.smartcontract.model.MarketDataSet;
import net.finmath.smartcontract.model.MarketDataSetValuesInner;
import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationDataItem;
import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationDataItem.Spec;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ReactiveMarketDataUpdater.class, String.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
class ReactiveMarketDataUpdaterDiffblueTest {
  @MockBean
  private JSONObject jSONObject;

  @Autowired
  private List<Spec> list;

  @Autowired
  private ReactiveMarketDataUpdater reactiveMarketDataUpdater;

  @MockBean
  private Spec spec;

  /**
   * Test {@link ReactiveMarketDataUpdater#ReactiveMarketDataUpdater(JSONObject, String, List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReactiveMarketDataUpdater#ReactiveMarketDataUpdater(JSONObject, String, List)}
   */
  @Test
  @DisplayName("Test new ReactiveMarketDataUpdater(JSONObject, String, List); when ArrayList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ReactiveMarketDataUpdater.<init>(JSONObject, String, List)"})
  void testNewReactiveMarketDataUpdater_whenArrayList() {
    // Arrange, Act and Assert
    assertFalse((new ReactiveMarketDataUpdater(jSONObject, "Position", new ArrayList<>())).requestSent);
  }

  /**
   * Test {@link ReactiveMarketDataUpdater#ReactiveMarketDataUpdater(JSONObject, String, List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()} add {@link CalibrationDataItem.Spec}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReactiveMarketDataUpdater#ReactiveMarketDataUpdater(JSONObject, String, List)}
   */
  @Test
  @DisplayName("Test new ReactiveMarketDataUpdater(JSONObject, String, List); when ArrayList() add Spec")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ReactiveMarketDataUpdater.<init>(JSONObject, String, List)"})
  void testNewReactiveMarketDataUpdater_whenArrayListAddSpec() {
    // Arrange
    ArrayList<Spec> itemList = new ArrayList<>();
    itemList.add(spec);

    // Act and Assert
    assertFalse((new ReactiveMarketDataUpdater(jSONObject, "Position", itemList)).requestSent);
  }

  /**
   * Test {@link ReactiveMarketDataUpdater#ReactiveMarketDataUpdater(JSONObject, String, List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()} add {@link CalibrationDataItem.Spec}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReactiveMarketDataUpdater#ReactiveMarketDataUpdater(JSONObject, String, List)}
   */
  @Test
  @DisplayName("Test new ReactiveMarketDataUpdater(JSONObject, String, List); when ArrayList() add Spec")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ReactiveMarketDataUpdater.<init>(JSONObject, String, List)"})
  void testNewReactiveMarketDataUpdater_whenArrayListAddSpec2() {
    // Arrange
    ArrayList<Spec> itemList = new ArrayList<>();
    itemList.add(spec);
    itemList.add(spec);

    // Act and Assert
    assertFalse((new ReactiveMarketDataUpdater(jSONObject, "Position", itemList)).requestSent);
  }

  /**
   * Test {@link ReactiveMarketDataUpdater#onConnected(WebSocket, Map)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link WebSocket} {@link WebSocket#sendText(String)} return {@code null}.</li>
   *   <li>Then calls {@link WebSocket#sendText(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReactiveMarketDataUpdater#onConnected(WebSocket, Map)}
   */
  @Test
  @DisplayName("Test onConnected(WebSocket, Map); given 'null'; when WebSocket sendText(String) return 'null'; then calls sendText(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ReactiveMarketDataUpdater.onConnected(WebSocket, Map)"})
  void testOnConnected_givenNull_whenWebSocketSendTextReturnNull_thenCallsSendText() throws Exception {
    // Arrange
    when(jSONObject.getString(Mockito.<String>any())).thenReturn("String");
    WebSocket websocket = mock(WebSocket.class);
    when(websocket.sendText(Mockito.<String>any())).thenReturn(null);

    // Act
    reactiveMarketDataUpdater.onConnected(websocket, new HashMap<>());

    // Assert
    verify(websocket, atLeast(1)).sendText(Mockito.<String>any());
    verify(jSONObject, atLeast(1)).getString(eq("access_token"));
  }

  /**
   * Test {@link ReactiveMarketDataUpdater#onConnected(WebSocket, Map)}.
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReactiveMarketDataUpdater#onConnected(WebSocket, Map)}
   */
  @Test
  @DisplayName("Test onConnected(WebSocket, Map); then throw IllegalStateException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ReactiveMarketDataUpdater.onConnected(WebSocket, Map)"})
  void testOnConnected_thenThrowIllegalStateException() throws Exception {
    // Arrange
    when(jSONObject.getString(Mockito.<String>any())).thenThrow(new IllegalStateException("access_token"));

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> reactiveMarketDataUpdater.onConnected(null, new HashMap<>()));
    verify(jSONObject).getString(eq("access_token"));
  }

  /**
   * Test {@link ReactiveMarketDataUpdater#asObservable()}.
   * <p>
   * Method under test: {@link ReactiveMarketDataUpdater#asObservable()}
   */
  @Test
  @DisplayName("Test asObservable()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Observable ReactiveMarketDataUpdater.asObservable()"})
  void testAsObservable() {
    // Arrange and Act
    Observable<MarketDataSet> actualAsObservableResult = reactiveMarketDataUpdater.asObservable();

    // Assert
    Observable<Timed<MarketDataSet>> timestampResult = actualAsObservableResult.timestamp();
    assertTrue(timestampResult.timestamp() instanceof ObservableMap);
    Subject<MarketDataSet> toSerializedResult = ((PublishSubject<MarketDataSet>) actualAsObservableResult)
        .toSerialized();
    assertTrue(toSerializedResult.timestamp() instanceof ObservableMap);
    assertTrue(timestampResult instanceof ObservableMap);
    assertTrue(timestampResult.toList() instanceof ObservableToListSingle);
    assertTrue(toSerializedResult.toList() instanceof ObservableToListSingle);
    Single<List<MarketDataSet>> toListResult = actualAsObservableResult.toList();
    assertTrue(toListResult instanceof ObservableToListSingle);
    assertTrue(toListResult.timestamp() instanceof SingleTimeInterval);
    assertTrue(actualAsObservableResult instanceof PublishSubject);
    assertNull(((PublishSubject<MarketDataSet>) actualAsObservableResult).getThrowable());
    assertFalse(((PublishSubject<MarketDataSet>) actualAsObservableResult).hasComplete());
    assertFalse(((PublishSubject<MarketDataSet>) actualAsObservableResult).hasObservers());
    assertFalse(((PublishSubject<MarketDataSet>) actualAsObservableResult).hasThrowable());
    assertSame(toSerializedResult.toSerialized(), toSerializedResult.toSerialized());
  }

  /**
   * Test {@link ReactiveMarketDataUpdater#onTextMessage(WebSocket, String)} with {@code websocket}, {@code message}.
   * <p>
   * Method under test: {@link ReactiveMarketDataUpdater#onTextMessage(WebSocket, String)}
   */
  @Test
  @DisplayName("Test onTextMessage(WebSocket, String) with 'websocket', 'message'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ReactiveMarketDataUpdater.onTextMessage(WebSocket, String)"})
  void testOnTextMessageWithWebsocketMessage() {
    // Arrange and Act
    reactiveMarketDataUpdater.onTextMessage(null, "");

    // Assert that nothing has changed
    assertFalse(reactiveMarketDataUpdater.requestSent);
  }

  /**
   * Test {@link ReactiveMarketDataUpdater#onTextMessage(WebSocket, String)} with {@code websocket}, {@code message}.
   * <ul>
   *   <li>Then {@link ReactiveMarketDataUpdater} {@link ReactiveMarketDataUpdater#requestSent}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReactiveMarketDataUpdater#onTextMessage(WebSocket, String)}
   */
  @Test
  @DisplayName("Test onTextMessage(WebSocket, String) with 'websocket', 'message'; then ReactiveMarketDataUpdater requestSent")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ReactiveMarketDataUpdater.onTextMessage(WebSocket, String)"})
  void testOnTextMessageWithWebsocketMessage_thenReactiveMarketDataUpdaterRequestSent() {
    // Arrange
    when(spec.getKey()).thenReturn("Key");
    WebSocket websocket = mock(WebSocket.class);
    when(websocket.sendText(Mockito.<String>any())).thenReturn(null);

    // Act
    reactiveMarketDataUpdater.onTextMessage(websocket, "Not all who wander are lost");

    // Assert
    verify(websocket).sendText(
        eq("{\"ID\":2,\"Key\":{\"Name\":[\"Key\"]},\"View\":[\"MID\",\"BID\",\"ASK\",\"VALUE_DT1\",\"VALUE_TS1\"]}"));
    verify(spec).getKey();
    assertTrue(reactiveMarketDataUpdater.requestSent);
  }

  /**
   * Test {@link ReactiveMarketDataUpdater#onTextMessage(WebSocket, String)} with {@code websocket}, {@code message}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReactiveMarketDataUpdater#onTextMessage(WebSocket, String)}
   */
  @Test
  @DisplayName("Test onTextMessage(WebSocket, String) with 'websocket', 'message'; when '42'; then throw IllegalStateException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ReactiveMarketDataUpdater.onTextMessage(WebSocket, String)"})
  void testOnTextMessageWithWebsocketMessage_when42_thenThrowIllegalStateException() {
    // Arrange
    when(spec.getKey()).thenThrow(new IllegalStateException(
        "JSON mapper is failing silently in order to skip message:{}{}{}as it is not a quote/fixing update."));

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> reactiveMarketDataUpdater.onTextMessage(null, "42"));
    verify(spec).getKey();
  }

  /**
   * Test {@link ReactiveMarketDataUpdater#onTextMessage(WebSocket, String)} with {@code websocket}, {@code message}.
   * <ul>
   *   <li>When a string.</li>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReactiveMarketDataUpdater#onTextMessage(WebSocket, String)}
   */
  @Test
  @DisplayName("Test onTextMessage(WebSocket, String) with 'websocket', 'message'; when a string; then throw IllegalStateException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ReactiveMarketDataUpdater.onTextMessage(WebSocket, String)"})
  void testOnTextMessageWithWebsocketMessage_whenAString_thenThrowIllegalStateException() {
    // Arrange
    when(spec.getKey()).thenThrow(new IllegalStateException(
        "JSON mapper is failing silently in order to skip message:{}{}{}as it is not a quote/fixing update."));

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> reactiveMarketDataUpdater.onTextMessage(null,
        "{\"ID\":1,\"Domain\":\"Login\",\"Key\":{\"Elements\":{\"ApplicationId\":\"\",\"Position\":\"\",\"AuthenticationToken\":\""
            + "\"},\"NameType\":\"AuthnToken\"}}"));
    verify(spec).getKey();
  }

  /**
   * Test {@link ReactiveMarketDataUpdater#onTextMessage(WebSocket, String)} with {@code websocket}, {@code message}.
   * <ul>
   *   <li>When {@code ...done}.</li>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReactiveMarketDataUpdater#onTextMessage(WebSocket, String)}
   */
  @Test
  @DisplayName("Test onTextMessage(WebSocket, String) with 'websocket', 'message'; when '...done'; then throw IllegalStateException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ReactiveMarketDataUpdater.onTextMessage(WebSocket, String)"})
  void testOnTextMessageWithWebsocketMessage_whenDone_thenThrowIllegalStateException() {
    // Arrange
    when(spec.getKey()).thenThrow(new IllegalStateException(
        "JSON mapper is failing silently in order to skip message:{}{}{}as it is not a quote/fixing update."));

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> reactiveMarketDataUpdater.onTextMessage(null, "...done"));
    verify(spec).getKey();
  }

  /**
   * Test {@link ReactiveMarketDataUpdater#onTextMessage(WebSocket, String)} with {@code websocket}, {@code message}.
   * <ul>
   *   <li>When {@code "Key":{"Name":[}.</li>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReactiveMarketDataUpdater#onTextMessage(WebSocket, String)}
   */
  @Test
  @DisplayName("Test onTextMessage(WebSocket, String) with 'websocket', 'message'; when '\"Key\":{\"Name\":['; then throw IllegalStateException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ReactiveMarketDataUpdater.onTextMessage(WebSocket, String)"})
  void testOnTextMessageWithWebsocketMessage_whenKeyName_thenThrowIllegalStateException() {
    // Arrange
    when(spec.getKey()).thenThrow(new IllegalStateException(
        "JSON mapper is failing silently in order to skip message:{}{}{}as it is not a quote/fixing update."));

    // Act and Assert
    assertThrows(IllegalStateException.class,
        () -> reactiveMarketDataUpdater.onTextMessage(null, "\"Key\":{\"Name\":["));
    verify(spec).getKey();
  }

  /**
   * Test {@link ReactiveMarketDataUpdater#onTextMessage(WebSocket, String)} with {@code websocket}, {@code message}.
   * <ul>
   *   <li>When {@code Not all who wander are lost}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReactiveMarketDataUpdater#onTextMessage(WebSocket, String)}
   */
  @Test
  @DisplayName("Test onTextMessage(WebSocket, String) with 'websocket', 'message'; when 'Not all who wander are lost'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ReactiveMarketDataUpdater.onTextMessage(WebSocket, String)"})
  void testOnTextMessageWithWebsocketMessage_whenNotAllWhoWanderAreLost() {
    // Arrange
    when(spec.getKey()).thenThrow(new IllegalStateException(
        "JSON mapper is failing silently in order to skip message:{}{}{}as it is not a quote/fixing update."));

    // Act and Assert
    assertThrows(IllegalStateException.class,
        () -> reactiveMarketDataUpdater.onTextMessage(null, "Not all who wander are lost"));
    verify(spec).getKey();
  }

  /**
   * Test {@link ReactiveMarketDataUpdater#onTextMessage(WebSocket, String)} with {@code websocket}, {@code message}.
   * <ul>
   *   <li>When {@code "}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReactiveMarketDataUpdater#onTextMessage(WebSocket, String)}
   */
  @Test
  @DisplayName("Test onTextMessage(WebSocket, String) with 'websocket', 'message'; when '\"'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ReactiveMarketDataUpdater.onTextMessage(WebSocket, String)"})
  void testOnTextMessageWithWebsocketMessage_whenQuotationMark() {
    // Arrange
    when(spec.getKey()).thenThrow(new IllegalStateException(
        "JSON mapper is failing silently in order to skip message:{}{}{}as it is not a quote/fixing update."));

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> reactiveMarketDataUpdater.onTextMessage(null, "\""));
    verify(spec).getKey();
  }

  /**
   * Test {@link ReactiveMarketDataUpdater#closeStreamsAndLogoff(WebSocket)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>Then calls {@link WebSocket#sendText(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReactiveMarketDataUpdater#closeStreamsAndLogoff(WebSocket)}
   */
  @Test
  @DisplayName("Test closeStreamsAndLogoff(WebSocket); given 'null'; then calls sendText(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ReactiveMarketDataUpdater.closeStreamsAndLogoff(WebSocket)"})
  void testCloseStreamsAndLogoff_givenNull_thenCallsSendText() {
    // Arrange
    WebSocket webSocket = mock(WebSocket.class);
    when(webSocket.sendText(Mockito.<String>any())).thenReturn(null);

    // Act
    reactiveMarketDataUpdater.closeStreamsAndLogoff(webSocket);

    // Assert
    verify(webSocket).sendText(eq("{\"ID\":1, \"Type\": \"Close\", \"Domain\":\"Login\"}"));
  }

  /**
   * Test {@link ReactiveMarketDataUpdater#writeDataset(String, MarketDataSet, boolean)} with {@code String}, {@code MarketDataSet}, {@code boolean}.
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReactiveMarketDataUpdater#writeDataset(String, MarketDataSet, boolean)}
   */
  @Test
  @DisplayName("Test writeDataset(String, MarketDataSet, boolean) with 'String', 'MarketDataSet', 'boolean'; then throw IllegalStateException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ReactiveMarketDataUpdater.writeDataset(String, MarketDataSet, boolean)"})
  void testWriteDatasetWithStringMarketDataSetBoolean_thenThrowIllegalStateException() throws IOException {
    // Arrange
    MarketDataSetValuesInner valuesItem = mock(MarketDataSetValuesInner.class);
    when(valuesItem.getValue()).thenThrow(new IllegalStateException("EUROSTR="));
    when(valuesItem.getDataTimestamp())
        .thenReturn(OffsetDateTime.of(LocalDate.of(1970, 1, 1), LocalTime.MIDNIGHT, ZoneOffset.UTC));
    when(valuesItem.getSymbol()).thenReturn("EUROSTR=");

    MarketDataSet transferMessage = new MarketDataSet();
    transferMessage.addValuesItem(valuesItem);

    // Act and Assert
    assertThrows(IllegalStateException.class,
        () -> reactiveMarketDataUpdater.writeDataset("Import File", transferMessage, true));
    verify(valuesItem, atLeast(1)).getDataTimestamp();
    verify(valuesItem).getSymbol();
    verify(valuesItem).getValue();
  }
}
