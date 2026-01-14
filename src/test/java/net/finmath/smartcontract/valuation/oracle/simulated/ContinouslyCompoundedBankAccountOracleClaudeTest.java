/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */

package net.finmath.smartcontract.valuation.oracle.simulated;

import net.finmath.stochastic.RandomVariable;
import net.finmath.time.TimeDiscretization;
import net.finmath.time.TimeDiscretizationFromArray;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for ContinouslyCompoundedBankAccountOracle.
 * Tests all constructors and the getValue method with focus on branch and condition coverage.
 *
 * @author Claude Code
 */
class ContinouslyCompoundedBankAccountOracleClaudeTest {

	/**
	 * Test default constructor (no arguments).
	 * This constructor uses LocalDateTime.now(), so we just verify it creates a valid oracle.
	 */
	@Test
	void testDefaultConstructor_Success() {
		// Act
		ContinouslyCompoundedBankAccountOracle oracle = new ContinouslyCompoundedBankAccountOracle();

		// Assert
		assertNotNull(oracle, "Oracle should be created successfully");
	}

	/**
	 * Test constructor with only initial time.
	 * Should use default values: initialValue=1.0, timeHorizon=20.0, riskFreeRate=0.02
	 */
	@Test
	void testConstructorWithInitialTime_Success() {
		// Arrange
		LocalDateTime initialTime = LocalDateTime.of(2024, 1, 1, 0, 0);

		// Act
		ContinouslyCompoundedBankAccountOracle oracle = new ContinouslyCompoundedBankAccountOracle(initialTime);

		// Assert
		assertNotNull(oracle, "Oracle should be created successfully");
	}

	/**
	 * Test constructor with initial time, initial value, time horizon, and risk-free rate.
	 */
	@Test
	void testConstructorWithAllParameters_Success() {
		// Arrange
		LocalDateTime initialTime = LocalDateTime.of(2024, 1, 1, 0, 0);
		double initialValue = 100.0;
		double timeHorizon = 10.0;
		double riskFreeRate = 0.05;

		// Act
		ContinouslyCompoundedBankAccountOracle oracle = new ContinouslyCompoundedBankAccountOracle(
			initialTime, initialValue, timeHorizon, riskFreeRate);

		// Assert
		assertNotNull(oracle, "Oracle should be created successfully");
	}

	/**
	 * Test constructor with time discretization.
	 */
	@Test
	void testConstructorWithTimeDiscretization_Success() {
		// Arrange
		TimeDiscretization timeDiscretization = new TimeDiscretizationFromArray(
			0.0, 10.0, 1.0, TimeDiscretizationFromArray.ShortPeriodLocation.SHORT_PERIOD_AT_START);
		LocalDateTime initialTime = LocalDateTime.of(2024, 1, 1, 0, 0);
		double initialValue = 100.0;
		double riskFreeRate = 0.05;

		// Act
		ContinouslyCompoundedBankAccountOracle oracle = new ContinouslyCompoundedBankAccountOracle(
			timeDiscretization, initialTime, initialValue, riskFreeRate);

		// Assert
		assertNotNull(oracle, "Oracle should be created successfully");
	}

	/**
	 * Test getValue at initial time (T=0).
	 * Expected: initialValue * exp(0) = initialValue
	 */
	@Test
	void testGetValue_AtInitialTime_ReturnsInitialValue() {
		// Arrange
		LocalDateTime initialTime = LocalDateTime.of(2024, 1, 1, 0, 0);
		double initialValue = 100.0;
		double timeHorizon = 10.0;
		double riskFreeRate = 0.05;
		ContinouslyCompoundedBankAccountOracle oracle = new ContinouslyCompoundedBankAccountOracle(
			initialTime, initialValue, timeHorizon, riskFreeRate);

		// Act
		RandomVariable value = oracle.getValue(initialTime, initialTime);

		// Assert
		assertNotNull(value, "Value should not be null");
		assertEquals(initialValue, value.doubleValue(), 1e-10,
			"Value at initial time should equal initial value");
	}

