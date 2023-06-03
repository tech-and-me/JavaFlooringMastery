package com.wileyedge.flooringmastery.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wileyedge.flooringmastery.dto.Order;
import com.wileyedge.flooringmastery.service.IService;
import com.wileyedge.flooringmastery.ui.OrderView;

@Component
public class OrderController {
	private IService service;
	private OrderView view;

	@Autowired
	public OrderController(IService service, OrderView view) {
		this.service = service;
		this.view = view;
	}

	public void run() {
		//load data from file
		service.loadTaxFile();
		service.loadProductFile();
		service.loadOrderFile();
		
		
		//display menu
		while(true) {
			int choice = view.displayMenuAndGetChoice();
			switch(choice) {
			case 1: //Display Orders
				Map<Integer,Order> orderMap = service.getAllOrders();
				view.displayAllOrders(orderMap);
				break;
			case 2: // Add an Order
				Order order = view.getNewOrder();
				if(order!= null) {
					service.caculateTotalOrderCostAndTax(order);
					boolean confirmOrder = view.confirmOrder(order);
					if(confirmOrder) {
						service.placeOrder(order);
						view.displayAddedSuccess(order);
					}else {
						service.cancelDraftOrder(order);
					}
				}
				break;
			case 3: // Edit an Order
				System.out.println("Edit an order ...");
				break;
			case 4: // Remove an Order
				System.out.println("Remove an order ...");
				break;
			case 5: // Export All Data
				System.out.println("Export all data...");
				break;
			case 6: // Quit
				view.displayExit();
                System.exit(0);
				break;
			case 7: // Backup Data to a Backup folder
				System.out.println("Export all data to a backup folder.");
				break;
			default: //unknown
				view.displayError("Unknown Option");
			}
		}
	}
}
