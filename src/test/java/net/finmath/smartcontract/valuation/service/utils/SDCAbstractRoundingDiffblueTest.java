package net.finmath.smartcontract.valuation.service.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.math.RoundingMode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class SDCAbstractRoundingDiffblueTest {
  /**
   * Test {@link SDCAbstractRounding#roundDouble(double)}.
   *
   * <ul>
   *   <li>Given {@link SDCRounding#SDCRounding(int, RoundingMode)} with sc is one and rc is {@code
   *       UP}.
   *   <li>When ten.
   *   <li>Then return ten.
   * </ul>
   *
   * <p>Method under test: {@link SDCAbstractRounding#roundDouble(double)}
   */
  @Test
  @DisplayName(
      "Test roundDouble(double); given SDCRounding(int, RoundingMode) with sc is one and rc is 'UP'; when ten; then return ten")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"double SDCAbstractRounding.roundDouble(double)"})
  void testRoundDouble_givenSDCRoundingWithScIsOneAndRcIsUp_whenTen_thenReturnTen() {
    // Arrange, Act and Assert
    assertEquals(10.0d, new SDCRounding(1, RoundingMode.UP).roundDouble(10.0d));
  }

  /**
   * Test {@link SDCAbstractRounding#getRoundedValueAsIntegerString(double)}.
   *
   * <ul>
   *   <li>Then return {@code 100}.
   * </ul>
   *
   * <p>Method under test: {@link SDCAbstractRounding#getRoundedValueAsIntegerString(double)}
   */
  @Test
  @DisplayName("Test getRoundedValueAsIntegerString(double); then return '100'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String SDCAbstractRounding.getRoundedValueAsIntegerString(double)"})
  void testGetRoundedValueAsIntegerString_thenReturn100() {
    // Arrange, Act and Assert
    assertEquals("100", new SDCRounding(1, RoundingMode.UP).getRoundedValueAsIntegerString(10.0d));
  }

  /**
   * Test {@link SDCAbstractRounding#getDoubleFromIntegerString(String)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then return {@code 0.5}.
   * </ul>
   *
   * <p>Method under test: {@link SDCAbstractRounding#getDoubleFromIntegerString(String)}
   */
  @Test
  @DisplayName("Test getDoubleFromIntegerString(String); when '42'; then return '0.5'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"double SDCAbstractRounding.getDoubleFromIntegerString(String)"})
  void testGetDoubleFromIntegerString_when42_thenReturn05() {
    // Arrange, Act and Assert
    assertEquals(0.5d, new SDCRounding(1, RoundingMode.UP).getDoubleFromIntegerString("42"));
  }

  /**
   * Test {@link SDCAbstractRounding#getDoubleFromIntegerString(String)}.
   *
   * <ul>
   *   <li>When {@code 4242}.
   *   <li>Then return {@code 424.2}.
   * </ul>
   *
   * <p>Method under test: {@link SDCAbstractRounding#getDoubleFromIntegerString(String)}
   */
  @Test
  @DisplayName("Test getDoubleFromIntegerString(String); when '4242'; then return '424.2'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"double SDCAbstractRounding.getDoubleFromIntegerString(String)"})
  void testGetDoubleFromIntegerString_when4242_thenReturn4242() {
    // Arrange, Act and Assert
    assertEquals(424.2d, new SDCRounding(1, RoundingMode.UP).getDoubleFromIntegerString("4242"));
  }
}
