package net.finmath.smartcontract.valuation.service.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import net.finmath.smartcontract.valuation.service.utils.ApplicationProperties;
import net.finmath.smartcontract.valuation.service.utils.SDCUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

class BasicAuthWebSecurityConfigurationDiffblueTest {
  /**
   * Test {@link BasicAuthWebSecurityConfiguration#userDetailsService(ApplicationProperties)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return not userExists {@code janedoe}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BasicAuthWebSecurityConfiguration#userDetailsService(ApplicationProperties)}
   */
  @Test
  @DisplayName("Test userDetailsService(ApplicationProperties); given ArrayList(); then return not userExists 'janedoe'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "InMemoryUserDetailsManager BasicAuthWebSecurityConfiguration.userDetailsService(ApplicationProperties)"})
  void testUserDetailsService_givenArrayList_thenReturnNotUserExistsJanedoe() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    BasicAuthWebSecurityConfiguration basicAuthWebSecurityConfiguration = new BasicAuthWebSecurityConfiguration();

    ApplicationProperties applicationProperties = new ApplicationProperties();
    applicationProperties.setUsers(new ArrayList<>());

    // Act and Assert
    assertFalse(basicAuthWebSecurityConfiguration.userDetailsService(applicationProperties).userExists("janedoe"));
  }

  /**
   * Test {@link BasicAuthWebSecurityConfiguration#userDetailsService(ApplicationProperties)}.
   * <ul>
   *   <li>Given {@link SDCUser} (default constructor) Password is {@code Password}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BasicAuthWebSecurityConfiguration#userDetailsService(ApplicationProperties)}
   */
  @Test
  @DisplayName("Test userDetailsService(ApplicationProperties); given SDCUser (default constructor) Password is 'Password'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "InMemoryUserDetailsManager BasicAuthWebSecurityConfiguration.userDetailsService(ApplicationProperties)"})
  void testUserDetailsService_givenSDCUserPasswordIsPassword() throws UsernameNotFoundException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    BasicAuthWebSecurityConfiguration basicAuthWebSecurityConfiguration = new BasicAuthWebSecurityConfiguration();

    SDCUser sdcUser = new SDCUser();
    sdcUser.setPassword("iloveyou");
    sdcUser.setRole("Role");
    sdcUser.setUsername("janedoe");

    SDCUser sdcUser2 = new SDCUser();
    sdcUser2.setPassword("Password");
    sdcUser2.setRole("42");
    sdcUser2.setUsername("Username");

    ArrayList<SDCUser> users = new ArrayList<>();
    users.add(sdcUser2);
    users.add(sdcUser);

    ApplicationProperties applicationProperties = new ApplicationProperties();
    applicationProperties.setUsers(users);

    // Act
    InMemoryUserDetailsManager actualUserDetailsServiceResult = basicAuthWebSecurityConfiguration
        .userDetailsService(applicationProperties);

    // Assert
    UserDetails loadUserByUsernameResult = actualUserDetailsServiceResult.loadUserByUsername("janedoe");
    Collection<? extends GrantedAuthority> authorities = loadUserByUsernameResult.getAuthorities();
    assertEquals(1, authorities.size());
    assertTrue(authorities instanceof Set);
    assertTrue(loadUserByUsernameResult instanceof User);
    assertEquals("janedoe", loadUserByUsernameResult.getUsername());
    assertEquals("{noop}iloveyou", loadUserByUsernameResult.getPassword());
    assertTrue(loadUserByUsernameResult.isAccountNonExpired());
    assertTrue(loadUserByUsernameResult.isAccountNonLocked());
    assertTrue(loadUserByUsernameResult.isCredentialsNonExpired());
    assertTrue(loadUserByUsernameResult.isEnabled());
    assertTrue(actualUserDetailsServiceResult.userExists("janedoe"));
  }

  /**
   * Test {@link BasicAuthWebSecurityConfiguration#userDetailsService(ApplicationProperties)}.
   * <ul>
   *   <li>Then return loadUserByUsername {@code janedoe} Authorities size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link BasicAuthWebSecurityConfiguration#userDetailsService(ApplicationProperties)}
   */
  @Test
  @DisplayName("Test userDetailsService(ApplicationProperties); then return loadUserByUsername 'janedoe' Authorities size is one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "InMemoryUserDetailsManager BasicAuthWebSecurityConfiguration.userDetailsService(ApplicationProperties)"})
  void testUserDetailsService_thenReturnLoadUserByUsernameJanedoeAuthoritiesSizeIsOne()
      throws UsernameNotFoundException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    BasicAuthWebSecurityConfiguration basicAuthWebSecurityConfiguration = new BasicAuthWebSecurityConfiguration();

    SDCUser sdcUser = new SDCUser();
    sdcUser.setPassword("iloveyou");
    sdcUser.setRole("Role");
    sdcUser.setUsername("janedoe");

    ArrayList<SDCUser> users = new ArrayList<>();
    users.add(sdcUser);

    ApplicationProperties applicationProperties = new ApplicationProperties();
    applicationProperties.setUsers(users);

    // Act
    InMemoryUserDetailsManager actualUserDetailsServiceResult = basicAuthWebSecurityConfiguration
        .userDetailsService(applicationProperties);

    // Assert
    UserDetails loadUserByUsernameResult = actualUserDetailsServiceResult.loadUserByUsername("janedoe");
    Collection<? extends GrantedAuthority> authorities = loadUserByUsernameResult.getAuthorities();
    assertEquals(1, authorities.size());
    assertTrue(authorities instanceof Set);
    assertTrue(loadUserByUsernameResult instanceof User);
    assertEquals("janedoe", loadUserByUsernameResult.getUsername());
    assertEquals("{noop}iloveyou", loadUserByUsernameResult.getPassword());
    assertTrue(loadUserByUsernameResult.isAccountNonExpired());
    assertTrue(loadUserByUsernameResult.isAccountNonLocked());
    assertTrue(loadUserByUsernameResult.isCredentialsNonExpired());
    assertTrue(loadUserByUsernameResult.isEnabled());
    assertTrue(actualUserDetailsServiceResult.userExists("janedoe"));
  }
}
