/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */
package net.finmath.smartcontract.valuation.service.utils;

import net.finmath.smartcontract.model.*;
import net.finmath.smartcontract.valuation.implementation.MarginCalculator;
import net.finmath.smartcontract.valuation.marketdata.generators.MarketDataGeneratorLauncher;
import net.finmath.smartcontract.valuation.service.config.RefinitivConfig;
import net.finmath.smartcontract.valuation.service.config.ValuationConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Test class to achieve coverage for SettlementService.initConnectionProperties method.
 *
 * Since initConnectionProperties is a private method, we test it through the public methods
 * that call it. The method is called from retrieveMarketData when:
 * - liveMarketData is true
 * - AND the SDC's market data provider matches the live provider
 *
 * We use mocking to intercept the MarketDataGeneratorLauncher call to verify the connection
 * properties are correctly initialized.
 *
 * @author Claude Code
 */
class SettlementServiceClaude_initConnectionPropertiesTest {

	private SettlementService settlementService;
	private RefinitivConfig refinitivConfig;
	private ValuationConfig valuationConfig;
	private MarginCalculator mockMarginCalculator;

	/**
	 * Set up test fixtures before each test.
	 */
	@BeforeEach
	void setUp() throws Exception {
		refinitivConfig = new RefinitivConfig();
		refinitivConfig.setUser("testUser");
		refinitivConfig.setPassword("testPassword");
		refinitivConfig.setClientId("testClientId");
		refinitivConfig.setHostName("testHost");
		refinitivConfig.setPort(443);
		refinitivConfig.setAuthUrl("https://test.auth.url");
		refinitivConfig.setUseProxy("false");
		refinitivConfig.setProxyHost("proxyHost");
		refinitivConfig.setProxyPort(8080);
		refinitivConfig.setProxyUser("proxyUser");
		refinitivConfig.setProxyPassword("proxyPassword");

		valuationConfig = new ValuationConfig();
		valuationConfig.setLiveMarketData(true); // Must be true to trigger initConnectionProperties
		valuationConfig.setSettlementCurrency("EUR");
		valuationConfig.setLiveMarketDataProvider("refinitiv"); // Match provider in smartderivativecontract_with_rics.xml
		valuationConfig.setInternalMarketDataProvider("internal");
		valuationConfig.setProductFixingType("Fixing");

		settlementService = new SettlementService(refinitivConfig, valuationConfig);

		// Create mock MarginCalculator
		mockMarginCalculator = mock(MarginCalculator.class);

		// Use reflection to inject the mock MarginCalculator
		Field marginCalculatorField = SettlementService.class.getDeclaredField("marginCalculator");
		marginCalculatorField.setAccessible(true);
		marginCalculatorField.set(settlementService, mockMarginCalculator);
	}

	/**
	 * Helper method to load XML file from resources.
	 */
	private String loadResourceFile(String filename) throws IOException {
		String path = "src/main/resources/" + filename;
		return Files.readString(Paths.get(path));
	}

