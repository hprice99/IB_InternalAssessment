package view;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;

import controller.StaffController;

import javax.swing.JPasswordField;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class AddStaff extends JPanel {
	private JTextField txtFName = new JTextField();
	private JTextField txtLName = new JTextField();
	private JTextField txtUsername = new JTextField();
	private JPasswordField pswPassword = new JPasswordField();
	private JPasswordField pswConfirm = new JPasswordField();
	private JCheckBox chckbxAdmin = new JCheckBox("Grant admin privileges");

	/**
	 * Create the panel.
	 */
	public AddStaff() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{128, 227, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblPleaseCompleteThe = new JLabel("Please complete the form below to add a new employee to the system.");
		GridBagConstraints gbc_lblPleaseCompleteThe = new GridBagConstraints();
		gbc_lblPleaseCompleteThe.gridwidth = 2;
		gbc_lblPleaseCompleteThe.insets = new Insets(0, 0, 5, 0);
		gbc_lblPleaseCompleteThe.gridx = 0;
		gbc_lblPleaseCompleteThe.gridy = 0;
		add(lblPleaseCompleteThe, gbc_lblPleaseCompleteThe);
		
		JLabel lblFirstName = new JLabel("First name");
		GridBagConstraints gbc_lblFirstName = new GridBagConstraints();
		gbc_lblFirstName.anchor = GridBagConstraints.EAST;
		gbc_lblFirstName.insets = new Insets(0, 0, 5, 5);
		gbc_lblFirstName.gridx = 0;
		gbc_lblFirstName.gridy = 2;
		add(lblFirstName, gbc_lblFirstName);
		
		GridBagConstraints gbc_txtFName = new GridBagConstraints();
		gbc_txtFName.insets = new Insets(0, 0, 5, 0);
		gbc_txtFName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFName.gridx = 1;
		gbc_txtFName.gridy = 2;
		add(txtFName, gbc_txtFName);
		txtFName.setColumns(10);
		
		JLabel lblSurname = new JLabel("Surname");
		GridBagConstraints gbc_lblSurname = new GridBagConstraints();
		gbc_lblSurname.anchor = GridBagConstraints.EAST;
		gbc_lblSurname.insets = new Insets(0, 0, 5, 5);
		gbc_lblSurname.gridx = 0;
		gbc_lblSurname.gridy = 3;
		add(lblSurname, gbc_lblSurname);
		
		GridBagConstraints gbc_txtLName = new GridBagConstraints();
		gbc_txtLName.insets = new Insets(0, 0, 5, 0);
		gbc_txtLName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtLName.gridx = 1;
		gbc_txtLName.gridy = 3;
		add(txtLName, gbc_txtLName);
		txtLName.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username");
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.anchor = GridBagConstraints.EAST;
		gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsername.gridx = 0;
		gbc_lblUsername.gridy = 4;
		add(lblUsername, gbc_lblUsername);
		
		GridBagConstraints gbc_txtUsername = new GridBagConstraints();
		gbc_txtUsername.anchor = GridBagConstraints.NORTH;
		gbc_txtUsername.insets = new Insets(0, 0, 5, 0);
		gbc_txtUsername.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtUsername.gridx = 1;
		gbc_txtUsername.gridy = 4;
		add(txtUsername, gbc_txtUsername);
		txtUsername.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.EAST;
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 0;
		gbc_lblPassword.gridy = 5;
		add(lblPassword, gbc_lblPassword);
		
		pswPassword.setText("");
		GridBagConstraints gbc_pswPassword = new GridBagConstraints();
		gbc_pswPassword.insets = new Insets(0, 0, 5, 0);
		gbc_pswPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_pswPassword.gridx = 1;
		gbc_pswPassword.gridy = 5;
		add(pswPassword, gbc_pswPassword);
		
		JLabel lblConfirmPassword = new JLabel("Confirm password");
		GridBagConstraints gbc_lblConfirmPassword = new GridBagConstraints();
		gbc_lblConfirmPassword.anchor = GridBagConstraints.EAST;
		gbc_lblConfirmPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblConfirmPassword.gridx = 0;
		gbc_lblConfirmPassword.gridy = 6;
		add(lblConfirmPassword, gbc_lblConfirmPassword);
		
		GridBagConstraints gbc_pswConfirm = new GridBagConstraints();
		gbc_pswConfirm.insets = new Insets(0, 0, 5, 0);
		gbc_pswConfirm.fill = GridBagConstraints.HORIZONTAL;
		gbc_pswConfirm.gridx = 1;
		gbc_pswConfirm.gridy = 6;
		add(pswConfirm, gbc_pswConfirm);
		
		
		GridBagConstraints gbc_chckbxAdmin = new GridBagConstraints();
		gbc_chckbxAdmin.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxAdmin.gridx = 1;
		gbc_chckbxAdmin.gridy = 7;
		add(chckbxAdmin, gbc_chckbxAdmin);
		
		JButton btnAddEmployee = new JButton("Add employee");
		btnAddEmployee.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String fName = txtFName.getText();
				String lName = txtLName.getText();
				String username = txtUsername.getText();
				String password = new String(pswPassword.getPassword());
				String confirmPassword = new String(pswConfirm.getPassword());
				boolean isAdmin = chckbxAdmin.isSelected();
				
				
				boolean registered = StaffController.validate(fName, lName, username, password, confirmPassword, isAdmin);
				if(registered){
					JOptionPane.showMessageDialog(null, "Successfully registered.");
					clearForm();
				}
			}
		});
		GridBagConstraints gbc_btnAddEmployee = new GridBagConstraints();
		gbc_btnAddEmployee.gridx = 1;
		gbc_btnAddEmployee.gridy = 8;
		add(btnAddEmployee, gbc_btnAddEmployee);

	}

	public void clearForm(){
		txtFName.setText("");
		txtLName.setText("");
		txtUsername.setText("");
		pswPassword.setText("");
		pswConfirm.setText("");
		chckbxAdmin.setSelected(false);
	}
}
