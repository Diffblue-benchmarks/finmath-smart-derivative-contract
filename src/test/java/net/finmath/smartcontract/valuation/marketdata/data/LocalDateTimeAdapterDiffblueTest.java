package net.finmath.smartcontract.valuation.marketdata.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {LocalDateTimeAdapter.class})
@ExtendWith(SpringExtension.class)
class LocalDateTimeAdapterDiffblueTest {
  @Autowired
  private LocalDateTimeAdapter localDateTimeAdapter;

  /**
   * Method under test: {@link LocalDateTimeAdapter#marshal(LocalDateTime)}
   */
  @Test
  void testMarshal() {
    // Arrange, Act and Assert
    assertEquals("19700101-000000", localDateTimeAdapter.marshal(LocalDate.of(1970, 1, 1).atStartOfDay()));
  }
}
