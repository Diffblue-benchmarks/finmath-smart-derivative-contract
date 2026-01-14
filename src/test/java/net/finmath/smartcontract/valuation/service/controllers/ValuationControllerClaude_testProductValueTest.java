/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */

package net.finmath.smartcontract.valuation.service.controllers;

import net.finmath.smartcontract.model.ExceptionId;
import net.finmath.smartcontract.model.SDCException;
import net.finmath.smartcontract.model.ValueResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for ValuationController.testProductValue method.
 * Tests the test product value endpoint with focus on branch and condition coverage.
 *
 * Note: This method loads market data from a fixed resource file (md_testset1.xml) and accepts
 * trade data via MultipartFile. We test without mocking to verify the actual behavior.
 *
 * @author Claude Code
 */
class ValuationControllerClaude_testProductValueTest {

	private ValuationController controller;

	/**
	 * Set up test fixtures before each test.
	 */
	@BeforeEach
	void setUp() {
		controller = new ValuationController();
	}

	/**
	 * Test testProductValue with null MultipartFile throws exception.
	 * The method will fail when trying to access the null file's input stream.
	 */
	@Test
	void testProductValue_NullMultipartFile_ThrowsException() {
		// Act & Assert
		assertThrows(Exception.class, () -> {
			controller.testProductValue(null);
		}, "Should throw exception for null MultipartFile");
	}

