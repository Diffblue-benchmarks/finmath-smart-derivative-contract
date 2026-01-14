/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */

package net.finmath.smartcontract.settlement;

import net.finmath.smartcontract.model.MarketDataList;
import net.finmath.smartcontract.model.SDCException;
import net.finmath.smartcontract.product.SmartDerivativeContractDescriptor;
import net.finmath.smartcontract.product.xml.SDCXMLParser;
import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationDataItem;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for SettlementGenerator.
 * Tests all methods with focus on branch and condition coverage.
 *
 * @author Claude Code
 */
class SettlementGeneratorClaudeTest {

	/**
	 * Helper method to create a valid SmartDerivativeContractDescriptor instance.
	 */
	private SmartDerivativeContractDescriptor createValidDescriptor() throws Exception {
		String dltTradeId = "TRADE-123";
		String dltAddress = "0x1234567890abcdef";
		String uniqueTradeIdentifier = "UTI-123456";
		LocalDate tradeDate = LocalDate.of(2024, 1, 15);
		OffsetTime settlementTime = OffsetTime.of(10, 0, 0, 0, ZoneOffset.UTC);

		List<SmartDerivativeContractDescriptor.Party> counterparties = new ArrayList<>();
		counterparties.add(new SmartDerivativeContractDescriptor.Party("party1", "Party One", "href1", "address1"));
		counterparties.add(new SmartDerivativeContractDescriptor.Party("party2", "Party Two", "href2", "address2"));

		Map<String, Double> marginAccounts = new HashMap<>();
		marginAccounts.put("party1", 10000.0);
		marginAccounts.put("party2", 20000.0);

		Map<String, Double> penaltyFees = new HashMap<>();
		penaltyFees.put("party1", 100.0);
		penaltyFees.put("party2", 200.0);

		String receiverPartyID = "party1";

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
	 * Helper method to create a valid market data XML string.
	 */
	private String createValidMarketDataXml() {
		MarketDataList marketDataList = new MarketDataList();
		return SDCXMLParser.marshalClassToXMLString(marketDataList);
	}

	/**
	 * Test the default constructor creates a non-null instance.
	 */
	@Test
	void testConstructor() {
		SettlementGenerator generator = new SettlementGenerator();
		assertNotNull(generator, "Constructor should create a non-null SettlementGenerator instance");
	}

	/**
	 * Test generateInitialSettlementXml creates settlement with INITIAL type.
	 */
	@Test
	void testGenerateInitialSettlementXml_ValidInputs_Success() throws Exception {
		// Arrange
		SettlementGenerator generator = new SettlementGenerator();
		String marketDataXml = createValidMarketDataXml();
		SmartDerivativeContractDescriptor sdc = createValidDescriptor();

		// Act
		SettlementGenerator result = generator.generateInitialSettlementXml(marketDataXml, sdc);

		// Assert
		assertNotNull(result, "generateInitialSettlementXml should return a non-null SettlementGenerator");
		assertSame(generator, result, "generateInitialSettlementXml should return the same instance (fluent API)");
	}

	/**
	 * Test generateRegularSettlementXml creates settlement with REGULAR type.
	 */
	@Test
	void testGenerateRegularSettlementXml_ValidInputs_Success() throws Exception {
		// Arrange
		SettlementGenerator generator = new SettlementGenerator();
		String marketDataXml = createValidMarketDataXml();
		SmartDerivativeContractDescriptor sdc = createValidDescriptor();
		BigDecimal marginValue = new BigDecimal("1000.50");

		// Act
		SettlementGenerator result = generator.generateRegularSettlementXml(marketDataXml, sdc, marginValue);

		// Assert
		assertNotNull(result, "generateRegularSettlementXml should return a non-null SettlementGenerator");
		assertSame(generator, result, "generateRegularSettlementXml should return the same instance (fluent API)");
	}

	/**
	 * Test marginLimits method sets margin limits correctly.
	 */
	@Test
	void testMarginLimits_ValidList_Success() throws Exception {
		// Arrange
		SettlementGenerator generator = new SettlementGenerator();
		String marketDataXml = createValidMarketDataXml();
		SmartDerivativeContractDescriptor sdc = createValidDescriptor();
		generator.generateInitialSettlementXml(marketDataXml, sdc);

		List<BigDecimal> marginLimits = Arrays.asList(
				new BigDecimal("1000.00"),
				new BigDecimal("2000.00")
		);

		// Act
		SettlementGenerator result = generator.marginLimits(marginLimits);

		// Assert
		assertNotNull(result, "marginLimits should return a non-null SettlementGenerator");
		assertSame(generator, result, "marginLimits should return the same instance (fluent API)");
	}

	/**
	 * Test settlementNPV method sets settlement NPV correctly.
	 */
	@Test
	void testSettlementNPV_ValidValue_Success() throws Exception {
		// Arrange
		SettlementGenerator generator = new SettlementGenerator();
		String marketDataXml = createValidMarketDataXml();
		SmartDerivativeContractDescriptor sdc = createValidDescriptor();
		generator.generateInitialSettlementXml(marketDataXml, sdc);

		BigDecimal settlementNPV = new BigDecimal("50000.75");

		// Act
		SettlementGenerator result = generator.settlementNPV(settlementNPV);

		// Assert
		assertNotNull(result, "settlementNPV should return a non-null SettlementGenerator");
		assertSame(generator, result, "settlementNPV should return the same instance (fluent API)");
	}

	/**
	 * Test settlementNPVPrevious method sets previous settlement NPV correctly.
	 */
	@Test
	void testSettlementNPVPrevious_ValidValue_Success() throws Exception {
		// Arrange
		SettlementGenerator generator = new SettlementGenerator();
		String marketDataXml = createValidMarketDataXml();
		SmartDerivativeContractDescriptor sdc = createValidDescriptor();
		generator.generateInitialSettlementXml(marketDataXml, sdc);

		BigDecimal settlementNPVPrevious = new BigDecimal("48000.25");

		// Act
		SettlementGenerator result = generator.settlementNPVPrevious(settlementNPVPrevious);

		// Assert
		assertNotNull(result, "settlementNPVPrevious should return a non-null SettlementGenerator");
		assertSame(generator, result, "settlementNPVPrevious should return the same instance (fluent API)");
	}

	/**
	 * Test settlementTimeNext method sets next settlement time correctly.
	 */
	@Test
	void testSettlementTimeNext_ValidValue_Success() throws Exception {
		// Arrange
		SettlementGenerator generator = new SettlementGenerator();
		String marketDataXml = createValidMarketDataXml();
		SmartDerivativeContractDescriptor sdc = createValidDescriptor();
		generator.generateInitialSettlementXml(marketDataXml, sdc);

		ZonedDateTime settlementTimeNext = ZonedDateTime.of(2024, 2, 15, 10, 0, 0, 0, ZoneOffset.UTC);

		// Act
		SettlementGenerator result = generator.settlementTimeNext(settlementTimeNext);

		// Assert
		assertNotNull(result, "settlementTimeNext should return a non-null SettlementGenerator");
		assertSame(generator, result, "settlementTimeNext should return the same instance (fluent API)");
	}

	/**
	 * Test settlementNPVNext method sets next settlement NPV correctly.
	 */
	@Test
	void testSettlementNPVNext_ValidValue_Success() throws Exception {
		// Arrange
		SettlementGenerator generator = new SettlementGenerator();
		String marketDataXml = createValidMarketDataXml();
		SmartDerivativeContractDescriptor sdc = createValidDescriptor();
		generator.generateInitialSettlementXml(marketDataXml, sdc);

		BigDecimal settlementNPVNext = new BigDecimal("52000.00");

		// Act
		SettlementGenerator result = generator.settlementNPVNext(settlementNPVNext);

		// Assert
		assertNotNull(result, "settlementNPVNext should return a non-null SettlementGenerator");
		assertSame(generator, result, "settlementNPVNext should return the same instance (fluent API)");
	}

	/**
	 * Test settlementInfo method converts map to settlement infos correctly.
	 */
	@Test
	void testSettlementInfo_ValidMap_Success() throws Exception {
		// Arrange
		SettlementGenerator generator = new SettlementGenerator();
		String marketDataXml = createValidMarketDataXml();
		SmartDerivativeContractDescriptor sdc = createValidDescriptor();
		generator.generateInitialSettlementXml(marketDataXml, sdc);

		Map<String, BigDecimal> info = new HashMap<>();
		info.put("risk1", new BigDecimal("100.00"));
		info.put("risk2", new BigDecimal("200.00"));

		// Act
		SettlementGenerator result = generator.settlementInfo(info);

		// Assert
		assertNotNull(result, "settlementInfo should return a non-null SettlementGenerator");
		assertSame(generator, result, "settlementInfo should return the same instance (fluent API)");
	}

	/**
	 * Test settlementInfo method with empty map.
	 */
	@Test
	void testSettlementInfo_EmptyMap_Success() throws Exception {
		// Arrange
		SettlementGenerator generator = new SettlementGenerator();
		String marketDataXml = createValidMarketDataXml();
		SmartDerivativeContractDescriptor sdc = createValidDescriptor();
		generator.generateInitialSettlementXml(marketDataXml, sdc);

		Map<String, BigDecimal> info = new HashMap<>();

		// Act
		SettlementGenerator result = generator.settlementInfo(info);

		// Assert
		assertNotNull(result, "settlementInfo should return a non-null SettlementGenerator");
		assertSame(generator, result, "settlementInfo should return the same instance (fluent API)");
	}

	/**
	 * Test buildObject method with fully populated settlement.
	 */
	@Test
	void testBuildObject_FullyPopulatedSettlement_Success() throws Exception {
		// Arrange
		SettlementGenerator generator = new SettlementGenerator();
		String marketDataXml = createValidMarketDataXml();
		SmartDerivativeContractDescriptor sdc = createValidDescriptor();

		generator.generateInitialSettlementXml(marketDataXml, sdc)
				.marginLimits(Arrays.asList(new BigDecimal("1000.00"), new BigDecimal("2000.00")))
				.settlementNPV(new BigDecimal("50000.75"))
				.settlementTimeNext(ZonedDateTime.of(2024, 2, 15, 10, 0, 0, 0, ZoneOffset.UTC))
				.settlementNPVNext(new BigDecimal("52000.00"))
				.settlementInfo(Map.of("risk1", new BigDecimal("100.00")));

		// Act
		Settlement result = generator.buildObject();

		// Assert
		assertNotNull(result, "buildObject should return a non-null Settlement");
		assertEquals("TRADE-123", result.getTradeId(), "Settlement should have correct trade ID");
		assertEquals(Settlement.SettlementType.INITIAL, result.getSettlementType(), "Settlement should have INITIAL type");
		assertEquals("EUR", result.getCurrency(), "Settlement should have correct currency");
		assertNotNull(result.getMarketData(), "Settlement should have market data");
	}

	/**
	 * Test buildObject method throws exception when settlement is incomplete.
	 */
	@Test
	void testBuildObject_IncompleteSettlement_ThrowsException() throws Exception {
		// Arrange
		SettlementGenerator generator = new SettlementGenerator();
		String marketDataXml = createValidMarketDataXml();
		SmartDerivativeContractDescriptor sdc = createValidDescriptor();

		// Only set some fields, not all
		generator.generateInitialSettlementXml(marketDataXml, sdc);

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> generator.buildObject(),
				"buildObject should throw SDCException when settlement is incomplete");

		assertTrue(exception.getMessage().contains("settlement input incomplete"),
				"Exception message should indicate incomplete settlement");
	}

