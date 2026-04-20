package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import net.finmath.marketdata.calibration.CalibratedCurves;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CalibrationSpecProviderDepositTest {

	@Test
	void testConstructorAndGetCalibrationSpec() {
		CalibrationSpecProviderDeposit provider = new CalibrationSpecProviderDeposit("6M", "1Y", 0.05);

		CalibrationContext ctx = new CalibrationContextImpl(LocalDateTime.of(2023, 6, 15, 12, 0), 1E-12);
		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(ctx);

		assertNotNull(spec);
	}

	@Test
	void testGetCalibrationSpecWithDifferentParameters() {
		CalibrationSpecProviderDeposit provider = new CalibrationSpecProviderDeposit("3M", "2Y", 0.03);

		CalibrationContext ctx = new CalibrationContextImpl(LocalDateTime.of(2024, 1, 10, 10, 0), 1E-10);
		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(ctx);

		assertNotNull(spec);
	}
}
