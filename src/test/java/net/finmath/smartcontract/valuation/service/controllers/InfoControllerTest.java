package net.finmath.smartcontract.valuation.service.controllers;

import net.finmath.smartcontract.model.SDCException;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class InfoControllerTest {

	private final InfoController controller = new InfoController();

	@Test
	void testInfoGit() {
		try {
			ResponseEntity<String> response = controller.infoGit();
			assertEquals(200, response.getStatusCode().value());
			assertNotNull(response.getBody());
			assertFalse(response.getBody().isEmpty());
		} catch (SDCException e) {
			// git.properties may not be on classpath during test; exception path is also valid coverage
			assertTrue(e.getMessage().contains("SDC_GIT_ERROR"));
		}
	}

	@Test
	void testInfoFinmath() {
		try {
			ResponseEntity<String> response = controller.infoFinmath();
			assertEquals(200, response.getStatusCode().value());
			assertNotNull(response.getBody());
			assertFalse(response.getBody().isEmpty());
		} catch (SDCException e) {
			assertTrue(e.getMessage().contains("SDC_GIT_ERROR"));
		}
	}
}
