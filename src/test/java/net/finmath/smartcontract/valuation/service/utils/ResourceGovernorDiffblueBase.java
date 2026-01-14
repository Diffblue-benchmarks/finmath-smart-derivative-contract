package net.finmath.smartcontract.valuation.service.utils;

import com.diffblue.cover.annotations.InterestingTestFactory;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Files;

/**
 * Custom base class for Diffblue Cover generated tests for ResourceGovernor.
 * Provides factory methods to create test instances with properly initialized fields.
 */
public abstract class ResourceGovernorDiffblueBase {

    /**
     * Factory method to create a ResourceGovernor with properly initialized fields.
     * This prevents NullPointerException when testing methods that use @Value injected fields.
     *
     * The factory sets up the ResourceGovernor with temporary directories for testing,
     * ensuring that importDir and storageBaseDir are not null when methods like
     * getImportCandidateAsResourceInReadMode() and getImportCandidateAsResourceInWriteMode()
     * are called.
     *
     * This factory avoids using mocks to prevent unused stubbing errors.
     *
     * @return A ResourceGovernor instance with all required fields properly initialized
     */
    @InterestingTestFactory
    public static ResourceGovernor createResourceGovernorWithInitializedFields() {
        try {
            // Create the ResourceGovernor instance
            ResourceGovernor resourceGovernor = new ResourceGovernor(new PathMatchingResourcePatternResolver());

            // Create temporary directories for testing
            File tempBaseDir = Files.createTempDirectory("test-storage-base").toFile();
            File tempImportDir = Files.createTempDirectory("test-import").toFile();

            // Ensure directories are cleaned up on JVM exit
            tempBaseDir.deleteOnExit();
            tempImportDir.deleteOnExit();

            // Use reflection to set the @Value injected fields
            setField(resourceGovernor, "storageBaseDir", tempBaseDir.getAbsolutePath());
            setField(resourceGovernor, "importDir", tempImportDir.getAbsolutePath());
            setField(resourceGovernor, "refinitivConnectionPropertiesFile",
                tempImportDir.getAbsolutePath() + "/refinitiv.properties");
            setField(resourceGovernor, "databaseConnectionPropertiesFile",
                tempImportDir.getAbsolutePath() + "/database.properties");

            return resourceGovernor;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create ResourceGovernor with initialized fields for testing", e);
        }
    }

    /**
     * Alternative factory method to create a ResourceGovernor for testing getReadableResource
     * and getWritableResource methods. This ensures storageBaseDir is properly initialized
     * to prevent NullPointerException.
     *
     * @return A ResourceGovernor instance with storageBaseDir initialized
     */
    @InterestingTestFactory
    public static ResourceGovernor createResourceGovernorForReadWriteMethods() {
        try {
            // Create the ResourceGovernor instance
            ResourceGovernor resourceGovernor = new ResourceGovernor(new PathMatchingResourcePatternResolver());

            // Create temporary directory for testing
            File tempBaseDir = Files.createTempDirectory("test-storage-base").toFile();

            // Ensure directory is cleaned up on JVM exit
            tempBaseDir.deleteOnExit();

            // Use reflection to set the @Value injected fields
            setField(resourceGovernor, "storageBaseDir", tempBaseDir.getAbsolutePath());
            setField(resourceGovernor, "importDir", tempBaseDir.getAbsolutePath());
            setField(resourceGovernor, "refinitivConnectionPropertiesFile",
                tempBaseDir.getAbsolutePath() + "/refinitiv.properties");
            setField(resourceGovernor, "databaseConnectionPropertiesFile",
                tempBaseDir.getAbsolutePath() + "/database.properties");

            return resourceGovernor;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create ResourceGovernor for read/write methods", e);
        }
    }

    /**
     * Helper method to set a private field using reflection.
     */
    private static void setField(Object target, String fieldName, Object value) throws Exception {
        Field field = ResourceGovernor.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }
}
