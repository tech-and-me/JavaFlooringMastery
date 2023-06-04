package com.wileyedge.flooringmastery.dao;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.wileyedge.flooringmastery.dto.Order;
import com.wileyedge.flooringmastery.dto.ProductType;
import com.wileyedge.flooringmastery.dto.StateAbbrev;

@Component
public class OrderDAOImpl implements IOrderDAO {
	private final File orderFileName = new File("C:\\C353\\flooringmastery\\Orders.txt");


	@Override
	public Map<Integer, Order> readOrdersFromFile() {
		Map<Integer, Order> orderMap = new HashMap<>();
		BufferedReader reader = null;
		String line = "";
		try {
			if (!orderFileName.exists()) {
				System.out.println("File not found. Empty orderMap is created instead.");
				return orderMap;
			}

			reader = new BufferedReader(new FileReader(orderFileName));
			String headerLine = reader.readLine(); // Read and discard the header line

			//looping from second line onward to collect order records

			while ((line = reader.readLine()) != null) {
				String[] values = line.split(",");

				// Validate the number of values in the line
				if (values.length != 13) {
					System.out.println("Value lenght is : " + values.length);
					System.out.println("Values is : " + values);
					System.out.println("Invalid data format in line: " + line);
					continue; // Skip this line and proceed to the next one
				}


				// Parse the values and create an Order object
				int orderNumber = Integer.parseInt(values[0]);
				String customerName = values[1];
				StateAbbrev stateAbbrev = StateAbbrev.valueOf(values[2]);
				BigDecimal taxRate = new BigDecimal(values[3]);
				ProductType productType = ProductType.valueOf(values[4]);
				BigDecimal area = new BigDecimal(values[5]);
				BigDecimal costPerSquareFoot = new BigDecimal(values[6]);
				BigDecimal laborCostPerSquareFoot = new BigDecimal(values[7]);
				BigDecimal materialCost = new BigDecimal(values[8]);
				BigDecimal laborCost = new BigDecimal(values[9]);
				BigDecimal tax = new BigDecimal(values[10]);
				BigDecimal total = new BigDecimal(values[11]);
				
				//Parse date to correct format
				DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	            LocalDate orderDate = LocalDate.parse(values[12], dateFormatter);

				Order order = new Order(orderNumber, customerName, stateAbbrev, taxRate, productType, area,
						costPerSquareFoot, laborCostPerSquareFoot, materialCost, laborCost, tax, total, orderDate);
				orderMap.put(orderNumber, order);
			}
			//Update last order id to the max id so that the next new order will continue from the max of loaded order id
			updateLastOrderId(orderMap);
			System.out.println("Order file loaded successfully.");
		} catch(NumberFormatException e) {
			System.out.println("Error parsing decimal value in line: " + line);
		} catch (FileNotFoundException e) {
			System.out.println("File not found. Empty orderMap is created instead.");
		} catch (IOException e) {
			System.out.println("Error reading from file: " + e.getMessage());
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				System.out.println("Error closing file: " + e.getMessage());
			}
		}
		
		return orderMap;
	}


	@Override
	public void writeOrdersToFile(Map<Integer,Order> orderMap) {

		// Check if orderMap is null or empty
		if (orderMap == null || orderMap.isEmpty()) {
			System.out.println("No orders to write.");
			return;
		}

		FileWriter fw = null; // this class extends 'Writer'and is used to write character data to a file
		BufferedWriter bw = null; // this class also extends 'Writer' --provide buffering capabilities for efficient writing of text data in a large chunk.
		try {

			// Create the file if it doesn't exist
			if (!orderFileName.exists()) {
				orderFileName.createNewFile();
			}

			// create BufferedWited object to write 
			fw = new FileWriter(orderFileName);
			bw = new BufferedWriter(fw);

			// Write the header
			StringBuilder headerBuilder = new StringBuilder();
			headerBuilder.append("OrderNumber,");
			headerBuilder.append("CustomerName,");
			headerBuilder.append("State,");
			headerBuilder.append("TaxRate,");
			headerBuilder.append("ProductType,");
			headerBuilder.append("Area,");
			headerBuilder.append("CostPerSquareFoot,");
			headerBuilder.append("LaborCostPerSquareFoot,");
			headerBuilder.append("MaterialCost,");
			headerBuilder.append("LaborCost,");
			headerBuilder.append("Tax,");
			headerBuilder.append("Total");
			headerBuilder.append("OrderDate");
			bw.write(headerBuilder.toString());
			bw.newLine();

			// Write the orders
			for (Order order : orderMap.values()) {
				StringBuilder lineBuilder = new StringBuilder();
				lineBuilder.append(order.getOrderNumber()).append(",");
				lineBuilder.append(order.getCustomerName()).append(",");
				lineBuilder.append(order.getStateAbbrev()).append(",");
				lineBuilder.append(order.getTaxRate()).append(",");
				lineBuilder.append(order.getProductType()).append(",");
				lineBuilder.append(order.getArea()).append(",");
				lineBuilder.append(order.getCostPerSquareFoot()).append(",");
				lineBuilder.append(order.getLaborCostPerSquareFoot()).append(",");
				lineBuilder.append(order.getMaterialCost()).append(",");
				lineBuilder.append(order.getLaborCost()).append(",");
				lineBuilder.append(order.getTax()).append(",");
				lineBuilder.append(order.getTotal()).append(",");
				
				// Format the date as "dd-MM-yyyy" before writing
	            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	            lineBuilder.append(order.getOrderDate().format(dateFormatter));

	            bw.write(lineBuilder.toString());
				bw.newLine();
			}

			System.out.println("Order data has been written to the file successfully!");
		} catch (IOException e) {
			System.out.println("An error occurred while writing to the file: " + e.getMessage());
		} finally {
			try {
				if (bw != null) {
					bw.close();
				}if (fw != null) {
					fw.close();
				}
			} catch (IOException e) {
				System.out.println("An error occurred while closing the file: " + e.getMessage());
			}
		}
	}

	public void updateLastOrderId(Map<Integer, Order> orderMap) {
		int maxOrderId = 0;

		for (int orderId : orderMap.keySet()) {
			if (orderId > maxOrderId) {
				maxOrderId = orderId;
			}
		}

		Order.setLastOrderId(maxOrderId);
	}



}
