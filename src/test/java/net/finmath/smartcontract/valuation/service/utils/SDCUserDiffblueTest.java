package net.finmath.smartcontract.valuation.service.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class SDCUserDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link SDCUser}
   *   <li>{@link SDCUser#setPassword(String)}
   *   <li>{@link SDCUser#setRole(String)}
   *   <li>{@link SDCUser#setUsername(String)}
   *   <li>{@link SDCUser#getPassword()}
   *   <li>{@link SDCUser#getRole()}
   *   <li>{@link SDCUser#getUsername()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    SDCUser actualSdcUser = new SDCUser();
    actualSdcUser.setPassword("iloveyou");
    actualSdcUser.setRole("Role");
    actualSdcUser.setUsername("janedoe");
    String actualPassword = actualSdcUser.getPassword();
    String actualRole = actualSdcUser.getRole();

    // Assert that nothing has changed
    assertEquals("Role", actualRole);
    assertEquals("iloveyou", actualPassword);
    assertEquals("janedoe", actualSdcUser.getUsername());
  }
}
