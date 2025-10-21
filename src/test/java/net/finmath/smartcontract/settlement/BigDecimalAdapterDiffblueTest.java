package net.finmath.smartcontract.settlement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
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
   * Test {@link BigDecimalAdapter#marshal(BigDecimal)} with {@code BigDecimal}.
   * <ul>
   *   <li>When {@link BigDecimal#BigDecimal(String)} with {@code 2.3}.</li>
   *   <li>Then return {@code 2.3}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BigDecimalAdapter#marshal(BigDecimal)}
   */
  @Test
  @DisplayName("Test marshal(BigDecimal) with 'BigDecimal'; when BigDecimal(String) with '2.3'; then return '2.3'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String BigDecimalAdapter.marshal(BigDecimal)"})
  void testMarshalWithBigDecimal_whenBigDecimalWith23_thenReturn23() throws Exception {
    // Arrange, Act and Assert
    assertEquals("2.3", bigDecimalAdapter.marshal(new BigDecimal("2.3")));
  }

  /**
   * Test {@link BigDecimalAdapter#marshal(BigDecimal)} with {@code BigDecimal}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BigDecimalAdapter#marshal(BigDecimal)}
   */
  @Test
  @DisplayName("Test marshal(BigDecimal) with 'BigDecimal'; when 'null'; then return 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String BigDecimalAdapter.marshal(BigDecimal)"})
  void testMarshalWithBigDecimal_whenNull_thenReturnNull() throws Exception {
    // Arrange, Act and Assert
    assertNull(bigDecimalAdapter.marshal(null));
  }

  /**
   * Test {@link BigDecimalAdapter#unmarshal(String)} with {@code String}.
   * <ul>
   *   <li>When {@code 2.3}.</li>
   *   <li>Then return {@link BigDecimal#BigDecimal(String)} with {@code 2.3}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BigDecimalAdapter#unmarshal(String)}
   */
  @Test
  @DisplayName("Test unmarshal(String) with 'String'; when '2.3'; then return BigDecimal(String) with '2.3'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"BigDecimal BigDecimalAdapter.unmarshal(String)"})
  void testUnmarshalWithString_when23_thenReturnBigDecimalWith23() throws Exception {
    // Arrange and Act
    BigDecimal actualUnmarshalResult = bigDecimalAdapter.unmarshal("2.3");

    // Assert
    assertEquals(new BigDecimal("2.3"), actualUnmarshalResult);
  }
}
