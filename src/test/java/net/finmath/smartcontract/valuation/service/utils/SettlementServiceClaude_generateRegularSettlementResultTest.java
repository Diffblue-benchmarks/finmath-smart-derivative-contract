/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */
package net.finmath.smartcontract.valuation.service.utils;

import net.finmath.smartcontract.model.MarketDataList;
import net.finmath.smartcontract.model.RegularSettlementRequest;
import net.finmath.smartcontract.model.RegularSettlementResult;
import net.finmath.smartcontract.model.ValueResult;
import net.finmath.smartcontract.product.xml.SDCXMLParser;
import net.finmath.smartcontract.valuation.implementation.MarginCalculator;
import net.finmath.smartcontract.valuation.service.config.RefinitivConfig;
import net.finmath.smartcontract.valuation.service.config.ValuationConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Test class for SettlementService.generateRegularSettlementResult method.
 * Uses real XML files and mocking of MarginCalculator to achieve coverage.
 *
 * Note: We use reflection to inject a mocked MarginCalculator because the calculator has complex
 * dependencies on financial calculation libraries. Testing without mocking would require a full
 * integration test environment. The focus here is on testing the SettlementService's logic for
 * the generateRegularSettlementResult method.
 *
 * @author Claude Code
 */
class SettlementServiceClaude_generateRegularSettlementResultTest {

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
		valuationConfig.setLiveMarketData(false);
		valuationConfig.setSettlementCurrency("EUR");
		valuationConfig.setLiveMarketDataProvider("Reuters");
		valuationConfig.setInternalMarketDataProvider("internal");
		valuationConfig.setProductFixingType("Fixing");

		settlementService = new SettlementService(refinitivConfig, valuationConfig);

		// Create mock MarginCalculator
		mockMarginCalculator = mock(MarginCalculator.class);

		// Use reflection to inject the mock MarginCalculator
		// This is necessary because MarginCalculator has complex financial calculation dependencies
		// that would require full integration test setup. We're focusing on testing SettlementService logic.
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
	 * Test generateRegularSettlementResult with provided market data (newProvidedMarketData != null).
	 * This covers lines 61-66 (the else branch).
	 */
	@Test
	void testGenerateRegularSettlementResult_WithProvidedMarketData_Success() throws Exception {
		// Arrange
		String tradeData = loadResourceFile("net.finmath.smartcontract.product.xml/smartderivativecontract_simulated_historical_marketdata.xml");
		String settlementLast = loadResourceFile("net/finmath/smartcontract/valuation/client/settlement_testset_initial_historical.xml");
		String marketData = loadResourceFile("net/finmath/smartcontract/valuation/client/md_historical_test_from_initial.xml");

		RegularSettlementRequest request = new RegularSettlementRequest();
		request.setTradeData(tradeData);
		request.setSettlementLast(settlementLast);
		request.setNewProvidedMarketData(marketData);

		// Mock the MarginCalculator responses
		ValueResult mockValueResult = new ValueResult();
		mockValueResult.setValue(BigDecimal.valueOf(1000.50));

		Map<String, BigDecimal> mockMarginValues = new HashMap<>();
		mockMarginValues.put("value", BigDecimal.valueOf(123.45));
		mockMarginValues.put("initialMargin", BigDecimal.valueOf(50.0));

		when(mockMarginCalculator.getValueAtEvaluationTime(anyString(), anyString(), any(LocalDateTime.class)))
				.thenReturn(mockValueResult);
		when(mockMarginCalculator.getValue(anyString(), anyString()))
				.thenReturn(mockValueResult);
		when(mockMarginCalculator.getValues(anyString(), anyString(), anyString()))
				.thenReturn(mockMarginValues);

		// Act
		RegularSettlementResult result = settlementService.generateRegularSettlementResult(request);

		// Assert
		assertNotNull(result, "Result should not be null");
		assertNotNull(result.getGeneratedRegularSettlement(), "Generated settlement should not be null");
		assertEquals("EUR", result.getCurrency(), "Currency should match config");
		assertEquals(BigDecimal.valueOf(123.45), result.getMarginValue(), "Margin value should match");
		assertNotNull(result.getValuationDate(), "Valuation date should not be null");
		assertTrue(result.getValuationDate().matches("\\d{8}-\\d{6}"),
				"Valuation date should match yyyyMMdd-HHmmss format");

		// Verify that the else branch was taken (provided market data)
		verify(mockMarginCalculator, times(1)).getValueAtEvaluationTime(anyString(), anyString(), any(LocalDateTime.class));
		verify(mockMarginCalculator, times(1)).getValue(anyString(), anyString());
		verify(mockMarginCalculator, times(1)).getValues(anyString(), anyString(), anyString());
	}

