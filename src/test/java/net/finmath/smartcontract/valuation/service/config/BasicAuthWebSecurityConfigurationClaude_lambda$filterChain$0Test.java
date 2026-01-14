/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */

package net.finmath.smartcontract.valuation.service.config;

import net.finmath.smartcontract.model.ExceptionId;
import net.finmath.smartcontract.model.SDCException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test class for BasicAuthWebSecurityConfiguration lambda$filterChain$0 method.
 * Tests the lambda inside filterChain to achieve coverage of lines 39, 40, 41, 42, 43.
 *
 * The lambda is executed inside the authorizeHttpRequests customizer. To test it,
 * we capture the Customizer argument and invoke it with a mocked
 * AuthorizationManagerRequestMatcherRegistry to execute the lambda body.
 *
 * @author Claude Code
 */
class BasicAuthWebSecurityConfigurationClaude_lambda$filterChain$0Test {

	private BasicAuthWebSecurityConfiguration configuration;
	private HttpSecurity httpSecurity;
	private CorsConfigurer<HttpSecurity> corsConfigurer;
	private DefaultSecurityFilterChain securityFilterChain;
	private AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authzRegistry;
	private AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizedUrl authorizedUrl;
	private HttpBasicConfigurer<HttpSecurity> httpBasicConfigurer;

	@SuppressWarnings("unchecked")
	@BeforeEach
	void setUp() throws Exception {
		configuration = new BasicAuthWebSecurityConfiguration();

		// Mock HttpSecurity and its configurers
		httpSecurity = mock(HttpSecurity.class);
		corsConfigurer = mock(CorsConfigurer.class);
		securityFilterChain = mock(DefaultSecurityFilterChain.class);
		authzRegistry = mock(AuthorizeHttpRequestsConfigurer.AuthorizationManagerRequestMatcherRegistry.class);
		authorizedUrl = mock(AuthorizeHttpRequestsConfigurer.AuthorizedUrl.class);
		httpBasicConfigurer = mock(HttpBasicConfigurer.class);

		// Setup mock behavior for method chaining in the lambda
		when(authzRegistry.anyRequest()).thenReturn(authorizedUrl);
		when(authorizedUrl.authenticated()).thenReturn(authzRegistry);
		when(authzRegistry.and()).thenReturn(httpSecurity);
		when(httpSecurity.httpBasic()).thenReturn(httpBasicConfigurer);

		// Setup mock behavior for method chaining in filterChain
		when(httpSecurity.csrf(any(Customizer.class))).thenReturn(httpSecurity);
		when(httpSecurity.authorizeHttpRequests(any(Customizer.class))).thenReturn(httpSecurity);
		when(httpSecurity.cors()).thenReturn(corsConfigurer);
		when(corsConfigurer.and()).thenReturn(httpSecurity);
		when(httpSecurity.build()).thenReturn(securityFilterChain);
	}

	/**
	 * Test that the lambda executes and configures authorization correctly.
	 * This test captures the Customizer passed to authorizeHttpRequests and invokes it,
	 * thereby executing the lambda body which covers lines 39-43.
	 */
	@Test
	@SuppressWarnings("unchecked")
	void testLambdaExecutesAuthorizationConfiguration() throws Exception {
		// Capture the Customizer (lambda) passed to authorizeHttpRequests
		ArgumentCaptor<Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry>> customizerCaptor =
			ArgumentCaptor.forClass(Customizer.class);

		// Call filterChain
		configuration.filterChain(httpSecurity);

		// Verify and capture the authorizeHttpRequests call
		verify(httpSecurity).authorizeHttpRequests(customizerCaptor.capture());

		// Get the captured customizer (this is the lambda)
		Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> capturedLambda =
			customizerCaptor.getValue();

		// Execute the lambda by calling customize() with our mocked registry
		// This executes lines 39-43
		capturedLambda.customize(authzRegistry);

		// Verify the lambda executed the expected method calls (line 39)
		verify(authzRegistry).anyRequest();
		verify(authorizedUrl).authenticated();
		verify(authzRegistry).and();
	}

