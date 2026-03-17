package net.finmath.smartcontract.valuation.service.config;

import net.finmath.smartcontract.valuation.service.utils.ApplicationProperties;
import net.finmath.smartcontract.valuation.service.utils.SDCUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BasicAuthWebSecurityConfigurationTest {

	private BasicAuthWebSecurityConfiguration configuration;

	@BeforeEach
	void setUp() {
		configuration = new BasicAuthWebSecurityConfiguration();
		ReflectionTestUtils.setField(configuration, "serviceUrl", "http://localhost:8080");
	}

	@Test
	void testCorsConfigurer() {
		WebMvcConfigurer configurer = configuration.corsConfigurer();

		assertNotNull(configurer);

		CorsRegistry registry = new CorsRegistry();
		configurer.addCorsMappings(registry);
	}

	@Test
	void testUserDetailsService() {
		ApplicationProperties applicationProperties = new ApplicationProperties();
		List<SDCUser> users = new ArrayList<>();

		SDCUser user1 = new SDCUser();
		user1.setUsername("testuser1");
		user1.setPassword("password1");
		user1.setRole("USER");
		users.add(user1);

		SDCUser user2 = new SDCUser();
		user2.setUsername("testuser2");
		user2.setPassword("password2");
		user2.setRole("ADMIN");
		users.add(user2);

		applicationProperties.setUsers(users);

		InMemoryUserDetailsManager userDetailsManager = configuration.userDetailsService(applicationProperties);

		assertNotNull(userDetailsManager);
		assertTrue(userDetailsManager.userExists("testuser1"));
		assertTrue(userDetailsManager.userExists("testuser2"));

		UserDetails userDetails1 = userDetailsManager.loadUserByUsername("testuser1");
		assertEquals("testuser1", userDetails1.getUsername());
		assertEquals("{noop}password1", userDetails1.getPassword());

		UserDetails userDetails2 = userDetailsManager.loadUserByUsername("testuser2");
		assertEquals("testuser2", userDetails2.getUsername());
		assertEquals("{noop}password2", userDetails2.getPassword());
	}

	@Test
	void testUserDetailsServiceWithEmptyUserList() {
		ApplicationProperties applicationProperties = new ApplicationProperties();
		applicationProperties.setUsers(new ArrayList<>());

		InMemoryUserDetailsManager userDetailsManager = configuration.userDetailsService(applicationProperties);

		assertNotNull(userDetailsManager);
	}

	@Test
	void testUserDetailsServiceWithMultipleRoles() {
		ApplicationProperties applicationProperties = new ApplicationProperties();
		List<SDCUser> users = new ArrayList<>();

		SDCUser user = new SDCUser();
		user.setUsername("multiuser");
		user.setPassword("pass123");
		user.setRole("USER");
		users.add(user);

		applicationProperties.setUsers(users);

		InMemoryUserDetailsManager userDetailsManager = configuration.userDetailsService(applicationProperties);

		assertNotNull(userDetailsManager);
		assertTrue(userDetailsManager.userExists("multiuser"));

		UserDetails userDetails = userDetailsManager.loadUserByUsername("multiuser");
		assertEquals("multiuser", userDetails.getUsername());
		assertTrue(userDetails.getAuthorities().stream()
				.anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));
	}
}
