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

    public Order getNewOrder() {
    	Order order = null;
    	io.print("Enter new order info");
    	try {
    		LocalDate orderDate = io.getInputAsFutureDate("Order Date","Enter order date (dd/mm/yyy) : ");
    		String customerName = io.getInputAsString("Customer name","Enter customer name : ");
    		StateAbbrev stateAbbrev = io.getInputAsState("State","Enter state abbreveation : ");
    		ProductType productType = io.getInputAsProductType("Product type","Please enter product type : "); 
    		BigDecimal area = io.getInputAsPositiveBigDecimal("Area size", "Please enter size of the area: ");
    		io.print("Finished getting all input. Now wrapping input info into object to return to service layer.");
    		order = new Order(orderDate,customerName,stateAbbrev,productType,area);
    	}catch(Exception e) {
    		order = null;
    		io.print("Ooop! something went wrong in getNewOrder() --View Layer !");
    	}
        return order;
    }

    public void displayExit() {
        io.print("Existing Master Flooring Order App ...");
    }
    
    public void displayAddedSuccess(Order order){
    	io.print("Below order has been added successfully.");
    	io.print(order.toString());
    }
    
    public void displayAllOrders(Map<Integer,Order> orderMap) {
    	if(orderMap == null) {
    		io.print("No order to display.");
    		return;
    	}
    	io.print("-------------ORDER LIST-------------");
    	for(Order order:orderMap.values()) {
    		io.print(order.toString());
    	}
    }

    public void displayUpdateSuccess() {
        io.print("Order updated successfully");
    }
    
    public boolean confirmOrder(Order order) {
    	io.print("Based on info provided, here's the summary of all the cost involved : ");
    	io.print("-------ORDER SUMMARY------");
    	io.print("Material cost : " + order.getMaterialCost());
    	io.print("Labor cost : " + order.getLaborCost());
    	io.print("Tax : " + order.getTax());
    	io.print("Total cost (tax inclusive) : " + order.getTax());
    	io.print("--------------------------");
    	String input =io.getInputAsStringYN("Would you like to place the order Y/N : ");
    	if(input.equalsIgnoreCase("Y")) {
    		return true;
    	}else {
    		io.print("Darft order cancelled successfully.");
    		return false;
    	}
    }

}
