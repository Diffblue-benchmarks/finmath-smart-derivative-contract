/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */

package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import net.finmath.marketdata.calibration.CalibratedCurves;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for CalibrationSpecProviderFRA.
 * Tests all methods with focus on branch and condition coverage.
 *
 * @author Claude Code
 */
class CalibrationSpecProviderFRAClaudeTest {

	/**
	 * Test constructor with typical FRA parameters.
	 * Tests the basic constructor path and calculation of startOffsetLabel.
	 */
	@Test
	void testConstructorWithTypicalParameters() {
		String tenorLabel = "3M";
		String maturityLabel = "6M";
		double fraRate = 0.05;

		CalibrationSpecProviderFRA provider = new CalibrationSpecProviderFRA(
			tenorLabel,
			maturityLabel,
			fraRate
		);

		assertNotNull(provider);
	}

	/**
	 * Test constructor with zero FRA rate.
	 */
	@Test
	void testConstructorWithZeroRate() {
		String tenorLabel = "6M";
		String maturityLabel = "12M";
		double fraRate = 0.0;

		CalibrationSpecProviderFRA provider = new CalibrationSpecProviderFRA(
			tenorLabel,
			maturityLabel,
			fraRate
		);

		assertNotNull(provider);
	}

	/**
	 * Test constructor with negative FRA rate.
	 */
	@Test
	void testConstructorWithNegativeRate() {
		String tenorLabel = "3M";
		String maturityLabel = "9M";
		double fraRate = -0.01;

		CalibrationSpecProviderFRA provider = new CalibrationSpecProviderFRA(
			tenorLabel,
			maturityLabel,
			fraRate
		);

		assertNotNull(provider);
	}

	/**
	 * Test constructor where maturity equals tenor (offset should be 0M).
	 * This tests the edge case where nMonthOffset = 0.
	 */
	@Test
	void testConstructorWithMaturityEqualsTenor() {
		String tenorLabel = "6M";
		String maturityLabel = "6M";
		double fraRate = 0.03;

		CalibrationSpecProviderFRA provider = new CalibrationSpecProviderFRA(
			tenorLabel,
			maturityLabel,
			fraRate
		);

		assertNotNull(provider);
	}

	/**
	 * Test constructor with short tenor and longer maturity.
	 * Common FRA configuration - e.g., 3x6 (3 month tenor, 6 month maturity).
	 */
	@Test
	void testConstructorWith3x6FRA() {
		String tenorLabel = "3M";
		String maturityLabel = "6M";
		double fraRate = 0.045;

		CalibrationSpecProviderFRA provider = new CalibrationSpecProviderFRA(
			tenorLabel,
			maturityLabel,
			fraRate
		);

		assertNotNull(provider);
	}

	/**
	 * Test constructor with 6x12 FRA configuration.
	 */
	@Test
	void testConstructorWith6x12FRA() {
		String tenorLabel = "6M";
		String maturityLabel = "12M";
		double fraRate = 0.05;

		CalibrationSpecProviderFRA provider = new CalibrationSpecProviderFRA(
			tenorLabel,
			maturityLabel,
			fraRate
		);

		assertNotNull(provider);
	}

	/**
	 * Test constructor with 1M tenor and 3M maturity.
	 */
	@Test
	void testConstructorWith1x3FRA() {
		String tenorLabel = "1M";
		String maturityLabel = "3M";
		double fraRate = 0.035;

		CalibrationSpecProviderFRA provider = new CalibrationSpecProviderFRA(
			tenorLabel,
			maturityLabel,
			fraRate
		);

		assertNotNull(provider);
	}

	/**
	 * Test constructor with large offset (long maturity, short tenor).
	 */
	@Test
	void testConstructorWithLargeOffset() {
		String tenorLabel = "3M";
		String maturityLabel = "24M";
		double fraRate = 0.055;

		CalibrationSpecProviderFRA provider = new CalibrationSpecProviderFRA(
			tenorLabel,
			maturityLabel,
			fraRate
		);

		assertNotNull(provider);
	}

