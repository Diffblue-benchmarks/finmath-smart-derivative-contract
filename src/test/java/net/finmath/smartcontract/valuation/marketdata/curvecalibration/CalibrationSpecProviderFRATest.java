package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import net.finmath.marketdata.calibration.CalibratedCurves;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CalibrationSpecProviderFRATest {

	@Test
	void testConstructor() {
		CalibrationSpecProviderFRA provider = new CalibrationSpecProviderFRA("6M", "9M", 0.05);
		assertNotNull(provider);
	}

	@Test
	void testConstructorWithDifferentTenors() {
		CalibrationSpecProviderFRA provider = new CalibrationSpecProviderFRA("3M", "6M", 0.02);
		assertNotNull(provider);
	}

	@Test
	void testGetCalibrationSpec() {
		CalibrationSpecProviderFRA provider = new CalibrationSpecProviderFRA("6M", "9M", 0.05);
		CalibrationContext ctx = new CalibrationContextImpl(LocalDateTime.of(2023, 1, 15, 12, 0), 1E-12);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(ctx);

		assertNotNull(spec);
	}

	@Test
	void testGetCalibrationSpecWith3MonthTenor() {
		CalibrationSpecProviderFRA provider = new CalibrationSpecProviderFRA("3M", "6M", 0.03);
		CalibrationContext ctx = new CalibrationContextImpl(LocalDateTime.of(2023, 6, 1, 10, 0), 1E-12);

		CalibratedCurves.CalibrationSpec spec = provider.getCalibrationSpec(ctx);

		assertNotNull(spec);
	}
}
