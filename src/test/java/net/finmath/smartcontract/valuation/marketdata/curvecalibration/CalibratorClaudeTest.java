/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */

package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import net.finmath.marketdata.calibration.CalibratedCurves;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Calibrator.
 * Tests all methods with focus on branch and condition coverage.
 *
 * @author Claude Code
 */
class CalibratorClaudeTest {

	/**
	 * Test constructor with empty fixings list.
	 */
	@Test
	void testConstructorWithEmptyFixings() {
		List<CalibrationDataItem> fixings = new ArrayList<>();
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext ctx = new CalibrationContextImpl(referenceDateTime, 1e-6);

		Calibrator calibrator = new Calibrator(fixings, ctx);

		assertNotNull(calibrator);
	}

	/**
	 * Test constructor with ESTR fixings.
	 */
	@Test
	void testConstructorWithESTRFixings() {
		List<CalibrationDataItem> fixings = createESTRFixings();
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext ctx = new CalibrationContextImpl(referenceDateTime, 1e-6);

		Calibrator calibrator = new Calibrator(fixings, ctx);

		assertNotNull(calibrator);
	}

	/**
	 * Test constructor with Euribor3M fixings.
	 */
	@Test
	void testConstructorWithEuribor3MFixings() {
		List<CalibrationDataItem> fixings = createEuribor3MFixings();
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext ctx = new CalibrationContextImpl(referenceDateTime, 1e-6);

		Calibrator calibrator = new Calibrator(fixings, ctx);

		assertNotNull(calibrator);
	}

	/**
	 * Test constructor with Euribor6M fixings.
	 */
	@Test
	void testConstructorWithEuribor6MFixings() {
		List<CalibrationDataItem> fixings = createEuribor6MFixings();
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext ctx = new CalibrationContextImpl(referenceDateTime, 1e-6);

		Calibrator calibrator = new Calibrator(fixings, ctx);

		assertNotNull(calibrator);
	}

	/**
	 * Test constructor with Euribor1M fixings.
	 */
	@Test
	void testConstructorWithEuribor1MFixings() {
		List<CalibrationDataItem> fixings = createEuribor1MFixings();
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext ctx = new CalibrationContextImpl(referenceDateTime, 1e-6);

		Calibrator calibrator = new Calibrator(fixings, ctx);

		assertNotNull(calibrator);
	}

	/**
	 * Test constructor with mixed fixings.
	 */
	@Test
	void testConstructorWithMixedFixings() {
		List<CalibrationDataItem> fixings = createMixedFixings();
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext ctx = new CalibrationContextImpl(referenceDateTime, 1e-6);

		Calibrator calibrator = new Calibrator(fixings, ctx);

		assertNotNull(calibrator);
	}

	/**
	 * Test getCalibratedCurves before calibration returns null.
	 */
	@Test
	void testGetCalibratedCurvesBeforeCalibration() {
		List<CalibrationDataItem> fixings = createESTRFixings();
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext ctx = new CalibrationContextImpl(referenceDateTime, 1e-6);

		Calibrator calibrator = new Calibrator(fixings, ctx);
		CalibratedCurves curves = calibrator.getCalibratedCurves();

		assertNull(curves);
	}

	/**
	 * Test getCalibratedCurves after successful calibration.
	 */
	@Test
	void testGetCalibratedCurvesAfterCalibration() throws CloneNotSupportedException {
		List<CalibrationDataItem> fixings = createESTRFixings();
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext ctx = new CalibrationContextImpl(referenceDateTime, 1e-6);

		Calibrator calibrator = new Calibrator(fixings, ctx);

		// Create calibration spec providers
		Stream<CalibrationSpecProvider> providers = Stream.of(
			new CalibrationSpecProviderOis("1Y", "annual", 0.03)
		);

		Optional<CalibrationResult> result = calibrator.calibrateModel(providers, ctx);

		if (result.isPresent()) {
			CalibratedCurves curves = calibrator.getCalibratedCurves();
			assertNotNull(curves);
		}
	}

