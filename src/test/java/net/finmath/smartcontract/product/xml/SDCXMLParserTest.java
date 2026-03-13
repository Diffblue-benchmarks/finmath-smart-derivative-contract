package net.finmath.smartcontract.product.xml;

import net.finmath.smartcontract.model.MarketDataList;
import net.finmath.smartcontract.model.SDCException;
import net.finmath.smartcontract.settlement.Settlement;
import net.finmath.smartcontract.valuation.marketdata.data.MarketDataPoint;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SDCXMLParserTest {

	@Test
	void marshalAndUnmarshal_MarketDataList_shouldRoundTrip() {
		MarketDataList original = new MarketDataList();
		original.setRequestTimeStamp(LocalDateTime.of(2024, 6, 15, 14, 30, 0));
		original.add(new MarketDataPoint("EUR6M_5Y", 0.03, LocalDateTime.of(2024, 6, 15, 14, 30, 0)));

		String xml = SDCXMLParser.marshalClassToXMLString(original);
		assertNotNull(xml);
		assertTrue(xml.contains("EUR6M_5Y"));

		MarketDataList unmarshalled = SDCXMLParser.unmarshalXml(xml, MarketDataList.class);
		assertNotNull(unmarshalled);
		assertEquals(1, unmarshalled.getSize());
		assertEquals("EUR6M_5Y", unmarshalled.getPoints().get(0).getId());
	}

	@Test
	void marshalAndUnmarshal_Settlement_shouldRoundTrip() {
		Settlement original = new Settlement();
		original.setTradeId("TRADE-001");
		original.setSettlementType(Settlement.SettlementType.REGULAR);
		original.setCurrency("EUR");
		original.setMarginValue(BigDecimal.valueOf(1500));
		original.setMarginLimits(List.of(BigDecimal.valueOf(10000), BigDecimal.valueOf(10000)));
		original.setSettlementTime(ZonedDateTime.of(2024, 6, 15, 17, 0, 0, 0, ZoneId.of("Europe/Berlin")));
		original.setSettlementNPV(BigDecimal.valueOf(5000));
		original.setSettlementNPVPrevious(BigDecimal.valueOf(3500));
		original.setSettlementTimeNext(ZonedDateTime.of(2024, 6, 16, 17, 0, 0, 0, ZoneId.of("Europe/Berlin")));
		original.setSettlementNPVNext(BigDecimal.valueOf(5100));
		original.setMarketData(new MarketDataList());
		original.setSettlementInfos(List.of());

		String xml = SDCXMLParser.marshalClassToXMLString(original);
		assertNotNull(xml);
		assertTrue(xml.contains("TRADE-001"));

		Settlement unmarshalled = SDCXMLParser.unmarshalXml(xml, Settlement.class);
		assertNotNull(unmarshalled);
		assertEquals("TRADE-001", unmarshalled.getTradeId());
		assertEquals(Settlement.SettlementType.REGULAR, unmarshalled.getSettlementType());
		assertEquals("EUR", unmarshalled.getCurrency());
	}

	@Test
	void unmarshalXml_shouldThrowSDCException_forInvalidXml() {
		assertThrows(SDCException.class,
				() -> SDCXMLParser.unmarshalXml("invalid xml", MarketDataList.class));
	}

	@Test
	void unmarshalXml_shouldParseMarketDataFromResource() {
		String xml = """
				<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
				<marketDataList>
				    <requestTimeStamp>20240615-170000</requestTimeStamp>
				    <item>
				        <id>ESTRSWP1Y</id>
				        <value>0.025</value>
				        <timeStamp>20240615-170000</timeStamp>
				    </item>
				</marketDataList>
				""";
		MarketDataList mdl = SDCXMLParser.unmarshalXml(xml, MarketDataList.class);
		assertEquals(1, mdl.getSize());
		assertEquals("ESTRSWP1Y", mdl.getPoints().get(0).getId());
		assertEquals(0.025, mdl.getPoints().get(0).getValue());
	}
}
