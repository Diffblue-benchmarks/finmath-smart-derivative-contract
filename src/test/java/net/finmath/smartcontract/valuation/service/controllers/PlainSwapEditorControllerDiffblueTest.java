package net.finmath.smartcontract.valuation.service.controllers;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import net.finmath.smartcontract.model.PlainSwapOperationRequest;
import net.finmath.smartcontract.valuation.marketdata.database.DatabaseConnector;
import net.finmath.smartcontract.valuation.service.config.ValuationConfig;
import net.finmath.smartcontract.valuation.service.utils.ResourceGovernor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

class PlainSwapEditorControllerDiffblueTest {
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
  void testGeneratePlainSwapSdcml_thenThrowRuntimeException() throws IOException {
    // Arrange
    ValuationConfig valuationConfig1 = new ValuationConfig();
    valuationConfig1.setMarketDataProviderToTemplate(new HashMap<>());
    Resource resource = mock(Resource.class);
    when(resource.getInputStream()).thenThrow(new IOException());
    ResourceLoader resourceLoader = mock(ResourceLoader.class);
    when(resourceLoader.getResource(Mockito.<String>any())).thenReturn(resource);
    DatabaseConnector databaseConnector =
        new DatabaseConnector(
            new ResourceGovernor(new AnnotationConfigReactiveWebApplicationContext()));
    ResourceGovernor resourceGovernor =
        new ResourceGovernor(new AnnotationConfigReactiveWebApplicationContext());
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    ValuationConfig valuationConfig = new ValuationConfig();
    PlainSwapEditorController plainSwapEditorController =
        new PlainSwapEditorController(
            databaseConnector,
            resourceGovernor,
            objectMapper,
            valuationConfig,
            valuationConfig1,
            new BuildProperties(new Properties()),
            resourceLoader);

    // Act and Assert
    assertThrows(
        RuntimeException.class,
        () -> plainSwapEditorController.generatePlainSwapSdcml(new PlainSwapOperationRequest()));
    verify(resource).getInputStream();
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
  void testEvaluateFromPlainSwapEditor_thenThrowRuntimeException() throws IOException {
    // Arrange
    ValuationConfig valuationConfig1 = new ValuationConfig();
    valuationConfig1.setMarketDataProviderToTemplate(new HashMap<>());
    Resource resource = mock(Resource.class);
    when(resource.getInputStream()).thenThrow(new IOException());
    ResourceLoader resourceLoader = mock(ResourceLoader.class);
    when(resourceLoader.getResource(Mockito.<String>any())).thenReturn(resource);
    DatabaseConnector databaseConnector =
        new DatabaseConnector(
            new ResourceGovernor(new AnnotationConfigReactiveWebApplicationContext()));
    ResourceGovernor resourceGovernor =
        new ResourceGovernor(new AnnotationConfigReactiveWebApplicationContext());
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    ValuationConfig valuationConfig = new ValuationConfig();
    PlainSwapEditorController plainSwapEditorController =
        new PlainSwapEditorController(
            databaseConnector,
            resourceGovernor,
            objectMapper,
            valuationConfig,
            valuationConfig1,
            new BuildProperties(new Properties()),
            resourceLoader);

    // Act and Assert
    assertThrows(
        RuntimeException.class,
        () ->
            plainSwapEditorController.evaluateFromPlainSwapEditor(new PlainSwapOperationRequest()));
    verify(resource).getInputStream();
    verify(resourceLoader).getResource(null);
  }
}
