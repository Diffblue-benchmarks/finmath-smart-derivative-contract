package net.finmath.smartcontract.product;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import net.finmath.marketdata.products.AnalyticProduct;
import net.finmath.marketdata.products.Swap;
import net.finmath.marketdata.products.SwapLeg;
import net.finmath.modelling.descriptor.InterestRateSwapLegProductDescriptor;
import net.finmath.modelling.descriptor.InterestRateSwapProductDescriptor;
import net.finmath.time.ScheduleFromPeriods;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class IRSwapGeneratorDiffblueTest {
  /**
   * Test {@link IRSwapGenerator#generateAnalyticSwapObject(LocalDate, String, double, double,
   * boolean, String, String)}.
   *
   * <p>Method under test: {@link IRSwapGenerator#generateAnalyticSwapObject(LocalDate, String,
   * double, double, boolean, String, String)}
   */
  @Test
  @DisplayName(
      "Test generateAnalyticSwapObject(LocalDate, String, double, double, boolean, String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Swap IRSwapGenerator.generateAnalyticSwapObject(LocalDate, String, double, double, boolean, String, String)"
  })
  void testGenerateAnalyticSwapObject() {
    // Arrange and Act
    Swap actualGenerateAnalyticSwapObjectResult =
        IRSwapGenerator.generateAnalyticSwapObject(
            LocalDate.of(1970, 1, 1),
            "42",
            10.0d,
            10.0d,
            true,
            "\"EUR-OIS-ForwardCurve\"",
            "\"EUR-OIS-Discount\"");

    // Assert
    AnalyticProduct legPayer = actualGenerateAnalyticSwapObjectResult.getLegPayer();
    assertTrue(legPayer instanceof SwapLeg);
    AnalyticProduct legReceiver = actualGenerateAnalyticSwapObjectResult.getLegReceiver();
    assertTrue(legReceiver instanceof SwapLeg);
    InterestRateSwapProductDescriptor descriptor =
        actualGenerateAnalyticSwapObjectResult.getDescriptor();
    assertTrue(descriptor.getLegPayer() instanceof InterestRateSwapLegProductDescriptor);
    assertTrue(descriptor.getLegReceiver() instanceof InterestRateSwapLegProductDescriptor);
    assertTrue(((SwapLeg) legPayer).getSchedule() instanceof ScheduleFromPeriods);
    assertTrue(((SwapLeg) legReceiver).getSchedule() instanceof ScheduleFromPeriods);
    assertArrayEquals(
        new double[] {
          0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d,
          0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d,
          0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d
        },
        ((SwapLeg) legPayer).getSpreads(),
        0.0);
    assertArrayEquals(
        new double[] {
          10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d,
          10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d,
          10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d,
          10.0d, 10.0d, 10.0d
        },
        ((SwapLeg) legReceiver).getSpreads(),
        0.0);
  }

  /**
   * Test {@link IRSwapGenerator#generateAnalyticSwapObject(LocalDate, String, double, double,
   * boolean, String, String)}.
   *
   * <ul>
   *   <li>Then return array length is five hundred four.
   * </ul>
   *
   * <p>Method under test: {@link IRSwapGenerator#generateAnalyticSwapObject(LocalDate, String,
   * double, double, boolean, String, String)}
   */
  @Test
  @DisplayName(
      "Test generateAnalyticSwapObject(LocalDate, String, double, double, boolean, String, String); then return array length is five hundred four")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Swap IRSwapGenerator.generateAnalyticSwapObject(LocalDate, String, double, double, boolean, String, String)"
  })
  void testGenerateAnalyticSwapObject_thenReturnArrayLengthIsFiveHundredFour() {
    // Arrange and Act
    Swap actualGenerateAnalyticSwapObjectResult =
        IRSwapGenerator.generateAnalyticSwapObject(
            LocalDate.ofYearDay(2, 8), "42", 10.0d, 10.0d, true, "1M", "\"EUR-OIS-Discount\"");

    // Assert
    AnalyticProduct legPayer = actualGenerateAnalyticSwapObjectResult.getLegPayer();
    assertTrue(legPayer instanceof SwapLeg);
    AnalyticProduct legReceiver = actualGenerateAnalyticSwapObjectResult.getLegReceiver();
    assertTrue(legReceiver instanceof SwapLeg);
    InterestRateSwapProductDescriptor descriptor =
        actualGenerateAnalyticSwapObjectResult.getDescriptor();
    assertTrue(descriptor.getLegPayer() instanceof InterestRateSwapLegProductDescriptor);
    assertTrue(descriptor.getLegReceiver() instanceof InterestRateSwapLegProductDescriptor);
    assertTrue(((SwapLeg) legPayer).getSchedule() instanceof ScheduleFromPeriods);
    assertTrue(((SwapLeg) legReceiver).getSchedule() instanceof ScheduleFromPeriods);
    assertEquals(504, ((SwapLeg) legPayer).getSpreads().length);
    assertArrayEquals(
        new double[] {
          10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d,
          10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d,
          10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d,
          10.0d, 10.0d, 10.0d
        },
        ((SwapLeg) legReceiver).getSpreads(),
        0.0);
  }

  /**
   * Test {@link IRSwapGenerator#generateAnalyticSwapObject(LocalDate, String, double, double,
   * boolean, String, String)}.
   *
   * <ul>
   *   <li>Then return array length is one hundred sixty-eight.
   * </ul>
   *
   * <p>Method under test: {@link IRSwapGenerator#generateAnalyticSwapObject(LocalDate, String,
   * double, double, boolean, String, String)}
   */
  @Test
  @DisplayName(
      "Test generateAnalyticSwapObject(LocalDate, String, double, double, boolean, String, String); then return array length is one hundred sixty-eight")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Swap IRSwapGenerator.generateAnalyticSwapObject(LocalDate, String, double, double, boolean, String, String)"
  })
  void testGenerateAnalyticSwapObject_thenReturnArrayLengthIsOneHundredSixtyEight() {
    // Arrange and Act
    Swap actualGenerateAnalyticSwapObjectResult =
        IRSwapGenerator.generateAnalyticSwapObject(
            LocalDate.of(1970, 1, 1), "42", 10.0d, 10.0d, true, "3M", "\"EUR-OIS-Discount\"");

    // Assert
    AnalyticProduct legPayer = actualGenerateAnalyticSwapObjectResult.getLegPayer();
    assertTrue(legPayer instanceof SwapLeg);
    AnalyticProduct legReceiver = actualGenerateAnalyticSwapObjectResult.getLegReceiver();
    assertTrue(legReceiver instanceof SwapLeg);
    InterestRateSwapProductDescriptor descriptor =
        actualGenerateAnalyticSwapObjectResult.getDescriptor();
    assertTrue(descriptor.getLegPayer() instanceof InterestRateSwapLegProductDescriptor);
    assertTrue(descriptor.getLegReceiver() instanceof InterestRateSwapLegProductDescriptor);
    assertTrue(((SwapLeg) legPayer).getSchedule() instanceof ScheduleFromPeriods);
    assertTrue(((SwapLeg) legReceiver).getSchedule() instanceof ScheduleFromPeriods);
    assertEquals(168, ((SwapLeg) legPayer).getSpreads().length);
    assertArrayEquals(
        new double[] {
          10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d,
          10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d,
          10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d,
          10.0d, 10.0d, 10.0d
        },
        ((SwapLeg) legReceiver).getSpreads(),
        0.0);
  }

  /**
   * Test {@link IRSwapGenerator#generateAnalyticSwapObject(LocalDate, String, double, double,
   * boolean, String, String)}.
   *
   * <ul>
   *   <li>Then return LegPayer DiscountCurveName is empty string.
   * </ul>
   *
   * <p>Method under test: {@link IRSwapGenerator#generateAnalyticSwapObject(LocalDate, String,
   * double, double, boolean, String, String)}
   */
  @Test
  @DisplayName(
      "Test generateAnalyticSwapObject(LocalDate, String, double, double, boolean, String, String); then return LegPayer DiscountCurveName is empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Swap IRSwapGenerator.generateAnalyticSwapObject(LocalDate, String, double, double, boolean, String, String)"
  })
  void testGenerateAnalyticSwapObject_thenReturnLegPayerDiscountCurveNameIsEmptyString() {
    // Arrange and Act
    Swap actualGenerateAnalyticSwapObjectResult =
        IRSwapGenerator.generateAnalyticSwapObject(
            LocalDate.of(1970, 1, 1), "3M", 10.0d, 10.0d, true, "\"EUR-OIS-ForwardCurve\"", "");

    // Assert
    AnalyticProduct legPayer = actualGenerateAnalyticSwapObjectResult.getLegPayer();
    assertTrue(legPayer instanceof SwapLeg);
    AnalyticProduct legReceiver = actualGenerateAnalyticSwapObjectResult.getLegReceiver();
    assertTrue(legReceiver instanceof SwapLeg);
    assertEquals("", ((SwapLeg) legPayer).getDiscountCurveName());
    assertEquals("", ((SwapLeg) legReceiver).getDiscountCurveName());
    assertArrayEquals(new double[] {0.0d}, ((SwapLeg) legPayer).getSpreads(), 0.0);
    assertArrayEquals(new double[] {10.0d}, ((SwapLeg) legReceiver).getSpreads(), 0.0);
  }

  /**
   * Test {@link IRSwapGenerator#generateAnalyticSwapObject(LocalDate, String, double, double,
   * boolean, String, String)}.
   *
   * <ul>
   *   <li>Then return LegPayer ForwardCurveName is empty string.
   * </ul>
   *
   * <p>Method under test: {@link IRSwapGenerator#generateAnalyticSwapObject(LocalDate, String,
   * double, double, boolean, String, String)}
   */
  @Test
  @DisplayName(
      "Test generateAnalyticSwapObject(LocalDate, String, double, double, boolean, String, String); then return LegPayer ForwardCurveName is empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Swap IRSwapGenerator.generateAnalyticSwapObject(LocalDate, String, double, double, boolean, String, String)"
  })
  void testGenerateAnalyticSwapObject_thenReturnLegPayerForwardCurveNameIsEmptyString() {
    // Arrange and Act
    Swap actualGenerateAnalyticSwapObjectResult =
        IRSwapGenerator.generateAnalyticSwapObject(
            LocalDate.of(1970, 1, 1),
            "3M",
            10.0d,
            10.0d,
            false,
            "\"EUR-OIS-ForwardCurve\"",
            "\"EUR-OIS-Discount\"");

    // Assert
    AnalyticProduct legPayer = actualGenerateAnalyticSwapObjectResult.getLegPayer();
    assertTrue(legPayer instanceof SwapLeg);
    AnalyticProduct legReceiver = actualGenerateAnalyticSwapObjectResult.getLegReceiver();
    assertTrue(legReceiver instanceof SwapLeg);
    assertEquals("", ((SwapLeg) legPayer).getForwardCurveName());
    assertEquals("\"EUR-OIS-ForwardCurve\"", ((SwapLeg) legReceiver).getForwardCurveName());
    assertEquals(0.0d, ((SwapLeg) legReceiver).getSpread());
    assertEquals(10.0d, ((SwapLeg) legPayer).getSpread());
    assertArrayEquals(new double[] {0.0d}, ((SwapLeg) legReceiver).getSpreads(), 0.0);
    assertArrayEquals(new double[] {10.0d}, ((SwapLeg) legPayer).getSpreads(), 0.0);
  }

  /**
   * Test {@link IRSwapGenerator#generateAnalyticSwapObject(LocalDate, String, double, double,
   * boolean, String, String)}.
   *
   * <ul>
   *   <li>Then return LegPayer Spreads is array of {@code double} with zero.
   * </ul>
   *
   * <p>Method under test: {@link IRSwapGenerator#generateAnalyticSwapObject(LocalDate, String,
   * double, double, boolean, String, String)}
   */
  @Test
  @DisplayName(
      "Test generateAnalyticSwapObject(LocalDate, String, double, double, boolean, String, String); then return LegPayer Spreads is array of double with zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Swap IRSwapGenerator.generateAnalyticSwapObject(LocalDate, String, double, double, boolean, String, String)"
  })
  void testGenerateAnalyticSwapObject_thenReturnLegPayerSpreadsIsArrayOfDoubleWithZero() {
    // Arrange and Act
    Swap actualGenerateAnalyticSwapObjectResult =
        IRSwapGenerator.generateAnalyticSwapObject(
            LocalDate.of(1970, 1, 1),
            "3M",
            10.0d,
            10.0d,
            true,
            "\"EUR-OIS-ForwardCurve\"",
            "\"EUR-OIS-Discount\"");

    // Assert
    AnalyticProduct legPayer = actualGenerateAnalyticSwapObjectResult.getLegPayer();
    assertTrue(legPayer instanceof SwapLeg);
    AnalyticProduct legReceiver = actualGenerateAnalyticSwapObjectResult.getLegReceiver();
    assertTrue(legReceiver instanceof SwapLeg);
    InterestRateSwapProductDescriptor descriptor =
        actualGenerateAnalyticSwapObjectResult.getDescriptor();
    assertTrue(descriptor.getLegPayer() instanceof InterestRateSwapLegProductDescriptor);
    assertTrue(descriptor.getLegReceiver() instanceof InterestRateSwapLegProductDescriptor);
    assertTrue(((SwapLeg) legPayer).getSchedule() instanceof ScheduleFromPeriods);
    assertTrue(((SwapLeg) legReceiver).getSchedule() instanceof ScheduleFromPeriods);
    assertArrayEquals(new double[] {0.0d}, ((SwapLeg) legPayer).getSpreads(), 0.0);
    assertArrayEquals(new double[] {10.0d}, ((SwapLeg) legReceiver).getSpreads(), 0.0);
  }

  /**
   * Test {@link IRSwapGenerator#generateAnalyticSwapObject(LocalDate, String, double, double,
   * boolean, String, String)}.
   *
   * <ul>
   *   <li>Then return LegPayer Spreads is array of {@code double} with zero.
   * </ul>
   *
   * <p>Method under test: {@link IRSwapGenerator#generateAnalyticSwapObject(LocalDate, String,
   * double, double, boolean, String, String)}
   */
  @Test
  @DisplayName(
      "Test generateAnalyticSwapObject(LocalDate, String, double, double, boolean, String, String); then return LegPayer Spreads is array of double with zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Swap IRSwapGenerator.generateAnalyticSwapObject(LocalDate, String, double, double, boolean, String, String)"
  })
  void testGenerateAnalyticSwapObject_thenReturnLegPayerSpreadsIsArrayOfDoubleWithZero2() {
    // Arrange and Act
    Swap actualGenerateAnalyticSwapObjectResult =
        IRSwapGenerator.generateAnalyticSwapObject(
            LocalDate.of(1970, 1, 1), "3M", 10.0d, 10.0d, true, "3M", "\"EUR-OIS-Discount\"");

    // Assert
    AnalyticProduct legPayer = actualGenerateAnalyticSwapObjectResult.getLegPayer();
    assertTrue(legPayer instanceof SwapLeg);
    AnalyticProduct legReceiver = actualGenerateAnalyticSwapObjectResult.getLegReceiver();
    assertTrue(legReceiver instanceof SwapLeg);
    InterestRateSwapProductDescriptor descriptor =
        actualGenerateAnalyticSwapObjectResult.getDescriptor();
    assertTrue(descriptor.getLegPayer() instanceof InterestRateSwapLegProductDescriptor);
    assertTrue(descriptor.getLegReceiver() instanceof InterestRateSwapLegProductDescriptor);
    assertTrue(((SwapLeg) legPayer).getSchedule() instanceof ScheduleFromPeriods);
    assertTrue(((SwapLeg) legReceiver).getSchedule() instanceof ScheduleFromPeriods);
    assertArrayEquals(new double[] {0.0d}, ((SwapLeg) legPayer).getSpreads(), 0.0);
    assertArrayEquals(new double[] {10.0d}, ((SwapLeg) legReceiver).getSpreads(), 0.0);
  }

  /**
   * Test {@link IRSwapGenerator#generateAnalyticSwapObject(LocalDate, String, double, double,
   * boolean, String, String)}.
   *
   * <ul>
   *   <li>When {@code 1M}.
   *   <li>Then return LegPayer ForwardCurveName is {@code 1M}.
   * </ul>
   *
   * <p>Method under test: {@link IRSwapGenerator#generateAnalyticSwapObject(LocalDate, String,
   * double, double, boolean, String, String)}
   */
  @Test
  @DisplayName(
      "Test generateAnalyticSwapObject(LocalDate, String, double, double, boolean, String, String); when '1M'; then return LegPayer ForwardCurveName is '1M'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Swap IRSwapGenerator.generateAnalyticSwapObject(LocalDate, String, double, double, boolean, String, String)"
  })
  void testGenerateAnalyticSwapObject_when1m_thenReturnLegPayerForwardCurveNameIs1m() {
    // Arrange and Act
    Swap actualGenerateAnalyticSwapObjectResult =
        IRSwapGenerator.generateAnalyticSwapObject(
            LocalDate.of(1970, 1, 1), "3M", 10.0d, 10.0d, true, "1M", "\"EUR-OIS-Discount\"");

    // Assert
    AnalyticProduct legPayer = actualGenerateAnalyticSwapObjectResult.getLegPayer();
    assertTrue(legPayer instanceof SwapLeg);
    AnalyticProduct legReceiver = actualGenerateAnalyticSwapObjectResult.getLegReceiver();
    assertTrue(legReceiver instanceof SwapLeg);
    InterestRateSwapProductDescriptor descriptor =
        actualGenerateAnalyticSwapObjectResult.getDescriptor();
    assertTrue(descriptor.getLegPayer() instanceof InterestRateSwapLegProductDescriptor);
    assertTrue(descriptor.getLegReceiver() instanceof InterestRateSwapLegProductDescriptor);
    assertTrue(((SwapLeg) legPayer).getSchedule() instanceof ScheduleFromPeriods);
    assertTrue(((SwapLeg) legReceiver).getSchedule() instanceof ScheduleFromPeriods);
    assertEquals("1M", ((SwapLeg) legPayer).getForwardCurveName());
    assertArrayEquals(new double[] {10.0d}, ((SwapLeg) legReceiver).getSpreads(), 0.0);
    assertArrayEquals(new double[] {0.0d, 0.0d, 0.0d}, ((SwapLeg) legPayer).getSpreads(), 0.0);
  }

  /**
   * Test {@link IRSwapGenerator#generateAnalyticSwapObject(LocalDate, String, double, double,
   * boolean, String, String)}.
   *
   * <ul>
   *   <li>When {@code 6M}.
   *   <li>Then return LegPayer ForwardCurveName is {@code 6M}.
   * </ul>
   *
   * <p>Method under test: {@link IRSwapGenerator#generateAnalyticSwapObject(LocalDate, String,
   * double, double, boolean, String, String)}
   */
  @Test
  @DisplayName(
      "Test generateAnalyticSwapObject(LocalDate, String, double, double, boolean, String, String); when '6M'; then return LegPayer ForwardCurveName is '6M'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Swap IRSwapGenerator.generateAnalyticSwapObject(LocalDate, String, double, double, boolean, String, String)"
  })
  void testGenerateAnalyticSwapObject_when6m_thenReturnLegPayerForwardCurveNameIs6m() {
    // Arrange and Act
    Swap actualGenerateAnalyticSwapObjectResult =
        IRSwapGenerator.generateAnalyticSwapObject(
            LocalDate.of(1970, 1, 1), "3M", 10.0d, 10.0d, true, "6M", "\"EUR-OIS-Discount\"");

    // Assert
    AnalyticProduct legPayer = actualGenerateAnalyticSwapObjectResult.getLegPayer();
    assertTrue(legPayer instanceof SwapLeg);
    AnalyticProduct legReceiver = actualGenerateAnalyticSwapObjectResult.getLegReceiver();
    assertTrue(legReceiver instanceof SwapLeg);
    InterestRateSwapProductDescriptor descriptor =
        actualGenerateAnalyticSwapObjectResult.getDescriptor();
    assertTrue(descriptor.getLegPayer() instanceof InterestRateSwapLegProductDescriptor);
    assertTrue(descriptor.getLegReceiver() instanceof InterestRateSwapLegProductDescriptor);
    assertTrue(((SwapLeg) legPayer).getSchedule() instanceof ScheduleFromPeriods);
    assertTrue(((SwapLeg) legReceiver).getSchedule() instanceof ScheduleFromPeriods);
    assertEquals("6M", ((SwapLeg) legPayer).getForwardCurveName());
    assertArrayEquals(new double[] {0.0d}, ((SwapLeg) legPayer).getSpreads(), 0.0);
    assertArrayEquals(new double[] {10.0d}, ((SwapLeg) legReceiver).getSpreads(), 0.0);
  }

  /**
   * Test {@link IRSwapGenerator#generateAnalyticSwapObject(LocalDate, String, double, double,
   * boolean, String, String)}.
   *
   * <ul>
   *   <li>When ofYearDay two and eight.
   * </ul>
   *
   * <p>Method under test: {@link IRSwapGenerator#generateAnalyticSwapObject(LocalDate, String,
   * double, double, boolean, String, String)}
   */
  @Test
  @DisplayName(
      "Test generateAnalyticSwapObject(LocalDate, String, double, double, boolean, String, String); when ofYearDay two and eight")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Swap IRSwapGenerator.generateAnalyticSwapObject(LocalDate, String, double, double, boolean, String, String)"
  })
  void testGenerateAnalyticSwapObject_whenOfYearDayTwoAndEight() {
    // Arrange and Act
    Swap actualGenerateAnalyticSwapObjectResult =
        IRSwapGenerator.generateAnalyticSwapObject(
            LocalDate.ofYearDay(2, 8),
            "42",
            10.0d,
            10.0d,
            true,
            "\"EUR-OIS-ForwardCurve\"",
            "\"EUR-OIS-Discount\"");

    // Assert
    AnalyticProduct legPayer = actualGenerateAnalyticSwapObjectResult.getLegPayer();
    assertTrue(legPayer instanceof SwapLeg);
    AnalyticProduct legReceiver = actualGenerateAnalyticSwapObjectResult.getLegReceiver();
    assertTrue(legReceiver instanceof SwapLeg);
    InterestRateSwapProductDescriptor descriptor =
        actualGenerateAnalyticSwapObjectResult.getDescriptor();
    assertTrue(descriptor.getLegPayer() instanceof InterestRateSwapLegProductDescriptor);
    assertTrue(descriptor.getLegReceiver() instanceof InterestRateSwapLegProductDescriptor);
    assertTrue(((SwapLeg) legPayer).getSchedule() instanceof ScheduleFromPeriods);
    assertTrue(((SwapLeg) legReceiver).getSchedule() instanceof ScheduleFromPeriods);
    assertArrayEquals(
        new double[] {
          0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d,
          0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d,
          0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d
        },
        ((SwapLeg) legPayer).getSpreads(),
        0.0);
    assertArrayEquals(
        new double[] {
          10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d,
          10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d,
          10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d,
          10.0d, 10.0d, 10.0d
        },
        ((SwapLeg) legReceiver).getSpreads(),
        0.0);
  }

  /**
   * Test {@link IRSwapGenerator#generateAnalyticSwapObject(LocalDate, String, double, double,
   * boolean, String, String)}.
   *
   * <ul>
   *   <li>When ofYearDay two and eight.
   * </ul>
   *
   * <p>Method under test: {@link IRSwapGenerator#generateAnalyticSwapObject(LocalDate, String,
   * double, double, boolean, String, String)}
   */
  @Test
  @DisplayName(
      "Test generateAnalyticSwapObject(LocalDate, String, double, double, boolean, String, String); when ofYearDay two and eight")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Swap IRSwapGenerator.generateAnalyticSwapObject(LocalDate, String, double, double, boolean, String, String)"
  })
  void testGenerateAnalyticSwapObject_whenOfYearDayTwoAndEight2() {
    // Arrange and Act
    Swap actualGenerateAnalyticSwapObjectResult =
        IRSwapGenerator.generateAnalyticSwapObject(
            LocalDate.ofYearDay(2, 8), "42", 10.0d, 10.0d, true, "3M", "\"EUR-OIS-Discount\"");

    // Assert
    AnalyticProduct legPayer = actualGenerateAnalyticSwapObjectResult.getLegPayer();
    assertTrue(legPayer instanceof SwapLeg);
    AnalyticProduct legReceiver = actualGenerateAnalyticSwapObjectResult.getLegReceiver();
    assertTrue(legReceiver instanceof SwapLeg);
    InterestRateSwapProductDescriptor descriptor =
        actualGenerateAnalyticSwapObjectResult.getDescriptor();
    assertTrue(descriptor.getLegPayer() instanceof InterestRateSwapLegProductDescriptor);
    assertTrue(descriptor.getLegReceiver() instanceof InterestRateSwapLegProductDescriptor);
    assertTrue(((SwapLeg) legPayer).getSchedule() instanceof ScheduleFromPeriods);
    assertTrue(((SwapLeg) legReceiver).getSchedule() instanceof ScheduleFromPeriods);
    assertArrayEquals(
        new double[] {
          10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d,
          10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d,
          10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d,
          10.0d, 10.0d, 10.0d
        },
        ((SwapLeg) legReceiver).getSpreads(),
        0.0);
  }

  /**
   * Test {@link IRSwapGenerator#generateAnalyticSwapObject(LocalDate, String, double, double,
   * boolean, String, String)}.
   *
   * <ul>
   *   <li>When ofYearDay two and one hundred.
   * </ul>
   *
   * <p>Method under test: {@link IRSwapGenerator#generateAnalyticSwapObject(LocalDate, String,
   * double, double, boolean, String, String)}
   */
  @Test
  @DisplayName(
      "Test generateAnalyticSwapObject(LocalDate, String, double, double, boolean, String, String); when ofYearDay two and one hundred")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Swap IRSwapGenerator.generateAnalyticSwapObject(LocalDate, String, double, double, boolean, String, String)"
  })
  void testGenerateAnalyticSwapObject_whenOfYearDayTwoAndOneHundred() {
    // Arrange and Act
    Swap actualGenerateAnalyticSwapObjectResult =
        IRSwapGenerator.generateAnalyticSwapObject(
            LocalDate.ofYearDay(2, 100),
            "3M",
            10.0d,
            10.0d,
            true,
            "\"EUR-OIS-ForwardCurve\"",
            "\"EUR-OIS-Discount\"");

    // Assert
    AnalyticProduct legPayer = actualGenerateAnalyticSwapObjectResult.getLegPayer();
    assertTrue(legPayer instanceof SwapLeg);
    AnalyticProduct legReceiver = actualGenerateAnalyticSwapObjectResult.getLegReceiver();
    assertTrue(legReceiver instanceof SwapLeg);
    InterestRateSwapProductDescriptor descriptor =
        actualGenerateAnalyticSwapObjectResult.getDescriptor();
    assertTrue(descriptor.getLegPayer() instanceof InterestRateSwapLegProductDescriptor);
    assertTrue(descriptor.getLegReceiver() instanceof InterestRateSwapLegProductDescriptor);
    assertTrue(((SwapLeg) legPayer).getSchedule() instanceof ScheduleFromPeriods);
    assertTrue(((SwapLeg) legReceiver).getSchedule() instanceof ScheduleFromPeriods);
    assertArrayEquals(new double[] {0.0d}, ((SwapLeg) legPayer).getSpreads(), 0.0);
    assertArrayEquals(new double[] {10.0d}, ((SwapLeg) legReceiver).getSpreads(), 0.0);
  }

  /**
   * Test {@link IRSwapGenerator#generateAnalyticSwapObject(LocalDate, String, double, double,
   * boolean, String, String)}.
   *
   * <ul>
   *   <li>When ofYearDay two and two.
   * </ul>
   *
   * <p>Method under test: {@link IRSwapGenerator#generateAnalyticSwapObject(LocalDate, String,
   * double, double, boolean, String, String)}
   */
  @Test
  @DisplayName(
      "Test generateAnalyticSwapObject(LocalDate, String, double, double, boolean, String, String); when ofYearDay two and two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Swap IRSwapGenerator.generateAnalyticSwapObject(LocalDate, String, double, double, boolean, String, String)"
  })
  void testGenerateAnalyticSwapObject_whenOfYearDayTwoAndTwo() {
    // Arrange and Act
    Swap actualGenerateAnalyticSwapObjectResult =
        IRSwapGenerator.generateAnalyticSwapObject(
            LocalDate.ofYearDay(2, 2),
            "3M",
            10.0d,
            10.0d,
            true,
            "\"EUR-OIS-ForwardCurve\"",
            "\"EUR-OIS-Discount\"");

    // Assert
    AnalyticProduct legPayer = actualGenerateAnalyticSwapObjectResult.getLegPayer();
    assertTrue(legPayer instanceof SwapLeg);
    AnalyticProduct legReceiver = actualGenerateAnalyticSwapObjectResult.getLegReceiver();
    assertTrue(legReceiver instanceof SwapLeg);
    InterestRateSwapProductDescriptor descriptor =
        actualGenerateAnalyticSwapObjectResult.getDescriptor();
    assertTrue(descriptor.getLegPayer() instanceof InterestRateSwapLegProductDescriptor);
    assertTrue(descriptor.getLegReceiver() instanceof InterestRateSwapLegProductDescriptor);
    assertTrue(((SwapLeg) legPayer).getSchedule() instanceof ScheduleFromPeriods);
    assertTrue(((SwapLeg) legReceiver).getSchedule() instanceof ScheduleFromPeriods);
    assertArrayEquals(new double[] {0.0d}, ((SwapLeg) legPayer).getSpreads(), 0.0);
    assertArrayEquals(new double[] {10.0d}, ((SwapLeg) legReceiver).getSpreads(), 0.0);
  }

  /**
   * Test {@link IRSwapGenerator#generateAnalyticSwapObject(LocalDate, String, double, double,
   * boolean, String, String)}.
   *
   * <ul>
   *   <li>When ofYearDay two and two.
   * </ul>
   *
   * <p>Method under test: {@link IRSwapGenerator#generateAnalyticSwapObject(LocalDate, String,
   * double, double, boolean, String, String)}
   */
  @Test
  @DisplayName(
      "Test generateAnalyticSwapObject(LocalDate, String, double, double, boolean, String, String); when ofYearDay two and two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Swap IRSwapGenerator.generateAnalyticSwapObject(LocalDate, String, double, double, boolean, String, String)"
  })
  void testGenerateAnalyticSwapObject_whenOfYearDayTwoAndTwo2() {
    // Arrange and Act
    Swap actualGenerateAnalyticSwapObjectResult =
        IRSwapGenerator.generateAnalyticSwapObject(
            LocalDate.ofYearDay(2, 2),
            "42",
            10.0d,
            10.0d,
            true,
            "\"EUR-OIS-ForwardCurve\"",
            "\"EUR-OIS-Discount\"");

    // Assert
    AnalyticProduct legPayer = actualGenerateAnalyticSwapObjectResult.getLegPayer();
    assertTrue(legPayer instanceof SwapLeg);
    AnalyticProduct legReceiver = actualGenerateAnalyticSwapObjectResult.getLegReceiver();
    assertTrue(legReceiver instanceof SwapLeg);
    InterestRateSwapProductDescriptor descriptor =
        actualGenerateAnalyticSwapObjectResult.getDescriptor();
    assertTrue(descriptor.getLegPayer() instanceof InterestRateSwapLegProductDescriptor);
    assertTrue(descriptor.getLegReceiver() instanceof InterestRateSwapLegProductDescriptor);
    assertTrue(((SwapLeg) legPayer).getSchedule() instanceof ScheduleFromPeriods);
    assertTrue(((SwapLeg) legReceiver).getSchedule() instanceof ScheduleFromPeriods);
    assertArrayEquals(
        new double[] {
          0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d,
          0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d,
          0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d
        },
        ((SwapLeg) legPayer).getSpreads(),
        0.0);
    assertArrayEquals(
        new double[] {
          10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d,
          10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d,
          10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d,
          10.0d, 10.0d, 10.0d
        },
        ((SwapLeg) legReceiver).getSpreads(),
        0.0);
  }

  /**
   * Test {@link IRSwapGenerator#generateAnalyticSwapObject(LocalDate, String, double, double,
   * boolean, String, String)}.
   *
   * <ul>
   *   <li>When ofYearDay two and two.
   * </ul>
   *
   * <p>Method under test: {@link IRSwapGenerator#generateAnalyticSwapObject(LocalDate, String,
   * double, double, boolean, String, String)}
   */
  @Test
  @DisplayName(
      "Test generateAnalyticSwapObject(LocalDate, String, double, double, boolean, String, String); when ofYearDay two and two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Swap IRSwapGenerator.generateAnalyticSwapObject(LocalDate, String, double, double, boolean, String, String)"
  })
  void testGenerateAnalyticSwapObject_whenOfYearDayTwoAndTwo3() {
    // Arrange and Act
    Swap actualGenerateAnalyticSwapObjectResult =
        IRSwapGenerator.generateAnalyticSwapObject(
            LocalDate.ofYearDay(2, 2), "42", 10.0d, 10.0d, true, "3M", "\"EUR-OIS-Discount\"");

    // Assert
    AnalyticProduct legPayer = actualGenerateAnalyticSwapObjectResult.getLegPayer();
    assertTrue(legPayer instanceof SwapLeg);
    AnalyticProduct legReceiver = actualGenerateAnalyticSwapObjectResult.getLegReceiver();
    assertTrue(legReceiver instanceof SwapLeg);
    InterestRateSwapProductDescriptor descriptor =
        actualGenerateAnalyticSwapObjectResult.getDescriptor();
    assertTrue(descriptor.getLegPayer() instanceof InterestRateSwapLegProductDescriptor);
    assertTrue(descriptor.getLegReceiver() instanceof InterestRateSwapLegProductDescriptor);
    assertTrue(((SwapLeg) legPayer).getSchedule() instanceof ScheduleFromPeriods);
    assertTrue(((SwapLeg) legReceiver).getSchedule() instanceof ScheduleFromPeriods);
    assertArrayEquals(
        new double[] {
          10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d,
          10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d,
          10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d,
          10.0d, 10.0d, 10.0d
        },
        ((SwapLeg) legReceiver).getSpreads(),
        0.0);
  }
}