	/**
	 * Test initConnectionProperties through generateInitialSettlementResult with live market data.
	 * This covers lines 196-209 (successful path).
	 */
	@Test
	void testInitConnectionProperties_ThroughInitialSettlement_WithLiveData_Success() throws Exception {
		// Arrange
		String tradeData = loadResourceFile("net.finmath.smartcontract.product.xml/smartderivativecontract_with_rics.xml");

		InitialSettlementRequest request = new InitialSettlementRequest();
		request.setTradeData(tradeData);
		request.setNewProvidedMarketData(null); // Null to trigger retrieveMarketData

		// Mock the MarginCalculator responses
		ValueResult mockValueResult = new ValueResult();
		mockValueResult.setValue(BigDecimal.valueOf(1000.00));

		when(mockMarginCalculator.getValueAtEvaluationTime(anyString(), anyString(), any(LocalDateTime.class)))
				.thenReturn(mockValueResult);
		when(mockMarginCalculator.getValue(anyString(), anyString()))
				.thenReturn(mockValueResult);

		// Use MockedStatic to mock the MarketDataGeneratorLauncher
		try (MockedStatic<MarketDataGeneratorLauncher> mockedLauncher = mockStatic(MarketDataGeneratorLauncher.class)) {
			MarketDataList mockMarketDataList = new MarketDataList();
			mockMarketDataList.setRequestTimeStamp(LocalDateTime.now());

			// Capture the Properties passed to instantiateMarketDataGeneratorWebsocket
			mockedLauncher.when(() -> MarketDataGeneratorLauncher.instantiateMarketDataGeneratorWebsocket(
					any(Properties.class),
					any()))
				.thenAnswer(invocation -> {
					Properties props = invocation.getArgument(0);

					// Assert that all connection properties are correctly set (lines 196-207)
					assertNotNull(props, "Connection properties should not be null");
					assertEquals("testUser", props.get("USER"), "USER should be set from config");
					assertEquals("testPassword", props.get("PASSWORD"), "PASSWORD should be set from config");
					assertEquals("testClientId", props.get("CLIENTID"), "CLIENTID should be set from config");
					assertEquals("testHost", props.get("HOSTNAME"), "HOSTNAME should be set from config");
					assertEquals(443, props.get("PORT"), "PORT should be set from config");
					assertEquals("https://test.auth.url", props.get("AUTHURL"), "AUTHURL should be set from config");
					assertEquals("false", props.get("USEPROXY"), "USEPROXY should be set from config");
					assertEquals("proxyHost", props.get("PROXYHOST"), "PROXYHOST should be set from config");
					assertEquals(8080, props.get("PROXYPORT"), "PROXYPORT should be set from config");
					assertEquals("proxyUser", props.get("PROXYUSER"), "PROXYUSER should be set from config");
					assertEquals("proxyPassword", props.get("PROXYPASS"), "PROXYPASS should be set from config");

					return mockMarketDataList;
				});

			// Act
			InitialSettlementResult result = settlementService.generateInitialSettlementResult(request);

			// Assert
			assertNotNull(result, "Result should not be null");

			// Verify that instantiateMarketDataGeneratorWebsocket was called (meaning initConnectionProperties was called)
			mockedLauncher.verify(() ->
				MarketDataGeneratorLauncher.instantiateMarketDataGeneratorWebsocket(any(Properties.class), any()),
				times(1));
		}
	}

	/**
	 * Test initConnectionProperties through generateRegularSettlementResult with live market data.
	 * This provides additional coverage of lines 196-209.
	 */
	@Test
	void testInitConnectionProperties_ThroughRegularSettlement_WithLiveData_Success() throws Exception {
		// Arrange
		String tradeData = loadResourceFile("net.finmath.smartcontract.product.xml/smartderivativecontract_with_rics.xml");
		String settlementLast = loadResourceFile("net/finmath/smartcontract/valuation/client/settlement_testset_initial.xml");

		RegularSettlementRequest request = new RegularSettlementRequest();
		request.setTradeData(tradeData);
		request.setSettlementLast(settlementLast);
		request.setNewProvidedMarketData(null); // Null to trigger retrieveMarketData

		// Mock the MarginCalculator responses
		ValueResult mockValueResult = new ValueResult();
		mockValueResult.setValue(BigDecimal.valueOf(2000.00));

		Map<String, BigDecimal> mockMarginValues = new HashMap<>();
		mockMarginValues.put("value", BigDecimal.valueOf(500.00));

		when(mockMarginCalculator.getValueAtEvaluationTime(anyString(), anyString(), any(LocalDateTime.class)))
				.thenReturn(mockValueResult);
		when(mockMarginCalculator.getValue(anyString(), anyString()))
				.thenReturn(mockValueResult);
		when(mockMarginCalculator.getValues(anyString(), anyString(), anyString()))
				.thenReturn(mockMarginValues);

		// Use MockedStatic to mock the MarketDataGeneratorLauncher
		try (MockedStatic<MarketDataGeneratorLauncher> mockedLauncher = mockStatic(MarketDataGeneratorLauncher.class)) {
			MarketDataList mockMarketDataList = new MarketDataList();
			mockMarketDataList.setRequestTimeStamp(LocalDateTime.now());

			// Capture and verify the Properties
			mockedLauncher.when(() -> MarketDataGeneratorLauncher.instantiateMarketDataGeneratorWebsocket(
					any(Properties.class),
					any()))
				.thenAnswer(invocation -> {
					Properties props = invocation.getArgument(0);

					// Verify all properties are correctly initialized
					assertEquals(11, props.size(), "Should have 11 connection properties");
					assertTrue(props.containsKey("USER"), "Should contain USER key");
					assertTrue(props.containsKey("PASSWORD"), "Should contain PASSWORD key");
					assertTrue(props.containsKey("CLIENTID"), "Should contain CLIENTID key");
					assertTrue(props.containsKey("HOSTNAME"), "Should contain HOSTNAME key");
					assertTrue(props.containsKey("PORT"), "Should contain PORT key");
					assertTrue(props.containsKey("AUTHURL"), "Should contain AUTHURL key");
					assertTrue(props.containsKey("USEPROXY"), "Should contain USEPROXY key");
					assertTrue(props.containsKey("PROXYHOST"), "Should contain PROXYHOST key");
					assertTrue(props.containsKey("PROXYPORT"), "Should contain PROXYPORT key");
					assertTrue(props.containsKey("PROXYUSER"), "Should contain PROXYUSER key");
					assertTrue(props.containsKey("PROXYPASS"), "Should contain PROXYPASS key");

					return mockMarketDataList;
				});

			// Act
			RegularSettlementResult result = settlementService.generateRegularSettlementResult(request);

			// Assert
			assertNotNull(result, "Result should not be null");

			// Verify that initConnectionProperties was called via the launcher
			mockedLauncher.verify(() ->
				MarketDataGeneratorLauncher.instantiateMarketDataGeneratorWebsocket(any(Properties.class), any()),
				times(1));
		}
	}

