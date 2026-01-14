/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */

package net.finmath.smartcontract.product;

import net.finmath.smartcontract.product.SmartDerivativeContractDescriptor.Party;
import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationDataItem;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for SmartDerivativeContractDescriptor.
 * Tests all methods with focus on branch and condition coverage.
 *
 * @author Claude Code
 */
class SmartDerivativeContractDescriptorClaudeTest {

	/**
	 * Helper method to create a valid SmartDerivativeContractDescriptor instance.
	 */
	private SmartDerivativeContractDescriptor createValidDescriptor() throws Exception {
		String dltTradeId = "trade123";
		String dltAddress = "0x1234567890abcdef";
		String uniqueTradeIdentifier = "UTI-123456";
		LocalDate tradeDate = LocalDate.of(2024, 1, 15);
		OffsetTime settlementTime = OffsetTime.of(10, 0, 0, 0, ZoneOffset.UTC);

		List<Party> counterparties = new ArrayList<>();
		counterparties.add(new Party("party1", "Party One", "href1", "address1"));
		counterparties.add(new Party("party2", "Party Two", "href2", "address2"));

		Map<String, Double> marginAccounts = new HashMap<>();
		marginAccounts.put("party1", 10000.0);
		marginAccounts.put("party2", 20000.0);

		Map<String, Double> penaltyFees = new HashMap<>();
		penaltyFees.put("party1", 100.0);
		penaltyFees.put("party2", 200.0);

		String receiverPartyID = "party1";

		// Create a simple XML node for underlying
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.newDocument();
		Node underlying = doc.createElement("swap");

		List<CalibrationDataItem.Spec> marketdataItems = new ArrayList<>();
		marketdataItems.add(new CalibrationDataItem.Spec("key1", "EUR-DISCOUNT", "DEPOSIT", "1Y"));

		String currency = "EUR";
		String marketDataProvider = "Reuters";
		String tradeType = "InterestRateSwap";

		return new SmartDerivativeContractDescriptor(
				dltTradeId, dltAddress, uniqueTradeIdentifier, tradeDate, settlementTime,
				counterparties, marginAccounts, penaltyFees, receiverPartyID,
				underlying, marketdataItems, currency, marketDataProvider, tradeType
		);
	}

	/**
	 * Test successful construction with valid parameters.
	 */
	@Test
	void testConstructor_ValidParameters_Success() throws Exception {
		// Arrange & Act
		SmartDerivativeContractDescriptor descriptor = createValidDescriptor();

		// Assert
		assertNotNull(descriptor, "Descriptor should be created successfully");
		assertEquals("trade123", descriptor.getDltTradeId());
		assertEquals("0x1234567890abcdef", descriptor.getDltAddress());
		assertEquals("UTI-123456", descriptor.getUniqueTradeIdentifier());
	}

	/**
	 * Test constructor validation: counterparties must be exactly 2.
	 */
	@Test
	void testConstructor_InvalidCounterpartiesCount_ThrowsException() throws Exception {
		// Arrange
		String dltTradeId = "trade123";
		String dltAddress = "0x1234567890abcdef";
		String uniqueTradeIdentifier = "UTI-123456";
		LocalDate tradeDate = LocalDate.of(2024, 1, 15);
		OffsetTime settlementTime = OffsetTime.of(10, 0, 0, 0, ZoneOffset.UTC);

		// Only one counterparty (invalid)
		List<Party> counterparties = new ArrayList<>();
		counterparties.add(new Party("party1", "Party One", "href1", "address1"));

		Map<String, Double> marginAccounts = new HashMap<>();
		marginAccounts.put("party1", 10000.0);
		marginAccounts.put("party2", 20000.0);

		Map<String, Double> penaltyFees = new HashMap<>();
		penaltyFees.put("party1", 100.0);
		penaltyFees.put("party2", 200.0);

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.newDocument();
		Node underlying = doc.createElement("swap");

		List<CalibrationDataItem.Spec> marketdataItems = new ArrayList<>();

		// Act & Assert
		assertThrows(IllegalArgumentException.class, () -> {
			new SmartDerivativeContractDescriptor(
					dltTradeId, dltAddress, uniqueTradeIdentifier, tradeDate, settlementTime,
					counterparties, marginAccounts, penaltyFees, "party1",
					underlying, marketdataItems, "EUR", "Reuters", "InterestRateSwap"
			);
		}, "Should throw IllegalArgumentException when counterparties count is not 2");
	}

