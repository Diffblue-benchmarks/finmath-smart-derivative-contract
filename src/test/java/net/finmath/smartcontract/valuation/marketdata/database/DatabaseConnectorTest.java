package net.finmath.smartcontract.valuation.marketdata.database;

import net.finmath.smartcontract.valuation.service.utils.ResourceGovernor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DatabaseConnectorTest {

	private ResourceGovernor resourceGovernor;
	private DatabaseConnector connector;

	@BeforeEach
	void setUp() {
		resourceGovernor = mock(ResourceGovernor.class);
		connector = new DatabaseConnector(resourceGovernor);
	}

	@Test
	void testConstructor() {
		assertNotNull(connector);
	}

	@Test
	void testInitSuccess() throws Exception {
		String properties = "URL=jdbc:postgresql://localhost:5432/testdb\nUSERNAME=user\nPASSWORD=pass\n";
		Resource resource = mock(Resource.class);
		when(resource.getContentAsString(StandardCharsets.UTF_8)).thenReturn(properties);
		when(resourceGovernor.getDatabasePropertiesAsResourceInReadMode()).thenReturn(resource);

		Connection mockConnection = mock(Connection.class);

		try (MockedStatic<DriverManager> driverManagerMock = mockStatic(DriverManager.class)) {
			driverManagerMock.when(() -> DriverManager.getConnection("jdbc:postgresql://localhost:5432/testdb", "user", "pass"))
					.thenReturn(mockConnection);

			connector.init();
		}

		Field connectionField = DatabaseConnector.class.getDeclaredField("connection");
		connectionField.setAccessible(true);
		assertNotNull(connectionField.get(connector));
	}

	@Test
	void testInitSQLException() throws Exception {
		String properties = "URL=jdbc:postgresql://localhost:5432/testdb\nUSERNAME=user\nPASSWORD=pass\n";
		Resource resource = mock(Resource.class);
		when(resource.getContentAsString(StandardCharsets.UTF_8)).thenReturn(properties);
		when(resourceGovernor.getDatabasePropertiesAsResourceInReadMode()).thenReturn(resource);

		SQLException sqlException = new SQLException("Connection refused");

		try (MockedStatic<DriverManager> driverManagerMock = mockStatic(DriverManager.class)) {
			driverManagerMock.when(() -> DriverManager.getConnection("jdbc:postgresql://localhost:5432/testdb", "user", "pass"))
					.thenThrow(sqlException);

			connector.init();
		}

		Field connectionField = DatabaseConnector.class.getDeclaredField("connection");
		connectionField.setAccessible(true);
		assertNull(connectionField.get(connector));
	}

	@Test
	void testInitIOException() throws Exception {
		Resource resource = mock(Resource.class);
		when(resourceGovernor.getDatabasePropertiesAsResourceInReadMode()).thenReturn(resource);
		when(resource.getContentAsString(StandardCharsets.UTF_8)).thenThrow(new IOException("File not found"));

		connector.init();

		Field connectionField = DatabaseConnector.class.getDeclaredField("connection");
		connectionField.setAccessible(true);
		assertNull(connectionField.get(connector));
	}

	@Test
	void testFetchFromDatabase() throws Exception {
		Connection mockConnection = mock(Connection.class);
		Statement mockStatement = mock(Statement.class);
		ResultSet mockResultSet = mock(ResultSet.class);
		OutputStream mockOutputStream = mock(OutputStream.class);
		WritableResource mockWritableResource = mock(WritableResource.class);

		when(mockConnection.createStatement()).thenReturn(mockStatement);
		when(mockStatement.execute(anyString())).thenReturn(true);
		when(mockStatement.getResultSet()).thenReturn(mockResultSet);
		when(mockResultSet.next()).thenReturn(true);
		when(mockResultSet.getString(1)).thenReturn("{\"requestTimestamp\":\"2024-01-01T00:00:00Z\",\"values\":[]}");
		when(resourceGovernor.getActiveDatasetAsResourceInWriteMode("testuser")).thenReturn(mockWritableResource);
		when(mockWritableResource.getOutputStream()).thenReturn(mockOutputStream);

		setConnectionField(mockConnection);

		connector.fetchFromDatabase(List.of("SYM1", "SYM2"), "testuser");

		verify(mockStatement).execute(anyString());
		verify(mockOutputStream).write(any(byte[].class));
		verify(mockOutputStream).close();
		verify(mockStatement).close();
	}

	@Test
	void testFetchFromDatabaseNoResults() throws Exception {
		Connection mockConnection = mock(Connection.class);
		Statement mockStatement = mock(Statement.class);
		ResultSet mockResultSet = mock(ResultSet.class);

		when(mockConnection.createStatement()).thenReturn(mockStatement);
		when(mockStatement.execute(anyString())).thenReturn(true);
		when(mockStatement.getResultSet()).thenReturn(mockResultSet);
		when(mockResultSet.next()).thenReturn(false);

		setConnectionField(mockConnection);

		connector.fetchFromDatabase(List.of("SYM1"), "testuser");

		verify(mockStatement).execute(anyString());
		verify(resourceGovernor, never()).getActiveDatasetAsResourceInWriteMode(anyString());
	}

	@Test
	void testUpdateDatabase() throws Exception {
		Connection mockConnection = mock(Connection.class);
		Statement mockCreationStatement = mock(Statement.class);
		Statement mockImportStatement = mock(Statement.class);
		Statement mockClearStatement = mock(Statement.class);

		when(mockConnection.createStatement())
				.thenReturn(mockCreationStatement)
				.thenReturn(mockImportStatement)
				.thenReturn(mockClearStatement);

		Resource mockResource = mock(Resource.class);
		File mockFile = mock(File.class);
		when(resourceGovernor.getImportCandidateAsResourceInReadMode()).thenReturn(mockResource);
		when(mockResource.getFile()).thenReturn(mockFile);
		when(mockFile.getAbsolutePath()).thenReturn("/tmp/import_candidate.json");

		setConnectionField(mockConnection);

		connector.updateDatabase();

		verify(mockCreationStatement).execute(anyString());
		verify(mockImportStatement).execute(anyString());
		verify(mockClearStatement).execute("DROP TABLE IF EXISTS import_helper;");
		verify(mockCreationStatement).close();
		verify(mockImportStatement).close();
		verify(mockClearStatement).close();
	}

	private void setConnectionField(Connection mockConnection) throws Exception {
		Field connectionField = DatabaseConnector.class.getDeclaredField("connection");
		connectionField.setAccessible(true);
		connectionField.set(connector, mockConnection);
	}
}
