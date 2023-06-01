package com.wileyedge.flooringmastery.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wileyedge.flooringmastery.dao.IOrderDAO;
import com.wileyedge.flooringmastery.dto.Order;
import com.wileyedge.flooringmastery.dto.Product;
import com.wileyedge.flooringmastery.dto.ProductType;
import com.wileyedge.flooringmastery.dto.Tax;

@Component
public class ServiceImpl implements IService {
	private IOrderDAO dao;
	private Map<String, Tax> taxMap = new HashMap<>();
	private Map<ProductType, Product> productMap = new HashMap<>();
	private Map<Integer, Order> orderMap = new HashMap<>();
	private final File taxFileName = new File("C:\\C353\\flooringmastery\\Taxes.txt");
	private final File productFileName = new File("C:\\C353\\flooringmastery\\Products.txt");

	@Autowired
	public ServiceImpl(IOrderDAO dao) {
		this.dao = dao;
	}

	@Override
	public int addNewOrder(Order order) {
		System.out.println("Service layer ---addNewOrder() invoked. ");
		int newOrderId = 1;


		return newOrderId;
	}


	@Override
	public void loadTaxFile() {
		try (BufferedReader reader = new BufferedReader(new FileReader(taxFileName))) {
	        String line;
	        boolean isFirstLine = true;
	        while ((line = reader.readLine()) != null) {
	            if (isFirstLine) {
	                isFirstLine = false;
	                continue; // Skip the first line
	            }
	            String[] parts = line.split(",");
	            if (parts.length == 3) {
	                String taxAbbrev = parts[0].trim();
	                String stateName = parts[1].trim();
	                BigDecimal taxRate = new BigDecimal(parts[2].trim());
	                Tax tax = new Tax(taxAbbrev, stateName, taxRate);
	                taxMap.put(taxAbbrev, tax);
	            }
	            
	            // Display taxMap 
	            System.out.println("Tax Map:");
	            for (Map.Entry<String, Tax> entry : taxMap.entrySet()) {
	                String taxAbbrev = entry.getKey();
	                Tax tax = entry.getValue();
	                System.out.println("Tax Abbreviation: " + taxAbbrev);
	                System.out.println("Tax Details: " + tax.toString());
	                System.out.println("-----------------------");
	            }
	            
	        }
	    } catch (IOException e) {
	        System.out.println("Error reading tax file: " + e.getMessage());
	    }

	}

	@Override
	public void loadProductFile() {
		try (BufferedReader reader = new BufferedReader(new FileReader(productFileName))) {
	        String line;
	        boolean isFirstLine = true;
	        while ((line = reader.readLine()) != null) {
	            if (isFirstLine) {
	                isFirstLine = false;
	                continue; // Skip the first line
	            }
	            String[] parts = line.split(",");
	            if (parts.length == 3) {
	                String productTypeName = parts[0].trim();
	                BigDecimal costPerSquareFoot = new BigDecimal(parts[1].trim());
	                BigDecimal laborCostPerSquareFoot = new BigDecimal(parts[2].trim());

	                // Convert the product type name to the corresponding ProductType enum
	                ProductType productType = ProductType.valueOf(productTypeName.toUpperCase());

	                Product product = new Product(productType, costPerSquareFoot, laborCostPerSquareFoot);
	                productMap.put(productType, product);
	            }
	            
	            System.out.println("Product Map:");
	            for (Map.Entry<ProductType, Product> entry : productMap.entrySet()) {
	                ProductType productType = entry.getKey();
	                Product product = entry.getValue();
	                System.out.println("Product Type: " + productType);
	                System.out.println("Product Details: " + product.toString());
	                System.out.println("-----------------------");
	            }
	            
	            
	        }
	    } catch (IOException e) {
	        System.out.println("Error reading product file: " + e.getMessage());
	    }
	}

	@Override
	public void loadOrderFile() {
		Map<Integer, Order> ordersFromFile = dao.readOrdersFromFile();
		if (ordersFromFile != null && !ordersFromFile.isEmpty()) {
			this.orderMap.putAll(ordersFromFile);
			for (Map.Entry<Integer, Order> entry : orderMap.entrySet()) {
		        System.out.println(entry.getValue());
		        System.out.println("-----------------------");
		    }
		}else {
			System.out.println("No orders in file.");
		}
	}

	@Override
	public void saveOrderToFile() {
		dao.writeOrdersToFile(orderMap);

	}

	@Override
	public void displayOrders() {
		System.out.println("----- Displaying Orders -----");
		if (orderMap.isEmpty()) {
			System.out.println("No orders found.");
		} else {
			for (Order order : orderMap.values()) {
				System.out.println(order);
			}
		}
		System.out.println("----------------------------");
	}

	@Override
	public void editOrder(Order order) {
		int orderId = order.getOrderId();
		if (orderMap.containsKey(orderId)) {
			orderMap.put(orderId, order);
			System.out.println("Order edited successfully.");
		} else {
			System.out.println("Order not found. Edit failed.");
		}

	}

	@Override
	public void removeOrder(int orderId) {
		if (orderMap.containsKey(orderId)) {
			orderMap.remove(orderId);
			System.out.println("Order removed successfully.");
		} else {
			System.out.println("Order not found. Removal failed.");
		}
	}

	@Override
	public void exportAllData() {
		// TODO Auto-generated method stub

	}

}
