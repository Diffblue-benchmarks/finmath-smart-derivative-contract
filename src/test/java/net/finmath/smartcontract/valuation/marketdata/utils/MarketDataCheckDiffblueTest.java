package net.finmath.smartcontract.valuation.marketdata.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import net.finmath.smartcontract.model.MarketDataList;
import net.finmath.smartcontract.product.xml.DataDocument;
import net.finmath.smartcontract.product.xml.OnBehalfOf;
import net.finmath.smartcontract.product.xml.OriginatingEvent;
import net.finmath.smartcontract.product.xml.PlainSwapEditorHandler;
import net.finmath.smartcontract.product.xml.Smartderivativecontract;
import net.finmath.smartcontract.product.xml.Smartderivativecontract.Parties;
import net.finmath.smartcontract.product.xml.Smartderivativecontract.Settlement;
import net.finmath.smartcontract.product.xml.Smartderivativecontract.Settlement.Marketdata;
import net.finmath.smartcontract.product.xml.Smartderivativecontract.Settlement.Marketdata.Marketdataitems;
import net.finmath.smartcontract.product.xml.Smartderivativecontract.Settlement.SettlementTime;
import net.finmath.smartcontract.product.xml.Smartderivativecontract.Underlyings;
import net.finmath.smartcontract.product.xml.Smartderivativecontract.Underlyings.Underlying;
import net.finmath.smartcontract.product.xml.Smartderivativecontract.Valuation;
import net.finmath.smartcontract.product.xml.Smartderivativecontract.Valuation.Artefact;
import net.finmath.smartcontract.valuation.marketdata.data.MarketDataPoint;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class MarketDataCheckDiffblueTest {
  /**
   * Test {@link MarketDataCheck#checkMarketData(MarketDataList, Smartderivativecontract)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.
   *   <li>Then return MissingDataPoints size is one.
   * </ul>
   *
   * <p>Method under test: {@link MarketDataCheck#checkMarketData(MarketDataList,
   * Smartderivativecontract)}
   */
  @Test
  @DisplayName(
      "Test checkMarketData(MarketDataList, Smartderivativecontract); given ArrayList(); then return MissingDataPoints size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MarketDataErrors MarketDataCheck.checkMarketData(MarketDataList, Smartderivativecontract)"
  })
  void testCheckMarketData_givenArrayList_thenReturnMissingDataPointsSizeIsOne() {
    // Arrange
    MarketDataList marketDataList = mock(MarketDataList.class);
    when(marketDataList.getPoints()).thenReturn(new ArrayList<>());

    Marketdata value = new Marketdata();
    value.setMarketdataitems(new Marketdataitems());

    Settlement value2 = new Settlement();
    value2.setMarketdata(value);
    value2.setSettlementTime(new SettlementTime());

    DataDocument value3 = new DataDocument();
    value3.setActualBuild(PlainSwapEditorHandler.DEFAULT_FIXED_PERIOD_MULTIPLIER);
    value3.setExpectedBuild(PlainSwapEditorHandler.DEFAULT_FIXED_PERIOD_MULTIPLIER);
    value3.setFpmlVersion("42");
    value3.setOnBehalfOf(new OnBehalfOf());
    value3.setOriginatingEvent(new OriginatingEvent());

    Underlying value4 = new Underlying();
    value4.setDataDocument(value3);

    Underlyings value5 = new Underlyings();
    value5.setUnderlying(value4);

    Valuation value6 = new Valuation();
    value6.setArtefact(new Artefact());

    Smartderivativecontract sdc = new Smartderivativecontract();
    sdc.setDltAddress("42");
    sdc.setDltTradeId("42");
    sdc.setParties(new Parties());
    sdc.setReceiverPartyID("42");
    sdc.setSettlement(value2);
    sdc.setSettlementCurrency("42");
    sdc.setTradeType("42");
    sdc.setUnderlyings(value5);
    sdc.setUniqueTradeIdentifier("42");
    sdc.setValuation(value6);

    // Act
    MarketDataErrors actualCheckMarketDataResult =
        MarketDataCheck.checkMarketData(marketDataList, sdc);

    // Assert
    verify(marketDataList).getPoints();
    List<String> missingDataPoints = actualCheckMarketDataResult.getMissingDataPoints();
    assertEquals(1, missingDataPoints.size());
    assertEquals("all, no data provided", missingDataPoints.get(0));
    assertEquals(
        "error in marketData service - no data generated",
        actualCheckMarketDataResult.getErrorMessage());
    assertTrue(actualCheckMarketDataResult.hasErrors());
  }

  /**
   * Test {@link MarketDataCheck#checkMarketData(MarketDataList, Smartderivativecontract)}.
   *
   * <ul>
   *   <li>Then return ErrorMessage is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link MarketDataCheck#checkMarketData(MarketDataList,
   * Smartderivativecontract)}
   */
  @Test
  @DisplayName(
      "Test checkMarketData(MarketDataList, Smartderivativecontract); then return ErrorMessage is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MarketDataErrors MarketDataCheck.checkMarketData(MarketDataList, Smartderivativecontract)"
  })
  void testCheckMarketData_thenReturnErrorMessageIsNull() {
    // Arrange
    ArrayList<MarketDataPoint> marketDataPointList = new ArrayList<>();
    marketDataPointList.add(new MarketDataPoint());

    MarketDataList marketDataList = mock(MarketDataList.class);
    when(marketDataList.getPoints()).thenReturn(marketDataPointList);

    Marketdata value = new Marketdata();
    value.setMarketdataitems(new Marketdataitems());

    Settlement value2 = new Settlement();
    value2.setMarketdata(value);
    value2.setSettlementTime(new SettlementTime());

    DataDocument value3 = new DataDocument();
    value3.setActualBuild(PlainSwapEditorHandler.DEFAULT_FIXED_PERIOD_MULTIPLIER);
    value3.setExpectedBuild(PlainSwapEditorHandler.DEFAULT_FIXED_PERIOD_MULTIPLIER);
    value3.setFpmlVersion("42");
    value3.setOnBehalfOf(new OnBehalfOf());
    value3.setOriginatingEvent(new OriginatingEvent());

    Underlying value4 = new Underlying();
    value4.setDataDocument(value3);

    Underlyings value5 = new Underlyings();
    value5.setUnderlying(value4);

    Valuation value6 = new Valuation();
    value6.setArtefact(new Artefact());

    Smartderivativecontract sdc = new Smartderivativecontract();
    sdc.setDltAddress("42");
    sdc.setDltTradeId("42");
    sdc.setParties(new Parties());
    sdc.setReceiverPartyID("42");
    sdc.setSettlement(value2);
    sdc.setSettlementCurrency("42");
    sdc.setTradeType("42");
    sdc.setUnderlyings(value5);
    sdc.setUniqueTradeIdentifier("42");
    sdc.setValuation(value6);

    // Act
    MarketDataErrors actualCheckMarketDataResult =
        MarketDataCheck.checkMarketData(marketDataList, sdc);

    // Assert
    verify(marketDataList).getPoints();
    assertNull(actualCheckMarketDataResult.getErrorMessage());
    assertFalse(actualCheckMarketDataResult.hasErrors());
    assertTrue(actualCheckMarketDataResult.getMissingDataPoints().isEmpty());
  }

  /**
   * Test {@link MarketDataCheck#checkMarketData(MarketDataList, Smartderivativecontract)}.
   *
   * <ul>
   *   <li>When {@link MarketDataList} (default constructor).
   *   <li>Then return MissingDataPoints size is one.
   * </ul>
   *
   * <p>Method under test: {@link MarketDataCheck#checkMarketData(MarketDataList,
   * Smartderivativecontract)}
   */
  @Test
  @DisplayName(
      "Test checkMarketData(MarketDataList, Smartderivativecontract); when MarketDataList (default constructor); then return MissingDataPoints size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MarketDataErrors MarketDataCheck.checkMarketData(MarketDataList, Smartderivativecontract)"
  })
  void testCheckMarketData_whenMarketDataList_thenReturnMissingDataPointsSizeIsOne() {
    // Arrange
    MarketDataList marketDataList = new MarketDataList();

    Marketdata value = new Marketdata();
    value.setMarketdataitems(new Marketdataitems());

    Settlement value2 = new Settlement();
    value2.setMarketdata(value);
    value2.setSettlementTime(new SettlementTime());

    DataDocument value3 = new DataDocument();
    value3.setActualBuild(PlainSwapEditorHandler.DEFAULT_FIXED_PERIOD_MULTIPLIER);
    value3.setExpectedBuild(PlainSwapEditorHandler.DEFAULT_FIXED_PERIOD_MULTIPLIER);
    value3.setFpmlVersion("42");
    value3.setOnBehalfOf(new OnBehalfOf());
    value3.setOriginatingEvent(new OriginatingEvent());

    Underlying value4 = new Underlying();
    value4.setDataDocument(value3);

    Underlyings value5 = new Underlyings();
    value5.setUnderlying(value4);

    Valuation value6 = new Valuation();
    value6.setArtefact(new Artefact());

    Smartderivativecontract sdc = new Smartderivativecontract();
    sdc.setDltAddress("42");
    sdc.setDltTradeId("42");
    sdc.setParties(new Parties());
    sdc.setReceiverPartyID("42");
    sdc.setSettlement(value2);
    sdc.setSettlementCurrency("42");
    sdc.setTradeType("42");
    sdc.setUnderlyings(value5);
    sdc.setUniqueTradeIdentifier("42");
    sdc.setValuation(value6);

    // Act
    MarketDataErrors actualCheckMarketDataResult =
        MarketDataCheck.checkMarketData(marketDataList, sdc);

    // Assert
    List<String> missingDataPoints = actualCheckMarketDataResult.getMissingDataPoints();
    assertEquals(1, missingDataPoints.size());
    assertEquals("all, no data provided", missingDataPoints.get(0));
    assertEquals(
        "error in marketData service - no data generated",
        actualCheckMarketDataResult.getErrorMessage());
    assertTrue(actualCheckMarketDataResult.hasErrors());
  }
}
