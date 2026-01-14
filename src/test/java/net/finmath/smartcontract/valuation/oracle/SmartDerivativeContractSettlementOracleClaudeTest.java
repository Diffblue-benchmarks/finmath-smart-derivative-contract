/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */

package net.finmath.smartcontract.valuation.oracle;

import org.junit.jupiter.api.Test;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for SmartDerivativeContractSettlementOracle.
 * Tests all methods with focus on branch and condition coverage.
 *
 * @author Claude Code
 */
class SmartDerivativeContractSettlementOracleClaudeTest {

	/**
	 * Simple test implementation of ValuationOracle for testing purposes.
	 * This allows testing without mocking, using predictable values.
	 */
	private static class TestValuationOracle implements ValuationOracle {
		private final Map<String, BigDecimal> currentValues;
		private final Map<String, BigDecimal> previousValues;

		public TestValuationOracle(Map<String, BigDecimal> currentValues, Map<String, BigDecimal> previousValues) {
			this.currentValues = currentValues;
			this.previousValues = previousValues;
		}

		@Override
		public BigDecimal getValue(LocalDateTime evaluationTime, LocalDateTime marketDataTime) {
			// For simplicity, return the sum of all values
			return getValues(evaluationTime, marketDataTime).values().stream()
					.reduce(BigDecimal.ZERO, BigDecimal::add);
		}

		@Override
		public Map<String, BigDecimal> getValues(LocalDateTime evaluationTime, LocalDateTime marketDataTime) {
			// If evaluation time equals market data time, return current values (margin period end scenario)
			// Otherwise, return previous values (margin period start scenario)
			if (evaluationTime.equals(marketDataTime)) {
				return currentValues;
			} else {
				return previousValues;
			}
		}

		@Override
		public MonetaryAmount getAmount(LocalDateTime evaluationTime, LocalDateTime marketDataTime) {
			// Not used in SmartDerivativeContractSettlementOracle, so return null
			return null;
		}
	}

	/**
	 * Test successful construction with valid ValuationOracle.
	 */
	@Test
	void testConstructor_ValidValuationOracle_Success() {
		// Arrange
		Map<String, BigDecimal> currentValues = Map.of("party1", BigDecimal.valueOf(1000));
		Map<String, BigDecimal> previousValues = Map.of("party1", BigDecimal.valueOf(800));
		ValuationOracle oracle = new TestValuationOracle(currentValues, previousValues);

		// Act
		SmartDerivativeContractSettlementOracle settlementOracle =
			new SmartDerivativeContractSettlementOracle(oracle);

		// Assert
		assertNotNull(settlementOracle, "SmartDerivativeContractSettlementOracle should be created successfully");
	}

	/**
	 * Test construction with null ValuationOracle.
	 * This tests the behavior when a null oracle is provided.
	 */
	@Test
	void testConstructor_NullValuationOracle_Success() {
		// Act
		SmartDerivativeContractSettlementOracle settlementOracle =
			new SmartDerivativeContractSettlementOracle(null);

		// Assert
		assertNotNull(settlementOracle, "SmartDerivativeContractSettlementOracle should be created even with null oracle");
	}

	/**
	 * Test getMargin with positive margin (value increase).
	 * Tests the case where the derivative value increased from previous to current period.
	 */
	@Test
	void testGetMargin_PositiveMargin_ReturnsCorrectMargin() {
		// Arrange
		Map<String, BigDecimal> currentValues = Map.of(
			"party1", BigDecimal.valueOf(1000),
			"party2", BigDecimal.valueOf(2000)
		);
		Map<String, BigDecimal> previousValues = Map.of(
			"party1", BigDecimal.valueOf(800),
			"party2", BigDecimal.valueOf(1500)
		);
		ValuationOracle oracle = new TestValuationOracle(currentValues, previousValues);
		SmartDerivativeContractSettlementOracle settlementOracle =
			new SmartDerivativeContractSettlementOracle(oracle);

		LocalDateTime periodStart = LocalDateTime.of(2024, 1, 1, 0, 0);
		LocalDateTime periodEnd = LocalDateTime.of(2024, 1, 31, 23, 59);

		// Act
		Map<String, BigDecimal> margin = settlementOracle.getMargin(periodStart, periodEnd);

		// Assert
		assertNotNull(margin, "Margin should not be null");
		assertEquals(2, margin.size(), "Margin should have 2 entries");
		assertEquals(BigDecimal.valueOf(200), margin.get("party1"), "party1 margin should be 200 (1000 - 800)");
		assertEquals(BigDecimal.valueOf(500), margin.get("party2"), "party2 margin should be 500 (2000 - 1500)");
	}

