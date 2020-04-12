package controller;

import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import view.AddProduct;
import view.MainFrame;

public class ProductController extends DBConnect{
	
	public static ArrayList<String[]> getProducts(String category){
		String qryGetProductsFromCategory = "select * from products where category = " + "'" + category + "'";
		
		ArrayList<String[]> products = new ArrayList<String[]>();
		
		try {
			// Execute the query
			results = statement.executeQuery(qryGetProductsFromCategory);
		} catch (Exception ex) {
			// Catch any errors and display the error
			System.out.println("Error 1: " + ex);
		}
		
		try {
			// Add all of the products in the category to the ArrayList Products
			while(results.next()){
				// Create a new product on each iteration so that the arrayList does not reference the same memory location
				String[] product = new String[4];
				product[0] = results.getString("name");
				product[1] = results.getString("productId");
				product[2] = results.getString("price");
				product[3] = results.getString("quantity");
				
				products.add(product);
				for(int i = 0; i < products.size(); i++){
					System.out.println("Product " + i + " is " + Arrays.toString(products.get(i)));
				}
			}
		} catch (Exception e) {
			// Output an error in the console
			System.out.println("Error 2: " + e);
		}
		return products;
	}
	
	public static String getProductName(int id){
		String productName = null;
		
		String qryGetProductById = "Select * from products where productId = " + id + " LIMIT 1";
		
		try {
			// Execute the query
			results = statement.executeQuery(qryGetProductById);
		} catch (Exception ex) {
			// Catch any errors and display the error
			System.out.println("Error 1: " + ex);
		}
		
		try {
			// Set the product name
			while(results.next()){
				productName = results.getString("name");
			}
		} catch (Exception e) {
			// Output an error in the console
			System.out.println("Error 2: " + e);
		}
		
		return productName;
	}
	
	public static int getProductId(String product){
		int productId = -1;
		String qryGetProductId = "Select * from products where name = '" + product + "' LIMIT 1";
		
		try{
			// Execute the query
			results = statement.executeQuery(qryGetProductId);
		} catch (Exception ex) {
			// Catch any errors and display the error
			System.out.println("Error 1: " + ex);
		}
		
		try {
			// Set the product name
			while(results.next()){
				productId = Integer.parseInt(results.getString("productId"));
			}
		} catch (Exception e) {
			// Output an error in the console
			System.out.println("Error 2: " + e);
		}
		
		
		return productId;
	}
	
	public static String getPrice(int productId){
		String qryGetPrice = "select price from products where productId = " + productId;
	
		String price = null;
		
		try{
			results = statement.executeQuery(qryGetPrice);
		} catch(Exception ex){
			System.out.println("Error 1: " + ex);
		}
		
		try{
			while(results.next()){
				price = "$" + results.getString("price");
			}
		} catch(Exception ex){
			System.out.println("Error 2: " + ex);
		}
		
		return price;
	}
	
	public static void addProduct(String name, String category, int quantity, double price){
		String qryAddProduct = "insert into products (name, category, quantity, price) values " + "('" + name + "'," + "'" + category + "'," + "'" + quantity + "'," + "'" + price + "')";
		
		try {
			statement.executeUpdate(qryAddProduct);
		} catch (SQLException e) {
			System.out.println("Error 1: " + e);
		}
	}
	
