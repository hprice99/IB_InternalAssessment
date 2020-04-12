package controller;

import java.sql.*;

import javax.swing.JOptionPane;

public class DBConnect {

	// Connection to the database
	protected static Connection connection;
	
	// Queries to the database
	protected static Statement statement;
	
	// Results from queries
	protected static ResultSet results;
	
	// URL link to the database
	protected String url = "jdbc:mysql://localhost:3306/woods";
	
	// Username and password to access the database
	protected String username = "root";
	protected String password = "woodstennis";
	
	public boolean connected = false;
	
	public DBConnect(){
		try{
			// Load driver class
			Class.forName("com.mysql.jdbc.Driver");
			
			// Feedback for driver loading shown in console
			System.out.println("Driver loaded");
			
			// Connection to the database
			try{
				connection = DriverManager.getConnection(url, username, password);
			} catch(Exception ex){
				System.out.println("Connection not made");
				connection = DriverManager.getConnection(url, username, null);
			}
			
			connected = true;
			
			// Statement for the database
			statement = connection.createStatement();
			
		} catch(Exception ex){
			JOptionPane.showMessageDialog(null, "A connection to the database could not be established. Please open XAMPP and start the Apache and MySQL modules.", "Could not connnect to database", JOptionPane.ERROR_MESSAGE);
		}
	}
}
