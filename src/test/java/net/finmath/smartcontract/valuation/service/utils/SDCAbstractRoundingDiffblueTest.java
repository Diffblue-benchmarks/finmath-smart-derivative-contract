package net.finmath.smartcontract.valuation.service.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.math.RoundingMode;
import org.junit.jupiter.api.Test;

class SDCAbstractRoundingDiffblueTest {
  /**
   * Method under test: {@link SDCAbstractRounding#roundDouble(double)}
   */
  @Test
  void testRoundDouble() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange, Act and Assert
    assertEquals(10.0d, (new SDCRounding(1, RoundingMode.UP)).roundDouble(10.0d));
  }

  /**
   * Method under test:
   * {@link SDCAbstractRounding#getRoundedValueAsIntegerString(double)}
   */
  @Test
  void testGetRoundedValueAsIntegerString() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange, Act and Assert
    assertEquals("100", (new SDCRounding(1, RoundingMode.UP)).getRoundedValueAsIntegerString(10.0d));
  }

  /**
   * Method under test:
   * {@link SDCAbstractRounding#getDoubleFromIntegerString(String)}
   */
  @Test
  void testGetDoubleFromIntegerString() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange, Act and Assert
    assertEquals(0.5d, (new SDCRounding(1, RoundingMode.UP)).getDoubleFromIntegerString("42"));
  }
}
