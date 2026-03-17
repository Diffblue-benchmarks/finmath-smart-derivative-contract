package net.finmath.smartcontract.settlement;

import net.finmath.smartcontract.model.SDCException;
import net.finmath.smartcontract.product.SmartDerivativeContractDescriptor;
import net.finmath.smartcontract.product.xml.SDCXMLParser;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SettlementGeneratorTest {

	@Test
	void generateInitialSettlement() throws IOException, ParserConfigurationException, SAXException {
		InputStream inputStream = SettlementGeneratorTest.class.getClassLoader().getResourceAsStream("net/finmath/smartcontract/valuation/historicalMarketData/marketdata_2008-05-02.xml");
		String marketDataString = new String(inputStream.readAllBytes());

		inputStream = SettlementGeneratorTest.class.getClassLoader().getResourceAsStream("net.finmath.smartcontract.product.xml/smartderivativecontract_with_rics.xml");
		String productString = new String(inputStream.readAllBytes());
		SmartDerivativeContractDescriptor sdc = SDCXMLParser.parse(productString);

		String settlementString = new SettlementGenerator().generateInitialSettlementXml(marketDataString, sdc)
				.marginLimits(List.of(BigDecimal.ONE, BigDecimal.ZERO))
				.settlementNPV(BigDecimal.ZERO)
				//.settlementNPVPrevious(BigDecimal.ZERO)
				.settlementTimeNext(ZonedDateTime.now())
				.settlementNPVNext(BigDecimal.ZERO)
				.settlementInfo(Map.of())
				.build();

		System.out.println(settlementString);

		assertTrue(settlementString.contains("ESTRSWP3Y"));
		assertTrue(settlementString.contains("ESTRSWP1W"));
		assertTrue(settlementString.contains("INITIAL"));
		assertFalse(settlementString.contains("REGULAR"));
		assertTrue(settlementString.contains("<marginValue>0</marginValue>"));
		assertTrue(settlementString.contains("<marketData>"));
		assertTrue(settlementString.contains("<requestTimeStamp>"));
		assertTrue(settlementString.contains("<item>"));
		assertTrue(settlementString.contains("<value>"));
		assertTrue(settlementString.contains("<settlementTimeNext>"));
		assertTrue(settlementString.contains("<settlementNPVNext>"));
		assertTrue(settlementString.contains("<settlementNPVPrevious>"));
		assertTrue(settlementString.contains("<settlementNPV>"));
		assertTrue(settlementString.contains("<marginLimits>"));
	}

	@Test
	void generateRegularSettlement() throws IOException, ParserConfigurationException, SAXException {
		InputStream inputStream = SettlementGeneratorTest.class.getClassLoader().getResourceAsStream("net/finmath/smartcontract/valuation/historicalMarketData/marketdata_2008-05-02.xml");
		String marketDataString = new String(inputStream.readAllBytes());

		inputStream = SettlementGeneratorTest.class.getClassLoader().getResourceAsStream("net.finmath.smartcontract.product.xml/smartderivativecontract_with_rics.xml");
		String productString = new String(inputStream.readAllBytes());
		SmartDerivativeContractDescriptor sdc = SDCXMLParser.parse(productString);

		String settlementString = new SettlementGenerator().generateRegularSettlementXml(marketDataString, sdc, BigDecimal.ONE)
				.marginLimits(List.of(BigDecimal.ONE, BigDecimal.ZERO))
				.settlementNPV(BigDecimal.ZERO)
				.settlementNPVPrevious(BigDecimal.ZERO)
				.settlementTimeNext(ZonedDateTime.now())
				.settlementNPVNext(BigDecimal.ZERO)
				.settlementInfo(Map.of())
				.build();

		System.out.println(settlementString);

		assertTrue(settlementString.contains("ESTRSWP3Y"));
		assertTrue(settlementString.contains("ESTRSWP1W"));
		assertTrue(settlementString.contains("REGULAR"));
		assertFalse(settlementString.contains("INITIAL"));
		assertTrue(settlementString.contains("<marginValue>1</marginValue>"));
		assertTrue(settlementString.contains("<marketData>"));
		assertTrue(settlementString.contains("<requestTimeStamp>"));
		assertTrue(settlementString.contains("<item>"));
		assertTrue(settlementString.contains("<value>"));
		assertTrue(settlementString.contains("<settlementTimeNext>"));
		assertTrue(settlementString.contains("<settlementNPVNext>"));
		assertTrue(settlementString.contains("<settlementNPVPrevious>"));
		assertTrue(settlementString.contains("<settlementNPV>"));
		assertTrue(settlementString.contains("<marginLimits>"));
	}

	@Test
	void generateIncompleteSettlement_Exception() throws IOException, ParserConfigurationException, SAXException {
		InputStream inputStream = SettlementGeneratorTest.class.getClassLoader().getResourceAsStream("net/finmath/smartcontract/valuation/historicalMarketData/marketdata_2008-05-02.xml");
		String marketDataString = new String(inputStream.readAllBytes());

		inputStream = SettlementGeneratorTest.class.getClassLoader().getResourceAsStream("net.finmath.smartcontract.product.xml/smartderivativecontract_with_rics.xml");
		String productString = new String(inputStream.readAllBytes());
		SmartDerivativeContractDescriptor sdc = SDCXMLParser.parse(productString);

		SettlementGenerator generator = new SettlementGenerator().generateRegularSettlementXml(marketDataString, sdc, BigDecimal.ONE)
				.marginLimits(List.of(BigDecimal.ONE, BigDecimal.ZERO))
				.settlementNPV(BigDecimal.ZERO)
				.settlementNPVPrevious(BigDecimal.ZERO)
				.settlementTimeNext(ZonedDateTime.now());
				//.settlementNPVNext(BigDecimal.ZERO);

		assertThrows(SDCException.class, generator::build);
	}

	@Test
	void testBuildObject() throws IOException, ParserConfigurationException, SAXException {
		InputStream inputStream = SettlementGeneratorTest.class.getClassLoader().getResourceAsStream("net/finmath/smartcontract/valuation/historicalMarketData/marketdata_2008-05-02.xml");
		String marketDataString = new String(inputStream.readAllBytes());

		inputStream = SettlementGeneratorTest.class.getClassLoader().getResourceAsStream("net.finmath.smartcontract.product.xml/smartderivativecontract_with_rics.xml");
		String productString = new String(inputStream.readAllBytes());
		SmartDerivativeContractDescriptor sdc = SDCXMLParser.parse(productString);

		Settlement settlement = new SettlementGenerator().generateInitialSettlementXml(marketDataString, sdc)
				.marginLimits(List.of(BigDecimal.ONE, BigDecimal.ZERO))
				.settlementNPV(BigDecimal.ZERO)
				.settlementTimeNext(ZonedDateTime.now())
				.settlementNPVNext(BigDecimal.ZERO)
				.settlementInfo(Map.of())
				.buildObject();

		assertNotNull(settlement);
		assertNotNull(settlement.getTradeId());
		assertEquals(Settlement.SettlementType.INITIAL, settlement.getSettlementType());
		assertNotNull(settlement.getCurrency());
		assertEquals(BigDecimal.ZERO, settlement.getMarginValue());
		assertNotNull(settlement.getMarginLimits());
		assertEquals(2, settlement.getMarginLimits().size());
	}

	@Test
	void testBuildObjectIncomplete_Exception() throws IOException, ParserConfigurationException, SAXException {
		InputStream inputStream = SettlementGeneratorTest.class.getClassLoader().getResourceAsStream("net/finmath/smartcontract/valuation/historicalMarketData/marketdata_2008-05-02.xml");
		String marketDataString = new String(inputStream.readAllBytes());

		inputStream = SettlementGeneratorTest.class.getClassLoader().getResourceAsStream("net.finmath.smartcontract.product.xml/smartderivativecontract_with_rics.xml");
		String productString = new String(inputStream.readAllBytes());
		SmartDerivativeContractDescriptor sdc = SDCXMLParser.parse(productString);

		SettlementGenerator generator = new SettlementGenerator().generateInitialSettlementXml(marketDataString, sdc)
				.marginLimits(List.of(BigDecimal.ONE, BigDecimal.ZERO))
				.settlementNPV(BigDecimal.ZERO)
				.settlementTimeNext(ZonedDateTime.now());

		assertThrows(SDCException.class, generator::buildObject);
	}

	@Test
	void testBuilderMethods() throws IOException, ParserConfigurationException, SAXException {
		InputStream inputStream = SettlementGeneratorTest.class.getClassLoader().getResourceAsStream("net/finmath/smartcontract/valuation/historicalMarketData/marketdata_2008-05-02.xml");
		String marketDataString = new String(inputStream.readAllBytes());

		inputStream = SettlementGeneratorTest.class.getClassLoader().getResourceAsStream("net.finmath.smartcontract.product.xml/smartderivativecontract_with_rics.xml");
		String productString = new String(inputStream.readAllBytes());
		SmartDerivativeContractDescriptor sdc = SDCXMLParser.parse(productString);

		ZonedDateTime nextTime = ZonedDateTime.now().plusDays(1);
		String settlementString = new SettlementGenerator()
				.generateRegularSettlementXml(marketDataString, sdc, BigDecimal.TEN)
				.marginLimits(List.of(BigDecimal.valueOf(100), BigDecimal.valueOf(50)))
				.settlementNPV(BigDecimal.valueOf(5))
				.settlementNPVPrevious(BigDecimal.valueOf(3))
				.settlementTimeNext(nextTime)
				.settlementNPVNext(BigDecimal.valueOf(7))
				.settlementInfo(Map.of("key1", BigDecimal.ONE, "key2", BigDecimal.valueOf(2)))
				.build();

		assertNotNull(settlementString);
		assertTrue(settlementString.contains("REGULAR"));
		assertTrue(settlementString.contains("<marginValue>10</marginValue>"));
		assertTrue(settlementString.contains("<settlementNPV>5</settlementNPV>"));
		assertTrue(settlementString.contains("<settlementNPVPrevious>3</settlementNPVPrevious>"));
		assertTrue(settlementString.contains("<settlementNPVNext>7</settlementNPVNext>"));
	}

	@Test
	void testGenerateInitialSettlementBuilderChain() throws IOException, ParserConfigurationException, SAXException {
		InputStream inputStream = SettlementGeneratorTest.class.getClassLoader().getResourceAsStream("net/finmath/smartcontract/valuation/historicalMarketData/marketdata_2008-05-02.xml");
		String marketDataString = new String(inputStream.readAllBytes());

		inputStream = SettlementGeneratorTest.class.getClassLoader().getResourceAsStream("net.finmath.smartcontract.product.xml/smartderivativecontract_with_rics.xml");
		String productString = new String(inputStream.readAllBytes());
		SmartDerivativeContractDescriptor sdc = SDCXMLParser.parse(productString);

		SettlementGenerator generator = new SettlementGenerator().generateInitialSettlementXml(marketDataString, sdc);

		assertNotNull(generator);

		String settlementString = generator
				.marginLimits(List.of(BigDecimal.ONE, BigDecimal.ZERO))
				.settlementNPV(BigDecimal.ZERO)
				.settlementTimeNext(ZonedDateTime.now())
				.settlementNPVNext(BigDecimal.ZERO)
				.settlementInfo(Map.of())
				.build();

		assertTrue(settlementString.contains("INITIAL"));
		assertTrue(settlementString.contains("<settlementNPVPrevious>0</settlementNPVPrevious>"));
	}
}