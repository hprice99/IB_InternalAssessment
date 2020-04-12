package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.CustomerController;
import controller.StaffController;
import controller.TransactionController;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Toolkit;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Receipt extends JFrame {

	private JPanel contentPane;
	private JTextField txtCustomerName;
	private JTextField txtDate;
	private JTextField txtProduct;
	private JTextField txtQuantity;
	private JTextField txtAmount;
	private JTextField txtPaymentMethod;
	private JTextField txtStaff;
	
	private String staff = StaffController.getStaffName();
	
	// Object to display cost variable as a currency value
	private NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
	private JLabel lblTransactionNumber;
	private JTextField txtTransactionId;

	/**
	 * Create the frame.
	 * @param transactionId 
	 */
	
	
	public Receipt(int transactionId) {
		setTitle("Receipt for transaction no. " + transactionId);
		String[] transactionDetails = TransactionController.getTransactionDetails(transactionId);
		
		setBounds(100, 100, 450, 387);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblCustomerName = new JLabel("Customer name");
		GridBagConstraints gbc_lblCustomerName = new GridBagConstraints();
		gbc_lblCustomerName.anchor = GridBagConstraints.WEST;
		gbc_lblCustomerName.insets = new Insets(0, 0, 5, 5);
		gbc_lblCustomerName.gridx = 0;
		gbc_lblCustomerName.gridy = 0;
		contentPane.add(lblCustomerName, gbc_lblCustomerName);
		
		txtCustomerName = new JTextField(transactionDetails[2]);
		txtCustomerName.setEditable(false);
		GridBagConstraints gbc_txtCustomerName = new GridBagConstraints();
		gbc_txtCustomerName.anchor = GridBagConstraints.NORTH;
		gbc_txtCustomerName.insets = new Insets(0, 0, 5, 0);
		gbc_txtCustomerName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCustomerName.gridx = 1;
		gbc_txtCustomerName.gridy = 0;
		contentPane.add(txtCustomerName, gbc_txtCustomerName);
		txtCustomerName.setColumns(10);
		
		JLabel lblDate = new JLabel("Date");
		GridBagConstraints gbc_lblDate = new GridBagConstraints();
		gbc_lblDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblDate.anchor = GridBagConstraints.WEST;
		gbc_lblDate.gridx = 0;
		gbc_lblDate.gridy = 1;
		contentPane.add(lblDate, gbc_lblDate);
		
		SimpleDateFormat dbDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dbDate = new Date();
		try {
			dbDate = dbDateFormat.parse(transactionDetails[3]);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		SimpleDateFormat newFormat = new SimpleDateFormat ("dd/MM/yy h:mm a");
		String dateString = newFormat.format(dbDate);
		transactionDetails[3] = dateString;
		
		txtDate = new JTextField(dateString);
		txtDate.setEditable(false);
		GridBagConstraints gbc_txtDate = new GridBagConstraints();
		gbc_txtDate.insets = new Insets(0, 0, 5, 0);
		gbc_txtDate.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDate.gridx = 1;
		gbc_txtDate.gridy = 1;
		contentPane.add(txtDate, gbc_txtDate);
		txtDate.setColumns(10);
		
		JLabel lblProduct = new JLabel("Product");
		GridBagConstraints gbc_lblProduct = new GridBagConstraints();
		gbc_lblProduct.anchor = GridBagConstraints.WEST;
		gbc_lblProduct.insets = new Insets(0, 0, 5, 5);
		gbc_lblProduct.gridx = 0;
		gbc_lblProduct.gridy = 2;
		contentPane.add(lblProduct, gbc_lblProduct);
		
		txtProduct = new JTextField(transactionDetails[4]);
		txtProduct.setEditable(false);
		GridBagConstraints gbc_txtProduct = new GridBagConstraints();
		gbc_txtProduct.insets = new Insets(0, 0, 5, 0);
		gbc_txtProduct.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtProduct.gridx = 1;
		gbc_txtProduct.gridy = 2;
		contentPane.add(txtProduct, gbc_txtProduct);
		txtProduct.setColumns(10);
		
		JLabel lblQuantity = new JLabel("Quantity");
		GridBagConstraints gbc_lblQuantity = new GridBagConstraints();
		gbc_lblQuantity.anchor = GridBagConstraints.WEST;
		gbc_lblQuantity.insets = new Insets(0, 0, 5, 5);
		gbc_lblQuantity.gridx = 0;
		gbc_lblQuantity.gridy = 3;
		contentPane.add(lblQuantity, gbc_lblQuantity);
		
		txtQuantity = new JTextField(transactionDetails[5]);
		txtQuantity.setEditable(false);
		GridBagConstraints gbc_txtQuantity = new GridBagConstraints();
		gbc_txtQuantity.anchor = GridBagConstraints.NORTH;
		gbc_txtQuantity.insets = new Insets(0, 0, 5, 0);
		gbc_txtQuantity.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtQuantity.gridx = 1;
		gbc_txtQuantity.gridy = 3;
		contentPane.add(txtQuantity, gbc_txtQuantity);
		txtQuantity.setColumns(10);
		
		JLabel lblAmount = new JLabel("Amount");
		GridBagConstraints gbc_lblAmount = new GridBagConstraints();
		gbc_lblAmount.anchor = GridBagConstraints.WEST;
		gbc_lblAmount.insets = new Insets(0, 0, 5, 5);
		gbc_lblAmount.gridx = 0;
		gbc_lblAmount.gridy = 4;
		contentPane.add(lblAmount, gbc_lblAmount);
		
		txtAmount = new JTextField(currencyFormatter.format(Double.parseDouble(transactionDetails[6])));
		txtAmount.setEditable(false);
		GridBagConstraints gbc_txtAmount = new GridBagConstraints();
		gbc_txtAmount.anchor = GridBagConstraints.NORTH;
		gbc_txtAmount.insets = new Insets(0, 0, 5, 0);
		gbc_txtAmount.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtAmount.gridx = 1;
		gbc_txtAmount.gridy = 4;
		contentPane.add(txtAmount, gbc_txtAmount);
		txtAmount.setColumns(10);
		
		JLabel lblPaymentMethod = new JLabel("Payment method");
		GridBagConstraints gbc_lblPaymentMethod = new GridBagConstraints();
		gbc_lblPaymentMethod.anchor = GridBagConstraints.WEST;
		gbc_lblPaymentMethod.insets = new Insets(0, 0, 5, 5);
		gbc_lblPaymentMethod.gridx = 0;
		gbc_lblPaymentMethod.gridy = 5;
		contentPane.add(lblPaymentMethod, gbc_lblPaymentMethod);
		
		
		txtPaymentMethod = new JTextField(transactionDetails[7]);
		txtPaymentMethod.setEditable(false);
		GridBagConstraints gbc_txtPaymentMethod = new GridBagConstraints();
		gbc_txtPaymentMethod.anchor = GridBagConstraints.NORTH;
		gbc_txtPaymentMethod.insets = new Insets(0, 0, 5, 0);
		gbc_txtPaymentMethod.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPaymentMethod.gridx = 1;
		gbc_txtPaymentMethod.gridy = 5;
		contentPane.add(txtPaymentMethod, gbc_txtPaymentMethod);
		txtPaymentMethod.setColumns(10);
		
		JLabel lblServedBy = new JLabel("Served by");
		GridBagConstraints gbc_lblServedBy = new GridBagConstraints();
		gbc_lblServedBy.insets = new Insets(0, 0, 5, 5);
		gbc_lblServedBy.anchor = GridBagConstraints.WEST;
		gbc_lblServedBy.gridx = 0;
		gbc_lblServedBy.gridy = 6;
		contentPane.add(lblServedBy, gbc_lblServedBy);
		
		txtStaff = new JTextField(transactionDetails[8]);
		txtStaff.setEditable(false);
		GridBagConstraints gbc_txtStaff = new GridBagConstraints();
		gbc_txtStaff.insets = new Insets(0, 0, 5, 0);
		gbc_txtStaff.anchor = GridBagConstraints.NORTH;
		gbc_txtStaff.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtStaff.gridx = 1;
		gbc_txtStaff.gridy = 6;
		contentPane.add(txtStaff, gbc_txtStaff);
		txtStaff.setColumns(10);
		
		JButton btnSaveToPdf = new JButton("Save to PDF");
		btnSaveToPdf.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// Save the receipt on a PDF
				TransactionController.saveReceipt(transactionDetails);
			}
		});
		
		lblTransactionNumber = new JLabel("Transaction number");
		GridBagConstraints gbc_lblTransactionNumber = new GridBagConstraints();
		gbc_lblTransactionNumber.anchor = GridBagConstraints.EAST;
		gbc_lblTransactionNumber.insets = new Insets(0, 0, 5, 5);
		gbc_lblTransactionNumber.gridx = 0;
		gbc_lblTransactionNumber.gridy = 7;
		contentPane.add(lblTransactionNumber, gbc_lblTransactionNumber);
		
		txtTransactionId = new JTextField(Integer.toString(transactionId));
		txtTransactionId.setEditable(false);
		GridBagConstraints gbc_txtTransactionId = new GridBagConstraints();
		gbc_txtTransactionId.anchor = GridBagConstraints.NORTH;
		gbc_txtTransactionId.insets = new Insets(0, 0, 5, 0);
		gbc_txtTransactionId.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTransactionId.gridx = 1;
		gbc_txtTransactionId.gridy = 7;
		contentPane.add(txtTransactionId, gbc_txtTransactionId);
		txtTransactionId.setColumns(10);
		GridBagConstraints gbc_btnSaveToPdf = new GridBagConstraints();
		gbc_btnSaveToPdf.gridx = 1;
		gbc_btnSaveToPdf.gridy = 8;
		contentPane.add(btnSaveToPdf, gbc_btnSaveToPdf);
		
		setIcon();
	}

	private void setIcon(){
		// Set the application's icon
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Woods logo square.png")));
	}
	
	
}