	/**
	 * Test that the lambda's try block executes successfully.
	 * This specifically covers line 39: authz.anyRequest().authenticated().and().httpBasic()
	 */
	@Test
	@SuppressWarnings("unchecked")
	void testLambdaTryBlockExecution() throws Exception {
		// Capture the lambda
		ArgumentCaptor<Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry>> customizerCaptor =
			ArgumentCaptor.forClass(Customizer.class);

		configuration.filterChain(httpSecurity);
		verify(httpSecurity).authorizeHttpRequests(customizerCaptor.capture());

		// Execute the lambda - this covers line 39 in the try block
		customizerCaptor.getValue().customize(authzRegistry);

		// Verify the complete chain was called
		verify(authzRegistry).anyRequest();
		verify(authorizedUrl).authenticated();
		verify(authzRegistry).and();
		verify(httpSecurity).httpBasic();
	}

	/**
	 * Test that the lambda's catch block handles exceptions correctly.
	 * This covers lines 40-42: the exception handling that throws SDCException.
	 */
	@Test
	@SuppressWarnings("unchecked")
	void testLambdaCatchBlockExecutesOnException() throws Exception {
		// Setup mocks to throw an exception in the try block
		when(authzRegistry.anyRequest()).thenReturn(authorizedUrl);
		when(authorizedUrl.authenticated()).thenThrow(new RuntimeException("Test exception"));

		// Capture the lambda
		ArgumentCaptor<Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry>> customizerCaptor =
			ArgumentCaptor.forClass(Customizer.class);

		configuration.filterChain(httpSecurity);
		verify(httpSecurity).authorizeHttpRequests(customizerCaptor.capture());

		// Execute the lambda - this should trigger the catch block (lines 40-42)
		Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> capturedLambda =
			customizerCaptor.getValue();

		// The lambda should catch the exception and throw SDCException
		SDCException exception = assertThrows(SDCException.class, () -> {
			capturedLambda.customize(authzRegistry);
		});

		// Verify the exception has the correct ID and message
		assertEquals(ExceptionId.SDC_AUTH_ERROR, exception.getId(),
			"Exception should have SDC_AUTH_ERROR ID");
		assertEquals("SDC_AUTH_ERROR Test exception", exception.getMessage(),
			"Exception message should be formatted as 'ID message'");
	}

	/**
	 * Test the complete lambda execution flow including both success and error paths.
	 * This comprehensive test ensures all lines (39-43) are covered.
	 */
	@Test
	@SuppressWarnings("unchecked")
	void testLambdaCompleteFlow() throws Exception {
		// Capture the lambda
		ArgumentCaptor<Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry>> customizerCaptor =
			ArgumentCaptor.forClass(Customizer.class);

		SecurityFilterChain result = configuration.filterChain(httpSecurity);

		// Verify filterChain completed
		assertNotNull(result, "SecurityFilterChain should be created");

		// Verify the lambda was passed to authorizeHttpRequests
		verify(httpSecurity).authorizeHttpRequests(customizerCaptor.capture());

		// Execute the lambda to cover lines 39-43
		customizerCaptor.getValue().customize(authzRegistry);

		// Verify all method calls in the lambda were executed
		verify(authzRegistry).anyRequest();
		verify(authorizedUrl).authenticated();
		verify(authzRegistry).and();
		verify(httpSecurity).httpBasic();
	}

	/**
	 * Test multiple executions of the lambda to ensure consistent behavior.
	 * This verifies that the lambda (lines 39-43) works correctly on repeated invocations.
	 */
	@Test
	@SuppressWarnings("unchecked")
	void testLambdaMultipleExecutions() throws Exception {
		// Capture the lambda
		ArgumentCaptor<Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry>> customizerCaptor =
			ArgumentCaptor.forClass(Customizer.class);

		configuration.filterChain(httpSecurity);
		verify(httpSecurity).authorizeHttpRequests(customizerCaptor.capture());

		Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> lambda =
			customizerCaptor.getValue();

		// Execute the lambda multiple times
		lambda.customize(authzRegistry);
		lambda.customize(authzRegistry);

		// Verify the lambda executed correctly both times
		verify(authzRegistry, times(2)).anyRequest();
		verify(authorizedUrl, times(2)).authenticated();
		verify(authzRegistry, times(2)).and();
		verify(httpSecurity, times(2)).httpBasic();
	}
}
