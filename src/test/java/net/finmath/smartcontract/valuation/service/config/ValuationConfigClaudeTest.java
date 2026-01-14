/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */

package net.finmath.smartcontract.valuation.service.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for ValuationConfig.
 * Tests all methods with focus on branch and condition coverage.
 *
 * @author Claude Code
 */
class ValuationConfigClaudeTest {

	private ValuationConfig config;

	@BeforeEach
	void setUp() {
		config = new ValuationConfig();
	}

	/**
	 * Test the default constructor of ValuationConfig.
	 * The constructor should create a valid instance with all fields initialized to default values.
	 */
	@Test
	void testConstructor() {
		ValuationConfig newConfig = new ValuationConfig();
		assertNotNull(newConfig, "Configuration instance should not be null");

		// Verify all String fields are null by default
		assertNull(newConfig.getSettlementCurrency(), "SettlementCurrency should be null by default");
		assertNull(newConfig.getLiveMarketDataProvider(), "LiveMarketDataProvider should be null by default");
		assertNull(newConfig.getInternalMarketDataProvider(), "InternalMarketDataProvider should be null by default");
		assertNull(newConfig.getProductFixingType(), "ProductFixingType should be null by default");
		assertNull(newConfig.getFpmlSchemaPath(), "FpmlSchemaPath should be null by default");
		assertNull(newConfig.getMarketDataProviderToTemplate(), "MarketDataProviderToTemplate should be null by default");

		// Verify boolean field is false by default
		assertFalse(newConfig.isLiveMarketData(), "LiveMarketData should be false by default");
	}

	/**
	 * Test isLiveMarketData and setLiveMarketData methods.
	 * Verifies that:
	 * - Setting true stores it correctly
	 * - Setting false stores it correctly
	 * - Getting returns the set value
	 */
	@Test
	void testLiveMarketDataGetterSetter() {
		// Default should be false
		assertFalse(config.isLiveMarketData(), "LiveMarketData should be false by default");

		// Test setting to true
		config.setLiveMarketData(true);
		assertTrue(config.isLiveMarketData(), "LiveMarketData should be true after setting to true");

		// Test setting to false
		config.setLiveMarketData(false);
		assertFalse(config.isLiveMarketData(), "LiveMarketData should be false after setting to false");

		// Test toggling
		config.setLiveMarketData(true);
		assertTrue(config.isLiveMarketData(), "LiveMarketData should be true");
		config.setLiveMarketData(false);
		assertFalse(config.isLiveMarketData(), "LiveMarketData should be false");
	}

	/**
	 * Test getSettlementCurrency and setSettlementCurrency methods.
	 * Verifies that:
	 * - Setting a currency value stores it correctly
	 * - Getting returns the set value
	 * - Setting null is allowed
	 * - Setting empty string is allowed
	 */
	@Test
	void testSettlementCurrencyGetterSetter() {
		// Test setting a normal value
		String testCurrency = "USD";
		config.setSettlementCurrency(testCurrency);
		assertEquals(testCurrency, config.getSettlementCurrency(), "SettlementCurrency should match the set value");

		// Test setting null
		config.setSettlementCurrency(null);
		assertNull(config.getSettlementCurrency(), "SettlementCurrency should be null after setting to null");

		// Test setting empty string
		config.setSettlementCurrency("");
		assertEquals("", config.getSettlementCurrency(), "SettlementCurrency should be empty string");

		// Test setting a different value
		String anotherCurrency = "EUR";
		config.setSettlementCurrency(anotherCurrency);
		assertEquals(anotherCurrency, config.getSettlementCurrency(), "SettlementCurrency should match the new value");
	}

