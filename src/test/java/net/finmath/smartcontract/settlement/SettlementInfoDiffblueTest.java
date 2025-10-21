package net.finmath.smartcontract.settlement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class SettlementInfoDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
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
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SettlementInfo.<init>()", "void SettlementInfo.<init>(String, BigDecimal)",
      "String SettlementInfo.getKey()", "BigDecimal SettlementInfo.getValue()", "void SettlementInfo.setKey(String)",
      "void SettlementInfo.setValue(BigDecimal)"})
  void testGettersAndSetters() {
    // Arrange and Act
    SettlementInfo actualSettlementInfo = new SettlementInfo();
    actualSettlementInfo.setKey("Key");
    BigDecimal value = new BigDecimal("2.3");
    actualSettlementInfo.setValue(value);
    String actualKey = actualSettlementInfo.getKey();
    BigDecimal actualValue = actualSettlementInfo.getValue();

    // Assert
    assertEquals("Key", actualKey);
    assertEquals(new BigDecimal("2.3"), actualValue);
    assertSame(value, actualValue);
  }

  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@code Key}.</li>
   * </ul>
   * <p>
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
  @DisplayName("Test getters and setters; when 'Key'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SettlementInfo.<init>()", "void SettlementInfo.<init>(String, BigDecimal)",
      "String SettlementInfo.getKey()", "BigDecimal SettlementInfo.getValue()", "void SettlementInfo.setKey(String)",
      "void SettlementInfo.setValue(BigDecimal)"})
  void testGettersAndSetters_whenKey() {
    // Arrange and Act
    SettlementInfo actualSettlementInfo = new SettlementInfo("Key", new BigDecimal("2.3"));
    actualSettlementInfo.setKey("Key");
    BigDecimal value = new BigDecimal("2.3");
    actualSettlementInfo.setValue(value);
    String actualKey = actualSettlementInfo.getKey();
    BigDecimal actualValue = actualSettlementInfo.getValue();

    // Assert
    assertEquals("Key", actualKey);
    assertEquals(new BigDecimal("2.3"), actualValue);
    assertSame(value, actualValue);
  }
}
