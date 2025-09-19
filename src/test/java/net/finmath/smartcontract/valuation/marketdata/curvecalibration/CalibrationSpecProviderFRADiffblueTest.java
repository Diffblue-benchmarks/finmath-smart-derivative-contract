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

class CalibrationSpecProviderFRADiffblueTest {
  /**
   * Test {@link CalibrationSpecProviderFRA#CalibrationSpecProviderFRA(String, String, double)}.
   *
   * <p>Method under test: {@link CalibrationSpecProviderFRA#CalibrationSpecProviderFRA(String,
   * String, double)}
   */
  @Test
  @DisplayName("Test new CalibrationSpecProviderFRA(String, String, double)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CalibrationSpecProviderFRA.<init>(String, String, double)"})
  void testNewCalibrationSpecProviderFRA() {
    // Arrange and Act
    CalibrationSpecProviderFRA actualCalibrationSpecProviderFRA =
        new CalibrationSpecProviderFRA("42", "42", 10.0d);
    CalibrationSpec actualCalibrationSpec =
        actualCalibrationSpecProviderFRA.getCalibrationSpec(
            new CalibrationContextImpl(LocalDate.of(1970, 1, 1).atStartOfDay(), 10.0d));

    // Assert
    assertEquals("EUR-4242", actualCalibrationSpec.getSymbol());
  }

  /**
   * Test {@link CalibrationSpecProviderFRA#getCalibrationSpec(CalibrationContext)}.
   *
   * <ul>
   *   <li>Then return Symbol is {@code EUR-4242}.
   * </ul>
   *
   * <p>Method under test: {@link CalibrationSpecProviderFRA#getCalibrationSpec(CalibrationContext)}
   */
  @Test
  @DisplayName("Test getCalibrationSpec(CalibrationContext); then return Symbol is 'EUR-4242'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CalibrationSpec CalibrationSpecProviderFRA.getCalibrationSpec(CalibrationContext)"
  })
  void testGetCalibrationSpec_thenReturnSymbolIsEur4242() {
    // Arrange
    CalibrationSpecProviderFRA calibrationSpecProviderFRA =
        new CalibrationSpecProviderFRA("42", "42", 10.0d);

    // Act
    CalibrationSpec actualCalibrationSpec =
        calibrationSpecProviderFRA.getCalibrationSpec(
            new CalibrationContextImpl(LocalDate.of(1970, 1, 1).atStartOfDay(), 10.0d));

    // Assert
    assertEquals("EUR-4242", actualCalibrationSpec.getSymbol());
  }
}
