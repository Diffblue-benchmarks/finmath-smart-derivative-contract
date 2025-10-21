package net.finmath.smartcontract.valuation.marketdata.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class MarketDataPointDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
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
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void MarketDataPoint.<init>()", "void MarketDataPoint.<init>(String, Double, LocalDateTime)",
      "String MarketDataPoint.getId()", "LocalDateTime MarketDataPoint.getTimeStamp()",
      "Double MarketDataPoint.getValue()", "void MarketDataPoint.setId(String)",
      "void MarketDataPoint.setTimeStamp(LocalDateTime)", "void MarketDataPoint.setValue(Double)",
      "String MarketDataPoint.toString()"})
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

    // Assert
    assertEquals("42", actualId);
    assertEquals("MarketDataPoint{timeStamp=1970-01-01T00:00, id='42', value=10.0}", actualToStringResult);
    assertEquals(10.0d, actualMarketDataPoint.getValue().doubleValue());
    assertSame(timeStamp, actualTimeStamp);
  }

  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@code 42}.</li>
   * </ul>
   * <p>
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
  @DisplayName("Test getters and setters; when '42'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void MarketDataPoint.<init>()", "void MarketDataPoint.<init>(String, Double, LocalDateTime)",
      "String MarketDataPoint.getId()", "LocalDateTime MarketDataPoint.getTimeStamp()",
      "Double MarketDataPoint.getValue()", "void MarketDataPoint.setId(String)",
      "void MarketDataPoint.setTimeStamp(LocalDateTime)", "void MarketDataPoint.setValue(Double)",
      "String MarketDataPoint.toString()"})
  void testGettersAndSetters_when42() {
    // Arrange and Act
    MarketDataPoint actualMarketDataPoint = new MarketDataPoint("42", 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay());
    actualMarketDataPoint.setId("42");
    LocalDateTime timeStamp = LocalDate.of(1970, 1, 1).atStartOfDay();
    actualMarketDataPoint.setTimeStamp(timeStamp);
    actualMarketDataPoint.setValue(10.0d);
    String actualToStringResult = actualMarketDataPoint.toString();
    String actualId = actualMarketDataPoint.getId();
    LocalDateTime actualTimeStamp = actualMarketDataPoint.getTimeStamp();

    // Assert
    assertEquals("42", actualId);
    assertEquals("MarketDataPoint{timeStamp=1970-01-01T00:00, id='42', value=10.0}", actualToStringResult);
    assertEquals(10.0d, actualMarketDataPoint.getValue().doubleValue());
    assertSame(timeStamp, actualTimeStamp);
  }

  /**
   * Test {@link MarketDataPoint#equals(Object)}, and {@link Object#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link MarketDataPoint#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean MarketDataPoint.equals(Object)"})
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
   * Test {@link MarketDataPoint#equals(Object)}, and {@link Object#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link MarketDataPoint#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean MarketDataPoint.equals(Object)"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    MarketDataPoint marketDataPoint = new MarketDataPoint();

    // Act and Assert
    assertEquals(marketDataPoint, marketDataPoint);
    int expectedHashCodeResult = marketDataPoint.hashCode();
    assertEquals(expectedHashCodeResult, marketDataPoint.hashCode());
  }

  /**
   * Test {@link MarketDataPoint#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link MarketDataPoint#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean MarketDataPoint.equals(Object)"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    MarketDataPoint marketDataPoint = new MarketDataPoint("42", 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay());

    // Act and Assert
    assertNotEquals(marketDataPoint, new MarketDataPoint());
  }

  /**
   * Test {@link MarketDataPoint#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link MarketDataPoint#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean MarketDataPoint.equals(Object)"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    MarketDataPoint marketDataPoint = new MarketDataPoint();
    marketDataPoint.setId("42");

    // Act and Assert
    assertNotEquals(marketDataPoint, new MarketDataPoint());
  }

  /**
   * Test {@link MarketDataPoint#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link MarketDataPoint#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean MarketDataPoint.equals(Object)"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    MarketDataPoint marketDataPoint = new MarketDataPoint();
    marketDataPoint.setValue(10.0d);

    // Act and Assert
    assertNotEquals(marketDataPoint, new MarketDataPoint());
  }

  /**
   * Test {@link MarketDataPoint#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link MarketDataPoint#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean MarketDataPoint.equals(Object)"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new MarketDataPoint(), null);
  }

  /**
   * Test {@link MarketDataPoint#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link MarketDataPoint#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean MarketDataPoint.equals(Object)"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new MarketDataPoint(), "Different type to MarketDataPoint");
  }
}
