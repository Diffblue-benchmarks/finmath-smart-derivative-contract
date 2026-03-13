package net.finmath.smartcontract.valuation.service.websocket;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link WebSocketConfig}.
 * Verifies that the WebSocket container bean is created with the expected buffer sizes.
 */
class WebSocketConfigTest {

	private final WebSocketConfig config = new WebSocketConfig();

	@Test
	@DisplayName("createWebSocketContainer returns non-null bean")
	void createWebSocketContainer_returnsNonNull() {
		ServletServerContainerFactoryBean container = config.createWebSocketContainer();

		assertNotNull(container, "WebSocket container bean should not be null");
	}

	@Test
	@DisplayName("createWebSocketContainer sets max text message buffer size to 1MB")
	void createWebSocketContainer_maxTextBufferSizeIs1MB() {
		ServletServerContainerFactoryBean container = config.createWebSocketContainer();

		assertEquals(1024 * 1024, container.getMaxTextMessageBufferSize(),
				"Max text message buffer size should be 1MB (1048576 bytes)");
	}

	@Test
	@DisplayName("createWebSocketContainer sets max binary message buffer size to 1MB")
	void createWebSocketContainer_maxBinaryBufferSizeIs1MB() {
		ServletServerContainerFactoryBean container = config.createWebSocketContainer();

		assertEquals(1024 * 1024, container.getMaxBinaryMessageBufferSize(),
				"Max binary message buffer size should be 1MB (1048576 bytes)");
	}
}
