package net.finmath.smartcontract.settlement;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.finmath.smartcontract.model.MarketDataList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {Settlements.class})
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class SettlementsDiffblueTest {
  @Autowired
  private Settlements settlements;

  /**
   * Method under test: {@link Settlements#getPrevious(Settlement)}
   */
  @Test
  void testGetPrevious() {
    // Arrange
    Settlements settlements2 = new Settlements();
    settlements2.setSettlements(new ArrayList<>());
    Settlement settlement = mock(Settlement.class);
    doNothing().when(settlement).setCurrency(Mockito.<String>any());
    doNothing().when(settlement).setMarginLimits(Mockito.<List<BigDecimal>>any());
    doNothing().when(settlement).setMarginValue(Mockito.<BigDecimal>any());
    doNothing().when(settlement).setMarketData(Mockito.<MarketDataList>any());
    doNothing().when(settlement).setSettlementInfos(Mockito.<List<SettlementInfo>>any());
    doNothing().when(settlement).setSettlementNPV(Mockito.<BigDecimal>any());
    doNothing().when(settlement).setSettlementNPVNext(Mockito.<BigDecimal>any());
    doNothing().when(settlement).setSettlementNPVPrevious(Mockito.<BigDecimal>any());
    doNothing().when(settlement).setSettlementTime(Mockito.<ZonedDateTime>any());
    doNothing().when(settlement).setSettlementTimeNext(Mockito.<ZonedDateTime>any());
    doNothing().when(settlement).setSettlementType(Mockito.<Settlement.SettlementType>any());
    doNothing().when(settlement).setTradeId(Mockito.<String>any());
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
    settlement.setSettlementType(Settlement.SettlementType.INITIAL);
    settlement.setTradeId("42");

    // Act
    Optional<Settlement> actualPrevious = settlements2.getPrevious(settlement);

    // Assert
    verify(settlement).setCurrency(eq("GBP"));
    verify(settlement).setMarginLimits(isA(List.class));
    verify(settlement).setMarginValue(isA(BigDecimal.class));
    verify(settlement).setMarketData(isA(MarketDataList.class));
    verify(settlement).setSettlementInfos(isA(List.class));
    verify(settlement).setSettlementNPV(isA(BigDecimal.class));
    verify(settlement).setSettlementNPVNext(isA(BigDecimal.class));
    verify(settlement).setSettlementNPVPrevious(isA(BigDecimal.class));
    verify(settlement).setSettlementTime(isA(ZonedDateTime.class));
    verify(settlement).setSettlementTimeNext(isA(ZonedDateTime.class));
    verify(settlement).setSettlementType(eq(Settlement.SettlementType.INITIAL));
    verify(settlement).setTradeId(eq("42"));
    assertFalse(actualPrevious.isPresent());
  }

  /**
   * Method under test: {@link Settlements#getNext(Settlement)}
   */
  @Test
  void testGetNext() {
    // Arrange
    Settlements settlements2 = new Settlements();
    settlements2.setSettlements(new ArrayList<>());
    Settlement settlement = mock(Settlement.class);
    doNothing().when(settlement).setCurrency(Mockito.<String>any());
    doNothing().when(settlement).setMarginLimits(Mockito.<List<BigDecimal>>any());
    doNothing().when(settlement).setMarginValue(Mockito.<BigDecimal>any());
    doNothing().when(settlement).setMarketData(Mockito.<MarketDataList>any());
    doNothing().when(settlement).setSettlementInfos(Mockito.<List<SettlementInfo>>any());
    doNothing().when(settlement).setSettlementNPV(Mockito.<BigDecimal>any());
    doNothing().when(settlement).setSettlementNPVNext(Mockito.<BigDecimal>any());
    doNothing().when(settlement).setSettlementNPVPrevious(Mockito.<BigDecimal>any());
    doNothing().when(settlement).setSettlementTime(Mockito.<ZonedDateTime>any());
    doNothing().when(settlement).setSettlementTimeNext(Mockito.<ZonedDateTime>any());
    doNothing().when(settlement).setSettlementType(Mockito.<Settlement.SettlementType>any());
    doNothing().when(settlement).setTradeId(Mockito.<String>any());
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
    settlement.setSettlementType(Settlement.SettlementType.INITIAL);
    settlement.setTradeId("42");

    // Act
    Optional<Settlement> actualNext = settlements2.getNext(settlement);

    // Assert
    verify(settlement).setCurrency(eq("GBP"));
    verify(settlement).setMarginLimits(isA(List.class));
    verify(settlement).setMarginValue(isA(BigDecimal.class));
    verify(settlement).setMarketData(isA(MarketDataList.class));
    verify(settlement).setSettlementInfos(isA(List.class));
    verify(settlement).setSettlementNPV(isA(BigDecimal.class));
    verify(settlement).setSettlementNPVNext(isA(BigDecimal.class));
    verify(settlement).setSettlementNPVPrevious(isA(BigDecimal.class));
    verify(settlement).setSettlementTime(isA(ZonedDateTime.class));
    verify(settlement).setSettlementTimeNext(isA(ZonedDateTime.class));
    verify(settlement).setSettlementType(eq(Settlement.SettlementType.INITIAL));
    verify(settlement).setTradeId(eq("42"));
    assertFalse(actualNext.isPresent());
  }

  /**
   * Method under test: {@link Settlements#getNext(Settlement)}
   */
  @Test
  void testGetNext2() {
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
    settlement.setSettlementType(Settlement.SettlementType.INITIAL);
    settlement.setTradeId("42");

    ArrayList<Settlement> settlements2 = new ArrayList<>();
    settlements2.add(settlement);

    Settlements settlements3 = new Settlements();
    settlements3.setSettlements(settlements2);
    Settlement settlement2 = mock(Settlement.class);
    doNothing().when(settlement2).setCurrency(Mockito.<String>any());
    doNothing().when(settlement2).setMarginLimits(Mockito.<List<BigDecimal>>any());
    doNothing().when(settlement2).setMarginValue(Mockito.<BigDecimal>any());
    doNothing().when(settlement2).setMarketData(Mockito.<MarketDataList>any());
    doNothing().when(settlement2).setSettlementInfos(Mockito.<List<SettlementInfo>>any());
    doNothing().when(settlement2).setSettlementNPV(Mockito.<BigDecimal>any());
    doNothing().when(settlement2).setSettlementNPVNext(Mockito.<BigDecimal>any());
    doNothing().when(settlement2).setSettlementNPVPrevious(Mockito.<BigDecimal>any());
    doNothing().when(settlement2).setSettlementTime(Mockito.<ZonedDateTime>any());
    doNothing().when(settlement2).setSettlementTimeNext(Mockito.<ZonedDateTime>any());
    doNothing().when(settlement2).setSettlementType(Mockito.<Settlement.SettlementType>any());
    doNothing().when(settlement2).setTradeId(Mockito.<String>any());
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
    settlement2.setSettlementType(Settlement.SettlementType.INITIAL);
    settlement2.setTradeId("42");

    // Act
    Optional<Settlement> actualNext = settlements3.getNext(settlement2);

    // Assert
    verify(settlement2).setCurrency(eq("GBP"));
    verify(settlement2).setMarginLimits(isA(List.class));
    verify(settlement2).setMarginValue(isA(BigDecimal.class));
    verify(settlement2).setMarketData(isA(MarketDataList.class));
    verify(settlement2).setSettlementInfos(isA(List.class));
    verify(settlement2).setSettlementNPV(isA(BigDecimal.class));
    verify(settlement2).setSettlementNPVNext(isA(BigDecimal.class));
    verify(settlement2).setSettlementNPVPrevious(isA(BigDecimal.class));
    verify(settlement2).setSettlementTime(isA(ZonedDateTime.class));
    verify(settlement2).setSettlementTimeNext(isA(ZonedDateTime.class));
    verify(settlement2).setSettlementType(eq(Settlement.SettlementType.INITIAL));
    verify(settlement2).setTradeId(eq("42"));
    assertTrue(actualNext.isPresent());
    assertSame(settlement, actualNext.get());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link Settlements}
   *   <li>{@link Settlements#setSettlements(List)}
   *   <li>{@link Settlements#getSettlements()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    Settlements actualSettlements = new Settlements();
    ArrayList<Settlement> settlements = new ArrayList<>();
    actualSettlements.setSettlements(settlements);
    List<Settlement> actualSettlements2 = actualSettlements.getSettlements();

    // Assert that nothing has changed
    assertTrue(actualSettlements2.isEmpty());
    assertSame(settlements, actualSettlements2);
  }
}