	/**
	 * Test getMargin with negative margin (value decrease).
	 * Tests the case where the derivative value decreased from previous to current period.
	 */
	@Test
	void testGetMargin_NegativeMargin_ReturnsCorrectMargin() {
		// Arrange
		Map<String, BigDecimal> currentValues = Map.of(
			"party1", BigDecimal.valueOf(500),
			"party2", BigDecimal.valueOf(1000)
		);
		Map<String, BigDecimal> previousValues = Map.of(
			"party1", BigDecimal.valueOf(800),
			"party2", BigDecimal.valueOf(1500)
		);
		ValuationOracle oracle = new TestValuationOracle(currentValues, previousValues);
		SmartDerivativeContractSettlementOracle settlementOracle =
			new SmartDerivativeContractSettlementOracle(oracle);

		LocalDateTime periodStart = LocalDateTime.of(2024, 1, 1, 0, 0);
		LocalDateTime periodEnd = LocalDateTime.of(2024, 1, 31, 23, 59);

		// Act
		Map<String, BigDecimal> margin = settlementOracle.getMargin(periodStart, periodEnd);

		// Assert
		assertNotNull(margin, "Margin should not be null");
		assertEquals(2, margin.size(), "Margin should have 2 entries");
		assertEquals(BigDecimal.valueOf(-300), margin.get("party1"), "party1 margin should be -300 (500 - 800)");
		assertEquals(BigDecimal.valueOf(-500), margin.get("party2"), "party2 margin should be -500 (1000 - 1500)");
	}

	/**
	 * Test getMargin with zero margin (no value change).
	 * Tests the case where the derivative value remained the same.
	 */
	@Test
	void testGetMargin_ZeroMargin_ReturnsZeroMargin() {
		// Arrange
		Map<String, BigDecimal> currentValues = Map.of(
			"party1", BigDecimal.valueOf(1000),
			"party2", BigDecimal.valueOf(2000)
		);
		Map<String, BigDecimal> previousValues = Map.of(
			"party1", BigDecimal.valueOf(1000),
			"party2", BigDecimal.valueOf(2000)
		);
		ValuationOracle oracle = new TestValuationOracle(currentValues, previousValues);
		SmartDerivativeContractSettlementOracle settlementOracle =
			new SmartDerivativeContractSettlementOracle(oracle);

		LocalDateTime periodStart = LocalDateTime.of(2024, 1, 1, 0, 0);
		LocalDateTime periodEnd = LocalDateTime.of(2024, 1, 31, 23, 59);

		// Act
		Map<String, BigDecimal> margin = settlementOracle.getMargin(periodStart, periodEnd);

		// Assert
		assertNotNull(margin, "Margin should not be null");
		assertEquals(2, margin.size(), "Margin should have 2 entries");
		assertEquals(BigDecimal.valueOf(0), margin.get("party1"), "party1 margin should be 0 (1000 - 1000)");
		assertEquals(BigDecimal.valueOf(0), margin.get("party2"), "party2 margin should be 0 (2000 - 2000)");
	}

