package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import net.finmath.marketdata.calibration.CalibratedCurves;
import net.finmath.marketdata.calibration.CalibratedCurves.CalibrationSpec;
import net.finmath.marketdata.model.AnalyticModel;
import net.finmath.marketdata.products.AnalyticProduct;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CalibrationResultTest {

	@Test
	void testGetFreshness() {
		CalibratedCurves calibratedCurves = Mockito.mock(CalibratedCurves.class);
		when(calibratedCurves.getModel()).thenReturn(Mockito.mock(AnalyticModel.class));

		LocalTime before = LocalTime.now();
		CalibrationResult result = new CalibrationResult(calibratedCurves);
		LocalTime after = LocalTime.now();

		LocalTime freshness = result.getFreshness();
		assertNotNull(freshness);
		assertFalse(freshness.isBefore(before), "Freshness should not be before construction time");
		assertFalse(freshness.isAfter(after), "Freshness should not be after construction time");
	}

	@Test
	void testGetSumOfSquaredErrorsSingleSpec() {
		CalibratedCurves calibratedCurves = Mockito.mock(CalibratedCurves.class);
		AnalyticModel model = Mockito.mock(AnalyticModel.class);
		CalibrationSpec spec = Mockito.mock(CalibrationSpec.class);
		AnalyticProduct product = Mockito.mock(AnalyticProduct.class);

		when(calibratedCurves.getModel()).thenReturn(model);
		when(calibratedCurves.getCalibrationProductForSpec(spec)).thenReturn(product);
		when(product.getValue(0.0, model)).thenReturn(3.0);

		CalibrationResult result = new CalibrationResult(calibratedCurves, spec);
		double sse = result.getSumOfSquaredErrors();

		assertEquals(9.0, sse, 1e-12, "Sum of squared errors for single spec with value 3.0");
	}

	@Test
	void testGetSumOfSquaredErrorsMultipleSpecs() {
		CalibratedCurves calibratedCurves = Mockito.mock(CalibratedCurves.class);
		AnalyticModel model = Mockito.mock(AnalyticModel.class);
		CalibrationSpec spec1 = Mockito.mock(CalibrationSpec.class);
		CalibrationSpec spec2 = Mockito.mock(CalibrationSpec.class);
		AnalyticProduct product1 = Mockito.mock(AnalyticProduct.class);
		AnalyticProduct product2 = Mockito.mock(AnalyticProduct.class);

		when(calibratedCurves.getModel()).thenReturn(model);
		when(calibratedCurves.getCalibrationProductForSpec(spec1)).thenReturn(product1);
		when(calibratedCurves.getCalibrationProductForSpec(spec2)).thenReturn(product2);
		when(product1.getValue(0.0, model)).thenReturn(2.0);
		when(product2.getValue(0.0, model)).thenReturn(4.0);

		CalibrationResult result = new CalibrationResult(calibratedCurves, spec1, spec2);
		double sse = result.getSumOfSquaredErrors();

		assertEquals(20.0, sse, 1e-12, "Sum of squared errors: 2^2 + 4^2 = 4 + 16 = 20");
	}

	@Test
	void testGetSumOfSquaredErrorsNoSpecs() {
		CalibratedCurves calibratedCurves = Mockito.mock(CalibratedCurves.class);
		when(calibratedCurves.getModel()).thenReturn(Mockito.mock(AnalyticModel.class));

		CalibrationResult result = new CalibrationResult(calibratedCurves);
		double sse = result.getSumOfSquaredErrors();

		assertEquals(0.0, sse, 1e-12, "Sum of squared errors with no specs should be 0");
	}
}