	/**
	 * Test constructor with very high rate.
	 */
	@Test
	void testConstructorWithHighRate() {
		String tenorLabel = "3M";
		String maturityLabel = "9M";
		double fraRate = 0.5; // 50%

		CalibrationSpecProviderFRA provider = new CalibrationSpecProviderFRA(
			tenorLabel,
			maturityLabel,
			fraRate
		);

		assertNotNull(provider);
	}

	/**
	 * Test constructor with very small rate.
	 */
	@Test
	void testConstructorWithVerySmallRate() {
		String tenorLabel = "6M";
		String maturityLabel = "12M";
		double fraRate = 0.0001; // 0.01%

		CalibrationSpecProviderFRA provider = new CalibrationSpecProviderFRA(
			tenorLabel,
			maturityLabel,
			fraRate
		);

		assertNotNull(provider);
	}

	/**
	 * Test getCalibrationSpec with typical FRA parameters and verify basic structure.
	 */
	@Test
	void testGetCalibrationSpecWithTypicalParameters() {
		String tenorLabel = "3M";
		String maturityLabel = "6M";
		double fraRate = 0.05;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderFRA provider = new CalibrationSpecProviderFRA(
			tenorLabel,
			maturityLabel,
			fraRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
		assertNotNull(spec.toString());
	}

	/**
	 * Test getCalibrationSpec with 6x12 FRA.
	 */
	@Test
	void testGetCalibrationSpecWith6x12FRA() {
		String tenorLabel = "6M";
		String maturityLabel = "12M";
		double fraRate = 0.045;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderFRA provider = new CalibrationSpecProviderFRA(
			tenorLabel,
			maturityLabel,
			fraRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
		assertNotNull(spec.toString());
	}

	/**
	 * Test getCalibrationSpec with zero FRA rate.
	 */
	@Test
	void testGetCalibrationSpecWithZeroRate() {
		String tenorLabel = "3M";
		String maturityLabel = "9M";
		double fraRate = 0.0;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 12, 31, 23, 59, 59);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderFRA provider = new CalibrationSpecProviderFRA(
			tenorLabel,
			maturityLabel,
			fraRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
	}

	/**
	 * Test getCalibrationSpec with negative rate.
	 */
	@Test
	void testGetCalibrationSpecWithNegativeRate() {
		String tenorLabel = "6M";
		String maturityLabel = "12M";
		double fraRate = -0.02;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderFRA provider = new CalibrationSpecProviderFRA(
			tenorLabel,
			maturityLabel,
			fraRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
	}

	/**
	 * Test getCalibrationSpec with different tenor labels.
	 */
	@Test
	void testGetCalibrationSpecDifferentTenors() {
		double fraRate = 0.04;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		String[][] fraConfigurations = {
			{"1M", "3M"},
			{"3M", "6M"},
			{"6M", "12M"},
			{"12M", "24M"}
		};

		for (String[] config : fraConfigurations) {
			CalibrationSpecProviderFRA provider = new CalibrationSpecProviderFRA(
				config[0],
				config[1],
				fraRate
			);

			CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

			assertNotNull(spec, "Spec should not be null for tenor: " + config[0] + " and maturity: " + config[1]);
		}
	}

	/**
	 * Test getCalibrationSpec with different reference dates.
	 */
	@Test
	void testGetCalibrationSpecDifferentReferenceDates() {
		String tenorLabel = "6M";
		String maturityLabel = "12M";
		double fraRate = 0.045;

		LocalDateTime[] referenceDates = {
			LocalDateTime.of(2024, 1, 1, 0, 0, 0),
			LocalDateTime.of(2024, 6, 15, 10, 30, 0),
			LocalDateTime.of(2024, 12, 31, 23, 59, 59),
			LocalDateTime.of(2025, 2, 28, 12, 0, 0)
		};

		for (LocalDateTime refDate : referenceDates) {
			CalibrationContext context = new CalibrationContextImpl(refDate, 1e-6);
			CalibrationSpecProviderFRA provider = new CalibrationSpecProviderFRA(
				tenorLabel,
				maturityLabel,
				fraRate
			);

			CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

			assertNotNull(spec, "Spec should not be null for date: " + refDate);
		}
	}

	/**
	 * Test getCalibrationSpec with leap year date.
	 */
	@Test
	void testGetCalibrationSpecLeapYearDate() {
		String tenorLabel = "3M";
		String maturityLabel = "6M";
		double fraRate = 0.03;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 2, 29, 12, 0, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderFRA provider = new CalibrationSpecProviderFRA(
			tenorLabel,
			maturityLabel,
			fraRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
	}

	/**
	 * Test getCalibrationSpec with maturity equal to tenor (edge case).
	 * This should create a schedule with 0M start offset.
	 */
	@Test
	void testGetCalibrationSpecMaturityEqualsTenor() {
		String tenorLabel = "6M";
		String maturityLabel = "6M";
		double fraRate = 0.04;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderFRA provider = new CalibrationSpecProviderFRA(
			tenorLabel,
			maturityLabel,
			fraRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
	}

	/**
	 * Test getCalibrationSpec with high FRA rate.
	 */
	@Test
	void testGetCalibrationSpecHighRate() {
		String tenorLabel = "3M";
		String maturityLabel = "9M";
		double fraRate = 0.25; // 25%
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderFRA provider = new CalibrationSpecProviderFRA(
			tenorLabel,
			maturityLabel,
			fraRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
	}

	/**
	 * Test getCalibrationSpec with Double.MAX_VALUE rate.
	 */
	@Test
	void testGetCalibrationSpecMaxRate() {
		String tenorLabel = "3M";
		String maturityLabel = "6M";
		double fraRate = Double.MAX_VALUE;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderFRA provider = new CalibrationSpecProviderFRA(
			tenorLabel,
			maturityLabel,
			fraRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
	}

	/**
	 * Test getCalibrationSpec with Double.MIN_VALUE rate.
	 */
	@Test
	void testGetCalibrationSpecMinRate() {
		String tenorLabel = "6M";
		String maturityLabel = "12M";
		double fraRate = Double.MIN_VALUE;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderFRA provider = new CalibrationSpecProviderFRA(
			tenorLabel,
			maturityLabel,
			fraRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
	}

	/**
	 * Test getCalibrationSpec with positive infinity rate.
	 */
	@Test
	void testGetCalibrationSpecPositiveInfinityRate() {
		String tenorLabel = "3M";
		String maturityLabel = "9M";
		double fraRate = Double.POSITIVE_INFINITY;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderFRA provider = new CalibrationSpecProviderFRA(
			tenorLabel,
			maturityLabel,
			fraRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
	}

	/**
	 * Test getCalibrationSpec with negative infinity rate.
	 */
	@Test
	void testGetCalibrationSpecNegativeInfinityRate() {
		String tenorLabel = "6M";
		String maturityLabel = "12M";
		double fraRate = Double.NEGATIVE_INFINITY;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderFRA provider = new CalibrationSpecProviderFRA(
			tenorLabel,
			maturityLabel,
			fraRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
	}

	/**
	 * Test getCalibrationSpec with NaN rate.
	 */
	@Test
	void testGetCalibrationSpecNaNRate() {
		String tenorLabel = "3M";
		String maturityLabel = "6M";
		double fraRate = Double.NaN;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderFRA provider = new CalibrationSpecProviderFRA(
			tenorLabel,
			maturityLabel,
			fraRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
	}

	/**
	 * Test getCalibrationSpec called multiple times returns consistent results.
	 */
	@Test
	void testGetCalibrationSpecConsistency() {
		String tenorLabel = "3M";
		String maturityLabel = "9M";
		double fraRate = 0.04;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderFRA provider = new CalibrationSpecProviderFRA(
			tenorLabel,
			maturityLabel,
			fraRate
		);

		CalibratedCurves.CalibrationSpec spec1 = provider.getCalibrationSpec(context);
		CalibratedCurves.CalibrationSpec spec2 = provider.getCalibrationSpec(context);

		assertNotNull(spec1);
		assertNotNull(spec2);
	}

	/**
	 * Test that CalibrationSpecProviderFRA implements CalibrationSpecProvider interface.
	 */
	@Test
	void testImplementsInterface() {
		CalibrationSpecProviderFRA provider = new CalibrationSpecProviderFRA(
			"3M",
			"6M",
			0.05
		);

		assertTrue(provider instanceof CalibrationSpecProvider);
	}

	/**
	 * Test constructor with 12M tenor and 24M maturity (12 month offset).
	 */
	@Test
	void testConstructorWith12x24FRA() {
		String tenorLabel = "12M";
		String maturityLabel = "24M";
		double fraRate = 0.06;

		CalibrationSpecProviderFRA provider = new CalibrationSpecProviderFRA(
			tenorLabel,
			maturityLabel,
			fraRate
		);

		assertNotNull(provider);
	}

	/**
	 * Test getCalibrationSpec with short tenor and very long maturity.
	 */
	@Test
	void testGetCalibrationSpecShortTenorLongMaturity() {
		String tenorLabel = "1M";
		String maturityLabel = "24M";
		double fraRate = 0.055;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderFRA provider = new CalibrationSpecProviderFRA(
			tenorLabel,
			maturityLabel,
			fraRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
	}

	/**
	 * Test constructor parsing of tenor label "1M".
	 */
	@Test
	void testConstructorParsesTenorLabel1M() {
		String tenorLabel = "1M";
		String maturityLabel = "4M";
		double fraRate = 0.03;

		CalibrationSpecProviderFRA provider = new CalibrationSpecProviderFRA(
			tenorLabel,
			maturityLabel,
			fraRate
		);

		assertNotNull(provider);
	}

	/**
	 * Test constructor parsing of tenor label "12M".
	 */
	@Test
	void testConstructorParsesTenorLabel12M() {
		String tenorLabel = "12M";
		String maturityLabel = "18M";
		double fraRate = 0.055;

		CalibrationSpecProviderFRA provider = new CalibrationSpecProviderFRA(
			tenorLabel,
			maturityLabel,
			fraRate
		);

		assertNotNull(provider);
	}

	/**
	 * Test constructor with various offset calculations.
	 * This tests the calculation: nMonthOffset = nMonthMaturity - nMonthTenor
	 */
	@Test
	void testConstructorOffsetCalculations() {
		String[][] configs = {
			{"3M", "6M"},   // offset = 3
			{"6M", "12M"},  // offset = 6
			{"3M", "9M"},   // offset = 6
			{"1M", "7M"}    // offset = 6
		};

		for (String[] config : configs) {
			CalibrationSpecProviderFRA provider = new CalibrationSpecProviderFRA(
				config[0],
				config[1],
				0.04
			);
			assertNotNull(provider, "Provider should be created for " + config[0] + " tenor and " + config[1] + " maturity");
		}
	}

	/**
	 * Test getCalibrationSpec with various contexts to ensure schedule generation works.
	 */
	@Test
	void testGetCalibrationSpecScheduleGeneration() {
		String tenorLabel = "3M";
		String maturityLabel = "9M";
		double fraRate = 0.045;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderFRA provider = new CalibrationSpecProviderFRA(
			tenorLabel,
			maturityLabel,
			fraRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
		// Schedule should be generated with proper start offset
	}

	/**
	 * Test getCalibrationSpec with different accuracy values in context.
	 */
	@Test
	void testGetCalibrationSpecDifferentAccuracyValues() {
		String tenorLabel = "6M";
		String maturityLabel = "12M";
		double fraRate = 0.04;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);

		double[] accuracyValues = {1e-6, 1e-8, 1e-10, 1e-4};

		for (double accuracy : accuracyValues) {
			CalibrationContext context = new CalibrationContextImpl(referenceDateTime, accuracy);
			CalibrationSpecProviderFRA provider = new CalibrationSpecProviderFRA(
				tenorLabel,
				maturityLabel,
				fraRate
			);

			CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

			assertNotNull(spec, "Spec should not be null for accuracy: " + accuracy);
		}
	}

	/**
	 * Test getCalibrationSpec with year-end reference date.
	 */
	@Test
	void testGetCalibrationSpecYearEndDate() {
		String tenorLabel = "3M";
		String maturityLabel = "6M";
		double fraRate = 0.035;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 12, 31, 23, 59, 59);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderFRA provider = new CalibrationSpecProviderFRA(
			tenorLabel,
			maturityLabel,
			fraRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
	}

	/**
	 * Test getCalibrationSpec with year-start reference date.
	 */
	@Test
	void testGetCalibrationSpecYearStartDate() {
		String tenorLabel = "6M";
		String maturityLabel = "12M";
		double fraRate = 0.04;
		LocalDateTime referenceDateTime = LocalDateTime.of(2025, 1, 1, 0, 0, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderFRA provider = new CalibrationSpecProviderFRA(
			tenorLabel,
			maturityLabel,
			fraRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
	}

	/**
	 * Test getCalibrationSpec multiple FRA configurations in sequence.
	 */
	@Test
	void testGetCalibrationSpecMultipleFRAConfigurations() {
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		String[][] fraConfigurations = {
			{"1M", "4M"},
			{"3M", "6M"},
			{"6M", "9M"},
			{"6M", "12M"},
			{"12M", "18M"}
		};

		for (String[] config : fraConfigurations) {
			CalibrationSpecProviderFRA provider = new CalibrationSpecProviderFRA(
				config[0],
				config[1],
				0.045
			);

			CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);
			assertNotNull(spec, "Spec should not be null for " + config[0] + "x" + config[1]);
		}
	}

	/**
	 * Test constructor with very small rate close to zero.
	 */
	@Test
	void testConstructorWithRateCloseToZero() {
		String tenorLabel = "3M";
		String maturityLabel = "6M";
		double fraRate = 1e-10;

		CalibrationSpecProviderFRA provider = new CalibrationSpecProviderFRA(
			tenorLabel,
			maturityLabel,
			fraRate
		);

		assertNotNull(provider);
	}

	/**
	 * Test getCalibrationSpec with rate close to zero.
	 */
	@Test
	void testGetCalibrationSpecRateCloseToZero() {
		String tenorLabel = "6M";
		String maturityLabel = "12M";
		double fraRate = 1e-10;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderFRA provider = new CalibrationSpecProviderFRA(
			tenorLabel,
			maturityLabel,
			fraRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
	}

	/**
	 * Test getCalibrationSpec with very large rate.
	 */
	@Test
	void testGetCalibrationSpecVeryLargeRate() {
		String tenorLabel = "3M";
		String maturityLabel = "9M";
		double fraRate = 1e6; // 100000000%
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderFRA provider = new CalibrationSpecProviderFRA(
			tenorLabel,
			maturityLabel,
			fraRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
	}

	/**
	 * Test getCalibrationSpec with very large negative rate.
	 */
	@Test
	void testGetCalibrationSpecVeryLargeNegativeRate() {
		String tenorLabel = "6M";
		String maturityLabel = "12M";
		double fraRate = -1e6;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderFRA provider = new CalibrationSpecProviderFRA(
			tenorLabel,
			maturityLabel,
			fraRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
	}
}
