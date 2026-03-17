package net.finmath.smartcontract.settlement;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import net.finmath.smartcontract.model.MarketDataList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

class SettlementTests {

	@Test
	void testGenerateSettlementXML() throws Exception{

		String tradeID = "SDCTestTrade1";
		Settlement.SettlementType  type = Settlement.SettlementType.REGULAR;
		BigDecimal marginValue = BigDecimal.valueOf(-5000);
		BigDecimal settlementValue = BigDecimal.valueOf(20000);
		BigDecimal setttlementValuePrevious = BigDecimal.valueOf(25000);
		BigDecimal settlementValueNext = BigDecimal.valueOf(20001);
		List<BigDecimal> marginLimits = List.of(BigDecimal.valueOf(-50000),BigDecimal.valueOf(50000));
		ZoneId zone = ZoneId.of("Europe/Berlin");
		ZonedDateTime settlementTime = ZonedDateTime.of(LocalDate.now(), LocalTime.of(17,0,0), zone);
		ZonedDateTime settlementTimeNext = settlementTime.plusDays(1);

		final String marketDataXMLStr = new String(SettlementTests.class.getClassLoader().getResourceAsStream("net/finmath/smartcontract/valuation/client/md_testset1.xml").readAllBytes(), StandardCharsets.UTF_8);

		StringReader reader = new StringReader(marketDataXMLStr);
		JAXBContext jaxbContext = JAXBContext.newInstance(MarketDataList.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		MarketDataList marketData = (MarketDataList) jaxbUnmarshaller.unmarshal(reader);

		Settlement settlement = new Settlement();
		settlement.setTradeId(tradeID);
		settlement.setSettlementType(type);
		settlement.setCurrency("EUR");
		settlement.setMarginValue(marginValue);
		settlement.setMarginLimits(marginLimits);
		settlement.setSettlementTime(settlementTime);
		settlement.setMarketData(marketData);
		settlement.setSettlementNPV(settlementValue);
		settlement.setSettlementNPVPrevious(setttlementValuePrevious);
		settlement.setSettlementTimeNext(settlementTimeNext);
		settlement.setSettlementNPVNext(settlementValueNext);

		JAXBContext jaxbContextSettlement = JAXBContext.newInstance(Settlement.class);
		Marshaller jaxbMarshaller = jaxbContextSettlement.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		StringWriter writer = new StringWriter();
		jaxbMarshaller.marshal(settlement, System.out);
		jaxbMarshaller.marshal(settlement, writer);
		String xmlStr = writer.toString();
		Assertions.assertFalse(xmlStr.isEmpty());
	}

	@Test
	void testTradeIdGetterAndSetter() {
		final Settlement settlement = new Settlement();
		final String tradeId = "TRADE123";

		settlement.setTradeId(tradeId);

		Assertions.assertEquals(tradeId, settlement.getTradeId());
	}

	@Test
	void testSettlementTypeGetterAndSetter() {
		final Settlement settlement = new Settlement();
		final Settlement.SettlementType settlementType = Settlement.SettlementType.INITIAL;

		settlement.setSettlementType(settlementType);

		Assertions.assertEquals(settlementType, settlement.getSettlementType());
	}

	@Test
	void testCurrencyGetterAndSetter() {
		final Settlement settlement = new Settlement();
		final String currency = "USD";

		settlement.setCurrency(currency);

		Assertions.assertEquals(currency, settlement.getCurrency());
	}

	@Test
	void testMarginValueGetterAndSetter() {
		final Settlement settlement = new Settlement();
		final BigDecimal marginValue = BigDecimal.valueOf(1000.50);

		settlement.setMarginValue(marginValue);

		Assertions.assertEquals(marginValue, settlement.getMarginValue());
	}

	@Test
	void testMarginLimitsGetterAndSetter() {
		final Settlement settlement = new Settlement();
		final List<BigDecimal> marginLimits = List.of(BigDecimal.valueOf(-1000), BigDecimal.valueOf(1000));

		settlement.setMarginLimits(marginLimits);

		Assertions.assertEquals(marginLimits, settlement.getMarginLimits());
	}

	@Test
	void testSettlementTimeGetterAndSetter() {
		final Settlement settlement = new Settlement();
		final ZonedDateTime settlementTime = ZonedDateTime.of(2024, 3, 15, 10, 30, 0, 0, ZoneId.of("UTC"));

		settlement.setSettlementTime(settlementTime);

		Assertions.assertEquals(settlementTime, settlement.getSettlementTime());
	}

	@Test
	void testSettlementNPVGetterAndSetter() {
		final Settlement settlement = new Settlement();
		final BigDecimal settlementNPV = BigDecimal.valueOf(50000);

		settlement.setSettlementNPV(settlementNPV);

		Assertions.assertEquals(settlementNPV, settlement.getSettlementNPV());
	}

	@Test
	void testSettlementNPVPreviousGetterAndSetter() {
		final Settlement settlement = new Settlement();
		final BigDecimal settlementNPVPrevious = BigDecimal.valueOf(48000);

		settlement.setSettlementNPVPrevious(settlementNPVPrevious);

		Assertions.assertEquals(settlementNPVPrevious, settlement.getSettlementNPVPrevious());
	}

	@Test
	void testSettlementTimeNextGetterAndSetter() {
		final Settlement settlement = new Settlement();
		final ZonedDateTime settlementTimeNext = ZonedDateTime.of(2024, 3, 16, 10, 30, 0, 0, ZoneId.of("UTC"));

		settlement.setSettlementTimeNext(settlementTimeNext);

		Assertions.assertEquals(settlementTimeNext, settlement.getSettlementTimeNext());
	}

	@Test
	void testSettlementNPVNextGetterAndSetter() {
		final Settlement settlement = new Settlement();
		final BigDecimal settlementNPVNext = BigDecimal.valueOf(51000);

		settlement.setSettlementNPVNext(settlementNPVNext);

		Assertions.assertEquals(settlementNPVNext, settlement.getSettlementNPVNext());
	}

	@Test
	void testMarketDataGetterAndSetter() {
		final Settlement settlement = new Settlement();
		final MarketDataList marketData = new MarketDataList();

		settlement.setMarketData(marketData);

		Assertions.assertEquals(marketData, settlement.getMarketData());
	}

	@Test
	void testSettlementInfosGetterAndSetter() {
		final Settlement settlement = new Settlement();
		final List<SettlementInfo> settlementInfos = List.of(new SettlementInfo());

		settlement.setSettlementInfos(settlementInfos);

		Assertions.assertEquals(settlementInfos, settlement.getSettlementInfos());
	}
}
