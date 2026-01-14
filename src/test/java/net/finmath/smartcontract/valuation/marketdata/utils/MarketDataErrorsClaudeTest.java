/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */

package net.finmath.smartcontract.valuation.marketdata.utils;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for MarketDataErrors.
 * Tests all methods with focus on branch and condition coverage.
 *
 * @author Claude Code
 */
class MarketDataErrorsClaudeTest {

	/**
	 * Test constructor with hasErrors=true.
	 * Tests line 12-13.
	 */
	@Test
	void testConstructor_WithErrors() {
		// Act
		MarketDataErrors errors = new MarketDataErrors(true);

		// Assert
		assertTrue(errors.hasErrors(), "hasErrors should be true when constructed with true");
	}

	/**
	 * Test constructor with hasErrors=false.
	 * Tests line 12-13.
	 */
	@Test
	void testConstructor_WithoutErrors() {
		// Act
		MarketDataErrors errors = new MarketDataErrors(false);

		// Assert
		assertFalse(errors.hasErrors(), "hasErrors should be false when constructed with false");
	}

	/**
	 * Test hasErrors method returns the value set in constructor.
	 * Tests line 16-18.
	 */
	@Test
	void testHasErrors() {
		// Arrange
		MarketDataErrors errorsTrue = new MarketDataErrors(true);
		MarketDataErrors errorsFalse = new MarketDataErrors(false);

		// Act & Assert
		assertTrue(errorsTrue.hasErrors(), "hasErrors should return true");
		assertFalse(errorsFalse.hasErrors(), "hasErrors should return false");
	}

	/**
	 * Test getMissingDataPoints returns the default empty list after construction.
	 * Tests line 20-22.
	 */
	@Test
	void testGetMissingDataPoints_DefaultEmptyList() {
		// Arrange
		MarketDataErrors errors = new MarketDataErrors(false);

		// Act
		List<String> missingDataPoints = errors.getMissingDataPoints();

		// Assert
		assertNotNull(missingDataPoints, "getMissingDataPoints should not return null");
		assertTrue(missingDataPoints.isEmpty(), "getMissingDataPoints should return empty list by default");
	}

	/**
	 * Test getMissingDataPoints returns the list after adding items.
	 * Tests line 20-22 and 28-30.
	 */
	@Test
	void testGetMissingDataPoints_WithItems() {
		// Arrange
		MarketDataErrors errors = new MarketDataErrors(true);
		errors.addMissingData("point1");
		errors.addMissingData("point2");

		// Act
		List<String> missingDataPoints = errors.getMissingDataPoints();

		// Assert
		assertEquals(2, missingDataPoints.size(), "Should have 2 missing data points");
		assertEquals("point1", missingDataPoints.get(0));
		assertEquals("point2", missingDataPoints.get(1));
	}

	/**
	 * Test setMissingDataPoints replaces the list.
	 * Tests line 24-26.
	 */
	@Test
	void testSetMissingDataPoints() {
		// Arrange
		MarketDataErrors errors = new MarketDataErrors(true);
		List<String> newList = Arrays.asList("missing1", "missing2", "missing3");

		// Act
		errors.setMissingDataPoints(newList);

		// Assert
		List<String> retrievedList = errors.getMissingDataPoints();
		assertEquals(3, retrievedList.size());
		assertEquals("missing1", retrievedList.get(0));
		assertEquals("missing2", retrievedList.get(1));
		assertEquals("missing3", retrievedList.get(2));
	}

	/**
	 * Test setMissingDataPoints with empty list.
	 * Tests line 24-26.
	 */
	@Test
	void testSetMissingDataPoints_EmptyList() {
		// Arrange
		MarketDataErrors errors = new MarketDataErrors(true);
		errors.addMissingData("existing");
		List<String> emptyList = new ArrayList<>();

		// Act
		errors.setMissingDataPoints(emptyList);

		// Assert
		assertTrue(errors.getMissingDataPoints().isEmpty(), "List should be empty after setting empty list");
	}

	/**
	 * Test setMissingDataPoints with null replaces the previous list.
	 * Tests line 24-26.
	 */
	@Test
	void testSetMissingDataPoints_Null() {
		// Arrange
		MarketDataErrors errors = new MarketDataErrors(true);
		errors.addMissingData("existing");

		// Act
		errors.setMissingDataPoints(null);

		// Assert
		assertNull(errors.getMissingDataPoints(), "List should be null after setting null");
	}

	/**
	 * Test addMissingData adds a single item to the list.
	 * Tests line 28-30.
	 */
	@Test
	void testAddMissingData_SingleItem() {
		// Arrange
		MarketDataErrors errors = new MarketDataErrors(true);

		// Act
		errors.addMissingData("dataPoint1");

		// Assert
		assertEquals(1, errors.getMissingDataPoints().size());
		assertEquals("dataPoint1", errors.getMissingDataPoints().get(0));
	}

