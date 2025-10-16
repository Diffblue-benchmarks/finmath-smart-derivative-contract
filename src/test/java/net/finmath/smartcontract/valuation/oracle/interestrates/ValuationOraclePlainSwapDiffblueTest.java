package net.finmath.smartcontract.valuation.oracle.interestrates;

import static org.junit.jupiter.api.Assertions.assertNull;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import net.finmath.marketdata.products.AnalyticProduct;
import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationDataset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ValuationOraclePlainSwapDiffblueTest {
  /**
   * Test {@link ValuationOraclePlainSwap#ValuationOraclePlainSwap(Map, List)}.
   *
   * <ul>
   *   <li>Given {@link LocalDate} with {@code 1970} and one and one atStartOfDay.
   * </ul>
   *
   * <p>Method under test: {@link ValuationOraclePlainSwap#ValuationOraclePlainSwap(Map, List)}
   */
  @Test
  @DisplayName(
      "Test new ValuationOraclePlainSwap(Map, List); given LocalDate with '1970' and one and one atStartOfDay")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ValuationOraclePlainSwap.<init>(Map, List)"})
  void testNewValuationOraclePlainSwap_givenLocalDateWith1970AndOneAndOneAtStartOfDay() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    HashMap<String, AnalyticProduct> products = new HashMap<>();

    ArrayList<CalibrationDataset> scenarioList = new ArrayList<>();
    CalibrationDataset calibrationDataset =
        new CalibrationDataset(new HashSet<>(), LocalDate.of(1970, 1, 1).atStartOfDay());
    scenarioList.add(calibrationDataset);

    // Act
    ValuationOraclePlainSwap actualValuationOraclePlainSwap =
        new ValuationOraclePlainSwap(products, scenarioList);

    // Assert
    assertNull(actualValuationOraclePlainSwap.getValues(null, null));
  }

  /**
   * Test {@link ValuationOraclePlainSwap#ValuationOraclePlainSwap(Map, List)}.
   *
   * <ul>
   *   <li>Given {@link LocalDate} with {@code 1970} and one and one atStartOfDay.
   * </ul>
   *
   * <p>Method under test: {@link ValuationOraclePlainSwap#ValuationOraclePlainSwap(Map, List)}
   */
  @Test
  @DisplayName(
      "Test new ValuationOraclePlainSwap(Map, List); given LocalDate with '1970' and one and one atStartOfDay")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ValuationOraclePlainSwap.<init>(Map, List)"})
  void testNewValuationOraclePlainSwap_givenLocalDateWith1970AndOneAndOneAtStartOfDay2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    HashMap<String, AnalyticProduct> products = new HashMap<>();

    ArrayList<CalibrationDataset> scenarioList = new ArrayList<>();
    CalibrationDataset calibrationDataset =
        new CalibrationDataset(new HashSet<>(), LocalDate.of(1970, 1, 1).atStartOfDay());
    scenarioList.add(calibrationDataset);
    CalibrationDataset calibrationDataset2 =
        new CalibrationDataset(new HashSet<>(), LocalDate.of(1970, 1, 1).atStartOfDay());
    scenarioList.add(calibrationDataset2);

    // Act
    ValuationOraclePlainSwap actualValuationOraclePlainSwap =
        new ValuationOraclePlainSwap(products, scenarioList);

    // Assert
    assertNull(actualValuationOraclePlainSwap.getValues(null, null));
  }

  /**
   * Test {@link ValuationOraclePlainSwap#ValuationOraclePlainSwap(Map, List, int)}.
   *
   * <ul>
   *   <li>Given {@link LocalDate} with {@code 1970} and one and one atStartOfDay.
   * </ul>
   *
   * <p>Method under test: {@link ValuationOraclePlainSwap#ValuationOraclePlainSwap(Map, List, int)}
   */
  @Test
  @DisplayName(
      "Test new ValuationOraclePlainSwap(Map, List, int); given LocalDate with '1970' and one and one atStartOfDay")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ValuationOraclePlainSwap.<init>(Map, List, int)"})
  void testNewValuationOraclePlainSwap_givenLocalDateWith1970AndOneAndOneAtStartOfDay3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    HashMap<String, AnalyticProduct> products = new HashMap<>();

    ArrayList<CalibrationDataset> scenarioList = new ArrayList<>();
    CalibrationDataset calibrationDataset =
        new CalibrationDataset(new HashSet<>(), LocalDate.of(1970, 1, 1).atStartOfDay());
    scenarioList.add(calibrationDataset);

    // Act
    ValuationOraclePlainSwap actualValuationOraclePlainSwap =
        new ValuationOraclePlainSwap(products, scenarioList, 1);

    // Assert
    assertNull(actualValuationOraclePlainSwap.getValues(null, null));
  }

  /**
   * Test {@link ValuationOraclePlainSwap#ValuationOraclePlainSwap(Map, List, int)}.
   *
   * <ul>
   *   <li>Given {@link LocalDate} with {@code 1970} and one and one atStartOfDay.
   * </ul>
   *
   * <p>Method under test: {@link ValuationOraclePlainSwap#ValuationOraclePlainSwap(Map, List, int)}
   */
  @Test
  @DisplayName(
      "Test new ValuationOraclePlainSwap(Map, List, int); given LocalDate with '1970' and one and one atStartOfDay")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ValuationOraclePlainSwap.<init>(Map, List, int)"})
  void testNewValuationOraclePlainSwap_givenLocalDateWith1970AndOneAndOneAtStartOfDay4() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    HashMap<String, AnalyticProduct> products = new HashMap<>();

    ArrayList<CalibrationDataset> scenarioList = new ArrayList<>();
    CalibrationDataset calibrationDataset =
        new CalibrationDataset(new HashSet<>(), LocalDate.of(1970, 1, 1).atStartOfDay());
    scenarioList.add(calibrationDataset);
    CalibrationDataset calibrationDataset2 =
        new CalibrationDataset(new HashSet<>(), LocalDate.of(1970, 1, 1).atStartOfDay());
    scenarioList.add(calibrationDataset2);

    // Act
    ValuationOraclePlainSwap actualValuationOraclePlainSwap =
        new ValuationOraclePlainSwap(products, scenarioList, 1);

    // Assert
    assertNull(actualValuationOraclePlainSwap.getValues(null, null));
  }

  /**
   * Test {@link ValuationOraclePlainSwap#ValuationOraclePlainSwap(Map, List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link ValuationOraclePlainSwap#ValuationOraclePlainSwap(Map, List)}
   */
  @Test
  @DisplayName("Test new ValuationOraclePlainSwap(Map, List); when ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ValuationOraclePlainSwap.<init>(Map, List)"})
  void testNewValuationOraclePlainSwap_whenArrayList() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    HashMap<String, AnalyticProduct> products = new HashMap<>();

    // Act
    ValuationOraclePlainSwap actualValuationOraclePlainSwap =
        new ValuationOraclePlainSwap(products, new ArrayList<>());

    // Assert
    assertNull(actualValuationOraclePlainSwap.getValues(null, null));
  }

  /**
   * Test {@link ValuationOraclePlainSwap#ValuationOraclePlainSwap(Map, List, int)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link ValuationOraclePlainSwap#ValuationOraclePlainSwap(Map, List, int)}
   */
  @Test
  @DisplayName("Test new ValuationOraclePlainSwap(Map, List, int); when ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ValuationOraclePlainSwap.<init>(Map, List, int)"})
  void testNewValuationOraclePlainSwap_whenArrayList2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    HashMap<String, AnalyticProduct> products = new HashMap<>();

    // Act
    ValuationOraclePlainSwap actualValuationOraclePlainSwap =
        new ValuationOraclePlainSwap(products, new ArrayList<>(), 1);

    // Assert
    assertNull(actualValuationOraclePlainSwap.getValues(null, null));
  }
}
