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
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import net.finmath.marketdata.calibration.CalibratedCurves;
import net.finmath.marketdata.calibration.CalibratedCurves.CalibrationSpec;
import net.finmath.marketdata.products.AnalyticProduct;
import net.finmath.marketdata.products.Cashflow;
import net.finmath.smartcontract.model.ExceptionId;
import net.finmath.smartcontract.model.SDCException;
import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationContext;
import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationDataItem;
import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationDataItem.Spec;
import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationDataset;
import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationParser;
import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationSpecProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ValuationOraclePlainSwapDiffblueTest {
  /**
   * Test {@link ValuationOraclePlainSwap#ValuationOraclePlainSwap(Map, List)}.
   * <ul>
   *   <li>Given {@link LocalDate} with {@code 1970} and one and one atStartOfDay.</li>
   * </ul>
   * <p>
   * Method under test: {@link ValuationOraclePlainSwap#ValuationOraclePlainSwap(Map, List)}
   */
  @Test
  @DisplayName("Test new ValuationOraclePlainSwap(Map, List); given LocalDate with '1970' and one and one atStartOfDay")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ValuationOraclePlainSwap.<init>(Map, List)"})
  void testNewValuationOraclePlainSwap_givenLocalDateWith1970AndOneAndOneAtStartOfDay() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    HashMap<String, AnalyticProduct> products = new HashMap<>();

    ArrayList<CalibrationDataset> scenarioList = new ArrayList<>();
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    scenarioList.add(new CalibrationDataset(curveDataPointSet, LocalDate.of(1970, 1, 1).atStartOfDay()));

    // Act and Assert
    assertNull((new ValuationOraclePlainSwap(products, scenarioList)).getValues(null, null));
  }

  /**
   * Test {@link ValuationOraclePlainSwap#ValuationOraclePlainSwap(Map, List)}.
   * <ul>
   *   <li>Given {@link LocalDate} with {@code 1970} and one and one atStartOfDay.</li>
   * </ul>
   * <p>
   * Method under test: {@link ValuationOraclePlainSwap#ValuationOraclePlainSwap(Map, List)}
   */
  @Test
  @DisplayName("Test new ValuationOraclePlainSwap(Map, List); given LocalDate with '1970' and one and one atStartOfDay")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ValuationOraclePlainSwap.<init>(Map, List)"})
  void testNewValuationOraclePlainSwap_givenLocalDateWith1970AndOneAndOneAtStartOfDay2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ValuationOraclePlainSwap#ValuationOraclePlainSwap(Map, List, int)}.
   * <ul>
   *   <li>Given {@link LocalDate} with {@code 1970} and one and one atStartOfDay.</li>
   * </ul>
   * <p>
   * Method under test: {@link ValuationOraclePlainSwap#ValuationOraclePlainSwap(Map, List, int)}
   */
  @Test
  @DisplayName("Test new ValuationOraclePlainSwap(Map, List, int); given LocalDate with '1970' and one and one atStartOfDay")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ValuationOraclePlainSwap.<init>(Map, List, int)"})
  void testNewValuationOraclePlainSwap_givenLocalDateWith1970AndOneAndOneAtStartOfDay3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    HashMap<String, AnalyticProduct> products = new HashMap<>();

    ArrayList<CalibrationDataset> scenarioList = new ArrayList<>();
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    scenarioList.add(new CalibrationDataset(curveDataPointSet, LocalDate.of(1970, 1, 1).atStartOfDay()));

    // Act and Assert
    assertNull((new ValuationOraclePlainSwap(products, scenarioList, 1)).getValues(null, null));
  }

  /**
   * Test {@link ValuationOraclePlainSwap#ValuationOraclePlainSwap(Map, List, int)}.
   * <ul>
   *   <li>Given {@link LocalDate} with {@code 1970} and one and one atStartOfDay.</li>
   * </ul>
   * <p>
   * Method under test: {@link ValuationOraclePlainSwap#ValuationOraclePlainSwap(Map, List, int)}
   */
  @Test
  @DisplayName("Test new ValuationOraclePlainSwap(Map, List, int); given LocalDate with '1970' and one and one atStartOfDay")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ValuationOraclePlainSwap.<init>(Map, List, int)"})
  void testNewValuationOraclePlainSwap_givenLocalDateWith1970AndOneAndOneAtStartOfDay4() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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
   * Test {@link ValuationOraclePlainSwap#ValuationOraclePlainSwap(Map, List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ValuationOraclePlainSwap#ValuationOraclePlainSwap(Map, List)}
   */
  @Test
  @DisplayName("Test new ValuationOraclePlainSwap(Map, List); when ArrayList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ValuationOraclePlainSwap.<init>(Map, List)"})
  void testNewValuationOraclePlainSwap_whenArrayList() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    HashMap<String, AnalyticProduct> products = new HashMap<>();

    // Act and Assert
    assertNull((new ValuationOraclePlainSwap(products, new ArrayList<>())).getValues(null, null));
  }

  /**
   * Test {@link ValuationOraclePlainSwap#ValuationOraclePlainSwap(Map, List, int)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ValuationOraclePlainSwap#ValuationOraclePlainSwap(Map, List, int)}
   */
  @Test
  @DisplayName("Test new ValuationOraclePlainSwap(Map, List, int); when ArrayList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ValuationOraclePlainSwap.<init>(Map, List, int)"})
  void testNewValuationOraclePlainSwap_whenArrayList2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    HashMap<String, AnalyticProduct> products = new HashMap<>();

    // Act and Assert
    assertNull((new ValuationOraclePlainSwap(products, new ArrayList<>(), 1)).getValues(null, null));
  }

  /**
   * Test {@link ValuationOraclePlainSwap#getAmount(LocalDateTime, LocalDateTime)}.
   * <p>
   * Method under test: {@link ValuationOraclePlainSwap#getAmount(LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test getAmount(LocalDateTime, LocalDateTime)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"javax.money.MonetaryAmount ValuationOraclePlainSwap.getAmount(LocalDateTime, LocalDateTime)"})
  void testGetAmount() {
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
   * Test {@link ValuationOraclePlainSwap#getAmount(LocalDateTime, LocalDateTime)}.
   * <p>
   * Method under test: {@link ValuationOraclePlainSwap#getAmount(LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test getAmount(LocalDateTime, LocalDateTime)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"javax.money.MonetaryAmount ValuationOraclePlainSwap.getAmount(LocalDateTime, LocalDateTime)"})
  void testGetAmount2() {
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
   * Test {@link ValuationOraclePlainSwap#getAmount(LocalDateTime, LocalDateTime)}.
   * <p>
   * Method under test: {@link ValuationOraclePlainSwap#getAmount(LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test getAmount(LocalDateTime, LocalDateTime)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"javax.money.MonetaryAmount ValuationOraclePlainSwap.getAmount(LocalDateTime, LocalDateTime)"})
  void testGetAmount3() {
    // Arrange
    CalibrationSpecProvider calibrationSpecProvider = mock(CalibrationSpecProvider.class);
    when(calibrationSpecProvider.getCalibrationSpec(Mockito.<CalibrationContext>any()))
        .thenReturn(new CalibrationSpec("Type", new double[]{10.0d, 0.5d, 10.0d, 0.5d}, "Forward Curve Receiver Name",
            10.0d, "3", "Calibration Curve Name", 10.0d));
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
   * Test {@link ValuationOraclePlainSwap#getAmount(LocalDateTime, LocalDateTime)}.
   * <ul>
   *   <li>Then calls {@link CalibrationDataset#getDataAsCalibrationDataPointStream(CalibrationParser)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ValuationOraclePlainSwap#getAmount(LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test getAmount(LocalDateTime, LocalDateTime); then calls getDataAsCalibrationDataPointStream(CalibrationParser)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"javax.money.MonetaryAmount ValuationOraclePlainSwap.getAmount(LocalDateTime, LocalDateTime)"})
  void testGetAmount_thenCallsGetDataAsCalibrationDataPointStream() {
    // Arrange
    CalibrationSpecProvider calibrationSpecProvider = mock(CalibrationSpecProvider.class);
    when(calibrationSpecProvider.getCalibrationSpec(Mockito.<CalibrationContext>any()))
        .thenReturn(new CalibrationSpec("Type", new double[]{10.0d, 0.5d, 10.0d, 0.5d}, "Forward Curve Receiver Name",
            10.0d, "3", "Calibration Curve Name", 10.0d));

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
   * Test {@link ValuationOraclePlainSwap#getValue(LocalDateTime, LocalDateTime)}.
   * <p>
   * Method under test: {@link ValuationOraclePlainSwap#getValue(LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test getValue(LocalDateTime, LocalDateTime)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"BigDecimal ValuationOraclePlainSwap.getValue(LocalDateTime, LocalDateTime)"})
  void testGetValue() {
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
   * Test {@link ValuationOraclePlainSwap#getValue(LocalDateTime, LocalDateTime)}.
   * <p>
   * Method under test: {@link ValuationOraclePlainSwap#getValue(LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test getValue(LocalDateTime, LocalDateTime)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"BigDecimal ValuationOraclePlainSwap.getValue(LocalDateTime, LocalDateTime)"})
  void testGetValue2() {
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
   * Test {@link ValuationOraclePlainSwap#getValue(LocalDateTime, LocalDateTime)}.
   * <p>
   * Method under test: {@link ValuationOraclePlainSwap#getValue(LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test getValue(LocalDateTime, LocalDateTime)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"BigDecimal ValuationOraclePlainSwap.getValue(LocalDateTime, LocalDateTime)"})
  void testGetValue3() {
    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    Spec spec = new Spec("discount-EUR-OIS", "discount-EUR-OIS", "discount-EUR-OIS", "discount-EUR-OIS");

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
   * Test {@link ValuationOraclePlainSwap#getValue(LocalDateTime, LocalDateTime)}.
   * <p>
   * Method under test: {@link ValuationOraclePlainSwap#getValue(LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test getValue(LocalDateTime, LocalDateTime)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"BigDecimal ValuationOraclePlainSwap.getValue(LocalDateTime, LocalDateTime)"})
  void testGetValue4() {
    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    Spec spec = new Spec("EURESTSD", "discount-EUR-OIS", "discount-EUR-OIS", "discount-EUR-OIS");

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
   * Test {@link ValuationOraclePlainSwap#getValue(LocalDateTime, LocalDateTime)}.
   * <p>
   * Method under test: {@link ValuationOraclePlainSwap#getValue(LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test getValue(LocalDateTime, LocalDateTime)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"BigDecimal ValuationOraclePlainSwap.getValue(LocalDateTime, LocalDateTime)"})
  void testGetValue5() {
    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    Spec spec = new Spec("EUREST1D", "discount-EUR-OIS", "discount-EUR-OIS", "discount-EUR-OIS");

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
   * Test {@link ValuationOraclePlainSwap#getValue(LocalDateTime, LocalDateTime)}.
   * <p>
   * Method under test: {@link ValuationOraclePlainSwap#getValue(LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test getValue(LocalDateTime, LocalDateTime)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"BigDecimal ValuationOraclePlainSwap.getValue(LocalDateTime, LocalDateTime)"})
  void testGetValue6() {
    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    Spec spec = new Spec("discount-EUR-OIS", "discount-EUR-OIS", "Fixing", "discount-EUR-OIS");

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
   * Test {@link ValuationOraclePlainSwap#getValue(LocalDateTime, LocalDateTime)}.
   * <p>
   * Method under test: {@link ValuationOraclePlainSwap#getValue(LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test getValue(LocalDateTime, LocalDateTime)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"BigDecimal ValuationOraclePlainSwap.getValue(LocalDateTime, LocalDateTime)"})
  void testGetValue7() {
    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    Spec spec = new Spec("discount-EUR-OIS", "discount-EUR-OIS", "Deposit", "discount-EUR-OIS");

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
   * Test {@link ValuationOraclePlainSwap#getValue(LocalDateTime, LocalDateTime)}.
   * <p>
   * Method under test: {@link ValuationOraclePlainSwap#getValue(LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test getValue(LocalDateTime, LocalDateTime)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"BigDecimal ValuationOraclePlainSwap.getValue(LocalDateTime, LocalDateTime)"})
  void testGetValue8() {
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
   * Test {@link ValuationOraclePlainSwap#getValue(LocalDateTime, LocalDateTime)}.
   * <ul>
   *   <li>Given {@link LocalDate} with {@code 1970} and one and one atStartOfDay.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ValuationOraclePlainSwap#getValue(LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test getValue(LocalDateTime, LocalDateTime); given LocalDate with '1970' and one and one atStartOfDay; then return 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"BigDecimal ValuationOraclePlainSwap.getValue(LocalDateTime, LocalDateTime)"})
  void testGetValue_givenLocalDateWith1970AndOneAndOneAtStartOfDay_thenReturnNull() {
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
   * Test {@link ValuationOraclePlainSwap#getValue(LocalDateTime, LocalDateTime)}.
   * <ul>
   *   <li>Then calls {@link CalibrationSpecProvider#getCalibrationSpec(CalibrationContext)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ValuationOraclePlainSwap#getValue(LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test getValue(LocalDateTime, LocalDateTime); then calls getCalibrationSpec(CalibrationContext)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"BigDecimal ValuationOraclePlainSwap.getValue(LocalDateTime, LocalDateTime)"})
  void testGetValue_thenCallsGetCalibrationSpec() {
    // Arrange
    CalibrationSpecProvider calibrationSpecProvider = mock(CalibrationSpecProvider.class);
    when(calibrationSpecProvider.getCalibrationSpec(Mockito.<CalibrationContext>any()))
        .thenReturn(new CalibrationSpec("Type", new double[]{10.0d, 0.5d, 10.0d, 0.5d}, "Forward Curve Receiver Name",
            10.0d, "3", "Calibration Curve Name", 10.0d));

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
   * Test {@link ValuationOraclePlainSwap#getValue(LocalDateTime, LocalDateTime)}.
   * <ul>
   *   <li>Then calls {@link CalibrationDataset#getDataAsCalibrationDataPointStream(CalibrationParser)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ValuationOraclePlainSwap#getValue(LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test getValue(LocalDateTime, LocalDateTime); then calls getDataAsCalibrationDataPointStream(CalibrationParser)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"BigDecimal ValuationOraclePlainSwap.getValue(LocalDateTime, LocalDateTime)"})
  void testGetValue_thenCallsGetDataAsCalibrationDataPointStream() {
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
   * Test {@link ValuationOraclePlainSwap#getValues(LocalDateTime, LocalDateTime)}.
   * <p>
   * Method under test: {@link ValuationOraclePlainSwap#getValues(LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test getValues(LocalDateTime, LocalDateTime)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Map ValuationOraclePlainSwap.getValues(LocalDateTime, LocalDateTime)"})
  void testGetValues() {
    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    Spec spec = new Spec("discount-EUR-OIS", "discount-EUR-OIS", "discount-EUR-OIS", "discount-EUR-OIS");

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
   * Test {@link ValuationOraclePlainSwap#getValues(LocalDateTime, LocalDateTime)}.
   * <p>
   * Method under test: {@link ValuationOraclePlainSwap#getValues(LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test getValues(LocalDateTime, LocalDateTime)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Map ValuationOraclePlainSwap.getValues(LocalDateTime, LocalDateTime)"})
  void testGetValues2() {
    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    Spec spec = new Spec("EURESTSD", "discount-EUR-OIS", "discount-EUR-OIS", "discount-EUR-OIS");

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
   * Test {@link ValuationOraclePlainSwap#getValues(LocalDateTime, LocalDateTime)}.
   * <p>
   * Method under test: {@link ValuationOraclePlainSwap#getValues(LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test getValues(LocalDateTime, LocalDateTime)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Map ValuationOraclePlainSwap.getValues(LocalDateTime, LocalDateTime)"})
  void testGetValues3() {
    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    Spec spec = new Spec("EUREST1D", "discount-EUR-OIS", "discount-EUR-OIS", "discount-EUR-OIS");

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
   * Test {@link ValuationOraclePlainSwap#getValues(LocalDateTime, LocalDateTime)}.
   * <p>
   * Method under test: {@link ValuationOraclePlainSwap#getValues(LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test getValues(LocalDateTime, LocalDateTime)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Map ValuationOraclePlainSwap.getValues(LocalDateTime, LocalDateTime)"})
  void testGetValues4() {
    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    Spec spec = new Spec("discount-EUR-OIS", "discount-EUR-OIS", "Fixing", "discount-EUR-OIS");

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
   * Test {@link ValuationOraclePlainSwap#getValues(LocalDateTime, LocalDateTime)}.
   * <p>
   * Method under test: {@link ValuationOraclePlainSwap#getValues(LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test getValues(LocalDateTime, LocalDateTime)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Map ValuationOraclePlainSwap.getValues(LocalDateTime, LocalDateTime)"})
  void testGetValues5() {
    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    Spec spec = new Spec("discount-EUR-OIS", "discount-EUR-OIS", "Deposit", "discount-EUR-OIS");

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
   * Test {@link ValuationOraclePlainSwap#getValues(LocalDateTime, LocalDateTime)}.
   * <ul>
   *   <li>Given {@link LocalDate} with {@code 1970} and one and one atStartOfDay.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ValuationOraclePlainSwap#getValues(LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test getValues(LocalDateTime, LocalDateTime); given LocalDate with '1970' and one and one atStartOfDay; then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Map ValuationOraclePlainSwap.getValues(LocalDateTime, LocalDateTime)"})
  void testGetValues_givenLocalDateWith1970AndOneAndOneAtStartOfDay_thenReturnEmpty() {
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
   * Test {@link ValuationOraclePlainSwap#getValues(LocalDateTime, LocalDateTime)}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ValuationOraclePlainSwap#getValues(LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test getValues(LocalDateTime, LocalDateTime); then return 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Map ValuationOraclePlainSwap.getValues(LocalDateTime, LocalDateTime)"})
  void testGetValues_thenReturnNull() {
    // Arrange
    HashMap<String, AnalyticProduct> products = new HashMap<>();
    ValuationOraclePlainSwap valuationOraclePlainSwap = new ValuationOraclePlainSwap(products, new ArrayList<>());
    LocalDateTime evaluationDate = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act and Assert
    assertNull(valuationOraclePlainSwap.getValues(evaluationDate, LocalDate.of(1970, 1, 1).atStartOfDay()));
  }

  /**
   * Test {@link ValuationOraclePlainSwap#getValues(LocalDateTime, LocalDateTime)}.
   * <ul>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ValuationOraclePlainSwap#getValues(LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test getValues(LocalDateTime, LocalDateTime); then return size is one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Map ValuationOraclePlainSwap.getValues(LocalDateTime, LocalDateTime)"})
  void testGetValues_thenReturnSizeIsOne() {
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
   * Test {@link ValuationOraclePlainSwap#getValues(LocalDateTime, LocalDateTime)}.
   * <ul>
   *   <li>Then throw {@link SDCException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ValuationOraclePlainSwap#getValues(LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test getValues(LocalDateTime, LocalDateTime); then throw SDCException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Map ValuationOraclePlainSwap.getValues(LocalDateTime, LocalDateTime)"})
  void testGetValues_thenThrowSDCException() {
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
}
