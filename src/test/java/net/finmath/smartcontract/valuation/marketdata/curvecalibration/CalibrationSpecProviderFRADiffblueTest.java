package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class CalibrationSpecProviderFRADiffblueTest {
  /**
   * Test {@link CalibrationSpecProviderFRA#getCalibrationSpec(CalibrationContext)}.
   * <ul>
   *   <li>Then return Symbol is {@code EUR-4242}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CalibrationSpecProviderFRA#getCalibrationSpec(CalibrationContext)}
   */
  @Test
  @DisplayName("Test getCalibrationSpec(CalibrationContext); then return Symbol is 'EUR-4242'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "net.finmath.marketdata.calibration.CalibratedCurves.CalibrationSpec CalibrationSpecProviderFRA.getCalibrationSpec(CalibrationContext)"})
  void testGetCalibrationSpec_thenReturnSymbolIsEur4242() {
    // Arrange
    CalibrationSpecProviderFRA calibrationSpecProviderFRA = new CalibrationSpecProviderFRA("42", "42", 10.0d);

    // Act and Assert
    assertEquals("EUR-4242",
        calibrationSpecProviderFRA
            .getCalibrationSpec(new CalibrationContextImpl(LocalDate.of(1970, 1, 1).atStartOfDay(), 10.0d))
            .getSymbol());
  }
}
