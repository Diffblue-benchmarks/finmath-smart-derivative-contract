package net.finmath.smartcontract.valuation.oracle.simulated;

import net.finmath.stochastic.RandomVariable;
import net.finmath.time.TimeDiscretizationFromArray;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BrownianMotionOracleTest {

	@Test
	void testDefaultConstructor() {
		BrownianMotionOracle oracle = new BrownianMotionOracle();
		assertNotNull(oracle);
	}

	@Test
	void testConstructorWithInitialTime() {
		LocalDateTime initialTime = LocalDateTime.of(2020, 1, 1, 0, 0);
		BrownianMotionOracle oracle = new BrownianMotionOracle(initialTime);
		assertNotNull(oracle);
	}

	@Test
	void testConstructorWithAllParameters() {
		LocalDateTime initialTime = LocalDateTime.of(2020, 1, 1, 0, 0);
		BrownianMotionOracle oracle = new BrownianMotionOracle(initialTime, 0.0, 10.0, 0.02, 0.10, 500);
		assertNotNull(oracle);
	}

	@Test
	void testConstructorWithTimeDiscretization() {
		LocalDateTime initialTime = LocalDateTime.of(2020, 1, 1, 0, 0);
		var timeDiscretization = new TimeDiscretizationFromArray(0.0, 10.0, 1.0 / 365.0,
				TimeDiscretizationFromArray.ShortPeriodLocation.SHORT_PERIOD_AT_END);
		BrownianMotionOracle oracle = new BrownianMotionOracle(timeDiscretization, initialTime, 0.0, 0.02, 0.10, 500);
		assertNotNull(oracle);
	}

	@Test
	void testGetValue() {
		LocalDateTime initialTime = LocalDateTime.of(2020, 1, 1, 0, 0);
		BrownianMotionOracle oracle = new BrownianMotionOracle(initialTime, 0.0, 5.0, 0.02, 0.10, 100);

		LocalDateTime evaluationTime = LocalDateTime.of(2020, 6, 1, 0, 0);
		LocalDateTime marketDataTime = LocalDateTime.of(2020, 6, 1, 0, 0);

		RandomVariable value = oracle.getValue(evaluationTime, marketDataTime);
		assertNotNull(value);
	}

	@Test
	void testGetValueMultipleTimes() {
		LocalDateTime initialTime = LocalDateTime.of(2020, 1, 1, 0, 0);
		BrownianMotionOracle oracle = new BrownianMotionOracle(initialTime, 0.0, 5.0, 0.02, 0.10, 100);

		LocalDateTime time1 = LocalDateTime.of(2020, 3, 1, 0, 0);
		LocalDateTime time2 = LocalDateTime.of(2020, 9, 1, 0, 0);

		RandomVariable value1 = oracle.getValue(time1, time1);
		RandomVariable value2 = oracle.getValue(time2, time2);

		assertNotNull(value1);
		assertNotNull(value2);
		// Values at different times should generally differ
		assertEquals(100, value1.size(), "Number of paths should match");
		assertEquals(100, value2.size(), "Number of paths should match");
	}
}
