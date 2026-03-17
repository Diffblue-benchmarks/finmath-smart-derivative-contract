package net.finmath.smartcontract.valuation.service.websocket.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.web.socket.*;

import java.net.InetSocketAddress;
import java.net.URI;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ValuationHandlerTest {

	private ValuationHandler handler;

	@BeforeEach
	void setUp() {
		handler = new ValuationHandler();
	}

	@Test
	void supportsPartialMessages() {
		assertFalse(handler.supportsPartialMessages());
	}

	@Test
	void afterConnectionEstablished() throws Exception {
		WebSocketSession session = null;
		assertDoesNotThrow(() -> handler.afterConnectionEstablished(session));
	}

	@Test
	void afterConnectionClosed() throws Exception {
		WebSocketSession session = null;
		CloseStatus status = CloseStatus.NORMAL;
		assertDoesNotThrow(() -> handler.afterConnectionClosed(session, status));
	}

	@Test
	void handleTransportError() throws Exception {
		WebSocketSession session = null;
		Throwable exception = new RuntimeException("Test exception");
		assertDoesNotThrow(() -> handler.handleTransportError(session, exception));
	}

	@Test
	void handleMessageWithNonTextMessage() {
		WebSocketSession session = createMockSession();
		WebSocketMessage<?> message = new BinaryMessage(new byte[]{1, 2, 3});

		IllegalStateException exception = assertThrows(IllegalStateException.class,
				() -> handler.handleMessage(session, message));

		assertTrue(exception.getMessage().contains("Unexpected WebSocket message type"));
	}

	private WebSocketSession createMockSession() {
		return new WebSocketSession() {
			@Override
			public String getId() {
				return "test-session";
			}

			@Override
			public List<WebSocketExtension> getExtensions() {
				return List.of();
			}

			@Override
			public String getAcceptedProtocol() {
				return null;
			}

			@Override
			public void setTextMessageSizeLimit(int messageSizeLimit) {
			}

			@Override
			public int getTextMessageSizeLimit() {
				return 0;
			}

			@Override
			public void setBinaryMessageSizeLimit(int messageSizeLimit) {
			}

			@Override
			public int getBinaryMessageSizeLimit() {
				return 0;
			}

			@Override
			public HttpHeaders getHandshakeHeaders() {
				return new HttpHeaders();
			}

			@Override
			public URI getUri() {
				return null;
			}

			@Override
			public Map<String, Object> getAttributes() {
				return new HashMap<>();
			}

			@Override
			public Principal getPrincipal() {
				return null;
			}

			@Override
			public InetSocketAddress getLocalAddress() {
				return null;
			}

			@Override
			public InetSocketAddress getRemoteAddress() {
				return null;
			}

			@Override
			public boolean isOpen() {
				return true;
			}

			@Override
			public void close() {
			}

			@Override
			public void close(CloseStatus status) {
			}

			@Override
			public void sendMessage(WebSocketMessage<?> message) {
			}
		};
	}
}
