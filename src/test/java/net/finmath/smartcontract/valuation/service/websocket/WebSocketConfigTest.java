package net.finmath.smartcontract.valuation.service.websocket;

import net.finmath.smartcontract.valuation.service.websocket.handler.ValuationHandler;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistration;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class WebSocketConfigTest {

	@Test
	void testRegisterWebSocketHandlers() {
		final WebSocketConfig config = new WebSocketConfig();
		final WebSocketHandlerRegistry registry = mock(WebSocketHandlerRegistry.class);
		final WebSocketHandlerRegistration registration = mock(WebSocketHandlerRegistration.class);

		when(registry.addHandler(any(ValuationHandler.class), eq("/valuationfeed")))
				.thenReturn(registration);
		when(registration.setAllowedOrigins("*")).thenReturn(registration);

		config.registerWebSocketHandlers(registry);

		verify(registry).addHandler(any(ValuationHandler.class), eq("/valuationfeed"));
		verify(registration).setAllowedOrigins("*");
	}

	@Test
	void testCreateWebSocketContainer() {
		final WebSocketConfig config = new WebSocketConfig();

		final ServletServerContainerFactoryBean container = config.createWebSocketContainer();

		assertNotNull(container);
		assertEquals(1024 * 1024, container.getMaxTextMessageBufferSize());
		assertEquals(1024 * 1024, container.getMaxBinaryMessageBufferSize());
	}
}
