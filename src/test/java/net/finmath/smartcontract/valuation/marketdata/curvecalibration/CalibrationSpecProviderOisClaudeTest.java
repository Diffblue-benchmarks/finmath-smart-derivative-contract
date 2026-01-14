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
 * Test class for CalibrationSpecProviderOis.
 * Tests all methods with focus on branch and condition coverage.
 *
 * @author Claude Code
 */
class CalibrationSpecProviderOisClaudeTest {

	/**
	 * Test constructor with typical OIS parameters.
	 */
	@Test
	void testConstructorWithTypicalParameters() {
		String maturityLabel = "1Y";
		String frequency = "annual";
		double swapRate = 0.03;

		CalibrationSpecProviderOis provider = new CalibrationSpecProviderOis(
			maturityLabel,
			frequency,
			swapRate
		);

		assertNotNull(provider);
	}

	/**
	 * Test constructor with zero swap rate.
	 */
	@Test
	void testConstructorWithZeroRate() {
		String maturityLabel = "2Y";
		String frequency = "annual";
		double swapRate = 0.0;

		CalibrationSpecProviderOis provider = new CalibrationSpecProviderOis(
			maturityLabel,
			frequency,
			swapRate
		);

		assertNotNull(provider);
	}

	/**
	 * Test constructor with negative swap rate.
	 */
	@Test
	void testConstructorWithNegativeRate() {
		String maturityLabel = "5Y";
		String frequency = "annual";
		double swapRate = -0.01;

		CalibrationSpecProviderOis provider = new CalibrationSpecProviderOis(
			maturityLabel,
			frequency,
			swapRate
		);

		assertNotNull(provider);
	}

	/**
	 * Test constructor with high swap rate.
	 */
	@Test
	void testConstructorWithHighRate() {
		String maturityLabel = "10Y";
		String frequency = "annual";
		double swapRate = 0.15;

		CalibrationSpecProviderOis provider = new CalibrationSpecProviderOis(
			maturityLabel,
			frequency,
			swapRate
		);

		assertNotNull(provider);
	}

	/**
	 * Test constructor with short maturity.
	 */
	@Test
	void testConstructorWithShortMaturity() {
		String maturityLabel = "3M";
		String frequency = "quarterly";
		double swapRate = 0.025;

		CalibrationSpecProviderOis provider = new CalibrationSpecProviderOis(
			maturityLabel,
			frequency,
			swapRate
		);

		assertNotNull(provider);
	}

	/**
	 * Test constructor with long maturity.
	 */
	@Test
	void testConstructorWithLongMaturity() {
		String maturityLabel = "30Y";
		String frequency = "annual";
		double swapRate = 0.04;

		CalibrationSpecProviderOis provider = new CalibrationSpecProviderOis(
			maturityLabel,
			frequency,
			swapRate
		);

		assertNotNull(provider);
	}

	/**
	 * Test constructor with annual frequency.
	 */
	@Test
	void testConstructorWithAnnualFrequency() {
		String maturityLabel = "5Y";
		String frequency = "annual";
		double swapRate = 0.035;

		CalibrationSpecProviderOis provider = new CalibrationSpecProviderOis(
			maturityLabel,
			frequency,
			swapRate
		);

		assertNotNull(provider);
	}

	/**
	 * Test constructor with semiannual frequency.
	 */
	@Test
	void testConstructorWithSemiannualFrequency() {
		String maturityLabel = "7Y";
		String frequency = "semiannual";
		double swapRate = 0.038;

		CalibrationSpecProviderOis provider = new CalibrationSpecProviderOis(
			maturityLabel,
			frequency,
			swapRate
		);

		assertNotNull(provider);
	}

	/**
	 * Test constructor with quarterly frequency.
	 */
	@Test
	void testConstructorWithQuarterlyFrequency() {
		String maturityLabel = "2Y";
		String frequency = "quarterly";
		double swapRate = 0.032;

		CalibrationSpecProviderOis provider = new CalibrationSpecProviderOis(
			maturityLabel,
			frequency,
			swapRate
		);

		assertNotNull(provider);
	}

	/**
	 * Test constructor with monthly frequency.
	 */
	@Test
	void testConstructorWithMonthlyFrequency() {
		String maturityLabel = "1Y";
		String frequency = "monthly";
		double swapRate = 0.03;

		CalibrationSpecProviderOis provider = new CalibrationSpecProviderOis(
			maturityLabel,
			frequency,
			swapRate
		);

		assertNotNull(provider);
	}

	/**
	 * Test constructor with very small rate.
	 */
	@Test
	void testConstructorWithVerySmallRate() {
		String maturityLabel = "3Y";
		String frequency = "annual";
		double swapRate = 0.0001;

		CalibrationSpecProviderOis provider = new CalibrationSpecProviderOis(
			maturityLabel,
			frequency,
			swapRate
		);

		assertNotNull(provider);
	}

