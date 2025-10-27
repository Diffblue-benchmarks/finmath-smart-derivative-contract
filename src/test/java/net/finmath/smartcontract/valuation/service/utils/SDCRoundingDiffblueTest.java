package net.finmath.smartcontract.valuation.service.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.math.RoundingMode;
import org.junit.jupiter.api.Test;

class SDCRoundingDiffblueTest {
  /**
   * Method under test: {@link SDCRounding#SDCRounding(int, RoundingMode)}
   */
  @Test
  void testNewSDCRounding() {
    // Arrange and Act
    SDCRounding actualSdcRounding = new SDCRounding(1, RoundingMode.UP);

    // Assert
    assertEquals(1, actualSdcRounding.scale);
    assertEquals(RoundingMode.UP, actualSdcRounding.roundingMode);
  }
}