	/**
	 * Test generateRegularSettlementResult without provided market data (newProvidedMarketData == null).
	 * This covers lines 57-59 (the if branch with retrieveMarketData).
	 */
	@Test
	void testGenerateRegularSettlementResult_WithoutProvidedMarketData_Success() throws Exception {
		// Arrange
		String tradeData = loadResourceFile("net.finmath.smartcontract.product.xml/smartderivativecontract_simulated_historical_marketdata.xml");
		String settlementLast = loadResourceFile("net/finmath/smartcontract/valuation/client/settlement_testset_initial_historical.xml");

		RegularSettlementRequest request = new RegularSettlementRequest();
		request.setTradeData(tradeData);
		request.setSettlementLast(settlementLast);
		request.setNewProvidedMarketData(null); // Explicitly null to trigger retrieveMarketData path

		// Mock the MarginCalculator responses
		ValueResult mockValueResult = new ValueResult();
		mockValueResult.setValue(BigDecimal.valueOf(2000.75));

		Map<String, BigDecimal> mockMarginValues = new HashMap<>();
		mockMarginValues.put("value", BigDecimal.valueOf(456.78));
		mockMarginValues.put("initialMargin", BigDecimal.valueOf(100.0));

		when(mockMarginCalculator.getValueAtEvaluationTime(anyString(), anyString(), any(LocalDateTime.class)))
				.thenReturn(mockValueResult);
		when(mockMarginCalculator.getValue(anyString(), anyString()))
				.thenReturn(mockValueResult);
		when(mockMarginCalculator.getValues(anyString(), anyString(), anyString()))
				.thenReturn(mockMarginValues);

		// Act
		RegularSettlementResult result = settlementService.generateRegularSettlementResult(request);

		// Assert
		assertNotNull(result, "Result should not be null");
		assertNotNull(result.getGeneratedRegularSettlement(), "Generated settlement should not be null");
		assertEquals("EUR", result.getCurrency(), "Currency should match config");
		assertEquals(BigDecimal.valueOf(456.78), result.getMarginValue(), "Margin value should match");
		assertNotNull(result.getValuationDate(), "Valuation date should not be null");

		// Verify margin calculator was called
		verify(mockMarginCalculator, atLeastOnce()).getValueAtEvaluationTime(anyString(), anyString(), any(LocalDateTime.class));
		verify(mockMarginCalculator, atLeastOnce()).getValue(anyString(), anyString());
		verify(mockMarginCalculator, atLeastOnce()).getValues(anyString(), anyString(), anyString());
	}

	/**
	 * Test that result contains correct settlement XML structure.
	 */
	@Test
	void testGenerateRegularSettlementResult_GeneratesValidSettlementXml() throws Exception {
		// Arrange
		String tradeData = loadResourceFile("net.finmath.smartcontract.product.xml/smartderivativecontract_simulated_historical_marketdata.xml");
		String settlementLast = loadResourceFile("net/finmath/smartcontract/valuation/client/settlement_testset_initial_historical.xml");
		String marketData = loadResourceFile("net/finmath/smartcontract/valuation/client/md_historical_test_from_initial.xml");

		RegularSettlementRequest request = new RegularSettlementRequest();
		request.setTradeData(tradeData);
		request.setSettlementLast(settlementLast);
		request.setNewProvidedMarketData(marketData);

		// Mock the MarginCalculator responses
		ValueResult mockValueResult = new ValueResult();
		mockValueResult.setValue(BigDecimal.valueOf(5000.00));

		Map<String, BigDecimal> mockMarginValues = new HashMap<>();
		mockMarginValues.put("value", BigDecimal.valueOf(789.12));

		when(mockMarginCalculator.getValueAtEvaluationTime(anyString(), anyString(), any(LocalDateTime.class)))
				.thenReturn(mockValueResult);
		when(mockMarginCalculator.getValue(anyString(), anyString()))
				.thenReturn(mockValueResult);
		when(mockMarginCalculator.getValues(anyString(), anyString(), anyString()))
				.thenReturn(mockMarginValues);

		// Act
		RegularSettlementResult result = settlementService.generateRegularSettlementResult(request);

		// Assert
		String settlementXml = result.getGeneratedRegularSettlement();
		assertNotNull(settlementXml, "Settlement XML should not be null");
		assertTrue(settlementXml.contains("<?xml"), "Should be valid XML");
		assertTrue(settlementXml.contains("<settlement>"), "Should contain settlement element");
		assertTrue(settlementXml.contains("ID-historical123"), "Should contain trade ID from test file");
	}