	/**
	 * Test getValue after one year with 5% risk-free rate.
	 * Note: Uses ACT/365 day count, and 2024 is a leap year (366 days).
	 */
	@Test
	void testGetValue_AfterOneYear_ReturnsCompoundedValue() {
		// Arrange
		LocalDateTime initialTime = LocalDateTime.of(2024, 1, 1, 0, 0);
		LocalDateTime marketDataTime = LocalDateTime.of(2025, 1, 1, 0, 0); // 1 year later (leap year)
		double initialValue = 100.0;
		double timeHorizon = 10.0;
		double riskFreeRate = 0.05;
		ContinouslyCompoundedBankAccountOracle oracle = new ContinouslyCompoundedBankAccountOracle(
			initialTime, initialValue, timeHorizon, riskFreeRate);

		// Act
		RandomVariable value = oracle.getValue(initialTime, marketDataTime);

		// Assert
		assertNotNull(value, "Value should not be null");
		assertTrue(value.doubleValue() > initialValue,
			"Value should be greater than initial value");
		assertTrue(value.doubleValue() < initialValue * 1.06,
			"Value growth should be reasonable for 5% rate");
	}

	/**
	 * Test getValue with zero risk-free rate.
	 * Expected: value should remain constant at initialValue
	 */
	@Test
	void testGetValue_ZeroRiskFreeRate_ReturnsInitialValue() {
		// Arrange
		LocalDateTime initialTime = LocalDateTime.of(2024, 1, 1, 0, 0);
		LocalDateTime marketDataTime = LocalDateTime.of(2025, 1, 1, 0, 0);
		double initialValue = 100.0;
		double timeHorizon = 10.0;
		double riskFreeRate = 0.0; // Zero rate
		ContinouslyCompoundedBankAccountOracle oracle = new ContinouslyCompoundedBankAccountOracle(
			initialTime, initialValue, timeHorizon, riskFreeRate);

		// Act
		RandomVariable value = oracle.getValue(initialTime, marketDataTime);

		// Assert
		assertNotNull(value, "Value should not be null");
		assertEquals(initialValue, value.doubleValue(), 1e-10,
			"With zero rate, value should remain constant");
	}

	/**
	 * Test getValue with negative risk-free rate.
	 * Expected: value should decrease over time
	 */
	@Test
	void testGetValue_NegativeRiskFreeRate_ReturnsDecreasingValue() {
		// Arrange
		LocalDateTime initialTime = LocalDateTime.of(2024, 1, 1, 0, 0);
		LocalDateTime marketDataTime = LocalDateTime.of(2025, 1, 1, 0, 0);
		double initialValue = 100.0;
		double timeHorizon = 10.0;
		double riskFreeRate = -0.05; // Negative rate
		ContinouslyCompoundedBankAccountOracle oracle = new ContinouslyCompoundedBankAccountOracle(
			initialTime, initialValue, timeHorizon, riskFreeRate);

		// Act
		RandomVariable value = oracle.getValue(initialTime, marketDataTime);

		// Assert
		assertNotNull(value, "Value should not be null");
		assertTrue(value.doubleValue() < initialValue,
			"Value should be less than initial value with negative rate");
		assertTrue(value.doubleValue() > initialValue * 0.94,
			"Value should decrease reasonably with -5% rate");
	}

