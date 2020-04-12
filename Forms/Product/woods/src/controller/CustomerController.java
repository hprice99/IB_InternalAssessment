package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import view.MainFrame;

public class CustomerController extends DBConnect{
	
	private static Statement lessonPaymentStatement;
	private static ResultSet lessonPaymentResult;
	
	private static Statement fixturePaymentStatement;
	private static ResultSet fixturePaymentResult;
	
	public static int validateCustomer(String fName, String lName, String add1, String add2, String suburb, String pCode, String state, String phone, String email){
		int customerId = -1;

		// Format the phone number to delete all spaces
		phone = phone.replaceAll("[^0-9]", "");
		
		
		// Validate all of the new customer's data
		if(!Pattern.matches("[a-zA-Z]+", fName)){
			errorMessage("First name");
			return customerId;
		} else if(!Pattern.matches("[a-zA-Z]+", lName)){
			errorMessage("Last name");
			return customerId;
		} else if(!(add1 != null && add2 != null && suburb != null && Pattern.matches("[0-9]+", pCode) && pCode.length() == 4)){
			errorMessage("Address");
			return customerId;
		} else if(!Pattern.matches("[0-9]+", phone) || phone.length() != 10){
			errorMessage("Phone number");
			return customerId;
		} else if(!Pattern.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", email)){
			errorMessage("Email");
			return customerId;
		}
		customerId = addCustomer(fName, lName, add1, add2, suburb, pCode, state, phone, email);
	
		return customerId;
	}
	
	public static boolean validateCustomer(String add1, String add2, String suburb, String pCode, String state, String phone, String email, int customerId){
		// Format the phone number to delete all spaces
		phone = phone.replaceAll("[^0-9]", "");
		
		boolean updated = false;
		
		if(!(add1 != null && add2 != null && suburb != null && Pattern.matches("[0-9]+", pCode) && pCode.length() == 4)){
			errorMessage("Address");
			return updated;
		} else if(!Pattern.matches("[0-9]+", phone) || phone.length() != 10){
			errorMessage("Phone number");
			return updated;
		} else if(!Pattern.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", email)){
			errorMessage("Email");
			return updated;
		}
		return updateCustomer(add1, add2, suburb, pCode, state, phone, email, customerId);
	}
	
	private static boolean updateCustomer(String add1, String add2, String suburb, String pCode, String state, String phone, String email, int customerId) {
		boolean updated = false;
		String qryUpdateCustomer = "update customers set addressLine1 = '" + add1 + "', addressLine2 = '" + add2 + "', suburb = '" + suburb + "', postCode = '" + pCode + "', state = '" + state + "', phone = '" + phone + "', email = '" + email + "' where customerId = " + customerId;
		
		System.out.println(qryUpdateCustomer);
		try{
			statement.executeUpdate(qryUpdateCustomer);
			updated = true;
		} catch(Exception ex){
			System.out.println("Customer not updated " + ex);
		}
		return updated;
	}

	public static void errorMessage(String variable){
		JOptionPane.showMessageDialog(new JPanel(), variable + " is incorrect. Please make sure it is in the right format", variable + " is invalid", JOptionPane.ERROR_MESSAGE);
	}


