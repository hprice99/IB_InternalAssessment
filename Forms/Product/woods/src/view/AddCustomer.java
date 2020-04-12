package view;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;

import controller.CustomerController;
import controller.FixtureController;
import controller.LessonController;

import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

public class AddCustomer extends JPanel {
	protected JTextField txtFName;
	protected JTextField txtAdd1;
	protected JTextField txtAdd2;
	protected JTextField txtSuburb;
	protected JTextField txtPCode;
	protected JTextField txtEmail;
	protected JLabel lblPhoneNumber;
	protected JTextField txtPhone;
	protected JLabel lblLessons;
	protected JComboBox cmbLessons;
	protected JTextArea txtLessons;
	protected JLabel lblFixtures;
	protected JComboBox cmbFixtures;
	protected JTextArea txtFixtures;
	protected JButton btnSubmit;
	protected JLabel lblLName;
	protected JTextField txtLName;
	protected JComboBox cmbState;
	
	protected String[] state = {"QLD", "NSW", "VIC", "TAS", "SA", "WA", "NT", "ACT"};
	protected JButton btnAddLesson;
	protected JButton btnAddFixture;
	protected String[][] lessons;
	protected String[][] fixtures;
	
	private ArrayList<String[]> fixturesAdded = new ArrayList<String[]>();
	private ArrayList<String[]> lessonsAdded = new ArrayList<String[]>();
	
	protected JLabel lblInstruction = new JLabel("Please use the form below to register a new customer.");