	/**
	 * Test getValue with fractional year (e.g., 6 months).
	 */
	@Test
	void testGetValue_FractionalYear_ReturnsCorrectValue() {
		// Arrange
		LocalDateTime initialTime = LocalDateTime.of(2024, 1, 1, 0, 0);
		LocalDateTime marketDataTime = LocalDateTime.of(2024, 7, 1, 0, 0); // ~6 months
		double initialValue = 100.0;
		double timeHorizon = 10.0;
		double riskFreeRate = 0.05;
		ContinouslyCompoundedBankAccountOracle oracle = new ContinouslyCompoundedBankAccountOracle(
			initialTime, initialValue, timeHorizon, riskFreeRate);

		// Act
		RandomVariable value = oracle.getValue(initialTime, marketDataTime);

		// Assert
		// Time in years (ACT/365) for ~6 months ≈ 181/365 ≈ 0.496
		assertNotNull(value, "Value should not be null");
		assertTrue(value.doubleValue() > initialValue,
			"Value should be greater than initial value");
		assertTrue(value.doubleValue() < initialValue * Math.exp(riskFreeRate),
			"Value should be less than one year's growth");
	}

	/**
	 * Test getValue with very small initial value.
	 */
	@Test
	void testGetValue_SmallInitialValue_ReturnsCorrectValue() {
		// Arrange
		LocalDateTime initialTime = LocalDateTime.of(2024, 1, 1, 0, 0);
		LocalDateTime marketDataTime = LocalDateTime.of(2025, 1, 1, 0, 0);
		double initialValue = 0.01; // Very small
		double timeHorizon = 10.0;
		double riskFreeRate = 0.05;
		ContinouslyCompoundedBankAccountOracle oracle = new ContinouslyCompoundedBankAccountOracle(
			initialTime, initialValue, timeHorizon, riskFreeRate);

		// Act
		RandomVariable value = oracle.getValue(initialTime, marketDataTime);

		// Assert
		assertNotNull(value, "Value should not be null");
		assertTrue(value.doubleValue() > initialValue,
			"Small initial values should compound and increase");
		assertTrue(value.doubleValue() < initialValue * 1.06,
			"Growth should be reasonable for small initial values");
	}

	/**
	 * Test getValue with very large initial value.
	 */
	@Test
	void testGetValue_LargeInitialValue_ReturnsCorrectValue() {
		// Arrange
		LocalDateTime initialTime = LocalDateTime.of(2024, 1, 1, 0, 0);
		LocalDateTime marketDataTime = LocalDateTime.of(2025, 1, 1, 0, 0);
		double initialValue = 1000000.0; // Very large
		double timeHorizon = 10.0;
		double riskFreeRate = 0.05;
		ContinouslyCompoundedBankAccountOracle oracle = new ContinouslyCompoundedBankAccountOracle(
			initialTime, initialValue, timeHorizon, riskFreeRate);

		// Act
		RandomVariable value = oracle.getValue(initialTime, marketDataTime);

		// Assert
		assertNotNull(value, "Value should not be null");
		assertTrue(value.doubleValue() > initialValue,
			"Large initial values should compound and increase");
		assertTrue(value.doubleValue() < initialValue * 1.06,
			"Growth should be reasonable for large initial values");
	}

	/**
	 * Test getValue with high risk-free rate.
	 */
	@Test
	void testGetValue_HighRiskFreeRate_ReturnsCorrectValue() {
		// Arrange
		LocalDateTime initialTime = LocalDateTime.of(2024, 1, 1, 0, 0);
		LocalDateTime marketDataTime = LocalDateTime.of(2025, 1, 1, 0, 0);
		double initialValue = 100.0;
		double timeHorizon = 10.0;
		double riskFreeRate = 0.20; // 20% rate
		ContinouslyCompoundedBankAccountOracle oracle = new ContinouslyCompoundedBankAccountOracle(
			initialTime, initialValue, timeHorizon, riskFreeRate);

		// Act
		RandomVariable value = oracle.getValue(initialTime, marketDataTime);

		// Assert
		assertNotNull(value, "Value should not be null");
		assertTrue(value.doubleValue() > 120.0,
			"With 20% continuous rate, value should grow significantly");
		assertTrue(value.doubleValue() < 125.0,
			"Value growth should be reasonable for 20% rate");
	}

