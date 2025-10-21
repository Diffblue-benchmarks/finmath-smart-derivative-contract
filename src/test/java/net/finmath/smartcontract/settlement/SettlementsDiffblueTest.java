package net.finmath.smartcontract.settlement;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.finmath.smartcontract.model.MarketDataList;
import net.finmath.smartcontract.settlement.Settlement.SettlementType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {Settlements.class})
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
class SettlementsDiffblueTest {
  @Autowired
  private Settlements settlements;

  /**
   * Test {@link Settlements#getPrevious(Settlement)}.
   * <ul>
   *   <li>Given {@link Settlement} (default constructor) Currency is {@code GBP}.</li>
   *   <li>Then return not Present.</li>
   * </ul>
   * <p>
   * Method under test: {@link Settlements#getPrevious(Settlement)}
   */
  @Test
  @DisplayName("Test getPrevious(Settlement); given Settlement (default constructor) Currency is 'GBP'; then return not Present")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional Settlements.getPrevious(Settlement)"})
  void testGetPrevious_givenSettlementCurrencyIsGbp_thenReturnNotPresent() {
    // Arrange
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
    settlement.setSettlementTimeNext(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC));
    settlement.setSettlementType(SettlementType.INITIAL);
    settlement.setTradeId("42");

    ArrayList<Settlement> settlements2 = new ArrayList<>();
    settlements2.add(settlement);

    Settlements settlements3 = new Settlements();
    settlements3.setSettlements(settlements2);

    Settlement settlement2 = new Settlement();
    settlement2.setCurrency("GBP");
    settlement2.setMarginLimits(new ArrayList<>());
    settlement2.setMarginValue(new BigDecimal("2.3"));
    settlement2.setMarketData(new MarketDataList());
    settlement2.setSettlementInfos(new ArrayList<>());
    settlement2.setSettlementNPV(new BigDecimal("2.3"));
    settlement2.setSettlementNPVNext(new BigDecimal("2.3"));
    settlement2.setSettlementNPVPrevious(new BigDecimal("2.3"));
    settlement2.setSettlementTime(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC));
    settlement2.setSettlementTimeNext(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC));
    settlement2.setSettlementType(SettlementType.INITIAL);
    settlement2.setTradeId("42");

    // Act and Assert
    assertFalse(settlements3.getPrevious(settlement2).isPresent());
  }

  /**
   * Test {@link Settlements#getNext(Settlement)}.
   * <ul>
   *   <li>Given {@link Settlement} (default constructor) Currency is {@code GBP}.</li>
   *   <li>Then return Present.</li>
   * </ul>
   * <p>
   * Method under test: {@link Settlements#getNext(Settlement)}
   */
  @Test
  @DisplayName("Test getNext(Settlement); given Settlement (default constructor) Currency is 'GBP'; then return Present")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional Settlements.getNext(Settlement)"})
  void testGetNext_givenSettlementCurrencyIsGbp_thenReturnPresent() {
    // Arrange
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
    settlement.setSettlementTimeNext(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC));
    settlement.setSettlementType(SettlementType.INITIAL);
    settlement.setTradeId("42");

    ArrayList<Settlement> settlements2 = new ArrayList<>();
    settlements2.add(settlement);

    Settlements settlements3 = new Settlements();
    settlements3.setSettlements(settlements2);

    Settlement settlement2 = new Settlement();
    settlement2.setCurrency("GBP");
    settlement2.setMarginLimits(new ArrayList<>());
    settlement2.setMarginValue(new BigDecimal("2.3"));
    settlement2.setMarketData(new MarketDataList());
    settlement2.setSettlementInfos(new ArrayList<>());
    settlement2.setSettlementNPV(new BigDecimal("2.3"));
    settlement2.setSettlementNPVNext(new BigDecimal("2.3"));
    settlement2.setSettlementNPVPrevious(new BigDecimal("2.3"));
    settlement2.setSettlementTime(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC));
    settlement2.setSettlementTimeNext(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC));
    settlement2.setSettlementType(SettlementType.INITIAL);
    settlement2.setTradeId("42");

    // Act
    Optional<Settlement> actualNext = settlements3.getNext(settlement2);

    // Assert
    assertTrue(actualNext.isPresent());
    assertSame(settlement, actualNext.get());
  }

  /**
   * Test {@link Settlements#getNext(Settlement)}.
   * <ul>
   *   <li>Given {@link Settlements} (default constructor) Settlements is {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return not Present.</li>
   * </ul>
   * <p>
   * Method under test: {@link Settlements#getNext(Settlement)}
   */
  @Test
  @DisplayName("Test getNext(Settlement); given Settlements (default constructor) Settlements is ArrayList(); then return not Present")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional Settlements.getNext(Settlement)"})
  void testGetNext_givenSettlementsSettlementsIsArrayList_thenReturnNotPresent() {
    // Arrange
    Settlements settlements2 = new Settlements();
    settlements2.setSettlements(new ArrayList<>());

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
    settlement.setSettlementTimeNext(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC));
    settlement.setSettlementType(SettlementType.INITIAL);
    settlement.setTradeId("42");

    // Act and Assert
    assertFalse(settlements2.getNext(settlement).isPresent());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link Settlements}
   *   <li>{@link Settlements#setSettlements(List)}
   *   <li>{@link Settlements#getSettlements()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void Settlements.<init>()", "List Settlements.getSettlements()",
      "void Settlements.setSettlements(List)"})
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
