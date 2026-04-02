package net.finmath.smartcontract.valuation.service;

import net.finmath.smartcontract.valuation.service.config.BasicAuthWebSecurityConfiguration;
import net.finmath.smartcontract.valuation.service.config.MockUserAuthConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {Application.class},
		webEnvironment = SpringBootTest.WebEnvironment.MOCK,
		useMainMethod = SpringBootTest.UseMainMethod.ALWAYS)
@ContextConfiguration(classes = {BasicAuthWebSecurityConfiguration.class, Application.class, MockUserAuthConfig.class})
@AutoConfigureMockMvc
@WebAppConfiguration
@ActiveProfiles("test")
class ApplicationTest {

	@Test
	void mainSetsTimezoneToUTC() {
		assertEquals("UTC", TimeZone.getDefault().getID());
	}
}
