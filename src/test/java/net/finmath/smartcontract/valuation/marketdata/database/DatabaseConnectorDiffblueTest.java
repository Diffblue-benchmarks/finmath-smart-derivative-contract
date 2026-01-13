package net.finmath.smartcontract.valuation.marketdata.database;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.UnsupportedEncodingException;
import net.finmath.smartcontract.valuation.service.utils.ResourceGovernor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@ExtendWith(MockitoExtension.class)
class DatabaseConnectorDiffblueTest {
  @InjectMocks private DatabaseConnector databaseConnector;

  @Mock private ResourceGovernor resourceGovernor;

  /**
   * Test {@link DatabaseConnector#init()}.
   *
   * <p>Method under test: {@link DatabaseConnector#init()}
   */
  @Test
  @DisplayName("Test init()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DatabaseConnector.init()"})
  void testInit() throws UnsupportedEncodingException {
    // Arrange
    ResourceGovernor resourceGovernor = mock(ResourceGovernor.class);
    when(resourceGovernor.getDatabasePropertiesAsResourceInReadMode())
        .thenReturn(new ByteArrayResource("AXAXAXAX".getBytes("UTF-8")));

    // Act
    new DatabaseConnector(resourceGovernor).init();

    // Assert
    verify(resourceGovernor).getDatabasePropertiesAsResourceInReadMode();
  }

  /**
   * Test {@link DatabaseConnector#init()}.
   *
   * <p>Method under test: {@link DatabaseConnector#init()}
   */
  @Test
  @DisplayName("Test init()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DatabaseConnector.init()"})
  void testInit2() {
    // Arrange
    when(resourceGovernor.getDatabasePropertiesAsResourceInReadMode())
        .thenReturn(new ClassPathResource("URL"));

    // Act
    databaseConnector.init();

    // Assert
    verify(resourceGovernor).getDatabasePropertiesAsResourceInReadMode();
  }

  /**
   * Test {@link DatabaseConnector#init()}.
   *
   * <ul>
   *   <li>Then calls {@link PathMatchingResourcePatternResolver#getResource(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DatabaseConnector#init()}
   */
  @Test
  @DisplayName("Test init(); then calls getResource(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DatabaseConnector.init()"})
  void testInit_thenCallsGetResource() throws UnsupportedEncodingException {
    // Arrange
    PathMatchingResourcePatternResolver resourcePatternResolver =
        mock(PathMatchingResourcePatternResolver.class);
    when(resourcePatternResolver.getResource(Mockito.<String>any()))
        .thenReturn(new ByteArrayResource("AXAXAXAX".getBytes("UTF-8")));
    ResourceGovernor resourceGovernor = new ResourceGovernor(resourcePatternResolver);

    // Act
    new DatabaseConnector(resourceGovernor).init();

    // Assert
    verify(resourcePatternResolver).getResource("file:///null");
  }
}
