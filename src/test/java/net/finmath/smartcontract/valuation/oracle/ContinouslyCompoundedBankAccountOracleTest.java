/*
 * (c) Copyright Christian P. Fries, Germany. All rights reserved. Contact: email@christianfries.com.
 *
 * Created on 6 Oct 2018
 */

package net.finmath.smartcontract.valuation.oracle;

import net.finmath.smartcontract.valuation.oracle.simulated.ContinouslyCompoundedBankAccountOracle;
import net.finmath.stochastic.RandomVariable;
import net.finmath.time.TimeDiscretizationFromArray;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

/**
 * Tests for ContinouslyCompoundedBankAccountOracle.
 */
class ContinouslyCompoundedBankAccountOracleTest {

	@Test
	void testDefaultConstructor() {
		final ContinouslyCompoundedBankAccountOracle oracle = new ContinouslyCompoundedBankAccountOracle();

		Assertions.assertNotNull(oracle);
	}

	@Test
	void testConstructorWithInitialTime() {
		final LocalDateTime initialTime = LocalDateTime.of(2020, 1, 1, 0, 0);

		final ContinouslyCompoundedBankAccountOracle oracle = new ContinouslyCompoundedBankAccountOracle(initialTime);

		Assertions.assertNotNull(oracle);
	}

	@Test
	void testConstructorWithInitialTimeAndParameters() {
		final LocalDateTime initialTime = LocalDateTime.of(2020, 1, 1, 0, 0);
		final double initialValue = 100.0;
		final double timeHorizon = 10.0;
		final double riskFreeRate = 0.05;

		final ContinouslyCompoundedBankAccountOracle oracle = new ContinouslyCompoundedBankAccountOracle(initialTime, initialValue, timeHorizon, riskFreeRate);

		Assertions.assertNotNull(oracle);
	}

	@Test
	void testConstructorWithTimeDiscretization() {
		final TimeDiscretizationFromArray timeDiscretization = new TimeDiscretizationFromArray(0.0, 10.0, 1.0 / 365.0,
				TimeDiscretizationFromArray.ShortPeriodLocation.SHORT_PERIOD_AT_END);
		final LocalDateTime initialTime = LocalDateTime.of(2020, 1, 1, 0, 0);
		final double initialValue = 1.0;
		final double riskFreeRate = 0.02;

		final ContinouslyCompoundedBankAccountOracle oracle = new ContinouslyCompoundedBankAccountOracle(timeDiscretization, initialTime, initialValue, riskFreeRate);

		Assertions.assertNotNull(oracle);
	}

	@Test
	void getValue() {
		final LocalDateTime initialTime = LocalDateTime.of(2019, 1, 1, 0, 0);
		final double initialValue = 1.0;
		final double riskFreeRate = 0.05;
		final ContinouslyCompoundedBankAccountOracle oracle = new ContinouslyCompoundedBankAccountOracle(initialTime, initialValue, 20.0, riskFreeRate);

		// 2019 is not a leap year, so 2019-01-01 to 2020-01-01 is exactly 365 days (time = 1.0 in ACT/365)
		final LocalDateTime marketDataTime = LocalDateTime.of(2020, 1, 1, 0, 0);

		final RandomVariable result = oracle.getValue(initialTime, marketDataTime);

		final double expectedTime = 1.0;
		final double expectedValue = initialValue * Math.exp(riskFreeRate * expectedTime);

		Assertions.assertEquals(expectedValue, result.doubleValue(), 1e-4, "getValue should return initialValue * exp(riskFreeRate * time)");
	}
}
