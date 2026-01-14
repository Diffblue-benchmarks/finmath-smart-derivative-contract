/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */

package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import net.finmath.marketdata.calibration.CalibratedCurves;
import net.finmath.marketdata.model.AnalyticModel;
import net.finmath.marketdata.products.AnalyticProduct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test class for CalibrationResult.
 * Tests all methods with focus on branch and condition coverage.
 *
 * @author Claude Code
 */
class CalibrationResultClaudeTest {

	private CalibratedCurves mockCalibratedCurves;
	private AnalyticModel mockModel;
	private CalibratedCurves.CalibrationSpec[] testSpecs;

	@BeforeEach
	void setUp() {
		// Create mocks for CalibratedCurves and AnalyticModel
		mockCalibratedCurves = mock(CalibratedCurves.class);
		mockModel = mock(AnalyticModel.class);

		// Set up basic mock behavior
		when(mockCalibratedCurves.getModel()).thenReturn(mockModel);

		// Create test calibration specs
		testSpecs = new CalibratedCurves.CalibrationSpec[3];
		for (int i = 0; i < 3; i++) {
			testSpecs[i] = mock(CalibratedCurves.CalibrationSpec.class);
		}
	}

	// Constructor tests

	@Test
	void testConstructor_WithValidCalibratedCurvesAndSpecs() {
		CalibrationResult result = new CalibrationResult(mockCalibratedCurves, testSpecs);

		assertNotNull(result);
		assertNotNull(result.getCalibratedModel());
		assertNotNull(result.getFreshness());
	}

	@Test
	void testConstructor_WithSingleSpec() {
		CalibratedCurves.CalibrationSpec[] singleSpec = new CalibratedCurves.CalibrationSpec[]{
			mock(CalibratedCurves.CalibrationSpec.class)
		};

		CalibrationResult result = new CalibrationResult(mockCalibratedCurves, singleSpec);

		assertNotNull(result);
		assertNotNull(result.getCalibratedModel());
	}

	@Test
	void testConstructor_WithEmptySpecs() {
		CalibratedCurves.CalibrationSpec[] emptySpecs = new CalibratedCurves.CalibrationSpec[0];

		CalibrationResult result = new CalibrationResult(mockCalibratedCurves, emptySpecs);

		assertNotNull(result);
		assertNotNull(result.getCalibratedModel());
	}

	@Test
	void testConstructor_SetsFreshnessToCurrentTime() {
		LocalTime beforeCreation = LocalTime.now();

		CalibrationResult result = new CalibrationResult(mockCalibratedCurves, testSpecs);

		LocalTime afterCreation = LocalTime.now();
		LocalTime freshness = result.getFreshness();

		assertNotNull(freshness);
		// Freshness should be between before and after creation times
		assertFalse(freshness.isBefore(beforeCreation));
		assertFalse(freshness.isAfter(afterCreation));
	}

	@Test
	void testConstructor_WithVarargsSyntax() {
		// Test using varargs syntax (passing individual specs)
		CalibrationResult result = new CalibrationResult(
			mockCalibratedCurves,
			testSpecs[0],
			testSpecs[1],
			testSpecs[2]
		);

		assertNotNull(result);
		assertNotNull(result.getCalibratedModel());
	}

	@Test
	void testConstructor_WithNoVarargs() {
		// Test calling with just the array parameter
		CalibrationResult result = new CalibrationResult(mockCalibratedCurves, testSpecs);

		assertNotNull(result);
	}

	// getCalibratedModel tests

	@Test
	void testGetCalibratedModel_ReturnsNonNull() {
		CalibrationResult result = new CalibrationResult(mockCalibratedCurves, testSpecs);

		AnalyticModel model = result.getCalibratedModel();

		assertNotNull(model);
	}

	@Test
	void testGetCalibratedModel_ReturnsSameModelFromCalibratedCurves() {
		CalibrationResult result = new CalibrationResult(mockCalibratedCurves, testSpecs);

		AnalyticModel resultModel = result.getCalibratedModel();

		assertSame(mockModel, resultModel);
		verify(mockCalibratedCurves, atLeastOnce()).getModel();
	}

