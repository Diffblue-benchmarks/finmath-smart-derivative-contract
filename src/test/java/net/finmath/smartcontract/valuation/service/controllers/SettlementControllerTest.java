package net.finmath.smartcontract.valuation.service.controllers;

import net.finmath.smartcontract.model.InitialSettlementRequest;
import net.finmath.smartcontract.model.InitialSettlementResult;
import net.finmath.smartcontract.model.RegularSettlementRequest;
import net.finmath.smartcontract.model.RegularSettlementResult;
import net.finmath.smartcontract.valuation.service.utils.SettlementService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link SettlementController}.
 * <p>
 * Tests the controller as a plain Java object with mocked dependencies,
 * verifying correct delegation to {@link SettlementService} and HTTP 200 responses.
 */
@ExtendWith(MockitoExtension.class)
class SettlementControllerTest {

	@Mock
	private SettlementService settlementService;

	@InjectMocks
	private SettlementController settlementController;

	@Test
	void generateRegularSettlementResult_shouldDelegateToServiceAndReturnOk() {
		// Arrange
		RegularSettlementRequest request = mock(RegularSettlementRequest.class);
		RegularSettlementResult expectedResult = mock(RegularSettlementResult.class);
		when(settlementService.generateRegularSettlementResult(request)).thenReturn(expectedResult);

		// Act
		ResponseEntity<RegularSettlementResult> response = settlementController.generateRegularSettlementResult(request);

		// Assert
		assertNotNull(response, "Response should not be null");
		assertEquals(HttpStatus.OK, response.getStatusCode(), "HTTP status should be 200 OK");
		assertSame(expectedResult, response.getBody(), "Response body should be the result from the service");
		verify(settlementService, times(1)).generateRegularSettlementResult(request);
		verifyNoMoreInteractions(settlementService);
	}

	@Test
	void generateInitialSettlementResult_shouldDelegateToServiceAndReturnOk() {
		// Arrange
		InitialSettlementRequest request = mock(InitialSettlementRequest.class);
		InitialSettlementResult expectedResult = mock(InitialSettlementResult.class);
		when(settlementService.generateInitialSettlementResult(request)).thenReturn(expectedResult);

		// Act
		ResponseEntity<InitialSettlementResult> response = settlementController.generateInitialSettlementResult(request);

		// Assert
		assertNotNull(response, "Response should not be null");
		assertEquals(HttpStatus.OK, response.getStatusCode(), "HTTP status should be 200 OK");
		assertSame(expectedResult, response.getBody(), "Response body should be the result from the service");
		verify(settlementService, times(1)).generateInitialSettlementResult(request);
		verifyNoMoreInteractions(settlementService);
	}

	@Test
	void generateRegularSettlementResult_shouldPropagateServiceException() {
		// Arrange
		RegularSettlementRequest request = mock(RegularSettlementRequest.class);
		RuntimeException serviceException = new RuntimeException("Service failure");
		when(settlementService.generateRegularSettlementResult(request)).thenThrow(serviceException);

		// Act & Assert
		RuntimeException thrown = assertThrows(RuntimeException.class,
				() -> settlementController.generateRegularSettlementResult(request));
		assertEquals("Service failure", thrown.getMessage());
		verify(settlementService).generateRegularSettlementResult(request);
	}

	@Test
	void generateInitialSettlementResult_shouldPropagateServiceException() {
		// Arrange
		InitialSettlementRequest request = mock(InitialSettlementRequest.class);
		RuntimeException serviceException = new RuntimeException("Initial settlement failure");
		when(settlementService.generateInitialSettlementResult(request)).thenThrow(serviceException);

		// Act & Assert
		RuntimeException thrown = assertThrows(RuntimeException.class,
				() -> settlementController.generateInitialSettlementResult(request));
		assertEquals("Initial settlement failure", thrown.getMessage());
		verify(settlementService).generateInitialSettlementResult(request);
	}
}