	/**
	 * Test getLiveMarketDataProvider and setLiveMarketDataProvider methods.
	 * Verifies that:
	 * - Setting a provider value stores it correctly
	 * - Getting returns the set value
	 * - Setting null is allowed
	 * - Setting empty string is allowed
	 */
	@Test
	void testLiveMarketDataProviderGetterSetter() {
		// Test setting a normal value
		String testProvider = "Refinitiv";
		config.setLiveMarketDataProvider(testProvider);
		assertEquals(testProvider, config.getLiveMarketDataProvider(), "LiveMarketDataProvider should match the set value");

		// Test setting null
		config.setLiveMarketDataProvider(null);
		assertNull(config.getLiveMarketDataProvider(), "LiveMarketDataProvider should be null after setting to null");

		// Test setting empty string
		config.setLiveMarketDataProvider("");
		assertEquals("", config.getLiveMarketDataProvider(), "LiveMarketDataProvider should be empty string");

		// Test setting a different value
		String anotherProvider = "Bloomberg";
		config.setLiveMarketDataProvider(anotherProvider);
		assertEquals(anotherProvider, config.getLiveMarketDataProvider(), "LiveMarketDataProvider should match the new value");
	}

	/**
	 * Test getInternalMarketDataProvider and setInternalMarketDataProvider methods.
	 * Verifies that:
	 * - Setting a provider value stores it correctly
	 * - Getting returns the set value
	 * - Setting null is allowed
	 * - Setting empty string is allowed
	 */
	@Test
	void testInternalMarketDataProviderGetterSetter() {
		// Test setting a normal value
		String testProvider = "InternalProvider";
		config.setInternalMarketDataProvider(testProvider);
		assertEquals(testProvider, config.getInternalMarketDataProvider(), "InternalMarketDataProvider should match the set value");

		// Test setting null
		config.setInternalMarketDataProvider(null);
		assertNull(config.getInternalMarketDataProvider(), "InternalMarketDataProvider should be null after setting to null");

		// Test setting empty string
		config.setInternalMarketDataProvider("");
		assertEquals("", config.getInternalMarketDataProvider(), "InternalMarketDataProvider should be empty string");

		// Test setting a different value
		String anotherProvider = "AnotherInternalProvider";
		config.setInternalMarketDataProvider(anotherProvider);
		assertEquals(anotherProvider, config.getInternalMarketDataProvider(), "InternalMarketDataProvider should match the new value");
	}

	/**
	 * Test getProductFixingType and setProductFixingType methods.
	 * Verifies that:
	 * - Setting a fixing type value stores it correctly
	 * - Getting returns the set value
	 * - Setting null is allowed
	 * - Setting empty string is allowed
	 */
	@Test
	void testProductFixingTypeGetterSetter() {
		// Test setting a normal value
		String testFixingType = "LIBOR";
		config.setProductFixingType(testFixingType);
		assertEquals(testFixingType, config.getProductFixingType(), "ProductFixingType should match the set value");

		// Test setting null
		config.setProductFixingType(null);
		assertNull(config.getProductFixingType(), "ProductFixingType should be null after setting to null");

		// Test setting empty string
		config.setProductFixingType("");
		assertEquals("", config.getProductFixingType(), "ProductFixingType should be empty string");

		// Test setting a different value
		String anotherFixingType = "SOFR";
		config.setProductFixingType(anotherFixingType);
		assertEquals(anotherFixingType, config.getProductFixingType(), "ProductFixingType should match the new value");
	}

	/**
	 * Test getFpmlSchemaPath and setFpmlSchemaPath methods.
	 * Verifies that:
	 * - Setting a path value stores it correctly
	 * - Getting returns the set value
	 * - Setting null is allowed
	 * - Setting empty string is allowed
	 */
	@Test
	void testFpmlSchemaPathGetterSetter() {
		// Test setting a normal value
		String testPath = "/schemas/fpml-5-10.xsd";
		config.setFpmlSchemaPath(testPath);
		assertEquals(testPath, config.getFpmlSchemaPath(), "FpmlSchemaPath should match the set value");

		// Test setting null
		config.setFpmlSchemaPath(null);
		assertNull(config.getFpmlSchemaPath(), "FpmlSchemaPath should be null after setting to null");

		// Test setting empty string
		config.setFpmlSchemaPath("");
		assertEquals("", config.getFpmlSchemaPath(), "FpmlSchemaPath should be empty string");

		// Test setting a different value
		String anotherPath = "/schemas/fpml-5-11.xsd";
		config.setFpmlSchemaPath(anotherPath);
		assertEquals(anotherPath, config.getFpmlSchemaPath(), "FpmlSchemaPath should match the new value");
	}

