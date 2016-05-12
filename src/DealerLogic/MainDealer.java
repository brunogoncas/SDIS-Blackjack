package DealerLogic;

import java.io.IOException;
import java.util.Scanner;

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
								
				System.out.println("Qual � o seu nome? ");
				reader.nextLine();
				name = reader.nextLine();
			    
				System.out.println("Qual � o nome da sala que pretende criar? ");
				NameRoom = reader.nextLine();
				
				int response=0;
				String[] paramName = { "roomname", "dealername"};
				String[] paramVal = { name, NameRoom };
				
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
				System.out.println("\nEscolha n�o valida!\n");
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
				response = Communication.GET("roomService/room/"+name+"/player");
				numJogadoresMesa = Integer.parseInt(response);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("NUM: " + numJogadoresMesa);
			
			while(numJogadoresMesa == 0) {
								
				Thread.sleep(2000);//two second.				
								
				response = Communication.GET("roomService/room/"+name+"/player");
				numJogadoresMesa = Integer.parseInt(response);
				
				System.out.println("fAsfasfasS: " + numJogadoresMesa);
			}

			// menu come�ar jogo -> "place your bets"
			//POST(place your bets);
			String[] paramName = {};
			String[] paramVal = {};
			
			int response2 = 0;
			response2 = Communication.POST("roomService/room/"+name+"/state/"+"Bet", paramName , paramVal);
		
			Thread.sleep(10000);//ten second.
				
			// dar 2 cartas a cada jogador que apostou  -> mudar 
			int response3 = 0;
			response3 = Communication.POST("roomService/room/"+name+"/state/"+"getcards", paramName , paramVal);
			Thread.sleep(2000);//2 second.
			
			// dar duas (mostrar uma) carta ao dealer -> mostrar uma carta do dealer
			
			break;
			// desistencias (fazer s� no fim)
			// ir jogador a jogador para tomar decis�o (pedir, ficar , dobrar)
			
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