	/**
	 * Test constructor validation: margin accounts must be exactly 2.
	 */
	@Test
	void testConstructor_InvalidMarginAccountsCount_ThrowsException() throws Exception {
		// Arrange
		String dltTradeId = "trade123";
		String dltAddress = "0x1234567890abcdef";
		String uniqueTradeIdentifier = "UTI-123456";
		LocalDate tradeDate = LocalDate.of(2024, 1, 15);
		OffsetTime settlementTime = OffsetTime.of(10, 0, 0, 0, ZoneOffset.UTC);

		List<Party> counterparties = new ArrayList<>();
		counterparties.add(new Party("party1", "Party One", "href1", "address1"));
		counterparties.add(new Party("party2", "Party Two", "href2", "address2"));

		// Only one margin account (invalid)
		Map<String, Double> marginAccounts = new HashMap<>();
		marginAccounts.put("party1", 10000.0);

		Map<String, Double> penaltyFees = new HashMap<>();
		penaltyFees.put("party1", 100.0);
		penaltyFees.put("party2", 200.0);

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.newDocument();
		Node underlying = doc.createElement("swap");

		List<CalibrationDataItem.Spec> marketdataItems = new ArrayList<>();

		// Act & Assert
		assertThrows(IllegalArgumentException.class, () -> {
			new SmartDerivativeContractDescriptor(
					dltTradeId, dltAddress, uniqueTradeIdentifier, tradeDate, settlementTime,
					counterparties, marginAccounts, penaltyFees, "party1",
					underlying, marketdataItems, "EUR", "Reuters", "InterestRateSwap"
			);
		}, "Should throw IllegalArgumentException when margin accounts count is not 2");
	}

	/**
	 * Test constructor validation: penalty fees must be exactly 2.
	 */
	@Test
	void testConstructor_InvalidPenaltyFeesCount_ThrowsException() throws Exception {
		// Arrange
		String dltTradeId = "trade123";
		String dltAddress = "0x1234567890abcdef";
		String uniqueTradeIdentifier = "UTI-123456";
		LocalDate tradeDate = LocalDate.of(2024, 1, 15);
		OffsetTime settlementTime = OffsetTime.of(10, 0, 0, 0, ZoneOffset.UTC);

		List<Party> counterparties = new ArrayList<>();
		counterparties.add(new Party("party1", "Party One", "href1", "address1"));
		counterparties.add(new Party("party2", "Party Two", "href2", "address2"));

		Map<String, Double> marginAccounts = new HashMap<>();
		marginAccounts.put("party1", 10000.0);
		marginAccounts.put("party2", 20000.0);

		// Only one penalty fee (invalid)
		Map<String, Double> penaltyFees = new HashMap<>();
		penaltyFees.put("party1", 100.0);

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.newDocument();
		Node underlying = doc.createElement("swap");

		List<CalibrationDataItem.Spec> marketdataItems = new ArrayList<>();

		// Act & Assert
		assertThrows(IllegalArgumentException.class, () -> {
			new SmartDerivativeContractDescriptor(
					dltTradeId, dltAddress, uniqueTradeIdentifier, tradeDate, settlementTime,
					counterparties, marginAccounts, penaltyFees, "party1",
					underlying, marketdataItems, "EUR", "Reuters", "InterestRateSwap"
			);
		}, "Should throw IllegalArgumentException when penalty fees count is not 2");
	}

