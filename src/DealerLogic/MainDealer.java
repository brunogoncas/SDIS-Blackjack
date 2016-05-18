package DealerLogic;

import java.io.IOException;
import java.util.Observable;
import java.util.Scanner;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;

import logic.Communication;

public class MainDealer extends Observable{
	
	Scanner reader = new Scanner(System.in);
	//globals
	private int numJogadoresMesa=0;
	private static String name="",NameRoom="";

	static int rPost = 0;
	static String rGet = "";
	
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
				
				String[] paramName = { "roomname", "dealername"};
				String[] paramVal = { NameRoom ,name};
				
				try {
					rPost = Communication.POST("roomService/room", paramName, paramVal);
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
		
		Timer timer = new Timer();
		timer.schedule(new TestPlayers(), 0, 3500);
		
		while(true) {
			
			

			if (getnumJogadoresMesa() != 0) {
			    do {
			    	
					// menu começar jogo -> "begin"
					String[] paramName = {};
					String[] paramVal = {};
					rPost = Communication.POST("roomService/room/"+NameRoom+"/state/"+"begin", paramName , paramVal);
					Thread.sleep(4000);//4 second.
					
					//POST(place your bets);
					rPost = Communication.POST("roomService/room/"+NameRoom+"/state/"+"Bet", paramName , paramVal);
				
					Thread.sleep(10000);//ten second.
					
					// dar duas (mostrar uma) carta ao dealer -> mostrar uma carta do dealer

					//post para atribuir cartas ao jgoador	
					try {
						rPost = Communication.POST("dealerService/addCards/"+name+"/"+2, paramName , paramVal);
					} catch (Exception e) {
						e.printStackTrace();
					}
					Thread.sleep(1000);//1 second.
						
					// dar 2 cartas a cada jogador que apostou  -> mudar 

					rPost = Communication.POST("roomService/room/"+NameRoom+"/state/"+"getcards", paramName , paramVal);
					Thread.sleep(2000);//2 second.
								
					
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

					JSONArray jArrays = new JSONArray(players);
					 for(int i = 0; i < jArrays.length(); i++){
						String nameplayer = jArrays.getJSONObject(i).getString("name");
						 
						rPost = Communication.POST("roomService/room/"+NameRoom+"/state/"+nameplayer, paramName , paramVal);
						Thread.sleep(8000);//5 second.
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

						//post para atribuir cartas ao dealer	
						try {
							rPost = Communication.POST("dealerService/addCards/"+name+"/"+1, paramName , paramVal);
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

					rPost = Communication.POST("roomService/room/"+NameRoom+"/state/"+"results", paramName , paramVal);
					
					Thread.sleep(3000);//3 second.
					// terminar jogo -> novo jogo

					rPost = Communication.POST("roomService/room/"+NameRoom+"/state/"+"done", paramName , paramVal);
					
					Thread.sleep(4000);//4 second.
					//começar novo jogo -> limpar dados do jogo atual
					//limpar cartas do jogo.
					Communication.DELETE("roomService/removeCards/"+NameRoom);
					numJogadoresMesa=0;
					rPost = Communication.POST("roomService/room/"+NameRoom+"/state/"+"begin", paramName , paramVal);
					Thread.sleep(2000);//2 second.
				
			    } while(getnumJogadoresMesa() != 0);
			} else {
			    System.out.println("NOT ENOUGH PLAYERS!!! :(");
			    Thread.sleep(3000);//3.
			    continue;
			}
		
		}
		
	}
	
	public void setnumJogadoresMesa(int someVariable) {
	    synchronized (this) {
	      this.numJogadoresMesa = someVariable;
	    }
	    setChanged();
	    notifyObservers();
	  }

	  public synchronized int getnumJogadoresMesa() {
	    return numJogadoresMesa;
	  }
	
	class TestPlayers extends TimerTask {
	    public void run() {

			// ver se tem jogadores na mesa -> se retornar >0
			String response=null;
			try {
				response = Communication.GET("roomService/room/"+NameRoom+"/player");
				int temp = Integer.parseInt(response);
				setnumJogadoresMesa(temp);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}

	// And From your main() method or any other method

	
	public static void main(String[] args) throws IOException {
		System.out.println(" ===== Dealer - BLACKJACK ===== ");
		MainDealer Menu = new MainDealer();
		Menu.mainMenu();
	}
}
