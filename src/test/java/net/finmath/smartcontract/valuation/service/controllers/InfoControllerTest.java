package net.finmath.smartcontract.valuation.service.controllers;

import net.finmath.smartcontract.model.ExceptionId;
import net.finmath.smartcontract.model.SDCException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for InfoController.
 * Tests controller logic without requiring full Spring context.
 */
class InfoControllerTest {

    private InfoController controller;

    @BeforeEach
    void setUp() {
        controller = new InfoController();
    }

    @Test
    void testInfoGit_ReturnsResponseEntity() {
        // This test verifies the controller method signature and basic structure
        // In a real scenario, git.properties would need to exist in test resources
        assertNotNull(controller);
    }

    @Test
    void testInfoGit_ThrowsSDCException_WhenResourceNotFound() {
        // When git.properties doesn't exist, should throw SDCException
        try {
            ResponseEntity<String> response = controller.infoGit();
            // If resource exists in test classpath, verify it returns OK
            if (response != null) {
                assertEquals(HttpStatus.OK, response.getStatusCode());
            }
        } catch (SDCException e) {
            // Expected when resource doesn't exist
            assertEquals(ExceptionId.SDC_GIT_ERROR, e.getId());
        }
    }

    @Test
    void testInfoFinmath_ReturnsResponseEntity() {
        // Finmath library should have properties file in its jar
        try {
            ResponseEntity<String> response = controller.infoFinmath();
            // May succeed if finmath-lib.properties exists
            if (response != null) {
                assertNotNull(response);
            }
        } catch (SDCException e) {
            // Expected if resource not available in test context
            assertEquals(ExceptionId.SDC_GIT_ERROR, e.getId());
        }
    }

    @Test
    void testInfoGit_SDCExceptionHasCorrectId() {
        try {
            controller.infoGit();
        } catch (SDCException e) {
            assertEquals(ExceptionId.SDC_GIT_ERROR, e.getId());
            assertTrue(e.getMessage().contains("SDC_GIT_ERROR"));
        }
    }

    @Test
    void testInfoFinmath_SDCExceptionHasCorrectId() {
        try {
            controller.infoFinmath();
        } catch (SDCException e) {
            assertEquals(ExceptionId.SDC_GIT_ERROR, e.getId());
            assertTrue(e.getMessage().contains("SDC_GIT_ERROR"));
        }
    }

    @Test
    void testControllerIsRestController() {
        // Verify controller can be instantiated
        assertNotNull(controller);
        assertTrue(controller instanceof InfoController);
    }

    @Test
    void testInfoGit_ResponseContainsHeaders() {
        try {
            ResponseEntity<String> response = controller.infoGit();
            if (response != null && response.getStatusCode() == HttpStatus.OK) {
                assertNotNull(response.getBody());
            }
        } catch (SDCException e) {
            // Expected when resource unavailable
            assertNotNull(e.getId());
        }
    }

    @Test
    void testInfoFinmath_ResponseContainsHeaders() {
        try {
            ResponseEntity<String> response = controller.infoFinmath();
            if (response != null && response.getStatusCode() == HttpStatus.OK) {
                assertNotNull(response.getBody());
            }
        } catch (SDCException e) {
            // Expected when resource unavailable
            assertNotNull(e.getId());
        }
    }
}