	/**
	 * Test getMargin with single party.
	 * Tests the case where only one party is involved.
	 */
	@Test
	void testGetMargin_SingleParty_ReturnsCorrectMargin() {
		// Arrange
		Map<String, BigDecimal> currentValues = Map.of("party1", BigDecimal.valueOf(1500));
		Map<String, BigDecimal> previousValues = Map.of("party1", BigDecimal.valueOf(1200));
		ValuationOracle oracle = new TestValuationOracle(currentValues, previousValues);
		SmartDerivativeContractSettlementOracle settlementOracle =
			new SmartDerivativeContractSettlementOracle(oracle);

		LocalDateTime periodStart = LocalDateTime.of(2024, 1, 1, 0, 0);
		LocalDateTime periodEnd = LocalDateTime.of(2024, 1, 31, 23, 59);

		// Act
		Map<String, BigDecimal> margin = settlementOracle.getMargin(periodStart, periodEnd);

		// Assert
		assertNotNull(margin, "Margin should not be null");
		assertEquals(1, margin.size(), "Margin should have 1 entry");
		assertEquals(BigDecimal.valueOf(300), margin.get("party1"), "party1 margin should be 300 (1500 - 1200)");
	}

	/**
	 * Test getMargin with multiple parties (more than 2).
	 * Tests the case where multiple parties are involved.
	 */
	@Test
	void testGetMargin_MultipleParties_ReturnsCorrectMargin() {
		// Arrange
		Map<String, BigDecimal> currentValues = Map.of(
			"party1", BigDecimal.valueOf(1000),
			"party2", BigDecimal.valueOf(2000),
			"party3", BigDecimal.valueOf(1500)
		);
		Map<String, BigDecimal> previousValues = Map.of(
			"party1", BigDecimal.valueOf(800),
			"party2", BigDecimal.valueOf(1800),
			"party3", BigDecimal.valueOf(1600)
		);
		ValuationOracle oracle = new TestValuationOracle(currentValues, previousValues);
		SmartDerivativeContractSettlementOracle settlementOracle =
			new SmartDerivativeContractSettlementOracle(oracle);

		LocalDateTime periodStart = LocalDateTime.of(2024, 1, 1, 0, 0);
		LocalDateTime periodEnd = LocalDateTime.of(2024, 1, 31, 23, 59);

		// Act
		Map<String, BigDecimal> margin = settlementOracle.getMargin(periodStart, periodEnd);

		// Assert
		assertNotNull(margin, "Margin should not be null");
		assertEquals(3, margin.size(), "Margin should have 3 entries");
		assertEquals(BigDecimal.valueOf(200), margin.get("party1"), "party1 margin should be 200");
		assertEquals(BigDecimal.valueOf(200), margin.get("party2"), "party2 margin should be 200");
		assertEquals(BigDecimal.valueOf(-100), margin.get("party3"), "party3 margin should be -100");
	}

	/**
	 * Test getMargin with empty values map.
	 * Tests the case where the oracle returns empty maps.
	 */
	@Test
	void testGetMargin_EmptyValues_ReturnsEmptyMargin() {
		// Arrange
		Map<String, BigDecimal> currentValues = Map.of();
		Map<String, BigDecimal> previousValues = Map.of();
		ValuationOracle oracle = new TestValuationOracle(currentValues, previousValues);
		SmartDerivativeContractSettlementOracle settlementOracle =
			new SmartDerivativeContractSettlementOracle(oracle);

		LocalDateTime periodStart = LocalDateTime.of(2024, 1, 1, 0, 0);
		LocalDateTime periodEnd = LocalDateTime.of(2024, 1, 31, 23, 59);

		// Act
		Map<String, BigDecimal> margin = settlementOracle.getMargin(periodStart, periodEnd);

		// Assert
		assertNotNull(margin, "Margin should not be null");
		assertEquals(0, margin.size(), "Margin should be empty");
	}

