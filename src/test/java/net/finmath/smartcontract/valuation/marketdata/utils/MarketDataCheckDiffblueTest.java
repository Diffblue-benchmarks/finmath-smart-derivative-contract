package net.finmath.smartcontract.valuation.marketdata.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import net.finmath.smartcontract.model.MarketDataList;
import net.finmath.smartcontract.product.xml.DataDocument;
import net.finmath.smartcontract.product.xml.OnBehalfOf;
import net.finmath.smartcontract.product.xml.OriginatingEvent;
import net.finmath.smartcontract.product.xml.PlainSwapEditorHandler;
import net.finmath.smartcontract.product.xml.Smartderivativecontract;
import net.finmath.smartcontract.valuation.marketdata.data.MarketDataPoint;
import org.junit.jupiter.api.Test;

class MarketDataCheckDiffblueTest {
  /**
   * Method under test:
   * {@link MarketDataCheck#checkMarketData(MarketDataList, Smartderivativecontract)}
   */
  @Test
  void testCheckMarketData() {
    // Arrange
    MarketDataList marketDataList = new MarketDataList();

    Smartderivativecontract.Settlement.Marketdata value = new Smartderivativecontract.Settlement.Marketdata();
    value.setMarketdataitems(new Smartderivativecontract.Settlement.Marketdata.Marketdataitems());
    value.setProvider("42");

    Smartderivativecontract.Settlement.SettlementTime value2 = new Smartderivativecontract.Settlement.SettlementTime();
    value2.setType("42");
    value2.setValue("42");

    Smartderivativecontract.Settlement value3 = new Smartderivativecontract.Settlement();
    value3.setMarketdata(value);
    value3.setSettlementTime(value2);

    DataDocument value4 = new DataDocument();
    value4.setActualBuild(PlainSwapEditorHandler.DEFAULT_FIXED_PERIOD_MULTIPLIER);
    value4.setExpectedBuild(PlainSwapEditorHandler.DEFAULT_FIXED_PERIOD_MULTIPLIER);
    value4.setFpmlVersion("42");
    value4.setOnBehalfOf(new OnBehalfOf());
    value4.setOriginatingEvent(new OriginatingEvent());

    Smartderivativecontract.Underlyings.Underlying value5 = new Smartderivativecontract.Underlyings.Underlying();
    value5.setDataDocument(value4);

    Smartderivativecontract.Underlyings value6 = new Smartderivativecontract.Underlyings();
    value6.setUnderlying(value5);

    Smartderivativecontract.Valuation.Artefact value7 = new Smartderivativecontract.Valuation.Artefact();
    value7.setArtifactId("42");
    value7.setGroupId("42");
    value7.setVersion("42");

    Smartderivativecontract.Valuation value8 = new Smartderivativecontract.Valuation();
    value8.setArtefact(value7);

    Smartderivativecontract sdc = new Smartderivativecontract();
    sdc.setDltAddress("42");
    sdc.setDltTradeId("42");
    sdc.setParties(new Smartderivativecontract.Parties());
    sdc.setReceiverPartyID("42");
    sdc.setSettlement(value3);
    sdc.setSettlementCurrency("42");
    sdc.setTradeType("42");
    sdc.setUnderlyings(value6);
    sdc.setUniqueTradeIdentifier("42");
    sdc.setValuation(value8);

    // Act
    MarketDataErrors actualCheckMarketDataResult = MarketDataCheck.checkMarketData(marketDataList, sdc);

    // Assert
    List<String> missingDataPoints = actualCheckMarketDataResult.getMissingDataPoints();
    assertEquals(1, missingDataPoints.size());
    assertEquals("all, no data provided", missingDataPoints.get(0));
    assertEquals("error in marketData service - no data generated", actualCheckMarketDataResult.getErrorMessage());
    assertTrue(actualCheckMarketDataResult.hasErrors());
  }

