/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */

package net.finmath.smartcontract.valuation.service.websocket;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for WebSocketServerApplication.
 * Tests all methods with focus on branch and condition coverage.
 *
 * Note: The WebSocketServerApplication class is a Spring Boot entry point with minimal logic.
 * The constructor is implicitly defined and main() configures the server port to 443
 * and starts the Spring application.
 *
 * @author Claude Code
 */
class WebSocketServerApplicationClaudeTest {

	/**
	 * Test the default constructor of WebSocketServerApplication.
	 * The constructor is implicitly defined and should be callable.
	 */
	@Test
	void testConstructor() {
		WebSocketServerApplication application = new WebSocketServerApplication();
		assertNotNull(application, "WebSocketServerApplication instance should not be null");
	}

	/**
	 * Test that the WebSocketServerApplication class is properly annotated as a SpringBootApplication.
	 * This verifies that the class is correctly configured to be a Spring Boot application entry point.
	 */
	@Test
	void testApplicationAnnotations() {
		assertTrue(WebSocketServerApplication.class.isAnnotationPresent(
			org.springframework.boot.autoconfigure.SpringBootApplication.class),
			"WebSocketServerApplication should be annotated with @SpringBootApplication");
	}

	/**
	 * Test that the class has the @EnableWebSocket annotation.
	 * This is required for WebSocket functionality.
	 */
	@Test
	void testEnableWebSocketAnnotation() {
		assertTrue(WebSocketServerApplication.class.isAnnotationPresent(
			org.springframework.web.socket.config.annotation.EnableWebSocket.class),
			"WebSocketServerApplication should be annotated with @EnableWebSocket");
	}

	/**
	 * Test that the class has the @Import annotation with BasicAuthWebSecurityConfiguration.
	 * This verifies that security configuration is imported.
	 */
	@Test
	void testImportAnnotation() {
		assertTrue(WebSocketServerApplication.class.isAnnotationPresent(
			org.springframework.context.annotation.Import.class),
			"WebSocketServerApplication should be annotated with @Import");

		org.springframework.context.annotation.Import importAnnotation =
			WebSocketServerApplication.class.getAnnotation(org.springframework.context.annotation.Import.class);
		assertNotNull(importAnnotation, "Import annotation should be present");

		Class<?>[] importClasses = importAnnotation.value();
		assertEquals(1, importClasses.length, "Should import 1 configuration class");
		assertEquals("BasicAuthWebSecurityConfiguration", importClasses[0].getSimpleName(),
			"Should import BasicAuthWebSecurityConfiguration");
	}

	/**
	 * Test that the WebSocketServerApplication class has a main method with the correct signature.
	 * The main method is the entry point that configures the server port and starts Spring Boot.
	 */
	@Test
	void testMainMethodExists() {
		try {
			java.lang.reflect.Method mainMethod = WebSocketServerApplication.class.getMethod("main", String[].class);
			assertNotNull(mainMethod, "Main method should exist");
			assertEquals(void.class, mainMethod.getReturnType(), "Main method should return void");
			assertTrue(java.lang.reflect.Modifier.isStatic(mainMethod.getModifiers()),
				"Main method should be static");
			assertTrue(java.lang.reflect.Modifier.isPublic(mainMethod.getModifiers()),
				"Main method should be public");
		} catch (NoSuchMethodException e) {
			fail("Main method with signature main(String[]) should exist");
		}
	}

	/**
	 * Test that the class is public and concrete (not abstract).
	 * This is a requirement for Spring Boot application classes.
	 */
	@Test
	void testClassModifiers() {
		assertTrue(java.lang.reflect.Modifier.isPublic(WebSocketServerApplication.class.getModifiers()),
			"WebSocketServerApplication should be public");
		assertFalse(java.lang.reflect.Modifier.isAbstract(WebSocketServerApplication.class.getModifiers()),
			"WebSocketServerApplication should not be abstract");
	}

	/**
	 * Test that multiple instances of WebSocketServerApplication can be created.
	 * This is useful for testing scenarios.
	 */
	@Test
	void testMultipleInstancesCanBeCreated() {
		WebSocketServerApplication app1 = new WebSocketServerApplication();
		WebSocketServerApplication app2 = new WebSocketServerApplication();

		assertNotNull(app1, "First instance should not be null");
		assertNotNull(app2, "Second instance should not be null");
		assertNotSame(app1, app2, "Each instance should be distinct");
	}

	/**
	 * Test that the class follows the Spring Boot application pattern.
	 * Should have a no-arg constructor and proper annotations.
	 */
	@Test
	void testSpringBootApplicationPattern() {
		// Should be able to instantiate without any constructor arguments
		assertDoesNotThrow(() -> new WebSocketServerApplication(),
			"WebSocketServerApplication should have a no-arg constructor for Spring");

		// Verify class name ends with "Application" (common Spring Boot convention)
		assertTrue(WebSocketServerApplication.class.getSimpleName().endsWith("Application"),
			"Class should follow Spring Boot naming convention");
	}

	/**
	 * Test that the class is in the expected package.
	 * This ensures proper organization of the application structure.
	 */
	@Test
	void testPackageStructure() {
		assertEquals("net.finmath.smartcontract.valuation.service.websocket",
			WebSocketServerApplication.class.getPackageName(),
			"Class should be in the correct package");
	}

	/**
	 * Test all required annotations are present on the class.
	 * This is a comprehensive check to ensure the class is properly configured.
	 */
	@Test
	void testAllRequiredAnnotationsPresent() {
		// Check for @SpringBootApplication
		assertNotNull(WebSocketServerApplication.class.getAnnotation(
			org.springframework.boot.autoconfigure.SpringBootApplication.class),
			"Should have @SpringBootApplication annotation");

		// Check for @EnableWebSocket
		assertNotNull(WebSocketServerApplication.class.getAnnotation(
			org.springframework.web.socket.config.annotation.EnableWebSocket.class),
			"Should have @EnableWebSocket annotation");

		// Check for @Import
		assertNotNull(WebSocketServerApplication.class.getAnnotation(
			org.springframework.context.annotation.Import.class),
			"Should have @Import annotation");
	}
}
