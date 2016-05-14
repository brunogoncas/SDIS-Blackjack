package DealerLogic;

import java.io.IOException;
import java.util.Scanner;

import org.json.JSONArray;

import logic.Communication;

public class MainDealer {
	
	Scanner reader = new Scanner(System.in);
	//globals
	int numJogadoresMesa=0;
	String name="";
	String NameRoom="";
	
	public void mainMenu() {
		
		while (true) {
			System.out.println("1. Criar uma sala de jogo");
			System.out.println("2. Sair\n");

			System.out.print("Escolha: ");

			int choice;

			try {
				choice = Integer.parseInt(reader.next());
			} catch (NumberFormatException e) {
				System.out.println("\nInput invalido!");
				System.out.println("");

				continue;
			}
			switch (choice) {
			case 1: {
								
				System.out.println("Qual é o seu nome? ");
				reader.nextLine();
				name = reader.nextLine();
			    
				System.out.println("Qual é o nome da sala que pretende criar? ");
				NameRoom = reader.nextLine();
				
				int response=0;
				String[] paramName = { "roomname", "dealername"};
				String[] paramVal = { NameRoom ,name};
				
				try {
					response = Communication.POST("roomService/room", paramName, paramVal);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				try {
					LogicDealer();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
			case 2: {
				System.out.println("\nA fechar...");
				System.exit(0);
				break;
			}
			default: {
				System.out.println("\nEscolha não valida!\n");
				break;
			}
			}
		}
	}
	
	public void LogicDealer() throws IOException, InterruptedException {
		
		while(true) {
			
			// ver se tem jogadores na mesa -> se retornar >0
			String response=null;
			try {
				response = Communication.GET("roomService/room/"+NameRoom+"/player");
				numJogadoresMesa = Integer.parseInt(response);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("NUM: " + numJogadoresMesa);
			
			while(numJogadoresMesa == 0) {
								
				Thread.sleep(2000);//two second.				
								
				response = Communication.GET("roomService/room/"+NameRoom+"/player");
				numJogadoresMesa = Integer.parseInt(response);
				
				System.out.println("Players in the room: " + NameRoom + ":" + numJogadoresMesa);
			}

			// menu começar jogo -> "place your bets"
			//POST(place your bets);
			String[] paramName = {};
			String[] paramVal = {};
			
			int response2 = 0;
			response2 = Communication.POST("roomService/room/"+NameRoom+"/state/"+"Bet", paramName , paramVal);
		
			Thread.sleep(10000);//ten second.
				
			// dar 2 cartas a cada jogador que apostou  -> mudar 
			int response3 = 0;
			response3 = Communication.POST("roomService/room/"+NameRoom+"/state/"+"getcards", paramName , paramVal);
			Thread.sleep(2000);//2 second.
			
			// dar duas (mostrar uma) carta ao dealer -> mostrar uma carta do dealer
			int response4 = 0;
			//post para atribuir cartas ao jgoador	
			try {
				response4 = Communication.POST("playerService/addCards/"+NameRoom+"/"+name, paramName , paramVal);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Thread.sleep(1000);//1 second.
			
			
			// desistencias (fazer só no fim)
			// ir jogador a jogador para tomar decisão (pedir, ficar , dobrar)
			
			// mostrar a segunda carta do dealer -> jogar dealer
			//get cartas dealer
			String cardsDealer=null;
			try {
				cardsDealer = Communication.GET("playerService/getCards/"+NameRoom+"/"+name);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			JSONArray jArray = new JSONArray(cardsDealer);
			 for(int i = 0; i < jArray.length(); i++){
				 String suit = jArray.getJSONObject(i).getString("suit");
				 String figure = jArray.getJSONObject(i).getString("figure");
				 System.out.println("You got an"+ figure + " of " + suit); 
			  }
			
			break;
			// resultados
			
			// terminar jogo -> novo jogo
		}
		
	}
	
	public static void main(String[] args) throws IOException {
		System.out.println(" ===== Dealer - BLACKJACK ===== ");
		MainDealer Menu = new MainDealer();
		Menu.mainMenu();
	}
}
