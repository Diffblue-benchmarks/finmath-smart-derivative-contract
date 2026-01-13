package net.finmath.smartcontract.valuation.service.controllers;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import net.finmath.smartcontract.model.PlainSwapOperationRequest;
import net.finmath.smartcontract.valuation.service.config.ValuationConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.info.BuildProperties;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponseException;

@ExtendWith(MockitoExtension.class)
class PlainSwapEditorControllerDiffblueTest {
  @Mock private BuildProperties buildProperties;

  @InjectMocks private PlainSwapEditorController plainSwapEditorController;

  @Mock private ResourceLoader resourceLoader;

  @Mock private ValuationConfig valuationConfig;

  /**
   * Test {@link PlainSwapEditorController#generatePlainSwapSdcml(PlainSwapOperationRequest)}.
   *
   * <p>Method under test: {@link
   * PlainSwapEditorController#generatePlainSwapSdcml(PlainSwapOperationRequest)}
   */
  @Test
  @DisplayName("Test generatePlainSwapSdcml(PlainSwapOperationRequest)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.http.ResponseEntity PlainSwapEditorController.generatePlainSwapSdcml(PlainSwapOperationRequest)"
  })
  void testGeneratePlainSwapSdcml() {
    // Arrange
    when(valuationConfig.getMarketDataProviderToTemplate())
        .thenThrow(new ErrorResponseException(HttpStatus.OK));

    // Act and Assert
    assertThrows(
        ErrorResponseException.class,
        () -> plainSwapEditorController.generatePlainSwapSdcml(new PlainSwapOperationRequest()));
    verify(valuationConfig).getMarketDataProviderToTemplate();
  }

  /**
   * Test {@link PlainSwapEditorController#generatePlainSwapSdcml(PlainSwapOperationRequest)}.
   *
   * <p>Method under test: {@link
   * PlainSwapEditorController#generatePlainSwapSdcml(PlainSwapOperationRequest)}
   */
  @Test
  @DisplayName("Test generatePlainSwapSdcml(PlainSwapOperationRequest)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.http.ResponseEntity PlainSwapEditorController.generatePlainSwapSdcml(PlainSwapOperationRequest)"
  })
  void testGeneratePlainSwapSdcml2() {
    // Arrange
    when(valuationConfig.getMarketDataProviderToTemplate()).thenReturn(new HashMap<>());
    when(resourceLoader.getResource(Mockito.<String>any()))
        .thenThrow(new ErrorResponseException(HttpStatus.OK));

    // Act and Assert
    assertThrows(
        ErrorResponseException.class,
        () -> plainSwapEditorController.generatePlainSwapSdcml(new PlainSwapOperationRequest()));
    verify(valuationConfig).getMarketDataProviderToTemplate();
    verify(resourceLoader).getResource(null);
  }

  /**
   * Test {@link PlainSwapEditorController#generatePlainSwapSdcml(PlainSwapOperationRequest)}.
   *
   * <ul>
   *   <li>Then calls {@link BuildProperties#getVersion()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * PlainSwapEditorController#generatePlainSwapSdcml(PlainSwapOperationRequest)}
   */
  @Test
  @DisplayName("Test generatePlainSwapSdcml(PlainSwapOperationRequest); then calls getVersion()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.http.ResponseEntity PlainSwapEditorController.generatePlainSwapSdcml(PlainSwapOperationRequest)"
  })
  void testGeneratePlainSwapSdcml_thenCallsGetVersion() throws UnsupportedEncodingException {
    // Arrange
    when(valuationConfig.getMarketDataProviderToTemplate()).thenReturn(new HashMap<>());
    when(buildProperties.getVersion()).thenThrow(new ErrorResponseException(HttpStatus.OK));
    when(resourceLoader.getResource(Mockito.<String>any()))
        .thenReturn(new ByteArrayResource("AXAXAXAX".getBytes("UTF-8")));

    // Act and Assert
    assertThrows(
        ErrorResponseException.class,
        () -> plainSwapEditorController.generatePlainSwapSdcml(new PlainSwapOperationRequest()));
    verify(valuationConfig).getMarketDataProviderToTemplate();
    verify(buildProperties).getVersion();
    verify(resourceLoader).getResource(null);
  }

  /**
   * Test {@link PlainSwapEditorController#generatePlainSwapSdcml(PlainSwapOperationRequest)}.
   *
   * <ul>
   *   <li>Then throw {@link RuntimeException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * PlainSwapEditorController#generatePlainSwapSdcml(PlainSwapOperationRequest)}
   */
  @Test
  @DisplayName(
      "Test generatePlainSwapSdcml(PlainSwapOperationRequest); then throw RuntimeException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.http.ResponseEntity PlainSwapEditorController.generatePlainSwapSdcml(PlainSwapOperationRequest)"
  })
  void testGeneratePlainSwapSdcml_thenThrowRuntimeException() {
    // Arrange
    when(valuationConfig.getMarketDataProviderToTemplate()).thenReturn(new HashMap<>());
    when(resourceLoader.getResource(Mockito.<String>any()))
        .thenReturn(new ClassPathResource("Path"));

    // Act and Assert
    assertThrows(
        RuntimeException.class,
        () -> plainSwapEditorController.generatePlainSwapSdcml(new PlainSwapOperationRequest()));
    verify(valuationConfig).getMarketDataProviderToTemplate();
    verify(resourceLoader).getResource(null);
  }

  /**
   * Test {@link PlainSwapEditorController#evaluateFromPlainSwapEditor(PlainSwapOperationRequest)}.
   *
   * <p>Method under test: {@link
   * PlainSwapEditorController#evaluateFromPlainSwapEditor(PlainSwapOperationRequest)}
   */
  @Test
  @DisplayName("Test evaluateFromPlainSwapEditor(PlainSwapOperationRequest)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.http.ResponseEntity PlainSwapEditorController.evaluateFromPlainSwapEditor(PlainSwapOperationRequest)"
  })
  void testEvaluateFromPlainSwapEditor() {
    // Arrange
    when(valuationConfig.getMarketDataProviderToTemplate())
        .thenThrow(new ErrorResponseException(HttpStatus.OK));

    // Act and Assert
    assertThrows(
        ErrorResponseException.class,
        () ->
            plainSwapEditorController.evaluateFromPlainSwapEditor(new PlainSwapOperationRequest()));
    verify(valuationConfig).getMarketDataProviderToTemplate();
  }

  /**
   * Test {@link PlainSwapEditorController#evaluateFromPlainSwapEditor(PlainSwapOperationRequest)}.
   *
   * <p>Method under test: {@link
   * PlainSwapEditorController#evaluateFromPlainSwapEditor(PlainSwapOperationRequest)}
   */
  @Test
  @DisplayName("Test evaluateFromPlainSwapEditor(PlainSwapOperationRequest)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.http.ResponseEntity PlainSwapEditorController.evaluateFromPlainSwapEditor(PlainSwapOperationRequest)"
  })
  void testEvaluateFromPlainSwapEditor2() {
    // Arrange
    when(valuationConfig.getMarketDataProviderToTemplate()).thenReturn(new HashMap<>());
    when(resourceLoader.getResource(Mockito.<String>any()))
        .thenThrow(new ErrorResponseException(HttpStatus.OK));

    // Act and Assert
    assertThrows(
        ErrorResponseException.class,
        () ->
            plainSwapEditorController.evaluateFromPlainSwapEditor(new PlainSwapOperationRequest()));
    verify(valuationConfig).getMarketDataProviderToTemplate();
    verify(resourceLoader).getResource(null);
  }

  /**
   * Test {@link PlainSwapEditorController#evaluateFromPlainSwapEditor(PlainSwapOperationRequest)}.
   *
   * <ul>
   *   <li>Then calls {@link BuildProperties#getVersion()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * PlainSwapEditorController#evaluateFromPlainSwapEditor(PlainSwapOperationRequest)}
   */
  @Test
  @DisplayName(
      "Test evaluateFromPlainSwapEditor(PlainSwapOperationRequest); then calls getVersion()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.http.ResponseEntity PlainSwapEditorController.evaluateFromPlainSwapEditor(PlainSwapOperationRequest)"
  })
  void testEvaluateFromPlainSwapEditor_thenCallsGetVersion() throws UnsupportedEncodingException {
    // Arrange
    when(valuationConfig.getMarketDataProviderToTemplate()).thenReturn(new HashMap<>());
    when(buildProperties.getVersion()).thenThrow(new ErrorResponseException(HttpStatus.OK));
    when(resourceLoader.getResource(Mockito.<String>any()))
        .thenReturn(new ByteArrayResource("AXAXAXAX".getBytes("UTF-8")));

    // Act and Assert
    assertThrows(
        ErrorResponseException.class,
        () ->
            plainSwapEditorController.evaluateFromPlainSwapEditor(new PlainSwapOperationRequest()));
    verify(valuationConfig).getMarketDataProviderToTemplate();
    verify(buildProperties).getVersion();
    verify(resourceLoader).getResource(null);
  }

  /**
   * Test {@link PlainSwapEditorController#evaluateFromPlainSwapEditor(PlainSwapOperationRequest)}.
   *
   * <ul>
   *   <li>Then throw {@link RuntimeException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * PlainSwapEditorController#evaluateFromPlainSwapEditor(PlainSwapOperationRequest)}
   */
  @Test
  @DisplayName(
      "Test evaluateFromPlainSwapEditor(PlainSwapOperationRequest); then throw RuntimeException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.http.ResponseEntity PlainSwapEditorController.evaluateFromPlainSwapEditor(PlainSwapOperationRequest)"
  })
  void testEvaluateFromPlainSwapEditor_thenThrowRuntimeException() {
    // Arrange
    when(valuationConfig.getMarketDataProviderToTemplate()).thenReturn(new HashMap<>());
    when(resourceLoader.getResource(Mockito.<String>any()))
        .thenReturn(new ClassPathResource("Path"));

    // Act and Assert
    assertThrows(
        RuntimeException.class,
        () ->
            plainSwapEditorController.evaluateFromPlainSwapEditor(new PlainSwapOperationRequest()));
    verify(valuationConfig).getMarketDataProviderToTemplate();
    verify(resourceLoader).getResource(null);
  }
}
