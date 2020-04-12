package view;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Desktop;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class EmailStatements extends JPanel {

	/**
	 * Create the panel.
	 */
	public EmailStatements() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{576, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblInstructions = new JLabel("Click the button below to send statements to customers with outstanding payments");
		GridBagConstraints gbc_lblInstructions = new GridBagConstraints();
		gbc_lblInstructions.insets = new Insets(0, 0, 5, 0);
		gbc_lblInstructions.gridx = 0;
		gbc_lblInstructions.gridy = 0;
		add(lblInstructions, gbc_lblInstructions);
		
		JButton btnSendStatements = new JButton("Send statements");
		btnSendStatements.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.out.println("Button clicked");
				// Run the StatementMailMerge executable file
				Desktop desktop = Desktop.getDesktop();
				File exe = new File("StatementMailMerge.exe");
				if(exe.exists()){
					try {
						System.out.println("File found");
						desktop.open(exe);
						JOptionPane.showMessageDialog(new JFrame(),"Statements successfully sent");
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null, "Executable file not found to send statements", "Executable file not found", JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					}
				}
			}
		});
		GridBagConstraints gbc_btnSendStatements = new GridBagConstraints();
		gbc_btnSendStatements.gridx = 0;
		gbc_btnSendStatements.gridy = 2;
		add(btnSendStatements, gbc_btnSendStatements);

	}

}
