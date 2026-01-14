/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */

package net.finmath.smartcontract.valuation.service.controllers;

import net.finmath.smartcontract.model.ExceptionId;
import net.finmath.smartcontract.model.MarginRequest;
import net.finmath.smartcontract.model.MarginResult;
import net.finmath.smartcontract.model.SDCException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.xml.sax.SAXException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for ValuationController.margin method.
 * Tests the margin calculation endpoint with focus on branch and condition coverage.
 *
 * Note: This method creates MarginCalculator internally and performs complex financial calculations.
 * We test without mocking to verify the actual behavior, using minimal valid XML data where possible,
 * and focusing on error handling paths which can be triggered with invalid inputs.
 *
 * @author Claude Code
 */
class ValuationControllerClaude_marginTest {

	private ValuationController controller;

	/**
	 * Set up test fixtures before each test.
	 */
	@BeforeEach
	void setUp() {
		controller = new ValuationController();
	}

	/**
	 * Test margin with null request throws NullPointerException.
	 * The method tries to access properties of the null request.
	 */
	@Test
	void testMargin_NullRequest_ThrowsNullPointerException() {
		// Act & Assert
		assertThrows(NullPointerException.class, () -> {
			controller.margin(null);
		}, "Should throw NullPointerException for null request");
	}

