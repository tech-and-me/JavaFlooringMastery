package com.wileyedge.flooringmastery.ui;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wileyedge.flooringmastery.dto.Order;
import com.wileyedge.flooringmastery.dto.ProductType;
import com.wileyedge.flooringmastery.dto.StateAbbrev;
import com.wileyedge.flooringmastery.utilities.IUserIO;


@Component
public class OrderView {
    
    private IUserIO io;
    
    @Autowired
    public OrderView(IUserIO io) {
        this.io = io;
    }
    
    public void displayAppBanner() {
        io.print("Welcome to flooring master ordering system");
    }

    public int displayMenuAndGetChoice() {
        io.print("");
        io.print("Main Menu");
        io.print("1. Display Orders");
        io.print("2. Add an Order");
        io.print("3. Edit an Order");
        io.print("4. Remove an Order");
        io.print("5. Export All Data");
        io.print("6. Quit");
        
        return io.getInputAsInteger("Option","Please select an option:", 1, 6);
    }

    public void displayError(String error) {
        io.print("ERROR: " + error);
    }
    
    public void displayTransactionFailed(String failedMessage) {
        io.print("Transaction failed: " + failedMessage);
    }

    public Order getNewOrder() {
    	Order order = null;
    	io.print("Enter new order info");
    	try {
    		LocalDate orderDate = io.getInputAsFutureDate("Order date","Enter order date (dd/mm/yyy) : ");
    		String customerName = io.getInputAsString("Customer name","Enter customer name : ");
    		StateAbbrev stateAbbrev = io.getInputAsState("State","Enter state abbreveation : ");
    		ProductType productType = io.getInputAsProductType("Product type","Please enter product type : "); 
    		BigDecimal area = io.getInputAsPositiveBigDecimal("Area size", "Please enter size of the area: ");
    		order = new Order(orderDate,customerName,stateAbbrev,productType,area);
    	}catch(Exception e) {
    		order = null;
    		io.print("Ooop! something went wrong in getNewOrder() --View Layer !");
    	}
        return order;
    }
    
    
    public Order getDraftUpdatedOrder(Order order) {
    	Order draftUpdatedOrder =  null;
    	io.print("Enter updated order info");
    	try {
    		String customerName = io.getInputAsString("Customer name", "Enter customer name (" + order.getCustomerName() + "):");
    		StateAbbrev stateAbbrev = io.getInputAsState("State", "Enter state abbreviation (" + order.getStateAbbrev() + "):");
    		ProductType productType = io.getInputAsProductType("Product type", "Enter product type (" + order.getProductType() + "):");
    		BigDecimal area = io.getInputAsPositiveBigDecimal("Area size", "Enter area size (" + order.getArea() + "):");
    		
    		// send the above 4 input to controller
    		draftUpdatedOrder = new Order(order.getOrderNumber(),order.getOrderDate(),customerName,stateAbbrev,productType,area);
    		System.out.println("ORDER IN DARFT VIEW --" + order);
    	}catch(Exception e) {
    		draftUpdatedOrder = null;
    		io.print("Ooop! something went wrong in getNewOrder() --View Layer !");
    	}
        return draftUpdatedOrder;
    }
    
    
    public LocalDate getOrderDate() {
    	LocalDate orderDate = null;
    	try {
    		orderDate = io.getInputAsFutureDate("Order date","Enter order date (dd/mm/yyy) : ");
    	}catch(Exception e) {
    		io.print("Oop! something went wrong in orderDate()"); 
    	}
    	return orderDate;
    }
    
    public int getOrderNumber() {
    	int orderNumber = -1;
    	try {
    		orderNumber = io.getInputAsInteger("Order number", "Enter order number");
    	}catch(Exception e) {
    		io.print("Oop! something went wrong in getOrderNumber()"); 
    	}
    	return orderNumber;
    }

    public void displayExit() {
        io.print("Existing Master Flooring Order App ...");
    }
    
    public void displayAddedSuccess(Order order){
    	io.print("Below order has been added successfully.");
    	io.print(order.toString());
    }
    
    public void displayAllOrders(Map<Integer,Order> orderMap) {
    	if(orderMap == null || orderMap.isEmpty()) {
    		io.print("No order to display.");
    	}else {
    		io.print("-------------ORDER LIST-------------");
        	for(Order order:orderMap.values()) {
        		io.print(order.toString());
        	}
    	}
    }

    public void displayUpdateSuccess() {
        io.print("Order updated successfully");
    }
    
    public boolean confirmOrder(Order order) {
    	io.print("-------ORDER SUMMARY------");
    	io.print("Customer name: " + order.getCustomerName() );
    	io.print("State" + order.getStateAbbrev());
    	io.print("Product type: " + order.getProductType() );
    	io.print("Area: " + order.getArea());
    	io.print("Material cost : " + order.getMaterialCost());
    	io.print("Labor cost : " + order.getLaborCost());
    	io.print("Tax : " + order.getTax());
    	io.print("Total cost (tax inclusive) : " + order.getTax());
    	io.print("--------------------------");
    	String input =io.getInputAsStringYN("Would you like to place/update the order Y/N : ");
    	if(input.equalsIgnoreCase("Y")) {
    		return true;
    	}else {
    		io.print("Transaction abort : no further action taken for this order.");
    		return false;
    	}
    }
    
    public boolean confirmRemoveOrder() {
    	String isRemove = io.getInputAsStringYN("Are you sure you want to remove this order ? Y/N :  ");
    	if(isRemove.equalsIgnoreCase("Y")) {
    		return true;
    	}else {
    		io.print("Transaction abort: No action taken for this order");
    	}
    	return false;
    }

}
