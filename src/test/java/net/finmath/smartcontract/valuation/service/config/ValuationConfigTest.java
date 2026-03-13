package net.finmath.smartcontract.valuation.service.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link ValuationConfig}.
 * Tests all getter/setter pairs as plain POJO without Spring context.
 */
class ValuationConfigTest {

	private ValuationConfig config;

	@BeforeEach
	void setUp() {
		config = new ValuationConfig();
	}

	@Test
	void testLiveMarketDataDefaultIsFalse() {
		assertFalse(config.isLiveMarketData());
	}

	@Test
	void testLiveMarketDataSetToTrue() {
		config.setLiveMarketData(true);
		assertTrue(config.isLiveMarketData());
	}

	@Test
	void testLiveMarketDataSetToFalse() {
		config.setLiveMarketData(true);
		config.setLiveMarketData(false);
		assertFalse(config.isLiveMarketData());
	}

	@Test
	void testSettlementCurrency() {
		assertNull(config.getSettlementCurrency());
		config.setSettlementCurrency("EUR");
		assertEquals("EUR", config.getSettlementCurrency());
	}

	@Test
	void testLiveMarketDataProvider() {
		assertNull(config.getLiveMarketDataProvider());
		config.setLiveMarketDataProvider("refinitiv");
		assertEquals("refinitiv", config.getLiveMarketDataProvider());
	}

	@Test
	void testInternalMarketDataProvider() {
		assertNull(config.getInternalMarketDataProvider());
		config.setInternalMarketDataProvider("internal-provider");
		assertEquals("internal-provider", config.getInternalMarketDataProvider());
	}

	@Test
	void testProductFixingType() {
		assertNull(config.getProductFixingType());
		config.setProductFixingType("ESTR");
		assertEquals("ESTR", config.getProductFixingType());
	}

	@Test
	void testFpmlSchemaPath() {
		assertNull(config.getFpmlSchemaPath());
		config.setFpmlSchemaPath("/schemas/fpml-5-12");
		assertEquals("/schemas/fpml-5-12", config.getFpmlSchemaPath());
	}

	@Test
	void testMarketDataProviderToTemplateDefaultIsNull() {
		assertNull(config.getMarketDataProviderToTemplate());
	}

	@Test
	void testMarketDataProviderToTemplate() {
		Map<String, String> templateMap = new HashMap<>();
		templateMap.put("refinitiv", "template-refinitiv.xml");
		templateMap.put("bloomberg", "template-bloomberg.xml");

		config.setMarketDataProviderToTemplate(templateMap);

		Map<String, String> result = config.getMarketDataProviderToTemplate();
		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals("template-refinitiv.xml", result.get("refinitiv"));
		assertEquals("template-bloomberg.xml", result.get("bloomberg"));
	}

	@Test
	void testMarketDataProviderToTemplateEmptyMap() {
		config.setMarketDataProviderToTemplate(Map.of());

		Map<String, String> result = config.getMarketDataProviderToTemplate();
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}

	@Test
	void testSetAllFieldsAndVerify() {
		config.setLiveMarketData(true);
		config.setSettlementCurrency("USD");
		config.setLiveMarketDataProvider("refinitiv");
		config.setInternalMarketDataProvider("internal");
		config.setProductFixingType("LIBOR");
		config.setFpmlSchemaPath("/path/to/schema");
		config.setMarketDataProviderToTemplate(Map.of("key", "value"));

		assertAll(
				() -> assertTrue(config.isLiveMarketData()),
				() -> assertEquals("USD", config.getSettlementCurrency()),
				() -> assertEquals("refinitiv", config.getLiveMarketDataProvider()),
				() -> assertEquals("internal", config.getInternalMarketDataProvider()),
				() -> assertEquals("LIBOR", config.getProductFixingType()),
				() -> assertEquals("/path/to/schema", config.getFpmlSchemaPath()),
				() -> assertEquals(1, config.getMarketDataProviderToTemplate().size()),
				() -> assertEquals("value", config.getMarketDataProviderToTemplate().get("key"))
		);
	}
}