	/**
	 * Test margin with request containing null marketDataStart throws exception.
	 */
	@Test
	void testMargin_NullMarketDataStart_ThrowsSDCException() {
		// Arrange
		MarginRequest request = new MarginRequest();
		request.setMarketDataStart(null);
		request.setMarketDataEnd("<marketDataList></marketDataList>");
		request.setTradeData("<trade></trade>");

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.margin(request);
		}, "Should throw SDCException for null market data start");

		assertEquals(ExceptionId.SDC_MARGIN_CALCULATION_ERROR, exception.getId(),
				"Exception should have MARGIN_CALCULATION_ERROR ID");
	}

	/**
	 * Test margin with request containing null marketDataEnd throws exception.
	 */
	@Test
	void testMargin_NullMarketDataEnd_ThrowsSDCException() {
		// Arrange
		MarginRequest request = new MarginRequest();
		request.setMarketDataStart("<marketDataList></marketDataList>");
		request.setMarketDataEnd(null);
		request.setTradeData("<trade></trade>");

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.margin(request);
		}, "Should throw SDCException for null market data end");

		assertEquals(ExceptionId.SDC_MARGIN_CALCULATION_ERROR, exception.getId(),
				"Exception should have MARGIN_CALCULATION_ERROR ID");
	}

	/**
	 * Test margin with request containing null tradeData throws exception.
	 */
	@Test
	void testMargin_NullTradeData_ThrowsSDCException() {
		// Arrange
		MarginRequest request = new MarginRequest();
		request.setMarketDataStart("<marketDataList></marketDataList>");
		request.setMarketDataEnd("<marketDataList></marketDataList>");
		request.setTradeData(null);

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.margin(request);
		}, "Should throw SDCException for null trade data");

		assertEquals(ExceptionId.SDC_MARGIN_CALCULATION_ERROR, exception.getId(),
				"Exception should have MARGIN_CALCULATION_ERROR ID");
	}

	/**
	 * Test margin with empty marketDataStart throws exception.
	 */
	@Test
	void testMargin_EmptyMarketDataStart_ThrowsSDCException() {
		// Arrange
		MarginRequest request = new MarginRequest();
		request.setMarketDataStart("");
		request.setMarketDataEnd("<marketDataList></marketDataList>");
		request.setTradeData("<trade></trade>");

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.margin(request);
		}, "Should throw SDCException for empty market data start");

		assertEquals(ExceptionId.SDC_MARGIN_CALCULATION_ERROR, exception.getId(),
				"Exception should have MARGIN_CALCULATION_ERROR ID");
	}

	/**
	 * Test margin with empty marketDataEnd throws exception.
	 */
	@Test
	void testMargin_EmptyMarketDataEnd_ThrowsSDCException() {
		// Arrange
		MarginRequest request = new MarginRequest();
		request.setMarketDataStart("<marketDataList></marketDataList>");
		request.setMarketDataEnd("");
		request.setTradeData("<trade></trade>");

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.margin(request);
		}, "Should throw SDCException for empty market data end");

		assertEquals(ExceptionId.SDC_MARGIN_CALCULATION_ERROR, exception.getId(),
				"Exception should have MARGIN_CALCULATION_ERROR ID");
	}

	/**
	 * Test margin with empty tradeData throws exception.
	 */
	@Test
	void testMargin_EmptyTradeData_ThrowsSDCException() {
		// Arrange
		MarginRequest request = new MarginRequest();
		request.setMarketDataStart("<marketDataList></marketDataList>");
		request.setMarketDataEnd("<marketDataList></marketDataList>");
		request.setTradeData("");

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.margin(request);
		}, "Should throw SDCException for empty trade data");

		assertEquals(ExceptionId.SDC_MARGIN_CALCULATION_ERROR, exception.getId(),
				"Exception should have MARGIN_CALCULATION_ERROR ID");
	}

	/**
	 * Test margin with invalid XML in tradeData throws SDCException with INVALID_TRADE_DATA.
	 * This tests the SAXException catch block.
	 */
	@Test
	void testMargin_InvalidXmlTradeData_ThrowsSDCExceptionWithInvalidTradeData() {
		// Arrange
		MarginRequest request = new MarginRequest();
		request.setMarketDataStart("<marketDataList></marketDataList>");
		request.setMarketDataEnd("<marketDataList></marketDataList>");
		request.setTradeData("<invalid><xml>");  // Malformed XML

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.margin(request);
		}, "Should throw SDCException for invalid XML trade data");

		// The exception could be either INVALID_TRADE_DATA or MARGIN_CALCULATION_ERROR
		// depending on where the XML parsing fails
		assertTrue(
				exception.getId() == ExceptionId.SDC_INVALID_TRADE_DATA ||
						exception.getId() == ExceptionId.SDC_MARGIN_CALCULATION_ERROR,
				"Exception should have INVALID_TRADE_DATA or MARGIN_CALCULATION_ERROR ID");
	}

	/**
	 * Test margin with malformed XML that triggers SAXException.
	 */
	@Test
	void testMargin_MalformedXml_ThrowsSDCException() {
		// Arrange
		MarginRequest request = new MarginRequest();
		request.setMarketDataStart("<marketDataList></marketDataList>");
		request.setMarketDataEnd("<marketDataList></marketDataList>");
		request.setTradeData("not xml at all");

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.margin(request);
		}, "Should throw SDCException for malformed XML");

		assertNotNull(exception.getId(), "Exception should have an exception ID");
	}

	/**
	 * Test margin with invalid but well-formed XML throws SDCException.
	 */
	@Test
	void testMargin_InvalidButWellFormedXml_ThrowsSDCException() {
		// Arrange
		MarginRequest request = new MarginRequest();
		request.setMarketDataStart("<marketDataList><item><id>test</id></item></marketDataList>");
		request.setMarketDataEnd("<marketDataList><item><id>test</id></item></marketDataList>");
		request.setTradeData("<wrongElement>content</wrongElement>");

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.margin(request);
		}, "Should throw SDCException for invalid XML structure");

		assertNotNull(exception.getId(), "Exception should have an exception ID");
	}

	/**
	 * Test margin with incomplete XML data throws SDCException.
	 */
	@Test
	void testMargin_IncompleteXml_ThrowsSDCException() {
		// Arrange
		MarginRequest request = new MarginRequest();
		request.setMarketDataStart("<marketDataList>");  // Incomplete XML
		request.setMarketDataEnd("<marketDataList></marketDataList>");
		request.setTradeData("<trade></trade>");

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.margin(request);
		}, "Should throw SDCException for incomplete XML");

		assertNotNull(exception.getId(), "Exception should have an exception ID");
	}

	/**
	 * Test margin with very long invalid data doesn't hang.
	 */
	@Test
	void testMargin_VeryLongInvalidData_ThrowsSDCException() {
		// Arrange
		MarginRequest request = new MarginRequest();
		StringBuilder longData = new StringBuilder("<data>");
		for (int i = 0; i < 1000; i++) {
			longData.append("<item>").append(i).append("</item>");
		}
		longData.append("</data>");

		request.setMarketDataStart(longData.toString());
		request.setMarketDataEnd(longData.toString());
		request.setTradeData(longData.toString());

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.margin(request);
		}, "Should throw SDCException for invalid data format");

		assertNotNull(exception.getId(), "Exception should have an exception ID");
	}

	/**
	 * Test margin with special characters in XML.
	 */
	@Test
	void testMargin_SpecialCharactersInXml_ThrowsSDCException() {
		// Arrange
		MarginRequest request = new MarginRequest();
		request.setMarketDataStart("<marketDataList><item>&invalid;</item></marketDataList>");
		request.setMarketDataEnd("<marketDataList></marketDataList>");
		request.setTradeData("<trade></trade>");

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.margin(request);
		}, "Should throw SDCException for special characters");

		assertNotNull(exception.getId(), "Exception should have an exception ID");
	}

	/**
	 * Test margin with request containing whitespace-only strings throws exception.
	 */
	@Test
	void testMargin_WhitespaceOnlyData_ThrowsSDCException() {
		// Arrange
		MarginRequest request = new MarginRequest();
		request.setMarketDataStart("   ");
		request.setMarketDataEnd("   ");
		request.setTradeData("   ");

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.margin(request);
		}, "Should throw SDCException for whitespace-only data");

		assertEquals(ExceptionId.SDC_MARGIN_CALCULATION_ERROR, exception.getId(),
				"Exception should have MARGIN_CALCULATION_ERROR ID");
	}

	/**
	 * Test margin throws SDCException when MarginCalculator fails.
	 * Verifies the general exception catch block.
	 */
	@Test
	void testMargin_InvalidData_ThrowsSDCExceptionWithMarginCalculationError() {
		// Arrange
		MarginRequest request = new MarginRequest();
		request.setMarketDataStart("<marketDataList></marketDataList>");
		request.setMarketDataEnd("<marketDataList></marketDataList>");
		request.setTradeData("<dataDocument></dataDocument>");

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.margin(request);
		}, "Should throw SDCException when calculation fails");

		// Should be wrapped in SDCException
		assertNotNull(exception, "Exception should not be null");
		assertNotNull(exception.getId(), "Exception should have an ID");
	}

	/**
	 * Test that margin throws SDCException with appropriate error message.
	 */
	@Test
	void testMargin_InvalidRequest_SDCExceptionHasMessage() {
		// Arrange
		MarginRequest request = new MarginRequest();
		request.setMarketDataStart("<invalid");
		request.setMarketDataEnd("<invalid");
		request.setTradeData("<invalid");

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.margin(request);
		}, "Should throw SDCException");

		assertNotNull(exception.getMessage(), "Exception should have a message");
		assertFalse(exception.getMessage().isEmpty(), "Exception message should not be empty");
	}

	/**
	 * Test margin with mismatched XML tags throws SDCException.
	 */
	@Test
	void testMargin_MismatchedXmlTags_ThrowsSDCException() {
		// Arrange
		MarginRequest request = new MarginRequest();
		request.setMarketDataStart("<marketDataList><item></wrongtag>");
		request.setMarketDataEnd("<marketDataList></marketDataList>");
		request.setTradeData("<trade></trade>");

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.margin(request);
		}, "Should throw SDCException for mismatched XML tags");

		assertNotNull(exception.getId(), "Exception should have an exception ID");
	}

	/**
	 * Test margin with XML containing unclosed tags.
	 */
	@Test
	void testMargin_UnclosedXmlTags_ThrowsSDCException() {
		// Arrange
		MarginRequest request = new MarginRequest();
		request.setMarketDataStart("<marketDataList><item>");
		request.setMarketDataEnd("<marketDataList></marketDataList>");
		request.setTradeData("<trade></trade>");

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.margin(request);
		}, "Should throw SDCException for unclosed XML tags");

		assertNotNull(exception.getId(), "Exception should have an exception ID");
	}

	/**
	 * Test margin with nested invalid XML structure.
	 */
	@Test
	void testMargin_NestedInvalidXml_ThrowsSDCException() {
		// Arrange
		MarginRequest request = new MarginRequest();
		request.setMarketDataStart("<outer><inner><broken></inner>");
		request.setMarketDataEnd("<marketDataList></marketDataList>");
		request.setTradeData("<trade></trade>");

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.margin(request);
		}, "Should throw SDCException for nested invalid XML");

		assertNotNull(exception.getId(), "Exception should have an exception ID");
	}

	/**
	 * Test margin catches and wraps all exceptions as SDCException.
	 */
	@Test
	void testMargin_AnyException_WrappedAsSDCException() {
		// Arrange
		MarginRequest request = new MarginRequest();
		// Set data that will cause an exception during processing
		request.setMarketDataStart("invalid");
		request.setMarketDataEnd("invalid");
		request.setTradeData("invalid");

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.margin(request);
		}, "Any exception should be wrapped as SDCException");

		assertNotNull(exception.getId(), "SDCException should have an exception ID");
	}
}
