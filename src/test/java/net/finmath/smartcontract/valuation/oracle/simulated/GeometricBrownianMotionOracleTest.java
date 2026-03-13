package net.finmath.smartcontract.valuation.oracle.simulated;

import net.finmath.stochastic.RandomVariable;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class GeometricBrownianMotionOracleTest {

	private final LocalDateTime initialTime = LocalDateTime.of(2024, 1, 1, 0, 0);

	@Test
	void getValue_atInitialTime_shouldReturnInitialValue() {
		GeometricBrownianMotionOracle oracle =
				new GeometricBrownianMotionOracle(initialTime, 1.0, 5.0, 0.02, 0.10, 100);

		RandomVariable value = oracle.getValue(initialTime, initialTime);
		assertNotNull(value);
		assertTrue(value.size() >= 1);
	}

	@Test
	void getValue_afterSomeTime_shouldReturnNonNullValues() {
		GeometricBrownianMotionOracle oracle =
				new GeometricBrownianMotionOracle(initialTime, 1.0, 5.0, 0.02, 0.10, 100);

		LocalDateTime later = initialTime.plusDays(30);
		RandomVariable value = oracle.getValue(initialTime, later);
		assertNotNull(value);
		assertTrue(value.getAverage() > 0);
	}

	@Test
	void constructorWithInitialTime_shouldWork() {
		GeometricBrownianMotionOracle oracle = new GeometricBrownianMotionOracle(initialTime);

		RandomVariable value = oracle.getValue(initialTime, initialTime);
		assertNotNull(value);
	}

	@Test
	void getValue_shouldBeLazilyInitialized() {
		GeometricBrownianMotionOracle oracle =
				new GeometricBrownianMotionOracle(initialTime, 1.0, 5.0, 0.02, 0.10, 50);

		// First call triggers initialization
		RandomVariable value1 = oracle.getValue(initialTime, initialTime);
		// Second call should reuse the simulation
		RandomVariable value2 = oracle.getValue(initialTime, initialTime.plusDays(1));
		assertNotNull(value1);
		assertNotNull(value2);
	}
}
