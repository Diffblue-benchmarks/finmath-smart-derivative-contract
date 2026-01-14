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
import net.finmath.smartcontract.valuation.marketdata.generators.MarketDataGeneratorLauncherDiffblueBase;
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
    // Arrange
    String sdcXML =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();

    // Act
    ConditionalSettlementCalculator actualConditionalSettlementCalculator =
        new ConditionalSettlementCalculator(sdcXML, new BigDecimal("2.3"));
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
    // Arrange
    String sdcXML =
        MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml();

    // Act
    ConditionalSettlementCalculator actualConditionalSettlementCalculator =
        new ConditionalSettlementCalculator(sdcXML, new BigDecimal("2.3"));
    CalibrationDataset actualmarketdata = mock(CalibrationDataset.class);
    when(actualmarketdata.serializeToJson())
        .thenReturn(
            MarketDataGeneratorLauncherDiffblueBase.createMinimalSmartDerivativeContractXml());
    ValueResult actualApplyResult = actualConditionalSettlementCalculator.apply(actualmarketdata);

    // Assert
    verify(actualmarketdata).serializeToJson();
    assertNull(actualApplyResult.getCurrency());
    assertNull(actualApplyResult.getValuationDate());
    assertNull(actualApplyResult.getValue());
  }
}
