package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import static org.junit.jupiter.api.Assertions.assertNull;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class CalibratorDiffblueTest {
  /**
   * Test {@link Calibrator#getCalibratedCurves()}.
   *
   * <p>Method under test: {@link Calibrator#getCalibratedCurves()}
   */
  @Test
  @DisplayName("Test getCalibratedCurves()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "net.finmath.marketdata.calibration.CalibratedCurves Calibrator.getCalibratedCurves()"
  })
  void testGetCalibratedCurves() {
    // Arrange
    ArrayList<CalibrationDataItem> fixings = new ArrayList<>();
    CalibrationContextImpl ctx =
        new CalibrationContextImpl(LocalDate.of(1970, 1, 1).atStartOfDay(), 10.0d);

    Calibrator calibrator = new Calibrator(fixings, ctx);

    // Act and Assert
    assertNull(calibrator.getCalibratedCurves());
  }
}