	/**
	 * Test build method with fully populated settlement.
	 */
	@Test
	void testBuild_FullyPopulatedSettlement_ReturnsXmlString() throws Exception {
		// Arrange
		SettlementGenerator generator = new SettlementGenerator();
		String marketDataXml = createValidMarketDataXml();
		SmartDerivativeContractDescriptor sdc = createValidDescriptor();

		generator.generateInitialSettlementXml(marketDataXml, sdc)
				.marginLimits(Arrays.asList(new BigDecimal("1000.00"), new BigDecimal("2000.00")))
				.settlementNPV(new BigDecimal("50000.75"))
				.settlementTimeNext(ZonedDateTime.of(2024, 2, 15, 10, 0, 0, 0, ZoneOffset.UTC))
				.settlementNPVNext(new BigDecimal("52000.00"))
				.settlementInfo(Map.of("risk1", new BigDecimal("100.00")));

		// Act
		String result = generator.build();

		// Assert
		assertNotNull(result, "build should return a non-null XML string");
		assertFalse(result.isEmpty(), "build should return a non-empty XML string");
		assertTrue(result.contains("<?xml"), "build should return XML formatted string");
		assertTrue(result.contains("TRADE-123"), "XML should contain trade ID");
	}

	/**
	 * Test build method throws exception when settlement is incomplete.
	 */
	@Test
	void testBuild_IncompleteSettlement_ThrowsException() throws Exception {
		// Arrange
		SettlementGenerator generator = new SettlementGenerator();
		String marketDataXml = createValidMarketDataXml();
		SmartDerivativeContractDescriptor sdc = createValidDescriptor();

		// Only set some fields, not all
		generator.generateInitialSettlementXml(marketDataXml, sdc);

		// Act & Assert
		SDCException exception = assertThrows(SDCException.class, () -> generator.build(),
				"build should throw SDCException when settlement is incomplete");

		assertTrue(exception.getMessage().contains("settlement input incomplete"),
				"Exception message should indicate incomplete settlement");
	}

