package net.finmath.smartcontract.valuation.client;

import net.finmath.smartcontract.model.MarginResult;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ValuationClientTest {

	@Test
	void testMainWithDefaultArgs() {
		AtomicInteger counter = new AtomicInteger(0);

		try (MockedConstruction<RestTemplate> mocked = Mockito.mockConstruction(RestTemplate.class,
				(mock, context) -> {
					int idx = counter.getAndIncrement();
					if (idx == 0) {
						ResponseEntity<MarginResult> marginResponse = ResponseEntity.ok(new MarginResult());
						when(mock.exchange(any(RequestEntity.class), any(Class.class)))
								.thenReturn((ResponseEntity) marginResponse);
					} else {
						ResponseEntity<String> stringResponse = ResponseEntity.ok("info");
						when(mock.exchange(any(RequestEntity.class), any(Class.class)))
								.thenReturn((ResponseEntity) stringResponse);
					}
				})) {

			assertDoesNotThrow(() -> ValuationClient.main(new String[]{}));
		}
	}

	@Test
	void testMainWithUrlArg() {
		AtomicInteger counter = new AtomicInteger(0);

		try (MockedConstruction<RestTemplate> mocked = Mockito.mockConstruction(RestTemplate.class,
				(mock, context) -> {
					int idx = counter.getAndIncrement();
					if (idx == 0) {
						ResponseEntity<MarginResult> marginResponse = ResponseEntity.ok(new MarginResult());
						when(mock.exchange(any(RequestEntity.class), any(Class.class)))
								.thenReturn((ResponseEntity) marginResponse);
					} else {
						ResponseEntity<String> stringResponse = ResponseEntity.ok("git info");
						when(mock.exchange(any(RequestEntity.class), any(Class.class)))
								.thenReturn((ResponseEntity) stringResponse);
					}
				})) {

			assertDoesNotThrow(() -> ValuationClient.main(new String[]{"http://localhost:9090"}));
		}
	}

	@Test
	void testMainWithUrlAndCredentials() {
		AtomicInteger counter = new AtomicInteger(0);

		try (MockedConstruction<RestTemplate> mocked = Mockito.mockConstruction(RestTemplate.class,
				(mock, context) -> {
					int idx = counter.getAndIncrement();
					if (idx == 0) {
						ResponseEntity<MarginResult> marginResponse = ResponseEntity.ok(new MarginResult());
						when(mock.exchange(any(RequestEntity.class), any(Class.class)))
								.thenReturn((ResponseEntity) marginResponse);
					} else {
						ResponseEntity<String> stringResponse = ResponseEntity.ok("finmath info");
						when(mock.exchange(any(RequestEntity.class), any(Class.class)))
								.thenReturn((ResponseEntity) stringResponse);
					}
				})) {

			assertDoesNotThrow(() -> ValuationClient.main(new String[]{"http://localhost:9090", "admin:secret"}));
		}
	}
}
