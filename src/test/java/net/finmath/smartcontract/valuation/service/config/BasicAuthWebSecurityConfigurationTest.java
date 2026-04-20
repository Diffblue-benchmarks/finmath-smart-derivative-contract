package net.finmath.smartcontract.valuation.service.config;

import net.finmath.smartcontract.valuation.service.utils.ApplicationProperties;
import net.finmath.smartcontract.valuation.service.utils.SDCUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BasicAuthWebSecurityConfigurationTest {

	private BasicAuthWebSecurityConfiguration configuration;

	@BeforeEach
	void setUp() throws Exception {
		configuration = new BasicAuthWebSecurityConfiguration();
		Field serviceUrlField = BasicAuthWebSecurityConfiguration.class.getDeclaredField("serviceUrl");
		serviceUrlField.setAccessible(true);
		serviceUrlField.set(configuration, "http://localhost:8080");
	}

	@Test
	void testCorsConfigurer() {
		WebMvcConfigurer configurer = configuration.corsConfigurer();

		assertNotNull(configurer);
	}

	@Test
	void testUserDetailsService() {
		ApplicationProperties props = new ApplicationProperties();
		List<SDCUser> users = new ArrayList<>();
		SDCUser user = new SDCUser();
		user.setUsername("testuser");
		user.setPassword("testpass");
		user.setRole("USER");
		users.add(user);
		props.setUsers(users);

		InMemoryUserDetailsManager manager = configuration.userDetailsService(props);

		assertNotNull(manager);
		UserDetails loaded = manager.loadUserByUsername("testuser");
		assertEquals("testuser", loaded.getUsername());
		assertTrue(loaded.getAuthorities().stream()
				.anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
	}

	@Test
	void testUserDetailsServiceMultipleUsers() {
		ApplicationProperties props = new ApplicationProperties();
		List<SDCUser> users = new ArrayList<>();

		SDCUser user1 = new SDCUser();
		user1.setUsername("admin");
		user1.setPassword("adminpass");
		user1.setRole("ADMIN");
		users.add(user1);

		SDCUser user2 = new SDCUser();
		user2.setUsername("reader");
		user2.setPassword("readerpass");
		user2.setRole("READER");
		users.add(user2);

		props.setUsers(users);

		InMemoryUserDetailsManager manager = configuration.userDetailsService(props);

		assertNotNull(manager);
		assertTrue(manager.userExists("admin"));
		assertTrue(manager.userExists("reader"));

		UserDetails adminDetails = manager.loadUserByUsername("admin");
		assertTrue(adminDetails.getAuthorities().stream()
				.anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
	}

	@Test
	void testBuildUserDetailsList() throws Exception {
		ApplicationProperties props = new ApplicationProperties();
		List<SDCUser> users = new ArrayList<>();
		SDCUser user = new SDCUser();
		user.setUsername("user1");
		user.setPassword("pass1");
		user.setRole("USER");
		users.add(user);
		props.setUsers(users);

		Method buildMethod = BasicAuthWebSecurityConfiguration.class
				.getDeclaredMethod("buildUserDetailsList", ApplicationProperties.class);
		buildMethod.setAccessible(true);

		@SuppressWarnings("unchecked")
		List<UserDetails> result = (List<UserDetails>) buildMethod.invoke(configuration, props);

		assertEquals(1, result.size());
		assertEquals("user1", result.get(0).getUsername());
		assertEquals("{noop}pass1", result.get(0).getPassword());
	}
}
