package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyDouble;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.finmath.smartcontract.model.MarketDataList;
import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationDataItem.Spec;
import net.finmath.smartcontract.valuation.marketdata.data.MarketDataPoint;
import net.finmath.smartcontract.valuation.marketdata.generators.MarketDataGeneratorLauncherDiffblueBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CalibrationDatasetDiffblueTest {
  @InjectMocks private CalibrationDataset calibrationDataset;

  @Mock private Set<CalibrationDataItem> set;

  /**
   * Test {@link CalibrationDataset#CalibrationDataset(Set, LocalDateTime)}.
   *
   * <ul>
   *   <li>Then return CalibrationDataItems is {@link HashSet#HashSet()}.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationDataset#CalibrationDataset(Set, LocalDateTime)}
   */
  @Test
  @DisplayName(
      "Test new CalibrationDataset(Set, LocalDateTime); then return CalibrationDataItems is HashSet()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CalibrationDataset.<init>(Set, LocalDateTime)"})
  void testNewCalibrationDataset_thenReturnCalibrationDataItemsIsHashSet() {
    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
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
    curveDataPointSet.add(
        new CalibrationDataItem(spec, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));
    LocalDateTime scenarioDate = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act
    CalibrationDataset actualCalibrationDataset =
        new CalibrationDataset(curveDataPointSet, scenarioDate);

    // Assert
    assertTrue(actualCalibrationDataset.getFixingDataItems().isEmpty());
    assertEquals(curveDataPointSet, actualCalibrationDataset.getCalibrationDataItems());
    assertEquals(curveDataPointSet, actualCalibrationDataset.getDataPoints());
    assertSame(scenarioDate, actualCalibrationDataset.getDate());
  }

  /**
   * Test {@link CalibrationDataset#CalibrationDataset(Set, LocalDateTime)}.
   *
   * <ul>
   *   <li>Then return CalibrationDataItems is {@link LinkedHashSet#LinkedHashSet()}.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationDataset#CalibrationDataset(Set, LocalDateTime)}
   */
  @Test
  @DisplayName(
      "Test new CalibrationDataset(Set, LocalDateTime); then return CalibrationDataItems is LinkedHashSet()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CalibrationDataset.<init>(Set, LocalDateTime)"})
  void testNewCalibrationDataset_thenReturnCalibrationDataItemsIsLinkedHashSet() {
    // Arrange
    CalibrationDataItem calibrationDataItem = mock(CalibrationDataItem.class);
    when(calibrationDataItem.getProductName())
        .thenReturn(
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());

    LinkedHashSet<CalibrationDataItem> curveDataPointSet = new LinkedHashSet<>();
    curveDataPointSet.add(calibrationDataItem);
    LocalDateTime scenarioDate = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act
    CalibrationDataset actualCalibrationDataset =
        new CalibrationDataset(curveDataPointSet, scenarioDate);

    // Assert
    verify(calibrationDataItem, atLeast(1)).getProductName();
    assertTrue(actualCalibrationDataset.getFixingDataItems().isEmpty());
    assertEquals(curveDataPointSet, actualCalibrationDataset.getCalibrationDataItems());
    assertEquals(curveDataPointSet, actualCalibrationDataset.getDataPoints());
    assertSame(scenarioDate, actualCalibrationDataset.getDate());
  }

  /**
   * Test {@link CalibrationDataset#CalibrationDataset(Set, LocalDateTime)}.
   *
   * <ul>
   *   <li>Then return FixingDataItems is {@link LinkedHashSet#LinkedHashSet()}.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationDataset#CalibrationDataset(Set, LocalDateTime)}
   */
  @Test
  @DisplayName(
      "Test new CalibrationDataset(Set, LocalDateTime); then return FixingDataItems is LinkedHashSet()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CalibrationDataset.<init>(Set, LocalDateTime)"})
  void testNewCalibrationDataset_thenReturnFixingDataItemsIsLinkedHashSet() {
    // Arrange
    LinkedHashSet<CalibrationDataItem> curveDataPointSet = new LinkedHashSet<>();
    String key = MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();
    String curveName =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();

    Spec spec =
        new Spec(
            key,
            curveName,
            "Fixing",
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());
    curveDataPointSet.add(
        new CalibrationDataItem(spec, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));
    LocalDateTime scenarioDate = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act
    CalibrationDataset actualCalibrationDataset =
        new CalibrationDataset(curveDataPointSet, scenarioDate);

    // Assert
    assertTrue(actualCalibrationDataset.getCalibrationDataItems().isEmpty());
    assertEquals(curveDataPointSet, actualCalibrationDataset.getDataPoints());
    assertEquals(curveDataPointSet, actualCalibrationDataset.getFixingDataItems());
    assertSame(scenarioDate, actualCalibrationDataset.getDate());
  }

  /**
   * Test {@link CalibrationDataset#CalibrationDataset(Set, LocalDateTime)}.
   *
   * <ul>
   *   <li>Then return FixingDataItems size is one.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationDataset#CalibrationDataset(Set, LocalDateTime)}
   */
  @Test
  @DisplayName(
      "Test new CalibrationDataset(Set, LocalDateTime); then return FixingDataItems size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CalibrationDataset.<init>(Set, LocalDateTime)"})
  void testNewCalibrationDataset_thenReturnFixingDataItemsSizeIsOne() {
    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    String key = MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();
    String curveName =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();

    Spec spec =
        new Spec(
            key,
            curveName,
            "Fixing",
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());
    curveDataPointSet.add(
        new CalibrationDataItem(spec, 0.5d, LocalDate.of(1970, 1, 1).atStartOfDay()));
    String key2 = MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();
    String curveName2 =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();
    String productName =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();

    Spec spec2 =
        new Spec(
            key2,
            curveName2,
            productName,
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());
    curveDataPointSet.add(
        new CalibrationDataItem(spec2, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));
    LocalDateTime scenarioDate = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act
    CalibrationDataset actualCalibrationDataset =
        new CalibrationDataset(curveDataPointSet, scenarioDate);

    // Assert
    assertEquals(1, actualCalibrationDataset.getCalibrationDataItems().size());
    assertEquals(1, actualCalibrationDataset.getFixingDataItems().size());
    assertEquals(curveDataPointSet, actualCalibrationDataset.getDataPoints());
    assertSame(scenarioDate, actualCalibrationDataset.getDate());
  }

  /**
   * Test {@link CalibrationDataset#CalibrationDataset(Set, LocalDateTime)}.
   *
   * <ul>
   *   <li>Then return FixingDataItems size is two.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationDataset#CalibrationDataset(Set, LocalDateTime)}
   */
  @Test
  @DisplayName(
      "Test new CalibrationDataset(Set, LocalDateTime); then return FixingDataItems size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CalibrationDataset.<init>(Set, LocalDateTime)"})
  void testNewCalibrationDataset_thenReturnFixingDataItemsSizeIsTwo() {
    // Arrange
    CalibrationDataItem calibrationDataItem = mock(CalibrationDataItem.class);
    when(calibrationDataItem.getProductName())
        .thenReturn(
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());

    CalibrationDataItem calibrationDataItem2 = mock(CalibrationDataItem.class);
    when(calibrationDataItem2.getDate()).thenReturn(LocalDate.of(1970, 1, 1));
    when(calibrationDataItem2.getProductName()).thenReturn("Fixing");

    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    String key = MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();
    Spec spec =
        new Spec(
            key,
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml(),
            "Fixing",
            "42");
    curveDataPointSet.add(
        new CalibrationDataItem(spec, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));
    curveDataPointSet.add(calibrationDataItem2);
    curveDataPointSet.add(calibrationDataItem);
    LocalDateTime scenarioDate = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act
    CalibrationDataset actualCalibrationDataset =
        new CalibrationDataset(curveDataPointSet, scenarioDate);

    // Assert
    verify(calibrationDataItem2).getDate();
    verify(calibrationDataItem2, atLeast(1)).getProductName();
    verify(calibrationDataItem, atLeast(1)).getProductName();
    assertEquals(1, actualCalibrationDataset.getCalibrationDataItems().size());
    assertEquals(2, actualCalibrationDataset.getFixingDataItems().size());
    assertEquals(curveDataPointSet, actualCalibrationDataset.getDataPoints());
    assertSame(scenarioDate, actualCalibrationDataset.getDate());
  }

  /**
   * Test {@link CalibrationDataset#CalibrationDataset(Set, LocalDateTime)}.
   *
   * <ul>
   *   <li>When {@link HashSet#HashSet()}.
   *   <li>Then return DataPoints Empty.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationDataset#CalibrationDataset(Set, LocalDateTime)}
   */
  @Test
  @DisplayName(
      "Test new CalibrationDataset(Set, LocalDateTime); when HashSet(); then return DataPoints Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CalibrationDataset.<init>(Set, LocalDateTime)"})
  void testNewCalibrationDataset_whenHashSet_thenReturnDataPointsEmpty() {
    // Arrange
    LocalDateTime scenarioDate = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act
    CalibrationDataset actualCalibrationDataset =
        new CalibrationDataset(new HashSet<>(), scenarioDate);

    // Assert
    assertTrue(actualCalibrationDataset.getCalibrationDataItems().isEmpty());
    assertTrue(actualCalibrationDataset.getDataPoints().isEmpty());
    assertTrue(actualCalibrationDataset.getFixingDataItems().isEmpty());
    assertSame(scenarioDate, actualCalibrationDataset.getDate());
  }

  /**
   * Test {@link CalibrationDataset#getScaled(double)}.
   *
   * <p>Method under test: {@link CalibrationDataset#getScaled(double)}
   */
  @Test
  @DisplayName("Test getScaled(double)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"CalibrationDataset CalibrationDataset.getScaled(double)"})
  void testGetScaled() {
    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
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
    curveDataPointSet.add(
        new CalibrationDataItem(spec, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));

    LocalDate ofResult = LocalDate.of(1970, 1, 1);
    CalibrationDataset calibrationDataset =
        new CalibrationDataset(curveDataPointSet, ofResult.atStartOfDay());

    // Act
    CalibrationDataset actualScaled = calibrationDataset.getScaled(10.0d);

    // Assert
    LocalDateTime date = actualScaled.getDate();
    assertEquals("00:00", date.toLocalTime().toString());
    LocalDate toLocalDateResult = date.toLocalDate();
    assertEquals("1970-01-01", toLocalDateResult.toString());
    Set<CalibrationDataItem> calibrationDataItems = actualScaled.getCalibrationDataItems();
    assertEquals(1, calibrationDataItems.size());
    assertTrue(actualScaled.getFixingDataItems().isEmpty());
    assertEquals(calibrationDataItems, actualScaled.getDataPoints());
    assertSame(ofResult, toLocalDateResult);
  }

  /**
   * Test {@link CalibrationDataset#getScaled(double)}.
   *
   * <p>Method under test: {@link CalibrationDataset#getScaled(double)}
   */
  @Test
  @DisplayName("Test getScaled(double)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"CalibrationDataset CalibrationDataset.getScaled(double)"})
  void testGetScaled2() {
    // Arrange
    CalibrationDataItem calibrationDataItem = mock(CalibrationDataItem.class);
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
    when(calibrationDataItem.getClonedScaled(anyDouble()))
        .thenReturn(new CalibrationDataItem(spec, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));
    when(calibrationDataItem.getProductName())
        .thenReturn(
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());

    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    curveDataPointSet.add(calibrationDataItem);

    LocalDate ofResult = LocalDate.of(1970, 1, 1);
    CalibrationDataset calibrationDataset =
        new CalibrationDataset(curveDataPointSet, ofResult.atStartOfDay());

    // Act
    CalibrationDataset actualScaled = calibrationDataset.getScaled(10.0d);

    // Assert
    verify(calibrationDataItem).getClonedScaled(10.0d);
    verify(calibrationDataItem, atLeast(1)).getProductName();
    LocalDateTime date = actualScaled.getDate();
    assertEquals("00:00", date.toLocalTime().toString());
    LocalDate toLocalDateResult = date.toLocalDate();
    assertEquals("1970-01-01", toLocalDateResult.toString());
    Set<CalibrationDataItem> calibrationDataItems = actualScaled.getCalibrationDataItems();
    assertEquals(1, calibrationDataItems.size());
    assertTrue(actualScaled.getFixingDataItems().isEmpty());
    assertEquals(calibrationDataItems, actualScaled.getDataPoints());
    assertSame(ofResult, toLocalDateResult);
  }

  /**
   * Test {@link CalibrationDataset#getScaled(double)}.
   *
   * <ul>
   *   <li>Given {@link CalibrationDataItem} {@link CalibrationDataItem#getClonedScaled(double)}
   *       return {@link CalibrationDataItem}.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationDataset#getScaled(double)}
   */
  @Test
  @DisplayName(
      "Test getScaled(double); given CalibrationDataItem getClonedScaled(double) return CalibrationDataItem")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"CalibrationDataset CalibrationDataset.getScaled(double)"})
  void testGetScaled_givenCalibrationDataItemGetClonedScaledReturnCalibrationDataItem() {
    // Arrange
    CalibrationDataItem calibrationDataItem = mock(CalibrationDataItem.class);
    when(calibrationDataItem.getProductName())
        .thenReturn(
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());

    CalibrationDataItem calibrationDataItem2 = mock(CalibrationDataItem.class);
    when(calibrationDataItem2.getClonedScaled(anyDouble())).thenReturn(calibrationDataItem);
    when(calibrationDataItem2.getProductName())
        .thenReturn(
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());

    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    curveDataPointSet.add(calibrationDataItem2);

    LocalDate ofResult = LocalDate.of(1970, 1, 1);
    CalibrationDataset calibrationDataset =
        new CalibrationDataset(curveDataPointSet, ofResult.atStartOfDay());

    // Act
    CalibrationDataset actualScaled = calibrationDataset.getScaled(10.0d);

    // Assert
    verify(calibrationDataItem2).getClonedScaled(10.0d);
    verify(calibrationDataItem2, atLeast(1)).getProductName();
    verify(calibrationDataItem, atLeast(1)).getProductName();
    LocalDateTime date = actualScaled.getDate();
    assertEquals("00:00", date.toLocalTime().toString());
    LocalDate toLocalDateResult = date.toLocalDate();
    assertEquals("1970-01-01", toLocalDateResult.toString());
    Set<CalibrationDataItem> calibrationDataItems = actualScaled.getCalibrationDataItems();
    assertEquals(1, calibrationDataItems.size());
    assertTrue(actualScaled.getFixingDataItems().isEmpty());
    assertEquals(calibrationDataItems, actualScaled.getDataPoints());
    assertSame(ofResult, toLocalDateResult);
  }

  /**
   * Test {@link CalibrationDataset#getScaled(double)}.
   *
   * <ul>
   *   <li>Then return DataPoints Empty.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationDataset#getScaled(double)}
   */
  @Test
  @DisplayName("Test getScaled(double); then return DataPoints Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"CalibrationDataset CalibrationDataset.getScaled(double)"})
  void testGetScaled_thenReturnDataPointsEmpty() {
    // Arrange
    LocalDate ofResult = LocalDate.of(1970, 1, 1);
    CalibrationDataset calibrationDataset =
        new CalibrationDataset(new HashSet<>(), ofResult.atStartOfDay());

    // Act
    CalibrationDataset actualScaled = calibrationDataset.getScaled(10.0d);

    // Assert
    LocalDateTime date = actualScaled.getDate();
    assertEquals("00:00", date.toLocalTime().toString());
    LocalDate toLocalDateResult = date.toLocalDate();
    assertEquals("1970-01-01", toLocalDateResult.toString());
    assertTrue(actualScaled.getCalibrationDataItems().isEmpty());
    assertTrue(actualScaled.getDataPoints().isEmpty());
    assertTrue(actualScaled.getFixingDataItems().isEmpty());
    assertSame(ofResult, toLocalDateResult);
  }

  /**
   * Test {@link CalibrationDataset#getScaled(double)}.
   *
   * <ul>
   *   <li>Then return DataPoints size is one.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationDataset#getScaled(double)}
   */
  @Test
  @DisplayName("Test getScaled(double); then return DataPoints size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"CalibrationDataset CalibrationDataset.getScaled(double)"})
  void testGetScaled_thenReturnDataPointsSizeIsOne() {
    // Arrange
    CalibrationDataItem calibrationDataItem = mock(CalibrationDataItem.class);
    String key = MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();
    String curveName =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();

    Spec spec =
        new Spec(
            key,
            curveName,
            "Fixing",
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());
    when(calibrationDataItem.getClonedScaled(anyDouble()))
        .thenReturn(new CalibrationDataItem(spec, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));
    when(calibrationDataItem.getProductName())
        .thenReturn(
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());

    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    curveDataPointSet.add(calibrationDataItem);

    LocalDate ofResult = LocalDate.of(1970, 1, 1);
    CalibrationDataset calibrationDataset =
        new CalibrationDataset(curveDataPointSet, ofResult.atStartOfDay());

    // Act
    CalibrationDataset actualScaled = calibrationDataset.getScaled(10.0d);

    // Assert
    verify(calibrationDataItem).getClonedScaled(10.0d);
    verify(calibrationDataItem, atLeast(1)).getProductName();
    LocalDateTime date = actualScaled.getDate();
    assertEquals("00:00", date.toLocalTime().toString());
    LocalDate toLocalDateResult = date.toLocalDate();
    assertEquals("1970-01-01", toLocalDateResult.toString());
    Set<CalibrationDataItem> dataPoints = actualScaled.getDataPoints();
    assertEquals(1, dataPoints.size());
    assertTrue(actualScaled.getCalibrationDataItems().isEmpty());
    assertEquals(dataPoints, actualScaled.getFixingDataItems());
    assertSame(ofResult, toLocalDateResult);
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link CalibrationDataset#getCalibrationDataItems()}
   *   <li>{@link CalibrationDataset#getDate()}
   *   <li>{@link CalibrationDataset#getFixingDataItems()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Set CalibrationDataset.getCalibrationDataItems()",
    "LocalDateTime CalibrationDataset.getDate()",
    "Set CalibrationDataset.getFixingDataItems()"
  })
  void testGettersAndSetters() {
    // Arrange
    CalibrationDataset calibrationDataset =
        new CalibrationDataset(new HashSet<>(), LocalDate.of(1970, 1, 1).atStartOfDay());

    // Act
    Set<CalibrationDataItem> actualCalibrationDataItems =
        calibrationDataset.getCalibrationDataItems();
    LocalDateTime actualDate = calibrationDataset.getDate();
    Set<CalibrationDataItem> actualFixingDataItems = calibrationDataset.getFixingDataItems();

    // Assert
    assertTrue(actualCalibrationDataItems.isEmpty());
    assertTrue(actualFixingDataItems.isEmpty());
    assertSame(calibrationDataset.scenarioDate, actualDate);
  }

  /**
   * Test {@link CalibrationDataset#getClonedFixingsAdded(Set)}.
   *
   * <p>Method under test: {@link CalibrationDataset#getClonedFixingsAdded(Set)}
   */
  @Test
  @DisplayName("Test getClonedFixingsAdded(Set)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"CalibrationDataset CalibrationDataset.getClonedFixingsAdded(Set)"})
  void testGetClonedFixingsAdded() {
    // Arrange
    HashSet<CalibrationDataItem> newFixingDataItems = new HashSet<>();
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
    newFixingDataItems.add(
        new CalibrationDataItem(spec, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));

    // Act
    CalibrationDataset actualClonedFixingsAdded =
        calibrationDataset.getClonedFixingsAdded(newFixingDataItems);

    // Assert
    assertTrue(actualClonedFixingsAdded.getCalibrationDataItems().isEmpty());
    assertTrue(actualClonedFixingsAdded.getDataPoints().isEmpty());
    assertTrue(actualClonedFixingsAdded.getFixingDataItems().isEmpty());
  }

  /**
   * Test {@link CalibrationDataset#getClonedFixingsAdded(Set)}.
   *
   * <p>Method under test: {@link CalibrationDataset#getClonedFixingsAdded(Set)}
   */
  @Test
  @DisplayName("Test getClonedFixingsAdded(Set)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"CalibrationDataset CalibrationDataset.getClonedFixingsAdded(Set)"})
  void testGetClonedFixingsAdded2() {
    // Arrange
    HashSet<CalibrationDataItem> newFixingDataItems = new HashSet<>();
    String key = MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();
    String curveName =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();

    Spec spec =
        new Spec(
            key,
            curveName,
            "Fixing",
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());
    newFixingDataItems.add(
        new CalibrationDataItem(spec, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));

    // Act
    CalibrationDataset actualClonedFixingsAdded =
        calibrationDataset.getClonedFixingsAdded(newFixingDataItems);

    // Assert
    assertTrue(actualClonedFixingsAdded.getCalibrationDataItems().isEmpty());
    assertEquals(newFixingDataItems, actualClonedFixingsAdded.getDataPoints());
    assertEquals(newFixingDataItems, actualClonedFixingsAdded.getFixingDataItems());
  }

  /**
   * Test {@link CalibrationDataset#getClonedFixingsAdded(Set)}.
   *
   * <p>Method under test: {@link CalibrationDataset#getClonedFixingsAdded(Set)}
   */
  @Test
  @DisplayName("Test getClonedFixingsAdded(Set)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"CalibrationDataset CalibrationDataset.getClonedFixingsAdded(Set)"})
  void testGetClonedFixingsAdded3() {
    // Arrange
    HashSet<CalibrationDataItem> newFixingDataItems = new HashSet<>();
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
    newFixingDataItems.add(
        new CalibrationDataItem(spec, 0.5d, LocalDate.of(1970, 1, 1).atStartOfDay()));
    String key2 = MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();
    String curveName2 =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();
    String productName2 =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();

    Spec spec2 =
        new Spec(
            key2,
            curveName2,
            productName2,
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());
    newFixingDataItems.add(
        new CalibrationDataItem(spec2, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));

    // Act
    CalibrationDataset actualClonedFixingsAdded =
        calibrationDataset.getClonedFixingsAdded(newFixingDataItems);

    // Assert
    assertTrue(actualClonedFixingsAdded.getCalibrationDataItems().isEmpty());
    assertTrue(actualClonedFixingsAdded.getDataPoints().isEmpty());
    assertTrue(actualClonedFixingsAdded.getFixingDataItems().isEmpty());
  }

  /**
   * Test {@link CalibrationDataset#getClonedFixingsAdded(Set)}.
   *
   * <p>Method under test: {@link CalibrationDataset#getClonedFixingsAdded(Set)}
   */
  @Test
  @DisplayName("Test getClonedFixingsAdded(Set)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"CalibrationDataset CalibrationDataset.getClonedFixingsAdded(Set)"})
  void testGetClonedFixingsAdded4() {
    // Arrange
    CalibrationDataItem calibrationDataItem = mock(CalibrationDataItem.class);
    when(calibrationDataItem.getProductName())
        .thenReturn(
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());

    HashSet<CalibrationDataItem> newFixingDataItems = new HashSet<>();
    newFixingDataItems.add(calibrationDataItem);

    // Act
    CalibrationDataset actualClonedFixingsAdded =
        calibrationDataset.getClonedFixingsAdded(newFixingDataItems);

    // Assert
    verify(calibrationDataItem).getProductName();
    assertTrue(actualClonedFixingsAdded.getCalibrationDataItems().isEmpty());
    assertTrue(actualClonedFixingsAdded.getDataPoints().isEmpty());
    assertTrue(actualClonedFixingsAdded.getFixingDataItems().isEmpty());
  }

  /**
   * Test {@link CalibrationDataset#getClonedFixingsAdded(Set)}.
   *
   * <ul>
   *   <li>Given {@link CalibrationDataItem} {@link CalibrationDataItem#getProductName()} return
   *       {@code Fixing}.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationDataset#getClonedFixingsAdded(Set)}
   */
  @Test
  @DisplayName(
      "Test getClonedFixingsAdded(Set); given CalibrationDataItem getProductName() return 'Fixing'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"CalibrationDataset CalibrationDataset.getClonedFixingsAdded(Set)"})
  void testGetClonedFixingsAdded_givenCalibrationDataItemGetProductNameReturnFixing() {
    // Arrange
    CalibrationDataItem calibrationDataItem = mock(CalibrationDataItem.class);
    when(calibrationDataItem.getProductName()).thenReturn("Fixing");

    HashSet<CalibrationDataItem> newFixingDataItems = new HashSet<>();
    newFixingDataItems.add(calibrationDataItem);

    // Act
    CalibrationDataset actualClonedFixingsAdded =
        calibrationDataset.getClonedFixingsAdded(newFixingDataItems);

    // Assert
    verify(calibrationDataItem, atLeast(1)).getProductName();
    assertTrue(actualClonedFixingsAdded.getCalibrationDataItems().isEmpty());
    assertEquals(newFixingDataItems, actualClonedFixingsAdded.getDataPoints());
    assertEquals(newFixingDataItems, actualClonedFixingsAdded.getFixingDataItems());
  }

  /**
   * Test {@link CalibrationDataset#getClonedFixingsAdded(Set)}.
   *
   * <ul>
   *   <li>Then return Date toLocalTime toString is {@code 00:00}.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationDataset#getClonedFixingsAdded(Set)}
   */
  @Test
  @DisplayName("Test getClonedFixingsAdded(Set); then return Date toLocalTime toString is '00:00'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"CalibrationDataset CalibrationDataset.getClonedFixingsAdded(Set)"})
  void testGetClonedFixingsAdded_thenReturnDateToLocalTimeToStringIs0000() {
    // Arrange
    LocalDate ofResult = LocalDate.of(1970, 1, 1);
    CalibrationDataset calibrationDataset =
        new CalibrationDataset(new HashSet<>(), ofResult.atStartOfDay());

    // Act
    CalibrationDataset actualClonedFixingsAdded =
        calibrationDataset.getClonedFixingsAdded(new HashSet<>());

    // Assert
    LocalDateTime date = actualClonedFixingsAdded.getDate();
    assertEquals("00:00", date.toLocalTime().toString());
    LocalDate toLocalDateResult = date.toLocalDate();
    assertEquals("1970-01-01", toLocalDateResult.toString());
    assertTrue(actualClonedFixingsAdded.getCalibrationDataItems().isEmpty());
    assertTrue(actualClonedFixingsAdded.getDataPoints().isEmpty());
    assertTrue(actualClonedFixingsAdded.getFixingDataItems().isEmpty());
    assertSame(ofResult, toLocalDateResult);
  }

  /**
   * Test {@link CalibrationDataset#toMarketDataList()}.
   *
   * <p>Method under test: {@link CalibrationDataset#toMarketDataList()}
   */
  @Test
  @DisplayName("Test toMarketDataList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"MarketDataList CalibrationDataset.toMarketDataList()"})
  void testToMarketDataList() {
    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
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

    LocalDate ofResult = LocalDate.of(1970, 1, 1);
    curveDataPointSet.add(new CalibrationDataItem(spec, 10.0d, ofResult.atStartOfDay()));
    CalibrationDataset calibrationDataset =
        new CalibrationDataset(curveDataPointSet, LocalDate.of(1970, 1, 1).atStartOfDay());

    // Act
    MarketDataList actualToMarketDataListResult = calibrationDataset.toMarketDataList();

    // Assert
    List<MarketDataPoint> points = actualToMarketDataListResult.getPoints();
    assertEquals(1, points.size());
    MarketDataPoint getResult = points.get(0);
    LocalDate toLocalDateResult = getResult.getTimeStamp().toLocalDate();
    assertEquals("1970-01-01", toLocalDateResult.toString());
    assertEquals(
        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><smartderivativecontract xmlns:xsi=\"http://www"
            + ".w3.org/2001/XMLSchema-instance\" xmlns=\"uri:sdc\" xsi:schemaLocation=\"uri:sdc smartderivativecontract"
            + ".xsd\"><dltTradeId>TEST-TRADE-001</dltTradeId><dltAddress>0xTEST</dltAddress><uniqueTradeIdentifier>UTI"
            + "-TEST-001</uniqueTradeIdentifier><settlementCurrency>EUR</settlementCurrency><tradeType>SDCPledgedBalance"
            + "</tradeType><valuation>  <artefact>    <groupId>net.finmath</groupId>    <artifactId>finmath-smart"
            + "-derivative-contract</artifactId>    <version>1.0.0</version>  </artefact></valuation><parties>  <party>"
            + "    <name>Party 1</name>    <id>party1</id>    <marginAccount><type>constant</type><value>10000.0<"
            + "/value></marginAccount>    <penaltyFee><type>constant</type><value>100.0</value></penaltyFee>   "
            + " <address>0x1234</address>  </party>  <party>    <name>Party 2</name>    <id>party2</id>   "
            + " <marginAccount><type>constant</type><value>10000.0</value></marginAccount>    <penaltyFee><type"
            + ">constant</type><value>100.0</value></penaltyFee>    <address>0x5678</address>  </party></parties>"
            + "<settlement>  <settlementTime><type>daily</type><value>17:00</value></settlementTime>  <marketdata> "
            + "   <provider>test</provider>    <marketdataitems>      <item>        <symbol>EUR-EONIA</symbol>     "
            + "   <curve>ESTR</curve>        <type>Fixing</type>        <tenor>1D</tenor>      </item>    </marketdataitems>"
            + "  </marketdata></settlement><trade>  <product>InterestRateDerivative</product>  <productType>Swap<"
            + "/productType>  <startDate>2024-01-01</startDate>  <endDate>2029-01-01</endDate>  <receiverPartyReference"
            + "><partyReference>party1</partyReference></receiverPartyReference>  <notional><currency>EUR</currency"
            + "><amount>1000000.0</amount></notional>  <swap>    <swapStream id=\"floatLeg\">      <receiverPartyReference"
            + ">party1</receiverPartyReference>      <calculationPeriodDates>        <effectiveDate>2024-01-01<"
            + "/effectiveDate>        <terminationDate>2029-01-01</terminationDate>        <calculationPeriodFrequency"
            + "><periodMultiplier>6</periodMultiplier><period>M</period></calculationPeriodFrequency>     "
            + " </calculationPeriodDates>      <paymentDates>        <paymentFrequency><periodMultiplier>6<"
            + "/periodMultiplier><period>M</period></paymentFrequency>      </paymentDates>      <calculationPeriodAmount>"
            + "        <calculation><notional><currency>EUR</currency><amount>1000000.0</amount></notional>       "
            + " <floatingRateCalculation><floatingRateIndex>EUR-EURIBOR-Reuters</floatingRateIndex><indexTenor>"
            + "<periodMultiplier>6</periodMultiplier><period>M</period></indexTenor></floatingRateCalculation>     "
            + "   </calculation>      </calculationPeriodAmount>    </swapStream>    <swapStream id=\"fixedLeg\">    "
            + "  <receiverPartyReference>party2</receiverPartyReference>      <calculationPeriodDates>       "
            + " <effectiveDate>2024-01-01</effectiveDate>        <terminationDate>2029-01-01</terminationDate>     "
            + "   <calculationPeriodFrequency><periodMultiplier>1</periodMultiplier><period>Y</period></calculation"
            + "PeriodFrequency>      </calculationPeriodDates>      <paymentDates>        <paymentFrequency>"
            + "<periodMultiplier>1</periodMultiplier><period>Y</period></paymentFrequency>      </paymentDates>    "
            + "  <calculationPeriodAmount>        <calculation><notional><currency>EUR</currency><amount>1000000.0<"
            + "/amount></notional>        <fixedRateSchedule><initialValue>0.02</initialValue></fixedRateSchedule> "
            + "       </calculation>      </calculationPeriodAmount>    </swapStream>  </swap></trade></smartderiva"
            + "tivecontract>",
        getResult.getId());
    assertEquals(1, actualToMarketDataListResult.getSize());
    assertEquals(10.0d, getResult.getValue().doubleValue());
    assertSame(ofResult, toLocalDateResult);
  }

  /**
   * Test {@link CalibrationDataset#toMarketDataList()}.
   *
   * <ul>
   *   <li>Then calls {@link CalibrationDataItem#getDateTime()}.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationDataset#toMarketDataList()}
   */
  @Test
  @DisplayName("Test toMarketDataList(); then calls getDateTime()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"MarketDataList CalibrationDataset.toMarketDataList()"})
  void testToMarketDataList_thenCallsGetDateTime() {
    // Arrange
    CalibrationDataItem calibrationDataItem = mock(CalibrationDataItem.class);
    when(calibrationDataItem.getQuote()).thenReturn(10.0d);

    LocalDate ofResult = LocalDate.of(1970, 1, 1);
    when(calibrationDataItem.getDateTime()).thenReturn(ofResult.atStartOfDay());
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
    when(calibrationDataItem.getSpec()).thenReturn(spec);
    when(calibrationDataItem.getProductName())
        .thenReturn(
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());

    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    curveDataPointSet.add(calibrationDataItem);
    CalibrationDataset calibrationDataset =
        new CalibrationDataset(curveDataPointSet, LocalDate.of(1970, 1, 1).atStartOfDay());

    // Act
    MarketDataList actualToMarketDataListResult = calibrationDataset.toMarketDataList();

    // Assert
    verify(calibrationDataItem).getDateTime();
    verify(calibrationDataItem, atLeast(1)).getProductName();
    verify(calibrationDataItem).getQuote();
    verify(calibrationDataItem).getSpec();
    List<MarketDataPoint> points = actualToMarketDataListResult.getPoints();
    assertEquals(1, points.size());
    MarketDataPoint getResult = points.get(0);
    LocalDate toLocalDateResult = getResult.getTimeStamp().toLocalDate();
    assertEquals("1970-01-01", toLocalDateResult.toString());
    assertEquals(
        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><smartderivativecontract xmlns:xsi=\"http://www"
            + ".w3.org/2001/XMLSchema-instance\" xmlns=\"uri:sdc\" xsi:schemaLocation=\"uri:sdc smartderivativecontract"
            + ".xsd\"><dltTradeId>TEST-TRADE-001</dltTradeId><dltAddress>0xTEST</dltAddress><uniqueTradeIdentifier>UTI"
            + "-TEST-001</uniqueTradeIdentifier><settlementCurrency>EUR</settlementCurrency><tradeType>SDCPledgedBalance"
            + "</tradeType><valuation>  <artefact>    <groupId>net.finmath</groupId>    <artifactId>finmath-smart"
            + "-derivative-contract</artifactId>    <version>1.0.0</version>  </artefact></valuation><parties>  <party>"
            + "    <name>Party 1</name>    <id>party1</id>    <marginAccount><type>constant</type><value>10000.0<"
            + "/value></marginAccount>    <penaltyFee><type>constant</type><value>100.0</value></penaltyFee>   "
            + " <address>0x1234</address>  </party>  <party>    <name>Party 2</name>    <id>party2</id>   "
            + " <marginAccount><type>constant</type><value>10000.0</value></marginAccount>    <penaltyFee><type"
            + ">constant</type><value>100.0</value></penaltyFee>    <address>0x5678</address>  </party></parties>"
            + "<settlement>  <settlementTime><type>daily</type><value>17:00</value></settlementTime>  <marketdata> "
            + "   <provider>test</provider>    <marketdataitems>      <item>        <symbol>EUR-EONIA</symbol>     "
            + "   <curve>ESTR</curve>        <type>Fixing</type>        <tenor>1D</tenor>      </item>    </marketdataitems>"
            + "  </marketdata></settlement><trade>  <product>InterestRateDerivative</product>  <productType>Swap<"
            + "/productType>  <startDate>2024-01-01</startDate>  <endDate>2029-01-01</endDate>  <receiverPartyReference"
            + "><partyReference>party1</partyReference></receiverPartyReference>  <notional><currency>EUR</currency"
            + "><amount>1000000.0</amount></notional>  <swap>    <swapStream id=\"floatLeg\">      <receiverPartyReference"
            + ">party1</receiverPartyReference>      <calculationPeriodDates>        <effectiveDate>2024-01-01<"
            + "/effectiveDate>        <terminationDate>2029-01-01</terminationDate>        <calculationPeriodFrequency"
            + "><periodMultiplier>6</periodMultiplier><period>M</period></calculationPeriodFrequency>     "
            + " </calculationPeriodDates>      <paymentDates>        <paymentFrequency><periodMultiplier>6<"
            + "/periodMultiplier><period>M</period></paymentFrequency>      </paymentDates>      <calculationPeriodAmount>"
            + "        <calculation><notional><currency>EUR</currency><amount>1000000.0</amount></notional>       "
            + " <floatingRateCalculation><floatingRateIndex>EUR-EURIBOR-Reuters</floatingRateIndex><indexTenor>"
            + "<periodMultiplier>6</periodMultiplier><period>M</period></indexTenor></floatingRateCalculation>     "
            + "   </calculation>      </calculationPeriodAmount>    </swapStream>    <swapStream id=\"fixedLeg\">    "
            + "  <receiverPartyReference>party2</receiverPartyReference>      <calculationPeriodDates>       "
            + " <effectiveDate>2024-01-01</effectiveDate>        <terminationDate>2029-01-01</terminationDate>     "
            + "   <calculationPeriodFrequency><periodMultiplier>1</periodMultiplier><period>Y</period></calculation"
            + "PeriodFrequency>      </calculationPeriodDates>      <paymentDates>        <paymentFrequency>"
            + "<periodMultiplier>1</periodMultiplier><period>Y</period></paymentFrequency>      </paymentDates>    "
            + "  <calculationPeriodAmount>        <calculation><notional><currency>EUR</currency><amount>1000000.0<"
            + "/amount></notional>        <fixedRateSchedule><initialValue>0.02</initialValue></fixedRateSchedule> "
            + "       </calculation>      </calculationPeriodAmount>    </swapStream>  </swap></trade></smartderiva"
            + "tivecontract>",
        getResult.getId());
    assertEquals(1, actualToMarketDataListResult.getSize());
    assertEquals(10.0d, getResult.getValue().doubleValue());
    assertSame(ofResult, toLocalDateResult);
  }

  /**
   * Test {@link CalibrationDataset#toMarketDataList()}.
   *
   * <ul>
   *   <li>Then return Size is zero.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationDataset#toMarketDataList()}
   */
  @Test
  @DisplayName("Test toMarketDataList(); then return Size is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"MarketDataList CalibrationDataset.toMarketDataList()"})
  void testToMarketDataList_thenReturnSizeIsZero() {
    // Arrange
    CalibrationDataset calibrationDataset =
        new CalibrationDataset(new HashSet<>(), LocalDate.of(1970, 1, 1).atStartOfDay());

    // Act
    MarketDataList actualToMarketDataListResult = calibrationDataset.toMarketDataList();

    // Assert
    assertEquals(0, actualToMarketDataListResult.getSize());
    assertTrue(actualToMarketDataListResult.getPoints().isEmpty());
  }

  /**
   * Test {@link CalibrationDataset#serializeToJson()}.
   *
   * <ul>
   *   <li>Given {@link CalibrationDataItem} {@link CalibrationDataItem#getQuote()} return ten.
   *   <li>Then calls {@link CalibrationDataItem#getQuote()}.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationDataset#serializeToJson()}
   */
  @Test
  @DisplayName(
      "Test serializeToJson(); given CalibrationDataItem getQuote() return ten; then calls getQuote()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String CalibrationDataset.serializeToJson()"})
  void testSerializeToJson_givenCalibrationDataItemGetQuoteReturnTen_thenCallsGetQuote() {
    // Arrange
    CalibrationDataItem calibrationDataItem = mock(CalibrationDataItem.class);
    when(calibrationDataItem.getQuote()).thenReturn(10.0d);
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
    when(calibrationDataItem.getSpec()).thenReturn(spec);
    when(calibrationDataItem.getProductName())
        .thenReturn(
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());

    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    curveDataPointSet.add(calibrationDataItem);
    CalibrationDataset calibrationDataset =
        new CalibrationDataset(curveDataPointSet, LocalDate.of(1970, 1, 1).atStartOfDay());

    // Act
    calibrationDataset.serializeToJson();

    // Assert
    verify(calibrationDataItem, atLeast(1)).getProductName();
    verify(calibrationDataItem).getQuote();
    verify(calibrationDataItem, atLeast(1)).getSpec();
  }

  /**
   * Test {@link CalibrationDataset#serializeToJson()}.
   *
   * <ul>
   *   <li>Given {@link CalibrationDataItem} {@link CalibrationDataItem#getQuote()} throw {@link
   *       RuntimeException#RuntimeException()}.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationDataset#serializeToJson()}
   */
  @Test
  @DisplayName(
      "Test serializeToJson(); given CalibrationDataItem getQuote() throw RuntimeException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String CalibrationDataset.serializeToJson()"})
  void testSerializeToJson_givenCalibrationDataItemGetQuoteThrowRuntimeException() {
    // Arrange
    CalibrationDataItem calibrationDataItem = mock(CalibrationDataItem.class);
    when(calibrationDataItem.getQuote()).thenThrow(new RuntimeException());
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
    when(calibrationDataItem.getSpec()).thenReturn(spec);
    when(calibrationDataItem.getProductName())
        .thenReturn(
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());

    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    curveDataPointSet.add(calibrationDataItem);
    CalibrationDataset calibrationDataset =
        new CalibrationDataset(curveDataPointSet, LocalDate.of(1970, 1, 1).atStartOfDay());

    // Act and Assert
    assertThrows(RuntimeException.class, () -> calibrationDataset.serializeToJson());
    verify(calibrationDataItem, atLeast(1)).getProductName();
    verify(calibrationDataItem).getQuote();
    verify(calibrationDataItem, atLeast(1)).getSpec();
  }

  /**
   * Test {@link CalibrationDataset#serializeToJson()}.
   *
   * <ul>
   *   <li>Given {@link CalibrationDataItem} {@link CalibrationDataItem#getSpec()} throw {@link
   *       RuntimeException#RuntimeException()}.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationDataset#serializeToJson()}
   */
  @Test
  @DisplayName(
      "Test serializeToJson(); given CalibrationDataItem getSpec() throw RuntimeException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String CalibrationDataset.serializeToJson()"})
  void testSerializeToJson_givenCalibrationDataItemGetSpecThrowRuntimeException() {
    // Arrange
    CalibrationDataItem calibrationDataItem = mock(CalibrationDataItem.class);
    when(calibrationDataItem.getSpec()).thenThrow(new RuntimeException());
    when(calibrationDataItem.getProductName())
        .thenReturn(
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());

    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    curveDataPointSet.add(calibrationDataItem);
    CalibrationDataset calibrationDataset =
        new CalibrationDataset(curveDataPointSet, LocalDate.of(1970, 1, 1).atStartOfDay());

    // Act and Assert
    assertThrows(RuntimeException.class, () -> calibrationDataset.serializeToJson());
    verify(calibrationDataItem, atLeast(1)).getProductName();
    verify(calibrationDataItem).getSpec();
  }

  /**
   * Test {@link CalibrationDataset#getDataAsCalibrationDataPointStream(CalibrationParser)}.
   *
   * <p>Method under test: {@link
   * CalibrationDataset#getDataAsCalibrationDataPointStream(CalibrationParser)}
   */
  @Test
  @DisplayName("Test getDataAsCalibrationDataPointStream(CalibrationParser)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Stream CalibrationDataset.getDataAsCalibrationDataPointStream(CalibrationParser)"
  })
  void testGetDataAsCalibrationDataPointStream() {
    // Arrange
    CalibrationDataset calibrationDataset =
        new CalibrationDataset(new HashSet<>(), LocalDate.of(1970, 1, 1).atStartOfDay());

    CalibrationParser parser = mock(CalibrationParser.class);

    ArrayList<CalibrationSpecProvider> calibrationSpecProviderList = new ArrayList<>();
    Stream<CalibrationSpecProvider> streamResult = calibrationSpecProviderList.stream();
    when(parser.parse(Mockito.<Stream<CalibrationDataItem>>any())).thenReturn(streamResult);

    // Act
    Stream<CalibrationSpecProvider> actualDataAsCalibrationDataPointStream =
        calibrationDataset.getDataAsCalibrationDataPointStream(parser);

    // Assert
    verify(parser).parse(isA(Stream.class));
    assertTrue(
        actualDataAsCalibrationDataPointStream.limit(5).collect(Collectors.toList()).isEmpty());
  }

  /**
   * Test {@link CalibrationDataset#getDataPoints()}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationDataset#getDataPoints()}
   */
  @Test
  @DisplayName("Test getDataPoints(); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Set CalibrationDataset.getDataPoints()"})
  void testGetDataPoints_thenReturnEmpty() {
    // Arrange
    CalibrationDataset calibrationDataset =
        new CalibrationDataset(new HashSet<>(), LocalDate.of(1970, 1, 1).atStartOfDay());

    // Act and Assert
    assertTrue(calibrationDataset.getDataPoints().isEmpty());
  }

  /**
   * Test {@link CalibrationDataset#getDataPoints()}.
   *
   * <ul>
   *   <li>Then return size is one.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationDataset#getDataPoints()}
   */
  @Test
  @DisplayName("Test getDataPoints(); then return size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Set CalibrationDataset.getDataPoints()"})
  void testGetDataPoints_thenReturnSizeIsOne() {
    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
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
    curveDataPointSet.add(
        new CalibrationDataItem(spec, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));
    CalibrationDataset calibrationDataset =
        new CalibrationDataset(curveDataPointSet, LocalDate.of(1970, 1, 1).atStartOfDay());

    // Act and Assert
    assertEquals(1, calibrationDataset.getDataPoints().size());
  }
}
