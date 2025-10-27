package net.finmath.smartcontract.product;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import net.finmath.marketdata.products.AnalyticProduct;
import net.finmath.marketdata.products.Swap;
import net.finmath.marketdata.products.SwapLeg;
import net.finmath.modelling.InterestRateProductDescriptor;
import net.finmath.modelling.descriptor.InterestRateSwapLegProductDescriptor;
import net.finmath.modelling.descriptor.InterestRateSwapProductDescriptor;
import net.finmath.modelling.descriptor.ScheduleDescriptor;
import net.finmath.time.Period;
import net.finmath.time.Schedule;
import net.finmath.time.ScheduleFromPeriods;
import net.finmath.time.daycount.DayCountConvention_30E_360;
import net.finmath.time.daycount.DayCountConvention_ACT_360;
import org.junit.jupiter.api.Test;

class IRSwapGeneratorDiffblueTest {
  /**
   * Method under test:
   * {@link IRSwapGenerator#generateAnalyticSwapObject(LocalDate, String, double, double, boolean, String, String)}
   */
  @Test
  void testGenerateAnalyticSwapObject() {
    // Arrange
    LocalDate startDate = LocalDate.of(1970, 1, 1);

    // Act
    Swap actualGenerateAnalyticSwapObjectResult = IRSwapGenerator.generateAnalyticSwapObject(startDate, "42", 10.0d,
        10.0d, true, "Forward Curve Name", "3");

    // Assert
    AnalyticProduct legPayer = actualGenerateAnalyticSwapObjectResult.getLegPayer();
    assertTrue(legPayer instanceof SwapLeg);
    AnalyticProduct legReceiver = actualGenerateAnalyticSwapObjectResult.getLegReceiver();
    assertTrue(legReceiver instanceof SwapLeg);
    InterestRateSwapProductDescriptor descriptor = actualGenerateAnalyticSwapObjectResult.getDescriptor();
    InterestRateProductDescriptor legPayer2 = descriptor.getLegPayer();
    assertTrue(legPayer2 instanceof InterestRateSwapLegProductDescriptor);
    InterestRateProductDescriptor legReceiver2 = descriptor.getLegReceiver();
    assertTrue(legReceiver2 instanceof InterestRateSwapLegProductDescriptor);
    Schedule schedule = ((SwapLeg) legPayer).getSchedule();
    assertTrue(schedule instanceof ScheduleFromPeriods);
    Schedule schedule2 = ((SwapLeg) legReceiver).getSchedule();
    assertTrue(schedule2 instanceof ScheduleFromPeriods);
    assertTrue(schedule2.getDaycountconvention() instanceof DayCountConvention_30E_360);
    assertTrue(schedule.getDaycountconvention() instanceof DayCountConvention_ACT_360);
    assertEquals("", ((SwapLeg) legReceiver).getForwardCurveName());
    InterestRateSwapLegProductDescriptor descriptor2 = ((SwapLeg) legReceiver).getDescriptor();
    assertEquals("", descriptor2.getForwardCurveName());
    assertEquals("", ((InterestRateSwapLegProductDescriptor) legReceiver2).getForwardCurveName());
    LocalDate referenceDate = schedule.getReferenceDate();
    assertEquals("1970-01-01", referenceDate.toString());
    ScheduleDescriptor legScheduleDescriptor = ((InterestRateSwapLegProductDescriptor) legPayer2)
        .getLegScheduleDescriptor();
    List<Period> periods = legScheduleDescriptor.getPeriods();
    assertEquals(42, periods.size());
    Period getResult = periods.get(0);
    LocalDate fixing = getResult.getFixing();
    assertEquals("1970-01-05", fixing.toString());
    LocalDate payment = getResult.getPayment();
    assertEquals("1970-12-28", payment.toString());
    Period getResult2 = periods.get(1);
    LocalDate payment2 = getResult2.getPayment();
    assertEquals("1971-12-27", payment2.toString());
    Period getResult3 = periods.get(40);
    LocalDate fixing2 = getResult3.getFixing();
    assertEquals("2009-12-28", fixing2.toString());
    LocalDate payment3 = getResult3.getPayment();
    assertEquals("2010-12-27", payment3.toString());
    Period getResult4 = periods.get(41);
    LocalDate payment4 = getResult4.getPayment();
    assertEquals("2011-12-27", payment4.toString());
    assertEquals("3", ((SwapLeg) legPayer).getDiscountCurveName());
    assertEquals("3", ((SwapLeg) legReceiver).getDiscountCurveName());
    InterestRateSwapLegProductDescriptor descriptor3 = ((SwapLeg) legPayer).getDescriptor();
    assertEquals("3", descriptor3.getDiscountCurveName());
    assertEquals("3", descriptor2.getDiscountCurveName());
    assertEquals("3", ((InterestRateSwapLegProductDescriptor) legPayer2).getDiscountCurveName());
    assertEquals("3", ((InterestRateSwapLegProductDescriptor) legReceiver2).getDiscountCurveName());
    assertEquals("Forward Curve Name", ((SwapLeg) legPayer).getForwardCurveName());
    assertEquals("Forward Curve Name", descriptor3.getForwardCurveName());
    assertEquals("Forward Curve Name", ((InterestRateSwapLegProductDescriptor) legPayer2).getForwardCurveName());
    assertEquals(0.0d, ((SwapLeg) legPayer).getSpread());
    assertEquals(10.0d, ((SwapLeg) legReceiver).getSpread());
    ScheduleDescriptor legScheduleDescriptor2 = descriptor3.getLegScheduleDescriptor();
    assertEquals(42, legScheduleDescriptor2.getNumberOfPeriods());
    ScheduleDescriptor legScheduleDescriptor3 = descriptor2.getLegScheduleDescriptor();
    assertEquals(42, legScheduleDescriptor3.getNumberOfPeriods());
    assertEquals(42, legScheduleDescriptor.getNumberOfPeriods());
    ScheduleDescriptor legScheduleDescriptor4 = ((InterestRateSwapLegProductDescriptor) legReceiver2)
        .getLegScheduleDescriptor();
    assertEquals(42, legScheduleDescriptor4.getNumberOfPeriods());
    assertEquals(42, schedule.getNumberOfPeriods());
    assertEquals(42, schedule2.getNumberOfPeriods());
    assertFalse(((SwapLeg) legPayer).isNotionalExchanged());
    assertFalse(((SwapLeg) legReceiver).isNotionalExchanged());
    assertFalse(descriptor3.isNotionalExchanged());
    assertFalse(descriptor2.isNotionalExchanged());
    assertFalse(((InterestRateSwapLegProductDescriptor) legPayer2).isNotionalExchanged());
    assertFalse(((InterestRateSwapLegProductDescriptor) legReceiver2).isNotionalExchanged());
    Iterator<Period> iteratorResult = schedule.iterator();
    assertTrue(iteratorResult.hasNext());
    assertTrue(schedule2.iterator().hasNext());
    List<Period> periods2 = legScheduleDescriptor4.getPeriods();
    assertEquals(periods, periods2);
    assertSame(getResult, iteratorResult.next());
    assertSame(getResult2, iteratorResult.next());
    double[] spreads = ((InterestRateSwapLegProductDescriptor) legPayer2).getSpreads();
    assertSame(spreads, descriptor3.getSpreads());
    double[] spreads2 = ((InterestRateSwapLegProductDescriptor) legReceiver2).getSpreads();
    assertSame(spreads2, descriptor2.getSpreads());
    assertSame(periods, legScheduleDescriptor2.getPeriods());
    assertSame(periods, schedule.getPeriods());
    assertSame(periods2, legScheduleDescriptor3.getPeriods());
    assertSame(periods2, schedule2.getPeriods());
    assertSame(fixing, getResult.getPeriodStart());
    assertSame(fixing2, getResult3.getPeriodStart());
    assertSame(payment, getResult2.getFixing());
    assertSame(payment, getResult.getPeriodEnd());
    assertSame(payment, getResult2.getPeriodStart());
    assertSame(payment2, getResult2.getPeriodEnd());
    assertSame(payment3, getResult4.getFixing());
    assertSame(payment3, getResult3.getPeriodEnd());
    assertSame(payment3, getResult4.getPeriodStart());
    assertSame(payment4, getResult4.getPeriodEnd());
    assertSame(startDate, referenceDate);
    assertSame(startDate, schedule2.getReferenceDate());
    assertArrayEquals(new double[]{0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d,
        0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d,
        0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d}, ((SwapLeg) legPayer).getSpreads(), 0.0);
    assertArrayEquals(new double[]{0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d,
        0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d,
        0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d}, spreads, 0.0);
    assertArrayEquals(new double[]{1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d,
        1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d,
        1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d}, descriptor2.getNotionals(), 0.0);
    assertArrayEquals(
        new double[]{1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d,
            1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d,
            1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d},
        ((InterestRateSwapLegProductDescriptor) legReceiver2).getNotionals(), 0.0);
    assertArrayEquals(
        new double[]{10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d,
            10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d,
            10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d},
        ((SwapLeg) legReceiver).getSpreads(), 0.0);
    assertArrayEquals(
        new double[]{10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d,
            10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d,
            10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d},
        descriptor3.getNotionals(), 0.0);
    assertArrayEquals(
        new double[]{10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d,
            10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d,
            10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d},
        ((InterestRateSwapLegProductDescriptor) legPayer2).getNotionals(), 0.0);
    assertArrayEquals(new double[]{10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d,
        10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d,
        10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d}, spreads2,
        0.0);
  }

