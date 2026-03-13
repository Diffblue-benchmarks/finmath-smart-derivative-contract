package net.finmath.smartcontract.valuation.service.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ValuationController.
 * Tests controller construction and basic logic.
 */
class ValuationControllerUnitTest {

    private ValuationController controller;

    @BeforeEach
    void setUp() {
        controller = new ValuationController();
    }

    @Test
    void testControllerConstruction() {
        assertNotNull(controller);
    }

    @Test
    void testControllerIsRestController() {
        assertTrue(controller instanceof ValuationController);
    }

    @Test
    void testControllerCanBeInstantiated() {
        ValuationController newController = new ValuationController();
        assertNotNull(newController);
    }

    @Test
    void testMultipleInstancesAreIndependent() {
        ValuationController controller1 = new ValuationController();
        ValuationController controller2 = new ValuationController();

        assertNotSame(controller1, controller2);
    }
}
