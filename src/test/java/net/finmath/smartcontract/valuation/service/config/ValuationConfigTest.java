package net.finmath.smartcontract.valuation.service.config;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ValuationConfigTest {

	@Test
	void testLiveMarketData() {
		ValuationConfig config = new ValuationConfig();
		assertFalse(config.isLiveMarketData());
		config.setLiveMarketData(true);
		assertTrue(config.isLiveMarketData());
	}

	@Test
	void testSettlementCurrency() {
		ValuationConfig config = new ValuationConfig();
		assertNull(config.getSettlementCurrency());
		config.setSettlementCurrency("EUR");
		assertEquals("EUR", config.getSettlementCurrency());
	}

	@Test
	void testLiveMarketDataProvider() {
		ValuationConfig config = new ValuationConfig();
		assertNull(config.getLiveMarketDataProvider());
		config.setLiveMarketDataProvider("refinitiv");
		assertEquals("refinitiv", config.getLiveMarketDataProvider());
	}

	@Test
	void testInternalMarketDataProvider() {
		ValuationConfig config = new ValuationConfig();
		assertNull(config.getInternalMarketDataProvider());
		config.setInternalMarketDataProvider("internal");
		assertEquals("internal", config.getInternalMarketDataProvider());
	}

	@Test
	void testProductFixingType() {
		ValuationConfig config = new ValuationConfig();
		assertNull(config.getProductFixingType());
		config.setProductFixingType("OIS");
		assertEquals("OIS", config.getProductFixingType());
	}

	@Test
	void testFpmlSchemaPath() {
		ValuationConfig config = new ValuationConfig();
		assertNull(config.getFpmlSchemaPath());
		config.setFpmlSchemaPath("schemas/fpml");
		assertEquals("schemas/fpml", config.getFpmlSchemaPath());
	}

	@Test
	void testMarketDataProviderToTemplate() {
		ValuationConfig config = new ValuationConfig();
		assertNull(config.getMarketDataProviderToTemplate());
		Map<String, String> map = Map.of("provider1", "template1");
		config.setMarketDataProviderToTemplate(map);
		assertEquals(map, config.getMarketDataProviderToTemplate());
	}
}