	/**
	 * Test constructor validation: underlying must not be null.
	 */
	@Test
	void testConstructor_NullUnderlying_ThrowsException() throws Exception {
		// Arrange
		String dltTradeId = "trade123";
		String dltAddress = "0x1234567890abcdef";
		String uniqueTradeIdentifier = "UTI-123456";
		LocalDate tradeDate = LocalDate.of(2024, 1, 15);
		OffsetTime settlementTime = OffsetTime.of(10, 0, 0, 0, ZoneOffset.UTC);

		List<Party> counterparties = new ArrayList<>();
		counterparties.add(new Party("party1", "Party One", "href1", "address1"));
		counterparties.add(new Party("party2", "Party Two", "href2", "address2"));

		Map<String, Double> marginAccounts = new HashMap<>();
		marginAccounts.put("party1", 10000.0);
		marginAccounts.put("party2", 20000.0);

		Map<String, Double> penaltyFees = new HashMap<>();
		penaltyFees.put("party1", 100.0);
		penaltyFees.put("party2", 200.0);

		List<CalibrationDataItem.Spec> marketdataItems = new ArrayList<>();

		// Act & Assert
		assertThrows(NullPointerException.class, () -> {
			new SmartDerivativeContractDescriptor(
					dltTradeId, dltAddress, uniqueTradeIdentifier, tradeDate, settlementTime,
					counterparties, marginAccounts, penaltyFees, "party1",
					null, marketdataItems, "EUR", "Reuters", "InterestRateSwap"
			);
		}, "Should throw NullPointerException when underlying is null");
	}

	/**
	 * Test constructor validation: tradeType must not be null.
	 */
	@Test
	void testConstructor_NullTradeType_ThrowsException() throws Exception {
		// Arrange
		String dltTradeId = "trade123";
		String dltAddress = "0x1234567890abcdef";
		String uniqueTradeIdentifier = "UTI-123456";
		LocalDate tradeDate = LocalDate.of(2024, 1, 15);
		OffsetTime settlementTime = OffsetTime.of(10, 0, 0, 0, ZoneOffset.UTC);

		List<Party> counterparties = new ArrayList<>();
		counterparties.add(new Party("party1", "Party One", "href1", "address1"));
		counterparties.add(new Party("party2", "Party Two", "href2", "address2"));

		Map<String, Double> marginAccounts = new HashMap<>();
		marginAccounts.put("party1", 10000.0);
		marginAccounts.put("party2", 20000.0);

		Map<String, Double> penaltyFees = new HashMap<>();
		penaltyFees.put("party1", 100.0);
		penaltyFees.put("party2", 200.0);

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.newDocument();
		Node underlying = doc.createElement("swap");

		List<CalibrationDataItem.Spec> marketdataItems = new ArrayList<>();

		// Act & Assert
		assertThrows(NullPointerException.class, () -> {
			new SmartDerivativeContractDescriptor(
					dltTradeId, dltAddress, uniqueTradeIdentifier, tradeDate, settlementTime,
					counterparties, marginAccounts, penaltyFees, "party1",
					underlying, marketdataItems, "EUR", "Reuters", null
			);
		}, "Should throw NullPointerException when tradeType is null");
	}

	/**
	 * Test getDltTradeId returns correct value.
	 */
	@Test
	void testGetDltTradeId_ReturnsCorrectValue() throws Exception {
		// Arrange
		SmartDerivativeContractDescriptor descriptor = createValidDescriptor();

		// Act
		String result = descriptor.getDltTradeId();

		// Assert
		assertEquals("trade123", result, "getDltTradeId should return the correct DLT trade ID");
	}

	/**
	 * Test getDltAddress returns correct value.
	 */
	@Test
	void testGetDltAddress_ReturnsCorrectValue() throws Exception {
		// Arrange
		SmartDerivativeContractDescriptor descriptor = createValidDescriptor();

		// Act
		String result = descriptor.getDltAddress();

		// Assert
		assertEquals("0x1234567890abcdef", result, "getDltAddress should return the correct DLT address");
	}

