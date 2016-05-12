package dto;

public class Player {
	
	private int chips,id, betmoney;
	private String name;
	private String password;
	
	public Player(){
		
	}
	
	public Player(int id, int chips, String name, String password, int betmoney){
		
		this.id = id;
		this.chips = chips;
		this.name = name;
		this.password = password;
		this.betmoney = betmoney;
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
	
	public String getPassword(){
		
		return password;
	}

	public void setPassword(String password){
		
		this.password = password;
	}
	
	public int getChips(){	
		return chips;	
	}
	
	public void setChips(int chips){	
		this.chips = chips;	
	}
	
	public void setBetMoney(int betmoney){	
		this.betmoney = betmoney;	
	}

}