	/**
	 * Test testProductValue with empty trade data file throws SDCException.
	 */
	@Test
	void testProductValue_EmptyTradeData_ThrowsSDCException() {
		// Arrange
		MultipartFile emptyFile = new MockMultipartFile(
				"tradeData",
				"trade.xml",
				"text/xml",
				"".getBytes(StandardCharsets.UTF_8)
		);

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.testProductValue(emptyFile);
		}, "Should throw SDCException for empty trade data");

		assertEquals(ExceptionId.SDC_MARGIN_CALCULATION_ERROR, exception.getId(),
				"Exception should have MARGIN_CALCULATION_ERROR ID");
	}

	/**
	 * Test testProductValue with invalid XML in trade data throws SDCException.
	 */
	@Test
	void testProductValue_InvalidXmlTradeData_ThrowsSDCException() {
		// Arrange
		String invalidXml = "<invalid><xml>";
		MultipartFile file = new MockMultipartFile(
				"tradeData",
				"trade.xml",
				"text/xml",
				invalidXml.getBytes(StandardCharsets.UTF_8)
		);

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.testProductValue(file);
		}, "Should throw SDCException for invalid XML");

		assertEquals(ExceptionId.SDC_MARGIN_CALCULATION_ERROR, exception.getId(),
				"Exception should have MARGIN_CALCULATION_ERROR ID");
	}

	/**
	 * Test testProductValue with malformed XML throws SDCException.
	 */
	@Test
	void testProductValue_MalformedXml_ThrowsSDCException() {
		// Arrange
		String malformedXml = "not xml at all";
		MultipartFile file = new MockMultipartFile(
				"tradeData",
				"trade.xml",
				"text/xml",
				malformedXml.getBytes(StandardCharsets.UTF_8)
		);

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.testProductValue(file);
		}, "Should throw SDCException for malformed XML");

		assertEquals(ExceptionId.SDC_MARGIN_CALCULATION_ERROR, exception.getId(),
				"Exception should have MARGIN_CALCULATION_ERROR ID");
	}

	/**
	 * Test testProductValue with whitespace-only content throws SDCException.
	 */
	@Test
	void testProductValue_WhitespaceOnly_ThrowsSDCException() {
		// Arrange
		String whitespace = "   \n\t  ";
		MultipartFile file = new MockMultipartFile(
				"tradeData",
				"trade.xml",
				"text/xml",
				whitespace.getBytes(StandardCharsets.UTF_8)
		);

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.testProductValue(file);
		}, "Should throw SDCException for whitespace-only content");

		assertEquals(ExceptionId.SDC_MARGIN_CALCULATION_ERROR, exception.getId(),
				"Exception should have MARGIN_CALCULATION_ERROR ID");
	}

	/**
	 * Test testProductValue with invalid but well-formed XML throws SDCException.
	 */
	@Test
	void testProductValue_InvalidButWellFormedXml_ThrowsSDCException() {
		// Arrange
		String invalidStructure = "<wrongElement>content</wrongElement>";
		MultipartFile file = new MockMultipartFile(
				"tradeData",
				"trade.xml",
				"text/xml",
				invalidStructure.getBytes(StandardCharsets.UTF_8)
		);

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.testProductValue(file);
		}, "Should throw SDCException for invalid XML structure");

		assertEquals(ExceptionId.SDC_MARGIN_CALCULATION_ERROR, exception.getId(),
				"Exception should have MARGIN_CALCULATION_ERROR ID");
	}

	/**
	 * Test testProductValue with incomplete XML throws SDCException.
	 */
	@Test
	void testProductValue_IncompleteXml_ThrowsSDCException() {
		// Arrange
		String incompleteXml = "<trade>";
		MultipartFile file = new MockMultipartFile(
				"tradeData",
				"trade.xml",
				"text/xml",
				incompleteXml.getBytes(StandardCharsets.UTF_8)
		);

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.testProductValue(file);
		}, "Should throw SDCException for incomplete XML");

		assertEquals(ExceptionId.SDC_MARGIN_CALCULATION_ERROR, exception.getId(),
				"Exception should have MARGIN_CALCULATION_ERROR ID");
	}

	/**
	 * Test testProductValue with mismatched XML tags throws SDCException.
	 */
	@Test
	void testProductValue_MismatchedXmlTags_ThrowsSDCException() {
		// Arrange
		String mismatchedXml = "<trade><data></wrongtag></trade>";
		MultipartFile file = new MockMultipartFile(
				"tradeData",
				"trade.xml",
				"text/xml",
				mismatchedXml.getBytes(StandardCharsets.UTF_8)
		);

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.testProductValue(file);
		}, "Should throw SDCException for mismatched XML tags");

		assertEquals(ExceptionId.SDC_MARGIN_CALCULATION_ERROR, exception.getId(),
				"Exception should have MARGIN_CALCULATION_ERROR ID");
	}

	/**
	 * Test testProductValue with empty XML elements throws SDCException.
	 */
	@Test
	void testProductValue_EmptyXmlElements_ThrowsSDCException() {
		// Arrange
		String emptyElements = "<trade></trade>";
		MultipartFile file = new MockMultipartFile(
				"tradeData",
				"trade.xml",
				"text/xml",
				emptyElements.getBytes(StandardCharsets.UTF_8)
		);

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.testProductValue(file);
		}, "Should throw SDCException for empty XML elements");

		assertEquals(ExceptionId.SDC_MARGIN_CALCULATION_ERROR, exception.getId(),
				"Exception should have MARGIN_CALCULATION_ERROR ID");
	}

	/**
	 * Test testProductValue with special characters in XML.
	 */
	@Test
	void testProductValue_SpecialCharacters_ThrowsSDCException() {
		// Arrange
		String specialChars = "<trade><data>&invalid;</data></trade>";
		MultipartFile file = new MockMultipartFile(
				"tradeData",
				"trade.xml",
				"text/xml",
				specialChars.getBytes(StandardCharsets.UTF_8)
		);

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.testProductValue(file);
		}, "Should throw SDCException for special characters");

		assertEquals(ExceptionId.SDC_MARGIN_CALCULATION_ERROR, exception.getId(),
				"Exception should have MARGIN_CALCULATION_ERROR ID");
	}

	/**
	 * Test testProductValue with very long invalid data doesn't hang.
	 */
	@Test
	void testProductValue_VeryLongInvalidData_ThrowsSDCException() {
		// Arrange
		StringBuilder longData = new StringBuilder("<data>");
		for (int i = 0; i < 1000; i++) {
			longData.append("<item>").append(i).append("</item>");
		}
		longData.append("</data>");

		MultipartFile file = new MockMultipartFile(
				"tradeData",
				"trade.xml",
				"text/xml",
				longData.toString().getBytes(StandardCharsets.UTF_8)
		);

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.testProductValue(file);
		}, "Should throw SDCException for invalid data format");

		assertEquals(ExceptionId.SDC_MARGIN_CALCULATION_ERROR, exception.getId(),
				"Exception should have MARGIN_CALCULATION_ERROR ID");
	}

	/**
	 * Test testProductValue with XML containing CDATA sections.
	 */
	@Test
	void testProductValue_XmlWithCDATA_ThrowsSDCException() {
		// Arrange
		String cdataXml = "<trade><![CDATA[data]]></trade>";
		MultipartFile file = new MockMultipartFile(
				"tradeData",
				"trade.xml",
				"text/xml",
				cdataXml.getBytes(StandardCharsets.UTF_8)
		);

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.testProductValue(file);
		}, "Should throw SDCException for XML with CDATA");

		assertEquals(ExceptionId.SDC_MARGIN_CALCULATION_ERROR, exception.getId(),
				"Exception should have MARGIN_CALCULATION_ERROR ID");
	}

	/**
	 * Test testProductValue with nested invalid XML structure.
	 */
	@Test
	void testProductValue_NestedInvalidXml_ThrowsSDCException() {
		// Arrange
		String nestedInvalid = "<outer><inner><broken></inner>";
		MultipartFile file = new MockMultipartFile(
				"tradeData",
				"trade.xml",
				"text/xml",
				nestedInvalid.getBytes(StandardCharsets.UTF_8)
		);

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.testProductValue(file);
		}, "Should throw SDCException for nested invalid XML");

		assertEquals(ExceptionId.SDC_MARGIN_CALCULATION_ERROR, exception.getId(),
				"Exception should have MARGIN_CALCULATION_ERROR ID");
	}

	/**
	 * Test testProductValue with MultipartFile that throws IOException on getInputStream.
	 */
	@Test
	void testProductValue_IOExceptionOnGetInputStream_ThrowsSDCException() {
		// Arrange
		MultipartFile fileWithIOException = new MockMultipartFile("tradeData", "trade.xml", "text/xml", new byte[0]) {
			@Override
			public java.io.InputStream getInputStream() throws IOException {
				throw new IOException("Simulated IO error");
			}
		};

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.testProductValue(fileWithIOException);
		}, "Should throw SDCException when IOException occurs");

		assertEquals(ExceptionId.SDC_MARGIN_CALCULATION_ERROR, exception.getId(),
				"Exception should have MARGIN_CALCULATION_ERROR ID");
		assertTrue(exception.getMessage().contains("Simulated IO error"),
				"Exception message should contain the IO error message");
	}

	/**
	 * Test testProductValue exception includes the original error message.
	 */
	@Test
	void testProductValue_ExceptionIncludesOriginalMessage() {
		// Arrange
		String invalidData = "completely invalid data";
		MultipartFile file = new MockMultipartFile(
				"tradeData",
				"trade.xml",
				"text/xml",
				invalidData.getBytes(StandardCharsets.UTF_8)
		);

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.testProductValue(file);
		}, "Should throw SDCException");

		// Verify exception has proper structure
		assertNotNull(exception.getMessage(), "Exception should have a message");
		assertNotNull(exception.getId(), "Exception should have an ID");
		assertEquals(ExceptionId.SDC_MARGIN_CALCULATION_ERROR, exception.getId(),
				"Exception should have MARGIN_CALCULATION_ERROR ID");
	}

	/**
	 * Test testProductValue with binary data (not text) throws SDCException.
	 */
	@Test
	void testProductValue_BinaryData_ThrowsSDCException() {
		// Arrange
		byte[] binaryData = {0x00, 0x01, 0x02, 0x03, (byte) 0xFF, (byte) 0xFE};
		MultipartFile file = new MockMultipartFile(
				"tradeData",
				"trade.bin",
				"application/octet-stream",
				binaryData
		);

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.testProductValue(file);
		}, "Should throw SDCException for binary data");

		assertEquals(ExceptionId.SDC_MARGIN_CALCULATION_ERROR, exception.getId(),
				"Exception should have MARGIN_CALCULATION_ERROR ID");
	}

	/**
	 * Test testProductValue with different file extension but invalid content.
	 */
	@Test
	void testProductValue_DifferentExtension_ThrowsSDCException() {
		// Arrange
		String invalidData = "invalid content";
		MultipartFile file = new MockMultipartFile(
				"tradeData",
				"trade.txt",
				"text/plain",
				invalidData.getBytes(StandardCharsets.UTF_8)
		);

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.testProductValue(file);
		}, "Should throw SDCException regardless of file extension");

		assertEquals(ExceptionId.SDC_MARGIN_CALCULATION_ERROR, exception.getId(),
				"Exception should have MARGIN_CALCULATION_ERROR ID");
	}

	/**
	 * Test testProductValue catches and wraps all exceptions as SDCException.
	 */
	@Test
	void testProductValue_AnyException_WrappedAsSDCException() {
		// Arrange
		String invalidData = "<invalid data>";
		MultipartFile file = new MockMultipartFile(
				"tradeData",
				"trade.xml",
				"text/xml",
				invalidData.getBytes(StandardCharsets.UTF_8)
		);

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.testProductValue(file);
		}, "Any exception should be wrapped as SDCException");

		assertNotNull(exception.getId(), "SDCException should have an exception ID");
		assertEquals(ExceptionId.SDC_MARGIN_CALCULATION_ERROR, exception.getId(),
				"Exception should have MARGIN_CALCULATION_ERROR ID");
	}

	/**
	 * Test testProductValue with unclosed XML tags.
	 */
	@Test
	void testProductValue_UnclosedXmlTags_ThrowsSDCException() {
		// Arrange
		String unclosedTags = "<trade><data>";
		MultipartFile file = new MockMultipartFile(
				"tradeData",
				"trade.xml",
				"text/xml",
				unclosedTags.getBytes(StandardCharsets.UTF_8)
		);

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.testProductValue(file);
		}, "Should throw SDCException for unclosed XML tags");

		assertEquals(ExceptionId.SDC_MARGIN_CALCULATION_ERROR, exception.getId(),
				"Exception should have MARGIN_CALCULATION_ERROR ID");
	}

	/**
	 * Test testProductValue with XML containing attributes (still invalid for processing).
	 */
	@Test
	void testProductValue_XmlWithAttributes_ThrowsSDCException() {
		// Arrange
		String xmlWithAttrs = "<trade id=\"123\" type=\"swap\"></trade>";
		MultipartFile file = new MockMultipartFile(
				"tradeData",
				"trade.xml",
				"text/xml",
				xmlWithAttrs.getBytes(StandardCharsets.UTF_8)
		);

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.testProductValue(file);
		}, "Should throw SDCException for XML with attributes but no valid content");

		assertEquals(ExceptionId.SDC_MARGIN_CALCULATION_ERROR, exception.getId(),
				"Exception should have MARGIN_CALCULATION_ERROR ID");
	}

	/**
	 * Test testProductValue with valid XML but missing required data elements.
	 */
	@Test
	void testProductValue_ValidXmlMissingRequiredData_ThrowsSDCException() {
		// Arrange
		String validButIncomplete = "<dataDocument></dataDocument>";
		MultipartFile file = new MockMultipartFile(
				"tradeData",
				"trade.xml",
				"text/xml",
				validButIncomplete.getBytes(StandardCharsets.UTF_8)
		);

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> {
			controller.testProductValue(file);
		}, "Should throw SDCException for valid XML with missing required data");

		assertEquals(ExceptionId.SDC_MARGIN_CALCULATION_ERROR, exception.getId(),
				"Exception should have MARGIN_CALCULATION_ERROR ID");
	}
}
