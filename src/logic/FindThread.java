package logic;

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
	    		    Thread.sleep(5000);                 //5 seconds
	    		} catch(InterruptedException ex) {
	    		    Thread.currentThread().interrupt();
	    		}	 
        }
    }
}
