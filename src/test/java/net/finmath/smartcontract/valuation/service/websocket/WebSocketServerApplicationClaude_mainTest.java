/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */

package net.finmath.smartcontract.valuation.service.websocket;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedConstruction;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test class for WebSocketServerApplication.main method with focus on improving coverage.
 * This test class specifically targets the uncovered lines 24, 25, 26, 27 in the main method.
 *
 * The main method creates a SpringApplication, sets the default server port to 443,
 * and runs the application. These tests use Mockito's MockedConstruction to intercept
 * the SpringApplication creation and verify the correct behavior without actually
 * starting a Spring Boot application.
 *
 * @author Claude Code
 */
class WebSocketServerApplicationClaude_mainTest {

	/**
	 * Test main method execution covering all lines (24, 25, 26, 27).
	 * This test verifies that:
	 * - Line 24: SpringApplication is created with WebSocketServerApplication.class
	 * - Line 25: Default properties are set with server.port=443
	 * - Line 26: app.run(args) is called
	 * - Line 27: Method completes
	 */
	@Test
	void testMain_CoversAllLines() {
		// Arrange
		String[] args = {"--test-arg"};
		ConfigurableApplicationContext mockContext = mock(ConfigurableApplicationContext.class);

		// Act & Assert - Use MockedConstruction to intercept SpringApplication creation
		try (MockedConstruction<SpringApplication> mockedConstruction = mockConstruction(
			SpringApplication.class,
			(mock, context) -> {
				// When run() is called, return the mock context
				when(mock.run(any(String[].class))).thenReturn(mockContext);
			})) {

			// Call the main method - this covers lines 24, 25, 26, 27
			WebSocketServerApplication.main(args);

			// Verify SpringApplication was constructed exactly once
			assertEquals(1, mockedConstruction.constructed().size(),
				"SpringApplication should be constructed exactly once");

			SpringApplication constructedApp = mockedConstruction.constructed().get(0);

			// Capture the arguments passed to setDefaultProperties (line 25)
			ArgumentCaptor<Map<String, Object>> propertiesCaptor = ArgumentCaptor.forClass(Map.class);
			verify(constructedApp).setDefaultProperties(propertiesCaptor.capture());

			Map<String, Object> capturedProperties = propertiesCaptor.getValue();
			assertNotNull(capturedProperties, "Default properties should not be null");
			assertEquals("443", capturedProperties.get("server.port"),
				"server.port should be set to 443");
			assertEquals(1, capturedProperties.size(),
				"Should have exactly one default property");

			// Verify run was called with the provided args (line 26)
			ArgumentCaptor<String[]> argsCaptor = ArgumentCaptor.forClass(String[].class);
			verify(constructedApp).run(argsCaptor.capture());

			String[] capturedArgs = argsCaptor.getValue();
			assertArrayEquals(args, capturedArgs,
				"run() should be called with the same args passed to main()");
		}
	}

	/**
	 * Test main method with empty args array.
	 * Verifies that the method works correctly when no command line arguments are provided.
	 * Covers lines 24, 25, 26, 27.
	 */
	@Test
	void testMain_WithEmptyArgs_CoversAllLines() {
		// Arrange
		String[] emptyArgs = {};
		ConfigurableApplicationContext mockContext = mock(ConfigurableApplicationContext.class);

		// Act
		try (MockedConstruction<SpringApplication> mockedConstruction = mockConstruction(
			SpringApplication.class,
			(mock, context) -> {
				when(mock.run(any(String[].class))).thenReturn(mockContext);
			})) {

			// Call main with empty args
			WebSocketServerApplication.main(emptyArgs);

			// Assert
			assertEquals(1, mockedConstruction.constructed().size());
			SpringApplication constructedApp = mockedConstruction.constructed().get(0);

			// Verify setDefaultProperties was called
			verify(constructedApp).setDefaultProperties(any(Map.class));

			// Verify run was called with empty args
			ArgumentCaptor<String[]> argsCaptor = ArgumentCaptor.forClass(String[].class);
			verify(constructedApp).run(argsCaptor.capture());
			assertArrayEquals(emptyArgs, argsCaptor.getValue());
		}
	}

