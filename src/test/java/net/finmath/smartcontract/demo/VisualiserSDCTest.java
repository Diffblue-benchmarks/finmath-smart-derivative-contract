package net.finmath.smartcontract.demo;

import net.finmath.plots.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
