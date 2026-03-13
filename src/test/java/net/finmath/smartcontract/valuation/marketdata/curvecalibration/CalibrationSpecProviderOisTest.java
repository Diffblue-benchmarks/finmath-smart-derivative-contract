package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import net.finmath.marketdata.calibration.CalibratedCurves;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CalibrationSpecProviderOisTest {

	@Test
	void getCalibrationSpec_shouldReturnSwapSpec() {
		CalibrationSpecProviderOis provider =
				new CalibrationSpecProviderOis("5Y", "annual", 0.025);

		CalibrationContext ctx = new CalibrationContextImpl(
				LocalDateTime.of(2024, 6, 15, 17, 0), 1e-12);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(ctx);
		assertNotNull(spec);
	}
}
