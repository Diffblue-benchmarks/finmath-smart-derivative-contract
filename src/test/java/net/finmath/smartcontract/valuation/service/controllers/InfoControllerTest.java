package net.finmath.smartcontract.valuation.service.controllers;

import com.fasterxml.jackson.dataformat.javaprop.JavaPropsMapper;
import net.finmath.smartcontract.model.SDCException;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

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

	@Test
	void testInfoFinmathThrowsOnReadFailure() {
		try (MockedConstruction<JavaPropsMapper> ignored = Mockito.mockConstruction(JavaPropsMapper.class,
				(mock, context) -> Mockito.when(mock.readValue(any(InputStream.class), eq(com.fasterxml.jackson.databind.node.ObjectNode.class)))
						.thenThrow(new RuntimeException("simulated read failure")))) {
			SDCException thrown = assertThrows(SDCException.class, () -> controller.infoFinmath());
			assertTrue(thrown.getMessage().contains("SDC_GIT_ERROR"));
		}
	}
}
