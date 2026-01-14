package net.finmath.smartcontract.settlement;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class SettlementsDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link Settlements}
   *   <li>{@link Settlements#setSettlements(List)}
   *   <li>{@link Settlements#getSettlements()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void Settlements.<init>()",
    "List Settlements.getSettlements()",
    "void Settlements.setSettlements(List)"
  })
  void testGettersAndSetters() {
    // Arrange and Act
    Settlements actualSettlements = new Settlements();
    ArrayList<Settlement> settlements = new ArrayList<>();
    actualSettlements.setSettlements(settlements);
    List<Settlement> actualSettlements2 = actualSettlements.getSettlements();

    // Assert
    assertTrue(actualSettlements2.isEmpty());
    assertSame(settlements, actualSettlements2);
  }
}