	/**
	 * Test getMarketDataProviderToTemplate and setMarketDataProviderToTemplate methods.
	 * Verifies that:
	 * - Setting a map value stores it correctly
	 * - Getting returns the set value
	 * - Setting null is allowed
	 * - Setting empty map is allowed
	 * - Map contents are preserved correctly
	 */
	@Test
	void testMarketDataProviderToTemplateGetterSetter() {
		// Test setting a normal map
		Map<String, String> testMap = new HashMap<>();
		testMap.put("Refinitiv", "refinitiv-template");
		testMap.put("Bloomberg", "bloomberg-template");

		config.setMarketDataProviderToTemplate(testMap);
		Map<String, String> retrievedMap = config.getMarketDataProviderToTemplate();
		assertNotNull(retrievedMap, "MarketDataProviderToTemplate should not be null");
		assertEquals(2, retrievedMap.size(), "Map should contain 2 entries");
		assertEquals("refinitiv-template", retrievedMap.get("Refinitiv"), "Refinitiv template should match");
		assertEquals("bloomberg-template", retrievedMap.get("Bloomberg"), "Bloomberg template should match");

		// Test setting null
		config.setMarketDataProviderToTemplate(null);
		assertNull(config.getMarketDataProviderToTemplate(), "MarketDataProviderToTemplate should be null after setting to null");

		// Test setting empty map
		Map<String, String> emptyMap = new HashMap<>();
		config.setMarketDataProviderToTemplate(emptyMap);
		assertNotNull(config.getMarketDataProviderToTemplate(), "MarketDataProviderToTemplate should not be null");
		assertTrue(config.getMarketDataProviderToTemplate().isEmpty(), "Map should be empty");

		// Test setting a different map
		Map<String, String> anotherMap = new HashMap<>();
		anotherMap.put("Internal", "internal-template");
		config.setMarketDataProviderToTemplate(anotherMap);
		assertEquals(1, config.getMarketDataProviderToTemplate().size(), "Map should contain 1 entry");
		assertEquals("internal-template", config.getMarketDataProviderToTemplate().get("Internal"), "Internal template should match");
	}

	/**
	 * Test that the class has the correct Spring annotations.
	 * This verifies that the class is properly configured as a Spring Configuration.
	 */
	@Test
	void testClassAnnotations() {
		assertTrue(
			ValuationConfig.class.isAnnotationPresent(
				org.springframework.context.annotation.Configuration.class
			),
			"Class should be annotated with @Configuration"
		);
		assertTrue(
			ValuationConfig.class.isAnnotationPresent(
				org.springframework.boot.context.properties.ConfigurationProperties.class
			),
			"Class should be annotated with @ConfigurationProperties"
		);
	}

	/**
	 * Test the ConfigurationProperties annotation has the correct prefix.
	 */
	@Test
	void testConfigurationPropertiesPrefix() {
		org.springframework.boot.context.properties.ConfigurationProperties annotation =
			ValuationConfig.class.getAnnotation(
				org.springframework.boot.context.properties.ConfigurationProperties.class
			);

		assertNotNull(annotation, "ConfigurationProperties annotation should be present");
		assertEquals("valuation", annotation.prefix(), "ConfigurationProperties prefix should be 'valuation'");
	}