	/**
	 * Test getUniqueTradeIdentifier returns correct value.
	 */
	@Test
	void testGetUniqueTradeIdentifier_ReturnsCorrectValue() throws Exception {
		// Arrange
		SmartDerivativeContractDescriptor descriptor = createValidDescriptor();

		// Act
		String result = descriptor.getUniqueTradeIdentifier();

		// Assert
		assertEquals("UTI-123456", result, "getUniqueTradeIdentifier should return the correct UTI");
	}

	/**
	 * Test getTradeDate returns correct value.
	 */
	@Test
	void testGetTradeDate_ReturnsCorrectValue() throws Exception {
		// Arrange
		SmartDerivativeContractDescriptor descriptor = createValidDescriptor();

		// Act
		LocalDate result = descriptor.getTradeDate();

		// Assert
		assertEquals(LocalDate.of(2024, 1, 15), result, "getTradeDate should return the correct trade date");
	}

	/**
	 * Test getSettlementTime returns correct value.
	 */
	@Test
	void testGetSettlementTime_ReturnsCorrectValue() throws Exception {
		// Arrange
		SmartDerivativeContractDescriptor descriptor = createValidDescriptor();

		// Act
		OffsetTime result = descriptor.getSettlementTime();

		// Assert
		assertEquals(OffsetTime.of(10, 0, 0, 0, ZoneOffset.UTC), result,
				"getSettlementTime should return the correct settlement time");
	}

	/**
	 * Test getCounterparties returns correct list.
	 */
	@Test
	void testGetCounterparties_ReturnsCorrectList() throws Exception {
		// Arrange
		SmartDerivativeContractDescriptor descriptor = createValidDescriptor();

		// Act
		List<Party> result = descriptor.getCounterparties();

		// Assert
		assertNotNull(result, "getCounterparties should not return null");
		assertEquals(2, result.size(), "getCounterparties should return exactly 2 parties");
		assertEquals("party1", result.get(0).getId(), "First party should have ID 'party1'");
		assertEquals("party2", result.get(1).getId(), "Second party should have ID 'party2'");
	}

	/**
	 * Test getMarginAccount returns correct value for existing party.
	 */
	@Test
	void testGetMarginAccount_ExistingParty_ReturnsCorrectValue() throws Exception {
		// Arrange
		SmartDerivativeContractDescriptor descriptor = createValidDescriptor();

		// Act
		Double result1 = descriptor.getMarginAccount("party1");
		Double result2 = descriptor.getMarginAccount("party2");

		// Assert
		assertEquals(10000.0, result1, "getMarginAccount should return 10000.0 for party1");
		assertEquals(20000.0, result2, "getMarginAccount should return 20000.0 for party2");
	}

	/**
	 * Test getMarginAccount returns null for non-existing party.
	 */
	@Test
	void testGetMarginAccount_NonExistingParty_ReturnsNull() throws Exception {
		// Arrange
		SmartDerivativeContractDescriptor descriptor = createValidDescriptor();

		// Act
		Double result = descriptor.getMarginAccount("nonExistingParty");

		// Assert
		assertNull(result, "getMarginAccount should return null for non-existing party");
	}

	/**
	 * Test getPenaltyFee returns correct value for existing party.
	 */
	@Test
	void testGetPenaltyFee_ExistingParty_ReturnsCorrectValue() throws Exception {
		// Arrange
		SmartDerivativeContractDescriptor descriptor = createValidDescriptor();

		// Act
		Double result1 = descriptor.getPenaltyFee("party1");
		Double result2 = descriptor.getPenaltyFee("party2");

		// Assert
		assertEquals(100.0, result1, "getPenaltyFee should return 100.0 for party1");
		assertEquals(200.0, result2, "getPenaltyFee should return 200.0 for party2");
	}

	/**
	 * Test getPenaltyFee returns null for non-existing party.
	 */
	@Test
	void testGetPenaltyFee_NonExistingParty_ReturnsNull() throws Exception {
		// Arrange
		SmartDerivativeContractDescriptor descriptor = createValidDescriptor();

		// Act
		Double result = descriptor.getPenaltyFee("nonExistingParty");

		// Assert
		assertNull(result, "getPenaltyFee should return null for non-existing party");
	}