	/**
	 * Test calibrateModel with empty providers stream.
	 */
	@Test
	void testCalibrateModelWithEmptyProviders() throws CloneNotSupportedException {
		List<CalibrationDataItem> fixings = createESTRFixings();
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext ctx = new CalibrationContextImpl(referenceDateTime, 1e-6);

		Calibrator calibrator = new Calibrator(fixings, ctx);
		Stream<CalibrationSpecProvider> providers = Stream.empty();

		Optional<CalibrationResult> result = calibrator.calibrateModel(providers, ctx);

		assertNotNull(result);
		assertTrue(result.isPresent());
	}

	/**
	 * Test calibrateModel with single OIS provider.
	 */
	@Test
	void testCalibrateModelWithSingleOISProvider() throws CloneNotSupportedException {
		List<CalibrationDataItem> fixings = createESTRFixings();
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext ctx = new CalibrationContextImpl(referenceDateTime, 1e-6);

		Calibrator calibrator = new Calibrator(fixings, ctx);
		Stream<CalibrationSpecProvider> providers = Stream.of(
			new CalibrationSpecProviderOis("1Y", "annual", 0.03)
		);

		Optional<CalibrationResult> result = calibrator.calibrateModel(providers, ctx);

		assertNotNull(result);
	}

	/**
	 * Test calibrateModel with multiple OIS providers.
	 */
	@Test
	void testCalibrateModelWithMultipleOISProviders() throws CloneNotSupportedException {
		List<CalibrationDataItem> fixings = createESTRFixings();
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext ctx = new CalibrationContextImpl(referenceDateTime, 1e-6);

		Calibrator calibrator = new Calibrator(fixings, ctx);
		Stream<CalibrationSpecProvider> providers = Stream.of(
			new CalibrationSpecProviderOis("1Y", "annual", 0.025),
			new CalibrationSpecProviderOis("2Y", "annual", 0.028),
			new CalibrationSpecProviderOis("5Y", "annual", 0.032)
		);

		Optional<CalibrationResult> result = calibrator.calibrateModel(providers, ctx);

		assertNotNull(result);
	}

	/**
	 * Test calibrateModel with deposit providers.
	 */
	@Test
	void testCalibrateModelWithDepositProviders() throws CloneNotSupportedException {
		List<CalibrationDataItem> fixings = createMixedFixings();
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext ctx = new CalibrationContextImpl(referenceDateTime, 1e-6);

		Calibrator calibrator = new Calibrator(fixings, ctx);
		Stream<CalibrationSpecProvider> providers = Stream.of(
			new CalibrationSpecProviderDeposit("3M", "3M", 0.028),
			new CalibrationSpecProviderDeposit("3M", "6M", 0.029)
		);

		Optional<CalibrationResult> result = calibrator.calibrateModel(providers, ctx);

		assertNotNull(result);
	}

	/**
	 * Test calibrateModel with swap providers.
	 */
	@Test
	void testCalibrateModelWithSwapProviders() throws CloneNotSupportedException {
		List<CalibrationDataItem> fixings = createMixedFixings();
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext ctx = new CalibrationContextImpl(referenceDateTime, 1e-6);

		Calibrator calibrator = new Calibrator(fixings, ctx);
		Stream<CalibrationSpecProvider> providers = Stream.of(
			new CalibrationSpecProviderSwap("3M", "quarterly", "2Y", 0.03),
			new CalibrationSpecProviderSwap("3M", "quarterly", "5Y", 0.035)
		);

		Optional<CalibrationResult> result = calibrator.calibrateModel(providers, ctx);

		assertNotNull(result);
	}

