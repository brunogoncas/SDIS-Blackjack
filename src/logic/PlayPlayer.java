package logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;

public class PlayPlayer {
	
	static Scanner reader = new Scanner(System.in);
	private static String str = "";
	private static int pPoints = 0, betmoney;
	
	public static void Player(int idRoom, String usernameLogged) {
		
		while(true) {
			
			//LER MENSAGEM SERVIDOR -> GET STATE (player e room)
			String[] paramName = {};
			String[] paramVal = {};
			String response=null, responseP=null; 
			int response2;
			
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
		
				System.out.println("Aposta CABRAO.");
						
				Timer timer = new Timer();
		        timer.schedule( task, 9*1000 );

		        System.out.print("Insira quanto quer apostar: ");
		        BufferedReader in = new BufferedReader(
		        new InputStreamReader( System.in ) );
		        try {
					str = in.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		        timer.cancel();
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
			else if(response.equals("getcards") && responseP.equals("getcards")) {
				//post para atribuir cartas ao jgoador
	
				try {
					response2 = Communication.POST("playerService/addCards/"+idRoom+"/"+usernameLogged, paramName , paramVal);
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
				
				 try {
					response2 = Communication.POST("playerService/updateState/"+idRoom+"/"+usernameLogged+"/"+"results", paramName , paramVal);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					
			}
			else if(response.equals("results") && responseP.equals("results")) {
				//ver os pontos do dealer e comparar com os meus e atribuir dinheiro ou tirar
				String dDealer = null;
				try {
					dDealer = Communication.GET("dealerService/getDPoints/"+idRoom);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(Integer.parseInt(dDealer) < pPoints && Integer.parseInt(dDealer) < 22 && pPoints < 22){
					
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
				break;
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
	
	static TimerTask task = new TimerTask()
	{
	    public void run()
	    {
	        if( str.equals("") )
	        {
	            System.out.println( "you input nothing. exit..." );
	            System.exit( 0 );
	        }
	    }    
    };
	
}