	/**
	 * Test getUnderlying returns correct node.
	 */
	@Test
	void testGetUnderlying_ReturnsCorrectNode() throws Exception {
		// Arrange
		SmartDerivativeContractDescriptor descriptor = createValidDescriptor();

		// Act
		Node result = descriptor.getUnderlying();

		// Assert
		assertNotNull(result, "getUnderlying should not return null");
		assertEquals("swap", result.getNodeName(), "Underlying node should have name 'swap'");
	}

	/**
	 * Test getUnderlyingReceiverPartyID returns correct value.
	 */
	@Test
	void testGetUnderlyingReceiverPartyID_ReturnsCorrectValue() throws Exception {
		// Arrange
		SmartDerivativeContractDescriptor descriptor = createValidDescriptor();

		// Act
		String result = descriptor.getUnderlyingReceiverPartyID();

		// Assert
		assertEquals("party1", result, "getUnderlyingReceiverPartyID should return 'party1'");
	}

	/**
	 * Test getMarketdataItemList returns correct list.
	 */
	@Test
	void testGetMarketdataItemList_ReturnsCorrectList() throws Exception {
		// Arrange
		SmartDerivativeContractDescriptor descriptor = createValidDescriptor();

		// Act
		List<CalibrationDataItem.Spec> result = descriptor.getMarketdataItemList();

		// Assert
		assertNotNull(result, "getMarketdataItemList should not return null");
		assertEquals(1, result.size(), "getMarketdataItemList should return list with 1 item");
		assertEquals("key1", result.get(0).getKey(), "Market data item should have key 'key1'");
		assertEquals("EUR-DISCOUNT", result.get(0).getCurveName(), "Market data item should have curve name 'EUR-DISCOUNT'");
	}

	/**
	 * Test getMarketdataItemList with empty list.
	 */
	@Test
	void testGetMarketdataItemList_EmptyList_ReturnsEmptyList() throws Exception {
		// Arrange
		String dltTradeId = "trade123";
		String dltAddress = "0x1234567890abcdef";
		String uniqueTradeIdentifier = "UTI-123456";
		LocalDate tradeDate = LocalDate.of(2024, 1, 15);
		OffsetTime settlementTime = OffsetTime.of(10, 0, 0, 0, ZoneOffset.UTC);

		List<Party> counterparties = new ArrayList<>();
		counterparties.add(new Party("party1", "Party One", "href1", "address1"));
		counterparties.add(new Party("party2", "Party Two", "href2", "address2"));

		Map<String, Double> marginAccounts = new HashMap<>();
		marginAccounts.put("party1", 10000.0);
		marginAccounts.put("party2", 20000.0);

		Map<String, Double> penaltyFees = new HashMap<>();
		penaltyFees.put("party1", 100.0);
		penaltyFees.put("party2", 200.0);

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.newDocument();
		Node underlying = doc.createElement("swap");

		List<CalibrationDataItem.Spec> marketdataItems = new ArrayList<>(); // Empty list

		SmartDerivativeContractDescriptor descriptor = new SmartDerivativeContractDescriptor(
				dltTradeId, dltAddress, uniqueTradeIdentifier, tradeDate, settlementTime,
				counterparties, marginAccounts, penaltyFees, "party1",
				underlying, marketdataItems, "EUR", "Reuters", "InterestRateSwap"
		);

		// Act
		List<CalibrationDataItem.Spec> result = descriptor.getMarketdataItemList();

		// Assert
		assertNotNull(result, "getMarketdataItemList should not return null even for empty list");
		assertEquals(0, result.size(), "getMarketdataItemList should return empty list");
	}