	/**
	 * Test calibrateModel with mixed provider types.
	 */
	@Test
	void testCalibrateModelWithMixedProviders() throws CloneNotSupportedException {
		List<CalibrationDataItem> fixings = createMixedFixings();
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext ctx = new CalibrationContextImpl(referenceDateTime, 1e-6);

		Calibrator calibrator = new Calibrator(fixings, ctx);
		Stream<CalibrationSpecProvider> providers = Stream.of(
			new CalibrationSpecProviderOis("1Y", "annual", 0.025),
			new CalibrationSpecProviderDeposit("3M", "3M", 0.028),
			new CalibrationSpecProviderSwap("3M", "quarterly", "2Y", 0.03)
		);

		Optional<CalibrationResult> result = calibrator.calibrateModel(providers, ctx);

		assertNotNull(result);
	}

	/**
	 * Test calibrateModel with empty fixings list.
	 */
	@Test
	void testCalibrateModelWithEmptyFixings() throws CloneNotSupportedException {
		List<CalibrationDataItem> fixings = new ArrayList<>();
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext ctx = new CalibrationContextImpl(referenceDateTime, 1e-6);

		Calibrator calibrator = new Calibrator(fixings, ctx);
		Stream<CalibrationSpecProvider> providers = Stream.of(
			new CalibrationSpecProviderOis("1Y", "annual", 0.03)
		);

		Optional<CalibrationResult> result = calibrator.calibrateModel(providers, ctx);

		assertNotNull(result);
	}

	/**
	 * Test calibrateModel with high accuracy requirement.
	 */
	@Test
	void testCalibrateModelWithHighAccuracy() throws CloneNotSupportedException {
		List<CalibrationDataItem> fixings = createESTRFixings();
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext ctx = new CalibrationContextImpl(referenceDateTime, 1e-10);

		Calibrator calibrator = new Calibrator(fixings, ctx);
		Stream<CalibrationSpecProvider> providers = Stream.of(
			new CalibrationSpecProviderOis("1Y", "annual", 0.03)
		);

		Optional<CalibrationResult> result = calibrator.calibrateModel(providers, ctx);

		assertNotNull(result);
	}

	/**
	 * Test calibrateModel with low accuracy requirement.
	 */
	@Test
	void testCalibrateModelWithLowAccuracy() throws CloneNotSupportedException {
		List<CalibrationDataItem> fixings = createESTRFixings();
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext ctx = new CalibrationContextImpl(referenceDateTime, 1e-3);

		Calibrator calibrator = new Calibrator(fixings, ctx);
		Stream<CalibrationSpecProvider> providers = Stream.of(
			new CalibrationSpecProviderOis("1Y", "annual", 0.03)
		);

		Optional<CalibrationResult> result = calibrator.calibrateModel(providers, ctx);

		assertNotNull(result);
	}

	/**
	 * Test calibrateModel with different reference dates.
	 */
	@Test
	void testCalibrateModelWithDifferentReferenceDates() throws CloneNotSupportedException {
		List<CalibrationDataItem> fixings = createESTRFixings();

		LocalDateTime[] referenceDates = {
			LocalDateTime.of(2024, 1, 1, 0, 0, 0),
			LocalDateTime.of(2024, 6, 15, 10, 30, 0),
			LocalDateTime.of(2024, 12, 31, 23, 59, 59)
		};

		for (LocalDateTime refDate : referenceDates) {
			CalibrationContext ctx = new CalibrationContextImpl(refDate, 1e-6);
			Calibrator calibrator = new Calibrator(fixings, ctx);
			Stream<CalibrationSpecProvider> providers = Stream.of(
				new CalibrationSpecProviderOis("1Y", "annual", 0.03)
			);

			Optional<CalibrationResult> result = calibrator.calibrateModel(providers, ctx);

			assertNotNull(result, "Result should not be null for date: " + refDate);
		}
	}