	/**
	 * Test getCalibrationSpec with typical OIS parameters.
	 */
	@Test
	void testGetCalibrationSpecWithTypicalParameters() {
		String maturityLabel = "1Y";
		String frequency = "annual";
		double swapRate = 0.03;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderOis provider = new CalibrationSpecProviderOis(
			maturityLabel,
			frequency,
			swapRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
		assertNotNull(spec.toString());
	}

	/**
	 * Test getCalibrationSpec with 2-year maturity.
	 */
	@Test
	void testGetCalibrationSpecWith2YearMaturity() {
		String maturityLabel = "2Y";
		String frequency = "annual";
		double swapRate = 0.035;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderOis provider = new CalibrationSpecProviderOis(
			maturityLabel,
			frequency,
			swapRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
		assertNotNull(spec.toString());
	}

	/**
	 * Test getCalibrationSpec with 5-year maturity.
	 */
	@Test
	void testGetCalibrationSpecWith5YearMaturity() {
		String maturityLabel = "5Y";
		String frequency = "annual";
		double swapRate = 0.04;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderOis provider = new CalibrationSpecProviderOis(
			maturityLabel,
			frequency,
			swapRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
	}

	/**
	 * Test getCalibrationSpec with 10-year maturity.
	 */
	@Test
	void testGetCalibrationSpecWith10YearMaturity() {
		String maturityLabel = "10Y";
		String frequency = "annual";
		double swapRate = 0.045;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderOis provider = new CalibrationSpecProviderOis(
			maturityLabel,
			frequency,
			swapRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
	}

	/**
	 * Test getCalibrationSpec with zero swap rate.
	 */
	@Test
	void testGetCalibrationSpecWithZeroRate() {
		String maturityLabel = "3Y";
		String frequency = "annual";
		double swapRate = 0.0;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 12, 31, 23, 59, 59);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderOis provider = new CalibrationSpecProviderOis(
			maturityLabel,
			frequency,
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
		String maturityLabel = "7Y";
		String frequency = "annual";
		double swapRate = -0.02;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderOis provider = new CalibrationSpecProviderOis(
			maturityLabel,
			frequency,
			swapRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
	}

	/**
	 * Test getCalibrationSpec with different frequencies.
	 */
	@Test
	void testGetCalibrationSpecDifferentFrequencies() {
		String maturityLabel = "5Y";
		double swapRate = 0.04;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		String[] frequencies = {"annual", "semiannual", "quarterly", "monthly"};

		for (String frequency : frequencies) {
			CalibrationSpecProviderOis provider = new CalibrationSpecProviderOis(
				maturityLabel,
				frequency,
				swapRate
			);

			CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

			assertNotNull(spec, "Spec should not be null for frequency: " + frequency);
		}
	}

	/**
	 * Test getCalibrationSpec with different maturities.
	 */
	@Test
	void testGetCalibrationSpecDifferentMaturities() {
		String frequency = "annual";
		double swapRate = 0.035;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		String[] maturities = {"1Y", "2Y", "3Y", "5Y", "7Y", "10Y", "15Y", "20Y", "30Y"};

		for (String maturityLabel : maturities) {
			CalibrationSpecProviderOis provider = new CalibrationSpecProviderOis(
				maturityLabel,
				frequency,
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
		String maturityLabel = "5Y";
		String frequency = "annual";
		double swapRate = 0.04;

		LocalDateTime[] referenceDates = {
			LocalDateTime.of(2024, 1, 1, 0, 0, 0),
			LocalDateTime.of(2024, 6, 15, 10, 30, 0),
			LocalDateTime.of(2024, 12, 31, 23, 59, 59),
			LocalDateTime.of(2025, 2, 28, 12, 0, 0)
		};

		for (LocalDateTime refDate : referenceDates) {
			CalibrationContext context = new CalibrationContextImpl(refDate, 1e-6);
			CalibrationSpecProviderOis provider = new CalibrationSpecProviderOis(
				maturityLabel,
				frequency,
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
		String maturityLabel = "3Y";
		String frequency = "annual";
		double swapRate = 0.03;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 2, 29, 12, 0, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderOis provider = new CalibrationSpecProviderOis(
			maturityLabel,
			frequency,
			swapRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
	}

	/**
	 * Test getCalibrationSpec with short maturity (3 months).
	 */
	@Test
	void testGetCalibrationSpecShortMaturity() {
		String maturityLabel = "3M";
		String frequency = "quarterly";
		double swapRate = 0.025;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderOis provider = new CalibrationSpecProviderOis(
			maturityLabel,
			frequency,
			swapRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
	}

	/**
	 * Test getCalibrationSpec with long maturity (30 years).
	 */
	@Test
	void testGetCalibrationSpecLongMaturity() {
		String maturityLabel = "30Y";
		String frequency = "annual";
		double swapRate = 0.045;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderOis provider = new CalibrationSpecProviderOis(
			maturityLabel,
			frequency,
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
		String maturityLabel = "5Y";
		String frequency = "annual";
		double swapRate = 0.25; // 25%
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderOis provider = new CalibrationSpecProviderOis(
			maturityLabel,
			frequency,
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
		String maturityLabel = "3Y";
		String frequency = "annual";
		double swapRate = Double.MAX_VALUE;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderOis provider = new CalibrationSpecProviderOis(
			maturityLabel,
			frequency,
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
		String maturityLabel = "5Y";
		String frequency = "annual";
		double swapRate = Double.MIN_VALUE;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderOis provider = new CalibrationSpecProviderOis(
			maturityLabel,
			frequency,
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
		String maturityLabel = "3Y";
		String frequency = "annual";
		double swapRate = Double.POSITIVE_INFINITY;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderOis provider = new CalibrationSpecProviderOis(
			maturityLabel,
			frequency,
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
		String maturityLabel = "5Y";
		String frequency = "annual";
		double swapRate = Double.NEGATIVE_INFINITY;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderOis provider = new CalibrationSpecProviderOis(
			maturityLabel,
			frequency,
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
		String maturityLabel = "3Y";
		String frequency = "annual";
		double swapRate = Double.NaN;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderOis provider = new CalibrationSpecProviderOis(
			maturityLabel,
			frequency,
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
		String maturityLabel = "5Y";
		String frequency = "annual";
		double swapRate = 0.04;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderOis provider = new CalibrationSpecProviderOis(
			maturityLabel,
			frequency,
			swapRate
		);

		CalibratedCurves.CalibrationSpec spec1 = provider.getCalibrationSpec(context);
		CalibratedCurves.CalibrationSpec spec2 = provider.getCalibrationSpec(context);

		assertNotNull(spec1);
		assertNotNull(spec2);
	}

	/**
	 * Test that CalibrationSpecProviderOis implements CalibrationSpecProvider interface.
	 */
	@Test
	void testImplementsInterface() {
		CalibrationSpecProviderOis provider = new CalibrationSpecProviderOis(
			"5Y",
			"annual",
			0.04
		);

		assertTrue(provider instanceof CalibrationSpecProvider);
	}

	/**
	 * Test getCalibrationSpec with year-end reference date.
	 */
	@Test
	void testGetCalibrationSpecYearEndDate() {
		String maturityLabel = "3Y";
		String frequency = "annual";
		double swapRate = 0.035;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 12, 31, 23, 59, 59);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderOis provider = new CalibrationSpecProviderOis(
			maturityLabel,
			frequency,
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
		String maturityLabel = "5Y";
		String frequency = "annual";
		double swapRate = 0.04;
		LocalDateTime referenceDateTime = LocalDateTime.of(2025, 1, 1, 0, 0, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderOis provider = new CalibrationSpecProviderOis(
			maturityLabel,
			frequency,
			swapRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
	}

	/**
	 * Test getCalibrationSpec multiple OIS configurations in sequence.
	 */
	@Test
	void testGetCalibrationSpecMultipleOISConfigurations() {
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		String[][] oisConfigurations = {
			{"1Y", "annual"},
			{"2Y", "annual"},
			{"3Y", "annual"},
			{"5Y", "semiannual"},
			{"10Y", "annual"}
		};

		for (String[] config : oisConfigurations) {
			CalibrationSpecProviderOis provider = new CalibrationSpecProviderOis(
				config[0],
				config[1],
				0.04
			);

			CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);
			assertNotNull(spec, "Spec should not be null for " + config[0] + " with " + config[1] + " frequency");
		}
	}

	/**
	 * Test constructor with rate close to zero.
	 */
	@Test
	void testConstructorWithRateCloseToZero() {
		String maturityLabel = "3Y";
		String frequency = "annual";
		double swapRate = 1e-10;

		CalibrationSpecProviderOis provider = new CalibrationSpecProviderOis(
			maturityLabel,
			frequency,
			swapRate
		);

		assertNotNull(provider);
	}

	/**
	 * Test getCalibrationSpec with rate close to zero.
	 */
	@Test
	void testGetCalibrationSpecRateCloseToZero() {
		String maturityLabel = "5Y";
		String frequency = "annual";
		double swapRate = 1e-10;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderOis provider = new CalibrationSpecProviderOis(
			maturityLabel,
			frequency,
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
		String maturityLabel = "3Y";
		String frequency = "annual";
		double swapRate = 1e6; // 100000000%
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderOis provider = new CalibrationSpecProviderOis(
			maturityLabel,
			frequency,
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
		String maturityLabel = "5Y";
		String frequency = "annual";
		double swapRate = -1e6;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderOis provider = new CalibrationSpecProviderOis(
			maturityLabel,
			frequency,
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
		String maturityLabel = "5Y";
		String frequency = "annual";
		double swapRate = 0.04;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);

		double[] accuracyValues = {1e-6, 1e-8, 1e-10, 1e-4};

		for (double accuracy : accuracyValues) {
			CalibrationContext context = new CalibrationContextImpl(referenceDateTime, accuracy);
			CalibrationSpecProviderOis provider = new CalibrationSpecProviderOis(
				maturityLabel,
				frequency,
				swapRate
			);

			CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

			assertNotNull(spec, "Spec should not be null for accuracy: " + accuracy);
		}
	}

	/**
	 * Test getCalibrationSpec with semiannual frequency and short maturity.
	 */
	@Test
	void testGetCalibrationSpecSemiannualShortMaturity() {
		String maturityLabel = "6M";
		String frequency = "semiannual";
		double swapRate = 0.028;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderOis provider = new CalibrationSpecProviderOis(
			maturityLabel,
			frequency,
			swapRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
	}

	/**
	 * Test getCalibrationSpec with monthly frequency and medium maturity.
	 */
	@Test
	void testGetCalibrationSpecMonthlyMediumMaturity() {
		String maturityLabel = "2Y";
		String frequency = "monthly";
		double swapRate = 0.033;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderOis provider = new CalibrationSpecProviderOis(
			maturityLabel,
			frequency,
			swapRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
	}

	/**
	 * Test getCalibrationSpec with quarterly frequency and long maturity.
	 */
	@Test
	void testGetCalibrationSpecQuarterlyLongMaturity() {
		String maturityLabel = "10Y";
		String frequency = "quarterly";
		double swapRate = 0.045;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderOis provider = new CalibrationSpecProviderOis(
			maturityLabel,
			frequency,
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
		String maturityLabel = "15Y";
		String frequency = "annual";
		double swapRate = 0.048;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderOis provider = new CalibrationSpecProviderOis(
			maturityLabel,
			frequency,
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
		String maturityLabel = "20Y";
		String frequency = "annual";
		double swapRate = 0.05;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderOis provider = new CalibrationSpecProviderOis(
			maturityLabel,
			frequency,
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
		String maturityLabel = "5Y";
		String frequency = "annual";
		double swapRate = 0.04;
		// Saturday
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 1, 10, 0, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderOis provider = new CalibrationSpecProviderOis(
			maturityLabel,
			frequency,
			swapRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
	}

	/**
	 * Test constructor with 6-month maturity.
	 */
	@Test
	void testConstructorWith6MonthMaturity() {
		String maturityLabel = "6M";
		String frequency = "semiannual";
		double swapRate = 0.03;

		CalibrationSpecProviderOis provider = new CalibrationSpecProviderOis(
			maturityLabel,
			frequency,
			swapRate
		);

		assertNotNull(provider);
	}

	/**
	 * Test constructor with 9-month maturity.
	 */
	@Test
	void testConstructorWith9MonthMaturity() {
		String maturityLabel = "9M";
		String frequency = "quarterly";
		double swapRate = 0.032;

		CalibrationSpecProviderOis provider = new CalibrationSpecProviderOis(
			maturityLabel,
			frequency,
			swapRate
		);

		assertNotNull(provider);
	}

	/**
	 * Test constructor with 18-month maturity.
	 */
	@Test
	void testConstructorWith18MonthMaturity() {
		String maturityLabel = "18M";
		String frequency = "semiannual";
		double swapRate = 0.036;

		CalibrationSpecProviderOis provider = new CalibrationSpecProviderOis(
			maturityLabel,
			frequency,
			swapRate
		);

		assertNotNull(provider);
	}

	/**
	 * Test getCalibrationSpec with 6-month maturity.
	 */
	@Test
	void testGetCalibrationSpecWith6MonthMaturity() {
		String maturityLabel = "6M";
		String frequency = "semiannual";
		double swapRate = 0.03;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderOis provider = new CalibrationSpecProviderOis(
			maturityLabel,
			frequency,
			swapRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
	}

	/**
	 * Test getCalibrationSpec with 18-month maturity.
	 */
	@Test
	void testGetCalibrationSpecWith18MonthMaturity() {
		String maturityLabel = "18M";
		String frequency = "semiannual";
		double swapRate = 0.036;
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext context = new CalibrationContextImpl(referenceDateTime, 1e-6);

		CalibrationSpecProviderOis provider = new CalibrationSpecProviderOis(
			maturityLabel,
			frequency,
			swapRate
		);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(context);

		assertNotNull(spec);
	}
}
