package dto;

public class Room {
	
	private int id;
	private String name, password;
	
	public Room(){
		
	}
	
	public Room(int id, String name, String password){
		
		this.id = id;
		this.name = name;
		this.password = password;
	}
	
	public int getID(){
		
		return id;
	}
	
	public void setID(int id){
		
		this.id = id;
	}
	
	public String getName(){
		
		return name;
	}
	
	public void setName(String name){
		
		this.name = name;
	}
	
	public String getPAssword(){
		
		return password;
	}
	
	public void setPassword(String password){
		
		this.password = password;
	}

}
