package net.finmath.smartcontract.product.xml;

import net.finmath.smartcontract.model.MarketDataList;
import net.finmath.smartcontract.model.SDCException;
import net.finmath.smartcontract.product.SmartDerivativeContractDescriptor;
import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationDataItem;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SDCXMLParserCoverageTest {

	private String loadSdcXml() throws IOException {
		return new String(
				SDCXMLParserCoverageTest.class.getClassLoader()
						.getResourceAsStream("net.finmath.smartcontract.product.xml/smartderivativecontract.xml")
						.readAllBytes(),
				StandardCharsets.UTF_8
		);
	}

	@Test
	void testParseMarketDataItems() throws IOException, SAXException, ParserConfigurationException {
		String sdcXml = loadSdcXml();

		SmartDerivativeContractDescriptor sdc = SDCXMLParser.parse(sdcXml);

		List<CalibrationDataItem.Spec> marketDataItems = sdc.getMarketdataItemList();
		assertNotNull(marketDataItems);
		assertFalse(marketDataItems.isEmpty());

		CalibrationDataItem.Spec firstItem = marketDataItems.get(0);
		assertEquals("ESTRFIX1D", firstItem.getKey());
	}

	@Test
	void testParsePartiesAndMarginAccounts() throws IOException, SAXException, ParserConfigurationException {
		String sdcXml = loadSdcXml();

		SmartDerivativeContractDescriptor sdc = SDCXMLParser.parse(sdcXml);

		List<SmartDerivativeContractDescriptor.Party> parties = sdc.getCounterparties();
		assertEquals(2, parties.size());

		assertEquals("party1", parties.get(0).getId());
		assertEquals("Counterparty 1", parties.get(0).getName());
		assertEquals("0x627306090abab3a6e1400e9345bc60c78a8bef57", parties.get(0).getAddress());

		assertEquals("party2", parties.get(1).getId());
		assertEquals("Counterparty 2", parties.get(1).getName());
		assertEquals("0xf17f52151ebef6c7334fad080c5704d77216b732", parties.get(1).getAddress());

		assertEquals(10000.0, sdc.getMarginAccount("party1"), 0.001);
		assertEquals(10000.0, sdc.getMarginAccount("party2"), 0.001);

		assertEquals(50000.0, sdc.getPenaltyFee("party1"), 0.001);
		assertEquals(50000.0, sdc.getPenaltyFee("party2"), 0.001);
	}

	@Test
	void testParseSettlementTimeAndTradeType() throws IOException, SAXException, ParserConfigurationException {
		String sdcXml = loadSdcXml();

		SmartDerivativeContractDescriptor sdc = SDCXMLParser.parse(sdcXml);

		OffsetTime expectedTime = OffsetTime.of(17, 0, 0, 0, ZoneOffset.UTC);
		assertEquals(expectedTime, sdc.getSettlementTime());
		assertEquals("SDCPledgedBalance", sdc.getTradeType());
	}

	@Test
	void testParseUnderlying() throws IOException, SAXException, ParserConfigurationException {
		String sdcXml = loadSdcXml();

		SmartDerivativeContractDescriptor sdc = SDCXMLParser.parse(sdcXml);

		Node underlying = sdc.getUnderlying();
		assertNotNull(underlying);
		assertTrue(underlying.getNodeName().contains("dataDocument"));
	}

	@Test
	void testUnmarshalAndMarshalRoundTrip() throws IOException {
		String sdcXml = loadSdcXml();

		Smartderivativecontract sdc = SDCXMLParser.unmarshalXml(sdcXml, Smartderivativecontract.class);
		assertNotNull(sdc);
		assertEquals("UTI12345", sdc.getUniqueTradeIdentifier().trim());

		String marshaledXml = SDCXMLParser.marshalClassToXMLString(sdc);
		assertNotNull(marshaledXml);
		assertTrue(marshaledXml.contains("UTI12345"));
		assertTrue(marshaledXml.contains("smartderivativecontract"));
	}

	@Test
	void testMarshalSDCToXMLStringReplacesNamespaceTags() throws IOException {
		String sdcXml = loadSdcXml();

		Smartderivativecontract sdc = SDCXMLParser.unmarshalXml(sdcXml, Smartderivativecontract.class);

		String xmlString = SDCXMLParser.marshalSDCToXMLString(sdc);
		assertNotNull(xmlString);
		assertFalse(xmlString.contains("fpml:"), "SDC XML should not contain fpml: namespace prefix");
		assertTrue(xmlString.contains("<dataDocument fpmlVersion=\"5-9\" xmlns=\"http://www.fpml.org/FpML-5/confirmation\">"));
	}

	@Test
	void testMarshalNonSdcClass() throws IOException {
		MarketDataList marketDataList = new MarketDataList();

		String xmlString = SDCXMLParser.marshalClassToXMLString(marketDataList);
		assertNotNull(xmlString);
		assertTrue(xmlString.contains("xml version"));
	}

	@Test
	void testUnmarshalInvalidXmlThrowsSDCException() {
		assertThrows(SDCException.class, () -> SDCXMLParser.unmarshalXml("not valid xml", Smartderivativecontract.class));
	}
}
