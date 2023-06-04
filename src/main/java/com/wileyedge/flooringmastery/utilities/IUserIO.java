package com.wileyedge.flooringmastery.utilities;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.wileyedge.flooringmastery.dto.ProductType;
import com.wileyedge.flooringmastery.dto.StateAbbrev;


public interface IUserIO {
	void print(String message);
	int getInputAsInteger(String varName, String prompt);
	int getInputAsInteger(String varName, String prompt, int min, int max);
	double getInputAsDouble(String varName, String prompt);
	long getInputAsLong(String varName, String prompt);
	String getInputAsString(String varName, String prompt) ;
	String getInputAsStringYN(String prompt);
	LocalDate getInputAsDate(String varName, String prompt);
	LocalDate getInputAsFutureDate(String varName, String prompt); 
	BigDecimal getInputAsBigDecimal(String varName, String prompt);
	BigDecimal getInputAsPositiveBigDecimal(String varName, String prompt);
	ProductType getInputAsProductType(String varName,String prompt);
	StateAbbrev getInputAsState(String varName,String prompt);
//	int getInputAsIntegerWithMinLimit(String varName, String prompt, int min);
	int getInputAsIntegerWithMinAndMaxLimit(String varName, String prompt, int min, int max);
	BigDecimal getInputAsBigDecimalWithMinLimit(String string, String string2, BigDecimal bigDecimal);
	String getInputAsName(String varName, String prompt);
	String getInputAsOptionalName(String string, String string2);
	ProductType getInputAsOptionalProductType(String varName, String prompt);
	StateAbbrev getInputAsOptionalState(String varName, String prompt);
	BigDecimal getInputAsOptionalBigDecimalWithMinLimit(String varName, String prompt, BigDecimal min);
	
    
}
