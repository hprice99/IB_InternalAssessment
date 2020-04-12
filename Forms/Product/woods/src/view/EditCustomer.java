package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

import controller.CustomerController;
import controller.FixtureController;
import controller.LessonController;
import controller.StaffController;
import controller.TransactionController;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

public class EditCustomer extends JFrame {
	private JPanel contentPane;

	private JPanel contentPane_1 = new JPanel();
	private JTextField txtFName;
	private JTextField txtLName;
	private JTextField txtAdd1;
	private JTextField txtAdd2;
	private JTextField txtSuburb;
	private JTextField txtPCode;
	private JLabel lblEmailAddress;
	private JTextField txtEmail;
	private JLabel lblPhoneNumber;
	private JTextField txtPhone;
	private JLabel lblLessons;
	private JTable tblLessons;
	private JButton btnAddLesson;
	private JLabel lblFixtures;
	private JTable tblFixtures;
	private JComboBox cmbFixtures;
	private JButton btnAddFixture;
	private JLabel lblOutstandingPayments;
	private JTable tblOutstandingPayments;
	private JLabel lblPastPayments;
	private JTable tblPastPayments;
	private JButton btnUpdateDetails;
	private JComboBox cmbLessons;
	
	private ArrayList<String[]> lessonsAdded = new ArrayList<String[]>();
	private JButton btnSaveToCsv;
	/**
	 * Create the frame.
	 */
	public EditCustomer(int id) {
		String[] customer = CustomerController.getCustomer(id);
		setTitle(CustomerController.getCustomerName(id) + "\'s profile");
		setBounds(100, 100, 1021, 758);
		
		contentPane_1.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		GridBagLayout gbl___tmpField = new GridBagLayout();
		gbl___tmpField.columnWidths = new int[]{106, 114, 0, 42, 52, 207, 240, 0};
		gbl___tmpField.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 129, 0, 0, 122, 0, 0, 0};
		gbl___tmpField.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl___tmpField.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane_1.setLayout(gbl___tmpField);
		
		JLabel lblPleaseUseThe = new JLabel("Please use the form below to edit the customer's details");
		GridBagConstraints gbc_lblPleaseUseThe = new GridBagConstraints();
		gbc_lblPleaseUseThe.anchor = GridBagConstraints.WEST;
		gbc_lblPleaseUseThe.gridwidth = 3;
		gbc_lblPleaseUseThe.insets = new Insets(0, 0, 5, 5);
		gbc_lblPleaseUseThe.gridx = 0;
		gbc_lblPleaseUseThe.gridy = 0;
		contentPane_1.add(lblPleaseUseThe, gbc_lblPleaseUseThe);
		
		JLabel lblFirstName = new JLabel("First name");
		GridBagConstraints gbc_lblFirstName = new GridBagConstraints();
		gbc_lblFirstName.anchor = GridBagConstraints.WEST;
		gbc_lblFirstName.insets = new Insets(0, 0, 5, 5);
		gbc_lblFirstName.gridx = 0;
		gbc_lblFirstName.gridy = 2;
		contentPane_1.add(lblFirstName, gbc_lblFirstName);
		
		txtFName = new JTextField(customer[0]);
		txtFName.setEditable(false);
		GridBagConstraints gbc_txtFName = new GridBagConstraints();
		gbc_txtFName.gridwidth = 2;
		gbc_txtFName.anchor = GridBagConstraints.NORTH;
		gbc_txtFName.insets = new Insets(0, 0, 5, 5);
		gbc_txtFName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFName.gridx = 1;
		gbc_txtFName.gridy = 2;
		contentPane_1.add(txtFName, gbc_txtFName);
		txtFName.setColumns(10);
		
		lblLessons = new JLabel("Lessons");
		GridBagConstraints gbc_lblLessons = new GridBagConstraints();
		gbc_lblLessons.anchor = GridBagConstraints.WEST;
		gbc_lblLessons.insets = new Insets(0, 0, 5, 5);
		gbc_lblLessons.gridx = 4;
		gbc_lblLessons.gridy = 2;
		contentPane_1.add(lblLessons, gbc_lblLessons);
		
