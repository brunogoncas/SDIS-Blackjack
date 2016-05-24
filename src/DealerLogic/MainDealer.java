package DealerLogic;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Observable;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.json.JSONArray;

import logic.Communication;

public class MainDealer extends Observable{
	
	Scanner reader = new Scanner(System.in);
	//globals
	private int numJogadoresMesa=0;
	private static String name="",NameRoom="";

	static int rPost = 0;
	static String rGet = "";
	
	public void mainMenu() throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		
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
				System.out.println("\nEscolha n�o valida!\n");
				break;
			}
			}
		}
	}
	
	public void LogicDealer() throws IOException, InterruptedException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		
		Timer timer = new Timer();
		timer.schedule(new TestPlayers(), 0, 3500);
		
		while(true) {	

			if (getnumJogadoresMesa() != 0) {
			    do {
			    	
					// menu come�ar jogo -> "begin"
					String[] paramName = {"nameRoom", "state"};
					String[] paramVal = {NameRoom, "begin"};
					rPost = Communication.POST("roomService/room/state", paramName , paramVal);
					Thread.sleep(5000);//5 second.
					
					//POST(place your bets);
					String[] paramVal2 = {NameRoom, "Bet"};
					rPost = Communication.POST("roomService/room/state", paramName , paramVal2);
				
					Thread.sleep(10000);//ten second.
					
					// dar duas (mostrar uma) carta ao dealer -> mostrar uma carta do dealer

					//post para atribuir cartas ao jgoador	
					try {
						String[] paramNameD = {"namedealer", "numberCards"};
						String[] paramValD = {name, "2"};
						rPost = Communication.POST("dealerService/addCards", paramNameD , paramValD);
					} catch (Exception e) {
						e.printStackTrace();
					}
					Thread.sleep(2000);//1 second.
						
					// dar 2 cartas a cada jogador que apostou  -> mudar
					String[] paramVal3 = {NameRoom, "getcards"};
					rPost = Communication.POST("roomService/room/state", paramName , paramVal3);
					Thread.sleep(4000);//2 second.
								
					
					// desistencias (fazer s� no fim)
					// ir jogador a jogador para tomar decis�o (pedir, ficar , dobrar) 
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
						 
						String[] paramVal4 = {NameRoom, nameplayer};
						rPost = Communication.POST("roomService/room/state", paramName , paramVal4);
						Thread.sleep(6000);//6 second.
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
					
					Thread.sleep(1000);//1 second.
					
					while(points < 17) {
						points = 0;
						cardsDealer = "";
						//pedir +1 carta

						//post para atribuir cartas ao dealer	
						try {
							String[] paramNameD1 = {"namedealer", "numberCards"};
							String[] paramValD1 = {name, "1"};
							rPost = Communication.POST("dealerService/addCards", paramNameD1 , paramValD1);
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
						Thread.sleep(1000);//two second.
					}
					
					// resultados
					String[] paramVal5 = {NameRoom, "results"};
					rPost = Communication.POST("roomService/room/state", paramName , paramVal5);
					
					Thread.sleep(3000);//3 second.
					// terminar jogo -> novo jogo
					
					String[] paramVal6 = {NameRoom, "done"};
					rPost = Communication.POST("roomService/room/state", paramName , paramVal6);
					
					Thread.sleep(5000);//5 second.
					//come�ar novo jogo -> limpar dados do jogo atual
					//limpar cartas do jogo.
					Communication.DELETE("roomService/removeCards/"+NameRoom);
					
					String[] paramVal7 = {NameRoom, "begin"};
					rPost = Communication.POST("roomService/room/state", paramName , paramVal7);
					Thread.sleep(2000);//2 second.
				
			    } while(getnumJogadoresMesa() != 0);
			} else {
			    System.out.println("NOT ENOUGH PLAYERS!!! :(");
			    Thread.sleep(2000);//2.
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
				System.out.println("Numero de Jogadores na Sala: " + response);
				int temp = Integer.parseInt(response);
				setnumJogadoresMesa(temp);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}

	// And From your main() method or any other method

	
	public static void main(String[] args) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		System.out.println(" ===== Dealer - BLACKJACK ===== ");
		MainDealer Menu = new MainDealer();
		Menu.mainMenu();
	}
}
