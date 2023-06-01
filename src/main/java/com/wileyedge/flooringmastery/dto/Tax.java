package com.wileyedge.flooringmastery.dto;

import java.math.BigDecimal;

public class Tax {
	private String stateAbbrev;
	private String stateName;
	private BigDecimal taxRate;

	public Tax(String stateAbbrev, String stateName, BigDecimal taxRate) {
		this.stateAbbrev = stateAbbrev;
		this.stateName = stateName;
		this.taxRate = taxRate;
	}

	public String getStateAbbrev() {
		return stateAbbrev;
	}

	public String getStateName() {
		return stateName;
	}

	public BigDecimal getTaxRate() {
		return taxRate;
	}

	@Override
	public String toString() {
		return "Tax [stateAbbrev=" + stateAbbrev + ", stateName=" + stateName + ", taxRate=" + taxRate + "]";
	}
	
	
}
