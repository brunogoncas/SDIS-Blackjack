package logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

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
			
			String response=null, responseP=null, numberT = null; 
			String cardDealer=null;

			try {

			responseP= Communication.GET("playerService/getState/"+idRoom+"/"+usernameLogged);
			Thread.sleep(500);//0,5 second.
			response = Communication.GET("roomService/room/"+idRoom);
			Thread.sleep(500);//0,5 second.
			numberT = Communication.GET("playerService/getTimeouts/"+idRoom+"/"+usernameLogged);
			Thread.sleep(500);//0,5 second.
			
			System.out.println("response:" + response + " " + responseP + " " + numberT);
				
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
						    String[] state = {"idRoom","namePlayer","state"};
							String[] stateVal = {String.valueOf(idRoom),usernameLogged,"begin"};
							Communication.POST("playerService/updateState", state , stateVal);
						    continue;
						    
						} else {
						    System.out.println("You did not enter something! :(");
						    
						    String[] paramName3 = {"name", "idRoom"};
							String[] paramVal3 = {usernameLogged, String.valueOf(idRoom)};
							
							Communication.POST("playerService/editTimeout", paramName3 , paramVal3);
							continue;
						  }
					}
				
			}
			
			if(response.equals("begin") && responseP.equals("begin")) {
				String[] state = {"idRoom","namePlayer","state"};
				String[] stateVal = {String.valueOf(idRoom),usernameLogged,"Bet"};
				Communication.POST("playerService/updateState", state , stateVal);
				
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
								String[] state = {"idRoom","namePlayer","state"};
								String[] stateVal = {String.valueOf(idRoom),usernameLogged,"begin"};
								Communication.POST("playerService/updateState", state , stateVal);
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
							String[] state = {"idRoom","namePlayer","state"};
							String[] stateVal = {String.valueOf(idRoom),usernameLogged,"getcards"};
							Communication.POST("playerService/addBet", paramName2 , paramVal2);
							Communication.POST("playerService/updateState", state , stateVal);
						}
					}
					else {
						String[] state = {"idRoom","namePlayer","state"};
						String[] stateVal = {String.valueOf(idRoom),usernameLogged,"Iddle"};
						Communication.POST("playerService/updateState", state , stateVal);
						System.out.println("Please enter something next time!");
					    continue;
					}
				
			}		
			else if(response.equals("getcards") && responseP.equals("getcards")) {
				//post para atribuir cartas ao jgoador
				String[] cards = {"name","idRoom","numCards"};
				String[] cardsVal = {usernameLogged,String.valueOf(idRoom),String.valueOf(2)};
				Communication.POST("playerService/addCards", cards , cardsVal);
				
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
				 String[] state = {"idRoom","namePlayer","state"};
				 String[] stateVal = {String.valueOf(idRoom),usernameLogged,"myturn"};
				 Communication.POST("playerService/updateState", state , stateVal);					
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
					String[] state = {"idRoom","namePlayer","state"};
					String[] stateVal = {String.valueOf(idRoom),usernameLogged,"results"};
					Communication.POST("playerService/updateState", state , stateVal);
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
						
						String[] cards = {"name","idRoom","numCards"};
						String[] cardsVal1 = {usernameLogged,String.valueOf(idRoom),String.valueOf(1)};
						
						Communication.POST("playerService/addCards", cards , cardsVal1);
						
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
						String[] cards = {"name","idRoom","numCards"};
						String[] cardsVal = {usernameLogged,String.valueOf(idRoom),String.valueOf(1)};
						Communication.POST("playerService/addCards", cards , cardsVal);
								
						//remove o dinheiro 
						String[] paramName2 = { "name", "removeChips"};
						String[] paramVal2 = { usernameLogged, Integer.toString(betmoney) };
						
						Communication.POST("playerService/removeChips", paramName2, paramVal2);
						
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
					String[] state = {"idRoom","namePlayer","state"};
					String[] stateVal = {String.valueOf(idRoom),usernameLogged,"results"};
					Communication.POST("playerService/updateState", state , stateVal);
					continue;
				}
				
			}
			else if(response.equals("results") && responseP.equals("results")) {
				Communication.GET("dealerService/getCardD/"+idRoom);
				
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
					
					Communication.POST("playerService/addChips", paramName3 , paramVal3);
					
					System.out.println("Ganhou! Ganhou: " + 2*betmoney + "chips com:" + pPoints);
					
				}
				
				else if (pointsD == pPoints && pointsD < 22 && pPoints < 22){
					
					String[] paramName4 = {"name", "addChips"};
					String[] paramVal4 = {usernameLogged, String.valueOf(betmoney)};
				
					Communication.POST("playerService/addChips", paramName4 , paramVal4);

					
					System.out.println("Empatou!");
				}
				
				else{
					
					System.out.println("Perdeu!");
				}
				String[] state = {"idRoom","namePlayer","state"};
				String[] stateVal = {String.valueOf(idRoom),usernameLogged,"done"};
				Communication.POST("playerService/updateState", state , stateVal);
				
			}
			else if(response.equals("done") && responseP.equals("done")) {
				pPoints = 0;
				betmoney = 0;
				String[] state = {"idRoom","namePlayer","state"};
				String[] stateVal = {String.valueOf(idRoom),usernameLogged,"begin"};
				Communication.POST("playerService/updateState", state , stateVal);

				//System.out.print("Se desejar sair pressione e(exit) ou q(quit) !");
			
			}
			else{
				System.out.println("Esperando para jogar!");
				try {
				    Thread.sleep(1000);//two second.
				} catch(InterruptedException ex) {
				    Thread.currentThread().interrupt();
				}
			}		
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		}
		// TODO Auto-generated method stub
		
	}
	
}
