package view;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.GridBagLayout;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import controller.FixtureController;
import controller.Initialise;
import controller.LessonController;
import controller.StaffController;
import javax.swing.border.EmptyBorder;

public class Welcome extends JPanel {
	private JTable tblLessons;
	private JTable tblFixtures;
	private NoEditTableModel lessonModel;
	private NoEditTableModel fixtureModel;
	private JLabel lblWelcome;
	private JLabel lblLessons;
	/**
	 * Create the panel.
	 */
	public Welcome() {
		setBorder(new EmptyBorder(15, 15, 15, 15));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 105, 0, 110, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		lblWelcome = new JLabel("Welcome " + StaffController.getStaffFName());
		GridBagConstraints gbc_lblWelcome = new GridBagConstraints();
		gbc_lblWelcome.insets = new Insets(0, 0, 5, 0);
		gbc_lblWelcome.gridwidth = 2;
		gbc_lblWelcome.gridx = 0;
		gbc_lblWelcome.gridy = 0;
		add(lblWelcome, gbc_lblWelcome);
		
		Date today = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd/MM/yyyy");
		
		double term = Initialise.getCurrentTerm();
		JLabel lblDate;
		if(term == 1.5){
			lblDate = new JLabel("Today is " + dateFormat.format(today) + ". " + "It is the Easter holidays");
		} else if(term == 2.5){
			lblDate = new JLabel("Today is " + dateFormat.format(today) + ". " + "It is the Winter holidays");
		} else if(term == 3.5){
			lblDate = new JLabel("Today is " + dateFormat.format(today) + ". " + "It is the Spring holidays");
		} else if(term == 4.5){
			lblDate = new JLabel("Today is " + dateFormat.format(today) + ". " + "It is the Summer holidays");
		} else{
			lblDate = new JLabel("Today is " + dateFormat.format(today) + ". " + "It is term " + (int)term +  ".");
		}
		GridBagConstraints gbc_lblDate = new GridBagConstraints();
		gbc_lblDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblDate.gridx = 0;
		gbc_lblDate.gridy = 1;
		add(lblDate, gbc_lblDate);
		
		
		
		JLabel lblLessons_1 = new JLabel("Lessons");
		GridBagConstraints gbc_lblLessons_1 = new GridBagConstraints();
		gbc_lblLessons_1.anchor = GridBagConstraints.WEST;
		gbc_lblLessons_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblLessons_1.gridx = 0;
		gbc_lblLessons_1.gridy = 3;
		add(lblLessons_1, gbc_lblLessons_1);
				
		// Table to display the lesson list for the day
		// Header of each column
		String[] lessonColumnHeaders = {"Coach", "Name", "Location", "Time", "Duration", "Number of players"};
		
		lessonModel = new NoEditTableModel(0, lessonColumnHeaders.length);
		lessonModel.setColumnIdentifiers(lessonColumnHeaders);
		
		// Get the lesson data
		ArrayList<String[]> lessons = LessonController.getLessonsToday();
		int lessonCount = 0;
		for(int i = 0; i < lessons.size(); i = i + 1){
			if(lessons.get(i)[0].equals(StaffController.getStaffName())){
				lessonCount = lessonCount + 1;
			}
			lessonModel.addRow(lessons.get(i));
		}
		
		tblLessons = new JTable(lessonModel);
		// Highlight the rows that correspond to the current staff member's lessons
		tblLessons.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
		    @Override
		    public Component getTableCellRendererComponent(JTable table,
		            Object value, boolean isSelected, boolean hasFocus, int row, int col) {

		        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

		        String name = (String)table.getModel().getValueAt(row, 0);
		        if (StaffController.getStaffName().equals(name)) {
		            setBackground(Color.YELLOW);
		            setForeground(Color.BLACK);
		        } else {
		            setBackground(table.getBackground());
		            setForeground(table.getForeground());
		        }       
		        return this;
		    }   
		});
		GridBagConstraints gbc_tblLessons = new GridBagConstraints();
		gbc_tblLessons.insets = new Insets(0, 0, 5, 0);
		gbc_tblLessons.gridwidth = 2;
		gbc_tblLessons.fill = GridBagConstraints.BOTH;
		gbc_tblLessons.gridx = 0;
		gbc_tblLessons.gridy = 4;
		
		JScrollPane lessonScrollPane = new JScrollPane(tblLessons);
		add(lessonScrollPane, gbc_tblLessons);
		
		
		if(lessonCount == 1){
			lblLessons = new JLabel("You have " + lessonCount + " lesson today.");
		} else{
			lblLessons = new JLabel("You have " + lessonCount + " lessons today.");
		}
		GridBagConstraints gbc_lblLessons = new GridBagConstraints();
		gbc_lblLessons.insets = new Insets(0, 0, 5, 0);
		gbc_lblLessons.gridwidth = 2;
		gbc_lblLessons.gridx = 0;
		gbc_lblLessons.gridy = 2;
		add(lblLessons, gbc_lblLessons);
		
		
		// Table to display the fixture list for the day
		// Header of each column
		String[] fixtureColumnHeaders = {"Name", "Location", "Time", "Duration", "Number of players"};
		
		fixtureModel = new NoEditTableModel(0, fixtureColumnHeaders.length);
		fixtureModel.setColumnIdentifiers(fixtureColumnHeaders);
		
		// Get the lesson data
		ArrayList<String[]> fixtures = FixtureController.getFixturesToday();
		for(int i = 0; i < fixtures.size(); i = i + 1){
			fixtureModel.addRow(fixtures.get(i));
		}
		
		JLabel lblFixtures = new JLabel("Fixtures");
		GridBagConstraints gbc_lblFixtures = new GridBagConstraints();
		gbc_lblFixtures.anchor = GridBagConstraints.WEST;
		gbc_lblFixtures.insets = new Insets(0, 0, 5, 5);
		gbc_lblFixtures.gridx = 0;
		gbc_lblFixtures.gridy = 5;
		add(lblFixtures, gbc_lblFixtures);
		
		// Table to display the list of fixtures for the day
		tblFixtures = new JTable(fixtureModel);
		GridBagConstraints gbc_tblFixtures = new GridBagConstraints();
		gbc_tblFixtures.gridwidth = 2;
		gbc_tblFixtures.fill = GridBagConstraints.BOTH;
		gbc_tblFixtures.gridx = 0;
		gbc_tblFixtures.gridy = 6;
		
		JScrollPane fixtureScrollPane = new JScrollPane(tblFixtures);
		
		add(fixtureScrollPane, gbc_tblFixtures);

	}

	public void clearForm(){
		for(int i = 0; i < lessonModel.getRowCount(); i = i + 1){
			lessonModel.removeRow(i);
		}
		
		// Get the lesson data
		ArrayList<String[]> lessons = LessonController.getLessonsToday();
		int lessonCount = 0;
		for(int i = 0; i < lessons.size(); i = i + 1){
			if(lessons.get(i)[0].equals(StaffController.getStaffName())){
				lessonCount = lessonCount + 1;
			}
			lessonModel.addRow(lessons.get(i));
		}
		tblLessons.setModel(lessonModel);
		tblLessons.repaint();
		tblLessons.revalidate();
		
		for(int i = 0; i < fixtureModel.getRowCount(); i = i + 1){
			fixtureModel.removeRow(i);
		}
		
		
		// Get the lesson data
		ArrayList<String[]> fixtures = FixtureController.getFixturesToday();
		for(int i = 0; i < fixtures.size(); i = i + 1){
			fixtureModel.addRow(fixtures.get(i));
		}
		tblFixtures.setModel(fixtureModel);
		tblFixtures.repaint();
		tblFixtures.revalidate();
		
		if(lessonCount == 1){
			lblLessons = new JLabel("You have " + lessonCount + " lesson today.");
		} else{
			lblLessons = new JLabel("You have " + lessonCount + " lessons today.");
		}
		lblLessons.repaint();
		lblLessons.revalidate();
	}
}