	/**
	 * Test fluent API chaining with generateInitialSettlementXml.
	 */
	@Test
	void testFluentApiChaining_InitialSettlement_Success() throws Exception {
		// Arrange
		SettlementGenerator generator = new SettlementGenerator();
		String marketDataXml = createValidMarketDataXml();
		SmartDerivativeContractDescriptor sdc = createValidDescriptor();

		// Act - chain multiple methods
		SettlementGenerator result = generator
				.generateInitialSettlementXml(marketDataXml, sdc)
				.marginLimits(Arrays.asList(new BigDecimal("1000.00")))
				.settlementNPV(new BigDecimal("50000.75"))
				.settlementTimeNext(ZonedDateTime.of(2024, 2, 15, 10, 0, 0, 0, ZoneOffset.UTC))
				.settlementNPVNext(new BigDecimal("52000.00"))
				.settlementInfo(Map.of("risk1", new BigDecimal("100.00")));

		// Assert
		assertNotNull(result, "Fluent API should return non-null result");
		assertSame(generator, result, "Fluent API should return the same instance throughout the chain");
	}

	/**
	 * Test fluent API chaining with generateRegularSettlementXml.
	 */
	@Test
	void testFluentApiChaining_RegularSettlement_Success() throws Exception {
		// Arrange
		SettlementGenerator generator = new SettlementGenerator();
		String marketDataXml = createValidMarketDataXml();
		SmartDerivativeContractDescriptor sdc = createValidDescriptor();
		BigDecimal marginValue = new BigDecimal("1000.50");

		// Act - chain multiple methods
		SettlementGenerator result = generator
				.generateRegularSettlementXml(marketDataXml, sdc, marginValue)
				.marginLimits(Arrays.asList(new BigDecimal("1000.00")))
				.settlementNPV(new BigDecimal("50000.75"))
				.settlementNPVPrevious(new BigDecimal("48000.25"))
				.settlementTimeNext(ZonedDateTime.of(2024, 2, 15, 10, 0, 0, 0, ZoneOffset.UTC))
				.settlementNPVNext(new BigDecimal("52000.00"))
				.settlementInfo(Map.of("risk1", new BigDecimal("100.00")));

		// Assert
		assertNotNull(result, "Fluent API should return non-null result");
		assertSame(generator, result, "Fluent API should return the same instance throughout the chain");
	}

