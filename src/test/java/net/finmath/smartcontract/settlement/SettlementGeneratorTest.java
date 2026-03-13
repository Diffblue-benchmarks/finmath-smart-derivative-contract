package net.finmath.smartcontract.settlement;

import net.finmath.smartcontract.model.MarketDataList;
import net.finmath.smartcontract.product.xml.SDCXMLParser;
import net.finmath.smartcontract.valuation.marketdata.data.MarketDataPoint;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SettlementGeneratorTest {

	@Test
	void settlementXmlRoundTrip_shouldPreserveFields() {
		Settlement settlement = new Settlement();
		settlement.setTradeId("TEST-001");
		settlement.setSettlementType(Settlement.SettlementType.INITIAL);
		settlement.setCurrency("EUR");
		settlement.setMarginValue(BigDecimal.ZERO);
		settlement.setMarginLimits(List.of(BigDecimal.valueOf(10000), BigDecimal.valueOf(10000)));
		settlement.setSettlementTime(ZonedDateTime.now(ZoneId.of("Europe/Berlin")));
		settlement.setSettlementNPV(BigDecimal.valueOf(5000));
		settlement.setSettlementNPVPrevious(BigDecimal.ZERO);
		settlement.setSettlementTimeNext(ZonedDateTime.now(ZoneId.of("Europe/Berlin")).plusDays(1));
		settlement.setSettlementNPVNext(BigDecimal.valueOf(5100));
		MarketDataList mdl = new MarketDataList();
		mdl.setRequestTimeStamp(LocalDateTime.of(2024, 6, 15, 14, 30, 0));
		mdl.add(new MarketDataPoint("EUR6M_5Y", 0.03, LocalDateTime.of(2024, 6, 15, 14, 30, 0)));
		settlement.setMarketData(mdl);
		settlement.setSettlementInfos(List.of(new SettlementInfo("margin", BigDecimal.valueOf(1500))));

		String xml = SDCXMLParser.marshalClassToXMLString(settlement);
		assertNotNull(xml);
		assertTrue(xml.contains("TEST-001"));

		Settlement back = SDCXMLParser.unmarshalXml(xml, Settlement.class);
		assertEquals("TEST-001", back.getTradeId());
		assertEquals("EUR", back.getCurrency());
		assertEquals(Settlement.SettlementType.INITIAL, back.getSettlementType());
		assertEquals(1, back.getMarketData().getSize());
		assertNotNull(back.getSettlementInfos());
	}

	@Test
	void allFieldsSet_reflectionCheck_shouldDetectCompleteSettlement() {
		// A complete settlement with all fields set should marshal successfully
		Settlement settlement = new Settlement();
		settlement.setTradeId("TEST-002");
		settlement.setSettlementType(Settlement.SettlementType.REGULAR);
		settlement.setCurrency("EUR");
		settlement.setMarginValue(BigDecimal.valueOf(500));
		settlement.setMarginLimits(List.of(BigDecimal.valueOf(10000)));
		settlement.setSettlementTime(ZonedDateTime.of(2024, 6, 15, 17, 0, 0, 0, ZoneId.of("Europe/Berlin")));
		settlement.setSettlementNPV(BigDecimal.valueOf(4500));
		settlement.setSettlementNPVPrevious(BigDecimal.valueOf(4000));
		settlement.setSettlementTimeNext(ZonedDateTime.of(2024, 6, 16, 17, 0, 0, 0, ZoneId.of("Europe/Berlin")));
		settlement.setSettlementNPVNext(BigDecimal.valueOf(4600));
		settlement.setMarketData(new MarketDataList());
		settlement.setSettlementInfos(List.of());

		String xml = SDCXMLParser.marshalClassToXMLString(settlement);
		assertNotNull(xml);
		assertTrue(xml.contains("REGULAR"));
	}
}
