package net.finmath.smartcontract.valuation.service.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import net.finmath.smartcontract.valuation.service.utils.ResourceGovernor.RoleFolders;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;

@ExtendWith(MockitoExtension.class)
class ResourceGovernorDiffblueTest {
  @InjectMocks private ResourceGovernor resourceGovernor;

  @Mock private ResourcePatternResolver resourcePatternResolver;

  /**
   * Test {@link ResourceGovernor#getRefinitivPropertiesAsResourceInReadMode()}.
   *
   * <p>Method under test: {@link ResourceGovernor#getRefinitivPropertiesAsResourceInReadMode()}
   */
  @Test
  @DisplayName("Test getRefinitivPropertiesAsResourceInReadMode()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Resource ResourceGovernor.getRefinitivPropertiesAsResourceInReadMode()"})
  void testGetRefinitivPropertiesAsResourceInReadMode() throws UnsupportedEncodingException {
    // Arrange
    ByteArrayResource byteArrayResource = new ByteArrayResource("AXAXAXAX".getBytes("UTF-8"));
    when(resourcePatternResolver.getResource(Mockito.<String>any())).thenReturn(byteArrayResource);

    // Act
    Resource actualRefinitivPropertiesAsResourceInReadMode =
        resourceGovernor.getRefinitivPropertiesAsResourceInReadMode();

    // Assert
    verify(resourcePatternResolver).getResource("file:///null");
    assertSame(byteArrayResource, actualRefinitivPropertiesAsResourceInReadMode);
  }

  /**
   * Test {@link ResourceGovernor#getDatabasePropertiesAsResourceInReadMode()}.
   *
   * <p>Method under test: {@link ResourceGovernor#getDatabasePropertiesAsResourceInReadMode()}
   */
  @Test
  @DisplayName("Test getDatabasePropertiesAsResourceInReadMode()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Resource ResourceGovernor.getDatabasePropertiesAsResourceInReadMode()"})
  void testGetDatabasePropertiesAsResourceInReadMode() throws UnsupportedEncodingException {
    // Arrange
    ByteArrayResource byteArrayResource = new ByteArrayResource("AXAXAXAX".getBytes("UTF-8"));
    when(resourcePatternResolver.getResource(Mockito.<String>any())).thenReturn(byteArrayResource);

    // Act
    Resource actualDatabasePropertiesAsResourceInReadMode =
        resourceGovernor.getDatabasePropertiesAsResourceInReadMode();

    // Assert
    verify(resourcePatternResolver).getResource("file:///null");
    assertSame(byteArrayResource, actualDatabasePropertiesAsResourceInReadMode);
  }

  /**
   * Test {@link ResourceGovernor#listContentsOfUserFolder(String, RoleFolders)}.
   *
   * <ul>
   *   <li>Then return array length is one.
   * </ul>
   *
   * <p>Method under test: {@link ResourceGovernor#listContentsOfUserFolder(String, RoleFolders)}
   */
  @Test
  @DisplayName(
      "Test listContentsOfUserFolder(String, RoleFolders); then return array length is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Resource[] ResourceGovernor.listContentsOfUserFolder(String, RoleFolders)"})
  void testListContentsOfUserFolder_thenReturnArrayLengthIsOne() throws IOException {
    // Arrange
    ByteArrayResource byteArrayResource = new ByteArrayResource("AXAXAXAX".getBytes("UTF-8"));
    when(resourcePatternResolver.getResources(Mockito.<String>any()))
        .thenReturn(new Resource[] {byteArrayResource});

    // Act
    Resource[] actualListContentsOfUserFolderResult =
        resourceGovernor.listContentsOfUserFolder("\"JohnDoe\"", RoleFolders.MARKET_DATA_FOLDER);

    // Assert
    verify(resourcePatternResolver).getResources("file:///null/\"JohnDoe\".marketdata/*");
    assertEquals(1, actualListContentsOfUserFolderResult.length);
    assertSame(byteArrayResource, actualListContentsOfUserFolderResult[0]);
  }

  /**
   * Test {@link ResourceGovernor#listContentsOfUserFolder(String, RoleFolders)}.
   *
   * <ul>
   *   <li>Then throw {@link IOException}.
   * </ul>
   *
   * <p>Method under test: {@link ResourceGovernor#listContentsOfUserFolder(String, RoleFolders)}
   */
  @Test
  @DisplayName("Test listContentsOfUserFolder(String, RoleFolders); then throw IOException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Resource[] ResourceGovernor.listContentsOfUserFolder(String, RoleFolders)"})
  void testListContentsOfUserFolder_thenThrowIOException() throws IOException {
    // Arrange
    when(resourcePatternResolver.getResources(Mockito.<String>any())).thenThrow(new IOException());

    // Act and Assert
    assertThrows(
        IOException.class,
        () ->
            resourceGovernor.listContentsOfUserFolder(
                "\"JohnDoe\"", RoleFolders.MARKET_DATA_FOLDER));
    verify(resourcePatternResolver).getResources("file:///null/\"JohnDoe\".marketdata/*");
  }

  /**
   * Test RoleFolders {@link RoleFolders#toString()}.
   *
   * <p>Method under test: {@link RoleFolders#toString()}
   */
  @Test
  @DisplayName("Test RoleFolders toString()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String RoleFolders.toString()"})
  void testRoleFoldersToString() {
    // Arrange, Act and Assert
    assertEquals("/%s.marketdata/", RoleFolders.valueOf("MARKET_DATA_FOLDER").toString());
  }
}