	@Test
	void testGetCalibratedModel_MultipleCallsUseCalibratedCurvesEachTime() {
		CalibrationResult result = new CalibrationResult(mockCalibratedCurves, testSpecs);

		AnalyticModel model1 = result.getCalibratedModel();
		AnalyticModel model2 = result.getCalibratedModel();

		// Both should return the same mock model
		assertSame(model1, model2);
		assertSame(mockModel, model1);
		// Verify it delegates to calibratedCurves each time
		verify(mockCalibratedCurves, atLeast(2)).getModel();
	}

	@Test
	void testGetCalibratedModel_DelegatesToCalibratedCurves() {
		CalibrationResult result = new CalibrationResult(mockCalibratedCurves, testSpecs);

		result.getCalibratedModel();

		verify(mockCalibratedCurves).getModel();
	}

	// getSumOfSquaredErrors tests

	@Test
	void testGetSumOfSquaredErrors_WithZeroErrors() {
		// Set up mock specs to return calibration products with value 0.0
		CalibratedCurves.CalibrationSpec[] zeroSpecs = new CalibratedCurves.CalibrationSpec[2];
		for (int i = 0; i < 2; i++) {
			zeroSpecs[i] = mock(CalibratedCurves.CalibrationSpec.class);
			AnalyticProduct mockProduct = mock(AnalyticProduct.class);
			when(mockProduct.getValue(eq(0.0), any(AnalyticModel.class))).thenReturn(0.0);
			when(mockCalibratedCurves.getCalibrationProductForSpec(zeroSpecs[i])).thenReturn(mockProduct);
		}

		CalibrationResult result = new CalibrationResult(mockCalibratedCurves, zeroSpecs);

		double sumOfSquaredErrors = result.getSumOfSquaredErrors();

		assertEquals(0.0, sumOfSquaredErrors, 1E-10);
	}

	@Test
	void testGetSumOfSquaredErrors_WithNonZeroErrors() {
		// Set up mock specs with known error values: 0.1 and 0.2
		CalibratedCurves.CalibrationSpec[] errorSpecs = new CalibratedCurves.CalibrationSpec[2];

		errorSpecs[0] = mock(CalibratedCurves.CalibrationSpec.class);
		AnalyticProduct mockProduct0 = mock(AnalyticProduct.class);
		when(mockProduct0.getValue(eq(0.0), any(AnalyticModel.class))).thenReturn(0.1);
		when(mockCalibratedCurves.getCalibrationProductForSpec(errorSpecs[0])).thenReturn(mockProduct0);

		errorSpecs[1] = mock(CalibratedCurves.CalibrationSpec.class);
		AnalyticProduct mockProduct1 = mock(AnalyticProduct.class);
		when(mockProduct1.getValue(eq(0.0), any(AnalyticModel.class))).thenReturn(0.2);
		when(mockCalibratedCurves.getCalibrationProductForSpec(errorSpecs[1])).thenReturn(mockProduct1);

		CalibrationResult result = new CalibrationResult(mockCalibratedCurves, errorSpecs);

		double sumOfSquaredErrors = result.getSumOfSquaredErrors();

		// Expected: 0.1^2 + 0.2^2 = 0.01 + 0.04 = 0.05
		assertEquals(0.05, sumOfSquaredErrors, 1E-10);
	}

	@Test
	void testGetSumOfSquaredErrors_WithSingleSpec() {
		CalibratedCurves.CalibrationSpec[] singleSpec = new CalibratedCurves.CalibrationSpec[1];
		singleSpec[0] = mock(CalibratedCurves.CalibrationSpec.class);

		AnalyticProduct mockProduct = mock(AnalyticProduct.class);
		when(mockProduct.getValue(eq(0.0), any(AnalyticModel.class))).thenReturn(0.3);
		when(mockCalibratedCurves.getCalibrationProductForSpec(singleSpec[0])).thenReturn(mockProduct);

		CalibrationResult result = new CalibrationResult(mockCalibratedCurves, singleSpec);

		double sumOfSquaredErrors = result.getSumOfSquaredErrors();

		// Expected: 0.3^2 = 0.09
		assertEquals(0.09, sumOfSquaredErrors, 1E-10);
	}