		String[] lessonColumns = {"Lesson name", "Day", "Time", "Location", ""};
		ColEditTableModel lessonModel = new ColEditTableModel(0, lessonColumns.length);
		lessonModel.setColumnIdentifiers(lessonColumns);
		
		ArrayList<String[]> lessons = LessonController.getCustomerLessons(id);
		
		for(int i = 0; i < lessons.size(); i = i + 1){
			String[] lesson = lessons.get(i);
			String[] tableLesson = {lesson[1], lesson[2], lesson[5], lesson[6], "Delete lesson"};
			lessonModel.addRow(tableLesson);
		}
		tblLessons = new JTable(lessonModel);
		
		
		// Functionality for delete button
		Action deleteLesson = new AbstractAction()
		{
			@Override
		    public void actionPerformed(ActionEvent e)
		    {
				// Options for user response
				Object[] options = {"Yes", "No"};
				
				int x = -1;
				x = JOptionPane.showOptionDialog(new JFrame(), "Would you like to remove this lesson?", "Remove lesson?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
				if(x == 0){
					JTable table = (JTable)e.getSource();
			        int modelRow = Integer.valueOf( e.getActionCommand() );
			        ((ColEditTableModel)table.getModel()).removeRow(modelRow);
			        LessonController.removeCustomerLesson(Integer.parseInt(lessons.get(modelRow)[0]), id);
			        lessons.remove(modelRow);
				}
		    }
		};
		
		ButtonColumn buttonColumnLesson = new ButtonColumn(tblLessons, deleteLesson, 4);
		buttonColumnLesson.setMnemonic(KeyEvent.VK_D);
		
		tblLessons.getColumnModel().getColumn(0).setPreferredWidth(110);
		tblLessons.getColumnModel().getColumn(1).setPreferredWidth(60);
		tblLessons.getColumnModel().getColumn(2).setPreferredWidth(60);
		tblLessons.getColumnModel().getColumn(3).setPreferredWidth(110);
		tblLessons.getColumnModel().getColumn(4).setPreferredWidth(100);
				
		GridBagConstraints gbc_tblLessons = new GridBagConstraints();
		gbc_tblLessons.gridheight = 3;
		gbc_tblLessons.gridwidth = 2;
		gbc_tblLessons.insets = new Insets(0, 0, 5, 0);
		gbc_tblLessons.fill = GridBagConstraints.BOTH;
		gbc_tblLessons.gridx = 5;
		gbc_tblLessons.gridy = 2;
		JScrollPane lessonScrollPane = new JScrollPane(tblLessons);
		contentPane_1.add(lessonScrollPane, gbc_tblLessons);
		
		JLabel lblSurname = new JLabel("Surname");
		GridBagConstraints gbc_lblSurname = new GridBagConstraints();
		gbc_lblSurname.anchor = GridBagConstraints.WEST;
		gbc_lblSurname.insets = new Insets(0, 0, 5, 5);
		gbc_lblSurname.gridx = 0;
		gbc_lblSurname.gridy = 3;
		contentPane_1.add(lblSurname, gbc_lblSurname);
		
		txtLName = new JTextField(customer[1]);
		txtLName.setEditable(false);
		GridBagConstraints gbc_txtLName = new GridBagConstraints();
		gbc_txtLName.gridwidth = 2;
		gbc_txtLName.insets = new Insets(0, 0, 5, 5);
		gbc_txtLName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtLName.gridx = 1;
		gbc_txtLName.gridy = 3;
		contentPane_1.add(txtLName, gbc_txtLName);
		txtLName.setColumns(10);
	
		JLabel lblAddress = new JLabel("Address");
		GridBagConstraints gbc_lblAddress = new GridBagConstraints();
		gbc_lblAddress.anchor = GridBagConstraints.WEST;
		gbc_lblAddress.insets = new Insets(0, 0, 5, 5);
		gbc_lblAddress.gridx = 0;
		gbc_lblAddress.gridy = 4;
		contentPane_1.add(lblAddress, gbc_lblAddress);
		
		txtAdd1 = new JTextField(customer[2]);
		GridBagConstraints gbc_txtAdd1 = new GridBagConstraints();
		gbc_txtAdd1.gridwidth = 2;
		gbc_txtAdd1.insets = new Insets(0, 0, 5, 5);
		gbc_txtAdd1.anchor = GridBagConstraints.NORTH;
		gbc_txtAdd1.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtAdd1.gridx = 1;
		gbc_txtAdd1.gridy = 4;
		contentPane_1.add(txtAdd1, gbc_txtAdd1);
		txtAdd1.setColumns(10);
		
		txtAdd2 = new JTextField(customer[3]);
		GridBagConstraints gbc_txtAdd2 = new GridBagConstraints();
		gbc_txtAdd2.insets = new Insets(0, 0, 5, 5);
		gbc_txtAdd2.gridwidth = 2;
		gbc_txtAdd2.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtAdd2.gridx = 1;
		gbc_txtAdd2.gridy = 5;
		contentPane_1.add(txtAdd2, gbc_txtAdd2);
		txtAdd2.setColumns(10);
		
		
		
		cmbLessons = new JComboBox();
		DefaultComboBoxModel lessonListModel;
		GridBagConstraints gbc_cmbLessons = new GridBagConstraints();
		gbc_cmbLessons.insets = new Insets(0, 0, 5, 5);
		gbc_cmbLessons.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbLessons.gridx = 5;
		gbc_cmbLessons.gridy = 5;
		contentPane_1.add(cmbLessons, gbc_cmbLessons);
		
		// Get the list of lessons
		ArrayList<String[]> lessonList = getLessons();
		// String[] lessonNames = getLessonNames(lessonList);	
		
		// Display a message if there are no free lessons
		String[] lessonNameArray = null;
		int[] lessonIdArray = null;
		ArrayList<String> lessonNames = new ArrayList<String>();
		ArrayList<Integer> lessonIds = new ArrayList<Integer>();
		if(lessonList.size() == 0){
			String[] noLessons = {"No lessons available"};
			lessonListModel = new DefaultComboBoxModel(noLessons);
			cmbLessons.setModel(lessonListModel);
			cmbLessons.setEditable(false);
		} else{
			for(int i = 0; i < lessonList.size(); i = i + 1){
				int lessonId = Integer.parseInt(lessonList.get(i)[0]);
				boolean found = false;
				for(int j = 0; j < lessons.size(); j = j + 1){
					if(Integer.parseInt(lessons.get(j)[0]) == lessonId){
						found = true;
					}
				}
				if(!found){
					lessonNames.add(lessonList.get(i)[1]);
					lessonIds.add(Integer.parseInt(lessonList.get(i)[0]));
				}
			}
			lessonNameArray = new String[lessonNames.size()];
			lessonIdArray = new int[lessonIds.size()];
			lessonNameArray = lessonNames.toArray(lessonNameArray);
			for(int i = 0; i < lessonIdArray.length; i = i + 1){
				lessonIdArray[i] = lessonIds.get(i);
			}
			lessonListModel = new DefaultComboBoxModel(lessonNameArray);
			cmbLessons.setModel(lessonListModel);
		}
		
		if(lessonNameArray.length > 0){
			btnAddLesson = new JButton("Add lesson");
			int index = cmbLessons.getSelectedIndex();
			int lessonId = lessonIdArray[index];
			btnAddLesson.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					String[] newLesson = LessonController.getLessonDetails(lessonId);
					String[] lessonTableRow = {newLesson[1], newLesson[2], newLesson[5], newLesson[6], "Delete lesson"};
					
					LessonController.addPlayer(Integer.parseInt(newLesson[0]), id);
					lessonModel.addRow(lessonTableRow);
					cmbLessons.remove(index);
					tblLessons.repaint();
					tblLessons.revalidate();
				}
			});
			GridBagConstraints gbc_btnAddLesson = new GridBagConstraints();
			gbc_btnAddLesson.anchor = GridBagConstraints.SOUTH;
			gbc_btnAddLesson.insets = new Insets(0, 0, 5, 0);
			gbc_btnAddLesson.gridx = 6;
			gbc_btnAddLesson.gridy = 5;
			contentPane_1.add(btnAddLesson, gbc_btnAddLesson);
		}
		
	
		
		txtSuburb = new JTextField(customer[4]);
		GridBagConstraints gbc_txtSuburb = new GridBagConstraints();
		gbc_txtSuburb.anchor = GridBagConstraints.NORTH;
		gbc_txtSuburb.insets = new Insets(0, 0, 5, 5);
		gbc_txtSuburb.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSuburb.gridx = 1;
		gbc_txtSuburb.gridy = 6;
		contentPane_1.add(txtSuburb, gbc_txtSuburb);
		txtSuburb.setColumns(10);
		
		txtPCode = new JTextField(customer[5]);
		GridBagConstraints gbc_txtPCode = new GridBagConstraints();
		gbc_txtPCode.insets = new Insets(0, 0, 5, 5);
		gbc_txtPCode.anchor = GridBagConstraints.NORTH;
		gbc_txtPCode.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPCode.gridx = 2;
		gbc_txtPCode.gridy = 6;
		contentPane_1.add(txtPCode, gbc_txtPCode);
		txtPCode.setColumns(10);
		
		String[] state = {"QLD", "NSW", "VIC", "TAS", "SA", "WA", "NT", "ACT"};
		
		JComboBox cmbState = new JComboBox(state);
		GridBagConstraints gbc_cmbState = new GridBagConstraints();
		gbc_cmbState.insets = new Insets(0, 0, 5, 5);
		gbc_cmbState.gridwidth = 2;
		gbc_cmbState.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbState.gridx = 1;
		gbc_cmbState.gridy = 7;
		contentPane_1.add(cmbState, gbc_cmbState);
		
		switch(customer[6]){
		case "QLD": cmbState.setSelectedIndex(0);
					break;
		case "NSW": cmbState.setSelectedIndex(1);
					break;
		case "VIC": cmbState.setSelectedIndex(2);
					break;
		case "TAS": cmbState.setSelectedIndex(3);
					break;
		case "SA": cmbState.setSelectedIndex(4);
					break;
		case "WA": cmbState.setSelectedIndex(5);
					break;
		case "NT": cmbState.setSelectedIndex(6);
					break;
		case "ACT": cmbState.setSelectedIndex(7);
					break;
		}
		
		lblFixtures = new JLabel("Fixtures");
		GridBagConstraints gbc_lblFixtures = new GridBagConstraints();
		gbc_lblFixtures.anchor = GridBagConstraints.WEST;
		gbc_lblFixtures.insets = new Insets(0, 0, 5, 5);
		gbc_lblFixtures.gridx = 4;
		gbc_lblFixtures.gridy = 7;
		contentPane_1.add(lblFixtures, gbc_lblFixtures);
		
		String[] fixtureColumns = {"Fixture name", "Day", "Time", "Location", ""};
		ColEditTableModel fixtureModel = new ColEditTableModel(0, fixtureColumns.length);
		fixtureModel.setColumnIdentifiers(fixtureColumns);
		
		ArrayList<String[]> fixtures = FixtureController.getCustomerFixtures(id);
		
		for(int i = 0; i < fixtures.size(); i = i + 1){
			String[] fixture = fixtures.get(i);
			String[] tableFixture = {fixture[1], fixture[2], fixture[5], fixture[6], "Delete fixture"};
			fixtureModel.addRow(tableFixture);
		}
		
		tblFixtures = new JTable(fixtureModel);
		
				
		// Functionality for delete button
		Action deleteFixture = new AbstractAction()
		{
			@Override
		    public void actionPerformed(ActionEvent e)
		    {
				// Options for user response
				Object[] options = {"Yes", "No"};
				
				int x = -1;
				x = JOptionPane.showOptionDialog(new JFrame(), "Would you like to remove this fixture?", "Remove fixture?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
				if(x == 0){
					JTable table = (JTable)e.getSource();
			        int modelRow = Integer.valueOf( e.getActionCommand() );
			        ((ColEditTableModel)table.getModel()).removeRow(modelRow);
			        FixtureController.removeCustomerFixture(Integer.parseInt(fixtures.get(modelRow)[0]), id);
			        fixtures.remove(modelRow);
				}
		    }
		};
		
		ButtonColumn buttonColumnFixture = new ButtonColumn(tblFixtures, deleteFixture, 4);
		buttonColumnFixture.setMnemonic(KeyEvent.VK_D);
		
		tblFixtures.getColumnModel().getColumn(0).setPreferredWidth(110);
		tblFixtures.getColumnModel().getColumn(1).setPreferredWidth(60);
		tblFixtures.getColumnModel().getColumn(2).setPreferredWidth(60);
		tblFixtures.getColumnModel().getColumn(3).setPreferredWidth(110);
		tblFixtures.getColumnModel().getColumn(4).setPreferredWidth(100);
		
		GridBagConstraints gbc_tblFixtures = new GridBagConstraints();
		gbc_tblFixtures.gridheight = 2;
		gbc_tblFixtures.gridwidth = 2;
		gbc_tblFixtures.insets = new Insets(0, 0, 5, 0);
		gbc_tblFixtures.fill = GridBagConstraints.BOTH;
		gbc_tblFixtures.gridx = 5;
		gbc_tblFixtures.gridy = 7;
		JScrollPane fixtureScrollPane = new JScrollPane(tblFixtures);
		contentPane_1.add(fixtureScrollPane, gbc_tblFixtures);
		
		lblEmailAddress = new JLabel("Email address");
		GridBagConstraints gbc_lblEmailAddress = new GridBagConstraints();
		gbc_lblEmailAddress.anchor = GridBagConstraints.WEST;
		gbc_lblEmailAddress.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmailAddress.gridx = 0;
		gbc_lblEmailAddress.gridy = 8;
		contentPane_1.add(lblEmailAddress, gbc_lblEmailAddress);
		
		txtEmail = new JTextField(customer[7]);
		GridBagConstraints gbc_txtEmail = new GridBagConstraints();
		gbc_txtEmail.insets = new Insets(0, 0, 5, 5);
		gbc_txtEmail.gridwidth = 2;
		gbc_txtEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtEmail.gridx = 1;
		gbc_txtEmail.gridy = 8;
		contentPane_1.add(txtEmail, gbc_txtEmail);
		txtEmail.setColumns(10);
		
		
		cmbFixtures = new JComboBox();
		DefaultComboBoxModel fixtureListModel;
		// Get the list of lessons
		ArrayList<String[]> fixtureList = getFixtures();	
		String[] fixtureNameArray = null;
		int[] fixtureIdArray = null;
		ArrayList<String> fixtureNames = new ArrayList<String>();
		ArrayList<Integer> fixtureIds = new ArrayList<Integer>();
		if(fixtureList.size() == 0){
			String[] noFixtures = {"No fixtures available"};
			fixtureListModel = new DefaultComboBoxModel(noFixtures);
			cmbFixtures.setModel(fixtureListModel);
			cmbFixtures.setEditable(false);
		} else{
			for(int i = 0; i < fixtureList.size(); i = i + 1){
				int fixtureId = Integer.parseInt(fixtureList.get(i)[0]);
				boolean found = false;
				for(int j = 0; j < fixtures.size(); j = j + 1){
					if(Integer.parseInt(fixtures.get(j)[0]) == fixtureId){
						found = true;
					}
				}
				if(!found){
					fixtureNames.add(fixtureList.get(i)[1]);
					fixtureIds.add(Integer.parseInt(fixtureList.get(i)[0]));
				}
			}
			fixtureIdArray = new int[fixtureIds.size()];
			for(int i = 0; i < fixtureIds.size(); i = i + 1){
				fixtureIdArray[i] = fixtureIds.get(i);
			}
			fixtureNameArray = new String[fixtureNames.size()];
			fixtureNameArray = fixtureNames.toArray(fixtureNameArray);
			fixtureListModel = new DefaultComboBoxModel(fixtureNameArray);
			cmbFixtures.setModel(fixtureListModel);
		}
		
		if(fixtureIdArray.length > 0){
			btnAddFixture = new JButton("Add fixture");
			int fixtureId = fixtureIdArray[cmbFixtures.getSelectedIndex()];
			btnAddFixture.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					String[] newFixture = FixtureController.getFixtureInfo(fixtureId);
					int fixtureId = Integer.parseInt(newFixture[0]);
					String[] newFixtureDetails = FixtureController.getFixtureInfo(fixtureId);
					String[] fixtureTableRow = {newFixture[1], newFixtureDetails[2], newFixtureDetails[5], newFixtureDetails[6], "Delete fixture"};
					
					FixtureController.addPlayer(fixtureId, id);
					fixtureModel.addRow(fixtureTableRow);
					tblFixtures.repaint();
					tblFixtures.revalidate();
				}
			});
			GridBagConstraints gbc_btnAddFixture = new GridBagConstraints();
			gbc_btnAddFixture.insets = new Insets(0, 0, 5, 0);
			gbc_btnAddFixture.gridx = 6;
			gbc_btnAddFixture.gridy = 9;
			contentPane_1.add(btnAddFixture, gbc_btnAddFixture);
		} else{
			String[] noFixtures = {"No fixtures available"};
			fixtureListModel = new DefaultComboBoxModel(noFixtures);
			cmbFixtures.setModel(fixtureListModel);
			cmbFixtures.setEditable(false);	
		}
		
		lblPhoneNumber = new JLabel("Phone number");
		GridBagConstraints gbc_lblPhoneNumber = new GridBagConstraints();
		gbc_lblPhoneNumber.anchor = GridBagConstraints.WEST;
		gbc_lblPhoneNumber.insets = new Insets(0, 0, 5, 5);
		gbc_lblPhoneNumber.gridx = 0;
		gbc_lblPhoneNumber.gridy = 9;
		contentPane_1.add(lblPhoneNumber, gbc_lblPhoneNumber);
		
		txtPhone = new JTextField(customer[8]);
		GridBagConstraints gbc_txtPhone = new GridBagConstraints();
		gbc_txtPhone.gridwidth = 2;
		gbc_txtPhone.anchor = GridBagConstraints.NORTH;
		gbc_txtPhone.insets = new Insets(0, 0, 5, 5);
		gbc_txtPhone.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPhone.gridx = 1;
		gbc_txtPhone.gridy = 9;
		contentPane_1.add(txtPhone, gbc_txtPhone);
		txtPhone.setColumns(10);
		
		GridBagConstraints gbc_cmbFixtures = new GridBagConstraints();
		gbc_cmbFixtures.insets = new Insets(0, 0, 5, 5);
		gbc_cmbFixtures.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbFixtures.gridx = 5;
		gbc_cmbFixtures.gridy = 9;
		contentPane_1.add(cmbFixtures, gbc_cmbFixtures);
		
		// Only show the add button if there are fixtures available
		if(fixtureNameArray.length > 0){
			btnAddFixture = new JButton("Add fixture");
			btnAddFixture.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					int index = cmbFixtures.getSelectedIndex();
					System.out.println(index);
					String[] newFixture = fixtureList.get(index);
					int fixtureId = Integer.parseInt(newFixture[0]);
					String[] newFixtureDetails = FixtureController.getFixtureInfo(fixtureId);
					String[] fixtureTableRow = {newFixture[1], newFixtureDetails[2], newFixtureDetails[5], newFixtureDetails[6], "Delete fixture"};
					
					FixtureController.addPlayer(fixtureId, id);
					fixtureModel.addRow(fixtureTableRow);
					tblFixtures.repaint();
					tblFixtures.revalidate();
				}
			});
			GridBagConstraints gbc_btnAddFixture = new GridBagConstraints();
			gbc_btnAddFixture.insets = new Insets(0, 0, 5, 0);
			gbc_btnAddFixture.gridx = 6;
			gbc_btnAddFixture.gridy = 9;
			contentPane_1.add(btnAddFixture, gbc_btnAddFixture);
		}
		
		
		btnUpdateDetails = new JButton("Update details");
		btnUpdateDetails.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String add1 = txtAdd1.getText();
				String add2 = txtAdd2.getText();
				String suburb = txtSuburb.getText();
				String pCode = txtPCode.getText();
				String state = cmbState.getSelectedItem().toString();
				String phone = txtPhone.getText();
				String email = txtEmail.getText();
				
				boolean updated = CustomerController.validateCustomer(add1, add2, suburb, pCode, state, phone, email, id);
				if(updated){
					JOptionPane.showMessageDialog(new JFrame(), "Customer details updated.", "Customer details updated", JOptionPane.INFORMATION_MESSAGE);
				} 
			}
		});
		GridBagConstraints gbc_btnUpdateDetails = new GridBagConstraints();
		gbc_btnUpdateDetails.insets = new Insets(0, 0, 5, 5);
		gbc_btnUpdateDetails.gridx = 2;
		gbc_btnUpdateDetails.gridy = 10;
		contentPane_1.add(btnUpdateDetails, gbc_btnUpdateDetails);
		
		lblOutstandingPayments = new JLabel("Outstanding payments");
		GridBagConstraints gbc_lblOutstandingPayments = new GridBagConstraints();
		gbc_lblOutstandingPayments.anchor = GridBagConstraints.WEST;
		gbc_lblOutstandingPayments.gridwidth = 2;
		gbc_lblOutstandingPayments.insets = new Insets(0, 0, 5, 5);
		gbc_lblOutstandingPayments.gridx = 0;
		gbc_lblOutstandingPayments.gridy = 12;
		contentPane_1.add(lblOutstandingPayments, gbc_lblOutstandingPayments);
		
		String[] outstandingPaymentColumns = {"Product", "Type", "Date due", "Status", "Amount"};
		
		NoEditTableModel outstandingPaymentModel = new NoEditTableModel(0, outstandingPaymentColumns.length);
		
		ArrayList<String[]> outstandingLessons = LessonController.getOutstandingLessons(id);
		
		for(int i = 0; i < outstandingLessons.size(); i = i + 1){
			outstandingPaymentModel.addRow(outstandingLessons.get(i));
		}
		
		ArrayList<String[]> outstandingFixtures = FixtureController.getOutstandingFixtures(id);
		
		for(int i = 0; i < outstandingFixtures.size(); i = i + 1){
			outstandingPaymentModel.addRow(outstandingFixtures.get(i));
		}
		
		outstandingPaymentModel.setColumnIdentifiers(outstandingPaymentColumns);
		
		tblOutstandingPayments = new JTable(outstandingPaymentModel);
		tblOutstandingPayments.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
		    @Override
		    public Component getTableCellRendererComponent(JTable table,
		            Object value, boolean isSelected, boolean hasFocus, int row, int col) {

		        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

		        String status = (String)table.getModel().getValueAt(row, 3);
		        if (status.equals("Overdue")) {
		            setBackground(Color.RED);
		            setForeground(Color.WHITE);
		        } else {
		            setBackground(table.getBackground());
		            setForeground(table.getForeground());
		        }       
		        return this;
		    }   
		});
		GridBagConstraints gbc_tblOutstandingPayments = new GridBagConstraints();
		gbc_tblOutstandingPayments.gridwidth = 7;
		gbc_tblOutstandingPayments.insets = new Insets(0, 0, 5, 0);
		gbc_tblOutstandingPayments.fill = GridBagConstraints.BOTH;
		gbc_tblOutstandingPayments.gridx = 0;
		gbc_tblOutstandingPayments.gridy = 13;
		
		JScrollPane outstandingPaymentScroll = new JScrollPane(tblOutstandingPayments);
		contentPane_1.add(outstandingPaymentScroll, gbc_tblOutstandingPayments);
		
		lblPastPayments = new JLabel("Past payments");
		GridBagConstraints gbc_lblPastPayments = new GridBagConstraints();
		gbc_lblPastPayments.anchor = GridBagConstraints.WEST;
		gbc_lblPastPayments.insets = new Insets(0, 0, 5, 5);
		gbc_lblPastPayments.gridx = 0;
		gbc_lblPastPayments.gridy = 15;
		contentPane_1.add(lblPastPayments, gbc_lblPastPayments);
		
		String[] pastPaymentColumns = {"Product", "Category", "Date", "Amount", "Payment method", "Transaction number"};
		
		NoEditTableModel pastPaymentModel = new NoEditTableModel(0, pastPaymentColumns.length);
		
		pastPaymentModel.setColumnIdentifiers(pastPaymentColumns);

		ArrayList<String[]> pastTransactions = TransactionController.getCustomerTransactions(id);
		
		for(int i = 0; i < pastTransactions.size(); i = i + 1){
			pastPaymentModel.addRow(pastTransactions.get(i));
		}
		tblPastPayments = new JTable(pastPaymentModel);
		GridBagConstraints gbc_tblPastPayments = new GridBagConstraints();
		gbc_tblPastPayments.insets = new Insets(0, 0, 5, 0);
		gbc_tblPastPayments.gridwidth = 7;
		gbc_tblPastPayments.fill = GridBagConstraints.BOTH;
		gbc_tblPastPayments.gridx = 0;
		gbc_tblPastPayments.gridy = 16;
		JScrollPane pastPaymentScroll = new JScrollPane(tblPastPayments);
		tblPastPayments.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int index = tblPastPayments.getSelectedRow();
				int transactionId = Integer.parseInt(tblPastPayments.getModel().getValueAt(index, 5).toString());
				Receipt receipt = new Receipt(transactionId);
				receipt.setVisible(true);
			}
		});
		contentPane_1.add(pastPaymentScroll, gbc_tblPastPayments);
		
		btnSaveToCsv = new JButton("Save to CSV");
		btnSaveToCsv.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				ArrayList<String[]> transactions = new ArrayList<String[]>();
				
				String name = customer[0] + " " + customer[1];
				
				// Save the past transactions into a new arraylist
				for(int i = 0; i < pastTransactions.size(); i = i + 1){
					String[] transaction = new String[6];
					
					transaction[0] = pastTransactions.get(i)[5];
					transaction[1] = pastTransactions.get(i)[2];
					transaction[2] = name;
					transaction[3] = pastTransactions.get(i)[0];
					String amountString = pastTransactions.get(i)[3];
					amountString = amountString.substring(1);
					transaction[4] = amountString;
					transaction[5] = pastTransactions.get(i)[4];
					
					transactions.add(transaction);
				}
				
				TransactionController.createCSV(transactions, name);
			}
		});
		GridBagConstraints gbc_btnSaveToCsv = new GridBagConstraints();
		gbc_btnSaveToCsv.insets = new Insets(0, 0, 5, 0);
		gbc_btnSaveToCsv.gridx = 6;
		gbc_btnSaveToCsv.gridy = 17;
		contentPane_1.add(btnSaveToCsv, gbc_btnSaveToCsv);
		
		this.setContentPane(contentPane_1);
		
		setIcon();
	}
	
	private void setIcon(){
		// Set the application's icon
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Woods logo square.png")));
	}
	
	private static ArrayList<String[]> getLessons(){
		ArrayList<String[]> lessons = LessonController.getLessons();
		
		return lessons;
	}
	
	private static ArrayList<String[]> getFixtures(){
		ArrayList<String[]> fixtures = FixtureController.getFixtures();
		
		return fixtures;
	}
}
