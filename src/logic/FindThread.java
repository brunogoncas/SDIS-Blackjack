package logic;

import java.io.IOException;

public class FindThread extends Thread {
	private String name;
	
	public FindThread(String n) {
        name = n;
    }
	
    @Override
    public void run() {
         while (true) {
			String response = null;
			try {
				response = Communication.GET("playerService/getUserRoom?username="+"name");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(response != null) {
				System.out.println(name + " is now at room " + response);
				break;
			}
			
			else {
				try {
		    		    Thread.sleep(5000);                 //5 seconds
		    		} catch(InterruptedException ex) {
		    		    Thread.currentThread().interrupt();
		    		}	
			}
        }
    }
}
