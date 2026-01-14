/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */

package net.finmath.smartcontract.valuation.service.websocket.client;

import net.finmath.smartcontract.model.SDCException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for DemoStarter.
 * Tests all methods in the DemoStarter class with focus on branch and condition coverage.
 *
 * Testing approach:
 * - The DemoStarter class is a simple main class that creates a WebSocket client and connects to a server
 * - The main method requires an external WebSocket server running on localhost:443, which is not
 *   available in a test environment
 * - We test the constructor and verify that main() attempts to run and handles expected exceptions
 *   when the server is not available
 *
 * @author Claude Code
 */
class DemoStarterClaudeTest {

	/**
	 * Test the constructor creates a valid instance.
	 * This verifies that DemoStarter can be instantiated.
	 */
	@Test
	void testConstructor() {
		// Act
		final DemoStarter demoStarter = new DemoStarter();

		// Assert
		assertNotNull(demoStarter, "DemoStarter instance should not be null");
	}

	/**
	 * Test the main method.
	 *
	 * Since main() requires:
	 * 1. A WebSocket server running on localhost:443
	 * 2. The XML resource file to be available
	 *
	 * We expect it to fail with an SDCException when trying to connect to the non-existent server.
	 * This test verifies that:
	 * - The XML resource can be loaded successfully
	 * - The WebSocketClientEndpoint is created
	 * - An SDCException is thrown when attempting to connect to the unavailable server
	 *
	 * This approach tests the actual code path without mocking, which aligns with the
	 * requirement to avoid mocking when possible.
	 */
	@Test
	@Timeout(10) // Timeout to prevent hanging if the behavior changes
	void testMain_ThrowsExceptionWhenServerNotAvailable() {
		// Act & Assert
		// The main method will attempt to connect to ws://localhost:443/valuationfeed
		// Since no server is running, it should throw an SDCException when trying to
		// initialize the WebSocket session
		assertThrows(SDCException.class,
				() -> DemoStarter.main(new String[]{}),
				"main() should throw SDCException when WebSocket server is not available");
	}

	/**
	 * Test that main method can be called with null arguments array.
	 * The main method doesn't use its arguments, so it should handle null or empty arrays.
	 */
	@Test
	@Timeout(10)
	void testMain_WithNullArgs() {
		// Act & Assert
		// Since args is not used in the main method, null should be acceptable
		// The exception will still be thrown due to the missing WebSocket server
		assertThrows(SDCException.class,
				() -> DemoStarter.main(null),
				"main() should throw SDCException when WebSocket server is not available, regardless of args");
	}

	/**
	 * Test that main method can be called with various argument arrays.
	 * The main method doesn't use its arguments, so any args array should work the same.
	 */
	@Test
	@Timeout(10)
	void testMain_WithVariousArgs() {
		// Act & Assert
		// The args parameter is ignored, so any array should produce the same result
		assertThrows(SDCException.class,
				() -> DemoStarter.main(new String[]{"arg1", "arg2"}),
				"main() should throw SDCException when WebSocket server is not available, regardless of args");
	}

	/**
	 * Test that the main method loads the XML resource successfully before failing.
	 * We verify this by checking that the exception occurs during WebSocket connection,
	 * not during resource loading.
	 */
	@Test
	@Timeout(10)
	void testMain_VerifyExceptionIsFromWebSocketNotResourceLoading() {
		// Act
		final SDCException exception = assertThrows(SDCException.class,
				() -> DemoStarter.main(new String[]{}),
				"main() should throw SDCException");

		// Assert
		// The exception message should indicate a WebSocket connection error
		// If it were a resource loading error, we'd get a NullPointerException instead
		assertNotNull(exception.getMessage(), "Exception should have a message");
		assertTrue(exception.getMessage().contains("WEBSOCKET") ||
				   exception.getMessage().contains("Connection") ||
				   exception.getMessage().contains("connection"),
				"Exception should be related to WebSocket connection, not resource loading");
	}

	/**
	 * Test that the resource file exists and can be loaded.
	 * This test verifies a precondition for the main method to work correctly.
	 */
	@Test
	void testResourceFileExists() {
		// Act
		final var resourceStream = DemoStarter.class.getClassLoader()
				.getResourceAsStream("net.finmath.smartcontract.product.xml/smartderivativecontract.xml");

		// Assert
		assertNotNull(resourceStream,
				"The smartderivativecontract.xml resource file should exist in the classpath");
	}

	/**
	 * Test that the resource file can be read and is not empty.
	 * This ensures the XML file has content that can be sent via WebSocket.
	 */
	@Test
	void testResourceFileHasContent() throws Exception {
		// Act
		final var resourceStream = DemoStarter.class.getClassLoader()
				.getResourceAsStream("net.finmath.smartcontract.product.xml/smartderivativecontract.xml");
		assertNotNull(resourceStream, "Resource stream should not be null");

		final byte[] content = resourceStream.readAllBytes();

		// Assert
		assertNotNull(content, "Resource content should not be null");
		assertTrue(content.length > 0, "Resource content should not be empty");
	}
}