	/**
	 * Test that valuation date format is correct.
	 */
	@Test
	void testGenerateRegularSettlementResult_ValuationDateFormat() throws Exception {
		// Arrange
		String tradeData = loadResourceFile("net.finmath.smartcontract.product.xml/smartderivativecontract_simulated_historical_marketdata.xml");
		String settlementLast = loadResourceFile("net/finmath/smartcontract/valuation/client/settlement_testset_initial_historical.xml");
		String marketData = loadResourceFile("net/finmath/smartcontract/valuation/client/md_historical_test_from_initial.xml");

		RegularSettlementRequest request = new RegularSettlementRequest();
		request.setTradeData(tradeData);
		request.setSettlementLast(settlementLast);
		request.setNewProvidedMarketData(marketData);

		// Mock the MarginCalculator responses
		ValueResult mockValueResult = new ValueResult();
		mockValueResult.setValue(BigDecimal.valueOf(100.00));

		Map<String, BigDecimal> mockMarginValues = new HashMap<>();
		mockMarginValues.put("value", BigDecimal.valueOf(50.00));

		when(mockMarginCalculator.getValueAtEvaluationTime(anyString(), anyString(), any(LocalDateTime.class)))
				.thenReturn(mockValueResult);
		when(mockMarginCalculator.getValue(anyString(), anyString()))
				.thenReturn(mockValueResult);
		when(mockMarginCalculator.getValues(anyString(), anyString(), anyString()))
				.thenReturn(mockMarginValues);

		// Act
		RegularSettlementResult result = settlementService.generateRegularSettlementResult(request);

		// Assert
		String valuationDate = result.getValuationDate();
		assertNotNull(valuationDate, "Valuation date should not be null");
		assertEquals(15, valuationDate.length(), "Valuation date should be 15 characters (yyyyMMdd-HHmmss)");
		assertTrue(valuationDate.matches("\\d{8}-\\d{6}"), "Valuation date should match pattern");

		// Verify it's close to current time
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
		LocalDateTime parsedDate = LocalDateTime.parse(valuationDate, formatter);
		LocalDateTime now = LocalDateTime.now();
		assertTrue(parsedDate.isBefore(now.plusMinutes(1)) && parsedDate.isAfter(now.minusMinutes(1)),
				"Valuation date should be close to current time");
	}

	/**
	 * Test that margin value from calculator is correctly used in result.
	 */
	@Test
	void testGenerateRegularSettlementResult_UsesMarginValueFromCalculator() throws Exception {
		// Arrange
		String tradeData = loadResourceFile("net.finmath.smartcontract.product.xml/smartderivativecontract_simulated_historical_marketdata.xml");
		String settlementLast = loadResourceFile("net/finmath/smartcontract/valuation/client/settlement_testset_initial_historical.xml");
		String marketData = loadResourceFile("net/finmath/smartcontract/valuation/client/md_historical_test_from_initial.xml");

		RegularSettlementRequest request = new RegularSettlementRequest();
		request.setTradeData(tradeData);
		request.setSettlementLast(settlementLast);
		request.setNewProvidedMarketData(marketData);

		// Mock with specific margin value
		BigDecimal expectedMargin = new BigDecimal("9876.54");
		ValueResult mockValueResult = new ValueResult();
		mockValueResult.setValue(BigDecimal.valueOf(100.00));

		Map<String, BigDecimal> mockMarginValues = new HashMap<>();
		mockMarginValues.put("value", expectedMargin);
		mockMarginValues.put("additionalInfo", BigDecimal.valueOf(123.45));

		when(mockMarginCalculator.getValueAtEvaluationTime(anyString(), anyString(), any(LocalDateTime.class)))
				.thenReturn(mockValueResult);
		when(mockMarginCalculator.getValue(anyString(), anyString()))
				.thenReturn(mockValueResult);
		when(mockMarginCalculator.getValues(anyString(), anyString(), anyString()))
				.thenReturn(mockMarginValues);

		// Act
		RegularSettlementResult result = settlementService.generateRegularSettlementResult(request);

		// Assert
		assertEquals(expectedMargin, result.getMarginValue(),
				"Result margin value should match the 'value' key from calculator");
	}

