package net.finmath.smartcontract.settlement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
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
   * Test {@link ZonedDateTimeAdapter#marshal(ZonedDateTime)} with {@code ZonedDateTime}.
   * <p>
   * Method under test: {@link ZonedDateTimeAdapter#marshal(ZonedDateTime)}
   */
  @Test
  @DisplayName("Test marshal(ZonedDateTime) with 'ZonedDateTime'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"java.lang.String ZonedDateTimeAdapter.marshal(ZonedDateTime)"})
  void testMarshalWithZonedDateTime() {
    // Arrange, Act and Assert
    assertEquals("19700101-000000",
        zonedDateTimeAdapter.marshal(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC)));
  }
}
