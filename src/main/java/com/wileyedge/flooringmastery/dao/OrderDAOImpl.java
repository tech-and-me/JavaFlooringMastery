package com.wileyedge.flooringmastery.dao;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;
import com.wileyedge.flooringmastery.dto.Order;

@Component
public class OrderDAOImpl implements IOrderDAO {
	private final File orderFileName = new File("C:\\C353\\flooringmaster\\Orders.txt");

	@Override
	public Map<Integer,Order> readOrdersFromFile() {
		 Map<Integer, Order> orderMap = new HashMap<>();
		    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(orderFileName))) {
		        Object obj = ois.readObject();

		        if (obj == null) {
		            System.out.println("No item in file.");
		        } else {
		            if (obj instanceof Map) {
		                orderMap = (Map<Integer, Order>) obj;
		                System.out.println("File read successfully.");
		            }
		        }
		    } catch (FileNotFoundException e) {
		        System.out.println("File not found. Empty orderMap is created instead.");
		    } catch (IOException e) {
		        System.out.println("Error reading from file: " + e.getMessage());
		    } catch (ClassNotFoundException e) {
		        System.out.println("Class not found!");
		    } catch (Exception e) {
		        System.out.println("Oops! Something went wrong. Please try again later.");
		    }
		
		return orderMap;
	}


	@Override
	public void writeOrdersToFile(Map<Integer,Order> orderMap) {
		try (
				FileOutputStream fos = new FileOutputStream(orderFileName);
				BufferedOutputStream bos = new BufferedOutputStream(fos);
				ObjectOutputStream oos = new ObjectOutputStream(bos)
				) {

			oos.writeObject(orderMap);
			System.out.println("File written successfully.");

		} catch (IOException e) {
			System.out.println("Error writing to file: " + e.getMessage());
		}
		
	}

}
