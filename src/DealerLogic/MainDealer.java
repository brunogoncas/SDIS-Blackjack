package DealerLogic;

import java.io.IOException;
import java.util.Scanner;

import javax.ws.rs.Path;

import logic.Communication;
import logic.Main;

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
				String[] paramVal = { name, NameRoom };
				
				try {
					response = Communication.POST("roomService/room", paramName, paramVal);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				LogicDealer();
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
	
	public void LogicDealer() {
		
		while(true) {
			
			// ver se tem jogadores na mesa -> se retornar >0
			String response=null;
			try {
				response = Communication.GET("roomService/room/"+name+"/player");
				numJogadoresMesa = Integer.parseInt(response);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("fAS: " + numJogadoresMesa);
			while(numJogadoresMesa == 0) {
				
				try {
				    Thread.sleep(2000);//two second.
				} catch(InterruptedException ex) {
				    Thread.currentThread().interrupt();
				}
				
				try {
					response = Communication.GET("roomService/room/"+name+"/player");
					numJogadoresMesa = Integer.parseInt(response);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("fAsfasfasS: " + numJogadoresMesa);
			}

			System.out.println(" ===== Dealer - BLACKJACK ===== ");
			break;
			// menu começar jogo -> "place your bets"
			//POST(place your bets);
			
			/*try {
			    Thread.sleep(10000);//ten second.
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}*/
			
			// dar 2 cartas a cada jogador que apostou 
			
			// dar uma carta ao dealer -> mostrar uma carta do dealer
			
			// desistencias (fazer só no fim)
			// ir jogador a jogador para tomar decisão (pedir, ficar , dobrar)
			
			// mostrar a segunda carta do dealer -> jogar dealer
			
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