	/**
	 * Test that currency from config is used in result.
	 */
	@Test
	void testGenerateRegularSettlementResult_UsesCurrencyFromConfig() throws Exception {
		// Arrange - set custom currency
		valuationConfig.setSettlementCurrency("USD");
		settlementService = new SettlementService(refinitivConfig, valuationConfig);

		// Re-inject mock calculator
		Field marginCalculatorField = SettlementService.class.getDeclaredField("marginCalculator");
		marginCalculatorField.setAccessible(true);
		marginCalculatorField.set(settlementService, mockMarginCalculator);

		String tradeData = loadResourceFile("net.finmath.smartcontract.product.xml/smartderivativecontract_simulated_historical_marketdata.xml");
		String settlementLast = loadResourceFile("net/finmath/smartcontract/valuation/client/settlement_testset_initial_historical.xml");
		String marketData = loadResourceFile("net/finmath/smartcontract/valuation/client/md_historical_test_from_initial.xml");

		RegularSettlementRequest request = new RegularSettlementRequest();
		request.setTradeData(tradeData);
		request.setSettlementLast(settlementLast);
		request.setNewProvidedMarketData(marketData);

		// Mock the MarginCalculator responses
		ValueResult mockValueResult = new ValueResult();
		mockValueResult.setValue(BigDecimal.valueOf(100.00));

		Map<String, BigDecimal> mockMarginValues = new HashMap<>();
		mockMarginValues.put("value", BigDecimal.valueOf(50.00));

		when(mockMarginCalculator.getValueAtEvaluationTime(anyString(), anyString(), any(LocalDateTime.class)))
				.thenReturn(mockValueResult);
		when(mockMarginCalculator.getValue(anyString(), anyString()))
				.thenReturn(mockValueResult);
		when(mockMarginCalculator.getValues(anyString(), anyString(), anyString()))
				.thenReturn(mockMarginValues);

		// Act
		RegularSettlementResult result = settlementService.generateRegularSettlementResult(request);

		// Assert
		assertEquals("USD", result.getCurrency(), "Result should use currency from config");
	}

	/**
	 * Test with negative margin value.
	 */
	@Test
	void testGenerateRegularSettlementResult_WithNegativeMargin() throws Exception {
		// Arrange
		String tradeData = loadResourceFile("net.finmath.smartcontract.product.xml/smartderivativecontract_simulated_historical_marketdata.xml");
		String settlementLast = loadResourceFile("net/finmath/smartcontract/valuation/client/settlement_testset_initial_historical.xml");
		String marketData = loadResourceFile("net/finmath/smartcontract/valuation/client/md_historical_test_from_initial.xml");

		RegularSettlementRequest request = new RegularSettlementRequest();
		request.setTradeData(tradeData);
		request.setSettlementLast(settlementLast);
		request.setNewProvidedMarketData(marketData);

		// Mock with negative margin
		BigDecimal negativeMargin = new BigDecimal("-500.50");
		ValueResult mockValueResult = new ValueResult();
		mockValueResult.setValue(BigDecimal.valueOf(100.00));

		Map<String, BigDecimal> mockMarginValues = new HashMap<>();
		mockMarginValues.put("value", negativeMargin);

		when(mockMarginCalculator.getValueAtEvaluationTime(anyString(), anyString(), any(LocalDateTime.class)))
				.thenReturn(mockValueResult);
		when(mockMarginCalculator.getValue(anyString(), anyString()))
				.thenReturn(mockValueResult);
		when(mockMarginCalculator.getValues(anyString(), anyString(), anyString()))
				.thenReturn(mockMarginValues);

		// Act
		RegularSettlementResult result = settlementService.generateRegularSettlementResult(request);

		// Assert
		assertEquals(negativeMargin, result.getMarginValue(),
				"Result should handle negative margin values");
		assertNotNull(result.getGeneratedRegularSettlement(),
				"Settlement should be generated even with negative margin");
	}

	/**
	 * Test with zero margin value.
	 */
	@Test
	void testGenerateRegularSettlementResult_WithZeroMargin() throws Exception {
		// Arrange
		String tradeData = loadResourceFile("net.finmath.smartcontract.product.xml/smartderivativecontract_simulated_historical_marketdata.xml");
		String settlementLast = loadResourceFile("net/finmath/smartcontract/valuation/client/settlement_testset_initial_historical.xml");
		String marketData = loadResourceFile("net/finmath/smartcontract/valuation/client/md_historical_test_from_initial.xml");

		RegularSettlementRequest request = new RegularSettlementRequest();
		request.setTradeData(tradeData);
		request.setSettlementLast(settlementLast);
		request.setNewProvidedMarketData(marketData);

		// Mock with zero margin
		ValueResult mockValueResult = new ValueResult();
		mockValueResult.setValue(BigDecimal.valueOf(100.00));

		Map<String, BigDecimal> mockMarginValues = new HashMap<>();
		mockMarginValues.put("value", BigDecimal.ZERO);

		when(mockMarginCalculator.getValueAtEvaluationTime(anyString(), anyString(), any(LocalDateTime.class)))
				.thenReturn(mockValueResult);
		when(mockMarginCalculator.getValue(anyString(), anyString()))
				.thenReturn(mockValueResult);
		when(mockMarginCalculator.getValues(anyString(), anyString(), anyString()))
				.thenReturn(mockMarginValues);

		// Act
		RegularSettlementResult result = settlementService.generateRegularSettlementResult(request);

		// Assert
		assertEquals(BigDecimal.ZERO, result.getMarginValue(),
				"Result should handle zero margin value");
		assertNotNull(result.getGeneratedRegularSettlement(),
				"Settlement should be generated with zero margin");
	}
}
