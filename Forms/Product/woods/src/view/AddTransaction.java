package view;

import javax.swing.JPanel;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import com.toedter.calendar.JDateChooser;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ItemListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.awt.event.ItemEvent;

import controller.CustomerController;
import controller.FixtureController;
import controller.Initialise;
import controller.LessonController;
import controller.ProductController;
import controller.StaffController;
import view.Receipt;
import controller.TransactionController;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JCheckBox;

public class AddTransaction extends JPanel {
	private JTextField txtCustomer;
	private JTextField txtQuantity;
	private JTextField txtCost;
	// Variable for today's date
	private Date today = new Date();
	private String[] categories = {"", "Equipment", "Food/Drink", "Lesson", "Fixture", "Clothing"};
	// Combobox for products
	private JComboBox cmbProduct;
	// Array for products
	private String[] products;
	
	private String[][] productList;
	
	private double cost;
	
	private String[] paymentMethods = {"Cash", "Card", "Cheque"};
	
	private String customer;
	private JDateChooser dateChooser;
	private JComboBox cmbCategory;
	private JComboBox cmbPaymentMethod;
	private DefaultComboBoxModel productModel = new DefaultComboBoxModel();
	/**
	 * Create the panel.
	 */
	public AddTransaction() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{126, 204, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblInstructions = new JLabel("Please complete the following form to add a transaction.");
		GridBagConstraints gbc_lblInstructions = new GridBagConstraints();
		gbc_lblInstructions.gridwidth = 2;
		gbc_lblInstructions.insets = new Insets(0, 0, 5, 0);
		gbc_lblInstructions.anchor = GridBagConstraints.WEST;
		gbc_lblInstructions.gridx = 0;
		gbc_lblInstructions.gridy = 0;
		add(lblInstructions, gbc_lblInstructions);
		
		JLabel lblDate = new JLabel("Date");
		GridBagConstraints gbc_lblDate = new GridBagConstraints();
		gbc_lblDate.anchor = GridBagConstraints.WEST;
		gbc_lblDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblDate.gridx = 0;
		gbc_lblDate.gridy = 2;
		add(lblDate, gbc_lblDate);
		
		dateChooser = new JDateChooser();
		// Set the date chooser to today's date by default
		dateChooser.setDate(today);
		GridBagConstraints gbc_dateChooser = new GridBagConstraints();
		gbc_dateChooser.insets = new Insets(0, 0, 5, 0);
		gbc_dateChooser.fill = GridBagConstraints.BOTH;
		gbc_dateChooser.gridx = 1;
		gbc_dateChooser.gridy = 2;
		add(dateChooser, gbc_dateChooser);
		
		JCheckBox chckbxAnonymousCustomer = new JCheckBox("Customer is not registered");
		chckbxAnonymousCustomer.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(chckbxAnonymousCustomer.isSelected()){
					txtCustomer.setText("Non-registered customer");
					txtCustomer.setEditable(false);
					customer = "Anonymous";
				} else if(!chckbxAnonymousCustomer.isSelected()){
					txtCustomer.setText("");
					txtCustomer.setEditable(true);
					customer = null;
				}
			}
		});
		GridBagConstraints gbc_chckbxAnonymousCustomer = new GridBagConstraints();
		gbc_chckbxAnonymousCustomer.anchor = GridBagConstraints.WEST;
		gbc_chckbxAnonymousCustomer.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxAnonymousCustomer.gridx = 1;
		gbc_chckbxAnonymousCustomer.gridy = 3;
		add(chckbxAnonymousCustomer, gbc_chckbxAnonymousCustomer);
		
		JLabel lblCustomer = new JLabel("Customer name");
		GridBagConstraints gbc_lblCustomer = new GridBagConstraints();
		gbc_lblCustomer.insets = new Insets(0, 0, 5, 5);
		gbc_lblCustomer.anchor = GridBagConstraints.WEST;
		gbc_lblCustomer.gridx = 0;
		gbc_lblCustomer.gridy = 4;
		add(lblCustomer, gbc_lblCustomer);
		
		txtCustomer = new JTextField();
		GridBagConstraints gbc_txtCustomer = new GridBagConstraints();
		gbc_txtCustomer.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCustomer.insets = new Insets(0, 0, 5, 0);
		gbc_txtCustomer.anchor = GridBagConstraints.NORTH;
		gbc_txtCustomer.gridx = 1;
		gbc_txtCustomer.gridy = 4;
		add(txtCustomer, gbc_txtCustomer);
		txtCustomer.setColumns(10);
		
		JLabel lblCategory = new JLabel("Product category");
		GridBagConstraints gbc_lblCategory = new GridBagConstraints();
		gbc_lblCategory.insets = new Insets(0, 0, 5, 5);
		gbc_lblCategory.anchor = GridBagConstraints.WEST;
		gbc_lblCategory.gridx = 0;
		gbc_lblCategory.gridy = 5;
		add(lblCategory, gbc_lblCategory);
		
		cmbCategory = new JComboBox(categories);
		cmbCategory.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				cmbCategoryItemStateChanged(e);
			}
		});
		GridBagConstraints gbc_cmbCategory = new GridBagConstraints();
		gbc_cmbCategory.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbCategory.insets = new Insets(0, 0, 5, 0);
		gbc_cmbCategory.gridx = 1;
		gbc_cmbCategory.gridy = 5;
		add(cmbCategory, gbc_cmbCategory);
		
		JLabel lblProduct = new JLabel("Product");
		GridBagConstraints gbc_lblProduct = new GridBagConstraints();
		gbc_lblProduct.insets = new Insets(0, 0, 5, 5);
		gbc_lblProduct.anchor = GridBagConstraints.WEST;
		gbc_lblProduct.gridx = 0;
		gbc_lblProduct.gridy = 6;
		add(lblProduct, gbc_lblProduct);
		
		cmbProduct = new JComboBox();
		GridBagConstraints gbc_cmbProduct = new GridBagConstraints();
		gbc_cmbProduct.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbProduct.insets = new Insets(0, 0, 5, 0);
		gbc_cmbProduct.gridx = 1;
		gbc_cmbProduct.gridy = 6;
		add(cmbProduct, gbc_cmbProduct);
		
		JLabel lblQuantity = new JLabel("Quantity");
		GridBagConstraints gbc_lblQuantity = new GridBagConstraints();
		gbc_lblQuantity.anchor = GridBagConstraints.WEST;
		gbc_lblQuantity.insets = new Insets(0, 0, 5, 5);
		gbc_lblQuantity.gridx = 0;
		gbc_lblQuantity.gridy = 7;
		add(lblQuantity, gbc_lblQuantity);
		
		txtCost = new JTextField();
		txtCost.setEditable(false);
		GridBagConstraints gbc_txtCost = new GridBagConstraints();
		gbc_txtCost.anchor = GridBagConstraints.NORTH;
		gbc_txtCost.insets = new Insets(0, 0, 5, 0);
		gbc_txtCost.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCost.gridx = 1;
		gbc_txtCost.gridy = 8;
		add(txtCost, gbc_txtCost);
		txtCost.setColumns(10);
		
		txtQuantity = new JTextField();
		txtQuantity.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				int productIndex = cmbProduct.getSelectedIndex();
				String quantityInput = txtQuantity.getText();
				
				// Make sure the quantity entered is a number
				if(quantityInput.matches("[-+]?\\d*\\.?\\d+")){
					System.out.println("Quantity is a number");
					int quantity = Integer.parseInt(quantityInput);
					int inStock = Integer.parseInt(productList[productIndex][3]); 
					if(quantity > inStock){
						// Show an error message and reset the quantity if the user tries to enter a quantity greater than the quantity in stock
						Runnable resetQuantityBox = new Runnable(){
							@Override
							public void run(){
								JOptionPane.showMessageDialog(new JPanel(), "There are only " + inStock + " in stock.", "Not enough of this product in stock", JOptionPane.ERROR_MESSAGE);
								txtQuantity.setText("");
								txtCost.setText("");
							}
						};
						SwingUtilities.invokeLater(resetQuantityBox); 
					}
					cost = quantity * Double.parseDouble(productList[productIndex][2]);
					
					// Object to display cost variable as a currency value
					NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
					
					txtCost.setText(currencyFormatter.format(cost));
				} else{
					JOptionPane.showMessageDialog(new JPanel(), "Please enter a numerical quantity.", "Invalid quantity", JOptionPane.ERROR_MESSAGE);
				}
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				
			}
		});
		GridBagConstraints gbc_txtQuantity = new GridBagConstraints();
		gbc_txtQuantity.insets = new Insets(0, 0, 5, 0);
		gbc_txtQuantity.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtQuantity.anchor = GridBagConstraints.NORTH;
		gbc_txtQuantity.gridx = 1;
		gbc_txtQuantity.gridy = 7;
		add(txtQuantity, gbc_txtQuantity);
		txtQuantity.setColumns(10);
		
		JLabel lblCost = new JLabel("Cost");
		GridBagConstraints gbc_lblCost = new GridBagConstraints();
		gbc_lblCost.anchor = GridBagConstraints.WEST;
		gbc_lblCost.insets = new Insets(0, 0, 5, 5);
		gbc_lblCost.gridx = 0;
		gbc_lblCost.gridy = 8;
		add(lblCost, gbc_lblCost);
		
		
		JLabel lblPaymentMethod = new JLabel("Payment method");
		GridBagConstraints gbc_lblPaymentMethod = new GridBagConstraints();
		gbc_lblPaymentMethod.insets = new Insets(0, 0, 5, 5);
		gbc_lblPaymentMethod.anchor = GridBagConstraints.WEST;
		gbc_lblPaymentMethod.gridx = 0;
		gbc_lblPaymentMethod.gridy = 9;
		add(lblPaymentMethod, gbc_lblPaymentMethod);
		
		cmbPaymentMethod = new JComboBox(paymentMethods);
		GridBagConstraints gbc_cmbPaymentMethod = new GridBagConstraints();
		gbc_cmbPaymentMethod.insets = new Insets(0, 0, 5, 0);
		gbc_cmbPaymentMethod.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbPaymentMethod.gridx = 1;
		gbc_cmbPaymentMethod.gridy = 9;
		add(cmbPaymentMethod, gbc_cmbPaymentMethod);
		
		JButton btnSubmit = new JButton("Submit and print receipt");
		btnSubmit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String quantityString = txtQuantity.getText();
				
				// Check if a quantity has been entered before adding the transaction
				if(!quantityString.equals("")){
					// Get the index of the product for the productList array
					int productIndex = cmbProduct.getSelectedIndex();
					
					// Get the productId based on the productId stored in the 2D array called productList
					int productId = Integer.parseInt(productList[productIndex][1]);
					
					// Set the product's name
					String product = productList[productIndex][0];
					
					// Quantity remaining in stock
					int quantity = Integer.parseInt(quantityString);
					int qty = (Integer.parseInt(productList[productIndex][3]) - quantity);
					
					// Set the default customerId to -1
					int customerId = -1;
					
					// Check if the customer has been set to anonymous
					if(customer == null){
						customer = txtCustomer.getText();
						
						// Get the customer's id based on their name
						customerId = CustomerController.getCustomerId(customer);
					} else{
						// An anonymous customer has a customerId of 0
						customerId = 0;
					}
					Date date = dateChooser.getDate();
					int paymentMethodIndex = cmbPaymentMethod.getSelectedIndex();
					
					// Get the name of the staff member that is currently logged in
					String staffFName = StaffController.getStaffFName();
					
					// Default payment method is cash
					String paymentMethod = "CASH";
					
					// Convert the payment method index to a string for the payment method
					switch (paymentMethodIndex) {
						case 0: paymentMethod = "CASH";
								break;
						case 1: paymentMethod = "CARD";
								break;
						case 2: paymentMethod = "CHEQ";
					}
					
					// Add the transaction if a customer has been set for the transaction
					if(customerId != -1){
						int transactionId = TransactionController.addTransaction(customerId, cost, productId, qty, date, paymentMethod, staffFName, quantity);
					
					
						// Change the payment method back into a standard form before displaying the receipt
						switch(paymentMethod){
						case "CASH": paymentMethod = "Cash";
									break;
						case "CARD": paymentMethod = "Card";
									break;
						case "CHEQ": paymentMethod = "Cheque";
						}
						
						// Create and show a JFrame for the receipt
						Receipt receipt = new Receipt(transactionId);
						receipt.setVisible(true);
					}
				} else{
					// Show an error if no quantity is entered
					JOptionPane.showMessageDialog(new JPanel(), "You must enter a quantity before adding a transaction", "Invalid quantity", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		GridBagConstraints gbc_btnSubmit = new GridBagConstraints();
		gbc_btnSubmit.anchor = GridBagConstraints.EAST;
		gbc_btnSubmit.gridx = 1;
		gbc_btnSubmit.gridy = 12;
		add(btnSubmit, gbc_btnSubmit);

	}
	
	// Method to set the product list based on the category chosen
	private void cmbCategoryItemStateChanged(java.awt.event.ItemEvent evt) {                                                     

	    if (java.awt.event.ItemEvent.SELECTED == evt.getStateChange()) {
	    	
	    	// Get the category selected in cmbCategory
			String categorySelected = evt.getItem().toString();
			
			// Query for the list of products that are in the category selected
			// Store the ArrayList of products from the method as a String array
			// Parameter of new String[0] converts the ArrayList to a String[]
			productList = ProductController.getProducts(categorySelected).toArray(new String[0][0]);
			
			System.out.println("The productList array is " + Arrays.deepToString(productList));
			// Make a new array containing only the product names
			String[] productNames = new String[productList.length];
			
			// Go to the first column of each row, which contains the name of each product
			for(int i = 0; i < productNames.length; i = i + 1){
				// Only show lessosn and fixtures in the future
				if(categorySelected.equals("Lesson")){
					int id = Integer.parseInt(productList[i][1]);
					String[] lessonDetails = LessonController.getLessonDetails(id);
					double term = Double.parseDouble(lessonDetails[3]);
					int year = Integer.parseInt(lessonDetails[4]);
					if(term >= Initialise.getCurrentTerm() && year >= Calendar.getInstance().YEAR){
						productNames[i] = productList[i][0];
					}
				} else if(categorySelected.equals("Fixture")) {
					int id = Integer.parseInt(productList[i][1]);
					String[] fixtureDetails = FixtureController.getFixtureInfo(id);
					double term = Double.parseDouble(fixtureDetails[3]);
					int year = Integer.parseInt(fixtureDetails[4]);
					if(term >= Initialise.getCurrentTerm() && year >= Calendar.getInstance().YEAR){
						productNames[i] = productList[i][0];
					}
				} else{
					productNames[i] = productList[i][0];
				}
				
			}
			// Set the list of items in cmbProduct to the products in the category selected
			productModel = new DefaultComboBoxModel(productNames);
			cmbProduct.setModel(productModel); 
			
			// If the category is a lesson or fixture, the quantity must be 1
			if(categorySelected.equals("Lesson") || categorySelected.equals("Fixture")){
				txtQuantity.setText("1");
				txtQuantity.setEditable(false);
			}
	    }
	}
	
	public void clearForm(){
		txtCustomer.setText("");
		txtQuantity.setText("");
		txtCost.setText("");
		cmbCategory.setSelectedIndex(0);
		productModel = new DefaultComboBoxModel();
		cmbProduct.setModel(productModel); 
		dateChooser.setDate(today);
	}
	
	private void resetQuantityBox(){
		txtQuantity.setText("");
	}
}
