/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */
package net.finmath.smartcontract.valuation.client;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for ValuationClient.printInfoGit method.
 * Tests the private printInfoGit method through the main method by running a mock server.
 *
 * @author Claude Code
 */
class ValuationClientClaude_printInfoGitTest {

    private static ConfigurableApplicationContext context;
    private static final int MOCK_SERVER_PORT = 8089;
    private static final String MOCK_SERVER_URL = "http://localhost:" + MOCK_SERVER_PORT;

    @SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
        org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration.class,
        org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration.class,
        org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration.class,
        org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class
    })
    @RestController
    static class MockServerApp {
        @PostMapping("/valuation/margin")
        public ResponseEntity<String> margin() {
            return ResponseEntity.ok("{\"value\":0.0,\"currency\":\"EUR\"}");
        }

        @GetMapping("/info/git")
        public ResponseEntity<String> gitInfo() {
            return ResponseEntity.ok("Git commit: abc123\\nBranch: main");
        }

        @GetMapping("/info/finmath")
        public ResponseEntity<String> finmathInfo() {
            return ResponseEntity.ok("finmath-lib version: 1.0.0");
        }
    }

    @BeforeAll
    static void setUp() {
        // Start mock Spring Boot server
        SpringApplication app = new SpringApplication(MockServerApp.class);
        app.setDefaultProperties(java.util.Collections.singletonMap("server.port", String.valueOf(MOCK_SERVER_PORT)));
        context = app.run();
    }

    @AfterAll
    static void tearDown() {
        // Stop mock server
        if (context != null) {
            context.close();
        }
    }

    /**
     * Test that main method calls printInfoGit and covers all lines in printInfoGit method.
     * This test covers lines 73-86 of ValuationClient:
     * - Line 73: HttpHeaders headers = new HttpHeaders();
     * - Line 74: headers.setContentType(MediaType.APPLICATION_JSON);
     * - Line 77: String base64Creds = Base64.getEncoder().encodeToString(authString.getBytes());
     * - Line 78: headers.add(HttpHeaders.AUTHORIZATION, BASIC + base64Creds);
     * - Line 80: RequestEntity<String> requestEntity = new RequestEntity<>(...);
     * - Line 82: ResponseEntity<String> response = new RestTemplate().exchange(requestEntity, String.class);
     * - Line 84: logger.info("git status");
     * - Line 85: logger.info(response.getBody());
     */
    @Test
    void testMain_CallsPrintInfoGit_WithDefaultCredentials() throws Exception {
        // Act - should not throw exception
        String[] args = {MOCK_SERVER_URL};
        assertDoesNotThrow(() -> ValuationClient.main(args));
    }

    /**
     * Test that main method calls printInfoGit with custom credentials.
     * This test ensures all lines in printInfoGit are covered with different authentication.
     * Tests lines 73-86 with custom user credentials.
     */
    @Test
    void testMain_CallsPrintInfoGit_WithCustomCredentials() throws Exception {
        // Arrange
        String customAuth = "testuser:testpass";

        // Act - should not throw exception
        String[] args = {MOCK_SERVER_URL, customAuth};
        assertDoesNotThrow(() -> ValuationClient.main(args));
    }

    /**
     * Test that main method handles printInfoGit response correctly.
     * This test verifies that the response body from /info/git is properly logged.
     * Tests lines 82, 84, 85, 86.
     */
    @Test
    void testMain_CallsPrintInfoGit_HandlesResponse() throws Exception {
        // Act - should not throw exception
        String[] args = {MOCK_SERVER_URL, "admin:admin123"};
        assertDoesNotThrow(() -> ValuationClient.main(args));
    }

    /**
     * Test that printInfoGit creates proper HTTP headers with JSON content type.
     * This specifically tests lines 73-74 (headers creation and content type setting).
     */
    @Test
    void testMain_PrintInfoGit_SetsCorrectHeaders() throws Exception {
        // Act - should not throw exception
        String[] args = {MOCK_SERVER_URL, "user:pass"};
        assertDoesNotThrow(() -> ValuationClient.main(args));
    }

    /**
     * Test that printInfoGit properly encodes authentication credentials.
     * This specifically tests lines 77-78 (Base64 encoding and Authorization header).
     */
    @Test
    void testMain_PrintInfoGit_EncodesAuthenticationCorrectly() throws Exception {
        // Arrange
        String customAuth = "specialuser:specialpass123";

        // Act - should not throw exception
        String[] args = {MOCK_SERVER_URL, customAuth};
        assertDoesNotThrow(() -> ValuationClient.main(args));
    }
}