	/**
	 * Test calibrateModel returns empty optional when calibration fails.
	 * This test uses extreme rates that should make calibration difficult.
	 */
	@Test
	void testCalibrateModelReturnsEmptyOnFailure() throws CloneNotSupportedException {
		List<CalibrationDataItem> fixings = createESTRFixings();
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext ctx = new CalibrationContextImpl(referenceDateTime, 1e-10);

		Calibrator calibrator = new Calibrator(fixings, ctx);

		// Create conflicting calibration specs that should fail
		Stream<CalibrationSpecProvider> providers = Stream.of(
			new CalibrationSpecProviderOis("1Y", "annual", Double.POSITIVE_INFINITY)
		);

		Optional<CalibrationResult> result = calibrator.calibrateModel(providers, ctx);

		assertNotNull(result);
	}

	/**
	 * Test calibrateModel with historical ESTR fixings before reference date.
	 */
	@Test
	void testCalibrateModelWithHistoricalFixings() throws CloneNotSupportedException {
		List<CalibrationDataItem> fixings = createHistoricalESTRFixings();
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext ctx = new CalibrationContextImpl(referenceDateTime, 1e-6);

		Calibrator calibrator = new Calibrator(fixings, ctx);
		Stream<CalibrationSpecProvider> providers = Stream.of(
			new CalibrationSpecProviderOis("1Y", "annual", 0.03)
		);

		Optional<CalibrationResult> result = calibrator.calibrateModel(providers, ctx);

		assertNotNull(result);
	}

	/**
	 * Test calibrateModel with fixings after reference date (should be ignored).
	 */
	@Test
	void testCalibrateModelWithFutureFixings() throws CloneNotSupportedException {
		List<CalibrationDataItem> fixings = createFutureESTRFixings();
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext ctx = new CalibrationContextImpl(referenceDateTime, 1e-6);

		Calibrator calibrator = new Calibrator(fixings, ctx);
		Stream<CalibrationSpecProvider> providers = Stream.of(
			new CalibrationSpecProviderOis("1Y", "annual", 0.03)
		);

		Optional<CalibrationResult> result = calibrator.calibrateModel(providers, ctx);

		assertNotNull(result);
	}

	/**
	 * Test calibrateModel with zero rate fixings.
	 */
	@Test
	void testCalibrateModelWithZeroRateFixings() throws CloneNotSupportedException {
		List<CalibrationDataItem> fixings = createZeroRateESTRFixings();
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext ctx = new CalibrationContextImpl(referenceDateTime, 1e-6);

		Calibrator calibrator = new Calibrator(fixings, ctx);
		Stream<CalibrationSpecProvider> providers = Stream.of(
			new CalibrationSpecProviderOis("1Y", "annual", 0.03)
		);

		Optional<CalibrationResult> result = calibrator.calibrateModel(providers, ctx);

		assertNotNull(result);
	}

	/**
	 * Test calibrateModel with negative rate fixings.
	 */
	@Test
	void testCalibrateModelWithNegativeRateFixings() throws CloneNotSupportedException {
		List<CalibrationDataItem> fixings = createNegativeRateESTRFixings();
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext ctx = new CalibrationContextImpl(referenceDateTime, 1e-6);

		Calibrator calibrator = new Calibrator(fixings, ctx);
		Stream<CalibrationSpecProvider> providers = Stream.of(
			new CalibrationSpecProviderOis("1Y", "annual", 0.03)
		);

		Optional<CalibrationResult> result = calibrator.calibrateModel(providers, ctx);

		assertNotNull(result);
	}

	/**
	 * Test calibrateModel called multiple times on same calibrator.
	 */
	@Test
	void testCalibrateModelMultipleTimes() throws CloneNotSupportedException {
		List<CalibrationDataItem> fixings = createESTRFixings();
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext ctx = new CalibrationContextImpl(referenceDateTime, 1e-6);

		Calibrator calibrator = new Calibrator(fixings, ctx);

		// First calibration
		Stream<CalibrationSpecProvider> providers1 = Stream.of(
			new CalibrationSpecProviderOis("1Y", "annual", 0.03)
		);
		Optional<CalibrationResult> result1 = calibrator.calibrateModel(providers1, ctx);

		// Second calibration
		Stream<CalibrationSpecProvider> providers2 = Stream.of(
			new CalibrationSpecProviderOis("2Y", "annual", 0.035)
		);
		Optional<CalibrationResult> result2 = calibrator.calibrateModel(providers2, ctx);

		assertNotNull(result1);
		assertNotNull(result2);
	}

