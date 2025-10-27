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
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class MarketDataGeneratorWebsocketDiffblueTest {
  /**
   * Method under test: {@link MarketDataGeneratorWebsocket#allQuotesRetrieved()}
   */
  @Test
  void testAllQuotesRetrieved() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    JSONObject authJson = new JSONObject();

    // Act and Assert
    assertTrue((new MarketDataGeneratorWebsocket(authJson, "Position", new ArrayList<>())).allQuotesRetrieved());
  }

  /**
   * Method under test: {@link MarketDataGeneratorWebsocket#allQuotesRetrieved()}
   */
  @Test
  void testAllQuotesRetrieved2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    JSONObject authJson = mock(JSONObject.class);

    // Act and Assert
    assertTrue((new MarketDataGeneratorWebsocket(authJson, "Position", new ArrayList<>())).allQuotesRetrieved());
  }

  /**
   * Method under test: {@link MarketDataGeneratorWebsocket#allQuotesRetrieved()}
   */
  @Test
  void testAllQuotesRetrieved3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ArrayList<CalibrationDataItem.Spec> itemList = new ArrayList<>();
    itemList.add(new CalibrationDataItem.Spec("Key", "Curve Name", "Product Name", "Maturity"));

    // Act and Assert
    assertFalse((new MarketDataGeneratorWebsocket(new JSONObject(), "Position", itemList)).allQuotesRetrieved());
  }

  /**
   * Method under test:
   * {@link MarketDataGeneratorWebsocket#onConnected(WebSocket, Map)}
   */
  @Test
  void testOnConnected() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
   * Method under test: {@link MarketDataGeneratorWebsocket#asObservable()}
   */
  @Test
  void testAsObservable() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    JSONObject authJson = new JSONObject();

    // Act
    Observable<MarketDataList> actualAsObservableResult = (new MarketDataGeneratorWebsocket(authJson, "Position",
        new ArrayList<>())).asObservable();

    // Assert
    Observable<Timed<MarketDataList>> timestampResult = actualAsObservableResult.timestamp();
    Observable<Timed<Timed<MarketDataList>>> timestampResult2 = timestampResult.timestamp();
    Observable<Timed<Timed<Timed<MarketDataList>>>> timestampResult3 = timestampResult2.timestamp();
    Observable<Timed<Timed<Timed<Timed<MarketDataList>>>>> timestampResult4 = timestampResult3.timestamp();
    Observable<Timed<Timed<Timed<Timed<Timed<MarketDataList>>>>>> timestampResult5 = timestampResult4.timestamp();
    Observable<Timed<Timed<Timed<Timed<Timed<Timed<MarketDataList>>>>>>> timestampResult6 = timestampResult5
        .timestamp();
    Observable<Timed<Timed<Timed<Timed<Timed<Timed<Timed<MarketDataList>>>>>>>> timestampResult7 = timestampResult6
        .timestamp();
    Observable<Timed<Timed<Timed<Timed<Timed<Timed<Timed<Timed<MarketDataList>>>>>>>>> timestampResult8 = timestampResult7
        .timestamp();
    assertTrue(timestampResult8.timestamp() instanceof ObservableMap);
    Subject<MarketDataList> toSerializedResult = ((PublishSubject<MarketDataList>) actualAsObservableResult)
        .toSerialized();
    Observable<Timed<MarketDataList>> timestampResult9 = toSerializedResult.timestamp();
    Observable<Timed<Timed<MarketDataList>>> timestampResult10 = timestampResult9.timestamp();
    Observable<Timed<Timed<Timed<MarketDataList>>>> timestampResult11 = timestampResult10.timestamp();
    Observable<Timed<Timed<Timed<Timed<MarketDataList>>>>> timestampResult12 = timestampResult11.timestamp();
    Observable<Timed<Timed<Timed<Timed<Timed<MarketDataList>>>>>> timestampResult13 = timestampResult12.timestamp();
    Observable<Timed<Timed<Timed<Timed<Timed<Timed<MarketDataList>>>>>>> timestampResult14 = timestampResult13
        .timestamp();
    Observable<Timed<Timed<Timed<Timed<Timed<Timed<Timed<MarketDataList>>>>>>>> timestampResult15 = timestampResult14
        .timestamp();
    assertTrue(timestampResult15.timestamp() instanceof ObservableMap);
    assertTrue(timestampResult8 instanceof ObservableMap);
    assertTrue(timestampResult15 instanceof ObservableMap);
    assertTrue(timestampResult7 instanceof ObservableMap);
    assertTrue(timestampResult14 instanceof ObservableMap);
    assertTrue(timestampResult6 instanceof ObservableMap);
    assertTrue(timestampResult13 instanceof ObservableMap);
    assertTrue(timestampResult5 instanceof ObservableMap);
    assertTrue(timestampResult12 instanceof ObservableMap);
    assertTrue(timestampResult4 instanceof ObservableMap);
    assertTrue(timestampResult11 instanceof ObservableMap);
    assertTrue(timestampResult3 instanceof ObservableMap);
    assertTrue(timestampResult10 instanceof ObservableMap);
    assertTrue(timestampResult2 instanceof ObservableMap);
    assertTrue(timestampResult9 instanceof ObservableMap);
    assertTrue(timestampResult instanceof ObservableMap);
    assertTrue(timestampResult8.toList() instanceof ObservableToListSingle);
    assertTrue(timestampResult15.toList() instanceof ObservableToListSingle);
    Single<List<Timed<Timed<Timed<Timed<Timed<Timed<Timed<MarketDataList>>>>>>>>> toListResult = timestampResult7
        .toList();
    assertTrue(toListResult instanceof ObservableToListSingle);
    Single<List<Timed<Timed<Timed<Timed<Timed<Timed<MarketDataList>>>>>>>> toListResult2 = timestampResult14.toList();
    assertTrue(toListResult2 instanceof ObservableToListSingle);
    Single<List<Timed<Timed<Timed<Timed<Timed<Timed<MarketDataList>>>>>>>> toListResult3 = timestampResult6.toList();
    assertTrue(toListResult3 instanceof ObservableToListSingle);
    Single<List<Timed<Timed<Timed<Timed<Timed<MarketDataList>>>>>>> toListResult4 = timestampResult13.toList();
    assertTrue(toListResult4 instanceof ObservableToListSingle);
    Single<List<Timed<Timed<Timed<Timed<Timed<MarketDataList>>>>>>> toListResult5 = timestampResult5.toList();
    assertTrue(toListResult5 instanceof ObservableToListSingle);
    Single<List<Timed<Timed<Timed<Timed<MarketDataList>>>>>> toListResult6 = timestampResult12.toList();
    assertTrue(toListResult6 instanceof ObservableToListSingle);
    Single<List<Timed<Timed<Timed<Timed<MarketDataList>>>>>> toListResult7 = timestampResult4.toList();
    assertTrue(toListResult7 instanceof ObservableToListSingle);
    Single<List<Timed<Timed<Timed<MarketDataList>>>>> toListResult8 = timestampResult11.toList();
    assertTrue(toListResult8 instanceof ObservableToListSingle);
    Single<List<Timed<Timed<Timed<MarketDataList>>>>> toListResult9 = timestampResult3.toList();
    assertTrue(toListResult9 instanceof ObservableToListSingle);
    Single<List<Timed<Timed<MarketDataList>>>> toListResult10 = timestampResult10.toList();
    assertTrue(toListResult10 instanceof ObservableToListSingle);
    Single<List<Timed<Timed<MarketDataList>>>> toListResult11 = timestampResult2.toList();
    assertTrue(toListResult11 instanceof ObservableToListSingle);
    Single<List<Timed<MarketDataList>>> toListResult12 = timestampResult9.toList();
    assertTrue(toListResult12 instanceof ObservableToListSingle);
    Single<List<Timed<MarketDataList>>> toListResult13 = timestampResult.toList();
    assertTrue(toListResult13 instanceof ObservableToListSingle);
    Single<List<MarketDataList>> toListResult14 = toSerializedResult.toList();
    assertTrue(toListResult14 instanceof ObservableToListSingle);
    Single<List<MarketDataList>> toListResult15 = actualAsObservableResult.toList();
    assertTrue(toListResult15 instanceof ObservableToListSingle);
    assertTrue(toListResult.timestamp() instanceof SingleTimeInterval);
    assertTrue(toListResult2.timestamp() instanceof SingleTimeInterval);
    Single<Timed<List<Timed<Timed<Timed<Timed<Timed<Timed<MarketDataList>>>>>>>>> timestampResult16 = toListResult3
        .timestamp();
    assertTrue(timestampResult16 instanceof SingleTimeInterval);
    Single<Timed<List<Timed<Timed<Timed<Timed<Timed<MarketDataList>>>>>>>> timestampResult17 = toListResult4
        .timestamp();
    assertTrue(timestampResult17 instanceof SingleTimeInterval);
    Single<Timed<List<Timed<Timed<Timed<Timed<Timed<MarketDataList>>>>>>>> timestampResult18 = toListResult5
        .timestamp();
    assertTrue(timestampResult18 instanceof SingleTimeInterval);
    Single<Timed<List<Timed<Timed<Timed<Timed<MarketDataList>>>>>>> timestampResult19 = toListResult6.timestamp();
    assertTrue(timestampResult19 instanceof SingleTimeInterval);
    Single<Timed<List<Timed<Timed<Timed<Timed<MarketDataList>>>>>>> timestampResult20 = toListResult7.timestamp();
    assertTrue(timestampResult20 instanceof SingleTimeInterval);
    Single<Timed<List<Timed<Timed<Timed<MarketDataList>>>>>> timestampResult21 = toListResult8.timestamp();
    assertTrue(timestampResult21 instanceof SingleTimeInterval);
    Single<Timed<List<Timed<Timed<Timed<MarketDataList>>>>>> timestampResult22 = toListResult9.timestamp();
    assertTrue(timestampResult22 instanceof SingleTimeInterval);
    Single<Timed<List<Timed<Timed<MarketDataList>>>>> timestampResult23 = toListResult10.timestamp();
    assertTrue(timestampResult23 instanceof SingleTimeInterval);
    Single<Timed<List<Timed<Timed<MarketDataList>>>>> timestampResult24 = toListResult11.timestamp();
    assertTrue(timestampResult24 instanceof SingleTimeInterval);
    Single<Timed<List<Timed<MarketDataList>>>> timestampResult25 = toListResult12.timestamp();
    assertTrue(timestampResult25 instanceof SingleTimeInterval);
    Single<Timed<List<Timed<MarketDataList>>>> timestampResult26 = toListResult13.timestamp();
    assertTrue(timestampResult26 instanceof SingleTimeInterval);
    Single<Timed<List<MarketDataList>>> timestampResult27 = toListResult14.timestamp();
    assertTrue(timestampResult27 instanceof SingleTimeInterval);
    Single<Timed<List<MarketDataList>>> timestampResult28 = toListResult15.timestamp();
    assertTrue(timestampResult28 instanceof SingleTimeInterval);
    assertTrue(timestampResult16.timestamp() instanceof SingleTimeInterval);
    assertTrue(timestampResult17.timestamp() instanceof SingleTimeInterval);
    Single<Timed<Timed<List<Timed<Timed<Timed<Timed<Timed<MarketDataList>>>>>>>>> timestampResult29 = timestampResult18
        .timestamp();
    assertTrue(timestampResult29 instanceof SingleTimeInterval);
    Single<Timed<Timed<List<Timed<Timed<Timed<Timed<MarketDataList>>>>>>>> timestampResult30 = timestampResult19
        .timestamp();
    assertTrue(timestampResult30 instanceof SingleTimeInterval);
    Single<Timed<Timed<List<Timed<Timed<Timed<Timed<MarketDataList>>>>>>>> timestampResult31 = timestampResult20
        .timestamp();
    assertTrue(timestampResult31 instanceof SingleTimeInterval);
    Single<Timed<Timed<List<Timed<Timed<Timed<MarketDataList>>>>>>> timestampResult32 = timestampResult21.timestamp();
    assertTrue(timestampResult32 instanceof SingleTimeInterval);
    Single<Timed<Timed<List<Timed<Timed<Timed<MarketDataList>>>>>>> timestampResult33 = timestampResult22.timestamp();
    assertTrue(timestampResult33 instanceof SingleTimeInterval);
    Single<Timed<Timed<List<Timed<Timed<MarketDataList>>>>>> timestampResult34 = timestampResult23.timestamp();
    assertTrue(timestampResult34 instanceof SingleTimeInterval);
    Single<Timed<Timed<List<Timed<Timed<MarketDataList>>>>>> timestampResult35 = timestampResult24.timestamp();
    assertTrue(timestampResult35 instanceof SingleTimeInterval);
    Single<Timed<Timed<List<Timed<MarketDataList>>>>> timestampResult36 = timestampResult25.timestamp();
    assertTrue(timestampResult36 instanceof SingleTimeInterval);
    Single<Timed<Timed<List<Timed<MarketDataList>>>>> timestampResult37 = timestampResult26.timestamp();
    assertTrue(timestampResult37 instanceof SingleTimeInterval);
    Single<Timed<Timed<List<MarketDataList>>>> timestampResult38 = timestampResult27.timestamp();
    assertTrue(timestampResult38 instanceof SingleTimeInterval);
    Single<Timed<Timed<List<MarketDataList>>>> timestampResult39 = timestampResult28.timestamp();
    assertTrue(timestampResult39 instanceof SingleTimeInterval);
    assertTrue(timestampResult29.timestamp() instanceof SingleTimeInterval);
    assertTrue(timestampResult30.timestamp() instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<List<Timed<Timed<Timed<Timed<MarketDataList>>>>>>>>> timestampResult40 = timestampResult31
        .timestamp();
    assertTrue(timestampResult40 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<List<Timed<Timed<Timed<MarketDataList>>>>>>>> timestampResult41 = timestampResult32
        .timestamp();
    assertTrue(timestampResult41 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<List<Timed<Timed<Timed<MarketDataList>>>>>>>> timestampResult42 = timestampResult33
        .timestamp();
    assertTrue(timestampResult42 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<List<Timed<Timed<MarketDataList>>>>>>> timestampResult43 = timestampResult34.timestamp();
    assertTrue(timestampResult43 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<List<Timed<Timed<MarketDataList>>>>>>> timestampResult44 = timestampResult35.timestamp();
    assertTrue(timestampResult44 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<List<Timed<MarketDataList>>>>>> timestampResult45 = timestampResult36.timestamp();
    assertTrue(timestampResult45 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<List<Timed<MarketDataList>>>>>> timestampResult46 = timestampResult37.timestamp();
    assertTrue(timestampResult46 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<List<MarketDataList>>>>> timestampResult47 = timestampResult38.timestamp();
    assertTrue(timestampResult47 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<List<MarketDataList>>>>> timestampResult48 = timestampResult39.timestamp();
    assertTrue(timestampResult48 instanceof SingleTimeInterval);
    assertTrue(timestampResult40.timestamp() instanceof SingleTimeInterval);
    assertTrue(timestampResult41.timestamp() instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<List<Timed<Timed<Timed<MarketDataList>>>>>>>>> timestampResult49 = timestampResult42
        .timestamp();
    assertTrue(timestampResult49 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<List<Timed<Timed<MarketDataList>>>>>>>> timestampResult50 = timestampResult43
        .timestamp();
    assertTrue(timestampResult50 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<List<Timed<Timed<MarketDataList>>>>>>>> timestampResult51 = timestampResult44
        .timestamp();
    assertTrue(timestampResult51 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<List<Timed<MarketDataList>>>>>>> timestampResult52 = timestampResult45.timestamp();
    assertTrue(timestampResult52 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<List<Timed<MarketDataList>>>>>>> timestampResult53 = timestampResult46.timestamp();
    assertTrue(timestampResult53 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<List<MarketDataList>>>>>> timestampResult54 = timestampResult47.timestamp();
    assertTrue(timestampResult54 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<List<MarketDataList>>>>>> timestampResult55 = timestampResult48.timestamp();
    assertTrue(timestampResult55 instanceof SingleTimeInterval);
    assertTrue(timestampResult49.timestamp() instanceof SingleTimeInterval);
    assertTrue(timestampResult50.timestamp() instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<Timed<List<Timed<Timed<MarketDataList>>>>>>>>> timestampResult56 = timestampResult51
        .timestamp();
    assertTrue(timestampResult56 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<Timed<List<Timed<MarketDataList>>>>>>>> timestampResult57 = timestampResult52
        .timestamp();
    assertTrue(timestampResult57 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<Timed<List<Timed<MarketDataList>>>>>>>> timestampResult58 = timestampResult53
        .timestamp();
    assertTrue(timestampResult58 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<Timed<List<MarketDataList>>>>>>> timestampResult59 = timestampResult54.timestamp();
    assertTrue(timestampResult59 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<Timed<List<MarketDataList>>>>>>> timestampResult60 = timestampResult55.timestamp();
    assertTrue(timestampResult60 instanceof SingleTimeInterval);
    assertTrue(timestampResult56.timestamp() instanceof SingleTimeInterval);
    assertTrue(timestampResult57.timestamp() instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<Timed<Timed<List<Timed<MarketDataList>>>>>>>>> timestampResult61 = timestampResult58
        .timestamp();
    assertTrue(timestampResult61 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<Timed<Timed<List<MarketDataList>>>>>>>> timestampResult62 = timestampResult59
        .timestamp();
    assertTrue(timestampResult62 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<Timed<Timed<List<MarketDataList>>>>>>>> timestampResult63 = timestampResult60
        .timestamp();
    assertTrue(timestampResult63 instanceof SingleTimeInterval);
    assertTrue(timestampResult61.timestamp() instanceof SingleTimeInterval);
    assertTrue(timestampResult62.timestamp() instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<Timed<Timed<Timed<List<MarketDataList>>>>>>>>> timestampResult64 = timestampResult63
        .timestamp();
    assertTrue(timestampResult64 instanceof SingleTimeInterval);
    assertTrue(timestampResult64.timestamp() instanceof SingleTimeInterval);
    assertTrue(actualAsObservableResult instanceof PublishSubject);
    assertNull(((PublishSubject<MarketDataList>) actualAsObservableResult).getThrowable());
    assertFalse(((PublishSubject<MarketDataList>) actualAsObservableResult).hasComplete());
    assertFalse(((PublishSubject<MarketDataList>) actualAsObservableResult).hasObservers());
    assertFalse(((PublishSubject<MarketDataList>) actualAsObservableResult).hasThrowable());
    assertSame(toSerializedResult.toSerialized(), toSerializedResult.toSerialized());
  }

  /**
   * Method under test: {@link MarketDataGeneratorWebsocket#asObservable()}
   */
  @Test
  void testAsObservable2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    JSONObject authJson = mock(JSONObject.class);

    // Act
    Observable<MarketDataList> actualAsObservableResult = (new MarketDataGeneratorWebsocket(authJson, "Position",
        new ArrayList<>())).asObservable();

    // Assert
    Observable<Timed<MarketDataList>> timestampResult = actualAsObservableResult.timestamp();
    Observable<Timed<Timed<MarketDataList>>> timestampResult2 = timestampResult.timestamp();
    Observable<Timed<Timed<Timed<MarketDataList>>>> timestampResult3 = timestampResult2.timestamp();
    Observable<Timed<Timed<Timed<Timed<MarketDataList>>>>> timestampResult4 = timestampResult3.timestamp();
    Observable<Timed<Timed<Timed<Timed<Timed<MarketDataList>>>>>> timestampResult5 = timestampResult4.timestamp();
    Observable<Timed<Timed<Timed<Timed<Timed<Timed<MarketDataList>>>>>>> timestampResult6 = timestampResult5
        .timestamp();
    Observable<Timed<Timed<Timed<Timed<Timed<Timed<Timed<MarketDataList>>>>>>>> timestampResult7 = timestampResult6
        .timestamp();
    Observable<Timed<Timed<Timed<Timed<Timed<Timed<Timed<Timed<MarketDataList>>>>>>>>> timestampResult8 = timestampResult7
        .timestamp();
    assertTrue(timestampResult8.timestamp() instanceof ObservableMap);
    Subject<MarketDataList> toSerializedResult = ((PublishSubject<MarketDataList>) actualAsObservableResult)
        .toSerialized();
    Observable<Timed<MarketDataList>> timestampResult9 = toSerializedResult.timestamp();
    Observable<Timed<Timed<MarketDataList>>> timestampResult10 = timestampResult9.timestamp();
    Observable<Timed<Timed<Timed<MarketDataList>>>> timestampResult11 = timestampResult10.timestamp();
    Observable<Timed<Timed<Timed<Timed<MarketDataList>>>>> timestampResult12 = timestampResult11.timestamp();
    Observable<Timed<Timed<Timed<Timed<Timed<MarketDataList>>>>>> timestampResult13 = timestampResult12.timestamp();
    Observable<Timed<Timed<Timed<Timed<Timed<Timed<MarketDataList>>>>>>> timestampResult14 = timestampResult13
        .timestamp();
    Observable<Timed<Timed<Timed<Timed<Timed<Timed<Timed<MarketDataList>>>>>>>> timestampResult15 = timestampResult14
        .timestamp();
    assertTrue(timestampResult15.timestamp() instanceof ObservableMap);
    assertTrue(timestampResult8 instanceof ObservableMap);
    assertTrue(timestampResult15 instanceof ObservableMap);
    assertTrue(timestampResult7 instanceof ObservableMap);
    assertTrue(timestampResult14 instanceof ObservableMap);
    assertTrue(timestampResult6 instanceof ObservableMap);
    assertTrue(timestampResult13 instanceof ObservableMap);
    assertTrue(timestampResult5 instanceof ObservableMap);
    assertTrue(timestampResult12 instanceof ObservableMap);
    assertTrue(timestampResult4 instanceof ObservableMap);
    assertTrue(timestampResult11 instanceof ObservableMap);
    assertTrue(timestampResult3 instanceof ObservableMap);
    assertTrue(timestampResult10 instanceof ObservableMap);
    assertTrue(timestampResult2 instanceof ObservableMap);
    assertTrue(timestampResult9 instanceof ObservableMap);
    assertTrue(timestampResult instanceof ObservableMap);
    assertTrue(timestampResult8.toList() instanceof ObservableToListSingle);
    assertTrue(timestampResult15.toList() instanceof ObservableToListSingle);
    Single<List<Timed<Timed<Timed<Timed<Timed<Timed<Timed<MarketDataList>>>>>>>>> toListResult = timestampResult7
        .toList();
    assertTrue(toListResult instanceof ObservableToListSingle);
    Single<List<Timed<Timed<Timed<Timed<Timed<Timed<MarketDataList>>>>>>>> toListResult2 = timestampResult14.toList();
    assertTrue(toListResult2 instanceof ObservableToListSingle);
    Single<List<Timed<Timed<Timed<Timed<Timed<Timed<MarketDataList>>>>>>>> toListResult3 = timestampResult6.toList();
    assertTrue(toListResult3 instanceof ObservableToListSingle);
    Single<List<Timed<Timed<Timed<Timed<Timed<MarketDataList>>>>>>> toListResult4 = timestampResult13.toList();
    assertTrue(toListResult4 instanceof ObservableToListSingle);
    Single<List<Timed<Timed<Timed<Timed<Timed<MarketDataList>>>>>>> toListResult5 = timestampResult5.toList();
    assertTrue(toListResult5 instanceof ObservableToListSingle);
    Single<List<Timed<Timed<Timed<Timed<MarketDataList>>>>>> toListResult6 = timestampResult12.toList();
    assertTrue(toListResult6 instanceof ObservableToListSingle);
    Single<List<Timed<Timed<Timed<Timed<MarketDataList>>>>>> toListResult7 = timestampResult4.toList();
    assertTrue(toListResult7 instanceof ObservableToListSingle);
    Single<List<Timed<Timed<Timed<MarketDataList>>>>> toListResult8 = timestampResult11.toList();
    assertTrue(toListResult8 instanceof ObservableToListSingle);
    Single<List<Timed<Timed<Timed<MarketDataList>>>>> toListResult9 = timestampResult3.toList();
    assertTrue(toListResult9 instanceof ObservableToListSingle);
    Single<List<Timed<Timed<MarketDataList>>>> toListResult10 = timestampResult10.toList();
    assertTrue(toListResult10 instanceof ObservableToListSingle);
    Single<List<Timed<Timed<MarketDataList>>>> toListResult11 = timestampResult2.toList();
    assertTrue(toListResult11 instanceof ObservableToListSingle);
    Single<List<Timed<MarketDataList>>> toListResult12 = timestampResult9.toList();
    assertTrue(toListResult12 instanceof ObservableToListSingle);
    Single<List<Timed<MarketDataList>>> toListResult13 = timestampResult.toList();
    assertTrue(toListResult13 instanceof ObservableToListSingle);
    Single<List<MarketDataList>> toListResult14 = toSerializedResult.toList();
    assertTrue(toListResult14 instanceof ObservableToListSingle);
    Single<List<MarketDataList>> toListResult15 = actualAsObservableResult.toList();
    assertTrue(toListResult15 instanceof ObservableToListSingle);
    assertTrue(toListResult.timestamp() instanceof SingleTimeInterval);
    assertTrue(toListResult2.timestamp() instanceof SingleTimeInterval);
    Single<Timed<List<Timed<Timed<Timed<Timed<Timed<Timed<MarketDataList>>>>>>>>> timestampResult16 = toListResult3
        .timestamp();
    assertTrue(timestampResult16 instanceof SingleTimeInterval);
    Single<Timed<List<Timed<Timed<Timed<Timed<Timed<MarketDataList>>>>>>>> timestampResult17 = toListResult4
        .timestamp();
    assertTrue(timestampResult17 instanceof SingleTimeInterval);
    Single<Timed<List<Timed<Timed<Timed<Timed<Timed<MarketDataList>>>>>>>> timestampResult18 = toListResult5
        .timestamp();
    assertTrue(timestampResult18 instanceof SingleTimeInterval);
    Single<Timed<List<Timed<Timed<Timed<Timed<MarketDataList>>>>>>> timestampResult19 = toListResult6.timestamp();
    assertTrue(timestampResult19 instanceof SingleTimeInterval);
    Single<Timed<List<Timed<Timed<Timed<Timed<MarketDataList>>>>>>> timestampResult20 = toListResult7.timestamp();
    assertTrue(timestampResult20 instanceof SingleTimeInterval);
    Single<Timed<List<Timed<Timed<Timed<MarketDataList>>>>>> timestampResult21 = toListResult8.timestamp();
    assertTrue(timestampResult21 instanceof SingleTimeInterval);
    Single<Timed<List<Timed<Timed<Timed<MarketDataList>>>>>> timestampResult22 = toListResult9.timestamp();
    assertTrue(timestampResult22 instanceof SingleTimeInterval);
    Single<Timed<List<Timed<Timed<MarketDataList>>>>> timestampResult23 = toListResult10.timestamp();
    assertTrue(timestampResult23 instanceof SingleTimeInterval);
    Single<Timed<List<Timed<Timed<MarketDataList>>>>> timestampResult24 = toListResult11.timestamp();
    assertTrue(timestampResult24 instanceof SingleTimeInterval);
    Single<Timed<List<Timed<MarketDataList>>>> timestampResult25 = toListResult12.timestamp();
    assertTrue(timestampResult25 instanceof SingleTimeInterval);
    Single<Timed<List<Timed<MarketDataList>>>> timestampResult26 = toListResult13.timestamp();
    assertTrue(timestampResult26 instanceof SingleTimeInterval);
    Single<Timed<List<MarketDataList>>> timestampResult27 = toListResult14.timestamp();
    assertTrue(timestampResult27 instanceof SingleTimeInterval);
    Single<Timed<List<MarketDataList>>> timestampResult28 = toListResult15.timestamp();
    assertTrue(timestampResult28 instanceof SingleTimeInterval);
    assertTrue(timestampResult16.timestamp() instanceof SingleTimeInterval);
    assertTrue(timestampResult17.timestamp() instanceof SingleTimeInterval);
    Single<Timed<Timed<List<Timed<Timed<Timed<Timed<Timed<MarketDataList>>>>>>>>> timestampResult29 = timestampResult18
        .timestamp();
    assertTrue(timestampResult29 instanceof SingleTimeInterval);
    Single<Timed<Timed<List<Timed<Timed<Timed<Timed<MarketDataList>>>>>>>> timestampResult30 = timestampResult19
        .timestamp();
    assertTrue(timestampResult30 instanceof SingleTimeInterval);
    Single<Timed<Timed<List<Timed<Timed<Timed<Timed<MarketDataList>>>>>>>> timestampResult31 = timestampResult20
        .timestamp();
    assertTrue(timestampResult31 instanceof SingleTimeInterval);
    Single<Timed<Timed<List<Timed<Timed<Timed<MarketDataList>>>>>>> timestampResult32 = timestampResult21.timestamp();
    assertTrue(timestampResult32 instanceof SingleTimeInterval);
    Single<Timed<Timed<List<Timed<Timed<Timed<MarketDataList>>>>>>> timestampResult33 = timestampResult22.timestamp();
    assertTrue(timestampResult33 instanceof SingleTimeInterval);
    Single<Timed<Timed<List<Timed<Timed<MarketDataList>>>>>> timestampResult34 = timestampResult23.timestamp();
    assertTrue(timestampResult34 instanceof SingleTimeInterval);
    Single<Timed<Timed<List<Timed<Timed<MarketDataList>>>>>> timestampResult35 = timestampResult24.timestamp();
    assertTrue(timestampResult35 instanceof SingleTimeInterval);
    Single<Timed<Timed<List<Timed<MarketDataList>>>>> timestampResult36 = timestampResult25.timestamp();
    assertTrue(timestampResult36 instanceof SingleTimeInterval);
    Single<Timed<Timed<List<Timed<MarketDataList>>>>> timestampResult37 = timestampResult26.timestamp();
    assertTrue(timestampResult37 instanceof SingleTimeInterval);
    Single<Timed<Timed<List<MarketDataList>>>> timestampResult38 = timestampResult27.timestamp();
    assertTrue(timestampResult38 instanceof SingleTimeInterval);
    Single<Timed<Timed<List<MarketDataList>>>> timestampResult39 = timestampResult28.timestamp();
    assertTrue(timestampResult39 instanceof SingleTimeInterval);
    assertTrue(timestampResult29.timestamp() instanceof SingleTimeInterval);
    assertTrue(timestampResult30.timestamp() instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<List<Timed<Timed<Timed<Timed<MarketDataList>>>>>>>>> timestampResult40 = timestampResult31
        .timestamp();
    assertTrue(timestampResult40 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<List<Timed<Timed<Timed<MarketDataList>>>>>>>> timestampResult41 = timestampResult32
        .timestamp();
    assertTrue(timestampResult41 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<List<Timed<Timed<Timed<MarketDataList>>>>>>>> timestampResult42 = timestampResult33
        .timestamp();
    assertTrue(timestampResult42 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<List<Timed<Timed<MarketDataList>>>>>>> timestampResult43 = timestampResult34.timestamp();
    assertTrue(timestampResult43 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<List<Timed<Timed<MarketDataList>>>>>>> timestampResult44 = timestampResult35.timestamp();
    assertTrue(timestampResult44 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<List<Timed<MarketDataList>>>>>> timestampResult45 = timestampResult36.timestamp();
    assertTrue(timestampResult45 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<List<Timed<MarketDataList>>>>>> timestampResult46 = timestampResult37.timestamp();
    assertTrue(timestampResult46 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<List<MarketDataList>>>>> timestampResult47 = timestampResult38.timestamp();
    assertTrue(timestampResult47 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<List<MarketDataList>>>>> timestampResult48 = timestampResult39.timestamp();
    assertTrue(timestampResult48 instanceof SingleTimeInterval);
    assertTrue(timestampResult40.timestamp() instanceof SingleTimeInterval);
    assertTrue(timestampResult41.timestamp() instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<List<Timed<Timed<Timed<MarketDataList>>>>>>>>> timestampResult49 = timestampResult42
        .timestamp();
    assertTrue(timestampResult49 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<List<Timed<Timed<MarketDataList>>>>>>>> timestampResult50 = timestampResult43
        .timestamp();
    assertTrue(timestampResult50 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<List<Timed<Timed<MarketDataList>>>>>>>> timestampResult51 = timestampResult44
        .timestamp();
    assertTrue(timestampResult51 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<List<Timed<MarketDataList>>>>>>> timestampResult52 = timestampResult45.timestamp();
    assertTrue(timestampResult52 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<List<Timed<MarketDataList>>>>>>> timestampResult53 = timestampResult46.timestamp();
    assertTrue(timestampResult53 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<List<MarketDataList>>>>>> timestampResult54 = timestampResult47.timestamp();
    assertTrue(timestampResult54 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<List<MarketDataList>>>>>> timestampResult55 = timestampResult48.timestamp();
    assertTrue(timestampResult55 instanceof SingleTimeInterval);
    assertTrue(timestampResult49.timestamp() instanceof SingleTimeInterval);
    assertTrue(timestampResult50.timestamp() instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<Timed<List<Timed<Timed<MarketDataList>>>>>>>>> timestampResult56 = timestampResult51
        .timestamp();
    assertTrue(timestampResult56 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<Timed<List<Timed<MarketDataList>>>>>>>> timestampResult57 = timestampResult52
        .timestamp();
    assertTrue(timestampResult57 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<Timed<List<Timed<MarketDataList>>>>>>>> timestampResult58 = timestampResult53
        .timestamp();
    assertTrue(timestampResult58 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<Timed<List<MarketDataList>>>>>>> timestampResult59 = timestampResult54.timestamp();
    assertTrue(timestampResult59 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<Timed<List<MarketDataList>>>>>>> timestampResult60 = timestampResult55.timestamp();
    assertTrue(timestampResult60 instanceof SingleTimeInterval);
    assertTrue(timestampResult56.timestamp() instanceof SingleTimeInterval);
    assertTrue(timestampResult57.timestamp() instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<Timed<Timed<List<Timed<MarketDataList>>>>>>>>> timestampResult61 = timestampResult58
        .timestamp();
    assertTrue(timestampResult61 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<Timed<Timed<List<MarketDataList>>>>>>>> timestampResult62 = timestampResult59
        .timestamp();
    assertTrue(timestampResult62 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<Timed<Timed<List<MarketDataList>>>>>>>> timestampResult63 = timestampResult60
        .timestamp();
    assertTrue(timestampResult63 instanceof SingleTimeInterval);
    assertTrue(timestampResult61.timestamp() instanceof SingleTimeInterval);
    assertTrue(timestampResult62.timestamp() instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<Timed<Timed<Timed<List<MarketDataList>>>>>>>>> timestampResult64 = timestampResult63
        .timestamp();
    assertTrue(timestampResult64 instanceof SingleTimeInterval);
    assertTrue(timestampResult64.timestamp() instanceof SingleTimeInterval);
    assertTrue(actualAsObservableResult instanceof PublishSubject);
    assertNull(((PublishSubject<MarketDataList>) actualAsObservableResult).getThrowable());
    assertFalse(((PublishSubject<MarketDataList>) actualAsObservableResult).hasComplete());
    assertFalse(((PublishSubject<MarketDataList>) actualAsObservableResult).hasObservers());
    assertFalse(((PublishSubject<MarketDataList>) actualAsObservableResult).hasThrowable());
    assertSame(toSerializedResult.toSerialized(), toSerializedResult.toSerialized());
  }

  /**
   * Method under test:
   * {@link MarketDataGeneratorWebsocket#closeStreamsAndLogoff(WebSocket)}
   */
  @Test
  void testCloseStreamsAndLogoff() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    JSONObject authJson = new JSONObject();
    MarketDataGeneratorWebsocket marketDataGeneratorWebsocket = new MarketDataGeneratorWebsocket(authJson, "Position",
        new ArrayList<>());
    WebSocket webSocket = mock(WebSocket.class);
    when(webSocket.sendText(Mockito.<String>any())).thenReturn(null);

    // Act
    marketDataGeneratorWebsocket.closeStreamsAndLogoff(webSocket);

    // Assert that nothing has changed
    verify(webSocket).sendText(eq("{\"ID\":1, \"Type\": \"Close\", \"Domain\":\"Login\"}"));
  }

  /**
   * Method under test:
   * {@link MarketDataGeneratorWebsocket#writeDataset(String, MarketDataList, boolean)}
   */
  @Test
  void testWriteDataset() throws IOException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    JSONObject authJson = new JSONObject();
    MarketDataGeneratorWebsocket marketDataGeneratorWebsocket = new MarketDataGeneratorWebsocket(authJson, "Position",
        new ArrayList<>());

    // Act and Assert
    assertThrows(RuntimeException.class,
        () -> marketDataGeneratorWebsocket.writeDataset("Import Dir", new MarketDataList(), true));
  }

  /**
   * Method under test:
   * {@link MarketDataGeneratorWebsocket#sendRICRequest(WebSocket)}
   */
  @Test
  void testSendRICRequest() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
   * Method under test:
   * {@link MarketDataGeneratorWebsocket#sendRICRequest(WebSocket)}
   */
  @Test
  void testSendRICRequest2() throws JSONException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    JSONObject authJson = new JSONObject();
    authJson.put("\"Key\":{\"Name\":[", 1L);

    ArrayList<CalibrationDataItem.Spec> itemList = new ArrayList<>();
    itemList.add(new CalibrationDataItem.Spec("Key", "Curve Name", "Product Name", "Maturity"));
    MarketDataGeneratorWebsocket marketDataGeneratorWebsocket = new MarketDataGeneratorWebsocket(authJson, "Position",
        itemList);
    WebSocket websocket = mock(WebSocket.class);
    when(websocket.sendText(Mockito.<String>any())).thenReturn(null);

    // Act
    marketDataGeneratorWebsocket.sendRICRequest(websocket);

    // Assert
    verify(websocket).sendText(
        eq("{\"ID\":2,\"Key\":{\"Name\":[\"Key\"]},\"View\":[\"MID\",\"BID\",\"ASK\",\"VALUE_DT1\",\"VALUE_TS1\"]}"));
  }

  /**
   * Method under test:
   * {@link MarketDataGeneratorWebsocket#sendLoginRequest(WebSocket, String, boolean)}
   */
  @Test
  void testSendLoginRequest() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
   * Method under test:
   * {@link MarketDataGeneratorWebsocket#sendLoginRequest(WebSocket, String, boolean)}
   */
  @Test
  void testSendLoginRequest2() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
   * Method under test:
   * {@link MarketDataGeneratorWebsocket#sendLoginRequest(WebSocket, String, boolean)}
   */
  @Test
  void testSendLoginRequest3() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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

  /**
   * Method under test:
   * {@link MarketDataGeneratorWebsocket#sendLoginRequest(WebSocket, String, boolean)}
   */
  @Test
  void testSendLoginRequest4() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    JSONObject authJson = new JSONObject();
    MarketDataGeneratorWebsocket marketDataGeneratorWebsocket = new MarketDataGeneratorWebsocket(authJson, "Fields",
        new ArrayList<>());
    WebSocket websocket = mock(WebSocket.class);
    when(websocket.sendText(Mockito.<String>any())).thenThrow(new RuntimeException(
        "{\"ID\":1,\"Domain\":\"Login\",\"Key\":{\"Elements\":{\"ApplicationId\":\"\",\"Position\":\"\",\"AuthenticationToken\":\""
            + "\"},\"NameType\":\"AuthnToken\"}}"));

    // Act and Assert
    assertThrows(RuntimeException.class,
        () -> marketDataGeneratorWebsocket.sendLoginRequest(websocket, "ABC123", true));
    verify(websocket).sendText(eq(
        "{\"ID\":1,\"Domain\":\"Login\",\"Key\":{\"Elements\":{\"ApplicationId\":\"256\",\"Position\":\"Fields\",\"AuthenticationToken\":\"ABC123\"},\"NameType\":\"AuthnToken\"}}"));
  }
}
