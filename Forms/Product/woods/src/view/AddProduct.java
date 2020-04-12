package view;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

import controller.Initialise;
import controller.ProductController;
import controller.StaffController;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;

import javax.swing.JSpinner;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JYearChooser;

public class AddProduct extends JPanel {
	private JTextField txtProduct;
	private JTextField txtQuantity;
	private JTextField txtPrice;
	
	JLabel lblQuantity = new JLabel("Quantity in stock");

	private String[] categories = {"", "Equipment", "Food/Drink", "Lesson", "Fixture", "Clothing"};
	
	// Lesson/Fixture specific fields
	private JTextField txtDuration = new JTextField();
	private JDateChooser dtePayment = new JDateChooser();
	private JSpinner timeSpinner = new JSpinner(new SpinnerDateModel());
	private String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
	private JComboBox cmbDay = new JComboBox(days);
	private String[] locations = {"Albany Hills State School", "Albany Creek State School", "All Saints", "St Dympna's", "Aspley East State School"};
	private JComboBox cmbLocation = new JComboBox(locations);
	private JTextField txtCoach = new JTextField(StaffController.getStaffFName());
	private JYearChooser yrYear = new JYearChooser();
	private String[] terms = {"1", "Easter holiday", "2", "Winter holiday", "3", "Spring holiday", "4", "Summer holiday"};
	private JComboBox cmbTerm = new JComboBox(terms);
	private JComboBox cmbCategory;
	private String[][] staffList = StaffController.getStaffList().toArray(new String[0][0]);
	private JComboBox cmbStaff = new JComboBox();
	private JSpinner.DateEditor timeEditor;
	
	private JLabel lblYear = new JLabel("Year");
	private JLabel lblTerm = new JLabel("Term");
	private JLabel lblDatePaymentDue = new JLabel("Date payment due");
	private JLabel lblDuration = new JLabel("Duration");
	private JLabel lblTime = new JLabel("Time");
	private JLabel lblLocation = new JLabel("Location");
	private JLabel lblCoach = new JLabel("Coach");
	private JLabel lblDay = new JLabel("Day");
	
