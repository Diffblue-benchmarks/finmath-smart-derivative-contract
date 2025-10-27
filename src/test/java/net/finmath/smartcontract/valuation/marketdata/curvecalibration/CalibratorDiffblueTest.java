package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import net.finmath.marketdata.calibration.CalibratedCurves;
import net.finmath.marketdata.model.AnalyticModel;
import net.finmath.marketdata.model.AnalyticModelFromCurvesAndVols;
import net.finmath.marketdata.model.curves.Curve;
import net.finmath.marketdata.model.curves.CurveInterpolation;
import net.finmath.marketdata.model.curves.DiscountCurveInterpolation;
import net.finmath.marketdata.model.curves.ForwardCurveInterpolation;
import net.finmath.time.businessdaycalendar.BusinessdayCalendar;
import net.finmath.time.businessdaycalendar.BusinessdayCalendarExcludingTARGETHolidays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {Calibrator.class})
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DisabledInAotMode
class CalibratorDiffblueTest {
  @MockBean
  private CalibrationContext calibrationContext;

  @MockBean
  private CalibrationDataItem calibrationDataItem;

  @Autowired
  private Calibrator calibrator;

  @Autowired
  private List<CalibrationDataItem> list;

  /**
   * Method under test: {@link Calibrator#getCalibratedCurves()}
   */
  @Test
  void testGetCalibratedCurves() {
    // Arrange
    ArrayList<CalibrationDataItem> fixings = new ArrayList<>();

    // Act and Assert
    assertNull((new Calibrator(fixings, new CalibrationContextImpl(LocalDate.of(1970, 1, 1).atStartOfDay(), 10.0d)))
        .getCalibratedCurves());
  }

