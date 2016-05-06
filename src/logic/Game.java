package logic;

public class Game {
	
	public Player player;
	public Dealer dealer;
	
	public Deck deck;
	
	public boolean gameOver;
	
	public Game(){
		
		this.player = new Player("George");
		this.dealer = new Dealer();
		
		this.deck = new Deck();
		
		this.gameOver = false;
	}
	
	public void hit(){
		
		//int index = randomGenerator.nextInt(deck.getDeck().size()-2);
		int index = 0 + (int)(Math.random() * 51);

		Card card = deck.getDeck().get(index);
		this.player.addPlayerPoints(card.getCardPoints(player.getPlayerPoints()));
		this.player.getHand().add(card);
		
		System.out.println("You were dealt a: " + card.getFigure() + " of " + card.getSuit());
	}
	
	public void stand(){
		
		this.player.addPlayerPoints(0);
		
	}
	
	public void dealerHit(){
		
	int index = 0 + (int)(Math.random() * 51);
		
	Card card = deck.getDeck().get(index);
	
	this.dealer.addDealerPoints(card.getCardPoints(dealer.getDealerPoints()));
	this.dealer.getDealerHand().add(card);		
	
	System.out.println("Dealer was dealt a: " + card.getFigure() + " of " + card.getSuit());
	
	}

}
