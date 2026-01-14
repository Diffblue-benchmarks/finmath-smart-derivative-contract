package net.finmath.smartcontract.valuation.implementation;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.function.DoubleUnaryOperator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class MarginCalculatorDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link MarginCalculator#MarginCalculator(DoubleUnaryOperator)}
   *   <li>{@link MarginCalculator#getRounding()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MarginCalculator.<init>(DoubleUnaryOperator)",
    "DoubleUnaryOperator MarginCalculator.getRounding()"
  })
  void testGettersAndSetters() {
    // Arrange
    DoubleUnaryOperator rounding = mock(DoubleUnaryOperator.class);

    // Act and Assert
    assertSame(rounding, new MarginCalculator(rounding).getRounding());
  }
}
