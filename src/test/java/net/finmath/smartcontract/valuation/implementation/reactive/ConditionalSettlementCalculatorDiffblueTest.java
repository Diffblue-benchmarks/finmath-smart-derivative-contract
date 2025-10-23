package net.finmath.smartcontract.valuation.implementation.reactive;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
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
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class ConditionalSettlementCalculatorDiffblueTest {
  @MockBean private BigDecimal bigDecimal;

  @Autowired private ConditionalSettlementCalculator conditionalSettlementCalculator;

  @InjectMocks private ConditionalSettlementCalculator conditionalSettlementCalculator2;

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
   * <p>Method under test: {@link ConditionalSettlementCalculator#apply(CalibrationDataset)}
   */
  @Test
  @DisplayName("Test apply(CalibrationDataset) with 'CalibrationDataset'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ValueResult ConditionalSettlementCalculator.apply(CalibrationDataset)"})
  void testApplyWithCalibrationDataset() {
    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    Spec spec =
        new Spec("yyyyMMdd-HHmmss", "yyyyMMdd-HHmmss", "yyyyMMdd-HHmmss", "yyyyMMdd-HHmmss");
    curveDataPointSet.add(
        new CalibrationDataItem(spec, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));
    CalibrationDataset actualmarketdata =
        new CalibrationDataset(curveDataPointSet, LocalDate.of(1970, 1, 1).atStartOfDay());

    // Act
    ValueResult actualApplyResult = conditionalSettlementCalculator2.apply(actualmarketdata);

    // Assert
    assertNull(actualApplyResult.getCurrency());
    assertNull(actualApplyResult.getValuationDate());
    assertNull(actualApplyResult.getValue());
  }

  /**
   * Test {@link ConditionalSettlementCalculator#apply(CalibrationDataset)} with {@code
   * CalibrationDataset}.
   *
   * <p>Method under test: {@link ConditionalSettlementCalculator#apply(CalibrationDataset)}
   */
  @Test
  @DisplayName("Test apply(CalibrationDataset) with 'CalibrationDataset'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ValueResult ConditionalSettlementCalculator.apply(CalibrationDataset)"})
  void testApplyWithCalibrationDataset2() {
    // Arrange
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    Spec spec = new Spec("yyyyMMdd-HHmmss", null, "yyyyMMdd-HHmmss", "yyyyMMdd-HHmmss");
    curveDataPointSet.add(
        new CalibrationDataItem(spec, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));
    CalibrationDataset actualmarketdata =
        new CalibrationDataset(curveDataPointSet, LocalDate.of(1970, 1, 1).atStartOfDay());

    // Act
    ValueResult actualApplyResult = conditionalSettlementCalculator2.apply(actualmarketdata);

    // Assert
    assertNull(actualApplyResult.getCurrency());
    assertNull(actualApplyResult.getValuationDate());
    assertNull(actualApplyResult.getValue());
  }

  /**
   * Test {@link ConditionalSettlementCalculator#apply(CalibrationDataset)} with {@code
   * CalibrationDataset}.
   *
   * <ul>
   *   <li>Given {@link BigDecimal#BigDecimal(String)} with {@code 2.3}.
   *   <li>Then return Currency is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ConditionalSettlementCalculator#apply(CalibrationDataset)}
   */
  @Test
  @DisplayName(
      "Test apply(CalibrationDataset) with 'CalibrationDataset'; given BigDecimal(String) with '2.3'; then return Currency is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ValueResult ConditionalSettlementCalculator.apply(CalibrationDataset)"})
  void testApplyWithCalibrationDataset_givenBigDecimalWith23_thenReturnCurrencyIsNull() {
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
   *   <li>Then calls {@link CalibrationDataItem#getProductName()}.
   * </ul>
   *
   * <p>Method under test: {@link ConditionalSettlementCalculator#apply(CalibrationDataset)}
   */
  @Test
  @DisplayName(
      "Test apply(CalibrationDataset) with 'CalibrationDataset'; then calls getProductName()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ValueResult ConditionalSettlementCalculator.apply(CalibrationDataset)"})
  void testApplyWithCalibrationDataset_thenCallsGetProductName() {
    // Arrange
    CalibrationDataItem calibrationDataItem = mock(CalibrationDataItem.class);
    when(calibrationDataItem.getQuote()).thenReturn(10.0d);
    Spec spec = new Spec("Key", "Curve Name", "Product Name", "Maturity");
    when(calibrationDataItem.getSpec()).thenReturn(spec);
    when(calibrationDataItem.getProductName()).thenReturn("Product Name");

    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    curveDataPointSet.add(calibrationDataItem);
    CalibrationDataset actualmarketdata =
        new CalibrationDataset(curveDataPointSet, LocalDate.of(1970, 1, 1).atStartOfDay());

    // Act
    ValueResult actualApplyResult = conditionalSettlementCalculator2.apply(actualmarketdata);

    // Assert
    verify(calibrationDataItem, atLeast(1)).getProductName();
    verify(calibrationDataItem).getQuote();
    verify(calibrationDataItem, atLeast(1)).getSpec();
    assertNull(actualApplyResult.getCurrency());
    assertNull(actualApplyResult.getValuationDate());
    assertNull(actualApplyResult.getValue());
  }
}
