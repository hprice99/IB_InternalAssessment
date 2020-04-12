package controller;

public class SavedSalts implements java.io.Serializable{
	String username;
	byte[] salt;
	
	private static final long serialVersionUID = 1L;
	
	public SavedSalts(String username, byte[] salt){
		this.username = username;
		this.salt = salt; 
	}
	
	public byte[] getSalt(){
		return salt;
	}
	
	public String getUsername(){
		return username;
	}
}
