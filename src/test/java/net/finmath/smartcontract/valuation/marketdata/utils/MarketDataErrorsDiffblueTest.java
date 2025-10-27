package net.finmath.smartcontract.valuation.marketdata.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import net.finmath.smartcontract.model.MarketDataList;
import net.finmath.smartcontract.product.xml.Smartderivativecontract;
import org.junit.jupiter.api.Test;

class MarketDataErrorsDiffblueTest {
  /**
   * Method under test: {@link MarketDataErrors#addMissingData(String)}
   */
  @Test
  void testAddMissingData() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    MarketDataErrors marketDataErrors = new MarketDataErrors(true);

    // Act
    marketDataErrors.addMissingData("Missing Data Point");

    // Assert
    List<String> missingDataPoints = marketDataErrors.getMissingDataPoints();
    assertEquals(1, missingDataPoints.size());
    assertEquals("Missing Data Point", missingDataPoints.get(0));
  }

  /**
   * Method under test: {@link MarketDataErrors#addMissingData(String)}
   */
  @Test
  void testAddMissingData2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    MarketDataList marketDataList = mock(MarketDataList.class);
    when(marketDataList.getPoints()).thenReturn(new ArrayList<>());
    MarketDataErrors checkMarketDataResult = MarketDataCheck.checkMarketData(marketDataList,
        new Smartderivativecontract());

    // Act
    checkMarketDataResult.addMissingData("Missing Data Point");

    // Assert
    verify(marketDataList).getPoints();
    List<String> missingDataPoints = checkMarketDataResult.getMissingDataPoints();
    assertEquals(2, missingDataPoints.size());
    assertEquals("Missing Data Point", missingDataPoints.get(1));
  }

  /**
   * Methods under test:
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
  void testGettersAndSetters() {
    // Arrange and Act
    MarketDataErrors actualMarketDataErrors = new MarketDataErrors(true);
    actualMarketDataErrors.setErrorMessage("An error occurred");
    ArrayList<String> missingDataPoints = new ArrayList<>();
    actualMarketDataErrors.setMissingDataPoints(missingDataPoints);
    String actualToStringResult = actualMarketDataErrors.toString();
    String actualErrorMessage = actualMarketDataErrors.getErrorMessage();
    List<String> actualMissingDataPoints = actualMarketDataErrors.getMissingDataPoints();
    boolean actualHasErrorsResult = actualMarketDataErrors.hasErrors();

    // Assert that nothing has changed
    assertEquals("An error occurred", actualErrorMessage);
    assertEquals("MarketDataErrors{hasErrors=true, missingDataPoints=[], errorMessage='An error occurred'}",
        actualToStringResult);
    assertTrue(actualMissingDataPoints.isEmpty());
    assertTrue(actualHasErrorsResult);
    assertSame(missingDataPoints, actualMissingDataPoints);
  }
}
