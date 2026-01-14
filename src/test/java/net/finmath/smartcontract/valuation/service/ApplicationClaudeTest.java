/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */

package net.finmath.smartcontract.valuation.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Application.
 * Tests all methods with focus on branch and condition coverage.
 *
 * Note: The Application class is a Spring Boot entry point with minimal logic.
 * The constructor is implicitly defined and main() calls SpringApplication.run().
 * We test the constructor and verify that the class has the expected annotations.
 *
 * @author Claude Code
 */
class ApplicationClaudeTest {

	/**
	 * Test the default constructor of Application.
	 * The constructor is implicitly defined and should be callable.
	 */
	@Test
	void testConstructor() {
		Application application = new Application();
		assertNotNull(application, "Application instance should not be null");
	}

	/**
	 * Test the Application class is properly annotated as a SpringBootApplication.
	 * This verifies that the class is correctly configured to be a Spring Boot application entry point.
	 */
	@Test
	void testApplicationAnnotations() {
		assertTrue(Application.class.isAnnotationPresent(
			org.springframework.boot.autoconfigure.SpringBootApplication.class),
			"Application should be annotated with @SpringBootApplication");
		assertTrue(Application.class.isAnnotationPresent(
			org.springframework.web.socket.config.annotation.EnableWebSocket.class),
			"Application should be annotated with @EnableWebSocket");
		assertTrue(Application.class.isAnnotationPresent(
			org.springframework.context.annotation.Import.class),
			"Application should be annotated with @Import");
		assertTrue(Application.class.isAnnotationPresent(
			org.springframework.context.annotation.ComponentScan.class),
			"Application should be annotated with @ComponentScan");
	}

	/**
	 * Test that the Application class has a main method with the correct signature.
	 * The main method is the entry point that sets timezone and starts Spring Boot.
	 */
	@Test
	void testMainMethodExists() {
		try {
			java.lang.reflect.Method mainMethod = Application.class.getMethod("main", String[].class);
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
	 * Test the ComponentScan annotation has the correct base packages configured.
	 * This ensures the application scans the correct packages for components.
	 */
	@Test
	void testComponentScanConfiguration() {
		org.springframework.context.annotation.ComponentScan componentScan =
			Application.class.getAnnotation(org.springframework.context.annotation.ComponentScan.class);
		assertNotNull(componentScan, "ComponentScan annotation should be present");

		String[] basePackages = componentScan.basePackages();
		assertEquals(2, basePackages.length, "Should have 2 base packages configured");
		assertTrue(java.util.Arrays.asList(basePackages).contains(
			"net.finmath.smartcontract.valuation.marketdata.database"),
			"Should scan marketdata.database package");
		assertTrue(java.util.Arrays.asList(basePackages).contains(
			"net.finmath.smartcontract.valuation.service"),
			"Should scan service package");
	}

	/**
	 * Test the Import annotation has the correct configuration class.
	 * This verifies that BasicAuthWebSecurityConfiguration is imported.
	 */
	@Test
	void testImportConfiguration() {
		org.springframework.context.annotation.Import importAnnotation =
			Application.class.getAnnotation(org.springframework.context.annotation.Import.class);
		assertNotNull(importAnnotation, "Import annotation should be present");

		Class<?>[] importClasses = importAnnotation.value();
		assertEquals(1, importClasses.length, "Should import 1 configuration class");
		assertEquals("BasicAuthWebSecurityConfiguration", importClasses[0].getSimpleName(),
			"Should import BasicAuthWebSecurityConfiguration");
	}

}
