package net.finmath.smartcontract.valuation.service.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import net.finmath.smartcontract.model.InitialSettlementRequest;
import net.finmath.smartcontract.model.InitialSettlementResult;
import net.finmath.smartcontract.model.RegularSettlementRequest;
import net.finmath.smartcontract.model.RegularSettlementResult;
import net.finmath.smartcontract.valuation.service.utils.SettlementService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {SettlementController.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class SettlementControllerDiffblueTest {
  @Autowired private SettlementController settlementController;

  @MockBean private SettlementService settlementService;

  /**
   * Test {@link SettlementController#generateRegularSettlementResult(RegularSettlementRequest)}.
   *
   * <p>Method under test: {@link
   * SettlementController#generateRegularSettlementResult(RegularSettlementRequest)}
   */
  @Test
  @DisplayName("Test generateRegularSettlementResult(RegularSettlementRequest)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ResponseEntity SettlementController.generateRegularSettlementResult(RegularSettlementRequest)"
  })
  void testGenerateRegularSettlementResult() {
    // Arrange
    RegularSettlementResult regularSettlementResult = new RegularSettlementResult();
    when(settlementService.generateRegularSettlementResult(Mockito.<RegularSettlementRequest>any()))
        .thenReturn(regularSettlementResult);

    // Act
    ResponseEntity<RegularSettlementResult> actualGenerateRegularSettlementResultResult =
        settlementController.generateRegularSettlementResult(new RegularSettlementRequest());

    // Assert
    verify(settlementService).generateRegularSettlementResult(isA(RegularSettlementRequest.class));
    HttpStatusCode statusCode = actualGenerateRegularSettlementResultResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertEquals(200, actualGenerateRegularSettlementResultResult.getStatusCodeValue());
    assertEquals(HttpStatus.OK, statusCode);
    assertTrue(actualGenerateRegularSettlementResultResult.hasBody());
    assertTrue(actualGenerateRegularSettlementResultResult.getHeaders().isEmpty());
    assertSame(regularSettlementResult, actualGenerateRegularSettlementResultResult.getBody());
  }

  /**
   * Test {@link SettlementController#generateInitialSettlementResult(InitialSettlementRequest)}.
   *
   * <p>Method under test: {@link
   * SettlementController#generateInitialSettlementResult(InitialSettlementRequest)}
   */
  @Test
  @DisplayName("Test generateInitialSettlementResult(InitialSettlementRequest)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ResponseEntity SettlementController.generateInitialSettlementResult(InitialSettlementRequest)"
  })
  void testGenerateInitialSettlementResult() {
    // Arrange
    InitialSettlementResult initialSettlementResult = new InitialSettlementResult();
    when(settlementService.generateInitialSettlementResult(Mockito.<InitialSettlementRequest>any()))
        .thenReturn(initialSettlementResult);

    // Act
    ResponseEntity<InitialSettlementResult> actualGenerateInitialSettlementResultResult =
        settlementController.generateInitialSettlementResult(new InitialSettlementRequest());

    // Assert
    verify(settlementService).generateInitialSettlementResult(isA(InitialSettlementRequest.class));
    HttpStatusCode statusCode = actualGenerateInitialSettlementResultResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertEquals(200, actualGenerateInitialSettlementResultResult.getStatusCodeValue());
    assertEquals(HttpStatus.OK, statusCode);
    assertTrue(actualGenerateInitialSettlementResultResult.hasBody());
    assertTrue(actualGenerateInitialSettlementResultResult.getHeaders().isEmpty());
    assertSame(initialSettlementResult, actualGenerateInitialSettlementResultResult.getBody());
  }
}
