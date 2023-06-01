package com.wileyedge.flooringmastery.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public class Order {
	private int orderId;
	private LocalDate orderDate;
	private int orderNumber;
	private String customerName;
	private String state;
	private BigDecimal taxRate;
	private ProductType productType;
	private BigDecimal area;
	private BigDecimal costPerSquareFoot;
	private BigDecimal laborCostPerSquareFoot;
	private BigDecimal materialCost;
	private BigDecimal laborCost;
	private BigDecimal tax;
	private BigDecimal total;
	
	public Order(LocalDate orderDate, String customerName, String state, ProductType productType, BigDecimal area) {
		this.orderDate = orderDate;
		this.customerName = customerName;
		this.state = state;
		this.productType = productType;
		this.area = area;
		
		//calculate and set material cost
		calculateMaterialCost();
		
		//calculate and set labor cost
	    calculateLaborCost();
	    
	    //calculate and set tax
	    calculateTax();
	    
	    //calculate and set total
	    calculateTotal();
	    
	}
	
	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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
	
	private void calculateMaterialCost() {
		System.out.println("Inside calculate material cost");
	    this.materialCost = this.area.multiply(this.productType.getPricePerSquareFoot());
	}

	private void calculateLaborCost() {
		System.out.println("Inside calculate laborCost");
	    this.laborCost = this.area.multiply(this.laborCostPerSquareFoot);
	}

	private void calculateTax() {
		System.out.println("Inside calculate tax");
	    BigDecimal totalCost = this.materialCost.add(this.laborCost);
	    BigDecimal taxRatePercentage = this.taxRate.divide(BigDecimal.valueOf(100));
	    this.tax = totalCost.multiply(taxRatePercentage);
	}

	private void calculateTotal() {
		System.out.println("Inside calculate total");
	    this.total = this.materialCost.add(this.laborCost).add(this.tax);
	}
	
	@Override
	public String toString() {
		return "Order [orderDate=" + orderDate + ", orderNumber=" + orderNumber + ", customerName=" + customerName
				+ ", state=" + state + ", taxRate=" + taxRate + ", productType=" + productType + ", area=" + area
				+ ", costPerSquareFoot=" + costPerSquareFoot + ", laborCostPerSquareFoot=" + laborCostPerSquareFoot
				+ ", materialCost=" + materialCost + ", laborCost=" + laborCost + ", tax=" + tax + ", total=" + total
				+ "]";
	}
	
	
}
