package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChooseCustomer extends JFrame {

	private JPanel contentPane;
	private int customer;
	private boolean customerSelected = false;

	/**
	 * Create the frame.
	 */
	public ChooseCustomer(String[] customerList) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblInstructions = new JLabel("Please select a customer from the drop-down menu below");
		GridBagConstraints gbc_lblInstructions = new GridBagConstraints();
		gbc_lblInstructions.insets = new Insets(0, 0, 5, 0);
		gbc_lblInstructions.gridx = 0;
		gbc_lblInstructions.gridy = 0;
		contentPane.add(lblInstructions, gbc_lblInstructions);
		
		JComboBox cmbCustomers = new JComboBox(customerList);
		GridBagConstraints gbc_cmbCustomers = new GridBagConstraints();
		gbc_cmbCustomers.insets = new Insets(0, 0, 5, 0);
		gbc_cmbCustomers.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbCustomers.gridx = 0;
		gbc_cmbCustomers.gridy = 3;
		contentPane.add(cmbCustomers, gbc_cmbCustomers);
		
		JButton btnSelect = new JButton("Select customer");
		btnSelect.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				customer = cmbCustomers.getSelectedIndex();
				dispose();
			}
		});
		GridBagConstraints gbc_btnSelect = new GridBagConstraints();
		gbc_btnSelect.insets = new Insets(0, 0, 5, 0);
		gbc_btnSelect.gridx = 0;
		gbc_btnSelect.gridy = 4;
		contentPane.add(btnSelect, gbc_btnSelect);
		
		setVisible(true);
	}
	
	public int getSelectedCustomer(){
		return customer;
	}
	
	public boolean isCustomerSelected(){
		return customerSelected;
	}
}
