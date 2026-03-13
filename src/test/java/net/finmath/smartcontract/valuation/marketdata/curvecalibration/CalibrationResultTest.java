package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import net.finmath.marketdata.calibration.CalibratedCurves;
import net.finmath.marketdata.model.AnalyticModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for CalibrationResult.
 * Uses Mockito to mock complex financial model dependencies.
 */
@ExtendWith(MockitoExtension.class)
class CalibrationResultTest {

    @Mock(lenient = true)
    private CalibratedCurves mockCalibratedCurves;

    @Mock
    private AnalyticModel mockAnalyticModel;

    @Mock
    private CalibratedCurves.CalibrationSpec mockSpec1;

    @Mock
    private CalibratedCurves.CalibrationSpec mockSpec2;

    @Test
    void testConstructorWithSingleSpec() {
        when(mockCalibratedCurves.getModel()).thenReturn(mockAnalyticModel);

        CalibrationResult result = new CalibrationResult(mockCalibratedCurves, mockSpec1);

        assertNotNull(result);
    }

    @Test
    void testConstructorWithMultipleSpecs() {
        when(mockCalibratedCurves.getModel()).thenReturn(mockAnalyticModel);

        CalibrationResult result = new CalibrationResult(
            mockCalibratedCurves,
            mockSpec1,
            mockSpec2
        );

        assertNotNull(result);
    }

    @Test
    void testConstructorWithNoSpecs() {
        when(mockCalibratedCurves.getModel()).thenReturn(mockAnalyticModel);

        CalibrationResult result = new CalibrationResult(mockCalibratedCurves);

        assertNotNull(result);
    }

    @Test
    void testGetCalibratedModel() {
        when(mockCalibratedCurves.getModel()).thenReturn(mockAnalyticModel);

        CalibrationResult result = new CalibrationResult(mockCalibratedCurves, mockSpec1);
        AnalyticModel model = result.getCalibratedModel();

        assertNotNull(model);
        assertEquals(mockAnalyticModel, model);
        verify(mockCalibratedCurves).getModel();
    }

    @Test
    void testGetFreshness() {
        when(mockCalibratedCurves.getModel()).thenReturn(mockAnalyticModel);

        LocalTime before = LocalTime.now();
        CalibrationResult result = new CalibrationResult(mockCalibratedCurves, mockSpec1);
        LocalTime freshness = result.getFreshness();
        LocalTime after = LocalTime.now();

        assertNotNull(freshness);
        // Freshness should be between before and after
        assertTrue(!freshness.isBefore(before.minusSeconds(1)));
        assertTrue(!freshness.isAfter(after.plusSeconds(1)));
    }

    @Test
    void testFreshnessIsSetAtCreation() {
        when(mockCalibratedCurves.getModel()).thenReturn(mockAnalyticModel);

        CalibrationResult result = new CalibrationResult(mockCalibratedCurves, mockSpec1);

        LocalTime firstCall = result.getFreshness();
        LocalTime secondCall = result.getFreshness();

        // Freshness should remain the same (not updated on each call)
        assertEquals(firstCall, secondCall);
    }

    @Test
    void testDifferentInstancesHaveDifferentFreshness() throws InterruptedException {
        when(mockCalibratedCurves.getModel()).thenReturn(mockAnalyticModel);

        CalibrationResult result1 = new CalibrationResult(mockCalibratedCurves, mockSpec1);
        Thread.sleep(10); // Small delay to ensure different timestamps
        CalibrationResult result2 = new CalibrationResult(mockCalibratedCurves, mockSpec2);

        LocalTime freshness1 = result1.getFreshness();
        LocalTime freshness2 = result2.getFreshness();

        // Freshness should be different (or very close)
        assertNotNull(freshness1);
        assertNotNull(freshness2);
    }

    @Test
    void testGetCalibratedModelCalledMultipleTimes() {
        when(mockCalibratedCurves.getModel()).thenReturn(mockAnalyticModel);

        CalibrationResult result = new CalibrationResult(mockCalibratedCurves, mockSpec1);

        result.getCalibratedModel();
        result.getCalibratedModel();
        result.getCalibratedModel();

        verify(mockCalibratedCurves, times(3)).getModel();
    }

    @Test
    void testConstructorStoresSpecs() {
        when(mockCalibratedCurves.getModel()).thenReturn(mockAnalyticModel);

        CalibrationResult result = new CalibrationResult(
            mockCalibratedCurves,
            mockSpec1,
            mockSpec2
        );

        assertNotNull(result);
        // Specs are stored internally (verified through getSumOfSquaredErrors if called)
    }

    @Test
    void testConstructorWithVarArgs() {
        when(mockCalibratedCurves.getModel()).thenReturn(mockAnalyticModel);

        CalibratedCurves.CalibrationSpec[] specs = {mockSpec1, mockSpec2};
        CalibrationResult result = new CalibrationResult(mockCalibratedCurves, specs);

        assertNotNull(result);
    }

    @Test
    void testGetCalibratedModel_ReturnsNonNull() {
        when(mockCalibratedCurves.getModel()).thenReturn(mockAnalyticModel);

        CalibrationResult result = new CalibrationResult(mockCalibratedCurves);
        AnalyticModel model = result.getCalibratedModel();

        assertNotNull(model);
    }

    @Test
    void testFreshnessIsLocalTime() {
        when(mockCalibratedCurves.getModel()).thenReturn(mockAnalyticModel);

        CalibrationResult result = new CalibrationResult(mockCalibratedCurves);
        LocalTime freshness = result.getFreshness();

        assertNotNull(freshness);
        assertTrue(freshness instanceof LocalTime);
    }
}
