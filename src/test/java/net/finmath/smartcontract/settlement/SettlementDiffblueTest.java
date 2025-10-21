package net.finmath.smartcontract.settlement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import net.finmath.smartcontract.model.MarketDataList;
import net.finmath.smartcontract.settlement.Settlement.SettlementType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class SettlementDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link Settlement}
   *   <li>{@link Settlement#setCurrency(String)}
   *   <li>{@link Settlement#setMarginLimits(List)}
   *   <li>{@link Settlement#setMarketData(MarketDataList)}
   *   <li>{@link Settlement#setSettlementInfos(List)}
   *   <li>{@link Settlement#setSettlementTime(ZonedDateTime)}
   *   <li>{@link Settlement#setSettlementTimeNext(ZonedDateTime)}
   *   <li>{@link Settlement#setSettlementType(SettlementType)}
   *   <li>{@link Settlement#setTradeId(String)}
   *   <li>{@link Settlement#getCurrency()}
   *   <li>{@link Settlement#getMarginLimits()}
   *   <li>{@link Settlement#getMarginValue()}
   *   <li>{@link Settlement#getMarketData()}
   *   <li>{@link Settlement#getSettlementInfos()}
   *   <li>{@link Settlement#getSettlementNPV()}
   *   <li>{@link Settlement#getSettlementNPVNext()}
   *   <li>{@link Settlement#getSettlementNPVPrevious()}
   *   <li>{@link Settlement#getSettlementTime()}
   *   <li>{@link Settlement#getSettlementTimeNext()}
   *   <li>{@link Settlement#getSettlementType()}
   *   <li>{@link Settlement#getTradeId()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void Settlement.<init>()",
    "String Settlement.getCurrency()",
    "List Settlement.getMarginLimits()",
    "BigDecimal Settlement.getMarginValue()",
    "MarketDataList Settlement.getMarketData()",
    "List Settlement.getSettlementInfos()",
    "BigDecimal Settlement.getSettlementNPV()",
    "BigDecimal Settlement.getSettlementNPVNext()",
    "BigDecimal Settlement.getSettlementNPVPrevious()",
    "ZonedDateTime Settlement.getSettlementTime()",
    "ZonedDateTime Settlement.getSettlementTimeNext()",
    "SettlementType Settlement.getSettlementType()",
    "String Settlement.getTradeId()",
    "void Settlement.setCurrency(String)",
    "void Settlement.setMarginLimits(List)",
    "void Settlement.setMarginValue(BigDecimal)",
    "void Settlement.setMarketData(MarketDataList)",
    "void Settlement.setSettlementInfos(List)",
    "void Settlement.setSettlementNPV(BigDecimal)",
    "void Settlement.setSettlementNPVNext(BigDecimal)",
    "void Settlement.setSettlementNPVPrevious(BigDecimal)",
    "void Settlement.setSettlementTime(ZonedDateTime)",
    "void Settlement.setSettlementTimeNext(ZonedDateTime)",
    "void Settlement.setSettlementType(SettlementType)",
    "void Settlement.setTradeId(String)"
  })
  void testGettersAndSetters() {
    // Arrange and Act
    Settlement actualSettlement = new Settlement();
    actualSettlement.setCurrency("\"USD\"");
    ArrayList<BigDecimal> marginLimits = new ArrayList<>();
    actualSettlement.setMarginLimits(marginLimits);
    MarketDataList marketData = new MarketDataList();
    actualSettlement.setMarketData(marketData);
    ArrayList<SettlementInfo> settlementInfos = new ArrayList<>();
    actualSettlement.setSettlementInfos(settlementInfos);
    ZonedDateTime settlementTime = LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC);
    actualSettlement.setSettlementTime(settlementTime);
    ZonedDateTime settlementTimeNext =
        LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC);
    actualSettlement.setSettlementTimeNext(settlementTimeNext);
    actualSettlement.setSettlementType(SettlementType.INITIAL);
    actualSettlement.setTradeId("\"TRADE12345XYZ\"");
    String actualCurrency = actualSettlement.getCurrency();
    List<BigDecimal> actualMarginLimits = actualSettlement.getMarginLimits();
    BigDecimal actualMarginValue = actualSettlement.getMarginValue();
    MarketDataList actualMarketData = actualSettlement.getMarketData();
    List<SettlementInfo> actualSettlementInfos = actualSettlement.getSettlementInfos();
    BigDecimal actualSettlementNPV = actualSettlement.getSettlementNPV();
    BigDecimal actualSettlementNPVNext = actualSettlement.getSettlementNPVNext();
    BigDecimal actualSettlementNPVPrevious = actualSettlement.getSettlementNPVPrevious();
    ZonedDateTime actualSettlementTime = actualSettlement.getSettlementTime();
    ZonedDateTime actualSettlementTimeNext = actualSettlement.getSettlementTimeNext();
    SettlementType actualSettlementType = actualSettlement.getSettlementType();

    // Assert
    assertEquals("\"TRADE12345XYZ\"", actualSettlement.getTradeId());
    assertEquals("\"USD\"", actualCurrency);
    assertNull(actualMarginValue);
    assertNull(actualSettlementNPV);
    assertNull(actualSettlementNPVNext);
    assertNull(actualSettlementNPVPrevious);
    assertEquals(SettlementType.INITIAL, actualSettlementType);
    assertTrue(actualMarginLimits.isEmpty());
    assertTrue(actualSettlementInfos.isEmpty());
    assertSame(marginLimits, actualMarginLimits);
    assertSame(settlementInfos, actualSettlementInfos);
    assertSame(marketData, actualMarketData);
    assertSame(settlementTime, actualSettlementTime);
    assertSame(settlementTimeNext, actualSettlementTimeNext);
  }
}
