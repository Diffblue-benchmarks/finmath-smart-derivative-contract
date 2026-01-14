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
import net.finmath.smartcontract.valuation.marketdata.data.LocalDateTimeAdapterDiffblueBase;
import net.finmath.smartcontract.valuation.marketdata.generators.MarketDataGeneratorLauncherDiffblueBase;
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
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
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
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link ReactiveMarketDataUpdater#ReactiveMarketDataUpdater(JSONObject,
   * String, List)}
   */
  @Test
  @DisplayName("Test new ReactiveMarketDataUpdater(JSONObject, String, List); when ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ReactiveMarketDataUpdater.<init>(JSONObject, String, List)"})
  void testNewReactiveMarketDataUpdater_whenArrayList() {
    // Arrange
    String position =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();

    // Act
    ReactiveMarketDataUpdater actualReactiveMarketDataUpdater =
        new ReactiveMarketDataUpdater(jSONObject, position, new ArrayList<>());

    // Assert
    assertFalse(actualReactiveMarketDataUpdater.requestSent);
  }

  /**
   * Test {@link ReactiveMarketDataUpdater#ReactiveMarketDataUpdater(JSONObject, String, List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()} add {@link CalibrationDataItem.Spec}.
   * </ul>
   *
   * <p>Method under test: {@link ReactiveMarketDataUpdater#ReactiveMarketDataUpdater(JSONObject,
   * String, List)}
   */
  @Test
  @DisplayName(
      "Test new ReactiveMarketDataUpdater(JSONObject, String, List); when ArrayList() add Spec")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ReactiveMarketDataUpdater.<init>(JSONObject, String, List)"})
  void testNewReactiveMarketDataUpdater_whenArrayListAddSpec() {
    // Arrange
    String position =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();

    ArrayList<Spec> itemList = new ArrayList<>();
    itemList.add(spec);

    // Act
    ReactiveMarketDataUpdater actualReactiveMarketDataUpdater =
        new ReactiveMarketDataUpdater(jSONObject, position, itemList);

    // Assert
    assertFalse(actualReactiveMarketDataUpdater.requestSent);
  }

  /**
   * Test {@link ReactiveMarketDataUpdater#ReactiveMarketDataUpdater(JSONObject, String, List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()} add {@link CalibrationDataItem.Spec}.
   * </ul>
   *
   * <p>Method under test: {@link ReactiveMarketDataUpdater#ReactiveMarketDataUpdater(JSONObject,
   * String, List)}
   */
  @Test
  @DisplayName(
      "Test new ReactiveMarketDataUpdater(JSONObject, String, List); when ArrayList() add Spec")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ReactiveMarketDataUpdater.<init>(JSONObject, String, List)"})
  void testNewReactiveMarketDataUpdater_whenArrayListAddSpec2() {
    // Arrange
    String position =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();

    ArrayList<Spec> itemList = new ArrayList<>();
    itemList.add(spec);
    itemList.add(spec);

    // Act
    ReactiveMarketDataUpdater actualReactiveMarketDataUpdater =
        new ReactiveMarketDataUpdater(jSONObject, position, itemList);

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
    when(jSONObject.getString(Mockito.<String>any()))
        .thenReturn(
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());

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
   * <p>Method under test: {@link ReactiveMarketDataUpdater#onTextMessage(WebSocket, String)}
   */
  @Test
  @DisplayName("Test onTextMessage(WebSocket, String) with 'websocket', 'message'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ReactiveMarketDataUpdater.onTextMessage(WebSocket, String)"})
  void testOnTextMessageWithWebsocketMessage() {
    // Arrange
    when(spec.getKey())
        .thenReturn(
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());

    WebSocket websocket = mock(WebSocket.class);
    when(websocket.sendText(Mockito.<String>any())).thenReturn(null);

    // Act
    reactiveMarketDataUpdater.onTextMessage(
        websocket,
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());

    // Assert
    verify(websocket)
        .sendText(
            "{\"ID\":2,\"Key\":{\"Name\":[\"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><smartderivativecontract xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"uri:sdc\" xsi:schemaLocation=\"uri:sdc smartderivativecontract.xsd\"><dltTradeId>TEST-TRADE-001</dltTradeId><dltAddress>0xTEST</dltAddress><uniqueTradeIdentifier>UTI-TEST-001</uniqueTradeIdentifier><settlementCurrency>EUR</settlementCurrency><tradeType>SDCPledgedBalance</tradeType><valuation>  <artefact>    <groupId>net.finmath</groupId>    <artifactId>finmath-smart-derivative-contract</artifactId>    <version>1.0.0</version>  </artefact></valuation><parties>  <party>    <name>Party 1</name>    <id>party1</id>    <marginAccount><type>constant</type><value>10000.0</value></marginAccount>    <penaltyFee><type>constant</type><value>100.0</value></penaltyFee>    <address>0x1234</address>  </party>  <party>    <name>Party 2</name>    <id>party2</id>    <marginAccount><type>constant</type><value>10000.0</value></marginAccount>    <penaltyFee><type>constant</type><value>100.0</value></penaltyFee>    <address>0x5678</address>  </party></parties><settlement>  <settlementTime><type>daily</type><value>17:00</value></settlementTime>  <marketdata>    <provider>test</provider>    <marketdataitems>      <item>        <symbol>EUR-EONIA</symbol>        <curve>ESTR</curve>        <type>Fixing</type>        <tenor>1D</tenor>      </item>    </marketdataitems>  </marketdata></settlement><trade>  <product>InterestRateDerivative</product>  <productType>Swap</productType>  <startDate>2024-01-01</startDate>  <endDate>2029-01-01</endDate>  <receiverPartyReference><partyReference>party1</partyReference></receiverPartyReference>  <notional><currency>EUR</currency><amount>1000000.0</amount></notional>  <swap>    <swapStream id=\"floatLeg\">      <receiverPartyReference>party1</receiverPartyReference>      <calculationPeriodDates>        <effectiveDate>2024-01-01</effectiveDate>        <terminationDate>2029-01-01</terminationDate>        <calculationPeriodFrequency><periodMultiplier>6</periodMultiplier><period>M</period></calculationPeriodFrequency>      </calculationPeriodDates>      <paymentDates>        <paymentFrequency><periodMultiplier>6</periodMultiplier><period>M</period></paymentFrequency>      </paymentDates>      <calculationPeriodAmount>        <calculation><notional><currency>EUR</currency><amount>1000000.0</amount></notional>        <floatingRateCalculation><floatingRateIndex>EUR-EURIBOR-Reuters</floatingRateIndex><indexTenor><periodMultiplier>6</periodMultiplier><period>M</period></indexTenor></floatingRateCalculation>        </calculation>      </calculationPeriodAmount>    </swapStream>    <swapStream id=\"fixedLeg\">      <receiverPartyReference>party2</receiverPartyReference>      <calculationPeriodDates>        <effectiveDate>2024-01-01</effectiveDate>        <terminationDate>2029-01-01</terminationDate>        <calculationPeriodFrequency><periodMultiplier>1</periodMultiplier><period>Y</period></calculationPeriodFrequency>      </calculationPeriodDates>      <paymentDates>        <paymentFrequency><periodMultiplier>1</periodMultiplier><period>Y</period></paymentFrequency>      </paymentDates>      <calculationPeriodAmount>        <calculation><notional><currency>EUR</currency><amount>1000000.0</amount></notional>        <fixedRateSchedule><initialValue>0.02</initialValue></fixedRateSchedule>        </calculation>      </calculationPeriodAmount>    </swapStream>  </swap></trade></smartderivativecontract>\"]},\"View\":[\"MID\",\"BID\",\"ASK\",\"VALUE_DT1\",\"VALUE_TS1\"]}");
    verify(spec).getKey();
    assertTrue(reactiveMarketDataUpdater.requestSent);
  }

  /**
   * Test {@link ReactiveMarketDataUpdater#onTextMessage(WebSocket, String)} with {@code websocket},
   * {@code message}.
   *
   * <p>Method under test: {@link ReactiveMarketDataUpdater#onTextMessage(WebSocket, String)}
   */
  @Test
  @DisplayName("Test onTextMessage(WebSocket, String) with 'websocket', 'message'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ReactiveMarketDataUpdater.onTextMessage(WebSocket, String)"})
  void testOnTextMessageWithWebsocketMessage2() {
    // Arrange and Act
    reactiveMarketDataUpdater.onTextMessage(mock(WebSocket.class), "");

    // Assert that nothing has changed
    assertFalse(reactiveMarketDataUpdater.requestSent);
  }

  /**
   * Test {@link ReactiveMarketDataUpdater#onTextMessage(WebSocket, String)} with {@code websocket},
   * {@code message}.
   *
   * <p>Method under test: {@link ReactiveMarketDataUpdater#onTextMessage(WebSocket, String)}
   */
  @Test
  @DisplayName("Test onTextMessage(WebSocket, String) with 'websocket', 'message'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ReactiveMarketDataUpdater.onTextMessage(WebSocket, String)"})
  void testOnTextMessageWithWebsocketMessage3() {
    // Arrange
    JSONObject authJson = new JSONObject();
    String position =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();

    ReactiveMarketDataUpdater reactiveMarketDataUpdater =
        new ReactiveMarketDataUpdater(authJson, position, new ArrayList<>());

    WebSocket websocket = mock(WebSocket.class);
    when(websocket.sendText(Mockito.<String>any())).thenReturn(null);

    // Act
    reactiveMarketDataUpdater.onTextMessage(
        websocket,
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());

    // Assert that nothing has changed
    verify(websocket)
        .sendText(
            "{\"ID\":2,\"Key\":{\"Name\":]},\"View\":[\"MID\",\"BID\",\"ASK\",\"VALUE_DT1\",\"VALUE_TS1\"]}");
    assertFalse(reactiveMarketDataUpdater.requestSent);
  }

  /**
   * Test {@link ReactiveMarketDataUpdater#onTextMessage(WebSocket, String)} with {@code websocket},
   * {@code message}.
   *
   * <p>Method under test: {@link ReactiveMarketDataUpdater#onTextMessage(WebSocket, String)}
   */
  @Test
  @DisplayName("Test onTextMessage(WebSocket, String) with 'websocket', 'message'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ReactiveMarketDataUpdater.onTextMessage(WebSocket, String)"})
  void testOnTextMessageWithWebsocketMessage4() {
    // Arrange
    JSONObject authJson = new JSONObject();
    String position =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();

    ReactiveMarketDataUpdater reactiveMarketDataUpdater =
        new ReactiveMarketDataUpdater(authJson, position, new ArrayList<>());

    WebSocket websocket = mock(WebSocket.class);
    when(websocket.sendText(Mockito.<String>any())).thenReturn(null);

    // Act
    reactiveMarketDataUpdater.onTextMessage(
        websocket, LocalDateTimeAdapterDiffblueBase.createValidDateTimeString());

    // Assert that nothing has changed
    verify(websocket)
        .sendText(
            "{\"ID\":2,\"Key\":{\"Name\":]},\"View\":[\"MID\",\"BID\",\"ASK\",\"VALUE_DT1\",\"VALUE_TS1\"]}");
    assertFalse(reactiveMarketDataUpdater.requestSent);
  }

  /**
   * Test {@link ReactiveMarketDataUpdater#onTextMessage(WebSocket, String)} with {@code websocket},
   * {@code message}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link ReactiveMarketDataUpdater#onTextMessage(WebSocket, String)}
   */
  @Test
  @DisplayName(
      "Test onTextMessage(WebSocket, String) with 'websocket', 'message'; then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ReactiveMarketDataUpdater.onTextMessage(WebSocket, String)"})
  void testOnTextMessageWithWebsocketMessage_thenThrowIllegalStateException() {
    // Arrange
    when(spec.getKey()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () ->
            reactiveMarketDataUpdater.onTextMessage(
                null,
                MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml()));
    verify(spec).getKey();
  }

  /**
   * Test {@link ReactiveMarketDataUpdater#onTextMessage(WebSocket, String)} with {@code websocket},
   * {@code message}.
   *
   * <ul>
   *   <li>When {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link ReactiveMarketDataUpdater#onTextMessage(WebSocket, String)}
   */
  @Test
  @DisplayName("Test onTextMessage(WebSocket, String) with 'websocket', 'message'; when '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ReactiveMarketDataUpdater.onTextMessage(WebSocket, String)"})
  void testOnTextMessageWithWebsocketMessage_when42() {
    // Arrange
    when(spec.getKey())
        .thenReturn(
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());

    WebSocket websocket = mock(WebSocket.class);
    when(websocket.sendText(Mockito.<String>any())).thenReturn(null);

    // Act
    reactiveMarketDataUpdater.onTextMessage(websocket, "42");

    // Assert
    verify(websocket)
        .sendText(
            "{\"ID\":2,\"Key\":{\"Name\":[\"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><smartderivativecontract xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"uri:sdc\" xsi:schemaLocation=\"uri:sdc smartderivativecontract.xsd\"><dltTradeId>TEST-TRADE-001</dltTradeId><dltAddress>0xTEST</dltAddress><uniqueTradeIdentifier>UTI-TEST-001</uniqueTradeIdentifier><settlementCurrency>EUR</settlementCurrency><tradeType>SDCPledgedBalance</tradeType><valuation>  <artefact>    <groupId>net.finmath</groupId>    <artifactId>finmath-smart-derivative-contract</artifactId>    <version>1.0.0</version>  </artefact></valuation><parties>  <party>    <name>Party 1</name>    <id>party1</id>    <marginAccount><type>constant</type><value>10000.0</value></marginAccount>    <penaltyFee><type>constant</type><value>100.0</value></penaltyFee>    <address>0x1234</address>  </party>  <party>    <name>Party 2</name>    <id>party2</id>    <marginAccount><type>constant</type><value>10000.0</value></marginAccount>    <penaltyFee><type>constant</type><value>100.0</value></penaltyFee>    <address>0x5678</address>  </party></parties><settlement>  <settlementTime><type>daily</type><value>17:00</value></settlementTime>  <marketdata>    <provider>test</provider>    <marketdataitems>      <item>        <symbol>EUR-EONIA</symbol>        <curve>ESTR</curve>        <type>Fixing</type>        <tenor>1D</tenor>      </item>    </marketdataitems>  </marketdata></settlement><trade>  <product>InterestRateDerivative</product>  <productType>Swap</productType>  <startDate>2024-01-01</startDate>  <endDate>2029-01-01</endDate>  <receiverPartyReference><partyReference>party1</partyReference></receiverPartyReference>  <notional><currency>EUR</currency><amount>1000000.0</amount></notional>  <swap>    <swapStream id=\"floatLeg\">      <receiverPartyReference>party1</receiverPartyReference>      <calculationPeriodDates>        <effectiveDate>2024-01-01</effectiveDate>        <terminationDate>2029-01-01</terminationDate>        <calculationPeriodFrequency><periodMultiplier>6</periodMultiplier><period>M</period></calculationPeriodFrequency>      </calculationPeriodDates>      <paymentDates>        <paymentFrequency><periodMultiplier>6</periodMultiplier><period>M</period></paymentFrequency>      </paymentDates>      <calculationPeriodAmount>        <calculation><notional><currency>EUR</currency><amount>1000000.0</amount></notional>        <floatingRateCalculation><floatingRateIndex>EUR-EURIBOR-Reuters</floatingRateIndex><indexTenor><periodMultiplier>6</periodMultiplier><period>M</period></indexTenor></floatingRateCalculation>        </calculation>      </calculationPeriodAmount>    </swapStream>    <swapStream id=\"fixedLeg\">      <receiverPartyReference>party2</receiverPartyReference>      <calculationPeriodDates>        <effectiveDate>2024-01-01</effectiveDate>        <terminationDate>2029-01-01</terminationDate>        <calculationPeriodFrequency><periodMultiplier>1</periodMultiplier><period>Y</period></calculationPeriodFrequency>      </calculationPeriodDates>      <paymentDates>        <paymentFrequency><periodMultiplier>1</periodMultiplier><period>Y</period></paymentFrequency>      </paymentDates>      <calculationPeriodAmount>        <calculation><notional><currency>EUR</currency><amount>1000000.0</amount></notional>        <fixedRateSchedule><initialValue>0.02</initialValue></fixedRateSchedule>        </calculation>      </calculationPeriodAmount>    </swapStream>  </swap></trade></smartderivativecontract>\"]},\"View\":[\"MID\",\"BID\",\"ASK\",\"VALUE_DT1\",\"VALUE_TS1\"]}");
    verify(spec).getKey();
    assertTrue(reactiveMarketDataUpdater.requestSent);
  }

  /**
   * Test {@link ReactiveMarketDataUpdater#onTextMessage(WebSocket, String)} with {@code websocket},
   * {@code message}.
   *
   * <ul>
   *   <li>When createValidDateTimeString.
   * </ul>
   *
   * <p>Method under test: {@link ReactiveMarketDataUpdater#onTextMessage(WebSocket, String)}
   */
  @Test
  @DisplayName(
      "Test onTextMessage(WebSocket, String) with 'websocket', 'message'; when createValidDateTimeString")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ReactiveMarketDataUpdater.onTextMessage(WebSocket, String)"})
  void testOnTextMessageWithWebsocketMessage_whenCreateValidDateTimeString() {
    // Arrange
    when(spec.getKey())
        .thenReturn(
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());

    WebSocket websocket = mock(WebSocket.class);
    when(websocket.sendText(Mockito.<String>any())).thenReturn(null);

    // Act
    reactiveMarketDataUpdater.onTextMessage(
        websocket, LocalDateTimeAdapterDiffblueBase.createValidDateTimeString());

    // Assert
    verify(websocket)
        .sendText(
            "{\"ID\":2,\"Key\":{\"Name\":[\"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><smartderivativecontract xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"uri:sdc\" xsi:schemaLocation=\"uri:sdc smartderivativecontract.xsd\"><dltTradeId>TEST-TRADE-001</dltTradeId><dltAddress>0xTEST</dltAddress><uniqueTradeIdentifier>UTI-TEST-001</uniqueTradeIdentifier><settlementCurrency>EUR</settlementCurrency><tradeType>SDCPledgedBalance</tradeType><valuation>  <artefact>    <groupId>net.finmath</groupId>    <artifactId>finmath-smart-derivative-contract</artifactId>    <version>1.0.0</version>  </artefact></valuation><parties>  <party>    <name>Party 1</name>    <id>party1</id>    <marginAccount><type>constant</type><value>10000.0</value></marginAccount>    <penaltyFee><type>constant</type><value>100.0</value></penaltyFee>    <address>0x1234</address>  </party>  <party>    <name>Party 2</name>    <id>party2</id>    <marginAccount><type>constant</type><value>10000.0</value></marginAccount>    <penaltyFee><type>constant</type><value>100.0</value></penaltyFee>    <address>0x5678</address>  </party></parties><settlement>  <settlementTime><type>daily</type><value>17:00</value></settlementTime>  <marketdata>    <provider>test</provider>    <marketdataitems>      <item>        <symbol>EUR-EONIA</symbol>        <curve>ESTR</curve>        <type>Fixing</type>        <tenor>1D</tenor>      </item>    </marketdataitems>  </marketdata></settlement><trade>  <product>InterestRateDerivative</product>  <productType>Swap</productType>  <startDate>2024-01-01</startDate>  <endDate>2029-01-01</endDate>  <receiverPartyReference><partyReference>party1</partyReference></receiverPartyReference>  <notional><currency>EUR</currency><amount>1000000.0</amount></notional>  <swap>    <swapStream id=\"floatLeg\">      <receiverPartyReference>party1</receiverPartyReference>      <calculationPeriodDates>        <effectiveDate>2024-01-01</effectiveDate>        <terminationDate>2029-01-01</terminationDate>        <calculationPeriodFrequency><periodMultiplier>6</periodMultiplier><period>M</period></calculationPeriodFrequency>      </calculationPeriodDates>      <paymentDates>        <paymentFrequency><periodMultiplier>6</periodMultiplier><period>M</period></paymentFrequency>      </paymentDates>      <calculationPeriodAmount>        <calculation><notional><currency>EUR</currency><amount>1000000.0</amount></notional>        <floatingRateCalculation><floatingRateIndex>EUR-EURIBOR-Reuters</floatingRateIndex><indexTenor><periodMultiplier>6</periodMultiplier><period>M</period></indexTenor></floatingRateCalculation>        </calculation>      </calculationPeriodAmount>    </swapStream>    <swapStream id=\"fixedLeg\">      <receiverPartyReference>party2</receiverPartyReference>      <calculationPeriodDates>        <effectiveDate>2024-01-01</effectiveDate>        <terminationDate>2029-01-01</terminationDate>        <calculationPeriodFrequency><periodMultiplier>1</periodMultiplier><period>Y</period></calculationPeriodFrequency>      </calculationPeriodDates>      <paymentDates>        <paymentFrequency><periodMultiplier>1</periodMultiplier><period>Y</period></paymentFrequency>      </paymentDates>      <calculationPeriodAmount>        <calculation><notional><currency>EUR</currency><amount>1000000.0</amount></notional>        <fixedRateSchedule><initialValue>0.02</initialValue></fixedRateSchedule>        </calculation>      </calculationPeriodAmount>    </swapStream>  </swap></trade></smartderivativecontract>\"]},\"View\":[\"MID\",\"BID\",\"ASK\",\"VALUE_DT1\",\"VALUE_TS1\"]}");
    verify(spec).getKey();
    assertTrue(reactiveMarketDataUpdater.requestSent);
  }

  /**
   * Test {@link ReactiveMarketDataUpdater#onTextMessage(WebSocket, String)} with {@code websocket}, {@code message}.
   * <ul>
   *   <li>When {@code "Key":{"Name":[}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReactiveMarketDataUpdater#onTextMessage(WebSocket, String)}
   */
  @Test
  @DisplayName(
      "Test onTextMessage(WebSocket, String) with 'websocket', 'message'; when '\"Key\":{\"Name\":['")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ReactiveMarketDataUpdater.onTextMessage(WebSocket, String)"})
  void testOnTextMessageWithWebsocketMessage_whenKeyName() {
    // Arrange
    when(spec.getKey())
        .thenReturn(
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());

    WebSocket websocket = mock(WebSocket.class);
    when(websocket.sendText(Mockito.<String>any())).thenReturn(null);

    // Act
    reactiveMarketDataUpdater.onTextMessage(websocket, "\"Key\":{\"Name\":[");

    // Assert
    verify(websocket)
        .sendText(
            "{\"ID\":2,\"Key\":{\"Name\":[\"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><smartderivativecontract xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"uri:sdc\" xsi:schemaLocation=\"uri:sdc smartderivativecontract.xsd\"><dltTradeId>TEST-TRADE-001</dltTradeId><dltAddress>0xTEST</dltAddress><uniqueTradeIdentifier>UTI-TEST-001</uniqueTradeIdentifier><settlementCurrency>EUR</settlementCurrency><tradeType>SDCPledgedBalance</tradeType><valuation>  <artefact>    <groupId>net.finmath</groupId>    <artifactId>finmath-smart-derivative-contract</artifactId>    <version>1.0.0</version>  </artefact></valuation><parties>  <party>    <name>Party 1</name>    <id>party1</id>    <marginAccount><type>constant</type><value>10000.0</value></marginAccount>    <penaltyFee><type>constant</type><value>100.0</value></penaltyFee>    <address>0x1234</address>  </party>  <party>    <name>Party 2</name>    <id>party2</id>    <marginAccount><type>constant</type><value>10000.0</value></marginAccount>    <penaltyFee><type>constant</type><value>100.0</value></penaltyFee>    <address>0x5678</address>  </party></parties><settlement>  <settlementTime><type>daily</type><value>17:00</value></settlementTime>  <marketdata>    <provider>test</provider>    <marketdataitems>      <item>        <symbol>EUR-EONIA</symbol>        <curve>ESTR</curve>        <type>Fixing</type>        <tenor>1D</tenor>      </item>    </marketdataitems>  </marketdata></settlement><trade>  <product>InterestRateDerivative</product>  <productType>Swap</productType>  <startDate>2024-01-01</startDate>  <endDate>2029-01-01</endDate>  <receiverPartyReference><partyReference>party1</partyReference></receiverPartyReference>  <notional><currency>EUR</currency><amount>1000000.0</amount></notional>  <swap>    <swapStream id=\"floatLeg\">      <receiverPartyReference>party1</receiverPartyReference>      <calculationPeriodDates>        <effectiveDate>2024-01-01</effectiveDate>        <terminationDate>2029-01-01</terminationDate>        <calculationPeriodFrequency><periodMultiplier>6</periodMultiplier><period>M</period></calculationPeriodFrequency>      </calculationPeriodDates>      <paymentDates>        <paymentFrequency><periodMultiplier>6</periodMultiplier><period>M</period></paymentFrequency>      </paymentDates>      <calculationPeriodAmount>        <calculation><notional><currency>EUR</currency><amount>1000000.0</amount></notional>        <floatingRateCalculation><floatingRateIndex>EUR-EURIBOR-Reuters</floatingRateIndex><indexTenor><periodMultiplier>6</periodMultiplier><period>M</period></indexTenor></floatingRateCalculation>        </calculation>      </calculationPeriodAmount>    </swapStream>    <swapStream id=\"fixedLeg\">      <receiverPartyReference>party2</receiverPartyReference>      <calculationPeriodDates>        <effectiveDate>2024-01-01</effectiveDate>        <terminationDate>2029-01-01</terminationDate>        <calculationPeriodFrequency><periodMultiplier>1</periodMultiplier><period>Y</period></calculationPeriodFrequency>      </calculationPeriodDates>      <paymentDates>        <paymentFrequency><periodMultiplier>1</periodMultiplier><period>Y</period></paymentFrequency>      </paymentDates>      <calculationPeriodAmount>        <calculation><notional><currency>EUR</currency><amount>1000000.0</amount></notional>        <fixedRateSchedule><initialValue>0.02</initialValue></fixedRateSchedule>        </calculation>      </calculationPeriodAmount>    </swapStream>  </swap></trade></smartderivativecontract>\"]},\"View\":[\"MID\",\"BID\",\"ASK\",\"VALUE_DT1\",\"VALUE_TS1\"]}");
    verify(spec).getKey();
    assertTrue(reactiveMarketDataUpdater.requestSent);
  }

  /**
   * Test {@link ReactiveMarketDataUpdater#onTextMessage(WebSocket, String)} with {@code websocket},
   * {@code message}.
   *
   * <ul>
   *   <li>When {@code ",}.
   * </ul>
   *
   * <p>Method under test: {@link ReactiveMarketDataUpdater#onTextMessage(WebSocket, String)}
   */
  @Test
  @DisplayName("Test onTextMessage(WebSocket, String) with 'websocket', 'message'; when '\",'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ReactiveMarketDataUpdater.onTextMessage(WebSocket, String)"})
  void testOnTextMessageWithWebsocketMessage_whenQuotationMarkComma() {
    // Arrange
    when(spec.getKey())
        .thenReturn(
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());

    WebSocket websocket = mock(WebSocket.class);
    when(websocket.sendText(Mockito.<String>any())).thenReturn(null);

    // Act
    reactiveMarketDataUpdater.onTextMessage(websocket, "\",");

    // Assert
    verify(websocket)
        .sendText(
            "{\"ID\":2,\"Key\":{\"Name\":[\"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><smartderivativecontract xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"uri:sdc\" xsi:schemaLocation=\"uri:sdc smartderivativecontract.xsd\"><dltTradeId>TEST-TRADE-001</dltTradeId><dltAddress>0xTEST</dltAddress><uniqueTradeIdentifier>UTI-TEST-001</uniqueTradeIdentifier><settlementCurrency>EUR</settlementCurrency><tradeType>SDCPledgedBalance</tradeType><valuation>  <artefact>    <groupId>net.finmath</groupId>    <artifactId>finmath-smart-derivative-contract</artifactId>    <version>1.0.0</version>  </artefact></valuation><parties>  <party>    <name>Party 1</name>    <id>party1</id>    <marginAccount><type>constant</type><value>10000.0</value></marginAccount>    <penaltyFee><type>constant</type><value>100.0</value></penaltyFee>    <address>0x1234</address>  </party>  <party>    <name>Party 2</name>    <id>party2</id>    <marginAccount><type>constant</type><value>10000.0</value></marginAccount>    <penaltyFee><type>constant</type><value>100.0</value></penaltyFee>    <address>0x5678</address>  </party></parties><settlement>  <settlementTime><type>daily</type><value>17:00</value></settlementTime>  <marketdata>    <provider>test</provider>    <marketdataitems>      <item>        <symbol>EUR-EONIA</symbol>        <curve>ESTR</curve>        <type>Fixing</type>        <tenor>1D</tenor>      </item>    </marketdataitems>  </marketdata></settlement><trade>  <product>InterestRateDerivative</product>  <productType>Swap</productType>  <startDate>2024-01-01</startDate>  <endDate>2029-01-01</endDate>  <receiverPartyReference><partyReference>party1</partyReference></receiverPartyReference>  <notional><currency>EUR</currency><amount>1000000.0</amount></notional>  <swap>    <swapStream id=\"floatLeg\">      <receiverPartyReference>party1</receiverPartyReference>      <calculationPeriodDates>        <effectiveDate>2024-01-01</effectiveDate>        <terminationDate>2029-01-01</terminationDate>        <calculationPeriodFrequency><periodMultiplier>6</periodMultiplier><period>M</period></calculationPeriodFrequency>      </calculationPeriodDates>      <paymentDates>        <paymentFrequency><periodMultiplier>6</periodMultiplier><period>M</period></paymentFrequency>      </paymentDates>      <calculationPeriodAmount>        <calculation><notional><currency>EUR</currency><amount>1000000.0</amount></notional>        <floatingRateCalculation><floatingRateIndex>EUR-EURIBOR-Reuters</floatingRateIndex><indexTenor><periodMultiplier>6</periodMultiplier><period>M</period></indexTenor></floatingRateCalculation>        </calculation>      </calculationPeriodAmount>    </swapStream>    <swapStream id=\"fixedLeg\">      <receiverPartyReference>party2</receiverPartyReference>      <calculationPeriodDates>        <effectiveDate>2024-01-01</effectiveDate>        <terminationDate>2029-01-01</terminationDate>        <calculationPeriodFrequency><periodMultiplier>1</periodMultiplier><period>Y</period></calculationPeriodFrequency>      </calculationPeriodDates>      <paymentDates>        <paymentFrequency><periodMultiplier>1</periodMultiplier><period>Y</period></paymentFrequency>      </paymentDates>      <calculationPeriodAmount>        <calculation><notional><currency>EUR</currency><amount>1000000.0</amount></notional>        <fixedRateSchedule><initialValue>0.02</initialValue></fixedRateSchedule>        </calculation>      </calculationPeriodAmount>    </swapStream>  </swap></trade></smartderivativecontract>\"]},\"View\":[\"MID\",\"BID\",\"ASK\",\"VALUE_DT1\",\"VALUE_TS1\"]}");
    verify(spec).getKey();
    assertTrue(reactiveMarketDataUpdater.requestSent);
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
    String importFile =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();

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
        () -> reactiveMarketDataUpdater.writeDataset(importFile, transferMessage, true));
    verify(transferMessage).getValues();
    verify(marketDataSetValuesInner).getDataTimestamp();
    verify(marketDataSetValuesInner).getSymbol();
    verify(marketDataSetValuesInner).symbol("EUROSTR=");
  }
}
