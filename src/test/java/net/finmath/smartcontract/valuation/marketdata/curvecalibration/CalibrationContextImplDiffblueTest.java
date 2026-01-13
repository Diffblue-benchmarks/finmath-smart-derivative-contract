package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class CalibrationContextImplDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link CalibrationContextImpl#CalibrationContextImpl(LocalDateTime, double)}
   *   <li>{@link CalibrationContextImpl#getAccuracy()}
   *   <li>{@link CalibrationContextImpl#getReferenceDateTime()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void CalibrationContextImpl.<init>(LocalDateTime, double)",
    "double CalibrationContextImpl.getAccuracy()",
    "LocalDateTime CalibrationContextImpl.getReferenceDateTime()"
  })
  void testGettersAndSetters() {
    // Arrange
    LocalDateTime referenceDateTime = LocalDate.of(1970, 1, 1).atStartOfDay();

    // Act
    CalibrationContextImpl actualCalibrationContextImpl =
        new CalibrationContextImpl(referenceDateTime, 10.0d);
    double actualAccuracy = actualCalibrationContextImpl.getAccuracy();

    // Assert
    assertEquals(10.0d, actualAccuracy);
    assertSame(referenceDateTime, actualCalibrationContextImpl.getReferenceDateTime());
  }

  /**
   * Test {@link CalibrationContextImpl#getReferenceDate()}.
   *
   * <p>Method under test: {@link CalibrationContextImpl#getReferenceDate()}
   */
  @Test
  @DisplayName("Test getReferenceDate()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"LocalDate CalibrationContextImpl.getReferenceDate()"})
  void testGetReferenceDate() {
    // Arrange
    LocalDate ofResult = LocalDate.of(1970, 1, 1);

    // Act
    LocalDate actualReferenceDate =
        new CalibrationContextImpl(ofResult.atStartOfDay(), 10.0d).getReferenceDate();

    // Assert
    assertEquals("1970-01-01", actualReferenceDate.toString());
    assertSame(ofResult, actualReferenceDate);
  }
}
