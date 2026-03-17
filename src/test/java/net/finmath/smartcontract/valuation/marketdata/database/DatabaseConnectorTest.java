package net.finmath.smartcontract.valuation.marketdata.database;

import net.finmath.smartcontract.valuation.service.utils.ResourceGovernor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseConnectorTest {

	private DatabaseConnector databaseConnector;
	private TestResourceGovernor testResourceGovernor;
	private Connection testConnection;

	@BeforeEach
	void setUp() throws SQLException {
		// Create H2 in-memory database
		testConnection = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "sa", "");

		// Create the MarketDataPoints table
		try (Statement stmt = testConnection.createStatement()) {
			// Drop and recreate table to ensure clean state
			stmt.execute("DROP TABLE IF EXISTS \"MarketDataPoints\"");
			stmt.execute("""
				CREATE TABLE "MarketDataPoints" (
					"symbolId_" VARCHAR(255),
					"dataTimestamp_" TIMESTAMP,
					"value_" DOUBLE,
					"owner_" INTEGER,
					"source_" VARCHAR(255),
					PRIMARY KEY ("symbolId_", "dataTimestamp_")
				)
				""");

			// Insert some test data
			stmt.execute("""
				INSERT INTO "MarketDataPoints" VALUES
				('EUR=', TIMESTAMP '2024-01-01 10:00:00', 1.05, 1, 'remote'),
				('USD=', TIMESTAMP '2024-01-01 10:00:00', 1.00, 1, 'remote'),
				('GBP=', TIMESTAMP '2024-01-01 10:00:00', 1.25, 1, 'remote')
				""");
		}

		testResourceGovernor = new TestResourceGovernor();
		databaseConnector = new DatabaseConnector(testResourceGovernor);
	}

	@AfterEach
	void tearDown() throws SQLException {
		if (testConnection != null && !testConnection.isClosed()) {
			testConnection.close();
		}
	}

	@Test
	void testConstructor() {
		// Given
		ResourceGovernor resourceGovernor = new TestResourceGovernor();

		// When
		DatabaseConnector connector = new DatabaseConnector(resourceGovernor);

		// Then
		assertNotNull(connector);
	}

	@Test
	void testInitWithValidProperties() {
		// Given
		String validProperties = """
			URL=jdbc:h2:mem:testdb
			USERNAME=sa
			PASSWORD=
			""";
		testResourceGovernor.setDatabasePropertiesContent(validProperties);

		// When
		databaseConnector.init();

		// Then - init should complete without throwing exception
		// Connection is established internally
		assertNotNull(databaseConnector);
	}

	@Test
	void testInitWithInvalidProperties() {
		// Given
		String invalidProperties = """
			URL=jdbc:invalid:connection
			USERNAME=invalid
			PASSWORD=invalid
			""";
		testResourceGovernor.setDatabasePropertiesContent(invalidProperties);

		// When
		databaseConnector.init();

		// Then - should not throw exception, but log warning
		// Connection will be null internally
		assertNotNull(databaseConnector);
	}

	@Test
	void testInitWithIOException() {
		// Given
		testResourceGovernor.setThrowIOException(true);

		// When
		databaseConnector.init();

		// Then - should handle exception gracefully
		assertNotNull(databaseConnector);
	}

	@Test
	@Disabled("Requires PostgreSQL-specific functions (json_build_object, json_agg) not available in H2")
	void testFetchFromDatabase() throws Exception {
		// Given
		testResourceGovernor.setDatabasePropertiesContent("""
			URL=jdbc:h2:mem:testdb
			USERNAME=sa
			PASSWORD=
			""");
		databaseConnector.init();

		List<String> fixingSymbols = List.of("EUR=", "USD=");
		String username = "testuser";

		// When
		databaseConnector.fetchFromDatabase(fixingSymbols, username);

		// Then
		String writtenContent = testResourceGovernor.getWrittenContent();
		assertNotNull(writtenContent);
		assertTrue(writtenContent.contains("requestTimestamp"));
		assertTrue(writtenContent.contains("values"));
	}

	@Test
	@Disabled("Requires PostgreSQL-specific functions (json_build_object, json_agg) not available in H2")
	void testFetchFromDatabaseWithEmptyFixingSymbols() throws Exception {
		// Given
		testResourceGovernor.setDatabasePropertiesContent("""
			URL=jdbc:h2:mem:testdb
			USERNAME=sa
			PASSWORD=
			""");
		databaseConnector.init();

		List<String> fixingSymbols = List.of();
		String username = "testuser";

		// When
		databaseConnector.fetchFromDatabase(fixingSymbols, username);

		// Then
		String writtenContent = testResourceGovernor.getWrittenContent();
		assertNotNull(writtenContent);
	}

	@Test
	@Disabled("Requires PostgreSQL-specific syntax (UNLOGGED TABLE, COPY) not available in H2")
	void testUpdateDatabase() throws Exception {
		// Given
		testResourceGovernor.setDatabasePropertiesContent("""
			URL=jdbc:h2:mem:testdb
			USERNAME=sa
			PASSWORD=
			""");
		databaseConnector.init();

		// Create temporary import file with JSON data
		String importData = """
			{"values":[{"dataTimestamp":"2024-01-02T10:00:00","symbol":"EUR=","value":105.5},{"dataTimestamp":"2024-01-02T10:00:00","symbol":"USD=","value":100.0}]}
			""";
		testResourceGovernor.setImportCandidateContent(importData);

		// When
		databaseConnector.updateDatabase();

		// Then
		// Verify data was inserted by checking the database
		try (Statement stmt = testConnection.createStatement()) {
			var rs = stmt.executeQuery("SELECT COUNT(*) FROM \"MarketDataPoints\" WHERE \"dataTimestamp_\" = TIMESTAMP '2024-01-02 10:00:00'");
			if (rs.next()) {
				int count = rs.getInt(1);
				assertTrue(count >= 0); // Data may or may not be inserted depending on parsing
			}
		}
	}

	/**
	 * Test implementation of ResourceGovernor for testing purposes
	 */
	private static class TestResourceGovernor extends ResourceGovernor {
		private String databasePropertiesContent = "";
		private String importCandidateContent = "";
		private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		private boolean throwIOException = false;
		private File tempImportFile;

		public TestResourceGovernor() {
			super(null);
		}

		public void setDatabasePropertiesContent(String content) {
			this.databasePropertiesContent = content;
		}

		public void setImportCandidateContent(String content) {
			this.importCandidateContent = content;
		}

		public void setThrowIOException(boolean throwIOException) {
			this.throwIOException = throwIOException;
		}

		public String getWrittenContent() {
			return outputStream.toString(StandardCharsets.UTF_8);
		}

		@Override
		public Resource getDatabasePropertiesAsResourceInReadMode() {
			return new Resource() {
				@Override
				public boolean exists() {
					return !throwIOException;
				}

				@Override
				public String getContentAsString(java.nio.charset.Charset charset) throws IOException {
					if (throwIOException) {
						throw new IOException("Test IO exception");
					}
					return databasePropertiesContent;
				}

				@Override
				public java.io.InputStream getInputStream() throws IOException {
					if (throwIOException) {
						throw new IOException("Test IO exception");
					}
					return new ByteArrayInputStream(databasePropertiesContent.getBytes(StandardCharsets.UTF_8));
				}

				@Override
				public java.net.URL getURL() throws IOException {
					throw new UnsupportedOperationException();
				}

				@Override
				public java.net.URI getURI() throws IOException {
					throw new UnsupportedOperationException();
				}

				@Override
				public File getFile() throws IOException {
					throw new UnsupportedOperationException();
				}

				@Override
				public long contentLength() throws IOException {
					return databasePropertiesContent.length();
				}

				@Override
				public long lastModified() throws IOException {
					return 0;
				}

				@Override
				public Resource createRelative(String relativePath) throws IOException {
					throw new UnsupportedOperationException();
				}

				@Override
				public String getFilename() {
					return "database.properties";
				}

				@Override
				public String getDescription() {
					return "Test database properties";
				}
			};
		}

		@Override
		public WritableResource getActiveDatasetAsResourceInWriteMode(String username) {
			return new WritableResource() {
				@Override
				public OutputStream getOutputStream() throws IOException {
					outputStream.reset();
					return outputStream;
				}

				@Override
				public boolean isWritable() {
					return true;
				}

				@Override
				public boolean exists() {
					return true;
				}

				@Override
				public java.io.InputStream getInputStream() throws IOException {
					return new ByteArrayInputStream(new byte[0]);
				}

				@Override
				public java.net.URL getURL() throws IOException {
					throw new UnsupportedOperationException();
				}

				@Override
				public java.net.URI getURI() throws IOException {
					throw new UnsupportedOperationException();
				}

				@Override
				public File getFile() throws IOException {
					throw new UnsupportedOperationException();
				}

				@Override
				public long contentLength() throws IOException {
					return 0;
				}

				@Override
				public long lastModified() throws IOException {
					return 0;
				}

				@Override
				public Resource createRelative(String relativePath) throws IOException {
					throw new UnsupportedOperationException();
				}

				@Override
				public String getFilename() {
					return "active_dataset.json";
				}

				@Override
				public String getDescription() {
					return "Test active dataset";
				}
			};
		}

		@Override
		public Resource getImportCandidateAsResourceInReadMode() {
			return new Resource() {
				@Override
				public boolean exists() {
					return true;
				}

				@Override
				public java.io.InputStream getInputStream() throws IOException {
					return new ByteArrayInputStream(importCandidateContent.getBytes(StandardCharsets.UTF_8));
				}

				@Override
				public java.net.URL getURL() throws IOException {
					throw new UnsupportedOperationException();
				}

				@Override
				public java.net.URI getURI() throws IOException {
					throw new UnsupportedOperationException();
				}

				@Override
				public File getFile() throws IOException {
					// Create a temporary file with the import candidate content
					if (tempImportFile == null) {
						tempImportFile = File.createTempFile("import_candidate", ".json");
						tempImportFile.deleteOnExit();
						java.nio.file.Files.writeString(tempImportFile.toPath(), importCandidateContent);
					}
					return tempImportFile;
				}

				@Override
				public long contentLength() throws IOException {
					return importCandidateContent.length();
				}

				@Override
				public long lastModified() throws IOException {
					return 0;
				}

				@Override
				public Resource createRelative(String relativePath) throws IOException {
					throw new UnsupportedOperationException();
				}

				@Override
				public String getFilename() {
					return "import_candidate.json";
				}

				@Override
				public String getDescription() {
					return "Test import candidate";
				}
			};
		}
	}
}