	public static void addLesson(String name, String category, int positions, double price, int staffId, Date time, String day, int duration, String location, Date datePaymentDue, String term, int year){
		String qryAddProduct = "insert into products (name, category, quantity, price) values " + "('" + name + "'," + "'" + category + "'," + "'" + positions + "'," + "'" + price + "')";
		
		int productId = -1;
		
		try {
			statement.executeUpdate(qryAddProduct);
		} catch (SQLException e) {
			System.out.println("Error 1: " + e);
		}

		// ProductId of the lesson just added
		String qryProductId = "select last_insert_id() from products";
		try {
			// Execute the query
			results = statement.executeQuery(qryProductId);
		} catch (Exception ex) {
			// Catch any errors and display the error
			System.out.println("Error 1: " + ex);
		}
		
		try {
			while(results.next()){
				productId = Integer.parseInt(results.getString("last_insert_id()"));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		LessonController.addLesson(productId, datePaymentDue, location, day, time, duration, staffId, term, year, positions);
	}

	public static void addFixture(String product, String category, int positions, double price, Date time, String day, int duration, String location, Date datePaymentDue, String term, int year) {
		String qryAddProduct = "insert into products (name, category, quantity, price) values " + "('" + product + "'," + "'" + category + "'," + "'" + positions + "'," + "'" + price + "')";
		
		int productId = -1;
		
		try {
			statement.executeUpdate(qryAddProduct);
		} catch (SQLException e) {
			System.out.println("Error 1: " + e);
		}
		
		// ProductId of the lesson just added
		String qryProductId = "select last_insert_id() from products";
		try {
			// Execute the query
			results = statement.executeQuery(qryProductId);
		} catch (Exception ex) {
			// Catch any errors and display the error
			System.out.println("Error 1: " + ex);
		}
		
		try {
			while(results.next()){
				productId = Integer.parseInt(results.getString("last_insert_id()"));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		FixtureController.addFixture(productId, datePaymentDue, location, day, time, duration, term, year, positions);
	}
	
	public static boolean validateProduct(String name, String category, int quantity, double price){
		boolean added = false;
		
		if(name.equals(null)){
			AddProduct.showError("Product name");
			return added;
		} else if(category.equals(null)){
			AddProduct.showError("Category");
			return added;
		} else if(quantity <= 0){
			AddProduct.showError("Quantity");
			return added;
		} else if(price <= 0.0){
			AddProduct.showError("Price");
			return added;
		} else{
			addProduct(name, category, quantity, price);
			added = true;
		}
		
		return added;
	}
	
	public static boolean validateFixture(String name, String category, int positions, double price, Date time, String day, int duration, String location, Date datePaymentDue, String term, int year){
		Calendar now = Calendar.getInstance();
		int thisYear = now.get(Calendar.YEAR);
		boolean added = false;
		
		if(name.equals(null)){
			AddProduct.showError("Product name");
			return added;
		} else if(category.equals(null)){
			AddProduct.showError("Category");
			return added;
		} else if(datePaymentDue.equals(null)){
			AddProduct.showError("Date payment due");
			return added;
		} else if(location.equals(null)){
			AddProduct.showError("Location");
			return added;
		} else if(day.equals(null)){
			AddProduct.showError("Day");
			return added;
		} else if(time.equals(null)){
			AddProduct.showError("Time");
			return added;
		} else if(duration <= 0){
			AddProduct.showError("Duration");
			return added;
		} else if(term.equals(null)){
			AddProduct.showError("Term");
			return added;
		} else if(year < thisYear){
			AddProduct.showError("Year");
			return added;
		} else if(positions <= 0){
			AddProduct.showError("Positions");
			return added;
		} else if(price <= 0.0){
			AddProduct.showError("Price");
			return added;
		} else{
			addFixture(name, category, positions, price, time, day, duration, location, datePaymentDue, term, year);
			added = true;
		}
		
		return added;
	}
	
	public static boolean validateLesson(String name, String category, int positions, double price, int staffId, Date time, String day, int duration, String location, Date datePaymentDue, String term, int year){
		Calendar now = Calendar.getInstance();
		int thisYear = now.get(Calendar.YEAR);
		boolean added = false;
		
		if(name.equals(null)){
			AddProduct.showError("Product name");
			return added;
		} else if(staffId == 0){
			AddProduct.showError("Staff");
			return added;
		} else if(category.equals(null)){
			AddProduct.showError("Category");
			return added;
		} else if(datePaymentDue.equals(null)){
			AddProduct.showError("Date payment due");
			return added;
		} else if(location.equals(null)){
			AddProduct.showError("Location");
			return added;
		} else if(day.equals(null)){
			AddProduct.showError("Day");
			return added;
		} else if(time.equals(null)){
			AddProduct.showError("Time");
			return added;
		} else if(duration <= 0){
			AddProduct.showError("Duration");
			return added;
		} else if(term.equals(null)){
			AddProduct.showError("Term");
			return added;
		} else if(year < thisYear){
			AddProduct.showError("Year");
			return added;
		} else if(positions <= 0){
			AddProduct.showError("Positions");
			return added;
		} else if(price <= 0.0){
			AddProduct.showError("Price");
			return added;
		} else{
			// String name, String category, int positions, double price, int staffId, Date time, String day, int duration, String location, Date datePaymentDue, String term, int year
			addLesson(name, category, positions, price, staffId, time, day, duration, location, datePaymentDue, term, year);
			added = true;
		}
		
		return added;
	}
	
	public static String getCategory(int productId){
		String qryGetCategory = "select category from products where productId = " + productId;
		
		String category = null;
		
		try{
			// Execute the query
			results = statement.executeQuery(qryGetCategory);
		} catch (Exception ex) {
			// Catch any errors and display the error
			System.out.println("Error 1: " + ex);
		}
		
		try {
			// Set the product name
			while(results.next()){
				category = results.getString("category");
			}
		} catch (Exception e) {
			// Output an error in the console
			System.out.println("Error 2: " + e);
		}
		return category;
	}
	
	public static void updateQuantity(int qtyLeft, int productId){
		String qryUpdateQuantity = "update products set quantity = " + qtyLeft + " where productId = " + productId;

		try {
			// Execute the query
			statement.executeUpdate(qryUpdateQuantity);
			System.out.println("Query complete. New quantity is " + qtyLeft);
		} catch (Exception ex) {
			// Catch any errors and display the error
			System.out.println("Error 1: " + ex);
		}
	}
}