package net.finmath.smartcontract.valuation.service.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class InfoControllerTest {

	@Test
	void testInfoGit() {
		// Given
		final InfoController controller = new InfoController();

		// When
		final ResponseEntity<String> response = controller.infoGit();

		// Then
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertTrue(response.getBody().contains("git"));
	}

	@Test
	void testInfoFinmath() {
		// Given
		final InfoController controller = new InfoController();

		// When
		final ResponseEntity<String> response = controller.infoFinmath();

		// Then
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertTrue(response.getBody().contains("finmath"));
	}
}