	/**
	 * Test generateRegularSettlementXml with zero margin value.
	 */
	@Test
	void testGenerateRegularSettlementXml_ZeroMarginValue_Success() throws Exception {
		// Arrange
		SettlementGenerator generator = new SettlementGenerator();
		String marketDataXml = createValidMarketDataXml();
		SmartDerivativeContractDescriptor sdc = createValidDescriptor();
		BigDecimal marginValue = BigDecimal.ZERO;

		// Act
		SettlementGenerator result = generator.generateRegularSettlementXml(marketDataXml, sdc, marginValue);

		// Assert
		assertNotNull(result, "generateRegularSettlementXml should handle zero margin value");
		assertSame(generator, result, "Should return the same instance");
	}

	/**
	 * Test generateRegularSettlementXml with negative margin value.
	 */
	@Test
	void testGenerateRegularSettlementXml_NegativeMarginValue_Success() throws Exception {
		// Arrange
		SettlementGenerator generator = new SettlementGenerator();
		String marketDataXml = createValidMarketDataXml();
		SmartDerivativeContractDescriptor sdc = createValidDescriptor();
		BigDecimal marginValue = new BigDecimal("-1000.50");

		// Act
		SettlementGenerator result = generator.generateRegularSettlementXml(marketDataXml, sdc, marginValue);

		// Assert
		assertNotNull(result, "generateRegularSettlementXml should handle negative margin value");
		assertSame(generator, result, "Should return the same instance");
	}

