package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class CalibrationSpecProviderDepositDiffblueTest {
  /**
   * Test {@link CalibrationSpecProviderDeposit#getCalibrationSpec(CalibrationContext)}.
   * <p>
   * Method under test: {@link CalibrationSpecProviderDeposit#getCalibrationSpec(CalibrationContext)}
   */
  @Test
  @DisplayName("Test getCalibrationSpec(CalibrationContext)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "net.finmath.marketdata.calibration.CalibratedCurves.CalibrationSpec CalibrationSpecProviderDeposit.getCalibrationSpec(CalibrationContext)"})
  void testGetCalibrationSpec() {
    // Arrange
    CalibrationSpecProviderDeposit calibrationSpecProviderDeposit = new CalibrationSpecProviderDeposit("Tenor Label",
        "42", 10.0d);

    // Act and Assert
    assertEquals("EUR-Tenor Label42",
        calibrationSpecProviderDeposit
            .getCalibrationSpec(new CalibrationContextImpl(LocalDate.ofYearDay(2, 2).atStartOfDay(), 10.0d))
            .getSymbol());
  }

  /**
   * Test {@link CalibrationSpecProviderDeposit#getCalibrationSpec(CalibrationContext)}.
   * <p>
   * Method under test: {@link CalibrationSpecProviderDeposit#getCalibrationSpec(CalibrationContext)}
   */
  @Test
  @DisplayName("Test getCalibrationSpec(CalibrationContext)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "net.finmath.marketdata.calibration.CalibratedCurves.CalibrationSpec CalibrationSpecProviderDeposit.getCalibrationSpec(CalibrationContext)"})
  void testGetCalibrationSpec2() {
    // Arrange
    CalibrationSpecProviderDeposit calibrationSpecProviderDeposit = new CalibrationSpecProviderDeposit("Tenor Label",
        "42", 10.0d);

    // Act and Assert
    assertEquals("EUR-Tenor Label42",
        calibrationSpecProviderDeposit
            .getCalibrationSpec(new CalibrationContextImpl(LocalDate.ofYearDay(2, 100).atStartOfDay(), 10.0d))
            .getSymbol());
  }

  /**
   * Test {@link CalibrationSpecProviderDeposit#getCalibrationSpec(CalibrationContext)}.
   * <p>
   * Method under test: {@link CalibrationSpecProviderDeposit#getCalibrationSpec(CalibrationContext)}
   */
  @Test
  @DisplayName("Test getCalibrationSpec(CalibrationContext)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "net.finmath.marketdata.calibration.CalibratedCurves.CalibrationSpec CalibrationSpecProviderDeposit.getCalibrationSpec(CalibrationContext)"})
  void testGetCalibrationSpec3() {
    // Arrange
    CalibrationSpecProviderDeposit calibrationSpecProviderDeposit = new CalibrationSpecProviderDeposit("Tenor Label",
        "42", 10.0d);

    // Act and Assert
    assertEquals("EUR-Tenor Label42",
        calibrationSpecProviderDeposit
            .getCalibrationSpec(new CalibrationContextImpl(LocalDate.ofYearDay(19, 100).atStartOfDay(), 10.0d))
            .getSymbol());
  }

  /**
   * Test {@link CalibrationSpecProviderDeposit#getCalibrationSpec(CalibrationContext)}.
   * <ul>
   *   <li>When {@link LocalDate} with {@code 1970} and one and one atStartOfDay.</li>
   * </ul>
   * <p>
   * Method under test: {@link CalibrationSpecProviderDeposit#getCalibrationSpec(CalibrationContext)}
   */
  @Test
  @DisplayName("Test getCalibrationSpec(CalibrationContext); when LocalDate with '1970' and one and one atStartOfDay")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "net.finmath.marketdata.calibration.CalibratedCurves.CalibrationSpec CalibrationSpecProviderDeposit.getCalibrationSpec(CalibrationContext)"})
  void testGetCalibrationSpec_whenLocalDateWith1970AndOneAndOneAtStartOfDay() {
    // Arrange
    CalibrationSpecProviderDeposit calibrationSpecProviderDeposit = new CalibrationSpecProviderDeposit("Tenor Label",
        "42", 10.0d);

    // Act and Assert
    assertEquals("EUR-Tenor Label42",
        calibrationSpecProviderDeposit
            .getCalibrationSpec(new CalibrationContextImpl(LocalDate.of(1970, 1, 1).atStartOfDay(), 10.0d))
            .getSymbol());
  }
}
