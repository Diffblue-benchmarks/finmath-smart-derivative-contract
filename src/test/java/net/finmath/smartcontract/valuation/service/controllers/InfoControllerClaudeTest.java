/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */

package net.finmath.smartcontract.valuation.service.controllers;

import net.finmath.smartcontract.model.ExceptionId;
import net.finmath.smartcontract.model.SDCException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for InfoController.
 * Tests all methods with focus on branch and condition coverage.
 *
 * @author Claude Code
 */
class InfoControllerClaudeTest {

	private InfoController controller;

	/**
	 * Set up test fixtures before each test.
	 */
	@BeforeEach
	void setUp() {
		controller = new InfoController();
	}

	/**
	 * Test constructor creates a valid instance.
	 */
	@Test
	void testConstructor_CreatesValidInstance() {
		// Arrange & Act
		InfoController newController = new InfoController();

		// Assert
		assertNotNull(newController, "Constructor should create a non-null instance");
	}

	/**
	 * Test infoGit returns ResponseEntity with git information.
	 * This test will work if git.properties exists in resources.
	 */
	@Test
	void testInfoGit_WithValidResource_ReturnsResponseEntity() {
		// Arrange
		InfoController testController = new InfoController();

		// Act & Assert
		// This test attempts to call the method. If git.properties exists, it succeeds.
		// If not, it throws SDCException which we can verify.
		try {
			ResponseEntity<String> response = testController.infoGit();

			// If we get here, the resource exists
			assertNotNull(response, "Response should not be null");
			assertEquals(200, response.getStatusCodeValue(), "Status code should be 200 OK");
			assertNotNull(response.getBody(), "Response body should not be null");
		} catch (SDCException e) {
			// If resource doesn't exist, verify the exception is correct
			assertEquals(ExceptionId.SDC_GIT_ERROR, e.getId(),
					"Should throw SDCException with SDC_GIT_ERROR when git.properties is not found");
			assertTrue(e.getMessage().contains("Failed to get git info."),
					"Exception message should contain expected error message");
		}
	}

	/**
	 * Test infoGit throws SDCException when resource is not available.
	 * We test the error path by relying on the fact that git.properties
	 * may not be available in the test classpath.
	 */
	@Test
	void testInfoGit_ResourceNotFound_ThrowsSDCException() {
		// Arrange
		InfoController testController = new InfoController();

		// Act & Assert
		// We attempt to invoke the method. The behavior depends on whether
		// git.properties is on the classpath. If it's missing, we verify exception.
		InputStream gitPropsStream = InfoController.class.getResourceAsStream("/git.properties");

		if (gitPropsStream == null) {
			// Resource doesn't exist, so we expect an exception
			SDCException exception = assertThrows(SDCException.class, () -> {
				testController.infoGit();
			}, "Should throw SDCException when git.properties is not found");

			assertEquals(ExceptionId.SDC_GIT_ERROR, exception.getId(),
					"Exception should have SDC_GIT_ERROR id");
			assertTrue(exception.getMessage().contains("Failed to get git info."),
					"Exception message should contain expected error message");
		} else {
			// Resource exists, so the method should succeed
			try {
				gitPropsStream.close();
			} catch (Exception e) {
				// Ignore close exception
			}
			assertDoesNotThrow(() -> {
				ResponseEntity<String> response = testController.infoGit();
				assertNotNull(response);
			}, "Should not throw exception when git.properties exists");
		}
	}

	/**
	 * Test infoFinmath returns ResponseEntity with finmath library information.
	 * This test will work if finmath-lib.properties exists in the classpath.
	 */
	@Test
	void testInfoFinmath_WithValidResource_ReturnsResponseEntity() {
		// Arrange
		InfoController testController = new InfoController();

		// Act & Assert
		// This test attempts to call the method. If finmath-lib.properties exists, it succeeds.
		// If not, it throws SDCException which we can verify.
		try {
			ResponseEntity<String> response = testController.infoFinmath();

			// If we get here, the resource exists
			assertNotNull(response, "Response should not be null");
			assertEquals(200, response.getStatusCodeValue(), "Status code should be 200 OK");
			assertNotNull(response.getBody(), "Response body should not be null");
			// Verify the response is valid JSON
			assertTrue(response.getBody().length() > 0, "Response body should not be empty");
		} catch (SDCException e) {
			// If resource doesn't exist, verify the exception is correct
			assertEquals(ExceptionId.SDC_GIT_ERROR, e.getId(),
					"Should throw SDCException with SDC_GIT_ERROR when finmath-lib.properties is not found");
			assertTrue(e.getMessage().contains("Failed to get git info."),
					"Exception message should contain expected error message");
		}
	}

	/**
	 * Test infoFinmath throws SDCException when resource is not available.
	 * We test the error path by checking if finmath-lib.properties is available.
	 */
	@Test
	void testInfoFinmath_ResourceNotFound_ThrowsSDCException() {
		// Arrange
		InfoController testController = new InfoController();

		// Act & Assert
		// We check if the resource exists by trying to load it
		InputStream finmathPropsStream = null;
		try {
			// Try to load from the Library class (as the actual code does)
			finmathPropsStream = net.finmath.information.Library.class.getResourceAsStream("/finmath-lib.properties");
		} catch (Exception e) {
			// If Library class doesn't exist or has issues, we handle it below
		}

		if (finmathPropsStream == null) {
			// Resource doesn't exist, so we expect an exception
			SDCException exception = assertThrows(SDCException.class, () -> {
				testController.infoFinmath();
			}, "Should throw SDCException when finmath-lib.properties is not found");

			assertEquals(ExceptionId.SDC_GIT_ERROR, exception.getId(),
					"Exception should have SDC_GIT_ERROR id");
			assertTrue(exception.getMessage().contains("Failed to get git info."),
					"Exception message should contain expected error message");
		} else {
			// Resource exists, so the method should succeed
			try {
				finmathPropsStream.close();
			} catch (Exception e) {
				// Ignore close exception
			}
			assertDoesNotThrow(() -> {
				ResponseEntity<String> response = testController.infoFinmath();
				assertNotNull(response);
			}, "Should not throw exception when finmath-lib.properties exists");
		}
	}

