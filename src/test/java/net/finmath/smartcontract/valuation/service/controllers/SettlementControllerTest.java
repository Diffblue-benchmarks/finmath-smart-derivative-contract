package net.finmath.smartcontract.valuation.service.controllers;

import com.google.gson.Gson;
import net.finmath.smartcontract.model.InitialSettlementRequest;
import net.finmath.smartcontract.model.InitialSettlementResult;
import net.finmath.smartcontract.model.RegularSettlementRequest;
import net.finmath.smartcontract.model.RegularSettlementResult;
import net.finmath.smartcontract.valuation.service.Application;
import net.finmath.smartcontract.valuation.service.config.BasicAuthWebSecurityConfiguration;
import net.finmath.smartcontract.valuation.service.config.MockUserAuthConfig;
import net.finmath.smartcontract.valuation.service.utils.SettlementService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {SettlementController.class, Application.class},
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
@ContextConfiguration(classes = {BasicAuthWebSecurityConfiguration.class, Application.class, MockUserAuthConfig.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SettlementControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SettlementService settlementService;

	@Test
	@WithUserDetails("user1")
	void generateRegularSettlementResult() throws Exception {
		String mockSettlement = "testSettlement";

		RegularSettlementRequest request = new RegularSettlementRequest()
				.settlementLast("settle")
				.tradeData("tradeData");
		Gson gson = new Gson();
		String jsonRequest = gson.toJson(request);
		RegularSettlementResult result = new RegularSettlementResult().generatedRegularSettlement(mockSettlement);

		when(this.settlementService.generateRegularSettlementResult(request)).thenReturn(result);

		this.mockMvc
				.perform(
						post("/settlement/generate-regular-settlement")
								.contentType(MediaType.APPLICATION_JSON)
								.accept(MediaType.APPLICATION_JSON)
								.content(jsonRequest)
				)
				.andExpect(status().isOk())
				.andExpect(content().string(containsString(mockSettlement)));
	}

	@Test
	@WithUserDetails("user1")
	void generateInitialSettlementResult() throws Exception {
		String mockSettlement = "testSettlement";

		InitialSettlementRequest request = new InitialSettlementRequest()
				.tradeData("tradeData");
		Gson gson = new Gson();
		String jsonRequest = gson.toJson(request);
		InitialSettlementResult result = new InitialSettlementResult().generatedInitialSettlement(mockSettlement);

		when(this.settlementService.generateInitialSettlementResult(request)).thenReturn(result);

		this.mockMvc
				.perform(
						post("/settlement/generate-initial-settlement")
								.contentType(MediaType.APPLICATION_JSON)
								.accept(MediaType.APPLICATION_JSON)
								.content(jsonRequest)
				)
				.andExpect(status().isOk())
				.andExpect(content().string(containsString(mockSettlement)));
	}

	@Test
	void testConstructor() {
		SettlementService mockService = mock(SettlementService.class);
		SettlementController controller = new SettlementController(mockService);
		assertNotNull(controller);
	}

	@Test
	void testGenerateRegularSettlementResultDirect() {
		SettlementService mockService = mock(SettlementService.class);
		SettlementController controller = new SettlementController(mockService);

		RegularSettlementRequest request = new RegularSettlementRequest()
				.settlementLast("settle")
				.tradeData("tradeData");
		RegularSettlementResult expectedResult = new RegularSettlementResult().generatedRegularSettlement("settlement");

		when(mockService.generateRegularSettlementResult(request)).thenReturn(expectedResult);

		var response = controller.generateRegularSettlementResult(request);

		assertNotNull(response);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals(expectedResult, response.getBody());
		verify(mockService).generateRegularSettlementResult(request);
	}

	@Test
	void testGenerateInitialSettlementResultDirect() {
		SettlementService mockService = mock(SettlementService.class);
		SettlementController controller = new SettlementController(mockService);

		InitialSettlementRequest request = new InitialSettlementRequest()
				.tradeData("tradeData");
		InitialSettlementResult expectedResult = new InitialSettlementResult().generatedInitialSettlement("settlement");

		when(mockService.generateInitialSettlementResult(request)).thenReturn(expectedResult);

		var response = controller.generateInitialSettlementResult(request);

		assertNotNull(response);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals(expectedResult, response.getBody());
		verify(mockService).generateInitialSettlementResult(request);
	}
}