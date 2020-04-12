package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controller.StaffController;

import javax.swing.JTabbedPane;

public class MainFrame extends JFrame {
	// This is the main JFrame that contains the tabbedPane for displaying the individual tabs (JPanels)
	
	// The JPanel that contains the tabbedPane that is used to display the individual tabs
	private JPanel contentPane;
	private static JFrame frame;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setTitle("Woods Tennis payment management system");
		frame = this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1028, 620);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// The tabbedPane that contains the tabs of other JPanels
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(12, 13, 986, 547);
		
		
		
		
		// Add the tabs
		Welcome welcome = new Welcome();
		tabbedPane.addTab("Welcome", welcome);
		
		AddTransaction addTransaction = new AddTransaction();
		tabbedPane.addTab("Add transaction", addTransaction);
		
		AddCustomer addCustomer = new AddCustomer();
		tabbedPane.addTab("Register customer", addCustomer);
		
		AddProduct addProduct = new AddProduct();
		tabbedPane.addTab("Add product", addProduct);
		
		SearchTransactions searchTransactions = new SearchTransactions();
		tabbedPane.addTab("Search transactions", searchTransactions);
		
		SearchCustomers searchCustomers = new SearchCustomers();
		tabbedPane.addTab("Search customers", searchCustomers);
		
		AddStaff addStaff = new AddStaff();
		
		EmailStatements emailStatements = new EmailStatements();
		
		if(StaffController.isAdmin()){
			tabbedPane.addTab("Add employee", addStaff);
			tabbedPane.addTab("Send statements", emailStatements);
		}
		
		JPanel logOut = new LogOut();
		tabbedPane.addTab("Log out", logOut);
		
		// Check which tab has been selected so that the entered details in the form can be deleted before the user enters new data
		tabbedPane.addChangeListener(new ChangeListener() {
	        public void stateChanged(ChangeEvent e) {
	        	int selectedTab = tabbedPane.getSelectedIndex();
	        	System.out.println("The tab that is now selected is: " + selectedTab);
	            switch (selectedTab){
	            case 0: welcome.clearForm();
	            		welcome.repaint();
	            		welcome.revalidate();
	            		break;
	            case 1: addTransaction.clearForm();
	            		addTransaction.repaint();
	            		addTransaction.revalidate();
	            		break;
	            case 2: addCustomer.clearForm();
	            		addCustomer.repaint();
	            		addCustomer.revalidate();
	            		break;
	            case 3: addProduct.clearForm();
	            		addProduct.repaint();
	            		addProduct.revalidate();
	            		break;
	            case 4: searchTransactions.clearForm();
	            		searchTransactions.repaint();
	            		searchTransactions.revalidate();
	            		break;
	            case 5: searchCustomers.clearForm();
	            		searchCustomers.repaint();
	            		searchCustomers.revalidate();
	            		break;
	            case 6: addStaff.clearForm();
	            		addStaff.repaint();
	            		addStaff.revalidate();
	            		break;
	            }
	        }
	    });
		
		contentPane.add(tabbedPane);
		
		setIcon();
	}
	
	private void setIcon(){
		// Set the application's icon
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Woods logo square.png")));
	}
	
	public static JFrame getMainFrame(){
		return frame;
	}
}
