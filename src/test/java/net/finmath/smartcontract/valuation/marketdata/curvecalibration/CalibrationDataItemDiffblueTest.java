package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationDataItem.Spec;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class CalibrationDataItemDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link CalibrationDataItem#CalibrationDataItem(Spec, Double, LocalDateTime)}
   *   <li>{@link CalibrationDataItem#getDateTime()}
   *   <li>{@link CalibrationDataItem#getQuote()}
   *   <li>{@link CalibrationDataItem#getSpec()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void CalibrationDataItem.<init>(Spec, Double, LocalDateTime)",
    "LocalDateTime CalibrationDataItem.getDateTime()",
    "Double CalibrationDataItem.getQuote()",
    "Spec CalibrationDataItem.getSpec()"
  })
  void testGettersAndSetters() {
    // Arrange
    Spec spec = new Spec("Key", "Curve Name", "Product Name", "Maturity");
    LocalDateTime dateTime = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act
    CalibrationDataItem actualCalibrationDataItem = new CalibrationDataItem(spec, 10.0d, dateTime);
    LocalDateTime actualDateTime = actualCalibrationDataItem.getDateTime();
    Double actualQuote = actualCalibrationDataItem.getQuote();
    Spec actualSpec = actualCalibrationDataItem.getSpec();

    // Assert
    assertEquals(10.0d, actualQuote.doubleValue());
    assertSame(spec, actualSpec);
    assertSame(dateTime, actualDateTime);
  }

  /**
   * Test {@link CalibrationDataItem#getClonedScaled(double)}.
   *
   * <p>Method under test: {@link CalibrationDataItem#getClonedScaled(double)}
   */
  @Test
  @DisplayName("Test getClonedScaled(double)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"CalibrationDataItem CalibrationDataItem.getClonedScaled(double)"})
  void testGetClonedScaled() {
    // Arrange
    Spec spec = new Spec("Key", "Curve Name", "Product Name", "Maturity");

    LocalDate ofResult = LocalDate.of(1970, 1, 1);

    // Act
    CalibrationDataItem actualClonedScaled =
        new CalibrationDataItem(spec, 10.0d, ofResult.atStartOfDay()).getClonedScaled(10.0d);

    // Assert
    LocalDate date = actualClonedScaled.getDate();
    assertEquals("1970-01-01", date.toString());
    assertEquals("1970-01-01", actualClonedScaled.getDateString());
    assertEquals("Curve Name", actualClonedScaled.getCurveName());
    assertEquals("Maturity", actualClonedScaled.getMaturity());
    assertEquals("Product Name", actualClonedScaled.getProductName());
    assertEquals(1.0d, actualClonedScaled.getQuote().doubleValue());
    assertSame(spec, actualClonedScaled.getSpec());
    assertSame(ofResult, actualClonedScaled.getDateTime().toLocalDate());
    assertSame(ofResult, date);
  }

  /**
   * Test {@link CalibrationDataItem#getClonedShifted(double)}.
   *
   * <p>Method under test: {@link CalibrationDataItem#getClonedShifted(double)}
   */
  @Test
  @DisplayName("Test getClonedShifted(double)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"CalibrationDataItem CalibrationDataItem.getClonedShifted(double)"})
  void testGetClonedShifted() {
    // Arrange
    Spec spec = new Spec("Key", "Curve Name", "Product Name", "Maturity");

    LocalDate ofResult = LocalDate.of(1970, 1, 1);

    // Act
    CalibrationDataItem actualClonedShifted =
        new CalibrationDataItem(spec, 10.0d, ofResult.atStartOfDay()).getClonedShifted(10.0d);

    // Assert
    LocalDate date = actualClonedShifted.getDate();
    assertEquals("1970-01-01", date.toString());
    assertEquals("1970-01-01", actualClonedShifted.getDateString());
    assertEquals("Curve Name", actualClonedShifted.getCurveName());
    assertEquals("Maturity", actualClonedShifted.getMaturity());
    assertEquals("Product Name", actualClonedShifted.getProductName());
    assertEquals(20.0d, actualClonedShifted.getQuote().doubleValue());
    assertSame(spec, actualClonedShifted.getSpec());
    assertSame(ofResult, actualClonedShifted.getDateTime().toLocalDate());
    assertSame(ofResult, date);
  }

  /**
   * Test {@link CalibrationDataItem#getCurveName()}.
   *
   * <p>Method under test: {@link CalibrationDataItem#getCurveName()}
   */
  @Test
  @DisplayName("Test getCurveName()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String CalibrationDataItem.getCurveName()"})
  void testGetCurveName() {
    // Arrange
    Spec spec = new Spec("Key", "Curve Name", "Product Name", "Maturity");

    // Act and Assert
    assertEquals(
        "Curve Name",
        new CalibrationDataItem(spec, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay())
            .getCurveName());
  }

  /**
   * Test {@link CalibrationDataItem#getProductName()}.
   *
   * <p>Method under test: {@link CalibrationDataItem#getProductName()}
   */
  @Test
  @DisplayName("Test getProductName()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String CalibrationDataItem.getProductName()"})
  void testGetProductName() {
    // Arrange
    Spec spec = new Spec("Key", "Curve Name", "Product Name", "Maturity");

    // Act and Assert
    assertEquals(
        "Product Name",
        new CalibrationDataItem(spec, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay())
            .getProductName());
  }

  /**
   * Test {@link CalibrationDataItem#getMaturity()}.
   *
   * <p>Method under test: {@link CalibrationDataItem#getMaturity()}
   */
  @Test
  @DisplayName("Test getMaturity()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String CalibrationDataItem.getMaturity()"})
  void testGetMaturity() {
    // Arrange
    Spec spec = new Spec("Key", "Curve Name", "Product Name", "Maturity");

    // Act and Assert
    assertEquals(
        "Maturity",
        new CalibrationDataItem(spec, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay())
            .getMaturity());
  }

  /**
   * Test {@link CalibrationDataItem#getDateString()}.
   *
   * <p>Method under test: {@link CalibrationDataItem#getDateString()}
   */
  @Test
  @DisplayName("Test getDateString()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String CalibrationDataItem.getDateString()"})
  void testGetDateString() {
    // Arrange
    Spec spec = new Spec("Key", "Curve Name", "Product Name", "Maturity");

    // Act and Assert
    assertEquals(
        "1970-01-01",
        new CalibrationDataItem(spec, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay())
            .getDateString());
  }

  /**
   * Test {@link CalibrationDataItem#getDate()}.
   *
   * <p>Method under test: {@link CalibrationDataItem#getDate()}
   */
  @Test
  @DisplayName("Test getDate()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"LocalDate CalibrationDataItem.getDate()"})
  void testGetDate() {
    // Arrange
    Spec spec = new Spec("Key", "Curve Name", "Product Name", "Maturity");

    LocalDate ofResult = LocalDate.of(1970, 1, 1);

    // Act
    LocalDate actualDate = new CalibrationDataItem(spec, 10.0d, ofResult.atStartOfDay()).getDate();

    // Assert
    assertEquals("1970-01-01", actualDate.toString());
    assertSame(ofResult, actualDate);
  }

  /**
   * Test {@link CalibrationDataItem#equals(Object)}, and {@link CalibrationDataItem#hashCode()}.
   *
   * <ul>
   *   <li>When other is equal.
   *   <li>Then return equal.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link CalibrationDataItem#equals(Object)}
   *   <li>{@link CalibrationDataItem#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean CalibrationDataItem.equals(Object)",
    "int CalibrationDataItem.hashCode()"
  })
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    Spec spec = new Spec("Key", "Curve Name", "Product Name", "Maturity");
    CalibrationDataItem calibrationDataItem =
        new CalibrationDataItem(spec, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay());
    Spec spec2 = new Spec("Key", "Curve Name", "Product Name", "Maturity");
    CalibrationDataItem calibrationDataItem2 =
        new CalibrationDataItem(spec2, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay());

    // Act and Assert
    assertEquals(calibrationDataItem, calibrationDataItem2);
    assertEquals(calibrationDataItem.hashCode(), calibrationDataItem2.hashCode());
  }

  /**
   * Test {@link CalibrationDataItem#equals(Object)}, and {@link CalibrationDataItem#hashCode()}.
   *
   * <ul>
   *   <li>When other is same.
   *   <li>Then return equal.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link CalibrationDataItem#equals(Object)}
   *   <li>{@link CalibrationDataItem#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean CalibrationDataItem.equals(Object)",
    "int CalibrationDataItem.hashCode()"
  })
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    Spec spec = new Spec("Key", "Curve Name", "Product Name", "Maturity");
    CalibrationDataItem calibrationDataItem =
        new CalibrationDataItem(spec, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay());

    // Act and Assert
    assertEquals(calibrationDataItem, calibrationDataItem);
    int expectedHashCodeResult = calibrationDataItem.hashCode();
    assertEquals(expectedHashCodeResult, calibrationDataItem.hashCode());
  }

  /**
   * Test {@link CalibrationDataItem#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationDataItem#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean CalibrationDataItem.equals(Object)",
    "int CalibrationDataItem.hashCode()"
  })
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    Spec spec = new Spec(null, "Curve Name", "Product Name", "Maturity");
    CalibrationDataItem calibrationDataItem =
        new CalibrationDataItem(spec, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay());
    Spec spec2 = new Spec("Key", "Curve Name", "Product Name", "Maturity");

    // Act and Assert
    assertNotEquals(
        calibrationDataItem,
        new CalibrationDataItem(spec2, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));
  }

  /**
   * Test {@link CalibrationDataItem#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationDataItem#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean CalibrationDataItem.equals(Object)",
    "int CalibrationDataItem.hashCode()"
  })
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    Spec spec = new Spec("Key", "Curve Name", "Product Name", "Maturity");
    CalibrationDataItem calibrationDataItem =
        new CalibrationDataItem(spec, null, LocalDate.of(1970, 1, 1).atStartOfDay());
    Spec spec2 = new Spec("Key", "Curve Name", "Product Name", "Maturity");

    // Act and Assert
    assertNotEquals(
        calibrationDataItem,
        new CalibrationDataItem(spec2, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));
  }

  /**
   * Test {@link CalibrationDataItem#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationDataItem#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean CalibrationDataItem.equals(Object)",
    "int CalibrationDataItem.hashCode()"
  })
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    Spec spec = new Spec("Key", "Curve Name", "Product Name", "Maturity");
    CalibrationDataItem calibrationDataItem =
        new CalibrationDataItem(spec, 10.0d, LocalDate.now().atStartOfDay());
    Spec spec2 = new Spec("Key", "Curve Name", "Product Name", "Maturity");

    // Act and Assert
    assertNotEquals(
        calibrationDataItem,
        new CalibrationDataItem(spec2, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));
  }

  /**
   * Test {@link CalibrationDataItem#equals(Object)}.
   *
   * <ul>
   *   <li>When other is {@code null}.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationDataItem#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean CalibrationDataItem.equals(Object)",
    "int CalibrationDataItem.hashCode()"
  })
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    Spec spec = new Spec("Key", "Curve Name", "Product Name", "Maturity");

    // Act and Assert
    assertNotEquals(
        new CalibrationDataItem(spec, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay()), null);
  }

  /**
   * Test {@link CalibrationDataItem#equals(Object)}.
   *
   * <ul>
   *   <li>When other is wrong type.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationDataItem#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean CalibrationDataItem.equals(Object)",
    "int CalibrationDataItem.hashCode()"
  })
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    Spec spec = new Spec("Key", "Curve Name", "Product Name", "Maturity");

    // Act and Assert
    assertNotEquals(
        new CalibrationDataItem(spec, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay()),
        "Different type to CalibrationDataItem");
  }

  /**
   * Test Spec {@link Spec#equals(Object)}, and {@link Spec#hashCode()}.
   *
   * <ul>
   *   <li>When other is equal.
   *   <li>Then return equal.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link Spec#equals(Object)}
   *   <li>{@link Spec#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test Spec equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Spec.equals(Object)", "int Spec.hashCode()"})
  void testSpecEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    Spec spec = new Spec("Key", "Curve Name", "Product Name", "Maturity");
    Spec spec2 = new Spec("Key", "Curve Name", "Product Name", "Maturity");

    // Act and Assert
    assertEquals(spec, spec2);
    assertEquals(spec.hashCode(), spec2.hashCode());
  }

  /**
   * Test Spec {@link Spec#equals(Object)}, and {@link Spec#hashCode()}.
   *
   * <ul>
   *   <li>When other is same.
   *   <li>Then return equal.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link Spec#equals(Object)}
   *   <li>{@link Spec#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test Spec equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Spec.equals(Object)", "int Spec.hashCode()"})
  void testSpecEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    Spec spec = new Spec("Key", "Curve Name", "Product Name", "Maturity");

    // Act and Assert
    assertEquals(spec, spec);
    int expectedHashCodeResult = spec.hashCode();
    assertEquals(expectedHashCodeResult, spec.hashCode());
  }

  /**
   * Test Spec {@link Spec#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link Spec#equals(Object)}
   */
  @Test
  @DisplayName("Test Spec equals(Object); when other is different; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Spec.equals(Object)", "int Spec.hashCode()"})
  void testSpecEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    Spec spec = new Spec(null, "Curve Name", "Product Name", "Maturity");

    // Act and Assert
    assertNotEquals(spec, new Spec("Key", "Curve Name", "Product Name", "Maturity"));
  }

  /**
   * Test Spec {@link Spec#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link Spec#equals(Object)}
   */
  @Test
  @DisplayName("Test Spec equals(Object); when other is different; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Spec.equals(Object)", "int Spec.hashCode()"})
  void testSpecEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    Spec spec = new Spec("Key", null, "Product Name", "Maturity");

    // Act and Assert
    assertNotEquals(spec, new Spec("Key", "Curve Name", "Product Name", "Maturity"));
  }

  /**
   * Test Spec {@link Spec#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link Spec#equals(Object)}
   */
  @Test
  @DisplayName("Test Spec equals(Object); when other is different; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Spec.equals(Object)", "int Spec.hashCode()"})
  void testSpecEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    Spec spec = new Spec("Key", "Curve Name", null, "Maturity");

    // Act and Assert
    assertNotEquals(spec, new Spec("Key", "Curve Name", "Product Name", "Maturity"));
  }

  /**
   * Test Spec {@link Spec#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link Spec#equals(Object)}
   */
  @Test
  @DisplayName("Test Spec equals(Object); when other is different; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Spec.equals(Object)", "int Spec.hashCode()"})
  void testSpecEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    Spec spec = new Spec("Key", "Curve Name", "Product Name", null);

    // Act and Assert
    assertNotEquals(spec, new Spec("Key", "Curve Name", "Product Name", "Maturity"));
  }

  /**
   * Test Spec {@link Spec#equals(Object)}.
   *
   * <ul>
   *   <li>When other is {@code null}.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link Spec#equals(Object)}
   */
  @Test
  @DisplayName("Test Spec equals(Object); when other is 'null'; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Spec.equals(Object)", "int Spec.hashCode()"})
  void testSpecEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new Spec("Key", "Curve Name", "Product Name", "Maturity"), null);
  }

  /**
   * Test Spec {@link Spec#equals(Object)}.
   *
   * <ul>
   *   <li>When other is wrong type.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link Spec#equals(Object)}
   */
  @Test
  @DisplayName("Test Spec equals(Object); when other is wrong type; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Spec.equals(Object)", "int Spec.hashCode()"})
  void testSpecEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(
        new Spec("Key", "Curve Name", "Product Name", "Maturity"), "Different type to Spec");
  }

  /**
   * Test Spec getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link Spec#Spec(String, String, String, String)}
   *   <li>{@link Spec#getCurveName()}
   *   <li>{@link Spec#getKey()}
   *   <li>{@link Spec#getMaturity()}
   *   <li>{@link Spec#getProductName()}
   * </ul>
   */
  @Test
  @DisplayName("Test Spec getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void Spec.<init>(String, String, String, String)",
    "String Spec.getCurveName()",
    "String Spec.getKey()",
    "String Spec.getMaturity()",
    "String Spec.getProductName()"
  })
  void testSpecGettersAndSetters() {
    // Arrange and Act
    Spec actualSpec = new Spec("Key", "Curve Name", "Product Name", "Maturity");
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
