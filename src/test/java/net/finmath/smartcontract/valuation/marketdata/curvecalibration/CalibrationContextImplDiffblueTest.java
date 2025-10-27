package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class CalibrationContextImplDiffblueTest {
  /**
   * Method under test: {@link CalibrationContextImpl#getReferenceDate()}
   */
  @Test
  void testGetReferenceDate() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    LocalDate ofResult = LocalDate.of(1970, 1, 1);

    // Act
    LocalDate actualReferenceDate = (new CalibrationContextImpl(ofResult.atStartOfDay(), 10.0d)).getReferenceDate();

    // Assert
    assertEquals("1970-01-01", actualReferenceDate.toString());
    assertSame(ofResult, actualReferenceDate);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link CalibrationContextImpl#CalibrationContextImpl(LocalDateTime, double)}
   *   <li>{@link CalibrationContextImpl#getAccuracy()}
   *   <li>{@link CalibrationContextImpl#getReferenceDateTime()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    LocalDateTime referenceDateTime = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act
    CalibrationContextImpl actualCalibrationContextImpl = new CalibrationContextImpl(referenceDateTime, 10.0d);
    double actualAccuracy = actualCalibrationContextImpl.getAccuracy();

    // Assert
    assertEquals(10.0d, actualAccuracy);
    assertSame(referenceDateTime, actualCalibrationContextImpl.getReferenceDateTime());
  }
}