  /**
   * Method under test:
   * {@link Calibrator#calibrateModel(Stream, CalibrationContext)}
   */
  @Test
  void testCalibrateModel() throws CloneNotSupportedException {
    // Arrange
    when(calibrationDataItem.getCurveName()).thenReturn("Curve Name");
    when(calibrationContext.getAccuracy()).thenReturn(10.0d);
    LocalDate ofResult = LocalDate.of(1970, 1, 1);
    when(calibrationContext.getReferenceDate()).thenReturn(ofResult);

    ArrayList<CalibrationSpecProvider> calibrationSpecProviderList = new ArrayList<>();
    Stream<CalibrationSpecProvider> providers = calibrationSpecProviderList.stream();

    // Act
    Optional<CalibrationResult> actualCalibrateModelResult = calibrator.calibrateModel(providers, calibrationContext);

    // Assert
    verify(calibrationContext).getAccuracy();
    verify(calibrationContext, atLeast(1)).getReferenceDate();
    verify(calibrationDataItem, atLeast(1)).getCurveName();
    CalibrationResult getResult = actualCalibrateModelResult.get();
    AnalyticModel calibratedModel = getResult.getCalibratedModel();
    assertTrue(calibratedModel instanceof AnalyticModelFromCurvesAndVols);
    Map<String, Curve> curves = calibratedModel.getCurves();
    assertEquals(5, curves.size());
    Curve getResult2 = curves.get("forward-EUR-1M");
    assertTrue(getResult2.getCloneBuilder() instanceof CurveInterpolation.Builder);
    Curve getResult3 = curves.get("forward-EUR-3M");
    assertTrue(getResult3.getCloneBuilder() instanceof CurveInterpolation.Builder);
    Curve getResult4 = curves.get(Calibrator.DISCOUNT_EUR_OIS);
    assertTrue(getResult4.getCloneBuilder() instanceof CurveInterpolation.Builder);
    assertTrue(getResult4 instanceof DiscountCurveInterpolation);
    assertTrue(getResult2 instanceof ForwardCurveInterpolation);
    assertTrue(getResult3 instanceof ForwardCurveInterpolation);
    assertTrue(((ForwardCurveInterpolation) getResult2)
        .getPaymentBusinessdayCalendar() instanceof BusinessdayCalendarExcludingTARGETHolidays);
    assertTrue(((ForwardCurveInterpolation) getResult3)
        .getPaymentBusinessdayCalendar() instanceof BusinessdayCalendarExcludingTARGETHolidays);
    LocalDate referenceDate = getResult4.getReferenceDate();
    assertEquals("1970-01-01", referenceDate.toString());
    assertEquals("1M", ((ForwardCurveInterpolation) getResult2).getPaymentOffsetCode());
    assertEquals("3M", ((ForwardCurveInterpolation) getResult3).getPaymentOffsetCode());
    assertEquals("forward-EUR-1M", getResult2.getName());
    assertEquals("forward-EUR-3M", getResult3.getName());
    assertNull(((AnalyticModelFromCurvesAndVols) calibratedModel).getReferenceDate());
    assertEquals(0, getResult2.getParameter().length);
    assertEquals(0, getResult3.getParameter().length);
    assertEquals(0, getResult4.getParameter().length);
    assertEquals(0, ((ForwardCurveInterpolation) getResult2).getTimes().length);
    assertEquals(0, ((ForwardCurveInterpolation) getResult3).getTimes().length);
    List<CurveInterpolation.Point> points = ((DiscountCurveInterpolation) getResult4).getPoints();
    assertEquals(1, points.size());
    CurveInterpolation.Point getResult5 = points.get(0);
    assertEquals(0.0d, getResult5.getTime());
    assertEquals(0.0d, getResult5.getValue());
    assertEquals(0.0d, getResult.getSumOfSquaredErrors());
    CalibratedCurves calibratedCurves = calibrator.getCalibratedCurves();
    assertEquals(2, calibratedCurves.getLastNumberOfInterations());
    assertEquals(CurveInterpolation.ExtrapolationMethod.CONSTANT,
        ((DiscountCurveInterpolation) getResult4).getExtrapolationMethod());
    assertEquals(CurveInterpolation.ExtrapolationMethod.CONSTANT,
        ((ForwardCurveInterpolation) getResult2).getExtrapolationMethod());
    assertEquals(CurveInterpolation.ExtrapolationMethod.CONSTANT,
        ((ForwardCurveInterpolation) getResult3).getExtrapolationMethod());
    assertEquals(CurveInterpolation.InterpolationEntity.LOG_OF_VALUE,
        ((DiscountCurveInterpolation) getResult4).getInterpolationEntity());
    assertEquals(CurveInterpolation.InterpolationEntity.VALUE,
        ((ForwardCurveInterpolation) getResult2).getInterpolationEntity());
    assertEquals(CurveInterpolation.InterpolationEntity.VALUE,
        ((ForwardCurveInterpolation) getResult3).getInterpolationEntity());
    assertEquals(CurveInterpolation.InterpolationMethod.LINEAR,
        ((DiscountCurveInterpolation) getResult4).getInterpolationMethod());
    assertEquals(CurveInterpolation.InterpolationMethod.LINEAR,
        ((ForwardCurveInterpolation) getResult2).getInterpolationMethod());
    assertEquals(CurveInterpolation.InterpolationMethod.LINEAR,
        ((ForwardCurveInterpolation) getResult3).getInterpolationMethod());
    assertEquals(ForwardCurveInterpolation.InterpolationEntityForward.FORWARD,
        ((ForwardCurveInterpolation) getResult2).getInterpolationEntityForward());
    assertEquals(ForwardCurveInterpolation.InterpolationEntityForward.FORWARD,
        ((ForwardCurveInterpolation) getResult3).getInterpolationEntityForward());
    assertEquals(BusinessdayCalendar.DateRollConvention.FOLLOWING,
        ((ForwardCurveInterpolation) getResult2).getPaymentDateRollConvention());
    assertEquals(BusinessdayCalendar.DateRollConvention.FOLLOWING,
        ((ForwardCurveInterpolation) getResult3).getPaymentDateRollConvention());
    assertFalse(getResult5.isParameter());
    assertTrue(((ForwardCurveInterpolation) getResult2).getPoints().isEmpty());
    assertTrue(((ForwardCurveInterpolation) getResult3).getPoints().isEmpty());
    assertTrue(calibratedModel.getVolatilitySurfaces().isEmpty());
    assertTrue(actualCalibrateModelResult.isPresent());
    assertEquals(Double.NaN, calibratedCurves.getLastAccuracy());
    assertEquals(Calibrator.DISCOUNT_EUR_OIS, ((ForwardCurveInterpolation) getResult2).getDiscountCurveName());
    assertEquals(Calibrator.DISCOUNT_EUR_OIS, ((ForwardCurveInterpolation) getResult3).getDiscountCurveName());
    assertEquals(Calibrator.DISCOUNT_EUR_OIS, getResult4.getName());
    assertSame(ofResult, getResult2.getReferenceDate());
    assertSame(ofResult, getResult3.getReferenceDate());
    assertSame(ofResult, referenceDate);
    assertArrayEquals(new double[]{0.0d}, ((DiscountCurveInterpolation) getResult4).getTimes(), 0.0);
  }
}
