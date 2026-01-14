package net.finmath.smartcontract.demo;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import net.finmath.plots.Plot2DBarFX;
import net.finmath.plots.Plot2DFX;
import net.finmath.plots.PlotableCategories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(MockitoExtension.class)
class VisualiserSDCDiffblueTest {
  @Mock private Plot2DBarFX plot2DBarFX;

  @InjectMocks private VisualiserSDC visualiserSDC;

  /**
   * Test {@link VisualiserSDC#start()}.
   *
   * <p>Method under test: {@link VisualiserSDC#start()}
   */
  @Test
  @DisplayName("Test start()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void VisualiserSDC.start()"})
  void testStart() {
    // Arrange
    VisualiserSDC visualiserSDC = new VisualiserSDC();

    // Act
    visualiserSDC.start();

    // Assert
    assertTrue(visualiserSDC.getSeriesMarketValues().isEmpty());
  }

  /**
   * Test {@link VisualiserSDC#updateWithValue(LocalDateTime, double, double, Double, double)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then calls {@link Plot2DBarFX#update(List)}.
   * </ul>
   *
   * <p>Method under test: {@link VisualiserSDC#updateWithValue(LocalDateTime, double, double,
   * Double, double)}
   */
  @Test
  @DisplayName(
      "Test updateWithValue(LocalDateTime, double, double, Double, double); when 'null'; then calls update(List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void VisualiserSDC.updateWithValue(LocalDateTime, double, double, Double, double)"
  })
  void testUpdateWithValue_whenNull_thenCallsUpdate() throws InterruptedException {
    // Arrange
    when(plot2DBarFX.update(Mockito.<List<PlotableCategories>>any())).thenReturn(new Plot2DBarFX());

    // Act
    visualiserSDC.updateWithValue(
        LocalDate.of(1970, 1, 1).atStartOfDay(), 10.0d, 2.0d, null, 10.0d);

    // Assert
    verify(plot2DBarFX).update(isA(List.class));
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link VisualiserSDC}
   *   <li>{@link VisualiserSDC#getPlotMarginAccounts()}
   *   <li>{@link VisualiserSDC#getPlotMarketValue()}
   *   <li>{@link VisualiserSDC#getSeriesMarketValues()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void VisualiserSDC.<init>()",
    "Plot2DBarFX VisualiserSDC.getPlotMarginAccounts()",
    "Plot2DFX VisualiserSDC.getPlotMarketValue()",
    "List VisualiserSDC.getSeriesMarketValues()"
  })
  void testGettersAndSetters() {
    // Arrange and Act
    VisualiserSDC actualVisualiserSDC = new VisualiserSDC();
    Plot2DBarFX actualPlotMarginAccounts = actualVisualiserSDC.getPlotMarginAccounts();
    Plot2DFX actualPlotMarketValue = actualVisualiserSDC.getPlotMarketValue();

    // Assert
    assertNull(actualVisualiserSDC.getSeriesMarketValues());
    assertNull(actualPlotMarginAccounts);
    assertNull(actualPlotMarketValue);
  }
}
