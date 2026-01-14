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
import net.finmath.smartcontract.valuation.marketdata.generators.MarketDataGeneratorLauncherDiffblueBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CalibrationResultDiffblueTest {
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
    String type = MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();
    String forwardCurveReceiverName =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();
    String discountCurveReceiverName =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();

    CalibrationSpec calibrationSpec =
        new CalibrationSpec(
            type,
            new double[] {10.0d, 0.5d, 10.0d, 0.5d},
            forwardCurveReceiverName,
            10.0d,
            discountCurveReceiverName,
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml(),
            10.0d);
    CalibrationSpec[] specs = new CalibrationSpec[] {calibrationSpec};

    // Act
    CalibrationResult actualCalibrationResult = new CalibrationResult(c, specs);

    // Assert
    AnalyticModel calibratedModel = actualCalibrationResult.getCalibratedModel();
    assertTrue(calibratedModel instanceof AnalyticModelFromCurvesAndVols);
    assertNull(((AnalyticModelFromCurvesAndVols) calibratedModel).getReferenceDate());
    assertTrue(calibratedModel.getCurves().isEmpty());
    assertTrue(calibratedModel.getVolatilitySurfaces().isEmpty());
    assertSame(c, actualCalibrationResult.getCalibration());
    assertSame(specs, actualCalibrationResult.getCalibrationSpecs());
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
  void testGetCalibratedModel_thenReturnAnalyticModelFromCurvesAndVols()
      throws CloneNotSupportedException, SolverException {
    // Arrange
    CalibratedCurves c = new CalibratedCurves(new ArrayList<>());
    String type = MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();
    String forwardCurveReceiverName =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();
    String discountCurveReceiverName =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();

    CalibrationSpec calibrationSpec =
        new CalibrationSpec(
            type,
            new double[] {10.0d, 0.5d, 10.0d, 0.5d},
            forwardCurveReceiverName,
            10.0d,
            discountCurveReceiverName,
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml(),
            10.0d);

    CalibrationResult calibrationResult = new CalibrationResult(c, calibrationSpec);

    // Act
    AnalyticModel actualCalibratedModel = calibrationResult.getCalibratedModel();

    // Assert
    assertTrue(actualCalibratedModel instanceof AnalyticModelFromCurvesAndVols);
    assertNull(((AnalyticModelFromCurvesAndVols) actualCalibratedModel).getReferenceDate());
    assertTrue(actualCalibratedModel.getCurves().isEmpty());
    assertTrue(actualCalibratedModel.getVolatilitySurfaces().isEmpty());
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
  void testGetCalibratedModel_thenReturnAnalyticModelFromCurvesAndVols2() {
    // Arrange
    CalibratedCurves c = mock(CalibratedCurves.class);
    AnalyticModelFromCurvesAndVols analyticModelFromCurvesAndVols =
        new AnalyticModelFromCurvesAndVols();
    when(c.getModel()).thenReturn(analyticModelFromCurvesAndVols);
    String type = MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();
    String forwardCurveReceiverName =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();
    String discountCurveReceiverName =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();

    CalibrationSpec calibrationSpec =
        new CalibrationSpec(
            type,
            new double[] {10.0d, 0.5d, 10.0d, 0.5d},
            forwardCurveReceiverName,
            10.0d,
            discountCurveReceiverName,
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml(),
            10.0d);

    CalibrationResult calibrationResult = new CalibrationResult(c, calibrationSpec);

    // Act
    AnalyticModel actualCalibratedModel = calibrationResult.getCalibratedModel();

    // Assert
    verify(c).getModel();
    assertSame(analyticModelFromCurvesAndVols, actualCalibratedModel);
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
    String type = MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();
    String forwardCurveReceiverName =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();
    String discountCurveReceiverName =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();

    CalibrationSpec calibrationSpec =
        new CalibrationSpec(
            type,
            new double[] {10.0d, 0.5d, 10.0d, 0.5d},
            forwardCurveReceiverName,
            10.0d,
            discountCurveReceiverName,
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml(),
            10.0d);

    CalibrationResult calibrationResult = new CalibrationResult(c, calibrationSpec);

    // Act
    double actualSumOfSquaredErrors = calibrationResult.getSumOfSquaredErrors();

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
    String type = MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();
    String forwardCurveReceiverName =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();
    String discountCurveReceiverName =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();

    CalibrationSpec calibrationSpec =
        new CalibrationSpec(
            type,
            new double[] {10.0d, 0.5d, 10.0d, 0.5d},
            forwardCurveReceiverName,
            10.0d,
            discountCurveReceiverName,
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml(),
            10.0d);
    String type2 =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();
    String forwardCurveReceiverName2 =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();
    String discountCurveReceiverName2 =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();

    CalibrationSpec calibrationSpec2 =
        new CalibrationSpec(
            type2,
            new double[] {10.0d, 0.5d, 10.0d, 0.5d},
            forwardCurveReceiverName2,
            10.0d,
            discountCurveReceiverName2,
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml(),
            10.0d);

    CalibrationResult calibrationResult =
        new CalibrationResult(c, calibrationSpec, calibrationSpec2);

    // Act
    double actualSumOfSquaredErrors = calibrationResult.getSumOfSquaredErrors();

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
  void testGetSumOfSquaredErrors_thenReturnZero()
      throws CloneNotSupportedException, SolverException {
    // Arrange, Act and Assert
    assertEquals(
        0.0d,
        new CalibrationResult(new CalibratedCurves(new ArrayList<>())).getSumOfSquaredErrors());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link CalibrationResult#getCalibration()}
   *   <li>{@link CalibrationResult#getCalibrationSpecs()}
   *   <li>{@link CalibrationResult#getFreshness()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CalibratedCurves CalibrationResult.getCalibration()",
    "CalibratedCurves.CalibrationSpec[] CalibrationResult.getCalibrationSpecs()",
    "java.time.LocalTime CalibrationResult.getFreshness()"
  })
  void testGettersAndSetters() throws CloneNotSupportedException, SolverException {
    // Arrange
    CalibratedCurves c = new CalibratedCurves(new ArrayList<>());
    String type = MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();
    String forwardCurveReceiverName =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();
    String discountCurveReceiverName =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();

    CalibrationSpec calibrationSpec =
        new CalibrationSpec(
            type,
            new double[] {10.0d, 0.5d, 10.0d, 0.5d},
            forwardCurveReceiverName,
            10.0d,
            discountCurveReceiverName,
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml(),
            10.0d);

    CalibrationResult calibrationResult = new CalibrationResult(c, calibrationSpec);

    // Act
    CalibratedCurves actualCalibration = calibrationResult.getCalibration();
    CalibrationSpec[] actualCalibrationSpecs = calibrationResult.getCalibrationSpecs();
    calibrationResult.getFreshness();

    // Assert
    assertEquals(1, actualCalibrationSpecs.length);
    assertSame(c, actualCalibration);
    assertSame(calibrationSpec, actualCalibrationSpecs[0]);
  }
}
