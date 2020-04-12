package view;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import controller.CustomerController;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SearchCustomers extends JPanel {
	private JTextField txtName = new JTextField();
	private JTextField txtEmail = new JTextField();
	private JTable tblResults;
	private NoEditTableModel model;
	private ButtonGroup group = new ButtonGroup();
	
	/**
	 * Create the panel.
	 */
	public SearchCustomers() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 165, 213, 184, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 207, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblDescription = new JLabel("Search for a customer using the form below.");
		GridBagConstraints gbc_lblDescription = new GridBagConstraints();
		gbc_lblDescription.gridwidth = 2;
		gbc_lblDescription.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescription.gridx = 0;
		gbc_lblDescription.gridy = 0;
		add(lblDescription, gbc_lblDescription);
		
		JRadioButton rdbtnCustomerName = new JRadioButton("Customer name");
		rdbtnCustomerName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rdbtnCustomerName.isSelected()){
					txtName.setEditable(true);
					txtEmail.setEditable(false);
				}
			}
		});
		rdbtnCustomerName.setActionCommand("Name");
		GridBagConstraints gbc_rdbtnCustomerName = new GridBagConstraints();
		gbc_rdbtnCustomerName.anchor = GridBagConstraints.WEST;
		gbc_rdbtnCustomerName.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnCustomerName.gridx = 0;
		gbc_rdbtnCustomerName.gridy = 2;
		group.add(rdbtnCustomerName);
		add(rdbtnCustomerName, gbc_rdbtnCustomerName);
		
		
		GridBagConstraints gbc_txtName = new GridBagConstraints();
		gbc_txtName.anchor = GridBagConstraints.NORTH;
		gbc_txtName.insets = new Insets(0, 0, 5, 5);
		gbc_txtName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtName.gridx = 1;
		gbc_txtName.gridy = 2;
		add(txtName, gbc_txtName);
		txtName.setColumns(10);
		txtName.setEditable(false);
		
		JRadioButton rdbtnCustomerEmail = new JRadioButton("Customer email");
		rdbtnCustomerEmail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rdbtnCustomerEmail.isSelected()){
					txtEmail.setEditable(true);
					txtName.setEditable(false);
				}
			}
		});
		rdbtnCustomerEmail.setActionCommand("Email");
		GridBagConstraints gbc_rdbtnCustomerEmail = new GridBagConstraints();
		gbc_rdbtnCustomerEmail.anchor = GridBagConstraints.WEST;
		gbc_rdbtnCustomerEmail.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnCustomerEmail.gridx = 0;
		gbc_rdbtnCustomerEmail.gridy = 3;
		group.add(rdbtnCustomerEmail);
		add(rdbtnCustomerEmail, gbc_rdbtnCustomerEmail);
		
		GridBagConstraints gbc_txtEmail = new GridBagConstraints();
		gbc_txtEmail.insets = new Insets(0, 0, 5, 5);
		gbc_txtEmail.anchor = GridBagConstraints.NORTH;
		gbc_txtEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtEmail.gridx = 1;
		gbc_txtEmail.gridy = 3;
		add(txtEmail, gbc_txtEmail);
		txtEmail.setColumns(10);
		txtEmail.setEditable(false);
		
		// Header of each column
		String[] columnHeaders = {"First name", "Surname", "Email address", "Customer ID", "Outstanding payments"};
		
		model = new NoEditTableModel(0, columnHeaders.length);
		model.setColumnIdentifiers(columnHeaders);
		
		tblResults = new JTable(model);
		GridBagConstraints gbc_tblResults = new GridBagConstraints();
		gbc_tblResults.insets = new Insets(0, 0, 5, 0);
		gbc_tblResults.gridwidth = 5;
		gbc_tblResults.fill = GridBagConstraints.BOTH;
		gbc_tblResults.gridx = 0;
		gbc_tblResults.gridy = 6;
		
		tblResults.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int index = tblResults.getSelectedRow();
				// Get the customerId
				int id = Integer.parseInt(tblResults.getModel().getValueAt(index, 3).toString());
				EditCustomer editCustomer = new EditCustomer(id);
				editCustomer.setVisible(true);
			}
		});
		
		// Add the table within a scroll pane
		JScrollPane scrollPane = new JScrollPane(tblResults);
		add(scrollPane, gbc_tblResults);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// Remove all rows from JTable
				model.setRowCount(0);
				
				if(group.getSelection().getActionCommand().equals("Name")){
					String name = txtName.getText();
					ArrayList<String[]> customers = CustomerController.nameSearch(name);
					
					int size = customers.size();
					
					for(int i = 0; i < size; i = i + 1){
						model.addRow(customers.get(i));
					}
				} else if(group.getSelection().getActionCommand().equals("Email")){
					String email = txtEmail.getText();
					ArrayList<String[]> customers = CustomerController.emailSearch(email);
					
					int size = customers.size();
					
					for(int i = 0; i < size; i = i + 1){
						model.addRow(customers.get(i));
					}
				}
				tblResults.validate();
				tblResults.repaint();
			}
		});
		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.insets = new Insets(0, 0, 5, 5);
		gbc_btnSearch.gridx = 1;
		gbc_btnSearch.gridy = 4;
		add(btnSearch, gbc_btnSearch);

	}

	public void clearForm(){
		txtName.setText("");
		txtEmail.setText("");
		int rowCount = model.getRowCount();
		
		for (int i = 0; i < rowCount; i = i + 1){
			model.removeRow(i);
		}
		tblResults.repaint();
		tblResults.revalidate();
		
		group.clearSelection();
	}
}
