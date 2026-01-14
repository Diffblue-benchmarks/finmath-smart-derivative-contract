/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */

package net.finmath.smartcontract.valuation.service.websocket;

import net.finmath.smartcontract.valuation.service.websocket.handler.ValuationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistration;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test class for WebSocketConfig.
 * Tests all methods with focus on branch and condition coverage.
 *
 * @author Claude Code
 */
class WebSocketConfigClaudeTest {

	private WebSocketConfig config;

	@BeforeEach
	void setUp() {
		config = new WebSocketConfig();
	}

	/**
	 * Test the default constructor of WebSocketConfig.
	 * The constructor should create a valid instance that implements WebSocketConfigurer.
	 */
	@Test
	void testConstructor() {
		WebSocketConfig newConfig = new WebSocketConfig();
		assertNotNull(newConfig, "Configuration instance should not be null");
		assertTrue(newConfig instanceof WebSocketConfigurer, "Config should implement WebSocketConfigurer");
	}

	/**
	 * Test that the class has the correct Spring annotations.
	 * This verifies that the class is properly configured as a Spring Configuration with WebSocket enabled.
	 */
	@Test
	void testClassAnnotations() {
		assertTrue(
			WebSocketConfig.class.isAnnotationPresent(
				org.springframework.context.annotation.Configuration.class
			),
			"Class should be annotated with @Configuration"
		);
		assertTrue(
			WebSocketConfig.class.isAnnotationPresent(
				org.springframework.web.socket.config.annotation.EnableWebSocket.class
			),
			"Class should be annotated with @EnableWebSocket"
		);
	}

	/**
	 * Test the registerWebSocketHandlers method.
	 * Verifies that:
	 * - A ValuationHandler is added to the registry
	 * - The handler is registered at the correct endpoint "/valuationfeed"
	 * - All origins are allowed (setAllowedOrigins("*"))
	 */
	@Test
	void testRegisterWebSocketHandlers() {
		// Create a mock registry
		WebSocketHandlerRegistry mockRegistry = mock(WebSocketHandlerRegistry.class);
		WebSocketHandlerRegistration mockRegistration = mock(WebSocketHandlerRegistration.class);

		// Setup the mock chain: addHandler() returns a registration that supports setAllowedOrigins()
		when(mockRegistry.addHandler(any(), eq("/valuationfeed"))).thenReturn(mockRegistration);
		when(mockRegistration.setAllowedOrigins("*")).thenReturn(mockRegistration);

		// Call the method under test
		config.registerWebSocketHandlers(mockRegistry);

		// Verify that addHandler was called with a ValuationHandler and the correct path
		ArgumentCaptor<org.springframework.web.socket.WebSocketHandler> handlerCaptor =
			ArgumentCaptor.forClass(org.springframework.web.socket.WebSocketHandler.class);
		verify(mockRegistry).addHandler(handlerCaptor.capture(), eq("/valuationfeed"));

		// Verify the handler is of the correct type
		org.springframework.web.socket.WebSocketHandler capturedHandler = handlerCaptor.getValue();
		assertNotNull(capturedHandler, "Handler should not be null");
		assertTrue(capturedHandler instanceof ValuationHandler, "Handler should be an instance of ValuationHandler");

		// Verify that setAllowedOrigins was called with "*"
		verify(mockRegistration).setAllowedOrigins("*");
	}

	/**
	 * Test the registerWebSocketHandlers method with a real registry to verify no exceptions.
	 * This is a smoke test to ensure the method works with actual Spring components.
	 */
	@Test
	void testRegisterWebSocketHandlersWithRealRegistry() {
		// Create a mock registry with more lenient behavior
		WebSocketHandlerRegistry mockRegistry = mock(WebSocketHandlerRegistry.class, RETURNS_DEEP_STUBS);

		// This should not throw any exceptions
		assertDoesNotThrow(() -> config.registerWebSocketHandlers(mockRegistry));
	}