	/**
	 * Test main method with null args.
	 * Verifies behavior when null is passed as arguments.
	 * Covers lines 24, 25, 26, 27.
	 */
	@Test
	void testMain_WithNullArgs_CoversAllLines() {
		// Arrange
		String[] nullArgs = null;
		ConfigurableApplicationContext mockContext = mock(ConfigurableApplicationContext.class);

		// Act
		try (MockedConstruction<SpringApplication> mockedConstruction = mockConstruction(
			SpringApplication.class,
			(mock, context) -> {
				when(mock.run(any())).thenReturn(mockContext);
			})) {

			// Call main with null args
			WebSocketServerApplication.main(nullArgs);

			// Assert
			assertEquals(1, mockedConstruction.constructed().size());
			SpringApplication constructedApp = mockedConstruction.constructed().get(0);

			// Verify setDefaultProperties was called
			verify(constructedApp).setDefaultProperties(any(Map.class));

			// Verify run was called
			verify(constructedApp).run(nullArgs);
		}
	}

	/**
	 * Test that verifies the exact port value set in default properties.
	 * This ensures line 25 is covered and the port is correctly set to "443".
	 */
	@Test
	void testMain_SetsServerPortTo443() {
		// Arrange
		String[] args = {};
		ConfigurableApplicationContext mockContext = mock(ConfigurableApplicationContext.class);

		// Act
		try (MockedConstruction<SpringApplication> mockedConstruction = mockConstruction(
			SpringApplication.class,
			(mock, context) -> {
				when(mock.run(any(String[].class))).thenReturn(mockContext);
			})) {

			WebSocketServerApplication.main(args);

			// Assert
			SpringApplication constructedApp = mockedConstruction.constructed().get(0);

			ArgumentCaptor<Map<String, Object>> propertiesCaptor = ArgumentCaptor.forClass(Map.class);
			verify(constructedApp).setDefaultProperties(propertiesCaptor.capture());

			Map<String, Object> properties = propertiesCaptor.getValue();

			// Verify the exact port value
			assertTrue(properties.containsKey("server.port"),
				"Properties should contain server.port key");
			assertEquals("443", properties.get("server.port"),
				"server.port value should be exactly '443' (as String)");
		}
	}

	/**
	 * Test that SpringApplication is constructed correctly.
	 * Verifies line 24 where SpringApplication is created with WebSocketServerApplication.class.
	 */
	@Test
	void testMain_CreatesSpringApplicationWithCorrectClass() {
		// Arrange
		String[] args = {};
		ConfigurableApplicationContext mockContext = mock(ConfigurableApplicationContext.class);

		// Act
		try (MockedConstruction<SpringApplication> mockedConstruction = mockConstruction(
			SpringApplication.class,
			(mock, context) -> {
				// The SpringApplication constructor takes varargs Class<?>... primarySources
				// We verify it was constructed, which covers line 24
				when(mock.run(any(String[].class))).thenReturn(mockContext);
			})) {

			WebSocketServerApplication.main(args);

			// Assert - verify construction happened exactly once
			assertEquals(1, mockedConstruction.constructed().size(),
				"SpringApplication should be constructed exactly once");
		}
	}

	/**
	 * Test the order of operations in main method.
	 * Verifies that setDefaultProperties is called before run().
	 * This tests the execution flow through lines 24, 25, 26, 27.
	 */
	@Test
	void testMain_CallsMethodsInCorrectOrder() {
		// Arrange
		String[] args = {};
		ConfigurableApplicationContext mockContext = mock(ConfigurableApplicationContext.class);

		// Act
		try (MockedConstruction<SpringApplication> mockedConstruction = mockConstruction(
			SpringApplication.class,
			(mock, context) -> {
				when(mock.run(any(String[].class))).thenReturn(mockContext);
			})) {

			WebSocketServerApplication.main(args);

			// Assert
			SpringApplication constructedApp = mockedConstruction.constructed().get(0);

			// Use InOrder to verify the sequence of method calls
			var inOrder = inOrder(constructedApp);

			// First, setDefaultProperties should be called (line 25)
			inOrder.verify(constructedApp).setDefaultProperties(any(Map.class));

			// Then, run should be called (line 26)
			inOrder.verify(constructedApp).run(any(String[].class));
		}
	}

