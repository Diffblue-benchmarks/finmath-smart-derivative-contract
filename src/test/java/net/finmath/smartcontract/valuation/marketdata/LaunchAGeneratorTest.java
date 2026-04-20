package net.finmath.smartcontract.valuation.marketdata;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class LaunchAGeneratorTest {

	@Test
	void testMainThrowsOnMissingPropertiesFile() {
		assertThrows(FileNotFoundException.class, () -> LaunchAGenerator.main(new String[]{}));
	}
}
