package com.wileyedge.flooringmastery.dto;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

public enum ProductType {
	TILE(new BigDecimal("2.50")),
    CARPET(new BigDecimal("3.75")),
    LAMINATE(new BigDecimal("4.20")),
    WOOD(new BigDecimal("5.80"));

    private BigDecimal pricePerSquareFoot;

    ProductType(BigDecimal pricePerSquareFoot) {
        this.pricePerSquareFoot = pricePerSquareFoot;
    }

    public BigDecimal getPricePerSquareFoot() {
        return pricePerSquareFoot;
    }
}