	/**
	 * Test initConnectionProperties with null RefinitivConfig values to cover catch block.
	 * This covers lines 210-212 (exception handling path).
	 */
	@Test
	void testInitConnectionProperties_WithNullConfig_ThrowsException() throws Exception {
		// Arrange - Create config with null values to trigger NullPointerException
		RefinitivConfig nullConfig = new RefinitivConfig();
		// Don't set any values - they will be null

		ValuationConfig validValuationConfig = new ValuationConfig();
		validValuationConfig.setLiveMarketData(true);
		validValuationConfig.setSettlementCurrency("EUR");
		validValuationConfig.setLiveMarketDataProvider("refinitiv");
		validValuationConfig.setInternalMarketDataProvider("internal");
		validValuationConfig.setProductFixingType("Fixing");

		SettlementService serviceWithNullConfig = new SettlementService(nullConfig, validValuationConfig);

		// Inject mock calculator
		Field marginCalculatorField = SettlementService.class.getDeclaredField("marginCalculator");
		marginCalculatorField.setAccessible(true);
		marginCalculatorField.set(serviceWithNullConfig, mockMarginCalculator);

		String tradeData = loadResourceFile("net.finmath.smartcontract.product.xml/smartderivativecontract_with_rics.xml");

		InitialSettlementRequest request = new InitialSettlementRequest();
		request.setTradeData(tradeData);
		request.setNewProvidedMarketData(null);

		// Mock the MarginCalculator
		ValueResult mockValueResult = new ValueResult();
		mockValueResult.setValue(BigDecimal.valueOf(1000.00));
		when(mockMarginCalculator.getValue(anyString(), anyString())).thenReturn(mockValueResult);

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class,
			() -> serviceWithNullConfig.generateInitialSettlementResult(request),
			"Should throw SDCException when RefinitivConfig has null values");

