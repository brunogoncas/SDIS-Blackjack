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
			
			// menu começar jogo -> "begin"
			int response2 = 0;
			String[] paramName = {};
			String[] paramVal = {};
			response2 = Communication.POST("roomService/room/"+NameRoom+"/state/"+"begin", paramName , paramVal);
			Thread.sleep(4000);//4 second.
			
			//POST(place your bets);
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
				response4 = Communication.POST("dealerService/addCards/"+name+"/"+2, paramName , paramVal);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Thread.sleep(1000);//1 second.
			
			
			// desistencias (fazer só no fim)
			// ir jogador a jogador para tomar decisão (pedir, ficar , dobrar) 
			//-> buscar jogadores da sala ativos -> mudar state para nome do jogador a jogar
			String players = null;
			try {
				players = Communication.GET("roomService/room/getPlayers/"+NameRoom);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println(players);
			int response8 = 0;
			JSONArray jArrays = new JSONArray(players);
			 for(int i = 0; i < jArrays.length(); i++){
				String nameplayer = jArrays.getJSONObject(i).getString("name");
				 
				response8 = Communication.POST("roomService/room/"+NameRoom+"/state/"+nameplayer, paramName , paramVal);
				Thread.sleep(5000);//5 second.
				System.out.println(nameplayer); 
			  }
		
			
			// mostrar a segunda carta do dealer -> jogar dealer
			//get cartas dealer
			String cardsDealer=null;
			try {
				cardsDealer = Communication.GET("dealerService/getCards/"+name);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int points = 0;
			JSONArray jArray = new JSONArray(cardsDealer);
			for(int i = 0; i < jArray.length(); i++){
				 String suit = jArray.getJSONObject(i).getString("suit");
				 String figure = jArray.getJSONObject(i).getString("figure");
				 points += jArray.getJSONObject(i).getInt("card_value");
				 System.out.println("You got an"+ figure + " of " + suit + " Points: " + points +"PRINT 1"); 
			}
			
			
			while(points < 17) {
				points = 0;
				cardsDealer = "";
				//pedir +1 carta
				int response5 = 0;
				//post para atribuir cartas ao dealer	
				try {
					response5 = Communication.POST("dealerService/addCards/"+name+"/"+1, paramName , paramVal);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				try {
					cardsDealer = Communication.GET("dealerService/getCards/"+name);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				jArray = new JSONArray(cardsDealer);
				for(int i = 0; i < jArray.length(); i++){
					 String suit = jArray.getJSONObject(i).getString("suit");
					 String figure = jArray.getJSONObject(i).getString("figure");
					 points += jArray.getJSONObject(i).getInt("card_value");
					 System.out.println("You got an"+ figure + " of " + suit + " Points: " + points +"PRINT 2"); 
				}		
			}
			
			// resultados
			int response6 = 0;
			response6 = Communication.POST("roomService/room/"+NameRoom+"/state/"+"results", paramName , paramVal);
			
			Thread.sleep(3000);//3 second.
			// terminar jogo -> novo jogo
			int response7 = 0;
			response7 = Communication.POST("roomService/room/"+NameRoom+"/state/"+"done", paramName , paramVal);
			
			Thread.sleep(4000);//4 second.
			//começar novo jogo
			numJogadoresMesa=0;
			response7 = Communication.POST("roomService/room/"+NameRoom+"/state/"+"begin", paramName , paramVal);
			Thread.sleep(2000);//2 second.
			break;
		}
		
	}
	
	public static void main(String[] args) throws IOException {
		System.out.println(" ===== Dealer - BLACKJACK ===== ");
		MainDealer Menu = new MainDealer();
		Menu.mainMenu();
	}
}
