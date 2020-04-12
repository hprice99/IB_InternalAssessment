package controller;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.regex.Pattern;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import view.MainFrame;

public class StaffController extends DBConnect {
	
	// Name and id of the staff member logged in
	private static int staffId;
	private static String fName;
	private static String lName;
	private static String username;
	private static boolean isAdmin;
	
	// Shows whether there is a staff member logged in
	private static boolean loggedIn = false;

	
	public static boolean logIn(String username, String attemptedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
		boolean loggedIn = false;
		
		String qryLogIn = "select * from staff where username = " + "'" + username + "'" + "LIMIT 1";
		
		try {
			// Execute the query
			results = statement.executeQuery(qryLogIn);
		} catch (Exception ex) {
			// Catch any errors and display the error
			System.out.println("Error 1: " + ex);
		}
		
		String encryptedDBPassword = null;
		
		try {
			// If the results set has data, check to see if the credentials entered by the user match those of the staff member stored in the table
			while(results.next()){
				// Get the encrypted password from the database
				encryptedDBPassword = results.getString("password");
				// Set the staff properties
				staffId = Integer.parseInt(results.getString("staffId"));
				fName = results.getString("fName");
				lName = results.getString("lName");
				if(Integer.parseInt(results.getString("admin")) == 1){
					isAdmin = true;
				} else{
					isAdmin = false;
				}
				StaffController.username = username;
			}
		} catch (Exception e) {
			// Output an error in the console
			System.out.println("Error 2: " + e);
		}
		
		// Create a byte array for the salt
		byte[] salt = null;
		
		// Get the linked list of existing salts so that the salt for the employee's account can be used to encrypt the password they enter
		LinkedList<SavedSalts> saltList = Serialise.deserialiseSalts();
		
		// Find the salt for matching the user
		for(int i = 0; i < saltList.size(); i = i + 1){
			if(saltList.get(i).getUsername().equals(username)){
				salt = saltList.get(i).getSalt();
			}
		}
		if(salt == null){
			JOptionPane.showMessageDialog(new JPanel(), "These account details are incorrect.", "Invalid details.", JOptionPane.ERROR_MESSAGE);
			return loggedIn;
		} else{
			// Encrypt the password that has been entered using the salt found for the user
			byte[] encryptedAttemptedPassword = getEncryptedPassword(attemptedPassword, salt);
			System.out.println("The database password is " + (encryptedDBPassword));
			System.out.println("The entered password is " + Arrays.toString(encryptedAttemptedPassword));
			
			// Determine if the passwords match
			loggedIn = encryptedDBPassword.equals(Arrays.toString(encryptedAttemptedPassword));
		}
		
		
		
		if(loggedIn){
			// Create the main frame
			MainFrame mf = new MainFrame();
			mf.setVisible(true);
			loggedIn = true;
			Initialise.logIn.dispose();
		}
		if(!loggedIn){
			JOptionPane.showMessageDialog(new JPanel(), "These account details are incorrect.", "Invalid details.", JOptionPane.ERROR_MESSAGE);
		}
		
		return loggedIn;
	} 
	