		// Verify the exception details (lines 211-212)
		assertEquals(ExceptionId.SDC_NO_DATA_FOUND, exception.getId(),
			"Exception should have SDC_NO_DATA_FOUND id");
		assertTrue(exception.getMessage().contains("missing connection properties"),
			"Exception message should mention missing connection properties");
		assertEquals(400, exception.getStatusCode(), "Exception should have 400 status code");
	}

	/**
	 * Test that all individual RefinitivConfig properties are correctly transferred to Properties object.
	 */
	@Test
	void testInitConnectionProperties_AllPropertiesTransferred() throws Exception {
		// Arrange - Use specific values to verify each property
		RefinitivConfig customConfig = new RefinitivConfig();
		customConfig.setUser("user123");
		customConfig.setPassword("pass456");
		customConfig.setClientId("client789");
		customConfig.setHostName("host.example.com");
		customConfig.setPort(9443);
		customConfig.setAuthUrl("https://auth.example.com/v1");
		customConfig.setUseProxy("true");
		customConfig.setProxyHost("proxy.example.com");
		customConfig.setProxyPort(3128);
		customConfig.setProxyUser("proxyuser");
		customConfig.setProxyPassword("proxypass");

		ValuationConfig customValuationConfig = new ValuationConfig();
		customValuationConfig.setLiveMarketData(true);
		customValuationConfig.setSettlementCurrency("USD");
		customValuationConfig.setLiveMarketDataProvider("refinitiv");
		customValuationConfig.setInternalMarketDataProvider("internal");
		customValuationConfig.setProductFixingType("Fixing");

		SettlementService customService = new SettlementService(customConfig, customValuationConfig);

		// Inject mock calculator
		Field marginCalculatorField = SettlementService.class.getDeclaredField("marginCalculator");
		marginCalculatorField.setAccessible(true);
		marginCalculatorField.set(customService, mockMarginCalculator);

		String tradeData = loadResourceFile("net.finmath.smartcontract.product.xml/smartderivativecontract_with_rics.xml");

		InitialSettlementRequest request = new InitialSettlementRequest();
		request.setTradeData(tradeData);
		request.setNewProvidedMarketData(null);

		// Mock the MarginCalculator
		ValueResult mockValueResult = new ValueResult();
		mockValueResult.setValue(BigDecimal.valueOf(1000.00));
		when(mockMarginCalculator.getValueAtEvaluationTime(anyString(), anyString(), any(LocalDateTime.class)))
				.thenReturn(mockValueResult);
		when(mockMarginCalculator.getValue(anyString(), anyString()))
				.thenReturn(mockValueResult);

		// Use MockedStatic to verify exact property values
		try (MockedStatic<MarketDataGeneratorLauncher> mockedLauncher = mockStatic(MarketDataGeneratorLauncher.class)) {
			MarketDataList mockMarketDataList = new MarketDataList();
			mockMarketDataList.setRequestTimeStamp(LocalDateTime.now());

			mockedLauncher.when(() -> MarketDataGeneratorLauncher.instantiateMarketDataGeneratorWebsocket(
					any(Properties.class),
					any()))
				.thenAnswer(invocation -> {
					Properties props = invocation.getArgument(0);

					// Verify each specific value (lines 197-207)
					assertEquals("user123", props.get("USER"));
					assertEquals("pass456", props.get("PASSWORD"));
					assertEquals("client789", props.get("CLIENTID"));
					assertEquals("host.example.com", props.get("HOSTNAME"));
					assertEquals(9443, props.get("PORT"));
					assertEquals("https://auth.example.com/v1", props.get("AUTHURL"));
					assertEquals("true", props.get("USEPROXY"));
					assertEquals("proxy.example.com", props.get("PROXYHOST"));
					assertEquals(3128, props.get("PROXYPORT"));
					assertEquals("proxyuser", props.get("PROXYUSER"));
					assertEquals("proxypass", props.get("PROXYPASS"));

					return mockMarketDataList;
				});

			// Act
			InitialSettlementResult result = customService.generateInitialSettlementResult(request);

			// Assert
			assertNotNull(result, "Result should not be null");
			mockedLauncher.verify(() ->
				MarketDataGeneratorLauncher.instantiateMarketDataGeneratorWebsocket(any(Properties.class), any()),
				times(1));
		}
	}

	/**
	 * Test that Properties object is correctly instantiated (line 196).
	 */
	@Test
	void testInitConnectionProperties_PropertiesObjectCreated() throws Exception {
		// Arrange
		String tradeData = loadResourceFile("net.finmath.smartcontract.product.xml/smartderivativecontract_with_rics.xml");

		InitialSettlementRequest request = new InitialSettlementRequest();
		request.setTradeData(tradeData);
		request.setNewProvidedMarketData(null);

		ValueResult mockValueResult = new ValueResult();
		mockValueResult.setValue(BigDecimal.valueOf(1000.00));
		when(mockMarginCalculator.getValueAtEvaluationTime(anyString(), anyString(), any(LocalDateTime.class)))
				.thenReturn(mockValueResult);
		when(mockMarginCalculator.getValue(anyString(), anyString()))
				.thenReturn(mockValueResult);

		try (MockedStatic<MarketDataGeneratorLauncher> mockedLauncher = mockStatic(MarketDataGeneratorLauncher.class)) {
			MarketDataList mockMarketDataList = new MarketDataList();
			mockMarketDataList.setRequestTimeStamp(LocalDateTime.now());

			mockedLauncher.when(() -> MarketDataGeneratorLauncher.instantiateMarketDataGeneratorWebsocket(
					any(Properties.class),
					any()))
				.thenAnswer(invocation -> {
					Properties props = invocation.getArgument(0);

					// Verify Properties instance is created (line 196) and returned (line 209)
					assertNotNull(props, "Properties object should be created");
					assertTrue(props instanceof Properties, "Should be a Properties instance");
					assertFalse(props.isEmpty(), "Properties should not be empty");

					return mockMarketDataList;
				});

			// Act
			settlementService.generateInitialSettlementResult(request);

			// Verify initConnectionProperties was invoked
			mockedLauncher.verify(() ->
				MarketDataGeneratorLauncher.instantiateMarketDataGeneratorWebsocket(any(Properties.class), any()),
				times(1));
		}
	}
}