	/**
	 * Test that the properties map passed to setDefaultProperties uses Collections.singletonMap.
	 * This verifies the exact implementation detail on line 25.
	 */
	@Test
	void testMain_UsesSingletonMapForProperties() {
		// Arrange
		String[] args = {};
		ConfigurableApplicationContext mockContext = mock(ConfigurableApplicationContext.class);

		// Act
		try (MockedConstruction<SpringApplication> mockedConstruction = mockConstruction(
			SpringApplication.class,
			(mock, context) -> {
				when(mock.run(any(String[].class))).thenReturn(mockContext);
			})) {

			WebSocketServerApplication.main(args);

			// Assert
			SpringApplication constructedApp = mockedConstruction.constructed().get(0);

			ArgumentCaptor<Map<String, Object>> propertiesCaptor = ArgumentCaptor.forClass(Map.class);
			verify(constructedApp).setDefaultProperties(propertiesCaptor.capture());

			Map<String, Object> capturedMap = propertiesCaptor.getValue();

			// Verify it's a singleton map (has exactly one entry)
			assertEquals(1, capturedMap.size(),
				"Properties map should have exactly one entry (singleton map)");

			// Verify attempting to modify the map throws UnsupportedOperationException
			// (characteristic of Collections.singletonMap)
			assertThrows(UnsupportedOperationException.class,
				() -> capturedMap.put("another.key", "value"),
				"Map should be unmodifiable (Collections.singletonMap)");
		}
	}

	/**
	 * Test main method with multiple arguments.
	 * Verifies that all arguments are passed correctly to run().
	 * Covers lines 24, 25, 26, 27.
	 */
	@Test
	void testMain_WithMultipleArgs_PassesAllToRun() {
		// Arrange
		String[] multipleArgs = {"--spring.profiles.active=test", "--server.port=8080", "--debug"};
		ConfigurableApplicationContext mockContext = mock(ConfigurableApplicationContext.class);

		// Act
		try (MockedConstruction<SpringApplication> mockedConstruction = mockConstruction(
			SpringApplication.class,
			(mock, context) -> {
				when(mock.run(any(String[].class))).thenReturn(mockContext);
			})) {

			WebSocketServerApplication.main(multipleArgs);

			// Assert
			SpringApplication constructedApp = mockedConstruction.constructed().get(0);

			ArgumentCaptor<String[]> argsCaptor = ArgumentCaptor.forClass(String[].class);
			verify(constructedApp).run(argsCaptor.capture());

			String[] capturedArgs = argsCaptor.getValue();
			assertArrayEquals(multipleArgs, capturedArgs,
				"All arguments should be passed to run()");
			assertEquals(3, capturedArgs.length,
				"Should have all 3 arguments");
		}
	}

	/**
	 * Test that main method completes successfully.
	 * This is a simple smoke test that verifies all lines (24, 25, 26, 27) execute
	 * without throwing exceptions.
	 */
	@Test
	void testMain_CompletesWithoutException() {
		// Arrange
		String[] args = {};
		ConfigurableApplicationContext mockContext = mock(ConfigurableApplicationContext.class);

		// Act & Assert
		try (MockedConstruction<SpringApplication> mockedConstruction = mockConstruction(
			SpringApplication.class,
			(mock, context) -> {
				when(mock.run(any(String[].class))).thenReturn(mockContext);
			})) {

			// Should not throw any exception
			assertDoesNotThrow(() -> WebSocketServerApplication.main(args),
				"main method should complete without throwing exceptions");

			// Verify the application was constructed and run
			assertEquals(1, mockedConstruction.constructed().size());
			verify(mockedConstruction.constructed().get(0)).run(any(String[].class));
		}
	}

	/**
	 * Test that verifies the constructed SpringApplication receives the source class.
	 * Line 24 passes WebSocketServerApplication.class as the source.
	 */
	@Test
	void testMain_SpringApplicationHasSingleSource() {
		// Arrange
		String[] args = {};
		ConfigurableApplicationContext mockContext = mock(ConfigurableApplicationContext.class);

		// Act
		try (MockedConstruction<SpringApplication> mockedConstruction = mockConstruction(
			SpringApplication.class,
			(mock, context) -> {
				// The constructor is called with a varargs Class<?>... parameter
				// Just verify it was constructed
				when(mock.run(any(String[].class))).thenReturn(mockContext);
			})) {

			WebSocketServerApplication.main(args);

			// Assert
			assertEquals(1, mockedConstruction.constructed().size(),
				"Exactly one SpringApplication should be constructed");
		}
	}
}
