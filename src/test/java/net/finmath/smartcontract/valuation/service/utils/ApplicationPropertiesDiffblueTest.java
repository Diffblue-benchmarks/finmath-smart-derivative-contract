package net.finmath.smartcontract.valuation.service.utils;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ApplicationPropertiesDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ApplicationProperties#setUsers(List)}
   *   <li>{@link ApplicationProperties#getUsers()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ApplicationProperties.getUsers()", "void ApplicationProperties.setUsers(List)"})
  void testGettersAndSetters() {
    // Arrange
    ApplicationProperties applicationProperties = new ApplicationProperties();
    ArrayList<SDCUser> users = new ArrayList<>();

    // Act
    applicationProperties.setUsers(users);
    List<SDCUser> actualUsers = applicationProperties.getUsers();

    // Assert
    assertTrue(actualUsers.isEmpty());
    assertSame(users, actualUsers);
  }
}
