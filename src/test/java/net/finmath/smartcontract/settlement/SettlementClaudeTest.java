/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */

package net.finmath.smartcontract.settlement;

import net.finmath.smartcontract.model.MarketDataList;
import net.finmath.smartcontract.settlement.Settlement.SettlementType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Settlement.
 * Tests all methods with focus on branch and condition coverage.
 *
 * @author Claude Code
 */
class SettlementClaudeTest {

	/**
	 * Test the default constructor creates a non-null instance.
	 */
	@Test
	void testConstructor() {
		Settlement settlement = new Settlement();
		assertNotNull(settlement, "Constructor should create a non-null Settlement instance");
	}

	/**
	 * Test getTradeId and setTradeId methods.
	 */
	@Test
	void testGetSetTradeId() {
		Settlement settlement = new Settlement();

		// Test with null value
		assertNull(settlement.getTradeId(), "Initial tradeId should be null");

		// Test setting a value
		String tradeId = "TRADE-12345";
		settlement.setTradeId(tradeId);
		assertEquals(tradeId, settlement.getTradeId(), "getTradeId should return the set value");

		// Test setting a different value
		String newTradeId = "TRADE-67890";
		settlement.setTradeId(newTradeId);
		assertEquals(newTradeId, settlement.getTradeId(), "getTradeId should return the updated value");

		// Test setting null
		settlement.setTradeId(null);
		assertNull(settlement.getTradeId(), "getTradeId should return null after setting null");
	}

	/**
	 * Test getSettlementType and setSettlementType methods for all enum values.
	 */
	@Test
	void testGetSetSettlementType() {
		Settlement settlement = new Settlement();

		// Test with null value
		assertNull(settlement.getSettlementType(), "Initial settlementType should be null");

		// Test with INITIAL
		settlement.setSettlementType(SettlementType.INITIAL);
		assertEquals(SettlementType.INITIAL, settlement.getSettlementType(),
				"getSettlementType should return INITIAL");

		// Test with REGULAR
		settlement.setSettlementType(SettlementType.REGULAR);
		assertEquals(SettlementType.REGULAR, settlement.getSettlementType(),
				"getSettlementType should return REGULAR");

		// Test with TERMINAL
		settlement.setSettlementType(SettlementType.TERMINAL);
		assertEquals(SettlementType.TERMINAL, settlement.getSettlementType(),
				"getSettlementType should return TERMINAL");

		// Test setting null
		settlement.setSettlementType(null);
		assertNull(settlement.getSettlementType(), "getSettlementType should return null after setting null");
	}

	/**
	 * Test getCurrency and setCurrency methods.
	 */
	@Test
	void testGetSetCurrency() {
		Settlement settlement = new Settlement();

		// Test with null value
		assertNull(settlement.getCurrency(), "Initial currency should be null");

		// Test setting a value
		String currency = "USD";
		settlement.setCurrency(currency);
		assertEquals(currency, settlement.getCurrency(), "getCurrency should return the set value");

		// Test setting a different value
		String newCurrency = "EUR";
		settlement.setCurrency(newCurrency);
		assertEquals(newCurrency, settlement.getCurrency(), "getCurrency should return the updated value");

		// Test setting null
		settlement.setCurrency(null);
		assertNull(settlement.getCurrency(), "getCurrency should return null after setting null");
	}

	/**
	 * Test getMarginValue and setMarginValue methods.
	 */
	@Test
	void testGetSetMarginValue() {
		Settlement settlement = new Settlement();

		// Test with null value
		assertNull(settlement.getMarginValue(), "Initial marginValue should be null");

		// Test setting a positive value
		BigDecimal marginValue = new BigDecimal("1000.50");
		settlement.setMarginValue(marginValue);
		assertEquals(marginValue, settlement.getMarginValue(), "getMarginValue should return the set value");

		// Test setting a negative value
		BigDecimal negativeValue = new BigDecimal("-500.25");
		settlement.setMarginValue(negativeValue);
		assertEquals(negativeValue, settlement.getMarginValue(),
				"getMarginValue should return the negative value");

		// Test setting zero
		BigDecimal zeroValue = BigDecimal.ZERO;
		settlement.setMarginValue(zeroValue);
		assertEquals(zeroValue, settlement.getMarginValue(), "getMarginValue should return zero");

		// Test setting null
		settlement.setMarginValue(null);
		assertNull(settlement.getMarginValue(), "getMarginValue should return null after setting null");
	}

