package net.finmath.smartcontract.fixtures;

import net.finmath.smartcontract.model.*;
import net.finmath.smartcontract.settlement.Settlement;
import net.finmath.smartcontract.valuation.marketdata.data.MarketDataPoint;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for TestDataFixtures to ensure test data is created correctly.
 */
class TestDataFixturesTest {

    @Test
    void testCreateSampleMarketDataPoint() {
        MarketDataPoint point = TestDataFixtures.createSampleMarketDataPoint();

        assertNotNull(point);
        assertEquals(TestDataFixtures.SAMPLE_MARKET_DATA_ID, point.getId());
        assertEquals(TestDataFixtures.SAMPLE_MARKET_DATA_VALUE, point.getValue());
        assertNotNull(point.getTimeStamp());
    }

    @Test
    void testCreateSampleMarketDataList() {
        MarketDataList marketDataList = TestDataFixtures.createSampleMarketDataList();

        assertNotNull(marketDataList);
        assertNotNull(marketDataList.getPoints());
        assertEquals(3, marketDataList.getSize());
        assertNotNull(marketDataList.getRequestTimeStamp());
    }

    @Test
    void testCreateSampleSettlement() {
        Settlement settlement = TestDataFixtures.createSampleSettlement();

        assertNotNull(settlement);
        assertEquals(TestDataFixtures.SAMPLE_TRADE_ID, settlement.getTradeId());
        assertEquals(Settlement.SettlementType.REGULAR, settlement.getSettlementType());
        assertEquals(TestDataFixtures.SAMPLE_CURRENCY, settlement.getCurrency());
        assertEquals(TestDataFixtures.SAMPLE_MARGIN_VALUE, settlement.getMarginValue());
        assertNotNull(settlement.getMarginLimits());
        assertNotNull(settlement.getSettlementTime());
        assertNotNull(settlement.getMarketData());
        assertNotNull(settlement.getSettlementInfos());
        assertEquals(2, settlement.getSettlementInfos().size());
    }

    @Test
    void testCreateSampleInitialSettlementRequest() {
        InitialSettlementRequest request = TestDataFixtures.createSampleInitialSettlementRequest();

        assertNotNull(request);
        assertNotNull(request.getTradeData());
    }

    @Test
    void testCreateSampleRegularSettlementRequest() {
        RegularSettlementRequest request = TestDataFixtures.createSampleRegularSettlementRequest();

        assertNotNull(request);
        assertNotNull(request.getTradeData());
        assertNotNull(request.getSettlementLast());
    }

    @Test
    void testCreateSampleValueRequest() {
        ValueRequest request = TestDataFixtures.createSampleValueRequest();

        assertNotNull(request);
        assertNotNull(request.getMarketData());
        assertNotNull(request.getTradeData());
        assertNotNull(request.getValuationDate());
    }

    @Test
    void testCreateSampleMarginRequest() {
        MarginRequest request = TestDataFixtures.createSampleMarginRequest();

        assertNotNull(request);
        assertNotNull(request.getMarketDataStart());
        assertNotNull(request.getMarketDataEnd());
        assertNotNull(request.getTradeData());
    }

    @Test
    void testCreateSampleValueResult() {
        ValueResult result = TestDataFixtures.createSampleValueResult();

        assertNotNull(result);
        assertNotNull(result.getValue());
        assertEquals("EUR", result.getCurrency());
        assertNotNull(result.getValuationDate());
    }

    @Test
    void testCreateSampleMarginResult() {
        MarginResult result = TestDataFixtures.createSampleMarginResult();

        assertNotNull(result);
        assertNotNull(result.getValue());
        assertEquals("EUR", result.getCurrency());
        assertNotNull(result.getValuationDate());
    }

    @Test
    void testCreateSampleInitialSettlementResult() {
        InitialSettlementResult result = TestDataFixtures.createSampleInitialSettlementResult();

        assertNotNull(result);
        assertNotNull(result.getGeneratedInitialSettlement());
        assertEquals("EUR", result.getCurrency());
        assertEquals(BigDecimal.ZERO, result.getMarginValue());
    }

    @Test
    void testCreateSampleRegularSettlementResult() {
        RegularSettlementResult result = TestDataFixtures.createSampleRegularSettlementResult();

        assertNotNull(result);
        assertNotNull(result.getGeneratedRegularSettlement());
        assertEquals("EUR", result.getCurrency());
        assertNotNull(result.getMarginValue());
    }

    @Test
    void testAllFixturesAreReusable() {
        // Create fixtures multiple times to ensure they're independent
        MarketDataPoint point1 = TestDataFixtures.createSampleMarketDataPoint();
        MarketDataPoint point2 = TestDataFixtures.createSampleMarketDataPoint();

        assertNotNull(point1);
        assertNotNull(point2);
        // Each call creates a new instance
        assertNotSame(point1, point2);
    }

    @Test
    void testMarketDataListContainsMultiplePoints() {
        MarketDataList list = TestDataFixtures.createSampleMarketDataList();

        assertTrue(list.getSize() > 0);
        assertTrue(list.getPoints().stream().anyMatch(p -> p.getId().equals("EUR-SWAP-5Y")));
        assertTrue(list.getPoints().stream().anyMatch(p -> p.getId().equals("EUR-SWAP-10Y")));
        assertTrue(list.getPoints().stream().anyMatch(p -> p.getId().equals("EUR-OIS-1Y")));
    }

    @Test
    void testSettlementHasCompleteData() {
        Settlement settlement = TestDataFixtures.createSampleSettlement();

        assertNotNull(settlement.getTradeId());
        assertNotNull(settlement.getSettlementType());
        assertNotNull(settlement.getCurrency());
        assertNotNull(settlement.getMarginValue());
        assertNotNull(settlement.getSettlementNPV());
        assertNotNull(settlement.getSettlementNPVPrevious());
        assertNotNull(settlement.getSettlementNPVNext());
    }
}
