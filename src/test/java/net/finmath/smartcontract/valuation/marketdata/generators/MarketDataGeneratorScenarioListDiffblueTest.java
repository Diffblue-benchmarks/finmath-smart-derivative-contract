package net.finmath.smartcontract.valuation.marketdata.generators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.internal.operators.observable.ObservableCreate;
import io.reactivex.rxjava3.internal.operators.observable.ObservableMap;
import io.reactivex.rxjava3.internal.operators.observable.ObservableToListSingle;
import io.reactivex.rxjava3.internal.operators.single.SingleTimeInterval;
import io.reactivex.rxjava3.schedulers.Timed;
import java.util.List;
import net.finmath.smartcontract.model.MarketDataList;
import net.finmath.smartcontract.model.SDCException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {MarketDataGeneratorScenarioList.class})
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MarketDataGeneratorScenarioListDiffblueTest {
  @Autowired
  private MarketDataGeneratorScenarioList marketDataGeneratorScenarioList;

  /**
   * Method under test: {@link MarketDataGeneratorScenarioList#asObservable()}
   */
  @Test
  void testAsObservable() {
    // Arrange and Act
    Observable<MarketDataList> actualAsObservableResult = marketDataGeneratorScenarioList.asObservable();

    // Assert
    assertTrue(actualAsObservableResult instanceof ObservableCreate);
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
    assertTrue(timestampResult8 instanceof ObservableMap);
    assertTrue(timestampResult7 instanceof ObservableMap);
    assertTrue(timestampResult6 instanceof ObservableMap);
    assertTrue(timestampResult5 instanceof ObservableMap);
    assertTrue(timestampResult4 instanceof ObservableMap);
    assertTrue(timestampResult3 instanceof ObservableMap);
    assertTrue(timestampResult2 instanceof ObservableMap);
    assertTrue(timestampResult instanceof ObservableMap);
    assertTrue(timestampResult8.toList() instanceof ObservableToListSingle);
    Single<List<Timed<Timed<Timed<Timed<Timed<Timed<Timed<MarketDataList>>>>>>>>> toListResult = timestampResult7
        .toList();
    assertTrue(toListResult instanceof ObservableToListSingle);
    Single<List<Timed<Timed<Timed<Timed<Timed<Timed<MarketDataList>>>>>>>> toListResult2 = timestampResult6.toList();
    assertTrue(toListResult2 instanceof ObservableToListSingle);
    Single<List<Timed<Timed<Timed<Timed<Timed<MarketDataList>>>>>>> toListResult3 = timestampResult5.toList();
    assertTrue(toListResult3 instanceof ObservableToListSingle);
    Single<List<Timed<Timed<Timed<Timed<MarketDataList>>>>>> toListResult4 = timestampResult4.toList();
    assertTrue(toListResult4 instanceof ObservableToListSingle);
    Single<List<Timed<Timed<Timed<MarketDataList>>>>> toListResult5 = timestampResult3.toList();
    assertTrue(toListResult5 instanceof ObservableToListSingle);
    Single<List<Timed<Timed<MarketDataList>>>> toListResult6 = timestampResult2.toList();
    assertTrue(toListResult6 instanceof ObservableToListSingle);
    Single<List<Timed<MarketDataList>>> toListResult7 = timestampResult.toList();
    assertTrue(toListResult7 instanceof ObservableToListSingle);
    Single<List<MarketDataList>> toListResult8 = actualAsObservableResult.toList();
    assertTrue(toListResult8 instanceof ObservableToListSingle);
    assertTrue(toListResult.timestamp() instanceof SingleTimeInterval);
    Single<Timed<List<Timed<Timed<Timed<Timed<Timed<Timed<MarketDataList>>>>>>>>> timestampResult9 = toListResult2
        .timestamp();
    assertTrue(timestampResult9 instanceof SingleTimeInterval);
    Single<Timed<List<Timed<Timed<Timed<Timed<Timed<MarketDataList>>>>>>>> timestampResult10 = toListResult3
        .timestamp();
    assertTrue(timestampResult10 instanceof SingleTimeInterval);
    Single<Timed<List<Timed<Timed<Timed<Timed<MarketDataList>>>>>>> timestampResult11 = toListResult4.timestamp();
    assertTrue(timestampResult11 instanceof SingleTimeInterval);
    Single<Timed<List<Timed<Timed<Timed<MarketDataList>>>>>> timestampResult12 = toListResult5.timestamp();
    assertTrue(timestampResult12 instanceof SingleTimeInterval);
    Single<Timed<List<Timed<Timed<MarketDataList>>>>> timestampResult13 = toListResult6.timestamp();
    assertTrue(timestampResult13 instanceof SingleTimeInterval);
    Single<Timed<List<Timed<MarketDataList>>>> timestampResult14 = toListResult7.timestamp();
    assertTrue(timestampResult14 instanceof SingleTimeInterval);
    Single<Timed<List<MarketDataList>>> timestampResult15 = toListResult8.timestamp();
    assertTrue(timestampResult15 instanceof SingleTimeInterval);
    assertTrue(timestampResult9.timestamp() instanceof SingleTimeInterval);
    Single<Timed<Timed<List<Timed<Timed<Timed<Timed<Timed<MarketDataList>>>>>>>>> timestampResult16 = timestampResult10
        .timestamp();
    assertTrue(timestampResult16 instanceof SingleTimeInterval);
    Single<Timed<Timed<List<Timed<Timed<Timed<Timed<MarketDataList>>>>>>>> timestampResult17 = timestampResult11
        .timestamp();
    assertTrue(timestampResult17 instanceof SingleTimeInterval);
    Single<Timed<Timed<List<Timed<Timed<Timed<MarketDataList>>>>>>> timestampResult18 = timestampResult12.timestamp();
    assertTrue(timestampResult18 instanceof SingleTimeInterval);
    Single<Timed<Timed<List<Timed<Timed<MarketDataList>>>>>> timestampResult19 = timestampResult13.timestamp();
    assertTrue(timestampResult19 instanceof SingleTimeInterval);
    Single<Timed<Timed<List<Timed<MarketDataList>>>>> timestampResult20 = timestampResult14.timestamp();
    assertTrue(timestampResult20 instanceof SingleTimeInterval);
    Single<Timed<Timed<List<MarketDataList>>>> timestampResult21 = timestampResult15.timestamp();
    assertTrue(timestampResult21 instanceof SingleTimeInterval);
    assertTrue(timestampResult16.timestamp() instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<List<Timed<Timed<Timed<Timed<MarketDataList>>>>>>>>> timestampResult22 = timestampResult17
        .timestamp();
    assertTrue(timestampResult22 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<List<Timed<Timed<Timed<MarketDataList>>>>>>>> timestampResult23 = timestampResult18
        .timestamp();
    assertTrue(timestampResult23 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<List<Timed<Timed<MarketDataList>>>>>>> timestampResult24 = timestampResult19.timestamp();
    assertTrue(timestampResult24 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<List<Timed<MarketDataList>>>>>> timestampResult25 = timestampResult20.timestamp();
    assertTrue(timestampResult25 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<List<MarketDataList>>>>> timestampResult26 = timestampResult21.timestamp();
    assertTrue(timestampResult26 instanceof SingleTimeInterval);
    assertTrue(timestampResult22.timestamp() instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<List<Timed<Timed<Timed<MarketDataList>>>>>>>>> timestampResult27 = timestampResult23
        .timestamp();
    assertTrue(timestampResult27 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<List<Timed<Timed<MarketDataList>>>>>>>> timestampResult28 = timestampResult24
        .timestamp();
    assertTrue(timestampResult28 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<List<Timed<MarketDataList>>>>>>> timestampResult29 = timestampResult25.timestamp();
    assertTrue(timestampResult29 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<List<MarketDataList>>>>>> timestampResult30 = timestampResult26.timestamp();
    assertTrue(timestampResult30 instanceof SingleTimeInterval);
    assertTrue(timestampResult27.timestamp() instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<Timed<List<Timed<Timed<MarketDataList>>>>>>>>> timestampResult31 = timestampResult28
        .timestamp();
    assertTrue(timestampResult31 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<Timed<List<Timed<MarketDataList>>>>>>>> timestampResult32 = timestampResult29
        .timestamp();
    assertTrue(timestampResult32 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<Timed<List<MarketDataList>>>>>>> timestampResult33 = timestampResult30.timestamp();
    assertTrue(timestampResult33 instanceof SingleTimeInterval);
    assertTrue(timestampResult31.timestamp() instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<Timed<Timed<List<Timed<MarketDataList>>>>>>>>> timestampResult34 = timestampResult32
        .timestamp();
    assertTrue(timestampResult34 instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<Timed<Timed<List<MarketDataList>>>>>>>> timestampResult35 = timestampResult33
        .timestamp();
    assertTrue(timestampResult35 instanceof SingleTimeInterval);
    assertTrue(timestampResult34.timestamp() instanceof SingleTimeInterval);
    Single<Timed<Timed<Timed<Timed<Timed<Timed<Timed<List<MarketDataList>>>>>>>>> timestampResult36 = timestampResult35
        .timestamp();
    assertTrue(timestampResult36 instanceof SingleTimeInterval);
    assertTrue(timestampResult36.timestamp() instanceof SingleTimeInterval);
  }

  /**
   * Method under test:
   * {@link MarketDataGeneratorScenarioList#getMarketDataString(String)}
   */
  @Test
  void testGetMarketDataString() {
    // Arrange, Act and Assert
    assertThrows(SDCException.class, () -> marketDataGeneratorScenarioList.getMarketDataString("foo.txt"));
  }

  /**
   * Method under test: {@link MarketDataGeneratorScenarioList#getCounter()}
   */
  @Test
  void testGetCounter() {
    // Arrange, Act and Assert
    assertEquals(0, (new MarketDataGeneratorScenarioList()).getCounter());
  }
}
