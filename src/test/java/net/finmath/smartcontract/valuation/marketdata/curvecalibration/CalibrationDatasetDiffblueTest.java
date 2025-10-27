package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyDouble;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.finmath.smartcontract.model.MarketDataList;
import net.finmath.smartcontract.valuation.marketdata.data.MarketDataPoint;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CalibrationDatasetDiffblueTest {
  /**
   * Method under test: {@link CalibrationDataset#getScaled(double)}
   */
  @Test
  void testGetScaled() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    LocalDate ofResult = LocalDate.of(1970, 1, 1);

    // Act
    CalibrationDataset actualScaled = (new CalibrationDataset(curveDataPointSet, ofResult.atStartOfDay()))
        .getScaled(10.0d);

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
   * Method under test: {@link CalibrationDataset#getScaled(double)}
   */
  @Test
  void testGetScaled2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("Key", "Curve Name", "Product Name", "Maturity");

    curveDataPointSet.add(new CalibrationDataItem(spec, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));
    LocalDate ofResult = LocalDate.of(1970, 1, 1);

    // Act
    CalibrationDataset actualScaled = (new CalibrationDataset(curveDataPointSet, ofResult.atStartOfDay()))
        .getScaled(10.0d);

    // Assert
    LocalDateTime date = actualScaled.getDate();
    assertEquals("00:00", date.toLocalTime().toString());
    LocalDate toLocalDateResult = date.toLocalDate();
    assertEquals("1970-01-01", toLocalDateResult.toString());
    assertEquals(1, actualScaled.getCalibrationDataItems().size());
    assertEquals(1, actualScaled.getDataPoints().size());
    assertTrue(actualScaled.getFixingDataItems().isEmpty());
    assertSame(ofResult, toLocalDateResult);
  }

  /**
   * Method under test: {@link CalibrationDataset#getScaled(double)}
   */
  @Test
  void testGetScaled3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibrationDataItem.Spec spec = mock(CalibrationDataItem.Spec.class);
    when(spec.getProductName()).thenReturn("Product Name");
    CalibrationDataItem calibrationDataItem = new CalibrationDataItem(spec, 10.0d,
        LocalDate.of(1970, 1, 1).atStartOfDay());

    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    curveDataPointSet.add(calibrationDataItem);
    LocalDate ofResult = LocalDate.of(1970, 1, 1);

    // Act
    CalibrationDataset actualScaled = (new CalibrationDataset(curveDataPointSet, ofResult.atStartOfDay()))
        .getScaled(10.0d);

    // Assert
    verify(spec, atLeast(1)).getProductName();
    LocalDateTime date = actualScaled.getDate();
    assertEquals("00:00", date.toLocalTime().toString());
    LocalDate toLocalDateResult = date.toLocalDate();
    assertEquals("1970-01-01", toLocalDateResult.toString());
    assertEquals(1, actualScaled.getCalibrationDataItems().size());
    assertEquals(1, actualScaled.getDataPoints().size());
    assertTrue(actualScaled.getFixingDataItems().isEmpty());
    assertSame(ofResult, toLocalDateResult);
  }

  /**
   * Method under test: {@link CalibrationDataset#getScaled(double)}
   */
  @Test
  void testGetScaled4() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibrationDataItem calibrationDataItem = mock(CalibrationDataItem.class);
    CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("Fixing", "Fixing", "Fixing", "Fixing");

    when(calibrationDataItem.getClonedScaled(anyDouble()))
        .thenReturn(new CalibrationDataItem(spec, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));
    when(calibrationDataItem.getProductName()).thenReturn("Product Name");

    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    curveDataPointSet.add(calibrationDataItem);
    LocalDate ofResult = LocalDate.of(1970, 1, 1);

    // Act
    CalibrationDataset actualScaled = (new CalibrationDataset(curveDataPointSet, ofResult.atStartOfDay()))
        .getScaled(10.0d);

    // Assert
    verify(calibrationDataItem).getClonedScaled(eq(10.0d));
    verify(calibrationDataItem, atLeast(1)).getProductName();
    LocalDateTime date = actualScaled.getDate();
    assertEquals("00:00", date.toLocalTime().toString());
    LocalDate toLocalDateResult = date.toLocalDate();
    assertEquals("1970-01-01", toLocalDateResult.toString());
    assertEquals(1, actualScaled.getDataPoints().size());
    assertEquals(1, actualScaled.getFixingDataItems().size());
    assertTrue(actualScaled.getCalibrationDataItems().isEmpty());
    assertSame(ofResult, toLocalDateResult);
  }

  /**
   * Method under test: {@link CalibrationDataset#getScaled(double)}
   */
  @Test
  void testGetScaled5() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibrationDataItem calibrationDataItem = mock(CalibrationDataItem.class);
    when(calibrationDataItem.getProductName()).thenReturn("Product Name");
    CalibrationDataItem calibrationDataItem2 = mock(CalibrationDataItem.class);
    when(calibrationDataItem2.getClonedScaled(anyDouble())).thenReturn(calibrationDataItem);
    when(calibrationDataItem2.getProductName()).thenReturn("Product Name");

    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    curveDataPointSet.add(calibrationDataItem2);
    LocalDate ofResult = LocalDate.of(1970, 1, 1);

    // Act
    CalibrationDataset actualScaled = (new CalibrationDataset(curveDataPointSet, ofResult.atStartOfDay()))
        .getScaled(10.0d);

    // Assert
    verify(calibrationDataItem2).getClonedScaled(eq(10.0d));
    verify(calibrationDataItem2, atLeast(1)).getProductName();
    verify(calibrationDataItem, atLeast(1)).getProductName();
    LocalDateTime date = actualScaled.getDate();
    assertEquals("00:00", date.toLocalTime().toString());
    LocalDate toLocalDateResult = date.toLocalDate();
    assertEquals("1970-01-01", toLocalDateResult.toString());
    assertEquals(1, actualScaled.getCalibrationDataItems().size());
    assertEquals(1, actualScaled.getDataPoints().size());
    assertTrue(actualScaled.getFixingDataItems().isEmpty());
    assertSame(ofResult, toLocalDateResult);
  }

  /**
   * Method under test: {@link CalibrationDataset#getClonedFixingsAdded(Set)}
   */
  @Test
  void testGetClonedFixingsAdded() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    LocalDate ofResult = LocalDate.of(1970, 1, 1);
    CalibrationDataset calibrationDataset = new CalibrationDataset(curveDataPointSet, ofResult.atStartOfDay());

    // Act
    CalibrationDataset actualClonedFixingsAdded = calibrationDataset.getClonedFixingsAdded(new HashSet<>());

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
   * Method under test: {@link CalibrationDataset#getClonedFixingsAdded(Set)}
   */
  @Test
  void testGetClonedFixingsAdded2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("Key", "Curve Name", "Product Name", "Maturity");

    curveDataPointSet.add(new CalibrationDataItem(spec, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));
    LocalDate ofResult = LocalDate.of(1970, 1, 1);
    CalibrationDataset calibrationDataset = new CalibrationDataset(curveDataPointSet, ofResult.atStartOfDay());

    // Act
    CalibrationDataset actualClonedFixingsAdded = calibrationDataset.getClonedFixingsAdded(new HashSet<>());

    // Assert
    LocalDateTime date = actualClonedFixingsAdded.getDate();
    assertEquals("00:00", date.toLocalTime().toString());
    LocalDate toLocalDateResult = date.toLocalDate();
    assertEquals("1970-01-01", toLocalDateResult.toString());
    assertEquals(1, actualClonedFixingsAdded.getCalibrationDataItems().size());
    assertEquals(1, actualClonedFixingsAdded.getDataPoints().size());
    assertTrue(actualClonedFixingsAdded.getFixingDataItems().isEmpty());
    assertSame(ofResult, toLocalDateResult);
  }

  /**
   * Method under test: {@link CalibrationDataset#getClonedFixingsAdded(Set)}
   */
  @Test
  void testGetClonedFixingsAdded3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("Key", "Curve Name", "Fixing", "Maturity");

    curveDataPointSet.add(new CalibrationDataItem(spec, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));
    LocalDate ofResult = LocalDate.of(1970, 1, 1);
    CalibrationDataset calibrationDataset = new CalibrationDataset(curveDataPointSet, ofResult.atStartOfDay());

    // Act
    CalibrationDataset actualClonedFixingsAdded = calibrationDataset.getClonedFixingsAdded(new HashSet<>());

    // Assert
    LocalDateTime date = actualClonedFixingsAdded.getDate();
    assertEquals("00:00", date.toLocalTime().toString());
    LocalDate toLocalDateResult = date.toLocalDate();
    assertEquals("1970-01-01", toLocalDateResult.toString());
    assertEquals(1, actualClonedFixingsAdded.getDataPoints().size());
    assertEquals(1, actualClonedFixingsAdded.getFixingDataItems().size());
    assertTrue(actualClonedFixingsAdded.getCalibrationDataItems().isEmpty());
    assertSame(ofResult, toLocalDateResult);
  }

  /**
   * Method under test: {@link CalibrationDataset#getClonedFixingsAdded(Set)}
   */
  @Test
  void testGetClonedFixingsAdded4() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    LocalDate ofResult = LocalDate.of(1970, 1, 1);
    CalibrationDataset calibrationDataset = new CalibrationDataset(curveDataPointSet, ofResult.atStartOfDay());

    HashSet<CalibrationDataItem> newFixingDataItems = new HashSet<>();
    CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("Key", "Curve Name", "Product Name", "Maturity");

    newFixingDataItems.add(new CalibrationDataItem(spec, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));

    // Act
    CalibrationDataset actualClonedFixingsAdded = calibrationDataset.getClonedFixingsAdded(newFixingDataItems);

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
   * Method under test: {@link CalibrationDataset#getClonedFixingsAdded(Set)}
   */
  @Test
  void testGetClonedFixingsAdded5() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    LocalDate ofResult = LocalDate.of(1970, 1, 1);
    CalibrationDataset calibrationDataset = new CalibrationDataset(curveDataPointSet, ofResult.atStartOfDay());

    HashSet<CalibrationDataItem> newFixingDataItems = new HashSet<>();
    CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("Key", "Curve Name", "Fixing", "Maturity");

    newFixingDataItems.add(new CalibrationDataItem(spec, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));

    // Act
    CalibrationDataset actualClonedFixingsAdded = calibrationDataset.getClonedFixingsAdded(newFixingDataItems);

    // Assert
    LocalDateTime date = actualClonedFixingsAdded.getDate();
    assertEquals("00:00", date.toLocalTime().toString());
    LocalDate toLocalDateResult = date.toLocalDate();
    assertEquals("1970-01-01", toLocalDateResult.toString());
    assertEquals(1, actualClonedFixingsAdded.getDataPoints().size());
    assertEquals(1, actualClonedFixingsAdded.getFixingDataItems().size());
    assertTrue(actualClonedFixingsAdded.getCalibrationDataItems().isEmpty());
    assertSame(ofResult, toLocalDateResult);
  }

  /**
   * Method under test: {@link CalibrationDataset#getClonedFixingsAdded(Set)}
   */
  @Test
  void testGetClonedFixingsAdded6() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    LocalDate ofResult = LocalDate.of(1970, 1, 1);
    CalibrationDataset calibrationDataset = new CalibrationDataset(curveDataPointSet, ofResult.atStartOfDay());

    HashSet<CalibrationDataItem> newFixingDataItems = new HashSet<>();
    CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec(
        "net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationDataItem$Spec", "Curve Name",
        "Product Name", "Maturity");

    newFixingDataItems.add(new CalibrationDataItem(spec, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));
    CalibrationDataItem.Spec spec2 = new CalibrationDataItem.Spec("Key", "Curve Name", "Product Name", "Maturity");

    newFixingDataItems.add(new CalibrationDataItem(spec2, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));

    // Act
    CalibrationDataset actualClonedFixingsAdded = calibrationDataset.getClonedFixingsAdded(newFixingDataItems);

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
   * Method under test: {@link CalibrationDataset#getClonedFixingsAdded(Set)}
   */
  @Test
  void testGetClonedFixingsAdded7() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibrationDataItem.Spec spec = mock(CalibrationDataItem.Spec.class);
    when(spec.getProductName()).thenReturn("Product Name");
    CalibrationDataItem calibrationDataItem = new CalibrationDataItem(spec, 10.0d,
        LocalDate.of(1970, 1, 1).atStartOfDay());

    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    curveDataPointSet.add(calibrationDataItem);
    LocalDate ofResult = LocalDate.of(1970, 1, 1);
    CalibrationDataset calibrationDataset = new CalibrationDataset(curveDataPointSet, ofResult.atStartOfDay());

    // Act
    CalibrationDataset actualClonedFixingsAdded = calibrationDataset.getClonedFixingsAdded(new HashSet<>());

    // Assert
    verify(spec, atLeast(1)).getProductName();
    LocalDateTime date = actualClonedFixingsAdded.getDate();
    assertEquals("00:00", date.toLocalTime().toString());
    LocalDate toLocalDateResult = date.toLocalDate();
    assertEquals("1970-01-01", toLocalDateResult.toString());
    assertEquals(1, actualClonedFixingsAdded.getCalibrationDataItems().size());
    assertEquals(1, actualClonedFixingsAdded.getDataPoints().size());
    assertTrue(actualClonedFixingsAdded.getFixingDataItems().isEmpty());
    assertSame(ofResult, toLocalDateResult);
  }

  /**
   * Method under test: {@link CalibrationDataset#getClonedFixingsAdded(Set)}
   */
  @Test
  void testGetClonedFixingsAdded8() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibrationDataItem calibrationDataItem = mock(CalibrationDataItem.class);
    when(calibrationDataItem.getProductName()).thenReturn("Product Name");

    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    curveDataPointSet.add(calibrationDataItem);
    LocalDate ofResult = LocalDate.of(1970, 1, 1);
    CalibrationDataset calibrationDataset = new CalibrationDataset(curveDataPointSet, ofResult.atStartOfDay());

    // Act
    CalibrationDataset actualClonedFixingsAdded = calibrationDataset.getClonedFixingsAdded(new HashSet<>());

    // Assert
    verify(calibrationDataItem, atLeast(1)).getProductName();
    LocalDateTime date = actualClonedFixingsAdded.getDate();
    assertEquals("00:00", date.toLocalTime().toString());
    LocalDate toLocalDateResult = date.toLocalDate();
    assertEquals("1970-01-01", toLocalDateResult.toString());
    assertEquals(1, actualClonedFixingsAdded.getCalibrationDataItems().size());
    assertEquals(1, actualClonedFixingsAdded.getDataPoints().size());
    assertTrue(actualClonedFixingsAdded.getFixingDataItems().isEmpty());
    assertSame(ofResult, toLocalDateResult);
  }

  /**
   * Method under test: {@link CalibrationDataset#getClonedFixingsAdded(Set)}
   */
  @Test
  void testGetClonedFixingsAdded9() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibrationDataItem calibrationDataItem = mock(CalibrationDataItem.class);
    when(calibrationDataItem.getProductName()).thenReturn("Fixing");

    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("Key", "Curve Name", "Product Name", "Maturity");

    curveDataPointSet.add(new CalibrationDataItem(spec, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));
    curveDataPointSet.add(calibrationDataItem);
    LocalDate ofResult = LocalDate.of(1970, 1, 1);
    CalibrationDataset calibrationDataset = new CalibrationDataset(curveDataPointSet, ofResult.atStartOfDay());

    // Act
    CalibrationDataset actualClonedFixingsAdded = calibrationDataset.getClonedFixingsAdded(new HashSet<>());

    // Assert
    verify(calibrationDataItem, atLeast(1)).getProductName();
    LocalDateTime date = actualClonedFixingsAdded.getDate();
    assertEquals("00:00", date.toLocalTime().toString());
    LocalDate toLocalDateResult = date.toLocalDate();
    assertEquals("1970-01-01", toLocalDateResult.toString());
    assertEquals(1, actualClonedFixingsAdded.getCalibrationDataItems().size());
    assertEquals(1, actualClonedFixingsAdded.getFixingDataItems().size());
    assertEquals(curveDataPointSet, actualClonedFixingsAdded.getDataPoints());
    assertSame(ofResult, toLocalDateResult);
  }

  /**
   * Method under test: {@link CalibrationDataset#getClonedFixingsAdded(Set)}
   */
  @Test
  void testGetClonedFixingsAdded10() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibrationDataItem calibrationDataItem = mock(CalibrationDataItem.class);
    when(calibrationDataItem.getProductName()).thenReturn("Product Name");

    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    curveDataPointSet.add(calibrationDataItem);
    LocalDate ofResult = LocalDate.of(1970, 1, 1);
    CalibrationDataset calibrationDataset = new CalibrationDataset(curveDataPointSet, ofResult.atStartOfDay());
    CalibrationDataItem calibrationDataItem2 = mock(CalibrationDataItem.class);
    when(calibrationDataItem2.getProductName()).thenReturn("Product Name");

    HashSet<CalibrationDataItem> newFixingDataItems = new HashSet<>();
    newFixingDataItems.add(calibrationDataItem2);

    // Act
    CalibrationDataset actualClonedFixingsAdded = calibrationDataset.getClonedFixingsAdded(newFixingDataItems);

    // Assert
    verify(calibrationDataItem2).getProductName();
    verify(calibrationDataItem, atLeast(1)).getProductName();
    LocalDateTime date = actualClonedFixingsAdded.getDate();
    assertEquals("00:00", date.toLocalTime().toString());
    LocalDate toLocalDateResult = date.toLocalDate();
    assertEquals("1970-01-01", toLocalDateResult.toString());
    assertEquals(1, actualClonedFixingsAdded.getCalibrationDataItems().size());
    assertEquals(1, actualClonedFixingsAdded.getDataPoints().size());
    assertTrue(actualClonedFixingsAdded.getFixingDataItems().isEmpty());
    assertSame(ofResult, toLocalDateResult);
  }

  /**
   * Method under test: {@link CalibrationDataset#getClonedFixingsAdded(Set)}
   */
  @Test
  void testGetClonedFixingsAdded11() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibrationDataItem calibrationDataItem = mock(CalibrationDataItem.class);
    when(calibrationDataItem.getProductName()).thenReturn("Product Name");

    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    curveDataPointSet.add(calibrationDataItem);
    LocalDate ofResult = LocalDate.of(1970, 1, 1);
    CalibrationDataset calibrationDataset = new CalibrationDataset(curveDataPointSet, ofResult.atStartOfDay());
    CalibrationDataItem calibrationDataItem2 = mock(CalibrationDataItem.class);
    when(calibrationDataItem2.getProductName()).thenReturn("Fixing");

    HashSet<CalibrationDataItem> newFixingDataItems = new HashSet<>();
    newFixingDataItems.add(calibrationDataItem2);

    // Act
    CalibrationDataset actualClonedFixingsAdded = calibrationDataset.getClonedFixingsAdded(newFixingDataItems);

    // Assert
    verify(calibrationDataItem2, atLeast(1)).getProductName();
    verify(calibrationDataItem, atLeast(1)).getProductName();
    LocalDateTime date = actualClonedFixingsAdded.getDate();
    assertEquals("00:00", date.toLocalTime().toString());
    LocalDate toLocalDateResult = date.toLocalDate();
    assertEquals("1970-01-01", toLocalDateResult.toString());
    assertEquals(1, actualClonedFixingsAdded.getCalibrationDataItems().size());
    assertEquals(1, actualClonedFixingsAdded.getFixingDataItems().size());
    assertEquals(2, actualClonedFixingsAdded.getDataPoints().size());
    assertSame(ofResult, toLocalDateResult);
  }

  /**
   * Method under test: {@link CalibrationDataset#getClonedFixingsAdded(Set)}
   */
  @Test
  void testGetClonedFixingsAdded12() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibrationDataItem calibrationDataItem = mock(CalibrationDataItem.class);
    when(calibrationDataItem.getDate()).thenReturn(LocalDate.of(1970, 1, 1));
    when(calibrationDataItem.getCurveName()).thenReturn("Curve Name");
    when(calibrationDataItem.getProductName()).thenReturn("Fixing");

    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    curveDataPointSet.add(calibrationDataItem);
    LocalDate ofResult = LocalDate.of(1970, 1, 1);
    CalibrationDataset calibrationDataset = new CalibrationDataset(curveDataPointSet, ofResult.atStartOfDay());
    CalibrationDataItem calibrationDataItem2 = mock(CalibrationDataItem.class);
    when(calibrationDataItem2.getCurveName()).thenReturn("Curve Name");
    when(calibrationDataItem2.getDate()).thenReturn(LocalDate.of(1970, 1, 1));
    when(calibrationDataItem2.getProductName()).thenReturn("Fixing");

    HashSet<CalibrationDataItem> newFixingDataItems = new HashSet<>();
    newFixingDataItems.add(calibrationDataItem2);

    // Act
    CalibrationDataset actualClonedFixingsAdded = calibrationDataset.getClonedFixingsAdded(newFixingDataItems);

    // Assert
    verify(calibrationDataItem).getCurveName();
    verify(calibrationDataItem2).getCurveName();
    verify(calibrationDataItem).getDate();
    verify(calibrationDataItem2).getDate();
    verify(calibrationDataItem2).getProductName();
    verify(calibrationDataItem, atLeast(1)).getProductName();
    LocalDateTime date = actualClonedFixingsAdded.getDate();
    assertEquals("00:00", date.toLocalTime().toString());
    LocalDate toLocalDateResult = date.toLocalDate();
    assertEquals("1970-01-01", toLocalDateResult.toString());
    assertEquals(1, actualClonedFixingsAdded.getDataPoints().size());
    assertEquals(1, actualClonedFixingsAdded.getFixingDataItems().size());
    assertTrue(actualClonedFixingsAdded.getCalibrationDataItems().isEmpty());
    assertSame(ofResult, toLocalDateResult);
  }

  /**
   * Method under test: {@link CalibrationDataset#getClonedFixingsAdded(Set)}
   */
  @Test
  void testGetClonedFixingsAdded13() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibrationDataItem calibrationDataItem = mock(CalibrationDataItem.class);
    when(calibrationDataItem.getDate()).thenReturn(LocalDate.of(1970, 1, 1));
    when(calibrationDataItem.getCurveName()).thenReturn("Fixing");
    when(calibrationDataItem.getProductName()).thenReturn("Fixing");

    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    curveDataPointSet.add(calibrationDataItem);
    LocalDate ofResult = LocalDate.of(1970, 1, 1);
    CalibrationDataset calibrationDataset = new CalibrationDataset(curveDataPointSet, ofResult.atStartOfDay());
    CalibrationDataItem calibrationDataItem2 = mock(CalibrationDataItem.class);
    when(calibrationDataItem2.getCurveName()).thenReturn("Curve Name");
    when(calibrationDataItem2.getDate()).thenReturn(LocalDate.of(1970, 1, 1));
    when(calibrationDataItem2.getProductName()).thenReturn("Fixing");

    HashSet<CalibrationDataItem> newFixingDataItems = new HashSet<>();
    newFixingDataItems.add(calibrationDataItem2);

    // Act
    CalibrationDataset actualClonedFixingsAdded = calibrationDataset.getClonedFixingsAdded(newFixingDataItems);

    // Assert
    verify(calibrationDataItem).getCurveName();
    verify(calibrationDataItem2).getCurveName();
    verify(calibrationDataItem).getDate();
    verify(calibrationDataItem2).getDate();
    verify(calibrationDataItem2, atLeast(1)).getProductName();
    verify(calibrationDataItem, atLeast(1)).getProductName();
    LocalDateTime date = actualClonedFixingsAdded.getDate();
    assertEquals("00:00", date.toLocalTime().toString());
    LocalDate toLocalDateResult = date.toLocalDate();
    assertEquals("1970-01-01", toLocalDateResult.toString());
    Set<CalibrationDataItem> dataPoints = actualClonedFixingsAdded.getDataPoints();
    assertEquals(2, dataPoints.size());
    assertTrue(actualClonedFixingsAdded.getCalibrationDataItems().isEmpty());
    assertEquals(dataPoints, actualClonedFixingsAdded.getFixingDataItems());
    assertSame(ofResult, toLocalDateResult);
  }

  /**
   * Method under test: {@link CalibrationDataset#toMarketDataList()}
   */
  @Test
  void testToMarketDataList() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();

    // Act
    MarketDataList actualToMarketDataListResult = (new CalibrationDataset(curveDataPointSet,
        LocalDate.of(1970, 1, 1).atStartOfDay())).toMarketDataList();

    // Assert
    assertEquals(0, actualToMarketDataListResult.getSize());
    assertTrue(actualToMarketDataListResult.getPoints().isEmpty());
  }

  /**
   * Method under test: {@link CalibrationDataset#toMarketDataList()}
   */
  @Test
  void testToMarketDataList2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("Key", "Curve Name", "Product Name", "Maturity");

    LocalDate ofResult = LocalDate.of(1970, 1, 1);
    curveDataPointSet.add(new CalibrationDataItem(spec, 10.0d, ofResult.atStartOfDay()));

    // Act
    MarketDataList actualToMarketDataListResult = (new CalibrationDataset(curveDataPointSet,
        LocalDate.of(1970, 1, 1).atStartOfDay())).toMarketDataList();

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
   * Method under test: {@link CalibrationDataset#toMarketDataList()}
   */
  @Test
  void testToMarketDataList3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibrationDataItem.Spec spec = mock(CalibrationDataItem.Spec.class);
    when(spec.getKey()).thenReturn("Key");
    when(spec.getProductName()).thenReturn("Product Name");
    LocalDate ofResult = LocalDate.of(1970, 1, 1);
    CalibrationDataItem calibrationDataItem = new CalibrationDataItem(spec, 10.0d, ofResult.atStartOfDay());

    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    curveDataPointSet.add(calibrationDataItem);

    // Act
    MarketDataList actualToMarketDataListResult = (new CalibrationDataset(curveDataPointSet,
        LocalDate.of(1970, 1, 1).atStartOfDay())).toMarketDataList();

    // Assert
    verify(spec).getKey();
    verify(spec, atLeast(1)).getProductName();
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
   * Method under test: {@link CalibrationDataset#toMarketDataList()}
   */
  @Test
  void testToMarketDataList4() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibrationDataItem calibrationDataItem = mock(CalibrationDataItem.class);
    when(calibrationDataItem.getQuote()).thenReturn(10.0d);
    LocalDate ofResult = LocalDate.of(1970, 1, 1);
    when(calibrationDataItem.getDateTime()).thenReturn(ofResult.atStartOfDay());
    when(calibrationDataItem.getSpec())
        .thenReturn(new CalibrationDataItem.Spec("Key", "Curve Name", "Product Name", "Maturity"));
    when(calibrationDataItem.getProductName()).thenReturn("Product Name");

    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    curveDataPointSet.add(calibrationDataItem);

    // Act
    MarketDataList actualToMarketDataListResult = (new CalibrationDataset(curveDataPointSet,
        LocalDate.of(1970, 1, 1).atStartOfDay())).toMarketDataList();

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
   * Method under test: {@link CalibrationDataset#serializeToJson()}
   */
  @Test
  void testSerializeToJson() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibrationDataItem.Spec spec = mock(CalibrationDataItem.Spec.class);
    when(spec.getCurveName()).thenReturn("Curve Name");
    when(spec.getMaturity()).thenReturn("Maturity");
    when(spec.getProductName()).thenReturn("Product Name");
    CalibrationDataItem calibrationDataItem = new CalibrationDataItem(spec, 10.0d,
        LocalDate.of(1970, 1, 1).atStartOfDay());

    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    curveDataPointSet.add(calibrationDataItem);

    // Act
    (new CalibrationDataset(curveDataPointSet, LocalDate.of(1970, 1, 1).atStartOfDay())).serializeToJson();

    // Assert
    verify(spec, atLeast(1)).getCurveName();
    verify(spec).getMaturity();
    verify(spec, atLeast(1)).getProductName();
  }

  /**
   * Method under test: {@link CalibrationDataset#serializeToJson()}
   */
  @Test
  void testSerializeToJson2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibrationDataItem calibrationDataItem = mock(CalibrationDataItem.class);
    when(calibrationDataItem.getQuote()).thenReturn(10.0d);
    when(calibrationDataItem.getSpec())
        .thenReturn(new CalibrationDataItem.Spec("Key", "Curve Name", "Product Name", "Maturity"));
    when(calibrationDataItem.getProductName()).thenReturn("Product Name");

    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    curveDataPointSet.add(calibrationDataItem);

    // Act
    (new CalibrationDataset(curveDataPointSet, LocalDate.of(1970, 1, 1).atStartOfDay())).serializeToJson();

    // Assert
    verify(calibrationDataItem, atLeast(1)).getProductName();
    verify(calibrationDataItem).getQuote();
    verify(calibrationDataItem, atLeast(1)).getSpec();
  }

  /**
   * Method under test:
   * {@link CalibrationDataset#getDataAsCalibrationDataPointStream(CalibrationParser)}
   */
  @Test
  void testGetDataAsCalibrationDataPointStream() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    CalibrationDataset calibrationDataset = new CalibrationDataset(curveDataPointSet,
        LocalDate.of(1970, 1, 1).atStartOfDay());
    CalibrationParser parser = mock(CalibrationParser.class);

    ArrayList<CalibrationSpecProvider> calibrationSpecProviderList = new ArrayList<>();
    Stream<CalibrationSpecProvider> streamResult = calibrationSpecProviderList.stream();
    when(parser.parse(Mockito.<Stream<CalibrationDataItem>>any())).thenReturn(streamResult);

    // Act
    Stream<CalibrationSpecProvider> actualDataAsCalibrationDataPointStream = calibrationDataset
        .getDataAsCalibrationDataPointStream(parser);

    // Assert
    verify(parser).parse(isA(Stream.class));
    assertTrue(actualDataAsCalibrationDataPointStream.limit(5).collect(Collectors.toList()).isEmpty());
  }

  /**
   * Method under test: {@link CalibrationDataset#getDataPoints()}
   */
  @Test
  void testGetDataPoints() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();

    // Act and Assert
    assertTrue(
        (new CalibrationDataset(curveDataPointSet, LocalDate.of(1970, 1, 1).atStartOfDay())).getDataPoints().isEmpty());
  }

  /**
   * Method under test: {@link CalibrationDataset#getDataPoints()}
   */
  @Test
  void testGetDataPoints2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("Key", "Curve Name", "Product Name", "Maturity");

    curveDataPointSet.add(new CalibrationDataItem(spec, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));

    // Act and Assert
    assertEquals(1,
        (new CalibrationDataset(curveDataPointSet, LocalDate.of(1970, 1, 1).atStartOfDay())).getDataPoints().size());
  }

  /**
   * Method under test: {@link CalibrationDataset#getDataPoints()}
   */
  @Test
  void testGetDataPoints3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibrationDataItem.Spec spec = mock(CalibrationDataItem.Spec.class);
    when(spec.getProductName()).thenReturn("Product Name");
    CalibrationDataItem calibrationDataItem = new CalibrationDataItem(spec, 10.0d,
        LocalDate.of(1970, 1, 1).atStartOfDay());

    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    curveDataPointSet.add(calibrationDataItem);

    // Act
    Set<CalibrationDataItem> actualDataPoints = (new CalibrationDataset(curveDataPointSet,
        LocalDate.of(1970, 1, 1).atStartOfDay())).getDataPoints();

    // Assert
    verify(spec, atLeast(1)).getProductName();
    assertEquals(1, actualDataPoints.size());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CalibrationDataset#getCalibrationDataItems()}
   *   <li>{@link CalibrationDataset#getDate()}
   *   <li>{@link CalibrationDataset#getFixingDataItems()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    CalibrationDataset calibrationDataset = new CalibrationDataset(curveDataPointSet,
        LocalDate.of(1970, 1, 1).atStartOfDay());

    // Act
    Set<CalibrationDataItem> actualCalibrationDataItems = calibrationDataset.getCalibrationDataItems();
    LocalDateTime actualDate = calibrationDataset.getDate();
    Set<CalibrationDataItem> actualFixingDataItems = calibrationDataset.getFixingDataItems();

    // Assert
    assertTrue(actualCalibrationDataItems.isEmpty());
    assertTrue(actualFixingDataItems.isEmpty());
    assertSame(calibrationDataset.scenarioDate, actualDate);
  }

  /**
   * Method under test:
   * {@link CalibrationDataset#CalibrationDataset(Set, LocalDateTime)}
   */
  @Test
  void testNewCalibrationDataset() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    LocalDateTime scenarioDate = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act
    CalibrationDataset actualCalibrationDataset = new CalibrationDataset(curveDataPointSet, scenarioDate);

    // Assert
    assertTrue(actualCalibrationDataset.getCalibrationDataItems().isEmpty());
    assertTrue(actualCalibrationDataset.getDataPoints().isEmpty());
    assertTrue(actualCalibrationDataset.getFixingDataItems().isEmpty());
    assertSame(scenarioDate, actualCalibrationDataset.getDate());
  }

  /**
   * Method under test:
   * {@link CalibrationDataset#CalibrationDataset(Set, LocalDateTime)}
   */
  @Test
  void testNewCalibrationDataset2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("Key", "Curve Name", "Product Name", "Maturity");

    curveDataPointSet.add(new CalibrationDataItem(spec, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));
    LocalDateTime scenarioDate = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act
    CalibrationDataset actualCalibrationDataset = new CalibrationDataset(curveDataPointSet, scenarioDate);

    // Assert
    assertEquals(1, actualCalibrationDataset.getCalibrationDataItems().size());
    assertEquals(1, actualCalibrationDataset.getDataPoints().size());
    assertTrue(actualCalibrationDataset.getFixingDataItems().isEmpty());
    assertSame(scenarioDate, actualCalibrationDataset.getDate());
  }

  /**
   * Method under test:
   * {@link CalibrationDataset#CalibrationDataset(Set, LocalDateTime)}
   */
  @Test
  void testNewCalibrationDataset3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("Key", "Curve Name", "Fixing", "Maturity");

    curveDataPointSet.add(new CalibrationDataItem(spec, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));
    LocalDateTime scenarioDate = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act
    CalibrationDataset actualCalibrationDataset = new CalibrationDataset(curveDataPointSet, scenarioDate);

    // Assert
    assertEquals(1, actualCalibrationDataset.getDataPoints().size());
    assertEquals(1, actualCalibrationDataset.getFixingDataItems().size());
    assertTrue(actualCalibrationDataset.getCalibrationDataItems().isEmpty());
    assertSame(scenarioDate, actualCalibrationDataset.getDate());
  }

  /**
   * Method under test:
   * {@link CalibrationDataset#CalibrationDataset(Set, LocalDateTime)}
   */
  @Test
  void testNewCalibrationDataset4() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibrationDataItem.Spec spec = mock(CalibrationDataItem.Spec.class);
    when(spec.getProductName()).thenReturn("Product Name");
    CalibrationDataItem calibrationDataItem = new CalibrationDataItem(spec, 10.0d,
        LocalDate.of(1970, 1, 1).atStartOfDay());

    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    curveDataPointSet.add(calibrationDataItem);
    LocalDateTime scenarioDate = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act
    CalibrationDataset actualCalibrationDataset = new CalibrationDataset(curveDataPointSet, scenarioDate);

    // Assert
    verify(spec, atLeast(1)).getProductName();
    assertEquals(1, actualCalibrationDataset.getCalibrationDataItems().size());
    assertEquals(1, actualCalibrationDataset.getDataPoints().size());
    assertTrue(actualCalibrationDataset.getFixingDataItems().isEmpty());
    assertSame(scenarioDate, actualCalibrationDataset.getDate());
  }

  /**
   * Method under test:
   * {@link CalibrationDataset#CalibrationDataset(Set, LocalDateTime)}
   */
  @Test
  void testNewCalibrationDataset5() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibrationDataItem calibrationDataItem = mock(CalibrationDataItem.class);
    when(calibrationDataItem.getProductName()).thenReturn("Product Name");

    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    curveDataPointSet.add(calibrationDataItem);
    LocalDateTime scenarioDate = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act
    CalibrationDataset actualCalibrationDataset = new CalibrationDataset(curveDataPointSet, scenarioDate);

    // Assert
    verify(calibrationDataItem, atLeast(1)).getProductName();
    assertEquals(1, actualCalibrationDataset.getCalibrationDataItems().size());
    assertEquals(1, actualCalibrationDataset.getDataPoints().size());
    assertTrue(actualCalibrationDataset.getFixingDataItems().isEmpty());
    assertSame(scenarioDate, actualCalibrationDataset.getDate());
  }
}
