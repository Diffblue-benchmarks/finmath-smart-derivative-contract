package net.finmath.smartcontract.settlement;

import net.finmath.smartcontract.model.MarketDataList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Settlement class.
 */
class SettlementTest {

    private Settlement settlement;

    @BeforeEach
    void setUp() {
        settlement = new Settlement();
    }

    @Test
    void testSetAndGetTradeId() {
        String tradeId = "TRADE-12345";
        settlement.setTradeId(tradeId);
        assertEquals(tradeId, settlement.getTradeId());
    }

    @Test
    void testSetAndGetSettlementType() {
        settlement.setSettlementType(Settlement.SettlementType.INITIAL);
        assertEquals(Settlement.SettlementType.INITIAL, settlement.getSettlementType());

        settlement.setSettlementType(Settlement.SettlementType.REGULAR);
        assertEquals(Settlement.SettlementType.REGULAR, settlement.getSettlementType());

        settlement.setSettlementType(Settlement.SettlementType.TERMINAL);
        assertEquals(Settlement.SettlementType.TERMINAL, settlement.getSettlementType());
    }

    @Test
    void testSetAndGetCurrency() {
        String currency = "USD";
        settlement.setCurrency(currency);
        assertEquals(currency, settlement.getCurrency());
    }

    @Test
    void testSetAndGetMarginValue() {
        BigDecimal marginValue = new BigDecimal("1000.50");
        settlement.setMarginValue(marginValue);
        assertEquals(marginValue, settlement.getMarginValue());
    }

    @Test
    void testSetAndGetMarginLimits() {
        List<BigDecimal> marginLimits = Arrays.asList(
            new BigDecimal("500"),
            new BigDecimal("1000"),
            new BigDecimal("2000")
        );
        settlement.setMarginLimits(marginLimits);
        assertEquals(marginLimits, settlement.getMarginLimits());
    }

    @Test
    void testSetAndGetSettlementTime() {
        ZonedDateTime settlementTime = ZonedDateTime.now();
        settlement.setSettlementTime(settlementTime);
        assertEquals(settlementTime, settlement.getSettlementTime());
    }

    @Test
    void testSetAndGetSettlementNPV() {
        BigDecimal npv = new BigDecimal("5000.75");
        settlement.setSettlementNPV(npv);
        assertEquals(npv, settlement.getSettlementNPV());
    }

    @Test
    void testSetAndGetSettlementNPVPrevious() {
        BigDecimal npvPrevious = new BigDecimal("4800.25");
        settlement.setSettlementNPVPrevious(npvPrevious);
        assertEquals(npvPrevious, settlement.getSettlementNPVPrevious());
    }

    @Test
    void testSetAndGetSettlementTimeNext() {
        ZonedDateTime settlementTimeNext = ZonedDateTime.now().plusDays(1);
        settlement.setSettlementTimeNext(settlementTimeNext);
        assertEquals(settlementTimeNext, settlement.getSettlementTimeNext());
    }

    @Test
    void testSetAndGetSettlementNPVNext() {
        BigDecimal npvNext = new BigDecimal("5200.00");
        settlement.setSettlementNPVNext(npvNext);
        assertEquals(npvNext, settlement.getSettlementNPVNext());
    }

    @Test
    void testSetAndGetMarketData() {
        MarketDataList marketData = new MarketDataList();
        settlement.setMarketData(marketData);
        assertEquals(marketData, settlement.getMarketData());
    }

    @Test
    void testSetAndGetSettlementInfos() {
        List<SettlementInfo> settlementInfos = new ArrayList<>();
        settlementInfos.add(new SettlementInfo("risk_factor", new BigDecimal("0.5")));
        settlementInfos.add(new SettlementInfo("volatility", new BigDecimal("0.25")));

        settlement.setSettlementInfos(settlementInfos);
        assertEquals(settlementInfos, settlement.getSettlementInfos());
        assertEquals(2, settlement.getSettlementInfos().size());
    }

    @Test
    void testSettlementWithNullValues() {
        Settlement settlement = new Settlement();
        assertNull(settlement.getTradeId());
        assertNull(settlement.getSettlementType());
        assertNull(settlement.getCurrency());
        assertNull(settlement.getMarginValue());
        assertNull(settlement.getMarginLimits());
    }

    @Test
    void testSettlementTypeEnum() {
        assertNotNull(Settlement.SettlementType.INITIAL);
        assertNotNull(Settlement.SettlementType.REGULAR);
        assertNotNull(Settlement.SettlementType.TERMINAL);

        assertEquals("INITIAL", Settlement.SettlementType.INITIAL.name());
        assertEquals("REGULAR", Settlement.SettlementType.REGULAR.name());
        assertEquals("TERMINAL", Settlement.SettlementType.TERMINAL.name());
    }

    @Test
    void testCompleteSettlementObject() {
        settlement.setTradeId("TRADE-001");
        settlement.setSettlementType(Settlement.SettlementType.REGULAR);
        settlement.setCurrency("EUR");
        settlement.setMarginValue(new BigDecimal("1500.00"));
        settlement.setSettlementNPV(new BigDecimal("10000.00"));
        settlement.setSettlementNPVPrevious(new BigDecimal("9500.00"));
        settlement.setSettlementTime(ZonedDateTime.now());

        assertNotNull(settlement.getTradeId());
        assertNotNull(settlement.getSettlementType());
        assertNotNull(settlement.getCurrency());
        assertNotNull(settlement.getMarginValue());
        assertNotNull(settlement.getSettlementNPV());
        assertNotNull(settlement.getSettlementNPVPrevious());
        assertNotNull(settlement.getSettlementTime());
    }
}
