package net.finmath.smartcontract.demo;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import net.finmath.plots.Plot2DBarFX;
import net.finmath.plots.PlotableCategories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VisualiserSDCDiffblueTest {
  @Mock private Plot2DBarFX plot2DBarFX;

  @InjectMocks private VisualiserSDC visualiserSDC;

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
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    when(plot2DBarFX.update(Mockito.<List<PlotableCategories>>any())).thenReturn(new Plot2DBarFX());

    // Act
    visualiserSDC.updateWithValue(
        LocalDate.of(1970, 1, 1).atStartOfDay(), 10.0d, 2.0d, null, 10.0d);

    // Assert
    verify(plot2DBarFX).update(isA(List.class));
  }
}
