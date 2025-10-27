package net.finmath.smartcontract.valuation.service.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import net.finmath.smartcontract.valuation.service.utils.ApplicationProperties;
import net.finmath.smartcontract.valuation.service.utils.SDCUser;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

class BasicAuthWebSecurityConfigurationDiffblueTest {
  /**
   * Method under test:
   * {@link BasicAuthWebSecurityConfiguration#userDetailsService(ApplicationProperties)}
   */
  @Test
  void testUserDetailsService() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    BasicAuthWebSecurityConfiguration basicAuthWebSecurityConfiguration = new BasicAuthWebSecurityConfiguration();

    ApplicationProperties applicationProperties = new ApplicationProperties();
    applicationProperties.setUsers(new ArrayList<>());

    // Act and Assert
    assertFalse(basicAuthWebSecurityConfiguration.userDetailsService(applicationProperties).userExists("janedoe"));
  }

  /**
   * Method under test:
   * {@link BasicAuthWebSecurityConfiguration#userDetailsService(ApplicationProperties)}
   */
  @Test
  void testUserDetailsService2() throws UsernameNotFoundException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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

  /**
   * Method under test:
   * {@link BasicAuthWebSecurityConfiguration#userDetailsService(ApplicationProperties)}
   */
  @Test
  void testUserDetailsService3() throws UsernameNotFoundException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
   * Method under test:
   * {@link BasicAuthWebSecurityConfiguration#userDetailsService(ApplicationProperties)}
   */
  @Test
  void testUserDetailsService4() throws UsernameNotFoundException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    BasicAuthWebSecurityConfiguration basicAuthWebSecurityConfiguration = new BasicAuthWebSecurityConfiguration();
    SDCUser sdcUser = mock(SDCUser.class);
    when(sdcUser.getPassword()).thenReturn("iloveyou");
    when(sdcUser.getRole()).thenReturn("Role");
    when(sdcUser.getUsername()).thenReturn("janedoe");
    doNothing().when(sdcUser).setPassword(Mockito.<String>any());
    doNothing().when(sdcUser).setRole(Mockito.<String>any());
    doNothing().when(sdcUser).setUsername(Mockito.<String>any());
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
    verify(sdcUser).getPassword();
    verify(sdcUser).getRole();
    verify(sdcUser).getUsername();
    verify(sdcUser).setPassword(eq("iloveyou"));
    verify(sdcUser).setRole(eq("Role"));
    verify(sdcUser).setUsername(eq("janedoe"));
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
