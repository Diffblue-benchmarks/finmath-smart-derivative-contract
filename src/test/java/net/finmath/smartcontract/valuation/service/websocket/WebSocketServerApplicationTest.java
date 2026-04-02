package net.finmath.smartcontract.valuation.service.websocket;

import net.finmath.smartcontract.valuation.service.config.BasicAuthWebSecurityConfiguration;
import net.finmath.smartcontract.valuation.service.config.MockUserAuthConfig;
import net.finmath.smartcontract.valuation.service.utils.ApplicationProperties;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(
		classes = {WebSocketServerApplication.class, ApplicationProperties.class},
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		useMainMethod = SpringBootTest.UseMainMethod.ALWAYS
)
@ContextConfiguration(classes = {BasicAuthWebSecurityConfiguration.class, WebSocketServerApplication.class, ApplicationProperties.class, MockUserAuthConfig.class})
@AutoConfigureMockMvc
@ActiveProfiles("test")
class WebSocketServerApplicationTest {

	@Test
	void mainStartsApplication() {
		// Verifies that the application context loads successfully and the main method runs,
		// which sets the default server port and starts the application.
	}

}
