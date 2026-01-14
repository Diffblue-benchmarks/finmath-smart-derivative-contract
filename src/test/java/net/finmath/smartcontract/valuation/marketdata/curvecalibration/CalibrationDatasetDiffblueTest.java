package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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
   *   <li>Given createCalibrationDataItemMonthlyMaturity.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationDataset#CalibrationDataset(Set, LocalDateTime)}
   */
  @Test
  @DisplayName(
      "Test new CalibrationDataset(Set, LocalDateTime); given createCalibrationDataItemMonthlyMaturity")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CalibrationDataset.<init>(Set, LocalDateTime)"})
  void testNewCalibrationDataset_givenCreateCalibrationDataItemMonthlyMaturity() {
    // Arrange
    CalibrationDataItem calibrationDataItem = mock(CalibrationDataItem.class);
    when(calibrationDataItem.getDaysToMaturity()).thenReturn(1);
    when(calibrationDataItem.getProductName())
        .thenReturn(
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());

    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    curveDataPointSet.add(
        CalibrationDataItemTestFactory.createCalibrationDataItemMonthlyMaturity());
    curveDataPointSet.add(calibrationDataItem);
    LocalDateTime scenarioDate = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act
    CalibrationDataset actualCalibrationDataset =
        new CalibrationDataset(curveDataPointSet, scenarioDate);

    // Assert
    verify(calibrationDataItem).getDaysToMaturity();
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
   *   <li>Given createCalibrationDataItemWithNullQuote.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationDataset#CalibrationDataset(Set, LocalDateTime)}
   */
  @Test
  @DisplayName(
      "Test new CalibrationDataset(Set, LocalDateTime); given createCalibrationDataItemWithNullQuote")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CalibrationDataset.<init>(Set, LocalDateTime)"})
  void testNewCalibrationDataset_givenCreateCalibrationDataItemWithNullQuote() {
    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    curveDataPointSet.add(CalibrationDataItemTestFactory.createCalibrationDataItemWithNullQuote());
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
   *   <li>Then calls {@link CalibrationDataItem#getDaysToMaturity()}.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationDataset#CalibrationDataset(Set, LocalDateTime)}
   */
  @Test
  @DisplayName("Test new CalibrationDataset(Set, LocalDateTime); then calls getDaysToMaturity()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CalibrationDataset.<init>(Set, LocalDateTime)"})
  void testNewCalibrationDataset_thenCallsGetDaysToMaturity() {
    // Arrange
    CalibrationDataItem calibrationDataItem = mock(CalibrationDataItem.class);
    when(calibrationDataItem.getDaysToMaturity()).thenReturn(1);
    when(calibrationDataItem.getProductName())
        .thenReturn(
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());

    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    curveDataPointSet.add(CalibrationDataItemTestFactory.createCalibrationDataItemWithNullQuote());
    curveDataPointSet.add(calibrationDataItem);
    LocalDateTime scenarioDate = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act
    CalibrationDataset actualCalibrationDataset =
        new CalibrationDataset(curveDataPointSet, scenarioDate);

    // Assert
    verify(calibrationDataItem).getDaysToMaturity();
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
   *   <li>Then calls {@link CalibrationDataItem#getProductName()}.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationDataset#CalibrationDataset(Set, LocalDateTime)}
   */
  @Test
  @DisplayName("Test new CalibrationDataset(Set, LocalDateTime); then calls getProductName()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CalibrationDataset.<init>(Set, LocalDateTime)"})
  void testNewCalibrationDataset_thenCallsGetProductName() {
    // Arrange
    CalibrationDataItem calibrationDataItem = mock(CalibrationDataItem.class);
    when(calibrationDataItem.getProductName())
        .thenReturn(
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());

    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
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
   *   <li>Then return FixingDataItems is {@link HashSet#HashSet()}.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationDataset#CalibrationDataset(Set, LocalDateTime)}
   */
  @Test
  @DisplayName(
      "Test new CalibrationDataset(Set, LocalDateTime); then return FixingDataItems is HashSet()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CalibrationDataset.<init>(Set, LocalDateTime)"})
  void testNewCalibrationDataset_thenReturnFixingDataItemsIsHashSet() {
    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    curveDataPointSet.add(CalibrationDataItemTestFactory.createCalibrationDataItemDailyMaturity());
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
    CalibrationDataItem calibrationDataItem = mock(CalibrationDataItem.class);
    when(calibrationDataItem.getClonedScaled(anyDouble()))
        .thenReturn(CalibrationDataItemTestFactory.createCalibrationDataItemWithNullQuote());
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
   *   <li>Given {@link HashSet#HashSet()} add createCalibrationDataItemEuribor6M.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationDataset#getScaled(double)}
   */
  @Test
  @DisplayName("Test getScaled(double); given HashSet() add createCalibrationDataItemEuribor6M")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"CalibrationDataset CalibrationDataset.getScaled(double)"})
  void testGetScaled_givenHashSetAddCreateCalibrationDataItemEuribor6M() {
    // Arrange
    CalibrationDataItem calibrationDataItem = mock(CalibrationDataItem.class);
    when(calibrationDataItem.getDaysToMaturity()).thenReturn(1);
    when(calibrationDataItem.getProductName())
        .thenReturn(
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());

    CalibrationDataItem calibrationDataItem2 = mock(CalibrationDataItem.class);
    when(calibrationDataItem2.getDaysToMaturity()).thenReturn(1);
    when(calibrationDataItem2.getClonedScaled(anyDouble())).thenReturn(calibrationDataItem);
    when(calibrationDataItem2.getProductName())
        .thenReturn(
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());

    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    curveDataPointSet.add(CalibrationDataItemTestFactory.createCalibrationDataItemEuribor6M());
    curveDataPointSet.add(calibrationDataItem2);

    LocalDate ofResult = LocalDate.of(1970, 1, 1);
    CalibrationDataset calibrationDataset =
        new CalibrationDataset(curveDataPointSet, ofResult.atStartOfDay());

    // Act
    CalibrationDataset actualScaled = calibrationDataset.getScaled(10.0d);

    // Assert
    verify(calibrationDataItem2).getClonedScaled(10.0d);
    verify(calibrationDataItem2).getDaysToMaturity();
    verify(calibrationDataItem).getDaysToMaturity();
    verify(calibrationDataItem2, atLeast(1)).getProductName();
    verify(calibrationDataItem, atLeast(1)).getProductName();
    LocalDateTime date = actualScaled.getDate();
    assertEquals("00:00", date.toLocalTime().toString());
    LocalDate toLocalDateResult = date.toLocalDate();
    assertEquals("1970-01-01", toLocalDateResult.toString());
    Set<CalibrationDataItem> calibrationDataItems = actualScaled.getCalibrationDataItems();
    assertEquals(2, calibrationDataItems.size());
    assertTrue(actualScaled.getFixingDataItems().isEmpty());
    assertEquals(calibrationDataItems, actualScaled.getDataPoints());
    assertSame(ofResult, toLocalDateResult);
  }

  /**
   * Test {@link CalibrationDataset#getScaled(double)}.
   *
   * <ul>
   *   <li>Given {@link HashSet#HashSet()} add createCalibrationDataItemMonthlyMaturity.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationDataset#getScaled(double)}
   */
  @Test
  @DisplayName(
      "Test getScaled(double); given HashSet() add createCalibrationDataItemMonthlyMaturity")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"CalibrationDataset CalibrationDataset.getScaled(double)"})
  void testGetScaled_givenHashSetAddCreateCalibrationDataItemMonthlyMaturity() {
    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    curveDataPointSet.add(
        CalibrationDataItemTestFactory.createCalibrationDataItemMonthlyMaturity());

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
   * <ul>
   *   <li>Then return CalibrationDataItems size is one.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationDataset#getScaled(double)}
   */
  @Test
  @DisplayName("Test getScaled(double); then return CalibrationDataItems size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"CalibrationDataset CalibrationDataset.getScaled(double)"})
  void testGetScaled_thenReturnCalibrationDataItemsSizeIsOne() {
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
   *   <li>Then return CalibrationDataItems size is two.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationDataset#getScaled(double)}
   */
  @Test
  @DisplayName("Test getScaled(double); then return CalibrationDataItems size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"CalibrationDataset CalibrationDataset.getScaled(double)"})
  void testGetScaled_thenReturnCalibrationDataItemsSizeIsTwo() {
    // Arrange
    CalibrationDataItem calibrationDataItem = mock(CalibrationDataItem.class);
    when(calibrationDataItem.getDaysToMaturity()).thenReturn(1);
    when(calibrationDataItem.getProductName())
        .thenReturn(
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());

    CalibrationDataItem calibrationDataItem2 = mock(CalibrationDataItem.class);
    when(calibrationDataItem2.getDaysToMaturity()).thenReturn(1);
    when(calibrationDataItem2.getClonedScaled(anyDouble())).thenReturn(calibrationDataItem);
    when(calibrationDataItem2.getProductName())
        .thenReturn(
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());

    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    curveDataPointSet.add(
        CalibrationDataItemTestFactory.createCalibrationDataItemMonthlyMaturity());
    curveDataPointSet.add(calibrationDataItem2);

    LocalDate ofResult = LocalDate.of(1970, 1, 1);
    CalibrationDataset calibrationDataset =
        new CalibrationDataset(curveDataPointSet, ofResult.atStartOfDay());

    // Act
    CalibrationDataset actualScaled = calibrationDataset.getScaled(10.0d);

    // Assert
    verify(calibrationDataItem2).getClonedScaled(10.0d);
    verify(calibrationDataItem2).getDaysToMaturity();
    verify(calibrationDataItem).getDaysToMaturity();
    verify(calibrationDataItem2, atLeast(1)).getProductName();
    verify(calibrationDataItem, atLeast(1)).getProductName();
    LocalDateTime date = actualScaled.getDate();
    assertEquals("00:00", date.toLocalTime().toString());
    LocalDate toLocalDateResult = date.toLocalDate();
    assertEquals("1970-01-01", toLocalDateResult.toString());
    Set<CalibrationDataItem> calibrationDataItems = actualScaled.getCalibrationDataItems();
    assertEquals(2, calibrationDataItems.size());
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
    when(calibrationDataItem.getClonedScaled(anyDouble()))
        .thenReturn(CalibrationDataItemTestFactory.createCalibrationDataItemDailyMaturity());
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
   * <p>Method under test: {@link CalibrationDataset#getClonedFixingsAdded(Set)}
   */
  @Test
  @DisplayName("Test getClonedFixingsAdded(Set)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"CalibrationDataset CalibrationDataset.getClonedFixingsAdded(Set)"})
  void testGetClonedFixingsAdded2() {
    // Arrange
    CalibrationDataItem calibrationDataItem = mock(CalibrationDataItem.class);
    when(calibrationDataItem.getProductName())
        .thenReturn(
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());

    HashSet<CalibrationDataItem> newFixingDataItems = new HashSet<>();
    newFixingDataItems.add(CalibrationDataItemTestFactory.createCalibrationDataItemWithNullQuote());
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
   *   <li>Given createCalibrationDataItemDailyMaturity.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationDataset#getClonedFixingsAdded(Set)}
   */
  @Test
  @DisplayName("Test getClonedFixingsAdded(Set); given createCalibrationDataItemDailyMaturity")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"CalibrationDataset CalibrationDataset.getClonedFixingsAdded(Set)"})
  void testGetClonedFixingsAdded_givenCreateCalibrationDataItemDailyMaturity() {
    // Arrange
    HashSet<CalibrationDataItem> newFixingDataItems = new HashSet<>();
    newFixingDataItems.add(CalibrationDataItemTestFactory.createCalibrationDataItemDailyMaturity());

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
   * <ul>
   *   <li>Given createCalibrationDataItemWithNullQuote.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationDataset#getClonedFixingsAdded(Set)}
   */
  @Test
  @DisplayName("Test getClonedFixingsAdded(Set); given createCalibrationDataItemWithNullQuote")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"CalibrationDataset CalibrationDataset.getClonedFixingsAdded(Set)"})
  void testGetClonedFixingsAdded_givenCreateCalibrationDataItemWithNullQuote() {
    // Arrange
    HashSet<CalibrationDataItem> newFixingDataItems = new HashSet<>();
    newFixingDataItems.add(CalibrationDataItemTestFactory.createCalibrationDataItemWithNullQuote());

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
   * <ul>
   *   <li>Then return Points first TimeStamp toLocalDate toString is {@code 1970-01-01}.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationDataset#toMarketDataList()}
   */
  @Test
  @DisplayName(
      "Test toMarketDataList(); then return Points first TimeStamp toLocalDate toString is '1970-01-01'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"MarketDataList CalibrationDataset.toMarketDataList()"})
  void testToMarketDataList_thenReturnPointsFirstTimeStampToLocalDateToStringIs19700101() {
    // Arrange
    CalibrationDataItem calibrationDataItem = mock(CalibrationDataItem.class);
    when(calibrationDataItem.getQuote()).thenReturn(10.0d);

    LocalDate ofResult = LocalDate.of(1970, 1, 1);
    when(calibrationDataItem.getDateTime()).thenReturn(ofResult.atStartOfDay());
    when(calibrationDataItem.getSpec())
        .thenReturn(CalibrationDataItemTestFactory.createSpecForSwapRate());
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
    assertEquals("ESTR_Swap-Rate_5Y", getResult.getId());
    assertEquals(10.0d, getResult.getValue().doubleValue());
    assertSame(ofResult, toLocalDateResult);
  }

  /**
   * Test {@link CalibrationDataset#toMarketDataList()}.
   *
   * <ul>
   *   <li>Then return Points first TimeStamp toLocalDate toString is {@code 2024-01-01}.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationDataset#toMarketDataList()}
   */
  @Test
  @DisplayName(
      "Test toMarketDataList(); then return Points first TimeStamp toLocalDate toString is '2024-01-01'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"MarketDataList CalibrationDataset.toMarketDataList()"})
  void testToMarketDataList_thenReturnPointsFirstTimeStampToLocalDateToStringIs20240101() {
    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    curveDataPointSet.add(CalibrationDataItemTestFactory.createCalibrationDataItemDailyMaturity());
    CalibrationDataset calibrationDataset =
        new CalibrationDataset(curveDataPointSet, LocalDate.of(1970, 1, 1).atStartOfDay());

    // Act
    MarketDataList actualToMarketDataListResult = calibrationDataset.toMarketDataList();

    // Assert
    List<MarketDataPoint> points = actualToMarketDataListResult.getPoints();
    assertEquals(1, points.size());
    MarketDataPoint getResult = points.get(0);
    assertEquals("2024-01-01", getResult.getTimeStamp().toLocalDate().toString());
    assertEquals("ESTR_Fixing_1D", getResult.getId());
    assertEquals(0.001d, getResult.getValue().doubleValue());
    assertEquals(1, actualToMarketDataListResult.getSize());
  }

  /**
   * Test {@link CalibrationDataset#toMarketDataList()}.
   *
   * <ul>
   *   <li>Then return Points first TimeStamp toLocalTime toString is {@code 17:00}.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationDataset#toMarketDataList()}
   */
  @Test
  @DisplayName(
      "Test toMarketDataList(); then return Points first TimeStamp toLocalTime toString is '17:00'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"MarketDataList CalibrationDataset.toMarketDataList()"})
  void testToMarketDataList_thenReturnPointsFirstTimeStampToLocalTimeToStringIs1700() {
    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    curveDataPointSet.add(CalibrationDataItemTestFactory.createCalibrationDataItemWithNullQuote());
    CalibrationDataset calibrationDataset =
        new CalibrationDataset(curveDataPointSet, LocalDate.of(1970, 1, 1).atStartOfDay());

    // Act and Assert
    List<MarketDataPoint> points = calibrationDataset.toMarketDataList().getPoints();
    assertEquals(1, points.size());
    MarketDataPoint getResult = points.get(0);
    LocalDateTime timeStamp = getResult.getTimeStamp();
    assertEquals("17:00", timeStamp.toLocalTime().toString());
    assertEquals("2024-03-15", timeStamp.toLocalDate().toString());
    assertEquals("ESTR_Swap-Rate_2Y", getResult.getId());
    assertNull(getResult.getValue());
  }

  /**
   * Test {@link CalibrationDataset#toMarketDataList()}.
   *
   * <ul>
   *   <li>Then return Points size is two.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationDataset#toMarketDataList()}
   */
  @Test
  @DisplayName("Test toMarketDataList(); then return Points size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"MarketDataList CalibrationDataset.toMarketDataList()"})
  void testToMarketDataList_thenReturnPointsSizeIsTwo() {
    // Arrange
    CalibrationDataItem calibrationDataItem = mock(CalibrationDataItem.class);
    when(calibrationDataItem.getDaysToMaturity()).thenReturn(1);
    when(calibrationDataItem.getQuote()).thenReturn(10.0d);
    when(calibrationDataItem.getDateTime()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
    when(calibrationDataItem.getSpec())
        .thenReturn(CalibrationDataItemTestFactory.createSpecForSwapRate());
    when(calibrationDataItem.getProductName())
        .thenReturn(
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());

    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    curveDataPointSet.add(CalibrationDataItemTestFactory.createCalibrationDataItemWithNullQuote());
    curveDataPointSet.add(calibrationDataItem);
    CalibrationDataset calibrationDataset =
        new CalibrationDataset(curveDataPointSet, LocalDate.of(1970, 1, 1).atStartOfDay());

    // Act
    MarketDataList actualToMarketDataListResult = calibrationDataset.toMarketDataList();

    // Assert
    verify(calibrationDataItem).getDateTime();
    verify(calibrationDataItem).getDaysToMaturity();
    verify(calibrationDataItem, atLeast(1)).getProductName();
    verify(calibrationDataItem).getQuote();
    verify(calibrationDataItem).getSpec();
    List<MarketDataPoint> points = actualToMarketDataListResult.getPoints();
    assertEquals(2, points.size());
    MarketDataPoint getResult = points.get(1);
    LocalDateTime timeStamp = getResult.getTimeStamp();
    assertEquals("17:00", timeStamp.toLocalTime().toString());
    assertEquals("2024-03-15", timeStamp.toLocalDate().toString());
    assertEquals("ESTR_Swap-Rate_2Y", getResult.getId());
    assertNull(getResult.getValue());
    assertEquals(2, actualToMarketDataListResult.getSize());
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
  @MethodsUnderTest({"java.lang.String CalibrationDataset.serializeToJson()"})
  void testSerializeToJson_givenCalibrationDataItemGetQuoteReturnTen_thenCallsGetQuote() {
    // Arrange
    CalibrationDataItem calibrationDataItem = mock(CalibrationDataItem.class);
    when(calibrationDataItem.getQuote()).thenReturn(10.0d);
    when(calibrationDataItem.getSpec())
        .thenReturn(CalibrationDataItemTestFactory.createSpecForSwapRate());
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
  @MethodsUnderTest({"java.lang.String CalibrationDataset.serializeToJson()"})
  void testSerializeToJson_givenCalibrationDataItemGetQuoteThrowRuntimeException() {
    // Arrange
    CalibrationDataItem calibrationDataItem = mock(CalibrationDataItem.class);
    when(calibrationDataItem.getQuote()).thenThrow(new RuntimeException());
    when(calibrationDataItem.getSpec())
        .thenReturn(CalibrationDataItemTestFactory.createSpecForSwapRate());
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
  @MethodsUnderTest({"java.lang.String CalibrationDataset.serializeToJson()"})
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
   * Test {@link CalibrationDataset#serializeToJson()}.
   *
   * <ul>
   *   <li>Given {@link Spec} {@link Spec#getCurveName()} throw {@link
   *       RuntimeException#RuntimeException()}.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationDataset#serializeToJson()}
   */
  @Test
  @DisplayName("Test serializeToJson(); given Spec getCurveName() throw RuntimeException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.String CalibrationDataset.serializeToJson()"})
  void testSerializeToJson_givenSpecGetCurveNameThrowRuntimeException() {
    // Arrange
    Spec spec = mock(Spec.class);
    when(spec.getCurveName()).thenThrow(new RuntimeException());

    CalibrationDataItem calibrationDataItem = mock(CalibrationDataItem.class);
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
    verify(calibrationDataItem).getSpec();
    verify(spec).getCurveName();
  }

  /**
   * Test {@link CalibrationDataset#serializeToJson()}.
   *
   * <ul>
   *   <li>Then calls {@link Spec#getMaturity()}.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationDataset#serializeToJson()}
   */
  @Test
  @DisplayName("Test serializeToJson(); then calls getMaturity()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.String CalibrationDataset.serializeToJson()"})
  void testSerializeToJson_thenCallsGetMaturity() {
    // Arrange
    Spec spec = mock(Spec.class);
    when(spec.getCurveName())
        .thenReturn(
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());
    when(spec.getMaturity())
        .thenReturn(
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());
    when(spec.getProductName())
        .thenReturn(
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());

    CalibrationDataItem calibrationDataItem = mock(CalibrationDataItem.class);
    when(calibrationDataItem.getQuote()).thenReturn(10.0d);
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
    verify(spec, atLeast(1)).getCurveName();
    verify(spec).getMaturity();
    verify(spec, atLeast(1)).getProductName();
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
   *   <li>Given {@link HashSet#HashSet()} add createCalibrationDataItemDailyMaturity.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationDataset#getDataPoints()}
   */
  @Test
  @DisplayName("Test getDataPoints(); given HashSet() add createCalibrationDataItemDailyMaturity")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Set CalibrationDataset.getDataPoints()"})
  void testGetDataPoints_givenHashSetAddCreateCalibrationDataItemDailyMaturity() {
    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    curveDataPointSet.add(CalibrationDataItemTestFactory.createCalibrationDataItemDailyMaturity());
    CalibrationDataset calibrationDataset =
        new CalibrationDataset(curveDataPointSet, LocalDate.of(1970, 1, 1).atStartOfDay());

    // Act and Assert
    assertEquals(1, calibrationDataset.getDataPoints().size());
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
   *   <li>Then return {@link HashSet#HashSet()}.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationDataset#getDataPoints()}
   */
  @Test
  @DisplayName("Test getDataPoints(); then return HashSet()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Set CalibrationDataset.getDataPoints()"})
  void testGetDataPoints_thenReturnHashSet() {
    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    curveDataPointSet.add(
        CalibrationDataItemTestFactory.createCalibrationDataItemMonthlyMaturity());
    curveDataPointSet.add(CalibrationDataItemTestFactory.createCalibrationDataItemWithNullQuote());
    CalibrationDataset calibrationDataset =
        new CalibrationDataset(curveDataPointSet, LocalDate.of(1970, 1, 1).atStartOfDay());

    // Act and Assert
    assertEquals(curveDataPointSet, calibrationDataset.getDataPoints());
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
    curveDataPointSet.add(CalibrationDataItemTestFactory.createCalibrationDataItemWithNullQuote());
    CalibrationDataset calibrationDataset =
        new CalibrationDataset(curveDataPointSet, LocalDate.of(1970, 1, 1).atStartOfDay());

    // Act and Assert
    assertEquals(1, calibrationDataset.getDataPoints().size());
  }
}
