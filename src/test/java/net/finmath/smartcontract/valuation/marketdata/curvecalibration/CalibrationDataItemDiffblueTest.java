package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class CalibrationDataItemDiffblueTest {
  /**
   * Method under test: {@link CalibrationDataItem#getClonedScaled(double)}
   */
  @Test
  void testGetClonedScaled() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("Key", "Curve Name", "Product Name", "Maturity");

    LocalDate ofResult = LocalDate.of(1970, 1, 1);

    // Act
    CalibrationDataItem actualClonedScaled = (new CalibrationDataItem(spec, 10.0d, ofResult.atStartOfDay()))
        .getClonedScaled(10.0d);

    // Assert
    LocalDateTime dateTime = actualClonedScaled.getDateTime();
    assertEquals("00:00", dateTime.toLocalTime().toString());
    LocalDate date = actualClonedScaled.getDate();
    assertEquals("1970-01-01", date.toString());
    assertEquals("1970-01-01", actualClonedScaled.getDateString());
    assertEquals("Curve Name", actualClonedScaled.getCurveName());
    assertEquals("Maturity", actualClonedScaled.getMaturity());
    assertEquals("Product Name", actualClonedScaled.getProductName());
    assertEquals(1.0d, actualClonedScaled.getQuote().doubleValue());
    assertSame(spec, actualClonedScaled.getSpec());
    assertSame(ofResult, dateTime.toLocalDate());
    assertSame(ofResult, date);
  }

  /**
   * Method under test: {@link CalibrationDataItem#getClonedScaled(double)}
   */
  @Test
  void testGetClonedScaled2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibrationDataItem.Spec spec = mock(CalibrationDataItem.Spec.class);
    LocalDate ofResult = LocalDate.of(1970, 1, 1);

    // Act
    CalibrationDataItem actualClonedScaled = (new CalibrationDataItem(spec, 10.0d, ofResult.atStartOfDay()))
        .getClonedScaled(10.0d);

    // Assert
    LocalDateTime dateTime = actualClonedScaled.getDateTime();
    assertEquals("00:00", dateTime.toLocalTime().toString());
    LocalDate date = actualClonedScaled.getDate();
    assertEquals("1970-01-01", date.toString());
    assertEquals("1970-01-01", actualClonedScaled.getDateString());
    assertNull(actualClonedScaled.getCurveName());
    assertNull(actualClonedScaled.getMaturity());
    assertNull(actualClonedScaled.getProductName());
    assertEquals(1.0d, actualClonedScaled.getQuote().doubleValue());
    assertSame(ofResult, dateTime.toLocalDate());
    assertSame(ofResult, date);
  }

  /**
   * Method under test: {@link CalibrationDataItem#getClonedShifted(double)}
   */
  @Test
  void testGetClonedShifted() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("Key", "Curve Name", "Product Name", "Maturity");

    LocalDate ofResult = LocalDate.of(1970, 1, 1);

    // Act
    CalibrationDataItem actualClonedShifted = (new CalibrationDataItem(spec, 10.0d, ofResult.atStartOfDay()))
        .getClonedShifted(10.0d);

    // Assert
    LocalDateTime dateTime = actualClonedShifted.getDateTime();
    assertEquals("00:00", dateTime.toLocalTime().toString());
    LocalDate date = actualClonedShifted.getDate();
    assertEquals("1970-01-01", date.toString());
    assertEquals("1970-01-01", actualClonedShifted.getDateString());
    assertEquals("Curve Name", actualClonedShifted.getCurveName());
    assertEquals("Maturity", actualClonedShifted.getMaturity());
    assertEquals("Product Name", actualClonedShifted.getProductName());
    assertEquals(20.0d, actualClonedShifted.getQuote().doubleValue());
    assertSame(spec, actualClonedShifted.getSpec());
    assertSame(ofResult, dateTime.toLocalDate());
    assertSame(ofResult, date);
  }

  /**
   * Method under test: {@link CalibrationDataItem#getClonedShifted(double)}
   */
  @Test
  void testGetClonedShifted2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibrationDataItem.Spec spec = mock(CalibrationDataItem.Spec.class);
    LocalDate ofResult = LocalDate.of(1970, 1, 1);

    // Act
    CalibrationDataItem actualClonedShifted = (new CalibrationDataItem(spec, 10.0d, ofResult.atStartOfDay()))
        .getClonedShifted(10.0d);

    // Assert
    LocalDateTime dateTime = actualClonedShifted.getDateTime();
    assertEquals("00:00", dateTime.toLocalTime().toString());
    LocalDate date = actualClonedShifted.getDate();
    assertEquals("1970-01-01", date.toString());
    assertEquals("1970-01-01", actualClonedShifted.getDateString());
    assertNull(actualClonedShifted.getCurveName());
    assertNull(actualClonedShifted.getMaturity());
    assertNull(actualClonedShifted.getProductName());
    assertEquals(20.0d, actualClonedShifted.getQuote().doubleValue());
    assertSame(ofResult, dateTime.toLocalDate());
    assertSame(ofResult, date);
  }

  /**
   * Method under test: {@link CalibrationDataItem#getCurveName()}
   */
  @Test
  void testGetCurveName() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("Key", "Curve Name", "Product Name", "Maturity");

    // Act and Assert
    assertEquals("Curve Name",
        (new CalibrationDataItem(spec, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay())).getCurveName());
  }

  /**
   * Method under test: {@link CalibrationDataItem#getCurveName()}
   */
  @Test
  void testGetCurveName2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibrationDataItem.Spec spec = mock(CalibrationDataItem.Spec.class);
    when(spec.getCurveName()).thenReturn("Curve Name");

    // Act
    String actualCurveName = (new CalibrationDataItem(spec, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay()))
        .getCurveName();

    // Assert
    verify(spec).getCurveName();
    assertEquals("Curve Name", actualCurveName);
  }

  /**
   * Method under test: {@link CalibrationDataItem#getProductName()}
   */
  @Test
  void testGetProductName() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("Key", "Curve Name", "Product Name", "Maturity");

    // Act and Assert
    assertEquals("Product Name",
        (new CalibrationDataItem(spec, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay())).getProductName());
  }

  /**
   * Method under test: {@link CalibrationDataItem#getProductName()}
   */
  @Test
  void testGetProductName2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibrationDataItem.Spec spec = mock(CalibrationDataItem.Spec.class);
    when(spec.getProductName()).thenReturn("Product Name");

    // Act
    String actualProductName = (new CalibrationDataItem(spec, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay()))
        .getProductName();

    // Assert
    verify(spec).getProductName();
    assertEquals("Product Name", actualProductName);
  }

  /**
   * Method under test: {@link CalibrationDataItem#getMaturity()}
   */
  @Test
  void testGetMaturity() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("Key", "Curve Name", "Product Name", "Maturity");

    // Act and Assert
    assertEquals("Maturity",
        (new CalibrationDataItem(spec, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay())).getMaturity());
  }

  /**
   * Method under test: {@link CalibrationDataItem#getMaturity()}
   */
  @Test
  void testGetMaturity2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibrationDataItem.Spec spec = mock(CalibrationDataItem.Spec.class);
    when(spec.getMaturity()).thenReturn("Maturity");

    // Act
    String actualMaturity = (new CalibrationDataItem(spec, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay()))
        .getMaturity();

    // Assert
    verify(spec).getMaturity();
    assertEquals("Maturity", actualMaturity);
  }

  /**
   * Method under test: {@link CalibrationDataItem#getDateString()}
   */
  @Test
  void testGetDateString() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("Key", "Curve Name", "Product Name", "Maturity");

    // Act and Assert
    assertEquals("1970-01-01",
        (new CalibrationDataItem(spec, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay())).getDateString());
  }

  /**
   * Method under test: {@link CalibrationDataItem#getDateString()}
   */
  @Test
  void testGetDateString2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibrationDataItem.Spec spec = mock(CalibrationDataItem.Spec.class);

    // Act and Assert
    assertEquals("1970-01-01",
        (new CalibrationDataItem(spec, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay())).getDateString());
  }

  /**
   * Method under test: {@link CalibrationDataItem#getDate()}
   */
  @Test
  void testGetDate() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("Key", "Curve Name", "Product Name", "Maturity");

    LocalDate ofResult = LocalDate.of(1970, 1, 1);

    // Act
    LocalDate actualDate = (new CalibrationDataItem(spec, 10.0d, ofResult.atStartOfDay())).getDate();

    // Assert
    assertEquals("1970-01-01", actualDate.toString());
    assertSame(ofResult, actualDate);
  }

  /**
   * Method under test: {@link CalibrationDataItem#getDate()}
   */
  @Test
  void testGetDate2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibrationDataItem.Spec spec = mock(CalibrationDataItem.Spec.class);
    LocalDate ofResult = LocalDate.of(1970, 1, 1);

    // Act
    LocalDate actualDate = (new CalibrationDataItem(spec, 10.0d, ofResult.atStartOfDay())).getDate();

    // Assert
    assertEquals("1970-01-01", actualDate.toString());
    assertSame(ofResult, actualDate);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CalibrationDataItem#equals(Object)}
   *   <li>{@link CalibrationDataItem#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("Key", "Curve Name", "Product Name", "Maturity");

    CalibrationDataItem calibrationDataItem = new CalibrationDataItem(spec, 10.0d,
        LocalDate.of(1970, 1, 1).atStartOfDay());
    CalibrationDataItem.Spec spec2 = new CalibrationDataItem.Spec("Key", "Curve Name", "Product Name", "Maturity");

    CalibrationDataItem calibrationDataItem2 = new CalibrationDataItem(spec2, 10.0d,
        LocalDate.of(1970, 1, 1).atStartOfDay());

    // Act and Assert
    assertEquals(calibrationDataItem, calibrationDataItem2);
    int expectedHashCodeResult = calibrationDataItem.hashCode();
    assertEquals(expectedHashCodeResult, calibrationDataItem2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CalibrationDataItem#equals(Object)}
   *   <li>{@link CalibrationDataItem#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("Key", "Curve Name", "Product Name", "Maturity");

    CalibrationDataItem calibrationDataItem = new CalibrationDataItem(spec, 10.0d,
        LocalDate.of(1970, 1, 1).atStartOfDay());

    // Act and Assert
    assertEquals(calibrationDataItem, calibrationDataItem);
    int expectedHashCodeResult = calibrationDataItem.hashCode();
    assertEquals(expectedHashCodeResult, calibrationDataItem.hashCode());
  }

  /**
   * Method under test: {@link CalibrationDataItem#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec(null, "Curve Name", "Product Name", "Maturity");

    CalibrationDataItem calibrationDataItem = new CalibrationDataItem(spec, 10.0d,
        LocalDate.of(1970, 1, 1).atStartOfDay());
    CalibrationDataItem.Spec spec2 = new CalibrationDataItem.Spec("Key", "Curve Name", "Product Name", "Maturity");

    // Act and Assert
    assertNotEquals(calibrationDataItem,
        new CalibrationDataItem(spec2, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));
  }

  /**
   * Method under test: {@link CalibrationDataItem#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    CalibrationDataItem.Spec spec = mock(CalibrationDataItem.Spec.class);
    CalibrationDataItem calibrationDataItem = new CalibrationDataItem(spec, 10.0d,
        LocalDate.of(1970, 1, 1).atStartOfDay());
    CalibrationDataItem.Spec spec2 = new CalibrationDataItem.Spec("Key", "Curve Name", "Product Name", "Maturity");

    // Act and Assert
    assertNotEquals(calibrationDataItem,
        new CalibrationDataItem(spec2, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));
  }

  /**
   * Method under test: {@link CalibrationDataItem#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("Key", "Curve Name", "Product Name", "Maturity");

    CalibrationDataItem calibrationDataItem = new CalibrationDataItem(spec, null,
        LocalDate.of(1970, 1, 1).atStartOfDay());
    CalibrationDataItem.Spec spec2 = new CalibrationDataItem.Spec("Key", "Curve Name", "Product Name", "Maturity");

    // Act and Assert
    assertNotEquals(calibrationDataItem,
        new CalibrationDataItem(spec2, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));
  }

  /**
   * Method under test: {@link CalibrationDataItem#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("Key", "Curve Name", "Product Name", "Maturity");

    CalibrationDataItem calibrationDataItem = new CalibrationDataItem(spec, 10.0d, LocalDate.now().atStartOfDay());
    CalibrationDataItem.Spec spec2 = new CalibrationDataItem.Spec("Key", "Curve Name", "Product Name", "Maturity");

    // Act and Assert
    assertNotEquals(calibrationDataItem,
        new CalibrationDataItem(spec2, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));
  }

  /**
   * Method under test: {@link CalibrationDataItem#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("Key", "Curve Name", "Product Name", "Maturity");

    // Act and Assert
    assertNotEquals(new CalibrationDataItem(spec, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay()), null);
  }

  /**
   * Method under test: {@link CalibrationDataItem#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("Key", "Curve Name", "Product Name", "Maturity");

    // Act and Assert
    assertNotEquals(new CalibrationDataItem(spec, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay()),
        "Different type to CalibrationDataItem");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link CalibrationDataItem#CalibrationDataItem(CalibrationDataItem.Spec, Double, LocalDateTime)}
   *   <li>{@link CalibrationDataItem#getDateTime()}
   *   <li>{@link CalibrationDataItem#getQuote()}
   *   <li>{@link CalibrationDataItem#getSpec()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("Key", "Curve Name", "Product Name", "Maturity");

    LocalDateTime dateTime = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act
    CalibrationDataItem actualCalibrationDataItem = new CalibrationDataItem(spec, 10.0d, dateTime);
    LocalDateTime actualDateTime = actualCalibrationDataItem.getDateTime();
    Double actualQuote = actualCalibrationDataItem.getQuote();
    CalibrationDataItem.Spec actualSpec = actualCalibrationDataItem.getSpec();

    // Assert
    assertEquals(10.0d, actualQuote.doubleValue());
    assertSame(spec, actualSpec);
    assertSame(dateTime, actualDateTime);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CalibrationDataItem.Spec#equals(Object)}
   *   <li>{@link CalibrationDataItem.Spec#hashCode()}
   * </ul>
   */
  @Test
  void testSpecEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("Key", "Curve Name", "Product Name", "Maturity");
    CalibrationDataItem.Spec spec2 = new CalibrationDataItem.Spec("Key", "Curve Name", "Product Name", "Maturity");

    // Act and Assert
    assertEquals(spec, spec2);
    int expectedHashCodeResult = spec.hashCode();
    assertEquals(expectedHashCodeResult, spec2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CalibrationDataItem.Spec#equals(Object)}
   *   <li>{@link CalibrationDataItem.Spec#hashCode()}
   * </ul>
   */
  @Test
  void testSpecEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("Key", "Curve Name", "Product Name", "Maturity");

    // Act and Assert
    assertEquals(spec, spec);
    int expectedHashCodeResult = spec.hashCode();
    assertEquals(expectedHashCodeResult, spec.hashCode());
  }

  /**
   * Method under test: {@link CalibrationDataItem.Spec#equals(Object)}
   */
  @Test
  void testSpecEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec(null, "Curve Name", "Product Name", "Maturity");

    // Act and Assert
    assertNotEquals(spec, new CalibrationDataItem.Spec("Key", "Curve Name", "Product Name", "Maturity"));
  }

  /**
   * Method under test: {@link CalibrationDataItem.Spec#equals(Object)}
   */
  @Test
  void testSpecEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("Key", null, "Product Name", "Maturity");

    // Act and Assert
    assertNotEquals(spec, new CalibrationDataItem.Spec("Key", "Curve Name", "Product Name", "Maturity"));
  }

  /**
   * Method under test: {@link CalibrationDataItem.Spec#equals(Object)}
   */
  @Test
  void testSpecEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("Key", "Curve Name", null, "Maturity");

    // Act and Assert
    assertNotEquals(spec, new CalibrationDataItem.Spec("Key", "Curve Name", "Product Name", "Maturity"));
  }

  /**
   * Method under test: {@link CalibrationDataItem.Spec#equals(Object)}
   */
  @Test
  void testSpecEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("Key", "Curve Name", "Product Name", null);

    // Act and Assert
    assertNotEquals(spec, new CalibrationDataItem.Spec("Key", "Curve Name", "Product Name", "Maturity"));
  }

  /**
   * Method under test: {@link CalibrationDataItem.Spec#equals(Object)}
   */
  @Test
  void testSpecEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new CalibrationDataItem.Spec("Key", "Curve Name", "Product Name", "Maturity"), null);
  }

  /**
   * Method under test: {@link CalibrationDataItem.Spec#equals(Object)}
   */
  @Test
  void testSpecEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new CalibrationDataItem.Spec("Key", "Curve Name", "Product Name", "Maturity"),
        "Different type to Spec");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CalibrationDataItem.Spec#Spec(String, String, String, String)}
   *   <li>{@link CalibrationDataItem.Spec#getCurveName()}
   *   <li>{@link CalibrationDataItem.Spec#getKey()}
   *   <li>{@link CalibrationDataItem.Spec#getMaturity()}
   *   <li>{@link CalibrationDataItem.Spec#getProductName()}
   * </ul>
   */
  @Test
  void testSpecGettersAndSetters() {
    // Arrange and Act
    CalibrationDataItem.Spec actualSpec = new CalibrationDataItem.Spec("Key", "Curve Name", "Product Name", "Maturity");
    String actualCurveName = actualSpec.getCurveName();
    String actualKey = actualSpec.getKey();
    String actualMaturity = actualSpec.getMaturity();

    // Assert
    assertEquals("Curve Name", actualCurveName);
    assertEquals("Key", actualKey);
    assertEquals("Maturity", actualMaturity);
    assertEquals("Product Name", actualSpec.getProductName());
  }
}
