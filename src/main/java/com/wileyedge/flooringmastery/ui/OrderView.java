package com.wileyedge.flooringmastery.ui;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wileyedge.flooringmastery.dto.Order;
import com.wileyedge.flooringmastery.dto.ProductType;
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

//    public void displayAllBooks(List<Book> books) {
//        io.print("");
//        io.print("All Books");
//        books.stream()
//                .forEach((b) -> io.print(b.getTitle() + " - " + b.getAuthor()));
//    }
//
//    public String getBookTitle() {
//        io.print("");
//        return io.readString("Enter book name:");
//    }
//
//    public void displayBookDetails(Book book) {        
//        io.print("");
//        io.print("Title: " + book.getTitle());
//        io.print("Author: " + book.getAuthor());
//        io.print("Year: " + book.getYear());
//        io.print("Genre: " + book.getGenre());
//    }
//
    public Order getNewOrder() {
    	Order order = null;
    	System.out.println("Enter new order info");
    	try {
    		LocalDate orderDate = io.getInputAsFutureDate("Order Date","Enter order date (dd/mm/yyy) : ");
    		String customerName = io.getInputAsString("Customer name","Enter customer name : ");
    		String state = io.getInputAsString("State","Enter state abbreveation : ").toUpperCase();
    		ProductType productType = io.getInputAsProductType("Product type","Please enter product type : "); 
    		BigDecimal area = io.getInputAsPositiveBigDecimal("Area size", "Please enter size of the area: ");
    		System.out.println("Finished getting all input. Now start calculation.");
    		order = new Order(orderDate,customerName,state,productType,area);
    		
    		System.out.println("Here below the order details : ");
    		String placeOrder = io.getInputAsStringYN("Would you like to place this order ? Y/N : ");
    		if(placeOrder.equalsIgnoreCase("N")) {
    			order = null;
    		}
    	}catch(Exception e) {
    		System.out.println("Ooop! something went wrong! Please try again later. bye!");
    	}
        return order;
    }

//    public void displayAddSuccess() {
//        io.print("Book added successfully");
//    }
//
    public void displayExit() {
        io.print("Existing Master Flooring Order App ...");
    }
//
//    public String getBookTitleToDelete() {
//        io.print("");
//        return io.readString("Enter book name to delete:");
//    }
//
//    public void displayDeleteSuccess() {
//        io.print("Book deleted successfully");
//    }
//
//    public String getBookTitleToUpdate() {
//        io.print("");
//        return io.readString("Enter book name to update:");    }
//
//    public Book getUpdateBook(Book updateBook) {
//        io.print("Updating " + updateBook.getTitle());
//        String author = io.readString("Enter Author (" + updateBook.getAuthor() + "):");
//        String year = io.readString("Enter Year (" + updateBook.getYear() + "):");
//        String genre = io.readString("Enter Genre (" + updateBook.getGenre() + "):");
//        if(!author.isBlank()) {
//            updateBook.setAuthor(author);
//        }
//        if(!year.isBlank()) {
//            updateBook.setYear(Integer.parseInt(year));
//        }
//        if(!genre.isBlank()) {
//            updateBook.setGenre(genre);
//        }
//        return updateBook;
//    }

    public void displayUpdateSuccess() {
        io.print("Order updated successfully");
    }

}
