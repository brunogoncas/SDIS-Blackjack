package teste;

public class Card {
	
	private String figure;
	private String suit;
	
	public Card(String figure, String suit){
		
		this.figure = figure;
		this.suit = suit;
	}
	
	public String getSuit(){
		
		return suit;
	}
	
	public String getFigure(){
		
		return figure;
	}
	
	public Boolean isFigure(){
		
		return (this.getFigure().equals("King") 
				|| this.getFigure().equals("Jack")
				|| this.getFigure().equals("Queen")
				|| this.getFigure().equals("Ace"));
	}
	
	public int getCardPoints(int sum){
		
		if(!this.isFigure())
			return Integer.valueOf(this.getFigure());
		else if(this.getFigure().equals("Ace")){
			if(sum <= 10)
				return 11;
			else
				return 1;
		}
		else
			return 10;
	}

}
