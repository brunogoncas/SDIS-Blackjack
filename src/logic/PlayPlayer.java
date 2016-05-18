package logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import org.json.JSONArray;

public class PlayPlayer implements Runnable{
	
	static Scanner reader = new Scanner(System.in);
	private static String str = "";
	static int pPoints = 0, betmoney = 0, numberT = 0;
	int idRoom;
	String usernameLogged;
	
	public PlayPlayer(int idRoom, String usernameLogged){
		
		this.idRoom = idRoom;
		this.usernameLogged = usernameLogged;
		
	}
	
	public static boolean isInteger(String str) {

	    int length = str.length();

	    int i = 0;
	    if (str.charAt(0) == '-') {
	        if (length == 1) {
	            return false;
	        }
	        i = 1;
	    }
	    for (; i < length; i++) {
	        char c = str.charAt(i);
	        if (c < '0' || c > '9') {
	            return false;
	        }
	    }
	    return true;
	}

	@Override
	public void run() {
		
		while(true) {
			
			//LER MENSAGEM SERVIDOR -> GET STATE (player e room)
			String[] paramName = {};
			String[] paramVal = {};
			String response=null, responseP=null, numberT = null; 
			int response2;
			String cardDealer=null;

			try {
			
			responseP= Communication.GET("playerService/getState/"+idRoom+"/"+usernameLogged);
			
			response = Communication.GET("roomService/room/"+idRoom);
			
			numberT = Communication.GET("playerService/getTimeouts/"+idRoom+"/"+usernameLogged);
				
				//System.out.println("response:" + response + " " + responseP + " " + numberT);
				
			if(responseP.equals("Iddle")){
				
				if(Integer.parseInt(numberT) == 2){
					
					//SAIR DA SALA
					System.out.println("Falhaste 2 tentativas de voltar á sala, a ser removido!");
					
					Communication.DELETE("playerService/removePlayer/"+usernameLogged+"/"+idRoom);

					break;
					
				}
				
				else{
				
					int x = 5; // wait 5 seconds at most
					
					System.out.println("Please say something!");
					
					BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
					long startTime = System.currentTimeMillis();
					
						while ((System.currentTimeMillis() - startTime) < x * 1000
						        && !in.ready()) {
							
						}

						if (in.ready()) {
							
						    System.out.println("You are back!!!");

							response2 = Communication.POST("playerService/updateState/"+idRoom+"/"+usernameLogged+"/"+"begin", paramName , paramVal);
						    
						    continue;
						    
						} else {
						    System.out.println("You did not enter something! :(");
						    
						    String[] paramName3 = {"name", "idRoom"};
							String[] paramVal3 = {usernameLogged, String.valueOf(idRoom)};
							
							response2 = Communication.POST("playerService/editTimeout", paramName3 , paramVal3);

							continue;
						  }
					}
				
			}
			
			if(response.equals("begin") && responseP.equals("begin")) {
				response2 = Communication.POST("playerService/updateState/"+idRoom+"/"+usernameLogged+"/"+"Bet", paramName , paramVal);

			}
			else if(response.equals("Bet") && responseP.equals("Bet")) {
		
				System.out.println("Aposta: ");
						
				int x = 10; // wait 2 seconds at most

				BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
				long startTime = System.currentTimeMillis();
				System.out.println("Insira a sua aposta: ");
					while ((System.currentTimeMillis() - startTime) < x * 1000
					        && !in.ready()) {
					}
				
					if (in.ready()) {
						str = in.readLine();

					    if(!isInteger(str)){
							System.out.println("You did not enter a valid number to bet! Remember that next round.");
							try {
								response2 = Communication.POST("playerService/updateState/"+idRoom+"/"+usernameLogged+"/"+"begin", paramName , paramVal);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							continue;
						}
						else{
					
					        System.out.println( "you have entered: "+ str ); 
					        betmoney = Integer.parseInt(str);
							
					        String[] paramName2 = {"name","addBet"};
							String[] paramVal2 = {usernameLogged , Integer.toString(betmoney)};
							
							response2 = Communication.POST("playerService/addBet", paramName2 , paramVal2);
							response2 = Communication.POST("playerService/updateState/"+idRoom+"/"+usernameLogged+"/"+"getcards", paramName , paramVal);
						}
					}
					else {
						response2 = Communication.POST("playerService/updateState/"+idRoom+"/"+usernameLogged+"/"+"Iddle", paramName , paramVal);
						System.out.println("Please enter something next time!");
					    continue;
					}
				
			}		
			else if(response.equals("getcards") && responseP.equals("getcards")) {
				//post para atribuir cartas ao jgoador
				response2 = Communication.POST("playerService/addCards/"+idRoom+"/"+usernameLogged+"/"+2, paramName , paramVal);
				
				//get cartas jogador
				String cardsPlayer=null;
				cardsPlayer = Communication.GET("playerService/getCards/"+idRoom+"/"+usernameLogged);
				
				JSONArray jArray = new JSONArray(cardsPlayer);
				 for(int i = 0; i < jArray.length(); i++){
					 String suit = jArray.getJSONObject(i).getString("suit");
					 String figure = jArray.getJSONObject(i).getString("figure");
					 pPoints += jArray.getJSONObject(i).getInt("card_value");
					 System.out.println("You got an"+ figure + " of " + suit); 
				  }
				 
				 System.out.println("Tens " + pPoints + " pontos!");
				 
				 response2 = Communication.POST("playerService/updateState/"+idRoom+"/"+usernameLogged+"/"+"myturn", paramName , paramVal);
									
			}
			else if(response.equals(usernameLogged) && responseP.equals("myturn") ) {
				
				 //get 1 carta dealer
				cardDealer = Communication.GET("dealerService/getCardD/"+idRoom);

				 
				JSONArray jArray = new JSONArray(cardDealer);
				jArray = new JSONArray(cardDealer);
				String suit = jArray.getJSONObject(0).getString("suit");
				String figure = jArray.getJSONObject(0).getString("figure");
				int pointsD = jArray.getJSONObject(0).getInt("card_value");
				System.out.println("Dealer got an "+ figure + " of " + suit + " Points: " + pointsD); 
			
				
				
				if(pPoints >= 21) {
					
					response2 = Communication.POST("playerService/updateState/"+idRoom+"/"+usernameLogged+"/"+"results", paramName , paramVal);
					continue;
				}
				
				//ciclo player
				String resp = "x";
				while (!resp.toLowerCase().equals("s")){
					System.out.println("You got " + pPoints +"! O que queres fazer? ( S(Stand) ; H (Hit); D (Double) ).");
					resp = reader.nextLine();
										
					//se for hit -> pedir +1carta
					if(resp.toLowerCase().equals("h")) {
						
						pPoints = 0;
						
						response2 = Communication.POST("playerService/addCards/"+idRoom+"/"+usernameLogged+"/"+1, paramName , paramVal);
						
						//get cartas jogador
						String cardsPlayer2=null;
						
						cardsPlayer2 = Communication.GET("playerService/getCards/"+idRoom+"/"+usernameLogged);
						
						JSONArray jArray2 = new JSONArray(cardsPlayer2);
						 for(int i = 0; i < jArray2.length(); i++){
							 suit = jArray2.getJSONObject(i).getString("suit");
							 figure = jArray2.getJSONObject(i).getString("figure");
							 pPoints += jArray2.getJSONObject(i).getInt("card_value");
							 System.out.println("You got an"+ figure + " of " + suit); 
						  }
						 
						 System.out.println("Tens " + pPoints + " pontos!");
						 if(pPoints > 21) {
							 resp = "s";
						 }
					}
					else if (resp.toLowerCase().equals("d")) {
						
						pPoints = 0;
						//se double -> pedir só +1 carta e tirar dinheiro da aposta inciial e avançar
						response2 = Communication.POST("playerService/addCards/"+idRoom+"/"+usernameLogged+"/"+1, paramName , paramVal);
						
						//remove o dinheiro 
						String[] paramName2 = { "name", "removeChips"};
						String[] paramVal2 = { usernameLogged, Integer.toString(betmoney) };
						
						int response3 = 0;

						response3 = Communication.POST("playerService/removeChips", paramName2, paramVal2);
						
						//get cartas jogador
						String cardsPlayer3=null;

						cardsPlayer3 = Communication.GET("playerService/getCards/"+idRoom+"/"+usernameLogged);
						
						JSONArray jArray3 = new JSONArray(cardsPlayer3);
						 for(int i = 0; i < jArray3.length(); i++){
							 suit = jArray3.getJSONObject(i).getString("suit");
							 figure = jArray3.getJSONObject(i).getString("figure");
							 pPoints += jArray3.getJSONObject(i).getInt("card_value");
							 System.out.println("You got an"+ figure + " of " + suit); 
						  }
						 
						 System.out.println("Tens " + pPoints + " pontos!");
						 resp = "s";
					}	
				}	
				
				if(resp.toLowerCase().equals("s")) {
					
					response2 = Communication.POST("playerService/updateState/"+idRoom+"/"+usernameLogged+"/"+"results", paramName , paramVal);

					continue;
				}
				
			}
			else if(response.equals("results") && responseP.equals("results")) {
				//ver os pontos do dealer e comparar com os meus e atribuir dinheiro ou tirar
				String dDealer = null;
				
				dDealer = Communication.GET("dealerService/getCardD/"+idRoom);

				
				//get cartas dealer -> todas
				cardDealer = Communication.GET("dealerService/getCardD/"+idRoom);

								
				JSONArray jArray2 = new JSONArray(cardDealer);
				int pointsD=0;
				 for(int i = 0; i < jArray2.length(); i++){
					 String suit = jArray2.getJSONObject(i).getString("suit");
					 String figure = jArray2.getJSONObject(i).getString("figure");
					 pointsD += jArray2.getJSONObject(i).getInt("card_value");
					 System.out.println("Dealer got an"+ figure + " of " + suit); 
				  }
				System.out.println("Dealer got " + pointsD +" Points!"); 
				
				if((pointsD < pPoints && pointsD < 22 && pPoints < 22)
						|| (pointsD > 21 && pPoints < 22)){
					
					String[] paramName3 = {"name", "addChips"};
					String[] paramVal3 = {usernameLogged, String.valueOf(2*betmoney)};
					
					response2 = Communication.POST("playerService/addChips", paramName3 , paramVal3);

					
					System.out.println("Ganhou! Ganhou: " + 2*betmoney + "chips com:" + pPoints);
					
				}
				
				else if (pointsD == pPoints && pointsD < 22 && pPoints < 22){
					
					String[] paramName4 = {"name", "addChips"};
					String[] paramVal4 = {usernameLogged, String.valueOf(betmoney)};
				
					response2 = Communication.POST("playerService/addChips", paramName4 , paramVal4);

					
					System.out.println("Empatou!");
				}
				
				else{
					
					System.out.println("Perdeu!");
				}
				
				response2 = Communication.POST("playerService/updateState/"+idRoom+"/"+usernameLogged+"/"+"done", paramName , paramVal);
				
			}
			else if(response.equals("done") && responseP.equals("done")) {
				pPoints = 0;
				betmoney = 0;
				
				response2 = Communication.POST("playerService/updateState/"+idRoom+"/"+usernameLogged+"/"+"begin", paramName , paramVal);

			}
			else{
				System.out.println("Esperando para jogar!");
				try {
				    Thread.sleep(2000);//two second.
				} catch(InterruptedException ex) {
				    Thread.currentThread().interrupt();
				}
			}		
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			
		}
		// TODO Auto-generated method stub
		
	}
	
}
