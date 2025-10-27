package net.finmath.smartcontract.settlement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class SettlementInfoDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link SettlementInfo#SettlementInfo()}
   *   <li>{@link SettlementInfo#setKey(String)}
   *   <li>{@link SettlementInfo#setValue(BigDecimal)}
   *   <li>{@link SettlementInfo#getKey()}
   *   <li>{@link SettlementInfo#getValue()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    SettlementInfo actualSettlementInfo = new SettlementInfo();
    actualSettlementInfo.setKey("Key");
    BigDecimal value = new BigDecimal("2.3");
    actualSettlementInfo.setValue(value);
    String actualKey = actualSettlementInfo.getKey();
    BigDecimal actualValue = actualSettlementInfo.getValue();

    // Assert that nothing has changed
    assertEquals("Key", actualKey);
    assertEquals(new BigDecimal("2.3"), actualValue);
    assertSame(value, actualValue);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link SettlementInfo#SettlementInfo(String, BigDecimal)}
   *   <li>{@link SettlementInfo#setKey(String)}
   *   <li>{@link SettlementInfo#setValue(BigDecimal)}
   *   <li>{@link SettlementInfo#getKey()}
   *   <li>{@link SettlementInfo#getValue()}
   * </ul>
   */
  @Test
  void testGettersAndSetters2() {
    // Arrange and Act
    SettlementInfo actualSettlementInfo = new SettlementInfo("Key", new BigDecimal("2.3"));
    actualSettlementInfo.setKey("Key");
    BigDecimal value = new BigDecimal("2.3");
    actualSettlementInfo.setValue(value);
    String actualKey = actualSettlementInfo.getKey();
    BigDecimal actualValue = actualSettlementInfo.getValue();

    // Assert that nothing has changed
    assertEquals("Key", actualKey);
    assertEquals(new BigDecimal("2.3"), actualValue);
    assertSame(value, actualValue);
  }
}
