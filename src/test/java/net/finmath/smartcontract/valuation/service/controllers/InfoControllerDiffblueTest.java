package net.finmath.smartcontract.valuation.service.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {InfoController.class})
@ExtendWith(SpringExtension.class)
class InfoControllerDiffblueTest {
  @Autowired
  private InfoController infoController;

  /**
   * Method under test: {@link InfoController#infoFinmath()}
   */
  @Test
  void testInfoFinmath() {
    // Arrange and Act
    ResponseEntity<String> actualInfoFinmathResult = infoController.infoFinmath();

    // Assert
    HttpStatusCode statusCode = actualInfoFinmathResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertEquals(
        "{\n" + "  \"finmath-lib\" : {\n" + "    \"version\" : \"6.0.19\",\n" + "    \"name\" : \"finmath lib\",\n"
            + "    \"build\" : \"e7059ba349976b8a80e6d3256fbf89cf16ce4ea0\"\n" + "  }\n" + "}",
        actualInfoFinmathResult.getBody());
    assertEquals(200, actualInfoFinmathResult.getStatusCodeValue());
    assertEquals(HttpStatus.OK, statusCode);
    assertTrue(actualInfoFinmathResult.hasBody());
    assertTrue(actualInfoFinmathResult.getHeaders().isEmpty());
  }
}
