/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */

package net.finmath.smartcontract.valuation.marketdata.utils;

import net.finmath.smartcontract.model.MarketDataList;
import net.finmath.smartcontract.product.xml.SDCXMLParser;
import net.finmath.smartcontract.product.xml.Smartderivativecontract;
import net.finmath.smartcontract.valuation.marketdata.data.MarketDataPoint;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for MarketDataCheck.
 * Tests the checkMarketData method with focus on branch and condition coverage.
 *
 * @author Claude Code
 */
class MarketDataCheckClaudeTest {

	/**
	 * Helper method to load SDC from XML file.
	 */
	private Smartderivativecontract loadSDCFromFile(String filename) throws Exception {
		ClassPathResource resource = new ClassPathResource(filename);
		String xmlContent = new String(Files.readAllBytes(resource.getFile().toPath()));
		return SDCXMLParser.unmarshalXml(xmlContent, Smartderivativecontract.class);
	}

	/**
	 * Test checkMarketData when the marketDataList has no data (empty points list equals new MarketDataList points).
	 * This tests lines 18-25 where hasErrors=true and errorMessage is set.
	 */
	@Test
	void testCheckMarketData_EmptyMarketDataList() throws Exception {
		// Arrange
		MarketDataList emptyMarketDataList = new MarketDataList();
		Smartderivativecontract sdc = loadSDCFromFile("net.finmath.smartcontract.product.xml/smartderivativecontract.xml");

		// Act
		MarketDataErrors errors = MarketDataCheck.checkMarketData(emptyMarketDataList, sdc);

		// Assert
		assertTrue(errors.hasErrors(), "Should have errors when market data list is empty");
		assertEquals("error in marketData service - no data generated", errors.getErrorMessage());
		assertEquals(1, errors.getMissingDataPoints().size());
		assertEquals("all, no data provided", errors.getMissingDataPoints().get(0));
	}

	/**
	 * Test checkMarketData when all required market data points are present.
	 * This tests lines 28-54 where hasAllIDs remains true and returns hasErrors=false.
	 */
	@Test
	void testCheckMarketData_AllDataPresent() throws Exception {
		// Arrange
		Smartderivativecontract sdc = loadSDCFromFile("net.finmath.smartcontract.product.xml/smartderivativecontract.xml");

		MarketDataList marketDataList = new MarketDataList();
		LocalDateTime timestamp = LocalDateTime.now();

		// Add all required market data points based on the SDC
		for (Smartderivativecontract.Settlement.Marketdata.Marketdataitems.Item item :
				sdc.getSettlement().getMarketdata().getMarketdataitems().getItem()) {
			String symbol = item.getSymbol().get(0);
			marketDataList.add(new MarketDataPoint(symbol, 0.05, timestamp));
		}

		// Act
		MarketDataErrors errors = MarketDataCheck.checkMarketData(marketDataList, sdc);

		// Assert
		assertFalse(errors.hasErrors(), "Should not have errors when all data is present");
		assertNull(errors.getErrorMessage());
		assertTrue(errors.getMissingDataPoints().isEmpty());
	}

	/**
	 * Test checkMarketData when one required market data point is missing.
	 * This tests lines 30-39 where hasAllIDs becomes false and missingData is added.
	 */
	@Test
	void testCheckMarketData_OneMissingDataPoint() throws Exception {
		// Arrange
		Smartderivativecontract sdc = loadSDCFromFile("net.finmath.smartcontract.product.xml/smartderivativecontract.xml");

		MarketDataList marketDataList = new MarketDataList();
		LocalDateTime timestamp = LocalDateTime.now();

		List<Smartderivativecontract.Settlement.Marketdata.Marketdataitems.Item> items =
				sdc.getSettlement().getMarketdata().getMarketdataitems().getItem();

		// Add all but the first required market data point
		for (int i = 1; i < items.size(); i++) {
			String symbol = items.get(i).getSymbol().get(0);
			marketDataList.add(new MarketDataPoint(symbol, 0.05, timestamp));
		}

		// Get the first symbol which we're not adding
		String missingSymbol = items.get(0).getSymbol().get(0);

		// Act
		MarketDataErrors errors = MarketDataCheck.checkMarketData(marketDataList, sdc);

		// Assert
		assertTrue(errors.hasErrors(), "Should have errors when data point is missing");
		assertEquals("error in marketData service - missing points in marketData", errors.getErrorMessage());
		assertEquals(1, errors.getMissingDataPoints().size());
		assertEquals(missingSymbol, errors.getMissingDataPoints().get(0));
	}

