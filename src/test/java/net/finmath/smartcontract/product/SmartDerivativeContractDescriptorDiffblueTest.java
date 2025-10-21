package net.finmath.smartcontract.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.diffblue.cover.annotations.MethodsUnderTest;
import net.finmath.smartcontract.product.SmartDerivativeContractDescriptor.Party;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class SmartDerivativeContractDescriptorDiffblueTest {
  /**
   * Test Party getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Party#Party(String, String, String, String)}
   *   <li>{@link Party#toString()}
   *   <li>{@link Party#getAddress()}
   *   <li>{@link Party#getHref()}
   *   <li>{@link Party#getId()}
   *   <li>{@link Party#getName()}
   * </ul>
   */
  @Test
  @DisplayName("Test Party getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void Party.<init>(String, String, String, String)", "String Party.getAddress()",
      "String Party.getHref()", "String Party.getId()", "String Party.getName()", "String Party.toString()"})
  void testPartyGettersAndSetters() {
    // Arrange and Act
    Party actualParty = new Party("42", "Name", "Href", "42 Main St");
    String actualToStringResult = actualParty.toString();
    String actualAddress = actualParty.getAddress();
    String actualHref = actualParty.getHref();
    String actualId = actualParty.getId();

    // Assert
    assertEquals("42 Main St", actualAddress);
    assertEquals("42", actualId);
    assertEquals("Href", actualHref);
    assertEquals("Name", actualParty.getName());
    assertEquals("Party {id='42', name='Name', href='Href', address='42 Main St'}", actualToStringResult);
  }
}
