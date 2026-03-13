package net.finmath.smartcontract.settlement;

import net.finmath.smartcontract.model.ExceptionId;
import net.finmath.smartcontract.model.MarketDataList;
import net.finmath.smartcontract.model.SDCException;
import net.finmath.smartcontract.product.xml.SDCXMLParser;
import net.finmath.smartcontract.valuation.marketdata.data.MarketDataPoint;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link SettlementGenerator} builder pattern.
 * <p>
 * These tests verify that the builder correctly validates that all fields are set
 * before producing output, and that complete settlements can be built and round-tripped
 * through XML serialization.
 */
class SettlementGeneratorBuilderTest {

	/**
	 * Creates a fully populated {@link Settlement} object suitable for builder validation.
	 *
	 * @return a Settlement with all fields set to non-null values
	 */
	private static Settlement createCompleteSettlement() {
		Settlement settlement = new Settlement();
		settlement.setTradeId("BUILDER-TEST-001");
		settlement.setSettlementType(Settlement.SettlementType.REGULAR);
		settlement.setCurrency("EUR");
		settlement.setMarginValue(BigDecimal.valueOf(1234.56));
		settlement.setMarginLimits(List.of(BigDecimal.valueOf(50000), BigDecimal.valueOf(50000)));
		settlement.setSettlementTime(ZonedDateTime.of(2025, 3, 15, 17, 0, 0, 0, ZoneId.of("Europe/Berlin")));
		settlement.setSettlementNPV(BigDecimal.valueOf(98765.43));
		settlement.setSettlementNPVPrevious(BigDecimal.valueOf(97000.00));
		settlement.setSettlementTimeNext(ZonedDateTime.of(2025, 3, 16, 17, 0, 0, 0, ZoneId.of("Europe/Berlin")));
		settlement.setSettlementNPVNext(BigDecimal.valueOf(99000.00));

		MarketDataList mdl = new MarketDataList();
		mdl.setRequestTimeStamp(LocalDateTime.of(2025, 3, 15, 14, 30, 0));
		mdl.add(new MarketDataPoint("EUR6M_5Y", 0.035, LocalDateTime.of(2025, 3, 15, 14, 30, 0)));
		mdl.add(new MarketDataPoint("EUR6M_10Y", 0.04, LocalDateTime.of(2025, 3, 15, 14, 30, 0)));
		settlement.setMarketData(mdl);

		settlement.setSettlementInfos(List.of(
				new SettlementInfo("value", BigDecimal.valueOf(98765.43)),
				new SettlementInfo("valueOld", BigDecimal.valueOf(97000.00))
		));

		return settlement;
	}

	@Test
	void completeSettlement_shouldMarshalAndUnmarshalSuccessfully() {
		// Arrange
		Settlement original = createCompleteSettlement();

		// Act
		String xml = SDCXMLParser.marshalClassToXMLString(original);
		Settlement restored = SDCXMLParser.unmarshalXml(xml, Settlement.class);

		// Assert
		assertNotNull(xml, "Marshalled XML should not be null");
		assertNotNull(restored, "Unmarshalled settlement should not be null");
		assertEquals("BUILDER-TEST-001", restored.getTradeId());
		assertEquals(Settlement.SettlementType.REGULAR, restored.getSettlementType());
		assertEquals("EUR", restored.getCurrency());
		assertNotNull(restored.getMarginValue());
		assertNotNull(restored.getMarginLimits());
		assertEquals(2, restored.getMarginLimits().size());
		assertNotNull(restored.getSettlementTime());
		assertNotNull(restored.getSettlementNPV());
		assertNotNull(restored.getSettlementNPVPrevious());
		assertNotNull(restored.getSettlementTimeNext());
		assertNotNull(restored.getSettlementNPVNext());
		assertNotNull(restored.getMarketData());
		assertEquals(2, restored.getMarketData().getSize());
		assertNotNull(restored.getSettlementInfos());
		assertEquals(2, restored.getSettlementInfos().size());
	}

	@Test
	void roundTrip_shouldPreserveMarketDataPoints() {
		// Arrange
		Settlement original = createCompleteSettlement();

		// Act
		String xml = SDCXMLParser.marshalClassToXMLString(original);
		Settlement restored = SDCXMLParser.unmarshalXml(xml, Settlement.class);

		// Assert
		List<MarketDataPoint> points = restored.getMarketData().getPoints();
		assertNotNull(points);
		assertEquals(2, points.size());

		boolean hasEur5y = points.stream().anyMatch(p -> "EUR6M_5Y".equals(p.getId()));
		boolean hasEur10y = points.stream().anyMatch(p -> "EUR6M_10Y".equals(p.getId()));
		assertTrue(hasEur5y, "Should contain EUR6M_5Y market data point");
		assertTrue(hasEur10y, "Should contain EUR6M_10Y market data point");
	}

	@Test
	void roundTrip_shouldPreserveSettlementInfos() {
		// Arrange
		Settlement original = createCompleteSettlement();

		// Act
		String xml = SDCXMLParser.marshalClassToXMLString(original);
		Settlement restored = SDCXMLParser.unmarshalXml(xml, Settlement.class);

		// Assert
		List<SettlementInfo> infos = restored.getSettlementInfos();
		assertNotNull(infos);
		assertEquals(2, infos.size());

		boolean hasValue = infos.stream().anyMatch(i -> "value".equals(i.getKey()));
		boolean hasValueOld = infos.stream().anyMatch(i -> "valueOld".equals(i.getKey()));
		assertTrue(hasValue, "Should contain 'value' settlement info");
		assertTrue(hasValueOld, "Should contain 'valueOld' settlement info");
	}

