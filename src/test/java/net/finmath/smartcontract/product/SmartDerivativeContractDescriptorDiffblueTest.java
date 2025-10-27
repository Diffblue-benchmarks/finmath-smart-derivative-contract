package net.finmath.smartcontract.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class SmartDerivativeContractDescriptorDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link SmartDerivativeContractDescriptor.Party#Party(String, String, String, String)}
   *   <li>{@link SmartDerivativeContractDescriptor.Party#toString()}
   *   <li>{@link SmartDerivativeContractDescriptor.Party#getAddress()}
   *   <li>{@link SmartDerivativeContractDescriptor.Party#getHref()}
   *   <li>{@link SmartDerivativeContractDescriptor.Party#getId()}
   *   <li>{@link SmartDerivativeContractDescriptor.Party#getName()}
   * </ul>
   */
  @Test
  void testPartyGettersAndSetters() {
    // Arrange and Act
    SmartDerivativeContractDescriptor.Party actualParty = new SmartDerivativeContractDescriptor.Party("42", "Name",
        "Href", "42 Main St");
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