	/**
	 * Test getCurrency returns correct value.
	 */
	@Test
	void testGetCurrency_ReturnsCorrectValue() throws Exception {
		// Arrange
		SmartDerivativeContractDescriptor descriptor = createValidDescriptor();

		// Act
		String result = descriptor.getCurrency();

		// Assert
		assertEquals("EUR", result, "getCurrency should return 'EUR'");
	}

	/**
	 * Test getMarketDataProvider returns correct value.
	 */
	@Test
	void testGetMarketDataProvider_ReturnsCorrectValue() throws Exception {
		// Arrange
		SmartDerivativeContractDescriptor descriptor = createValidDescriptor();

		// Act
		String result = descriptor.getMarketDataProvider();

		// Assert
		assertEquals("Reuters", result, "getMarketDataProvider should return 'Reuters'");
	}

	/**
	 * Test getTradeType returns correct value.
	 */
	@Test
	void testGetTradeType_ReturnsCorrectValue() throws Exception {
		// Arrange
		SmartDerivativeContractDescriptor descriptor = createValidDescriptor();

		// Act
		String result = descriptor.getTradeType();

		// Assert
		assertEquals("InterestRateSwap", result, "getTradeType should return 'InterestRateSwap'");
	}

	/**
	 * Test with different trade types to cover different scenarios.
	 */
	@Test
	void testGetTradeType_DifferentTradeTypes_ReturnsCorrectValues() throws Exception {
		// Arrange - Create descriptors with different trade types
		String[] tradeTypes = {"InterestRateSwap", "CreditDefaultSwap", "FXOption", "Equity"};

		for (String tradeType : tradeTypes) {
			String dltTradeId = "trade123";
			String dltAddress = "0x1234567890abcdef";
			String uniqueTradeIdentifier = "UTI-123456";
			LocalDate tradeDate = LocalDate.of(2024, 1, 15);
			OffsetTime settlementTime = OffsetTime.of(10, 0, 0, 0, ZoneOffset.UTC);

			List<Party> counterparties = new ArrayList<>();
			counterparties.add(new Party("party1", "Party One", "href1", "address1"));
			counterparties.add(new Party("party2", "Party Two", "href2", "address2"));

			Map<String, Double> marginAccounts = new HashMap<>();
			marginAccounts.put("party1", 10000.0);
			marginAccounts.put("party2", 20000.0);

			Map<String, Double> penaltyFees = new HashMap<>();
			penaltyFees.put("party1", 100.0);
			penaltyFees.put("party2", 200.0);

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.newDocument();
			Node underlying = doc.createElement("swap");

			List<CalibrationDataItem.Spec> marketdataItems = new ArrayList<>();

			SmartDerivativeContractDescriptor descriptor = new SmartDerivativeContractDescriptor(
					dltTradeId, dltAddress, uniqueTradeIdentifier, tradeDate, settlementTime,
					counterparties, marginAccounts, penaltyFees, "party1",
					underlying, marketdataItems, "EUR", "Reuters", tradeType
			);

			// Act
			String result = descriptor.getTradeType();

			// Assert
			assertEquals(tradeType, result, "getTradeType should return '" + tradeType + "'");
		}
	}