	/**
	 * Test getValue multiple times with same parameters returns consistent results.
	 */
	@Test
	void testGetValue_MultipleCalls_ReturnsConsistentResults() {
		// Arrange
		LocalDateTime initialTime = LocalDateTime.of(2024, 1, 1, 0, 0);
		LocalDateTime marketDataTime = LocalDateTime.of(2025, 1, 1, 0, 0);
		double initialValue = 100.0;
		double timeHorizon = 10.0;
		double riskFreeRate = 0.05;
		ContinouslyCompoundedBankAccountOracle oracle = new ContinouslyCompoundedBankAccountOracle(
			initialTime, initialValue, timeHorizon, riskFreeRate);

		// Act
		RandomVariable value1 = oracle.getValue(initialTime, marketDataTime);
		RandomVariable value2 = oracle.getValue(initialTime, marketDataTime);
		RandomVariable value3 = oracle.getValue(initialTime, marketDataTime);

		// Assert
		assertNotNull(value1, "First value should not be null");
		assertNotNull(value2, "Second value should not be null");
		assertNotNull(value3, "Third value should not be null");
		assertEquals(value1.doubleValue(), value2.doubleValue(), 1e-10,
			"Multiple calls should return same value");
		assertEquals(value2.doubleValue(), value3.doubleValue(), 1e-10,
			"Multiple calls should return same value");
	}

	/**
	 * Test getValue with evaluation time different from market data time.
	 * The evaluation time parameter is passed but the calculation only uses market data time.
	 */
	@Test
	void testGetValue_DifferentEvaluationTime_UsesMarketDataTime() {
		// Arrange
		LocalDateTime initialTime = LocalDateTime.of(2024, 1, 1, 0, 0);
		LocalDateTime evaluationTime = LocalDateTime.of(2024, 6, 1, 0, 0);
		LocalDateTime marketDataTime = LocalDateTime.of(2025, 1, 1, 0, 0);
		double initialValue = 100.0;
		double timeHorizon = 10.0;
		double riskFreeRate = 0.05;
		ContinouslyCompoundedBankAccountOracle oracle = new ContinouslyCompoundedBankAccountOracle(
			initialTime, initialValue, timeHorizon, riskFreeRate);

		// Act
		RandomVariable value = oracle.getValue(evaluationTime, marketDataTime);

		// Assert
		assertNotNull(value, "Value should not be null");
		assertTrue(value.doubleValue() > initialValue,
			"Value calculation should be based on market data time");
		assertTrue(value.doubleValue() < initialValue * 1.06,
			"Value should grow reasonably from initial to market data time");
	}

	/**
	 * Test getValue with market data time before initial time.
	 * This tests behavior with negative time difference.
	 */
	@Test
	void testGetValue_MarketDataBeforeInitialTime_ReturnsCorrectValue() {
		// Arrange
		LocalDateTime initialTime = LocalDateTime.of(2024, 1, 1, 0, 0);
		LocalDateTime marketDataTime = LocalDateTime.of(2023, 1, 1, 0, 0); // 1 year before
		double initialValue = 100.0;
		double timeHorizon = 10.0;
		double riskFreeRate = 0.05;
		ContinouslyCompoundedBankAccountOracle oracle = new ContinouslyCompoundedBankAccountOracle(
			initialTime, initialValue, timeHorizon, riskFreeRate);

		// Act
		RandomVariable value = oracle.getValue(initialTime, marketDataTime);

		// Assert
		// Time will be negative (-1 year), so with positive rate, value will be discounted
		double expectedValue = initialValue * Math.exp(riskFreeRate * -1.0);
		assertNotNull(value, "Value should not be null");
		assertEquals(expectedValue, value.doubleValue(), 1e-6,
			"Negative time should result in discounting");
		assertTrue(value.doubleValue() < initialValue,
			"Value before initial time should be less than initial value");
	}

