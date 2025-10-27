package net.finmath.smartcontract.valuation.implementation.reactive;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import net.finmath.smartcontract.model.ValueResult;
import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationDataItem;
import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationDataset;
import org.junit.jupiter.api.Test;

class ConditionalSettlementCalculatorDiffblueTest {
  /**
   * Method under test:
   * {@link ConditionalSettlementCalculator#apply(CalibrationDataset)}
   */
  @Test
  void testApply() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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

  /**
   * Method under test:
   * {@link ConditionalSettlementCalculator#apply(CalibrationDataset)}
   */
  @Test
  void testApply2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ConditionalSettlementCalculator conditionalSettlementCalculator = new ConditionalSettlementCalculator("Sdc XML",
        new BigDecimal("2.3"));

    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("yyyyMMdd-HHmmss", "yyyyMMdd-HHmmss",
        "yyyyMMdd-HHmmss", "yyyyMMdd-HHmmss");

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
   * Method under test:
   * {@link ConditionalSettlementCalculator#apply(CalibrationDataset)}
   */
  @Test
  void testApply3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ConditionalSettlementCalculator conditionalSettlementCalculator = new ConditionalSettlementCalculator("Sdc XML",
        new BigDecimal("2.3"));

    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("yyyyMMdd-HHmmss", null, "yyyyMMdd-HHmmss",
        "yyyyMMdd-HHmmss");

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
   * Method under test:
   * {@link ConditionalSettlementCalculator#apply(CalibrationDataset)}
   */
  @Test
  void testApply4() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
   * Method under test:
   * {@link ConditionalSettlementCalculator#ConditionalSettlementCalculator(String, BigDecimal)}
   */
  @Test
  void testNewConditionalSettlementCalculator() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
   * Method under test:
   * {@link ConditionalSettlementCalculator#ConditionalSettlementCalculator(String, BigDecimal)}
   */
  @Test
  void testNewConditionalSettlementCalculator2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange and Act
    ConditionalSettlementCalculator actualConditionalSettlementCalculator = new ConditionalSettlementCalculator(
        "Sdc XML", new BigDecimal("2.3"));
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("yyyyMMdd-HHmmss", "yyyyMMdd-HHmmss",
        "yyyyMMdd-HHmmss", "yyyyMMdd-HHmmss");

    curveDataPointSet.add(new CalibrationDataItem(spec, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));
    ValueResult actualApplyResult = actualConditionalSettlementCalculator
        .apply(new CalibrationDataset(curveDataPointSet, LocalDate.of(1970, 1, 1).atStartOfDay()));

    // Assert
    assertNull(actualApplyResult.getCurrency());
    assertNull(actualApplyResult.getValuationDate());
    assertNull(actualApplyResult.getValue());
  }

  /**
   * Method under test:
   * {@link ConditionalSettlementCalculator#ConditionalSettlementCalculator(String, BigDecimal)}
   */
  @Test
  void testNewConditionalSettlementCalculator3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange and Act
    ConditionalSettlementCalculator actualConditionalSettlementCalculator = new ConditionalSettlementCalculator(
        "Sdc XML", new BigDecimal("2.3"));
    HashSet<CalibrationDataItem> curveDataPointSet = new HashSet<>();
    CalibrationDataItem.Spec spec = new CalibrationDataItem.Spec("yyyyMMdd-HHmmss", null, "yyyyMMdd-HHmmss",
        "yyyyMMdd-HHmmss");

    curveDataPointSet.add(new CalibrationDataItem(spec, 10.0d, LocalDate.of(1970, 1, 1).atStartOfDay()));
    ValueResult actualApplyResult = actualConditionalSettlementCalculator
        .apply(new CalibrationDataset(curveDataPointSet, LocalDate.of(1970, 1, 1).atStartOfDay()));

    // Assert
    assertNull(actualApplyResult.getCurrency());
    assertNull(actualApplyResult.getValuationDate());
    assertNull(actualApplyResult.getValue());
  }
}