	/**
	 * Test checkMarketData when multiple required market data points are missing.
	 * This tests lines 30-47 where hasAllIDs becomes false, counter increments, and multiple missingData are added.
	 */
	@Test
	void testCheckMarketData_MultipleMissingDataPoints() throws Exception {
		// Arrange
		Smartderivativecontract sdc = loadSDCFromFile("net.finmath.smartcontract.product.xml/smartderivativecontract.xml");

		MarketDataList marketDataList = new MarketDataList();
		LocalDateTime timestamp = LocalDateTime.now();

		List<Smartderivativecontract.Settlement.Marketdata.Marketdataitems.Item> items =
				sdc.getSettlement().getMarketdata().getMarketdataitems().getItem();

		// Only add the first 2 market data points, leaving the rest missing
		int numToAdd = 2;
		for (int i = 0; i < numToAdd && i < items.size(); i++) {
			String symbol = items.get(i).getSymbol().get(0);
			marketDataList.add(new MarketDataPoint(symbol, 0.05, timestamp));
		}

		int expectedMissingCount = items.size() - numToAdd;

		// Act
		MarketDataErrors errors = MarketDataCheck.checkMarketData(marketDataList, sdc);

		// Assert
		assertTrue(errors.hasErrors(), "Should have errors when multiple data points are missing");
		assertEquals("error in marketData service - missing points in marketData", errors.getErrorMessage());
		assertEquals(expectedMissingCount, errors.getMissingDataPoints().size());

		// Verify that the missing symbols are correctly identified
		for (int i = numToAdd; i < items.size(); i++) {
			String expectedMissingSymbol = items.get(i).getSymbol().get(0);
			assertTrue(errors.getMissingDataPoints().contains(expectedMissingSymbol),
					"Missing data points should contain " + expectedMissingSymbol);
		}
	}

	/**
	 * Test checkMarketData when all required market data points are missing.
	 * This tests the extreme case where hasAllIDs is false for all items.
	 */
	@Test
	void testCheckMarketData_AllDataPointsMissing() throws Exception {
		// Arrange
		Smartderivativecontract sdc = loadSDCFromFile("net.finmath.smartcontract.product.xml/smartderivativecontract.xml");

		MarketDataList marketDataList = new MarketDataList();
		LocalDateTime timestamp = LocalDateTime.now();

		// Add some market data points with IDs that don't match any required ones
		marketDataList.add(new MarketDataPoint("UNKNOWN_ID_1", 0.05, timestamp));
		marketDataList.add(new MarketDataPoint("UNKNOWN_ID_2", 0.06, timestamp));

		List<Smartderivativecontract.Settlement.Marketdata.Marketdataitems.Item> items =
				sdc.getSettlement().getMarketdata().getMarketdataitems().getItem();

		// Act
		MarketDataErrors errors = MarketDataCheck.checkMarketData(marketDataList, sdc);

		// Assert
		assertTrue(errors.hasErrors(), "Should have errors when all data points are missing");
		assertEquals("error in marketData service - missing points in marketData", errors.getErrorMessage());
		assertEquals(items.size(), errors.getMissingDataPoints().size());

		// Verify that all required symbols are in the missing list
		for (Smartderivativecontract.Settlement.Marketdata.Marketdataitems.Item item : items) {
			String expectedMissingSymbol = item.getSymbol().get(0);
			assertTrue(errors.getMissingDataPoints().contains(expectedMissingSymbol),
					"Missing data points should contain " + expectedMissingSymbol);
		}
	}

