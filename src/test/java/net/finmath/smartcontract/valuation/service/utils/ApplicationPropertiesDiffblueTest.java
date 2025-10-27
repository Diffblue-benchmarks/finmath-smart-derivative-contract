package net.finmath.smartcontract.valuation.service.utils;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class ApplicationPropertiesDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ApplicationProperties#setUsers(List)}
   *   <li>{@link ApplicationProperties#getUsers()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    ApplicationProperties applicationProperties = new ApplicationProperties();
    ArrayList<SDCUser> users = new ArrayList<>();

    // Act
    applicationProperties.setUsers(users);
    List<SDCUser> actualUsers = applicationProperties.getUsers();

    // Assert that nothing has changed
    assertTrue(actualUsers.isEmpty());
    assertSame(users, actualUsers);
  }
}
