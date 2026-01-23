package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import net.finmath.marketdata.calibration.CalibratedCurves;
import net.finmath.marketdata.calibration.CalibratedCurves.CalibrationSpec;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class CalibrationSpecProviderDepositDiffblueTest {
  /**
   * Test {@link CalibrationSpecProviderDeposit#getCalibrationSpec(CalibrationContext)}.
   *
   * <p>Method under test: {@link
   * CalibrationSpecProviderDeposit#getCalibrationSpec(CalibrationContext)}
   */
  @Test
  @DisplayName("Test getCalibrationSpec(CalibrationContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CalibrationSpec CalibrationSpecProviderDeposit.getCalibrationSpec(CalibrationContext)"
  })
  void testGetCalibrationSpec() {
    // Arrange
    CalibrationSpecProviderDeposit calibrationSpecProviderDeposit =
        new CalibrationSpecProviderDeposit("Tenor Label", "42", 10.0d);

    // Act
    CalibrationSpec actualCalibrationSpec =
        calibrationSpecProviderDeposit.getCalibrationSpec(
            new CalibrationContextImpl(LocalDate.now().atStartOfDay(), 10.0d));

    // Assert
    assertEquals("EUR-Tenor Label42", actualCalibrationSpec.getSymbol());
  }

  /**
   * Test {@link CalibrationSpecProviderDeposit#getCalibrationSpec(CalibrationContext)}.
   *
   * <p>Method under test: {@link
   * CalibrationSpecProviderDeposit#getCalibrationSpec(CalibrationContext)}
   */
  @Test
  @DisplayName("Test getCalibrationSpec(CalibrationContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CalibrationSpec CalibrationSpecProviderDeposit.getCalibrationSpec(CalibrationContext)"
  })
  void testGetCalibrationSpec2() {
    // Arrange
    CalibrationSpecProviderDeposit calibrationSpecProviderDeposit =
        new CalibrationSpecProviderDeposit("Tenor Label", "42", 10.0d);

    LocalDate ofYearDayResult = LocalDate.ofYearDay(2, 2);

    // Act
    CalibrationSpec actualCalibrationSpec =
        calibrationSpecProviderDeposit.getCalibrationSpec(
            new CalibrationContextImpl(ofYearDayResult.atStartOfDay(), 10.0d));

    // Assert
    assertEquals("EUR-Tenor Label42", actualCalibrationSpec.getSymbol());
  }

  /**
   * Test {@link CalibrationSpecProviderDeposit#getCalibrationSpec(CalibrationContext)}.
   *
   * <p>Method under test: {@link
   * CalibrationSpecProviderDeposit#getCalibrationSpec(CalibrationContext)}
   */
  @Test
  @DisplayName("Test getCalibrationSpec(CalibrationContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CalibrationSpec CalibrationSpecProviderDeposit.getCalibrationSpec(CalibrationContext)"
  })
  void testGetCalibrationSpec3() {
    // Arrange
    CalibrationSpecProviderDeposit calibrationSpecProviderDeposit =
        new CalibrationSpecProviderDeposit("Tenor Label", "42", 10.0d);

    LocalDate ofYearDayResult = LocalDate.ofYearDay(2, 100);

    // Act
    CalibrationSpec actualCalibrationSpec =
        calibrationSpecProviderDeposit.getCalibrationSpec(
            new CalibrationContextImpl(ofYearDayResult.atStartOfDay(), 10.0d));

    // Assert
    assertEquals("EUR-Tenor Label42", actualCalibrationSpec.getSymbol());
  }

  /**
   * Test {@link CalibrationSpecProviderDeposit#getCalibrationSpec(CalibrationContext)}.
   *
   * <p>Method under test: {@link
   * CalibrationSpecProviderDeposit#getCalibrationSpec(CalibrationContext)}
   */
  @Test
  @DisplayName("Test getCalibrationSpec(CalibrationContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CalibrationSpec CalibrationSpecProviderDeposit.getCalibrationSpec(CalibrationContext)"
  })
  void testGetCalibrationSpec4() {
    // Arrange
    CalibrationSpecProviderDeposit calibrationSpecProviderDeposit =
        new CalibrationSpecProviderDeposit("Tenor Label", "42", 10.0d);

    LocalDate ofYearDayResult = LocalDate.ofYearDay(19, 100);

    // Act
    CalibrationSpec actualCalibrationSpec =
        calibrationSpecProviderDeposit.getCalibrationSpec(
            new CalibrationContextImpl(ofYearDayResult.atStartOfDay(), 10.0d));

    // Assert
    assertEquals("EUR-Tenor Label42", actualCalibrationSpec.getSymbol());
  }

  /**
   * Test {@link CalibrationSpecProviderDeposit#getCalibrationSpec(CalibrationContext)}.
   *
   * <p>Method under test: {@link
   * CalibrationSpecProviderDeposit#getCalibrationSpec(CalibrationContext)}
   */
  @Test
  @DisplayName("Test getCalibrationSpec(CalibrationContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CalibrationSpec CalibrationSpecProviderDeposit.getCalibrationSpec(CalibrationContext)"
  })
  void testGetCalibrationSpec5() {
    // Arrange
    CalibrationSpecProviderDeposit calibrationSpecProviderDeposit =
        new CalibrationSpecProviderDeposit("Tenor Label", "42", 10.0d);

    LocalDate ofYearDayResult = LocalDate.ofYearDay(19, Integer.SIZE);

    // Act
    CalibrationSpec actualCalibrationSpec =
        calibrationSpecProviderDeposit.getCalibrationSpec(
            new CalibrationContextImpl(ofYearDayResult.atStartOfDay(), 10.0d));

    // Assert
    assertEquals("EUR-Tenor Label42", actualCalibrationSpec.getSymbol());
  }

  /**
   * Test {@link CalibrationSpecProviderDeposit#getCalibrationSpec(CalibrationContext)}.
   *
   * <ul>
   *   <li>When {@link LocalDate} with {@code 1970} and one and one atStartOfDay.
   * </ul>
   *
   * <p>Method under test: {@link
   * CalibrationSpecProviderDeposit#getCalibrationSpec(CalibrationContext)}
   */
  @Test
  @DisplayName(
      "Test getCalibrationSpec(CalibrationContext); when LocalDate with '1970' and one and one atStartOfDay")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CalibrationSpec CalibrationSpecProviderDeposit.getCalibrationSpec(CalibrationContext)"
  })
  void testGetCalibrationSpec_whenLocalDateWith1970AndOneAndOneAtStartOfDay() {
    // Arrange
    CalibrationSpecProviderDeposit calibrationSpecProviderDeposit =
        new CalibrationSpecProviderDeposit("Tenor Label", "42", 10.0d);

    // Act
    CalibrationSpec actualCalibrationSpec =
        calibrationSpecProviderDeposit.getCalibrationSpec(
            new CalibrationContextImpl(LocalDate.of(1970, 1, 1).atStartOfDay(), 10.0d));

    // Assert
    assertEquals("EUR-Tenor Label42", actualCalibrationSpec.getSymbol());
  }
}
