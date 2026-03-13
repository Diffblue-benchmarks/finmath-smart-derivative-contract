package net.finmath.smartcontract.valuation.service.controllers;

import net.finmath.smartcontract.fixtures.TestDataFixtures;
import net.finmath.smartcontract.model.InitialSettlementRequest;
import net.finmath.smartcontract.model.InitialSettlementResult;
import net.finmath.smartcontract.model.RegularSettlementRequest;
import net.finmath.smartcontract.model.RegularSettlementResult;
import net.finmath.smartcontract.valuation.service.utils.SettlementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for SettlementController using Mockito.
 * Tests controller logic without Spring context.
 */
@ExtendWith(MockitoExtension.class)
class SettlementControllerUnitTest {

    @Mock
    private SettlementService settlementService;

    private SettlementController controller;

    @BeforeEach
    void setUp() {
        controller = new SettlementController(settlementService);
    }

    @Test
    void testControllerConstruction() {
        assertNotNull(controller);
    }

    @Test
    void testGenerateInitialSettlementResult_CallsService() {
        // Arrange
        InitialSettlementRequest request = TestDataFixtures.createSampleInitialSettlementRequest();
        InitialSettlementResult expectedResult = TestDataFixtures.createSampleInitialSettlementResult();

        when(settlementService.generateInitialSettlementResult(any(InitialSettlementRequest.class)))
            .thenReturn(expectedResult);

        // Act
        ResponseEntity<InitialSettlementResult> response = controller.generateInitialSettlementResult(request);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(settlementService, times(1)).generateInitialSettlementResult(any(InitialSettlementRequest.class));
    }

    @Test
    void testGenerateRegularSettlementResult_CallsService() {
        // Arrange
        RegularSettlementRequest request = TestDataFixtures.createSampleRegularSettlementRequest();
        RegularSettlementResult expectedResult = TestDataFixtures.createSampleRegularSettlementResult();

        when(settlementService.generateRegularSettlementResult(any(RegularSettlementRequest.class)))
            .thenReturn(expectedResult);

        // Act
        ResponseEntity<RegularSettlementResult> response = controller.generateRegularSettlementResult(request);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(settlementService, times(1)).generateRegularSettlementResult(any(RegularSettlementRequest.class));
    }

    @Test
    void testGenerateInitialSettlementResult_ReturnsServiceResult() {
        // Arrange
        InitialSettlementRequest request = TestDataFixtures.createSampleInitialSettlementRequest();
        InitialSettlementResult expectedResult = TestDataFixtures.createSampleInitialSettlementResult();

        when(settlementService.generateInitialSettlementResult(any())).thenReturn(expectedResult);

        // Act
        ResponseEntity<InitialSettlementResult> response = controller.generateInitialSettlementResult(request);

        // Assert
        assertEquals(expectedResult, response.getBody());
    }

    @Test
    void testGenerateRegularSettlementResult_ReturnsServiceResult() {
        // Arrange
        RegularSettlementRequest request = TestDataFixtures.createSampleRegularSettlementRequest();
        RegularSettlementResult expectedResult = TestDataFixtures.createSampleRegularSettlementResult();

        when(settlementService.generateRegularSettlementResult(any())).thenReturn(expectedResult);

        // Act
        ResponseEntity<RegularSettlementResult> response = controller.generateRegularSettlementResult(request);

        // Assert
        assertEquals(expectedResult, response.getBody());
    }

    @Test
    void testControllerDependsOnService() {
        // Verify controller requires SettlementService
        assertNotNull(controller);
        verify(settlementService, never()).generateInitialSettlementResult(any());
    }
}