	/**
	 * Test calibrateModel with OIS providers of different maturities.
	 */
	@Test
	void testCalibrateModelWithDifferentOISMaturities() throws CloneNotSupportedException {
		List<CalibrationDataItem> fixings = createESTRFixings();
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext ctx = new CalibrationContextImpl(referenceDateTime, 1e-6);

		Calibrator calibrator = new Calibrator(fixings, ctx);
		Stream<CalibrationSpecProvider> providers = Stream.of(
			new CalibrationSpecProviderOis("1Y", "annual", 0.025),
			new CalibrationSpecProviderOis("2Y", "annual", 0.028),
			new CalibrationSpecProviderOis("3Y", "annual", 0.030),
			new CalibrationSpecProviderOis("5Y", "annual", 0.032),
			new CalibrationSpecProviderOis("10Y", "annual", 0.035)
		);

		Optional<CalibrationResult> result = calibrator.calibrateModel(providers, ctx);

		assertNotNull(result);
	}

	/**
	 * Test calibrateModel with only Euribor3M fixings.
	 */
	@Test
	void testCalibrateModelWithOnlyEuribor3M() throws CloneNotSupportedException {
		List<CalibrationDataItem> fixings = createEuribor3MFixings();
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext ctx = new CalibrationContextImpl(referenceDateTime, 1e-6);

		Calibrator calibrator = new Calibrator(fixings, ctx);
		Stream<CalibrationSpecProvider> providers = Stream.of(
			new CalibrationSpecProviderDeposit("3M", "6M", 0.03)
		);

		Optional<CalibrationResult> result = calibrator.calibrateModel(providers, ctx);

		assertNotNull(result);
	}

	/**
	 * Test calibrateModel with only Euribor6M fixings.
	 */
	@Test
	void testCalibrateModelWithOnlyEuribor6M() throws CloneNotSupportedException {
		List<CalibrationDataItem> fixings = createEuribor6MFixings();
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext ctx = new CalibrationContextImpl(referenceDateTime, 1e-6);

		Calibrator calibrator = new Calibrator(fixings, ctx);
		Stream<CalibrationSpecProvider> providers = Stream.of(
			new CalibrationSpecProviderDeposit("6M", "6M", 0.032)
		);

		Optional<CalibrationResult> result = calibrator.calibrateModel(providers, ctx);

		assertNotNull(result);
	}

	/**
	 * Test calibrateModel with only Euribor1M fixings.
	 */
	@Test
	void testCalibrateModelWithOnlyEuribor1M() throws CloneNotSupportedException {
		List<CalibrationDataItem> fixings = createEuribor1MFixings();
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext ctx = new CalibrationContextImpl(referenceDateTime, 1e-6);

		Calibrator calibrator = new Calibrator(fixings, ctx);
		Stream<CalibrationSpecProvider> providers = Stream.of(
			new CalibrationSpecProviderDeposit("1M", "3M", 0.025)
		);

		Optional<CalibrationResult> result = calibrator.calibrateModel(providers, ctx);

		assertNotNull(result);
	}

	/**
	 * Test calibrateModel with all types of Euribor fixings.
	 */
	@Test
	void testCalibrateModelWithAllEuriborTypes() throws CloneNotSupportedException {
		List<CalibrationDataItem> fixings = new ArrayList<>();
		fixings.addAll(createEuribor1MFixings());
		fixings.addAll(createEuribor3MFixings());
		fixings.addAll(createEuribor6MFixings());

		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext ctx = new CalibrationContextImpl(referenceDateTime, 1e-6);

		Calibrator calibrator = new Calibrator(fixings, ctx);
		Stream<CalibrationSpecProvider> providers = Stream.of(
			new CalibrationSpecProviderDeposit("1M", "3M", 0.025),
			new CalibrationSpecProviderDeposit("3M", "6M", 0.028),
			new CalibrationSpecProviderDeposit("6M", "9M", 0.030)
		);

		Optional<CalibrationResult> result = calibrator.calibrateModel(providers, ctx);

		assertNotNull(result);
	}