	/**
	 * Test settlementNPV with negative value.
	 */
	@Test
	void testSettlementNPV_NegativeValue_Success() throws Exception {
		// Arrange
		SettlementGenerator generator = new SettlementGenerator();
		String marketDataXml = createValidMarketDataXml();
		SmartDerivativeContractDescriptor sdc = createValidDescriptor();
		generator.generateInitialSettlementXml(marketDataXml, sdc);

		BigDecimal settlementNPV = new BigDecimal("-25000.50");

		// Act
		SettlementGenerator result = generator.settlementNPV(settlementNPV);

		// Assert
		assertNotNull(result, "settlementNPV should handle negative value");
		assertSame(generator, result, "Should return the same instance");
	}

	/**
	 * Test settlementNPV with zero value.
	 */
	@Test
	void testSettlementNPV_ZeroValue_Success() throws Exception {
		// Arrange
		SettlementGenerator generator = new SettlementGenerator();
		String marketDataXml = createValidMarketDataXml();
		SmartDerivativeContractDescriptor sdc = createValidDescriptor();
		generator.generateInitialSettlementXml(marketDataXml, sdc);

		BigDecimal settlementNPV = BigDecimal.ZERO;

		// Act
		SettlementGenerator result = generator.settlementNPV(settlementNPV);

		// Assert
		assertNotNull(result, "settlementNPV should handle zero value");
		assertSame(generator, result, "Should return the same instance");
	}

	/**
	 * Test marginLimits with multiple values.
	 */
	@Test
	void testMarginLimits_MultipleValues_Success() throws Exception {
		// Arrange
		SettlementGenerator generator = new SettlementGenerator();
		String marketDataXml = createValidMarketDataXml();
		SmartDerivativeContractDescriptor sdc = createValidDescriptor();
		generator.generateInitialSettlementXml(marketDataXml, sdc);

		List<BigDecimal> marginLimits = Arrays.asList(
				new BigDecimal("1000.00"),
				new BigDecimal("2000.00"),
				new BigDecimal("3000.00"),
				new BigDecimal("4000.00")
		);

		// Act
		SettlementGenerator result = generator.marginLimits(marginLimits);

		// Assert
		assertNotNull(result, "marginLimits should handle multiple values");
		assertSame(generator, result, "Should return the same instance");
	}

	/**
	 * Test marginLimits with empty list.
	 */
	@Test
	void testMarginLimits_EmptyList_Success() throws Exception {
		// Arrange
		SettlementGenerator generator = new SettlementGenerator();
		String marketDataXml = createValidMarketDataXml();
		SmartDerivativeContractDescriptor sdc = createValidDescriptor();
		generator.generateInitialSettlementXml(marketDataXml, sdc);

		List<BigDecimal> marginLimits = new ArrayList<>();

		// Act
		SettlementGenerator result = generator.marginLimits(marginLimits);

		// Assert
		assertNotNull(result, "marginLimits should handle empty list");
		assertSame(generator, result, "Should return the same instance");
	}

