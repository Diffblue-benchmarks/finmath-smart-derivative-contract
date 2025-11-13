package net.finmath.smartcontract.valuation.implementation.reactive;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import net.finmath.smartcontract.model.ValueResult;
import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationDataset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(
    classes = {ConditionalSettlementCalculator.class, String.class, BigDecimal.class})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class ConditionalSettlementCalculatorDiffblueTest {
  @MockBean private BigDecimal bigDecimal;

  @Autowired private ConditionalSettlementCalculator conditionalSettlementCalculator;

  /**
   * Test {@link ConditionalSettlementCalculator#ConditionalSettlementCalculator(String,
   * BigDecimal)}.
   *
   * <p>Method under test: {@link
   * ConditionalSettlementCalculator#ConditionalSettlementCalculator(String, BigDecimal)}
   */
  @Test
  @DisplayName("Test new ConditionalSettlementCalculator(String, BigDecimal)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ConditionalSettlementCalculator.<init>(String, BigDecimal)"})
  void testNewConditionalSettlementCalculator() {
    // Arrange and Act
    ConditionalSettlementCalculator actualConditionalSettlementCalculator =
        new ConditionalSettlementCalculator("Sdc XML", new BigDecimal("2.3"));
    CalibrationDataset actualmarketdata =
        new CalibrationDataset(new HashSet<>(), LocalDate.of(1970, 1, 1).atStartOfDay());
    ValueResult actualApplyResult = actualConditionalSettlementCalculator.apply(actualmarketdata);

    // Assert
    assertNull(actualApplyResult.getCurrency());
    assertNull(actualApplyResult.getValuationDate());
    assertNull(actualApplyResult.getValue());
  }

  /**
   * Test {@link ConditionalSettlementCalculator#ConditionalSettlementCalculator(String,
   * BigDecimal)}.
   *
   * <p>Method under test: {@link
   * ConditionalSettlementCalculator#ConditionalSettlementCalculator(String, BigDecimal)}
   */
  @Test
  @DisplayName("Test new ConditionalSettlementCalculator(String, BigDecimal)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ConditionalSettlementCalculator.<init>(String, BigDecimal)"})
  void testNewConditionalSettlementCalculator2() {
    // Arrange and Act
    ConditionalSettlementCalculator actualConditionalSettlementCalculator =
        new ConditionalSettlementCalculator("Sdc XML", new BigDecimal("2.3"));
    CalibrationDataset actualmarketdata = mock(CalibrationDataset.class);
    when(actualmarketdata.serializeToJson()).thenReturn("Serialize To Json");
    ValueResult actualApplyResult = actualConditionalSettlementCalculator.apply(actualmarketdata);

    // Assert
    verify(actualmarketdata).serializeToJson();
    assertNull(actualApplyResult.getCurrency());
    assertNull(actualApplyResult.getValuationDate());
    assertNull(actualApplyResult.getValue());
  }

  /**
   * Test {@link ConditionalSettlementCalculator#apply(CalibrationDataset)} with {@code
   * CalibrationDataset}.
   *
   * <ul>
   *   <li>Given {@code Serialize To Json}.
   *   <li>Then calls {@link CalibrationDataset#serializeToJson()}.
   * </ul>
   *
   * <p>Method under test: {@link ConditionalSettlementCalculator#apply(CalibrationDataset)}
   */
  @Test
  @DisplayName(
      "Test apply(CalibrationDataset) with 'CalibrationDataset'; given 'Serialize To Json'; then calls serializeToJson()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ValueResult ConditionalSettlementCalculator.apply(CalibrationDataset)"})
  void testApplyWithCalibrationDataset_givenSerializeToJson_thenCallsSerializeToJson() {
    // Arrange
    ConditionalSettlementCalculator conditionalSettlementCalculator =
        new ConditionalSettlementCalculator("Sdc XML", new BigDecimal("2.3"));

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
   * Test {@link ConditionalSettlementCalculator#apply(CalibrationDataset)} with {@code
   * CalibrationDataset}.
   *
   * <ul>
   *   <li>When {@link LocalDate} with {@code 1970} and one and one atStartOfDay.
   * </ul>
   *
   * <p>Method under test: {@link ConditionalSettlementCalculator#apply(CalibrationDataset)}
   */
  @Test
  @DisplayName(
      "Test apply(CalibrationDataset) with 'CalibrationDataset'; when LocalDate with '1970' and one and one atStartOfDay")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ValueResult ConditionalSettlementCalculator.apply(CalibrationDataset)"})
  void testApplyWithCalibrationDataset_whenLocalDateWith1970AndOneAndOneAtStartOfDay() {
    // Arrange
    ConditionalSettlementCalculator conditionalSettlementCalculator =
        new ConditionalSettlementCalculator("Sdc XML", new BigDecimal("2.3"));
    CalibrationDataset actualmarketdata =
        new CalibrationDataset(new HashSet<>(), LocalDate.of(1970, 1, 1).atStartOfDay());

    // Act
    ValueResult actualApplyResult = conditionalSettlementCalculator.apply(actualmarketdata);

    // Assert
    assertNull(actualApplyResult.getCurrency());
    assertNull(actualApplyResult.getValuationDate());
    assertNull(actualApplyResult.getValue());
  }
}
