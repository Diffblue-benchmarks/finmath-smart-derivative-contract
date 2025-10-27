package net.finmath.smartcontract.settlement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {BigDecimalAdapter.class})
@ExtendWith(SpringExtension.class)
class BigDecimalAdapterDiffblueTest {
  @Autowired
  private BigDecimalAdapter bigDecimalAdapter;

  /**
   * Method under test: {@link BigDecimalAdapter#marshal(BigDecimal)}
   */
  @Test
  void testMarshal() throws Exception {
    // Arrange, Act and Assert
    assertEquals("2.3", bigDecimalAdapter.marshal(new BigDecimal("2.3")));
    assertNull(bigDecimalAdapter.marshal(null));
  }

  /**
   * Method under test: {@link BigDecimalAdapter#unmarshal(String)}
   */
  @Test
  void testUnmarshal() throws Exception {
    // Arrange and Act
    BigDecimal actualUnmarshalResult = bigDecimalAdapter.unmarshal("2.3");

    // Assert
    assertEquals(new BigDecimal("2.3"), actualUnmarshalResult);
  }
}
