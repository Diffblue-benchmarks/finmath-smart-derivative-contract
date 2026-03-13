package net.finmath.smartcontract.valuation.service.websocket.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link ValuationHandler}.
 * Verifies that {@code supportsPartialMessages} returns false and that
 * the no-op lifecycle methods do not throw exceptions.
 */
class ValuationHandlerTest {

	private ValuationHandler handler;
	private WebSocketSession session;

	@BeforeEach
	void setUp() {
		handler = new ValuationHandler();
		session = mock(WebSocketSession.class);
	}

	@Test
	@DisplayName("supportsPartialMessages returns false")
	void supportsPartialMessages_returnsFalse() {
		assertFalse(handler.supportsPartialMessages(),
				"ValuationHandler should not support partial messages");
	}

	@Test
	@DisplayName("afterConnectionEstablished does not throw")
	void afterConnectionEstablished_doesNotThrow() {
		assertDoesNotThrow(() -> handler.afterConnectionEstablished(session),
				"afterConnectionEstablished should be a no-op and not throw");
	}

	@Test
	@DisplayName("afterConnectionClosed does not throw")
	void afterConnectionClosed_doesNotThrow() {
		CloseStatus closeStatus = mock(CloseStatus.class);

		assertDoesNotThrow(() -> handler.afterConnectionClosed(session, closeStatus),
				"afterConnectionClosed should be a no-op and not throw");
	}

	@Test
	@DisplayName("handleTransportError does not throw")
	void handleTransportError_doesNotThrow() {
		Throwable error = new RuntimeException("test transport error");

		assertDoesNotThrow(() -> handler.handleTransportError(session, error),
				"handleTransportError should be a no-op and not throw");
	}
}
