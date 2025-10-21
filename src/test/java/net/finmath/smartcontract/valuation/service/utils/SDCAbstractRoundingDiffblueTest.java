package net.finmath.smartcontract.valuation.service.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.math.RoundingMode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SDCAbstractRoundingDiffblueTest {
  @InjectMocks
  private SDCRounding sDCRounding;

  /**
   * Test {@link SDCAbstractRounding#roundDouble(double)}.
   * <ul>
   *   <li>Given {@link SDCRounding#SDCRounding(int, RoundingMode)} with sc is one and rc is {@code UP}.</li>
   *   <li>When ten.</li>
   *   <li>Then return ten.</li>
   * </ul>
   * <p>
   * Method under test: {@link SDCAbstractRounding#roundDouble(double)}
   */
  @Test
  @DisplayName("Test roundDouble(double); given SDCRounding(int, RoundingMode) with sc is one and rc is 'UP'; when ten; then return ten")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"double SDCAbstractRounding.roundDouble(double)"})
  void testRoundDouble_givenSDCRoundingWithScIsOneAndRcIsUp_whenTen_thenReturnTen() {
    // Arrange, Act and Assert
    assertEquals(10.0d, (new SDCRounding(1, RoundingMode.UP)).roundDouble(10.0d));
  }

  /**
   * Test {@link SDCAbstractRounding#getRoundedValueAsIntegerString(double)}.
   * <ul>
   *   <li>Then return {@code 100}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SDCAbstractRounding#getRoundedValueAsIntegerString(double)}
   */
  @Test
  @DisplayName("Test getRoundedValueAsIntegerString(double); then return '100'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String SDCAbstractRounding.getRoundedValueAsIntegerString(double)"})
  void testGetRoundedValueAsIntegerString_thenReturn100() {
    // Arrange, Act and Assert
    assertEquals("100", (new SDCRounding(1, RoundingMode.UP)).getRoundedValueAsIntegerString(10.0d));
  }

  /**
   * Test {@link SDCAbstractRounding#getDoubleFromIntegerString(String)}.
   * <ul>
   *   <li>Given {@link SDCRounding#SDCRounding(int, RoundingMode)} with sc is one and rc is {@code UP}.</li>
   *   <li>Then return {@code 0.5}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SDCAbstractRounding#getDoubleFromIntegerString(String)}
   */
  @Test
  @DisplayName("Test getDoubleFromIntegerString(String); given SDCRounding(int, RoundingMode) with sc is one and rc is 'UP'; then return '0.5'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"double SDCAbstractRounding.getDoubleFromIntegerString(String)"})
  void testGetDoubleFromIntegerString_givenSDCRoundingWithScIsOneAndRcIsUp_thenReturn05() {
    // Arrange, Act and Assert
    assertEquals(0.5d, (new SDCRounding(1, RoundingMode.UP)).getDoubleFromIntegerString("42"));
  }
}
