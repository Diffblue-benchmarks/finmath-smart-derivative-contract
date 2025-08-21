package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import net.finmath.marketdata.calibration.CalibratedCurves;
import net.finmath.marketdata.model.AnalyticModel;
import net.finmath.marketdata.model.AnalyticModelFromCurvesAndVols;
import net.finmath.marketdata.model.curves.Curve;
import net.finmath.marketdata.model.curves.DiscountCurveInterpolation;
import net.finmath.marketdata.model.curves.ForwardCurveInterpolation;
import net.finmath.marketdata.model.curves.ForwardCurveWithFixings;
import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationDataItem.Spec;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {Calibrator.class})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class CalibratorDiffblueTest {
  @MockBean private CalibrationContext calibrationContext;

  @MockBean private CalibrationDataItem calibrationDataItem;

  @Autowired private Calibrator calibrator;

  /**
   * Test {@link Calibrator#getCalibratedCurves()}.
   *
   * <p>Method under test: {@link Calibrator#getCalibratedCurves()}
   */
  @Test
  @DisplayName("Test getCalibratedCurves()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"CalibratedCurves Calibrator.getCalibratedCurves()"})
  void testGetCalibratedCurves() {
    // Arrange
    ArrayList<CalibrationDataItem> fixings = new ArrayList<>();

    // Act and Assert
    assertNull(
        new Calibrator(
                fixings, new CalibrationContextImpl(LocalDate.of(1970, 1, 1).atStartOfDay(), 10.0d))
            .getCalibratedCurves());
  }

  /**
   * Test {@link Calibrator#calibrateModel(Stream, CalibrationContext)}.
   *
   * <p>Method under test: {@link Calibrator#calibrateModel(Stream, CalibrationContext)}
   */
  @Test
  @DisplayName("Test calibrateModel(Stream, CalibrationContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Optional Calibrator.calibrateModel(Stream, CalibrationContext)"})
  void testCalibrateModel() throws CloneNotSupportedException {
    // Arrange
    ArrayList<CalibrationDataItem> fixings = new ArrayList<>();
    Calibrator calibrator =
        new Calibrator(
            fixings, new CalibrationContextImpl(LocalDate.of(1970, 1, 1).atStartOfDay(), 10.0d));

    ArrayList<CalibrationSpecProvider> calibrationSpecProviderList = new ArrayList<>();
    Stream<CalibrationSpecProvider> providers = calibrationSpecProviderList.stream();

    // Act and Assert
    AnalyticModel calibratedModel =
        calibrator
            .calibrateModel(
                providers,
                new CalibrationContextImpl(LocalDate.of(1970, 1, 1).atStartOfDay(), 10.0d))
            .get()
            .getCalibratedModel();
    assertTrue(calibratedModel instanceof AnalyticModelFromCurvesAndVols);
    Map<String, Curve> curves = calibratedModel.getCurves();
    assertEquals(5, curves.size());
    Curve getResult = curves.get(Calibrator.DISCOUNT_EUR_OIS);
    assertTrue(getResult instanceof DiscountCurveInterpolation);
    Curve getResult2 = curves.get("forward-EUR-1M");
    assertTrue(getResult2 instanceof ForwardCurveInterpolation);
    Curve getResult3 = curves.get("forward-EUR-3M");
    assertTrue(getResult3 instanceof ForwardCurveInterpolation);
    assertArrayEquals(new double[] {}, getResult2.getParameter(), 0.0);
    assertArrayEquals(new double[] {}, getResult3.getParameter(), 0.0);
    assertArrayEquals(new double[] {}, getResult.getParameter(), 0.0);
    assertArrayEquals(new double[] {}, ((ForwardCurveInterpolation) getResult2).getTimes(), 0.0);
    assertArrayEquals(new double[] {}, ((ForwardCurveInterpolation) getResult3).getTimes(), 0.0);
    assertArrayEquals(
        new double[] {0.0d}, ((DiscountCurveInterpolation) getResult).getTimes(), 0.0);
  }

  /**
   * Test {@link Calibrator#calibrateModel(Stream, CalibrationContext)}.
   *
   * <p>Method under test: {@link Calibrator#calibrateModel(Stream, CalibrationContext)}
   */
  @Test
  @DisplayName("Test calibrateModel(Stream, CalibrationContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Optional Calibrator.calibrateModel(Stream, CalibrationContext)"})
  void testCalibrateModel2() throws CloneNotSupportedException {
    // Arrange
    ArrayList<CalibrationDataItem> fixings = new ArrayList<>();
    Spec spec =
        new Spec(
            Calibrator.DISCOUNT_EUR_OIS,
            Calibrator.DISCOUNT_EUR_OIS,
            Calibrator.DISCOUNT_EUR_OIS,
            Calibrator.DISCOUNT_EUR_OIS);

    fixings.add(new CalibrationDataItem(spec, 1.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));
    Spec spec2 =
        new Spec(
            Calibrator.DISCOUNT_EUR_OIS,
            Calibrator.DISCOUNT_EUR_OIS,
            Calibrator.DISCOUNT_EUR_OIS,
            Calibrator.DISCOUNT_EUR_OIS);

    fixings.add(new CalibrationDataItem(spec2, 1.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));
    Calibrator calibrator =
        new Calibrator(
            fixings, new CalibrationContextImpl(LocalDate.of(1970, 1, 1).atStartOfDay(), 10.0d));

    ArrayList<CalibrationSpecProvider> calibrationSpecProviderList = new ArrayList<>();
    Stream<CalibrationSpecProvider> providers = calibrationSpecProviderList.stream();

    // Act and Assert
    AnalyticModel calibratedModel =
        calibrator
            .calibrateModel(
                providers,
                new CalibrationContextImpl(LocalDate.of(1970, 1, 1).atStartOfDay(), 10.0d))
            .get()
            .getCalibratedModel();
    assertTrue(calibratedModel instanceof AnalyticModelFromCurvesAndVols);
    Map<String, Curve> curves = calibratedModel.getCurves();
    assertEquals(5, curves.size());
    Curve getResult = curves.get(Calibrator.DISCOUNT_EUR_OIS);
    assertTrue(getResult instanceof DiscountCurveInterpolation);
    Curve getResult2 = curves.get("forward-EUR-1M");
    assertTrue(getResult2 instanceof ForwardCurveInterpolation);
    Curve getResult3 = curves.get("forward-EUR-3M");
    assertTrue(getResult3 instanceof ForwardCurveInterpolation);
    assertArrayEquals(new double[] {}, getResult2.getParameter(), 0.0);
    assertArrayEquals(new double[] {}, getResult3.getParameter(), 0.0);
    assertArrayEquals(new double[] {}, getResult.getParameter(), 0.0);
    assertArrayEquals(new double[] {}, ((ForwardCurveInterpolation) getResult2).getTimes(), 0.0);
    assertArrayEquals(new double[] {}, ((ForwardCurveInterpolation) getResult3).getTimes(), 0.0);
    assertArrayEquals(
        new double[] {0.0d}, ((DiscountCurveInterpolation) getResult).getTimes(), 0.0);
  }

  /**
   * Test {@link Calibrator#calibrateModel(Stream, CalibrationContext)}.
   *
   * <p>Method under test: {@link Calibrator#calibrateModel(Stream, CalibrationContext)}
   */
  @Test
  @DisplayName("Test calibrateModel(Stream, CalibrationContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Optional Calibrator.calibrateModel(Stream, CalibrationContext)"})
  void testCalibrateModel3() throws CloneNotSupportedException {
    // Arrange
    ArrayList<CalibrationDataItem> fixings = new ArrayList<>();
    Spec spec =
        new Spec(
            Calibrator.DISCOUNT_EUR_OIS,
            "ESTR",
            Calibrator.DISCOUNT_EUR_OIS,
            Calibrator.DISCOUNT_EUR_OIS);

    fixings.add(new CalibrationDataItem(spec, 1.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));
    Calibrator calibrator =
        new Calibrator(
            fixings, new CalibrationContextImpl(LocalDate.of(1970, 1, 1).atStartOfDay(), 10.0d));

    ArrayList<CalibrationSpecProvider> calibrationSpecProviderList = new ArrayList<>();
    Stream<CalibrationSpecProvider> providers = calibrationSpecProviderList.stream();

    // Act and Assert
    AnalyticModel calibratedModel =
        calibrator
            .calibrateModel(
                providers,
                new CalibrationContextImpl(LocalDate.of(1970, 1, 1).atStartOfDay(), 10.0d))
            .get()
            .getCalibratedModel();
    assertTrue(calibratedModel instanceof AnalyticModelFromCurvesAndVols);
    Map<String, Curve> curves = calibratedModel.getCurves();
    assertEquals(5, curves.size());
    Curve getResult = curves.get(Calibrator.DISCOUNT_EUR_OIS);
    assertTrue(getResult instanceof DiscountCurveInterpolation);
    Curve getResult2 = curves.get("forward-EUR-1M");
    assertTrue(getResult2 instanceof ForwardCurveInterpolation);
    Curve getResult3 = curves.get("forward-EUR-3M");
    assertTrue(getResult3 instanceof ForwardCurveInterpolation);
    assertArrayEquals(new double[] {}, getResult2.getParameter(), 0.0);
    assertArrayEquals(new double[] {}, getResult3.getParameter(), 0.0);
    assertArrayEquals(new double[] {}, getResult.getParameter(), 0.0);
    assertArrayEquals(new double[] {}, ((ForwardCurveInterpolation) getResult2).getTimes(), 0.0);
    assertArrayEquals(new double[] {}, ((ForwardCurveInterpolation) getResult3).getTimes(), 0.0);
    assertArrayEquals(
        new double[] {0.0d}, ((DiscountCurveInterpolation) getResult).getTimes(), 0.0);
  }

  /**
   * Test {@link Calibrator#calibrateModel(Stream, CalibrationContext)}.
   *
   * <p>Method under test: {@link Calibrator#calibrateModel(Stream, CalibrationContext)}
   */
  @Test
  @DisplayName("Test calibrateModel(Stream, CalibrationContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Optional Calibrator.calibrateModel(Stream, CalibrationContext)"})
  void testCalibrateModel4() throws CloneNotSupportedException {
    // Arrange
    ArrayList<CalibrationDataItem> fixings = new ArrayList<>();
    Spec spec =
        new Spec(
            Calibrator.DISCOUNT_EUR_OIS,
            "Euribor1M",
            Calibrator.DISCOUNT_EUR_OIS,
            Calibrator.DISCOUNT_EUR_OIS);

    fixings.add(new CalibrationDataItem(spec, 1.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));
    Calibrator calibrator =
        new Calibrator(
            fixings, new CalibrationContextImpl(LocalDate.of(1970, 1, 1).atStartOfDay(), 10.0d));

    ArrayList<CalibrationSpecProvider> calibrationSpecProviderList = new ArrayList<>();
    Stream<CalibrationSpecProvider> providers = calibrationSpecProviderList.stream();

    // Act and Assert
    AnalyticModel calibratedModel =
        calibrator
            .calibrateModel(
                providers,
                new CalibrationContextImpl(LocalDate.of(1970, 1, 1).atStartOfDay(), 10.0d))
            .get()
            .getCalibratedModel();
    assertTrue(calibratedModel instanceof AnalyticModelFromCurvesAndVols);
    Map<String, Curve> curves = calibratedModel.getCurves();
    assertEquals(5, curves.size());
    Curve getResult = curves.get(Calibrator.DISCOUNT_EUR_OIS);
    assertTrue(getResult instanceof DiscountCurveInterpolation);
    Curve getResult2 = curves.get("forward-EUR-3M");
    assertTrue(getResult2 instanceof ForwardCurveInterpolation);
    Curve getResult3 = curves.get("forward-EUR-1M");
    assertTrue(getResult3 instanceof ForwardCurveWithFixings);
    assertArrayEquals(new double[] {}, getResult3.getParameter(), 0.0);
    assertArrayEquals(new double[] {}, getResult2.getParameter(), 0.0);
    assertArrayEquals(new double[] {}, getResult.getParameter(), 0.0);
    assertArrayEquals(new double[] {}, ((ForwardCurveInterpolation) getResult2).getTimes(), 0.0);
    assertArrayEquals(
        new double[] {0.0d}, ((DiscountCurveInterpolation) getResult).getTimes(), 0.0);
  }

  /**
   * Test {@link Calibrator#calibrateModel(Stream, CalibrationContext)}.
   *
   * <p>Method under test: {@link Calibrator#calibrateModel(Stream, CalibrationContext)}
   */
  @Test
  @DisplayName("Test calibrateModel(Stream, CalibrationContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Optional Calibrator.calibrateModel(Stream, CalibrationContext)"})
  void testCalibrateModel5() throws CloneNotSupportedException {
    // Arrange
    ArrayList<CalibrationDataItem> fixings = new ArrayList<>();
    Spec spec =
        new Spec(
            Calibrator.DISCOUNT_EUR_OIS,
            "Euribor3M",
            Calibrator.DISCOUNT_EUR_OIS,
            Calibrator.DISCOUNT_EUR_OIS);

    fixings.add(new CalibrationDataItem(spec, 1.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));
    Calibrator calibrator =
        new Calibrator(
            fixings, new CalibrationContextImpl(LocalDate.of(1970, 1, 1).atStartOfDay(), 10.0d));

    ArrayList<CalibrationSpecProvider> calibrationSpecProviderList = new ArrayList<>();
    Stream<CalibrationSpecProvider> providers = calibrationSpecProviderList.stream();

    // Act and Assert
    AnalyticModel calibratedModel =
        calibrator
            .calibrateModel(
                providers,
                new CalibrationContextImpl(LocalDate.of(1970, 1, 1).atStartOfDay(), 10.0d))
            .get()
            .getCalibratedModel();
    assertTrue(calibratedModel instanceof AnalyticModelFromCurvesAndVols);
    Map<String, Curve> curves = calibratedModel.getCurves();
    assertEquals(5, curves.size());
    Curve getResult = curves.get(Calibrator.DISCOUNT_EUR_OIS);
    assertTrue(getResult instanceof DiscountCurveInterpolation);
    Curve getResult2 = curves.get("forward-EUR-1M");
    assertTrue(getResult2 instanceof ForwardCurveInterpolation);
    Curve getResult3 = curves.get("forward-EUR-3M");
    assertTrue(getResult3 instanceof ForwardCurveWithFixings);
    assertArrayEquals(new double[] {}, getResult2.getParameter(), 0.0);
    assertArrayEquals(new double[] {}, getResult3.getParameter(), 0.0);
    assertArrayEquals(new double[] {}, getResult.getParameter(), 0.0);
    assertArrayEquals(new double[] {}, ((ForwardCurveInterpolation) getResult2).getTimes(), 0.0);
    assertArrayEquals(
        new double[] {0.0d}, ((DiscountCurveInterpolation) getResult).getTimes(), 0.0);
  }

  /**
   * Test {@link Calibrator#calibrateModel(Stream, CalibrationContext)}.
   *
   * <p>Method under test: {@link Calibrator#calibrateModel(Stream, CalibrationContext)}
   */
  @Test
  @DisplayName("Test calibrateModel(Stream, CalibrationContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Optional Calibrator.calibrateModel(Stream, CalibrationContext)"})
  void testCalibrateModel6() throws CloneNotSupportedException {
    // Arrange
    ArrayList<CalibrationDataItem> fixings = new ArrayList<>();
    Spec spec =
        new Spec(
            Calibrator.DISCOUNT_EUR_OIS,
            "Euribor1M",
            Calibrator.DISCOUNT_EUR_OIS,
            Calibrator.DISCOUNT_EUR_OIS);

    fixings.add(new CalibrationDataItem(spec, 1.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));
    Spec spec2 =
        new Spec(
            Calibrator.DISCOUNT_EUR_OIS,
            Calibrator.DISCOUNT_EUR_OIS,
            Calibrator.DISCOUNT_EUR_OIS,
            Calibrator.DISCOUNT_EUR_OIS);

    fixings.add(new CalibrationDataItem(spec2, 1.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));
    Calibrator calibrator =
        new Calibrator(
            fixings, new CalibrationContextImpl(LocalDate.of(1970, 1, 1).atStartOfDay(), 10.0d));

    ArrayList<CalibrationSpecProvider> calibrationSpecProviderList = new ArrayList<>();
    Stream<CalibrationSpecProvider> providers = calibrationSpecProviderList.stream();

    // Act and Assert
    AnalyticModel calibratedModel =
        calibrator
            .calibrateModel(
                providers,
                new CalibrationContextImpl(LocalDate.of(1970, 1, 1).atStartOfDay(), 10.0d))
            .get()
            .getCalibratedModel();
    assertTrue(calibratedModel instanceof AnalyticModelFromCurvesAndVols);
    Map<String, Curve> curves = calibratedModel.getCurves();
    assertEquals(5, curves.size());
    Curve getResult = curves.get(Calibrator.DISCOUNT_EUR_OIS);
    assertTrue(getResult instanceof DiscountCurveInterpolation);
    Curve getResult2 = curves.get("forward-EUR-3M");
    assertTrue(getResult2 instanceof ForwardCurveInterpolation);
    Curve getResult3 = curves.get("forward-EUR-1M");
    assertTrue(getResult3 instanceof ForwardCurveWithFixings);
    assertArrayEquals(new double[] {}, getResult3.getParameter(), 0.0);
    assertArrayEquals(new double[] {}, getResult2.getParameter(), 0.0);
    assertArrayEquals(new double[] {}, getResult.getParameter(), 0.0);
    assertArrayEquals(new double[] {}, ((ForwardCurveInterpolation) getResult2).getTimes(), 0.0);
    assertArrayEquals(
        new double[] {0.0d}, ((DiscountCurveInterpolation) getResult).getTimes(), 0.0);
  }

  /**
   * Test {@link Calibrator#calibrateModel(Stream, CalibrationContext)}.
   *
   * <p>Method under test: {@link Calibrator#calibrateModel(Stream, CalibrationContext)}
   */
  @Test
  @DisplayName("Test calibrateModel(Stream, CalibrationContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Optional Calibrator.calibrateModel(Stream, CalibrationContext)"})
  void testCalibrateModel7() throws CloneNotSupportedException {
    // Arrange
    ArrayList<CalibrationDataItem> fixings = new ArrayList<>();
    Spec spec =
        new Spec(
            Calibrator.DISCOUNT_EUR_OIS,
            "Euribor3M",
            Calibrator.DISCOUNT_EUR_OIS,
            Calibrator.DISCOUNT_EUR_OIS);

    fixings.add(new CalibrationDataItem(spec, 1.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));
    Spec spec2 =
        new Spec(
            Calibrator.DISCOUNT_EUR_OIS,
            Calibrator.DISCOUNT_EUR_OIS,
            Calibrator.DISCOUNT_EUR_OIS,
            Calibrator.DISCOUNT_EUR_OIS);

    fixings.add(new CalibrationDataItem(spec2, 1.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));
    Calibrator calibrator =
        new Calibrator(
            fixings, new CalibrationContextImpl(LocalDate.of(1970, 1, 1).atStartOfDay(), 10.0d));

    ArrayList<CalibrationSpecProvider> calibrationSpecProviderList = new ArrayList<>();
    Stream<CalibrationSpecProvider> providers = calibrationSpecProviderList.stream();

    // Act and Assert
    AnalyticModel calibratedModel =
        calibrator
            .calibrateModel(
                providers,
                new CalibrationContextImpl(LocalDate.of(1970, 1, 1).atStartOfDay(), 10.0d))
            .get()
            .getCalibratedModel();
    assertTrue(calibratedModel instanceof AnalyticModelFromCurvesAndVols);
    Map<String, Curve> curves = calibratedModel.getCurves();
    assertEquals(5, curves.size());
    Curve getResult = curves.get(Calibrator.DISCOUNT_EUR_OIS);
    assertTrue(getResult instanceof DiscountCurveInterpolation);
    Curve getResult2 = curves.get("forward-EUR-1M");
    assertTrue(getResult2 instanceof ForwardCurveInterpolation);
    Curve getResult3 = curves.get("forward-EUR-3M");
    assertTrue(getResult3 instanceof ForwardCurveWithFixings);
    assertArrayEquals(new double[] {}, getResult2.getParameter(), 0.0);
    assertArrayEquals(new double[] {}, getResult3.getParameter(), 0.0);
    assertArrayEquals(new double[] {}, getResult.getParameter(), 0.0);
    assertArrayEquals(new double[] {}, ((ForwardCurveInterpolation) getResult2).getTimes(), 0.0);
    assertArrayEquals(
        new double[] {0.0d}, ((DiscountCurveInterpolation) getResult).getTimes(), 0.0);
  }

  /**
   * Test {@link Calibrator#calibrateModel(Stream, CalibrationContext)}.
   *
   * <ul>
   *   <li>Then return {@link Optional#get()} CalibratedModel ReferenceDate is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Calibrator#calibrateModel(Stream, CalibrationContext)}
   */
  @Test
  @DisplayName(
      "Test calibrateModel(Stream, CalibrationContext); then return get() CalibratedModel ReferenceDate is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Optional Calibrator.calibrateModel(Stream, CalibrationContext)"})
  void testCalibrateModel_thenReturnGetCalibratedModelReferenceDateIsNull()
      throws CloneNotSupportedException {
    // Arrange
    when(calibrationDataItem.getCurveName()).thenReturn("Curve Name");
    when(calibrationContext.getAccuracy()).thenReturn(10.0d);
    when(calibrationContext.getReferenceDate()).thenReturn(LocalDate.of(1970, 1, 1));

    ArrayList<CalibrationSpecProvider> calibrationSpecProviderList = new ArrayList<>();
    Stream<CalibrationSpecProvider> providers = calibrationSpecProviderList.stream();

    // Act
    Optional<CalibrationResult> actualCalibrateModelResult =
        calibrator.calibrateModel(providers, calibrationContext);

    // Assert
    verify(calibrationContext).getAccuracy();
    verify(calibrationContext, atLeast(1)).getReferenceDate();
    verify(calibrationDataItem, atLeast(1)).getCurveName();
    CalibrationResult getResult = actualCalibrateModelResult.get();
    AnalyticModel calibratedModel = getResult.getCalibratedModel();
    assertTrue(calibratedModel instanceof AnalyticModelFromCurvesAndVols);
    Map<String, Curve> curves = calibratedModel.getCurves();
    assertEquals(5, curves.size());
    assertTrue(curves.get(Calibrator.DISCOUNT_EUR_OIS) instanceof DiscountCurveInterpolation);
    assertTrue(curves.get("forward-EUR-1M") instanceof ForwardCurveInterpolation);
    assertTrue(curves.get("forward-EUR-3M") instanceof ForwardCurveInterpolation);
    assertNull(((AnalyticModelFromCurvesAndVols) calibratedModel).getReferenceDate());
    assertEquals(0.0d, getResult.getSumOfSquaredErrors());
    CalibratedCurves calibratedCurves = calibrator.getCalibratedCurves();
    assertEquals(2, calibratedCurves.getLastNumberOfInterations());
    assertTrue(calibratedModel.getVolatilitySurfaces().isEmpty());
    assertTrue(actualCalibrateModelResult.isPresent());
    assertEquals(Double.NaN, calibratedCurves.getLastAccuracy());
  }
}
