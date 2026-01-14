package net.finmath.smartcontract.settlement;

import static org.junit.jupiter.api.Assertions.assertNull;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class SettlementGeneratorDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link SettlementGenerator}
   *   <li>{@link SettlementGenerator#getSettlement()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SettlementGenerator.<init>()",
    "net.finmath.smartcontract.settlement.Settlement SettlementGenerator.getSettlement()"
  })
  void testGettersAndSetters() {
    // Arrange, Act and Assert
    assertNull(new SettlementGenerator().getSettlement());
  }
}
