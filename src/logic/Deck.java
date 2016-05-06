package logic;

import java.util.ArrayList;

public class Deck {
	
	private ArrayList<Card> deck= new ArrayList <Card>();
	
	public Deck(){
		
		Card AceSpades = new Card("Ace", "Spades");
		Card AceHearts = new Card("Ace", "Hearts");
		Card AceDiamonds = new Card("Ace", "Diamonds");
		Card AceClubs = new Card("Ace", "Clubs");
		
		Card KingSpades = new Card("King", "Spades");
		Card KingHearts = new Card("King", "Hearts");
		Card KingDiamonds = new Card("King", "Diamonds");
		Card KingClubs = new Card("King", "Clubs");
		
		Card QueenSpades = new Card("Queen", "Spades");
		Card QueenHearts = new Card("Queen", "Hearts");
		Card QueenDiamonds = new Card("Queen", "Diamonds");
		Card QueenClubs = new Card("Queen", "Clubs");
		
		Card JackSpades = new Card("Jack", "Spades");
		Card JackHearts = new Card("Jack", "Hearts");
		Card JackDiamonds = new Card("Jack", "Diamonds");
		Card JackClubs = new Card("Jack", "Clubs");
		
		Card TenSpades = new Card("10", "Spades");
		Card TenHearts = new Card("10", "Hearts");
		Card TenDiamonds = new Card("10", "Diamonds");
		Card TenClubs = new Card("10", "Clubs");
		
		Card NineSpades = new Card("9", "Spades");
		Card NineHearts = new Card("9", "Hearts");
		Card NineDiamonds = new Card("9", "Diamonds");
		Card NineClubs = new Card("9", "Clubs");
		
		Card EightSpades = new Card("8", "Spades");
		Card EightHearts = new Card("8", "Hearts");
		Card EightDiamonds = new Card("8", "Diamonds");
		Card EightClubs = new Card("8", "Clubs");
		
		Card SevenSpades = new Card("7", "Spades");
		Card SevenHearts = new Card("7", "Hearts");
		Card SevenDiamonds = new Card("7", "Diamonds");
		Card SevenClubs = new Card("7", "Clubs");
		
		Card SixSpades = new Card("6", "Spades");
		Card SixHearts = new Card("6", "Hearts");
		Card SixDiamonds = new Card("6", "Diamonds");
		Card SixClubs = new Card("6", "Clubs");
		
		Card FiveSpades = new Card("5", "Spades");
		Card FiveHearts = new Card("5", "Hearts");
		Card FiveDiamonds = new Card("5", "Diamonds");
		Card FiveClubs = new Card("5", "Clubs");
		
		Card FourSpades = new Card("4", "Spades");
		Card FourHearts = new Card("4", "Hearts");
		Card FourDiamonds = new Card("4", "Diamonds");
		Card FourClubs = new Card("4", "Clubs");
		
		Card ThreeSpades = new Card("3", "Spades");
		Card ThreeHearts = new Card("3", "Hearts");
		Card ThreeDiamonds = new Card("3", "Diamonds");
		Card ThreeClubs = new Card("3", "Clubs");
		
		Card TwoSpades = new Card("2", "Spades");
		Card TwoHearts = new Card("2", "Hearts");
		Card TwoDiamonds = new Card("2", "Diamonds");
		Card TwoClubs = new Card("2", "Clubs");
		
		this.deck.add(TwoSpades);
		deck.add(TwoHearts);
		deck.add(TwoDiamonds);
		deck.add(TwoClubs);
		deck.add(ThreeSpades);
		deck.add(ThreeHearts);
		deck.add(ThreeDiamonds);
		deck.add(ThreeClubs);
		deck.add(FourSpades);
		deck.add(FourHearts);
		deck.add(FourDiamonds);
		deck.add(FourClubs);
		deck.add(FiveSpades);
		deck.add(FiveHearts);
		deck.add(FiveDiamonds);
		deck.add(FiveClubs);
		deck.add(SixSpades);
		deck.add(SixHearts);
		deck.add(SixDiamonds);
		deck.add(SixClubs);
		deck.add(SevenSpades);
		deck.add(SevenHearts);
		deck.add(SevenDiamonds);
		deck.add(SevenClubs);
		deck.add(EightSpades);
		deck.add(EightHearts);
		deck.add(EightDiamonds);
		deck.add(EightClubs);
		deck.add(NineSpades);
		deck.add(NineHearts);
		deck.add(NineDiamonds);
		deck.add(NineClubs);
		deck.add(TenSpades);
		deck.add(TenHearts);
		deck.add(TenDiamonds);
		deck.add(TenClubs);
		deck.add(JackSpades);
		deck.add(JackHearts);
		deck.add(JackDiamonds);
		deck.add(JackClubs);
		deck.add(QueenSpades);
		deck.add(QueenHearts);
		deck.add(QueenDiamonds);
		deck.add(QueenClubs);
		deck.add(KingSpades);
		deck.add(KingHearts);
		deck.add(KingDiamonds);
		deck.add(KingClubs);
		deck.add(AceSpades);
		deck.add(AceHearts);
		deck.add(AceDiamonds);
		deck.add(AceClubs);
		
	}
	
	public ArrayList<Card> getDeck(){
		
		return deck;
	}
}
