package view;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import com.toedter.calendar.JDateChooser;

import controller.CustomerController;
import controller.TransactionController;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SearchTransactions extends JPanel {
	private JTextField txtCustomer = new JTextField();
	private JTextField txtProduct = new JTextField();
	private JTable tblResults;
	private JDateChooser dateChooserFrom = new JDateChooser();
	private JDateChooser dateChooserTo = new JDateChooser();
	private ButtonGroup group = new ButtonGroup();
	private NoEditTableModel model;
	
	private ArrayList<String[]> transactions;
	
	/**
	 * Create the panel.
	 */
	public SearchTransactions() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 130, 191, 100, 139, 76, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 53, 56, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblSearchForTransactions = new JLabel("Search for transactions below.");
		GridBagConstraints gbc_lblSearchForTransactions = new GridBagConstraints();
		gbc_lblSearchForTransactions.anchor = GridBagConstraints.WEST;
		gbc_lblSearchForTransactions.insets = new Insets(0, 0, 5, 5);
		gbc_lblSearchForTransactions.gridx = 0;
		gbc_lblSearchForTransactions.gridy = 0;
		add(lblSearchForTransactions, gbc_lblSearchForTransactions);
		
		JLabel lblSearchBy = new JLabel("Search by:");
		GridBagConstraints gbc_lblSearchBy = new GridBagConstraints();
		gbc_lblSearchBy.insets = new Insets(0, 0, 5, 5);
		gbc_lblSearchBy.anchor = GridBagConstraints.WEST;
		gbc_lblSearchBy.gridx = 0;
		gbc_lblSearchBy.gridy = 2;
		add(lblSearchBy, gbc_lblSearchBy);
		
		// Group for radio button		
		JRadioButton rdbtnCustomer = new JRadioButton("Customer name");
		rdbtnCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(rdbtnCustomer.isSelected()){
					txtCustomer.setEditable(true);
					dateChooserFrom.setEnabled(false);
					dateChooserTo.setEnabled(false);
					txtProduct.setEditable(false);
				}
			}
		});
		rdbtnCustomer.setActionCommand("Customer");
		GridBagConstraints gbc_rdbtnCustomer = new GridBagConstraints();
		gbc_rdbtnCustomer.anchor = GridBagConstraints.WEST;
		gbc_rdbtnCustomer.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnCustomer.gridx = 0;
		gbc_rdbtnCustomer.gridy = 3;
		group.add(rdbtnCustomer);
		add(rdbtnCustomer, gbc_rdbtnCustomer);
		
		GridBagConstraints gbc_txtCustomer = new GridBagConstraints();
		gbc_txtCustomer.gridwidth = 2;
		gbc_txtCustomer.anchor = GridBagConstraints.NORTH;
		gbc_txtCustomer.insets = new Insets(0, 0, 5, 5);
		gbc_txtCustomer.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCustomer.gridx = 1;
		gbc_txtCustomer.gridy = 3;
		add(txtCustomer, gbc_txtCustomer);
		txtCustomer.setColumns(10);
		txtCustomer.setEditable(false);
		
		
		
		JRadioButton rdbtnDate = new JRadioButton("Date");
		rdbtnDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rdbtnDate.isSelected()){
					dateChooserFrom.setEnabled(true);
					dateChooserTo.setEnabled(true);
					txtCustomer.setEditable(false);
					txtProduct.setEditable(false);
				}
			}
		});
		rdbtnDate.setActionCommand("Date");
		GridBagConstraints gbc_rdbtnDate = new GridBagConstraints();
		gbc_rdbtnDate.anchor = GridBagConstraints.WEST;
		gbc_rdbtnDate.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnDate.gridx = 0;
		gbc_rdbtnDate.gridy = 4;
		group.add(rdbtnDate);
		add(rdbtnDate, gbc_rdbtnDate);
		
		JLabel lblFrom = new JLabel("From:");
		GridBagConstraints gbc_lblFrom = new GridBagConstraints();
		gbc_lblFrom.anchor = GridBagConstraints.WEST;
		gbc_lblFrom.insets = new Insets(0, 0, 5, 5);
		gbc_lblFrom.gridx = 1;
		gbc_lblFrom.gridy = 4;
		add(lblFrom, gbc_lblFrom);
		
		
		GridBagConstraints gbc_dateChooserFrom = new GridBagConstraints();
		gbc_dateChooserFrom.insets = new Insets(0, 0, 5, 5);
		gbc_dateChooserFrom.fill = GridBagConstraints.BOTH;
		gbc_dateChooserFrom.gridx = 2;
		gbc_dateChooserFrom.gridy = 4;
		add(dateChooserFrom, gbc_dateChooserFrom);
		dateChooserFrom.setEnabled(false);
		
		JLabel lblTo = new JLabel("To:");
		GridBagConstraints gbc_lblTo = new GridBagConstraints();
		gbc_lblTo.anchor = GridBagConstraints.WEST;
		gbc_lblTo.insets = new Insets(0, 0, 5, 5);
		gbc_lblTo.gridx = 1;
		gbc_lblTo.gridy = 5;
		add(lblTo, gbc_lblTo);
		
		
		GridBagConstraints gbc_dateChooserTo = new GridBagConstraints();
		gbc_dateChooserTo.insets = new Insets(0, 0, 5, 5);
		gbc_dateChooserTo.fill = GridBagConstraints.BOTH;
		gbc_dateChooserTo.gridx = 2;
		gbc_dateChooserTo.gridy = 5;
		add(dateChooserTo, gbc_dateChooserTo);
		dateChooserTo.setEnabled(false);
		
		JRadioButton rdbtnProduct = new JRadioButton("Product");
		rdbtnProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rdbtnProduct.isSelected()){
					txtProduct.setEditable(true);
					txtCustomer.setEditable(false);
					dateChooserFrom.setEnabled(false);
					dateChooserTo.setEnabled(false);
				}
			}
		});
		rdbtnProduct.setActionCommand("Product");
		GridBagConstraints gbc_rdbtnProduct = new GridBagConstraints();
		gbc_rdbtnProduct.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnProduct.anchor = GridBagConstraints.WEST;
		gbc_rdbtnProduct.gridx = 0;
		gbc_rdbtnProduct.gridy = 6;
		group.add(rdbtnProduct);
		add(rdbtnProduct, gbc_rdbtnProduct);
				
		GridBagConstraints gbc_txtProduct = new GridBagConstraints();
		gbc_txtProduct.insets = new Insets(0, 0, 5, 5);
		gbc_txtProduct.gridwidth = 2;
		gbc_txtProduct.anchor = GridBagConstraints.NORTH;
		gbc_txtProduct.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtProduct.gridx = 1;
		gbc_txtProduct.gridy = 6;
		add(txtProduct, gbc_txtProduct);
		txtProduct.setColumns(10);
		txtProduct.setEditable(false);
		
		// Header of each column
		String[] columnHeaders = {"<html>Transaction number</html>", "<html>Date</html>", "<html>Customer</html>", "<html>Product</html>", "<html>Amount</html>", "<html>Payment method</html>"};
		
		model = new NoEditTableModel(0, columnHeaders.length);
		model.setColumnIdentifiers(columnHeaders);
		
		
		tblResults = new JTable(model);
		GridBagConstraints gbc_tblResults = new GridBagConstraints();
		gbc_tblResults.insets = new Insets(0, 0, 5, 0);
		gbc_tblResults.gridheight = 2;
		gbc_tblResults.gridwidth = 7;
		gbc_tblResults.fill = GridBagConstraints.BOTH;
		gbc_tblResults.gridx = 0;
		gbc_tblResults.gridy = 8;
		tblResults.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblResults.getColumnModel().getColumn(0).setPreferredWidth(140);
		tblResults.getColumnModel().getColumn(1).setPreferredWidth(120);
		tblResults.getColumnModel().getColumn(2).setPreferredWidth(160);
		tblResults.getColumnModel().getColumn(3).setPreferredWidth(160);
		tblResults.getColumnModel().getColumn(4).setPreferredWidth(110);
		tblResults.getColumnModel().getColumn(5).setPreferredWidth(140);
		tblResults.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = tblResults.getSelectedRow();
				int transactionId = Integer.parseInt(tblResults.getModel().getValueAt(index, 0).toString());
				Receipt receipt = new Receipt(transactionId);
				receipt.setVisible(true);
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
				
				if(group.getSelection().getActionCommand().equals("Customer")){
					String customer = txtCustomer.getText();
					transactions = TransactionController.customerSearch(customer);
					
					int size = transactions.size();
					
					for(int i = 0; i < size; i = i + 1){
						model.addRow(transactions.get(i));
					}
					
					System.out.println("Row count is now " + model.getRowCount());
				} else if(group.getSelection().getActionCommand().equals("Product")){
					String product = txtProduct.getText();
					
					transactions = TransactionController.productSearch(product);
					
					int size = transactions.size();
					
					for(int i = 0; i < size; i = i + 1){
						model.addRow(transactions.get(i));
					}
				} else if(group.getSelection().getActionCommand().equals("Date")){
					Date from = dateChooserFrom.getDate();
					Date to = dateChooserTo.getDate();
					
					transactions = TransactionController.dateSearch(from, to);
					int size = transactions.size();
					
					for(int i = 0; i < size; i = i + 1){
						model.addRow(transactions.get(i));
					}
				} else{
					JOptionPane.showMessageDialog(new JPanel(), "Please select a search type", "No selected search type", JOptionPane.ERROR_MESSAGE);
				}
				tblResults.validate();
				tblResults.repaint();
			}
		});
		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.insets = new Insets(0, 0, 5, 5);
		gbc_btnSearch.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSearch.gridx = 2;
		gbc_btnSearch.gridy = 7;
		add(btnSearch, gbc_btnSearch);
		
		JButton btnSaveToCsv = new JButton("Save to CSV");
		btnSaveToCsv.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(group.getSelection().getActionCommand().equals("Customer")){
					String customerName = (String) model.getValueAt(0, 2);
					TransactionController.createCSV(transactions, customerName);
				} else if(group.getSelection().getActionCommand().equals("Product")){
					String product = txtProduct.getText();
					TransactionController.createCSV(transactions, product);
				} else if(group.getSelection().getActionCommand().equals("Date")){
					Date from = dateChooserFrom.getDate();
					Date to = dateChooserTo.getDate();
					
					TransactionController.createCSV(transactions, from, to);
				}
				
			}
		});
		GridBagConstraints gbc_btnSaveToCsv = new GridBagConstraints();
		gbc_btnSaveToCsv.gridwidth = 2;
		gbc_btnSaveToCsv.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSaveToCsv.gridx = 5;
		gbc_btnSaveToCsv.gridy = 10;
		add(btnSaveToCsv, gbc_btnSaveToCsv);

	}

	public void clearForm(){
		int rowCount = model.getRowCount();
		
		for (int i = 0; i < rowCount; i = i + 1){
			model.removeRow(i);
		}
		tblResults.repaint();
		tblResults.revalidate();
		
		txtCustomer.setText("");
		txtProduct.setText("");
		group.clearSelection();
			
	}
}