  /**
   * Method under test:
   * {@link MarketDataCheck#checkMarketData(MarketDataList, Smartderivativecontract)}
   */
  @Test
  void testCheckMarketData2() {
    // Arrange
    MarketDataList marketDataList = mock(MarketDataList.class);
    when(marketDataList.getPoints()).thenReturn(new ArrayList<>());

    Smartderivativecontract.Settlement.Marketdata value = new Smartderivativecontract.Settlement.Marketdata();
    value.setMarketdataitems(new Smartderivativecontract.Settlement.Marketdata.Marketdataitems());
    value.setProvider("42");

    Smartderivativecontract.Settlement.SettlementTime value2 = new Smartderivativecontract.Settlement.SettlementTime();
    value2.setType("42");
    value2.setValue("42");

    Smartderivativecontract.Settlement value3 = new Smartderivativecontract.Settlement();
    value3.setMarketdata(value);
    value3.setSettlementTime(value2);

    DataDocument value4 = new DataDocument();
    value4.setActualBuild(PlainSwapEditorHandler.DEFAULT_FIXED_PERIOD_MULTIPLIER);
    value4.setExpectedBuild(PlainSwapEditorHandler.DEFAULT_FIXED_PERIOD_MULTIPLIER);
    value4.setFpmlVersion("42");
    value4.setOnBehalfOf(new OnBehalfOf());
    value4.setOriginatingEvent(new OriginatingEvent());

    Smartderivativecontract.Underlyings.Underlying value5 = new Smartderivativecontract.Underlyings.Underlying();
    value5.setDataDocument(value4);

    Smartderivativecontract.Underlyings value6 = new Smartderivativecontract.Underlyings();
    value6.setUnderlying(value5);

    Smartderivativecontract.Valuation.Artefact value7 = new Smartderivativecontract.Valuation.Artefact();
    value7.setArtifactId("42");
    value7.setGroupId("42");
    value7.setVersion("42");

    Smartderivativecontract.Valuation value8 = new Smartderivativecontract.Valuation();
    value8.setArtefact(value7);

    Smartderivativecontract sdc = new Smartderivativecontract();
    sdc.setDltAddress("42");
    sdc.setDltTradeId("42");
    sdc.setParties(new Smartderivativecontract.Parties());
    sdc.setReceiverPartyID("42");
    sdc.setSettlement(value3);
    sdc.setSettlementCurrency("42");
    sdc.setTradeType("42");
    sdc.setUnderlyings(value6);
    sdc.setUniqueTradeIdentifier("42");
    sdc.setValuation(value8);

    // Act
    MarketDataErrors actualCheckMarketDataResult = MarketDataCheck.checkMarketData(marketDataList, sdc);

    // Assert
    verify(marketDataList).getPoints();
    List<String> missingDataPoints = actualCheckMarketDataResult.getMissingDataPoints();
    assertEquals(1, missingDataPoints.size());
    assertEquals("all, no data provided", missingDataPoints.get(0));
    assertEquals("error in marketData service - no data generated", actualCheckMarketDataResult.getErrorMessage());
    assertTrue(actualCheckMarketDataResult.hasErrors());
  }

  /**
   * Method under test:
   * {@link MarketDataCheck#checkMarketData(MarketDataList, Smartderivativecontract)}
   */
  @Test
  void testCheckMarketData3() {
    // Arrange
    ArrayList<MarketDataPoint> marketDataPointList = new ArrayList<>();
    marketDataPointList.add(new MarketDataPoint());
    MarketDataList marketDataList = mock(MarketDataList.class);
    when(marketDataList.getPoints()).thenReturn(marketDataPointList);

    Smartderivativecontract.Settlement.Marketdata value = new Smartderivativecontract.Settlement.Marketdata();
    value.setMarketdataitems(new Smartderivativecontract.Settlement.Marketdata.Marketdataitems());
    value.setProvider("42");

    Smartderivativecontract.Settlement.SettlementTime value2 = new Smartderivativecontract.Settlement.SettlementTime();
    value2.setType("42");
    value2.setValue("42");

    Smartderivativecontract.Settlement value3 = new Smartderivativecontract.Settlement();
    value3.setMarketdata(value);
    value3.setSettlementTime(value2);

    DataDocument value4 = new DataDocument();
    value4.setActualBuild(PlainSwapEditorHandler.DEFAULT_FIXED_PERIOD_MULTIPLIER);
    value4.setExpectedBuild(PlainSwapEditorHandler.DEFAULT_FIXED_PERIOD_MULTIPLIER);
    value4.setFpmlVersion("42");
    value4.setOnBehalfOf(new OnBehalfOf());
    value4.setOriginatingEvent(new OriginatingEvent());

    Smartderivativecontract.Underlyings.Underlying value5 = new Smartderivativecontract.Underlyings.Underlying();
    value5.setDataDocument(value4);

    Smartderivativecontract.Underlyings value6 = new Smartderivativecontract.Underlyings();
    value6.setUnderlying(value5);

    Smartderivativecontract.Valuation.Artefact value7 = new Smartderivativecontract.Valuation.Artefact();
    value7.setArtifactId("42");
    value7.setGroupId("42");
    value7.setVersion("42");

    Smartderivativecontract.Valuation value8 = new Smartderivativecontract.Valuation();
    value8.setArtefact(value7);

    Smartderivativecontract sdc = new Smartderivativecontract();
    sdc.setDltAddress("42");
    sdc.setDltTradeId("42");
    sdc.setParties(new Smartderivativecontract.Parties());
    sdc.setReceiverPartyID("42");
    sdc.setSettlement(value3);
    sdc.setSettlementCurrency("42");
    sdc.setTradeType("42");
    sdc.setUnderlyings(value6);
    sdc.setUniqueTradeIdentifier("42");
    sdc.setValuation(value8);

    // Act
    MarketDataErrors actualCheckMarketDataResult = MarketDataCheck.checkMarketData(marketDataList, sdc);

    // Assert
    verify(marketDataList).getPoints();
    assertNull(actualCheckMarketDataResult.getErrorMessage());
    assertFalse(actualCheckMarketDataResult.hasErrors());
    assertTrue(actualCheckMarketDataResult.getMissingDataPoints().isEmpty());
  }
}