	/**
	 * Test getMargin with same start and end time.
	 * Tests boundary condition where the period has no duration.
	 */
	@Test
	void testGetMargin_SamePeriodStartAndEnd_ReturnsMargin() {
		// Arrange
		Map<String, BigDecimal> currentValues = Map.of("party1", BigDecimal.valueOf(1000));
		Map<String, BigDecimal> previousValues = Map.of("party1", BigDecimal.valueOf(800));
		ValuationOracle oracle = new TestValuationOracle(currentValues, previousValues);
		SmartDerivativeContractSettlementOracle settlementOracle =
			new SmartDerivativeContractSettlementOracle(oracle);

		LocalDateTime sameTime = LocalDateTime.of(2024, 1, 15, 12, 0);

		// Act
		Map<String, BigDecimal> margin = settlementOracle.getMargin(sameTime, sameTime);

		// Assert - both calls will use same time, so both return currentValues
		assertNotNull(margin, "Margin should not be null");
		assertEquals(1, margin.size(), "Margin should have 1 entry");
		assertEquals(BigDecimal.valueOf(0), margin.get("party1"), "party1 margin should be 0 (same time means same values)");
	}

	/**
	 * Test getMargin with decimal values.
	 * Tests that decimal precision is maintained correctly.
	 */
	@Test
	void testGetMargin_DecimalValues_ReturnsCorrectPrecision() {
		// Arrange
		Map<String, BigDecimal> currentValues = Map.of(
			"party1", new BigDecimal("1000.50"),
			"party2", new BigDecimal("2000.75")
		);
		Map<String, BigDecimal> previousValues = Map.of(
			"party1", new BigDecimal("800.25"),
			"party2", new BigDecimal("1500.50")
		);
		ValuationOracle oracle = new TestValuationOracle(currentValues, previousValues);
		SmartDerivativeContractSettlementOracle settlementOracle =
			new SmartDerivativeContractSettlementOracle(oracle);

		LocalDateTime periodStart = LocalDateTime.of(2024, 1, 1, 0, 0);
		LocalDateTime periodEnd = LocalDateTime.of(2024, 1, 31, 23, 59);

		// Act
		Map<String, BigDecimal> margin = settlementOracle.getMargin(periodStart, periodEnd);

		// Assert
		assertNotNull(margin, "Margin should not be null");
		assertEquals(2, margin.size(), "Margin should have 2 entries");
		assertEquals(new BigDecimal("200.25"), margin.get("party1"), "party1 margin should be 200.25");
		assertEquals(new BigDecimal("500.25"), margin.get("party2"), "party2 margin should be 500.25");
	}

	/**
	 * Test getMargin with very large values.
	 * Tests that large BigDecimal values are handled correctly.
	 */
	@Test
	void testGetMargin_LargeValues_ReturnsCorrectMargin() {
		// Arrange
		Map<String, BigDecimal> currentValues = Map.of(
			"party1", new BigDecimal("999999999999999.99")
		);
		Map<String, BigDecimal> previousValues = Map.of(
			"party1", new BigDecimal("888888888888888.88")
		);
		ValuationOracle oracle = new TestValuationOracle(currentValues, previousValues);
		SmartDerivativeContractSettlementOracle settlementOracle =
			new SmartDerivativeContractSettlementOracle(oracle);

		LocalDateTime periodStart = LocalDateTime.of(2024, 1, 1, 0, 0);
		LocalDateTime periodEnd = LocalDateTime.of(2024, 1, 31, 23, 59);

		// Act
		Map<String, BigDecimal> margin = settlementOracle.getMargin(periodStart, periodEnd);

		// Assert
		assertNotNull(margin, "Margin should not be null");
		assertEquals(1, margin.size(), "Margin should have 1 entry");
		assertEquals(new BigDecimal("111111111111111.11"), margin.get("party1"),
			"party1 margin should be correctly calculated for large values");
	}