	public static byte[] getEncryptedPassword(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
		// Use PBKDF2 with SHA-1 as the hashing algorithm
		String algorithm = "PBKDF2WithHmacSHA1";
		// SHA-1 generates 160 bit hashes
		int derivedKeyLength = 160;
		
		// Perform 15000 encryption iterations
		int iterations = 15000;
		
		// Encrypt the password based on the byte array
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, derivedKeyLength);
		SecretKeyFactory f = SecretKeyFactory.getInstance(algorithm);
		return f.generateSecret(spec).getEncoded();
	}
	
	public static byte[] generateSalt() throws NoSuchAlgorithmException {
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		
		// Generate a random salt
		byte[] salt = new byte[8];
		random.nextBytes(salt);
		return salt;
	}
	
	public static String getStaffName(){
		return fName + " " + lName;
	}
	
	public static String getStaffFName(){
		return fName;
	}
	
	public static int getStaffId(String name){
		int staffId = -1;
		String qryGetStaffId = "select staffId from staff where fName = '" + name + "'";
		
		try{
			results = statement.executeQuery(qryGetStaffId);
		} catch (Exception ex) {
			// Catch any errors and display the error
			System.out.println("Error 1: " + ex);
		}
		
		try{
			while(results.next()){
				staffId = Integer.parseInt(results.getString("staffId"));
			}
		} catch (Exception ex) {
			// Catch any errors and display the error
			System.out.println("Error 2: " + ex);
		}
		return staffId;
	}
	
	public static boolean staffExists(String fName){
		boolean exists = false;
		String qryStaffExists = "select * from staff where fName = '" + fName + "' LIMIT 1";
		
		try{
			results = statement.executeQuery(qryStaffExists);
		} catch (Exception ex) {
			// Catch any errors and display the error
			System.out.println("Error 1: " + ex);
		}
		
		try{
			while(results.next()){
				exists = true;
			}
		} catch (Exception ex) {
			// Catch any errors and display the error
			System.out.println("Error 2: " + ex);
		}
		return exists;
	}

	public static String getStaffName(int id) {
		String staffName = null;
		
		String qryGetStaffName = "select fName, lName from staff where staffId = " + id;
		
		try{
			results = statement.executeQuery(qryGetStaffName);
		} catch (Exception ex) {
			// Catch any errors and display the error
			System.out.println("Error 1: " + ex);
		}
		
		try{
			while(results.next()){
				staffName = results.getString("fName") + " " + results.getString("lName");
			}
		} catch (Exception ex) {
			// Catch any errors and display the error
			System.out.println("Error 2: " + ex);
		}
		
		return staffName;
	}
	
	public static boolean isAdmin(){
		return isAdmin;
	}

	public static boolean validate(String fName, String lName, String username, String password, String confirmPassword, boolean isAdmin) {
		boolean registered = false;
		if(!Pattern.matches("[a-zA-Z]+", fName)){
			errorMessage("First name");
			return registered;
		} else if(!Pattern.matches("[a-zA-Z]+", lName)){
			errorMessage("Surname");
			return registered;
		} else if(!password.equals(confirmPassword)){
			errorMessage("Password");
			return registered;
		}
		
		registered = addStaff(fName, lName, username, password, isAdmin);
		return registered;
	}
	
	public static void errorMessage(String variable){
		if(variable.equals("Username")){
			JOptionPane.showMessageDialog(new JPanel(), "This username is already taken. Please enter a different username", "Username has been taken", JOptionPane.ERROR_MESSAGE);
		} else{
			JOptionPane.showMessageDialog(new JPanel(), variable + " is incorrect. Please make sure it is in the right format", variable + " is invalid", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private static boolean addStaff(String fName, String lName, String username, String password, boolean isAdmin){
		boolean registered = false;
		
		byte[] salt = null;
		byte[] encryptedPassword = null;
		
		try {
			// Generate a salt for the user's password
			salt = generateSalt();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		// Encrypt the password before saving into the database	
		try {
			encryptedPassword = getEncryptedPassword(password, salt);
			SavedSalts savedSalt = new SavedSalts(username, salt);
			// Save the salt and username combination in a linked list
			if(Serialise.deserialiseSalts() == null){
				LinkedList<SavedSalts> salts = new LinkedList<SavedSalts>();
				salts.add(savedSalt);
				Serialise.serialise(salts);
			} else{
				LinkedList<SavedSalts> salts = Serialise.deserialiseSalts();
				salts.add(savedSalt);
				Serialise.serialise(salts);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		
		int admin = -1;
		if(isAdmin){
			admin = 1;
		} else{
			admin = 0;
		}
		
		String qryAddStaff = "insert into staff (fName, lName, username, password, admin) values " + "('" + fName + "'," + "'" + lName + 
				"'," + "'" + username + "'," + "'" + Arrays.toString(encryptedPassword) + "'," + "'" + admin + "')"; 
		
		
		try {
			statement.executeUpdate(qryAddStaff);
			registered = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return registered;
	}
	
	public static ArrayList<String[]> getStaffList(){
		ArrayList<String[]> staffList = new ArrayList<String[]>();
		
		String qryGetStaffList = "SELECT staffId, username, fName, lName from staff";
		
		try{
			results = statement.executeQuery(qryGetStaffList);
		} catch(Exception ex){
			System.out.println("Error fetching staff: " + ex);
		}
		
		try{
			while(results.next()){
				String[] staff = new String[3];
				
				staff[0] = results.getString("staffId");
				staff[1] = results.getString("username");
				staff[2] = results.getString("fName") + " " + results.getString("lName");
				
				staffList.add(staff);
			}
		} catch(Exception ex){
			System.out.println("Error fetching staff: " + ex);
		}
		
		return staffList;
	}
}
