package net.finmath.smartcontract.demo;

import net.finmath.marketdata.products.Swap;
import net.finmath.plots.*;
import net.finmath.smartcontract.product.IRSwapGenerator;
import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationDataItem;
import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationDataset;
import net.finmath.smartcontract.valuation.marketdata.curvecalibration.CalibrationParserDataItems;
import net.finmath.smartcontract.valuation.oracle.SmartDerivativeContractSettlementOracle;
import net.finmath.smartcontract.valuation.oracle.interestrates.ValuationOraclePlainSwap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;

import javax.swing.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class VisualiserSDCTest {

	private VisualiserSDC visualiser;
	private Plot2DBarFX mockBarPlot;
	private Plot2DFX mockLinePlot;

	@BeforeEach
	void setUp() throws Exception {
		visualiser = new VisualiserSDC();

		mockBarPlot = mock(Plot2DBarFX.class);
		mockLinePlot = mock(Plot2DFX.class);
		when(mockLinePlot.setTitle(anyString())).thenReturn(mockLinePlot);

		setField(visualiser, "plotMarginAccounts", mockBarPlot);
		setField(visualiser, "plotMarketValue", mockLinePlot);
		setField(visualiser, "seriesMarketValues", new ArrayList<Point2D>());
	}

	private static void setField(Object target, String fieldName, Object value) throws Exception {
		Field field = target.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		field.set(target, value);
	}

	@SuppressWarnings("unchecked")
	private static <T> T getField(Object target, String fieldName) throws Exception {
		Field field = target.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		return (T) field.get(target);
	}

	@Test
	void testUpdateWithValueNullValue() throws Exception {
		LocalDateTime date = LocalDateTime.of(2008, 5, 1, 0, 0);

		visualiser.updateWithValue(date, 120000, 0, null, 0);

		verify(mockBarPlot).update(anyList());
		verify(mockLinePlot, never()).update(anyList());
		verify(mockLinePlot, never()).setTitle(anyString());
	}

	@Test
	void testUpdateWithValueNonNullValue() throws Exception {
		LocalDateTime date = LocalDateTime.of(2008, 5, 1, 0, 0);

		visualiser.updateWithValue(date, 120000, 1, 5000.0, 200.0);

		verify(mockBarPlot).update(anyList());
		verify(mockLinePlot).update(anyList());
		verify(mockLinePlot).setTitle("Market Value (01.05.2008-01.05.2008)");

		List<Point2D> seriesMarketValues = getField(visualiser, "seriesMarketValues");
		assertEquals(1, seriesMarketValues.size());
		assertEquals(1.0, seriesMarketValues.get(0).getX(), 1E-10);
		assertEquals(5000.0, seriesMarketValues.get(0).getY(), 1E-10);
	}

	@SuppressWarnings("unchecked")
	@Test
	void testUpdateWithValueBarPlotCategories() throws Exception {
		LocalDateTime date = LocalDateTime.of(2008, 6, 15, 0, 0);
		ArgumentCaptor<List<PlotableCategories>> captor = ArgumentCaptor.forClass(List.class);

		visualiser.updateWithValue(date, 100000, 0, null, 500.0);

		verify(mockBarPlot).update(captor.capture());
		List<PlotableCategories> plotables = captor.getValue();
		assertEquals(3, plotables.size());
		assertEquals("Margin", plotables.get(0).getName());
		assertEquals("Pay", plotables.get(1).getName());
		assertEquals("Receive", plotables.get(2).getName());

		// Margin base: We = base + min(0, +increment) = 100000 + 0 = 100000
		List<Category2D> marginBase = plotables.get(0).getSeries();
		assertEquals(2, marginBase.size());

		// Pay (marginRemoved): We = -min(0, +500) = 0
		List<Category2D> marginRemoved = plotables.get(1).getSeries();
		assertEquals(2, marginRemoved.size());

		// Receive (marginExcessed): We = max(0, +500) = 500
		List<Category2D> marginExcessed = plotables.get(2).getSeries();
		assertEquals(2, marginExcessed.size());
	}

	@Test
	void testUpdateWithValueNegativeIncrement() throws Exception {
		LocalDateTime date = LocalDateTime.of(2008, 7, 1, 0, 0);

		visualiser.updateWithValue(date, 100000, 2, -3000.0, -1000.0);

		verify(mockBarPlot).update(anyList());
		verify(mockLinePlot).update(anyList());
		verify(mockLinePlot).setTitle("Market Value (01.05.2008-01.07.2008)");

		List<Point2D> seriesMarketValues = getField(visualiser, "seriesMarketValues");
		assertEquals(1, seriesMarketValues.size());
		assertEquals(2.0, seriesMarketValues.get(0).getX(), 1E-10);
		assertEquals(-3000.0, seriesMarketValues.get(0).getY(), 1E-10);
	}

	@Test
	void testUpdateWithValueAccumulatesMarketValues() throws Exception {
		LocalDateTime date1 = LocalDateTime.of(2008, 5, 1, 0, 0);
		LocalDateTime date2 = LocalDateTime.of(2008, 5, 2, 0, 0);

		visualiser.updateWithValue(date1, 120000, 0, 1000.0, 100.0);
		visualiser.updateWithValue(date2, 120000, 1, 2000.0, 200.0);

		List<Point2D> seriesMarketValues = getField(visualiser, "seriesMarketValues");
		assertEquals(2, seriesMarketValues.size());
		assertEquals(0.0, seriesMarketValues.get(0).getX(), 1E-10);
		assertEquals(1000.0, seriesMarketValues.get(0).getY(), 1E-10);
		assertEquals(1.0, seriesMarketValues.get(1).getX(), 1E-10);
		assertEquals(2000.0, seriesMarketValues.get(1).getY(), 1E-10);
	}

	@Test
	void testStartInitializesFieldsAndConfiguresPlots() throws Exception {
		VisualiserSDC sdc = new VisualiserSDC();

		try (MockedConstruction<Plot2DBarFX> barConstruction = mockConstruction(Plot2DBarFX.class);
			 MockedConstruction<Plot2DFX> fxConstruction = mockConstruction(Plot2DFX.class,
					 (mock, context) -> {
						 when(mock.setTitle(anyString())).thenReturn(mock);
						 when(mock.setXAxisLabel(anyString())).thenReturn(mock);
						 when(mock.setYAxisLabel(anyString())).thenReturn(mock);
					 });
			 MockedStatic<SwingUtilities> swingMock = mockStatic(SwingUtilities.class)) {

			swingMock.when(() -> SwingUtilities.invokeLater(any(Runnable.class))).then(invocation -> null);

			sdc.start();

			// Verify Plot2DBarFX was constructed and configured
			assertEquals(1, barConstruction.constructed().size());
			Plot2DBarFX constructedBar = barConstruction.constructed().get(0);
			verify(constructedBar).setIsSeriesStacked(true);

			// Verify Plot2DFX was constructed and configured
			assertEquals(1, fxConstruction.constructed().size());
			Plot2DFX constructedFx = fxConstruction.constructed().get(0);
			verify(constructedFx).setIsLegendVisible(false);
			verify(constructedFx).setTitle("Market Value");
			verify(constructedFx).setXAxisLabel("Date");
			verify(constructedFx).setYAxisLabel("Market Value");

			// Verify seriesMarketValues was initialized
			List<Point2D> series = getField(sdc, "seriesMarketValues");
			assertNotNull(series);
			assertTrue(series.isEmpty());

			// Verify SwingUtilities.invokeLater was called
			swingMock.verify(() -> SwingUtilities.invokeLater(any(Runnable.class)));
		}
	}

	@Test
	void testMain() throws Exception {
		CalibrationDataItem.Spec spec5Y = new CalibrationDataItem.Spec("key1", "Euribor6M", "Swap-Rate", "5Y");
		CalibrationDataItem dataItem5Y = new CalibrationDataItem(spec5Y, 0.04, LocalDateTime.of(2008, 6, 1, 0, 0));

		CalibrationDataset scenario1 = mock(CalibrationDataset.class);
		when(scenario1.getDate()).thenReturn(LocalDateTime.of(2008, 6, 1, 0, 0));
		when(scenario1.getDataPoints()).thenReturn(Set.of(dataItem5Y));

		CalibrationDataset scenario2 = mock(CalibrationDataset.class);
		when(scenario2.getDate()).thenReturn(LocalDateTime.of(2008, 7, 1, 0, 0));
		when(scenario2.getDataPoints()).thenReturn(Set.of(dataItem5Y));

		List<CalibrationDataset> scenarios = List.of(scenario1, scenario2);

		Swap mockSwap = mock(Swap.class);

		try (MockedStatic<CalibrationParserDataItems> parserMock = mockStatic(CalibrationParserDataItems.class);
			 MockedStatic<IRSwapGenerator> swapGenMock = mockStatic(IRSwapGenerator.class);
			 MockedConstruction<ValuationOraclePlainSwap> oracleConstruction = mockConstruction(ValuationOraclePlainSwap.class);
			 MockedConstruction<SmartDerivativeContractSettlementOracle> marginConstruction = mockConstruction(
					 SmartDerivativeContractSettlementOracle.class,
					 (mock, context) -> when(mock.getMargin(any(), any())).thenReturn(Map.of("value", BigDecimal.valueOf(1000.0))));
			 MockedConstruction<VisualiserSDC> sdcConstruction = mockConstruction(VisualiserSDC.class)) {

			parserMock.when(() -> CalibrationParserDataItems.getScenariosFromJsonFile("timeseriesdatamap.json"))
					.thenReturn(scenarios);
			swapGenMock.when(() -> IRSwapGenerator.generateAnalyticSwapObject(
							any(), eq("5Y"), eq(1.0E7), eq(0.04), eq(false), eq("forward-EUR-6M"), eq("discount-EUR-OIS")))
					.thenReturn(mockSwap);

			VisualiserSDC.main(new String[]{});

			assertEquals(1, sdcConstruction.constructed().size());
			VisualiserSDC constructedSDC = sdcConstruction.constructed().get(0);
			verify(constructedSDC).start();
			// Initial call + 2 iterations * 2 calls each = 5 total
			verify(constructedSDC, times(5)).updateWithValue(any(), anyDouble(), anyDouble(), any(), anyDouble());
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	void testUpdateWithValueGraphStyles() throws Exception {
		LocalDateTime date = LocalDateTime.of(2008, 5, 1, 0, 0);
		ArgumentCaptor<List<PlotableCategories>> barCaptor = ArgumentCaptor.forClass(List.class);

		visualiser.updateWithValue(date, 100000, 0, 5000.0, 0);

		verify(mockBarPlot).update(barCaptor.capture());
		List<PlotableCategories> plotables = barCaptor.getValue();

		// Margin category should have a non-null style
		assertNotNull(plotables.get(0).getStyle());
		// Pay and Receive categories should have null style
		assertNull(plotables.get(1).getStyle());
		assertNull(plotables.get(2).getStyle());
	}
}
