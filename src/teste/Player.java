package teste;

import java.util.ArrayList;

public class Player {
	
	private int totalPoints;
	private int totalChips;
	private String name;
	
	private ArrayList<Card> hand;
	
	public Player(String name){
		
		this.totalPoints = 0;
		this.totalChips = 1000;
		this.name = name;
		
		this.hand = new ArrayList<Card>();
	}
	
	public String getName(){
		
		return name;
	}
	
	public int getPlayerPoints(){
		
		return totalPoints;
	}
	
	public void resetPlayerPoints(){
		
		this.totalPoints = 0;
	}
	
	public int getTotalChips(){
		
		return totalChips;
		
	}
	
	public ArrayList<Card> getHand(){
		
		return hand;
		
	}
	
	public void addCard(Card c){
		
		hand.add(c);
	}

	public void addChips(int chips){
		
		this.totalChips += chips;
	}
	
	public void removeChips(int chips){
		
		this.totalChips -= chips;
	}
	
	public boolean playerBusted(){
		
		return this.totalPoints > 21;
	}
	
	public boolean playerBlackJack(){
		
		return this.totalPoints == 21;
	}

	public void addPlayerPoints(int points){
		
		this.totalPoints += points;
	}


}
