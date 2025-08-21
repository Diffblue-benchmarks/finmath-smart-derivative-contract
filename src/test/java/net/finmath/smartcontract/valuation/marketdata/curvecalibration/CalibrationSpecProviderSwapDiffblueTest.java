package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import net.finmath.marketdata.calibration.CalibratedCurves;
import net.finmath.marketdata.calibration.CalibratedCurves.CalibrationSpec;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class CalibrationSpecProviderSwapDiffblueTest {
  /**
   * Test {@link CalibrationSpecProviderSwap#getCalibrationSpec(CalibrationContext)}.
   *
   * <ul>
   *   <li>Given {@link LocalDate} with {@code 1970} and one and one.
   * </ul>
   *
   * <p>Method under test: {@link
   * CalibrationSpecProviderSwap#getCalibrationSpec(CalibrationContext)}
   */
  @Test
  @DisplayName(
      "Test getCalibrationSpec(CalibrationContext); given LocalDate with '1970' and one and one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CalibrationSpec CalibrationSpecProviderSwap.getCalibrationSpec(CalibrationContext)"
  })
  void testGetCalibrationSpec_givenLocalDateWith1970AndOneAndOne() {
    // Arrange
    CalibrationSpecProviderSwap calibrationSpecProviderSwap =
        new CalibrationSpecProviderSwap("Tenor Label", "annual", "42", 10.0d);
    CalibrationContextImpl ctx = mock(CalibrationContextImpl.class);
    when(ctx.getReferenceDate()).thenReturn(LocalDate.of(1970, 1, 1));

    // Act
    CalibrationSpec actualCalibrationSpec = calibrationSpecProviderSwap.getCalibrationSpec(ctx);

    // Assert
    verify(ctx, atLeast(1)).getReferenceDate();
    assertEquals("EUR-Tenor Label42", actualCalibrationSpec.getSymbol());
  }

  /**
   * Test {@link CalibrationSpecProviderSwap#getCalibrationSpec(CalibrationContext)}.
   *
   * <ul>
   *   <li>Then return Symbol is {@code EUR-Tenor Label42}.
   * </ul>
   *
   * <p>Method under test: {@link
   * CalibrationSpecProviderSwap#getCalibrationSpec(CalibrationContext)}
   */
  @Test
  @DisplayName(
      "Test getCalibrationSpec(CalibrationContext); then return Symbol is 'EUR-Tenor Label42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CalibrationSpec CalibrationSpecProviderSwap.getCalibrationSpec(CalibrationContext)"
  })
  void testGetCalibrationSpec_thenReturnSymbolIsEurTenorLabel42() {
    // Arrange
    CalibrationSpecProviderSwap calibrationSpecProviderSwap =
        new CalibrationSpecProviderSwap("Tenor Label", "annual", "42", 10.0d);
    CalibrationContextImpl ctx = mock(CalibrationContextImpl.class);
    when(ctx.getReferenceDate()).thenReturn(LocalDate.now());

    // Act
    CalibrationSpec actualCalibrationSpec = calibrationSpecProviderSwap.getCalibrationSpec(ctx);

    // Assert
    verify(ctx, atLeast(1)).getReferenceDate();
    assertEquals("EUR-Tenor Label42", actualCalibrationSpec.getSymbol());
  }
}
