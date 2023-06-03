package com.wileyedge.flooringmastery.service;

import java.util.Map;

import com.wileyedge.flooringmastery.dto.Order;

public interface IService {
	
	void loadTaxFile();
	void loadProductFile();
	void loadOrderFile();
	void saveOrderToFile();
	void caculateTotalOrderCostAndTax(Order order);
	void placeOrder(Order order);
	void displayOrders();
	void editOrder(Order order);
	void removeOrder(int orderId);
	void exportAllData();
	void calculateMaterialCost(Order order);
	void calculateLaborCost(Order order);
	void calculateTax(Order order);
	void calculateTotal(Order order);
	void setCostPerSquareFoot(Order order);
	Map<Integer, Order> getAllOrders();
	void cancelDraftOrder(Order draftOrder);
}
