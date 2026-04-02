package net.finmath.smartcontract.valuation.marketdata.database;

import net.finmath.smartcontract.valuation.service.utils.ResourceGovernor;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class DatabaseConnectorTest {

	private static void injectConnection(DatabaseConnector connector, Connection connection) throws Exception {
		Field field = DatabaseConnector.class.getDeclaredField("connection");
		field.setAccessible(true);
		field.set(connector, connection);
	}

	@Test
	void testConstructorStoresResourceGovernor() {
		ResourceGovernor mockGovernor = mock(ResourceGovernor.class);

		DatabaseConnector connector = new DatabaseConnector(mockGovernor);

		assertNotNull(connector);
	}

	@Test
	void testInitHandlesIOExceptionGracefully() throws IOException {
		ResourceGovernor mockGovernor = mock(ResourceGovernor.class);
		Resource mockResource = mock(Resource.class);
		when(mockGovernor.getDatabasePropertiesAsResourceInReadMode()).thenReturn(mockResource);
		when(mockResource.getContentAsString(any(java.nio.charset.Charset.class)))
				.thenThrow(new IOException("simulated read failure"));

		DatabaseConnector connector = new DatabaseConnector(mockGovernor);

		assertDoesNotThrow(connector::init);
	}

	@Test
	void testInitHandlesSQLExceptionGracefully() throws IOException {
		ResourceGovernor mockGovernor = mock(ResourceGovernor.class);
		Resource mockResource = mock(Resource.class);
		when(mockGovernor.getDatabasePropertiesAsResourceInReadMode()).thenReturn(mockResource);
		when(mockResource.getContentAsString(any(java.nio.charset.Charset.class)))
				.thenReturn("URL=jdbc:invalid:url\nUSERNAME=user\nPASSWORD=pass");

		DatabaseConnector connector = new DatabaseConnector(mockGovernor);

		assertDoesNotThrow(connector::init);
	}

	@Test
	void testFetchFromDatabaseWritesResult() throws Exception {
		ResourceGovernor mockGovernor = mock(ResourceGovernor.class);
		Connection mockConnection = mock(Connection.class);
		Statement mockStatement = mock(Statement.class);
		ResultSet mockResultSet = mock(ResultSet.class);
		WritableResource mockWritable = mock(WritableResource.class);
		OutputStream mockOutputStream = mock(OutputStream.class);

		when(mockConnection.createStatement()).thenReturn(mockStatement);
		when(mockStatement.execute(anyString())).thenReturn(true);
		when(mockStatement.getResultSet()).thenReturn(mockResultSet);
		when(mockResultSet.next()).thenReturn(true);
		when(mockResultSet.getString(1)).thenReturn("{\"values\":[]}");
		when(mockGovernor.getActiveDatasetAsResourceInWriteMode(anyString())).thenReturn(mockWritable);
		when(mockWritable.getOutputStream()).thenReturn(mockOutputStream);

		DatabaseConnector connector = new DatabaseConnector(mockGovernor);
		injectConnection(connector, mockConnection);

		connector.fetchFromDatabase(List.of("EUR-EURIBOR-Reuters/3M"), "testuser");

		verify(mockStatement).execute(anyString());
		verify(mockOutputStream).write(any(byte[].class));
	}

	@Test
	void testFetchFromDatabaseNoResultSetRow() throws Exception {
		ResourceGovernor mockGovernor = mock(ResourceGovernor.class);
		Connection mockConnection = mock(Connection.class);
		Statement mockStatement = mock(Statement.class);
		ResultSet mockResultSet = mock(ResultSet.class);

		when(mockConnection.createStatement()).thenReturn(mockStatement);
		when(mockStatement.execute(anyString())).thenReturn(true);
		when(mockStatement.getResultSet()).thenReturn(mockResultSet);
		when(mockResultSet.next()).thenReturn(false);

		DatabaseConnector connector = new DatabaseConnector(mockGovernor);
		injectConnection(connector, mockConnection);

		assertDoesNotThrow(() -> connector.fetchFromDatabase(List.of("EUR-EURIBOR-Reuters/3M"), "testuser"));
		verify(mockGovernor, never()).getActiveDatasetAsResourceInWriteMode(anyString());
	}

	@Test
	void testUpdateDatabaseExecutesStatements() throws Exception {
		ResourceGovernor mockGovernor = mock(ResourceGovernor.class);
		Connection mockConnection = mock(Connection.class);
		Statement mockImportTableCreation = mock(Statement.class);
		Statement mockImport = mock(Statement.class);
		Statement mockClear = mock(Statement.class);
		Resource mockImportResource = mock(Resource.class);
		java.io.File tempFile = java.io.File.createTempFile("import_candidates", ".json");
		tempFile.deleteOnExit();

		when(mockConnection.createStatement())
				.thenReturn(mockImportTableCreation)
				.thenReturn(mockImport)
				.thenReturn(mockClear);
		when(mockGovernor.getImportCandidateAsResourceInReadMode()).thenReturn(mockImportResource);
		when(mockImportResource.getFile()).thenReturn(tempFile);

		DatabaseConnector connector = new DatabaseConnector(mockGovernor);
		injectConnection(connector, mockConnection);

		connector.updateDatabase();

		verify(mockImportTableCreation).execute(anyString());
		verify(mockImport).execute(anyString());
		verify(mockClear).execute("DROP TABLE IF EXISTS import_helper;");
	}
}
