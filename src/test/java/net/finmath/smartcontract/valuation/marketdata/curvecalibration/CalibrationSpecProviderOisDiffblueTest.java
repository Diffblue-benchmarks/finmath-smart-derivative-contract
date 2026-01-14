package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class CalibrationSpecProviderOisDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link CalibrationSpecProviderOis#CalibrationSpecProviderOis(String, String, double)}
   *   <li>{@link CalibrationSpecProviderOis#getFrequency()}
   *   <li>{@link CalibrationSpecProviderOis#getMaturityLabel()}
   *   <li>{@link CalibrationSpecProviderOis#getSwapRate()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void CalibrationSpecProviderOis.<init>(String, String, double)",
    "String CalibrationSpecProviderOis.getFrequency()",
    "String CalibrationSpecProviderOis.getMaturityLabel()",
    "double CalibrationSpecProviderOis.getSwapRate()"
  })
  void testGettersAndSetters() {
    // Arrange and Act
    CalibrationSpecProviderOis actualCalibrationSpecProviderOis =
        new CalibrationSpecProviderOis("Maturity Label", "Frequency", 10.0d);
    String actualFrequency = actualCalibrationSpecProviderOis.getFrequency();
    String actualMaturityLabel = actualCalibrationSpecProviderOis.getMaturityLabel();

    // Assert
    assertEquals("Frequency", actualFrequency);
    assertEquals("Maturity Label", actualMaturityLabel);
    assertEquals(10.0d, actualCalibrationSpecProviderOis.getSwapRate());
  }
}
