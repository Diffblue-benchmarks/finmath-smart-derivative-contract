package net.finmath.smartcontract.valuation.marketdata.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class MarketDataErrorsDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link MarketDataErrors#MarketDataErrors(boolean)}
   *   <li>{@link MarketDataErrors#setErrorMessage(String)}
   *   <li>{@link MarketDataErrors#setMissingDataPoints(List)}
   *   <li>{@link MarketDataErrors#toString()}
   *   <li>{@link MarketDataErrors#getErrorMessage()}
   *   <li>{@link MarketDataErrors#getMissingDataPoints()}
   *   <li>{@link MarketDataErrors#hasErrors()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MarketDataErrors.<init>(boolean)",
    "String MarketDataErrors.getErrorMessage()",
    "List MarketDataErrors.getMissingDataPoints()",
    "boolean MarketDataErrors.hasErrors()",
    "void MarketDataErrors.setErrorMessage(String)",
    "void MarketDataErrors.setMissingDataPoints(List)",
    "String MarketDataErrors.toString()"
  })
  void testGettersAndSetters() {
    // Arrange and Act
    MarketDataErrors actualMarketDataErrors = new MarketDataErrors(true);
    actualMarketDataErrors.setErrorMessage(
        "\"Invalid market data received. Expected format: JSON. Please check the data source for potential issues"
            + " or inconsistencies.\"");
    ArrayList<String> missingDataPoints = new ArrayList<>();
    actualMarketDataErrors.setMissingDataPoints(missingDataPoints);
    String actualToStringResult = actualMarketDataErrors.toString();
    String actualErrorMessage = actualMarketDataErrors.getErrorMessage();
    List<String> actualMissingDataPoints = actualMarketDataErrors.getMissingDataPoints();
    boolean actualHasErrorsResult = actualMarketDataErrors.hasErrors();

    // Assert
    assertEquals(
        "MarketDataErrors{hasErrors=true, missingDataPoints=[], errorMessage='\"Invalid market data received."
            + " Expected format: JSON. Please check the data source for potential issues or inconsistencies.\"'}",
        actualToStringResult);
    assertEquals(
        "\"Invalid market data received. Expected format: JSON. Please check the data source for potential issues"
            + " or inconsistencies.\"",
        actualErrorMessage);
    assertTrue(actualMissingDataPoints.isEmpty());
    assertTrue(actualHasErrorsResult);
    assertSame(missingDataPoints, actualMissingDataPoints);
  }

  /**
   * Test {@link MarketDataErrors#addMissingData(String)}.
   *
   * <p>Method under test: {@link MarketDataErrors#addMissingData(String)}
   */
  @Test
  @DisplayName("Test addMissingData(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void MarketDataErrors.addMissingData(String)"})
  void testAddMissingData() {
    // Arrange
    MarketDataErrors marketDataErrors = new MarketDataErrors(true);

    // Act
    marketDataErrors.addMissingData("\"EUR/USD FX Rate 2022-03-01\"");

    // Assert
    List<String> missingDataPoints = marketDataErrors.getMissingDataPoints();
    assertEquals(1, missingDataPoints.size());
    assertEquals("\"EUR/USD FX Rate 2022-03-01\"", missingDataPoints.get(0));
  }
}