	/**
	 * Test addMissingData adds multiple items in order.
	 * Tests line 28-30.
	 */
	@Test
	void testAddMissingData_MultipleItems() {
		// Arrange
		MarketDataErrors errors = new MarketDataErrors(true);

		// Act
		errors.addMissingData("first");
		errors.addMissingData("second");
		errors.addMissingData("third");

		// Assert
		List<String> points = errors.getMissingDataPoints();
		assertEquals(3, points.size());
		assertEquals("first", points.get(0));
		assertEquals("second", points.get(1));
		assertEquals("third", points.get(2));
	}

	/**
	 * Test addMissingData can add null values.
	 * Tests line 28-30.
	 */
	@Test
	void testAddMissingData_NullValue() {
		// Arrange
		MarketDataErrors errors = new MarketDataErrors(true);

		// Act
		errors.addMissingData(null);

		// Assert
		assertEquals(1, errors.getMissingDataPoints().size());
		assertNull(errors.getMissingDataPoints().get(0), "Should be able to add null value");
	}

	/**
	 * Test addMissingData can add empty strings.
	 * Tests line 28-30.
	 */
	@Test
	void testAddMissingData_EmptyString() {
		// Arrange
		MarketDataErrors errors = new MarketDataErrors(true);

		// Act
		errors.addMissingData("");

		// Assert
		assertEquals(1, errors.getMissingDataPoints().size());
		assertEquals("", errors.getMissingDataPoints().get(0));
	}

	/**
	 * Test getErrorMessage returns null by default.
	 * Tests line 32-34.
	 */
	@Test
	void testGetErrorMessage_DefaultNull() {
		// Arrange
		MarketDataErrors errors = new MarketDataErrors(false);

		// Act
		String errorMessage = errors.getErrorMessage();

		// Assert
		assertNull(errorMessage, "Error message should be null by default");
	}

	/**
	 * Test getErrorMessage returns the set message.
	 * Tests line 32-34 and 36-38.
	 */
	@Test
	void testGetErrorMessage_AfterSet() {
		// Arrange
		MarketDataErrors errors = new MarketDataErrors(true);
		String message = "Something went wrong";

		// Act
		errors.setErrorMessage(message);

		// Assert
		assertEquals(message, errors.getErrorMessage());
	}

	/**
	 * Test setErrorMessage sets the message.
	 * Tests line 36-38.
	 */
	@Test
	void testSetErrorMessage() {
		// Arrange
		MarketDataErrors errors = new MarketDataErrors(true);

		// Act
		errors.setErrorMessage("Test error message");

		// Assert
		assertEquals("Test error message", errors.getErrorMessage());
	}

	/**
	 * Test setErrorMessage can set null.
	 * Tests line 36-38.
	 */
	@Test
	void testSetErrorMessage_Null() {
		// Arrange
		MarketDataErrors errors = new MarketDataErrors(true);
		errors.setErrorMessage("initial message");

		// Act
		errors.setErrorMessage(null);

		// Assert
		assertNull(errors.getErrorMessage(), "Error message should be null after setting null");
	}

	/**
	 * Test setErrorMessage can set empty string.
	 * Tests line 36-38.
	 */
	@Test
	void testSetErrorMessage_EmptyString() {
		// Arrange
		MarketDataErrors errors = new MarketDataErrors(true);

		// Act
		errors.setErrorMessage("");

		// Assert
		assertEquals("", errors.getErrorMessage());
	}

	/**
	 * Test setErrorMessage can replace an existing message.
	 * Tests line 36-38.
	 */
	@Test
	void testSetErrorMessage_Replace() {
		// Arrange
		MarketDataErrors errors = new MarketDataErrors(true);
		errors.setErrorMessage("first message");

		// Act
		errors.setErrorMessage("second message");

		// Assert
		assertEquals("second message", errors.getErrorMessage());
	}

	/**
	 * Test toString with hasErrors=true and no data.
	 * Tests line 40-47.
	 */
	@Test
	void testToString_WithErrorsNoData() {
		// Arrange
		MarketDataErrors errors = new MarketDataErrors(true);

		// Act
		String result = errors.toString();

		// Assert
		assertNotNull(result);
		assertTrue(result.contains("hasErrors=true"), "Should contain hasErrors=true");
		assertTrue(result.contains("missingDataPoints=[]"), "Should contain empty list");
		assertTrue(result.contains("errorMessage='null'"), "Should contain null error message");
	}

