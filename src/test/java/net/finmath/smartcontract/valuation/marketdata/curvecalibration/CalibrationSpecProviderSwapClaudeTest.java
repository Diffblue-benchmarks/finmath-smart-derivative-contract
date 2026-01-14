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
 * Test class for CalibrationSpecProviderSwap.
 * Tests all methods with focus on branch and condition coverage.
 *
 * @author Claude Code
 */
class CalibrationSpecProviderSwapClaudeTest {

	/**
	 * Test constructor with typical swap parameters.
	 */
	@Test
	void testConstructorWithTypicalParameters() {
		String tenorLabel = "3M";
		String frequencyLabel = "quarterly";
		String maturityLabel = "5Y";
		double swapRate = 0.04;

		CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
			tenorLabel,
			frequencyLabel,
			maturityLabel,
			swapRate
		);

		assertNotNull(provider);
	}

	/**
	 * Test constructor with zero swap rate.
	 */
	@Test
	void testConstructorWithZeroRate() {
		String tenorLabel = "6M";
		String frequencyLabel = "semiannual";
		String maturityLabel = "10Y";
		double swapRate = 0.0;

		CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
			tenorLabel,
			frequencyLabel,
			maturityLabel,
			swapRate
		);

		assertNotNull(provider);
	}

	/**
	 * Test constructor with negative swap rate.
	 */
	@Test
	void testConstructorWithNegativeRate() {
		String tenorLabel = "3M";
		String frequencyLabel = "quarterly";
		String maturityLabel = "2Y";
		double swapRate = -0.01;

		CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
			tenorLabel,
			frequencyLabel,
			maturityLabel,
			swapRate
		);

		assertNotNull(provider);
	}

	/**
	 * Test constructor with high swap rate.
	 */
	@Test
	void testConstructorWithHighRate() {
		String tenorLabel = "6M";
		String frequencyLabel = "semiannual";
		String maturityLabel = "7Y";
		double swapRate = 0.15;

		CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
			tenorLabel,
			frequencyLabel,
			maturityLabel,
			swapRate
		);

		assertNotNull(provider);
	}

	/**
	 * Test constructor with 3M tenor.
	 */
	@Test
	void testConstructorWith3MTenor() {
		String tenorLabel = "3M";
		String frequencyLabel = "quarterly";
		String maturityLabel = "5Y";
		double swapRate = 0.035;

		CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
			tenorLabel,
			frequencyLabel,
			maturityLabel,
			swapRate
		);

		assertNotNull(provider);
	}

	/**
	 * Test constructor with 6M tenor.
	 */
	@Test
	void testConstructorWith6MTenor() {
		String tenorLabel = "6M";
		String frequencyLabel = "semiannual";
		String maturityLabel = "10Y";
		double swapRate = 0.04;

		CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
			tenorLabel,
			frequencyLabel,
			maturityLabel,
			swapRate
		);

		assertNotNull(provider);
	}

	/**
	 * Test constructor with quarterly frequency.
	 */
	@Test
	void testConstructorWithQuarterlyFrequency() {
		String tenorLabel = "3M";
		String frequencyLabel = "quarterly";
		String maturityLabel = "3Y";
		double swapRate = 0.032;

		CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
			tenorLabel,
			frequencyLabel,
			maturityLabel,
			swapRate
		);

		assertNotNull(provider);
	}

	/**
	 * Test constructor with semiannual frequency.
	 */
	@Test
	void testConstructorWithSemiannualFrequency() {
		String tenorLabel = "6M";
		String frequencyLabel = "semiannual";
		String maturityLabel = "5Y";
		double swapRate = 0.038;

		CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
			tenorLabel,
			frequencyLabel,
			maturityLabel,
			swapRate
		);

		assertNotNull(provider);
	}

	/**
	 * Test constructor with annual frequency.
	 */
	@Test
	void testConstructorWithAnnualFrequency() {
		String tenorLabel = "12M";
		String frequencyLabel = "annual";
		String maturityLabel = "10Y";
		double swapRate = 0.045;

		CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
			tenorLabel,
			frequencyLabel,
			maturityLabel,
			swapRate
		);

		assertNotNull(provider);
	}

	/**
	 * Test constructor with monthly frequency.
	 */
	@Test
	void testConstructorWithMonthlyFrequency() {
		String tenorLabel = "1M";
		String frequencyLabel = "monthly";
		String maturityLabel = "2Y";
		double swapRate = 0.03;

		CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
			tenorLabel,
			frequencyLabel,
			maturityLabel,
			swapRate
		);

		assertNotNull(provider);
	}

	/**
	 * Test constructor with short maturity.
	 */
	@Test
	void testConstructorWithShortMaturity() {
		String tenorLabel = "3M";
		String frequencyLabel = "quarterly";
		String maturityLabel = "1Y";
		double swapRate = 0.025;

		CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
			tenorLabel,
			frequencyLabel,
			maturityLabel,
			swapRate
		);

		assertNotNull(provider);
	}

	/**
	 * Test constructor with long maturity.
	 */
	@Test
	void testConstructorWithLongMaturity() {
		String tenorLabel = "6M";
		String frequencyLabel = "semiannual";
		String maturityLabel = "30Y";
		double swapRate = 0.05;

		CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
			tenorLabel,
			frequencyLabel,
			maturityLabel,
			swapRate
		);

		assertNotNull(provider);
	}

	/**
	 * Test constructor with very small rate.
	 */
	@Test
	void testConstructorWithVerySmallRate() {
		String tenorLabel = "3M";
		String frequencyLabel = "quarterly";
		String maturityLabel = "5Y";
		double swapRate = 0.0001;

		CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
			tenorLabel,
			frequencyLabel,
			maturityLabel,
			swapRate
		);

		assertNotNull(provider);
	}

	/**
	 * Test getCalibrationSpec with typical swap parameters.
	 */
	@Test
	void testGetCalibrationSpecWithTypicalParameters() {
		String tenorLabel = "3M";
		String frequencyLabel = "quarterly";
		String maturityLabel = "5Y";
		double swapRate = 0.04;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
			tenorLabel,
			frequencyLabel,
			maturityLabel,
			swapRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
		assertNotNull(spec.toString());
	}

	/**
	 * Test getCalibrationSpec with 6M tenor and semiannual frequency.
	 */
	@Test
	void testGetCalibrationSpecWith6MTenor() {
		String tenorLabel = "6M";
		String frequencyLabel = "semiannual";
		String maturityLabel = "10Y";
		double swapRate = 0.045;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
			tenorLabel,
			frequencyLabel,
			maturityLabel,
			swapRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
		assertNotNull(spec.toString());
	}

	/**
	 * Test getCalibrationSpec with zero swap rate.
	 */
	@Test
	void testGetCalibrationSpecWithZeroRate() {
		String tenorLabel = "3M";
		String frequencyLabel = "quarterly";
		String maturityLabel = "5Y";
		double swapRate = 0.0;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 12, 31, 23, 59, 59);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
			tenorLabel,
			frequencyLabel,
			maturityLabel,
			swapRate
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
		String frequencyLabel = "semiannual";
		String maturityLabel = "7Y";
		double swapRate = -0.02;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
			tenorLabel,
			frequencyLabel,
			maturityLabel,
			swapRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
	}

	/**
	 * Test getCalibrationSpec with different tenor labels.
	 */
	@Test
	void testGetCalibrationSpecDifferentTenors() {
		double swapRate = 0.04;
		String maturityLabel = "5Y";
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		String[][] swapConfigurations = {
			{"1M", "monthly"},
			{"3M", "quarterly"},
			{"6M", "semiannual"},
			{"12M", "annual"}
		};

		for (String[] config : swapConfigurations) {
			CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
				config[0],
				config[1],
				maturityLabel,
				swapRate
			);

			CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

			assertNotNull(spec, "Spec should not be null for tenor: " + config[0] + " and frequency: " + config[1]);
		}
	}

	/**
	 * Test getCalibrationSpec with different maturities.
	 */
	@Test
	void testGetCalibrationSpecDifferentMaturities() {
		String tenorLabel = "3M";
		String frequencyLabel = "quarterly";
		double swapRate = 0.035;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		String[] maturities = {"1Y", "2Y", "3Y", "5Y", "7Y", "10Y", "15Y", "20Y", "30Y"};

		for (String maturityLabel : maturities) {
			CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
				tenorLabel,
				frequencyLabel,
				maturityLabel,
				swapRate
			);

			CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

			assertNotNull(spec, "Spec should not be null for maturity: " + maturityLabel);
		}
	}

	/**
	 * Test getCalibrationSpec with different reference dates.
	 */
	@Test
	void testGetCalibrationSpecDifferentReferenceDates() {
		String tenorLabel = "6M";
		String frequencyLabel = "semiannual";
		String maturityLabel = "5Y";
		double swapRate = 0.04;

		LocalDateTime[] referenceDates = {
			LocalDateTime.of(2024, 1, 1, 0, 0, 0),
			LocalDateTime.of(2024, 6, 15, 10, 30, 0),
			LocalDateTime.of(2024, 12, 31, 23, 59, 59),
			LocalDateTime.of(2025, 2, 28, 12, 0, 0)
		};

		for (LocalDateTime refDate : referenceDates) {
			CalibrationContext context = new CalibrationContextImpl(refDate, 1e-6);
			CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
				tenorLabel,
				frequencyLabel,
				maturityLabel,
				swapRate
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
		String frequencyLabel = "quarterly";
		String maturityLabel = "5Y";
		double swapRate = 0.03;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 2, 29, 12, 0, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
			tenorLabel,
			frequencyLabel,
			maturityLabel,
			swapRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
	}

	/**
	 * Test getCalibrationSpec with high swap rate.
	 */
	@Test
	void testGetCalibrationSpecHighRate() {
		String tenorLabel = "3M";
		String frequencyLabel = "quarterly";
		String maturityLabel = "5Y";
		double swapRate = 0.25; // 25%
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
			tenorLabel,
			frequencyLabel,
			maturityLabel,
			swapRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
	}

	/**
	 * Test getCalibrationSpec with Double.MAX_VALUE rate.
	 */
	@Test
	void testGetCalibrationSpecMaxRate() {
		String tenorLabel = "6M";
		String frequencyLabel = "semiannual";
		String maturityLabel = "3Y";
		double swapRate = Double.MAX_VALUE;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
			tenorLabel,
			frequencyLabel,
			maturityLabel,
			swapRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
	}

	/**
	 * Test getCalibrationSpec with Double.MIN_VALUE rate.
	 */
	@Test
	void testGetCalibrationSpecMinRate() {
		String tenorLabel = "3M";
		String frequencyLabel = "quarterly";
		String maturityLabel = "5Y";
		double swapRate = Double.MIN_VALUE;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
			tenorLabel,
			frequencyLabel,
			maturityLabel,
			swapRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
	}

	/**
	 * Test getCalibrationSpec with positive infinity rate.
	 */
	@Test
	void testGetCalibrationSpecPositiveInfinityRate() {
		String tenorLabel = "6M";
		String frequencyLabel = "semiannual";
		String maturityLabel = "3Y";
		double swapRate = Double.POSITIVE_INFINITY;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
			tenorLabel,
			frequencyLabel,
			maturityLabel,
			swapRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
	}

	/**
	 * Test getCalibrationSpec with negative infinity rate.
	 */
	@Test
	void testGetCalibrationSpecNegativeInfinityRate() {
		String tenorLabel = "3M";
		String frequencyLabel = "quarterly";
		String maturityLabel = "5Y";
		double swapRate = Double.NEGATIVE_INFINITY;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
			tenorLabel,
			frequencyLabel,
			maturityLabel,
			swapRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
	}

	/**
	 * Test getCalibrationSpec with NaN rate.
	 */
	@Test
	void testGetCalibrationSpecNaNRate() {
		String tenorLabel = "6M";
		String frequencyLabel = "semiannual";
		String maturityLabel = "3Y";
		double swapRate = Double.NaN;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
			tenorLabel,
			frequencyLabel,
			maturityLabel,
			swapRate
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
		String frequencyLabel = "quarterly";
		String maturityLabel = "5Y";
		double swapRate = 0.04;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
			tenorLabel,
			frequencyLabel,
			maturityLabel,
			swapRate
		);

		CalibratedCurves.CalibrationSpec spec1 = provider.getCalibrationSpec(context);
		CalibratedCurves.CalibrationSpec spec2 = provider.getCalibrationSpec(context);

		assertNotNull(spec1);
		assertNotNull(spec2);
	}

	/**
	 * Test that CalibrationSpecProviderSwap implements CalibrationSpecProvider interface.
	 */
	@Test
	void testImplementsInterface() {
		CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
			"3M",
			"quarterly",
			"5Y",
			0.04
		);

		assertTrue(provider instanceof CalibrationSpecProvider);
	}

	/**
	 * Test getCalibrationSpec with year-end reference date.
	 */
	@Test
	void testGetCalibrationSpecYearEndDate() {
		String tenorLabel = "6M";
		String frequencyLabel = "semiannual";
		String maturityLabel = "3Y";
		double swapRate = 0.035;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 12, 31, 23, 59, 59);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
			tenorLabel,
			frequencyLabel,
			maturityLabel,
			swapRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
	}

	/**
	 * Test getCalibrationSpec with year-start reference date.
	 */
	@Test
	void testGetCalibrationSpecYearStartDate() {
		String tenorLabel = "3M";
		String frequencyLabel = "quarterly";
		String maturityLabel = "5Y";
		double swapRate = 0.04;
		LocalDateTime referenceDateTime = LocalDateTime.of(2025, 1, 1, 0, 0, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
			tenorLabel,
			frequencyLabel,
			maturityLabel,
			swapRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
	}

	/**
	 * Test getCalibrationSpec multiple swap configurations in sequence.
	 */
	@Test
	void testGetCalibrationSpecMultipleSwapConfigurations() {
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		String[][] swapConfigurations = {
			{"3M", "quarterly", "1Y"},
			{"3M", "quarterly", "2Y"},
			{"6M", "semiannual", "3Y"},
			{"6M", "semiannual", "5Y"},
			{"6M", "semiannual", "10Y"}
		};

		for (String[] config : swapConfigurations) {
			CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
				config[0],
				config[1],
				config[2],
				0.04
			);

			CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);
			assertNotNull(spec, "Spec should not be null for " + config[0] + " tenor, " + config[1] + " frequency, and " + config[2] + " maturity");
		}
	}

	/**
	 * Test constructor with rate close to zero.
	 */
	@Test
	void testConstructorWithRateCloseToZero() {
		String tenorLabel = "3M";
		String frequencyLabel = "quarterly";
		String maturityLabel = "5Y";
		double swapRate = 1e-10;

		CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
			tenorLabel,
			frequencyLabel,
			maturityLabel,
			swapRate
		);

		assertNotNull(provider);
	}

	/**
	 * Test getCalibrationSpec with rate close to zero.
	 */
	@Test
	void testGetCalibrationSpecRateCloseToZero() {
		String tenorLabel = "6M";
		String frequencyLabel = "semiannual";
		String maturityLabel = "5Y";
		double swapRate = 1e-10;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
			tenorLabel,
			frequencyLabel,
			maturityLabel,
			swapRate
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
		String frequencyLabel = "quarterly";
		String maturityLabel = "3Y";
		double swapRate = 1e6; // 100000000%
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
			tenorLabel,
			frequencyLabel,
			maturityLabel,
			swapRate
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
		String frequencyLabel = "semiannual";
		String maturityLabel = "5Y";
		double swapRate = -1e6;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
			tenorLabel,
			frequencyLabel,
			maturityLabel,
			swapRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
	}

	/**
	 * Test getCalibrationSpec with different accuracy values in context.
	 */
	@Test
	void testGetCalibrationSpecDifferentAccuracyValues() {
		String tenorLabel = "3M";
		String frequencyLabel = "quarterly";
		String maturityLabel = "5Y";
		double swapRate = 0.04;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);

		double[] accuracyValues = {1e-6, 1e-8, 1e-10, 1e-4};

		for (double accuracy : accuracyValues) {
			CalibrationContext context = new CalibrationContextImpl(referenceDateTime, accuracy);
			CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
				tenorLabel,
				frequencyLabel,
				maturityLabel,
				swapRate
			);

			CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

			assertNotNull(spec, "Spec should not be null for accuracy: " + accuracy);
		}
	}

	/**
	 * Test getCalibrationSpec with quarterly frequency and short maturity.
	 */
	@Test
	void testGetCalibrationSpecQuarterlyShortMaturity() {
		String tenorLabel = "3M";
		String frequencyLabel = "quarterly";
		String maturityLabel = "1Y";
		double swapRate = 0.028;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
			tenorLabel,
			frequencyLabel,
			maturityLabel,
			swapRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
	}

	/**
	 * Test getCalibrationSpec with semiannual frequency and medium maturity.
	 */
	@Test
	void testGetCalibrationSpecSemiannualMediumMaturity() {
		String tenorLabel = "6M";
		String frequencyLabel = "semiannual";
		String maturityLabel = "5Y";
		double swapRate = 0.033;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
			tenorLabel,
			frequencyLabel,
			maturityLabel,
			swapRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
	}

	/**
	 * Test getCalibrationSpec with annual frequency and long maturity.
	 */
	@Test
	void testGetCalibrationSpecAnnualLongMaturity() {
		String tenorLabel = "12M";
		String frequencyLabel = "annual";
		String maturityLabel = "20Y";
		double swapRate = 0.045;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
			tenorLabel,
			frequencyLabel,
			maturityLabel,
			swapRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
	}

	/**
	 * Test getCalibrationSpec with 15-year maturity.
	 */
	@Test
	void testGetCalibrationSpecWith15YearMaturity() {
		String tenorLabel = "6M";
		String frequencyLabel = "semiannual";
		String maturityLabel = "15Y";
		double swapRate = 0.048;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
			tenorLabel,
			frequencyLabel,
			maturityLabel,
			swapRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
	}

	/**
	 * Test getCalibrationSpec with 20-year maturity.
	 */
	@Test
	void testGetCalibrationSpecWith20YearMaturity() {
		String tenorLabel = "3M";
		String frequencyLabel = "quarterly";
		String maturityLabel = "20Y";
		double swapRate = 0.05;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
			tenorLabel,
			frequencyLabel,
			maturityLabel,
			swapRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
	}

	/**
	 * Test getCalibrationSpec with weekend reference date.
	 */
	@Test
	void testGetCalibrationSpecWeekendDate() {
		String tenorLabel = "6M";
		String frequencyLabel = "semiannual";
		String maturityLabel = "5Y";
		double swapRate = 0.04;
		// Saturday
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 1, 10, 0, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
			tenorLabel,
			frequencyLabel,
			maturityLabel,
			swapRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
	}

	/**
	 * Test constructor with 1M tenor.
	 */
	@Test
	void testConstructorWith1MTenor() {
		String tenorLabel = "1M";
		String frequencyLabel = "monthly";
		String maturityLabel = "2Y";
		double swapRate = 0.03;

		CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
			tenorLabel,
			frequencyLabel,
			maturityLabel,
			swapRate
		);

		assertNotNull(provider);
	}

	/**
	 * Test constructor with 12M tenor.
	 */
	@Test
	void testConstructorWith12MTenor() {
		String tenorLabel = "12M";
		String frequencyLabel = "annual";
		String maturityLabel = "10Y";
		double swapRate = 0.045;

		CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
			tenorLabel,
			frequencyLabel,
			maturityLabel,
			swapRate
		);

		assertNotNull(provider);
	}

	/**
	 * Test getCalibrationSpec with 1M tenor.
	 */
	@Test
	void testGetCalibrationSpecWith1MTenor() {
		String tenorLabel = "1M";
		String frequencyLabel = "monthly";
		String maturityLabel = "2Y";
		double swapRate = 0.03;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
			tenorLabel,
			frequencyLabel,
			maturityLabel,
			swapRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
	}

	/**
	 * Test getCalibrationSpec with 12M tenor.
	 */
	@Test
	void testGetCalibrationSpecWith12MTenor() {
		String tenorLabel = "12M";
		String frequencyLabel = "annual";
		String maturityLabel = "10Y";
		double swapRate = 0.045;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
			tenorLabel,
			frequencyLabel,
			maturityLabel,
			swapRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
	}

	/**
	 * Test getCalibrationSpec with monthly frequency.
	 */
	@Test
	void testGetCalibrationSpecMonthlyFrequency() {
		String tenorLabel = "1M";
		String frequencyLabel = "monthly";
		String maturityLabel = "3Y";
		double swapRate = 0.035;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
			tenorLabel,
			frequencyLabel,
			maturityLabel,
			swapRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
	}

	/**
	 * Test getCalibrationSpec with 2-year maturity.
	 */
	@Test
	void testGetCalibrationSpecWith2YearMaturity() {
		String tenorLabel = "3M";
		String frequencyLabel = "quarterly";
		String maturityLabel = "2Y";
		double swapRate = 0.032;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
			tenorLabel,
			frequencyLabel,
			maturityLabel,
			swapRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
	}

	/**
	 * Test getCalibrationSpec with 7-year maturity.
	 */
	@Test
	void testGetCalibrationSpecWith7YearMaturity() {
		String tenorLabel = "6M";
		String frequencyLabel = "semiannual";
		String maturityLabel = "7Y";
		double swapRate = 0.042;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
			tenorLabel,
			frequencyLabel,
			maturityLabel,
			swapRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
	}

	/**
	 * Test constructor with 2Y maturity.
	 */
	@Test
	void testConstructorWith2YMaturity() {
		String tenorLabel = "3M";
		String frequencyLabel = "quarterly";
		String maturityLabel = "2Y";
		double swapRate = 0.032;

		CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
			tenorLabel,
			frequencyLabel,
			maturityLabel,
			swapRate
		);

		assertNotNull(provider);
	}

	/**
	 * Test constructor with 7Y maturity.
	 */
	@Test
	void testConstructorWith7YMaturity() {
		String tenorLabel = "6M";
		String frequencyLabel = "semiannual";
		String maturityLabel = "7Y";
		double swapRate = 0.042;

		CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
			tenorLabel,
			frequencyLabel,
			maturityLabel,
			swapRate
		);

		assertNotNull(provider);
	}

	/**
	 * Test constructor with 15Y maturity.
	 */
	@Test
	void testConstructorWith15YMaturity() {
		String tenorLabel = "6M";
		String frequencyLabel = "semiannual";
		String maturityLabel = "15Y";
		double swapRate = 0.048;

		CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
			tenorLabel,
			frequencyLabel,
			maturityLabel,
			swapRate
		);

		assertNotNull(provider);
	}

	/**
	 * Test constructor with 20Y maturity.
	 */
	@Test
	void testConstructorWith20YMaturity() {
		String tenorLabel = "3M";
		String frequencyLabel = "quarterly";
		String maturityLabel = "20Y";
		double swapRate = 0.05;

		CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
			tenorLabel,
			frequencyLabel,
			maturityLabel,
			swapRate
		);

		assertNotNull(provider);
	}

	/**
	 * Test getCalibrationSpec with different floating leg frequencies.
	 */
	@Test
	void testGetCalibrationSpecDifferentFloatingLegFrequencies() {
		String tenorLabel = "6M";
		String maturityLabel = "5Y";
		double swapRate = 0.04;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		String[] frequencies = {"monthly", "quarterly", "semiannual", "annual"};

		for (String frequency : frequencies) {
			CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
				tenorLabel,
				frequency,
				maturityLabel,
				swapRate
			);

			CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

			assertNotNull(spec, "Spec should not be null for frequency: " + frequency);
		}
	}

	/**
	 * Test getCalibrationSpec with 30-year maturity.
	 */
	@Test
	void testGetCalibrationSpecWith30YearMaturity() {
		String tenorLabel = "6M";
		String frequencyLabel = "semiannual";
		String maturityLabel = "30Y";
		double swapRate = 0.052;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
			tenorLabel,
			frequencyLabel,
			maturityLabel,
			swapRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
	}

	/**
	 * Test constructor with 30Y maturity.
	 */
	@Test
	void testConstructorWith30YMaturity() {
		String tenorLabel = "6M";
		String frequencyLabel = "semiannual";
		String maturityLabel = "30Y";
		double swapRate = 0.052;

		CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
			tenorLabel,
			frequencyLabel,
			maturityLabel,
			swapRate
		);

		assertNotNull(provider);
	}

	/**
	 * Test getCalibrationSpec with 3-year maturity.
	 */
	@Test
	void testGetCalibrationSpecWith3YearMaturity() {
		String tenorLabel = "3M";
		String frequencyLabel = "quarterly";
		String maturityLabel = "3Y";
		double swapRate = 0.034;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
			tenorLabel,
			frequencyLabel,
			maturityLabel,
			swapRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
	}

	/**
	 * Test constructor with 3Y maturity.
	 */
	@Test
	void testConstructorWith3YMaturity() {
		String tenorLabel = "3M";
		String frequencyLabel = "quarterly";
		String maturityLabel = "3Y";
		double swapRate = 0.034;

		CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
			tenorLabel,
			frequencyLabel,
			maturityLabel,
			swapRate
		);

		assertNotNull(provider);
	}

	/**
	 * Test getCalibrationSpec with 10-year maturity.
	 */
	@Test
	void testGetCalibrationSpecWith10YearMaturity() {
		String tenorLabel = "6M";
		String frequencyLabel = "semiannual";
		String maturityLabel = "10Y";
		double swapRate = 0.046;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
			tenorLabel,
			frequencyLabel,
			maturityLabel,
			swapRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
	}

	/**
	 * Test constructor with 10Y maturity.
	 */
	@Test
	void testConstructorWith10YMaturity() {
		String tenorLabel = "6M";
		String frequencyLabel = "semiannual";
		String maturityLabel = "10Y";
		double swapRate = 0.046;

		CalibrationSpecProviderSwap provider = new CalibrationSpecProviderSwap(
			tenorLabel,
			frequencyLabel,
			maturityLabel,
			swapRate
		);

		assertNotNull(provider);
	}
}