	/**
	 * Test getMarginLimits and setMarginLimits methods.
	 */
	@Test
	void testGetSetMarginLimits() {
		Settlement settlement = new Settlement();

		// Test with null value
		assertNull(settlement.getMarginLimits(), "Initial marginLimits should be null");

		// Test setting an empty list
		List<BigDecimal> emptyList = new ArrayList<>();
		settlement.setMarginLimits(emptyList);
		assertNotNull(settlement.getMarginLimits(), "getMarginLimits should not be null after setting empty list");
		assertTrue(settlement.getMarginLimits().isEmpty(), "getMarginLimits should return empty list");

		// Test setting a list with values
		List<BigDecimal> marginLimits = Arrays.asList(
				new BigDecimal("1000.00"),
				new BigDecimal("2000.00"),
				new BigDecimal("3000.00")
		);
		settlement.setMarginLimits(marginLimits);
		assertEquals(marginLimits, settlement.getMarginLimits(), "getMarginLimits should return the set list");
		assertEquals(3, settlement.getMarginLimits().size(), "getMarginLimits should return list with 3 elements");

		// Test setting null
		settlement.setMarginLimits(null);
		assertNull(settlement.getMarginLimits(), "getMarginLimits should return null after setting null");
	}

	/**
	 * Test getSettlementTime and setSettlementTime methods.
	 */
	@Test
	void testGetSetSettlementTime() {
		Settlement settlement = new Settlement();

		// Test with null value
		assertNull(settlement.getSettlementTime(), "Initial settlementTime should be null");

		// Test setting a value
		ZonedDateTime settlementTime = ZonedDateTime.of(2024, 1, 15, 10, 0, 0, 0, ZoneOffset.UTC);
		settlement.setSettlementTime(settlementTime);
		assertEquals(settlementTime, settlement.getSettlementTime(),
				"getSettlementTime should return the set value");

		// Test setting a different value
		ZonedDateTime newSettlementTime = ZonedDateTime.of(2024, 2, 20, 14, 30, 0, 0, ZoneOffset.UTC);
		settlement.setSettlementTime(newSettlementTime);
		assertEquals(newSettlementTime, settlement.getSettlementTime(),
				"getSettlementTime should return the updated value");

		// Test setting null
		settlement.setSettlementTime(null);
		assertNull(settlement.getSettlementTime(), "getSettlementTime should return null after setting null");
	}

	/**
	 * Test getSettlementNPV and setSettlementNPV methods.
	 */
	@Test
	void testGetSetSettlementNPV() {
		Settlement settlement = new Settlement();

		// Test with null value
		assertNull(settlement.getSettlementNPV(), "Initial settlementNPV should be null");

		// Test setting a positive value
		BigDecimal settlementNPV = new BigDecimal("50000.75");
		settlement.setSettlementNPV(settlementNPV);
		assertEquals(settlementNPV, settlement.getSettlementNPV(),
				"getSettlementNPV should return the set value");

		// Test setting a negative value
		BigDecimal negativeNPV = new BigDecimal("-25000.50");
		settlement.setSettlementNPV(negativeNPV);
		assertEquals(negativeNPV, settlement.getSettlementNPV(),
				"getSettlementNPV should return the negative value");

		// Test setting null
		settlement.setSettlementNPV(null);
		assertNull(settlement.getSettlementNPV(), "getSettlementNPV should return null after setting null");
	}

