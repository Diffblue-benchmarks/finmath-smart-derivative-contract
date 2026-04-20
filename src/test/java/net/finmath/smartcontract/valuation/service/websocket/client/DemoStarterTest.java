package net.finmath.smartcontract.valuation.service.websocket.client;

import io.reactivex.rxjava3.core.Observable;
import jakarta.websocket.Session;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

class DemoStarterTest {

	@Test
	void testMain() throws Exception {
		Session mockSession = mock(Session.class);
		when(mockSession.getMaxIdleTimeout()).thenReturn(30000L);
		when(mockSession.isOpen()).thenReturn(false);

		try (MockedConstruction<WebSocketClientEndpoint> mocked = mockConstruction(
				WebSocketClientEndpoint.class,
				(mock, context) -> {
					when(mock.getUserSession()).thenReturn(mockSession);
					doNothing().when(mock).sendTextMessage(anyString());
					when(mock.asObservable()).thenReturn(Observable.empty());
				}
		)) {
			assertDoesNotThrow(() -> DemoStarter.main(new String[]{}));
		}
	}
}
