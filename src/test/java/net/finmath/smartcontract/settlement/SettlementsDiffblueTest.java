package net.finmath.smartcontract.settlement;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import net.finmath.smartcontract.model.MarketDataList;
import net.finmath.smartcontract.settlement.Settlement.SettlementType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
class SettlementsDiffblueTest {
  /**
   * Test {@link Settlements#getNext(Settlement)}.
   *
   * <ul>
   *   <li>Given {@link Settlements} (default constructor) Settlements is {@link
   *       ArrayList#ArrayList()}.
   *   <li>Then return not Present.
   * </ul>
   *
   * <p>Method under test: {@link Settlements#getNext(Settlement)}
   */
  @Test
  @DisplayName(
      "Test getNext(Settlement); given Settlements (default constructor) Settlements is ArrayList(); then return not Present")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.Optional Settlements.getNext(Settlement)"})
  void testGetNext_givenSettlementsSettlementsIsArrayList_thenReturnNotPresent() {
    // Arrange
    Settlements settlements = new Settlements();
    settlements.setSettlements(new ArrayList<>());

    Settlement settlement = new Settlement();
    settlement.setCurrency("GBP");
    settlement.setMarginLimits(new ArrayList<>());
    settlement.setMarginValue(new BigDecimal("2.3"));
    settlement.setMarketData(new MarketDataList());
    settlement.setSettlementInfos(new ArrayList<>());
    settlement.setSettlementNPV(new BigDecimal("2.3"));
    settlement.setSettlementNPVNext(new BigDecimal("2.3"));
    settlement.setSettlementNPVPrevious(new BigDecimal("2.3"));
    settlement.setSettlementTime(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC));
    settlement.setSettlementTimeNext(
        LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC));
    settlement.setSettlementType(SettlementType.INITIAL);
    settlement.setTradeId("42");

    // Act and Assert
    assertFalse(settlements.getNext(settlement).isPresent());
  }

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