	/**
	 * Test with null optional parameters (those that are allowed to be null).
	 */
	@Test
	void testConstructor_NullOptionalParameters_Success() throws Exception {
		// Arrange
		List<Party> counterparties = new ArrayList<>();
		counterparties.add(new Party("party1", "Party One", "href1", "address1"));
		counterparties.add(new Party("party2", "Party Two", "href2", "address2"));

		Map<String, Double> marginAccounts = new HashMap<>();
		marginAccounts.put("party1", 10000.0);
		marginAccounts.put("party2", 20000.0);

		Map<String, Double> penaltyFees = new HashMap<>();
		penaltyFees.put("party1", 100.0);
		penaltyFees.put("party2", 200.0);

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.newDocument();
		Node underlying = doc.createElement("swap");

		List<CalibrationDataItem.Spec> marketdataItems = new ArrayList<>();

		// Act - null values for fields that might be allowed to be null
		SmartDerivativeContractDescriptor descriptor = new SmartDerivativeContractDescriptor(
				null, null, null, null, null,
				counterparties, marginAccounts, penaltyFees, null,
				underlying, marketdataItems, null, null, "InterestRateSwap"
		);

		// Assert
		assertNotNull(descriptor, "Descriptor should be created even with some null fields");
		assertNull(descriptor.getDltTradeId(), "getDltTradeId should return null");
		assertNull(descriptor.getDltAddress(), "getDltAddress should return null");
		assertNull(descriptor.getUniqueTradeIdentifier(), "getUniqueTradeIdentifier should return null");
		assertNull(descriptor.getTradeDate(), "getTradeDate should return null");
		assertNull(descriptor.getSettlementTime(), "getSettlementTime should return null");
		assertNull(descriptor.getUnderlyingReceiverPartyID(), "getUnderlyingReceiverPartyID should return null");
		assertNull(descriptor.getCurrency(), "getCurrency should return null");
		assertNull(descriptor.getMarketDataProvider(), "getMarketDataProvider should return null");
	}

	// ========== Tests for Party inner class ==========

	/**
	 * Test Party constructor with all non-null parameters.
	 */
	@Test
	void testPartyConstructor_AllNonNullParameters_Success() {
		// Arrange & Act
		Party party = new Party("testId", "Test Name", "http://test.href", "0xTestAddress");

		// Assert
		assertNotNull(party, "Party should be created successfully");
		assertEquals("testId", party.getId());
		assertEquals("Test Name", party.getName());
		assertEquals("http://test.href", party.getHref());
		assertEquals("0xTestAddress", party.getAddress());
	}

	/**
	 * Test Party constructor with null parameters.
	 */
	@Test
	void testPartyConstructor_NullParameters_Success() {
		// Arrange & Act
		Party party = new Party(null, null, null, null);

		// Assert
		assertNotNull(party, "Party should be created even with null parameters");
		assertNull(party.getId());
		assertNull(party.getName());
		assertNull(party.getHref());
		assertNull(party.getAddress());
	}

	/**
	 * Test Party constructor with empty strings.
	 */
	@Test
	void testPartyConstructor_EmptyStrings_Success() {
		// Arrange & Act
		Party party = new Party("", "", "", "");

		// Assert
		assertNotNull(party, "Party should be created with empty strings");
		assertEquals("", party.getId());
		assertEquals("", party.getName());
		assertEquals("", party.getHref());
		assertEquals("", party.getAddress());
	}

	/**
	 * Test Party constructor with mixed null and non-null parameters.
	 */
	@Test
	void testPartyConstructor_MixedParameters_Success() {
		// Arrange & Act
		Party party = new Party("id123", null, "http://example.com", null);

		// Assert
		assertNotNull(party, "Party should be created with mixed parameters");
		assertEquals("id123", party.getId());
		assertNull(party.getName());
		assertEquals("http://example.com", party.getHref());
		assertNull(party.getAddress());
	}

	/**
	 * Test Party getId method returns correct value.
	 */
	@Test
	void testPartyGetId_ReturnsCorrectValue() {
		// Arrange
		Party party = new Party("partyId123", "Party Name", "href", "address");

		// Act
		String result = party.getId();

		// Assert
		assertEquals("partyId123", result, "getId should return the correct id");
	}

	/**
	 * Test Party getName method returns correct value.
	 */
	@Test
	void testPartyGetName_ReturnsCorrectValue() {
		// Arrange
		Party party = new Party("id", "Alice Smith", "href", "address");

		// Act
		String result = party.getName();

		// Assert
		assertEquals("Alice Smith", result, "getName should return the correct name");
	}

	/**
	 * Test Party getHref method returns correct value.
	 */
	@Test
	void testPartyGetHref_ReturnsCorrectValue() {
		// Arrange
		Party party = new Party("id", "name", "http://example.com/party", "address");

		// Act
		String result = party.getHref();

		// Assert
		assertEquals("http://example.com/party", result, "getHref should return the correct href");
	}