	/**
	 * Test getValue returns Scalar type (immutable RandomVariable).
	 */
	@Test
	void testGetValue_ReturnsScalarType() {
		// Arrange
		LocalDateTime initialTime = LocalDateTime.of(2024, 1, 1, 0, 0);
		LocalDateTime marketDataTime = LocalDateTime.of(2025, 1, 1, 0, 0);
		double initialValue = 100.0;
		double timeHorizon = 10.0;
		double riskFreeRate = 0.05;
		ContinouslyCompoundedBankAccountOracle oracle = new ContinouslyCompoundedBankAccountOracle(
			initialTime, initialValue, timeHorizon, riskFreeRate);

		// Act
		RandomVariable value = oracle.getValue(initialTime, marketDataTime);

		// Assert
		assertNotNull(value, "Value should not be null");
		// Scalar is a specific implementation of RandomVariable
		assertTrue(value.isDeterministic(),
			"Returned value should be deterministic (Scalar)");
	}

	/**
	 * Test constructor with different ShortPeriodLocation in time discretization.
	 */
	@Test
	void testConstructor_DifferentShortPeriodLocation_Success() {
		// Arrange
		TimeDiscretization timeDiscretizationStart = new TimeDiscretizationFromArray(
			0.0, 10.0, 1.0, TimeDiscretizationFromArray.ShortPeriodLocation.SHORT_PERIOD_AT_START);
		TimeDiscretization timeDiscretizationEnd = new TimeDiscretizationFromArray(
			0.0, 10.0, 1.0, TimeDiscretizationFromArray.ShortPeriodLocation.SHORT_PERIOD_AT_END);
		LocalDateTime initialTime = LocalDateTime.of(2024, 1, 1, 0, 0);
		double initialValue = 100.0;
		double riskFreeRate = 0.05;

		// Act
		ContinouslyCompoundedBankAccountOracle oracleStart = new ContinouslyCompoundedBankAccountOracle(
			timeDiscretizationStart, initialTime, initialValue, riskFreeRate);
		ContinouslyCompoundedBankAccountOracle oracleEnd = new ContinouslyCompoundedBankAccountOracle(
			timeDiscretizationEnd, initialTime, initialValue, riskFreeRate);

		// Assert
		assertNotNull(oracleStart, "Oracle with SHORT_PERIOD_AT_START should be created");
		assertNotNull(oracleEnd, "Oracle with SHORT_PERIOD_AT_END should be created");
	}

	/**
	 * Test getValue with very long time horizon.
	 */
	@Test
	void testGetValue_LongTimeHorizon_ReturnsCorrectValue() {
		// Arrange
		LocalDateTime initialTime = LocalDateTime.of(2024, 1, 1, 0, 0);
		LocalDateTime marketDataTime = LocalDateTime.of(2034, 1, 1, 0, 0); // 10 years later
		double initialValue = 100.0;
		double timeHorizon = 20.0; // 20 year horizon
		double riskFreeRate = 0.05;
		ContinouslyCompoundedBankAccountOracle oracle = new ContinouslyCompoundedBankAccountOracle(
			initialTime, initialValue, timeHorizon, riskFreeRate);

		// Act
		RandomVariable value = oracle.getValue(initialTime, marketDataTime);

		// Assert
		assertNotNull(value, "Value should not be null");
		assertTrue(value.doubleValue() > initialValue * 1.6,
			"Value after 10 years should increase significantly");
		assertTrue(value.doubleValue() < initialValue * 1.7,
			"Value growth over 10 years should be reasonable");
	}

	/**
	 * Test constructor with edge case: initial value of 1.0 and rate of 0.0.
	 * This creates a constant oracle that always returns 1.0.
	 */
	@Test
	void testConstructor_UnitValueZeroRate_ReturnsConstantOne() {
		// Arrange
		LocalDateTime initialTime = LocalDateTime.of(2024, 1, 1, 0, 0);
		LocalDateTime marketDataTime = LocalDateTime.of(2025, 1, 1, 0, 0);
		double initialValue = 1.0;
		double timeHorizon = 10.0;
		double riskFreeRate = 0.0;
		ContinouslyCompoundedBankAccountOracle oracle = new ContinouslyCompoundedBankAccountOracle(
			initialTime, initialValue, timeHorizon, riskFreeRate);

		// Act
		RandomVariable value = oracle.getValue(initialTime, marketDataTime);

		// Assert
		assertNotNull(value, "Value should not be null");
		assertEquals(1.0, value.doubleValue(), 1e-10,
			"Oracle with unit value and zero rate should always return 1.0");
	}

