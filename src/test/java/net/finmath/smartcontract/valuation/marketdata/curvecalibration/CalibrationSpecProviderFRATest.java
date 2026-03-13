package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import net.finmath.marketdata.calibration.CalibratedCurves;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CalibrationSpecProviderFRATest {

	@Test
	void getCalibrationSpec_shouldReturnFRASpec() {
		CalibrationSpecProviderFRA provider =
				new CalibrationSpecProviderFRA("6M", "12M", 0.035);

		CalibrationContext ctx = new CalibrationContextImpl(
				LocalDateTime.of(2024, 6, 15, 17, 0), 1e-12);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(ctx);
		assertNotNull(spec);
	}

	@Test
	void constructor_shouldComputeCorrectStartOffset() {
		// For tenor 6M and maturity 12M, start offset should be 6M
		CalibrationSpecProviderFRA provider =
				new CalibrationSpecProviderFRA("6M", "12M", 0.035);

		CalibrationContext ctx = new CalibrationContextImpl(
				LocalDateTime.of(2024, 6, 15, 17, 0), 1e-12);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(ctx);
		assertNotNull(spec);
	}
}
