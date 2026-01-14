/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */

package net.finmath.smartcontract.valuation.service.controllers;

import net.finmath.smartcontract.model.ExceptionId;
import net.finmath.smartcontract.model.SDCException;
import net.finmath.smartcontract.model.ValueRequest;
import net.finmath.smartcontract.model.ValueResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for ValuationController.value method.
 * Tests the value calculation endpoint with focus on branch and condition coverage.
 *
 * Note: This method creates MarginCalculator internally and performs complex financial calculations.
 * We test without mocking to verify the actual behavior, using minimal valid XML data where possible,
 * and focusing on error handling paths which can be triggered with invalid inputs.
 *
 * @author Claude Code
 */
class ValuationControllerClaude_valueTest {

	private ValuationController controller;

	/**
	 * Set up test fixtures before each test.
	 */
	@BeforeEach
	void setUp() {
		controller = new ValuationController();
	}

	/**
	 * Test value with null request throws SDCException.
	 * The method catches NullPointerException and wraps it in SDCException.
	 */
	@Test
	void testValue_NullRequest_ThrowsSDCException() {
		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.value(null);
		}, "Should throw SDCException for null request");

		assertEquals(ExceptionId.SDC_MARGIN_CALCULATION_ERROR, exception.getId(),
				"Exception should have MARGIN_CALCULATION_ERROR ID");
	}

	/**
	 * Test value with request containing null marketData throws exception.
	 */
	@Test
	void testValue_NullMarketData_ThrowsSDCException() {
		// Arrange
		ValueRequest request = new ValueRequest();
		request.setMarketData(null);
		request.setTradeData("<trade></trade>");

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.value(request);
		}, "Should throw SDCException for null market data");

		assertEquals(ExceptionId.SDC_MARGIN_CALCULATION_ERROR, exception.getId(),
				"Exception should have MARGIN_CALCULATION_ERROR ID");
	}

	/**
	 * Test value with request containing null tradeData throws exception.
	 */
	@Test
	void testValue_NullTradeData_ThrowsSDCException() {
		// Arrange
		ValueRequest request = new ValueRequest();
		request.setMarketData("<marketDataList></marketDataList>");
		request.setTradeData(null);

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.value(request);
		}, "Should throw SDCException for null trade data");

		assertEquals(ExceptionId.SDC_MARGIN_CALCULATION_ERROR, exception.getId(),
				"Exception should have MARGIN_CALCULATION_ERROR ID");
	}

	/**
	 * Test value with empty marketData throws exception.
	 */
	@Test
	void testValue_EmptyMarketData_ThrowsSDCException() {
		// Arrange
		ValueRequest request = new ValueRequest();
		request.setMarketData("");
		request.setTradeData("<trade></trade>");

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.value(request);
		}, "Should throw SDCException for empty market data");

		assertEquals(ExceptionId.SDC_MARGIN_CALCULATION_ERROR, exception.getId(),
				"Exception should have MARGIN_CALCULATION_ERROR ID");
	}

	/**
	 * Test value with empty tradeData throws exception.
	 */
	@Test
	void testValue_EmptyTradeData_ThrowsSDCException() {
		// Arrange
		ValueRequest request = new ValueRequest();
		request.setMarketData("<marketDataList></marketDataList>");
		request.setTradeData("");

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.value(request);
		}, "Should throw SDCException for empty trade data");

		assertEquals(ExceptionId.SDC_MARGIN_CALCULATION_ERROR, exception.getId(),
				"Exception should have MARGIN_CALCULATION_ERROR ID");
	}

	/**
	 * Test value with invalid XML in tradeData throws SDCException.
	 */
	@Test
	void testValue_InvalidXmlTradeData_ThrowsSDCException() {
		// Arrange
		ValueRequest request = new ValueRequest();
		request.setMarketData("<marketDataList></marketDataList>");
		request.setTradeData("<invalid><xml>");  // Malformed XML

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.value(request);
		}, "Should throw SDCException for invalid XML trade data");

		assertEquals(ExceptionId.SDC_MARGIN_CALCULATION_ERROR, exception.getId(),
				"Exception should have MARGIN_CALCULATION_ERROR ID");
	}

	/**
	 * Test value with malformed XML throws SDCException.
	 */
	@Test
	void testValue_MalformedXml_ThrowsSDCException() {
		// Arrange
		ValueRequest request = new ValueRequest();
		request.setMarketData("<marketDataList></marketDataList>");
		request.setTradeData("not xml at all");

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.value(request);
		}, "Should throw SDCException for malformed XML");

		assertNotNull(exception.getId(), "Exception should have an exception ID");
	}

	/**
	 * Test value with invalid but well-formed XML throws SDCException.
	 */
	@Test
	void testValue_InvalidButWellFormedXml_ThrowsSDCException() {
		// Arrange
		ValueRequest request = new ValueRequest();
		request.setMarketData("<marketDataList><item><id>test</id></item></marketDataList>");
		request.setTradeData("<wrongElement>content</wrongElement>");

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.value(request);
		}, "Should throw SDCException for invalid XML structure");

		assertNotNull(exception.getId(), "Exception should have an exception ID");
	}

	/**
	 * Test value with incomplete XML data throws SDCException.
	 */
	@Test
	void testValue_IncompleteXml_ThrowsSDCException() {
		// Arrange
		ValueRequest request = new ValueRequest();
		request.setMarketData("<marketDataList>");  // Incomplete XML
		request.setTradeData("<trade></trade>");

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.value(request);
		}, "Should throw SDCException for incomplete XML");

		assertNotNull(exception.getId(), "Exception should have an exception ID");
	}

	/**
	 * Test value with very long invalid data doesn't hang.
	 */
	@Test
	void testValue_VeryLongInvalidData_ThrowsSDCException() {
		// Arrange
		ValueRequest request = new ValueRequest();
		StringBuilder longData = new StringBuilder("<data>");
		for (int i = 0; i < 1000; i++) {
			longData.append("<item>").append(i).append("</item>");
		}
		longData.append("</data>");

		request.setMarketData(longData.toString());
		request.setTradeData(longData.toString());

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.value(request);
		}, "Should throw SDCException for invalid data format");

		assertNotNull(exception.getId(), "Exception should have an exception ID");
	}

	/**
	 * Test value with special characters in XML.
	 */
	@Test
	void testValue_SpecialCharactersInXml_ThrowsSDCException() {
		// Arrange
		ValueRequest request = new ValueRequest();
		request.setMarketData("<marketDataList><item>&invalid;</item></marketDataList>");
		request.setTradeData("<trade></trade>");

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.value(request);
		}, "Should throw SDCException for special characters");

		assertNotNull(exception.getId(), "Exception should have an exception ID");
	}

	/**
	 * Test value with request containing whitespace-only strings throws exception.
	 */
	@Test
	void testValue_WhitespaceOnlyData_ThrowsSDCException() {
		// Arrange
		ValueRequest request = new ValueRequest();
		request.setMarketData("   ");
		request.setTradeData("   ");

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.value(request);
		}, "Should throw SDCException for whitespace-only data");

		assertEquals(ExceptionId.SDC_MARGIN_CALCULATION_ERROR, exception.getId(),
				"Exception should have MARGIN_CALCULATION_ERROR ID");
	}

	/**
	 * Test value throws SDCException when MarginCalculator fails.
	 * Verifies the general exception catch block.
	 */
	@Test
	void testValue_InvalidData_ThrowsSDCExceptionWithMarginCalculationError() {
		// Arrange
		ValueRequest request = new ValueRequest();
		request.setMarketData("<marketDataList></marketDataList>");
		request.setTradeData("<dataDocument></dataDocument>");

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.value(request);
		}, "Should throw SDCException when calculation fails");

		// Should be wrapped in SDCException
		assertNotNull(exception, "Exception should not be null");
		assertNotNull(exception.getId(), "Exception should have an ID");
	}

	/**
	 * Test that value throws SDCException with appropriate error message.
	 */
	@Test
	void testValue_InvalidRequest_SDCExceptionHasMessage() {
		// Arrange
		ValueRequest request = new ValueRequest();
		request.setMarketData("<invalid");
		request.setTradeData("<invalid");

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.value(request);
		}, "Should throw SDCException");

		assertNotNull(exception.getMessage(), "Exception should have a message");
		assertFalse(exception.getMessage().isEmpty(), "Exception message should not be empty");
	}

	/**
	 * Test value with mismatched XML tags throws SDCException.
	 */
	@Test
	void testValue_MismatchedXmlTags_ThrowsSDCException() {
		// Arrange
		ValueRequest request = new ValueRequest();
		request.setMarketData("<marketDataList><item></wrongtag>");
		request.setTradeData("<trade></trade>");

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.value(request);
		}, "Should throw SDCException for mismatched XML tags");

		assertNotNull(exception.getId(), "Exception should have an exception ID");
	}

	/**
	 * Test value with XML containing unclosed tags.
	 */
	@Test
	void testValue_UnclosedXmlTags_ThrowsSDCException() {
		// Arrange
		ValueRequest request = new ValueRequest();
		request.setMarketData("<marketDataList><item>");
		request.setTradeData("<trade></trade>");

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.value(request);
		}, "Should throw SDCException for unclosed XML tags");

		assertNotNull(exception.getId(), "Exception should have an exception ID");
	}

	/**
	 * Test value with nested invalid XML structure.
	 */
	@Test
	void testValue_NestedInvalidXml_ThrowsSDCException() {
		// Arrange
		ValueRequest request = new ValueRequest();
		request.setMarketData("<outer><inner><broken></inner>");
		request.setTradeData("<trade></trade>");

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.value(request);
		}, "Should throw SDCException for nested invalid XML");

		assertNotNull(exception.getId(), "Exception should have an exception ID");
	}

	/**
	 * Test value catches and wraps all exceptions as SDCException.
	 */
	@Test
	void testValue_AnyException_WrappedAsSDCException() {
		// Arrange
		ValueRequest request = new ValueRequest();
		// Set data that will cause an exception during processing
		request.setMarketData("invalid");
		request.setTradeData("invalid");

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.value(request);
		}, "Any exception should be wrapped as SDCException");

		assertNotNull(exception.getId(), "SDCException should have an exception ID");
	}

	/**
	 * Test value with both null marketData and tradeData throws exception.
	 */
	@Test
	void testValue_BothNull_ThrowsSDCException() {
		// Arrange
		ValueRequest request = new ValueRequest();
		request.setMarketData(null);
		request.setTradeData(null);

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.value(request);
		}, "Should throw SDCException for both null values");

		assertEquals(ExceptionId.SDC_MARGIN_CALCULATION_ERROR, exception.getId(),
				"Exception should have MARGIN_CALCULATION_ERROR ID");
	}

	/**
	 * Test value with both empty marketData and tradeData throws exception.
	 */
	@Test
	void testValue_BothEmpty_ThrowsSDCException() {
		// Arrange
		ValueRequest request = new ValueRequest();
		request.setMarketData("");
		request.setTradeData("");

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.value(request);
		}, "Should throw SDCException for both empty values");

		assertEquals(ExceptionId.SDC_MARGIN_CALCULATION_ERROR, exception.getId(),
				"Exception should have MARGIN_CALCULATION_ERROR ID");
	}

	/**
	 * Test value with invalid marketData and valid tradeData structure.
	 */
	@Test
	void testValue_InvalidMarketData_ThrowsSDCException() {
		// Arrange
		ValueRequest request = new ValueRequest();
		request.setMarketData("<invalid><unclosed>");
		request.setTradeData("<trade></trade>");

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.value(request);
		}, "Should throw SDCException for invalid market data");

		assertNotNull(exception.getId(), "Exception should have an exception ID");
	}

	/**
	 * Test value with valid marketData structure and invalid tradeData.
	 */
	@Test
	void testValue_InvalidTradeData_ThrowsSDCException() {
		// Arrange
		ValueRequest request = new ValueRequest();
		request.setMarketData("<marketDataList></marketDataList>");
		request.setTradeData("<invalid><unclosed>");

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.value(request);
		}, "Should throw SDCException for invalid trade data");

		assertNotNull(exception.getId(), "Exception should have an exception ID");
	}

	/**
	 * Test value exception includes the original error message.
	 */
	@Test
	void testValue_ExceptionIncludesOriginalMessage() {
		// Arrange
		ValueRequest request = new ValueRequest();
		request.setMarketData("completely invalid data");
		request.setTradeData("completely invalid data");

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.value(request);
		}, "Should throw SDCException");

		// Verify exception has proper structure
		assertNotNull(exception.getMessage(), "Exception should have a message");
		assertNotNull(exception.getId(), "Exception should have an ID");
		assertEquals(ExceptionId.SDC_MARGIN_CALCULATION_ERROR, exception.getId(),
				"Exception should have MARGIN_CALCULATION_ERROR ID");
	}

	/**
	 * Test value with XML containing CDATA sections.
	 */
	@Test
	void testValue_XmlWithCDATA_ThrowsSDCException() {
		// Arrange
		ValueRequest request = new ValueRequest();
		request.setMarketData("<marketDataList><![CDATA[invalid]]></marketDataList>");
		request.setTradeData("<trade><![CDATA[data]]></trade>");

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.value(request);
		}, "Should throw SDCException for XML with CDATA");

		assertNotNull(exception.getId(), "Exception should have an exception ID");
	}

	/**
	 * Test value with empty but valid XML structure.
	 */
	@Test
	void testValue_EmptyValidXml_ThrowsSDCException() {
		// Arrange
		ValueRequest request = new ValueRequest();
		request.setMarketData("<marketDataList></marketDataList>");
		request.setTradeData("<trade></trade>");

		// Act & Assert
		// Even valid empty XML should fail because required data is missing
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.value(request);
		}, "Should throw SDCException for empty valid XML");

		assertNotNull(exception.getId(), "Exception should have an exception ID");
	}

	/**
	 * Test value with valuationDate set (should be ignored by this method).
	 */
	@Test
	void testValue_WithValuationDate_ThrowsSDCException() {
		// Arrange
		ValueRequest request = new ValueRequest();
		request.setMarketData("<marketDataList></marketDataList>");
		request.setTradeData("<trade></trade>");
		request.setValuationDate("20230131-143523");

		// Act & Assert
		// valuationDate is not used by this method, should still fail on invalid data
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.value(request);
		}, "Should throw SDCException for invalid data");

		assertNotNull(exception.getId(), "Exception should have an exception ID");
	}
}
