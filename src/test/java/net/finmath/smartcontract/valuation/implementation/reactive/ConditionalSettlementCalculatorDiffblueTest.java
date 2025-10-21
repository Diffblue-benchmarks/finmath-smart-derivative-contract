package net.finmath.smartcontract.valuation.implementation.reactive;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import net.finmath.smartcontract.model.ValueResult;
import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationDataItem;
import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationDataItem.Spec;
import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationDataset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
class ConditionalSettlementCalculatorDiffblueTest {
  /**
   * Test {@link ConditionalSettlementCalculator#ConditionalSettlementCalculator(String, BigDecimal)}.
   * <p>
   * Method under test: {@link ConditionalSettlementCalculator#ConditionalSettlementCalculator(String, BigDecimal)}
   */
  @Test
  @DisplayName("Test new ConditionalSettlementCalculator(String, BigDecimal)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ConditionalSettlementCalculator.<init>(String, BigDecimal)"})
  void testNewConditionalSettlementCalculator() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange and Act
    ConditionalSettlementCalculator actualConditionalSettlementCalculator = new ConditionalSettlementCalculator(
        "Sdc XML", new BigDecimal("2.3"));
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    ValueResult actualApplyResult = actualConditionalSettlementCalculator
        .apply(new CalibrationDataset(curveDataPointSet, LocalDate.of(1970, 1, 1).atStartOfDay()));

    // Assert
    assertNull(actualApplyResult.getCurrency());
    assertNull(actualApplyResult.getValuationDate());
    assertNull(actualApplyResult.getValue());
  }

  /**
   * Test {@link ConditionalSettlementCalculator#ConditionalSettlementCalculator(String, BigDecimal)}.
   * <p>
   * Method under test: {@link ConditionalSettlementCalculator#ConditionalSettlementCalculator(String, BigDecimal)}
   */
  @Test
  @DisplayName("Test new ConditionalSettlementCalculator(String, BigDecimal)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ConditionalSettlementCalculator.<init>(String, BigDecimal)"})
  void testNewConditionalSettlementCalculator2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange and Act
    ConditionalSettlementCalculator actualConditionalSettlementCalculator = new ConditionalSettlementCalculator(
        "Sdc XML", new BigDecimal("2.3"));
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    Spec spec = new Spec("yyyyMMdd-HHmmss", "yyyyMMdd-HHmmss", "yyyyMMdd-HHmmss", "yyyyMMdd-HHmmss");

    curveDataPointSet.add(new CalibrationDataItem(spec, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));
    ValueResult actualApplyResult = actualConditionalSettlementCalculator
        .apply(new CalibrationDataset(curveDataPointSet, LocalDate.of(1970, 1, 1).atStartOfDay()));

    // Assert
    assertNull(actualApplyResult.getCurrency());
    assertNull(actualApplyResult.getValuationDate());
    assertNull(actualApplyResult.getValue());
  }

  /**
   * Test {@link ConditionalSettlementCalculator#ConditionalSettlementCalculator(String, BigDecimal)}.
   * <p>
   * Method under test: {@link ConditionalSettlementCalculator#ConditionalSettlementCalculator(String, BigDecimal)}
   */
  @Test
  @DisplayName("Test new ConditionalSettlementCalculator(String, BigDecimal)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ConditionalSettlementCalculator.<init>(String, BigDecimal)"})
  void testNewConditionalSettlementCalculator3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange and Act
    ConditionalSettlementCalculator actualConditionalSettlementCalculator = new ConditionalSettlementCalculator(
        "Sdc XML", new BigDecimal("2.3"));
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    Spec spec = new Spec("yyyyMMdd-HHmmss", null, "yyyyMMdd-HHmmss", "yyyyMMdd-HHmmss");

    curveDataPointSet.add(new CalibrationDataItem(spec, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));
    ValueResult actualApplyResult = actualConditionalSettlementCalculator
        .apply(new CalibrationDataset(curveDataPointSet, LocalDate.of(1970, 1, 1).atStartOfDay()));

    // Assert
    assertNull(actualApplyResult.getCurrency());
    assertNull(actualApplyResult.getValuationDate());
    assertNull(actualApplyResult.getValue());
  }

  /**
   * Test {@link ConditionalSettlementCalculator#apply(CalibrationDataset)} with {@code CalibrationDataset}.
   * <p>
   * Method under test: {@link ConditionalSettlementCalculator#apply(CalibrationDataset)}
   */
  @Test
  @DisplayName("Test apply(CalibrationDataset) with 'CalibrationDataset'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ValueResult ConditionalSettlementCalculator.apply(CalibrationDataset)"})
  void testApplyWithCalibrationDataset() {
    // Arrange
    ConditionalSettlementCalculator conditionalSettlementCalculator = new ConditionalSettlementCalculator("Sdc XML",
        new BigDecimal("2.3"));

    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    Spec spec = new Spec("yyyyMMdd-HHmmss", "yyyyMMdd-HHmmss", "yyyyMMdd-HHmmss", "yyyyMMdd-HHmmss");

    curveDataPointSet.add(new CalibrationDataItem(spec, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));

    // Act
    ValueResult actualApplyResult = conditionalSettlementCalculator
        .apply(new CalibrationDataset(curveDataPointSet, LocalDate.of(1970, 1, 1).atStartOfDay()));

    // Assert
    assertNull(actualApplyResult.getCurrency());
    assertNull(actualApplyResult.getValuationDate());
    assertNull(actualApplyResult.getValue());
  }

  /**
   * Test {@link ConditionalSettlementCalculator#apply(CalibrationDataset)} with {@code CalibrationDataset}.
   * <p>
   * Method under test: {@link ConditionalSettlementCalculator#apply(CalibrationDataset)}
   */
  @Test
  @DisplayName("Test apply(CalibrationDataset) with 'CalibrationDataset'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ValueResult ConditionalSettlementCalculator.apply(CalibrationDataset)"})
  void testApplyWithCalibrationDataset2() {
    // Arrange
    ConditionalSettlementCalculator conditionalSettlementCalculator = new ConditionalSettlementCalculator("Sdc XML",
        new BigDecimal("2.3"));

    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    Spec spec = new Spec("yyyyMMdd-HHmmss", null, "yyyyMMdd-HHmmss", "yyyyMMdd-HHmmss");

    curveDataPointSet.add(new CalibrationDataItem(spec, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));

    // Act
    ValueResult actualApplyResult = conditionalSettlementCalculator
        .apply(new CalibrationDataset(curveDataPointSet, LocalDate.of(1970, 1, 1).atStartOfDay()));

    // Assert
    assertNull(actualApplyResult.getCurrency());
    assertNull(actualApplyResult.getValuationDate());
    assertNull(actualApplyResult.getValue());
  }

  /**
   * Test {@link ConditionalSettlementCalculator#apply(CalibrationDataset)} with {@code CalibrationDataset}.
   * <ul>
   *   <li>Given {@code Serialize To Json}.</li>
   *   <li>Then calls {@link CalibrationDataset#serializeToJson()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ConditionalSettlementCalculator#apply(CalibrationDataset)}
   */
  @Test
  @DisplayName("Test apply(CalibrationDataset) with 'CalibrationDataset'; given 'Serialize To Json'; then calls serializeToJson()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ValueResult ConditionalSettlementCalculator.apply(CalibrationDataset)"})
  void testApplyWithCalibrationDataset_givenSerializeToJson_thenCallsSerializeToJson() {
    // Arrange
    ConditionalSettlementCalculator conditionalSettlementCalculator = new ConditionalSettlementCalculator("Sdc XML",
        new BigDecimal("2.3"));
    CalibrationDataset actualmarketdata = mock(CalibrationDataset.class);
    when(actualmarketdata.serializeToJson()).thenReturn("Serialize To Json");

    // Act
    ValueResult actualApplyResult = conditionalSettlementCalculator.apply(actualmarketdata);

    // Assert
    verify(actualmarketdata).serializeToJson();
    assertNull(actualApplyResult.getCurrency());
    assertNull(actualApplyResult.getValuationDate());
    assertNull(actualApplyResult.getValue());
  }

  /**
   * Test {@link ConditionalSettlementCalculator#apply(CalibrationDataset)} with {@code CalibrationDataset}.
   * <ul>
   *   <li>When {@link LocalDate} with {@code 1970} and one and one atStartOfDay.</li>
   * </ul>
   * <p>
   * Method under test: {@link ConditionalSettlementCalculator#apply(CalibrationDataset)}
   */
  @Test
  @DisplayName("Test apply(CalibrationDataset) with 'CalibrationDataset'; when LocalDate with '1970' and one and one atStartOfDay")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ValueResult ConditionalSettlementCalculator.apply(CalibrationDataset)"})
  void testApplyWithCalibrationDataset_whenLocalDateWith1970AndOneAndOneAtStartOfDay() {
    // Arrange
    ConditionalSettlementCalculator conditionalSettlementCalculator = new ConditionalSettlementCalculator("Sdc XML",
        new BigDecimal("2.3"));
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();

    // Act
    ValueResult actualApplyResult = conditionalSettlementCalculator
        .apply(new CalibrationDataset(curveDataPointSet, LocalDate.of(1970, 1, 1).atStartOfDay()));

    // Assert
    assertNull(actualApplyResult.getCurrency());
    assertNull(actualApplyResult.getValuationDate());
    assertNull(actualApplyResult.getValue());
  }
}
