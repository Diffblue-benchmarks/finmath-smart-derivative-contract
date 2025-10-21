package net.finmath.smartcontract.valuation.service.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.File;
import java.io.IOException;
import net.finmath.smartcontract.valuation.service.utils.ResourceGovernor.RoleFolders;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ResourceGovernor.class})
@ExtendWith(SpringExtension.class)
class ResourceGovernorDiffblueTest {
  @Autowired
  private ResourceGovernor resourceGovernor;

  /**
   * Test {@link ResourceGovernor#getActiveDatasetAsResourceInReadMode(String)}.
   * <p>
   * Method under test: {@link ResourceGovernor#getActiveDatasetAsResourceInReadMode(String)}
   */
  @Test
  @DisplayName("Test getActiveDatasetAsResourceInReadMode(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Resource ResourceGovernor.getActiveDatasetAsResourceInReadMode(String)"})
  void testGetActiveDatasetAsResourceInReadMode() throws IOException {
    // Arrange and Act
    Resource actualActiveDatasetAsResourceInReadMode = resourceGovernor.getActiveDatasetAsResourceInReadMode("janedoe");

    // Assert
    assertTrue(actualActiveDatasetAsResourceInReadMode instanceof FileUrlResource);
    assertEquals("URL [file:/${storage.basedir}/janedoe.marketdata/active_dataset.json]",
        actualActiveDatasetAsResourceInReadMode.getDescription());
    File file = actualActiveDatasetAsResourceInReadMode.getFile();
    assertEquals("active_dataset.json", file.getName());
    assertEquals("active_dataset.json", actualActiveDatasetAsResourceInReadMode.getFilename());
    assertEquals("file:/${storage.basedir}/janedoe.marketdata/active_dataset.json",
        actualActiveDatasetAsResourceInReadMode.getURL().toString());
    assertFalse(actualActiveDatasetAsResourceInReadMode.isOpen());
    assertTrue(file.isAbsolute());
    assertTrue(actualActiveDatasetAsResourceInReadMode.isFile());
  }

  /**
   * Test {@link ResourceGovernor#getActiveDatasetAsResourceInWriteMode(String)}.
   * <p>
   * Method under test: {@link ResourceGovernor#getActiveDatasetAsResourceInWriteMode(String)}
   */
  @Test
  @DisplayName("Test getActiveDatasetAsResourceInWriteMode(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"WritableResource ResourceGovernor.getActiveDatasetAsResourceInWriteMode(String)"})
  void testGetActiveDatasetAsResourceInWriteMode() throws IOException {
    // Arrange and Act
    WritableResource actualActiveDatasetAsResourceInWriteMode = resourceGovernor
        .getActiveDatasetAsResourceInWriteMode("janedoe");

    // Assert
    assertTrue(actualActiveDatasetAsResourceInWriteMode instanceof FileUrlResource);
    assertEquals("URL [file:/${storage.basedir}/janedoe.marketdata/active_dataset.json]",
        actualActiveDatasetAsResourceInWriteMode.getDescription());
    File file = actualActiveDatasetAsResourceInWriteMode.getFile();
    assertEquals("active_dataset.json", file.getName());
    assertEquals("active_dataset.json", actualActiveDatasetAsResourceInWriteMode.getFilename());
    assertEquals("file:/${storage.basedir}/janedoe.marketdata/active_dataset.json",
        actualActiveDatasetAsResourceInWriteMode.getURL().toString());
    assertFalse(actualActiveDatasetAsResourceInWriteMode.isOpen());
    assertTrue(file.isAbsolute());
    assertTrue(actualActiveDatasetAsResourceInWriteMode.isFile());
  }

  /**
   * Test {@link ResourceGovernor#getImportCandidateAsResourceInReadMode()}.
   * <p>
   * Method under test: {@link ResourceGovernor#getImportCandidateAsResourceInReadMode()}
   */
  @Test
  @DisplayName("Test getImportCandidateAsResourceInReadMode()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Resource ResourceGovernor.getImportCandidateAsResourceInReadMode()"})
  void testGetImportCandidateAsResourceInReadMode() throws IOException {
    // Arrange and Act
    Resource actualImportCandidateAsResourceInReadMode = resourceGovernor.getImportCandidateAsResourceInReadMode();

    // Assert
    assertTrue(actualImportCandidateAsResourceInReadMode instanceof FileUrlResource);
    assertEquals("URL [file:/${storage.importdir}/import_candidate.json]",
        actualImportCandidateAsResourceInReadMode.getDescription());
    assertEquals("file:/${storage.importdir}/import_candidate.json",
        actualImportCandidateAsResourceInReadMode.getURL().toString());
    File file = actualImportCandidateAsResourceInReadMode.getFile();
    assertEquals("import_candidate.json", file.getName());
    assertEquals("import_candidate.json", actualImportCandidateAsResourceInReadMode.getFilename());
    assertFalse(actualImportCandidateAsResourceInReadMode.isOpen());
    assertTrue(file.isAbsolute());
    assertTrue(actualImportCandidateAsResourceInReadMode.isFile());
  }

  /**
   * Test {@link ResourceGovernor#getImportCandidateAsResourceInWriteMode()}.
   * <p>
   * Method under test: {@link ResourceGovernor#getImportCandidateAsResourceInWriteMode()}
   */
  @Test
  @DisplayName("Test getImportCandidateAsResourceInWriteMode()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"WritableResource ResourceGovernor.getImportCandidateAsResourceInWriteMode()"})
  void testGetImportCandidateAsResourceInWriteMode() throws IOException {
    // Arrange and Act
    WritableResource actualImportCandidateAsResourceInWriteMode = resourceGovernor
        .getImportCandidateAsResourceInWriteMode();

    // Assert
    assertTrue(actualImportCandidateAsResourceInWriteMode instanceof FileUrlResource);
    assertEquals("URL [file:/${storage.importdir}/import_candidate.json]",
        actualImportCandidateAsResourceInWriteMode.getDescription());
    assertEquals("file:/${storage.importdir}/import_candidate.json",
        actualImportCandidateAsResourceInWriteMode.getURL().toString());
    File file = actualImportCandidateAsResourceInWriteMode.getFile();
    assertEquals("import_candidate.json", file.getName());
    assertEquals("import_candidate.json", actualImportCandidateAsResourceInWriteMode.getFilename());
    assertFalse(actualImportCandidateAsResourceInWriteMode.isOpen());
    assertTrue(file.isAbsolute());
    assertTrue(actualImportCandidateAsResourceInWriteMode.isFile());
  }

  /**
   * Test {@link ResourceGovernor#getRefinitivPropertiesAsResourceInReadMode()}.
   * <p>
   * Method under test: {@link ResourceGovernor#getRefinitivPropertiesAsResourceInReadMode()}
   */
  @Test
  @DisplayName("Test getRefinitivPropertiesAsResourceInReadMode()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Resource ResourceGovernor.getRefinitivPropertiesAsResourceInReadMode()"})
  void testGetRefinitivPropertiesAsResourceInReadMode() throws IOException {
    // Arrange and Act
    Resource actualRefinitivPropertiesAsResourceInReadMode = resourceGovernor
        .getRefinitivPropertiesAsResourceInReadMode();

    // Assert
    assertTrue(actualRefinitivPropertiesAsResourceInReadMode instanceof FileUrlResource);
    File file = actualRefinitivPropertiesAsResourceInReadMode.getFile();
    assertEquals("${storage.internals.marketDataProviderConnectionPropertiesFile}", file.getName());
    assertEquals("${storage.internals.marketDataProviderConnectionPropertiesFile}",
        actualRefinitivPropertiesAsResourceInReadMode.getFilename());
    assertEquals("URL [file:/${storage.internals.marketDataProviderConnectionPropertiesFile}]",
        actualRefinitivPropertiesAsResourceInReadMode.getDescription());
    assertEquals("file:/${storage.internals.marketDataProviderConnectionPropertiesFile}",
        actualRefinitivPropertiesAsResourceInReadMode.getURL().toString());
    assertFalse(actualRefinitivPropertiesAsResourceInReadMode.isOpen());
    assertTrue(file.isAbsolute());
    assertTrue(actualRefinitivPropertiesAsResourceInReadMode.isFile());
  }

  /**
   * Test {@link ResourceGovernor#getDatabasePropertiesAsResourceInReadMode()}.
   * <p>
   * Method under test: {@link ResourceGovernor#getDatabasePropertiesAsResourceInReadMode()}
   */
  @Test
  @DisplayName("Test getDatabasePropertiesAsResourceInReadMode()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Resource ResourceGovernor.getDatabasePropertiesAsResourceInReadMode()"})
  void testGetDatabasePropertiesAsResourceInReadMode() throws IOException {
    // Arrange and Act
    Resource actualDatabasePropertiesAsResourceInReadMode = resourceGovernor
        .getDatabasePropertiesAsResourceInReadMode();

    // Assert
    assertTrue(actualDatabasePropertiesAsResourceInReadMode instanceof FileUrlResource);
    File file = actualDatabasePropertiesAsResourceInReadMode.getFile();
    assertEquals("${storage.internals.databaseConnectionPropertiesFile}", file.getName());
    assertEquals("${storage.internals.databaseConnectionPropertiesFile}",
        actualDatabasePropertiesAsResourceInReadMode.getFilename());
    assertEquals("URL [file:/${storage.internals.databaseConnectionPropertiesFile}]",
        actualDatabasePropertiesAsResourceInReadMode.getDescription());
    assertEquals("file:/${storage.internals.databaseConnectionPropertiesFile}",
        actualDatabasePropertiesAsResourceInReadMode.getURL().toString());
    assertFalse(actualDatabasePropertiesAsResourceInReadMode.isOpen());
    assertTrue(file.isAbsolute());
    assertTrue(actualDatabasePropertiesAsResourceInReadMode.isFile());
  }

  /**
   * Test {@link ResourceGovernor#getReadableResource(String, RoleFolders, String)}.
   * <p>
   * Method under test: {@link ResourceGovernor#getReadableResource(String, RoleFolders, String)}
   */
  @Test
  @DisplayName("Test getReadableResource(String, RoleFolders, String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Resource ResourceGovernor.getReadableResource(String, RoleFolders, String)"})
  void testGetReadableResource() throws IOException {
    // Arrange and Act
    Resource actualReadableResource = resourceGovernor.getReadableResource("janedoe", RoleFolders.MARKET_DATA_FOLDER,
        "foo.txt");

    // Assert
    assertTrue(actualReadableResource instanceof FileUrlResource);
    assertEquals("URL [file:/${storage.basedir}/janedoe.marketdata/foo.txt]", actualReadableResource.getDescription());
    assertEquals("file:/${storage.basedir}/janedoe.marketdata/foo.txt", actualReadableResource.getURL().toString());
    File file = actualReadableResource.getFile();
    assertEquals("foo.txt", file.getName());
    assertEquals("foo.txt", actualReadableResource.getFilename());
    assertFalse(actualReadableResource.isOpen());
    assertTrue(file.isAbsolute());
    assertTrue(actualReadableResource.isFile());
  }

  /**
   * Test {@link ResourceGovernor#getWritableResource(String, RoleFolders, String)}.
   * <p>
   * Method under test: {@link ResourceGovernor#getWritableResource(String, RoleFolders, String)}
   */
  @Test
  @DisplayName("Test getWritableResource(String, RoleFolders, String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"WritableResource ResourceGovernor.getWritableResource(String, RoleFolders, String)"})
  void testGetWritableResource() throws IOException {
    // Arrange and Act
    WritableResource actualWritableResource = resourceGovernor.getWritableResource("janedoe",
        RoleFolders.MARKET_DATA_FOLDER, "foo.txt");

    // Assert
    assertTrue(actualWritableResource instanceof FileUrlResource);
    assertEquals("URL [file:/${storage.basedir}/janedoe.marketdata/foo.txt]", actualWritableResource.getDescription());
    assertEquals("file:/${storage.basedir}/janedoe.marketdata/foo.txt", actualWritableResource.getURL().toString());
    File file = actualWritableResource.getFile();
    assertEquals("foo.txt", file.getName());
    assertEquals("foo.txt", actualWritableResource.getFilename());
    assertFalse(actualWritableResource.isOpen());
    assertTrue(file.isAbsolute());
    assertTrue(actualWritableResource.isFile());
  }

  /**
   * Test {@link ResourceGovernor#listContentsOfUserFolder(String, RoleFolders)}.
   * <p>
   * Method under test: {@link ResourceGovernor#listContentsOfUserFolder(String, RoleFolders)}
   */
  @Test
  @DisplayName("Test listContentsOfUserFolder(String, RoleFolders)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Resource[] ResourceGovernor.listContentsOfUserFolder(String, RoleFolders)"})
  void testListContentsOfUserFolder() throws IOException {
    // Arrange, Act and Assert
    assertEquals(0, resourceGovernor.listContentsOfUserFolder("janedoe", RoleFolders.MARKET_DATA_FOLDER).length);
  }

  /**
   * Test RoleFolders {@link RoleFolders#toString()}.
   * <p>
   * Method under test: {@link RoleFolders#toString()}
   */
  @Test
  @DisplayName("Test RoleFolders toString()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String RoleFolders.toString()"})
  void testRoleFoldersToString() {
    // Arrange, Act and Assert
    assertEquals("/%s.marketdata/", RoleFolders.valueOf("MARKET_DATA_FOLDER").toString());
  }
}
