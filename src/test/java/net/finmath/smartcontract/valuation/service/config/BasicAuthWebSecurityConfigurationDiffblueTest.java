package net.finmath.smartcontract.valuation.service.config;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import net.finmath.smartcontract.valuation.service.utils.ApplicationProperties;
import net.finmath.smartcontract.valuation.service.utils.SDCUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@ContextConfiguration(
    classes = {BasicAuthWebSecurityConfiguration.class, ApplicationProperties.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class BasicAuthWebSecurityConfigurationDiffblueTest {
  @Autowired private BasicAuthWebSecurityConfiguration basicAuthWebSecurityConfiguration;

  @MockBean private InMemoryUserDetailsManager inMemoryUserDetailsManager;

  @MockBean private SecurityFilterChain securityFilterChain;

  /**
   * Test {@link BasicAuthWebSecurityConfiguration#corsConfigurer()}.
   *
   * <ul>
   *   <li>Given {@link BasicAuthWebSecurityConfiguration} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BasicAuthWebSecurityConfiguration#corsConfigurer()}
   */
  @Test
  @DisplayName(
      "Test corsConfigurer(); given BasicAuthWebSecurityConfiguration (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"WebMvcConfigurer BasicAuthWebSecurityConfiguration.corsConfigurer()"})
  void testCorsConfigurer_givenBasicAuthWebSecurityConfiguration() {
    // Arrange and Act
    WebMvcConfigurer actualCorsConfigurerResult =
        new BasicAuthWebSecurityConfiguration().corsConfigurer();

    // Assert
    assertNull(actualCorsConfigurerResult.getMessageCodesResolver());
    assertNull(actualCorsConfigurerResult.getValidator());
  }

  /**
   * Test {@link BasicAuthWebSecurityConfiguration#corsConfigurer()}.
   *
   * <ul>
   *   <li>Then return MessageCodesResolver is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BasicAuthWebSecurityConfiguration#corsConfigurer()}
   */
  @Test
  @DisplayName("Test corsConfigurer(); then return MessageCodesResolver is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"WebMvcConfigurer BasicAuthWebSecurityConfiguration.corsConfigurer()"})
  void testCorsConfigurer_thenReturnMessageCodesResolverIsNull() {
    // Arrange and Act
    WebMvcConfigurer actualCorsConfigurerResult =
        basicAuthWebSecurityConfiguration.corsConfigurer();

    // Assert
    assertNull(actualCorsConfigurerResult.getMessageCodesResolver());
    assertNull(actualCorsConfigurerResult.getValidator());
  }

  /**
   * Test {@link BasicAuthWebSecurityConfiguration#userDetailsService(ApplicationProperties)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.
   *   <li>Then return not userExists {@code "testUser123"}.
   * </ul>
   *
   * <p>Method under test: {@link
   * BasicAuthWebSecurityConfiguration#userDetailsService(ApplicationProperties)}
   */
  @Test
  @DisplayName(
      "Test userDetailsService(ApplicationProperties); given ArrayList(); then return not userExists '\"testUser123\"'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InMemoryUserDetailsManager BasicAuthWebSecurityConfiguration.userDetailsService(ApplicationProperties)"
  })
  void testUserDetailsService_givenArrayList_thenReturnNotUserExistsTestUser123() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    BasicAuthWebSecurityConfiguration basicAuthWebSecurityConfiguration =
        new BasicAuthWebSecurityConfiguration();

    ApplicationProperties applicationProperties = new ApplicationProperties();
    applicationProperties.setUsers(new ArrayList<>());

    // Act and Assert
    assertFalse(
        basicAuthWebSecurityConfiguration
            .userDetailsService(applicationProperties)
            .userExists("\"testUser123\""));
  }

  /**
   * Test {@link BasicAuthWebSecurityConfiguration#userDetailsService(ApplicationProperties)}.
   *
   * <ul>
   *   <li>Given {@link SDCUser} (default constructor) Password is {@code iloveyou}.
   * </ul>
   *
   * <p>Method under test: {@link
   * BasicAuthWebSecurityConfiguration#userDetailsService(ApplicationProperties)}
   */
  @Test
  @DisplayName(
      "Test userDetailsService(ApplicationProperties); given SDCUser (default constructor) Password is 'iloveyou'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InMemoryUserDetailsManager BasicAuthWebSecurityConfiguration.userDetailsService(ApplicationProperties)"
  })
  void testUserDetailsService_givenSDCUserPasswordIsIloveyou() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    BasicAuthWebSecurityConfiguration basicAuthWebSecurityConfiguration =
        new BasicAuthWebSecurityConfiguration();

    SDCUser sdcUser = new SDCUser();
    sdcUser.setPassword("\"SecurePassword123!\"");
    sdcUser.setRole("\"Risk Analyst\"");
    sdcUser.setUsername("\"JohnDoe_SmartContractExpert\"");

    SDCUser sdcUser2 = new SDCUser();
    sdcUser2.setPassword("iloveyou");
    sdcUser2.setRole("Role");
    sdcUser2.setUsername("janedoe");

    ArrayList<SDCUser> users = new ArrayList<>();
    users.add(sdcUser2);
    users.add(sdcUser);

    ApplicationProperties applicationProperties = new ApplicationProperties();
    applicationProperties.setUsers(users);

    // Act and Assert
    assertFalse(
        basicAuthWebSecurityConfiguration
            .userDetailsService(applicationProperties)
            .userExists("\"testUser123\""));
  }

  /**
   * Test {@link BasicAuthWebSecurityConfiguration#userDetailsService(ApplicationProperties)}.
   *
   * <ul>
   *   <li>Then return not userExists {@code "testUser123"}.
   * </ul>
   *
   * <p>Method under test: {@link
   * BasicAuthWebSecurityConfiguration#userDetailsService(ApplicationProperties)}
   */
  @Test
  @DisplayName(
      "Test userDetailsService(ApplicationProperties); then return not userExists '\"testUser123\"'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "InMemoryUserDetailsManager BasicAuthWebSecurityConfiguration.userDetailsService(ApplicationProperties)"
  })
  void testUserDetailsService_thenReturnNotUserExistsTestUser123() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    BasicAuthWebSecurityConfiguration basicAuthWebSecurityConfiguration =
        new BasicAuthWebSecurityConfiguration();

    SDCUser sdcUser = new SDCUser();
    sdcUser.setPassword("\"SecurePassword123!\"");
    sdcUser.setRole("\"Risk Analyst\"");
    sdcUser.setUsername("\"JohnDoe_SmartContractExpert\"");

    ArrayList<SDCUser> users = new ArrayList<>();
    users.add(sdcUser);

    ApplicationProperties applicationProperties = new ApplicationProperties();
    applicationProperties.setUsers(users);

    // Act and Assert
    assertFalse(
        basicAuthWebSecurityConfiguration
            .userDetailsService(applicationProperties)
            .userExists("\"testUser123\""));
  }
}