	@Test
	void testGetSumOfSquaredErrors_WithEmptySpecs() {
		CalibratedCurves.CalibrationSpec[] emptySpecs = new CalibratedCurves.CalibrationSpec[0];
		CalibrationResult result = new CalibrationResult(mockCalibratedCurves, emptySpecs);

		double sumOfSquaredErrors = result.getSumOfSquaredErrors();

		assertEquals(0.0, sumOfSquaredErrors, 1E-10);
	}

	@Test
	void testGetSumOfSquaredErrors_WithNegativeValues() {
		// Test that negative values are squared correctly
		CalibratedCurves.CalibrationSpec[] negativeSpecs = new CalibratedCurves.CalibrationSpec[2];

		negativeSpecs[0] = mock(CalibratedCurves.CalibrationSpec.class);
		AnalyticProduct mockProduct0 = mock(AnalyticProduct.class);
		when(mockProduct0.getValue(eq(0.0), any(AnalyticModel.class))).thenReturn(-0.2);
		when(mockCalibratedCurves.getCalibrationProductForSpec(negativeSpecs[0])).thenReturn(mockProduct0);

		negativeSpecs[1] = mock(CalibratedCurves.CalibrationSpec.class);
		AnalyticProduct mockProduct1 = mock(AnalyticProduct.class);
		when(mockProduct1.getValue(eq(0.0), any(AnalyticModel.class))).thenReturn(-0.3);
		when(mockCalibratedCurves.getCalibrationProductForSpec(negativeSpecs[1])).thenReturn(mockProduct1);

		CalibrationResult result = new CalibrationResult(mockCalibratedCurves, negativeSpecs);

		double sumOfSquaredErrors = result.getSumOfSquaredErrors();

		// Expected: (-0.2)^2 + (-0.3)^2 = 0.04 + 0.09 = 0.13
		assertEquals(0.13, sumOfSquaredErrors, 1E-10);
	}

	@Test
	void testGetSumOfSquaredErrors_MultipleCallsReturnSameValue() {
		// Set up a spec with a known value
		CalibratedCurves.CalibrationSpec spec = mock(CalibratedCurves.CalibrationSpec.class);
		AnalyticProduct mockProduct = mock(AnalyticProduct.class);
		when(mockProduct.getValue(eq(0.0), any(AnalyticModel.class))).thenReturn(0.5);
		when(mockCalibratedCurves.getCalibrationProductForSpec(spec)).thenReturn(mockProduct);

		CalibrationResult result = new CalibrationResult(mockCalibratedCurves, spec);

		double sum1 = result.getSumOfSquaredErrors();
		double sum2 = result.getSumOfSquaredErrors();

		assertEquals(sum1, sum2, 1E-10);
		assertEquals(0.25, sum1, 1E-10); // 0.5^2 = 0.25
	}

	@Test
	void testGetSumOfSquaredErrors_WithMixedPositiveAndNegativeValues() {
		CalibratedCurves.CalibrationSpec[] mixedSpecs = new CalibratedCurves.CalibrationSpec[2];

		mixedSpecs[0] = mock(CalibratedCurves.CalibrationSpec.class);
		AnalyticProduct mockProduct0 = mock(AnalyticProduct.class);
		when(mockProduct0.getValue(eq(0.0), any(AnalyticModel.class))).thenReturn(0.4);
		when(mockCalibratedCurves.getCalibrationProductForSpec(mixedSpecs[0])).thenReturn(mockProduct0);

		mixedSpecs[1] = mock(CalibratedCurves.CalibrationSpec.class);
		AnalyticProduct mockProduct1 = mock(AnalyticProduct.class);
		when(mockProduct1.getValue(eq(0.0), any(AnalyticModel.class))).thenReturn(-0.3);
		when(mockCalibratedCurves.getCalibrationProductForSpec(mixedSpecs[1])).thenReturn(mockProduct1);

		CalibrationResult result = new CalibrationResult(mockCalibratedCurves, mixedSpecs);

		double sumOfSquaredErrors = result.getSumOfSquaredErrors();

		// Expected: 0.4^2 + (-0.3)^2 = 0.16 + 0.09 = 0.25
		assertEquals(0.25, sumOfSquaredErrors, 1E-10);
	}

