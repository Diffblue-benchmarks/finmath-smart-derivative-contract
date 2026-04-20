package net.finmath.smartcontract.valuation.service.websocket;

import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Collections;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class WebSocketServerApplicationTest {

	@Test
	void testMain() {
		try (MockedConstruction<SpringApplication> construction = mockConstruction(SpringApplication.class,
				(mock, context) -> {
					when(mock.run(any(String[].class))).thenReturn(mock(ConfigurableApplicationContext.class));
				})) {

			WebSocketServerApplication.main(new String[]{});

			SpringApplication constructed = construction.constructed().get(0);
			verify(constructed).setDefaultProperties(Collections.singletonMap("server.port", "443"));
			verify(constructed).run(new String[]{});
		}
	}
}