  /**
   * Method under test:
   * {@link IRSwapGenerator#generateAnalyticSwapObject(LocalDate, String, double, double, boolean, String, String)}
   */
  @Test
  void testGenerateAnalyticSwapObject2() {
    // Arrange
    LocalDate startDate = LocalDate.ofYearDay(2, 2);

    // Act
    Swap actualGenerateAnalyticSwapObjectResult = IRSwapGenerator.generateAnalyticSwapObject(startDate, "42", 10.0d,
        10.0d, true, "Forward Curve Name", "3");

    // Assert
    AnalyticProduct legPayer = actualGenerateAnalyticSwapObjectResult.getLegPayer();
    assertTrue(legPayer instanceof SwapLeg);
    AnalyticProduct legReceiver = actualGenerateAnalyticSwapObjectResult.getLegReceiver();
    assertTrue(legReceiver instanceof SwapLeg);
    InterestRateSwapProductDescriptor descriptor = actualGenerateAnalyticSwapObjectResult.getDescriptor();
    InterestRateProductDescriptor legPayer2 = descriptor.getLegPayer();
    assertTrue(legPayer2 instanceof InterestRateSwapLegProductDescriptor);
    InterestRateProductDescriptor legReceiver2 = descriptor.getLegReceiver();
    assertTrue(legReceiver2 instanceof InterestRateSwapLegProductDescriptor);
    Schedule schedule = ((SwapLeg) legPayer).getSchedule();
    assertTrue(schedule instanceof ScheduleFromPeriods);
    Schedule schedule2 = ((SwapLeg) legReceiver).getSchedule();
    assertTrue(schedule2 instanceof ScheduleFromPeriods);
    assertTrue(schedule2.getDaycountconvention() instanceof DayCountConvention_30E_360);
    assertTrue(schedule.getDaycountconvention() instanceof DayCountConvention_ACT_360);
    assertEquals("", ((SwapLeg) legReceiver).getForwardCurveName());
    InterestRateSwapLegProductDescriptor descriptor2 = ((SwapLeg) legReceiver).getDescriptor();
    assertEquals("", descriptor2.getForwardCurveName());
    assertEquals("", ((InterestRateSwapLegProductDescriptor) legReceiver2).getForwardCurveName());
    LocalDate referenceDate = schedule.getReferenceDate();
    assertEquals("0002-01-02", referenceDate.toString());
    ScheduleDescriptor legScheduleDescriptor = ((InterestRateSwapLegProductDescriptor) legPayer2)
        .getLegScheduleDescriptor();
    List<Period> periods = legScheduleDescriptor.getPeriods();
    assertEquals(42, periods.size());
    Period getResult = periods.get(0);
    LocalDate fixing = getResult.getFixing();
    assertEquals("0002-01-04", fixing.toString());
    LocalDate payment = getResult.getPayment();
    assertEquals("0002-12-27", payment.toString());
    Period getResult2 = periods.get(1);
    LocalDate payment2 = getResult2.getPayment();
    assertEquals("0003-12-29", payment2.toString());
    Period getResult3 = periods.get(40);
    LocalDate fixing2 = getResult3.getFixing();
    assertEquals("0041-12-27", fixing2.toString());
    LocalDate payment3 = getResult3.getPayment();
    assertEquals("0042-12-29", payment3.toString());
    Period getResult4 = periods.get(41);
    LocalDate payment4 = getResult4.getPayment();
    assertEquals("0043-12-28", payment4.toString());
    assertEquals("3", ((SwapLeg) legPayer).getDiscountCurveName());
    assertEquals("3", ((SwapLeg) legReceiver).getDiscountCurveName());
    InterestRateSwapLegProductDescriptor descriptor3 = ((SwapLeg) legPayer).getDescriptor();
    assertEquals("3", descriptor3.getDiscountCurveName());
    assertEquals("3", descriptor2.getDiscountCurveName());
    assertEquals("3", ((InterestRateSwapLegProductDescriptor) legPayer2).getDiscountCurveName());
    assertEquals("3", ((InterestRateSwapLegProductDescriptor) legReceiver2).getDiscountCurveName());
    assertEquals("Forward Curve Name", ((SwapLeg) legPayer).getForwardCurveName());
    assertEquals("Forward Curve Name", descriptor3.getForwardCurveName());
    assertEquals("Forward Curve Name", ((InterestRateSwapLegProductDescriptor) legPayer2).getForwardCurveName());
    assertEquals(0.0d, ((SwapLeg) legPayer).getSpread());
    assertEquals(10.0d, ((SwapLeg) legReceiver).getSpread());
    ScheduleDescriptor legScheduleDescriptor2 = descriptor3.getLegScheduleDescriptor();
    assertEquals(42, legScheduleDescriptor2.getNumberOfPeriods());
    ScheduleDescriptor legScheduleDescriptor3 = descriptor2.getLegScheduleDescriptor();
    assertEquals(42, legScheduleDescriptor3.getNumberOfPeriods());
    assertEquals(42, legScheduleDescriptor.getNumberOfPeriods());
    ScheduleDescriptor legScheduleDescriptor4 = ((InterestRateSwapLegProductDescriptor) legReceiver2)
        .getLegScheduleDescriptor();
    assertEquals(42, legScheduleDescriptor4.getNumberOfPeriods());
    assertEquals(42, schedule.getNumberOfPeriods());
    assertEquals(42, schedule2.getNumberOfPeriods());
    assertFalse(((SwapLeg) legPayer).isNotionalExchanged());
    assertFalse(((SwapLeg) legReceiver).isNotionalExchanged());
    assertFalse(descriptor3.isNotionalExchanged());
    assertFalse(descriptor2.isNotionalExchanged());
    assertFalse(((InterestRateSwapLegProductDescriptor) legPayer2).isNotionalExchanged());
    assertFalse(((InterestRateSwapLegProductDescriptor) legReceiver2).isNotionalExchanged());
    Iterator<Period> iteratorResult = schedule.iterator();
    assertTrue(iteratorResult.hasNext());
    assertTrue(schedule2.iterator().hasNext());
    List<Period> periods2 = legScheduleDescriptor4.getPeriods();
    assertEquals(periods, periods2);
    assertSame(getResult, iteratorResult.next());
    assertSame(getResult2, iteratorResult.next());
    double[] spreads = ((InterestRateSwapLegProductDescriptor) legPayer2).getSpreads();
    assertSame(spreads, descriptor3.getSpreads());
    double[] spreads2 = ((InterestRateSwapLegProductDescriptor) legReceiver2).getSpreads();
    assertSame(spreads2, descriptor2.getSpreads());
    assertSame(periods, legScheduleDescriptor2.getPeriods());
    assertSame(periods, schedule.getPeriods());
    assertSame(periods2, legScheduleDescriptor3.getPeriods());
    assertSame(periods2, schedule2.getPeriods());
    assertSame(fixing, getResult.getPeriodStart());
    assertSame(fixing2, getResult3.getPeriodStart());
    assertSame(payment, getResult2.getFixing());
    assertSame(payment, getResult.getPeriodEnd());
    assertSame(payment, getResult2.getPeriodStart());
    assertSame(payment2, getResult2.getPeriodEnd());
    assertSame(payment3, getResult4.getFixing());
    assertSame(payment3, getResult3.getPeriodEnd());
    assertSame(payment3, getResult4.getPeriodStart());
    assertSame(payment4, getResult4.getPeriodEnd());
    assertSame(startDate, referenceDate);
    assertSame(startDate, schedule2.getReferenceDate());
    assertArrayEquals(new double[]{0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d,
        0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d,
        0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d}, ((SwapLeg) legPayer).getSpreads(), 0.0);
    assertArrayEquals(new double[]{0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d,
        0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d,
        0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d}, spreads, 0.0);
    assertArrayEquals(new double[]{1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d,
        1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d,
        1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d}, descriptor2.getNotionals(), 0.0);
    assertArrayEquals(
        new double[]{1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d,
            1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d,
            1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d},
        ((InterestRateSwapLegProductDescriptor) legReceiver2).getNotionals(), 0.0);
    assertArrayEquals(
        new double[]{10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d,
            10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d,
            10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d},
        ((SwapLeg) legReceiver).getSpreads(), 0.0);
    assertArrayEquals(
        new double[]{10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d,
            10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d,
            10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d},
        descriptor3.getNotionals(), 0.0);
    assertArrayEquals(
        new double[]{10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d,
            10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d,
            10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d},
        ((InterestRateSwapLegProductDescriptor) legPayer2).getNotionals(), 0.0);
    assertArrayEquals(new double[]{10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d,
        10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d,
        10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d}, spreads2,
        0.0);
  }