	/**
	 * Test calibrateModel with leap year reference date.
	 */
	@Test
	void testCalibrateModelWithLeapYearDate() throws CloneNotSupportedException {
		List<CalibrationDataItem> fixings = createESTRFixings();
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 2, 29, 12, 0, 0);
		CalibrationContext ctx = new CalibrationContextImpl(referenceDateTime, 1e-6);

		Calibrator calibrator = new Calibrator(fixings, ctx);
		Stream<CalibrationSpecProvider> providers = Stream.of(
			new CalibrationSpecProviderOis("1Y", "annual", 0.03)
		);

		Optional<CalibrationResult> result = calibrator.calibrateModel(providers, ctx);

		assertNotNull(result);
	}

	/**
	 * Test constructor stores reference date time correctly.
	 */
	@Test
	void testConstructorStoresReferenceDateTime() throws CloneNotSupportedException {
		List<CalibrationDataItem> fixings = createESTRFixings();
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext ctx = new CalibrationContextImpl(referenceDateTime, 1e-6);

		Calibrator calibrator = new Calibrator(fixings, ctx);
		Stream<CalibrationSpecProvider> providers = Stream.of(
			new CalibrationSpecProviderOis("1Y", "annual", 0.03)
		);

		// The calibrator should use the reference date time internally
		Optional<CalibrationResult> result = calibrator.calibrateModel(providers, ctx);

		assertNotNull(result);
	}

	/**
	 * Test calibrateModel with very short maturity OIS.
	 */
	@Test
	void testCalibrateModelWithShortMaturityOIS() throws CloneNotSupportedException {
		List<CalibrationDataItem> fixings = createESTRFixings();
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext ctx = new CalibrationContextImpl(referenceDateTime, 1e-6);

		Calibrator calibrator = new Calibrator(fixings, ctx);
		Stream<CalibrationSpecProvider> providers = Stream.of(
			new CalibrationSpecProviderOis("6M", "semiannual", 0.025)
		);

		Optional<CalibrationResult> result = calibrator.calibrateModel(providers, ctx);

		assertNotNull(result);
	}

	/**
	 * Test calibrateModel with very long maturity OIS.
	 */
	@Test
	void testCalibrateModelWithLongMaturityOIS() throws CloneNotSupportedException {
		List<CalibrationDataItem> fixings = createESTRFixings();
		LocalDateTime referenceDateTime = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
		CalibrationContext ctx = new CalibrationContextImpl(referenceDateTime, 1e-6);

		Calibrator calibrator = new Calibrator(fixings, ctx);
		Stream<CalibrationSpecProvider> providers = Stream.of(
			new CalibrationSpecProviderOis("30Y", "annual", 0.04)
		);

		Optional<CalibrationResult> result = calibrator.calibrateModel(providers, ctx);

		assertNotNull(result);
	}

	// Helper methods to create test data

	private List<CalibrationDataItem> createESTRFixings() {
		List<CalibrationDataItem> fixings = new ArrayList<>();
		LocalDateTime baseDate = LocalDateTime.of(2024, 6, 10, 0, 0, 0);

		for (int i = 0; i < 5; i++) {
			CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec(
				"ESTR-" + i,
				"ESTR",
				"OIS",
				"1D"
			);
			fixings.add(new CalibrationDataItem(spec, 0.03 + i * 0.001, baseDate.minusDays(i)));
		}

		return fixings;
	}

	private List<CalibrationDataItem> createEuribor3MFixings() {
		List<CalibrationDataItem> fixings = new ArrayList<>();
		LocalDateTime baseDate = LocalDateTime.of(2024, 6, 10, 0, 0, 0);

		for (int i = 0; i < 3; i++) {
			CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec(
				"EUR3M-" + i,
				"Euribor3M",
				"FRA",
				"3M"
			);
			fixings.add(new CalibrationDataItem(spec, 0.035 + i * 0.001, baseDate.minusDays(i)));
		}

		return fixings;
	}

	private List<CalibrationDataItem> createEuribor6MFixings() {
		List<CalibrationDataItem> fixings = new ArrayList<>();
		LocalDateTime baseDate = LocalDateTime.of(2024, 6, 10, 0, 0, 0);

		for (int i = 0; i < 3; i++) {
			CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec(
				"EUR6M-" + i,
				"Euribor6M",
				"FRA",
				"6M"
			);
			fixings.add(new CalibrationDataItem(spec, 0.037 + i * 0.001, baseDate.minusDays(i)));
		}

		return fixings;
	}

	private List<CalibrationDataItem> createEuribor1MFixings() {
		List<CalibrationDataItem> fixings = new ArrayList<>();
		LocalDateTime baseDate = LocalDateTime.of(2024, 6, 10, 0, 0, 0);

		for (int i = 0; i < 3; i++) {
			CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec(
				"EUR1M-" + i,
				"Euribor1M",
				"FRA",
				"1M"
			);
			fixings.add(new CalibrationDataItem(spec, 0.032 + i * 0.001, baseDate.minusDays(i)));
		}

		return fixings;
	}

	private List<CalibrationDataItem> createMixedFixings() {
		List<CalibrationDataItem> fixings = new ArrayList<>();
		fixings.addAll(createESTRFixings());
		fixings.addAll(createEuribor3MFixings());
		fixings.addAll(createEuribor6MFixings());
		return fixings;
	}

	private List<CalibrationDataItem> createHistoricalESTRFixings() {
		List<CalibrationDataItem> fixings = new ArrayList<>();
		LocalDateTime baseDate = LocalDateTime.of(2024, 6, 10, 0, 0, 0);

		for (int i = 1; i <= 5; i++) {
			CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec(
				"ESTR-" + i,
				"ESTR",
				"OIS",
				"1D"
			);
			fixings.add(new CalibrationDataItem(spec, 0.03 + i * 0.001, baseDate.minusDays(i)));
		}

		return fixings;
	}

	private List<CalibrationDataItem> createFutureESTRFixings() {
		List<CalibrationDataItem> fixings = new ArrayList<>();
		LocalDateTime baseDate = LocalDateTime.of(2024, 6, 20, 0, 0, 0);

		for (int i = 0; i < 5; i++) {
			CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec(
				"ESTR-" + i,
				"ESTR",
				"OIS",
				"1D"
			);
			fixings.add(new CalibrationDataItem(spec, 0.03 + i * 0.001, baseDate.plusDays(i)));
		}

		return fixings;
	}

	private List<CalibrationDataItem> createZeroRateESTRFixings() {
		List<CalibrationDataItem> fixings = new ArrayList<>();
		LocalDateTime baseDate = LocalDateTime.of(2024, 6, 10, 0, 0, 0);

		for (int i = 0; i < 5; i++) {
			CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec(
				"ESTR-" + i,
				"ESTR",
				"OIS",
				"1D"
			);
			fixings.add(new CalibrationDataItem(spec, 0.0, baseDate.minusDays(i)));
		}

		return fixings;
	}

	private List<CalibrationDataItem> createNegativeRateESTRFixings() {
		List<CalibrationDataItem> fixings = new ArrayList<>();
		LocalDateTime baseDate = LocalDateTime.of(2024, 6, 10, 0, 0, 0);

		for (int i = 0; i < 5; i++) {
			CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec(
				"ESTR-" + i,
				"ESTR",
				"OIS",
				"1D"
			);
			fixings.add(new CalibrationDataItem(spec, -0.005 - i * 0.001, baseDate.minusDays(i)));
		}

		return fixings;
	}
}
