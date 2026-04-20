package net.finmath.smartcontract.settlement;

import net.finmath.smartcontract.model.MarketDataList;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class SettlementTest {

	@Test
	void testTradeId() {
		Settlement settlement = new Settlement();
		assertNull(settlement.getTradeId());
		settlement.setTradeId("TRADE-001");
		assertEquals("TRADE-001", settlement.getTradeId());
	}

	@Test
	void testSettlementType() {
		Settlement settlement = new Settlement();
		assertNull(settlement.getSettlementType());
		settlement.setSettlementType(Settlement.SettlementType.REGULAR);
		assertEquals(Settlement.SettlementType.REGULAR, settlement.getSettlementType());
	}

	@Test
	void testCurrency() {
		Settlement settlement = new Settlement();
		assertNull(settlement.getCurrency());
		settlement.setCurrency("EUR");
		assertEquals("EUR", settlement.getCurrency());
	}

	@Test
	void testMarginValue() {
		Settlement settlement = new Settlement();
		assertNull(settlement.getMarginValue());
		BigDecimal value = new BigDecimal("1234.56");
		settlement.setMarginValue(value);
		assertEquals(value, settlement.getMarginValue());
	}

	@Test
	void testMarginLimits() {
		Settlement settlement = new Settlement();
		assertNull(settlement.getMarginLimits());
		List<BigDecimal> limits = List.of(new BigDecimal("100"), new BigDecimal("200"));
		settlement.setMarginLimits(limits);
		assertEquals(limits, settlement.getMarginLimits());
	}

	@Test
	void testSettlementTime() {
		Settlement settlement = new Settlement();
		assertNull(settlement.getSettlementTime());
		ZonedDateTime time = ZonedDateTime.now();
		settlement.setSettlementTime(time);
		assertEquals(time, settlement.getSettlementTime());
	}

	@Test
	void testSettlementNPV() {
		Settlement settlement = new Settlement();
		assertNull(settlement.getSettlementNPV());
		BigDecimal npv = new BigDecimal("5000.00");
		settlement.setSettlementNPV(npv);
		assertEquals(npv, settlement.getSettlementNPV());
	}

	@Test
	void testSettlementNPVPrevious() {
		Settlement settlement = new Settlement();
		assertNull(settlement.getSettlementNPVPrevious());
		BigDecimal npvPrev = new BigDecimal("4500.00");
		settlement.setSettlementNPVPrevious(npvPrev);
		assertEquals(npvPrev, settlement.getSettlementNPVPrevious());
	}

	@Test
	void testSettlementTimeNext() {
		Settlement settlement = new Settlement();
		assertNull(settlement.getSettlementTimeNext());
		ZonedDateTime timeNext = ZonedDateTime.now().plusDays(1);
		settlement.setSettlementTimeNext(timeNext);
		assertEquals(timeNext, settlement.getSettlementTimeNext());
	}

	@Test
	void testSettlementNPVNext() {
		Settlement settlement = new Settlement();
		assertNull(settlement.getSettlementNPVNext());
		BigDecimal npvNext = new BigDecimal("5500.00");
		settlement.setSettlementNPVNext(npvNext);
		assertEquals(npvNext, settlement.getSettlementNPVNext());
	}

	@Test
	void testMarketData() {
		Settlement settlement = new Settlement();
		assertNull(settlement.getMarketData());
		MarketDataList marketData = new MarketDataList();
		settlement.setMarketData(marketData);
		assertEquals(marketData, settlement.getMarketData());
	}

	@Test
	void testSettlementInfos() {
		Settlement settlement = new Settlement();
		assertNull(settlement.getSettlementInfos());
		List<SettlementInfo> infos = List.of();
		settlement.setSettlementInfos(infos);
		assertEquals(infos, settlement.getSettlementInfos());
	}
}