	/**
	 * Test checkMarketData when market data list has extra points beyond what's required.
	 * This should still pass as long as all required points are present.
	 */
	@Test
	void testCheckMarketData_ExtraDataPoints() throws Exception {
		// Arrange
		Smartderivativecontract sdc = loadSDCFromFile("net.finmath.smartcontract.product.xml/smartderivativecontract.xml");

		MarketDataList marketDataList = new MarketDataList();
		LocalDateTime timestamp = LocalDateTime.now();

		// Add all required market data points
		for (Smartderivativecontract.Settlement.Marketdata.Marketdataitems.Item item :
				sdc.getSettlement().getMarketdata().getMarketdataitems().getItem()) {
			String symbol = item.getSymbol().get(0);
			marketDataList.add(new MarketDataPoint(symbol, 0.05, timestamp));
		}

		// Add extra points that are not required
		marketDataList.add(new MarketDataPoint("EXTRA_POINT_1", 0.07, timestamp));
		marketDataList.add(new MarketDataPoint("EXTRA_POINT_2", 0.08, timestamp));

		// Act
		MarketDataErrors errors = MarketDataCheck.checkMarketData(marketDataList, sdc);

		// Assert
		assertFalse(errors.hasErrors(), "Should not have errors when all required data is present even with extras");
		assertNull(errors.getErrorMessage());
		assertTrue(errors.getMissingDataPoints().isEmpty());
	}

	/**
	 * Test checkMarketData with a MarketDataList that has points but they match the empty list.
	 * This edge case tests line 18 condition more thoroughly.
	 */
	@Test
	void testCheckMarketData_EmptyPointsList() throws Exception {
		// Arrange
		MarketDataList marketDataList = new MarketDataList();
		// Explicitly verify points list is empty and equals new MarketDataList().getPoints()
		assertTrue(marketDataList.getPoints().equals(new MarketDataList().getPoints()));

		Smartderivativecontract sdc = loadSDCFromFile("net.finmath.smartcontract.product.xml/smartderivativecontract.xml");

		// Act
		MarketDataErrors errors = MarketDataCheck.checkMarketData(marketDataList, sdc);

		// Assert
		assertTrue(errors.hasErrors(), "Should have errors when points list is empty");
		assertEquals("error in marketData service - no data generated", errors.getErrorMessage());
		assertTrue(errors.getMissingDataPoints().contains("all, no data provided"));
	}

	/**
	 * Test checkMarketData with duplicate IDs in market data list.
	 * Should still pass as long as at least one of each required ID is present.
	 */
	@Test
	void testCheckMarketData_DuplicateDataPoints() throws Exception {
		// Arrange
		Smartderivativecontract sdc = loadSDCFromFile("net.finmath.smartcontract.product.xml/smartderivativecontract.xml");

		MarketDataList marketDataList = new MarketDataList();
		LocalDateTime timestamp = LocalDateTime.now();

		// Add all required market data points
		for (Smartderivativecontract.Settlement.Marketdata.Marketdataitems.Item item :
				sdc.getSettlement().getMarketdata().getMarketdataitems().getItem()) {
			String symbol = item.getSymbol().get(0);
			marketDataList.add(new MarketDataPoint(symbol, 0.05, timestamp));
		}

		// Add duplicates of the first few points
		List<Smartderivativecontract.Settlement.Marketdata.Marketdataitems.Item> items =
				sdc.getSettlement().getMarketdata().getMarketdataitems().getItem();
		if (items.size() > 0) {
			String firstSymbol = items.get(0).getSymbol().get(0);
			marketDataList.add(new MarketDataPoint(firstSymbol, 0.06, timestamp));
			marketDataList.add(new MarketDataPoint(firstSymbol, 0.07, timestamp));
		}

		// Act
		MarketDataErrors errors = MarketDataCheck.checkMarketData(marketDataList, sdc);

		// Assert
		assertFalse(errors.hasErrors(), "Should not have errors when all required data is present even with duplicates");
		assertNull(errors.getErrorMessage());
		assertTrue(errors.getMissingDataPoints().isEmpty());
	}

