package net.finmath.smartcontract.valuation.marketdata.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
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
   * Test {@link LocalDateTimeAdapter#marshal(LocalDateTime)} with {@code LocalDateTime}.
   * <p>
   * Method under test: {@link LocalDateTimeAdapter#marshal(LocalDateTime)}
   */
  @Test
  @DisplayName("Test marshal(LocalDateTime) with 'LocalDateTime'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"java.lang.String LocalDateTimeAdapter.marshal(LocalDateTime)"})
  void testMarshalWithLocalDateTime() {
    // Arrange, Act and Assert
    assertEquals("19700101-000000", localDateTimeAdapter.marshal(LocalDate.of(1970, 1, 1).atStartOfDay()));
  }
}
