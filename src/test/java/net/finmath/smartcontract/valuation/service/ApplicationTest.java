package net.finmath.smartcontract.valuation.service;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;

import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mockStatic;

class ApplicationTest {

	@Test
	void testMain() {
		TimeZone originalTimeZone = TimeZone.getDefault();
		try (MockedStatic<SpringApplication> springAppMock = mockStatic(SpringApplication.class)) {
			springAppMock.when(() -> SpringApplication.run(eq(Application.class), any(String[].class)))
					.thenReturn(null);

			Application.main(new String[]{});

			assertEquals("UTC", TimeZone.getDefault().getID());
			springAppMock.verify(() -> SpringApplication.run(eq(Application.class), any(String[].class)));
		} finally {
			TimeZone.setDefault(originalTimeZone);
		}
	}
}
