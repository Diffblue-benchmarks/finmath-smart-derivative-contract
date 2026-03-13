package net.finmath.smartcontract.valuation.service.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link ValuationController}.
 * <p>
 * The controller's {@code margin}, {@code value}, and {@code valueAtTime} methods
 * instantiate {@link net.finmath.smartcontract.valuation.implementation.MarginCalculator}
 * internally, making them difficult to unit test without integration resources.
 * These tests focus on the {@code test()} endpoint, which has no external dependencies.
 */
class ValuationControllerTest {

	private ValuationController valuationController;

	@BeforeEach
	void setUp() {
		valuationController = new ValuationController();
	}

	@Test
	void test_shouldReturnConnectSuccessful() {
		// Act
		ResponseEntity<String> response = valuationController.test();

		// Assert
		assertNotNull(response, "Response should not be null");
		assertEquals(HttpStatus.OK, response.getStatusCode(), "HTTP status should be 200 OK");
		assertEquals("Connect successful", response.getBody(), "Body should contain the connect message");
	}

	@Test
	void test_shouldReturnJsonContentType() {
		// Act
		ResponseEntity<String> response = valuationController.test();

		// Assert
		assertNotNull(response.getHeaders().getContentType(), "Content-Type header should be set");
		assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType(),
				"Content-Type should be application/json");
	}

	@Test
	void test_shouldContainRespondedHeader() {
		// Act
		ResponseEntity<String> response = valuationController.test();

		// Assert
		assertTrue(response.getHeaders().containsKey("Responded"),
				"Response should contain the 'Responded' header");
		assertEquals("test", response.getHeaders().getFirst("Responded"),
				"'Responded' header value should be 'test'");
	}
}