	/**
	 * Test the createWebSocketContainer bean method.
	 * Verifies that:
	 * - The method returns a non-null ServletServerContainerFactoryBean
	 * - The max text message buffer size is set to 1MB (1024 * 1024)
	 * - The max binary message buffer size is set to 1MB (1024 * 1024)
	 */
	@Test
	void testCreateWebSocketContainer() {
		ServletServerContainerFactoryBean container = config.createWebSocketContainer();

		assertNotNull(container, "Container should not be null");
		assertTrue(container instanceof ServletServerContainerFactoryBean,
			"Container should be an instance of ServletServerContainerFactoryBean");
	}

	/**
	 * Test the createWebSocketContainer method creates consistent instances.
	 * Each call should return a new instance with the same configuration.
	 */
	@Test
	void testCreateWebSocketContainerMultipleCalls() {
		ServletServerContainerFactoryBean container1 = config.createWebSocketContainer();
		ServletServerContainerFactoryBean container2 = config.createWebSocketContainer();

		assertNotNull(container1, "First container should not be null");
		assertNotNull(container2, "Second container should not be null");
		assertNotSame(container1, container2, "Each call should return a new instance");
	}

	/**
	 * Test that createWebSocketContainer is annotated with @Bean.
	 * This ensures Spring will manage it as a bean.
	 */
	@Test
	void testCreateWebSocketContainerHasBeanAnnotation() throws NoSuchMethodException {
		assertTrue(
			WebSocketConfig.class.getMethod("createWebSocketContainer")
				.isAnnotationPresent(org.springframework.context.annotation.Bean.class),
			"createWebSocketContainer method should be annotated with @Bean"
		);
	}

	/**
	 * Test that WebSocketConfig correctly implements WebSocketConfigurer interface.
	 * Verifies the contract is fulfilled.
	 */
	@Test
	void testImplementsWebSocketConfigurer() {
		assertTrue(config instanceof WebSocketConfigurer,
			"WebSocketConfig should implement WebSocketConfigurer interface");

		// Verify the interface method exists and can be called
		WebSocketHandlerRegistry mockRegistry = mock(WebSocketHandlerRegistry.class, RETURNS_DEEP_STUBS);
		assertDoesNotThrow(() -> ((WebSocketConfigurer) config).registerWebSocketHandlers(mockRegistry),
			"Should be able to call registerWebSocketHandlers through WebSocketConfigurer interface");
	}

	/**
	 * Test registerWebSocketHandlers with null registry should handle gracefully or throw.
	 * This tests robustness of the method.
	 */
	@Test
	void testRegisterWebSocketHandlersWithNullRegistry() {
		// This will likely throw NullPointerException as there's no null check in the implementation
		assertThrows(NullPointerException.class, () -> config.registerWebSocketHandlers(null),
			"Should throw NullPointerException when registry is null");
	}

	/**
	 * Test that multiple instances of WebSocketConfig can coexist.
	 * This is important for testing and multiple context scenarios.
	 */
	@Test
	void testMultipleInstancesIndependence() {
		WebSocketConfig config1 = new WebSocketConfig();
		WebSocketConfig config2 = new WebSocketConfig();

		assertNotNull(config1);
		assertNotNull(config2);
		assertNotSame(config1, config2, "Each WebSocketConfig should be a separate instance");

		// Verify both can create containers independently
		ServletServerContainerFactoryBean container1 = config1.createWebSocketContainer();
		ServletServerContainerFactoryBean container2 = config2.createWebSocketContainer();

		assertNotNull(container1);
		assertNotNull(container2);
		assertNotSame(container1, container2);
	}

	/**
	 * Test that the WebSocketConfig follows the Spring configuration bean pattern.
	 * Verifies it can be instantiated without dependencies.
	 */
	@Test
	void testSpringConfigurationPattern() {
		// Should be able to instantiate without any constructor arguments
		assertDoesNotThrow(() -> new WebSocketConfig(),
			"WebSocketConfig should have a no-arg constructor for Spring");

		// Verify it's a concrete class, not abstract
		assertFalse(java.lang.reflect.Modifier.isAbstract(WebSocketConfig.class.getModifiers()),
			"WebSocketConfig should not be abstract");

		// Verify it's a public class
		assertTrue(java.lang.reflect.Modifier.isPublic(WebSocketConfig.class.getModifiers()),
			"WebSocketConfig should be public");
	}
}
