package net.finmath.smartcontract.valuation.service.websocket.handler;

import static org.junit.jupiter.api.Assertions.assertFalse;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ValuationHandler.class})
@ExtendWith(SpringExtension.class)
class ValuationHandlerDiffblueTest {
  @Autowired
  private ValuationHandler valuationHandler;

  /**
   * Test {@link ValuationHandler#supportsPartialMessages()}.
   * <p>
   * Method under test: {@link ValuationHandler#supportsPartialMessages()}
   */
  @Test
  @DisplayName("Test supportsPartialMessages()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ValuationHandler.supportsPartialMessages()"})
  void testSupportsPartialMessages() {
    // Arrange, Act and Assert
    assertFalse(valuationHandler.supportsPartialMessages());
  }
}
