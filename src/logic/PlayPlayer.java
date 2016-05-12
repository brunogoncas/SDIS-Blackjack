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
	static ArrayList<String> states = new ArrayList<String>();
	
	public static void Player(int idRoom, String usernameLogged) {
		
		while(true) {
			
			//LER MENSAGEM SERVIDOR -> GET STATE
			
			String response=null;
			try {
				response = Communication.GET("roomService/room/"+idRoom);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
				
			if(response.equals("Bet") && !states.contains("Bet")) {
		
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
		        int betmoney = Integer.parseInt(str);
				
		        String[] paramName = {"name","addBet"};
				String[] paramVal = {usernameLogged , Integer.toString(betmoney)};
				
				int response2 = 0;
				try {
					response2 = Communication.POST("playerService/addBet", paramName , paramVal);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				states.add("Bet");
			}		
			else if(response.equals("getcards") && !states.contains("getcards")) {
				//post para atribuir cartas ao jgoador
				String[] paramName = {};
				String[] paramVal = {};
				
				int response2 = 0;
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
					 System.out.println("You got an"+ figure + " of " + suit); 
				  }
				
				states.add("getcards");
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
