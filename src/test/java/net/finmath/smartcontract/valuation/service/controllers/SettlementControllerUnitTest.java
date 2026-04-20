package net.finmath.smartcontract.valuation.service.controllers;

import net.finmath.smartcontract.model.InitialSettlementRequest;
import net.finmath.smartcontract.model.InitialSettlementResult;
import net.finmath.smartcontract.model.RegularSettlementRequest;
import net.finmath.smartcontract.model.RegularSettlementResult;
import net.finmath.smartcontract.valuation.service.utils.SettlementService;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SettlementControllerUnitTest {

	@Test
	void testGenerateRegularSettlementResult() {
		SettlementService settlementService = mock(SettlementService.class);
		SettlementController controller = new SettlementController(settlementService);

		RegularSettlementRequest request = new RegularSettlementRequest();
		RegularSettlementResult expectedResult = new RegularSettlementResult().generatedRegularSettlement("testSettlement");
		when(settlementService.generateRegularSettlementResult(request)).thenReturn(expectedResult);

		ResponseEntity<RegularSettlementResult> response = controller.generateRegularSettlementResult(request);

		assertEquals(200, response.getStatusCode().value());
		assertNotNull(response.getBody());
		assertEquals("testSettlement", response.getBody().getGeneratedRegularSettlement());
	}

	@Test
	void testGenerateInitialSettlementResult() {
		SettlementService settlementService = mock(SettlementService.class);
		SettlementController controller = new SettlementController(settlementService);

		InitialSettlementRequest request = new InitialSettlementRequest();
		InitialSettlementResult expectedResult = new InitialSettlementResult().generatedInitialSettlement("testInitialSettlement");
		when(settlementService.generateInitialSettlementResult(request)).thenReturn(expectedResult);

		ResponseEntity<InitialSettlementResult> response = controller.generateInitialSettlementResult(request);

		assertEquals(200, response.getStatusCode().value());
		assertNotNull(response.getBody());
		assertEquals("testInitialSettlement", response.getBody().getGeneratedInitialSettlement());
	}
}
