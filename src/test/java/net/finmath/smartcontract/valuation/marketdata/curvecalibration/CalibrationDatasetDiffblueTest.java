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
    Spec spec = new Spec("Key", "Curve Name", "Product Name", "Maturity");
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
    when(calibrationDataItem.getProductName()).thenReturn("Product Name");

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
   *   <li>Then return CalibrationDataItems size is one.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationDataset#CalibrationDataset(Set, LocalDateTime)}
   */
  @Test
  @DisplayName(
      "Test new CalibrationDataset(Set, LocalDateTime); then return CalibrationDataItems size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CalibrationDataset.<init>(Set, LocalDateTime)"})
  void testNewCalibrationDataset_thenReturnCalibrationDataItemsSizeIsOne() {
    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    Spec spec =
        new Spec(
            "net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationDataItem$Spec",
            "Curve Name",
            "Fixing",
            "Maturity");
    curveDataPointSet.add(
        new CalibrationDataItem(spec, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));
    Spec spec2 = new Spec("Key", "Curve Name", "Product Name", "Maturity");
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
    Spec spec = new Spec("Key", "Curve Name", "Fixing", "Maturity");
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
    Spec spec = new Spec("Key", "Curve Name", "Product Name", "Maturity");
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
    when(calibrationDataItem.getProductName()).thenReturn("Product Name");

    CalibrationDataItem calibrationDataItem2 = mock(CalibrationDataItem.class);
    when(calibrationDataItem2.getClonedScaled(anyDouble())).thenReturn(calibrationDataItem);
    when(calibrationDataItem2.getProductName()).thenReturn("Product Name");

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
   *   <li>Given {@link Spec#Spec(String, String, String, String)} with {@code Key} and {@code Curve
   *       Name} and {@code Product Name} and {@code Maturity}.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationDataset#getScaled(double)}
   */
  @Test
  @DisplayName(
      "Test getScaled(double); given Spec(String, String, String, String) with 'Key' and 'Curve Name' and 'Product Name' and 'Maturity'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"CalibrationDataset CalibrationDataset.getScaled(double)"})
  void testGetScaled_givenSpecWithKeyAndCurveNameAndProductNameAndMaturity() {
    // Arrange
    CalibrationDataItem calibrationDataItem = mock(CalibrationDataItem.class);
    Spec spec = new Spec("Key", "Curve Name", "Product Name", "Maturity");
    when(calibrationDataItem.getClonedScaled(anyDouble()))
        .thenReturn(new CalibrationDataItem(spec, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));
    when(calibrationDataItem.getProductName()).thenReturn("Product Name");

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
    Spec spec = new Spec("Key", "Curve Name", "Fixing", "Maturity");
    when(calibrationDataItem.getClonedScaled(anyDouble()))
        .thenReturn(new CalibrationDataItem(spec, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));
    when(calibrationDataItem.getProductName()).thenReturn("Product Name");

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
    Spec spec = new Spec("Key", "Curve Name", "Fixing", "Maturity");
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
  void testGetClonedFixingsAdded2() {
    // Arrange
    HashSet<CalibrationDataItem> newFixingDataItems = new HashSet<>();
    Spec spec =
        new Spec(
            "Key",
            "Curve Name",
            "net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationDataItem$Spec",
            "Maturity");
    newFixingDataItems.add(
        new CalibrationDataItem(spec, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));
    Spec spec2 = new Spec("Key", "Curve Name", "Product Name", "Maturity");
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
  void testGetClonedFixingsAdded3() {
    // Arrange
    CalibrationDataItem calibrationDataItem = mock(CalibrationDataItem.class);
    when(calibrationDataItem.getProductName()).thenReturn("Product Name");

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
   *   <li>Given {@link Spec#Spec(String, String, String, String)} with {@code Key} and {@code Curve
   *       Name} and {@code Product Name} and {@code Maturity}.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationDataset#getClonedFixingsAdded(Set)}
   */
  @Test
  @DisplayName(
      "Test getClonedFixingsAdded(Set); given Spec(String, String, String, String) with 'Key' and 'Curve Name' and 'Product Name' and 'Maturity'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"CalibrationDataset CalibrationDataset.getClonedFixingsAdded(Set)"})
  void testGetClonedFixingsAdded_givenSpecWithKeyAndCurveNameAndProductNameAndMaturity() {
    // Arrange
    HashSet<CalibrationDataItem> newFixingDataItems = new HashSet<>();
    Spec spec = new Spec("Key", "Curve Name", "Product Name", "Maturity");
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
   *   <li>Given {@link Spec#Spec(String, String, String, String)} with {@code Key} and {@code Curve
   *       Name} and {@code Product Name} and {@code Maturity}.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationDataset#toMarketDataList()}
   */
  @Test
  @DisplayName(
      "Test toMarketDataList(); given Spec(String, String, String, String) with 'Key' and 'Curve Name' and 'Product Name' and 'Maturity'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"MarketDataList CalibrationDataset.toMarketDataList()"})
  void testToMarketDataList_givenSpecWithKeyAndCurveNameAndProductNameAndMaturity() {
    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    Spec spec = new Spec("Key", "Curve Name", "Product Name", "Maturity");

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
    assertEquals("Key", getResult.getId());
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
    Spec spec = new Spec("Key", "Curve Name", "Product Name", "Maturity");
    when(calibrationDataItem.getSpec()).thenReturn(spec);
    when(calibrationDataItem.getProductName()).thenReturn("Product Name");

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
    assertEquals("Key", getResult.getId());
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
  @MethodsUnderTest({"java.lang.String CalibrationDataset.serializeToJson()"})
  void testSerializeToJson_givenCalibrationDataItemGetQuoteReturnTen_thenCallsGetQuote() {
    // Arrange
    CalibrationDataItem calibrationDataItem = mock(CalibrationDataItem.class);
    when(calibrationDataItem.getQuote()).thenReturn(10.0d);
    Spec spec = new Spec("Key", "Curve Name", "Product Name", "Maturity");
    when(calibrationDataItem.getSpec()).thenReturn(spec);
    when(calibrationDataItem.getProductName()).thenReturn("Product Name");

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
    Spec spec = new Spec("Key", "Curve Name", "Product Name", "Maturity");
    when(calibrationDataItem.getSpec()).thenReturn(spec);
    when(calibrationDataItem.getProductName()).thenReturn("Product Name");

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
    when(calibrationDataItem.getProductName()).thenReturn("Product Name");

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
    Spec spec = new Spec("Key", "Curve Name", "Product Name", "Maturity");
    curveDataPointSet.add(
        new CalibrationDataItem(spec, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));
    CalibrationDataset calibrationDataset =
        new CalibrationDataset(curveDataPointSet, LocalDate.of(1970, 1, 1).atStartOfDay());

    // Act and Assert
    assertEquals(1, calibrationDataset.getDataPoints().size());
  }
}
