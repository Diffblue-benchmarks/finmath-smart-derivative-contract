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
import java.util.List;
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
    itemList.add(new Spec("Key", "Curve Name", "Product Name", "Maturity"));

    // Act and Assert
    assertFalse(
        new MarketDataGeneratorWebsocket(new JSONObject(), "Position", itemList)
            .allQuotesRetrieved());
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

    // Act and Assert
    assertTrue(
        new MarketDataGeneratorWebsocket(authJson, "Position", new ArrayList<>())
            .allQuotesRetrieved());
  }

  /**
   * Test {@link MarketDataGeneratorWebsocket#asObservable()}.
   *
   * <ul>
   *   <li>Then timestamp timestamp return {@link ObservableMap}.
   * </ul>
   *
   * <p>Method under test: {@link MarketDataGeneratorWebsocket#asObservable()}
   */
  @Test
  @DisplayName("Test asObservable(); then timestamp timestamp return ObservableMap")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Observable MarketDataGeneratorWebsocket.asObservable()"})
  void testAsObservable_thenTimestampTimestampReturnObservableMap() {
    // Arrange
    JSONObject authJson = new JSONObject();

    // Act
    Observable<MarketDataList> actualAsObservableResult =
        new MarketDataGeneratorWebsocket(authJson, "Position", new ArrayList<>()).asObservable();

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
   * <ul>
   *   <li>Then throw {@link RuntimeException}.
   * </ul>
   *
   * <p>Method under test: {@link MarketDataGeneratorWebsocket#writeDataset(String, MarketDataList,
   * boolean)}
   */
  @Test
  @DisplayName("Test writeDataset(String, MarketDataList, boolean); then throw RuntimeException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MarketDataGeneratorWebsocket.writeDataset(String, MarketDataList, boolean)"
  })
  void testWriteDataset_thenThrowRuntimeException() throws IOException {
    // Arrange
    JSONObject authJson = new JSONObject();
    MarketDataGeneratorWebsocket marketDataGeneratorWebsocket =
        new MarketDataGeneratorWebsocket(authJson, "Position", new ArrayList<>());

    // Act and Assert
    assertThrows(
        RuntimeException.class,
        () -> marketDataGeneratorWebsocket.writeDataset("Import Dir", new MarketDataList(), true));
  }
}