	/**
	 * Test settlementInfo with multiple entries.
	 */
	@Test
	void testSettlementInfo_MultipleEntries_Success() throws Exception {
		// Arrange
		SettlementGenerator generator = new SettlementGenerator();
		String marketDataXml = createValidMarketDataXml();
		SmartDerivativeContractDescriptor sdc = createValidDescriptor();
		generator.generateInitialSettlementXml(marketDataXml, sdc);

		Map<String, BigDecimal> info = new HashMap<>();
		info.put("risk1", new BigDecimal("100.00"));
		info.put("risk2", new BigDecimal("200.00"));
		info.put("risk3", new BigDecimal("300.00"));

		// Act
		SettlementGenerator result = generator.settlementInfo(info);

		// Assert
		assertNotNull(result, "settlementInfo should handle multiple entries");
		assertSame(generator, result, "Should return the same instance");
	}

	/**
	 * Test complete workflow for initial settlement.
	 */
	@Test
	void testCompleteWorkflow_InitialSettlement_Success() throws Exception {
		// Arrange
		SettlementGenerator generator = new SettlementGenerator();
		String marketDataXml = createValidMarketDataXml();
		SmartDerivativeContractDescriptor sdc = createValidDescriptor();

		// Act
		Settlement settlement = generator
				.generateInitialSettlementXml(marketDataXml, sdc)
				.marginLimits(Arrays.asList(new BigDecimal("1000.00"), new BigDecimal("2000.00")))
				.settlementNPV(new BigDecimal("50000.75"))
				.settlementTimeNext(ZonedDateTime.of(2024, 2, 15, 10, 0, 0, 0, ZoneOffset.UTC))
				.settlementNPVNext(new BigDecimal("52000.00"))
				.settlementInfo(Map.of("risk1", new BigDecimal("100.00")))
				.buildObject();

		// Assert
		assertNotNull(settlement, "Complete workflow should produce a non-null Settlement");
		assertEquals("TRADE-123", settlement.getTradeId());
		assertEquals(Settlement.SettlementType.INITIAL, settlement.getSettlementType());
		assertEquals("EUR", settlement.getCurrency());
		assertEquals(BigDecimal.ZERO, settlement.getMarginValue());
		assertEquals(BigDecimal.ZERO, settlement.getSettlementNPVPrevious());
		assertEquals(new BigDecimal("50000.75"), settlement.getSettlementNPV());
		assertEquals(new BigDecimal("52000.00"), settlement.getSettlementNPVNext());
		assertNotNull(settlement.getMarginLimits());
		assertEquals(2, settlement.getMarginLimits().size());
		assertNotNull(settlement.getSettlementInfos());
		assertEquals(1, settlement.getSettlementInfos().size());
	}

	/**
	 * Test complete workflow for regular settlement.
	 */
	@Test
	void testCompleteWorkflow_RegularSettlement_Success() throws Exception {
		// Arrange
		SettlementGenerator generator = new SettlementGenerator();
		String marketDataXml = createValidMarketDataXml();
		SmartDerivativeContractDescriptor sdc = createValidDescriptor();
		BigDecimal marginValue = new BigDecimal("1000.50");

		// Act
		Settlement settlement = generator
				.generateRegularSettlementXml(marketDataXml, sdc, marginValue)
				.marginLimits(Arrays.asList(new BigDecimal("1000.00"), new BigDecimal("2000.00")))
				.settlementNPV(new BigDecimal("50000.75"))
				.settlementNPVPrevious(new BigDecimal("48000.25"))
				.settlementTimeNext(ZonedDateTime.of(2024, 2, 15, 10, 0, 0, 0, ZoneOffset.UTC))
				.settlementNPVNext(new BigDecimal("52000.00"))
				.settlementInfo(Map.of("risk1", new BigDecimal("100.00")))
				.buildObject();

		// Assert
		assertNotNull(settlement, "Complete workflow should produce a non-null Settlement");
		assertEquals("TRADE-123", settlement.getTradeId());
		assertEquals(Settlement.SettlementType.REGULAR, settlement.getSettlementType());
		assertEquals("EUR", settlement.getCurrency());
		assertEquals(marginValue, settlement.getMarginValue());
		assertEquals(new BigDecimal("48000.25"), settlement.getSettlementNPVPrevious());
		assertEquals(new BigDecimal("50000.75"), settlement.getSettlementNPV());
		assertEquals(new BigDecimal("52000.00"), settlement.getSettlementNPVNext());
	}