	/**
	 * Test that multiple properties can be set and retrieved correctly in combination.
	 * This simulates a realistic configuration scenario.
	 */
	@Test
	void testMultiplePropertiesSetAndGet() {
		// Create a sample map
		Map<String, String> templateMap = new HashMap<>();
		templateMap.put("Provider1", "template1");
		templateMap.put("Provider2", "template2");

		// Set all properties
		config.setLiveMarketData(true);
		config.setSettlementCurrency("USD");
		config.setLiveMarketDataProvider("Refinitiv");
		config.setInternalMarketDataProvider("InternalProvider");
		config.setProductFixingType("LIBOR");
		config.setFpmlSchemaPath("/schemas/fpml-5-10.xsd");
		config.setMarketDataProviderToTemplate(templateMap);

		// Verify all properties
		assertTrue(config.isLiveMarketData());
		assertEquals("USD", config.getSettlementCurrency());
		assertEquals("Refinitiv", config.getLiveMarketDataProvider());
		assertEquals("InternalProvider", config.getInternalMarketDataProvider());
		assertEquals("LIBOR", config.getProductFixingType());
		assertEquals("/schemas/fpml-5-10.xsd", config.getFpmlSchemaPath());
		assertNotNull(config.getMarketDataProviderToTemplate());
		assertEquals(2, config.getMarketDataProviderToTemplate().size());
	}

	/**
	 * Test that properties are independent of each other.
	 * Setting one property should not affect others.
	 */
	@Test
	void testPropertiesIndependence() {
		// Set some properties
		config.setLiveMarketData(true);
		config.setSettlementCurrency("USD");
		config.setLiveMarketDataProvider("Refinitiv");

		// Verify initial values
		assertTrue(config.isLiveMarketData());
		assertEquals("USD", config.getSettlementCurrency());
		assertEquals("Refinitiv", config.getLiveMarketDataProvider());

		// Change one property
		config.setSettlementCurrency("EUR");

		// Verify only the changed property is affected
		assertTrue(config.isLiveMarketData(), "LiveMarketData should remain unchanged");
		assertEquals("EUR", config.getSettlementCurrency());
		assertEquals("Refinitiv", config.getLiveMarketDataProvider(), "LiveMarketDataProvider should remain unchanged");
	}

	/**
	 * Test map modification after setting.
	 * Verifies that the map reference is stored (not a defensive copy).
	 */
	@Test
	void testMapModificationAfterSetting() {
		Map<String, String> testMap = new HashMap<>();
		testMap.put("Provider1", "template1");

		config.setMarketDataProviderToTemplate(testMap);

		// Get the map and verify initial state
		Map<String, String> retrievedMap = config.getMarketDataProviderToTemplate();
		assertEquals(1, retrievedMap.size());

		// Modify the original map
		testMap.put("Provider2", "template2");

		// The stored map should reflect the change (since it's the same reference)
		assertEquals(2, config.getMarketDataProviderToTemplate().size());
		assertEquals("template2", config.getMarketDataProviderToTemplate().get("Provider2"));
	}

	/**
	 * Test setting map with null values.
	 * Verifies that null values in the map are handled correctly.
	 */
	@Test
	void testMapWithNullValues() {
		Map<String, String> testMap = new HashMap<>();
		testMap.put("Provider1", null);
		testMap.put("Provider2", "template2");

		config.setMarketDataProviderToTemplate(testMap);

		Map<String, String> retrievedMap = config.getMarketDataProviderToTemplate();
		assertEquals(2, retrievedMap.size());
		assertNull(retrievedMap.get("Provider1"), "Provider1 value should be null");
		assertEquals("template2", retrievedMap.get("Provider2"));
	}

	/**
	 * Test boundary conditions for boolean field.
	 * Verifies that the boolean field behaves correctly when toggled multiple times.
	 */
	@Test
	void testBooleanFieldToggling() {
		// Start with default false
		assertFalse(config.isLiveMarketData());

		// Toggle multiple times
		for (int i = 0; i < 5; i++) {
			config.setLiveMarketData(true);
			assertTrue(config.isLiveMarketData(), "Should be true on iteration " + i);

			config.setLiveMarketData(false);
			assertFalse(config.isLiveMarketData(), "Should be false on iteration " + i);
		}
	}
}
