package logic;

import java.util.Scanner;

public class BlackJack {
	
	public static void main(String[] args){
		
		Game newGame = new Game();
		
		  do{
	       
	       // Player bets	         
	             System.out.println("How many chips do you want to bet?");
	             @SuppressWarnings("resource")
				Scanner sdtin = new Scanner(System.in);
	             int chipsBet = sdtin.nextInt();
	             
	       // Checking player's chips     
	             while(chipsBet > newGame.player.getTotalChips()){
	                 System.out.println("You only have: " + newGame.player.getTotalChips() + " Chips.");
	                 System.out.println("Only bet what you can afford to lose. Type in any number <= " + newGame.player.getTotalChips());
	                 chipsBet = sdtin.nextInt();
	             }
	             while(chipsBet < 1){
	                 System.out.println("At least 1 chip each hand. Type in any number >= 1.");
	                 chipsBet = sdtin.nextInt();
	             }
	                 System.out.println("You just bet: " + chipsBet + " Chips. Game starts.");
       
	        // Dealing Cards
	            // Player is dealt two cards
	                newGame.hit();
	               	newGame.hit();
	               	
	                if(newGame.player.getPlayerPoints() == 21){
	                    System.out.println("You were dealt: " + newGame.player.getPlayerPoints() + " Points.");
	                    System.out.println("Wow, Blackjack. Turn down for what!" ); 
	                }
	                else{
	                    System.out.println("You were dealt: " + newGame.player.getPlayerPoints() + " Points.");
	                }
	                
	           //  Dealer is dealt two cards, with one card facing up and the other facing down   
	                    newGame.dealerHit();
	                    System.out.println("Dealer's first card has " + newGame.dealer.getDealerPoints() + " Points.");
	                
	        // Player's turn: Hit or Stand?
	                    // If player gets Blackjack, don't ask. 
	                    if(newGame.player.getPlayerPoints() == 21){
	                        System.out.println("Let's see the dealer's second card." );
	                    }
	                
	                    else{
	                    System.out.println("Do you want to hit or stand? h/s");
	                    @SuppressWarnings("resource")
						Scanner hitOrStand = new Scanner(System.in);
	                    String typedLine = hitOrStand.nextLine();
	                                   
	              // Player hits 
	                while (typedLine.equals("h")){
	                    System.out.println("So you want to hit. Here is your new card." );
	                    newGame.hit();

                        System.out.println("New card has: " + 
                        newGame.player.getHand().get(newGame.player.getHand().size()-1).getCardPoints(0) + " Points." ); 
                        System.out.println("Now you have total: " + newGame.player.getPlayerPoints() + " Points." );
	            // checking player points
	                    if (newGame.player.getPlayerPoints() > 21){
	                        System.out.println("Well, you just bust. Waitress comes around, 'Cocktails? Cocktails'" ); 
	                        break;
	                    }
	                    else if(newGame.player.getPlayerPoints() == 21){
	                        System.out.println("Wow, Blackjack. Turn down for what!" );
	                        break;
	                    }
	                    else {    
	                        System.out.println("hit or stand? h/s");
	                        typedLine = hitOrStand.nextLine(); 
	                    } 

	                }// end while loop
	                
	            // Player stands    
	             if(typedLine.equals("s")){
	                    System.out.println("So you want to stand. Okie doke! Good luck!" );
	                    System.out.println("You have total: " 
	                    + newGame.player.getPlayerPoints() + " Points." );
	                }
	             else { 
	                    System.out.println("Let's see the dealer's cards" );
	                  }
	             
	              }
	       // Dealer's turn         
	             newGame.dealerHit();
	             System.out.println("Dealer flips the facing-down card." );
	            // Checking dealer's points        
	             if (newGame.dealer.getDealerPoints() > 21) {
	                    System.out.println("Dealer has two Aces and makes one Ace 1 point without busting.");
	                }
	                System.out.println("Dealer has total: " + newGame.dealer.getDealerPoints() + " Points.");
	                
	           // If player busts, dealer wins.   
	                if(newGame.player.getPlayerPoints() > 21){
	                    System.out.println("Dealer won. Sorry sucker, you just lost " + chipsBet + " chips." );
	                    newGame.player.removeChips(chipsBet);    
	                    System.out.println("You have " + newGame.player.getTotalChips() + " chips left. Good luck!" );
	                }
	                else {
	         //Dealer draws cards while player standing      
	                while (newGame.dealer.getDealerPoints() < 17){
	                    System.out.println("Dealer is getting a new card." );
	                    newGame.dealerHit();

	                    System.out.println("Dealer has total: " + newGame.dealer.getDealerPoints() + " Points."); 
	                    }
	                    
	                if(newGame.dealer.getDealerPoints() >21){
	                	System.out.println("Dealer busts." );
	                	System.out.println("Holy moly! You just won " + chipsBet + " Chips!!");
	                	newGame.player.addChips(chipsBet);
	                	System.out.println("You have " + newGame.player.getTotalChips() + " chips left. Good luck!" );
	                    }
	                
	                else{
	       // Time to count money
	                if (newGame.player.getPlayerPoints() > newGame.dealer.getDealerPoints()) {
	                    System.out.println("You have more points than the dealer. You just won " + chipsBet + " Chips!!!");
	                    newGame.player.addChips(chipsBet);
	                    System.out.println("You have total " + newGame.player.getTotalChips() + " Chips!!");
	                }
	                else if (newGame.player.getPlayerPoints() < newGame.dealer.getDealerPoints()) {
	                    System.out.println("Dealer has more points. Dealer won. Sorry sucker, you just lost " + chipsBet + " Chips.");
	                    newGame.player.removeChips(chipsBet);
	                    if (newGame.player.getTotalChips() <= 0){
	                        System.out.println("Damn dude/dudette, you lost all your chips." );
	                        }
	                    else{
	                        System.out.println("You have " + newGame.player.getTotalChips() + " chips left. Good luck!" );        
	                        }
	                }
	                else{
	                System.out.println("It's a tie. Safe and sound for now, you get your chips back.");
	                System.out.println("You have " + newGame.player.getTotalChips() + " chips left. Good luck!" );
	                }
	                
	                }
	                
	                }
	                
	       // Checking if player wants to keep playing         
	                System.out.println("Do you want to keep playing? y/n");
	                @SuppressWarnings("resource")
					Scanner playOrNot = new Scanner(System.in);
	                String response = playOrNot.nextLine();
	                
	                if (response.equals("y")){
	                	if (newGame.player.getTotalChips() <= 0) {
	                		System.out.println("You're out of chips. Think twice about opening that credit line, why don't you sleep on it.");
	                		newGame.gameOver = false;
	                	}
	                	else {
	                		newGame.player.resetPlayerPoints();
	                		newGame.dealer.resetDealerPoints();
	                		newGame.gameOver = false;
	                	}
	                }
	                else {
	                	newGame.gameOver = true;
	                        System.out.println("It's been a pleasure, go see a comedy show on the Strip to cheer you up!");
	                }
	                
	        }
			while (newGame.player.getTotalChips() > 0 && newGame.gameOver == false);
	        
	        
		}
				
}

