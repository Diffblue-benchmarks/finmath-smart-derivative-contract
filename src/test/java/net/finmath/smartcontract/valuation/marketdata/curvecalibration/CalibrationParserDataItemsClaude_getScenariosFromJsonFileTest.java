/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */

package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Additional test class for CalibrationParserDataItems.getScenariosFromJsonFile method
 * to improve coverage for lines 83-85 (IOException catch block).
 *
 * @author Claude Code
 */
class CalibrationParserDataItemsClaude_getScenariosFromJsonFileTest {

	/**
	 * Test getScenariosFromJsonFile with valid file from resources.
	 * This test verifies the happy path where the file exists and can be read successfully.
	 */
	@Test
	void testGetScenariosFromJsonFile_ValidFile_ReturnsScenarios() throws IOException {
		// Arrange
		String fileName = "/net/finmath/smartcontract/valuation/marketdata/curvecalibration/timeseriesdatamap.json";

		// Act
		List<CalibrationDataset> scenarios = CalibrationParserDataItems.getScenariosFromJsonFile(fileName);

		// Assert
		assertNotNull(scenarios, "Should return list of scenarios");
		assertFalse(scenarios.isEmpty(), "Should not be empty");
		assertTrue(scenarios.size() > 0, "Should have at least one scenario");
	}


	/**
	 * Test getScenariosFromJsonFile with non-existent file.
	 * Should throw NullPointerException when resource is not found.
	 */
	@Test
	void testGetScenariosFromJsonFile_FileNotFound_ThrowsException() {
		// Arrange
		String fileName = "/non/existent/file.json";

		// Act & Assert
		// When resource is not found, getResourceAsStream returns null, causing NullPointerException
		assertThrows(NullPointerException.class, () -> {
			CalibrationParserDataItems.getScenariosFromJsonFile(fileName);
		}, "Should throw NullPointerException for non-existent file");
	}

	/**
	 * Test getScenariosFromJsonFile with null filename.
	 * Should throw NullPointerException.
	 */
	@Test
	void testGetScenariosFromJsonFile_NullFileName_ThrowsException() {
		// Arrange
		String fileName = null;

		// Act & Assert
		assertThrows(NullPointerException.class, () -> {
			CalibrationParserDataItems.getScenariosFromJsonFile(fileName);
		}, "Should throw NullPointerException for null file name");
	}

	/**
	 * Test getScenariosFromJsonFile with empty string filename.
	 * Should throw exception as the file doesn't exist.
	 */
	@Test
	void testGetScenariosFromJsonFile_EmptyFileName_ThrowsException() {
		// Arrange
		String fileName = "";

		// Act & Assert
		// Empty string causes IllegalArgumentException when parsing the empty JSON that is read
		assertThrows(IllegalArgumentException.class, () -> {
			CalibrationParserDataItems.getScenariosFromJsonFile(fileName);
		}, "Should throw IllegalArgumentException for empty file name");
	}
}
