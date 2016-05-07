package dto;

public class Card {
	
	private String suit;
	private String figure;
	 
	public Card()
	{
	 
	}
	 
	public Card(String suit, String figure)
	{
	super();
	this.suit = suit;
	this.figure = figure;
	}
	 
	public String getSuit()
	{
	return suit;
	}
	 
	public void setSuit(String suit)
	{
	this.suit = suit;
	}
	 
	public String getFigure()
	{
	return figure;
	}
	 
	public void setFigure(String figure)
	{
	this.figure = figure;
	}
	 
	@Override
	public String toString()
	{
	return "Card [suit=" + suit + ", figure=" + figure+ "]";
	}
}
