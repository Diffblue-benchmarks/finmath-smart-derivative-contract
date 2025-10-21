package net.finmath.smartcontract.valuation.marketdata.generators.legacy;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ReactiveMarketDataUpdater.class, String.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class ReactiveMarketDataUpdaterDiffblueTest {
  @MockBean private JSONObject jSONObject;

  @Autowired private List<Spec> list;

  @Autowired private ReactiveMarketDataUpdater reactiveMarketDataUpdater;

  @MockBean private Spec spec;

  /**
   * Test {@link ReactiveMarketDataUpdater#ReactiveMarketDataUpdater(JSONObject, String, List)}.
   *
   * <p>Method under test: {@link ReactiveMarketDataUpdater#ReactiveMarketDataUpdater(JSONObject,
   * String, List)}
   */
  @Test
  @DisplayName("Test new ReactiveMarketDataUpdater(JSONObject, String, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ReactiveMarketDataUpdater.<init>(JSONObject, String, List)"})
  void testNewReactiveMarketDataUpdater() {
    // Arrange and Act
    ReactiveMarketDataUpdater actualReactiveMarketDataUpdater =
        new ReactiveMarketDataUpdater(
            jSONObject,
            "\"EUR/USD,GBP/USD,USD/JPY,USD/CHF,USD/CAD,AUD/USD,NZD/USD,EUR/GBP,EUR/JPY,EUR/CHF,EUR/CAD,EUR/AUD,EUR"
                + "/NZD,GBP/JPY,GBP/CHF,GBP/CAD,GBP/AUD,GBP/NZD\"",
            new ArrayList<>());

    // Assert
    assertFalse(actualReactiveMarketDataUpdater.requestSent);
  }

  /**
   * Test {@link ReactiveMarketDataUpdater#onConnected(WebSocket, Map)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link WebSocket} {@link WebSocket#sendText(String)} return {@code null}.
   *   <li>Then calls {@link WebSocket#sendText(String)}.
   * </ul>
   *
   * <p>Method under test: {@link ReactiveMarketDataUpdater#onConnected(WebSocket, Map)}
   */
  @Test
  @DisplayName(
      "Test onConnected(WebSocket, Map); given 'null'; when WebSocket sendText(String) return 'null'; then calls sendText(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ReactiveMarketDataUpdater.onConnected(WebSocket, Map)"})
  void testOnConnected_givenNull_whenWebSocketSendTextReturnNull_thenCallsSendText()
      throws Exception {
    // Arrange
    when(jSONObject.getString(Mockito.<String>any())).thenReturn("\"employeeName\"");

    WebSocket websocket = mock(WebSocket.class);
    when(websocket.sendText(Mockito.<String>any())).thenReturn(null);

    // Act
    reactiveMarketDataUpdater.onConnected(websocket, new HashMap<>());

    // Assert
    verify(websocket, atLeast(1)).sendText(Mockito.<String>any());
    verify(jSONObject, atLeast(1)).getString("access_token");
  }

  /**
   * Test {@link ReactiveMarketDataUpdater#onConnected(WebSocket, Map)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link ReactiveMarketDataUpdater#onConnected(WebSocket, Map)}
   */
  @Test
  @DisplayName("Test onConnected(WebSocket, Map); then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ReactiveMarketDataUpdater.onConnected(WebSocket, Map)"})
  void testOnConnected_thenThrowIllegalStateException() throws Exception {
    // Arrange
    when(jSONObject.getString(Mockito.<String>any())).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> reactiveMarketDataUpdater.onConnected(null, new HashMap<>()));
    verify(jSONObject).getString("access_token");
  }

  /**
   * Test {@link ReactiveMarketDataUpdater#asObservable()}.
   *
   * <p>Method under test: {@link ReactiveMarketDataUpdater#asObservable()}
   */
  @Test
  @DisplayName("Test asObservable()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Observable ReactiveMarketDataUpdater.asObservable()"})
  void testAsObservable() {
    // Arrange and Act
    Observable<MarketDataSet> actualAsObservableResult = reactiveMarketDataUpdater.asObservable();

    // Assert
    Observable<Timed<MarketDataSet>> timestampResult = actualAsObservableResult.timestamp();
    assertTrue(timestampResult.timestamp() instanceof ObservableMap);
    Subject<MarketDataSet> toSerializedResult =
        ((PublishSubject<MarketDataSet>) actualAsObservableResult).toSerialized();
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
   * Test {@link ReactiveMarketDataUpdater#onTextMessage(WebSocket, String)} with {@code websocket},
   * {@code message}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@code 42}.
   *   <li>Then calls {@link WebSocket#sendText(String)}.
   * </ul>
   *
   * <p>Method under test: {@link ReactiveMarketDataUpdater#onTextMessage(WebSocket, String)}
   */
  @Test
  @DisplayName(
      "Test onTextMessage(WebSocket, String) with 'websocket', 'message'; given 'null'; when '42'; then calls sendText(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ReactiveMarketDataUpdater.onTextMessage(WebSocket, String)"})
  void testOnTextMessageWithWebsocketMessage_givenNull_when42_thenCallsSendText() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    JSONObject authJson = new JSONObject();
    ReactiveMarketDataUpdater reactiveMarketDataUpdater =
        new ReactiveMarketDataUpdater(
            authJson,
            "\"EUR/USD,GBP/USD,USD/JPY,USD/CHF,USD/CAD,AUD/USD,NZD/USD,EUR/GBP,EUR/JPY,EUR/CHF,EUR/CAD,EUR/AUD,EUR"
                + "/NZD,GBP/JPY,GBP/CHF,GBP/CAD,GBP/AUD,GBP/NZD\"",
            new ArrayList<>());

    WebSocket websocket = mock(WebSocket.class);
    when(websocket.sendText(Mockito.<String>any())).thenReturn(null);

    // Act
    reactiveMarketDataUpdater.onTextMessage(websocket, "42");

    // Assert
    verify(websocket)
        .sendText(
            "{\"ID\":2,\"Key\":{\"Name\":]},\"View\":[\"MID\",\"BID\",\"ASK\",\"VALUE_DT1\",\"VALUE_TS1\"]}");
  }

  /**
   * Test {@link ReactiveMarketDataUpdater#onTextMessage(WebSocket, String)} with {@code websocket},
   * {@code message}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When a string.
   *   <li>Then calls {@link WebSocket#sendText(String)}.
   * </ul>
   *
   * <p>Method under test: {@link ReactiveMarketDataUpdater#onTextMessage(WebSocket, String)}
   */
  @Test
  @DisplayName(
      "Test onTextMessage(WebSocket, String) with 'websocket', 'message'; given 'null'; when a string; then calls sendText(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ReactiveMarketDataUpdater.onTextMessage(WebSocket, String)"})
  void testOnTextMessageWithWebsocketMessage_givenNull_whenAString_thenCallsSendText() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    JSONObject authJson = new JSONObject();
    ReactiveMarketDataUpdater reactiveMarketDataUpdater =
        new ReactiveMarketDataUpdater(
            authJson,
            "\"EUR/USD,GBP/USD,USD/JPY,USD/CHF,USD/CAD,AUD/USD,NZD/USD,EUR/GBP,EUR/JPY,EUR/CHF,EUR/CAD,EUR/AUD,EUR"
                + "/NZD,GBP/JPY,GBP/CHF,GBP/CAD,GBP/AUD,GBP/NZD\"",
            new ArrayList<>());

    WebSocket websocket = mock(WebSocket.class);
    when(websocket.sendText(Mockito.<String>any())).thenReturn(null);

    // Act
    reactiveMarketDataUpdater.onTextMessage(
        websocket,
        "\"{\\\"eventType\\\":\\\"MARKET_UPDATE\\\",\\\"data\\\":{\\\"symbol\\\":\\\"AAPL\\\",\\\"price\\\":150.25,\\\"volume\\\":10000,\\"
            + "\"timestamp\\\":\\\"2022-01-01T12:00:00Z\\\"}}\"");

    // Assert
    verify(websocket)
        .sendText(
            "{\"ID\":2,\"Key\":{\"Name\":]},\"View\":[\"MID\",\"BID\",\"ASK\",\"VALUE_DT1\",\"VALUE_TS1\"]}");
  }

  /**
   * Test {@link ReactiveMarketDataUpdater#onTextMessage(WebSocket, String)} with {@code websocket},
   * {@code message}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When a string.
   *   <li>Then calls {@link WebSocket#sendText(String)}.
   * </ul>
   *
   * <p>Method under test: {@link ReactiveMarketDataUpdater#onTextMessage(WebSocket, String)}
   */
  @Test
  @DisplayName(
      "Test onTextMessage(WebSocket, String) with 'websocket', 'message'; given 'null'; when a string; then calls sendText(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ReactiveMarketDataUpdater.onTextMessage(WebSocket, String)"})
  void testOnTextMessageWithWebsocketMessage_givenNull_whenAString_thenCallsSendText2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    JSONObject authJson = new JSONObject();
    ReactiveMarketDataUpdater reactiveMarketDataUpdater =
        new ReactiveMarketDataUpdater(
            authJson,
            "\"EUR/USD,GBP/USD,USD/JPY,USD/CHF,USD/CAD,AUD/USD,NZD/USD,EUR/GBP,EUR/JPY,EUR/CHF,EUR/CAD,EUR/AUD,EUR"
                + "/NZD,GBP/JPY,GBP/CHF,GBP/CAD,GBP/AUD,GBP/NZD\"",
            new ArrayList<>());

    WebSocket websocket = mock(WebSocket.class);
    when(websocket.sendText(Mockito.<String>any())).thenReturn(null);

    // Act
    reactiveMarketDataUpdater.onTextMessage(
        websocket,
        "{\"ID\":1,\"Domain\":\"Login\",\"Key\":{\"Elements\":{\"ApplicationId\":\"\",\"Position\":\"\",\"AuthenticationToken\":\""
            + "\"},\"NameType\":\"AuthnToken\"}}");

    // Assert
    verify(websocket)
        .sendText(
            "{\"ID\":2,\"Key\":{\"Name\":]},\"View\":[\"MID\",\"BID\",\"ASK\",\"VALUE_DT1\",\"VALUE_TS1\"]}");
  }

  /**
   * Test {@link ReactiveMarketDataUpdater#onTextMessage(WebSocket, String)} with {@code websocket},
   * {@code message}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@code ...done}.
   *   <li>Then calls {@link WebSocket#sendText(String)}.
   * </ul>
   *
   * <p>Method under test: {@link ReactiveMarketDataUpdater#onTextMessage(WebSocket, String)}
   */
  @Test
  @DisplayName(
      "Test onTextMessage(WebSocket, String) with 'websocket', 'message'; given 'null'; when '...done'; then calls sendText(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ReactiveMarketDataUpdater.onTextMessage(WebSocket, String)"})
  void testOnTextMessageWithWebsocketMessage_givenNull_whenDone_thenCallsSendText() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    JSONObject authJson = new JSONObject();
    ReactiveMarketDataUpdater reactiveMarketDataUpdater =
        new ReactiveMarketDataUpdater(
            authJson,
            "\"EUR/USD,GBP/USD,USD/JPY,USD/CHF,USD/CAD,AUD/USD,NZD/USD,EUR/GBP,EUR/JPY,EUR/CHF,EUR/CAD,EUR/AUD,EUR"
                + "/NZD,GBP/JPY,GBP/CHF,GBP/CAD,GBP/AUD,GBP/NZD\"",
            new ArrayList<>());

    WebSocket websocket = mock(WebSocket.class);
    when(websocket.sendText(Mockito.<String>any())).thenReturn(null);

    // Act
    reactiveMarketDataUpdater.onTextMessage(websocket, "...done");

    // Assert
    verify(websocket)
        .sendText(
            "{\"ID\":2,\"Key\":{\"Name\":]},\"View\":[\"MID\",\"BID\",\"ASK\",\"VALUE_DT1\",\"VALUE_TS1\"]}");
  }

  /**
   * Test {@link ReactiveMarketDataUpdater#onTextMessage(WebSocket, String)} with {@code websocket},
   * {@code message}.
   *
   * <ul>
   *   <li>When {@code Not all who wander are lost}.
   * </ul>
   *
   * <p>Method under test: {@link ReactiveMarketDataUpdater#onTextMessage(WebSocket, String)}
   */
  @Test
  @DisplayName(
      "Test onTextMessage(WebSocket, String) with 'websocket', 'message'; when 'Not all who wander are lost'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ReactiveMarketDataUpdater.onTextMessage(WebSocket, String)"})
  void testOnTextMessageWithWebsocketMessage_whenNotAllWhoWanderAreLost() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    JSONObject authJson = new JSONObject();
    ReactiveMarketDataUpdater reactiveMarketDataUpdater =
        new ReactiveMarketDataUpdater(
            authJson,
            "\"EUR/USD,GBP/USD,USD/JPY,USD/CHF,USD/CAD,AUD/USD,NZD/USD,EUR/GBP,EUR/JPY,EUR/CHF,EUR/CAD,EUR/AUD,EUR"
                + "/NZD,GBP/JPY,GBP/CHF,GBP/CAD,GBP/AUD,GBP/NZD\"",
            new ArrayList<>());

    WebSocket websocket = mock(WebSocket.class);
    when(websocket.sendText(Mockito.<String>any())).thenReturn(null);

    // Act
    reactiveMarketDataUpdater.onTextMessage(websocket, "Not all who wander are lost");

    // Assert
    verify(websocket)
        .sendText(
            "{\"ID\":2,\"Key\":{\"Name\":]},\"View\":[\"MID\",\"BID\",\"ASK\",\"VALUE_DT1\",\"VALUE_TS1\"]}");
  }

  /**
   * Test {@link ReactiveMarketDataUpdater#onTextMessage(WebSocket, String)} with {@code websocket},
   * {@code message}.
   *
   * <ul>
   *   <li>When {@code ",}.
   *   <li>Then calls {@link WebSocket#sendText(String)}.
   * </ul>
   *
   * <p>Method under test: {@link ReactiveMarketDataUpdater#onTextMessage(WebSocket, String)}
   */
  @Test
  @DisplayName(
      "Test onTextMessage(WebSocket, String) with 'websocket', 'message'; when '\",'; then calls sendText(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ReactiveMarketDataUpdater.onTextMessage(WebSocket, String)"})
  void testOnTextMessageWithWebsocketMessage_whenQuotationMarkComma_thenCallsSendText() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    JSONObject authJson = new JSONObject();
    ReactiveMarketDataUpdater reactiveMarketDataUpdater =
        new ReactiveMarketDataUpdater(
            authJson,
            "\"EUR/USD,GBP/USD,USD/JPY,USD/CHF,USD/CAD,AUD/USD,NZD/USD,EUR/GBP,EUR/JPY,EUR/CHF,EUR/CAD,EUR/AUD,EUR"
                + "/NZD,GBP/JPY,GBP/CHF,GBP/CAD,GBP/AUD,GBP/NZD\"",
            new ArrayList<>());

    WebSocket websocket = mock(WebSocket.class);
    when(websocket.sendText(Mockito.<String>any())).thenReturn(null);

    // Act
    reactiveMarketDataUpdater.onTextMessage(websocket, "\",");

    // Assert
    verify(websocket)
        .sendText(
            "{\"ID\":2,\"Key\":{\"Name\":]},\"View\":[\"MID\",\"BID\",\"ASK\",\"VALUE_DT1\",\"VALUE_TS1\"]}");
  }

  /**
   * Test {@link ReactiveMarketDataUpdater#onTextMessage(WebSocket, String)} with {@code websocket},
   * {@code message}.
   *
   * <ul>
   *   <li>When {@code ]}}.
   * </ul>
   *
   * <p>Method under test: {@link ReactiveMarketDataUpdater#onTextMessage(WebSocket, String)}
   */
  @Test
  @DisplayName("Test onTextMessage(WebSocket, String) with 'websocket', 'message'; when ']}'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ReactiveMarketDataUpdater.onTextMessage(WebSocket, String)"})
  void testOnTextMessageWithWebsocketMessage_whenRightSquareBracketRightCurlyBracket() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    JSONObject authJson = new JSONObject();
    ReactiveMarketDataUpdater reactiveMarketDataUpdater =
        new ReactiveMarketDataUpdater(
            authJson,
            "\"EUR/USD,GBP/USD,USD/JPY,USD/CHF,USD/CAD,AUD/USD,NZD/USD,EUR/GBP,EUR/JPY,EUR/CHF,EUR/CAD,EUR/AUD,EUR"
                + "/NZD,GBP/JPY,GBP/CHF,GBP/CAD,GBP/AUD,GBP/NZD\"",
            new ArrayList<>());

    WebSocket websocket = mock(WebSocket.class);
    when(websocket.sendText(Mockito.<String>any())).thenReturn(null);

    // Act
    reactiveMarketDataUpdater.onTextMessage(websocket, "]}");

    // Assert
    verify(websocket)
        .sendText(
            "{\"ID\":2,\"Key\":{\"Name\":]},\"View\":[\"MID\",\"BID\",\"ASK\",\"VALUE_DT1\",\"VALUE_TS1\"]}");
  }

  /**
   * Test {@link ReactiveMarketDataUpdater#closeStreamsAndLogoff(WebSocket)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>Then calls {@link WebSocket#sendText(String)}.
   * </ul>
   *
   * <p>Method under test: {@link ReactiveMarketDataUpdater#closeStreamsAndLogoff(WebSocket)}
   */
  @Test
  @DisplayName("Test closeStreamsAndLogoff(WebSocket); given 'null'; then calls sendText(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ReactiveMarketDataUpdater.closeStreamsAndLogoff(WebSocket)"})
  void testCloseStreamsAndLogoff_givenNull_thenCallsSendText() {
    // Arrange
    WebSocket webSocket = mock(WebSocket.class);
    when(webSocket.sendText(Mockito.<String>any())).thenReturn(null);

    // Act
    reactiveMarketDataUpdater.closeStreamsAndLogoff(webSocket);

    // Assert
    verify(webSocket).sendText("{\"ID\":1, \"Type\": \"Close\", \"Domain\":\"Login\"}");
  }

  /**
   * Test {@link ReactiveMarketDataUpdater#writeDataset(String, MarketDataSet, boolean)} with {@code
   * String}, {@code MarketDataSet}, {@code boolean}.
   *
   * <p>Method under test: {@link ReactiveMarketDataUpdater#writeDataset(String, MarketDataSet,
   * boolean)}
   */
  @Test
  @DisplayName(
      "Test writeDataset(String, MarketDataSet, boolean) with 'String', 'MarketDataSet', 'boolean'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ReactiveMarketDataUpdater.writeDataset(String, MarketDataSet, boolean)"})
  void testWriteDatasetWithStringMarketDataSetBoolean() throws IOException {
    // Arrange
    JSONObject authJson = new JSONObject();
    ReactiveMarketDataUpdater reactiveMarketDataUpdater =
        new ReactiveMarketDataUpdater(
            authJson,
            "\"EUR/USD,GBP/USD,USD/JPY,USD/CHF,USD/CAD,AUD/USD,NZD/USD,EUR/GBP,EUR/JPY,EUR/CHF,EUR/CAD,EUR/AUD,EUR"
                + "/NZD,GBP/JPY,GBP/CHF,GBP/CAD,GBP/AUD,GBP/NZD\"",
            new ArrayList<>());

    MarketDataSetValuesInner marketDataSetValuesInner = mock(MarketDataSetValuesInner.class);
    when(marketDataSetValuesInner.getDataTimestamp()).thenThrow(new IllegalStateException());
    when(marketDataSetValuesInner.getSymbol()).thenReturn("EUROSTR=");
    when(marketDataSetValuesInner.symbol(Mockito.<String>any()))
        .thenReturn(new MarketDataSetValuesInner());
    marketDataSetValuesInner.symbol("EUROSTR=");

    MarketDataSetValuesInner marketDataSetValuesInner2 = new MarketDataSetValuesInner();
    marketDataSetValuesInner2.symbol("\"NASDAQ:MSFT\"");

    ArrayList<MarketDataSetValuesInner> marketDataSetValuesInnerList = new ArrayList<>();
    marketDataSetValuesInnerList.add(marketDataSetValuesInner2);
    marketDataSetValuesInnerList.add(marketDataSetValuesInner);

    MarketDataSet transferMessage = mock(MarketDataSet.class);
    when(transferMessage.getValues()).thenReturn(marketDataSetValuesInnerList);

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () ->
            reactiveMarketDataUpdater.writeDataset(
                "\"/home/user/financial_data/market_data_import.csv\"", transferMessage, true));
    verify(transferMessage).getValues();
    verify(marketDataSetValuesInner).getDataTimestamp();
    verify(marketDataSetValuesInner).getSymbol();
    verify(marketDataSetValuesInner).symbol("EUROSTR=");
  }

  /**
   * Test {@link ReactiveMarketDataUpdater#writeDataset(String, MarketDataSet, boolean)} with {@code
   * String}, {@code MarketDataSet}, {@code boolean}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link ReactiveMarketDataUpdater#writeDataset(String, MarketDataSet,
   * boolean)}
   */
  @Test
  @DisplayName(
      "Test writeDataset(String, MarketDataSet, boolean) with 'String', 'MarketDataSet', 'boolean'; then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ReactiveMarketDataUpdater.writeDataset(String, MarketDataSet, boolean)"})
  void testWriteDatasetWithStringMarketDataSetBoolean_thenThrowIllegalStateException()
      throws IOException {
    // Arrange
    MarketDataSetValuesInner marketDataSetValuesInner = mock(MarketDataSetValuesInner.class);
    when(marketDataSetValuesInner.getDataTimestamp()).thenThrow(new IllegalStateException());
    when(marketDataSetValuesInner.getSymbol()).thenReturn("EUROSTR=");
    when(marketDataSetValuesInner.symbol(Mockito.<String>any()))
        .thenReturn(new MarketDataSetValuesInner());
    marketDataSetValuesInner.symbol("EUROSTR=");

    ArrayList<MarketDataSetValuesInner> marketDataSetValuesInnerList = new ArrayList<>();
    marketDataSetValuesInnerList.add(marketDataSetValuesInner);

    MarketDataSet transferMessage = mock(MarketDataSet.class);
    when(transferMessage.getValues()).thenReturn(marketDataSetValuesInnerList);

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () ->
            reactiveMarketDataUpdater.writeDataset(
                "\"/home/user/financial_data/market_data_import.csv\"", transferMessage, true));
    verify(transferMessage).getValues();
    verify(marketDataSetValuesInner).getDataTimestamp();
    verify(marketDataSetValuesInner).getSymbol();
    verify(marketDataSetValuesInner).symbol("EUROSTR=");
  }
}
