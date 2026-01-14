package net.finmath.smartcontract.valuation.oracle.interestrates;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
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
import net.finmath.smartcontract.valuation.marketdata.generators.MarketDataGeneratorLauncherDiffblueBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ValuationOraclePlainSwapDiffblueTest {
  /**
   * Test {@link ValuationOraclePlainSwap#ValuationOraclePlainSwap(Map, List)}.
   *
   * <ul>
   *   <li>Given {@link LocalDate} with {@code 1970} and one and one atStartOfDay.
   * </ul>
   *
   * <p>Method under test: {@link ValuationOraclePlainSwap#ValuationOraclePlainSwap(Map, List)}
   */
  @Test
  @DisplayName(
      "Test new ValuationOraclePlainSwap(Map, List); given LocalDate with '1970' and one and one atStartOfDay")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ValuationOraclePlainSwap.<init>(Map, List)"})
  void testNewValuationOraclePlainSwap_givenLocalDateWith1970AndOneAndOneAtStartOfDay() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    HashMap<String, AnalyticProduct> products = new HashMap<>();

    ArrayList<CalibrationDataset> scenarioList = new ArrayList<>();
    CalibrationDataset calibrationDataset =
        new CalibrationDataset(new HashSet<>(), LocalDate.of(1970, 1, 1).atStartOfDay());
    scenarioList.add(calibrationDataset);

    // Act
    ValuationOraclePlainSwap actualValuationOraclePlainSwap =
        new ValuationOraclePlainSwap(products, scenarioList);

    // Assert
    assertNull(actualValuationOraclePlainSwap.getValues(null, null));
  }

  /**
   * Test {@link ValuationOraclePlainSwap#ValuationOraclePlainSwap(Map, List)}.
   *
   * <ul>
   *   <li>Given {@link LocalDate} with {@code 1970} and one and one atStartOfDay.
   * </ul>
   *
   * <p>Method under test: {@link ValuationOraclePlainSwap#ValuationOraclePlainSwap(Map, List)}
   */
  @Test
  @DisplayName(
      "Test new ValuationOraclePlainSwap(Map, List); given LocalDate with '1970' and one and one atStartOfDay")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ValuationOraclePlainSwap.<init>(Map, List)"})
  void testNewValuationOraclePlainSwap_givenLocalDateWith1970AndOneAndOneAtStartOfDay2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    HashMap<String, AnalyticProduct> products = new HashMap<>();

    ArrayList<CalibrationDataset> scenarioList = new ArrayList<>();
    CalibrationDataset calibrationDataset =
        new CalibrationDataset(new HashSet<>(), LocalDate.of(1970, 1, 1).atStartOfDay());
    scenarioList.add(calibrationDataset);
    CalibrationDataset calibrationDataset2 =
        new CalibrationDataset(new HashSet<>(), LocalDate.of(1970, 1, 1).atStartOfDay());
    scenarioList.add(calibrationDataset2);

    // Act
    ValuationOraclePlainSwap actualValuationOraclePlainSwap =
        new ValuationOraclePlainSwap(products, scenarioList);

    // Assert
    assertNull(actualValuationOraclePlainSwap.getValues(null, null));
  }

  /**
   * Test {@link ValuationOraclePlainSwap#ValuationOraclePlainSwap(Map, List, int)}.
   *
   * <ul>
   *   <li>Given {@link LocalDate} with {@code 1970} and one and one atStartOfDay.
   * </ul>
   *
   * <p>Method under test: {@link ValuationOraclePlainSwap#ValuationOraclePlainSwap(Map, List, int)}
   */
  @Test
  @DisplayName(
      "Test new ValuationOraclePlainSwap(Map, List, int); given LocalDate with '1970' and one and one atStartOfDay")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ValuationOraclePlainSwap.<init>(Map, List, int)"})
  void testNewValuationOraclePlainSwap_givenLocalDateWith1970AndOneAndOneAtStartOfDay3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    HashMap<String, AnalyticProduct> products = new HashMap<>();

    ArrayList<CalibrationDataset> scenarioList = new ArrayList<>();
    CalibrationDataset calibrationDataset =
        new CalibrationDataset(new HashSet<>(), LocalDate.of(1970, 1, 1).atStartOfDay());
    scenarioList.add(calibrationDataset);

    // Act
    ValuationOraclePlainSwap actualValuationOraclePlainSwap =
        new ValuationOraclePlainSwap(products, scenarioList, 1);

    // Assert
    assertNull(actualValuationOraclePlainSwap.getValues(null, null));
  }

  /**
   * Test {@link ValuationOraclePlainSwap#ValuationOraclePlainSwap(Map, List, int)}.
   *
   * <ul>
   *   <li>Given {@link LocalDate} with {@code 1970} and one and one atStartOfDay.
   * </ul>
   *
   * <p>Method under test: {@link ValuationOraclePlainSwap#ValuationOraclePlainSwap(Map, List, int)}
   */
  @Test
  @DisplayName(
      "Test new ValuationOraclePlainSwap(Map, List, int); given LocalDate with '1970' and one and one atStartOfDay")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ValuationOraclePlainSwap.<init>(Map, List, int)"})
  void testNewValuationOraclePlainSwap_givenLocalDateWith1970AndOneAndOneAtStartOfDay4() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    HashMap<String, AnalyticProduct> products = new HashMap<>();

    ArrayList<CalibrationDataset> scenarioList = new ArrayList<>();
    CalibrationDataset calibrationDataset =
        new CalibrationDataset(new HashSet<>(), LocalDate.of(1970, 1, 1).atStartOfDay());
    scenarioList.add(calibrationDataset);
    CalibrationDataset calibrationDataset2 =
        new CalibrationDataset(new HashSet<>(), LocalDate.of(1970, 1, 1).atStartOfDay());
    scenarioList.add(calibrationDataset2);

    // Act
    ValuationOraclePlainSwap actualValuationOraclePlainSwap =
        new ValuationOraclePlainSwap(products, scenarioList, 1);

    // Assert
    assertNull(actualValuationOraclePlainSwap.getValues(null, null));
  }

  /**
   * Test {@link ValuationOraclePlainSwap#ValuationOraclePlainSwap(Map, List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link ValuationOraclePlainSwap#ValuationOraclePlainSwap(Map, List)}
   */
  @Test
  @DisplayName("Test new ValuationOraclePlainSwap(Map, List); when ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ValuationOraclePlainSwap.<init>(Map, List)"})
  void testNewValuationOraclePlainSwap_whenArrayList() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    HashMap<String, AnalyticProduct> products = new HashMap<>();

    // Act
    ValuationOraclePlainSwap actualValuationOraclePlainSwap =
        new ValuationOraclePlainSwap(products, new ArrayList<>());

    // Assert
    assertNull(actualValuationOraclePlainSwap.getValues(null, null));
  }

  /**
   * Test {@link ValuationOraclePlainSwap#ValuationOraclePlainSwap(Map, List, int)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link ValuationOraclePlainSwap#ValuationOraclePlainSwap(Map, List, int)}
   */
  @Test
  @DisplayName("Test new ValuationOraclePlainSwap(Map, List, int); when ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ValuationOraclePlainSwap.<init>(Map, List, int)"})
  void testNewValuationOraclePlainSwap_whenArrayList2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    HashMap<String, AnalyticProduct> products = new HashMap<>();

    // Act
    ValuationOraclePlainSwap actualValuationOraclePlainSwap =
        new ValuationOraclePlainSwap(products, new ArrayList<>(), 1);

    // Assert
    assertNull(actualValuationOraclePlainSwap.getValues(null, null));
  }

  /**
   * Test {@link ValuationOraclePlainSwap#getAmount(LocalDateTime, LocalDateTime)}.
   *
   * <p>Method under test: {@link ValuationOraclePlainSwap#getAmount(LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test getAmount(LocalDateTime, LocalDateTime)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "javax.money.MonetaryAmount ValuationOraclePlainSwap.getAmount(LocalDateTime, LocalDateTime)"
  })
  void testGetAmount() {
    // Arrange
    HashMap<String, AnalyticProduct> products = new HashMap<>();
    String createMinimalSmartDerivativeContractXmlResult =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();
    String currency =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();
    Cashflow cashflow =
        new Cashflow(
            currency,
            1.0E-9d,
            1.0E-9d,
            true,
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());

    products.put(createMinimalSmartDerivativeContractXmlResult, cashflow);

    ArrayList<CalibrationDataset> scenarioList = new ArrayList<>();
    CalibrationDataset calibrationDataset =
        new CalibrationDataset(new HashSet<>(), LocalDate.of(1970, 1, 1).atStartOfDay());
    scenarioList.add(calibrationDataset);

    ValuationOraclePlainSwap valuationOraclePlainSwap =
        new ValuationOraclePlainSwap(products, scenarioList);

    // Act and Assert
    assertThrows(
        SDCException.class,
        () ->
            valuationOraclePlainSwap.getAmount(
                LocalDate.of(1970, 1, 1).atStartOfDay(), LocalDate.of(1970, 1, 1).atStartOfDay()));
  }

  /**
   * Test {@link ValuationOraclePlainSwap#getAmount(LocalDateTime, LocalDateTime)}.
   *
   * <p>Method under test: {@link ValuationOraclePlainSwap#getAmount(LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test getAmount(LocalDateTime, LocalDateTime)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "javax.money.MonetaryAmount ValuationOraclePlainSwap.getAmount(LocalDateTime, LocalDateTime)"
  })
  void testGetAmount2() {
    // Arrange
    CalibrationDataset calibrationDataset = mock(CalibrationDataset.class);
    when(calibrationDataset.getDataPoints())
        .thenThrow(
            new SDCException(
                ExceptionId.SDC_AUTH_ERROR,
                MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml()));
    when(calibrationDataset.getDate()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());

    ArrayList<CalibrationDataset> scenarioList = new ArrayList<>();
    scenarioList.add(calibrationDataset);
    ValuationOraclePlainSwap valuationOraclePlainSwap =
        new ValuationOraclePlainSwap(new HashMap<>(), scenarioList);

    // Act and Assert
    assertThrows(
        SDCException.class,
        () ->
            valuationOraclePlainSwap.getAmount(
                LocalDate.of(1970, 1, 1).atStartOfDay(), LocalDate.of(1970, 1, 1).atStartOfDay()));
    verify(calibrationDataset).getDataPoints();
    verify(calibrationDataset).getDate();
  }

  /**
   * Test {@link ValuationOraclePlainSwap#getAmount(LocalDateTime, LocalDateTime)}.
   *
   * <p>Method under test: {@link ValuationOraclePlainSwap#getAmount(LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test getAmount(LocalDateTime, LocalDateTime)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "javax.money.MonetaryAmount ValuationOraclePlainSwap.getAmount(LocalDateTime, LocalDateTime)"
  })
  void testGetAmount3() {
    // Arrange
    CalibrationSpecProvider calibrationSpecProvider = mock(CalibrationSpecProvider.class);
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
    when(calibrationSpecProvider.getCalibrationSpec(Mockito.<CalibrationContext>any()))
        .thenReturn(calibrationSpec);

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
    ValuationOraclePlainSwap valuationOraclePlainSwap =
        new ValuationOraclePlainSwap(new HashMap<>(), scenarioList);

    // Act and Assert
    assertThrows(
        SDCException.class,
        () ->
            valuationOraclePlainSwap.getAmount(
                LocalDate.of(1970, 1, 1).atStartOfDay(), LocalDate.of(1970, 1, 1).atStartOfDay()));
    verify(calibrationDataset).getDataAsCalibrationDataPointStream(isA(CalibrationParser.class));
    verify(calibrationDataset, atLeast(1)).getDataPoints();
    verify(calibrationDataset).getDate();
    verify(calibrationDataset).getFixingDataItems();
    verify(calibrationSpecProvider).getCalibrationSpec(isA(CalibrationContext.class));
  }

  /**
   * Test {@link ValuationOraclePlainSwap#getAmount(LocalDateTime, LocalDateTime)}.
   *
   * <p>Method under test: {@link ValuationOraclePlainSwap#getAmount(LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test getAmount(LocalDateTime, LocalDateTime)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "javax.money.MonetaryAmount ValuationOraclePlainSwap.getAmount(LocalDateTime, LocalDateTime)"
  })
  void testGetAmount4() {
    // Arrange
    CalibrationSpecProvider calibrationSpecProvider = mock(CalibrationSpecProvider.class);
    when(calibrationSpecProvider.getCalibrationSpec(Mockito.<CalibrationContext>any()))
        .thenThrow(
            new SDCException(
                ExceptionId.SDC_AUTH_ERROR,
                MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml()));

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
    ValuationOraclePlainSwap valuationOraclePlainSwap =
        new ValuationOraclePlainSwap(new HashMap<>(), scenarioList);

    // Act and Assert
    assertThrows(
        SDCException.class,
        () ->
            valuationOraclePlainSwap.getAmount(
                LocalDate.of(1970, 1, 1).atStartOfDay(), LocalDate.of(1970, 1, 1).atStartOfDay()));
    verify(calibrationDataset).getDataAsCalibrationDataPointStream(isA(CalibrationParser.class));
    verify(calibrationDataset, atLeast(1)).getDataPoints();
    verify(calibrationDataset).getDate();
    verify(calibrationDataset).getFixingDataItems();
    verify(calibrationSpecProvider).getCalibrationSpec(isA(CalibrationContext.class));
  }

  /**
   * Test {@link ValuationOraclePlainSwap#getAmount(LocalDateTime, LocalDateTime)}.
   *
   * <p>Method under test: {@link ValuationOraclePlainSwap#getAmount(LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test getAmount(LocalDateTime, LocalDateTime)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "javax.money.MonetaryAmount ValuationOraclePlainSwap.getAmount(LocalDateTime, LocalDateTime)"
  })
  void testGetAmount5() {
    // Arrange
    CalibrationSpecProvider calibrationSpecProvider = mock(CalibrationSpecProvider.class);
    String type = MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();
    String forwardCurveReceiverName =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();

    CalibrationSpec calibrationSpec =
        new CalibrationSpec(
            type,
            new double[] {10.0d, 0.5d, 10.0d, 0.5d},
            forwardCurveReceiverName,
            10.0d,
            "discount-EUR-OIS",
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml(),
            10.0d);
    when(calibrationSpecProvider.getCalibrationSpec(Mockito.<CalibrationContext>any()))
        .thenReturn(calibrationSpec);

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
    ValuationOraclePlainSwap valuationOraclePlainSwap =
        new ValuationOraclePlainSwap(new HashMap<>(), scenarioList);

    // Act and Assert
    assertThrows(
        SDCException.class,
        () ->
            valuationOraclePlainSwap.getAmount(
                LocalDate.of(1970, 1, 1).atStartOfDay(), LocalDate.of(1970, 1, 1).atStartOfDay()));
    verify(calibrationDataset).getDataAsCalibrationDataPointStream(isA(CalibrationParser.class));
    verify(calibrationDataset, atLeast(1)).getDataPoints();
    verify(calibrationDataset).getDate();
    verify(calibrationDataset).getFixingDataItems();
    verify(calibrationSpecProvider).getCalibrationSpec(isA(CalibrationContext.class));
  }

  /**
   * Test {@link ValuationOraclePlainSwap#getValue(LocalDateTime, LocalDateTime)}.
   *
   * <p>Method under test: {@link ValuationOraclePlainSwap#getValue(LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test getValue(LocalDateTime, LocalDateTime)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"BigDecimal ValuationOraclePlainSwap.getValue(LocalDateTime, LocalDateTime)"})
  void testGetValue() {
    // Arrange
    ArrayList<CalibrationDataset> scenarioList = new ArrayList<>();
    CalibrationDataset calibrationDataset =
        new CalibrationDataset(new HashSet<>(), LocalDate.of(1970, 1, 1).atStartOfDay());
    scenarioList.add(calibrationDataset);
    ValuationOraclePlainSwap valuationOraclePlainSwap =
        new ValuationOraclePlainSwap(new HashMap<>(), scenarioList);

    // Act and Assert
    assertNull(
        valuationOraclePlainSwap.getValue(
            LocalDate.of(1970, 1, 1).atStartOfDay(), LocalDate.of(1970, 1, 1).atStartOfDay()));
  }

  /**
   * Test {@link ValuationOraclePlainSwap#getValue(LocalDateTime, LocalDateTime)}.
   *
   * <p>Method under test: {@link ValuationOraclePlainSwap#getValue(LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test getValue(LocalDateTime, LocalDateTime)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"BigDecimal ValuationOraclePlainSwap.getValue(LocalDateTime, LocalDateTime)"})
  void testGetValue2() {
    // Arrange
    HashMap<String, AnalyticProduct> products = new HashMap<>();
    String createMinimalSmartDerivativeContractXmlResult =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();
    String currency =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();
    Cashflow cashflow =
        new Cashflow(
            currency,
            1.0E-9d,
            1.0E-9d,
            true,
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());

    products.put(createMinimalSmartDerivativeContractXmlResult, cashflow);

    ArrayList<CalibrationDataset> scenarioList = new ArrayList<>();
    CalibrationDataset calibrationDataset =
        new CalibrationDataset(new HashSet<>(), LocalDate.of(1970, 1, 1).atStartOfDay());
    scenarioList.add(calibrationDataset);

    ValuationOraclePlainSwap valuationOraclePlainSwap =
        new ValuationOraclePlainSwap(products, scenarioList);

    // Act and Assert
    assertThrows(
        SDCException.class,
        () ->
            valuationOraclePlainSwap.getValue(
                LocalDate.of(1970, 1, 1).atStartOfDay(), LocalDate.of(1970, 1, 1).atStartOfDay()));
  }

  /**
   * Test {@link ValuationOraclePlainSwap#getValue(LocalDateTime, LocalDateTime)}.
   *
   * <p>Method under test: {@link ValuationOraclePlainSwap#getValue(LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test getValue(LocalDateTime, LocalDateTime)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"BigDecimal ValuationOraclePlainSwap.getValue(LocalDateTime, LocalDateTime)"})
  void testGetValue3() {
    // Arrange
    CalibrationDataset calibrationDataset = mock(CalibrationDataset.class);
    when(calibrationDataset.getDataPoints())
        .thenThrow(
            new SDCException(
                ExceptionId.SDC_AUTH_ERROR,
                MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml()));
    when(calibrationDataset.getDate()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());

    ArrayList<CalibrationDataset> scenarioList = new ArrayList<>();
    scenarioList.add(calibrationDataset);
    ValuationOraclePlainSwap valuationOraclePlainSwap =
        new ValuationOraclePlainSwap(new HashMap<>(), scenarioList);

    // Act and Assert
    assertThrows(
        SDCException.class,
        () ->
            valuationOraclePlainSwap.getValue(
                LocalDate.of(1970, 1, 1).atStartOfDay(), LocalDate.of(1970, 1, 1).atStartOfDay()));
    verify(calibrationDataset).getDataPoints();
    verify(calibrationDataset).getDate();
  }

  /**
   * Test {@link ValuationOraclePlainSwap#getValue(LocalDateTime, LocalDateTime)}.
   *
   * <p>Method under test: {@link ValuationOraclePlainSwap#getValue(LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test getValue(LocalDateTime, LocalDateTime)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"BigDecimal ValuationOraclePlainSwap.getValue(LocalDateTime, LocalDateTime)"})
  void testGetValue4() {
    // Arrange
    HashSet<CalibrationDataItem> calibrationDataItemSet = new HashSet<>();
    String key = MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();
    String curveName =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();
    String productName =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();

    Spec spec =
        new Spec(
            key,
            curveName,
            productName,
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());
    calibrationDataItemSet.add(
        new CalibrationDataItem(spec, 1.0E-9d, LocalDate.of(1970, 1, 1).atStartOfDay()));

    CalibrationDataset calibrationDataset = mock(CalibrationDataset.class);
    when(calibrationDataset.getDataPoints()).thenReturn(calibrationDataItemSet);
    when(calibrationDataset.getFixingDataItems()).thenReturn(new HashSet<>());

    ArrayList<CalibrationSpecProvider> calibrationSpecProviderList = new ArrayList<>();
    Stream<CalibrationSpecProvider> streamResult = calibrationSpecProviderList.stream();
    when(calibrationDataset.getDataAsCalibrationDataPointStream(Mockito.<CalibrationParser>any()))
        .thenReturn(streamResult);
    when(calibrationDataset.getDate()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());

    ArrayList<CalibrationDataset> scenarioList = new ArrayList<>();
    scenarioList.add(calibrationDataset);
    ValuationOraclePlainSwap valuationOraclePlainSwap =
        new ValuationOraclePlainSwap(new HashMap<>(), scenarioList);

    // Act
    BigDecimal actualValue =
        valuationOraclePlainSwap.getValue(
            LocalDate.of(1970, 1, 1).atStartOfDay(), LocalDate.of(1970, 1, 1).atStartOfDay());

    // Assert
    verify(calibrationDataset).getDataAsCalibrationDataPointStream(isA(CalibrationParser.class));
    verify(calibrationDataset, atLeast(1)).getDataPoints();
    verify(calibrationDataset).getDate();
    verify(calibrationDataset).getFixingDataItems();
    assertNull(actualValue);
  }

  /**
   * Test {@link ValuationOraclePlainSwap#getValue(LocalDateTime, LocalDateTime)}.
   *
   * <p>Method under test: {@link ValuationOraclePlainSwap#getValue(LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test getValue(LocalDateTime, LocalDateTime)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"BigDecimal ValuationOraclePlainSwap.getValue(LocalDateTime, LocalDateTime)"})
  void testGetValue5() {
    // Arrange
    HashSet<CalibrationDataItem> calibrationDataItemSet = new HashSet<>();
    String key = MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();
    String curveName =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();
    String productName =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();

    Spec spec =
        new Spec(
            key,
            curveName,
            productName,
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());
    calibrationDataItemSet.add(
        new CalibrationDataItem(spec, 1.0E-9d, LocalDate.of(1970, 1, 1).atStartOfDay()));

    CalibrationDataset calibrationDataset = mock(CalibrationDataset.class);
    when(calibrationDataset.getDataPoints()).thenReturn(new HashSet<>());
    when(calibrationDataset.getFixingDataItems()).thenReturn(calibrationDataItemSet);

    ArrayList<CalibrationSpecProvider> calibrationSpecProviderList = new ArrayList<>();
    Stream<CalibrationSpecProvider> streamResult = calibrationSpecProviderList.stream();
    when(calibrationDataset.getDataAsCalibrationDataPointStream(Mockito.<CalibrationParser>any()))
        .thenReturn(streamResult);
    when(calibrationDataset.getDate()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());

    ArrayList<CalibrationDataset> scenarioList = new ArrayList<>();
    scenarioList.add(calibrationDataset);
    ValuationOraclePlainSwap valuationOraclePlainSwap =
        new ValuationOraclePlainSwap(new HashMap<>(), scenarioList);

    // Act
    BigDecimal actualValue =
        valuationOraclePlainSwap.getValue(
            LocalDate.of(1970, 1, 1).atStartOfDay(), LocalDate.of(1970, 1, 1).atStartOfDay());

    // Assert
    verify(calibrationDataset).getDataAsCalibrationDataPointStream(isA(CalibrationParser.class));
    verify(calibrationDataset, atLeast(1)).getDataPoints();
    verify(calibrationDataset).getDate();
    verify(calibrationDataset).getFixingDataItems();
    assertNull(actualValue);
  }

  /**
   * Test {@link ValuationOraclePlainSwap#getValue(LocalDateTime, LocalDateTime)}.
   *
   * <p>Method under test: {@link ValuationOraclePlainSwap#getValue(LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test getValue(LocalDateTime, LocalDateTime)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"BigDecimal ValuationOraclePlainSwap.getValue(LocalDateTime, LocalDateTime)"})
  void testGetValue6() {
    // Arrange
    CalibrationSpecProvider calibrationSpecProvider = mock(CalibrationSpecProvider.class);
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
    when(calibrationSpecProvider.getCalibrationSpec(Mockito.<CalibrationContext>any()))
        .thenReturn(calibrationSpec);

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
    ValuationOraclePlainSwap valuationOraclePlainSwap =
        new ValuationOraclePlainSwap(new HashMap<>(), scenarioList);

    // Act and Assert
    assertThrows(
        SDCException.class,
        () ->
            valuationOraclePlainSwap.getValue(
                LocalDate.of(1970, 1, 1).atStartOfDay(), LocalDate.of(1970, 1, 1).atStartOfDay()));
    verify(calibrationDataset).getDataAsCalibrationDataPointStream(isA(CalibrationParser.class));
    verify(calibrationDataset, atLeast(1)).getDataPoints();
    verify(calibrationDataset).getDate();
    verify(calibrationDataset).getFixingDataItems();
    verify(calibrationSpecProvider).getCalibrationSpec(isA(CalibrationContext.class));
  }

  /**
   * Test {@link ValuationOraclePlainSwap#getValue(LocalDateTime, LocalDateTime)}.
   *
   * <p>Method under test: {@link ValuationOraclePlainSwap#getValue(LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test getValue(LocalDateTime, LocalDateTime)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"BigDecimal ValuationOraclePlainSwap.getValue(LocalDateTime, LocalDateTime)"})
  void testGetValue7() {
    // Arrange
    CalibrationSpecProvider calibrationSpecProvider = mock(CalibrationSpecProvider.class);
    when(calibrationSpecProvider.getCalibrationSpec(Mockito.<CalibrationContext>any()))
        .thenThrow(
            new SDCException(
                ExceptionId.SDC_AUTH_ERROR,
                MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml()));

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
    ValuationOraclePlainSwap valuationOraclePlainSwap =
        new ValuationOraclePlainSwap(new HashMap<>(), scenarioList);

    // Act and Assert
    assertThrows(
        SDCException.class,
        () ->
            valuationOraclePlainSwap.getValue(
                LocalDate.of(1970, 1, 1).atStartOfDay(), LocalDate.of(1970, 1, 1).atStartOfDay()));
    verify(calibrationDataset).getDataAsCalibrationDataPointStream(isA(CalibrationParser.class));
    verify(calibrationDataset, atLeast(1)).getDataPoints();
    verify(calibrationDataset).getDate();
    verify(calibrationDataset).getFixingDataItems();
    verify(calibrationSpecProvider).getCalibrationSpec(isA(CalibrationContext.class));
  }

  /**
   * Test {@link ValuationOraclePlainSwap#getValue(LocalDateTime, LocalDateTime)}.
   *
   * <p>Method under test: {@link ValuationOraclePlainSwap#getValue(LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test getValue(LocalDateTime, LocalDateTime)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"BigDecimal ValuationOraclePlainSwap.getValue(LocalDateTime, LocalDateTime)"})
  void testGetValue8() {
    // Arrange
    CalibrationSpecProvider calibrationSpecProvider = mock(CalibrationSpecProvider.class);
    String type = MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();
    String forwardCurveReceiverName =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();

    CalibrationSpec calibrationSpec =
        new CalibrationSpec(
            type,
            new double[] {10.0d, 0.5d, 10.0d, 0.5d},
            forwardCurveReceiverName,
            10.0d,
            "discount-EUR-OIS",
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml(),
            10.0d);
    when(calibrationSpecProvider.getCalibrationSpec(Mockito.<CalibrationContext>any()))
        .thenReturn(calibrationSpec);

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
    ValuationOraclePlainSwap valuationOraclePlainSwap =
        new ValuationOraclePlainSwap(new HashMap<>(), scenarioList);

    // Act and Assert
    assertThrows(
        SDCException.class,
        () ->
            valuationOraclePlainSwap.getValue(
                LocalDate.of(1970, 1, 1).atStartOfDay(), LocalDate.of(1970, 1, 1).atStartOfDay()));
    verify(calibrationDataset).getDataAsCalibrationDataPointStream(isA(CalibrationParser.class));
    verify(calibrationDataset, atLeast(1)).getDataPoints();
    verify(calibrationDataset).getDate();
    verify(calibrationDataset).getFixingDataItems();
    verify(calibrationSpecProvider).getCalibrationSpec(isA(CalibrationContext.class));
  }

  /**
   * Test {@link ValuationOraclePlainSwap#getValue(LocalDateTime, LocalDateTime)}.
   *
   * <ul>
   *   <li>Given {@link CalibrationDataset} {@link CalibrationDataset#getDataPoints()} return {@link
   *       HashSet#HashSet()}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ValuationOraclePlainSwap#getValue(LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName(
      "Test getValue(LocalDateTime, LocalDateTime); given CalibrationDataset getDataPoints() return HashSet(); then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"BigDecimal ValuationOraclePlainSwap.getValue(LocalDateTime, LocalDateTime)"})
  void testGetValue_givenCalibrationDatasetGetDataPointsReturnHashSet_thenReturnNull() {
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
    ValuationOraclePlainSwap valuationOraclePlainSwap =
        new ValuationOraclePlainSwap(new HashMap<>(), scenarioList);

    // Act
    BigDecimal actualValue =
        valuationOraclePlainSwap.getValue(
            LocalDate.of(1970, 1, 1).atStartOfDay(), LocalDate.of(1970, 1, 1).atStartOfDay());

    // Assert
    verify(calibrationDataset).getDataAsCalibrationDataPointStream(isA(CalibrationParser.class));
    verify(calibrationDataset, atLeast(1)).getDataPoints();
    verify(calibrationDataset).getDate();
    verify(calibrationDataset).getFixingDataItems();
    assertNull(actualValue);
  }

  /**
   * Test {@link ValuationOraclePlainSwap#getValues(LocalDateTime, LocalDateTime)}.
   *
   * <p>Method under test: {@link ValuationOraclePlainSwap#getValues(LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test getValues(LocalDateTime, LocalDateTime)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map ValuationOraclePlainSwap.getValues(LocalDateTime, LocalDateTime)"})
  void testGetValues() {
    // Arrange
    ArrayList<CalibrationDataset> scenarioList = new ArrayList<>();
    CalibrationDataset calibrationDataset =
        new CalibrationDataset(new HashSet<>(), LocalDate.of(1970, 1, 1).atStartOfDay());
    scenarioList.add(calibrationDataset);
    ValuationOraclePlainSwap valuationOraclePlainSwap =
        new ValuationOraclePlainSwap(new HashMap<>(), scenarioList);

    // Act and Assert
    assertTrue(
        valuationOraclePlainSwap
            .getValues(
                LocalDate.of(1970, 1, 1).atStartOfDay(), LocalDate.of(1970, 1, 1).atStartOfDay())
            .isEmpty());
  }

  /**
   * Test {@link ValuationOraclePlainSwap#getValues(LocalDateTime, LocalDateTime)}.
   *
   * <p>Method under test: {@link ValuationOraclePlainSwap#getValues(LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test getValues(LocalDateTime, LocalDateTime)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map ValuationOraclePlainSwap.getValues(LocalDateTime, LocalDateTime)"})
  void testGetValues2() {
    // Arrange
    HashMap<String, AnalyticProduct> products = new HashMap<>();
    String createMinimalSmartDerivativeContractXmlResult =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();
    String currency =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();
    Cashflow cashflow =
        new Cashflow(
            currency,
            1.0E-9d,
            1.0E-9d,
            true,
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());

    products.put(createMinimalSmartDerivativeContractXmlResult, cashflow);

    ArrayList<CalibrationDataset> scenarioList = new ArrayList<>();
    CalibrationDataset calibrationDataset =
        new CalibrationDataset(new HashSet<>(), LocalDate.of(1970, 1, 1).atStartOfDay());
    scenarioList.add(calibrationDataset);

    ValuationOraclePlainSwap valuationOraclePlainSwap =
        new ValuationOraclePlainSwap(products, scenarioList);

    // Act and Assert
    assertThrows(
        SDCException.class,
        () ->
            valuationOraclePlainSwap.getValues(
                LocalDate.of(1970, 1, 1).atStartOfDay(), LocalDate.of(1970, 1, 1).atStartOfDay()));
  }

  /**
   * Test {@link ValuationOraclePlainSwap#getValues(LocalDateTime, LocalDateTime)}.
   *
   * <p>Method under test: {@link ValuationOraclePlainSwap#getValues(LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test getValues(LocalDateTime, LocalDateTime)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map ValuationOraclePlainSwap.getValues(LocalDateTime, LocalDateTime)"})
  void testGetValues3() {
    // Arrange
    CalibrationDataset calibrationDataset = mock(CalibrationDataset.class);
    when(calibrationDataset.getDataPoints())
        .thenThrow(
            new SDCException(
                ExceptionId.SDC_AUTH_ERROR,
                MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml()));
    when(calibrationDataset.getDate()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());

    ArrayList<CalibrationDataset> scenarioList = new ArrayList<>();
    scenarioList.add(calibrationDataset);
    ValuationOraclePlainSwap valuationOraclePlainSwap =
        new ValuationOraclePlainSwap(new HashMap<>(), scenarioList);

    // Act and Assert
    assertThrows(
        SDCException.class,
        () ->
            valuationOraclePlainSwap.getValues(
                LocalDate.of(1970, 1, 1).atStartOfDay(), LocalDate.of(1970, 1, 1).atStartOfDay()));
    verify(calibrationDataset).getDataPoints();
    verify(calibrationDataset).getDate();
  }

  /**
   * Test {@link ValuationOraclePlainSwap#getValues(LocalDateTime, LocalDateTime)}.
   *
   * <p>Method under test: {@link ValuationOraclePlainSwap#getValues(LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test getValues(LocalDateTime, LocalDateTime)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map ValuationOraclePlainSwap.getValues(LocalDateTime, LocalDateTime)"})
  void testGetValues4() {
    // Arrange
    HashSet<CalibrationDataItem> calibrationDataItemSet = new HashSet<>();
    String key = MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();
    String curveName =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();
    String productName =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();

    Spec spec =
        new Spec(
            key,
            curveName,
            productName,
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());
    calibrationDataItemSet.add(
        new CalibrationDataItem(spec, 1.0E-9d, LocalDate.of(1970, 1, 1).atStartOfDay()));

    CalibrationDataset calibrationDataset = mock(CalibrationDataset.class);
    when(calibrationDataset.getDataPoints()).thenReturn(calibrationDataItemSet);
    when(calibrationDataset.getFixingDataItems()).thenReturn(new HashSet<>());

    ArrayList<CalibrationSpecProvider> calibrationSpecProviderList = new ArrayList<>();
    Stream<CalibrationSpecProvider> streamResult = calibrationSpecProviderList.stream();
    when(calibrationDataset.getDataAsCalibrationDataPointStream(Mockito.<CalibrationParser>any()))
        .thenReturn(streamResult);
    when(calibrationDataset.getDate()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());

    ArrayList<CalibrationDataset> scenarioList = new ArrayList<>();
    scenarioList.add(calibrationDataset);
    ValuationOraclePlainSwap valuationOraclePlainSwap =
        new ValuationOraclePlainSwap(new HashMap<>(), scenarioList);

    // Act
    Map<String, BigDecimal> actualValues =
        valuationOraclePlainSwap.getValues(
            LocalDate.of(1970, 1, 1).atStartOfDay(), LocalDate.of(1970, 1, 1).atStartOfDay());

    // Assert
    verify(calibrationDataset).getDataAsCalibrationDataPointStream(isA(CalibrationParser.class));
    verify(calibrationDataset, atLeast(1)).getDataPoints();
    verify(calibrationDataset).getDate();
    verify(calibrationDataset).getFixingDataItems();
    assertTrue(actualValues.isEmpty());
  }

  /**
   * Test {@link ValuationOraclePlainSwap#getValues(LocalDateTime, LocalDateTime)}.
   *
   * <p>Method under test: {@link ValuationOraclePlainSwap#getValues(LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test getValues(LocalDateTime, LocalDateTime)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map ValuationOraclePlainSwap.getValues(LocalDateTime, LocalDateTime)"})
  void testGetValues5() {
    // Arrange
    HashSet<CalibrationDataItem> calibrationDataItemSet = new HashSet<>();
    String key = MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();
    String curveName =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();
    String productName =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();

    Spec spec =
        new Spec(
            key,
            curveName,
            productName,
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());
    calibrationDataItemSet.add(
        new CalibrationDataItem(spec, 1.0E-9d, LocalDate.of(1970, 1, 1).atStartOfDay()));

    CalibrationDataset calibrationDataset = mock(CalibrationDataset.class);
    when(calibrationDataset.getDataPoints()).thenReturn(new HashSet<>());
    when(calibrationDataset.getFixingDataItems()).thenReturn(calibrationDataItemSet);

    ArrayList<CalibrationSpecProvider> calibrationSpecProviderList = new ArrayList<>();
    Stream<CalibrationSpecProvider> streamResult = calibrationSpecProviderList.stream();
    when(calibrationDataset.getDataAsCalibrationDataPointStream(Mockito.<CalibrationParser>any()))
        .thenReturn(streamResult);
    when(calibrationDataset.getDate()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());

    ArrayList<CalibrationDataset> scenarioList = new ArrayList<>();
    scenarioList.add(calibrationDataset);
    ValuationOraclePlainSwap valuationOraclePlainSwap =
        new ValuationOraclePlainSwap(new HashMap<>(), scenarioList);

    // Act
    Map<String, BigDecimal> actualValues =
        valuationOraclePlainSwap.getValues(
            LocalDate.of(1970, 1, 1).atStartOfDay(), LocalDate.of(1970, 1, 1).atStartOfDay());

    // Assert
    verify(calibrationDataset).getDataAsCalibrationDataPointStream(isA(CalibrationParser.class));
    verify(calibrationDataset, atLeast(1)).getDataPoints();
    verify(calibrationDataset).getDate();
    verify(calibrationDataset).getFixingDataItems();
    assertTrue(actualValues.isEmpty());
  }

  /**
   * Test {@link ValuationOraclePlainSwap#getValues(LocalDateTime, LocalDateTime)}.
   *
   * <p>Method under test: {@link ValuationOraclePlainSwap#getValues(LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test getValues(LocalDateTime, LocalDateTime)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map ValuationOraclePlainSwap.getValues(LocalDateTime, LocalDateTime)"})
  void testGetValues6() {
    // Arrange
    CalibrationSpecProvider calibrationSpecProvider = mock(CalibrationSpecProvider.class);
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
    when(calibrationSpecProvider.getCalibrationSpec(Mockito.<CalibrationContext>any()))
        .thenReturn(calibrationSpec);

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
    ValuationOraclePlainSwap valuationOraclePlainSwap =
        new ValuationOraclePlainSwap(new HashMap<>(), scenarioList);

    // Act and Assert
    assertThrows(
        SDCException.class,
        () ->
            valuationOraclePlainSwap.getValues(
                LocalDate.of(1970, 1, 1).atStartOfDay(), LocalDate.of(1970, 1, 1).atStartOfDay()));
    verify(calibrationDataset).getDataAsCalibrationDataPointStream(isA(CalibrationParser.class));
    verify(calibrationDataset, atLeast(1)).getDataPoints();
    verify(calibrationDataset).getDate();
    verify(calibrationDataset).getFixingDataItems();
    verify(calibrationSpecProvider).getCalibrationSpec(isA(CalibrationContext.class));
  }

  /**
   * Test {@link ValuationOraclePlainSwap#getValues(LocalDateTime, LocalDateTime)}.
   *
   * <p>Method under test: {@link ValuationOraclePlainSwap#getValues(LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test getValues(LocalDateTime, LocalDateTime)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map ValuationOraclePlainSwap.getValues(LocalDateTime, LocalDateTime)"})
  void testGetValues7() {
    // Arrange
    CalibrationSpecProvider calibrationSpecProvider = mock(CalibrationSpecProvider.class);
    when(calibrationSpecProvider.getCalibrationSpec(Mockito.<CalibrationContext>any()))
        .thenThrow(
            new SDCException(
                ExceptionId.SDC_AUTH_ERROR,
                MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml()));

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
    ValuationOraclePlainSwap valuationOraclePlainSwap =
        new ValuationOraclePlainSwap(new HashMap<>(), scenarioList);

    // Act and Assert
    assertThrows(
        SDCException.class,
        () ->
            valuationOraclePlainSwap.getValues(
                LocalDate.of(1970, 1, 1).atStartOfDay(), LocalDate.of(1970, 1, 1).atStartOfDay()));
    verify(calibrationDataset).getDataAsCalibrationDataPointStream(isA(CalibrationParser.class));
    verify(calibrationDataset, atLeast(1)).getDataPoints();
    verify(calibrationDataset).getDate();
    verify(calibrationDataset).getFixingDataItems();
    verify(calibrationSpecProvider).getCalibrationSpec(isA(CalibrationContext.class));
  }

  /**
   * Test {@link ValuationOraclePlainSwap#getValues(LocalDateTime, LocalDateTime)}.
   *
   * <p>Method under test: {@link ValuationOraclePlainSwap#getValues(LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test getValues(LocalDateTime, LocalDateTime)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map ValuationOraclePlainSwap.getValues(LocalDateTime, LocalDateTime)"})
  void testGetValues8() {
    // Arrange
    CalibrationSpecProvider calibrationSpecProvider = mock(CalibrationSpecProvider.class);
    String type = MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();
    String forwardCurveReceiverName =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();

    CalibrationSpec calibrationSpec =
        new CalibrationSpec(
            type,
            new double[] {10.0d, 0.5d, 10.0d, 0.5d},
            forwardCurveReceiverName,
            10.0d,
            "discount-EUR-OIS",
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml(),
            10.0d);
    when(calibrationSpecProvider.getCalibrationSpec(Mockito.<CalibrationContext>any()))
        .thenReturn(calibrationSpec);

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
    ValuationOraclePlainSwap valuationOraclePlainSwap =
        new ValuationOraclePlainSwap(new HashMap<>(), scenarioList);

    // Act and Assert
    assertThrows(
        SDCException.class,
        () ->
            valuationOraclePlainSwap.getValues(
                LocalDate.of(1970, 1, 1).atStartOfDay(), LocalDate.of(1970, 1, 1).atStartOfDay()));
    verify(calibrationDataset).getDataAsCalibrationDataPointStream(isA(CalibrationParser.class));
    verify(calibrationDataset, atLeast(1)).getDataPoints();
    verify(calibrationDataset).getDate();
    verify(calibrationDataset).getFixingDataItems();
    verify(calibrationSpecProvider).getCalibrationSpec(isA(CalibrationContext.class));
  }

  /**
   * Test {@link ValuationOraclePlainSwap#getValues(LocalDateTime, LocalDateTime)}.
   *
   * <ul>
   *   <li>Given {@link CalibrationDataset} {@link CalibrationDataset#getDataPoints()} return {@link
   *       HashSet#HashSet()}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link ValuationOraclePlainSwap#getValues(LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName(
      "Test getValues(LocalDateTime, LocalDateTime); given CalibrationDataset getDataPoints() return HashSet(); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map ValuationOraclePlainSwap.getValues(LocalDateTime, LocalDateTime)"})
  void testGetValues_givenCalibrationDatasetGetDataPointsReturnHashSet_thenReturnEmpty() {
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
    ValuationOraclePlainSwap valuationOraclePlainSwap =
        new ValuationOraclePlainSwap(new HashMap<>(), scenarioList);

    // Act
    Map<String, BigDecimal> actualValues =
        valuationOraclePlainSwap.getValues(
            LocalDate.of(1970, 1, 1).atStartOfDay(), LocalDate.of(1970, 1, 1).atStartOfDay());

    // Assert
    verify(calibrationDataset).getDataAsCalibrationDataPointStream(isA(CalibrationParser.class));
    verify(calibrationDataset, atLeast(1)).getDataPoints();
    verify(calibrationDataset).getDate();
    verify(calibrationDataset).getFixingDataItems();
    assertTrue(actualValues.isEmpty());
  }

  /**
   * Test {@link ValuationOraclePlainSwap#getValues(LocalDateTime, LocalDateTime)}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ValuationOraclePlainSwap#getValues(LocalDateTime, LocalDateTime)}
   */
  @Test
  @DisplayName("Test getValues(LocalDateTime, LocalDateTime); then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map ValuationOraclePlainSwap.getValues(LocalDateTime, LocalDateTime)"})
  void testGetValues_thenReturnNull() {
    // Arrange
    HashMap<String, AnalyticProduct> products = new HashMap<>();
    ValuationOraclePlainSwap valuationOraclePlainSwap =
        new ValuationOraclePlainSwap(products, new ArrayList<>());

    // Act and Assert
    assertNull(
        valuationOraclePlainSwap.getValues(
            LocalDate.of(1970, 1, 1).atStartOfDay(), LocalDate.of(1970, 1, 1).atStartOfDay()));
  }
}
