package net.finmath.smartcontract.valuation.marketdata.generators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {MarketDataGeneratorScenarioList.class})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
class MarketDataGeneratorScenarioListDiffblueTest {
  @Autowired private MarketDataGeneratorScenarioList marketDataGeneratorScenarioList;

  /**
   * Test {@link MarketDataGeneratorScenarioList#asObservable()}.
   *
   * <p>Method under test: {@link MarketDataGeneratorScenarioList#asObservable()}
   */
  @Test
  @DisplayName("Test asObservable()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Observable MarketDataGeneratorScenarioList.asObservable()"})
  void testAsObservable() {
    // Arrange and Act
    Observable<MarketDataList> actualAsObservableResult =
        marketDataGeneratorScenarioList.asObservable();

    // Assert
    assertTrue(actualAsObservableResult instanceof ObservableCreate);
    Observable<Timed<MarketDataList>> timestampResult = actualAsObservableResult.timestamp();
    Observable<Timed<Timed<MarketDataList>>> timestampResult2 = timestampResult.timestamp();
    assertTrue(timestampResult2.timestamp() instanceof ObservableMap);
    assertTrue(timestampResult2 instanceof ObservableMap);
    assertTrue(timestampResult instanceof ObservableMap);
    assertTrue(timestampResult2.toList() instanceof ObservableToListSingle);
    Single<List<Timed<MarketDataList>>> toListResult = timestampResult.toList();
    assertTrue(toListResult instanceof ObservableToListSingle);
    Single<List<MarketDataList>> toListResult2 = actualAsObservableResult.toList();
    assertTrue(toListResult2 instanceof ObservableToListSingle);
    assertTrue(toListResult.timestamp() instanceof SingleTimeInterval);
    Single<Timed<List<MarketDataList>>> timestampResult3 = toListResult2.timestamp();
    assertTrue(timestampResult3 instanceof SingleTimeInterval);
    assertTrue(timestampResult3.timestamp() instanceof SingleTimeInterval);
  }

  /**
   * Test {@link MarketDataGeneratorScenarioList#getMarketDataString(String)}.
   *
   * <ul>
   *   <li>Then throw {@link SDCException}.
   * </ul>
   *
   * <p>Method under test: {@link MarketDataGeneratorScenarioList#getMarketDataString(String)}
   */
  @Test
  @DisplayName("Test getMarketDataString(String); then throw SDCException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String MarketDataGeneratorScenarioList.getMarketDataString(String)"})
  void testGetMarketDataString_thenThrowSDCException() {
    // Arrange, Act and Assert
    assertThrows(
        SDCException.class,
        () ->
            marketDataGeneratorScenarioList.getMarketDataString(
                MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml()));
  }

  /**
   * Test {@link MarketDataGeneratorScenarioList#getCounter()}.
   *
   * <p>Method under test: {@link MarketDataGeneratorScenarioList#getCounter()}
   */
  @Test
  @DisplayName("Test getCounter()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"int MarketDataGeneratorScenarioList.getCounter()"})
  void testGetCounter() {
    // Arrange, Act and Assert
    assertEquals(0, new MarketDataGeneratorScenarioList().getCounter());
  }
}
