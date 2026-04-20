package net.finmath.smartcontract.valuation.service.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.finmath.smartcontract.valuation.marketdata.database.DatabaseConnector;
import net.finmath.smartcontract.valuation.service.config.ValuationConfig;
import net.finmath.smartcontract.valuation.service.utils.ResourceGovernor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.info.BuildProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PlainSwapEditorControllerIdentifyGeneratorTest {

	@Test
	void testIdentifyCurrentGeneratorXMLReadsResourceSuccessfully() throws Exception {
		ValuationConfig valuationConfig = mock(ValuationConfig.class);
		ValuationConfig valuationConfig1 = mock(ValuationConfig.class);
		ResourceLoader resourceLoader = mock(ResourceLoader.class);
		DatabaseConnector databaseConnector = mock(DatabaseConnector.class);
		ResourceGovernor resourceGovernor = mock(ResourceGovernor.class);
		ObjectMapper objectMapper = new ObjectMapper();
		BuildProperties buildProperties = mock(BuildProperties.class);

		when(valuationConfig.getFpmlSchemaPath()).thenReturn("dummy-schema-path");

		String expectedContent = "<xml>template content line1\nline2</xml>";
		Map<String, String> providerMap = Map.of("testProvider", "classpath:test-template.xml");
		when(valuationConfig1.getMarketDataProviderToTemplate()).thenReturn(providerMap);

		Resource resource = mock(Resource.class);
		when(resource.getInputStream()).thenReturn(
				new ByteArrayInputStream(expectedContent.getBytes(StandardCharsets.UTF_8)));
		when(resourceLoader.getResource("classpath:test-template.xml")).thenReturn(resource);

		PlainSwapEditorController controller = new PlainSwapEditorController(
				databaseConnector, resourceGovernor, objectMapper,
				valuationConfig, valuationConfig1, buildProperties, resourceLoader);

		Method method = PlainSwapEditorController.class.getDeclaredMethod(
				"identifyCurrentGeneratorXML", String.class);
		method.setAccessible(true);

		String result = (String) method.invoke(controller, "testProvider");

		// The method reads line-by-line and appends without newlines
		assertEquals("<xml>template content line1line2</xml>", result);
	}

	@Test
	void testIdentifyCurrentGeneratorXMLThrowsOnIOException() throws Exception {
		ValuationConfig valuationConfig = mock(ValuationConfig.class);
		ValuationConfig valuationConfig1 = mock(ValuationConfig.class);
		ResourceLoader resourceLoader = mock(ResourceLoader.class);
		DatabaseConnector databaseConnector = mock(DatabaseConnector.class);
		ResourceGovernor resourceGovernor = mock(ResourceGovernor.class);
		ObjectMapper objectMapper = new ObjectMapper();
		BuildProperties buildProperties = mock(BuildProperties.class);

		when(valuationConfig.getFpmlSchemaPath()).thenReturn("dummy-schema-path");

		Map<String, String> providerMap = Map.of("badProvider", "classpath:nonexistent.xml");
		when(valuationConfig1.getMarketDataProviderToTemplate()).thenReturn(providerMap);

		Resource resource = mock(Resource.class);
		when(resource.getInputStream()).thenThrow(new IOException("File not found"));
		when(resourceLoader.getResource("classpath:nonexistent.xml")).thenReturn(resource);

		PlainSwapEditorController controller = new PlainSwapEditorController(
				databaseConnector, resourceGovernor, objectMapper,
				valuationConfig, valuationConfig1, buildProperties, resourceLoader);

		Method method = PlainSwapEditorController.class.getDeclaredMethod(
				"identifyCurrentGeneratorXML", String.class);
		method.setAccessible(true);

		assertThrows(Exception.class, () -> method.invoke(controller, "badProvider"));
	}

	@Test
	void testIdentifyCurrentGeneratorXMLEmptyResource() throws Exception {
		ValuationConfig valuationConfig = mock(ValuationConfig.class);
		ValuationConfig valuationConfig1 = mock(ValuationConfig.class);
		ResourceLoader resourceLoader = mock(ResourceLoader.class);
		DatabaseConnector databaseConnector = mock(DatabaseConnector.class);
		ResourceGovernor resourceGovernor = mock(ResourceGovernor.class);
		ObjectMapper objectMapper = new ObjectMapper();
		BuildProperties buildProperties = mock(BuildProperties.class);

		when(valuationConfig.getFpmlSchemaPath()).thenReturn("dummy-schema-path");

		Map<String, String> providerMap = Map.of("emptyProvider", "classpath:empty.xml");
		when(valuationConfig1.getMarketDataProviderToTemplate()).thenReturn(providerMap);

		Resource resource = mock(Resource.class);
		when(resource.getInputStream()).thenReturn(new ByteArrayInputStream(new byte[0]));
		when(resourceLoader.getResource("classpath:empty.xml")).thenReturn(resource);

		PlainSwapEditorController controller = new PlainSwapEditorController(
				databaseConnector, resourceGovernor, objectMapper,
				valuationConfig, valuationConfig1, buildProperties, resourceLoader);

		Method method = PlainSwapEditorController.class.getDeclaredMethod(
				"identifyCurrentGeneratorXML", String.class);
		method.setAccessible(true);

		String result = (String) method.invoke(controller, "emptyProvider");

		assertEquals("", result);
	}
}
