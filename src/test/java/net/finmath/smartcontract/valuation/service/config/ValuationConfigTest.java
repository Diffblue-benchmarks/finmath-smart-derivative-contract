package net.finmath.smartcontract.valuation.service.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValuationConfigTest {
	private ValuationConfig valuationConfig;

	@BeforeEach
	public void setUp() {
		valuationConfig = new ValuationConfig();
	}

	@Test
	void testIsLiveMarketData() {
		assertFalse(valuationConfig.isLiveMarketData());
	}

	@Test
	void testSetLiveMarketData() {
		valuationConfig.setLiveMarketData(true);
		assertTrue(valuationConfig.isLiveMarketData());
	}

	@Test
	void testGetSettlementCurrency() {
		assertNull(valuationConfig.getSettlementCurrency());
	}

	@Test
	void testSetSettlementCurrency() {
		valuationConfig.setSettlementCurrency("EUR");
		assertEquals("EUR", valuationConfig.getSettlementCurrency());
	}

	@Test
	void testGetLiveMarketDataProvider() {
		assertNull(valuationConfig.getLiveMarketDataProvider());
	}

	@Test
	void testSetLiveMarketDataProvider() {
		valuationConfig.setLiveMarketDataProvider("Refinitiv");
		assertEquals("Refinitiv", valuationConfig.getLiveMarketDataProvider());
	}

	@Test
	void testGetInternalMarketDataProvider() {
		assertNull(valuationConfig.getInternalMarketDataProvider());
	}

	@Test
	void testSetInternalMarketDataProvider() {
		valuationConfig.setInternalMarketDataProvider("internal");
		assertEquals("internal", valuationConfig.getInternalMarketDataProvider());
	}

	@Test
	void testGetProductFixingType() {
		assertNull(valuationConfig.getProductFixingType());
	}

	@Test
	void testSetProductFixingType() {
		valuationConfig.setProductFixingType("IRS");
		assertEquals("IRS", valuationConfig.getProductFixingType());
	}

	@Test
	void testGetFpmlSchemaPath() {
		assertNull(valuationConfig.getFpmlSchemaPath());
	}

	@Test
	void testSetFpmlSchemaPath() {
		valuationConfig.setFpmlSchemaPath("/schemas/fpml.xsd");
		assertEquals("/schemas/fpml.xsd", valuationConfig.getFpmlSchemaPath());
	}

	@Test
	void testGetMarketDataProviderToTemplate() {
		assertNull(valuationConfig.getMarketDataProviderToTemplate());
	}

	@Test
	void testSetMarketDataProviderToTemplate() {
		Map<String, String> templateMap = new HashMap<>();
		templateMap.put("Refinitiv", "template1.xml");
		valuationConfig.setMarketDataProviderToTemplate(templateMap);
		assertEquals(templateMap, valuationConfig.getMarketDataProviderToTemplate());
	}
}
