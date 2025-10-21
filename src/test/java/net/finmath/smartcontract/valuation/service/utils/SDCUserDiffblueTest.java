package net.finmath.smartcontract.valuation.service.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class SDCUserDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
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
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SDCUser.<init>()", "String SDCUser.getPassword()", "String SDCUser.getRole()",
      "String SDCUser.getUsername()", "void SDCUser.setPassword(String)", "void SDCUser.setRole(String)",
      "void SDCUser.setUsername(String)"})
  void testGettersAndSetters() {
    // Arrange and Act
    SDCUser actualSdcUser = new SDCUser();
    actualSdcUser.setPassword("iloveyou");
    actualSdcUser.setRole("Role");
    actualSdcUser.setUsername("janedoe");
    String actualPassword = actualSdcUser.getPassword();
    String actualRole = actualSdcUser.getRole();

    // Assert
    assertEquals("Role", actualRole);
    assertEquals("iloveyou", actualPassword);
    assertEquals("janedoe", actualSdcUser.getUsername());
  }
}
