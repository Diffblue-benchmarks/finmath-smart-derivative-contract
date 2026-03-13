package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import net.finmath.marketdata.calibration.CalibratedCurves;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CalibrationSpecProviderSwapTest {

	@Test
	void getCalibrationSpec_shouldReturnSwapSpec() {
		CalibrationSpecProviderSwap provider =
				new CalibrationSpecProviderSwap("6M", "semiannual", "5Y", 0.03);

		CalibrationContext ctx = new CalibrationContextImpl(
				LocalDateTime.of(2024, 6, 15, 17, 0), 1e-12);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(ctx);
		assertNotNull(spec);
	}
}