	@Test
	void testGetSumOfSquaredErrors_CallsGetCalibratedModel() {
		CalibratedCurves.CalibrationSpec spec = mock(CalibratedCurves.CalibrationSpec.class);
		AnalyticProduct mockProduct = mock(AnalyticProduct.class);
		when(mockProduct.getValue(eq(0.0), any(AnalyticModel.class))).thenReturn(0.1);
		when(mockCalibratedCurves.getCalibrationProductForSpec(spec)).thenReturn(mockProduct);

		CalibrationResult result = new CalibrationResult(mockCalibratedCurves, spec);

		result.getSumOfSquaredErrors();

		// Verify that getCalibratedModel was called (indirectly via mockCalibratedCurves.getModel())
		verify(mockCalibratedCurves, atLeastOnce()).getModel();
	}

	@Test
	void testGetSumOfSquaredErrors_WithLargeValues() {
		CalibratedCurves.CalibrationSpec[] largeSpecs = new CalibratedCurves.CalibrationSpec[2];

		largeSpecs[0] = mock(CalibratedCurves.CalibrationSpec.class);
		AnalyticProduct mockProduct0 = mock(AnalyticProduct.class);
		when(mockProduct0.getValue(eq(0.0), any(AnalyticModel.class))).thenReturn(10.0);
		when(mockCalibratedCurves.getCalibrationProductForSpec(largeSpecs[0])).thenReturn(mockProduct0);

		largeSpecs[1] = mock(CalibratedCurves.CalibrationSpec.class);
		AnalyticProduct mockProduct1 = mock(AnalyticProduct.class);
		when(mockProduct1.getValue(eq(0.0), any(AnalyticModel.class))).thenReturn(20.0);
		when(mockCalibratedCurves.getCalibrationProductForSpec(largeSpecs[1])).thenReturn(mockProduct1);

		CalibrationResult result = new CalibrationResult(mockCalibratedCurves, largeSpecs);

		double sumOfSquaredErrors = result.getSumOfSquaredErrors();

		// Expected: 10^2 + 20^2 = 100 + 400 = 500
		assertEquals(500.0, sumOfSquaredErrors, 1E-10);
	}

	// getFreshness tests

	@Test
	void testGetFreshness_ReturnsNonNull() {
		CalibrationResult result = new CalibrationResult(mockCalibratedCurves, testSpecs);

		LocalTime freshness = result.getFreshness();

		assertNotNull(freshness);
	}

	@Test
	void testGetFreshness_MultipleCallsReturnSameValue() {
		CalibrationResult result = new CalibrationResult(mockCalibratedCurves, testSpecs);

		LocalTime freshness1 = result.getFreshness();
		LocalTime freshness2 = result.getFreshness();

		assertEquals(freshness1, freshness2);
	}

	@Test
	void testGetFreshness_DifferentInstancesHaveDifferentFreshness() throws InterruptedException {
		CalibrationResult result1 = new CalibrationResult(mockCalibratedCurves, testSpecs);
		// Small delay to ensure different timestamp
		Thread.sleep(10);
		CalibrationResult result2 = new CalibrationResult(mockCalibratedCurves, testSpecs);

		LocalTime freshness1 = result1.getFreshness();
		LocalTime freshness2 = result2.getFreshness();

		// The second result should have a later or equal freshness time
		assertFalse(freshness2.isBefore(freshness1));
	}

	@Test
	void testGetFreshness_IsReasonablyClose() {
		LocalTime beforeCreation = LocalTime.now();
		CalibrationResult result = new CalibrationResult(mockCalibratedCurves, testSpecs);
		LocalTime afterCreation = LocalTime.now();

		LocalTime freshness = result.getFreshness();

		// Freshness should be within the time window of object creation
		assertFalse(freshness.isBefore(beforeCreation));
		assertFalse(freshness.isAfter(afterCreation));
	}

	@Test
	void testGetFreshness_IsImmutable() {
		CalibrationResult result = new CalibrationResult(mockCalibratedCurves, testSpecs);

		LocalTime freshness1 = result.getFreshness();
		// Try to wait a bit
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			// Ignore
		}
		LocalTime freshness2 = result.getFreshness();

