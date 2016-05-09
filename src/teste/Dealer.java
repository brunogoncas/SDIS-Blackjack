package teste;

import java.util.ArrayList;

public class Dealer {
	
	private int totalPoints;
	
	private ArrayList<Card> hand;
	
	public Dealer(){
		
		this.totalPoints = 0;
		
		this.hand = new ArrayList<Card>();
	}
	
	public int getDealerPoints(){
		
		return totalPoints;
	}
	
	public void resetDealerPoints(){
		
		this.totalPoints = 0;
	}
	
	public ArrayList<Card> getDealerHand(){
		
		return hand;
		
	}
	
	public void addCard(Card c){
		
		hand.add(c);
	}
	
	public void addDealerPoints(int Points){
		
		this.totalPoints += Points; 
		
	}
	
	public boolean dealerBusted(){
		
		return(this.totalPoints > 21);
	}
	
	public boolean dealerBlackJack(){
		
		return(this.totalPoints == 21);
	}

}
