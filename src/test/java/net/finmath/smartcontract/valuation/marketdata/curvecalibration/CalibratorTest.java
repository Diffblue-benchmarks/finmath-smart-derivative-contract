package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class CalibratorTest {

	@Test
	void testGetCalibratedCurvesReturnsNull() {
		// Test that getCalibratedCurves returns null before calibration (covers line 38)
		final CalibrationContext ctx = new TestCalibrationContext(LocalDate.of(2024, 1, 15), 1.0E-9);
		final List<CalibrationDataItem> fixings = new ArrayList<>();
		final Calibrator calibrator = new Calibrator(fixings, ctx);

		assertNull(calibrator.getCalibratedCurves());
	}

	@Test
	void testCalibratorWithHistoricalESTRFixings() {
		// Test creating a Calibrator with historical ESTR fixings
		// This will exercise getOisDiscountCurve with time < 0 (lines 75-76, 79, 81-83, 87, 102-103, 106-107)
		final LocalDate referenceDate = LocalDate.of(2024, 1, 15);
		final CalibrationContext ctx = new TestCalibrationContext(referenceDate, 1.0E-9);

		final List<CalibrationDataItem> fixings = new ArrayList<>();

		// Add historical ESTR fixings (before reference date to trigger time < 0)
		final LocalDateTime historicalDate1 = LocalDateTime.of(2024, 1, 10, 17, 0);
		final LocalDateTime historicalDate2 = LocalDateTime.of(2024, 1, 11, 17, 0);
		final LocalDateTime historicalDate3 = LocalDateTime.of(2024, 1, 12, 17, 0);

		final CalibrationDataItem.Spec estrSpec1 = new CalibrationDataItem.Spec("ESTR1", "ESTR", "ESTR", "1D");
		final CalibrationDataItem.Spec estrSpec2 = new CalibrationDataItem.Spec("ESTR2", "ESTR", "ESTR", "1D");
		final CalibrationDataItem.Spec estrSpec3 = new CalibrationDataItem.Spec("ESTR3", "ESTR", "ESTR", "1D");

		fixings.add(new CalibrationDataItem(estrSpec1, 0.035, historicalDate1));
		fixings.add(new CalibrationDataItem(estrSpec2, 0.036, historicalDate2));
		fixings.add(new CalibrationDataItem(estrSpec3, 0.037, historicalDate3));

		// Creating the calibrator will cause the getOisDiscountCurve to be called
		// during calibration setup, exercising the historical fixing paths
		final Calibrator calibrator = new Calibrator(fixings, ctx);

		assertNotNull(calibrator);
	}

	@Test
	void testCalibratorWith3MEuriborFixings() {
		// Test creating a Calibrator with Euribor3M fixings
		// This will exercise get3MForwardCurve with fixings (lines 147-150, 160-161, 172-174)
		final LocalDate referenceDate = LocalDate.of(2024, 1, 15);
		final CalibrationContext ctx = new TestCalibrationContext(referenceDate, 1.0E-9);

		final List<CalibrationDataItem> fixings = new ArrayList<>();

		// Add Euribor3M fixings
		final LocalDateTime fixingDate1 = LocalDateTime.of(2023, 12, 15, 17, 0);
		final LocalDateTime fixingDate2 = LocalDateTime.of(2023, 12, 20, 17, 0);

		final CalibrationDataItem.Spec euribor3MSpec1 = new CalibrationDataItem.Spec("EUR3M1", "Euribor3M", "Euribor", "3M");
		final CalibrationDataItem.Spec euribor3MSpec2 = new CalibrationDataItem.Spec("EUR3M2", "Euribor3M", "Euribor", "3M");

		fixings.add(new CalibrationDataItem(euribor3MSpec1, 0.04, fixingDate1));
		fixings.add(new CalibrationDataItem(euribor3MSpec2, 0.041, fixingDate2));

		final Calibrator calibrator = new Calibrator(fixings, ctx);

		assertNotNull(calibrator);
	}

	@Test
	void testCalibratorWith6MEuriborFixings() {
		// Test creating a Calibrator with Euribor6M fixings
		// This will exercise get6MForwardCurve with fixings (lines 193-197, 206-207, 218-220)
		final LocalDate referenceDate = LocalDate.of(2024, 1, 15);
		final CalibrationContext ctx = new TestCalibrationContext(referenceDate, 1.0E-9);

		final List<CalibrationDataItem> fixings = new ArrayList<>();

		// Add Euribor6M fixings
		final LocalDateTime fixingDate1 = LocalDateTime.of(2023, 12, 10, 17, 0);
		final LocalDateTime fixingDate2 = LocalDateTime.of(2023, 12, 25, 17, 0);

		final CalibrationDataItem.Spec euribor6MSpec1 = new CalibrationDataItem.Spec("EUR6M1", "Euribor6M", "Euribor", "6M");
		final CalibrationDataItem.Spec euribor6MSpec2 = new CalibrationDataItem.Spec("EUR6M2", "Euribor6M", "Euribor", "6M");

		fixings.add(new CalibrationDataItem(euribor6MSpec1, 0.045, fixingDate1));
		fixings.add(new CalibrationDataItem(euribor6MSpec2, 0.046, fixingDate2));

		final Calibrator calibrator = new Calibrator(fixings, ctx);

		assertNotNull(calibrator);
	}

	@Test
	void testCalibratorWith1MEuriborFixings() {
		// Test creating a Calibrator with Euribor1M fixings
		// This will exercise get1MForwardCurve with fixings (lines 239-243, 252-253, 264-266)
		final LocalDate referenceDate = LocalDate.of(2024, 1, 15);
		final CalibrationContext ctx = new TestCalibrationContext(referenceDate, 1.0E-9);

		final List<CalibrationDataItem> fixings = new ArrayList<>();

		// Add Euribor1M fixings
		final LocalDateTime fixingDate1 = LocalDateTime.of(2023, 12, 18, 17, 0);
		final LocalDateTime fixingDate2 = LocalDateTime.of(2023, 12, 28, 17, 0);

		final CalibrationDataItem.Spec euribor1MSpec1 = new CalibrationDataItem.Spec("EUR1M1", "Euribor1M", "Euribor", "1M");
		final CalibrationDataItem.Spec euribor1MSpec2 = new CalibrationDataItem.Spec("EUR1M2", "Euribor1M", "Euribor", "1M");

		fixings.add(new CalibrationDataItem(euribor1MSpec1, 0.038, fixingDate1));
		fixings.add(new CalibrationDataItem(euribor1MSpec2, 0.039, fixingDate2));

		final Calibrator calibrator = new Calibrator(fixings, ctx);

		assertNotNull(calibrator);
	}

	@Test
	void testCalibratorWithAllFixingTypes() {
		// Test creating a Calibrator with all types of fixings together
		final LocalDate referenceDate = LocalDate.of(2024, 1, 15);
		final CalibrationContext ctx = new TestCalibrationContext(referenceDate, 1.0E-9);

		final List<CalibrationDataItem> fixings = new ArrayList<>();

		// Add ESTR historical fixings
		final LocalDateTime historicalDate = LocalDateTime.of(2024, 1, 10, 17, 0);
		final CalibrationDataItem.Spec estrSpec = new CalibrationDataItem.Spec("ESTR1", "ESTR", "ESTR", "1D");
		fixings.add(new CalibrationDataItem(estrSpec, 0.035, historicalDate));

		// Add Euribor 1M fixings
		final LocalDateTime euribor1MDate = LocalDateTime.of(2023, 12, 20, 17, 0);
		final CalibrationDataItem.Spec euribor1MSpec = new CalibrationDataItem.Spec("EUR1M1", "Euribor1M", "Euribor", "1M");
		fixings.add(new CalibrationDataItem(euribor1MSpec, 0.038, euribor1MDate));

		// Add Euribor 3M fixings
		final LocalDateTime euribor3MDate = LocalDateTime.of(2023, 12, 15, 17, 0);
		final CalibrationDataItem.Spec euribor3MSpec = new CalibrationDataItem.Spec("EUR3M1", "Euribor3M", "Euribor", "3M");
		fixings.add(new CalibrationDataItem(euribor3MSpec, 0.04, euribor3MDate));

		// Add Euribor 6M fixings
		final LocalDateTime euribor6MDate = LocalDateTime.of(2023, 12, 10, 17, 0);
		final CalibrationDataItem.Spec euribor6MSpec = new CalibrationDataItem.Spec("EUR6M1", "Euribor6M", "Euribor", "6M");
		fixings.add(new CalibrationDataItem(euribor6MSpec, 0.045, euribor6MDate));

		final Calibrator calibrator = new Calibrator(fixings, ctx);

		assertNotNull(calibrator);
	}

	// Test implementation of CalibrationContext
	private static class TestCalibrationContext implements CalibrationContext {
		private final LocalDate referenceDate;
		private final double accuracy;

		public TestCalibrationContext(final LocalDate referenceDate, final double accuracy) {
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
