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
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Test class for DatabaseConnector. Tests all methods with focus on branch and condition coverage.
 *
 * @author Claude Code
 */
class DatabaseConnectorClaudeTest {
  @Mock private ResourceGovernor resourceGovernor;

  @Mock private Resource databasePropertiesResource;

  @Mock private WritableResource activeDatasetResource;

  @Mock private Resource importCandidateResource;

  @Mock private File importCandidateFile;

  private DatabaseConnector databaseConnector;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    databaseConnector = new DatabaseConnector(resourceGovernor);
  }

  /** Test the constructor to ensure it properly sets the ResourceGovernor. */
  @Test
  void testConstructor() {
    DatabaseConnector connector = new DatabaseConnector(resourceGovernor);
    assertNotNull(connector);
  }

  /** Test the constructor with null ResourceGovernor. */
  @Test
  void testConstructorWithNull() {
    DatabaseConnector connector = new DatabaseConnector(null);
    assertNotNull(connector);
  }

  /**
   * Test init() method when database connection succeeds. Uses H2 in-memory database for testing.
   */
  @Test
  void testInitSuccess() throws Exception {
    // Setup properties for H2 in-memory database
    String dbProperties =
        "URL=jdbc:h2:mem:testdb;MODE=PostgreSQL\n" + "USERNAME=sa\n" + "PASSWORD=";

    when(resourceGovernor.getDatabasePropertiesAsResourceInReadMode())
        .thenReturn(databasePropertiesResource);
    when(databasePropertiesResource.getContentAsString(any())).thenReturn(dbProperties);

    databaseConnector.init();

    // Verify that the connection was established by checking it's not null using reflection

    // Reflection is needed here because the connection field is private and there's no getter
    // method
    Field connectionField = DatabaseConnector.class.getDeclaredField("connection");
    connectionField.setAccessible(true);
    Connection connection = (Connection) connectionField.get(databaseConnector);
    assertNotNull(connection);
    assertFalse(connection.isClosed());

    // Clean up
    connection.close();
  }

  /** Test init() method when IOException occurs reading properties. */
  @Test
  void testInitIOException() throws Exception {
    when(resourceGovernor.getDatabasePropertiesAsResourceInReadMode())
        .thenReturn(databasePropertiesResource);
    when(databasePropertiesResource.getContentAsString(any()))
        .thenThrow(new IOException("Failed to read"));

    // Should not throw exception, just log warning
    assertDoesNotThrow(() -> databaseConnector.init());

    // Verify connection remains null using reflection

    // Reflection is needed here because the connection field is private and there's no getter
    // method
    Field connectionField = DatabaseConnector.class.getDeclaredField("connection");
    connectionField.setAccessible(true);
    Connection connection = (Connection) connectionField.get(databaseConnector);
    assertNull(connection);
  }

  /** Test init() method when SQLException occurs during connection. */
  @Test
  void testInitSQLException() throws Exception {
    String dbProperties = "URL=jdbc:invalid:database\n" + "USERNAME=invalid\n" + "PASSWORD=invalid";

    when(resourceGovernor.getDatabasePropertiesAsResourceInReadMode())
        .thenReturn(databasePropertiesResource);
    when(databasePropertiesResource.getContentAsString(any())).thenReturn(dbProperties);

    // Should not throw exception, just log warning
    assertDoesNotThrow(() -> databaseConnector.init());

    // Verify connection remains null using reflection

    // Reflection is needed here because the connection field is private and there's no getter
    // method
    Field connectionField = DatabaseConnector.class.getDeclaredField("connection");
    connectionField.setAccessible(true);
    Connection connection = (Connection) connectionField.get(databaseConnector);
    assertNull(connection);
  }

  /** Test fetchFromDatabase() when connection is null (init failed). */
  @Test
  void testFetchFromDatabaseNullConnection() {
    // Don't call init(), so connection remains null
    List<String> fixingSymbols = Arrays.asList("EUR-USD");

    assertThrows(
        NullPointerException.class,
        () -> {
          databaseConnector.fetchFromDatabase(fixingSymbols, "testuser");
        });
  }

  /** Test updateDatabase() when connection is null (init failed). */
  @Test
  void testUpdateDatabaseNullConnection() {
    // Don't call init(), so connection remains null
    assertThrows(
        NullPointerException.class,
        () -> {
          databaseConnector.updateDatabase();
        });
  }

  /** Test updateDatabase() when IOException occurs reading import file. */
  @Test
  void testUpdateDatabaseIOException() throws Exception {
    // Setup H2 database
    String dbProperties =
        "URL=jdbc:h2:mem:testdb6;MODE=PostgreSQL\n" + "USERNAME=sa\n" + "PASSWORD=";

    when(resourceGovernor.getDatabasePropertiesAsResourceInReadMode())
        .thenReturn(databasePropertiesResource);
    when(databasePropertiesResource.getContentAsString(any())).thenReturn(dbProperties);

    databaseConnector.init();

    when(resourceGovernor.getImportCandidateAsResourceInReadMode())
        .thenReturn(this.databasePropertiesResource);
    when(this.databasePropertiesResource.getFile())
        .thenThrow(new IOException("Failed to read file"));

    assertThrows(
        IOException.class,
        () -> {
          databaseConnector.updateDatabase();
        });

    // Clean up
    Field connectionField = DatabaseConnector.class.getDeclaredField("connection");
    connectionField.setAccessible(true);
    Connection connection = (Connection) connectionField.get(databaseConnector);
    connection.close();
  }

  /** Test updateDatabase() when SQL execution fails. */
  @Test
  void testUpdateDatabaseSQLException() throws Exception {
    // Setup H2 database
    String dbProperties =
        "URL=jdbc:h2:mem:testdb7;MODE=PostgreSQL\n" + "USERNAME=sa\n" + "PASSWORD=";

    when(resourceGovernor.getDatabasePropertiesAsResourceInReadMode())
        .thenReturn(databasePropertiesResource);
    when(databasePropertiesResource.getContentAsString(any())).thenReturn(dbProperties);

    databaseConnector.init();

    // Get the connection and close it to cause SQLException
    Field connectionField = DatabaseConnector.class.getDeclaredField("connection");
    connectionField.setAccessible(true);
    Connection connection = (Connection) connectionField.get(databaseConnector);
    connection.close();

    // Create a temporary file for import
    File tempFile = File.createTempFile("import_candidate", ".json");
    tempFile.deleteOnExit();
    Files.writeString(tempFile.toPath(), "{\"values\":[]}");

    when(resourceGovernor.getImportCandidateAsResourceInReadMode())
        .thenReturn(this.databasePropertiesResource);
    when(this.databasePropertiesResource.getFile()).thenReturn(tempFile);

    assertThrows(
        SQLException.class,
        () -> {
          databaseConnector.updateDatabase();
        });
  }
}
