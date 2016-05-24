package logic;

import java.io.IOException;
import java.util.ArrayList;

public class FindThread extends Thread {
	private String name;
	ArrayList<String> arrayRooms = new ArrayList<String>();
	ArrayList<String> temp = new ArrayList<String>();
	
	public FindThread(String n) {
        name = n;
    }
	
    @Override
    public void run() {
         while (true) {
			String response = null;
			ArrayList<String> temp = new ArrayList<String>();
			try {
				response = Communication.GET("playerService/getUserRoom?username="+name);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(response != null) {
				String[] parts = response.split(";");
				for(int i=0; i < parts.length; i++) {
					
					if(!arrayRooms.contains(parts[i])) {
						System.out.println("\n" + name + " is now at room " + parts[i]);
						arrayRooms.add(parts[i]);
						temp.add(parts[i]);
					}
					else {
						temp.add(parts[i]);
					}	
				}
				
				//remover sala onde saiu
				if(arrayRooms.size() > temp.size()) { 
					for(int j=0; j < arrayRooms.size(); j++){
						if(!temp.contains(arrayRooms.get(j))) {
							arrayRooms.remove(j);
						}
					}
				}
				
				try {
	    		    Thread.sleep(5000);                 //5 seconds
	    		} catch(InterruptedException ex) {
	    		    Thread.currentThread().interrupt();
	    		}	
				
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