	/**
	 * Test getMargin with reversed period times (end before start).
	 * Tests behavior when period end is before period start.
	 */
	@Test
	void testGetMargin_ReversedPeriodTimes_ReturnsMargin() {
		// Arrange
		Map<String, BigDecimal> currentValues = Map.of("party1", BigDecimal.valueOf(1000));
		Map<String, BigDecimal> previousValues = Map.of("party1", BigDecimal.valueOf(800));
		ValuationOracle oracle = new TestValuationOracle(currentValues, previousValues);
		SmartDerivativeContractSettlementOracle settlementOracle =
			new SmartDerivativeContractSettlementOracle(oracle);

		LocalDateTime periodStart = LocalDateTime.of(2024, 1, 31, 23, 59);
		LocalDateTime periodEnd = LocalDateTime.of(2024, 1, 1, 0, 0);

		// Act - the method doesn't validate period order, it just uses the times as provided
		Map<String, BigDecimal> margin = settlementOracle.getMargin(periodStart, periodEnd);

		// Assert
		assertNotNull(margin, "Margin should not be null even with reversed times");
		assertEquals(1, margin.size(), "Margin should have 1 entry");
		// Both calls use periodEnd as evaluation time, so behavior depends on oracle implementation
		assertEquals(BigDecimal.valueOf(200), margin.get("party1"));
	}

	/**
	 * Test getMargin with mixed positive and negative margins.
	 * Tests a realistic scenario where some parties gain and others lose value.
	 */
	@Test
	void testGetMargin_MixedMargins_ReturnsCorrectMargin() {
		// Arrange
		Map<String, BigDecimal> currentValues = Map.of(
			"party1", BigDecimal.valueOf(1200),
			"party2", BigDecimal.valueOf(900),
			"party3", BigDecimal.valueOf(1500),
			"party4", BigDecimal.valueOf(1000)
		);
		Map<String, BigDecimal> previousValues = Map.of(
			"party1", BigDecimal.valueOf(1000),
			"party2", BigDecimal.valueOf(1000),
			"party3", BigDecimal.valueOf(1500),
			"party4", BigDecimal.valueOf(800)
		);
		ValuationOracle oracle = new TestValuationOracle(currentValues, previousValues);
		SmartDerivativeContractSettlementOracle settlementOracle =
			new SmartDerivativeContractSettlementOracle(oracle);

		LocalDateTime periodStart = LocalDateTime.of(2024, 1, 1, 0, 0);
		LocalDateTime periodEnd = LocalDateTime.of(2024, 1, 31, 23, 59);

		// Act
		Map<String, BigDecimal> margin = settlementOracle.getMargin(periodStart, periodEnd);

		// Assert
		assertNotNull(margin, "Margin should not be null");
		assertEquals(4, margin.size(), "Margin should have 4 entries");
		assertEquals(BigDecimal.valueOf(200), margin.get("party1"), "party1 margin should be 200 (gained)");
		assertEquals(BigDecimal.valueOf(-100), margin.get("party2"), "party2 margin should be -100 (lost)");
		assertEquals(BigDecimal.valueOf(0), margin.get("party3"), "party3 margin should be 0 (no change)");
		assertEquals(BigDecimal.valueOf(200), margin.get("party4"), "party4 margin should be 200 (gained)");
	}

	/**
	 * Test getMargin with different LocalDateTime precision.
	 * Tests that the method works with various LocalDateTime values including nanoseconds.
	 */
	@Test
	void testGetMargin_DifferentTimeFormats_ReturnsCorrectMargin() {
		// Arrange
		Map<String, BigDecimal> currentValues = Map.of("party1", BigDecimal.valueOf(1000));
		Map<String, BigDecimal> previousValues = Map.of("party1", BigDecimal.valueOf(800));
		ValuationOracle oracle = new TestValuationOracle(currentValues, previousValues);
		SmartDerivativeContractSettlementOracle settlementOracle =
			new SmartDerivativeContractSettlementOracle(oracle);

		LocalDateTime periodStart = LocalDateTime.of(2024, 1, 1, 10, 30, 45, 123456789);
		LocalDateTime periodEnd = LocalDateTime.of(2024, 1, 31, 15, 45, 30, 987654321);

		// Act
		Map<String, BigDecimal> margin = settlementOracle.getMargin(periodStart, periodEnd);

		// Assert
		assertNotNull(margin, "Margin should not be null");
		assertEquals(1, margin.size(), "Margin should have 1 entry");
		assertEquals(BigDecimal.valueOf(200), margin.get("party1"), "Margin should be calculated correctly regardless of time precision");
	}
}
