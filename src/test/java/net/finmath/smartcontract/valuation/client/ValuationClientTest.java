package net.finmath.smartcontract.valuation.client;

import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

class ValuationClientTest {

	private HttpServer mockServer;
	private int port;

	@BeforeEach
	void setUp() throws IOException {
		mockServer = HttpServer.create(new InetSocketAddress(0), 0);
		port = mockServer.getAddress().getPort();

		String marginResponse = "{\"value\":9908.52,\"currency\":\"EUR\",\"valuationDate\":\"2023-01-01\"}";
		mockServer.createContext("/valuation/margin", exchange -> {
			byte[] responseBytes = marginResponse.getBytes(StandardCharsets.UTF_8);
			exchange.getResponseHeaders().add("Content-Type", "application/json");
			exchange.sendResponseHeaders(200, responseBytes.length);
			try (OutputStream os = exchange.getResponseBody()) {
				os.write(responseBytes);
			}
		});

		String gitResponse = "{\"git.branch\":\"main\"}";
		mockServer.createContext("/info/git", exchange -> {
			byte[] responseBytes = gitResponse.getBytes(StandardCharsets.UTF_8);
			exchange.getResponseHeaders().add("Content-Type", "application/json");
			exchange.sendResponseHeaders(200, responseBytes.length);
			try (OutputStream os = exchange.getResponseBody()) {
				os.write(responseBytes);
			}
		});

		String finmathResponse = "{\"version\":\"1.0.0\"}";
		mockServer.createContext("/info/finmath", exchange -> {
			byte[] responseBytes = finmathResponse.getBytes(StandardCharsets.UTF_8);
			exchange.getResponseHeaders().add("Content-Type", "application/json");
			exchange.sendResponseHeaders(200, responseBytes.length);
			try (OutputStream os = exchange.getResponseBody()) {
				os.write(responseBytes);
			}
		});

		mockServer.start();
	}

	@AfterEach
	void tearDown() {
		if (mockServer != null) {
			mockServer.stop(0);
		}
	}

	@Test
	void testMainWithTwoArgs() throws Exception {
		String url = "http://localhost:" + port;
		String[] args = {url, "user1:password1"};

		ValuationClient.main(args);
	}

	@Test
	void testMainWithOneArg() throws Exception {
		String url = "http://localhost:" + port;
		String[] args = {url};

		ValuationClient.main(args);
	}

	@Test
	void testMainWithNoArgsUsesDefaults() {
		try {
			ValuationClient.main(new String[0]);
		} catch (Exception e) {
			// Expected: no server on default localhost:8080
		}
	}
}
