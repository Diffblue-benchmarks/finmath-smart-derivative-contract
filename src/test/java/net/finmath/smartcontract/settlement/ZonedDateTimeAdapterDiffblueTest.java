package net.finmath.smartcontract.settlement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ZonedDateTimeAdapter.class})
@ExtendWith(SpringExtension.class)
class ZonedDateTimeAdapterDiffblueTest {
  @Autowired
  private ZonedDateTimeAdapter zonedDateTimeAdapter;

  /**
   * Method under test: {@link ZonedDateTimeAdapter#marshal(ZonedDateTime)}
   */
  @Test
  void testMarshal() {
    // Arrange, Act and Assert
    assertEquals("19700101-000000",
        zonedDateTimeAdapter.marshal(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC)));
  }
}
