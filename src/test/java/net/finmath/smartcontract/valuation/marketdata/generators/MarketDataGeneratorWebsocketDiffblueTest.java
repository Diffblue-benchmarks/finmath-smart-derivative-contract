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
import net.finmath.smartcontract.settlement.SettlementGeneratorDiffblueBase;
import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationDataItem;
import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationDataItem.Spec;
import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationDataItemTestFactory;
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
   *   <li>Given {@link ArrayList#ArrayList()} add createSpecForSwapRate.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link MarketDataGeneratorWebsocket#allQuotesRetrieved()}
   */
  @Test
  @DisplayName(
      "Test allQuotesRetrieved(); given ArrayList() add createSpecForSwapRate; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean MarketDataGeneratorWebsocket.allQuotesRetrieved()"})
  void testAllQuotesRetrieved_givenArrayListAddCreateSpecForSwapRate_thenReturnFalse() {
    // Arrange
    ArrayList<Spec> itemList = new ArrayList<>();
    itemList.add(CalibrationDataItemTestFactory.createSpecForSwapRate());
    JSONObject authJson = new JSONObject();

    MarketDataGeneratorWebsocket marketDataGeneratorWebsocket =
        new MarketDataGeneratorWebsocket(
            authJson,
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml(),
            itemList);

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
    String position =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();

    MarketDataGeneratorWebsocket marketDataGeneratorWebsocket =
        new MarketDataGeneratorWebsocket(authJson, position, new ArrayList<>());

    // Act and Assert
    assertTrue(marketDataGeneratorWebsocket.allQuotesRetrieved());
  }

  /**
   * Test {@link MarketDataGeneratorWebsocket#onConnected(WebSocket, Map)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link WebSocket} {@link WebSocket#sendText(String)} return {@code null}.
   *   <li>Then calls {@link WebSocket#sendText(String)}.
   * </ul>
   *
   * <p>Method under test: {@link MarketDataGeneratorWebsocket#onConnected(WebSocket, Map)}
   */
  @Test
  @DisplayName(
      "Test onConnected(WebSocket, Map); given 'null'; when WebSocket sendText(String) return 'null'; then calls sendText(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void MarketDataGeneratorWebsocket.onConnected(WebSocket, Map)"})
  void testOnConnected_givenNull_whenWebSocketSendTextReturnNull_thenCallsSendText()
      throws Exception {
    // Arrange
    JSONObject authJson = mock(JSONObject.class);
    when(authJson.getString(Mockito.<String>any()))
        .thenReturn(
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());
    String position =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();

    MarketDataGeneratorWebsocket marketDataGeneratorWebsocket =
        new MarketDataGeneratorWebsocket(authJson, position, new ArrayList<>());

    WebSocket websocket = mock(WebSocket.class);
    when(websocket.sendText(Mockito.<String>any())).thenReturn(null);

    // Act
    marketDataGeneratorWebsocket.onConnected(websocket, new HashMap<>());

    // Assert
    verify(websocket)
        .sendText(
            "{\"ID\":1,\"Domain\":\"Login\",\"Key\":{\"Elements\":{\"ApplicationId\":\"256\",\"Position\":\"<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\" standalone=\\\"yes\\\"?><smartderivativecontract xmlns:xsi=\\\"http://www.w3.org/2001/XMLSchema-instance\\\" xmlns=\\\"uri:sdc\\\" xsi:schemaLocation=\\\"uri:sdc smartderivativecontract.xsd\\\"><dltTradeId>TEST-TRADE-001</dltTradeId><dltAddress>0xTEST</dltAddress><uniqueTradeIdentifier>UTI-TEST-001</uniqueTradeIdentifier><settlementCurrency>EUR</settlementCurrency><tradeType>SDCPledgedBalance</tradeType><valuation>  <artefact>    <groupId>net.finmath</groupId>    <artifactId>finmath-smart-derivative-contract</artifactId>    <version>1.0.0</version>  </artefact></valuation><parties>  <party>    <name>Party 1</name>    <id>party1</id>    <marginAccount><type>constant</type><value>10000.0</value></marginAccount>    <penaltyFee><type>constant</type><value>100.0</value></penaltyFee>    <address>0x1234</address>  </party>  <party>    <name>Party 2</name>    <id>party2</id>    <marginAccount><type>constant</type><value>10000.0</value></marginAccount>    <penaltyFee><type>constant</type><value>100.0</value></penaltyFee>    <address>0x5678</address>  </party></parties><settlement>  <settlementTime><type>daily</type><value>17:00</value></settlementTime>  <marketdata>    <provider>test</provider>    <marketdataitems>      <item>        <symbol>EUR-EONIA</symbol>        <curve>ESTR</curve>        <type>Fixing</type>        <tenor>1D</tenor>      </item>    </marketdataitems>  </marketdata></settlement><trade>  <product>InterestRateDerivative</product>  <productType>Swap</productType>  <startDate>2024-01-01</startDate>  <endDate>2029-01-01</endDate>  <receiverPartyReference><partyReference>party1</partyReference></receiverPartyReference>  <notional><currency>EUR</currency><amount>1000000.0</amount></notional>  <swap>    <swapStream id=\\\"floatLeg\\\">      <receiverPartyReference>party1</receiverPartyReference>      <calculationPeriodDates>        <effectiveDate>2024-01-01</effectiveDate>        <terminationDate>2029-01-01</terminationDate>        <calculationPeriodFrequency><periodMultiplier>6</periodMultiplier><period>M</period></calculationPeriodFrequency>      </calculationPeriodDates>      <paymentDates>        <paymentFrequency><periodMultiplier>6</periodMultiplier><period>M</period></paymentFrequency>      </paymentDates>      <calculationPeriodAmount>        <calculation><notional><currency>EUR</currency><amount>1000000.0</amount></notional>        <floatingRateCalculation><floatingRateIndex>EUR-EURIBOR-Reuters</floatingRateIndex><indexTenor><periodMultiplier>6</periodMultiplier><period>M</period></indexTenor></floatingRateCalculation>        </calculation>      </calculationPeriodAmount>    </swapStream>    <swapStream id=\\\"fixedLeg\\\">      <receiverPartyReference>party2</receiverPartyReference>      <calculationPeriodDates>        <effectiveDate>2024-01-01</effectiveDate>        <terminationDate>2029-01-01</terminationDate>        <calculationPeriodFrequency><periodMultiplier>1</periodMultiplier><period>Y</period></calculationPeriodFrequency>      </calculationPeriodDates>      <paymentDates>        <paymentFrequency><periodMultiplier>1</periodMultiplier><period>Y</period></paymentFrequency>      </paymentDates>      <calculationPeriodAmount>        <calculation><notional><currency>EUR</currency><amount>1000000.0</amount></notional>        <fixedRateSchedule><initialValue>0.02</initialValue></fixedRateSchedule>        </calculation>      </calculationPeriodAmount>    </swapStream>  </swap></trade></smartderivativecontract>\",\"AuthenticationToken\":\"<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\" standalone=\\\"yes\\\"?><smartderivativecontract xmlns:xsi=\\\"http://www.w3.org/2001/XMLSchema-instance\\\" xmlns=\\\"uri:sdc\\\" xsi:schemaLocation=\\\"uri:sdc smartderivativecontract.xsd\\\"><dltTradeId>TEST-TRADE-001</dltTradeId><dltAddress>0xTEST</dltAddress><uniqueTradeIdentifier>UTI-TEST-001</uniqueTradeIdentifier><settlementCurrency>EUR</settlementCurrency><tradeType>SDCPledgedBalance</tradeType><valuation>  <artefact>    <groupId>net.finmath</groupId>    <artifactId>finmath-smart-derivative-contract</artifactId>    <version>1.0.0</version>  </artefact></valuation><parties>  <party>    <name>Party 1</name>    <id>party1</id>    <marginAccount><type>constant</type><value>10000.0</value></marginAccount>    <penaltyFee><type>constant</type><value>100.0</value></penaltyFee>    <address>0x1234</address>  </party>  <party>    <name>Party 2</name>    <id>party2</id>    <marginAccount><type>constant</type><value>10000.0</value></marginAccount>    <penaltyFee><type>constant</type><value>100.0</value></penaltyFee>    <address>0x5678</address>  </party></parties><settlement>  <settlementTime><type>daily</type><value>17:00</value></settlementTime>  <marketdata>    <provider>test</provider>    <marketdataitems>      <item>        <symbol>EUR-EONIA</symbol>        <curve>ESTR</curve>        <type>Fixing</type>        <tenor>1D</tenor>      </item>    </marketdataitems>  </marketdata></settlement><trade>  <product>InterestRateDerivative</product>  <productType>Swap</productType>  <startDate>2024-01-01</startDate>  <endDate>2029-01-01</endDate>  <receiverPartyReference><partyReference>party1</partyReference></receiverPartyReference>  <notional><currency>EUR</currency><amount>1000000.0</amount></notional>  <swap>    <swapStream id=\\\"floatLeg\\\">      <receiverPartyReference>party1</receiverPartyReference>      <calculationPeriodDates>        <effectiveDate>2024-01-01</effectiveDate>        <terminationDate>2029-01-01</terminationDate>        <calculationPeriodFrequency><periodMultiplier>6</periodMultiplier><period>M</period></calculationPeriodFrequency>      </calculationPeriodDates>      <paymentDates>        <paymentFrequency><periodMultiplier>6</periodMultiplier><period>M</period></paymentFrequency>      </paymentDates>      <calculationPeriodAmount>        <calculation><notional><currency>EUR</currency><amount>1000000.0</amount></notional>        <floatingRateCalculation><floatingRateIndex>EUR-EURIBOR-Reuters</floatingRateIndex><indexTenor><periodMultiplier>6</periodMultiplier><period>M</period></indexTenor></floatingRateCalculation>        </calculation>      </calculationPeriodAmount>    </swapStream>    <swapStream id=\\\"fixedLeg\\\">      <receiverPartyReference>party2</receiverPartyReference>      <calculationPeriodDates>        <effectiveDate>2024-01-01</effectiveDate>        <terminationDate>2029-01-01</terminationDate>        <calculationPeriodFrequency><periodMultiplier>1</periodMultiplier><period>Y</period></calculationPeriodFrequency>      </calculationPeriodDates>      <paymentDates>        <paymentFrequency><periodMultiplier>1</periodMultiplier><period>Y</period></paymentFrequency>      </paymentDates>      <calculationPeriodAmount>        <calculation><notional><currency>EUR</currency><amount>1000000.0</amount></notional>        <fixedRateSchedule><initialValue>0.02</initialValue></fixedRateSchedule>        </calculation>      </calculationPeriodAmount>    </swapStream>  </swap></trade></smartderivativecontract>\"},\"NameType\":\"AuthnToken\"}}");
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
    String position =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();

    MarketDataGeneratorWebsocket marketDataGeneratorWebsocket =
        new MarketDataGeneratorWebsocket(authJson, position, new ArrayList<>());

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
   * <p>Method under test: {@link MarketDataGeneratorWebsocket#closeStreamsAndLogoff(WebSocket)}
   */
  @Test
  @DisplayName("Test closeStreamsAndLogoff(WebSocket)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void MarketDataGeneratorWebsocket.closeStreamsAndLogoff(WebSocket)"})
  void testCloseStreamsAndLogoff() {
    // Arrange
    String position = SettlementGeneratorDiffblueBase.createMinimalSmartDerivativeContractXml();
    MarketDataGeneratorWebsocket marketDataGeneratorWebsocket =
        new MarketDataGeneratorWebsocket(null, position, new ArrayList<>());

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
   *   <li>Then calls {@link WebSocket#sendText(String)}.
   * </ul>
   *
   * <p>Method under test: {@link MarketDataGeneratorWebsocket#closeStreamsAndLogoff(WebSocket)}
   */
  @Test
  @DisplayName("Test closeStreamsAndLogoff(WebSocket); then calls sendText(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void MarketDataGeneratorWebsocket.closeStreamsAndLogoff(WebSocket)"})
  void testCloseStreamsAndLogoff_thenCallsSendText() {
    // Arrange
    JSONObject authJson = new JSONObject();
    String position =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();

    MarketDataGeneratorWebsocket marketDataGeneratorWebsocket =
        new MarketDataGeneratorWebsocket(authJson, position, new ArrayList<>());

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
    String position =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();

    MarketDataGeneratorWebsocket marketDataGeneratorWebsocket =
        new MarketDataGeneratorWebsocket(authJson, position, new ArrayList<>());
    String importDir =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();

    // Act and Assert
    assertThrows(
        RuntimeException.class,
        () -> marketDataGeneratorWebsocket.writeDataset(importDir, new MarketDataList(), true));
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
    String position =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();

    MarketDataGeneratorWebsocket marketDataGeneratorWebsocket =
        new MarketDataGeneratorWebsocket(authJson, position, new ArrayList<>());

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
    itemList.add(CalibrationDataItemTestFactory.createSpecForSwapRate());
    JSONObject authJson = new JSONObject();

    MarketDataGeneratorWebsocket marketDataGeneratorWebsocket =
        new MarketDataGeneratorWebsocket(
            authJson,
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml(),
            itemList);

    WebSocket websocket = mock(WebSocket.class);
    when(websocket.sendText(Mockito.<String>any())).thenReturn(null);

    // Act
    marketDataGeneratorWebsocket.onTextMessage(websocket, "42");

    // Assert
    verify(websocket)
        .sendText(
            "{\"ID\":2,\"Key\":{\"Name\":[\"ESTR_Swap-Rate_5Y\"]},\"View\":[\"MID\",\"BID\",\"ASK\",\"VALUE_DT1\",\"VALUE_TS1\"]}");
    assertTrue(marketDataGeneratorWebsocket.requestSent);
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
    String position =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();

    MarketDataGeneratorWebsocket marketDataGeneratorWebsocket =
        new MarketDataGeneratorWebsocket(authJson, position, new ArrayList<>());

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
    String position =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();

    MarketDataGeneratorWebsocket marketDataGeneratorWebsocket =
        new MarketDataGeneratorWebsocket(authJson, position, new ArrayList<>());

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
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add createSpecForSwapRate.
   *   <li>Then calls {@link WebSocket#sendText(String)}.
   * </ul>
   *
   * <p>Method under test: {@link MarketDataGeneratorWebsocket#sendRICRequest(WebSocket)}
   */
  @Test
  @DisplayName(
      "Test sendRICRequest(WebSocket); given ArrayList() add createSpecForSwapRate; then calls sendText(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void MarketDataGeneratorWebsocket.sendRICRequest(WebSocket)"})
  void testSendRICRequest_givenArrayListAddCreateSpecForSwapRate_thenCallsSendText() {
    // Arrange
    ArrayList<Spec> itemList = new ArrayList<>();
    itemList.add(CalibrationDataItemTestFactory.createSpecForSwapRate());
    JSONObject authJson = new JSONObject();

    MarketDataGeneratorWebsocket marketDataGeneratorWebsocket =
        new MarketDataGeneratorWebsocket(
            authJson,
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml(),
            itemList);

    WebSocket websocket = mock(WebSocket.class);
    when(websocket.sendText(Mockito.<String>any())).thenReturn(null);

    // Act
    marketDataGeneratorWebsocket.sendRICRequest(websocket);

    // Assert
    verify(websocket)
        .sendText(
            "{\"ID\":2,\"Key\":{\"Name\":[\"ESTR_Swap-Rate_5Y\"]},\"View\":[\"MID\",\"BID\",\"ASK\",\"VALUE_DT1\",\"VALUE_TS1\"]}");
  }

  /**
   * Test {@link MarketDataGeneratorWebsocket#sendRICRequest(WebSocket)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link WebSocket} {@link WebSocket#sendText(String)} return {@code null}.
   *   <li>Then calls {@link WebSocket#sendText(String)}.
   * </ul>
   *
   * <p>Method under test: {@link MarketDataGeneratorWebsocket#sendRICRequest(WebSocket)}
   */
  @Test
  @DisplayName(
      "Test sendRICRequest(WebSocket); given 'null'; when WebSocket sendText(String) return 'null'; then calls sendText(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void MarketDataGeneratorWebsocket.sendRICRequest(WebSocket)"})
  void testSendRICRequest_givenNull_whenWebSocketSendTextReturnNull_thenCallsSendText() {
    // Arrange
    JSONObject authJson = new JSONObject();
    String position =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();

    MarketDataGeneratorWebsocket marketDataGeneratorWebsocket =
        new MarketDataGeneratorWebsocket(authJson, position, new ArrayList<>());

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
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link WebSocket} {@link WebSocket#sendText(String)} return {@code null}.
   *   <li>Then calls {@link WebSocket#sendText(String)}.
   * </ul>
   *
   * <p>Method under test: {@link MarketDataGeneratorWebsocket#sendLoginRequest(WebSocket, String,
   * boolean)}
   */
  @Test
  @DisplayName(
      "Test sendLoginRequest(WebSocket, String, boolean); given 'null'; when WebSocket sendText(String) return 'null'; then calls sendText(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MarketDataGeneratorWebsocket.sendLoginRequest(WebSocket, String, boolean)"
  })
  void testSendLoginRequest_givenNull_whenWebSocketSendTextReturnNull_thenCallsSendText()
      throws Exception {
    // Arrange
    JSONObject authJson = new JSONObject();
    String position =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();

    MarketDataGeneratorWebsocket marketDataGeneratorWebsocket =
        new MarketDataGeneratorWebsocket(authJson, position, new ArrayList<>());

    WebSocket websocket = mock(WebSocket.class);
    when(websocket.sendText(Mockito.<String>any())).thenReturn(null);

    // Act
    marketDataGeneratorWebsocket.sendLoginRequest(
        websocket,
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml(),
        true);

    // Assert
    verify(websocket)
        .sendText(
            "{\"ID\":1,\"Domain\":\"Login\",\"Key\":{\"Elements\":{\"ApplicationId\":\"256\",\"Position\":\"<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\" standalone=\\\"yes\\\"?><smartderivativecontract xmlns:xsi=\\\"http://www.w3.org/2001/XMLSchema-instance\\\" xmlns=\\\"uri:sdc\\\" xsi:schemaLocation=\\\"uri:sdc smartderivativecontract.xsd\\\"><dltTradeId>TEST-TRADE-001</dltTradeId><dltAddress>0xTEST</dltAddress><uniqueTradeIdentifier>UTI-TEST-001</uniqueTradeIdentifier><settlementCurrency>EUR</settlementCurrency><tradeType>SDCPledgedBalance</tradeType><valuation>  <artefact>    <groupId>net.finmath</groupId>    <artifactId>finmath-smart-derivative-contract</artifactId>    <version>1.0.0</version>  </artefact></valuation><parties>  <party>    <name>Party 1</name>    <id>party1</id>    <marginAccount><type>constant</type><value>10000.0</value></marginAccount>    <penaltyFee><type>constant</type><value>100.0</value></penaltyFee>    <address>0x1234</address>  </party>  <party>    <name>Party 2</name>    <id>party2</id>    <marginAccount><type>constant</type><value>10000.0</value></marginAccount>    <penaltyFee><type>constant</type><value>100.0</value></penaltyFee>    <address>0x5678</address>  </party></parties><settlement>  <settlementTime><type>daily</type><value>17:00</value></settlementTime>  <marketdata>    <provider>test</provider>    <marketdataitems>      <item>        <symbol>EUR-EONIA</symbol>        <curve>ESTR</curve>        <type>Fixing</type>        <tenor>1D</tenor>      </item>    </marketdataitems>  </marketdata></settlement><trade>  <product>InterestRateDerivative</product>  <productType>Swap</productType>  <startDate>2024-01-01</startDate>  <endDate>2029-01-01</endDate>  <receiverPartyReference><partyReference>party1</partyReference></receiverPartyReference>  <notional><currency>EUR</currency><amount>1000000.0</amount></notional>  <swap>    <swapStream id=\\\"floatLeg\\\">      <receiverPartyReference>party1</receiverPartyReference>      <calculationPeriodDates>        <effectiveDate>2024-01-01</effectiveDate>        <terminationDate>2029-01-01</terminationDate>        <calculationPeriodFrequency><periodMultiplier>6</periodMultiplier><period>M</period></calculationPeriodFrequency>      </calculationPeriodDates>      <paymentDates>        <paymentFrequency><periodMultiplier>6</periodMultiplier><period>M</period></paymentFrequency>      </paymentDates>      <calculationPeriodAmount>        <calculation><notional><currency>EUR</currency><amount>1000000.0</amount></notional>        <floatingRateCalculation><floatingRateIndex>EUR-EURIBOR-Reuters</floatingRateIndex><indexTenor><periodMultiplier>6</periodMultiplier><period>M</period></indexTenor></floatingRateCalculation>        </calculation>      </calculationPeriodAmount>    </swapStream>    <swapStream id=\\\"fixedLeg\\\">      <receiverPartyReference>party2</receiverPartyReference>      <calculationPeriodDates>        <effectiveDate>2024-01-01</effectiveDate>        <terminationDate>2029-01-01</terminationDate>        <calculationPeriodFrequency><periodMultiplier>1</periodMultiplier><period>Y</period></calculationPeriodFrequency>      </calculationPeriodDates>      <paymentDates>        <paymentFrequency><periodMultiplier>1</periodMultiplier><period>Y</period></paymentFrequency>      </paymentDates>      <calculationPeriodAmount>        <calculation><notional><currency>EUR</currency><amount>1000000.0</amount></notional>        <fixedRateSchedule><initialValue>0.02</initialValue></fixedRateSchedule>        </calculation>      </calculationPeriodAmount>    </swapStream>  </swap></trade></smartderivativecontract>\",\"AuthenticationToken\":\"<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\" standalone=\\\"yes\\\"?><smartderivativecontract xmlns:xsi=\\\"http://www.w3.org/2001/XMLSchema-instance\\\" xmlns=\\\"uri:sdc\\\" xsi:schemaLocation=\\\"uri:sdc smartderivativecontract.xsd\\\"><dltTradeId>TEST-TRADE-001</dltTradeId><dltAddress>0xTEST</dltAddress><uniqueTradeIdentifier>UTI-TEST-001</uniqueTradeIdentifier><settlementCurrency>EUR</settlementCurrency><tradeType>SDCPledgedBalance</tradeType><valuation>  <artefact>    <groupId>net.finmath</groupId>    <artifactId>finmath-smart-derivative-contract</artifactId>    <version>1.0.0</version>  </artefact></valuation><parties>  <party>    <name>Party 1</name>    <id>party1</id>    <marginAccount><type>constant</type><value>10000.0</value></marginAccount>    <penaltyFee><type>constant</type><value>100.0</value></penaltyFee>    <address>0x1234</address>  </party>  <party>    <name>Party 2</name>    <id>party2</id>    <marginAccount><type>constant</type><value>10000.0</value></marginAccount>    <penaltyFee><type>constant</type><value>100.0</value></penaltyFee>    <address>0x5678</address>  </party></parties><settlement>  <settlementTime><type>daily</type><value>17:00</value></settlementTime>  <marketdata>    <provider>test</provider>    <marketdataitems>      <item>        <symbol>EUR-EONIA</symbol>        <curve>ESTR</curve>        <type>Fixing</type>        <tenor>1D</tenor>      </item>    </marketdataitems>  </marketdata></settlement><trade>  <product>InterestRateDerivative</product>  <productType>Swap</productType>  <startDate>2024-01-01</startDate>  <endDate>2029-01-01</endDate>  <receiverPartyReference><partyReference>party1</partyReference></receiverPartyReference>  <notional><currency>EUR</currency><amount>1000000.0</amount></notional>  <swap>    <swapStream id=\\\"floatLeg\\\">      <receiverPartyReference>party1</receiverPartyReference>      <calculationPeriodDates>        <effectiveDate>2024-01-01</effectiveDate>        <terminationDate>2029-01-01</terminationDate>        <calculationPeriodFrequency><periodMultiplier>6</periodMultiplier><period>M</period></calculationPeriodFrequency>      </calculationPeriodDates>      <paymentDates>        <paymentFrequency><periodMultiplier>6</periodMultiplier><period>M</period></paymentFrequency>      </paymentDates>      <calculationPeriodAmount>        <calculation><notional><currency>EUR</currency><amount>1000000.0</amount></notional>        <floatingRateCalculation><floatingRateIndex>EUR-EURIBOR-Reuters</floatingRateIndex><indexTenor><periodMultiplier>6</periodMultiplier><period>M</period></indexTenor></floatingRateCalculation>        </calculation>      </calculationPeriodAmount>    </swapStream>    <swapStream id=\\\"fixedLeg\\\">      <receiverPartyReference>party2</receiverPartyReference>      <calculationPeriodDates>        <effectiveDate>2024-01-01</effectiveDate>        <terminationDate>2029-01-01</terminationDate>        <calculationPeriodFrequency><periodMultiplier>1</periodMultiplier><period>Y</period></calculationPeriodFrequency>      </calculationPeriodDates>      <paymentDates>        <paymentFrequency><periodMultiplier>1</periodMultiplier><period>Y</period></paymentFrequency>      </paymentDates>      <calculationPeriodAmount>        <calculation><notional><currency>EUR</currency><amount>1000000.0</amount></notional>        <fixedRateSchedule><initialValue>0.02</initialValue></fixedRateSchedule>        </calculation>      </calculationPeriodAmount>    </swapStream>  </swap></trade></smartderivativecontract>\"},\"NameType\":\"AuthnToken\"}}");
  }

  /**
   * Test {@link MarketDataGeneratorWebsocket#sendLoginRequest(WebSocket, String, boolean)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link WebSocket} {@link WebSocket#sendText(String)} return {@code null}.
   *   <li>Then calls {@link WebSocket#sendText(String)}.
   * </ul>
   *
   * <p>Method under test: {@link MarketDataGeneratorWebsocket#sendLoginRequest(WebSocket, String,
   * boolean)}
   */
  @Test
  @DisplayName(
      "Test sendLoginRequest(WebSocket, String, boolean); given 'null'; when WebSocket sendText(String) return 'null'; then calls sendText(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MarketDataGeneratorWebsocket.sendLoginRequest(WebSocket, String, boolean)"
  })
  void testSendLoginRequest_givenNull_whenWebSocketSendTextReturnNull_thenCallsSendText2()
      throws Exception {
    // Arrange
    JSONObject authJson = new JSONObject();
    String position =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();

    MarketDataGeneratorWebsocket marketDataGeneratorWebsocket =
        new MarketDataGeneratorWebsocket(authJson, position, new ArrayList<>());

    WebSocket websocket = mock(WebSocket.class);
    when(websocket.sendText(Mockito.<String>any())).thenReturn(null);

    // Act
    marketDataGeneratorWebsocket.sendLoginRequest(
        websocket,
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml(),
        false);

    // Assert
    verify(websocket)
        .sendText(
            "{\"ID\":1,\"Domain\":\"Login\",\"Key\":{\"Elements\":{\"ApplicationId\":\"256\",\"Position\":\"<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\" standalone=\\\"yes\\\"?><smartderivativecontract xmlns:xsi=\\\"http://www.w3.org/2001/XMLSchema-instance\\\" xmlns=\\\"uri:sdc\\\" xsi:schemaLocation=\\\"uri:sdc smartderivativecontract.xsd\\\"><dltTradeId>TEST-TRADE-001</dltTradeId><dltAddress>0xTEST</dltAddress><uniqueTradeIdentifier>UTI-TEST-001</uniqueTradeIdentifier><settlementCurrency>EUR</settlementCurrency><tradeType>SDCPledgedBalance</tradeType><valuation>  <artefact>    <groupId>net.finmath</groupId>    <artifactId>finmath-smart-derivative-contract</artifactId>    <version>1.0.0</version>  </artefact></valuation><parties>  <party>    <name>Party 1</name>    <id>party1</id>    <marginAccount><type>constant</type><value>10000.0</value></marginAccount>    <penaltyFee><type>constant</type><value>100.0</value></penaltyFee>    <address>0x1234</address>  </party>  <party>    <name>Party 2</name>    <id>party2</id>    <marginAccount><type>constant</type><value>10000.0</value></marginAccount>    <penaltyFee><type>constant</type><value>100.0</value></penaltyFee>    <address>0x5678</address>  </party></parties><settlement>  <settlementTime><type>daily</type><value>17:00</value></settlementTime>  <marketdata>    <provider>test</provider>    <marketdataitems>      <item>        <symbol>EUR-EONIA</symbol>        <curve>ESTR</curve>        <type>Fixing</type>        <tenor>1D</tenor>      </item>    </marketdataitems>  </marketdata></settlement><trade>  <product>InterestRateDerivative</product>  <productType>Swap</productType>  <startDate>2024-01-01</startDate>  <endDate>2029-01-01</endDate>  <receiverPartyReference><partyReference>party1</partyReference></receiverPartyReference>  <notional><currency>EUR</currency><amount>1000000.0</amount></notional>  <swap>    <swapStream id=\\\"floatLeg\\\">      <receiverPartyReference>party1</receiverPartyReference>      <calculationPeriodDates>        <effectiveDate>2024-01-01</effectiveDate>        <terminationDate>2029-01-01</terminationDate>        <calculationPeriodFrequency><periodMultiplier>6</periodMultiplier><period>M</period></calculationPeriodFrequency>      </calculationPeriodDates>      <paymentDates>        <paymentFrequency><periodMultiplier>6</periodMultiplier><period>M</period></paymentFrequency>      </paymentDates>      <calculationPeriodAmount>        <calculation><notional><currency>EUR</currency><amount>1000000.0</amount></notional>        <floatingRateCalculation><floatingRateIndex>EUR-EURIBOR-Reuters</floatingRateIndex><indexTenor><periodMultiplier>6</periodMultiplier><period>M</period></indexTenor></floatingRateCalculation>        </calculation>      </calculationPeriodAmount>    </swapStream>    <swapStream id=\\\"fixedLeg\\\">      <receiverPartyReference>party2</receiverPartyReference>      <calculationPeriodDates>        <effectiveDate>2024-01-01</effectiveDate>        <terminationDate>2029-01-01</terminationDate>        <calculationPeriodFrequency><periodMultiplier>1</periodMultiplier><period>Y</period></calculationPeriodFrequency>      </calculationPeriodDates>      <paymentDates>        <paymentFrequency><periodMultiplier>1</periodMultiplier><period>Y</period></paymentFrequency>      </paymentDates>      <calculationPeriodAmount>        <calculation><notional><currency>EUR</currency><amount>1000000.0</amount></notional>        <fixedRateSchedule><initialValue>0.02</initialValue></fixedRateSchedule>        </calculation>      </calculationPeriodAmount>    </swapStream>  </swap></trade></smartderivativecontract>\",\"AuthenticationToken\":\"<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\" standalone=\\\"yes\\\"?><smartderivativecontract xmlns:xsi=\\\"http://www.w3.org/2001/XMLSchema-instance\\\" xmlns=\\\"uri:sdc\\\" xsi:schemaLocation=\\\"uri:sdc smartderivativecontract.xsd\\\"><dltTradeId>TEST-TRADE-001</dltTradeId><dltAddress>0xTEST</dltAddress><uniqueTradeIdentifier>UTI-TEST-001</uniqueTradeIdentifier><settlementCurrency>EUR</settlementCurrency><tradeType>SDCPledgedBalance</tradeType><valuation>  <artefact>    <groupId>net.finmath</groupId>    <artifactId>finmath-smart-derivative-contract</artifactId>    <version>1.0.0</version>  </artefact></valuation><parties>  <party>    <name>Party 1</name>    <id>party1</id>    <marginAccount><type>constant</type><value>10000.0</value></marginAccount>    <penaltyFee><type>constant</type><value>100.0</value></penaltyFee>    <address>0x1234</address>  </party>  <party>    <name>Party 2</name>    <id>party2</id>    <marginAccount><type>constant</type><value>10000.0</value></marginAccount>    <penaltyFee><type>constant</type><value>100.0</value></penaltyFee>    <address>0x5678</address>  </party></parties><settlement>  <settlementTime><type>daily</type><value>17:00</value></settlementTime>  <marketdata>    <provider>test</provider>    <marketdataitems>      <item>        <symbol>EUR-EONIA</symbol>        <curve>ESTR</curve>        <type>Fixing</type>        <tenor>1D</tenor>      </item>    </marketdataitems>  </marketdata></settlement><trade>  <product>InterestRateDerivative</product>  <productType>Swap</productType>  <startDate>2024-01-01</startDate>  <endDate>2029-01-01</endDate>  <receiverPartyReference><partyReference>party1</partyReference></receiverPartyReference>  <notional><currency>EUR</currency><amount>1000000.0</amount></notional>  <swap>    <swapStream id=\\\"floatLeg\\\">      <receiverPartyReference>party1</receiverPartyReference>      <calculationPeriodDates>        <effectiveDate>2024-01-01</effectiveDate>        <terminationDate>2029-01-01</terminationDate>        <calculationPeriodFrequency><periodMultiplier>6</periodMultiplier><period>M</period></calculationPeriodFrequency>      </calculationPeriodDates>      <paymentDates>        <paymentFrequency><periodMultiplier>6</periodMultiplier><period>M</period></paymentFrequency>      </paymentDates>      <calculationPeriodAmount>        <calculation><notional><currency>EUR</currency><amount>1000000.0</amount></notional>        <floatingRateCalculation><floatingRateIndex>EUR-EURIBOR-Reuters</floatingRateIndex><indexTenor><periodMultiplier>6</periodMultiplier><period>M</period></indexTenor></floatingRateCalculation>        </calculation>      </calculationPeriodAmount>    </swapStream>    <swapStream id=\\\"fixedLeg\\\">      <receiverPartyReference>party2</receiverPartyReference>      <calculationPeriodDates>        <effectiveDate>2024-01-01</effectiveDate>        <terminationDate>2029-01-01</terminationDate>        <calculationPeriodFrequency><periodMultiplier>1</periodMultiplier><period>Y</period></calculationPeriodFrequency>      </calculationPeriodDates>      <paymentDates>        <paymentFrequency><periodMultiplier>1</periodMultiplier><period>Y</period></paymentFrequency>      </paymentDates>      <calculationPeriodAmount>        <calculation><notional><currency>EUR</currency><amount>1000000.0</amount></notional>        <fixedRateSchedule><initialValue>0.02</initialValue></fixedRateSchedule>        </calculation>      </calculationPeriodAmount>    </swapStream>  </swap></trade></smartderivativecontract>\"},\"NameType\":\"AuthnToken\"},\"Refresh\":false}");
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
    String position =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();

    MarketDataGeneratorWebsocket marketDataGeneratorWebsocket =
        new MarketDataGeneratorWebsocket(authJson, position, new ArrayList<>());

    WebSocket websocket = mock(WebSocket.class);
    when(websocket.sendText(Mockito.<String>any())).thenThrow(new RuntimeException());

    // Act and Assert
    assertThrows(
        RuntimeException.class,
        () ->
            marketDataGeneratorWebsocket.sendLoginRequest(
                websocket,
                MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml(),
                false));
    verify(websocket)
        .sendText(
            "{\"ID\":1,\"Domain\":\"Login\",\"Key\":{\"Elements\":{\"ApplicationId\":\"256\",\"Position\":\"<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\" standalone=\\\"yes\\\"?><smartderivativecontract xmlns:xsi=\\\"http://www.w3.org/2001/XMLSchema-instance\\\" xmlns=\\\"uri:sdc\\\" xsi:schemaLocation=\\\"uri:sdc smartderivativecontract.xsd\\\"><dltTradeId>TEST-TRADE-001</dltTradeId><dltAddress>0xTEST</dltAddress><uniqueTradeIdentifier>UTI-TEST-001</uniqueTradeIdentifier><settlementCurrency>EUR</settlementCurrency><tradeType>SDCPledgedBalance</tradeType><valuation>  <artefact>    <groupId>net.finmath</groupId>    <artifactId>finmath-smart-derivative-contract</artifactId>    <version>1.0.0</version>  </artefact></valuation><parties>  <party>    <name>Party 1</name>    <id>party1</id>    <marginAccount><type>constant</type><value>10000.0</value></marginAccount>    <penaltyFee><type>constant</type><value>100.0</value></penaltyFee>    <address>0x1234</address>  </party>  <party>    <name>Party 2</name>    <id>party2</id>    <marginAccount><type>constant</type><value>10000.0</value></marginAccount>    <penaltyFee><type>constant</type><value>100.0</value></penaltyFee>    <address>0x5678</address>  </party></parties><settlement>  <settlementTime><type>daily</type><value>17:00</value></settlementTime>  <marketdata>    <provider>test</provider>    <marketdataitems>      <item>        <symbol>EUR-EONIA</symbol>        <curve>ESTR</curve>        <type>Fixing</type>        <tenor>1D</tenor>      </item>    </marketdataitems>  </marketdata></settlement><trade>  <product>InterestRateDerivative</product>  <productType>Swap</productType>  <startDate>2024-01-01</startDate>  <endDate>2029-01-01</endDate>  <receiverPartyReference><partyReference>party1</partyReference></receiverPartyReference>  <notional><currency>EUR</currency><amount>1000000.0</amount></notional>  <swap>    <swapStream id=\\\"floatLeg\\\">      <receiverPartyReference>party1</receiverPartyReference>      <calculationPeriodDates>        <effectiveDate>2024-01-01</effectiveDate>        <terminationDate>2029-01-01</terminationDate>        <calculationPeriodFrequency><periodMultiplier>6</periodMultiplier><period>M</period></calculationPeriodFrequency>      </calculationPeriodDates>      <paymentDates>        <paymentFrequency><periodMultiplier>6</periodMultiplier><period>M</period></paymentFrequency>      </paymentDates>      <calculationPeriodAmount>        <calculation><notional><currency>EUR</currency><amount>1000000.0</amount></notional>        <floatingRateCalculation><floatingRateIndex>EUR-EURIBOR-Reuters</floatingRateIndex><indexTenor><periodMultiplier>6</periodMultiplier><period>M</period></indexTenor></floatingRateCalculation>        </calculation>      </calculationPeriodAmount>    </swapStream>    <swapStream id=\\\"fixedLeg\\\">      <receiverPartyReference>party2</receiverPartyReference>      <calculationPeriodDates>        <effectiveDate>2024-01-01</effectiveDate>        <terminationDate>2029-01-01</terminationDate>        <calculationPeriodFrequency><periodMultiplier>1</periodMultiplier><period>Y</period></calculationPeriodFrequency>      </calculationPeriodDates>      <paymentDates>        <paymentFrequency><periodMultiplier>1</periodMultiplier><period>Y</period></paymentFrequency>      </paymentDates>      <calculationPeriodAmount>        <calculation><notional><currency>EUR</currency><amount>1000000.0</amount></notional>        <fixedRateSchedule><initialValue>0.02</initialValue></fixedRateSchedule>        </calculation>      </calculationPeriodAmount>    </swapStream>  </swap></trade></smartderivativecontract>\",\"AuthenticationToken\":\"<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\" standalone=\\\"yes\\\"?><smartderivativecontract xmlns:xsi=\\\"http://www.w3.org/2001/XMLSchema-instance\\\" xmlns=\\\"uri:sdc\\\" xsi:schemaLocation=\\\"uri:sdc smartderivativecontract.xsd\\\"><dltTradeId>TEST-TRADE-001</dltTradeId><dltAddress>0xTEST</dltAddress><uniqueTradeIdentifier>UTI-TEST-001</uniqueTradeIdentifier><settlementCurrency>EUR</settlementCurrency><tradeType>SDCPledgedBalance</tradeType><valuation>  <artefact>    <groupId>net.finmath</groupId>    <artifactId>finmath-smart-derivative-contract</artifactId>    <version>1.0.0</version>  </artefact></valuation><parties>  <party>    <name>Party 1</name>    <id>party1</id>    <marginAccount><type>constant</type><value>10000.0</value></marginAccount>    <penaltyFee><type>constant</type><value>100.0</value></penaltyFee>    <address>0x1234</address>  </party>  <party>    <name>Party 2</name>    <id>party2</id>    <marginAccount><type>constant</type><value>10000.0</value></marginAccount>    <penaltyFee><type>constant</type><value>100.0</value></penaltyFee>    <address>0x5678</address>  </party></parties><settlement>  <settlementTime><type>daily</type><value>17:00</value></settlementTime>  <marketdata>    <provider>test</provider>    <marketdataitems>      <item>        <symbol>EUR-EONIA</symbol>        <curve>ESTR</curve>        <type>Fixing</type>        <tenor>1D</tenor>      </item>    </marketdataitems>  </marketdata></settlement><trade>  <product>InterestRateDerivative</product>  <productType>Swap</productType>  <startDate>2024-01-01</startDate>  <endDate>2029-01-01</endDate>  <receiverPartyReference><partyReference>party1</partyReference></receiverPartyReference>  <notional><currency>EUR</currency><amount>1000000.0</amount></notional>  <swap>    <swapStream id=\\\"floatLeg\\\">      <receiverPartyReference>party1</receiverPartyReference>      <calculationPeriodDates>        <effectiveDate>2024-01-01</effectiveDate>        <terminationDate>2029-01-01</terminationDate>        <calculationPeriodFrequency><periodMultiplier>6</periodMultiplier><period>M</period></calculationPeriodFrequency>      </calculationPeriodDates>      <paymentDates>        <paymentFrequency><periodMultiplier>6</periodMultiplier><period>M</period></paymentFrequency>      </paymentDates>      <calculationPeriodAmount>        <calculation><notional><currency>EUR</currency><amount>1000000.0</amount></notional>        <floatingRateCalculation><floatingRateIndex>EUR-EURIBOR-Reuters</floatingRateIndex><indexTenor><periodMultiplier>6</periodMultiplier><period>M</period></indexTenor></floatingRateCalculation>        </calculation>      </calculationPeriodAmount>    </swapStream>    <swapStream id=\\\"fixedLeg\\\">      <receiverPartyReference>party2</receiverPartyReference>      <calculationPeriodDates>        <effectiveDate>2024-01-01</effectiveDate>        <terminationDate>2029-01-01</terminationDate>        <calculationPeriodFrequency><periodMultiplier>1</periodMultiplier><period>Y</period></calculationPeriodFrequency>      </calculationPeriodDates>      <paymentDates>        <paymentFrequency><periodMultiplier>1</periodMultiplier><period>Y</period></paymentFrequency>      </paymentDates>      <calculationPeriodAmount>        <calculation><notional><currency>EUR</currency><amount>1000000.0</amount></notional>        <fixedRateSchedule><initialValue>0.02</initialValue></fixedRateSchedule>        </calculation>      </calculationPeriodAmount>    </swapStream>  </swap></trade></smartderivativecontract>\"},\"NameType\":\"AuthnToken\"},\"Refresh\":false}");
  }
}
