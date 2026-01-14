/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 14 Jan 2026
 */

package net.finmath.smartcontract.valuation.marketdata.database;

import net.finmath.smartcontract.valuation.service.utils.ResourceGovernor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.WritableResource;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Test class for DatabaseConnector.fetchFromDatabase method.
 * Focuses on achieving coverage for the fetchFromDatabase method.
 *
 * @author Claude Code
 */
class DatabaseConnectorClaude_fetchFromDatabaseTest {

	@Mock
	private ResourceGovernor resourceGovernor;

	@Mock
	private Connection mockConnection;

	@Mock
	private Statement mockStatement;

	@Mock
	private ResultSet mockResultSet;

	@Mock
	private WritableResource activeDatasetResource;

	private DatabaseConnector databaseConnector;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		databaseConnector = new DatabaseConnector(resourceGovernor);

		// Use reflection to set the mock connection
		// Reflection is needed because the connection field is private and can only be set via init()
		// which would require a real database. For testing fetchFromDatabase, we need to inject
		// a mock connection to control the Statement and ResultSet behavior.
		Field connectionField = DatabaseConnector.class.getDeclaredField("connection");
		connectionField.setAccessible(true);
		connectionField.set(databaseConnector, mockConnection);
	}

	/**
	 * Test fetchFromDatabase with successful execution and result set with data.
	 * This covers lines 73, 83, 97, 98, 100, 101, 102, 103, 104, 105, 106, 111.
	 */
	@Test
	void testFetchFromDatabaseSuccessWithData() throws Exception {
		// Setup mock behavior for successful query execution
		when(mockConnection.createStatement()).thenReturn(mockStatement);
		when(mockStatement.execute(anyString())).thenReturn(true);
		when(mockStatement.getResultSet()).thenReturn(mockResultSet);
		when(mockResultSet.next()).thenReturn(true);
		when(mockResultSet.getString(1)).thenReturn("{\"requestTimestamp\":\"2024-01-15T10:30:00Z\",\"values\":[{\"symbol\":\"EUR-USD\",\"value\":1.2345}]}");

		// Setup output stream
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		when(resourceGovernor.getActiveDatasetAsResourceInWriteMode(anyString())).thenReturn(activeDatasetResource);
		when(activeDatasetResource.getOutputStream()).thenReturn(outputStream);

		List<String> fixingSymbols = Arrays.asList("EUR-USD", "GBP-USD");

		// Execute the method
		databaseConnector.fetchFromDatabase(fixingSymbols, "testuser");

		// Verify interactions
		verify(mockConnection).createStatement();
		verify(mockStatement).execute(anyString());
		verify(mockStatement, times(2)).getResultSet(); // Called twice on lines 100 and 101
		verify(mockResultSet).next();
		verify(mockResultSet).getString(1);
		verify(resourceGovernor).getActiveDatasetAsResourceInWriteMode("testuser");
		verify(activeDatasetResource).getOutputStream();
		verify(mockStatement).close();

		// Verify data was written
		String writtenData = outputStream.toString();
		assertTrue(writtenData.contains("requestTimestamp"));
		assertTrue(writtenData.contains("EUR-USD"));
	}

	/**
	 * Test fetchFromDatabase with empty result set.
	 * This covers the case where next() returns false (line 100 condition is false).
	 */
	@Test
	void testFetchFromDatabaseEmptyResultSet() throws Exception {
		// Setup mock behavior for query with no results
		when(mockConnection.createStatement()).thenReturn(mockStatement);
		when(mockStatement.execute(anyString())).thenReturn(true);
		when(mockStatement.getResultSet()).thenReturn(mockResultSet);
		when(mockResultSet.next()).thenReturn(false); // No data in result set

		List<String> fixingSymbols = Arrays.asList("EUR-USD");

		// Execute the method - should complete without writing anything
		databaseConnector.fetchFromDatabase(fixingSymbols, "testuser");

		// Verify statement was created and executed
		verify(mockConnection).createStatement();
		verify(mockStatement).execute(anyString());
		verify(mockStatement).getResultSet();
		verify(mockResultSet).next();

		// Verify we never tried to get the string or write output
		verify(mockResultSet, never()).getString(anyInt());
		verify(resourceGovernor, never()).getActiveDatasetAsResourceInWriteMode(anyString());
		verify(mockStatement).close();
	}

	/**
	 * Test fetchFromDatabase with empty fixing symbols list.
	 */
	@Test
	void testFetchFromDatabaseEmptyFixingSymbols() throws Exception {
		// Setup mock behavior
		when(mockConnection.createStatement()).thenReturn(mockStatement);
		when(mockStatement.execute(anyString())).thenReturn(true);
		when(mockStatement.getResultSet()).thenReturn(mockResultSet);
		when(mockResultSet.next()).thenReturn(true);
		when(mockResultSet.getString(1)).thenReturn("{\"requestTimestamp\":\"2024-01-15T10:30:00Z\",\"values\":[]}");

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		when(resourceGovernor.getActiveDatasetAsResourceInWriteMode(anyString())).thenReturn(activeDatasetResource);
		when(activeDatasetResource.getOutputStream()).thenReturn(outputStream);

		List<String> fixingSymbols = Collections.emptyList();

		// Execute the method
		databaseConnector.fetchFromDatabase(fixingSymbols, "testuser");

		// Verify the method completes successfully
		verify(mockConnection).createStatement();
		verify(mockStatement).execute(anyString());
		verify(mockStatement).close();
	}

	/**
	 * Test fetchFromDatabase with single fixing symbol.
	 */
	@Test
	void testFetchFromDatabaseSingleSymbol() throws Exception {
		// Setup mock behavior
		when(mockConnection.createStatement()).thenReturn(mockStatement);
		when(mockStatement.execute(anyString())).thenReturn(true);
		when(mockStatement.getResultSet()).thenReturn(mockResultSet);
		when(mockResultSet.next()).thenReturn(true);
		when(mockResultSet.getString(1)).thenReturn("{\"requestTimestamp\":\"2024-01-15T10:30:00Z\",\"values\":[{\"symbol\":\"EUR-USD\",\"value\":1.2345}]}");

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		when(resourceGovernor.getActiveDatasetAsResourceInWriteMode(anyString())).thenReturn(activeDatasetResource);
		when(activeDatasetResource.getOutputStream()).thenReturn(outputStream);

		List<String> fixingSymbols = Collections.singletonList("EUR-USD");

		// Execute the method
		databaseConnector.fetchFromDatabase(fixingSymbols, "testuser");

		// Verify interactions
		verify(mockConnection).createStatement();
		verify(mockStatement).execute(anyString());
		verify(mockResultSet).getString(1);
		verify(mockStatement).close();

		// Verify data was written
		String writtenData = outputStream.toString();
		assertTrue(writtenData.contains("EUR-USD"));
	}

	/**
	 * Test fetchFromDatabase when execute returns false (no result set).
	 * This covers the branch where execute() returns false (line 83 condition is false).
	 */
	@Test
	void testFetchFromDatabaseExecuteReturnsFalse() throws Exception {
		// Setup mock behavior for execute returning false
		when(mockConnection.createStatement()).thenReturn(mockStatement);
		when(mockStatement.execute(anyString())).thenReturn(false);

		List<String> fixingSymbols = Arrays.asList("EUR-USD");

		// Execute the method
		databaseConnector.fetchFromDatabase(fixingSymbols, "testuser");

		// Verify statement was created and executed
		verify(mockConnection).createStatement();
		verify(mockStatement).execute(anyString());

		// Verify we never tried to get the result set
		verify(mockStatement, never()).getResultSet();
		verify(mockStatement).close();
	}

	/**
	 * Test fetchFromDatabase with multiple fixing symbols.
	 */
	@Test
	void testFetchFromDatabaseMultipleSymbols() throws Exception {
		// Setup mock behavior
		when(mockConnection.createStatement()).thenReturn(mockStatement);
		when(mockStatement.execute(anyString())).thenReturn(true);
		when(mockStatement.getResultSet()).thenReturn(mockResultSet);
		when(mockResultSet.next()).thenReturn(true);
		when(mockResultSet.getString(1)).thenReturn("{\"requestTimestamp\":\"2024-01-15T10:30:00Z\",\"values\":[" +
				"{\"symbol\":\"EUR-USD\",\"value\":1.2345}," +
				"{\"symbol\":\"GBP-USD\",\"value\":1.5678}," +
				"{\"symbol\":\"CHF-USD\",\"value\":0.9876}]}");

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		when(resourceGovernor.getActiveDatasetAsResourceInWriteMode(anyString())).thenReturn(activeDatasetResource);
		when(activeDatasetResource.getOutputStream()).thenReturn(outputStream);

		List<String> fixingSymbols = Arrays.asList("EUR-USD", "GBP-USD", "CHF-USD");

		// Execute the method
		databaseConnector.fetchFromDatabase(fixingSymbols, "testuser");

		// Verify interactions
		verify(mockConnection).createStatement();
		verify(mockStatement).execute(anyString());
		verify(mockResultSet).getString(1);
		verify(mockStatement).close();

		// Verify data was written with all symbols
		String writtenData = outputStream.toString();
		assertTrue(writtenData.contains("EUR-USD"));
		assertTrue(writtenData.contains("GBP-USD"));
		assertTrue(writtenData.contains("CHF-USD"));
	}

	/**
	 * Test fetchFromDatabase when SQLException occurs during statement creation.
	 */
	@Test
	void testFetchFromDatabaseSQLExceptionOnCreateStatement() throws Exception {
		when(mockConnection.createStatement()).thenThrow(new SQLException("Failed to create statement"));

		List<String> fixingSymbols = Arrays.asList("EUR-USD");

		// Should propagate the SQLException
		assertThrows(SQLException.class, () -> {
			databaseConnector.fetchFromDatabase(fixingSymbols, "testuser");
		});

		verify(mockConnection).createStatement();
	}

	/**
	 * Test fetchFromDatabase when SQLException occurs during execute.
	 */
	@Test
	void testFetchFromDatabaseSQLExceptionOnExecute() throws Exception {
		when(mockConnection.createStatement()).thenReturn(mockStatement);
		when(mockStatement.execute(anyString())).thenThrow(new SQLException("Failed to execute query"));

		List<String> fixingSymbols = Arrays.asList("EUR-USD");

		// Should propagate the SQLException
		assertThrows(SQLException.class, () -> {
			databaseConnector.fetchFromDatabase(fixingSymbols, "testuser");
		});

		verify(mockConnection).createStatement();
		verify(mockStatement).execute(anyString());
		verify(mockStatement).close();
	}

	/**
	 * Test fetchFromDatabase when IOException occurs writing output.
	 * This tests the IOException path on line 104-107.
	 */
	@Test
	void testFetchFromDatabaseIOExceptionOnWrite() throws Exception {
		// Setup mock behavior
		when(mockConnection.createStatement()).thenReturn(mockStatement);
		when(mockStatement.execute(anyString())).thenReturn(true);
		when(mockStatement.getResultSet()).thenReturn(mockResultSet);
		when(mockResultSet.next()).thenReturn(true);
		when(mockResultSet.getString(1)).thenReturn("{\"requestTimestamp\":\"2024-01-15T10:30:00Z\",\"values\":[]}");

		// Make getOutputStream throw IOException
		when(resourceGovernor.getActiveDatasetAsResourceInWriteMode(anyString())).thenReturn(activeDatasetResource);
		when(activeDatasetResource.getOutputStream()).thenThrow(new java.io.IOException("Failed to write"));

		List<String> fixingSymbols = Arrays.asList("EUR-USD");

		// Should propagate the IOException
		assertThrows(java.io.IOException.class, () -> {
			databaseConnector.fetchFromDatabase(fixingSymbols, "testuser");
		});

		verify(mockConnection).createStatement();
		verify(mockStatement).execute(anyString());
		verify(mockResultSet).getString(1);
		verify(resourceGovernor).getActiveDatasetAsResourceInWriteMode("testuser");
		verify(mockStatement).close();
	}

	/**
	 * Test fetchFromDatabase with different usernames to verify the username parameter is used.
	 */
	@Test
	void testFetchFromDatabaseDifferentUsernames() throws Exception {
		// Setup mock behavior
		when(mockConnection.createStatement()).thenReturn(mockStatement);
		when(mockStatement.execute(anyString())).thenReturn(true);
		when(mockStatement.getResultSet()).thenReturn(mockResultSet);
		when(mockResultSet.next()).thenReturn(true);
		when(mockResultSet.getString(1)).thenReturn("{\"requestTimestamp\":\"2024-01-15T10:30:00Z\",\"values\":[]}");

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		when(resourceGovernor.getActiveDatasetAsResourceInWriteMode(anyString())).thenReturn(activeDatasetResource);
		when(activeDatasetResource.getOutputStream()).thenReturn(outputStream);

		List<String> fixingSymbols = Arrays.asList("EUR-USD");

		// Test with first username
		databaseConnector.fetchFromDatabase(fixingSymbols, "user1");
		verify(resourceGovernor).getActiveDatasetAsResourceInWriteMode("user1");

		// Reset mocks and test with second username
		reset(mockConnection, mockStatement, mockResultSet, resourceGovernor, activeDatasetResource);

		// Re-setup mocks
		when(mockConnection.createStatement()).thenReturn(mockStatement);
		when(mockStatement.execute(anyString())).thenReturn(true);
		when(mockStatement.getResultSet()).thenReturn(mockResultSet);
		when(mockResultSet.next()).thenReturn(true);
		when(mockResultSet.getString(1)).thenReturn("{\"requestTimestamp\":\"2024-01-15T10:30:00Z\",\"values\":[]}");

		ByteArrayOutputStream outputStream2 = new ByteArrayOutputStream();
		when(resourceGovernor.getActiveDatasetAsResourceInWriteMode(anyString())).thenReturn(activeDatasetResource);
		when(activeDatasetResource.getOutputStream()).thenReturn(outputStream2);

		databaseConnector.fetchFromDatabase(fixingSymbols, "user2");
		verify(resourceGovernor).getActiveDatasetAsResourceInWriteMode("user2");
	}
}
