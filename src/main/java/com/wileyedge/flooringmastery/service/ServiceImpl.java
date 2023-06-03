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
import com.wileyedge.flooringmastery.dto.StateAbbrev;
import com.wileyedge.flooringmastery.dto.Tax;

@Component
public class ServiceImpl implements IService {
	private IOrderDAO dao;
	private Product product = null;
	private Map<String, Tax> taxMap = new HashMap<>();
	private Map<ProductType, Product> productMap = new HashMap<>();
	private Map<Integer, Order> orderMap = new HashMap<>();
	private final File taxFileName = new File("C:\\C353\\flooringmastery\\Taxes.txt");
	private final File productFileName = new File("C:\\C353\\flooringmastery\\Products.txt");


	public ServiceImpl() {}

	@Autowired
	public ServiceImpl(IOrderDAO dao,Product product) {
		this.dao = dao;
		this.product = product;
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
					String stateAbbrev = parts[0].trim();
					String stateName = parts[1].trim();
					BigDecimal taxRate = new BigDecimal(parts[2].trim());
					Tax tax = new Tax(stateAbbrev, stateName, taxRate);
					taxMap.put(stateAbbrev, tax);
				}
			}
			
			// Display taxMap 
			System.out.println("Tax Map:");
			for (Map.Entry<String, Tax> entry : taxMap.entrySet()) {
				Tax tax = entry.getValue();
				System.out.println("Tax Details: " + tax.toString());
			}
			System.out.println("-----------------------");
		} catch (IOException e) {
			System.out.println("Error reading tax file: " + e.getMessage());
		}

	}

	@Override
	public void loadProductFile() {

		try{
			FileReader fr = new FileReader(productFileName);
			BufferedReader reader = new BufferedReader(fr);

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

					Product product = new Product(productTypeName, costPerSquareFoot, laborCostPerSquareFoot);
					productMap.put(productType, product);
				}
			}
			System.out.println("Product Map:");
			for (Map.Entry<ProductType, Product> entry : productMap.entrySet()) {
				Product product = entry.getValue();
				System.out.println("Product Details: " + product.toString());
			}
			System.out.println("-----------------------");
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
			}
			System.out.println("-----------------------");
		}else {
			System.out.println("No orders to display.");
		}
	}

	@Override
	public void saveOrderToFile() {
		dao.writeOrdersToFile(orderMap);

	}

	@Override
	public void caculateTotalOrderCostAndTax(Order order) {
		this.setCostPerSquareFoot(order);
		this.calculateMaterialCost(order);
		this.calculateLaborCost(order);
		this.calculateTax(order);
		this.calculateTotal(order);
	}

	@Override
	public void setCostPerSquareFoot(Order order) {
		ProductType productType = order.getProductType();
		BigDecimal costPerSquareFoot = productMap.get(productType).getCostPerSquareFoot();
		order.setCostPerSquareFoot(costPerSquareFoot);
	}

	@Override
	public void calculateLaborCost(Order order) {
		ProductType productType = order.getProductType();
	
		//Extract labor cost per square foot from productMap
		BigDecimal laborCostPerSquareFoot = productMap.get(productType).getLaborCostPerSquareFoot();
		
		//set labor cost for square foot to order
		order.setLaborCostPerSquareFoot(laborCostPerSquareFoot);
		
		//Calculate total labor cost
		BigDecimal laborCost = order.getArea().multiply(laborCostPerSquareFoot);
		
		//set total labor cost to order
		order.setLaborCost(laborCost);
	}

	@Override
	public void calculateMaterialCost(Order order) {
		//Extract product type from the order
		ProductType productType = order.getProductType();
		
		//Get costPerSquareFoot from Product class according to ordered product type
		BigDecimal costPerSquareFoot = productMap.get(productType).getCostPerSquareFoot();
		
		//Calculate material cost
		BigDecimal materialCost = order.getArea().multiply(costPerSquareFoot);
		
		//Set material cost
		order.setMaterialCost(materialCost);
	}

	@Override
	public void calculateTax(Order order) {
		BigDecimal totalCost = order.getMaterialCost().add(order.getLaborCost());
		StateAbbrev stateAbbrev = order.getStateAbbrev();
		
		//extract tax rate from taxMap
		BigDecimal taxRate = taxMap.get(stateAbbrev.name()).getTaxRate();
		
		//set tax rate of the order
		order.setTaxRate(taxRate);
		
		BigDecimal taxRatePercentage = taxRate.divide(BigDecimal.valueOf(100));
		
		BigDecimal tax = totalCost.multiply(taxRatePercentage);
		order.setTax(tax);
	}
	
	@Override
	public void calculateTotal(Order order) {
		BigDecimal total = order.getMaterialCost().add(order.getLaborCost()).add(order.getTax());
		order.setTotal(total);
	}
	
	@Override
	public void placeOrder(Order order) {
		//Generate and set order number
		int newOrderId = Order.getLastOrderId() + 1;
		order.setOrderNumber(newOrderId);;

		//Add order to the list
		orderMap.put(order.getOrderNumber(), order);
		
		//Update last order id
		Order.setLastOrderId(newOrderId);
	}

	@Override
	public void displayOrders() {
		
		if (orderMap.isEmpty()) {
			System.out.println("No orders found.");
		} else {
			System.out.println("----------- ORDER LIST -----------");
			for (Order order : orderMap.values()) {
				System.out.println(order);
			}
		}
		System.out.println("----------------------------");
	}

	@Override
	public void editOrder(Order order) {
		int orderId = order.getOrderNumber();
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

	@Override
	public Map<Integer, Order> getAllOrders() {
		return orderMap;
	}
	
	@Override
	public void cancelDraftOrder(Order draftOrder) {
		draftOrder = null;
	}

}
