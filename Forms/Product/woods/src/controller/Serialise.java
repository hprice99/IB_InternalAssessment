package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

public class Serialise {

	private static final long serialVersionUID = 1L;
	private static String receiptFolder = "";
	private static String transactionFileFolder = "";
	
	public static void serialise(String folder, String fileName){
		// Serialize the data
		try{
			FileOutputStream fileOut = new FileOutputStream(fileName);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			if(fileName.equals("transactionLocation.ser")){
				transactionFileFolder = folder;
			} else if(fileName.equals("receiptFile.ser")){
				receiptFolder = folder;
			}
			out.writeObject(folder);
			out.close();
			fileOut.close();
			
			// Return to show that the data has been saved
			System.out.println("Data saved");
		} catch(IOException i){
			i.printStackTrace();
		}
	}
	
	public static String deserialise(String file){
		try{
			FileInputStream fileIn = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			if(file.equals("receiptFile.ser")){
				receiptFolder = in.readObject().toString();
				System.out.println(receiptFolder);
				in.close();
				fileIn.close();
				
				return receiptFolder;
			} else if(file.equals("transactionLocation.ser")){
				transactionFileFolder = in.readObject().toString();
				System.out.println(transactionFileFolder);
				in.close();
				fileIn.close();
				
				return transactionFileFolder;
			}
		} catch(IOException i){
			System.out.println("File exception " + i);
			
			return null;
		} catch(ClassNotFoundException c){
			System.out.println("Class not found");
			c.printStackTrace();
			return null;
		} catch(NullPointerException np){
			System.out.println("Null pointer exception");
			return null;
		} 
		return null;
	}
	
	public static LinkedList<SavedSalts> deserialiseSalts(){
		try{
			FileInputStream fileIn = new FileInputStream("salts.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			LinkedList<SavedSalts> salts = (LinkedList<SavedSalts>)in.readObject();
			in.close();
			fileIn.close();
			return salts;
		} catch(IOException i){
			System.out.println("File exception " + i);
			
			return null;
		} catch(NullPointerException np){
			System.out.println("Null pointer exception");
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null; 
	}

	public static void serialise(LinkedList<SavedSalts> salts) {
		try{
			FileOutputStream fileOut = new FileOutputStream("salts.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(salts);
			out.close();
			fileOut.close();
		} catch(IOException ex){
			System.out.println("Error in saving salt " + ex);
		}
	}
}
