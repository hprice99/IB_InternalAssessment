package view;

import javax.swing.JPanel;

import controller.Initialise;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LogOut extends JPanel {

	/**
	 * Create the panel.
	 */
	public LogOut() {
		setLayout(null);
		
		JLabel lblAreYouSure = new JLabel("Are you sure you want to log out?");
		lblAreYouSure.setBounds(12, 13, 385, 16);
		add(lblAreYouSure);
		
		JButton btnLogOut = new JButton("Log out");
		btnLogOut.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				MainFrame.getMainFrame().setVisible(false);
				MainFrame.getMainFrame().dispose();
				
				LogIn logIn = new LogIn();
				Initialise.logIn = logIn;
				logIn.setVisible(true);
			}
		});
		btnLogOut.setBounds(22, 42, 97, 25);
		add(btnLogOut);

	}
}