	/**
	 * Test getSettlementNPVPrevious and setSettlementNPVPrevious methods.
	 */
	@Test
	void testGetSetSettlementNPVPrevious() {
		Settlement settlement = new Settlement();

		// Test with null value
		assertNull(settlement.getSettlementNPVPrevious(), "Initial settlementNPVPrevious should be null");

		// Test setting a positive value
		BigDecimal settlementNPVPrevious = new BigDecimal("48000.25");
		settlement.setSettlementNPVPrevious(settlementNPVPrevious);
		assertEquals(settlementNPVPrevious, settlement.getSettlementNPVPrevious(),
				"getSettlementNPVPrevious should return the set value");

		// Test setting a negative value
		BigDecimal negativeNPV = new BigDecimal("-30000.75");
		settlement.setSettlementNPVPrevious(negativeNPV);
		assertEquals(negativeNPV, settlement.getSettlementNPVPrevious(),
				"getSettlementNPVPrevious should return the negative value");

		// Test setting null
		settlement.setSettlementNPVPrevious(null);
		assertNull(settlement.getSettlementNPVPrevious(),
				"getSettlementNPVPrevious should return null after setting null");
	}

	/**
	 * Test getSettlementTimeNext and setSettlementTimeNext methods.
	 */
	@Test
	void testGetSetSettlementTimeNext() {
		Settlement settlement = new Settlement();

		// Test with null value
		assertNull(settlement.getSettlementTimeNext(), "Initial settlementTimeNext should be null");

		// Test setting a value
		ZonedDateTime settlementTimeNext = ZonedDateTime.of(2024, 2, 15, 10, 0, 0, 0, ZoneOffset.UTC);
		settlement.setSettlementTimeNext(settlementTimeNext);
		assertEquals(settlementTimeNext, settlement.getSettlementTimeNext(),
				"getSettlementTimeNext should return the set value");

		// Test setting a different value
		ZonedDateTime newSettlementTimeNext = ZonedDateTime.of(2024, 3, 20, 14, 30, 0, 0, ZoneOffset.UTC);
		settlement.setSettlementTimeNext(newSettlementTimeNext);
		assertEquals(newSettlementTimeNext, settlement.getSettlementTimeNext(),
				"getSettlementTimeNext should return the updated value");

		// Test setting null
		settlement.setSettlementTimeNext(null);
		assertNull(settlement.getSettlementTimeNext(),
				"getSettlementTimeNext should return null after setting null");
	}

	/**
	 * Test getSettlementNPVNext and setSettlementNPVNext methods.
	 */
	@Test
	void testGetSetSettlementNPVNext() {
		Settlement settlement = new Settlement();

		// Test with null value
		assertNull(settlement.getSettlementNPVNext(), "Initial settlementNPVNext should be null");

		// Test setting a positive value
		BigDecimal settlementNPVNext = new BigDecimal("52000.00");
		settlement.setSettlementNPVNext(settlementNPVNext);
		assertEquals(settlementNPVNext, settlement.getSettlementNPVNext(),
				"getSettlementNPVNext should return the set value");

		// Test setting a negative value
		BigDecimal negativeNPV = new BigDecimal("-28000.25");
		settlement.setSettlementNPVNext(negativeNPV);
		assertEquals(negativeNPV, settlement.getSettlementNPVNext(),
				"getSettlementNPVNext should return the negative value");

		// Test setting null
		settlement.setSettlementNPVNext(null);
		assertNull(settlement.getSettlementNPVNext(),
				"getSettlementNPVNext should return null after setting null");
	}

	/**
	 * Test getMarketData and setMarketData methods.
	 */
	@Test
	void testGetSetMarketData() {
		Settlement settlement = new Settlement();

		// Test with null value
		assertNull(settlement.getMarketData(), "Initial marketData should be null");

		// Test setting a value
		MarketDataList marketData = new MarketDataList();
		settlement.setMarketData(marketData);
		assertNotNull(settlement.getMarketData(), "getMarketData should not be null after setting");
		assertEquals(marketData, settlement.getMarketData(), "getMarketData should return the set value");

		// Test setting a different value
		MarketDataList newMarketData = new MarketDataList();
		settlement.setMarketData(newMarketData);
		assertEquals(newMarketData, settlement.getMarketData(),
				"getMarketData should return the updated value");

		// Test setting null
		settlement.setMarketData(null);
		assertNull(settlement.getMarketData(), "getMarketData should return null after setting null");
	}

