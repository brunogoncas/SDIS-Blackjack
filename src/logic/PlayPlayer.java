package logic;

import java.io.IOException;

public class PlayPlayer {
	
	public static void Player(int idRoom) {
		
		while(true) {
			
			//LER MENSAGEM SERVIDOR -> GET STATE
			
			String response=null;
			try {
				response = Communication.GET("roomService/room/"+idRoom);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//SWITCH 
			switch (response) {
		
			case "Bet": {
		
				System.out.println("Aposta CABRAO.");
				try {
				    Thread.sleep(2000);//two second.
				} catch(InterruptedException ex) {
				    Thread.currentThread().interrupt();
				}
				break;
			}		

			default: {
				System.out.println("\nESPERA!\n");
				try {
				    Thread.sleep(2000);//two second.
				} catch(InterruptedException ex) {
				    Thread.currentThread().interrupt();
				}
				
				break;
			}
		}
					
		}
		
	}
	
}
