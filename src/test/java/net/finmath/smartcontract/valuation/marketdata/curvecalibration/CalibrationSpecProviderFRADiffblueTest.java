package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import net.finmath.marketdata.calibration.CalibratedCurves;
import org.junit.jupiter.api.Test;

class CalibrationSpecProviderFRADiffblueTest {
  /**
   * Method under test:
   * {@link CalibrationSpecProviderFRA#getCalibrationSpec(CalibrationContext)}
   */
  @Test
  void testGetCalibrationSpec() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibrationSpecProviderFRA calibrationSpecProviderFRA = new CalibrationSpecProviderFRA("42", "42", 10.0d);
    CalibrationContextImpl ctx = mock(CalibrationContextImpl.class);
    when(ctx.getReferenceDate()).thenReturn(LocalDate.of(1970, 1, 1));

    // Act
    CalibratedCurves.CalibrationSpec actualCalibrationSpec = calibrationSpecProviderFRA.getCalibrationSpec(ctx);

    // Assert
    verify(ctx).getReferenceDate();
    assertEquals("EUR-4242", actualCalibrationSpec.getSymbol());
  }

  /**
   * Method under test:
   * {@link CalibrationSpecProviderFRA#getCalibrationSpec(CalibrationContext)}
   */
  @Test
  void testGetCalibrationSpec2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibrationSpecProviderFRA calibrationSpecProviderFRA = new CalibrationSpecProviderFRA("42", "42", 10.0d);
    CalibrationContextImpl ctx = mock(CalibrationContextImpl.class);
    when(ctx.getReferenceDate()).thenReturn(LocalDate.ofYearDay(2, 2));

    // Act
    CalibratedCurves.CalibrationSpec actualCalibrationSpec = calibrationSpecProviderFRA.getCalibrationSpec(ctx);

    // Assert
    verify(ctx).getReferenceDate();
    assertEquals("EUR-4242", actualCalibrationSpec.getSymbol());
  }

  /**
   * Method under test:
   * {@link CalibrationSpecProviderFRA#getCalibrationSpec(CalibrationContext)}
   */
  @Test
  void testGetCalibrationSpec3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibrationSpecProviderFRA calibrationSpecProviderFRA = new CalibrationSpecProviderFRA("42", "42", 10.0d);
    CalibrationContextImpl ctx = mock(CalibrationContextImpl.class);
    when(ctx.getReferenceDate()).thenReturn(LocalDate.ofYearDay(2, 100));

    // Act
    CalibratedCurves.CalibrationSpec actualCalibrationSpec = calibrationSpecProviderFRA.getCalibrationSpec(ctx);

    // Assert
    verify(ctx).getReferenceDate();
    assertEquals("EUR-4242", actualCalibrationSpec.getSymbol());
  }

  /**
   * Method under test:
   * {@link CalibrationSpecProviderFRA#getCalibrationSpec(CalibrationContext)}
   */
  @Test
  void testGetCalibrationSpec4() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibrationSpecProviderFRA calibrationSpecProviderFRA = new CalibrationSpecProviderFRA("42", "42", 10.0d);
    CalibrationContextImpl ctx = mock(CalibrationContextImpl.class);
    when(ctx.getReferenceDate()).thenReturn(LocalDate.ofYearDay(2, 25));

    // Act
    CalibratedCurves.CalibrationSpec actualCalibrationSpec = calibrationSpecProviderFRA.getCalibrationSpec(ctx);

    // Assert
    verify(ctx).getReferenceDate();
    assertEquals("EUR-4242", actualCalibrationSpec.getSymbol());
  }

  /**
   * Method under test:
   * {@link CalibrationSpecProviderFRA#getCalibrationSpec(CalibrationContext)}
   */
  @Test
  void testGetCalibrationSpec5() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibrationSpecProviderFRA calibrationSpecProviderFRA = new CalibrationSpecProviderFRA("42", "42", 10.0d);
    CalibrationContextImpl ctx = mock(CalibrationContextImpl.class);
    when(ctx.getReferenceDate()).thenReturn(LocalDate.ofYearDay(19, 100));

    // Act
    CalibratedCurves.CalibrationSpec actualCalibrationSpec = calibrationSpecProviderFRA.getCalibrationSpec(ctx);

    // Assert
    verify(ctx).getReferenceDate();
    assertEquals("EUR-4242", actualCalibrationSpec.getSymbol());
  }

  /**
   * Method under test:
   * {@link CalibrationSpecProviderFRA#getCalibrationSpec(CalibrationContext)}
   */
  @Test
  void testGetCalibrationSpec6() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    CalibrationSpecProviderFRA calibrationSpecProviderFRA = new CalibrationSpecProviderFRA("42", "42", 10.0d);
    CalibrationContextImpl ctx = mock(CalibrationContextImpl.class);
    when(ctx.getReferenceDate()).thenReturn(LocalDate.ofYearDay(-2, 25));

    // Act
    CalibratedCurves.CalibrationSpec actualCalibrationSpec = calibrationSpecProviderFRA.getCalibrationSpec(ctx);

    // Assert
    verify(ctx).getReferenceDate();
    assertEquals("EUR-4242", actualCalibrationSpec.getSymbol());
  }
}
