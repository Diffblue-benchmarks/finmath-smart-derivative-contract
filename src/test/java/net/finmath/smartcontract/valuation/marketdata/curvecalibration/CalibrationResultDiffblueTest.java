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
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import net.finmath.marketdata.calibration.CalibratedCurves;
import net.finmath.marketdata.calibration.CalibratedCurves.CalibrationSpec;
import net.finmath.marketdata.model.AnalyticModel;
import net.finmath.marketdata.model.AnalyticModelFromCurvesAndVols;
import net.finmath.marketdata.products.AnalyticProduct;
import net.finmath.optimizer.SolverException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CalibrationResultDiffblueTest {
  @Mock private CalibratedCurves calibratedCurves;

  @InjectMocks private CalibrationResult calibrationResult;

  /**
   * Test {@link CalibrationResult#CalibrationResult(CalibratedCurves, CalibrationSpec[])}.
   *
   * <p>Method under test: {@link CalibrationResult#CalibrationResult(CalibratedCurves,
   * CalibratedCurves.CalibrationSpec[])}
   */
  @Test
  @DisplayName("Test new CalibrationResult(CalibratedCurves, CalibrationSpec[])")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void CalibrationResult.<init>(CalibratedCurves, CalibratedCurves.CalibrationSpec[])"
  })
  void testNewCalibrationResult() throws CloneNotSupportedException, SolverException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    CalibratedCurves c = new CalibratedCurves(new ArrayList<>());

    // Act and Assert
    AnalyticModel calibratedModel =
        new CalibrationResult(
                c,
                new CalibrationSpec(
                    "Type",
                    new double[] {10.0d, 0.5d, 10.0d, 0.5d},
                    "Forward Curve Receiver Name",
                    10.0d,
                    "3",
                    "Calibration Curve Name",
                    10.0d))
            .getCalibratedModel();
    assertTrue(calibratedModel instanceof AnalyticModelFromCurvesAndVols);
    assertNull(((AnalyticModelFromCurvesAndVols) calibratedModel).getReferenceDate());
    assertTrue(calibratedModel.getCurves().isEmpty());
    assertTrue(calibratedModel.getVolatilitySurfaces().isEmpty());
  }

  /**
   * Test {@link CalibrationResult#getCalibratedModel()}.
   *
   * <ul>
   *   <li>Then return {@link AnalyticModelFromCurvesAndVols#AnalyticModelFromCurvesAndVols()}.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationResult#getCalibratedModel()}
   */
  @Test
  @DisplayName("Test getCalibratedModel(); then return AnalyticModelFromCurvesAndVols()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"AnalyticModel CalibrationResult.getCalibratedModel()"})
  void testGetCalibratedModel_thenReturnAnalyticModelFromCurvesAndVols() {
    // Arrange
    AnalyticModelFromCurvesAndVols analyticModelFromCurvesAndVols =
        new AnalyticModelFromCurvesAndVols();
    when(calibratedCurves.getModel()).thenReturn(analyticModelFromCurvesAndVols);

    // Act
    AnalyticModel actualCalibratedModel = calibrationResult.getCalibratedModel();

    // Assert
    verify(calibratedCurves).getModel();
    assertSame(analyticModelFromCurvesAndVols, actualCalibratedModel);
  }

  /**
   * Test {@link CalibrationResult#getCalibratedModel()}.
   *
   * <ul>
   *   <li>Then return {@link AnalyticModelFromCurvesAndVols}.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationResult#getCalibratedModel()}
   */
  @Test
  @DisplayName("Test getCalibratedModel(); then return AnalyticModelFromCurvesAndVols")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"AnalyticModel CalibrationResult.getCalibratedModel()"})
  void testGetCalibratedModel_thenReturnAnalyticModelFromCurvesAndVols2()
      throws CloneNotSupportedException, SolverException {
    // Arrange
    CalibratedCurves c = new CalibratedCurves(new ArrayList<>());

    // Act
    AnalyticModel actualCalibratedModel =
        new CalibrationResult(
                c,
                new CalibrationSpec(
                    "Type",
                    new double[] {10.0d, 0.5d, 10.0d, 0.5d},
                    "Forward Curve Receiver Name",
                    10.0d,
                    "3",
                    "Calibration Curve Name",
                    10.0d))
            .getCalibratedModel();

    // Assert
    assertTrue(actualCalibratedModel instanceof AnalyticModelFromCurvesAndVols);
    assertNull(((AnalyticModelFromCurvesAndVols) actualCalibratedModel).getReferenceDate());
    assertTrue(actualCalibratedModel.getCurves().isEmpty());
    assertTrue(actualCalibratedModel.getVolatilitySurfaces().isEmpty());
  }

  /**
   * Test {@link CalibrationResult#getSumOfSquaredErrors()}.
   *
   * <ul>
   *   <li>Then return one hundred.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationResult#getSumOfSquaredErrors()}
   */
  @Test
  @DisplayName("Test getSumOfSquaredErrors(); then return one hundred")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"double CalibrationResult.getSumOfSquaredErrors()"})
  void testGetSumOfSquaredErrors_thenReturnOneHundred() {
    // Arrange
    AnalyticProduct analyticProduct = mock(AnalyticProduct.class);
    when(analyticProduct.getValue(anyDouble(), Mockito.<AnalyticModel>any())).thenReturn(10.0d);
    CalibratedCurves c = mock(CalibratedCurves.class);
    when(c.getModel()).thenReturn(new AnalyticModelFromCurvesAndVols());
    when(c.getCalibrationProductForSpec(Mockito.<CalibrationSpec>any()))
        .thenReturn(analyticProduct);

    // Act
    double actualSumOfSquaredErrors =
        new CalibrationResult(
                c,
                new CalibrationSpec(
                    "Type",
                    new double[] {10.0d, 0.5d, 10.0d, 0.5d},
                    "Forward Curve Receiver Name",
                    10.0d,
                    "3",
                    "Calibration Curve Name",
                    10.0d))
            .getSumOfSquaredErrors();

    // Assert
    verify(c).getCalibrationProductForSpec(isA(CalibrationSpec.class));
    verify(c).getModel();
    verify(analyticProduct).getValue(eq(0.0d), isA(AnalyticModel.class));
    assertEquals(100.0d, actualSumOfSquaredErrors);
  }

  /**
   * Test {@link CalibrationResult#getSumOfSquaredErrors()}.
   *
   * <ul>
   *   <li>Then return two hundred.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationResult#getSumOfSquaredErrors()}
   */
  @Test
  @DisplayName("Test getSumOfSquaredErrors(); then return two hundred")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"double CalibrationResult.getSumOfSquaredErrors()"})
  void testGetSumOfSquaredErrors_thenReturnTwoHundred() {
    // Arrange
    AnalyticProduct analyticProduct = mock(AnalyticProduct.class);
    when(analyticProduct.getValue(anyDouble(), Mockito.<AnalyticModel>any())).thenReturn(10.0d);
    CalibratedCurves c = mock(CalibratedCurves.class);
    when(c.getModel()).thenReturn(new AnalyticModelFromCurvesAndVols());
    when(c.getCalibrationProductForSpec(Mockito.<CalibrationSpec>any()))
        .thenReturn(analyticProduct);
    CalibrationSpec calibrationSpec =
        new CalibrationSpec(
            "Type",
            new double[] {2.0d, 10.0d, 2.0d, 10.0d},
            "Forward Curve Receiver Name",
            2.0d,
            "3",
            "Calibration Curve Name",
            2.0d);

    // Act
    double actualSumOfSquaredErrors =
        new CalibrationResult(
                c,
                calibrationSpec,
                new CalibrationSpec(
                    "Type",
                    new double[] {2.0d, 10.0d, 2.0d, 10.0d},
                    "Forward Curve Receiver Name",
                    2.0d,
                    "3",
                    "Calibration Curve Name",
                    2.0d))
            .getSumOfSquaredErrors();

    // Assert
    verify(c, atLeast(1)).getCalibrationProductForSpec(Mockito.<CalibrationSpec>any());
    verify(c, atLeast(1)).getModel();
    verify(analyticProduct, atLeast(1)).getValue(eq(0.0d), isA(AnalyticModel.class));
    assertEquals(200.0d, actualSumOfSquaredErrors);
  }

  /**
   * Test {@link CalibrationResult#getSumOfSquaredErrors()}.
   *
   * <ul>
   *   <li>Then return zero.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationResult#getSumOfSquaredErrors()}
   */
  @Test
  @DisplayName("Test getSumOfSquaredErrors(); then return zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"double CalibrationResult.getSumOfSquaredErrors()"})
  void testGetSumOfSquaredErrors_thenReturnZero() {
    // Arrange, Act and Assert
    assertEquals(0.0d, new CalibrationResult(mock(CalibratedCurves.class)).getSumOfSquaredErrors());
  }
}
