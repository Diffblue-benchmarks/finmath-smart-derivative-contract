package net.finmath.smartcontract.valuation.marketdata.database;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.UnsupportedEncodingException;
import net.finmath.smartcontract.valuation.service.utils.ResourceGovernor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;

@ExtendWith(MockitoExtension.class)
class DatabaseConnectorDiffblueTest {
  @InjectMocks
  private DatabaseConnector databaseConnector;

  @Mock
  private ResourceGovernor resourceGovernor;

  /**
   * Test {@link DatabaseConnector#init()}.
   * <p>
   * Method under test: {@link DatabaseConnector#init()}
   */
  @Test
  @DisplayName("Test init()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DatabaseConnector.init()"})
  void testInit() throws UnsupportedEncodingException {
    // Arrange
    when(resourceGovernor.getDatabasePropertiesAsResourceInReadMode())
        .thenReturn(new ByteArrayResource("AXAXAXAX".getBytes("UTF-8")));

    // Act
    databaseConnector.init();

    // Assert
    verify(resourceGovernor).getDatabasePropertiesAsResourceInReadMode();
  }

  /**
   * Test {@link DatabaseConnector#init()}.
   * <p>
   * Method under test: {@link DatabaseConnector#init()}
   */
  @Test
  @DisplayName("Test init()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DatabaseConnector.init()"})
  void testInit2() {
    // Arrange
    when(resourceGovernor.getDatabasePropertiesAsResourceInReadMode()).thenReturn(new ClassPathResource("URL"));

    // Act
    databaseConnector.init();

    // Assert
    verify(resourceGovernor).getDatabasePropertiesAsResourceInReadMode();
  }
}
