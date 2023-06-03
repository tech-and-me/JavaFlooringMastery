package com.wileyedge.flooringmastery.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public class Order {
	private int orderNumber;
	private LocalDate orderDate;
	private String customerName;
	private StateAbbrev stateAbbrev;
	private BigDecimal taxRate;
	private ProductType productType;
	private BigDecimal area;
	private BigDecimal costPerSquareFoot;
	private BigDecimal laborCostPerSquareFoot;
	private BigDecimal materialCost;
	private BigDecimal laborCost;
	private BigDecimal tax;
	private BigDecimal total;
	
	private static int lastOrderId = 0;
	
	
	public Order(int orderNumber,LocalDate orderDate,String customerName, StateAbbrev stateAbbrev, ProductType productType, BigDecimal area) {
		super();
		this.orderNumber = orderNumber;
		this.customerName = customerName;
		this.stateAbbrev = stateAbbrev;
		this.productType = productType;
		this.area = area;
	}

	public Order(LocalDate orderDate, String customerName, StateAbbrev state, ProductType productType, BigDecimal area) {
		this.orderDate = orderDate;
		this.orderDate = orderDate;
		this.customerName = customerName;
		this.stateAbbrev = state;
		this.productType = productType;
		this.area = area;
	}
	
	public static int getLastOrderId() {
		return lastOrderId;
	}

	public static void setLastOrderId(int lastOrderId) {
		Order.lastOrderId = lastOrderId;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}



	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}



	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public StateAbbrev getStateAbbrev() {
		return stateAbbrev;
	}

	public void setStateAbbrev(StateAbbrev stateAbbrev) {
		this.stateAbbrev = stateAbbrev;
	}

	public BigDecimal getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}

	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

	public BigDecimal getArea() {
		return area;
	}

	public void setArea(BigDecimal area) {
		this.area = area;
	}

	public BigDecimal getCostPerSquareFoot() {
		return costPerSquareFoot;
	}

	public void setCostPerSquareFoot(BigDecimal costPerSquareFoot) {
		this.costPerSquareFoot = costPerSquareFoot;
	}

	public BigDecimal getLaborCostPerSquareFoot() {
		return laborCostPerSquareFoot;
	}

	public void setLaborCostPerSquareFoot(BigDecimal laborCostPerSquareFoot) {
		this.laborCostPerSquareFoot = laborCostPerSquareFoot;
	}

	public BigDecimal getMaterialCost() {
		return materialCost;
	}

	public void setMaterialCost(BigDecimal materialCost) {
		this.materialCost = materialCost;
	}

	public BigDecimal getLaborCost() {
		return laborCost;
	}

	public void setLaborCost(BigDecimal laborCost) {
		this.laborCost = laborCost;
	}

	public BigDecimal getTax() {
		return tax;
	}

	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "Order [orderNumber=" + orderNumber + ", orderDate=" + orderDate + ", customerName=" + customerName
				+ ", productType=" + productType + ", area=" + area + ", materialCost=" + materialCost + ", laborCost="
				+ laborCost + ", tax=" + tax + ", total=" + total + "]";
	}

	
	
	
	
}