	/**
	 * Create the panel.
	 */
	public AddCustomer() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{116, 80, 63, -50, 65, 116, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		
		GridBagConstraints gbc_lblInstruction = new GridBagConstraints();
		gbc_lblInstruction.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblInstruction.gridwidth = 6;
		gbc_lblInstruction.insets = new Insets(0, 0, 5, 5);
		gbc_lblInstruction.gridx = 0;
		gbc_lblInstruction.gridy = 0;
		add(lblInstruction, gbc_lblInstruction);
		
		JLabel lblFName = new JLabel("First name");
		GridBagConstraints gbc_lblFName = new GridBagConstraints();
		gbc_lblFName.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblFName.insets = new Insets(0, 0, 5, 5);
		gbc_lblFName.gridx = 0;
		gbc_lblFName.gridy = 2;
		add(lblFName, gbc_lblFName);
		
		txtFName = new JTextField();
		GridBagConstraints gbc_txtFName = new GridBagConstraints();
		gbc_txtFName.gridwidth = 2;
		gbc_txtFName.insets = new Insets(0, 0, 5, 5);
		gbc_txtFName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFName.gridx = 1;
		gbc_txtFName.gridy = 2;
		add(txtFName, gbc_txtFName);
		txtFName.setColumns(10);
		
		lblLessons = new JLabel("Lessons");
		GridBagConstraints gbc_lblLessons = new GridBagConstraints();
		gbc_lblLessons.anchor = GridBagConstraints.WEST;
		gbc_lblLessons.insets = new Insets(0, 0, 5, 5);
		gbc_lblLessons.gridx = 4;
		gbc_lblLessons.gridy = 2;
		add(lblLessons, gbc_lblLessons);
		
		// Get the list of lessons
		lessons = getLessons();
		String[] lessonNames = getLessonNames(lessons);
		
		cmbLessons = new JComboBox();
		
		DefaultComboBoxModel lessonListModel;
		
		// Display a message if there are no free lessons
		if(lessonNames.length == 0){
			String[] noLessons = {"No lessons available"};
			lessonListModel = new DefaultComboBoxModel(noLessons);
			cmbLessons.setEditable(false);
		} else{
			lessonListModel = new DefaultComboBoxModel(lessonNames);
			cmbLessons.setModel(lessonListModel);
		}
	
		GridBagConstraints gbc_cmbLessons = new GridBagConstraints();
		gbc_cmbLessons.insets = new Insets(0, 0, 5, 5);
		gbc_cmbLessons.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbLessons.gridx = 5;
		gbc_cmbLessons.gridy = 2;
		add(cmbLessons, gbc_cmbLessons);
		
		btnAddLesson = new JButton("Add lesson");
		btnAddLesson.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int index = cmbLessons.getSelectedIndex();
				String lessonName = lessons[index][1];
				lessonsAdded.add(lessons[index]);
				
				// Only insert new line once one line already exists
				if(lessonsAdded.size() == 1){
					txtLessons.append(lessonName);
				} else{
					txtLessons.append("\n" + lessonName);
				}
				cmbLessons.remove(index);
			}
		});
		GridBagConstraints gbc_btnAddLesson = new GridBagConstraints();
		gbc_btnAddLesson.insets = new Insets(0, 0, 5, 0);
		gbc_btnAddLesson.gridx = 6;
		gbc_btnAddLesson.gridy = 2;
		add(btnAddLesson, gbc_btnAddLesson);
		
		lblLName = new JLabel("Surname");
		GridBagConstraints gbc_lblLName = new GridBagConstraints();
		gbc_lblLName.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblLName.insets = new Insets(0, 0, 5, 5);
		gbc_lblLName.gridx = 0;
		gbc_lblLName.gridy = 3;
		add(lblLName, gbc_lblLName);
		
		txtLName = new JTextField();
		GridBagConstraints gbc_txtLName = new GridBagConstraints();
		gbc_txtLName.gridwidth = 2;
		gbc_txtLName.anchor = GridBagConstraints.NORTH;
		gbc_txtLName.insets = new Insets(0, 0, 5, 5);
		gbc_txtLName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtLName.gridx = 1;
		gbc_txtLName.gridy = 3;
		add(txtLName, gbc_txtLName);
		txtLName.setColumns(10);
		
		txtLessons = new JTextArea();
		txtLessons.setEditable(false);
		GridBagConstraints gbc_txtLessons = new GridBagConstraints();
		gbc_txtLessons.insets = new Insets(0, 0, 5, 5);
		gbc_txtLessons.fill = GridBagConstraints.BOTH;
		gbc_txtLessons.gridx = 5;
		gbc_txtLessons.gridy = 3;
		add(txtLessons, gbc_txtLessons);
		
		JLabel lblAddress = new JLabel("Address");
		GridBagConstraints gbc_lblAddress = new GridBagConstraints();
		gbc_lblAddress.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblAddress.insets = new Insets(0, 0, 5, 5);
		gbc_lblAddress.gridx = 0;
		gbc_lblAddress.gridy = 4;
		add(lblAddress, gbc_lblAddress);
		
		txtAdd1 = new JTextField();
		txtAdd1.setToolTipText("Address line 1");
		GridBagConstraints gbc_txtAdd1 = new GridBagConstraints();
		gbc_txtAdd1.gridwidth = 2;
		gbc_txtAdd1.insets = new Insets(0, 0, 5, 5);
		gbc_txtAdd1.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtAdd1.gridx = 1;
		gbc_txtAdd1.gridy = 4;
		add(txtAdd1, gbc_txtAdd1);
		txtAdd1.setColumns(10);
		
		txtAdd2 = new JTextField();
		txtAdd2.setToolTipText("Address line 2");
		GridBagConstraints gbc_txtAdd2 = new GridBagConstraints();
		gbc_txtAdd2.gridwidth = 2;
		gbc_txtAdd2.anchor = GridBagConstraints.NORTH;
		gbc_txtAdd2.insets = new Insets(0, 0, 5, 5);
		gbc_txtAdd2.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtAdd2.gridx = 1;
		gbc_txtAdd2.gridy = 5;
		add(txtAdd2, gbc_txtAdd2);
		txtAdd2.setColumns(10);
		
		txtSuburb = new JTextField();
		txtSuburb.setToolTipText("Suburb");
		GridBagConstraints gbc_txtSuburb = new GridBagConstraints();
		gbc_txtSuburb.anchor = GridBagConstraints.NORTH;
		gbc_txtSuburb.insets = new Insets(0, 0, 5, 5);
		gbc_txtSuburb.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSuburb.gridx = 1;
		gbc_txtSuburb.gridy = 6;
		add(txtSuburb, gbc_txtSuburb);
		txtSuburb.setColumns(10);
		
		txtPCode = new JTextField();
		txtPCode.setToolTipText("Post code");
		GridBagConstraints gbc_txtPCode = new GridBagConstraints();
		gbc_txtPCode.anchor = GridBagConstraints.NORTH;
		gbc_txtPCode.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPCode.insets = new Insets(0, 0, 5, 5);
		gbc_txtPCode.gridx = 2;
		gbc_txtPCode.gridy = 6;
		add(txtPCode, gbc_txtPCode);
		txtPCode.setColumns(10);
		
		lblFixtures = new JLabel("Fixtures");
		GridBagConstraints gbc_lblFixtures = new GridBagConstraints();
		gbc_lblFixtures.anchor = GridBagConstraints.WEST;
		gbc_lblFixtures.insets = new Insets(0, 0, 5, 5);
		gbc_lblFixtures.gridx = 4;
		gbc_lblFixtures.gridy = 6;
		add(lblFixtures, gbc_lblFixtures);
		
		// Get the list of fixtures
		fixtures = getFixtures();
		String[] fixtureNames = getFixtureNames(fixtures);
		
		// Display a message if there are no free fixtures
		if(fixtureNames.length == 0){
			String[] noFixtures = {"No fixtures available"};
			cmbFixtures = new JComboBox(noFixtures);
			cmbFixtures.setEditable(false);
		} else{
			cmbFixtures = new JComboBox(fixtureNames);
		}

		GridBagConstraints gbc_cmbFixtures = new GridBagConstraints();
		gbc_cmbFixtures.insets = new Insets(0, 0, 5, 5);
		gbc_cmbFixtures.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbFixtures.gridx = 5;
		gbc_cmbFixtures.gridy = 6;
		add(cmbFixtures, gbc_cmbFixtures);
		
		btnAddFixture = new JButton("Add fixture");
		btnAddFixture.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int index = cmbFixtures.getSelectedIndex();
				String fixtureName = fixtures[index][1];
				fixturesAdded.add(fixtures[index]);
				
				// Only insert new line once one line already exists
				if(fixturesAdded.size() == 1){
					txtFixtures.append(fixtureName);
				} else{
					txtFixtures.append("\n" + fixtureName);
				}
			}
		});
		GridBagConstraints gbc_btnAddFixture = new GridBagConstraints();
		gbc_btnAddFixture.insets = new Insets(0, 0, 5, 0);
		gbc_btnAddFixture.gridx = 6;
		gbc_btnAddFixture.gridy = 6;
		add(btnAddFixture, gbc_btnAddFixture);
		
		cmbState = new JComboBox(state);
		GridBagConstraints gbc_cmbState = new GridBagConstraints();
		gbc_cmbState.gridwidth = 2;
		gbc_cmbState.insets = new Insets(0, 0, 5, 5);
		gbc_cmbState.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbState.gridx = 1;
		gbc_cmbState.gridy = 7;
		add(cmbState, gbc_cmbState);
		
		txtFixtures = new JTextArea();
		txtFixtures.setEditable(false);
		GridBagConstraints gbc_txtFixtures = new GridBagConstraints();
		gbc_txtFixtures.anchor = GridBagConstraints.NORTH;
		gbc_txtFixtures.insets = new Insets(0, 0, 5, 5);
		gbc_txtFixtures.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFixtures.gridx = 5;
		gbc_txtFixtures.gridy = 7;
		add(txtFixtures, gbc_txtFixtures);
		
		JLabel lblEmailAddress = new JLabel("Email address");
		GridBagConstraints gbc_lblEmailAddress = new GridBagConstraints();
		gbc_lblEmailAddress.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblEmailAddress.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmailAddress.gridx = 0;
		gbc_lblEmailAddress.gridy = 8;
		add(lblEmailAddress, gbc_lblEmailAddress);
		
		txtEmail = new JTextField();
		GridBagConstraints gbc_txtEmail = new GridBagConstraints();
		gbc_txtEmail.gridwidth = 2;
		gbc_txtEmail.insets = new Insets(0, 0, 5, 5);
		gbc_txtEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtEmail.gridx = 1;
		gbc_txtEmail.gridy = 8;
		add(txtEmail, gbc_txtEmail);
		txtEmail.setColumns(10);
		
		btnSubmit = new JButton("Submit");
		btnSubmit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String fName = txtFName.getText();
				String lName = txtLName.getText();
				String add1 = txtAdd1.getText();
				String add2 = txtAdd2.getText();
				String suburb = txtSuburb.getText();
				String pCode = txtPCode.getText();
				String state = cmbState.getSelectedItem().toString();
				String phone = txtPhone.getText();
				String email = txtEmail.getText();
				
				int customerId = CustomerController.validateCustomer(fName, lName, add1, add2, suburb, pCode, state, phone, email);
				if(customerId > 0){
					JOptionPane.showMessageDialog(null, "Successfully registered.");
									
					// Add customer to customerLessons table
					if(!lessonsAdded.isEmpty()){
						String[][] lessonsAddedArray = lessonsAdded.toArray(new String[0][0]);
						for(int i = 0; i < lessonsAddedArray.length; i = i + 1){
							int lessonId = Integer.parseInt(lessonsAddedArray[i][0]);
							LessonController.addPlayer(lessonId, customerId);
						}
					}
					
					// Add customer to customerFixtures table
					if(!fixturesAdded.isEmpty()){
						String[][] fixturesAddedArray = fixturesAdded.toArray(new String[0][0]);
						for(int i = 0; i < fixturesAddedArray.length; i = i + 1){
							int fixtureId = Integer.parseInt(fixturesAddedArray[i][0]);
							FixtureController.addPlayer(fixtureId, customerId);
						}
					}
					clearForm();
				}
			}
		});
		
		lblPhoneNumber = new JLabel("Phone number");
		GridBagConstraints gbc_lblPhoneNumber = new GridBagConstraints();
		gbc_lblPhoneNumber.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblPhoneNumber.insets = new Insets(0, 0, 5, 5);
		gbc_lblPhoneNumber.gridx = 0;
		gbc_lblPhoneNumber.gridy = 9;
		add(lblPhoneNumber, gbc_lblPhoneNumber);
		
		txtPhone = new JTextField();
		GridBagConstraints gbc_txtPhone = new GridBagConstraints();
		gbc_txtPhone.gridwidth = 2;
		gbc_txtPhone.insets = new Insets(0, 0, 5, 5);
		gbc_txtPhone.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPhone.gridx = 1;
		gbc_txtPhone.gridy = 9;
		add(txtPhone, gbc_txtPhone);
		txtPhone.setColumns(10);
		GridBagConstraints gbc_btnSubmit = new GridBagConstraints();
		gbc_btnSubmit.insets = new Insets(0, 0, 0, 5);
		gbc_btnSubmit.gridx = 2;
		gbc_btnSubmit.gridy = 10;
		add(btnSubmit, gbc_btnSubmit);

	}
	
	private static String[][] getLessons(){
		String[][] lessons = LessonController.getLessons().toArray(new String[0][0]);
		
		return lessons;
	}

	private static String[] getLessonNames(String[][] lessons){
		String[] lessonNames = new String[lessons.length];
		
		for(int i = 0; i < lessonNames.length; i = i + 1){
			lessonNames[i] = lessons[i][1];
		}
		return lessonNames;
	}
	
	private static String[][] getFixtures(){
		String[][] fixtures = FixtureController.getFixtures().toArray(new String[0][0]);
		
		return fixtures;
	}
	
	private static String[] getFixtureNames(String[][] fixtures){
		String[] fixtureNames = new String[fixtures.length];
		
		for(int i = 0; i < fixtureNames.length; i = i + 1){
			fixtureNames[i] = fixtures[i][1];
		}
		return fixtureNames;
	}
	
	public void clearForm(){
		// Reset the text fields
		txtFName.setText("");
		txtLName.setText("");
		txtAdd1.setText("");
		txtAdd2.setText("");
		txtPCode.setText("");
		txtSuburb.setText("");
		txtEmail.setText("");
		txtPhone.setText("");
		txtLessons.setText("");
		txtFixtures.setText("");
		cmbState.setSelectedIndex(0);
		
		// Reset the lesson and fixture comboboxes
		String[][] newLessons = getLessons();
		String[] newLessonNames = getLessonNames(newLessons);
		DefaultComboBoxModel lessonModel = new DefaultComboBoxModel(newLessonNames);
		cmbLessons.setModel(lessonModel);
		
		String[][] newFixtures = getFixtures();
		String[] newFixtureNames = getFixtureNames(newFixtures);
		DefaultComboBoxModel fixtureModel = new DefaultComboBoxModel(newFixtureNames);
		cmbFixtures.setModel(fixtureModel);
		
		lessonsAdded.clear();
		fixturesAdded.clear();
	}
}
