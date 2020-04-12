package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.Initialise;
import controller.StaffController;

import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LogIn extends JFrame {

	private JPanel contentPane;
	private JTextField txtUsername;
	private JPasswordField pswPassword;

	/**
	 * Create the frame.
	 */
	public LogIn() {
		setTitle("Log in");
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{91, 76, 211, 0};
		gbl_contentPane.rowHeights = new int[]{49, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblWelcome = new JLabel("<html>Welcome to the Woods Tennis payments management program. Please enter your credentials to log in.</html>\r\n");
		GridBagConstraints gbc_lblWelcome = new GridBagConstraints();
		gbc_lblWelcome.fill = GridBagConstraints.HORIZONTAL;
		lblWelcome.setMaximumSize(new Dimension(250, 32));
		gbc_lblWelcome.insets = new Insets(0, 0, 5, 0);
		gbc_lblWelcome.gridwidth = 3;
		gbc_lblWelcome.gridx = 0;
		gbc_lblWelcome.gridy = 0;
		contentPane.add(lblWelcome, gbc_lblWelcome);
		
		JLabel lblUsername = new JLabel("Username");
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.anchor = GridBagConstraints.WEST;
		gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsername.gridx = 0;
		gbc_lblUsername.gridy = 2;
		contentPane.add(lblUsername, gbc_lblUsername);
		
		txtUsername = new JTextField();
		txtUsername.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// Let the staff member log in if they press enter
				if(e.getKeyCode()== KeyEvent.VK_ENTER) {
					// Get the username and password of the user
					String username = txtUsername.getText();
					String password = new String(pswPassword.getPassword());
					
					
					try {
						StaffController.logIn(username, password);
					} catch (NoSuchAlgorithmException e1) {
						e1.printStackTrace();
					} catch (InvalidKeySpecException e1) {
						e1.printStackTrace();
					} 
				}
			}
		});
		GridBagConstraints gbc_txtUsername = new GridBagConstraints();
		gbc_txtUsername.insets = new Insets(0, 0, 5, 5);
		gbc_txtUsername.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtUsername.gridx = 1;
		gbc_txtUsername.gridy = 2;
		contentPane.add(txtUsername, gbc_txtUsername);
		txtUsername.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.WEST;
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 0;
		gbc_lblPassword.gridy = 3;
		contentPane.add(lblPassword, gbc_lblPassword);
		
		pswPassword = new JPasswordField();
		pswPassword.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// Let the staff member log in if they press enter
				if(e.getKeyCode()== KeyEvent.VK_ENTER) {
					// Get the username and password of the user
					String username = txtUsername.getText();
					String password = new String(pswPassword.getPassword());
					
					try {
						StaffController.logIn(username, password);
					} catch (NoSuchAlgorithmException e1) {
						e1.printStackTrace();
					} catch (InvalidKeySpecException e1) {
						e1.printStackTrace();
					} 
				}
			}
		});
		GridBagConstraints gbc_pswPassword = new GridBagConstraints();
		gbc_pswPassword.insets = new Insets(0, 0, 5, 5);
		gbc_pswPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_pswPassword.gridx = 1;
		gbc_pswPassword.gridy = 3;
		contentPane.add(pswPassword, gbc_pswPassword);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Get the username and password of the user
				String username = txtUsername.getText();
				String password = new String(pswPassword.getPassword());
				
				// StaffController.logIn(username, password);
				
				try {
					StaffController.logIn(username, password);
				} catch (NoSuchAlgorithmException e1) {
					e1.printStackTrace();
				} catch (InvalidKeySpecException e1) {
					e1.printStackTrace();
				} 
			}
		});
		GridBagConstraints gbc_btnSubmit = new GridBagConstraints();
		gbc_btnSubmit.insets = new Insets(0, 0, 0, 5);
		gbc_btnSubmit.gridx = 1;
		gbc_btnSubmit.gridy = 5;
		contentPane.add(btnSubmit, gbc_btnSubmit);
		
		setIcon();
	}
	
	private void setIcon(){
		// Set the application's icon
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Woods logo square.png")));
	}
}