	/**
	 * Test getSettlementInfos and setSettlementInfos methods.
	 */
	@Test
	void testGetSetSettlementInfos() {
		Settlement settlement = new Settlement();

		// Test with null value
		assertNull(settlement.getSettlementInfos(), "Initial settlementInfos should be null");

		// Test setting an empty list
		List<SettlementInfo> emptyList = new ArrayList<>();
		settlement.setSettlementInfos(emptyList);
		assertNotNull(settlement.getSettlementInfos(),
				"getSettlementInfos should not be null after setting empty list");
		assertTrue(settlement.getSettlementInfos().isEmpty(), "getSettlementInfos should return empty list");

		// Test setting a list with values
		List<SettlementInfo> settlementInfos = Arrays.asList(
				new SettlementInfo("risk1", new BigDecimal("100.00")),
				new SettlementInfo("risk2", new BigDecimal("200.00"))
		);
		settlement.setSettlementInfos(settlementInfos);
		assertEquals(settlementInfos, settlement.getSettlementInfos(),
				"getSettlementInfos should return the set list");
		assertEquals(2, settlement.getSettlementInfos().size(),
				"getSettlementInfos should return list with 2 elements");

		// Test setting null
		settlement.setSettlementInfos(null);
		assertNull(settlement.getSettlementInfos(), "getSettlementInfos should return null after setting null");
	}

	/**
	 * Test creating a fully populated Settlement object with all fields set.
	 * This ensures all getters and setters work together correctly.
	 */
	@Test
	void testFullyPopulatedSettlement() {
		Settlement settlement = new Settlement();

		// Set all fields
		String tradeId = "TRADE-12345";
		SettlementType settlementType = SettlementType.REGULAR;
		String currency = "USD";
		BigDecimal marginValue = new BigDecimal("1000.50");
		List<BigDecimal> marginLimits = Arrays.asList(
				new BigDecimal("1000.00"),
				new BigDecimal("2000.00")
		);
		ZonedDateTime settlementTime = ZonedDateTime.of(2024, 1, 15, 10, 0, 0, 0, ZoneOffset.UTC);
		BigDecimal settlementNPV = new BigDecimal("50000.75");
		BigDecimal settlementNPVPrevious = new BigDecimal("48000.25");
		ZonedDateTime settlementTimeNext = ZonedDateTime.of(2024, 2, 15, 10, 0, 0, 0, ZoneOffset.UTC);
		BigDecimal settlementNPVNext = new BigDecimal("52000.00");
		MarketDataList marketData = new MarketDataList();
		List<SettlementInfo> settlementInfos = Arrays.asList(
				new SettlementInfo("risk1", new BigDecimal("100.00"))
		);

		settlement.setTradeId(tradeId);
		settlement.setSettlementType(settlementType);
		settlement.setCurrency(currency);
		settlement.setMarginValue(marginValue);
		settlement.setMarginLimits(marginLimits);
		settlement.setSettlementTime(settlementTime);
		settlement.setSettlementNPV(settlementNPV);
		settlement.setSettlementNPVPrevious(settlementNPVPrevious);
		settlement.setSettlementTimeNext(settlementTimeNext);
		settlement.setSettlementNPVNext(settlementNPVNext);
		settlement.setMarketData(marketData);
		settlement.setSettlementInfos(settlementInfos);

		// Verify all fields
		assertEquals(tradeId, settlement.getTradeId());
		assertEquals(settlementType, settlement.getSettlementType());
		assertEquals(currency, settlement.getCurrency());
		assertEquals(marginValue, settlement.getMarginValue());
		assertEquals(marginLimits, settlement.getMarginLimits());
		assertEquals(settlementTime, settlement.getSettlementTime());
		assertEquals(settlementNPV, settlement.getSettlementNPV());
		assertEquals(settlementNPVPrevious, settlement.getSettlementNPVPrevious());
		assertEquals(settlementTimeNext, settlement.getSettlementTimeNext());
		assertEquals(settlementNPVNext, settlement.getSettlementNPVNext());
		assertEquals(marketData, settlement.getMarketData());
		assertEquals(settlementInfos, settlement.getSettlementInfos());
	}

