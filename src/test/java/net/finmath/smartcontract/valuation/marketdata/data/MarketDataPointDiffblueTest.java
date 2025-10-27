package net.finmath.smartcontract.valuation.marketdata.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class MarketDataPointDiffblueTest {
  /**
   * Method under test: {@link MarketDataPoint#equals(Object)}
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    MarketDataPoint marketDataPoint = new MarketDataPoint();
    MarketDataPoint marketDataPoint2 = new MarketDataPoint();

    // Act and Assert
    assertEquals(marketDataPoint, marketDataPoint2);
    int notExpectedHashCodeResult = marketDataPoint.hashCode();
    assertNotEquals(notExpectedHashCodeResult, marketDataPoint2.hashCode());
  }

  /**
   * Method under test: {@link MarketDataPoint#equals(Object)}
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    MarketDataPoint marketDataPoint = new MarketDataPoint();

    // Act and Assert
    assertEquals(marketDataPoint, marketDataPoint);
    int expectedHashCodeResult = marketDataPoint.hashCode();
    assertEquals(expectedHashCodeResult, marketDataPoint.hashCode());
  }

  /**
   * Method under test: {@link MarketDataPoint#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    MarketDataPoint marketDataPoint = new MarketDataPoint("42", 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay());

    // Act and Assert
    assertNotEquals(marketDataPoint, new MarketDataPoint());
  }

  /**
   * Method under test: {@link MarketDataPoint#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    MarketDataPoint marketDataPoint = new MarketDataPoint();
    marketDataPoint.setId("42");

    // Act and Assert
    assertNotEquals(marketDataPoint, new MarketDataPoint());
  }

  /**
   * Method under test: {@link MarketDataPoint#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    MarketDataPoint marketDataPoint = new MarketDataPoint();
    marketDataPoint.setValue(10.0d);

    // Act and Assert
    assertNotEquals(marketDataPoint, new MarketDataPoint());
  }

  /**
   * Method under test: {@link MarketDataPoint#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new MarketDataPoint(), null);
  }

  /**
   * Method under test: {@link MarketDataPoint#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new MarketDataPoint(), "Different type to MarketDataPoint");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link MarketDataPoint#MarketDataPoint()}
   *   <li>{@link MarketDataPoint#setId(String)}
   *   <li>{@link MarketDataPoint#setTimeStamp(LocalDateTime)}
   *   <li>{@link MarketDataPoint#setValue(Double)}
   *   <li>{@link MarketDataPoint#toString()}
   *   <li>{@link MarketDataPoint#getId()}
   *   <li>{@link MarketDataPoint#getTimeStamp()}
   *   <li>{@link MarketDataPoint#getValue()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    MarketDataPoint actualMarketDataPoint = new MarketDataPoint();
    actualMarketDataPoint.setId("42");
    LocalDateTime timeStamp = LocalDate.of(1970, 1, 1).atStartOfDay();
    actualMarketDataPoint.setTimeStamp(timeStamp);
    actualMarketDataPoint.setValue(10.0d);
    String actualToStringResult = actualMarketDataPoint.toString();
    String actualId = actualMarketDataPoint.getId();
    LocalDateTime actualTimeStamp = actualMarketDataPoint.getTimeStamp();

    // Assert that nothing has changed
    assertEquals("42", actualId);
    assertEquals("MarketDataPoint{timeStamp=1970-01-01T00:00, id='42', value=10.0}", actualToStringResult);
    assertEquals(10.0d, actualMarketDataPoint.getValue().doubleValue());
    assertSame(timeStamp, actualTimeStamp);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link MarketDataPoint#MarketDataPoint(String, Double, LocalDateTime)}
   *   <li>{@link MarketDataPoint#setId(String)}
   *   <li>{@link MarketDataPoint#setTimeStamp(LocalDateTime)}
   *   <li>{@link MarketDataPoint#setValue(Double)}
   *   <li>{@link MarketDataPoint#toString()}
   *   <li>{@link MarketDataPoint#getId()}
   *   <li>{@link MarketDataPoint#getTimeStamp()}
   *   <li>{@link MarketDataPoint#getValue()}
   * </ul>
   */
  @Test
  void testGettersAndSetters2() {
    // Arrange and Act
    MarketDataPoint actualMarketDataPoint = new MarketDataPoint("42", 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay());
    actualMarketDataPoint.setId("42");
    LocalDateTime timeStamp = LocalDate.of(1970, 1, 1).atStartOfDay();
    actualMarketDataPoint.setTimeStamp(timeStamp);
    actualMarketDataPoint.setValue(10.0d);
    String actualToStringResult = actualMarketDataPoint.toString();
    String actualId = actualMarketDataPoint.getId();
    LocalDateTime actualTimeStamp = actualMarketDataPoint.getTimeStamp();

    // Assert that nothing has changed
    assertEquals("42", actualId);
    assertEquals("MarketDataPoint{timeStamp=1970-01-01T00:00, id='42', value=10.0}", actualToStringResult);
    assertEquals(10.0d, actualMarketDataPoint.getValue().doubleValue());
    assertSame(timeStamp, actualTimeStamp);
  }
}
