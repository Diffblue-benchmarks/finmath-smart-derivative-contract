package net.finmath.smartcontract.valuation.service.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.math.RoundingMode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class SDCRoundingDiffblueTest {
  /**
   * Test {@link SDCRounding#SDCRounding(int, RoundingMode)}.
   *
   * <p>Method under test: {@link SDCRounding#SDCRounding(int, RoundingMode)}
   */
  @Test
  @DisplayName("Test new SDCRounding(int, RoundingMode)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SDCRounding.<init>(int, RoundingMode)"})
  void testNewSDCRounding() {
    // Arrange and Act
    SDCRounding actualSdcRounding = new SDCRounding(1, RoundingMode.UP);

    // Assert
    assertEquals(1, actualSdcRounding.scale);
    assertEquals(RoundingMode.UP, actualSdcRounding.roundingMode);
  }
}
