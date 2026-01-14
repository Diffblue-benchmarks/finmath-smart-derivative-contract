package net.finmath.smartcontract.valuation.marketdata.database;

import com.diffblue.cover.annotations.InterestingTestFactory;
import net.finmath.smartcontract.valuation.service.utils.ResourceGovernor;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Custom base class for Diffblue Cover generated tests for DatabaseConnector.
 * This handles the initialization issue where ResourceGovernor.getDatabasePropertiesAsResourceInReadMode()
 * returns null during test context setup, causing a NullPointerException in the @PostConstruct init() method.
 */
public abstract class DatabaseConnectorDiffblueBase {

    @MockBean
    protected ResourceGovernor resourceGovernor;

    @BeforeEach
    public void setUpDatabaseConnectorBase() throws Exception {
        MockitoAnnotations.openMocks(this);

        // Create a mock Resource that returns a valid properties string
        Resource mockResource = mock(Resource.class);
        String validProperties = "URL=jdbc:postgresql://localhost:5432/test\n" +
                                "USERNAME=testuser\n" +
                                "PASSWORD=testpass";
        when(mockResource.getContentAsString(StandardCharsets.UTF_8)).thenReturn(validProperties);

        // Configure the resourceGovernor mock to return our mock Resource
        when(resourceGovernor.getDatabasePropertiesAsResourceInReadMode()).thenReturn(mockResource);
    }

    /**
     * Factory method to create a DatabaseConnector with a properly initialized connection.
     * This prevents NullPointerException when testing updateDatabase() method.
     *
     * Note: This creates an in-memory H2 database connection instead of trying to connect to PostgreSQL,
     * which allows tests to run without an actual database server.
     *
     * @return A DatabaseConnector instance with a non-null connection
     */
    @InterestingTestFactory
    public static DatabaseConnector createDatabaseConnectorWithConnection() {
        try {
            // Create a minimal ResourceGovernor mock
            ResourceGovernor resourceGovernor = mock(ResourceGovernor.class);

            // Create the DatabaseConnector
            DatabaseConnector connector = new DatabaseConnector(resourceGovernor);

            // Create an in-memory H2 database connection for testing
            // This avoids needing a real PostgreSQL server
            Connection connection = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "sa", "");

            // Use reflection to set the private connection field
            Field connectionField = DatabaseConnector.class.getDeclaredField("connection");
            connectionField.setAccessible(true);
            connectionField.set(connector, connection);

            return connector;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create DatabaseConnector with connection for testing", e);
        }
    }
}
