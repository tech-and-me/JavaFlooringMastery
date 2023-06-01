package com.wileyedge.flooringmastery.service;

import com.wileyedge.flooringmastery.dto.Order;

public interface IService {
	
	void loadTaxFile();
	void loadProductFile();
	void loadOrderFile();
	void saveOrderToFile();
	int addNewOrder(Order order);
	void displayOrders();
	void editOrder(Order order);
	void removeOrder(int orderId);
	void exportAllData();
}
