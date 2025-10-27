package net.finmath.smartcontract.valuation.oracle.interestrates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Stream;
import net.finmath.marketdata.calibration.CalibratedCurves;
import net.finmath.marketdata.products.AnalyticProduct;
import net.finmath.marketdata.products.Cashflow;
import net.finmath.smartcontract.model.ExceptionId;
import net.finmath.smartcontract.model.SDCException;
import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationContext;
import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationDataItem;
import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationDataset;
import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationParser;
import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationSpecProvider;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ValuationOraclePlainSwapDiffblueTest {
  /**
   * Method under test:
   * {@link ValuationOraclePlainSwap#getAmount(LocalDateTime, LocalDateTime)}
   */
  @Test
  void testGetAmount() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    HashMap<String, AnalyticProduct> products = new HashMap<>();
    products.put("forward-EUR-OIS", new Cashflow("GBP", 1.0E-9d, 1.0E-9d, true, "3"));

    ArrayList<CalibrationDataset> scenarioList = new ArrayList<>();
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    scenarioList.add(new CalibrationDataset(curveDataPointSet, LocalDate.of(1970, 1, 1).atStartOfDay()));
    ValuationOraclePlainSwap valuationOraclePlainSwap = new ValuationOraclePlainSwap(products, scenarioList);
    LocalDateTime evaluationTime = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act and Assert
    assertThrows(SDCException.class,
        () -> valuationOraclePlainSwap.getAmount(evaluationTime, LocalDate.of(1970, 1, 1).atStartOfDay()));
  }

  /**
   * Method under test:
   * {@link ValuationOraclePlainSwap#getAmount(LocalDateTime, LocalDateTime)}
   */
  @Test
  void testGetAmount2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibrationDataset calibrationDataset = mock(CalibrationDataset.class);
    when(calibrationDataset.getDataPoints())
        .thenThrow(new SDCException(ExceptionId.SDC_AUTH_ERROR, "An error occurred"));
    when(calibrationDataset.getDate()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());

    ArrayList<CalibrationDataset> scenarioList = new ArrayList<>();
    scenarioList.add(calibrationDataset);
    ValuationOraclePlainSwap valuationOraclePlainSwap = new ValuationOraclePlainSwap(new HashMap<>(), scenarioList);
    LocalDateTime evaluationTime = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act and Assert
    assertThrows(SDCException.class,
        () -> valuationOraclePlainSwap.getAmount(evaluationTime, LocalDate.of(1970, 1, 1).atStartOfDay()));
    verify(calibrationDataset).getDataPoints();
    verify(calibrationDataset).getDate();
  }

  /**
   * Method under test:
   * {@link ValuationOraclePlainSwap#getAmount(LocalDateTime, LocalDateTime)}
   */
  @Test
  void testGetAmount3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibrationSpecProvider calibrationSpecProvider = mock(CalibrationSpecProvider.class);
    when(calibrationSpecProvider.getCalibrationSpec(Mockito.<CalibrationContext>any()))
        .thenReturn(new CalibratedCurves.CalibrationSpec("Type", new double[]{10.0d, 0.5d, 10.0d, 0.5d},
            "Forward Curve Receiver Name", 10.0d, "3", "Calibration Curve Name", 10.0d));

    ArrayList<CalibrationSpecProvider> calibrationSpecProviderList = new ArrayList<>();
    calibrationSpecProviderList.add(calibrationSpecProvider);
    Stream<CalibrationSpecProvider> streamResult = calibrationSpecProviderList.stream();
    CalibrationDataset calibrationDataset = mock(CalibrationDataset.class);
    when(calibrationDataset.getDataPoints()).thenReturn(new HashSet<>());
    when(calibrationDataset.getFixingDataItems()).thenReturn(new HashSet<>());
    when(calibrationDataset.getDataAsCalibrationDataPointStream(Mockito.<CalibrationParser>any()))
        .thenReturn(streamResult);
    when(calibrationDataset.getDate()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());

    ArrayList<CalibrationDataset> scenarioList = new ArrayList<>();
    scenarioList.add(calibrationDataset);
    ValuationOraclePlainSwap valuationOraclePlainSwap = new ValuationOraclePlainSwap(new HashMap<>(), scenarioList);
    LocalDateTime evaluationTime = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act and Assert
    assertThrows(SDCException.class,
        () -> valuationOraclePlainSwap.getAmount(evaluationTime, LocalDate.of(1970, 1, 1).atStartOfDay()));
    verify(calibrationDataset).getDataAsCalibrationDataPointStream(isA(CalibrationParser.class));
    verify(calibrationDataset, atLeast(1)).getDataPoints();
    verify(calibrationDataset).getDate();
    verify(calibrationDataset).getFixingDataItems();
    verify(calibrationSpecProvider).getCalibrationSpec(isA(CalibrationContext.class));
  }

  /**
   * Method under test:
   * {@link ValuationOraclePlainSwap#getAmount(LocalDateTime, LocalDateTime)}
   */
  @Test
  void testGetAmount4() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibrationSpecProvider calibrationSpecProvider = mock(CalibrationSpecProvider.class);
    when(calibrationSpecProvider.getCalibrationSpec(Mockito.<CalibrationContext>any()))
        .thenReturn(new CalibratedCurves.CalibrationSpec("Type", new double[]{10.0d, 0.5d, 10.0d, 0.5d},
            "Forward Curve Receiver Name", 10.0d, "3", "Calibration Curve Name", 10.0d));
    CalibrationSpecProvider calibrationSpecProvider2 = mock(CalibrationSpecProvider.class);
    when(calibrationSpecProvider2.getCalibrationSpec(Mockito.<CalibrationContext>any()))
        .thenThrow(new SDCException(ExceptionId.SDC_AUTH_ERROR, "An error occurred"));

    ArrayList<CalibrationSpecProvider> calibrationSpecProviderList = new ArrayList<>();
    calibrationSpecProviderList.add(calibrationSpecProvider2);
    calibrationSpecProviderList.add(calibrationSpecProvider);
    Stream<CalibrationSpecProvider> streamResult = calibrationSpecProviderList.stream();
    CalibrationDataset calibrationDataset = mock(CalibrationDataset.class);
    when(calibrationDataset.getDataPoints()).thenReturn(new HashSet<>());
    when(calibrationDataset.getFixingDataItems()).thenReturn(new HashSet<>());
    when(calibrationDataset.getDataAsCalibrationDataPointStream(Mockito.<CalibrationParser>any()))
        .thenReturn(streamResult);
    when(calibrationDataset.getDate()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());

    ArrayList<CalibrationDataset> scenarioList = new ArrayList<>();
    scenarioList.add(calibrationDataset);
    ValuationOraclePlainSwap valuationOraclePlainSwap = new ValuationOraclePlainSwap(new HashMap<>(), scenarioList);
    LocalDateTime evaluationTime = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act and Assert
    assertThrows(SDCException.class,
        () -> valuationOraclePlainSwap.getAmount(evaluationTime, LocalDate.of(1970, 1, 1).atStartOfDay()));
    verify(calibrationDataset).getDataAsCalibrationDataPointStream(isA(CalibrationParser.class));
    verify(calibrationDataset, atLeast(1)).getDataPoints();
    verify(calibrationDataset).getDate();
    verify(calibrationDataset).getFixingDataItems();
    verify(calibrationSpecProvider2).getCalibrationSpec(isA(CalibrationContext.class));
  }

  /**
   * Method under test:
   * {@link ValuationOraclePlainSwap#getValue(LocalDateTime, LocalDateTime)}
   */
  @Test
  void testGetValue() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ArrayList<CalibrationDataset> scenarioList = new ArrayList<>();
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    scenarioList.add(new CalibrationDataset(curveDataPointSet, LocalDate.of(1970, 1, 1).atStartOfDay()));
    ValuationOraclePlainSwap valuationOraclePlainSwap = new ValuationOraclePlainSwap(new HashMap<>(), scenarioList);
    LocalDateTime evaluationDate = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act and Assert
    assertNull(valuationOraclePlainSwap.getValue(evaluationDate, LocalDate.of(1970, 1, 1).atStartOfDay()));
  }

  /**
   * Method under test:
   * {@link ValuationOraclePlainSwap#getValue(LocalDateTime, LocalDateTime)}
   */
  @Test
  void testGetValue2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    HashMap<String, AnalyticProduct> products = new HashMap<>();
    products.put("forward-EUR-OIS", new Cashflow("GBP", 1.0E-9d, 1.0E-9d, true, "3"));

    ArrayList<CalibrationDataset> scenarioList = new ArrayList<>();
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    scenarioList.add(new CalibrationDataset(curveDataPointSet, LocalDate.of(1970, 1, 1).atStartOfDay()));
    ValuationOraclePlainSwap valuationOraclePlainSwap = new ValuationOraclePlainSwap(products, scenarioList);
    LocalDateTime evaluationDate = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act and Assert
    assertThrows(SDCException.class,
        () -> valuationOraclePlainSwap.getValue(evaluationDate, LocalDate.of(1970, 1, 1).atStartOfDay()));
  }

  /**
   * Method under test:
   * {@link ValuationOraclePlainSwap#getValue(LocalDateTime, LocalDateTime)}
   */
  @Test
  void testGetValue3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    HashMap<String, AnalyticProduct> products = new HashMap<>();
    products.put("forward-EUR-OIS", new Cashflow("GBP", 1.0E-9d, 1.0E-9d, true, "discount-EUR-OIS"));

    ArrayList<CalibrationDataset> scenarioList = new ArrayList<>();
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    scenarioList.add(new CalibrationDataset(curveDataPointSet, LocalDate.of(1970, 1, 1).atStartOfDay()));
    ValuationOraclePlainSwap valuationOraclePlainSwap = new ValuationOraclePlainSwap(products, scenarioList);
    LocalDateTime evaluationDate = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act and Assert
    assertNull(valuationOraclePlainSwap.getValue(evaluationDate, LocalDate.of(1970, 1, 1).atStartOfDay()));
  }

  /**
   * Method under test:
   * {@link ValuationOraclePlainSwap#getValue(LocalDateTime, LocalDateTime)}
   */
  @Test
  void testGetValue4() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("discount-EUR-OIS", "discount-EUR-OIS",
        "discount-EUR-OIS", "discount-EUR-OIS");

    curveDataPointSet.add(new CalibrationDataItem(spec, 1.0E-9d, LocalDate.of(1970, 1, 1).atStartOfDay()));
    CalibrationDataset calibrationDataset = new CalibrationDataset(curveDataPointSet,
        LocalDate.of(1970, 1, 1).atStartOfDay());

    ArrayList<CalibrationDataset> scenarioList = new ArrayList<>();
    scenarioList.add(calibrationDataset);
    ValuationOraclePlainSwap valuationOraclePlainSwap = new ValuationOraclePlainSwap(new HashMap<>(), scenarioList);
    LocalDateTime evaluationDate = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act and Assert
    assertNull(valuationOraclePlainSwap.getValue(evaluationDate, LocalDate.of(1970, 1, 1).atStartOfDay()));
  }

  /**
   * Method under test:
   * {@link ValuationOraclePlainSwap#getValue(LocalDateTime, LocalDateTime)}
   */
  @Test
  void testGetValue5() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("EURESTSD", "discount-EUR-OIS", "discount-EUR-OIS",
        "discount-EUR-OIS");

    curveDataPointSet.add(new CalibrationDataItem(spec, 1.0E-9d, LocalDate.of(1970, 1, 1).atStartOfDay()));
    CalibrationDataset calibrationDataset = new CalibrationDataset(curveDataPointSet,
        LocalDate.of(1970, 1, 1).atStartOfDay());

    ArrayList<CalibrationDataset> scenarioList = new ArrayList<>();
    scenarioList.add(calibrationDataset);
    ValuationOraclePlainSwap valuationOraclePlainSwap = new ValuationOraclePlainSwap(new HashMap<>(), scenarioList);
    LocalDateTime evaluationDate = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act and Assert
    assertNull(valuationOraclePlainSwap.getValue(evaluationDate, LocalDate.of(1970, 1, 1).atStartOfDay()));
  }

  /**
   * Method under test:
   * {@link ValuationOraclePlainSwap#getValue(LocalDateTime, LocalDateTime)}
   */
  @Test
  void testGetValue6() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("EUREST1D", "discount-EUR-OIS", "discount-EUR-OIS",
        "discount-EUR-OIS");

    curveDataPointSet.add(new CalibrationDataItem(spec, 1.0E-9d, LocalDate.of(1970, 1, 1).atStartOfDay()));
    CalibrationDataset calibrationDataset = new CalibrationDataset(curveDataPointSet,
        LocalDate.of(1970, 1, 1).atStartOfDay());

    ArrayList<CalibrationDataset> scenarioList = new ArrayList<>();
    scenarioList.add(calibrationDataset);
    ValuationOraclePlainSwap valuationOraclePlainSwap = new ValuationOraclePlainSwap(new HashMap<>(), scenarioList);
    LocalDateTime evaluationDate = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act and Assert
    assertNull(valuationOraclePlainSwap.getValue(evaluationDate, LocalDate.of(1970, 1, 1).atStartOfDay()));
  }

  /**
   * Method under test:
   * {@link ValuationOraclePlainSwap#getValue(LocalDateTime, LocalDateTime)}
   */
  @Test
  void testGetValue7() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("discount-EUR-OIS", "discount-EUR-OIS", "Fixing",
        "discount-EUR-OIS");

    curveDataPointSet.add(new CalibrationDataItem(spec, 1.0E-9d, LocalDate.of(1970, 1, 1).atStartOfDay()));
    CalibrationDataset calibrationDataset = new CalibrationDataset(curveDataPointSet,
        LocalDate.of(1970, 1, 1).atStartOfDay());

    ArrayList<CalibrationDataset> scenarioList = new ArrayList<>();
    scenarioList.add(calibrationDataset);
    ValuationOraclePlainSwap valuationOraclePlainSwap = new ValuationOraclePlainSwap(new HashMap<>(), scenarioList);
    LocalDateTime evaluationDate = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act and Assert
    assertNull(valuationOraclePlainSwap.getValue(evaluationDate, LocalDate.of(1970, 1, 1).atStartOfDay()));
  }

  /**
   * Method under test:
   * {@link ValuationOraclePlainSwap#getValue(LocalDateTime, LocalDateTime)}
   */
  @Test
  void testGetValue8() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("discount-EUR-OIS", "discount-EUR-OIS", "Deposit",
        "discount-EUR-OIS");

    curveDataPointSet.add(new CalibrationDataItem(spec, 1.0E-9d, LocalDate.of(1970, 1, 1).atStartOfDay()));
    CalibrationDataset calibrationDataset = new CalibrationDataset(curveDataPointSet,
        LocalDate.of(1970, 1, 1).atStartOfDay());

    ArrayList<CalibrationDataset> scenarioList = new ArrayList<>();
    scenarioList.add(calibrationDataset);
    ValuationOraclePlainSwap valuationOraclePlainSwap = new ValuationOraclePlainSwap(new HashMap<>(), scenarioList);
    LocalDateTime evaluationDate = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act and Assert
    assertNull(valuationOraclePlainSwap.getValue(evaluationDate, LocalDate.of(1970, 1, 1).atStartOfDay()));
  }

  /**
   * Method under test:
   * {@link ValuationOraclePlainSwap#getValue(LocalDateTime, LocalDateTime)}
   */
  @Test
  void testGetValue9() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibrationDataset calibrationDataset = mock(CalibrationDataset.class);
    when(calibrationDataset.getDataPoints()).thenReturn(new HashSet<>());
    when(calibrationDataset.getFixingDataItems()).thenReturn(new HashSet<>());

    ArrayList<CalibrationSpecProvider> calibrationSpecProviderList = new ArrayList<>();
    Stream<CalibrationSpecProvider> streamResult = calibrationSpecProviderList.stream();
    when(calibrationDataset.getDataAsCalibrationDataPointStream(Mockito.<CalibrationParser>any()))
        .thenReturn(streamResult);
    when(calibrationDataset.getDate()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());

    ArrayList<CalibrationDataset> scenarioList = new ArrayList<>();
    scenarioList.add(calibrationDataset);
    ValuationOraclePlainSwap valuationOraclePlainSwap = new ValuationOraclePlainSwap(new HashMap<>(), scenarioList);
    LocalDateTime evaluationDate = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act
    BigDecimal actualValue = valuationOraclePlainSwap.getValue(evaluationDate, LocalDate.of(1970, 1, 1).atStartOfDay());

    // Assert
    verify(calibrationDataset).getDataAsCalibrationDataPointStream(isA(CalibrationParser.class));
    verify(calibrationDataset, atLeast(1)).getDataPoints();
    verify(calibrationDataset).getDate();
    verify(calibrationDataset).getFixingDataItems();
    assertNull(actualValue);
  }

  /**
   * Method under test:
   * {@link ValuationOraclePlainSwap#getValue(LocalDateTime, LocalDateTime)}
   */
  @Test
  void testGetValue10() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibrationDataset calibrationDataset = mock(CalibrationDataset.class);
    when(calibrationDataset.getDataPoints())
        .thenThrow(new SDCException(ExceptionId.SDC_AUTH_ERROR, "An error occurred"));
    when(calibrationDataset.getDate()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());

    ArrayList<CalibrationDataset> scenarioList = new ArrayList<>();
    scenarioList.add(calibrationDataset);
    ValuationOraclePlainSwap valuationOraclePlainSwap = new ValuationOraclePlainSwap(new HashMap<>(), scenarioList);
    LocalDateTime evaluationDate = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act and Assert
    assertThrows(SDCException.class,
        () -> valuationOraclePlainSwap.getValue(evaluationDate, LocalDate.of(1970, 1, 1).atStartOfDay()));
    verify(calibrationDataset).getDataPoints();
    verify(calibrationDataset).getDate();
  }

  /**
   * Method under test:
   * {@link ValuationOraclePlainSwap#getValue(LocalDateTime, LocalDateTime)}
   */
  @Test
  void testGetValue11() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibrationSpecProvider calibrationSpecProvider = mock(CalibrationSpecProvider.class);
    when(calibrationSpecProvider.getCalibrationSpec(Mockito.<CalibrationContext>any()))
        .thenReturn(new CalibratedCurves.CalibrationSpec("Type", new double[]{10.0d, 0.5d, 10.0d, 0.5d},
            "Forward Curve Receiver Name", 10.0d, "3", "Calibration Curve Name", 10.0d));

    ArrayList<CalibrationSpecProvider> calibrationSpecProviderList = new ArrayList<>();
    calibrationSpecProviderList.add(calibrationSpecProvider);
    Stream<CalibrationSpecProvider> streamResult = calibrationSpecProviderList.stream();
    CalibrationDataset calibrationDataset = mock(CalibrationDataset.class);
    when(calibrationDataset.getDataPoints()).thenReturn(new HashSet<>());
    when(calibrationDataset.getFixingDataItems()).thenReturn(new HashSet<>());
    when(calibrationDataset.getDataAsCalibrationDataPointStream(Mockito.<CalibrationParser>any()))
        .thenReturn(streamResult);
    when(calibrationDataset.getDate()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());

    ArrayList<CalibrationDataset> scenarioList = new ArrayList<>();
    scenarioList.add(calibrationDataset);
    ValuationOraclePlainSwap valuationOraclePlainSwap = new ValuationOraclePlainSwap(new HashMap<>(), scenarioList);
    LocalDateTime evaluationDate = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act and Assert
    assertThrows(SDCException.class,
        () -> valuationOraclePlainSwap.getValue(evaluationDate, LocalDate.of(1970, 1, 1).atStartOfDay()));
    verify(calibrationDataset).getDataAsCalibrationDataPointStream(isA(CalibrationParser.class));
    verify(calibrationDataset, atLeast(1)).getDataPoints();
    verify(calibrationDataset).getDate();
    verify(calibrationDataset).getFixingDataItems();
    verify(calibrationSpecProvider).getCalibrationSpec(isA(CalibrationContext.class));
  }

  /**
   * Method under test:
   * {@link ValuationOraclePlainSwap#getValue(LocalDateTime, LocalDateTime)}
   */
  @Test
  void testGetValue12() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibrationSpecProvider calibrationSpecProvider = mock(CalibrationSpecProvider.class);
    when(calibrationSpecProvider.getCalibrationSpec(Mockito.<CalibrationContext>any()))
        .thenReturn(new CalibratedCurves.CalibrationSpec("Type", new double[]{10.0d, 0.5d, 10.0d, 0.5d},
            "Forward Curve Receiver Name", 10.0d, "3", "Calibration Curve Name", 10.0d));
    CalibrationSpecProvider calibrationSpecProvider2 = mock(CalibrationSpecProvider.class);
    when(calibrationSpecProvider2.getCalibrationSpec(Mockito.<CalibrationContext>any()))
        .thenThrow(new SDCException(ExceptionId.SDC_AUTH_ERROR, "An error occurred"));

    ArrayList<CalibrationSpecProvider> calibrationSpecProviderList = new ArrayList<>();
    calibrationSpecProviderList.add(calibrationSpecProvider2);
    calibrationSpecProviderList.add(calibrationSpecProvider);
    Stream<CalibrationSpecProvider> streamResult = calibrationSpecProviderList.stream();
    CalibrationDataset calibrationDataset = mock(CalibrationDataset.class);
    when(calibrationDataset.getDataPoints()).thenReturn(new HashSet<>());
    when(calibrationDataset.getFixingDataItems()).thenReturn(new HashSet<>());
    when(calibrationDataset.getDataAsCalibrationDataPointStream(Mockito.<CalibrationParser>any()))
        .thenReturn(streamResult);
    when(calibrationDataset.getDate()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());

    ArrayList<CalibrationDataset> scenarioList = new ArrayList<>();
    scenarioList.add(calibrationDataset);
    ValuationOraclePlainSwap valuationOraclePlainSwap = new ValuationOraclePlainSwap(new HashMap<>(), scenarioList);
    LocalDateTime evaluationDate = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act and Assert
    assertThrows(SDCException.class,
        () -> valuationOraclePlainSwap.getValue(evaluationDate, LocalDate.of(1970, 1, 1).atStartOfDay()));
    verify(calibrationDataset).getDataAsCalibrationDataPointStream(isA(CalibrationParser.class));
    verify(calibrationDataset, atLeast(1)).getDataPoints();
    verify(calibrationDataset).getDate();
    verify(calibrationDataset).getFixingDataItems();
    verify(calibrationSpecProvider2).getCalibrationSpec(isA(CalibrationContext.class));
  }

  /**
   * Method under test:
   * {@link ValuationOraclePlainSwap#getValues(LocalDateTime, LocalDateTime)}
   */
  @Test
  void testGetValues() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    HashMap<String, AnalyticProduct> products = new HashMap<>();
    ValuationOraclePlainSwap valuationOraclePlainSwap = new ValuationOraclePlainSwap(products, new ArrayList<>());
    LocalDateTime evaluationDate = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act and Assert
    assertNull(valuationOraclePlainSwap.getValues(evaluationDate, LocalDate.of(1970, 1, 1).atStartOfDay()));
  }

  /**
   * Method under test:
   * {@link ValuationOraclePlainSwap#getValues(LocalDateTime, LocalDateTime)}
   */
  @Test
  void testGetValues2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ArrayList<CalibrationDataset> scenarioList = new ArrayList<>();
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    scenarioList.add(new CalibrationDataset(curveDataPointSet, LocalDate.of(1970, 1, 1).atStartOfDay()));
    ValuationOraclePlainSwap valuationOraclePlainSwap = new ValuationOraclePlainSwap(new HashMap<>(), scenarioList);
    LocalDateTime evaluationDate = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act and Assert
    assertTrue(valuationOraclePlainSwap.getValues(evaluationDate, LocalDate.of(1970, 1, 1).atStartOfDay()).isEmpty());
  }

  /**
   * Method under test:
   * {@link ValuationOraclePlainSwap#getValues(LocalDateTime, LocalDateTime)}
   */
  @Test
  void testGetValues3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    HashMap<String, AnalyticProduct> products = new HashMap<>();
    products.computeIfPresent("foo", mock(BiFunction.class));
    ValuationOraclePlainSwap valuationOraclePlainSwap = new ValuationOraclePlainSwap(products, new ArrayList<>());
    LocalDateTime evaluationDate = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act and Assert
    assertNull(valuationOraclePlainSwap.getValues(evaluationDate, LocalDate.of(1970, 1, 1).atStartOfDay()));
  }

  /**
   * Method under test:
   * {@link ValuationOraclePlainSwap#getValues(LocalDateTime, LocalDateTime)}
   */
  @Test
  void testGetValues4() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    HashMap<String, AnalyticProduct> products = new HashMap<>();
    products.put("forward-EUR-OIS", new Cashflow("GBP", 1.0E-9d, 1.0E-9d, true, "3"));

    ArrayList<CalibrationDataset> scenarioList = new ArrayList<>();
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    scenarioList.add(new CalibrationDataset(curveDataPointSet, LocalDate.of(1970, 1, 1).atStartOfDay()));
    ValuationOraclePlainSwap valuationOraclePlainSwap = new ValuationOraclePlainSwap(products, scenarioList);
    LocalDateTime evaluationDate = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act and Assert
    assertThrows(SDCException.class,
        () -> valuationOraclePlainSwap.getValues(evaluationDate, LocalDate.of(1970, 1, 1).atStartOfDay()));
  }

  /**
   * Method under test:
   * {@link ValuationOraclePlainSwap#getValues(LocalDateTime, LocalDateTime)}
   */
  @Test
  void testGetValues5() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    HashMap<String, AnalyticProduct> products = new HashMap<>();
    products.put("forward-EUR-OIS", new Cashflow("GBP", 1.0E-9d, 1.0E-9d, true, "discount-EUR-OIS"));

    ArrayList<CalibrationDataset> scenarioList = new ArrayList<>();
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    scenarioList.add(new CalibrationDataset(curveDataPointSet, LocalDate.of(1970, 1, 1).atStartOfDay()));
    ValuationOraclePlainSwap valuationOraclePlainSwap = new ValuationOraclePlainSwap(products, scenarioList);
    LocalDateTime evaluationDate = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act
    Map<String, BigDecimal> actualValues = valuationOraclePlainSwap.getValues(evaluationDate,
        LocalDate.of(1970, 1, 1).atStartOfDay());

    // Assert
    assertEquals(1, actualValues.size());
    BigDecimal expectedGetResult = new BigDecimal("0.00");
    assertEquals(expectedGetResult, actualValues.get("forward-EUR-OIS"));
  }

  /**
   * Method under test:
   * {@link ValuationOraclePlainSwap#getValues(LocalDateTime, LocalDateTime)}
   */
  @Test
  void testGetValues6() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("discount-EUR-OIS", "discount-EUR-OIS",
        "discount-EUR-OIS", "discount-EUR-OIS");

    curveDataPointSet.add(new CalibrationDataItem(spec, 1.0E-9d, LocalDate.of(1970, 1, 1).atStartOfDay()));
    CalibrationDataset calibrationDataset = new CalibrationDataset(curveDataPointSet,
        LocalDate.of(1970, 1, 1).atStartOfDay());

    ArrayList<CalibrationDataset> scenarioList = new ArrayList<>();
    scenarioList.add(calibrationDataset);
    ValuationOraclePlainSwap valuationOraclePlainSwap = new ValuationOraclePlainSwap(new HashMap<>(), scenarioList);
    LocalDateTime evaluationDate = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act and Assert
    assertTrue(valuationOraclePlainSwap.getValues(evaluationDate, LocalDate.of(1970, 1, 1).atStartOfDay()).isEmpty());
  }

  /**
   * Method under test:
   * {@link ValuationOraclePlainSwap#getValues(LocalDateTime, LocalDateTime)}
   */
  @Test
  void testGetValues7() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("EURESTSD", "discount-EUR-OIS", "discount-EUR-OIS",
        "discount-EUR-OIS");

    curveDataPointSet.add(new CalibrationDataItem(spec, 1.0E-9d, LocalDate.of(1970, 1, 1).atStartOfDay()));
    CalibrationDataset calibrationDataset = new CalibrationDataset(curveDataPointSet,
        LocalDate.of(1970, 1, 1).atStartOfDay());

    ArrayList<CalibrationDataset> scenarioList = new ArrayList<>();
    scenarioList.add(calibrationDataset);
    ValuationOraclePlainSwap valuationOraclePlainSwap = new ValuationOraclePlainSwap(new HashMap<>(), scenarioList);
    LocalDateTime evaluationDate = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act and Assert
    assertTrue(valuationOraclePlainSwap.getValues(evaluationDate, LocalDate.of(1970, 1, 1).atStartOfDay()).isEmpty());
  }

  /**
   * Method under test:
   * {@link ValuationOraclePlainSwap#getValues(LocalDateTime, LocalDateTime)}
   */
  @Test
  void testGetValues8() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("EUREST1D", "discount-EUR-OIS", "discount-EUR-OIS",
        "discount-EUR-OIS");

    curveDataPointSet.add(new CalibrationDataItem(spec, 1.0E-9d, LocalDate.of(1970, 1, 1).atStartOfDay()));
    CalibrationDataset calibrationDataset = new CalibrationDataset(curveDataPointSet,
        LocalDate.of(1970, 1, 1).atStartOfDay());

    ArrayList<CalibrationDataset> scenarioList = new ArrayList<>();
    scenarioList.add(calibrationDataset);
    ValuationOraclePlainSwap valuationOraclePlainSwap = new ValuationOraclePlainSwap(new HashMap<>(), scenarioList);
    LocalDateTime evaluationDate = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act and Assert
    assertTrue(valuationOraclePlainSwap.getValues(evaluationDate, LocalDate.of(1970, 1, 1).atStartOfDay()).isEmpty());
  }

  /**
   * Method under test:
   * {@link ValuationOraclePlainSwap#getValues(LocalDateTime, LocalDateTime)}
   */
  @Test
  void testGetValues9() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("discount-EUR-OIS", "discount-EUR-OIS", "Fixing",
        "discount-EUR-OIS");

    curveDataPointSet.add(new CalibrationDataItem(spec, 1.0E-9d, LocalDate.of(1970, 1, 1).atStartOfDay()));
    CalibrationDataset calibrationDataset = new CalibrationDataset(curveDataPointSet,
        LocalDate.of(1970, 1, 1).atStartOfDay());

    ArrayList<CalibrationDataset> scenarioList = new ArrayList<>();
    scenarioList.add(calibrationDataset);
    ValuationOraclePlainSwap valuationOraclePlainSwap = new ValuationOraclePlainSwap(new HashMap<>(), scenarioList);
    LocalDateTime evaluationDate = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act and Assert
    assertTrue(valuationOraclePlainSwap.getValues(evaluationDate, LocalDate.of(1970, 1, 1).atStartOfDay()).isEmpty());
  }

  /**
   * Method under test:
   * {@link ValuationOraclePlainSwap#getValues(LocalDateTime, LocalDateTime)}
   */
  @Test
  void testGetValues10() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("discount-EUR-OIS", "discount-EUR-OIS", "Deposit",
        "discount-EUR-OIS");

    curveDataPointSet.add(new CalibrationDataItem(spec, 1.0E-9d, LocalDate.of(1970, 1, 1).atStartOfDay()));
    CalibrationDataset calibrationDataset = new CalibrationDataset(curveDataPointSet,
        LocalDate.of(1970, 1, 1).atStartOfDay());

    ArrayList<CalibrationDataset> scenarioList = new ArrayList<>();
    scenarioList.add(calibrationDataset);
    ValuationOraclePlainSwap valuationOraclePlainSwap = new ValuationOraclePlainSwap(new HashMap<>(), scenarioList);
    LocalDateTime evaluationDate = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act and Assert
    assertTrue(valuationOraclePlainSwap.getValues(evaluationDate, LocalDate.of(1970, 1, 1).atStartOfDay()).isEmpty());
  }

  /**
   * Method under test:
   * {@link ValuationOraclePlainSwap#getValues(LocalDateTime, LocalDateTime)}
   */
  @Test
  void testGetValues11() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibrationDataset calibrationDataset = mock(CalibrationDataset.class);
    when(calibrationDataset.getDataPoints()).thenReturn(new HashSet<>());
    when(calibrationDataset.getFixingDataItems()).thenReturn(new HashSet<>());

    ArrayList<CalibrationSpecProvider> calibrationSpecProviderList = new ArrayList<>();
    Stream<CalibrationSpecProvider> streamResult = calibrationSpecProviderList.stream();
    when(calibrationDataset.getDataAsCalibrationDataPointStream(Mockito.<CalibrationParser>any()))
        .thenReturn(streamResult);
    when(calibrationDataset.getDate()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());

    ArrayList<CalibrationDataset> scenarioList = new ArrayList<>();
    scenarioList.add(calibrationDataset);
    ValuationOraclePlainSwap valuationOraclePlainSwap = new ValuationOraclePlainSwap(new HashMap<>(), scenarioList);
    LocalDateTime evaluationDate = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act
    Map<String, BigDecimal> actualValues = valuationOraclePlainSwap.getValues(evaluationDate,
        LocalDate.of(1970, 1, 1).atStartOfDay());

    // Assert
    verify(calibrationDataset).getDataAsCalibrationDataPointStream(isA(CalibrationParser.class));
    verify(calibrationDataset, atLeast(1)).getDataPoints();
    verify(calibrationDataset).getDate();
    verify(calibrationDataset).getFixingDataItems();
    assertTrue(actualValues.isEmpty());
  }

  /**
   * Method under test:
   * {@link ValuationOraclePlainSwap#getValues(LocalDateTime, LocalDateTime)}
   */
  @Test
  void testGetValues12() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibrationDataset calibrationDataset = mock(CalibrationDataset.class);
    when(calibrationDataset.getDataPoints())
        .thenThrow(new SDCException(ExceptionId.SDC_AUTH_ERROR, "An error occurred"));
    when(calibrationDataset.getDate()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());

    ArrayList<CalibrationDataset> scenarioList = new ArrayList<>();
    scenarioList.add(calibrationDataset);
    ValuationOraclePlainSwap valuationOraclePlainSwap = new ValuationOraclePlainSwap(new HashMap<>(), scenarioList);
    LocalDateTime evaluationDate = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act and Assert
    assertThrows(SDCException.class,
        () -> valuationOraclePlainSwap.getValues(evaluationDate, LocalDate.of(1970, 1, 1).atStartOfDay()));
    verify(calibrationDataset).getDataPoints();
    verify(calibrationDataset).getDate();
  }

  /**
   * Method under test:
   * {@link ValuationOraclePlainSwap#getValues(LocalDateTime, LocalDateTime)}
   */
  @Test
  void testGetValues13() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibrationSpecProvider calibrationSpecProvider = mock(CalibrationSpecProvider.class);
    when(calibrationSpecProvider.getCalibrationSpec(Mockito.<CalibrationContext>any()))
        .thenReturn(new CalibratedCurves.CalibrationSpec("Type", new double[]{10.0d, 0.5d, 10.0d, 0.5d},
            "Forward Curve Receiver Name", 10.0d, "3", "Calibration Curve Name", 10.0d));

    ArrayList<CalibrationSpecProvider> calibrationSpecProviderList = new ArrayList<>();
    calibrationSpecProviderList.add(calibrationSpecProvider);
    Stream<CalibrationSpecProvider> streamResult = calibrationSpecProviderList.stream();
    CalibrationDataset calibrationDataset = mock(CalibrationDataset.class);
    when(calibrationDataset.getDataPoints()).thenReturn(new HashSet<>());
    when(calibrationDataset.getFixingDataItems()).thenReturn(new HashSet<>());
    when(calibrationDataset.getDataAsCalibrationDataPointStream(Mockito.<CalibrationParser>any()))
        .thenReturn(streamResult);
    when(calibrationDataset.getDate()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());

    ArrayList<CalibrationDataset> scenarioList = new ArrayList<>();
    scenarioList.add(calibrationDataset);
    ValuationOraclePlainSwap valuationOraclePlainSwap = new ValuationOraclePlainSwap(new HashMap<>(), scenarioList);
    LocalDateTime evaluationDate = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act and Assert
    assertThrows(SDCException.class,
        () -> valuationOraclePlainSwap.getValues(evaluationDate, LocalDate.of(1970, 1, 1).atStartOfDay()));
    verify(calibrationDataset).getDataAsCalibrationDataPointStream(isA(CalibrationParser.class));
    verify(calibrationDataset, atLeast(1)).getDataPoints();
    verify(calibrationDataset).getDate();
    verify(calibrationDataset).getFixingDataItems();
    verify(calibrationSpecProvider).getCalibrationSpec(isA(CalibrationContext.class));
  }

  /**
   * Method under test:
   * {@link ValuationOraclePlainSwap#getValues(LocalDateTime, LocalDateTime)}
   */
  @Test
  void testGetValues14() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibrationSpecProvider calibrationSpecProvider = mock(CalibrationSpecProvider.class);
    when(calibrationSpecProvider.getCalibrationSpec(Mockito.<CalibrationContext>any()))
        .thenReturn(new CalibratedCurves.CalibrationSpec("Type", new double[]{10.0d, 0.5d, 10.0d, 0.5d},
            "Forward Curve Receiver Name", 10.0d, "3", "Calibration Curve Name", 10.0d));
    CalibrationSpecProvider calibrationSpecProvider2 = mock(CalibrationSpecProvider.class);
    when(calibrationSpecProvider2.getCalibrationSpec(Mockito.<CalibrationContext>any()))
        .thenThrow(new SDCException(ExceptionId.SDC_AUTH_ERROR, "An error occurred"));

    ArrayList<CalibrationSpecProvider> calibrationSpecProviderList = new ArrayList<>();
    calibrationSpecProviderList.add(calibrationSpecProvider2);
    calibrationSpecProviderList.add(calibrationSpecProvider);
    Stream<CalibrationSpecProvider> streamResult = calibrationSpecProviderList.stream();
    CalibrationDataset calibrationDataset = mock(CalibrationDataset.class);
    when(calibrationDataset.getDataPoints()).thenReturn(new HashSet<>());
    when(calibrationDataset.getFixingDataItems()).thenReturn(new HashSet<>());
    when(calibrationDataset.getDataAsCalibrationDataPointStream(Mockito.<CalibrationParser>any()))
        .thenReturn(streamResult);
    when(calibrationDataset.getDate()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());

    ArrayList<CalibrationDataset> scenarioList = new ArrayList<>();
    scenarioList.add(calibrationDataset);
    ValuationOraclePlainSwap valuationOraclePlainSwap = new ValuationOraclePlainSwap(new HashMap<>(), scenarioList);
    LocalDateTime evaluationDate = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act and Assert
    assertThrows(SDCException.class,
        () -> valuationOraclePlainSwap.getValues(evaluationDate, LocalDate.of(1970, 1, 1).atStartOfDay()));
    verify(calibrationDataset).getDataAsCalibrationDataPointStream(isA(CalibrationParser.class));
    verify(calibrationDataset, atLeast(1)).getDataPoints();
    verify(calibrationDataset).getDate();
    verify(calibrationDataset).getFixingDataItems();
    verify(calibrationSpecProvider2).getCalibrationSpec(isA(CalibrationContext.class));
  }

  /**
   * Method under test:
   * {@link ValuationOraclePlainSwap#ValuationOraclePlainSwap(Map, List)}
   */
  @Test
  void testNewValuationOraclePlainSwap() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    HashMap<String, AnalyticProduct> products = new HashMap<>();

    // Act and Assert
    assertNull((new ValuationOraclePlainSwap(products, new ArrayList<>())).getValues(null, null));
  }

  /**
   * Method under test:
   * {@link ValuationOraclePlainSwap#ValuationOraclePlainSwap(Map, List)}
   */
  @Test
  void testNewValuationOraclePlainSwap2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    HashMap<String, AnalyticProduct> products = new HashMap<>();

    ArrayList<CalibrationDataset> scenarioList = new ArrayList<>();
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    scenarioList.add(new CalibrationDataset(curveDataPointSet, LocalDate.of(1970, 1, 1).atStartOfDay()));

    // Act and Assert
    assertNull((new ValuationOraclePlainSwap(products, scenarioList)).getValues(null, null));
  }

  /**
   * Method under test:
   * {@link ValuationOraclePlainSwap#ValuationOraclePlainSwap(Map, List)}
   */
  @Test
  void testNewValuationOraclePlainSwap3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    HashMap<String, AnalyticProduct> products = new HashMap<>();

    ArrayList<CalibrationDataset> scenarioList = new ArrayList<>();
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    scenarioList.add(new CalibrationDataset(curveDataPointSet, LocalDate.of(1970, 1, 1).atStartOfDay()));
    HashSet<CalibrationDataItem> curveDataPointSet2 = new HashSet<>();
    scenarioList.add(new CalibrationDataset(curveDataPointSet2, LocalDate.of(1970, 1, 1).atStartOfDay()));

    // Act and Assert
    assertNull((new ValuationOraclePlainSwap(products, scenarioList)).getValues(null, null));
  }

  /**
   * Method under test:
   * {@link ValuationOraclePlainSwap#ValuationOraclePlainSwap(Map, List)}
   */
  @Test
  void testNewValuationOraclePlainSwap4() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    HashMap<String, AnalyticProduct> products = new HashMap<>();
    products.computeIfPresent("EUR", mock(BiFunction.class));

    // Act and Assert
    assertNull((new ValuationOraclePlainSwap(products, new ArrayList<>())).getValues(null, null));
  }

  /**
   * Method under test:
   * {@link ValuationOraclePlainSwap#ValuationOraclePlainSwap(Map, List, int)}
   */
  @Test
  void testNewValuationOraclePlainSwap5() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    HashMap<String, AnalyticProduct> products = new HashMap<>();

    // Act and Assert
    assertNull((new ValuationOraclePlainSwap(products, new ArrayList<>(), 1)).getValues(null, null));
  }

  /**
   * Method under test:
   * {@link ValuationOraclePlainSwap#ValuationOraclePlainSwap(Map, List, int)}
   */
  @Test
  void testNewValuationOraclePlainSwap6() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    HashMap<String, AnalyticProduct> products = new HashMap<>();

    ArrayList<CalibrationDataset> scenarioList = new ArrayList<>();
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    scenarioList.add(new CalibrationDataset(curveDataPointSet, LocalDate.of(1970, 1, 1).atStartOfDay()));

    // Act and Assert
    assertNull((new ValuationOraclePlainSwap(products, scenarioList, 1)).getValues(null, null));
  }

  /**
   * Method under test:
   * {@link ValuationOraclePlainSwap#ValuationOraclePlainSwap(Map, List, int)}
   */
  @Test
  void testNewValuationOraclePlainSwap7() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    HashMap<String, AnalyticProduct> products = new HashMap<>();

    ArrayList<CalibrationDataset> scenarioList = new ArrayList<>();
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    scenarioList.add(new CalibrationDataset(curveDataPointSet, LocalDate.of(1970, 1, 1).atStartOfDay()));
    HashSet<CalibrationDataItem> curveDataPointSet2 = new HashSet<>();
    scenarioList.add(new CalibrationDataset(curveDataPointSet2, LocalDate.of(1970, 1, 1).atStartOfDay()));

    // Act and Assert
    assertNull((new ValuationOraclePlainSwap(products, scenarioList, 1)).getValues(null, null));
  }

  /**
   * Method under test:
   * {@link ValuationOraclePlainSwap#ValuationOraclePlainSwap(Map, List, int)}
   */
  @Test
  void testNewValuationOraclePlainSwap8() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    HashMap<String, AnalyticProduct> products = new HashMap<>();
    products.computeIfPresent("EUR", mock(BiFunction.class));

    // Act and Assert
    assertNull((new ValuationOraclePlainSwap(products, new ArrayList<>(), 1)).getValues(null, null));
  }
}
