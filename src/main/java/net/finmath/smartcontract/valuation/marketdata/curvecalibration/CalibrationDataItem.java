package net.finmath.smartcontract.valuation.marketdata.curvecalibration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("java:S125")
public record CalibrationDataItem(Spec spec, Double quote, LocalDateTime dateTime) {
	private static final String REGEX = "((?<=[a-zA-Z])(?=[0-9]))|((?<=[0-9])(?=[a-zA-Z]))";

	public record Spec(String key, String curveName, String productName, String maturity) {
		public String getKey() { return key; }
		public String getCurveName() { return curveName; }
		public String getProductName() { return productName; }
		public String getMaturity() { return maturity; }
	}

    /*public CalibrationDataItem(String curve, String productName, String maturity, Double quote){
        spec = new Spec("",curve,productName,maturity);
        this.quote = quote;
        this.dateTime=null;
    }*/

	public Spec getSpec() { return spec; }
	public Double getQuote() { return quote; }
	public LocalDateTime getDateTime() { return dateTime; }

	public CalibrationDataItem getClonedScaled(double factor) {
		return new CalibrationDataItem(spec, quote / factor, dateTime);
	}

	public CalibrationDataItem getClonedShifted(double amount) {
		return new CalibrationDataItem(spec, quote + amount, dateTime);
	}

	public String getCurveName() {
		return getSpec().getCurveName();
	}

	public String getProductName() {
		return getSpec().getProductName();
	}

	public String getMaturity() {
		return getSpec().getMaturity();
	}

	public Integer getDaysToMaturity() {
		List<String> list = Arrays.asList(getSpec().getMaturity().split(REGEX));
		int nTimeUnits = Integer.parseInt(list.get(0));
		String timeUnitKey = list.get(1);
		if (timeUnitKey.equals("D"))
			return nTimeUnits;
		if (timeUnitKey.equals("M"))
			return nTimeUnits * 30;
		if (timeUnitKey.equals("Y"))
			return nTimeUnits * 360;
		else
			return 0;
	}

	public String getDateString() {
		return this.dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}

	public LocalDate getDate() {return dateTime.toLocalDate();}
}
