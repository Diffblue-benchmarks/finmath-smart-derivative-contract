package net.finmath.smartcontract.valuation.service.config;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ValuationConfigTest {

	@Test
	void testLiveMarketData() {
		final ValuationConfig config = new ValuationConfig();

		config.setLiveMarketData(true);
		assertTrue(config.isLiveMarketData());

		config.setLiveMarketData(false);
		assertFalse(config.isLiveMarketData());
	}

	@Test
	void testSettlementCurrency() {
		final ValuationConfig config = new ValuationConfig();
		final String currency = "USD";

		config.setSettlementCurrency(currency);

		assertEquals(currency, config.getSettlementCurrency());
	}

	@Test
	void testLiveMarketDataProvider() {
		final ValuationConfig config = new ValuationConfig();
		final String provider = "Bloomberg";

		config.setLiveMarketDataProvider(provider);

		assertEquals(provider, config.getLiveMarketDataProvider());
	}

	@Test
	void testInternalMarketDataProvider() {
		final ValuationConfig config = new ValuationConfig();
		final String provider = "InternalProvider";

		config.setInternalMarketDataProvider(provider);

		assertEquals(provider, config.getInternalMarketDataProvider());
	}

	@Test
	void testProductFixingType() {
		final ValuationConfig config = new ValuationConfig();
		final String fixingType = "HISTORICAL";

		config.setProductFixingType(fixingType);

		assertEquals(fixingType, config.getProductFixingType());
	}

	@Test
	void testFpmlSchemaPath() {
		final ValuationConfig config = new ValuationConfig();
		final String schemaPath = "/path/to/schema.xsd";

		config.setFpmlSchemaPath(schemaPath);

		assertEquals(schemaPath, config.getFpmlSchemaPath());
	}

	@Test
	void testMarketDataProviderToTemplate() {
		final ValuationConfig config = new ValuationConfig();
		final Map<String, String> templateMap = new HashMap<>();
		templateMap.put("provider1", "template1");
		templateMap.put("provider2", "template2");

		config.setMarketDataProviderToTemplate(templateMap);

		assertEquals(templateMap, config.getMarketDataProviderToTemplate());
	}
}
