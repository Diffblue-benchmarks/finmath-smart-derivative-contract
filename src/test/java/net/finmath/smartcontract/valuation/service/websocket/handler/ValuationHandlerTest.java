package net.finmath.smartcontract.valuation.service.websocket.handler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

import static org.mockito.Mockito.mock;

class ValuationHandlerTest {

	@Test
	void supportsPartialMessages() {
		ValuationHandler handler = new ValuationHandler();
		Assertions.assertFalse(handler.supportsPartialMessages());
	}

	@Test
	void afterConnectionEstablished() throws Exception {
		ValuationHandler handler = new ValuationHandler();
		WebSocketSession session = mock(WebSocketSession.class);
		handler.afterConnectionEstablished(session);
	}

	@Test
	void afterConnectionClosed() throws Exception {
		ValuationHandler handler = new ValuationHandler();
		WebSocketSession session = mock(WebSocketSession.class);
		handler.afterConnectionClosed(session, CloseStatus.NORMAL);
	}

	@Test
	void handleTransportError() throws Exception {
		ValuationHandler handler = new ValuationHandler();
		WebSocketSession session = mock(WebSocketSession.class);
		handler.handleTransportError(session, new RuntimeException("test error"));
	}

	@Test
	void handleMessageNonTextThrowsIllegalStateException() throws Exception {
		ValuationHandler handler = new ValuationHandler();
		WebSocketSession session = mock(WebSocketSession.class);
		BinaryMessage binaryMessage = new BinaryMessage(new byte[]{1, 2, 3});
		Assertions.assertThrows(IllegalStateException.class, () -> handler.handleMessage(session, binaryMessage));
	}
}
