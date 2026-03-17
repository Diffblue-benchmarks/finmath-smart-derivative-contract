package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import net.finmath.marketdata.calibration.CalibratedCurves;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for CalibrationSpecProviderFRA.
 */
class CalibrationSpecProviderFRATest {

	@Test
	void testConstructorWithStandardInputs() {
		// Given
		final String tenorLabel = "6M";
		final String maturityLabel = "12M";
		final double fraRate = 0.05;

		// When
		final CalibrationSpecProviderFRA provider = new CalibrationSpecProviderFRA(tenorLabel, maturityLabel, fraRate);

		// Then
		assertNotNull(provider);
	}

	@Test
	void testConstructorWithDifferentTenors() {
		// Given
		final String tenorLabel = "3M";
		final String maturityLabel = "9M";
		final double fraRate = 0.03;

		// When
		final CalibrationSpecProviderFRA provider = new CalibrationSpecProviderFRA(tenorLabel, maturityLabel, fraRate);

		// Then
		assertNotNull(provider);
	}

	@Test
	void testGetCalibrationSpec() {
		// Given
		final String tenorLabel = "6M";
		final String maturityLabel = "12M";
		final double fraRate = 0.05;
		final CalibrationSpecProviderFRA provider = new CalibrationSpecProviderFRA(tenorLabel, maturityLabel, fraRate);
		final CalibrationContext ctx = new TestCalibrationContext(LocalDate.of(2023, 1, 1), 1E-9);

		// When
		final CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(ctx);

		// Then
		assertNotNull(spec);
		assertEquals("EUR-6M12M", spec.getSymbol());
	}

	@Test
	void testGetCalibrationSpecWithDifferentContext() {
		// Given
		final String tenorLabel = "3M";
		final String maturityLabel = "9M";
		final double fraRate = 0.03;
		final CalibrationSpecProviderFRA provider = new CalibrationSpecProviderFRA(tenorLabel, maturityLabel, fraRate);
		final CalibrationContext ctx = new TestCalibrationContext(LocalDate.of(2024, 6, 15), 1E-9);

		// When
		final CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(ctx);

		// Then
		assertNotNull(spec);
		assertEquals("EUR-3M9M", spec.getSymbol());
	}

	/**
	 * Simple implementation of CalibrationContext for testing.
	 */
	private static class TestCalibrationContext implements CalibrationContext {
		private final LocalDate referenceDate;
		private final double accuracy;

		TestCalibrationContext(final LocalDate referenceDate, final double accuracy) {
			this.referenceDate = referenceDate;
			this.accuracy = accuracy;
		}

		@Override
		public LocalDate getReferenceDate() {
			return referenceDate;
		}

		@Override
		public double getAccuracy() {
			return accuracy;
		}
	}
}
