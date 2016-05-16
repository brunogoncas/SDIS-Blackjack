package logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;

public class PlayPlayer {
	
	static Scanner reader = new Scanner(System.in);
	private static String str = "";
	private static int pPoints = 0, betmoney;
	
	public static void Player(int idRoom, String usernameLogged) throws IOException {
		
		while(true) {
			
			//LER MENSAGEM SERVIDOR -> GET STATE (player e room)
			String[] paramName = {};
			String[] paramVal = {};
			String response=null, responseP=null; 
			int response2;
			String cardDealer=null;
			
			try {
				responseP= Communication.GET("playerService/getState/"+idRoom+"/"+usernameLogged);
				
				response = Communication.GET("roomService/room/"+idRoom);
				System.out.println("response:" + response + " " + responseP);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(response.equals("begin") && responseP.equals("begin")) {
				try {
					response2 = Communication.POST("playerService/updateState/"+idRoom+"/"+usernameLogged+"/"+"Bet", paramName , paramVal);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
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
						System.out.println("You did not enter a valid number to bet!");
						continue;
					}
					else{
				
				        System.out.println( "you have entered: "+ str ); 
				        betmoney = Integer.parseInt(str);
						
				        String[] paramName2 = {"name","addBet"};
						String[] paramVal2 = {usernameLogged , Integer.toString(betmoney)};
						
						try {
							response2 = Communication.POST("playerService/addBet", paramName2 , paramVal2);
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						try {
							response2 = Communication.POST("playerService/updateState/"+idRoom+"/"+usernameLogged+"/"+"getcards", paramName , paramVal);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				else {
					System.out.println("You did not enter a valid bet!");
				    continue;
				}
				
			}		
			else if(response.equals("getcards") && responseP.equals("getcards")) {
				//post para atribuir cartas ao jgoador
	
				try {
					response2 = Communication.POST("playerService/addCards/"+idRoom+"/"+usernameLogged+"/"+2, paramName , paramVal);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				//get cartas jogador
				String cardsPlayer=null;
				try {
					cardsPlayer = Communication.GET("playerService/getCards/"+idRoom+"/"+usernameLogged);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				JSONArray jArray = new JSONArray(cardsPlayer);
				 for(int i = 0; i < jArray.length(); i++){
					 String suit = jArray.getJSONObject(i).getString("suit");
					 String figure = jArray.getJSONObject(i).getString("figure");
					 pPoints += jArray.getJSONObject(i).getInt("card_value");
					 System.out.println("You got an"+ figure + " of " + suit); 
				  }
				 
				 System.out.println("Tens " + pPoints + " pontos!");
				 
				try {
					response2 = Communication.POST("playerService/updateState/"+idRoom+"/"+usernameLogged+"/"+"myturn", paramName , paramVal);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
									
			}
			else if(response.equals(usernameLogged) && responseP.equals("myturn") ) {
				
				 //get 1 carta dealer
				 try {
					 cardDealer = Communication.GET("dealerService/getCardD/"+idRoom);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 
				JSONArray jArray = new JSONArray(cardDealer);
				jArray = new JSONArray(cardDealer);
				String suit = jArray.getJSONObject(0).getString("suit");
				String figure = jArray.getJSONObject(0).getString("figure");
				int pointsD = jArray.getJSONObject(0).getInt("card_value");
				System.out.println("Dealer got an "+ figure + " of " + suit + " Points: " + pointsD); 
			
				
				
				if(pPoints >= 21) {
					try {
						response2 = Communication.POST("playerService/updateState/"+idRoom+"/"+usernameLogged+"/"+"results", paramName , paramVal);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
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
						
						try {
							response2 = Communication.POST("playerService/addCards/"+idRoom+"/"+usernameLogged+"/"+1, paramName , paramVal);
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						//get cartas jogador
						String cardsPlayer2=null;
						try {
							cardsPlayer2 = Communication.GET("playerService/getCards/"+idRoom+"/"+usernameLogged);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
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
						try {
							response2 = Communication.POST("playerService/addCards/"+idRoom+"/"+usernameLogged+"/"+1, paramName , paramVal);
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						//remove o dinheiro 
						String[] paramName2 = { "name", "removeChips"};
						String[] paramVal2 = { usernameLogged, Integer.toString(betmoney) };
						
						int response3 = 0;
						try {
							response3 = Communication.POST("playerService/removeChips", paramName2, paramVal2);
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						//get cartas jogador
						String cardsPlayer3=null;
						try {
							cardsPlayer3 = Communication.GET("playerService/getCards/"+idRoom+"/"+usernameLogged);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
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
					try {
						response2 = Communication.POST("playerService/updateState/"+idRoom+"/"+usernameLogged+"/"+"results", paramName , paramVal);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					continue;
				}
				
			}
			else if(response.equals("results") && responseP.equals("results")) {
				//ver os pontos do dealer e comparar com os meus e atribuir dinheiro ou tirar
				String dDealer = null;
				try {
					dDealer = Communication.GET("dealerService/getCardD/"+idRoom);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//get cartas dealer -> todas
				 try {
					 cardDealer = Communication.GET("dealerService/getCards/"+idRoom);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
								
				JSONArray jArray2 = new JSONArray(cardDealer);
				int pointsD=0;
				 for(int i = 0; i < jArray2.length(); i++){
					 String suit = jArray2.getJSONObject(i).getString("suit");
					 String figure = jArray2.getJSONObject(i).getString("figure");
					 pointsD += jArray2.getJSONObject(i).getInt("card_value");
					 System.out.println("Dealer got an"+ figure + " of " + suit); 
				  }
				System.out.println("Dealer got " + pointsD +" Points!"); 
				
				if((Integer.parseInt(dDealer) < pPoints && Integer.parseInt(dDealer) < 22 && pPoints < 22)
						|| (Integer.parseInt(dDealer) > 21 && pPoints < 22)){
					
					String[] paramName3 = {"name", "addChips"};
					String[] paramVal3 = {usernameLogged, String.valueOf(2*betmoney)};
					
					try {
						response2 = Communication.POST("playerService/addChips", paramName3 , paramVal3);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					System.out.println("Ganhou! Ganhou: " + 2*betmoney + "chips com:" + pPoints);
					
				}
				
				else if (Integer.parseInt(dDealer) == pPoints && Integer.parseInt(dDealer) < 22 && pPoints < 22){
					
					String[] paramName4 = {"name", "addChips"};
					String[] paramVal4 = {usernameLogged, String.valueOf(betmoney)};
				
					try {
						response2 = Communication.POST("playerService/addChips", paramName4 , paramVal4);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					System.out.println("Empatou!");
				}
				
				else{
					
					System.out.println("Perdeu!");
				}
				
				try {
					response2 = Communication.POST("playerService/updateState/"+idRoom+"/"+usernameLogged+"/"+"done", paramName , paramVal);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(response.equals("done") && responseP.equals("done")) {
				pPoints = 0;
				betmoney = 0;
				try {
					response2 = Communication.POST("playerService/updateState/"+idRoom+"/"+usernameLogged+"/"+"begin", paramName , paramVal);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else{
				System.out.println("\nESPERA!\n");
				try {
				    Thread.sleep(2000);//two second.
				} catch(InterruptedException ex) {
				    Thread.currentThread().interrupt();
				}
			}		
		}
	}
	
	public static boolean isInteger(String str) {
	    if (str == null) {
	        return false;
	    }
	    int length = str.length();
	    if (length == 0) {
	        return false;
	    }
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
	
}