	// Current year for limits of year picker
	Calendar now = Calendar.getInstance();
	int thisYear = now.get(Calendar.YEAR);
	/**
	 * Create the panel.
	 */
	public AddProduct() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{109, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblPleaseCompleteThe = new JLabel("Please complete the form below to add a new product.");
		GridBagConstraints gbc_lblPleaseCompleteThe = new GridBagConstraints();
		gbc_lblPleaseCompleteThe.gridwidth = 2;
		gbc_lblPleaseCompleteThe.insets = new Insets(0, 0, 5, 0);
		gbc_lblPleaseCompleteThe.gridx = 0;
		gbc_lblPleaseCompleteThe.gridy = 0;
		add(lblPleaseCompleteThe, gbc_lblPleaseCompleteThe);
		
		JLabel lblProductName = new JLabel("Product name");
		GridBagConstraints gbc_lblProductName = new GridBagConstraints();
		gbc_lblProductName.insets = new Insets(0, 0, 5, 5);
		gbc_lblProductName.anchor = GridBagConstraints.WEST;
		gbc_lblProductName.gridx = 0;
		gbc_lblProductName.gridy = 2;
		add(lblProductName, gbc_lblProductName);
		
		txtProduct = new JTextField();
		GridBagConstraints gbc_txtProduct = new GridBagConstraints();
		gbc_txtProduct.insets = new Insets(0, 0, 5, 0);
		gbc_txtProduct.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtProduct.gridx = 1;
		gbc_txtProduct.gridy = 2;
		add(txtProduct, gbc_txtProduct);
		txtProduct.setColumns(10);
		
		JLabel lblCategory = new JLabel("Category");
		GridBagConstraints gbc_lblCategory = new GridBagConstraints();
		gbc_lblCategory.anchor = GridBagConstraints.WEST;
		gbc_lblCategory.insets = new Insets(0, 0, 5, 5);
		gbc_lblCategory.gridx = 0;
		gbc_lblCategory.gridy = 3;
		add(lblCategory, gbc_lblCategory);
		
		cmbCategory = new JComboBox(categories);
		cmbCategory.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				cmbCategoryItemStateChanged(e);
			}
		});
		GridBagConstraints gbc_cmbCategory = new GridBagConstraints();
		gbc_cmbCategory.insets = new Insets(0, 0, 5, 0);
		gbc_cmbCategory.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbCategory.gridx = 1;
		gbc_cmbCategory.gridy = 3;
		add(cmbCategory, gbc_cmbCategory);
		
		
		GridBagConstraints gbc_lblQuantity = new GridBagConstraints();
		gbc_lblQuantity.anchor = GridBagConstraints.WEST;
		gbc_lblQuantity.insets = new Insets(0, 0, 5, 5);
		gbc_lblQuantity.gridx = 0;
		gbc_lblQuantity.gridy = 4;
		add(lblQuantity, gbc_lblQuantity);
		
		txtQuantity = new JTextField();
		GridBagConstraints gbc_txtQuantity = new GridBagConstraints();
		gbc_txtQuantity.anchor = GridBagConstraints.NORTH;
		gbc_txtQuantity.insets = new Insets(0, 0, 5, 0);
		gbc_txtQuantity.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtQuantity.gridx = 1;
		gbc_txtQuantity.gridy = 4;
		add(txtQuantity, gbc_txtQuantity);
		txtQuantity.setColumns(10);
		
		JLabel lblPrice = new JLabel("Price");
		GridBagConstraints gbc_lblPrice = new GridBagConstraints();
		gbc_lblPrice.anchor = GridBagConstraints.WEST;
		gbc_lblPrice.insets = new Insets(0, 0, 5, 5);
		gbc_lblPrice.gridx = 0;
		gbc_lblPrice.gridy = 5;
		add(lblPrice, gbc_lblPrice);
		
		txtPrice = new JTextField();
		GridBagConstraints gbc_txtPrice = new GridBagConstraints();
		gbc_txtPrice.anchor = GridBagConstraints.NORTH;
		gbc_txtPrice.insets = new Insets(0, 0, 5, 0);
		gbc_txtPrice.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPrice.gridx = 1;
		gbc_txtPrice.gridy = 5;
		add(txtPrice, gbc_txtPrice);
		txtPrice.setColumns(10);
		
		JButton btnSubmit = new JButton("Add product");
		btnSubmit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String product = txtProduct.getText();
				String category = cmbCategory.getSelectedItem().toString();
				int quantity = Integer.parseInt(txtQuantity.getText());
				double price = Double.parseDouble(txtPrice.getText());
				
				if(!category.equals("Lesson") && !category.equals("Fixture")){
					boolean added = ProductController.validateProduct(product, category, quantity, price);
					if(added){
						JOptionPane.showMessageDialog(new JFrame(), "Product added successfully");
						clearForm();
					}
				} else if(category.equals("Lesson")){
					int coachIndex = cmbStaff.getSelectedIndex();
					int staffId = Integer.parseInt(staffList[coachIndex][0]);
					Date time = (Date)timeSpinner.getValue();
					String day = cmbDay.getSelectedItem().toString();
					int duration = Integer.parseInt(txtDuration.getText());
					
					int locationIndex = cmbLocation.getSelectedIndex();
					String location = "AH";
					
					// "Albany Hills State School", "Albany Creek State School", "All Saints", "St Dympna's", "Aspley East State School"
					
					switch (locationIndex){
						case 0: location = "AH";
								break;
						case 1: location = "AC";
								break;
						case 2: location = "AS";
								break;
						case 3: location = "SD";
								break;
						case 4: location = "AE";
					}
					
					
					Date datePaymentDue = dtePayment.getDate();
					
					int termIndex = cmbTerm.getSelectedIndex();
					String term = "1";
					
					switch (termIndex){
					case 0: term = "1";
							break;
					case 1: term = "1.5";
							break;
					case 2: term = "2";
							break;
					case 3: term = "2.5";
							break;
					case 4: term = "3";
							break;
					case 5: term = "3.5";
							break;
					case 6: term = "4";
							break;
					case 7: term = "4.5";
							break;
					}
					
					int year = yrYear.getValue();

					boolean added = ProductController.validateLesson(product, category, quantity, price, staffId, time, day, duration, location, datePaymentDue, term, year);
					if(added){
						JOptionPane.showMessageDialog(new JFrame(), "Lesson added successfully");	
						clearForm();
					}
				} else if(category.equals("Fixture")){
					Date time = (Date)timeSpinner.getValue();
					String day = cmbDay.getSelectedItem().toString();
					System.out.println(day);
					int duration = Integer.parseInt(txtDuration.getText());
					
					int locationIndex = cmbLocation.getSelectedIndex();
					String location = "AH";
					
					// "Albany Hills State School", "Albany Creek State School", "All Saints", "St Dympna's", "Aspley East State School"
					
					switch (locationIndex){
						case 0: location = "AH";
								break;
						case 1: location = "AC";
								break;
						case 2: location = "AS";
								break;
						case 3: location = "SD";
								break;
						case 4: location = "AE";
					}
					
					
					Date datePaymentDue = dtePayment.getDate();
					
					int termIndex = cmbTerm.getSelectedIndex();
					String term = "1";
					
					switch (termIndex){
					case 0: term = "1";
							break;
					case 1: term = "1.5";
							break;
					case 2: term = "2";
							break;
					case 3: term = "2.5";
							break;
					case 4: term = "3";
							break;
					case 5: term = "3.5";
							break;
					case 6: term = "4";
							break;
					case 7: term = "4.5";
							break;
					}
					int year = yrYear.getValue();
					
					boolean added = ProductController.validateFixture(product, category, quantity, price, time, day, duration, location, datePaymentDue, term, year);
					if(added){
						JOptionPane.showMessageDialog(new JFrame(), "Fixture added successfully");	
						clearForm();
					}
				}
			}
		});
		
		
		
		
		GridBagConstraints gbc_btnSubmit = new GridBagConstraints();
		gbc_btnSubmit.gridx = 1;
		gbc_btnSubmit.gridy = 15;
		add(btnSubmit, gbc_btnSubmit);

	}
	
	private void cmbCategoryItemStateChanged(java.awt.event.ItemEvent evt) {                                                     
	    if (java.awt.event.ItemEvent.SELECTED == evt.getStateChange()) {
	    	String categorySelected = evt.getItem().toString();
	    	
	    	lblQuantity.setText("Quantity in stock");
	    	lblDay.setVisible(false);
    		cmbDay.setVisible(false);
    		lblTime.setVisible(false);
    		if(timeEditor != null){
    			timeEditor.setVisible(false);
    		}
    		lblDuration.setVisible(false);
    		txtDuration.setVisible(false);
    		lblDatePaymentDue.setVisible(false);
    		dtePayment.setVisible(false);
    		lblTerm.setVisible(false);
    		cmbTerm.setVisible(false);
    		lblYear.setVisible(false);
    		yrYear.setVisible(false);
    		timeSpinner.setVisible(false);
    		if(lblCoach != null){
    			lblCoach.setVisible(false);
	    		cmbStaff.setVisible(false);
	    		lblLocation.setVisible(false);
	    		cmbLocation.setVisible(false);
    		} 
	    	
	    	if(categorySelected.equals("Lesson") || categorySelected.equals("Fixture")){  		
	    		lblQuantity.setText("Positions");
	    		if(categorySelected.equals("Fixture")){
	    			lblDay.setVisible(true);
	    			GridBagConstraints gbc_lblDay = new GridBagConstraints();
	    			gbc_lblDay.anchor = GridBagConstraints.WEST;
	    			gbc_lblDay.insets = new Insets(0, 0, 5, 5);
	    			gbc_lblDay.gridx = 0;
	    			gbc_lblDay.gridy = 6;
	    			add(lblDay, gbc_lblDay);
	    			
	    			GridBagConstraints gbc_cmbDay = new GridBagConstraints();
	    			gbc_cmbDay.insets = new Insets(0, 0, 5, 0);
	    			gbc_cmbDay.fill = GridBagConstraints.HORIZONTAL;
	    			gbc_cmbDay.gridx = 1;
	    			gbc_cmbDay.gridy = 6;
	    			cmbDay.setVisible(true);
	    			add(cmbDay, gbc_cmbDay);
	    			
	    			lblTime.setVisible(true);
	    			GridBagConstraints gbc_lblTime = new GridBagConstraints();
	    			gbc_lblTime.anchor = GridBagConstraints.WEST;
	    			gbc_lblTime.insets = new Insets(0, 0, 5, 5);
	    			gbc_lblTime.gridx = 0;
	    			gbc_lblTime.gridy = 7;
	    			add(lblTime, gbc_lblTime);
	    			
	    			timeEditor = new JSpinner.DateEditor(timeSpinner, "hh:mm a");
	    			timeSpinner.setEditor(timeEditor);
	    			timeSpinner.setValue(new Date());
	    			GridBagConstraints gbc_timeSpinner = new GridBagConstraints();
	    			gbc_timeSpinner.anchor = GridBagConstraints.WEST;
	    			gbc_timeSpinner.insets = new Insets(0, 0, 5, 0);
	    			gbc_timeSpinner.gridx = 1;
	    			gbc_timeSpinner.gridy = 7;
	    			timeSpinner.setVisible(true);
	    			add(timeSpinner, gbc_timeSpinner);
	    			
	    			lblDuration.setVisible(true);
	    			GridBagConstraints gbc_lblDuration = new GridBagConstraints();
	    			gbc_lblDuration.anchor = GridBagConstraints.WEST;
	    			gbc_lblDuration.insets = new Insets(0, 0, 5, 5);
	    			gbc_lblDuration.gridx = 0;
	    			gbc_lblDuration.gridy = 8;
	    			add(lblDuration, gbc_lblDuration);
	    			
	    			txtDuration.setToolTipText("Minutes");
	    			GridBagConstraints gbc_txtDuration = new GridBagConstraints();
	    			gbc_txtDuration.insets = new Insets(0, 0, 5, 0);
	    			gbc_txtDuration.fill = GridBagConstraints.HORIZONTAL;
	    			gbc_txtDuration.gridx = 1;
	    			gbc_txtDuration.gridy = 8;
	    			txtDuration.setVisible(true);
	    			add(txtDuration, gbc_txtDuration);
	    			txtDuration.setColumns(10);
	    			
	    			lblDatePaymentDue.setVisible(true);
	    			GridBagConstraints gbc_lblDatePaymentDue = new GridBagConstraints();
	    			gbc_lblDatePaymentDue.insets = new Insets(0, 0, 5, 5);
	    			gbc_lblDatePaymentDue.gridx = 0;
	    			gbc_lblDatePaymentDue.gridy = 9;
	    			add(lblDatePaymentDue, gbc_lblDatePaymentDue);
	    			
	    			
	    			GridBagConstraints gbc_dtePayment = new GridBagConstraints();
	    			gbc_dtePayment.insets = new Insets(0, 0, 5, 0);
	    			gbc_dtePayment.fill = GridBagConstraints.BOTH;
	    			gbc_dtePayment.gridx = 1;
	    			gbc_dtePayment.gridy = 9;
	    			dtePayment.setVisible(true);
	    			add(dtePayment, gbc_dtePayment);
	    			
	    			lblTerm.setVisible(true);
	    			GridBagConstraints gbc_lblTerm = new GridBagConstraints();
	    			gbc_lblTerm.anchor = GridBagConstraints.WEST;
	    			gbc_lblTerm.insets = new Insets(0, 0, 5, 5);
	    			gbc_lblTerm.gridx = 0;
	    			gbc_lblTerm.gridy = 10;
	    			add(lblTerm, gbc_lblTerm);
	    			
	    			GridBagConstraints gbc_cmbTerm = new GridBagConstraints();
	    			gbc_cmbTerm.insets = new Insets(0, 0, 5, 0);
	    			gbc_cmbTerm.fill = GridBagConstraints.HORIZONTAL;
	    			gbc_cmbTerm.gridx = 1;
	    			gbc_cmbTerm.gridy = 10;
	    			add(cmbTerm, gbc_cmbTerm);
	    			cmbTerm.setVisible(true);
	    			double currentTerm = Initialise.getCurrentTerm();
	    			int termInt = (int)currentTerm * 2 - 1;
	    			cmbTerm.setSelectedIndex(termInt);
	    			
	    			lblYear.setVisible(true);
	    			GridBagConstraints gbc_lblYear = new GridBagConstraints();
	    			gbc_lblYear.anchor = GridBagConstraints.WEST;
	    			gbc_lblYear.insets = new Insets(0, 0, 5, 5);
	    			gbc_lblYear.gridx = 0;
	    			gbc_lblYear.gridy = 11;
	    			add(lblYear, gbc_lblYear);
	    			
	    			yrYear.setEndYear(thisYear + 5);
	    			yrYear.setStartYear(thisYear);
	    			yrYear.setMinimum(thisYear);
	    			GridBagConstraints gbc_yrYear = new GridBagConstraints();
	    			gbc_yrYear.insets = new Insets(0, 0, 5, 0);
	    			gbc_yrYear.fill = GridBagConstraints.BOTH;
	    			gbc_yrYear.gridx = 1;
	    			gbc_yrYear.gridy = 11;
	    			yrYear.setVisible(true);
	    			add(yrYear, gbc_yrYear);
	    			
	    		}
	    		
	    		if(categorySelected.equals("Lesson")){
	    			lblCoach.setVisible(true);
	    			GridBagConstraints gbc_lblCoach = new GridBagConstraints();
	    			gbc_lblCoach.anchor = GridBagConstraints.WEST;
	    			gbc_lblCoach.insets = new Insets(0, 0, 5, 5);
	    			gbc_lblCoach.gridx = 0;
	    			gbc_lblCoach.gridy = 6;
	    			add(lblCoach, gbc_lblCoach);
	    			
	    			for(int i = 0; i < staffList.length; i = i + 1){
	    				String coach = staffList[i][2] + " (" + staffList[i][1] + ")";
	    				cmbStaff.addItem(coach);
	    			}
	    			
	    			GridBagConstraints gbc_cmbStaff = new GridBagConstraints();
	    			gbc_cmbStaff.insets = new Insets(0, 0, 5, 0);
	    			gbc_cmbStaff.anchor = GridBagConstraints.NORTH;
	    			gbc_cmbStaff.fill = GridBagConstraints.HORIZONTAL;
	    			gbc_cmbStaff.gridx = 1;
	    			gbc_cmbStaff.gridy = 6;
	    			cmbStaff.setVisible(true);
	    			add(cmbStaff, gbc_cmbStaff);
	    			
	    			lblLocation.setVisible(true);
	    			GridBagConstraints gbc_lblLocation = new GridBagConstraints();
	    			gbc_lblLocation.anchor = GridBagConstraints.WEST;
	    			gbc_lblLocation.insets = new Insets(0, 0, 5, 5);
	    			gbc_lblLocation.gridx = 0;
	    			gbc_lblLocation.gridy = 7;
	    			add(lblLocation, gbc_lblLocation);
	    			
	    			
	    			GridBagConstraints gbc_cmbLocation = new GridBagConstraints();
	    			gbc_cmbLocation.insets = new Insets(0, 0, 5, 0);
	    			gbc_cmbLocation.fill = GridBagConstraints.HORIZONTAL;
	    			gbc_cmbLocation.gridx = 1;
	    			gbc_cmbLocation.gridy = 7;
	    			cmbLocation.setVisible(true);
	    			add(cmbLocation, gbc_cmbLocation);
	    			
	    			lblDay.setVisible(true);
	    			GridBagConstraints gbc_lblDay = new GridBagConstraints();
	    			gbc_lblDay.anchor = GridBagConstraints.WEST;
	    			gbc_lblDay.insets = new Insets(0, 0, 5, 5);
	    			gbc_lblDay.gridx = 0;
	    			gbc_lblDay.gridy = 8;
	    			add(lblDay, gbc_lblDay);
	    			
	    			
	    			GridBagConstraints gbc_cmbDay = new GridBagConstraints();
	    			gbc_cmbDay.insets = new Insets(0, 0, 5, 0);
	    			gbc_cmbDay.fill = GridBagConstraints.HORIZONTAL;
	    			gbc_cmbDay.gridx = 1;
	    			gbc_cmbDay.gridy = 8;
	    			cmbDay.setVisible(true);
	    			add(cmbDay, gbc_cmbDay);
	    			
	    			lblTime.setVisible(true);
	    			GridBagConstraints gbc_lblTime = new GridBagConstraints();
	    			gbc_lblTime.anchor = GridBagConstraints.WEST;
	    			gbc_lblTime.insets = new Insets(0, 0, 5, 5);
	    			gbc_lblTime.gridx = 0;
	    			gbc_lblTime.gridy = 9;
	    			add(lblTime, gbc_lblTime);
	    			
	    			
	    			timeEditor = new JSpinner.DateEditor(timeSpinner, "hh:mm a");
	    			timeSpinner.setEditor(timeEditor);
	    			timeSpinner.setValue(new Date());
	    			GridBagConstraints gbc_timeSpinner = new GridBagConstraints();
	    			gbc_timeSpinner.anchor = GridBagConstraints.WEST;
	    			gbc_timeSpinner.insets = new Insets(0, 0, 5, 0);
	    			gbc_timeSpinner.gridx = 1;
	    			gbc_timeSpinner.gridy = 9;
	    			timeSpinner.setVisible(true);
	    			add(timeSpinner, gbc_timeSpinner);
	    			
	    			lblDuration.setVisible(true);
	    			GridBagConstraints gbc_lblDuration = new GridBagConstraints();
	    			gbc_lblDuration.anchor = GridBagConstraints.WEST;
	    			gbc_lblDuration.insets = new Insets(0, 0, 5, 5);
	    			gbc_lblDuration.gridx = 0;
	    			gbc_lblDuration.gridy = 10;
	    			add(lblDuration, gbc_lblDuration);
	    			
	    			txtDuration.setToolTipText("Minutes");
	    			GridBagConstraints gbc_txtDuration = new GridBagConstraints();
	    			gbc_txtDuration.insets = new Insets(0, 0, 5, 0);
	    			gbc_txtDuration.fill = GridBagConstraints.HORIZONTAL;
	    			gbc_txtDuration.gridx = 1;
	    			gbc_txtDuration.gridy = 10;
	    			txtDuration.setVisible(true);
	    			add(txtDuration, gbc_txtDuration);
	    			txtDuration.setColumns(10);
	    			
	    			lblDatePaymentDue.setVisible(true);
	    			GridBagConstraints gbc_lblDatePaymentDue = new GridBagConstraints();
	    			gbc_lblDatePaymentDue.insets = new Insets(0, 0, 5, 5);
	    			gbc_lblDatePaymentDue.gridx = 0;
	    			gbc_lblDatePaymentDue.gridy = 11;
	    			add(lblDatePaymentDue, gbc_lblDatePaymentDue);
	    			
	    			GridBagConstraints gbc_dtePayment = new GridBagConstraints();
	    			gbc_dtePayment.insets = new Insets(0, 0, 5, 0);
	    			gbc_dtePayment.fill = GridBagConstraints.BOTH;
	    			gbc_dtePayment.gridx = 1;
	    			gbc_dtePayment.gridy = 11;
	    			dtePayment.setVisible(true);
	    			add(dtePayment, gbc_dtePayment);
	    			
	    			lblTerm.setVisible(true);
	    			GridBagConstraints gbc_lblTerm = new GridBagConstraints();
	    			gbc_lblTerm.anchor = GridBagConstraints.WEST;
	    			gbc_lblTerm.insets = new Insets(0, 0, 5, 5);
	    			gbc_lblTerm.gridx = 0;
	    			gbc_lblTerm.gridy = 12;
	    			add(lblTerm, gbc_lblTerm);

	    			GridBagConstraints gbc_cmbTerm = new GridBagConstraints();
	    			gbc_cmbTerm.insets = new Insets(0, 0, 5, 0);
	    			gbc_cmbTerm.fill = GridBagConstraints.HORIZONTAL;
	    			gbc_cmbTerm.gridx = 1;
	    			gbc_cmbTerm.gridy = 12;
	    			add(cmbTerm, gbc_cmbTerm);
	    			cmbTerm.setVisible(true);
	    			double currentTerm = Initialise.getCurrentTerm();
	    			int termInt = (int) currentTerm * 2 - 2;
	    			cmbTerm.setSelectedIndex(termInt);
	    			
	    			lblYear.setVisible(true);
	    			GridBagConstraints gbc_lblYear = new GridBagConstraints();
	    			gbc_lblYear.anchor = GridBagConstraints.WEST;
	    			gbc_lblYear.insets = new Insets(0, 0, 5, 5);
	    			gbc_lblYear.gridx = 0;
	    			gbc_lblYear.gridy = 13;
	    			add(lblYear, gbc_lblYear);
	    				    			
	    			yrYear.setEndYear(thisYear + 5);
	    			yrYear.setStartYear(thisYear);
	    			yrYear.setMinimum(thisYear);
	    			GridBagConstraints gbc_yrYear = new GridBagConstraints();
	    			gbc_yrYear.insets = new Insets(0, 0, 5, 0);
	    			gbc_yrYear.fill = GridBagConstraints.BOTH;
	    			gbc_yrYear.gridx = 1;
	    			gbc_yrYear.gridy = 13;
	    			yrYear.setVisible(true);
	    			add(yrYear, gbc_yrYear);
	    			
	    		}
	    	}
		}
	}
	
	public static void showError(String field){
		if(field.equals("Staff")){
			JOptionPane.showMessageDialog(new JPanel(), "There are no staff members registered with that name. Please enter the first name of the coach for the lesson", "Coach does not exist", JOptionPane.ERROR_MESSAGE);
		} else{
			JOptionPane.showMessageDialog(new JPanel(), field + " is incorrect. Please make sure it is in the right format", field + " is invalid", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void clearForm(){
		txtProduct.setText("");
		txtQuantity.setText("");
		txtPrice.setText("");
		txtDuration.setText("");
		dtePayment.setDate(new Date());
		timeSpinner.setModel(new SpinnerDateModel());
		cmbDay.setSelectedIndex(0);
		cmbLocation.setSelectedIndex(0);
		txtCoach.setText(StaffController.getStaffFName());
		yrYear.setStartYear(thisYear);
		cmbTerm.setSelectedIndex(0);
		cmbCategory.setSelectedIndex(0);
	}
}

