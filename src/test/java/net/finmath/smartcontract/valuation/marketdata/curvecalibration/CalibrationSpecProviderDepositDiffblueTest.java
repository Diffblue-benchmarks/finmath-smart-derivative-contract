package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import net.finmath.marketdata.calibration.CalibratedCurves;
import org.junit.jupiter.api.Test;

class CalibrationSpecProviderDepositDiffblueTest {
  /**
   * Method under test:
   * {@link CalibrationSpecProviderDeposit#getCalibrationSpec(CalibrationContext)}
   */
  @Test
  void testGetCalibrationSpec() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibrationSpecProviderDeposit calibrationSpecProviderDeposit = new CalibrationSpecProviderDeposit("Tenor Label",
        "42", 10.0d);

    // Act and Assert
    assertEquals("EUR-Tenor Label42",
        calibrationSpecProviderDeposit
            .getCalibrationSpec(new CalibrationContextImpl(LocalDate.of(1970, 1, 1).atStartOfDay(), 10.0d))
            .getSymbol());
  }

  /**
   * Method under test:
   * {@link CalibrationSpecProviderDeposit#getCalibrationSpec(CalibrationContext)}
   */
  @Test
  void testGetCalibrationSpec2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibrationSpecProviderDeposit calibrationSpecProviderDeposit = new CalibrationSpecProviderDeposit("Tenor Label",
        "42", 10.0d);
    CalibrationContextImpl ctx = mock(CalibrationContextImpl.class);
    when(ctx.getReferenceDate()).thenReturn(LocalDate.ofYearDay(2, 2));

    // Act
    CalibratedCurves.CalibrationSpec actualCalibrationSpec = calibrationSpecProviderDeposit.getCalibrationSpec(ctx);

    // Assert
    verify(ctx).getReferenceDate();
    assertEquals("EUR-Tenor Label42", actualCalibrationSpec.getSymbol());
  }

  /**
   * Method under test:
   * {@link CalibrationSpecProviderDeposit#getCalibrationSpec(CalibrationContext)}
   */
  @Test
  void testGetCalibrationSpec3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibrationSpecProviderDeposit calibrationSpecProviderDeposit = new CalibrationSpecProviderDeposit("Tenor Label",
        "42", 10.0d);
    CalibrationContextImpl ctx = mock(CalibrationContextImpl.class);
    when(ctx.getReferenceDate()).thenReturn(LocalDate.ofYearDay(2, 100));

    // Act
    CalibratedCurves.CalibrationSpec actualCalibrationSpec = calibrationSpecProviderDeposit.getCalibrationSpec(ctx);

    // Assert
    verify(ctx).getReferenceDate();
    assertEquals("EUR-Tenor Label42", actualCalibrationSpec.getSymbol());
  }

  /**
   * Method under test:
   * {@link CalibrationSpecProviderDeposit#getCalibrationSpec(CalibrationContext)}
   */
  @Test
  void testGetCalibrationSpec4() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibrationSpecProviderDeposit calibrationSpecProviderDeposit = new CalibrationSpecProviderDeposit("Tenor Label",
        "42", 10.0d);
    CalibrationContextImpl ctx = mock(CalibrationContextImpl.class);
    when(ctx.getReferenceDate()).thenReturn(LocalDate.ofYearDay(19, 100));

    // Act
    CalibratedCurves.CalibrationSpec actualCalibrationSpec = calibrationSpecProviderDeposit.getCalibrationSpec(ctx);

    // Assert
    verify(ctx).getReferenceDate();
    assertEquals("EUR-Tenor Label42", actualCalibrationSpec.getSymbol());
  }

  /**
   * Method under test:
   * {@link CalibrationSpecProviderDeposit#getCalibrationSpec(CalibrationContext)}
   */
  @Test
  void testGetCalibrationSpec5() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibrationSpecProviderDeposit calibrationSpecProviderDeposit = new CalibrationSpecProviderDeposit("Tenor Label",
        "42", 10.0d);
    CalibrationContextImpl ctx = mock(CalibrationContextImpl.class);
    when(ctx.getReferenceDate()).thenReturn(LocalDate.ofYearDay(2, Integer.SIZE));

    // Act
    CalibratedCurves.CalibrationSpec actualCalibrationSpec = calibrationSpecProviderDeposit.getCalibrationSpec(ctx);

    // Assert
    verify(ctx).getReferenceDate();
    assertEquals("EUR-Tenor Label42", actualCalibrationSpec.getSymbol());
  }

  /**
   * Method under test:
   * {@link CalibrationSpecProviderDeposit#getCalibrationSpec(CalibrationContext)}
   */
  @Test
  void testGetCalibrationSpec6() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibrationSpecProviderDeposit calibrationSpecProviderDeposit = new CalibrationSpecProviderDeposit("Tenor Label",
        "42", 10.0d);
    CalibrationContextImpl ctx = mock(CalibrationContextImpl.class);
    when(ctx.getReferenceDate()).thenReturn(LocalDate.ofYearDay(19, Integer.SIZE));

    // Act
    CalibratedCurves.CalibrationSpec actualCalibrationSpec = calibrationSpecProviderDeposit.getCalibrationSpec(ctx);

    // Assert
    verify(ctx).getReferenceDate();
    assertEquals("EUR-Tenor Label42", actualCalibrationSpec.getSymbol());
  }
}
