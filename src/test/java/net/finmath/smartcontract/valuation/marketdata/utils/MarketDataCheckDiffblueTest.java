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
    value.setProvider("42");

    SettlementTime value2 = new SettlementTime();
    value2.setType("42");
    value2.setValue("42");

    Settlement value3 = new Settlement();
    value3.setMarketdata(value);
    value3.setSettlementTime(value2);

    DataDocument value4 = new DataDocument();
    value4.setActualBuild(PlainSwapEditorHandler.DEFAULT_FIXED_PERIOD_MULTIPLIER);
    value4.setExpectedBuild(PlainSwapEditorHandler.DEFAULT_FIXED_PERIOD_MULTIPLIER);
    value4.setFpmlVersion("42");
    value4.setOnBehalfOf(new OnBehalfOf());
    value4.setOriginatingEvent(new OriginatingEvent());

    Underlying value5 = new Underlying();
    value5.setDataDocument(value4);

    Underlyings value6 = new Underlyings();
    value6.setUnderlying(value5);

    Artefact value7 = new Artefact();
    value7.setArtifactId("42");
    value7.setGroupId("42");
    value7.setVersion("42");

    Valuation value8 = new Valuation();
    value8.setArtefact(value7);

    Smartderivativecontract sdc = new Smartderivativecontract();
    sdc.setDltAddress("42");
    sdc.setDltTradeId("42");
    sdc.setParties(new Parties());
    sdc.setReceiverPartyID("42");
    sdc.setSettlement(value3);
    sdc.setSettlementCurrency("42");
    sdc.setTradeType("42");
    sdc.setUnderlyings(value6);
    sdc.setUniqueTradeIdentifier("42");
    sdc.setValuation(value8);

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
    value.setProvider("42");

    SettlementTime value2 = new SettlementTime();
    value2.setType("42");
    value2.setValue("42");

    Settlement value3 = new Settlement();
    value3.setMarketdata(value);
    value3.setSettlementTime(value2);

    DataDocument value4 = new DataDocument();
    value4.setActualBuild(PlainSwapEditorHandler.DEFAULT_FIXED_PERIOD_MULTIPLIER);
    value4.setExpectedBuild(PlainSwapEditorHandler.DEFAULT_FIXED_PERIOD_MULTIPLIER);
    value4.setFpmlVersion("42");
    value4.setOnBehalfOf(new OnBehalfOf());
    value4.setOriginatingEvent(new OriginatingEvent());

    Underlying value5 = new Underlying();
    value5.setDataDocument(value4);

    Underlyings value6 = new Underlyings();
    value6.setUnderlying(value5);

    Artefact value7 = new Artefact();
    value7.setArtifactId("42");
    value7.setGroupId("42");
    value7.setVersion("42");

    Valuation value8 = new Valuation();
    value8.setArtefact(value7);

    Smartderivativecontract sdc = new Smartderivativecontract();
    sdc.setDltAddress("42");
    sdc.setDltTradeId("42");
    sdc.setParties(new Parties());
    sdc.setReceiverPartyID("42");
    sdc.setSettlement(value3);
    sdc.setSettlementCurrency("42");
    sdc.setTradeType("42");
    sdc.setUnderlyings(value6);
    sdc.setUniqueTradeIdentifier("42");
    sdc.setValuation(value8);

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
    value.setProvider("42");

    SettlementTime value2 = new SettlementTime();
    value2.setType("42");
    value2.setValue("42");

    Settlement value3 = new Settlement();
    value3.setMarketdata(value);
    value3.setSettlementTime(value2);

    DataDocument value4 = new DataDocument();
    value4.setActualBuild(PlainSwapEditorHandler.DEFAULT_FIXED_PERIOD_MULTIPLIER);
    value4.setExpectedBuild(PlainSwapEditorHandler.DEFAULT_FIXED_PERIOD_MULTIPLIER);
    value4.setFpmlVersion("42");
    value4.setOnBehalfOf(new OnBehalfOf());
    value4.setOriginatingEvent(new OriginatingEvent());

    Underlying value5 = new Underlying();
    value5.setDataDocument(value4);

    Underlyings value6 = new Underlyings();
    value6.setUnderlying(value5);

    Artefact value7 = new Artefact();
    value7.setArtifactId("42");
    value7.setGroupId("42");
    value7.setVersion("42");

    Valuation value8 = new Valuation();
    value8.setArtefact(value7);

    Smartderivativecontract sdc = new Smartderivativecontract();
    sdc.setDltAddress("42");
    sdc.setDltTradeId("42");
    sdc.setParties(new Parties());
    sdc.setReceiverPartyID("42");
    sdc.setSettlement(value3);
    sdc.setSettlementCurrency("42");
    sdc.setTradeType("42");
    sdc.setUnderlyings(value6);
    sdc.setUniqueTradeIdentifier("42");
    sdc.setValuation(value8);

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
