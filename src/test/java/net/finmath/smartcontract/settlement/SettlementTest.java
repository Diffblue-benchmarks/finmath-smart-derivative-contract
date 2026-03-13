package net.finmath.smartcontract.settlement;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SettlementTest {

	@Test
	void settersAndGetters_shouldWorkCorrectly() {
		Settlement settlement = new Settlement();

		settlement.setTradeId("TRADE-001");
		settlement.setSettlementType(Settlement.SettlementType.REGULAR);
		settlement.setCurrency("EUR");
		settlement.setMarginValue(BigDecimal.valueOf(1500.00));
		settlement.setMarginLimits(List.of(BigDecimal.valueOf(10000), BigDecimal.valueOf(10000)));

		ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Europe/Berlin"));
		settlement.setSettlementTime(now);
		settlement.setSettlementNPV(BigDecimal.valueOf(5000));
		settlement.setSettlementNPVPrevious(BigDecimal.valueOf(3500));

		ZonedDateTime nextTime = now.plusDays(1);
		settlement.setSettlementTimeNext(nextTime);
		settlement.setSettlementNPVNext(BigDecimal.valueOf(5100));

		assertEquals("TRADE-001", settlement.getTradeId());
		assertEquals(Settlement.SettlementType.REGULAR, settlement.getSettlementType());
		assertEquals("EUR", settlement.getCurrency());
		assertEquals(BigDecimal.valueOf(1500.00), settlement.getMarginValue());
		assertEquals(2, settlement.getMarginLimits().size());
		assertEquals(now, settlement.getSettlementTime());
		assertEquals(BigDecimal.valueOf(5000), settlement.getSettlementNPV());
		assertEquals(BigDecimal.valueOf(3500), settlement.getSettlementNPVPrevious());
		assertEquals(nextTime, settlement.getSettlementTimeNext());
		assertEquals(BigDecimal.valueOf(5100), settlement.getSettlementNPVNext());
	}

	@Test
	void settlementType_enum_shouldContainAllValues() {
		Settlement.SettlementType[] types = Settlement.SettlementType.values();
		assertEquals(3, types.length);
		assertEquals(Settlement.SettlementType.INITIAL, Settlement.SettlementType.valueOf("INITIAL"));
		assertEquals(Settlement.SettlementType.REGULAR, Settlement.SettlementType.valueOf("REGULAR"));
		assertEquals(Settlement.SettlementType.TERMINAL, Settlement.SettlementType.valueOf("TERMINAL"));
	}

	@Test
	void settlementInfos_shouldBeSettableAndGettable() {
		Settlement settlement = new Settlement();
		SettlementInfo info = new SettlementInfo("key1", BigDecimal.ONE);
		settlement.setSettlementInfos(List.of(info));
		assertEquals(1, settlement.getSettlementInfos().size());
		assertEquals("key1", settlement.getSettlementInfos().get(0).getKey());
	}
}
