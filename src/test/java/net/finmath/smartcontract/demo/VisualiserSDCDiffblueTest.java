package net.finmath.smartcontract.demo;

import static org.junit.jupiter.api.Assertions.assertThrows;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {VisualiserSDC.class})
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class VisualiserSDCDiffblueTest {
  @Autowired
  private VisualiserSDC visualiserSDC;

  /**
   * Method under test:
   * {@link VisualiserSDC#updateWithValue(LocalDateTime, double, double, Double, double)}
   */
  @Test
  void testUpdateWithValue() throws InterruptedException {
    // Arrange, Act and Assert
    assertThrows(NullPointerException.class,
        () -> visualiserSDC.updateWithValue(LocalDate.of(1970, 1, 1).atStartOfDay(), 10.0d, 2.0d, 10.0d, 10.0d));
  }

  /**
   * Method under test:
   * {@link VisualiserSDC#updateWithValue(LocalDateTime, double, double, Double, double)}
   */
  @Test
  void testUpdateWithValue2() throws InterruptedException {
    // Arrange, Act and Assert
    assertThrows(NullPointerException.class,
        () -> visualiserSDC.updateWithValue(LocalDate.of(1970, 1, 1).atStartOfDay(), 10.0d, 2.0d, null, 10.0d));
  }
}
