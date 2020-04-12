package controller;

import java.awt.Desktop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class TransactionController extends DBConnect{
	
	private static ResultSet transactionList;
	private static ResultSet customerTransactionResult;
	private static ResultSet transactionDetailsResult;
	
	// Create a new statement and result set to avoid conflicts with the inherited variables
	private static Statement transactionSearchStatement;
	private static Statement customerTransactionStatement;
	private static Statement transactionDetailsStatement;
	
	private static NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
	
	public static int addTransaction(int customerId, double cost, int productId, int qtyLeft, Date date, String paymentMethod, String staffFName, int quantity){	
		int transactionId = -1;
		
		SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// Format the date so that it is entered as a dateTime in SQL
		String dateString = d.format(date);
		
		
		int staffId = StaffController.getStaffId(staffFName);
		
		String qryAddTransaction = "insert into transactions (customerIdFK, cost, productIdFK, time, paymentMethod, staffIdFK, quantity) values " + "('" + customerId + "'," + "'" + cost + "'," + "'" + productId + "',"  + "'" + dateString + "'," + "'" + paymentMethod + "'," + "'" + staffId + "', " + "'" + quantity + "')";

		try {
			// Execute the query
			statement.executeUpdate(qryAddTransaction);
			System.out.println("Transaction added");
		} catch (Exception ex) {
			// Catch any errors and display the error
			System.out.println("Error 1: " + ex);
		}
		
		ProductController.updateQuantity(qtyLeft, productId);
		
		// TransactionId of the transaction just added
		String qryGetTransactionId = "select last_insert_id() from transactions LIMIT 1";
		try {
			// Execute the query
			results = statement.executeQuery(qryGetTransactionId);
		} catch (Exception ex) {
			// Catch any errors and display the error
			System.out.println("Error 1: " + ex);
		}
		
		try {
			while(results.next()){
				transactionId = Integer.parseInt(results.getString("last_insert_id()"));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(ProductController.getCategory(productId).equals("Lesson")){
			LessonController.payForLesson(customerId, productId);
		} else if(ProductController.getCategory(productId).equals("Fixture")){
			FixtureController.payForFixture(customerId, productId);
		}
		
		return transactionId;
	}
	
	public static void saveReceipt(String[] transactionDetails){
		Document receipt = new Document();
		
		String fileFolder = Serialise.deserialise("receiptFile.ser");
		String fileName = null;
		// Options for user response
		Object[] options = {"Yes", "No"};
		
		int x = -1;
		
		int transactionId = Integer.parseInt(transactionDetails[0]);
		
		if(Serialise.deserialise("receiptFile.ser") == null){
			// If there is no default location set, ask the user to set a location 
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new java.io.File("."));
		    chooser.setDialogTitle("Select file location");
		    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		    // Disable the "all files" option
		    chooser.setAcceptAllFileFilterUsed(false);
		    int returnValue = chooser.showOpenDialog(null);
	        if (returnValue == JFileChooser.APPROVE_OPTION) {
	          File location = chooser.getSelectedFile();
	          fileName = location + "\\Receipt #" + transactionId + ".pdf";
	          fileFolder = location.toString();
	          Serialise.serialise(location.toString(), "receiptFile.ser");
	        }
		} else{
			// Create an option pane that asks the user if they would like to open the newly created PDF of the receipt
			x = JOptionPane.showOptionDialog(new JFrame(), "Would you like to store the receipt in " + fileFolder + "?", "Choose location", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		}
		
		
		// Default directory for receipts to be saved to
		
		if(x == 1){
			// Open a file chooser to select the directory for the receipt to be saved to 
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new java.io.File("."));
		    chooser.setDialogTitle("Select file location");
		    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		    // Disable the "all files" option
		    chooser.setAcceptAllFileFilterUsed(false);
		    int returnValue = chooser.showOpenDialog(null);
	        if (returnValue == JFileChooser.APPROVE_OPTION) {
	          File location = chooser.getSelectedFile();
	          fileName = location + "\\Receipt #" + transactionId + ".pdf";
	          System.out.println(fileName);
	          Serialise.serialise(location.toString(), "receiptFile.ser");
	       }
		} else{
			fileName = fileFolder + "\\Receipt #" + transactionId + ".pdf";
		}
		
		try {
			PdfWriter.getInstance(receipt, new FileOutputStream(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		receipt.open();
		// Add metadata to file
		receipt.addTitle("Transaction receipt");
		
		// Create table with two columns
		PdfPTable table = new PdfPTable(2);
		
		table.addCell("Customer name");
		table.addCell(transactionDetails[2]);
		
		table.addCell("Date");
		table.addCell(transactionDetails[3]);
		
		table.addCell("Product");
		table.addCell(transactionDetails[4]);
		
		table.addCell("Quantity");
		table.addCell(transactionDetails[5]);
		
		table.addCell("Amount");
		table.addCell(currencyFormatter.format(Double.parseDouble(transactionDetails[6])));
		
		table.addCell("Payment method");
		table.addCell(transactionDetails[7]);
		
		table.addCell("Served by");
		table.addCell(transactionDetails[8]);
		
		table.addCell("Transaction number");
		table.addCell(Integer.toString(transactionId));
		
		try {
			receipt.add(table);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		receipt.close();
		
		
		// Create an option pane that asks the user if they would like to open the newly created PDF of the receipt
		int n = JOptionPane.showOptionDialog(new JFrame(), "Would you like to open the receipt?", "Open receipt?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		
		if(n == 0){
			// Open the receipt
	        File file = new File(fileName);
	        Desktop desktop = Desktop.getDesktop();
	        if(file.exists())
				try {
					desktop.open(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	
	public static ArrayList<String[]> dateSearch(Date from, Date to){
		ArrayList<String[]> transactions = new ArrayList<String[]>();
		
		SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateFrom = d.format(from);
		String dateTo = d.format(to);
		
		String qryGetTransactionsByDate = "Select * from transactions where time between '" + dateFrom + "' and '" + dateTo + "'";
		
		try {
			transactionSearchStatement = connection.createStatement();
			transactionList = transactionSearchStatement.executeQuery(qryGetTransactionsByDate);
		} catch (Exception ex){
			System.out.println("Error 1: " + ex);
		}
		
		// Format of the dates in the database
		DateFormat databaseFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");
				
		// Format to be shown in table
		DateFormat newFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		try{
			while(transactionList.next()){
				String[] transaction = new String[6];
				// Transaction number, date, customer, product, amount, payment method
				transaction[0] = transactionList.getString("transactionId");
				Date date = databaseFormat.parse(transactionList.getString("time"));
				
				transaction[1] = newFormat.format(date);
				int customerId = Integer.parseInt(transactionList.getString("customerIdFK"));
				transaction[2] = CustomerController.getCustomerName(customerId);
				
				int productId = Integer.parseInt(transactionList.getString("productIdFK"));
				transaction[3] = ProductController.getProductName(productId);
				
				transaction[4] = transactionList.getString("cost");
				String paymentMethod = transactionList.getString("paymentMethod");
				switch(paymentMethod){
				case "CASH": paymentMethod = "Cash";
							break;
				case "CARD": paymentMethod = "Card";
							break;
				case "CHEQ": paymentMethod = "Cheque";
				}
				transaction[5] = paymentMethod;
				
							
				// Add the String array to the ArrayList
				transactions.add(transaction);
				
			}
		} catch(Exception ex){
			System.out.println("Error 2: " + ex);
		}
		
		return transactions;
	}
	
	public static ArrayList<String[]> customerSearch(String customerName){
		ArrayList<String[]> transactions = new ArrayList<String[]>();
		
		int customerId = CustomerController.getCustomerId(customerName);
		
		String qryGetTransactionsByCustomer = "Select * from transactions where customerIdFK = " + customerId;
		
		try {
			transactionSearchStatement = connection.createStatement();
			// Execute the query
			transactionList = transactionSearchStatement.executeQuery(qryGetTransactionsByCustomer);
		} catch (Exception ex) {
			// Catch any errors and display the error
			System.out.println("Error 1: " + ex);
		}
		
		// Format of the dates in the database
		DateFormat databaseFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");
		
		// Format to be shown in table
		DateFormat newFormat = new SimpleDateFormat("dd/MM/yyyy"); 
		
		try {
			while(transactionList.next()){
				String[] transaction = new String[6];
				
				// Transaction number, date, customer, product, amount, payment method
				// Add each piece of data from each row into a String array
				// Convert the date from the database to a Java date
				Date date = databaseFormat.parse(transactionList.getString("time"));
				// Format the date to be shown in the table
				transaction[0] = transactionList.getString("transactionId");
				transaction[1] = newFormat.format(date);

				transaction[2] = CustomerController.getCustomerName(Integer.parseInt(transactionList.getString("customerIdFK")));
				// Get the product ID
				int productId = Integer.parseInt(transactionList.getString("productIdFK"));
				
				// Show the name of the product
				transaction[3] = ProductController.getProductName(productId);
				transaction[4] = transactionList.getString("cost");
				String paymentMethod = transactionList.getString("paymentMethod");
				switch(paymentMethod){
				case "CASH": paymentMethod = "Cash";
							break;
				case "CARD": paymentMethod = "Card";
							break;
				case "CHEQ": paymentMethod = "Cheque";
				}
				transaction[5] = paymentMethod;
				
							
				// Add the String array to the ArrayList
				transactions.add(transaction);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return transactions;
	}
	
	
	public static ArrayList<String[]> productSearch(String product){
		ArrayList<String[]> transactions = new ArrayList<String[]>();
		
		// Get the productId of the product based on its name
		int productId = ProductController.getProductId(product);
		
		// Query to get transaction details
		String qryGetTransactionsByProduct = "Select * from transactions where productIdFK = " + productId;
		
		
		try {
			transactionSearchStatement = connection.createStatement();
			// Execute the query
			transactionList = transactionSearchStatement.executeQuery(qryGetTransactionsByProduct);
		} catch (Exception ex) {
			// Catch any errors and display the error
			System.out.println("Error 1: " + ex);
		}
		
		// Format of the dates in the database
		DateFormat databaseFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");
		
		// Format to be shown in table
		DateFormat newFormat = new SimpleDateFormat("dd/MM/yyyy"); 
		
		try {
			while(transactionList.next()){
				String[] transaction = new String[6];
				
				// Transaction number, date, customer, product, amount, payment method
				transaction[0] = transactionList.getString("transactionId");
				// Add each piece of data from each row into a String array
				// Convert the date from the database to a Java date
				Date date = databaseFormat.parse(transactionList.getString("time"));
				// Format the date to be shown in the table
				transaction[1] = newFormat.format(date);
				
				// Get the customerId from the row in the transaction database table
				int customerId = Integer.parseInt(transactionList.getString("customerIdFK"));
				
				// Get the customer name using the customerId from the row in the transaction database table
				String customerName = CustomerController.getCustomerName(customerId);
				
				// Add the customerName to the transaction array
				transaction[2] = customerName;
				
				// Add the productName to the transaction array
				transaction[3] = product;
				
				// Add the cost to the transaction array
				transaction[4] = transactionList.getString("cost");
				
				
				String paymentMethod = transactionList.getString("paymentMethod");
				switch(paymentMethod){
				case "CASH": paymentMethod = "Cash";
							break;
				case "CARD": paymentMethod = "Card";
							break;
				case "CHEQ": paymentMethod = "Cheque";
				}
				
				// Add the reformatted paymentMethod to the transaction array
				transaction[5] = paymentMethod;
				
							
				// Add the transaction array to the transactions ArrayList
				transactions.add(transaction);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return transactions;
	}
	
	public static ArrayList<String[]> getCustomerTransactions(int customerId){
		ArrayList<String[]> transactions = new ArrayList<String[]>();
		
		SimpleDateFormat databaseFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");
		SimpleDateFormat newDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
		
		String qryGetCustomerTransactions = "select productIdFK, paymentMethod, time, cost, transactionId from transactions where customerIdFK = " + customerId + " order by time desc limit 10";
		
		try{
			customerTransactionStatement = connection.createStatement();
			customerTransactionResult = customerTransactionStatement.executeQuery(qryGetCustomerTransactions);
		} catch(Exception ex){
			System.out.println("Error 1: " + ex);
		}
		
		try{
			while(customerTransactionResult.next()){
				String[] transaction = new String[6];
				// Product, date, cost, payment method, transaction number
				
				// Add the product name to the array based on the productId stored in the transaction row
				transaction[0] = ProductController.getProductName(Integer.parseInt(customerTransactionResult.getString("productIdFK")));
				
				// Add the product category to the array based on the productId stored in the transaction row
				transaction[1] = ProductController.getCategory(Integer.parseInt(customerTransactionResult.getString("productIdFK")));
				
				// Convert the string date stored in the database to a Java date
				Date date = databaseFormat.parse(customerTransactionResult.getString("time"));
				transaction[2] = newDateFormat.format(date);
				
				// Add the cost of the transaction to the array
				transaction[3] = "$" + customerTransactionResult.getString("cost");
				
				// Format the payment method string
				String paymentMethod = customerTransactionResult.getString("paymentMethod");
				switch(paymentMethod){
				case "CASH": paymentMethod = "Cash";
							break;
				case "CARD": paymentMethod = "Card";
							break;
				case "CHEQ": paymentMethod = "Cheque";
							break;
				}
				// Add the payment method to the array
				transaction[4] = paymentMethod;
				
				// Add the transactionId to the array
				transaction[5] = customerTransactionResult.getString("transactionId");
				
				// Add the transaction array to the transactions ArrayList
				transactions.add(transaction);
			}
		} catch (Exception ex){
			System.out.println("Error in getCustomerTransactions(): " + ex);
		}
				
		return transactions;
	}
	
	public static String[] getTransactionDetails(int transactionId){
		// TransactionId, customerId, customerName, date, productName, quantity, cost, paymentMethod, staffName
		String[] transactionDetails = new String[9];
		
		String qryGetTransactionDetails = "select * from transactions where transactionId = " + transactionId;
		
		try{
			transactionDetailsStatement = connection.createStatement();
			transactionDetailsResult = transactionDetailsStatement.executeQuery(qryGetTransactionDetails);
		} catch(Exception ex){
			System.out.println("Transaction details result set is not found");
		}
		
		try{
			while(transactionDetailsResult.next()){
				transactionDetails[0] = Integer.toString(transactionId);
				int customerId = Integer.parseInt(transactionDetailsResult.getString("customerIdFK"));
				transactionDetails[1] = Integer.toString(customerId); 
				transactionDetails[2] = CustomerController.getCustomerName(customerId);
				transactionDetails[3] = transactionDetailsResult.getString("time");
				int productId = Integer.parseInt(transactionDetailsResult.getString("productIdFK"));
				transactionDetails[4] = ProductController.getProductName(productId);
				transactionDetails[5] = transactionDetailsResult.getString("quantity");
				transactionDetails[6] = transactionDetailsResult.getString("cost");
				String paymentMethod = transactionDetailsResult.getString("paymentMethod");
				
				switch(paymentMethod){
				case "CASH": paymentMethod = "Cash";
							break;
				case "CARD": paymentMethod = "Card";
							break;
				case "CHEQ": paymentMethod = "Cheque";
				}
				transactionDetails[7] = paymentMethod;
				int staffId = Integer.parseInt(transactionDetailsResult.getString("staffIdFK"));
				transactionDetails[8] = StaffController.getStaffName(staffId);
				}
			} catch(Exception ex){
				System.out.println("Transaction details not retrieved because " + ex);
			}		
		return transactionDetails;
		}

	public static void createCSV(ArrayList<String[]> transactions, String name) {
			
		String fileFolder = Serialise.deserialise("transactionLocation.ser");
		String fileName = null;
		// Options for user response
		Object[] options = {"Yes", "No"};
		
		int x = -1;
			
		if(Serialise.deserialise("transactionLocation.ser") == null){
			// If there is no default location set, ask the user to set a location 
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new java.io.File("."));
		    chooser.setDialogTitle("Select file location");
		    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		    // Disable the "all files" option
		    chooser.setAcceptAllFileFilterUsed(false);
		    int returnValue = chooser.showOpenDialog(null);
	        if (returnValue == JFileChooser.APPROVE_OPTION) {
	          File location = chooser.getSelectedFile();
	          fileFolder = location.toString();
	          fileName = fileFolder + "\\Transactions for " + name + ".csv";
	          System.out.println("The file name is " + fileName);
	          Serialise.serialise(fileFolder, "transactionLocation.ser");
	        }
		} else{
			// Create an option pane that asks the user if they would like to open the newly created PDF of the receipt
			x = JOptionPane.showOptionDialog(new JFrame(), "Would you like to store the file in " + fileFolder + "?", "Choose location", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		}
		
		
		if(x == 1){
			// Open a file chooser to select the directory for the receipt to be saved to 
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new java.io.File("."));
		    chooser.setDialogTitle("Select file location");
		    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		    // Disable the "all files" option
		    chooser.setAcceptAllFileFilterUsed(false);
		    int returnValue = chooser.showOpenDialog(null);
	        if (returnValue == JFileChooser.APPROVE_OPTION) {
	        	File location = chooser.getSelectedFile();
	        	fileFolder = location.toString();
	        	fileName = fileFolder + "\\Transactions for " + name + ".csv";
		        System.out.println(fileName);
		        Serialise.serialise(fileFolder, "transactionLocation.ser");
	       }
		} else{
			fileName = fileFolder + "\\Transactions for " + name + ".csv";
			System.out.println(fileName);
		}
		
		try {
			FileWriter writer = new FileWriter(fileName);
			
			// Date, name, product, amount, payment method, transaction number
			
			String[][] transactionArray = transactions.toArray(new String[0][0]);
			writer.append("Transaction number");
			writer.append(',');
			writer.append("Date");
			writer.append(',');
		    writer.append("Customer name");
		    writer.append(',');
		    writer.append("Product");
		    writer.append(',');
		    writer.append("Amount");
		    writer.append(',');
		    writer.append("Payment method");
		    writer.append('\n');
			
			for(int i = 0; i < transactionArray.length; i = i + 1){
				writer.append(transactionArray[i][0]);
				writer.append(',');
				writer.append(transactionArray[i][1]);
				writer.append(',');
				writer.append(transactionArray[i][2]);
				writer.append(',');
				writer.append(transactionArray[i][3]);
				writer.append(',');
				writer.append(transactionArray[i][4]);
				writer.append(',');
				writer.append(transactionArray[i][5]);
				writer.append('\n');
			}
			
			writer.flush();
		    writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		

		// Create an option pane that asks the user if they would like to open the newly created CSV
		int n = JOptionPane.showOptionDialog(new JFrame(), "Would you like to open the file?", "Open file?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		
		if(n == 0){
			// Open the receipt
	        File file = new File(fileName);
	        Desktop desktop = Desktop.getDesktop();
	        if(file.exists())
				try {
					desktop.open(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	
	public static void createCSV(ArrayList<String[]> transactions, Date from, Date to) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		
		String fileFolder = Serialise.deserialise("transactionLocation.ser");
		String fileName = null;
		// Options for user response
		Object[] options = {"Yes", "No"};
		
		int x = -1;
			
		if(Serialise.deserialise("transactionLocation.ser") == null){
			// If there is no default location set, ask the user to set a location 
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new java.io.File("."));
		    chooser.setDialogTitle("Select file location");
		    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		    // Disable the "all files" option
		    chooser.setAcceptAllFileFilterUsed(false);
		    int returnValue = chooser.showOpenDialog(null);
	        if (returnValue == JFileChooser.APPROVE_OPTION) {
	          File location = chooser.getSelectedFile();
	          fileFolder = location.toString();
	          fileName = fileFolder + "\\Transactions between " + dateFormat.format(from) + " to " + dateFormat.format(to) + ".csv";
	          System.out.println("The file name is " + fileName);
	          Serialise.serialise(fileFolder, "transactionLocation.ser");
	        }
		} else{
			// Create an option pane that asks the user if they would like to open the newly created PDF of the receipt
			x = JOptionPane.showOptionDialog(new JFrame(), "Would you like to store the file in " + fileFolder + "?", "Choose location", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		}
		
		
		if(x == 1){
			// Open a file chooser to select the directory for the receipt to be saved to 
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new java.io.File("."));
		    chooser.setDialogTitle("Select file location");
		    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		    // Disable the "all files" option
		    chooser.setAcceptAllFileFilterUsed(false);
		    int returnValue = chooser.showOpenDialog(null);
	        if (returnValue == JFileChooser.APPROVE_OPTION) {
	        	File location = chooser.getSelectedFile();
	        	fileFolder = location.toString();
	        	fileName = fileFolder + "\\Transactions between " + dateFormat.format(from) + " to " + dateFormat.format(to) + ".csv";
		        System.out.println(fileName);
		        Serialise.serialise(fileFolder, "transactionLocation.ser");
	       }
		} else{
			fileName = fileFolder + "\\Transactions between " + dateFormat.format(from) + " to " + dateFormat.format(to) + ".csv";
			System.out.println(fileName);
		}
		
		try {
			FileWriter writer = new FileWriter(fileName);
			
			// Date, name, product, amount, payment method, transaction number
			
			String[][] transactionArray = transactions.toArray(new String[0][0]);
			writer.append("Transaction number");
			writer.append(',');
			writer.append("Date");
			writer.append(',');
		    writer.append("Customer name");
		    writer.append(',');
		    writer.append("Product");
		    writer.append(',');
		    writer.append("Amount");
		    writer.append(',');
		    writer.append("Payment method");
		    writer.append('\n');
			
			for(int i = 0; i < transactionArray.length; i = i + 1){
				writer.append(transactionArray[i][0]);
				writer.append(',');
				writer.append(transactionArray[i][1]);
				writer.append(',');
				writer.append(transactionArray[i][2]);
				writer.append(',');
				writer.append(transactionArray[i][3]);
				writer.append(',');
				writer.append(transactionArray[i][4]);
				writer.append(',');
				writer.append(transactionArray[i][5]);
				writer.append('\n');
			}
			
			writer.flush();
		    writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		

		// Create an option pane that asks the user if they would like to open the newly created CSV
		int n = JOptionPane.showOptionDialog(new JFrame(), "Would you like to open the file?", "Open file?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		
		if(n == 0){
			// Open the receipt
	        File file = new File(fileName);
	        Desktop desktop = Desktop.getDesktop();
	        if(file.exists())
				try {
					desktop.open(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
}
