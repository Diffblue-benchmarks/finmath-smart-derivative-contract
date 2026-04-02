package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import net.finmath.marketdata.calibration.CalibratedCurves;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class CalibrationSpecProviderDepositTest {

	@Test
	void testGetCalibrationSpec() {
		final String tenorLabel = "6M";
		final String maturityLabel = "6M";
		final double depositRate = 0.05;

		CalibrationSpecProviderDeposit provider = new CalibrationSpecProviderDeposit(tenorLabel, maturityLabel, depositRate);

		CalibrationContext ctx = new CalibrationContextImpl(LocalDate.of(2023, 1, 2), 1E-9);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(ctx);

		Assertions.assertNotNull(spec, "CalibrationSpec should not be null");
		Assertions.assertEquals("EUR-" + tenorLabel + maturityLabel, spec.getSymbol(), "Symbol should match");
	}

	@Test
	void testGetCalibrationSpecWithDifferentTenor() {
		final String tenorLabel = "3M";
		final String maturityLabel = "1Y";
		final double depositRate = 0.03;

		CalibrationSpecProviderDeposit provider = new CalibrationSpecProviderDeposit(tenorLabel, maturityLabel, depositRate);

		CalibrationContext ctx = new CalibrationContextImpl(LocalDate.of(2023, 6, 1), 1E-9);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(ctx);

		Assertions.assertNotNull(spec, "CalibrationSpec should not be null");
		Assertions.assertEquals("EUR-" + tenorLabel + maturityLabel, spec.getSymbol(), "Symbol should match");
	}
}
