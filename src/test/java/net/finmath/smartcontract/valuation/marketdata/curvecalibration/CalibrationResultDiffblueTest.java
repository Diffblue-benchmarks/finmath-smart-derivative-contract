package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyDouble;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import net.finmath.marketdata.calibration.CalibratedCurves;
import net.finmath.marketdata.model.AnalyticModel;
import net.finmath.marketdata.model.AnalyticModelFromCurvesAndVols;
import net.finmath.marketdata.products.AnalyticProduct;
import net.finmath.optimizer.SolverException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CalibrationResultDiffblueTest {
  /**
   * Method under test: {@link CalibrationResult#getCalibratedModel()}
   */
  @Test
  void testGetCalibratedModel() throws CloneNotSupportedException, SolverException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibratedCurves c = new CalibratedCurves(new ArrayList<>());

    // Act
    AnalyticModel actualCalibratedModel = (new CalibrationResult(c,
        new CalibratedCurves.CalibrationSpec("Type", new double[]{10.0d, 0.5d, 10.0d, 0.5d},
            "Forward Curve Receiver Name", 10.0d, "3", "Calibration Curve Name", 10.0d)))
        .getCalibratedModel();

    // Assert
    assertTrue(actualCalibratedModel instanceof AnalyticModelFromCurvesAndVols);
    assertNull(((AnalyticModelFromCurvesAndVols) actualCalibratedModel).getReferenceDate());
    assertTrue(actualCalibratedModel.getCurves().isEmpty());
    assertTrue(actualCalibratedModel.getVolatilitySurfaces().isEmpty());
  }

  /**
   * Method under test: {@link CalibrationResult#getCalibratedModel()}
   */
  @Test
  void testGetCalibratedModel2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibratedCurves c = mock(CalibratedCurves.class);
    AnalyticModelFromCurvesAndVols analyticModelFromCurvesAndVols = new AnalyticModelFromCurvesAndVols();
    when(c.getModel()).thenReturn(analyticModelFromCurvesAndVols);

    // Act
    AnalyticModel actualCalibratedModel = (new CalibrationResult(c,
        new CalibratedCurves.CalibrationSpec("Type", new double[]{10.0d, 0.5d, 10.0d, 0.5d},
            "Forward Curve Receiver Name", 10.0d, "3", "Calibration Curve Name", 10.0d)))
        .getCalibratedModel();

    // Assert
    verify(c).getModel();
    assertSame(analyticModelFromCurvesAndVols, actualCalibratedModel);
  }

  /**
   * Method under test: {@link CalibrationResult#getSumOfSquaredErrors()}
   */
  @Test
  void testGetSumOfSquaredErrors() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AnalyticProduct analyticProduct = mock(AnalyticProduct.class);
    when(analyticProduct.getValue(anyDouble(), Mockito.<AnalyticModel>any())).thenReturn(10.0d);
    CalibratedCurves c = mock(CalibratedCurves.class);
    when(c.getModel()).thenReturn(new AnalyticModelFromCurvesAndVols());
    when(c.getCalibrationProductForSpec(Mockito.<CalibratedCurves.CalibrationSpec>any())).thenReturn(analyticProduct);

    // Act
    double actualSumOfSquaredErrors = (new CalibrationResult(c,
        new CalibratedCurves.CalibrationSpec("Type", new double[]{10.0d, 0.5d, 10.0d, 0.5d},
            "Forward Curve Receiver Name", 10.0d, "3", "Calibration Curve Name", 10.0d)))
        .getSumOfSquaredErrors();

    // Assert
    verify(c).getCalibrationProductForSpec(isA(CalibratedCurves.CalibrationSpec.class));
    verify(c).getModel();
    verify(analyticProduct).getValue(eq(0.0d), isA(AnalyticModel.class));
    assertEquals(100.0d, actualSumOfSquaredErrors);
  }

  /**
   * Method under test: {@link CalibrationResult#getSumOfSquaredErrors()}
   */
  @Test
  void testGetSumOfSquaredErrors2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange, Act and Assert
    assertEquals(0.0d, (new CalibrationResult(mock(CalibratedCurves.class))).getSumOfSquaredErrors());
  }

  /**
   * Method under test: {@link CalibrationResult#getSumOfSquaredErrors()}
   */
  @Test
  void testGetSumOfSquaredErrors3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    AnalyticProduct analyticProduct = mock(AnalyticProduct.class);
    when(analyticProduct.getValue(anyDouble(), Mockito.<AnalyticModel>any())).thenReturn(10.0d);
    CalibratedCurves c = mock(CalibratedCurves.class);
    when(c.getModel()).thenReturn(new AnalyticModelFromCurvesAndVols());
    when(c.getCalibrationProductForSpec(Mockito.<CalibratedCurves.CalibrationSpec>any())).thenReturn(analyticProduct);
    CalibratedCurves.CalibrationSpec calibrationSpec = new CalibratedCurves.CalibrationSpec("Type",
        new double[]{2.0d, 10.0d, 2.0d, 10.0d}, "Forward Curve Receiver Name", 2.0d, "3", "Calibration Curve Name",
        2.0d);

    // Act
    double actualSumOfSquaredErrors = (new CalibrationResult(c, calibrationSpec,
        new CalibratedCurves.CalibrationSpec("Type", new double[]{2.0d, 10.0d, 2.0d, 10.0d},
            "Forward Curve Receiver Name", 2.0d, "3", "Calibration Curve Name", 2.0d)))
        .getSumOfSquaredErrors();

    // Assert
    verify(c, atLeast(1)).getCalibrationProductForSpec(Mockito.<CalibratedCurves.CalibrationSpec>any());
    verify(c, atLeast(1)).getModel();
    verify(analyticProduct, atLeast(1)).getValue(eq(0.0d), isA(AnalyticModel.class));
    assertEquals(200.0d, actualSumOfSquaredErrors);
  }

  /**
   * Method under test:
   * {@link CalibrationResult#CalibrationResult(CalibratedCurves, CalibratedCurves.CalibrationSpec[])}
   */
  @Test
  void testNewCalibrationResult() throws CloneNotSupportedException, SolverException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibratedCurves c = new CalibratedCurves(new ArrayList<>());

    // Act and Assert
    AnalyticModel calibratedModel = (new CalibrationResult(c,
        new CalibratedCurves.CalibrationSpec("Type", new double[]{10.0d, 0.5d, 10.0d, 0.5d},
            "Forward Curve Receiver Name", 10.0d, "3", "Calibration Curve Name", 10.0d)))
        .getCalibratedModel();
    assertTrue(calibratedModel instanceof AnalyticModelFromCurvesAndVols);
    assertNull(((AnalyticModelFromCurvesAndVols) calibratedModel).getReferenceDate());
    assertTrue(calibratedModel.getCurves().isEmpty());
    assertTrue(calibratedModel.getVolatilitySurfaces().isEmpty());
  }

  /**
   * Method under test:
   * {@link CalibrationResult#CalibrationResult(CalibratedCurves, CalibratedCurves.CalibrationSpec[])}
   */
  @Test
  void testNewCalibrationResult2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibratedCurves c = mock(CalibratedCurves.class);

    // Act and Assert
    assertNull(
        (new CalibrationResult(c, new CalibratedCurves.CalibrationSpec("Type", new double[]{10.0d, 0.5d, 10.0d, 0.5d},
            "Forward Curve Receiver Name", 10.0d, "3", "Calibration Curve Name", 10.0d))).getCalibratedModel());
  }
}
