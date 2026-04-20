package net.finmath.smartcontract.valuation.service.config;

import net.finmath.smartcontract.model.ExceptionId;
import net.finmath.smartcontract.model.SDCException;
import net.finmath.smartcontract.valuation.service.utils.ApplicationProperties;
import net.finmath.smartcontract.valuation.service.utils.SDCUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

	@SuppressWarnings("unchecked")
	@Test
	void testFilterChain() throws Exception {
		HttpSecurity http = mock(HttpSecurity.class, RETURNS_SELF);
		SecurityFilterChain mockChain = mock(SecurityFilterChain.class);
		doReturn(mockChain).when(http).build();

		SecurityFilterChain result = configuration.filterChain(http);

		assertNotNull(result);
		assertSame(mockChain, result);
		verify(http).csrf(any());
		verify(http).authorizeHttpRequests(any());
		verify(http).cors();
	}

	@SuppressWarnings("unchecked")
	@Test
	void testFilterChainAuthorizationCustomizerErrorHandling() throws Exception {
		HttpSecurity http = mock(HttpSecurity.class, RETURNS_SELF);
		doReturn(mock(SecurityFilterChain.class)).when(http).build();

		configuration.filterChain(http);

		ArgumentCaptor<Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry>> captor =
				ArgumentCaptor.forClass(Customizer.class);
		verify(http).authorizeHttpRequests(captor.capture());

		AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry registry =
				mock(AuthorizeHttpRequestsConfigurer.AuthorizationManagerRequestMatcherRegistry.class, RETURNS_DEEP_STUBS);

		SDCException thrown = assertThrows(SDCException.class, () -> captor.getValue().customize(registry));
		assertEquals(ExceptionId.SDC_AUTH_ERROR, thrown.getId());
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