	/**
	 * Test Party getAddress method returns correct value.
	 */
	@Test
	void testPartyGetAddress_ReturnsCorrectValue() {
		// Arrange
		Party party = new Party("id", "name", "href", "0x742d35Cc6634C0532925a3b844Bc9e7595f0bEb");

		// Act
		String result = party.getAddress();

		// Assert
		assertEquals("0x742d35Cc6634C0532925a3b844Bc9e7595f0bEb", result, "getAddress should return the correct address");
	}

	/**
	 * Test Party toString method with all non-null fields.
	 */
	@Test
	void testPartyToString_AllNonNullFields_ReturnsFormattedString() {
		// Arrange
		Party party = new Party("party1", "Party One", "http://party1.com", "0x123456");

		// Act
		String result = party.toString();

		// Assert
		assertNotNull(result, "toString should not return null");
		assertTrue(result.contains("party1"), "toString should contain id");
		assertTrue(result.contains("Party One"), "toString should contain name");
		assertTrue(result.contains("http://party1.com"), "toString should contain href");
		assertTrue(result.contains("0x123456"), "toString should contain address");
		assertTrue(result.contains("Party {"), "toString should contain 'Party {'");
	}

	/**
	 * Test Party toString method with null fields.
	 */
	@Test
	void testPartyToString_NullFields_ReturnsFormattedString() {
		// Arrange
		Party party = new Party(null, null, null, null);

		// Act
		String result = party.toString();

		// Assert
		assertNotNull(result, "toString should not return null even with null fields");
		assertTrue(result.contains("Party {"), "toString should contain 'Party {'");
		assertTrue(result.contains("id='null'"), "toString should contain 'id='null''");
		assertTrue(result.contains("name='null'"), "toString should contain 'name='null''");
	}

	/**
	 * Test Party toString method format structure.
	 */
	@Test
	void testPartyToString_FormatStructure_IsCorrect() {
		// Arrange
		Party party = new Party("testId", "Test Name", "testHref", "testAddress");

		// Act
		String result = party.toString();

		// Assert
		String expected = "Party {id='testId', name='Test Name', href='testHref', address='testAddress'}";
		assertEquals(expected, result, "toString should return exactly formatted string");
	}

	/**
	 * Test Party with special characters in fields.
	 */
	@Test
	void testParty_SpecialCharactersInFields_HandledCorrectly() {
		// Arrange
		Party party = new Party("id-123_test", "Name with spaces & symbols!", "http://test.com?param=value", "0x123ABC");

		// Act & Assert
		assertEquals("id-123_test", party.getId());
		assertEquals("Name with spaces & symbols!", party.getName());
		assertEquals("http://test.com?param=value", party.getHref());
		assertEquals("0x123ABC", party.getAddress());
		assertNotNull(party.toString());
	}

	/**
	 * Test Party with very long strings.
	 */
	@Test
	void testParty_VeryLongStrings_HandledCorrectly() {
		// Arrange
		String longString = "a".repeat(1000);
		Party party = new Party(longString, longString, longString, longString);

		// Act & Assert
		assertEquals(longString, party.getId());
		assertEquals(longString, party.getName());
		assertEquals(longString, party.getHref());
		assertEquals(longString, party.getAddress());
		assertNotNull(party.toString());
		assertTrue(party.toString().length() > 1000);
	}

	/**
	 * Test Party with Unicode characters.
	 */
	@Test
	void testParty_UnicodeCharacters_HandledCorrectly() {
		// Arrange
		Party party = new Party("id_日本語", "名前 中文 한국어", "http://例え.com", "地址123");

		// Act & Assert
		assertEquals("id_日本語", party.getId());
		assertEquals("名前 中文 한국어", party.getName());
		assertEquals("http://例え.com", party.getHref());
		assertEquals("地址123", party.getAddress());
		assertNotNull(party.toString());
		assertTrue(party.toString().contains("日本語"));
	}
}