	/**
	 * Test getValue with specific date that has time component.
	 */
	@Test
	void testGetValue_WithTimeComponent_ReturnsCorrectValue() {
		// Arrange
		LocalDateTime initialTime = LocalDateTime.of(2024, 1, 1, 10, 30, 45);
		LocalDateTime marketDataTime = LocalDateTime.of(2024, 1, 2, 15, 45, 30);
		double initialValue = 100.0;
		double timeHorizon = 10.0;
		double riskFreeRate = 0.05;
		ContinouslyCompoundedBankAccountOracle oracle = new ContinouslyCompoundedBankAccountOracle(
			initialTime, initialValue, timeHorizon, riskFreeRate);

		// Act
		RandomVariable value = oracle.getValue(initialTime, marketDataTime);

		// Assert
		assertNotNull(value, "Value should not be null");
		assertTrue(value.doubleValue() > initialValue,
			"Value should be greater than initial value for future date");
	}

	/**
	 * Test that default constructor creates oracle that can calculate values.
	 * Since it uses LocalDateTime.now(), we test that it works immediately after creation.
	 */
	@Test
	void testDefaultConstructor_CanCalculateValues() {
		// Arrange
		ContinouslyCompoundedBankAccountOracle oracle = new ContinouslyCompoundedBankAccountOracle();
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime future = now.plusYears(1);

		// Act
		RandomVariable value = oracle.getValue(now, future);

		// Assert
		assertNotNull(value, "Value should not be null");
		assertTrue(value.doubleValue() > 0,
			"Default oracle should return positive value");
	}

	/**
	 * Test getValue with same evaluationTime and marketDataTime but different from initialTime.
	 */
	@Test
	void testGetValue_SameEvalAndMarketTime_ReturnsCorrectValue() {
		// Arrange
		LocalDateTime initialTime = LocalDateTime.of(2024, 1, 1, 0, 0);
		LocalDateTime sameTime = LocalDateTime.of(2025, 1, 1, 0, 0);
		double initialValue = 100.0;
		double timeHorizon = 10.0;
		double riskFreeRate = 0.05;
		ContinouslyCompoundedBankAccountOracle oracle = new ContinouslyCompoundedBankAccountOracle(
			initialTime, initialValue, timeHorizon, riskFreeRate);

		// Act
		RandomVariable value = oracle.getValue(sameTime, sameTime);

		// Assert
		assertNotNull(value, "Value should not be null");
		assertTrue(value.doubleValue() > initialValue,
			"Value should be based on time from initial to market data time");
		assertTrue(value.doubleValue() < initialValue * 1.06,
			"Value should grow reasonably over the period");
	}

	/**
	 * Test constructor with time discretization using fine granularity.
	 */
	@Test
	void testConstructor_FineGranularityTimeDiscretization_Success() {
		// Arrange
		TimeDiscretization timeDiscretization = new TimeDiscretizationFromArray(
			0.0, 1.0, 1.0/365.0, TimeDiscretizationFromArray.ShortPeriodLocation.SHORT_PERIOD_AT_END);
		LocalDateTime initialTime = LocalDateTime.of(2024, 1, 1, 0, 0);
		double initialValue = 100.0;
		double riskFreeRate = 0.05;

		// Act
		ContinouslyCompoundedBankAccountOracle oracle = new ContinouslyCompoundedBankAccountOracle(
			timeDiscretization, initialTime, initialValue, riskFreeRate);

		// Assert
		assertNotNull(oracle, "Oracle with fine granularity should be created successfully");
	}
}
