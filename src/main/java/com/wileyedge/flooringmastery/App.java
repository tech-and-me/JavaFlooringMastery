package com.wileyedge.flooringmastery;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.wileyedge.flooringmastery.controller.OrderController;


public class App {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
		appContext.scan("com.wileyedge.flooringmastery");
		appContext.refresh();
		
		// Invoke order controller make order processing available for user
		OrderController controller = appContext.getBean("orderController",OrderController.class);
		controller.run();
		
		// Close the application context to release resources
		appContext.close(); 
	}

}
