package com.wileyedge.flooringmastery.service;

import java.time.LocalDate;
import java.util.Map;

import com.wileyedge.flooringmastery.dto.Order;

public interface IService {
	
	void loadTaxFile();
	void loadProductFile();
	void loadOrderFile();
	void saveOrderToFile();
	void setCalculatedOrderCostAndTax(Order order);
	void placeOrder(Order order);
	void displayOrders();
	void editOrder(Order order);
	void removeOrder(int orderId);
	void exportAllData();
	void setCalculatedMaterialCost(Order order);
	void setCalculatedLaborCost(Order order);
	void setCalculatedTax(Order order);
	void setCalculatedTotal(Order order);
	void setCostPerSquareFoot(Order order);
	Map<Integer, Order> getAllOrders();
	void cancelDraftOrder(Order draftOrder);
	Order getExistingOrder(LocalDate orderDate, int orderNumber);
	Order saveUpdatedOrder(Order existingOrder, Order draftUpdatedOrder);
}
