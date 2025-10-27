package net.finmath.smartcontract.valuation.marketdata.generators.legacy;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.atLeast;
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
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ReactiveMarketDataUpdaterDiffblueTest {
  /**
   * Method under test: {@link ReactiveMarketDataUpdater#asObservable()}
   */
  @Test
  void testAsObservable() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    JSONObject authJson = new JSONObject();

    // Act
    Observable<MarketDataSet> actualAsObservableResult = (new ReactiveMarketDataUpdater(authJson, "Position",
        new ArrayList<>())).asObservable();

    // Assert
    Observable<Timed<MarketDataSet>> timestampResult = actualAsObservableResult.timestamp();
    Observable<Timed<Timed<MarketDataSet>>> timestampResult2 = timestampResult.timestamp();
    Observable<Timed<Timed<Timed<MarketDataSet>>>> timestampResult3 = timestampResult2.timestamp();
    Observable<Timed<Timed<Timed<Timed<MarketDataSet>>>>> timestampResult4 = timestampResult3.timestamp();
    Observable<Timed<Timed<Timed<Timed<Timed<MarketDataSet>>>>>> timestampResult5 = timestampResult4.timestamp();
    Observable<Timed<Timed<Timed<Timed<Timed<Timed<MarketDataSet>>>>>>> timestampResult6 = timestampResult5.timestamp();
    Observable<Timed<Timed<Timed<Timed<Timed<Timed<Timed<MarketDataSet>>>>>>>> timestampResult7 = timestampResult6
        .timestamp();
    Observable<Timed<Timed<Timed<Timed<Timed<Timed<Timed<Timed<MarketDataSet>>>>>>>>> timestampResult8 = timestampResult7
        .timestamp();
    assertTrue(timestampResult8.timestamp() instanceof ObservableMap);
    Subject<MarketDataSet> toSerializedResult = ((PublishSubject<MarketDataSet>) actualAsObservableResult)
        .toSerialized();
    Observable<Timed<MarketDataSet>> timestampResult9 = toSerializedResult.timestamp();
    Observable<Timed<Timed<MarketDataSet>>> timestampResult10 = timestampResult9.timestamp();
    Observable<Timed<Timed<Timed<MarketDataSet>>>> timestampResult11 = timestampResult10.timestamp();
    Observable<Timed<Timed<Timed<Timed<MarketDataSet>>>>> timestampResult12 = timestampResult11.timestamp();
    Observable<Timed<Timed<Timed<Timed<Timed<MarketDataSet>>>>>> timestampResult13 = timestampResult12.timestamp();
    Observable<Timed<Timed<Timed<Timed<Timed<Timed<MarketDataSet>>>>>>> timestampResult14 = timestampResult13
        .timestamp();
    Observable<Timed<Timed<Timed<Timed<Timed<Timed<Timed<MarketDataSet>>>>>>>> timestampResult15 = timestampResult14
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
    Single<List<Timed<Timed<Timed<Timed<Timed<Timed<Timed<MarketDataSet>>>>>>>>> toListResult = timestampResult7
        .toList();
    assertTrue(toListResult instanceof ObservableToListSingle);
    Single<List<Timed<Timed<Timed<Timed<Timed<Timed<MarketDataSet>>>>>>>> toListResult2 = timestampResult14.toList();
    assertTrue(toListResult2 instanceof ObservableToListSingle);
    Single<List<Timed<Timed<Timed<Timed<Timed<Timed<MarketDataSet>>>>>>>> toListResult3 = timestampResult6.toList();
    assertTrue(toListResult3 instanceof ObservableToListSingle);
    Single<List<Timed<Timed<Timed<Timed<Timed<MarketDataSet>>>>>>> toListResult4 = timestampResult13.toList();
    assertTrue(toListResult4 instanceof ObservableToListSingle);
    Single<List<Timed<Timed<Timed<Timed<Timed<MarketDataSet>>>>>>> toListResult5 = timestampResult5.toList();
    assertTrue(toListResult5 instanceof ObservableToListSingle);
    Single<List<Timed<Timed<Timed<Timed<MarketDataSet>>>>>> toListResult6 = timestampResult12.toList();
    assertTrue(toListResult6 instanceof ObservableToListSingle);
    Single<List<Timed<Timed<Timed<Timed<MarketDataSet>>>>>> toListResult7 = timestampResult4.toList();
    assertTrue(toListResult7 instanceof ObservableToListSingle);
    Single<List<Timed<Timed<Timed<MarketDataSet>>>>> toListResult8 = timestampResult11.toList();
    assertTrue(toListResult8 instanceof ObservableToListSingle);
    Single<List<Timed<Timed<Timed<MarketDataSet>>>>> toListResult9 = timestampResult3.toList();
    assertTrue(toListResult9 instanceof ObservableToListSingle);
    Single<List<Timed<Timed<MarketDataSet>>>> toListResult10 = timestampResult10.toList();
    assertTrue(toListResult10 instanceof ObservableToListSingle);
    Single<List<Timed<Timed<MarketDataSet>>>> toListResult11 = timestampResult2.toList();
    assertTrue(toListResult11 instanceof ObservableToListSingle);
    Single<List<Timed<MarketDataSet>>> toListResult12 = timestampResult9.toList();
    assertTrue(toListResult12 instanceof ObservableToListSingle);
    Single<List<Timed<MarketDataSet>>> toListResult13 = timestampResult.toList();
    assertTrue(toListResult13 instanceof ObservableToListSingle);
    Single<List<MarketDataSet>> toListResult14 = toSerializedResult.toList();
    assertTrue(toListResult14 instanceof ObservableToListSingle);
    Single<List<MarketDataSet>> toListResult15 = actualAsObservableResult.toList();
    assertTrue(toListResult15 instanceof ObservableToListSingle);
    assertTrue(toListResult.timestamp() instanceof SingleTimeInterval);
    assertTrue(toListResult2.timestamp() instanceof SingleTimeInterval);
    Single<Timed<List<Timed<Timed<Timed<Timed<Timed<Timed<MarketDataSet>>>>>>>>> timestampResult16 = toListResult3
        .timestamp();
    assertTrue(timestampResult16 instanceof SingleTimeInterval);
    Single<Timed<List<Timed<Timed<Timed<Timed<Timed<MarketDataSet>>>>>>>> timestampResult17 = toListResult4.timestamp();
    assertTrue(timestampResult17 instanceof SingleTimeInterval);
    Single<Timed<List<Timed<Timed<Timed<Timed<Timed<MarketDataSet>>>>>>>> timestampResult18 = toListResult5.timestamp();
    assertTrue(timestampResult18 instanceof SingleTimeInterval);
    Single<Timed<List<Timed<Timed<Timed<Timed<MarketDataSet>>>>>>> timestampResult19 = toListResult6.timestamp();
    assertTrue(timestampResult19 instanceof SingleTimeInterval);
    Single<Timed<List<Timed<Timed<Timed<Timed<MarketDataSet>>>>>>> timestampResult20 = toListResult7.timestamp();
    assertTrue(timestampResult20 instanceof SingleTimeInterval);
    Single<Timed<List<Timed<Timed<Timed<MarketDataSet>>>>>> timestampResult21 = toListResult8.timestamp();
    assertTrue(timestampResult21 instanceof SingleTimeInterval);
    Single<Timed<List<Timed<Timed<Timed<MarketDataSet>>>>>> timestampResult22 = toListResult9.timestamp();
    assertTrue(timestampResult22 instanceof SingleTimeInterval);
    Single<Timed<List<Timed<Timed<MarketDataSet>>>>> timestampResult23 = toListResult10.timestamp();
    assertTrue(timestampResult23 instanceof SingleTimeInterval);
    Single<Timed<List<Timed<Timed<MarketDataSet>>>>> timestampResult24 = toListResult11.timestamp();
    assertTrue(timestampResult24 instanceof SingleTimeInterval);
    Single<Timed<List<Timed<MarketDataSet>>>> timestampResult25 = toListResult12.timestamp();
    assertTrue(timestampResult25 instanceof SingleTimeInterval);
    Single<Timed<List<Timed<MarketDataSet>>>> timestampResult26 = toListResult13.timestamp();
    assertTrue(timestampResult26 instanceof SingleTimeInterval);
    Single<Timed<List<MarketDataSet>>> timestampResult27 = toListResult14.timestamp();
    assertTrue(timestampResult27 instanceof SingleTimeInterval);
    Single<Timed<List<MarketDataSet>>> timestampResult28 = toListResult15.timestamp();
    assertTrue(timestampResult28 instanceof SingleTimeInterval);
    assertTrue(timestampResult16.timestamp() instanceof SingleTimeInterval);
    assertTrue(timestampResult17.timestamp() instanceof SingleTimeInterval);
    Single<Timed<Timed<List<Timed<Timed<Timed<Timed<Timed<MarketDataSet>>>>>>>>> timestampResult29 = timestampResult18
        .timestamp();
    assertTrue(timestampResult29 instanceof SingleTimeInterval);
    Single<Timed<Timed<List<Timed<Timed<Timed<Timed<MarketDataSet>>>>>>>> timestampResult30 = timestampResult19
        .timestamp();
    assertTrue(timestampResult30 instanceof SingleTimeInterval);
    Single<Timed<Timed<List<Timed<Timed<Timed<Timed<MarketDataSet>>>>>>>> timestampResult31 = timestampResult20
        .timestamp();
    assertTrue(timestampResult31 instanceof SingleTimeInterval);
    Single<Timed<Timed<List<Timed<Timed<Timed<MarketDataSet>>>>>>> timestampResult32 = timestampResult21.timestamp();
    assertTrue(timestampResult32 instanceof SingleTimeInterval);
    Single<Timed<Timed<List<Timed<Timed<Timed<MarketDataSet>>>>>>> timestampResult33 = timestampResult22.timestamp();
    assertTrue(timestampResult33 instanceof SingleTimeInterval);
    Single<Timed<Timed<List<Timed<Timed<MarketDataSet>>>>>> timestampResult34 = timestampResult23.timestamp();
    assertTrue(timestampResult34 instanceof SingleTimeInterval);
    Single<Timed<Timed<List<Timed<Timed<MarketDataSet>>>>>> timestampResult35 = timestampResult24.timestamp();
    assertTrue(timestampResult35 instanceof SingleTimeInterval);
    Single<Timed<Timed<List<Timed<MarketDataSet>>>>> timestampResult36 = timestampResult25.timestamp();
    assertTrue(timestampResult36 instanceof SingleTimeInterval);
    Single<Timed<Timed<List<Timed<MarketDataSet>>>>> timestampResult37 = timestampResult26.timestamp();
    assertTrue(timestampResult37 instanceof SingleTimeInterval);
    Single<Timed<Timed<List<MarketDataSet>>>> timestampResult38 = timestampResult27.timestamp();
    assertTrue(timestampResult38 instanceof SingleTimeInterval);
    Single<Timed<Timed<List<MarketDataSet>>>> timestampResult39 = timestampResult28.timestamp();
    assertTrue(timestampResult39 instanceof SingleTimeInterval);
    assertTrue(timestampResult29.timestamp() instanceof SingleTimeInterval);
    assertTrue(timestampResult30.timestamp() instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<List<Timed<Timed<Timed<Timed<MarketDataSet>>>>>>>>> timestampResult40 = timestampResult31
        .timestamp();
    assertTrue(timestampResult40 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<List<Timed<Timed<Timed<MarketDataSet>>>>>>>> timestampResult41 = timestampResult32
        .timestamp();
    assertTrue(timestampResult41 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<List<Timed<Timed<Timed<MarketDataSet>>>>>>>> timestampResult42 = timestampResult33
        .timestamp();
    assertTrue(timestampResult42 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<List<Timed<Timed<MarketDataSet>>>>>>> timestampResult43 = timestampResult34.timestamp();
    assertTrue(timestampResult43 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<List<Timed<Timed<MarketDataSet>>>>>>> timestampResult44 = timestampResult35.timestamp();
    assertTrue(timestampResult44 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<List<Timed<MarketDataSet>>>>>> timestampResult45 = timestampResult36.timestamp();
    assertTrue(timestampResult45 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<List<Timed<MarketDataSet>>>>>> timestampResult46 = timestampResult37.timestamp();
    assertTrue(timestampResult46 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<List<MarketDataSet>>>>> timestampResult47 = timestampResult38.timestamp();
    assertTrue(timestampResult47 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<List<MarketDataSet>>>>> timestampResult48 = timestampResult39.timestamp();
    assertTrue(timestampResult48 instanceof SingleTimeInterval);
    assertTrue(timestampResult40.timestamp() instanceof SingleTimeInterval);
    assertTrue(timestampResult41.timestamp() instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<List<Timed<Timed<Timed<MarketDataSet>>>>>>>>> timestampResult49 = timestampResult42
        .timestamp();
    assertTrue(timestampResult49 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<List<Timed<Timed<MarketDataSet>>>>>>>> timestampResult50 = timestampResult43
        .timestamp();
    assertTrue(timestampResult50 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<List<Timed<Timed<MarketDataSet>>>>>>>> timestampResult51 = timestampResult44
        .timestamp();
    assertTrue(timestampResult51 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<List<Timed<MarketDataSet>>>>>>> timestampResult52 = timestampResult45.timestamp();
    assertTrue(timestampResult52 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<List<Timed<MarketDataSet>>>>>>> timestampResult53 = timestampResult46.timestamp();
    assertTrue(timestampResult53 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<List<MarketDataSet>>>>>> timestampResult54 = timestampResult47.timestamp();
    assertTrue(timestampResult54 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<List<MarketDataSet>>>>>> timestampResult55 = timestampResult48.timestamp();
    assertTrue(timestampResult55 instanceof SingleTimeInterval);
    assertTrue(timestampResult49.timestamp() instanceof SingleTimeInterval);
    assertTrue(timestampResult50.timestamp() instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<Timed<List<Timed<Timed<MarketDataSet>>>>>>>>> timestampResult56 = timestampResult51
        .timestamp();
    assertTrue(timestampResult56 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<Timed<List<Timed<MarketDataSet>>>>>>>> timestampResult57 = timestampResult52
        .timestamp();
    assertTrue(timestampResult57 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<Timed<List<Timed<MarketDataSet>>>>>>>> timestampResult58 = timestampResult53
        .timestamp();
    assertTrue(timestampResult58 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<Timed<List<MarketDataSet>>>>>>> timestampResult59 = timestampResult54.timestamp();
    assertTrue(timestampResult59 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<Timed<List<MarketDataSet>>>>>>> timestampResult60 = timestampResult55.timestamp();
    assertTrue(timestampResult60 instanceof SingleTimeInterval);
    assertTrue(timestampResult56.timestamp() instanceof SingleTimeInterval);
    assertTrue(timestampResult57.timestamp() instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<Timed<Timed<List<Timed<MarketDataSet>>>>>>>>> timestampResult61 = timestampResult58
        .timestamp();
    assertTrue(timestampResult61 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<Timed<Timed<List<MarketDataSet>>>>>>>> timestampResult62 = timestampResult59
        .timestamp();
    assertTrue(timestampResult62 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<Timed<Timed<List<MarketDataSet>>>>>>>> timestampResult63 = timestampResult60
        .timestamp();
    assertTrue(timestampResult63 instanceof SingleTimeInterval);
    assertTrue(timestampResult61.timestamp() instanceof SingleTimeInterval);
    assertTrue(timestampResult62.timestamp() instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<Timed<Timed<Timed<List<MarketDataSet>>>>>>>>> timestampResult64 = timestampResult63
        .timestamp();
    assertTrue(timestampResult64 instanceof SingleTimeInterval);
    assertTrue(timestampResult64.timestamp() instanceof SingleTimeInterval);
    assertTrue(actualAsObservableResult instanceof PublishSubject);
    assertNull(((PublishSubject<MarketDataSet>) actualAsObservableResult).getThrowable());
    assertFalse(((PublishSubject<MarketDataSet>) actualAsObservableResult).hasComplete());
    assertFalse(((PublishSubject<MarketDataSet>) actualAsObservableResult).hasObservers());
    assertFalse(((PublishSubject<MarketDataSet>) actualAsObservableResult).hasThrowable());
    assertSame(toSerializedResult.toSerialized(), toSerializedResult.toSerialized());
  }

  /**
   * Method under test: {@link ReactiveMarketDataUpdater#asObservable()}
   */
  @Test
  void testAsObservable2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    JSONObject authJson = mock(JSONObject.class);

    // Act
    Observable<MarketDataSet> actualAsObservableResult = (new ReactiveMarketDataUpdater(authJson, "Position",
        new ArrayList<>())).asObservable();

    // Assert
    Observable<Timed<MarketDataSet>> timestampResult = actualAsObservableResult.timestamp();
    Observable<Timed<Timed<MarketDataSet>>> timestampResult2 = timestampResult.timestamp();
    Observable<Timed<Timed<Timed<MarketDataSet>>>> timestampResult3 = timestampResult2.timestamp();
    Observable<Timed<Timed<Timed<Timed<MarketDataSet>>>>> timestampResult4 = timestampResult3.timestamp();
    Observable<Timed<Timed<Timed<Timed<Timed<MarketDataSet>>>>>> timestampResult5 = timestampResult4.timestamp();
    Observable<Timed<Timed<Timed<Timed<Timed<Timed<MarketDataSet>>>>>>> timestampResult6 = timestampResult5.timestamp();
    Observable<Timed<Timed<Timed<Timed<Timed<Timed<Timed<MarketDataSet>>>>>>>> timestampResult7 = timestampResult6
        .timestamp();
    Observable<Timed<Timed<Timed<Timed<Timed<Timed<Timed<Timed<MarketDataSet>>>>>>>>> timestampResult8 = timestampResult7
        .timestamp();
    assertTrue(timestampResult8.timestamp() instanceof ObservableMap);
    Subject<MarketDataSet> toSerializedResult = ((PublishSubject<MarketDataSet>) actualAsObservableResult)
        .toSerialized();
    Observable<Timed<MarketDataSet>> timestampResult9 = toSerializedResult.timestamp();
    Observable<Timed<Timed<MarketDataSet>>> timestampResult10 = timestampResult9.timestamp();
    Observable<Timed<Timed<Timed<MarketDataSet>>>> timestampResult11 = timestampResult10.timestamp();
    Observable<Timed<Timed<Timed<Timed<MarketDataSet>>>>> timestampResult12 = timestampResult11.timestamp();
    Observable<Timed<Timed<Timed<Timed<Timed<MarketDataSet>>>>>> timestampResult13 = timestampResult12.timestamp();
    Observable<Timed<Timed<Timed<Timed<Timed<Timed<MarketDataSet>>>>>>> timestampResult14 = timestampResult13
        .timestamp();
    Observable<Timed<Timed<Timed<Timed<Timed<Timed<Timed<MarketDataSet>>>>>>>> timestampResult15 = timestampResult14
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
    Single<List<Timed<Timed<Timed<Timed<Timed<Timed<Timed<MarketDataSet>>>>>>>>> toListResult = timestampResult7
        .toList();
    assertTrue(toListResult instanceof ObservableToListSingle);
    Single<List<Timed<Timed<Timed<Timed<Timed<Timed<MarketDataSet>>>>>>>> toListResult2 = timestampResult14.toList();
    assertTrue(toListResult2 instanceof ObservableToListSingle);
    Single<List<Timed<Timed<Timed<Timed<Timed<Timed<MarketDataSet>>>>>>>> toListResult3 = timestampResult6.toList();
    assertTrue(toListResult3 instanceof ObservableToListSingle);
    Single<List<Timed<Timed<Timed<Timed<Timed<MarketDataSet>>>>>>> toListResult4 = timestampResult13.toList();
    assertTrue(toListResult4 instanceof ObservableToListSingle);
    Single<List<Timed<Timed<Timed<Timed<Timed<MarketDataSet>>>>>>> toListResult5 = timestampResult5.toList();
    assertTrue(toListResult5 instanceof ObservableToListSingle);
    Single<List<Timed<Timed<Timed<Timed<MarketDataSet>>>>>> toListResult6 = timestampResult12.toList();
    assertTrue(toListResult6 instanceof ObservableToListSingle);
    Single<List<Timed<Timed<Timed<Timed<MarketDataSet>>>>>> toListResult7 = timestampResult4.toList();
    assertTrue(toListResult7 instanceof ObservableToListSingle);
    Single<List<Timed<Timed<Timed<MarketDataSet>>>>> toListResult8 = timestampResult11.toList();
    assertTrue(toListResult8 instanceof ObservableToListSingle);
    Single<List<Timed<Timed<Timed<MarketDataSet>>>>> toListResult9 = timestampResult3.toList();
    assertTrue(toListResult9 instanceof ObservableToListSingle);
    Single<List<Timed<Timed<MarketDataSet>>>> toListResult10 = timestampResult10.toList();
    assertTrue(toListResult10 instanceof ObservableToListSingle);
    Single<List<Timed<Timed<MarketDataSet>>>> toListResult11 = timestampResult2.toList();
    assertTrue(toListResult11 instanceof ObservableToListSingle);
    Single<List<Timed<MarketDataSet>>> toListResult12 = timestampResult9.toList();
    assertTrue(toListResult12 instanceof ObservableToListSingle);
    Single<List<Timed<MarketDataSet>>> toListResult13 = timestampResult.toList();
    assertTrue(toListResult13 instanceof ObservableToListSingle);
    Single<List<MarketDataSet>> toListResult14 = toSerializedResult.toList();
    assertTrue(toListResult14 instanceof ObservableToListSingle);
    Single<List<MarketDataSet>> toListResult15 = actualAsObservableResult.toList();
    assertTrue(toListResult15 instanceof ObservableToListSingle);
    assertTrue(toListResult.timestamp() instanceof SingleTimeInterval);
    assertTrue(toListResult2.timestamp() instanceof SingleTimeInterval);
    Single<Timed<List<Timed<Timed<Timed<Timed<Timed<Timed<MarketDataSet>>>>>>>>> timestampResult16 = toListResult3
        .timestamp();
    assertTrue(timestampResult16 instanceof SingleTimeInterval);
    Single<Timed<List<Timed<Timed<Timed<Timed<Timed<MarketDataSet>>>>>>>> timestampResult17 = toListResult4.timestamp();
    assertTrue(timestampResult17 instanceof SingleTimeInterval);
    Single<Timed<List<Timed<Timed<Timed<Timed<Timed<MarketDataSet>>>>>>>> timestampResult18 = toListResult5.timestamp();
    assertTrue(timestampResult18 instanceof SingleTimeInterval);
    Single<Timed<List<Timed<Timed<Timed<Timed<MarketDataSet>>>>>>> timestampResult19 = toListResult6.timestamp();
    assertTrue(timestampResult19 instanceof SingleTimeInterval);
    Single<Timed<List<Timed<Timed<Timed<Timed<MarketDataSet>>>>>>> timestampResult20 = toListResult7.timestamp();
    assertTrue(timestampResult20 instanceof SingleTimeInterval);
    Single<Timed<List<Timed<Timed<Timed<MarketDataSet>>>>>> timestampResult21 = toListResult8.timestamp();
    assertTrue(timestampResult21 instanceof SingleTimeInterval);
    Single<Timed<List<Timed<Timed<Timed<MarketDataSet>>>>>> timestampResult22 = toListResult9.timestamp();
    assertTrue(timestampResult22 instanceof SingleTimeInterval);
    Single<Timed<List<Timed<Timed<MarketDataSet>>>>> timestampResult23 = toListResult10.timestamp();
    assertTrue(timestampResult23 instanceof SingleTimeInterval);
    Single<Timed<List<Timed<Timed<MarketDataSet>>>>> timestampResult24 = toListResult11.timestamp();
    assertTrue(timestampResult24 instanceof SingleTimeInterval);
    Single<Timed<List<Timed<MarketDataSet>>>> timestampResult25 = toListResult12.timestamp();
    assertTrue(timestampResult25 instanceof SingleTimeInterval);
    Single<Timed<List<Timed<MarketDataSet>>>> timestampResult26 = toListResult13.timestamp();
    assertTrue(timestampResult26 instanceof SingleTimeInterval);
    Single<Timed<List<MarketDataSet>>> timestampResult27 = toListResult14.timestamp();
    assertTrue(timestampResult27 instanceof SingleTimeInterval);
    Single<Timed<List<MarketDataSet>>> timestampResult28 = toListResult15.timestamp();
    assertTrue(timestampResult28 instanceof SingleTimeInterval);
    assertTrue(timestampResult16.timestamp() instanceof SingleTimeInterval);
    assertTrue(timestampResult17.timestamp() instanceof SingleTimeInterval);
    Single<Timed<Timed<List<Timed<Timed<Timed<Timed<Timed<MarketDataSet>>>>>>>>> timestampResult29 = timestampResult18
        .timestamp();
    assertTrue(timestampResult29 instanceof SingleTimeInterval);
    Single<Timed<Timed<List<Timed<Timed<Timed<Timed<MarketDataSet>>>>>>>> timestampResult30 = timestampResult19
        .timestamp();
    assertTrue(timestampResult30 instanceof SingleTimeInterval);
    Single<Timed<Timed<List<Timed<Timed<Timed<Timed<MarketDataSet>>>>>>>> timestampResult31 = timestampResult20
        .timestamp();
    assertTrue(timestampResult31 instanceof SingleTimeInterval);
    Single<Timed<Timed<List<Timed<Timed<Timed<MarketDataSet>>>>>>> timestampResult32 = timestampResult21.timestamp();
    assertTrue(timestampResult32 instanceof SingleTimeInterval);
    Single<Timed<Timed<List<Timed<Timed<Timed<MarketDataSet>>>>>>> timestampResult33 = timestampResult22.timestamp();
    assertTrue(timestampResult33 instanceof SingleTimeInterval);
    Single<Timed<Timed<List<Timed<Timed<MarketDataSet>>>>>> timestampResult34 = timestampResult23.timestamp();
    assertTrue(timestampResult34 instanceof SingleTimeInterval);
    Single<Timed<Timed<List<Timed<Timed<MarketDataSet>>>>>> timestampResult35 = timestampResult24.timestamp();
    assertTrue(timestampResult35 instanceof SingleTimeInterval);
    Single<Timed<Timed<List<Timed<MarketDataSet>>>>> timestampResult36 = timestampResult25.timestamp();
    assertTrue(timestampResult36 instanceof SingleTimeInterval);
    Single<Timed<Timed<List<Timed<MarketDataSet>>>>> timestampResult37 = timestampResult26.timestamp();
    assertTrue(timestampResult37 instanceof SingleTimeInterval);
    Single<Timed<Timed<List<MarketDataSet>>>> timestampResult38 = timestampResult27.timestamp();
    assertTrue(timestampResult38 instanceof SingleTimeInterval);
    Single<Timed<Timed<List<MarketDataSet>>>> timestampResult39 = timestampResult28.timestamp();
    assertTrue(timestampResult39 instanceof SingleTimeInterval);
    assertTrue(timestampResult29.timestamp() instanceof SingleTimeInterval);
    assertTrue(timestampResult30.timestamp() instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<List<Timed<Timed<Timed<Timed<MarketDataSet>>>>>>>>> timestampResult40 = timestampResult31
        .timestamp();
    assertTrue(timestampResult40 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<List<Timed<Timed<Timed<MarketDataSet>>>>>>>> timestampResult41 = timestampResult32
        .timestamp();
    assertTrue(timestampResult41 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<List<Timed<Timed<Timed<MarketDataSet>>>>>>>> timestampResult42 = timestampResult33
        .timestamp();
    assertTrue(timestampResult42 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<List<Timed<Timed<MarketDataSet>>>>>>> timestampResult43 = timestampResult34.timestamp();
    assertTrue(timestampResult43 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<List<Timed<Timed<MarketDataSet>>>>>>> timestampResult44 = timestampResult35.timestamp();
    assertTrue(timestampResult44 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<List<Timed<MarketDataSet>>>>>> timestampResult45 = timestampResult36.timestamp();
    assertTrue(timestampResult45 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<List<Timed<MarketDataSet>>>>>> timestampResult46 = timestampResult37.timestamp();
    assertTrue(timestampResult46 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<List<MarketDataSet>>>>> timestampResult47 = timestampResult38.timestamp();
    assertTrue(timestampResult47 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<List<MarketDataSet>>>>> timestampResult48 = timestampResult39.timestamp();
    assertTrue(timestampResult48 instanceof SingleTimeInterval);
    assertTrue(timestampResult40.timestamp() instanceof SingleTimeInterval);
    assertTrue(timestampResult41.timestamp() instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<List<Timed<Timed<Timed<MarketDataSet>>>>>>>>> timestampResult49 = timestampResult42
        .timestamp();
    assertTrue(timestampResult49 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<List<Timed<Timed<MarketDataSet>>>>>>>> timestampResult50 = timestampResult43
        .timestamp();
    assertTrue(timestampResult50 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<List<Timed<Timed<MarketDataSet>>>>>>>> timestampResult51 = timestampResult44
        .timestamp();
    assertTrue(timestampResult51 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<List<Timed<MarketDataSet>>>>>>> timestampResult52 = timestampResult45.timestamp();
    assertTrue(timestampResult52 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<List<Timed<MarketDataSet>>>>>>> timestampResult53 = timestampResult46.timestamp();
    assertTrue(timestampResult53 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<List<MarketDataSet>>>>>> timestampResult54 = timestampResult47.timestamp();
    assertTrue(timestampResult54 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<List<MarketDataSet>>>>>> timestampResult55 = timestampResult48.timestamp();
    assertTrue(timestampResult55 instanceof SingleTimeInterval);
    assertTrue(timestampResult49.timestamp() instanceof SingleTimeInterval);
    assertTrue(timestampResult50.timestamp() instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<Timed<List<Timed<Timed<MarketDataSet>>>>>>>>> timestampResult56 = timestampResult51
        .timestamp();
    assertTrue(timestampResult56 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<Timed<List<Timed<MarketDataSet>>>>>>>> timestampResult57 = timestampResult52
        .timestamp();
    assertTrue(timestampResult57 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<Timed<List<Timed<MarketDataSet>>>>>>>> timestampResult58 = timestampResult53
        .timestamp();
    assertTrue(timestampResult58 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<Timed<List<MarketDataSet>>>>>>> timestampResult59 = timestampResult54.timestamp();
    assertTrue(timestampResult59 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<Timed<List<MarketDataSet>>>>>>> timestampResult60 = timestampResult55.timestamp();
    assertTrue(timestampResult60 instanceof SingleTimeInterval);
    assertTrue(timestampResult56.timestamp() instanceof SingleTimeInterval);
    assertTrue(timestampResult57.timestamp() instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<Timed<Timed<List<Timed<MarketDataSet>>>>>>>>> timestampResult61 = timestampResult58
        .timestamp();
    assertTrue(timestampResult61 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<Timed<Timed<List<MarketDataSet>>>>>>>> timestampResult62 = timestampResult59
        .timestamp();
    assertTrue(timestampResult62 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<Timed<Timed<List<MarketDataSet>>>>>>>> timestampResult63 = timestampResult60
        .timestamp();
    assertTrue(timestampResult63 instanceof SingleTimeInterval);
    assertTrue(timestampResult61.timestamp() instanceof SingleTimeInterval);
    assertTrue(timestampResult62.timestamp() instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<Timed<Timed<Timed<List<MarketDataSet>>>>>>>>> timestampResult64 = timestampResult63
        .timestamp();
    assertTrue(timestampResult64 instanceof SingleTimeInterval);
    assertTrue(timestampResult64.timestamp() instanceof SingleTimeInterval);
    assertTrue(actualAsObservableResult instanceof PublishSubject);
    assertNull(((PublishSubject<MarketDataSet>) actualAsObservableResult).getThrowable());
    assertFalse(((PublishSubject<MarketDataSet>) actualAsObservableResult).hasComplete());
    assertFalse(((PublishSubject<MarketDataSet>) actualAsObservableResult).hasObservers());
    assertFalse(((PublishSubject<MarketDataSet>) actualAsObservableResult).hasThrowable());
    assertSame(toSerializedResult.toSerialized(), toSerializedResult.toSerialized());
  }

  /**
   * Method under test:
   * {@link ReactiveMarketDataUpdater#closeStreamsAndLogoff(WebSocket)}
   */
  @Test
  void testCloseStreamsAndLogoff() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    JSONObject authJson = new JSONObject();
    ReactiveMarketDataUpdater reactiveMarketDataUpdater = new ReactiveMarketDataUpdater(authJson, "Position",
        new ArrayList<>());
    WebSocket webSocket = mock(WebSocket.class);
    when(webSocket.sendText(Mockito.<String>any())).thenReturn(null);

    // Act
    reactiveMarketDataUpdater.closeStreamsAndLogoff(webSocket);

    // Assert that nothing has changed
    verify(webSocket).sendText(eq("{\"ID\":1, \"Type\": \"Close\", \"Domain\":\"Login\"}"));
  }

  /**
   * Method under test:
   * {@link ReactiveMarketDataUpdater#ReactiveMarketDataUpdater(JSONObject, String, List)}
   */
  @Test
  void testNewReactiveMarketDataUpdater() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    JSONObject authJson = new JSONObject();

    // Act and Assert
    assertFalse((new ReactiveMarketDataUpdater(authJson, "Position", new ArrayList<>())).requestSent);
  }

  /**
   * Method under test:
   * {@link ReactiveMarketDataUpdater#ReactiveMarketDataUpdater(JSONObject, String, List)}
   */
  @Test
  void testNewReactiveMarketDataUpdater2() throws JSONException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    JSONObject authJson = new JSONObject();
    authJson.put("42", false);

    // Act and Assert
    assertFalse((new ReactiveMarketDataUpdater(authJson, "Position", new ArrayList<>())).requestSent);
  }

  /**
   * Method under test:
   * {@link ReactiveMarketDataUpdater#ReactiveMarketDataUpdater(JSONObject, String, List)}
   */
  @Test
  void testNewReactiveMarketDataUpdater3() throws JSONException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    JSONObject authJson = mock(JSONObject.class);
    when(authJson.put(Mockito.<String>any(), anyBoolean())).thenReturn(new JSONObject());
    authJson.put("42", false);

    // Act
    ReactiveMarketDataUpdater actualReactiveMarketDataUpdater = new ReactiveMarketDataUpdater(authJson, "Position",
        new ArrayList<>());

    // Assert
    verify(authJson).put(eq("42"), eq(false));
    assertFalse(actualReactiveMarketDataUpdater.requestSent);
  }

  /**
   * Method under test:
   * {@link ReactiveMarketDataUpdater#ReactiveMarketDataUpdater(JSONObject, String, List)}
   */
  @Test
  void testNewReactiveMarketDataUpdater4() throws JSONException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    JSONObject authJson = mock(JSONObject.class);
    when(authJson.put(Mockito.<String>any(), anyBoolean())).thenReturn(new JSONObject());
    authJson.put("42", false);

    ArrayList<CalibrationDataItem.Spec> itemList = new ArrayList<>();
    itemList.add(new CalibrationDataItem.Spec("Key", "Curve Name", "Product Name", "Maturity"));

    // Act
    ReactiveMarketDataUpdater actualReactiveMarketDataUpdater = new ReactiveMarketDataUpdater(authJson, "Position",
        itemList);

    // Assert
    verify(authJson).put(eq("42"), eq(false));
    assertFalse(actualReactiveMarketDataUpdater.requestSent);
  }

  /**
   * Method under test:
   * {@link ReactiveMarketDataUpdater#ReactiveMarketDataUpdater(JSONObject, String, List)}
   */
  @Test
  void testNewReactiveMarketDataUpdater5() throws JSONException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    JSONObject authJson = mock(JSONObject.class);
    when(authJson.put(Mockito.<String>any(), anyBoolean())).thenReturn(new JSONObject());
    authJson.put("42", false);

    ArrayList<CalibrationDataItem.Spec> itemList = new ArrayList<>();
    itemList.add(new CalibrationDataItem.Spec("Key", "Curve Name", "Product Name", "Maturity"));
    itemList.add(new CalibrationDataItem.Spec("Key", "Curve Name", "Product Name", "Maturity"));

    // Act
    ReactiveMarketDataUpdater actualReactiveMarketDataUpdater = new ReactiveMarketDataUpdater(authJson, "Position",
        itemList);

    // Assert
    verify(authJson).put(eq("42"), eq(false));
    assertFalse(actualReactiveMarketDataUpdater.requestSent);
  }

  /**
   * Method under test:
   * {@link ReactiveMarketDataUpdater#onConnected(WebSocket, Map)}
   */
  @Test
  void testOnConnected() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    JSONObject authJson = mock(JSONObject.class);
    when(authJson.getString(Mockito.<String>any())).thenReturn("String");
    ReactiveMarketDataUpdater reactiveMarketDataUpdater = new ReactiveMarketDataUpdater(authJson, "Position",
        new ArrayList<>());
    WebSocket websocket = mock(WebSocket.class);
    when(websocket.sendText(Mockito.<String>any())).thenReturn(null);

    // Act
    reactiveMarketDataUpdater.onConnected(websocket, new HashMap<>());

    // Assert
    verify(websocket, atLeast(1)).sendText(Mockito.<String>any());
    verify(authJson, atLeast(1)).getString(eq("access_token"));
  }

  /**
   * Method under test:
   * {@link ReactiveMarketDataUpdater#onTextMessage(WebSocket, String)}
   */
  @Test
  void testOnTextMessage() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    JSONObject authJson = new JSONObject();
    ReactiveMarketDataUpdater reactiveMarketDataUpdater = new ReactiveMarketDataUpdater(authJson, "Position",
        new ArrayList<>());
    WebSocket websocket = mock(WebSocket.class);
    when(websocket.sendText(Mockito.<String>any())).thenReturn(null);

    // Act
    reactiveMarketDataUpdater.onTextMessage(websocket, "Not all who wander are lost");

    // Assert
    verify(websocket)
        .sendText(eq("{\"ID\":2,\"Key\":{\"Name\":]},\"View\":[\"MID\",\"BID\",\"ASK\",\"VALUE_DT1\",\"VALUE_TS1\"]}"));
    assertFalse(reactiveMarketDataUpdater.requestSent);
  }

  /**
   * Method under test:
   * {@link ReactiveMarketDataUpdater#onTextMessage(WebSocket, String)}
   */
  @Test
  void testOnTextMessage2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ArrayList<CalibrationDataItem.Spec> itemList = new ArrayList<>();
    itemList.add(new CalibrationDataItem.Spec(
        "JSON mapper is failing silently in order to skip message:{}{}{}as it is not a quote/fixing update.",
        "JSON mapper is failing silently in order to skip message:{}{}{}as it is not a quote/fixing update.",
        "JSON mapper is failing silently in order to skip message:{}{}{}as it is not a quote/fixing update.",
        "JSON mapper is failing silently in order to skip message:{}{}{}as it is not a quote/fixing update."));
    ReactiveMarketDataUpdater reactiveMarketDataUpdater = new ReactiveMarketDataUpdater(new JSONObject(), "Position",
        itemList);
    WebSocket websocket = mock(WebSocket.class);
    when(websocket.sendText(Mockito.<String>any())).thenReturn(null);

    // Act
    reactiveMarketDataUpdater.onTextMessage(websocket, "Not all who wander are lost");

    // Assert
    verify(websocket).sendText(eq(
        "{\"ID\":2,\"Key\":{\"Name\":[\"JSON mapper is failing silently in order to skip message:{}{}{}as it is not a quote/fixing update.\"]},\"View\":[\"MID\",\"BID\",\"ASK\",\"VALUE_DT1\",\"VALUE_TS1\"]}"));
    assertTrue(reactiveMarketDataUpdater.requestSent);
  }

  /**
   * Method under test:
   * {@link ReactiveMarketDataUpdater#onTextMessage(WebSocket, String)}
   */
  @Test
  void testOnTextMessage3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    JSONObject authJson = new JSONObject();
    ReactiveMarketDataUpdater reactiveMarketDataUpdater = new ReactiveMarketDataUpdater(authJson, "Position",
        new ArrayList<>());
    WebSocket websocket = mock(WebSocket.class);
    when(websocket.sendText(Mockito.<String>any())).thenReturn(null);

    // Act
    reactiveMarketDataUpdater.onTextMessage(websocket, "\"Key\":{\"Name\":[");

    // Assert
    verify(websocket)
        .sendText(eq("{\"ID\":2,\"Key\":{\"Name\":]},\"View\":[\"MID\",\"BID\",\"ASK\",\"VALUE_DT1\",\"VALUE_TS1\"]}"));
    assertFalse(reactiveMarketDataUpdater.requestSent);
  }

  /**
   * Method under test:
   * {@link ReactiveMarketDataUpdater#onTextMessage(WebSocket, String)}
   */
  @Test
  void testOnTextMessage4() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    JSONObject authJson = new JSONObject();
    ReactiveMarketDataUpdater reactiveMarketDataUpdater = new ReactiveMarketDataUpdater(authJson, "Position",
        new ArrayList<>());
    WebSocket websocket = mock(WebSocket.class);
    when(websocket.sendText(Mockito.<String>any())).thenReturn(null);

    // Act
    reactiveMarketDataUpdater.onTextMessage(websocket, "]}");

    // Assert
    verify(websocket)
        .sendText(eq("{\"ID\":2,\"Key\":{\"Name\":]},\"View\":[\"MID\",\"BID\",\"ASK\",\"VALUE_DT1\",\"VALUE_TS1\"]}"));
    assertFalse(reactiveMarketDataUpdater.requestSent);
  }

  /**
   * Method under test:
   * {@link ReactiveMarketDataUpdater#onTextMessage(WebSocket, String)}
   */
  @Test
  void testOnTextMessage5() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    JSONObject authJson = new JSONObject();
    ReactiveMarketDataUpdater reactiveMarketDataUpdater = new ReactiveMarketDataUpdater(authJson, "Position",
        new ArrayList<>());
    WebSocket websocket = mock(WebSocket.class);
    when(websocket.sendText(Mockito.<String>any())).thenReturn(null);

    // Act
    reactiveMarketDataUpdater.onTextMessage(websocket, "\",");

    // Assert
    verify(websocket)
        .sendText(eq("{\"ID\":2,\"Key\":{\"Name\":]},\"View\":[\"MID\",\"BID\",\"ASK\",\"VALUE_DT1\",\"VALUE_TS1\"]}"));
    assertFalse(reactiveMarketDataUpdater.requestSent);
  }

  /**
   * Method under test:
   * {@link ReactiveMarketDataUpdater#onTextMessage(WebSocket, String)}
   */
  @Test
  void testOnTextMessage6() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    JSONObject authJson = new JSONObject();
    ReactiveMarketDataUpdater reactiveMarketDataUpdater = new ReactiveMarketDataUpdater(authJson, "Position",
        new ArrayList<>());
    WebSocket websocket = mock(WebSocket.class);
    when(websocket.sendText(Mockito.<String>any())).thenReturn(null);

    // Act
    reactiveMarketDataUpdater.onTextMessage(websocket, "42");

    // Assert
    verify(websocket)
        .sendText(eq("{\"ID\":2,\"Key\":{\"Name\":]},\"View\":[\"MID\",\"BID\",\"ASK\",\"VALUE_DT1\",\"VALUE_TS1\"]}"));
    assertFalse(reactiveMarketDataUpdater.requestSent);
  }

  /**
   * Method under test:
   * {@link ReactiveMarketDataUpdater#onTextMessage(WebSocket, String)}
   */
  @Test
  void testOnTextMessage7() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    JSONObject authJson = new JSONObject();
    ReactiveMarketDataUpdater reactiveMarketDataUpdater = new ReactiveMarketDataUpdater(authJson, "Position",
        new ArrayList<>());

    // Act
    reactiveMarketDataUpdater.onTextMessage(mock(WebSocket.class), "");

    // Assert
    assertFalse(reactiveMarketDataUpdater.requestSent);
  }

  /**
   * Method under test:
   * {@link ReactiveMarketDataUpdater#onTextMessage(WebSocket, String)}
   */
  @Test
  void testOnTextMessage8() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    JSONObject authJson = new JSONObject();
    ReactiveMarketDataUpdater reactiveMarketDataUpdater = new ReactiveMarketDataUpdater(authJson, "Position",
        new ArrayList<>());
    WebSocket websocket = mock(WebSocket.class);
    when(websocket.sendText(Mockito.<String>any())).thenReturn(null);

    // Act
    reactiveMarketDataUpdater.onTextMessage(websocket, "...done");

    // Assert
    verify(websocket)
        .sendText(eq("{\"ID\":2,\"Key\":{\"Name\":]},\"View\":[\"MID\",\"BID\",\"ASK\",\"VALUE_DT1\",\"VALUE_TS1\"]}"));
    assertFalse(reactiveMarketDataUpdater.requestSent);
  }

  /**
   * Method under test:
   * {@link ReactiveMarketDataUpdater#onTextMessage(WebSocket, String)}
   */
  @Test
  void testOnTextMessage9() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    JSONObject authJson = new JSONObject();
    ReactiveMarketDataUpdater reactiveMarketDataUpdater = new ReactiveMarketDataUpdater(authJson, "Position",
        new ArrayList<>());
    WebSocket websocket = mock(WebSocket.class);
    when(websocket.sendText(Mockito.<String>any())).thenReturn(null);

    // Act
    reactiveMarketDataUpdater.onTextMessage(websocket,
        "{\"ID\":1,\"Domain\":\"Login\",\"Key\":{\"Elements\":{\"ApplicationId\":\"\",\"Position\":\"\",\"AuthenticationToken\":\""
            + "\"},\"NameType\":\"AuthnToken\"}}");

    // Assert
    verify(websocket)
        .sendText(eq("{\"ID\":2,\"Key\":{\"Name\":]},\"View\":[\"MID\",\"BID\",\"ASK\",\"VALUE_DT1\",\"VALUE_TS1\"]}"));
    assertFalse(reactiveMarketDataUpdater.requestSent);
  }

  /**
   * Method under test:
   * {@link ReactiveMarketDataUpdater#onTextMessage(WebSocket, String)}
   */
  @Test
  void testOnTextMessage10() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ArrayList<CalibrationDataItem.Spec> itemList = new ArrayList<>();
    itemList.addAll(new ArrayList<>());
    ReactiveMarketDataUpdater reactiveMarketDataUpdater = new ReactiveMarketDataUpdater(new JSONObject(), "Position",
        itemList);
    WebSocket websocket = mock(WebSocket.class);
    when(websocket.sendText(Mockito.<String>any())).thenThrow(new IllegalStateException(
        "JSON mapper is failing silently in order to skip message:{}{}{}as it is not a quote/fixing update."));

    // Act and Assert
    assertThrows(IllegalStateException.class,
        () -> reactiveMarketDataUpdater.onTextMessage(websocket, "Not all who wander are lost"));
    verify(websocket)
        .sendText(eq("{\"ID\":2,\"Key\":{\"Name\":]},\"View\":[\"MID\",\"BID\",\"ASK\",\"VALUE_DT1\",\"VALUE_TS1\"]}"));
  }

  /**
   * Method under test:
   * {@link ReactiveMarketDataUpdater#writeDataset(String, MarketDataSet, boolean)}
   */
  @Test
  void testWriteDataset() throws IOException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    JSONObject authJson = new JSONObject();
    ReactiveMarketDataUpdater reactiveMarketDataUpdater = new ReactiveMarketDataUpdater(authJson, "Position",
        new ArrayList<>());
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