	public static int addCustomer(String fName, String lName, String add1, String add2, String suburb, String pCode, String state, String phone, String email){
		int customerId = -1;
		String qryAddCustomer = "insert into customers (fName, lName, addressLine1, addressLine2, suburb, postCode, state, email, phone) values " + "('" + fName + "'," + "'" + lName + "'," + "'" + add1 + "'," + "'" + add2 + "'," + "'" + suburb + "'," + "'" + pCode + "'," + "'" + state + "'," + "'" + email + "'," + "'" + phone + "')";
		try {
			statement.executeUpdate(qryAddCustomer);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// CustomerId of new customer
		String qryGetLastInsertId = "select last_insert_id() from customers";
		try {
			// Execute the query
			results = statement.executeQuery(qryGetLastInsertId);
		} catch (Exception ex) {
			// Catch any errors and display the error
			System.out.println("Error 1: " + ex);
		}
		
		try {
			while(results.next()){
				customerId = Integer.parseInt(results.getString("last_insert_id()"));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customerId;
	}
	
	public static int getCustomerId(String name){				
		int customerId = -1;
		int customerIndex = 0;
		
		ArrayList<String[]> customers = CustomerController.nameSearch(name);
		if(customers.isEmpty()){
			JOptionPane.showMessageDialog(new JFrame(), "No customers found with this name", "No customers find", JOptionPane.ERROR_MESSAGE);
			return customerId;
		} else if(customers.size() > 1){
			// Create an array that is used to show the list of customers with the same name
			String[] customerList = new String[customers.size()];
			for(int i = 0; i < customers.size(); i = i + 1){
				customerList[i] = CustomerController.getCustomerName(Integer.parseInt(customers.get(i)[3])) + " (" + customers.get(i)[2] + ")";
			}
			
			String customerSelected = (String)JOptionPane.showInputDialog(null, "Select customer", "Please select the customer you need.", JOptionPane.QUESTION_MESSAGE, null, customerList, customerList[0]); 
			boolean customerFound = false;
			int iteration = -1;
			while(!customerFound){
				iteration = iteration + 1;
				customerFound = customerSelected.equals(customerList[iteration]);
			}
			customerIndex = iteration;
			System.out.println("Combo box index is " + customerIndex);
			customerId = Integer.parseInt(customers.get(customerIndex)[3]);
		}
		return customerId;
	}
	
	public static String getCustomerName(int id){
		String qryGetCustomerName = "select fName, lName from customers where customerId = " + "'" + id + "'" + "LIMIT 1";
		
		String name = "Anonymous";
		
		try {
			// Execute the query
			results = statement.executeQuery(qryGetCustomerName);
		} catch (Exception ex) {
			// Catch any errors and display the error
			System.out.println("Error 1: " + ex);
		}
		
		try {
			while(results.next()){
					String fName = results.getString("fName");
					String lName = results.getString("lName");
					name = fName + " " + lName;
			}
		} catch (Exception e) {
			// Output an error in the console
			System.out.println("Error 2: " + e);
		}
		return name;
		
	
	}
	
	public static ArrayList<String[]> nameSearch(String name){
		ArrayList<String[]> customers = new ArrayList<String[]>();
		// Customer can be selected based on the name entered being their first name OR their surname OR their full name (split by spacing)
		String fName = "";
		String lName = "";
		if(name.contains(" ")){
			String[] fullName = name.split("\\s+");
			fName = fullName[0];
			lName = fullName[1];
			name = "";
		}
				
		String qryNameSearch = "select * from customers where fName = '" + name + "' OR fName = '" + fName + "' OR lName = '" + name + "' OR lName = '" + lName + "'";
		
		try{
			results = statement.executeQuery(qryNameSearch);
		} catch (Exception ex) {
			// Catch any errors and display the error
			System.out.println("Error 1: " + ex);
		}
		
		try{
			while(results.next()){
				String[] customer = new String[5];
				
				customer[0] = results.getString("fName");
				customer[1] = results.getString("lName");
				customer[2] = results.getString("email");
				customer[3] = results.getString("customerId");
				customer[4] = Integer.toString(getOutstandingPayments(Integer.parseInt(results.getString("customerId"))));
				
				customers.add(customer);
			}
		} catch (Exception e) {
			// Output an error in the console
			System.out.println("Error 2: " + e);
		}
		
		return customers;
	}
	

	public static ArrayList<String[]> emailSearch(String email){
		ArrayList<String[]> customers = new ArrayList<String[]>();
		
		String qryEmailSearch = "select customerId, fName, lName from customers where email = '" + email + "'";
		
		try{
			results = statement.executeQuery(qryEmailSearch);
		} catch (Exception ex) {
			// Catch any errors and display the error
			System.out.println("Error 1: " + ex);
		}
		
		try{
			while(results.next()){
				String[] customer = new String[5];
				
				customer[0] = results.getString("fName");
				customer[1] = results.getString("lName");
				customer[2] = email;
				customer[3] = results.getString("customerId");
				customer[4] = Integer.toString(getOutstandingPayments(Integer.parseInt(results.getString("customerId"))));
				
				customers.add(customer);
			}
		} catch (Exception e) {
			// Output an error in the console
			System.out.println("Error 2: " + e);
		}
		
		return customers;
	}
	
	private static int getOutstandingPayments(int customerId){
		int outstandingPayments = 0;
		
		String qryGetOutstandingLessonPayments = "select paymentMade from customerlessons where customerIdFK = " + customerId;
		
		try{
			lessonPaymentStatement = connection.createStatement();
			lessonPaymentResult = lessonPaymentStatement.executeQuery(qryGetOutstandingLessonPayments);
		} catch (Exception ex) {
			// Catch any errors and display the error
			System.out.println("Error 1: " + ex);
		}
		
		try{
			while(lessonPaymentResult.next()){
				if(Integer.parseInt(lessonPaymentResult.getString("paymentMade")) == 0){
					outstandingPayments = outstandingPayments + 1;
				}
			}
		} catch (Exception e) {
			// Output an error in the console
			System.out.println("Error 2: " + e);
		}
		
		String qryGetOutstandingFixturePayments = "select paymentMade from customerfixtures where customerIdFK = " + customerId;
		
		try{
			fixturePaymentStatement = connection.createStatement();
			fixturePaymentResult = fixturePaymentStatement.executeQuery(qryGetOutstandingFixturePayments);
		} catch (Exception ex) {
			// Catch any errors and display the error
			System.out.println("Error 1: " + ex);
		}
		
		try{
			while(fixturePaymentResult.next()){
				if(Integer.parseInt(fixturePaymentResult.getString("paymentMade")) == 0){
					outstandingPayments = outstandingPayments + 1;
				}
			}
		} catch (Exception e) {
			// Output an error in the console
			System.out.println("Error 2: " + e);
		}
		
		return outstandingPayments;
	}
	
	public static String[] getCustomer(int id){
		String[] customer = new String[9];
		String qryGetCustomer = "select * from customers where customerId = " + id;
		
		try{
			results = statement.executeQuery(qryGetCustomer);
		} catch (Exception ex){
			// Catch any errors and display the error
			System.out.println("Error 1: " + ex);
		}
		
		try{
			while(results.next()){
				customer[0] = results.getString("fName");
				customer[1] = results.getString("lName");
				customer[2] = results.getString("addressLine1");
				customer[3] = results.getString("addressLine2");
				customer[4] = results.getString("suburb");
				customer[5] = results.getString("postCode");
				customer[6] = results.getString("state");
				customer[7] = results.getString("email");
				customer[8] = results.getString("phone");
			}
		} catch (Exception ex){
			// Catch any errors and display the error
			System.out.println("Error 2: " + ex);
		}
		
		return customer;
	}
}
