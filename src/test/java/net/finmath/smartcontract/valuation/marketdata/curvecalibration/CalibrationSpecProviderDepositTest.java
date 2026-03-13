package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import net.finmath.marketdata.calibration.CalibratedCurves;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CalibrationSpecProviderDepositTest {

	@Test
	void getCalibrationSpec_shouldReturnDepositSpec() {
		CalibrationSpecProviderDeposit provider =
				new CalibrationSpecProviderDeposit("6M", "6M", 0.03);

		CalibrationContext ctx = new CalibrationContextImpl(
				LocalDateTime.of(2024, 6, 15, 17, 0), 1e-12);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(ctx);
		assertNotNull(spec);
	}
}
