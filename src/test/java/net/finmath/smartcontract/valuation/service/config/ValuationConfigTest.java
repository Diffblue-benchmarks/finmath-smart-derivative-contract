package net.finmath.smartcontract.valuation.service.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ValuationConfig configuration class.
 */
class ValuationConfigTest {

    private ValuationConfig config;

    @BeforeEach
    void setUp() {
        config = new ValuationConfig();
    }

    @Test
    void testDefaultConstructor() {
        assertNotNull(config);
    }

    @Test
    void testSetAndIsLiveMarketData() {
        config.setLiveMarketData(true);
        assertTrue(config.isLiveMarketData());

        config.setLiveMarketData(false);
        assertFalse(config.isLiveMarketData());
    }

    @Test
    void testSetAndGetSettlementCurrency() {
        String currency = "EUR";
        config.setSettlementCurrency(currency);
        assertEquals(currency, config.getSettlementCurrency());
    }

    @Test
    void testSetAndGetLiveMarketDataProvider() {
        String provider = "Refinitiv";
        config.setLiveMarketDataProvider(provider);
        assertEquals(provider, config.getLiveMarketDataProvider());
    }

    @Test
    void testSetAndGetInternalMarketDataProvider() {
        String provider = "Internal";
        config.setInternalMarketDataProvider(provider);
        assertEquals(provider, config.getInternalMarketDataProvider());
    }

    @Test
    void testSetAndGetProductFixingType() {
        String fixingType = "FIXING";
        config.setProductFixingType(fixingType);
        assertEquals(fixingType, config.getProductFixingType());
    }

    @Test
    void testSetAndGetFpmlSchemaPath() {
        String schemaPath = "/path/to/schema.xsd";
        config.setFpmlSchemaPath(schemaPath);
        assertEquals(schemaPath, config.getFpmlSchemaPath());
    }

    @Test
    void testSetAndGetMarketDataProviderToTemplate() {
        Map<String, String> template = new HashMap<>();
        template.put("provider1", "template1");
        template.put("provider2", "template2");

        config.setMarketDataProviderToTemplate(template);

        assertEquals(template, config.getMarketDataProviderToTemplate());
        assertEquals(2, config.getMarketDataProviderToTemplate().size());
    }

    @Test
    void testAllPropertiesSet() {
        Map<String, String> template = Map.of("key", "value");

        config.setLiveMarketData(true);
        config.setSettlementCurrency("USD");
        config.setLiveMarketDataProvider("Provider1");
        config.setInternalMarketDataProvider("Provider2");
        config.setProductFixingType("FIXING");
        config.setFpmlSchemaPath("/schema");
        config.setMarketDataProviderToTemplate(template);

        assertTrue(config.isLiveMarketData());
        assertEquals("USD", config.getSettlementCurrency());
        assertEquals("Provider1", config.getLiveMarketDataProvider());
        assertEquals("Provider2", config.getInternalMarketDataProvider());
        assertEquals("FIXING", config.getProductFixingType());
        assertEquals("/schema", config.getFpmlSchemaPath());
        assertEquals(template, config.getMarketDataProviderToTemplate());
    }

    @Test
    void testNullStringValues() {
        config.setSettlementCurrency(null);
        config.setLiveMarketDataProvider(null);
        config.setInternalMarketDataProvider(null);

        assertNull(config.getSettlementCurrency());
        assertNull(config.getLiveMarketDataProvider());
        assertNull(config.getInternalMarketDataProvider());
    }

    @Test
    void testEmptyStringValues() {
        config.setSettlementCurrency("");
        config.setLiveMarketDataProvider("");
        config.setFpmlSchemaPath("");

        assertEquals("", config.getSettlementCurrency());
        assertEquals("", config.getLiveMarketDataProvider());
        assertEquals("", config.getFpmlSchemaPath());
    }

    @Test
    void testBooleanDefaultValue() {
        // Default boolean value in Java is false
        ValuationConfig newConfig = new ValuationConfig();
        assertFalse(newConfig.isLiveMarketData());
    }

    @Test
    void testEmptyMarketDataProviderMap() {
        Map<String, String> emptyMap = new HashMap<>();
        config.setMarketDataProviderToTemplate(emptyMap);

        assertNotNull(config.getMarketDataProviderToTemplate());
        assertEquals(0, config.getMarketDataProviderToTemplate().size());
    }

    @Test
    void testNullMarketDataProviderMap() {
        config.setMarketDataProviderToTemplate(null);
        assertNull(config.getMarketDataProviderToTemplate());
    }

    @Test
    void testMultipleCurrencies() {
        config.setSettlementCurrency("EUR");
        assertEquals("EUR", config.getSettlementCurrency());

        config.setSettlementCurrency("USD");
        assertEquals("USD", config.getSettlementCurrency());

        config.setSettlementCurrency("GBP");
        assertEquals("GBP", config.getSettlementCurrency());
    }

    @Test
    void testToggleLiveMarketData() {
        config.setLiveMarketData(true);
        assertTrue(config.isLiveMarketData());

        config.setLiveMarketData(false);
        assertFalse(config.isLiveMarketData());

        config.setLiveMarketData(true);
        assertTrue(config.isLiveMarketData());
    }

    @Test
    void testMarketDataProviderMapModification() {
        Map<String, String> template = new HashMap<>();
        template.put("key1", "value1");

        config.setMarketDataProviderToTemplate(template);
        assertEquals(1, config.getMarketDataProviderToTemplate().size());

        config.getMarketDataProviderToTemplate().put("key2", "value2");
        assertEquals(2, config.getMarketDataProviderToTemplate().size());
    }
}
