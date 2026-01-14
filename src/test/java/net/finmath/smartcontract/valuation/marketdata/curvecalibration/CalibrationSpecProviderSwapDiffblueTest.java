package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class CalibrationSpecProviderSwapDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link CalibrationSpecProviderSwap#CalibrationSpecProviderSwap(String, String, String,
   *       double)}
   *   <li>{@link CalibrationSpecProviderSwap#getFrequencyLabel()}
   *   <li>{@link CalibrationSpecProviderSwap#getMaturityLabel()}
   *   <li>{@link CalibrationSpecProviderSwap#getSwapRate()}
   *   <li>{@link CalibrationSpecProviderSwap#getTenorLabel()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void CalibrationSpecProviderSwap.<init>(String, String, String, double)",
    "String CalibrationSpecProviderSwap.getFrequencyLabel()",
    "String CalibrationSpecProviderSwap.getMaturityLabel()",
    "double CalibrationSpecProviderSwap.getSwapRate()",
    "String CalibrationSpecProviderSwap.getTenorLabel()"
  })
  void testGettersAndSetters() {
    // Arrange and Act
    CalibrationSpecProviderSwap actualCalibrationSpecProviderSwap =
        new CalibrationSpecProviderSwap("Tenor Label", "Frequency Label", "Maturity Label", 10.0d);
    String actualFrequencyLabel = actualCalibrationSpecProviderSwap.getFrequencyLabel();
    String actualMaturityLabel = actualCalibrationSpecProviderSwap.getMaturityLabel();
    double actualSwapRate = actualCalibrationSpecProviderSwap.getSwapRate();

    // Assert
    assertEquals("Frequency Label", actualFrequencyLabel);
    assertEquals("Maturity Label", actualMaturityLabel);
    assertEquals("Tenor Label", actualCalibrationSpecProviderSwap.getTenorLabel());
    assertEquals(10.0d, actualSwapRate);
  }
}