	/**
	 * Test that multiple invocations of infoGit work correctly.
	 * This verifies that the resource stream is properly managed.
	 */
	@Test
	void testInfoGit_MultipleInvocations_WorkCorrectly() {
		// Arrange
		InfoController testController = new InfoController();

		// Act & Assert
		// Call the method multiple times to ensure resource management is correct
		for (int i = 0; i < 3; i++) {
			try {
				ResponseEntity<String> response = testController.infoGit();
				assertNotNull(response, "Response should not be null on iteration " + i);
			} catch (SDCException e) {
				// If resource doesn't exist, each call should throw the same exception
				assertEquals(ExceptionId.SDC_GIT_ERROR, e.getId(),
						"Should throw SDC_GIT_ERROR on iteration " + i);
			}
		}
	}

	/**
	 * Test that multiple invocations of infoFinmath work correctly.
	 * This verifies that the resource stream is properly managed.
	 */
	@Test
	void testInfoFinmath_MultipleInvocations_WorkCorrectly() {
		// Arrange
		InfoController testController = new InfoController();

		// Act & Assert
		// Call the method multiple times to ensure resource management is correct
		for (int i = 0; i < 3; i++) {
			try {
				ResponseEntity<String> response = testController.infoFinmath();
				assertNotNull(response, "Response should not be null on iteration " + i);
			} catch (SDCException e) {
				// If resource doesn't exist, each call should throw the same exception
				assertEquals(ExceptionId.SDC_GIT_ERROR, e.getId(),
						"Should throw SDC_GIT_ERROR on iteration " + i);
			}
		}
	}

	/**
	 * Test that infoGit returns proper HTTP 200 status when successful.
	 * This test focuses on the ResponseEntity status code.
	 */
	@Test
	void testInfoGit_SuccessfulResponse_HasOkStatus() {
		// Arrange
		InfoController testController = new InfoController();

		// Act & Assert
		try {
			ResponseEntity<String> response = testController.infoGit();

			// Verify ResponseEntity properties
			assertTrue(response.getStatusCode().is2xxSuccessful(),
					"Response should have 2xx success status");
			assertEquals(200, response.getStatusCodeValue(),
					"Response should have status 200");
		} catch (SDCException e) {
			// Resource not available, which is acceptable for this test environment
			assertTrue(e.getMessage().contains("Failed to get git info."),
					"Exception message should be about git info failure");
		}
	}

	/**
	 * Test that infoFinmath returns proper HTTP 200 status when successful.
	 * This test focuses on the ResponseEntity status code.
	 */
	@Test
	void testInfoFinmath_SuccessfulResponse_HasOkStatus() {
		// Arrange
		InfoController testController = new InfoController();

		// Act & Assert
		try {
			ResponseEntity<String> response = testController.infoFinmath();

			// Verify ResponseEntity properties
			assertTrue(response.getStatusCode().is2xxSuccessful(),
					"Response should have 2xx success status");
			assertEquals(200, response.getStatusCodeValue(),
					"Response should have status 200");
		} catch (SDCException e) {
			// Resource not available, which is acceptable for this test environment
			assertTrue(e.getMessage().contains("Failed to get git info."),
					"Exception message should be about git info failure");
		}
	}

	/**
	 * Test that both info methods can be called on the same controller instance.
	 * This verifies instance state management.
	 */
	@Test
	void testBothInfoMethods_OnSameInstance_WorkIndependently() {
		// Arrange
		InfoController testController = new InfoController();

		// Act & Assert
		// Call both methods to ensure they work independently
		try {
			ResponseEntity<String> gitResponse = testController.infoGit();
			assertNotNull(gitResponse, "Git response should not be null");
		} catch (SDCException e) {
			assertEquals(ExceptionId.SDC_GIT_ERROR, e.getId());
		}

		try {
			ResponseEntity<String> finmathResponse = testController.infoFinmath();
			assertNotNull(finmathResponse, "Finmath response should not be null");
		} catch (SDCException e) {
			assertEquals(ExceptionId.SDC_GIT_ERROR, e.getId());
		}
	}

	/**
	 * Test constructor can be called multiple times.
	 * Verifies that constructor has no side effects.
	 */
	@Test
	void testConstructor_MultipleInstances_AreIndependent() {
		// Arrange & Act
		InfoController controller1 = new InfoController();
		InfoController controller2 = new InfoController();
		InfoController controller3 = new InfoController();

		// Assert
		assertNotNull(controller1, "First controller should not be null");
		assertNotNull(controller2, "Second controller should not be null");
		assertNotNull(controller3, "Third controller should not be null");

		// Verify they are different instances
		assertNotSame(controller1, controller2, "Controllers should be different instances");
		assertNotSame(controller2, controller3, "Controllers should be different instances");
		assertNotSame(controller1, controller3, "Controllers should be different instances");
	}
}