		// Freshness should not change after object creation
		assertEquals(freshness1, freshness2);
	}

	// Integration tests

	@Test
	void testCalibrationResult_CompleteWorkflow() {
		// Create calibration specs with known values
		CalibratedCurves.CalibrationSpec[] specs = new CalibratedCurves.CalibrationSpec[2];

		specs[0] = mock(CalibratedCurves.CalibrationSpec.class);
		AnalyticProduct mockProduct0 = mock(AnalyticProduct.class);
		when(mockProduct0.getValue(eq(0.0), any(AnalyticModel.class))).thenReturn(0.05);
		when(mockCalibratedCurves.getCalibrationProductForSpec(specs[0])).thenReturn(mockProduct0);

		specs[1] = mock(CalibratedCurves.CalibrationSpec.class);
		AnalyticProduct mockProduct1 = mock(AnalyticProduct.class);
		when(mockProduct1.getValue(eq(0.0), any(AnalyticModel.class))).thenReturn(0.10);
		when(mockCalibratedCurves.getCalibrationProductForSpec(specs[1])).thenReturn(mockProduct1);

		CalibrationResult result = new CalibrationResult(mockCalibratedCurves, specs);

		// Verify all methods return expected values
		assertNotNull(result.getCalibratedModel());
		assertNotNull(result.getFreshness());

		double expectedSumOfSquaredErrors = 0.05 * 0.05 + 0.10 * 0.10; // 0.0025 + 0.01 = 0.0125
		assertEquals(expectedSumOfSquaredErrors, result.getSumOfSquaredErrors(), 1E-10);
	}

	@Test
	void testCalibrationResult_WithLargeNumberOfSpecs() {
		// Test with a larger number of calibration specs
		int numSpecs = 10;
		CalibratedCurves.CalibrationSpec[] manySpecs = new CalibratedCurves.CalibrationSpec[numSpecs];
		double expectedSum = 0.0;

		for (int i = 0; i < numSpecs; i++) {
			final double value = (i + 1) * 0.01; // 0.01, 0.02, 0.03, etc.
			manySpecs[i] = mock(CalibratedCurves.CalibrationSpec.class);
			AnalyticProduct mockProduct = mock(AnalyticProduct.class);
			when(mockProduct.getValue(eq(0.0), any(AnalyticModel.class))).thenReturn(value);
			when(mockCalibratedCurves.getCalibrationProductForSpec(manySpecs[i])).thenReturn(mockProduct);
			expectedSum += value * value;
		}

		CalibrationResult result = new CalibrationResult(mockCalibratedCurves, manySpecs);

		assertEquals(expectedSum, result.getSumOfSquaredErrors(), 1E-10);
		assertNotNull(result.getCalibratedModel());
		assertNotNull(result.getFreshness());
	}

	@Test
	void testCalibrationResult_VerySmallErrors() {
		// Test with very small error values
		CalibratedCurves.CalibrationSpec[] smallSpecs = new CalibratedCurves.CalibrationSpec[2];

		smallSpecs[0] = mock(CalibratedCurves.CalibrationSpec.class);
		AnalyticProduct mockProduct0 = mock(AnalyticProduct.class);
		when(mockProduct0.getValue(eq(0.0), any(AnalyticModel.class))).thenReturn(1e-6);
		when(mockCalibratedCurves.getCalibrationProductForSpec(smallSpecs[0])).thenReturn(mockProduct0);

		smallSpecs[1] = mock(CalibratedCurves.CalibrationSpec.class);
		AnalyticProduct mockProduct1 = mock(AnalyticProduct.class);
		when(mockProduct1.getValue(eq(0.0), any(AnalyticModel.class))).thenReturn(2e-6);
		when(mockCalibratedCurves.getCalibrationProductForSpec(smallSpecs[1])).thenReturn(mockProduct1);

		CalibrationResult result = new CalibrationResult(mockCalibratedCurves, smallSpecs);

		double expectedSum = 1e-12 + 4e-12; // (1e-6)^2 + (2e-6)^2
		assertEquals(expectedSum, result.getSumOfSquaredErrors(), 1E-15);
	}

	@Test
	void testCalibrationResult_EnsuresFreshnessIsSetBeforeAnyMethodCall() {
		// Create a result and immediately check freshness
		CalibrationResult result = new CalibrationResult(mockCalibratedCurves, testSpecs);

		// Freshness should be set immediately upon construction
		LocalTime freshness = result.getFreshness();
		assertNotNull(freshness);

		// Other methods should work correctly even if called before getFreshness
		assertNotNull(result.getCalibratedModel());
	}
}