	/**
	 * Test toString with hasErrors=false.
	 * Tests line 40-47.
	 */
	@Test
	void testToString_WithoutErrors() {
		// Arrange
		MarketDataErrors errors = new MarketDataErrors(false);

		// Act
		String result = errors.toString();

		// Assert
		assertNotNull(result);
		assertTrue(result.contains("hasErrors=false"), "Should contain hasErrors=false");
		assertTrue(result.contains("missingDataPoints=[]"), "Should contain empty list");
		assertTrue(result.contains("errorMessage='null'"), "Should contain null error message");
	}

	/**
	 * Test toString with all fields populated.
	 * Tests line 40-47.
	 */
	@Test
	void testToString_AllFieldsPopulated() {
		// Arrange
		MarketDataErrors errors = new MarketDataErrors(true);
		errors.addMissingData("point1");
		errors.addMissingData("point2");
		errors.setErrorMessage("Test error");

		// Act
		String result = errors.toString();

		// Assert
		assertNotNull(result);
		assertTrue(result.contains("hasErrors=true"));
		assertTrue(result.contains("point1"));
		assertTrue(result.contains("point2"));
		assertTrue(result.contains("errorMessage='Test error'"));
	}

	/**
	 * Test toString format matches expected pattern.
	 * Tests line 40-47.
	 */
	@Test
	void testToString_Format() {
		// Arrange
		MarketDataErrors errors = new MarketDataErrors(true);
		errors.setErrorMessage("Error occurred");
		errors.addMissingData("data1");

		// Act
		String result = errors.toString();

		// Assert
		assertTrue(result.startsWith("MarketDataErrors{"), "Should start with class name");
		assertTrue(result.endsWith("}"), "Should end with closing brace");
		assertTrue(result.contains("hasErrors="));
		assertTrue(result.contains("missingDataPoints="));
		assertTrue(result.contains("errorMessage="));
	}

	/**
	 * Test toString with special characters in error message.
	 * Tests line 40-47.
	 */
	@Test
	void testToString_SpecialCharacters() {
		// Arrange
		MarketDataErrors errors = new MarketDataErrors(true);
		errors.setErrorMessage("Error with 'quotes' and \"double quotes\"");
		errors.addMissingData("data with spaces");

		// Act
		String result = errors.toString();

		// Assert
		assertNotNull(result);
		assertTrue(result.contains("Error with 'quotes' and \"double quotes\""));
		assertTrue(result.contains("data with spaces"));
	}

	/**
	 * Test that getMissingDataPoints returns a mutable list.
	 * Tests line 20-22.
	 */
	@Test
	void testGetMissingDataPoints_Mutable() {
		// Arrange
		MarketDataErrors errors = new MarketDataErrors(true);
		errors.addMissingData("initial");

		// Act
		List<String> list = errors.getMissingDataPoints();
		list.add("added_directly");

		// Assert
		assertEquals(2, errors.getMissingDataPoints().size());
		assertTrue(errors.getMissingDataPoints().contains("added_directly"));
	}

	/**
	 * Test combination of operations to verify state consistency.
	 * Tests multiple methods together.
	 */
	@Test
	void testCompleteWorkflow() {
		// Arrange
		MarketDataErrors errors = new MarketDataErrors(true);

		// Act & Assert - build up state
		assertTrue(errors.hasErrors());
		assertNull(errors.getErrorMessage());
		assertTrue(errors.getMissingDataPoints().isEmpty());

		errors.setErrorMessage("Initial error");
		assertEquals("Initial error", errors.getErrorMessage());

		errors.addMissingData("point1");
		errors.addMissingData("point2");
		assertEquals(2, errors.getMissingDataPoints().size());

		// Replace with new list
		List<String> newList = Arrays.asList("new1", "new2", "new3");
		errors.setMissingDataPoints(newList);
		assertEquals(3, errors.getMissingDataPoints().size());

		// Update error message
		errors.setErrorMessage("Updated error");
		assertEquals("Updated error", errors.getErrorMessage());

		// Verify toString contains all info
		String result = errors.toString();
		assertTrue(result.contains("hasErrors=true"));
		assertTrue(result.contains("new1"));
		assertTrue(result.contains("new2"));
		assertTrue(result.contains("new3"));
		assertTrue(result.contains("Updated error"));
	}

	/**
	 * Test that hasErrors is immutable after construction.
	 * Tests line 8 (final field).
	 */
	@Test
	void testHasErrors_Immutable() {
		// Arrange
		MarketDataErrors errorsTrue = new MarketDataErrors(true);
		MarketDataErrors errorsFalse = new MarketDataErrors(false);

		// Act - modify other fields
		errorsTrue.setErrorMessage("error");
		errorsTrue.addMissingData("data");
		errorsFalse.setErrorMessage("error");
		errorsFalse.addMissingData("data");

		// Assert - hasErrors should remain unchanged
		assertTrue(errorsTrue.hasErrors(), "hasErrors should remain true");
		assertFalse(errorsFalse.hasErrors(), "hasErrors should remain false");
	}
}
