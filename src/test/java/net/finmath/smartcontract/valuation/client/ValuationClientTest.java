package net.finmath.smartcontract.valuation.client;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

class ValuationClientTest {

	@Test
	void testPrintInfoGitValidInputs() throws Exception {
		// Given
		String url = "http://localhost:8080";
		String authString = "user1:password1";

		// When - Use reflection to call the private method
		Method method = ValuationClient.class.getDeclaredMethod("printInfoGit", String.class, String.class);
		method.setAccessible(true);

		// Then - Verify the method can be invoked with valid inputs without URISyntaxException
		try {
			method.invoke(null, url, authString);
		} catch (InvocationTargetException e) {
			// The method will fail with connection refused or similar since no server is running
			// But we verify it's not a URISyntaxException (which would indicate malformed URL handling)
			assertFalse(e.getCause() instanceof URISyntaxException,
					"Method should not throw URISyntaxException for valid URL");
		}
	}

	@Test
	void testPrintInfoGitInvalidUrl() throws Exception {
		// Given
		String invalidUrl = "ht tp://invalid url with spaces";
		String authString = "user1:password1";

		// When - Use reflection to call the private method
		Method method = ValuationClient.class.getDeclaredMethod("printInfoGit", String.class, String.class);
		method.setAccessible(true);

		// Then - Verify the method throws URISyntaxException for invalid URL
		InvocationTargetException exception = assertThrows(InvocationTargetException.class, () -> {
			method.invoke(null, invalidUrl, authString);
		});

		assertTrue(exception.getCause() instanceof URISyntaxException,
				"Method should throw URISyntaxException for invalid URL");
	}
}