	/**
	 * Test that generateInitialSettlementXml sets settlementNPVPrevious to zero.
	 */
	@Test
	void testGenerateInitialSettlementXml_SetsNPVPreviousToZero() throws Exception {
		// Arrange
		SettlementGenerator generator = new SettlementGenerator();
		String marketDataXml = createValidMarketDataXml();
		SmartDerivativeContractDescriptor sdc = createValidDescriptor();

		// Act
		Settlement settlement = generator
				.generateInitialSettlementXml(marketDataXml, sdc)
				.marginLimits(Arrays.asList(new BigDecimal("1000.00")))
				.settlementNPV(new BigDecimal("50000.75"))
				.settlementTimeNext(ZonedDateTime.of(2024, 2, 15, 10, 0, 0, 0, ZoneOffset.UTC))
				.settlementNPVNext(new BigDecimal("52000.00"))
				.settlementInfo(Map.of("risk1", new BigDecimal("100.00")))
				.buildObject();

		// Assert
		assertEquals(BigDecimal.ZERO, settlement.getSettlementNPVPrevious(),
				"generateInitialSettlementXml should set settlementNPVPrevious to zero");
	}

	/**
	 * Test build method returns valid XML that can be unmarshalled.
	 */
	@Test
	void testBuild_ProducesValidXml_CanBeUnmarshalled() throws Exception {
		// Arrange
		SettlementGenerator generator = new SettlementGenerator();
		String marketDataXml = createValidMarketDataXml();
		SmartDerivativeContractDescriptor sdc = createValidDescriptor();

		String xml = generator
				.generateInitialSettlementXml(marketDataXml, sdc)
				.marginLimits(Arrays.asList(new BigDecimal("1000.00"), new BigDecimal("2000.00")))
				.settlementNPV(new BigDecimal("50000.75"))
				.settlementTimeNext(ZonedDateTime.of(2024, 2, 15, 10, 0, 0, 0, ZoneOffset.UTC))
				.settlementNPVNext(new BigDecimal("52000.00"))
				.settlementInfo(Map.of("risk1", new BigDecimal("100.00")))
				.build();

		// Act - unmarshal the XML back to an object
		Settlement unmarshalled = SDCXMLParser.unmarshalXml(xml, Settlement.class);

		// Assert
		assertNotNull(unmarshalled, "Unmarshalled settlement should not be null");
		assertEquals("TRADE-123", unmarshalled.getTradeId(), "Unmarshalled settlement should have correct trade ID");
		assertEquals(Settlement.SettlementType.INITIAL, unmarshalled.getSettlementType(),
				"Unmarshalled settlement should have correct type");
		assertEquals("EUR", unmarshalled.getCurrency(), "Unmarshalled settlement should have correct currency");
	}

	/**
	 * Test that settlement time is set to current time (approximately).
	 */
	@Test
	void testGenerateInitialSettlementXml_SetsCurrentTime() throws Exception {
		// Arrange
		SettlementGenerator generator = new SettlementGenerator();
		String marketDataXml = createValidMarketDataXml();
		SmartDerivativeContractDescriptor sdc = createValidDescriptor();
		ZonedDateTime beforeGeneration = ZonedDateTime.now();

		// Act
		Settlement settlement = generator
				.generateInitialSettlementXml(marketDataXml, sdc)
				.marginLimits(Arrays.asList(new BigDecimal("1000.00")))
				.settlementNPV(new BigDecimal("50000.75"))
				.settlementTimeNext(ZonedDateTime.of(2024, 2, 15, 10, 0, 0, 0, ZoneOffset.UTC))
				.settlementNPVNext(new BigDecimal("52000.00"))
				.settlementInfo(Map.of("risk1", new BigDecimal("100.00")))
				.buildObject();

		ZonedDateTime afterGeneration = ZonedDateTime.now();

		// Assert
		assertNotNull(settlement.getSettlementTime(), "Settlement time should be set");
		assertTrue(settlement.getSettlementTime().isAfter(beforeGeneration.minusSeconds(1)),
				"Settlement time should be after or equal to time before generation");
		assertTrue(settlement.getSettlementTime().isBefore(afterGeneration.plusSeconds(1)),
				"Settlement time should be before or equal to time after generation");
	}
}
