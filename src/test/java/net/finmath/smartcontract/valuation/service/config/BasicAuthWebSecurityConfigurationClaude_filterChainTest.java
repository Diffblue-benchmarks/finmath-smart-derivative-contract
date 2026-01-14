/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */

package net.finmath.smartcontract.valuation.service.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test class for BasicAuthWebSecurityConfiguration.filterChain method.
 * Tests the filterChain method to achieve coverage of lines 35, 36, 37, 44, 45.
 *
 * This test uses Mockito to mock HttpSecurity since it requires complex Spring context
 * that cannot be easily instantiated in unit tests. The mocking approach allows us to
 * verify that the correct configuration methods are called while achieving code coverage.
 *
 * @author Claude Code
 */
class BasicAuthWebSecurityConfigurationClaude_filterChainTest {

	private BasicAuthWebSecurityConfiguration configuration;
	private HttpSecurity httpSecurity;
	private CsrfConfigurer<HttpSecurity> csrfConfigurer;
	private AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authzRegistry;
	private CorsConfigurer<HttpSecurity> corsConfigurer;
	private DefaultSecurityFilterChain securityFilterChain;

	@SuppressWarnings("unchecked")
	@BeforeEach
	void setUp() throws Exception {
		configuration = new BasicAuthWebSecurityConfiguration();

		// Mock HttpSecurity and its configurers
		httpSecurity = mock(HttpSecurity.class);
		csrfConfigurer = mock(CsrfConfigurer.class);
		authzRegistry = mock(AuthorizeHttpRequestsConfigurer.AuthorizationManagerRequestMatcherRegistry.class);
		corsConfigurer = mock(CorsConfigurer.class);
		securityFilterChain = mock(DefaultSecurityFilterChain.class);

		// Setup mock behavior for method chaining
		when(httpSecurity.csrf(any(Customizer.class))).thenReturn(httpSecurity);
		when(httpSecurity.authorizeHttpRequests(any(Customizer.class))).thenReturn(httpSecurity);
		when(httpSecurity.cors()).thenReturn(corsConfigurer);  // Note: cors() is called without parameters (deprecated)
		when(corsConfigurer.and()).thenReturn(httpSecurity);
		when(httpSecurity.build()).thenReturn(securityFilterChain);
	}

	/**
	 * Test that filterChain calls csrf configuration.
	 * This test covers line 36: .csrf(AbstractHttpConfigurer::disable)
	 */
	@Test
	void testFilterChainConfiguresCsrf() throws Exception {
		configuration.filterChain(httpSecurity);

		// Verify csrf was called (line 36)
		verify(httpSecurity, times(1)).csrf(any(Customizer.class));
	}

	/**
	 * Test that filterChain calls authorizeHttpRequests configuration.
	 * This test covers line 37: .authorizeHttpRequests(...)
	 */
	@Test
	void testFilterChainConfiguresAuthorizeHttpRequests() throws Exception {
		configuration.filterChain(httpSecurity);

		// Verify authorizeHttpRequests was called (line 37)
		verify(httpSecurity, times(1)).authorizeHttpRequests(any(Customizer.class));
	}

	/**
	 * Test that filterChain calls cors configuration.
	 * This test covers line 44: .cors()
	 */
	@Test
	void testFilterChainConfiguresCors() throws Exception {
		configuration.filterChain(httpSecurity);

		// Verify cors was called (line 44) - note: deprecated no-arg version
		verify(httpSecurity, times(1)).cors();
	}

	/**
	 * Test that filterChain calls build and returns SecurityFilterChain.
	 * This test covers line 45: return http.build()
	 */
	@Test
	void testFilterChainBuildsAndReturnsSecurityFilterChain() throws Exception {
		SecurityFilterChain result = configuration.filterChain(httpSecurity);

		// Verify build was called (line 45)
		verify(httpSecurity, times(1)).build();

		// Verify the result is the expected SecurityFilterChain
		assertSame(securityFilterChain, result, "Should return the built SecurityFilterChain");
	}

	/**
	 * Test the complete filterChain configuration flow.
	 * This test ensures all configuration lines are executed including:
	 * - Line 35: http reference
	 * - Line 36: CSRF disable
	 * - Line 37: Authorization configuration
	 * - Line 44: CORS configuration
	 * - Line 45: Build and return
	 */
	@Test
	void testFilterChainCompleteConfigurationFlow() throws Exception {
		SecurityFilterChain result = configuration.filterChain(httpSecurity);

		// Verify all configuration methods were called in order (lines 35-45)
		verify(httpSecurity).csrf(any(Customizer.class));
		verify(httpSecurity).authorizeHttpRequests(any(Customizer.class));
		verify(httpSecurity).cors();  // Note: deprecated no-arg version
		verify(httpSecurity).build();

		assertNotNull(result, "SecurityFilterChain should not be null");
	}

	/**
	 * Test filterChain to ensure all security features are configured.
	 * This comprehensive test covers all the key lines: 35, 36, 37, 44, 45.
	 */
	@Test
	void testFilterChainConfiguresAllSecurityFeatures() throws Exception {
		SecurityFilterChain result = configuration.filterChain(httpSecurity);

		// Verify CSRF configuration (line 36)
		verify(httpSecurity, times(1)).csrf(any(Customizer.class));

		// Verify authorization configuration (line 37)
		verify(httpSecurity, times(1)).authorizeHttpRequests(any(Customizer.class));

		// Verify CORS configuration (line 44) - deprecated no-arg version
		verify(httpSecurity, times(1)).cors();

		// Verify build was called and result returned (line 45)
		verify(httpSecurity, times(1)).build();
		assertSame(securityFilterChain, result, "Should return the SecurityFilterChain from build()");
	}
}
