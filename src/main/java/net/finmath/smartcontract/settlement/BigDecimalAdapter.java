package net.finmath.smartcontract.settlement;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.math.BigDecimal;

public class BigDecimalAdapter extends XmlAdapter<String, BigDecimal> {

	public BigDecimalAdapter() {
		// Default constructor for XML binding
	}

	@Override
	public String marshal(BigDecimal value) throws Exception
	{
		if (value!= null)
		{
			return value.toString();
		}
		return null;
	}

	@Override
	public BigDecimal unmarshal(String s) throws Exception
	{
		if (s == null || s.trim().isEmpty()) {
			return null;
		}
		return new BigDecimal(s.trim());
	}
}