  /**
   * Method under test:
   * {@link IRSwapGenerator#generateAnalyticSwapObject(LocalDate, String, double, double, boolean, String, String)}
   */
  @Test
  void testGenerateAnalyticSwapObject3() {
    // Arrange
    LocalDate startDate = LocalDate.of(1970, 1, 1);

    // Act
    Swap actualGenerateAnalyticSwapObjectResult = IRSwapGenerator.generateAnalyticSwapObject(startDate, "42", 10.0d,
        10.0d, true, "3M", "3");

    // Assert
    AnalyticProduct legPayer = actualGenerateAnalyticSwapObjectResult.getLegPayer();
    assertTrue(legPayer instanceof SwapLeg);
    AnalyticProduct legReceiver = actualGenerateAnalyticSwapObjectResult.getLegReceiver();
    assertTrue(legReceiver instanceof SwapLeg);
    InterestRateSwapProductDescriptor descriptor = actualGenerateAnalyticSwapObjectResult.getDescriptor();
    InterestRateProductDescriptor legPayer2 = descriptor.getLegPayer();
    assertTrue(legPayer2 instanceof InterestRateSwapLegProductDescriptor);
    InterestRateProductDescriptor legReceiver2 = descriptor.getLegReceiver();
    assertTrue(legReceiver2 instanceof InterestRateSwapLegProductDescriptor);
    Schedule schedule = ((SwapLeg) legPayer).getSchedule();
    assertTrue(schedule instanceof ScheduleFromPeriods);
    Schedule schedule2 = ((SwapLeg) legReceiver).getSchedule();
    assertTrue(schedule2 instanceof ScheduleFromPeriods);
    assertTrue(schedule2.getDaycountconvention() instanceof DayCountConvention_30E_360);
    assertTrue(schedule.getDaycountconvention() instanceof DayCountConvention_ACT_360);
    assertEquals("", ((SwapLeg) legReceiver).getForwardCurveName());
    InterestRateSwapLegProductDescriptor descriptor2 = ((SwapLeg) legReceiver).getDescriptor();
    assertEquals("", descriptor2.getForwardCurveName());
    assertEquals("", ((InterestRateSwapLegProductDescriptor) legReceiver2).getForwardCurveName());
    LocalDate referenceDate = schedule.getReferenceDate();
    assertEquals("1970-01-01", referenceDate.toString());
    ScheduleDescriptor legScheduleDescriptor = ((InterestRateSwapLegProductDescriptor) legPayer2)
        .getLegScheduleDescriptor();
    List<Period> periods = legScheduleDescriptor.getPeriods();
    assertEquals(168, periods.size());
    Period getResult = periods.get(0);
    LocalDate fixing = getResult.getFixing();
    assertEquals("1970-01-05", fixing.toString());
    ScheduleDescriptor legScheduleDescriptor2 = ((InterestRateSwapLegProductDescriptor) legReceiver2)
        .getLegScheduleDescriptor();
    List<Period> periods2 = legScheduleDescriptor2.getPeriods();
    assertEquals(42, periods2.size());
    Period getResult2 = periods2.get(0);
    LocalDate fixing2 = getResult2.getFixing();
    assertEquals("1970-01-05", fixing2.toString());
    LocalDate payment = getResult.getPayment();
    assertEquals("1970-03-26", payment.toString());
    Period getResult3 = periods.get(1);
    LocalDate payment2 = getResult3.getPayment();
    assertEquals("1970-06-26", payment2.toString());
    LocalDate payment3 = getResult2.getPayment();
    assertEquals("1970-12-28", payment3.toString());
    Period getResult4 = periods2.get(1);
    LocalDate payment4 = getResult4.getPayment();
    assertEquals("1971-12-27", payment4.toString());
    Period getResult5 = periods2.get(40);
    LocalDate fixing3 = getResult5.getFixing();
    assertEquals("2009-12-28", fixing3.toString());
    LocalDate payment5 = getResult5.getPayment();
    assertEquals("2010-12-27", payment5.toString());
    Period getResult6 = periods.get(166);
    LocalDate fixing4 = getResult6.getFixing();
    assertEquals("2011-06-27", fixing4.toString());
    LocalDate payment6 = getResult6.getPayment();
    assertEquals("2011-09-26", payment6.toString());
    Period getResult7 = periods.get(167);
    LocalDate payment7 = getResult7.getPayment();
    assertEquals("2011-12-27", payment7.toString());
    Period getResult8 = periods2.get(41);
    LocalDate payment8 = getResult8.getPayment();
    assertEquals("2011-12-27", payment8.toString());
    assertEquals("3", ((SwapLeg) legPayer).getDiscountCurveName());
    assertEquals("3", ((SwapLeg) legReceiver).getDiscountCurveName());
    InterestRateSwapLegProductDescriptor descriptor3 = ((SwapLeg) legPayer).getDescriptor();
    assertEquals("3", descriptor3.getDiscountCurveName());
    assertEquals("3", descriptor2.getDiscountCurveName());
    assertEquals("3", ((InterestRateSwapLegProductDescriptor) legPayer2).getDiscountCurveName());
    assertEquals("3", ((InterestRateSwapLegProductDescriptor) legReceiver2).getDiscountCurveName());
    assertEquals("3M", ((SwapLeg) legPayer).getForwardCurveName());
    assertEquals("3M", descriptor3.getForwardCurveName());
    assertEquals("3M", ((InterestRateSwapLegProductDescriptor) legPayer2).getForwardCurveName());
    assertEquals(0.0d, ((SwapLeg) legPayer).getSpread());
    double[] spreads = ((SwapLeg) legPayer).getSpreads();
    assertEquals(0.0d, spreads[0]);
    assertEquals(0.0d, spreads[1]);
    assertEquals(0.0d, spreads[10]);
    assertEquals(0.0d, spreads[11]);
    assertEquals(0.0d, spreads[12]);
    assertEquals(0.0d, spreads[13]);
    assertEquals(0.0d, spreads[14]);
    assertEquals(0.0d, spreads[143]);
    assertEquals(0.0d, spreads[144]);
    assertEquals(0.0d, spreads[145]);
    assertEquals(0.0d, spreads[146]);
    assertEquals(0.0d, spreads[147]);
    assertEquals(0.0d, spreads[148]);
    assertEquals(0.0d, spreads[149]);
    assertEquals(0.0d, spreads[15]);
    assertEquals(0.0d, spreads[150]);
    assertEquals(0.0d, spreads[151]);
    assertEquals(0.0d, spreads[152]);
    assertEquals(0.0d, spreads[153]);
    assertEquals(0.0d, spreads[154]);
    assertEquals(0.0d, spreads[155]);
    assertEquals(0.0d, spreads[156]);
    assertEquals(0.0d, spreads[157]);
    assertEquals(0.0d, spreads[158]);
    assertEquals(0.0d, spreads[159]);
    assertEquals(0.0d, spreads[160]);
    assertEquals(0.0d, spreads[161]);
    assertEquals(0.0d, spreads[162]);
    assertEquals(0.0d, spreads[163]);
    assertEquals(0.0d, spreads[164]);
    assertEquals(0.0d, spreads[165]);
    assertEquals(0.0d, spreads[166]);
    assertEquals(0.0d, spreads[167]);
    assertEquals(0.0d, spreads[17]);
    assertEquals(0.0d, spreads[18]);
    assertEquals(0.0d, spreads[19]);
    assertEquals(0.0d, spreads[2]);
    assertEquals(0.0d, spreads[20]);
    assertEquals(0.0d, spreads[21]);
    assertEquals(0.0d, spreads[22]);
    assertEquals(0.0d, spreads[23]);
    assertEquals(0.0d, spreads[24]);
    assertEquals(0.0d, spreads[3]);
    assertEquals(0.0d, spreads[4]);
    assertEquals(0.0d, spreads[5]);
    assertEquals(0.0d, spreads[6]);
    assertEquals(0.0d, spreads[7]);
    assertEquals(0.0d, spreads[8]);
    assertEquals(0.0d, spreads[9]);
    assertEquals(0.0d, spreads[Short.SIZE]);
    double[] spreads2 = ((InterestRateSwapLegProductDescriptor) legPayer2).getSpreads();
    assertEquals(0.0d, spreads2[0]);
    assertEquals(0.0d, spreads2[1]);
    assertEquals(0.0d, spreads2[10]);
    assertEquals(0.0d, spreads2[11]);
    assertEquals(0.0d, spreads2[12]);
    assertEquals(0.0d, spreads2[13]);
    assertEquals(0.0d, spreads2[14]);
    assertEquals(0.0d, spreads2[143]);
    assertEquals(0.0d, spreads2[144]);
    assertEquals(0.0d, spreads2[145]);
    assertEquals(0.0d, spreads2[146]);
    assertEquals(0.0d, spreads2[147]);
    assertEquals(0.0d, spreads2[148]);
    assertEquals(0.0d, spreads2[149]);
    assertEquals(0.0d, spreads2[15]);
    assertEquals(0.0d, spreads2[150]);
    assertEquals(0.0d, spreads2[151]);
    assertEquals(0.0d, spreads2[152]);
    assertEquals(0.0d, spreads2[153]);
    assertEquals(0.0d, spreads2[154]);
    assertEquals(0.0d, spreads2[155]);
    assertEquals(0.0d, spreads2[156]);
    assertEquals(0.0d, spreads2[157]);
    assertEquals(0.0d, spreads2[158]);
    assertEquals(0.0d, spreads2[159]);
    assertEquals(0.0d, spreads2[160]);
    assertEquals(0.0d, spreads2[161]);
    assertEquals(0.0d, spreads2[162]);
    assertEquals(0.0d, spreads2[163]);
    assertEquals(0.0d, spreads2[164]);
    assertEquals(0.0d, spreads2[165]);
    assertEquals(0.0d, spreads2[166]);
    assertEquals(0.0d, spreads2[167]);
    assertEquals(0.0d, spreads2[17]);
    assertEquals(0.0d, spreads2[18]);
    assertEquals(0.0d, spreads2[19]);
    assertEquals(0.0d, spreads2[2]);
    assertEquals(0.0d, spreads2[20]);
    assertEquals(0.0d, spreads2[21]);
    assertEquals(0.0d, spreads2[22]);
    assertEquals(0.0d, spreads2[23]);
    assertEquals(0.0d, spreads2[24]);
    assertEquals(0.0d, spreads2[3]);
    assertEquals(0.0d, spreads2[4]);
    assertEquals(0.0d, spreads2[5]);
    assertEquals(0.0d, spreads2[6]);
    assertEquals(0.0d, spreads2[7]);
    assertEquals(0.0d, spreads2[8]);
    assertEquals(0.0d, spreads2[9]);
    assertEquals(0.0d, spreads2[Short.SIZE]);
    assertEquals(10.0d, ((SwapLeg) legReceiver).getSpread());
    double[] notionals = descriptor3.getNotionals();
    assertEquals(10.0d, notionals[0]);
    assertEquals(10.0d, notionals[1]);
    assertEquals(10.0d, notionals[10]);
    assertEquals(10.0d, notionals[11]);
    assertEquals(10.0d, notionals[12]);
    assertEquals(10.0d, notionals[13]);
    assertEquals(10.0d, notionals[14]);
    assertEquals(10.0d, notionals[143]);
    assertEquals(10.0d, notionals[144]);
    assertEquals(10.0d, notionals[145]);
    assertEquals(10.0d, notionals[146]);
    assertEquals(10.0d, notionals[147]);
    assertEquals(10.0d, notionals[148]);
    assertEquals(10.0d, notionals[149]);
    assertEquals(10.0d, notionals[15]);
    assertEquals(10.0d, notionals[150]);
    assertEquals(10.0d, notionals[151]);
    assertEquals(10.0d, notionals[152]);
    assertEquals(10.0d, notionals[153]);
    assertEquals(10.0d, notionals[154]);
    assertEquals(10.0d, notionals[155]);
    assertEquals(10.0d, notionals[156]);
    assertEquals(10.0d, notionals[157]);
    assertEquals(10.0d, notionals[158]);
    assertEquals(10.0d, notionals[159]);
    assertEquals(10.0d, notionals[160]);
    assertEquals(10.0d, notionals[161]);
    assertEquals(10.0d, notionals[162]);
    assertEquals(10.0d, notionals[163]);
    assertEquals(10.0d, notionals[164]);
    assertEquals(10.0d, notionals[165]);
    assertEquals(10.0d, notionals[166]);
    assertEquals(10.0d, notionals[167]);
    assertEquals(10.0d, notionals[17]);
    assertEquals(10.0d, notionals[18]);
    assertEquals(10.0d, notionals[19]);
    assertEquals(10.0d, notionals[2]);
    assertEquals(10.0d, notionals[20]);
    assertEquals(10.0d, notionals[21]);
    assertEquals(10.0d, notionals[22]);
    assertEquals(10.0d, notionals[23]);
    assertEquals(10.0d, notionals[24]);
    assertEquals(10.0d, notionals[3]);
    assertEquals(10.0d, notionals[4]);
    assertEquals(10.0d, notionals[5]);
    assertEquals(10.0d, notionals[6]);
    assertEquals(10.0d, notionals[7]);
    assertEquals(10.0d, notionals[8]);
    assertEquals(10.0d, notionals[9]);
    assertEquals(10.0d, notionals[Short.SIZE]);
    double[] notionals2 = ((InterestRateSwapLegProductDescriptor) legPayer2).getNotionals();
    assertEquals(10.0d, notionals2[0]);
    assertEquals(10.0d, notionals2[1]);
    assertEquals(10.0d, notionals2[10]);
    assertEquals(10.0d, notionals2[11]);
    assertEquals(10.0d, notionals2[12]);
    assertEquals(10.0d, notionals2[13]);
    assertEquals(10.0d, notionals2[14]);
    assertEquals(10.0d, notionals2[143]);
    assertEquals(10.0d, notionals2[144]);
    assertEquals(10.0d, notionals2[145]);
    assertEquals(10.0d, notionals2[146]);
    assertEquals(10.0d, notionals2[147]);
    assertEquals(10.0d, notionals2[148]);
    assertEquals(10.0d, notionals2[149]);
    assertEquals(10.0d, notionals2[15]);
    assertEquals(10.0d, notionals2[150]);
    assertEquals(10.0d, notionals2[151]);
    assertEquals(10.0d, notionals2[152]);
    assertEquals(10.0d, notionals2[153]);
    assertEquals(10.0d, notionals2[154]);
    assertEquals(10.0d, notionals2[155]);
    assertEquals(10.0d, notionals2[156]);
    assertEquals(10.0d, notionals2[157]);
    assertEquals(10.0d, notionals2[158]);
    assertEquals(10.0d, notionals2[159]);
    assertEquals(10.0d, notionals2[160]);
    assertEquals(10.0d, notionals2[161]);
    assertEquals(10.0d, notionals2[162]);
    assertEquals(10.0d, notionals2[163]);
    assertEquals(10.0d, notionals2[164]);
    assertEquals(10.0d, notionals2[165]);
    assertEquals(10.0d, notionals2[166]);
    assertEquals(10.0d, notionals2[167]);
    assertEquals(10.0d, notionals2[17]);
    assertEquals(10.0d, notionals2[18]);
    assertEquals(10.0d, notionals2[19]);
    assertEquals(10.0d, notionals2[2]);
    assertEquals(10.0d, notionals2[20]);
    assertEquals(10.0d, notionals2[21]);
    assertEquals(10.0d, notionals2[22]);
    assertEquals(10.0d, notionals2[23]);
    assertEquals(10.0d, notionals2[24]);
    assertEquals(10.0d, notionals2[3]);
    assertEquals(10.0d, notionals2[4]);
    assertEquals(10.0d, notionals2[5]);
    assertEquals(10.0d, notionals2[6]);
    assertEquals(10.0d, notionals2[7]);
    assertEquals(10.0d, notionals2[8]);
    assertEquals(10.0d, notionals2[9]);
    assertEquals(10.0d, notionals2[Short.SIZE]);
    ScheduleDescriptor legScheduleDescriptor3 = descriptor3.getLegScheduleDescriptor();
    assertEquals(168, legScheduleDescriptor3.getNumberOfPeriods());
    assertEquals(168, legScheduleDescriptor.getNumberOfPeriods());
    assertEquals(168, schedule.getNumberOfPeriods());
    assertEquals(168, spreads.length);
    assertEquals(168, notionals.length);
    assertEquals(168, notionals2.length);
    assertEquals(168, spreads2.length);
    ScheduleDescriptor legScheduleDescriptor4 = descriptor2.getLegScheduleDescriptor();
    assertEquals(42, legScheduleDescriptor4.getNumberOfPeriods());
    assertEquals(42, legScheduleDescriptor2.getNumberOfPeriods());
    assertEquals(42, schedule2.getNumberOfPeriods());
    assertFalse(((SwapLeg) legPayer).isNotionalExchanged());
    assertFalse(((SwapLeg) legReceiver).isNotionalExchanged());
    assertFalse(descriptor3.isNotionalExchanged());
    assertFalse(descriptor2.isNotionalExchanged());
    assertFalse(((InterestRateSwapLegProductDescriptor) legPayer2).isNotionalExchanged());
    assertFalse(((InterestRateSwapLegProductDescriptor) legReceiver2).isNotionalExchanged());
    Iterator<Period> iteratorResult = schedule.iterator();
    assertTrue(iteratorResult.hasNext());
    Iterator<Period> iteratorResult2 = schedule2.iterator();
    assertTrue(iteratorResult2.hasNext());
    assertSame(getResult, iteratorResult.next());
    assertSame(getResult3, iteratorResult.next());
    assertSame(getResult2, iteratorResult2.next());
    assertSame(getResult4, iteratorResult2.next());
    assertSame(spreads2, descriptor3.getSpreads());
    double[] spreads3 = ((InterestRateSwapLegProductDescriptor) legReceiver2).getSpreads();
    assertSame(spreads3, descriptor2.getSpreads());
    assertSame(periods, legScheduleDescriptor3.getPeriods());
    assertSame(periods, schedule.getPeriods());
    assertSame(periods2, legScheduleDescriptor4.getPeriods());
    assertSame(periods2, schedule2.getPeriods());
    assertSame(fixing, getResult.getPeriodStart());
    assertSame(fixing4, getResult6.getPeriodStart());
    assertSame(fixing2, getResult2.getPeriodStart());
    assertSame(fixing3, getResult5.getPeriodStart());
    assertSame(payment, getResult3.getFixing());
    assertSame(payment, getResult.getPeriodEnd());
    assertSame(payment, getResult3.getPeriodStart());
    assertSame(payment2, getResult3.getPeriodEnd());
    assertSame(payment6, getResult7.getFixing());
    assertSame(payment6, getResult6.getPeriodEnd());
    assertSame(payment6, getResult7.getPeriodStart());
    assertSame(payment7, getResult7.getPeriodEnd());
    assertSame(payment3, getResult4.getFixing());
    assertSame(payment3, getResult2.getPeriodEnd());
    assertSame(payment3, getResult4.getPeriodStart());
    assertSame(payment4, getResult4.getPeriodEnd());
    assertSame(payment5, getResult8.getFixing());
    assertSame(payment5, getResult5.getPeriodEnd());
    assertSame(payment5, getResult8.getPeriodStart());
    assertSame(payment8, getResult8.getPeriodEnd());
    assertSame(startDate, referenceDate);
    assertSame(startDate, schedule2.getReferenceDate());
    assertArrayEquals(new double[]{1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d,
        1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d,
        1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d}, descriptor2.getNotionals(), 0.0);
    assertArrayEquals(
        new double[]{1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d,
            1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d,
            1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d},
        ((InterestRateSwapLegProductDescriptor) legReceiver2).getNotionals(), 0.0);
    assertArrayEquals(
        new double[]{10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d,
            10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d,
            10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d},
        ((SwapLeg) legReceiver).getSpreads(), 0.0);
    assertArrayEquals(new double[]{10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d,
        10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d,
        10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d}, spreads3,
        0.0);
  }
}