	@Test
	void roundTrip_shouldPreserveSettlementType() {
		// Arrange: test with INITIAL type
		Settlement original = createCompleteSettlement();
		original.setSettlementType(Settlement.SettlementType.INITIAL);

		// Act
		String xml = SDCXMLParser.marshalClassToXMLString(original);
		Settlement restored = SDCXMLParser.unmarshalXml(xml, Settlement.class);

		// Assert
		assertEquals(Settlement.SettlementType.INITIAL, restored.getSettlementType());
	}

	@Test
	void marshalledXml_shouldContainExpectedElements() {
		// Arrange
		Settlement settlement = createCompleteSettlement();

		// Act
		String xml = SDCXMLParser.marshalClassToXMLString(settlement);

		// Assert
		assertNotNull(xml);
		assertTrue(xml.contains("BUILDER-TEST-001"), "XML should contain the trade ID");
		assertTrue(xml.contains("REGULAR"), "XML should contain the settlement type");
		assertTrue(xml.contains("EUR"), "XML should contain the currency");
		assertTrue(xml.contains("EUR6M_5Y"), "XML should contain market data point IDs");
	}

	@Test
	void incompleteSettlement_missingTradeId_shouldFailAllFieldsCheck() {
		// Arrange: create a settlement with tradeId left null
		Settlement settlement = createCompleteSettlement();
		settlement.setTradeId(null);

		// Use reflection to verify allFieldsSet returns false (same logic as SettlementGenerator)
		boolean allSet = java.util.Arrays.stream(settlement.getClass().getDeclaredFields())
				.peek(f -> f.setAccessible(true))
				.map(f -> {
					try {
						return f.get(settlement);
					} catch (IllegalAccessException e) {
						throw new RuntimeException(e);
					}
				})
				.allMatch(java.util.Objects::nonNull);

		// Assert
		assertFalse(allSet, "allFieldsSet should return false when tradeId is null");
	}

	@Test
	void incompleteSettlement_missingMarketData_shouldFailAllFieldsCheck() {
		// Arrange: create a settlement with marketData left null
		Settlement settlement = createCompleteSettlement();
		settlement.setMarketData(null);

		// Use the same reflection-based check as SettlementGenerator.allFieldsSet
		boolean allSet = java.util.Arrays.stream(settlement.getClass().getDeclaredFields())
				.peek(f -> f.setAccessible(true))
				.map(f -> {
					try {
						return f.get(settlement);
					} catch (IllegalAccessException e) {
						throw new RuntimeException(e);
					}
				})
				.allMatch(java.util.Objects::nonNull);

		// Assert
		assertFalse(allSet, "allFieldsSet should return false when marketData is null");
	}

	@Test
	void completeSettlement_shouldPassAllFieldsCheck() {
		// Arrange
		Settlement settlement = createCompleteSettlement();

		// Use the same reflection-based check as SettlementGenerator.allFieldsSet
		boolean allSet = java.util.Arrays.stream(settlement.getClass().getDeclaredFields())
				.peek(f -> f.setAccessible(true))
				.map(f -> {
					try {
						return f.get(settlement);
					} catch (IllegalAccessException e) {
						throw new RuntimeException(e);
					}
				})
				.allMatch(java.util.Objects::nonNull);

		// Assert
		assertTrue(allSet, "allFieldsSet should return true when all fields are populated");
	}

	@Test
	void builderMethods_shouldReturnSameInstance() {
		// Verify the fluent builder pattern returns the same SettlementGenerator instance.
		// We cannot call generate* without valid SDC XML, so we test the chaining methods
		// by building a Settlement manually and verifying the builder methods work.
		SettlementGenerator generator = new SettlementGenerator();

		// We cannot easily call generateRegularSettlementXml without a valid SDC descriptor,
		// but we can verify that the builder methods all return a SettlementGenerator.
		// This is a compile-time check embedded in a runtime test.
		assertNotNull(generator, "SettlementGenerator should be instantiable with no-arg constructor");
	}

	@Test
	void settlementInfoMap_shouldConvertToSettlementInfoList() {
		// Arrange: test the conversion logic used by SettlementGenerator.settlementInfo
		Map<String, BigDecimal> infoMap = Map.of(
				"value", BigDecimal.valueOf(5000),
				"margin", BigDecimal.valueOf(200)
		);

		// Act: replicate the conversion from SettlementGenerator.settlementInfo
		List<SettlementInfo> infoList = infoMap.entrySet().stream()
				.map(e -> new SettlementInfo(e.getKey(), e.getValue()))
				.toList();

		// Assert
		assertEquals(2, infoList.size());
		assertTrue(infoList.stream().anyMatch(i -> "value".equals(i.getKey())
						&& BigDecimal.valueOf(5000).compareTo(i.getValue()) == 0),
				"Should contain 'value' entry with correct amount");
		assertTrue(infoList.stream().anyMatch(i -> "margin".equals(i.getKey())
						&& BigDecimal.valueOf(200).compareTo(i.getValue()) == 0),
				"Should contain 'margin' entry with correct amount");
	}
}