	/**
	 * Test checkMarketData with case-sensitive ID matching.
	 * Market data point IDs should match exactly (case-sensitive).
	 */
	@Test
	void testCheckMarketData_CaseSensitiveIds() throws Exception {
		// Arrange
		Smartderivativecontract sdc = loadSDCFromFile("net.finmath.smartcontract.product.xml/smartderivativecontract.xml");

		MarketDataList marketDataList = new MarketDataList();
		LocalDateTime timestamp = LocalDateTime.now();

		List<Smartderivativecontract.Settlement.Marketdata.Marketdataitems.Item> items =
				sdc.getSettlement().getMarketdata().getMarketdataitems().getItem();

		// Add all but the first with correct case, add the first with wrong case
		String firstSymbol = items.get(0).getSymbol().get(0);
		marketDataList.add(new MarketDataPoint(firstSymbol.toLowerCase(), 0.05, timestamp));

		for (int i = 1; i < items.size(); i++) {
			String symbol = items.get(i).getSymbol().get(0);
			marketDataList.add(new MarketDataPoint(symbol, 0.05, timestamp));
		}

		// Act
		MarketDataErrors errors = MarketDataCheck.checkMarketData(marketDataList, sdc);

		// Assert
		// If the original symbol has uppercase characters, lowercase version should not match
		if (!firstSymbol.equals(firstSymbol.toLowerCase())) {
			assertTrue(errors.hasErrors(), "Should have errors when ID case doesn't match");
			assertTrue(errors.getMissingDataPoints().contains(firstSymbol));
		}
	}

	/**
	 * Test that the method correctly identifies missing data using stream().anyMatch()
	 * when market data list has some matching and some non-matching IDs.
	 */
	@Test
	void testCheckMarketData_MixedMatchingAndNonMatchingIds() throws Exception {
		// Arrange
		Smartderivativecontract sdc = loadSDCFromFile("net.finmath.smartcontract.product.xml/smartderivativecontract.xml");

		MarketDataList marketDataList = new MarketDataList();
		LocalDateTime timestamp = LocalDateTime.now();

		List<Smartderivativecontract.Settlement.Marketdata.Marketdataitems.Item> items =
				sdc.getSettlement().getMarketdata().getMarketdataitems().getItem();

		// Add some matching IDs and some non-matching IDs
		if (items.size() >= 3) {
			// Add first item correctly
			marketDataList.add(new MarketDataPoint(items.get(0).getSymbol().get(0), 0.05, timestamp));

			// Add a non-matching ID
			marketDataList.add(new MarketDataPoint("NONMATCHING_ID", 0.06, timestamp));

			// Skip second item (will be missing)

			// Add third item correctly
			marketDataList.add(new MarketDataPoint(items.get(2).getSymbol().get(0), 0.07, timestamp));

			// Add remaining items correctly
			for (int i = 3; i < items.size(); i++) {
				marketDataList.add(new MarketDataPoint(items.get(i).getSymbol().get(0), 0.05, timestamp));
			}
		}

		// Act
		MarketDataErrors errors = MarketDataCheck.checkMarketData(marketDataList, sdc);

		// Assert
		if (items.size() >= 3) {
			assertTrue(errors.hasErrors(), "Should have errors when at least one required data point is missing");
			assertEquals("error in marketData service - missing points in marketData", errors.getErrorMessage());
			assertTrue(errors.getMissingDataPoints().contains(items.get(1).getSymbol().get(0)),
					"Should identify the second item as missing");
		}
	}
}
