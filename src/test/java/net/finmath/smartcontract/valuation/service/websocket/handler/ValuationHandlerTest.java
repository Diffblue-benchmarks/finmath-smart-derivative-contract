package net.finmath.smartcontract.valuation.service.websocket.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ValuationHandlerTest {

	private ValuationHandler handler;
	private WebSocketSession session;

	@BeforeEach
	void setUp() {
		handler = new ValuationHandler();
		session = mock(WebSocketSession.class);
	}

	@Test
	void testSupportsPartialMessages() {
		assertFalse(handler.supportsPartialMessages());
	}

	@Test
	void testHandleTransportError() throws Exception {
		handler.handleTransportError(session, new RuntimeException("test error"));
	}

	@Test
	void testAfterConnectionEstablished() throws Exception {
		handler.afterConnectionEstablished(session);
	}

	@Test
	void testAfterConnectionClosed() throws Exception {
		handler.afterConnectionClosed(session, CloseStatus.NORMAL);
	}

	@Test
	void testHandleMessageWithNonTextMessage() {
		BinaryMessage binaryMessage = new BinaryMessage(new byte[]{1, 2, 3});
		assertThrows(IllegalStateException.class, () -> handler.handleMessage(session, binaryMessage));
	}

	@Test
	void testHandleMessageWithTextMessageThrowsOnInvalidXml() {
		TextMessage textMessage = new TextMessage("<invalid>xml</invalid>");
		assertThrows(Exception.class, () -> handler.handleMessage(session, textMessage));
	}
}