	/**
	 * Test that SettlementType enum has all expected values.
	 */
	@Test
	void testSettlementTypeEnumValues() {
		SettlementType[] values = SettlementType.values();
		assertEquals(3, values.length, "SettlementType should have 3 enum values");

		// Verify each enum value exists
		SettlementType initial = SettlementType.valueOf("INITIAL");
		assertNotNull(initial, "INITIAL enum value should exist");
		assertEquals(SettlementType.INITIAL, initial);

		SettlementType regular = SettlementType.valueOf("REGULAR");
		assertNotNull(regular, "REGULAR enum value should exist");
		assertEquals(SettlementType.REGULAR, regular);

		SettlementType terminal = SettlementType.valueOf("TERMINAL");
		assertNotNull(terminal, "TERMINAL enum value should exist");
		assertEquals(SettlementType.TERMINAL, terminal);
	}

	/**
	 * Test that SettlementType.valueOf() throws IllegalArgumentException for invalid values.
	 */
	@Test
	void testSettlementTypeValueOfInvalid() {
		// Test with invalid enum value
		assertThrows(IllegalArgumentException.class, () -> SettlementType.valueOf("INVALID"),
				"valueOf should throw IllegalArgumentException for invalid enum value");

		// Test with null (should throw NullPointerException according to enum contract)
		assertThrows(NullPointerException.class, () -> SettlementType.valueOf(null),
				"valueOf should throw NullPointerException for null value");

		// Test with lowercase (enum names are case-sensitive)
		assertThrows(IllegalArgumentException.class, () -> SettlementType.valueOf("initial"),
				"valueOf should throw IllegalArgumentException for lowercase enum name");
	}

	/**
	 * Test that values() returns a new array each time (defensive copy).
	 */
	@Test
	void testSettlementTypeValuesReturnsNewArray() {
		SettlementType[] values1 = SettlementType.values();
		SettlementType[] values2 = SettlementType.values();

		// Arrays should be equal in content
		assertArrayEquals(values1, values2, "values() should return arrays with same content");

		// But not the same instance (defensive copy)
		assertNotSame(values1, values2, "values() should return a new array each time");

		// Modifying one array should not affect the other
		values1[0] = null;
		assertNotNull(values2[0], "Modifying returned array should not affect subsequent calls");
	}

	/**
	 * Test enum ordinal values and order.
	 */
	@Test
	void testSettlementTypeOrdinals() {
		assertEquals(0, SettlementType.INITIAL.ordinal(), "INITIAL should have ordinal 0");
		assertEquals(1, SettlementType.REGULAR.ordinal(), "REGULAR should have ordinal 1");
		assertEquals(2, SettlementType.TERMINAL.ordinal(), "TERMINAL should have ordinal 2");

		// Verify order in values() array matches ordinals
		SettlementType[] values = SettlementType.values();
		assertEquals(SettlementType.INITIAL, values[0], "values()[0] should be INITIAL");
		assertEquals(SettlementType.REGULAR, values[1], "values()[1] should be REGULAR");
		assertEquals(SettlementType.TERMINAL, values[2], "values()[2] should be TERMINAL");
	}

	/**
	 * Test enum name() method.
	 */
	@Test
	void testSettlementTypeNames() {
		assertEquals("INITIAL", SettlementType.INITIAL.name(), "INITIAL.name() should return 'INITIAL'");
		assertEquals("REGULAR", SettlementType.REGULAR.name(), "REGULAR.name() should return 'REGULAR'");
		assertEquals("TERMINAL", SettlementType.TERMINAL.name(), "TERMINAL.name() should return 'TERMINAL'");
	}
}
